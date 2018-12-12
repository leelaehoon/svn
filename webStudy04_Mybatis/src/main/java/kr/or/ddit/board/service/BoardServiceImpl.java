package kr.or.ddit.board.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import kr.or.ddit.ServiceResult;
import kr.or.ddit.board.BoardException;
import kr.or.ddit.board.dao.BoardDAOImpl;
import kr.or.ddit.board.dao.IBoardDAO;
import kr.or.ddit.board.dao.IPdsDAO;
import kr.or.ddit.board.dao.PdsDAOImpl;
import kr.or.ddit.mybatis.CustomSqlSessionFactoryBuilder;
import kr.or.ddit.vo.BoardVO;
import kr.or.ddit.vo.PagingInfoVO;
import kr.or.ddit.vo.PdsVO;

public class BoardServiceImpl implements IBoardService {
	IBoardDAO boardDAO = new BoardDAOImpl();
	IPdsDAO pdsDAO = new PdsDAOImpl();
	File saveFolder = new File("d:/boardFiles");
	{
		if (!saveFolder.exists()) saveFolder.mkdirs();
	}
	
	private int processFiles(BoardVO board, SqlSession session) {
		int rowCnt = 0;
		List<PdsVO> pdsList = board.getPdsList();

		if (pdsList != null) {
			rowCnt += pdsDAO.insertPdsList(board, session);
			for (PdsVO pds : pdsList) {
				try (
						InputStream in = pds.getFileItem().getInputStream();
				) {
					FileUtils.copyInputStreamToFile(in, new File(saveFolder, pds.getPds_savename()));
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
		}
		Long[] delFiles = board.getDelFiles();
		if (delFiles != null) {
			rowCnt += pdsDAO.deletePdses(board, session);
			String[] saveNames = new String[delFiles.length];
			for (int i = 0; i < delFiles.length; i++) {
				saveNames[i] = pdsDAO.selectPDs(delFiles[i]).getPds_savename();
			}
			for (String saveName : saveNames) {
				FileUtils.deleteQuietly(new File(saveFolder,saveName));
			}
		}
		return rowCnt;
	}

	@Override
	public ServiceResult createBoard(BoardVO board) {
		try (
			SqlSession session = CustomSqlSessionFactoryBuilder.getSqlSessionFactory().openSession(false);
		) {
			int rowCnt = boardDAO.insertBoard(board, session);
			int check = 1;
			if (rowCnt > 0) {
				if (board.getPdsList() != null) check += board.getPdsList().size();
				rowCnt += processFiles(board, session);
			}
			ServiceResult result = ServiceResult.FAILED;
			if (rowCnt >= check) {
				result = ServiceResult.SUCCESS;
				session.commit();
			}
			return result;
		}
	}


	@Override
	public long retrieveBoardCount(PagingInfoVO<BoardVO> pagingVO) {
		return boardDAO.selectTotalRecord(pagingVO);
	}

	@Override
	public List<BoardVO> retrieveBoardList(PagingInfoVO<BoardVO> pagingVO) {
		return boardDAO.selectBoardList(pagingVO);
	}

	@Override
	public BoardVO retrieveBoard(long bo_no) {
		BoardVO board = boardDAO.selectBoard(bo_no);
		if (board==null) {
			throw new BoardException(bo_no + "번은 없는 게시글번호 입니다.");
		}
		boardDAO.incrementHit(bo_no);
		return board;
	}

	@Override
	public ServiceResult modifyBoard(BoardVO board) {
		try (
			SqlSession session = CustomSqlSessionFactoryBuilder.getSqlSessionFactory().openSession();
		) {
			BoardVO check = boardDAO.selectBoard(board.getBo_no());
			ServiceResult result = null;
			if (check != null) {
				if (check.getBo_pass().equals(board.getBo_pass())) {
					int rowCnt = boardDAO.updateBoard(board, session);
					int checkCnt = rowCnt;
					if (rowCnt > 0) {
						if (board.getPdsList() != null) checkCnt += board.getPdsList().size();
						if (board.getDelFiles() != null) checkCnt += board.getDelFiles().length;
						rowCnt += processFiles(board, session);
					}
					if (rowCnt >= checkCnt) {
						session.commit();
						result = ServiceResult.SUCCESS;
					} else {
						result = ServiceResult.FAILED;
					}
				} else {
					result = ServiceResult.INVALIDPASSWORD;
				}
			} else {
				throw new BoardException();
			}
			return result;
		}
	}

	@Override
	public ServiceResult removeBoard(BoardVO board) {
		try (
			SqlSession session = CustomSqlSessionFactoryBuilder.getSqlSessionFactory().openSession(false);
		) {
			BoardVO savedBoard = retrieveBoard(board.getBo_no());
			ServiceResult result = null;
			if (savedBoard.getBo_pass().equals(board.getBo_pass())) {
				int rowCnt = boardDAO.deleteBoard(board.getBo_no(), session);	
				if (rowCnt > 0) {
					List<PdsVO> pdsList = savedBoard.getPdsList();
					if (pdsList != null && pdsList.size() > 0) {
						for (PdsVO pds : pdsList) {
							FileUtils.deleteQuietly(new File(saveFolder, pds.getPds_savename()));
						}
					} // 첨부파일 체크 if end
					result = ServiceResult.SUCCESS;
					session.commit();
				} else {
					result = ServiceResult.FAILED;
				}
			} else {
				result = ServiceResult.INVALIDPASSWORD;
			}
			
			return result;
		}
	}

	@Override
	public PdsVO downloadPds(long pds_no) {
		PdsVO pds = pdsDAO.selectPDs(pds_no);
		if (pds == null) {
			throw new BoardException(pds_no + "번에 해당하는 파일이 없습니다.");
		}
		return pds;
	}
}
