package kr.or.ddit.board.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.ibatis.session.SqlSession;

import kr.or.ddit.BoardException;
import kr.or.ddit.ServiceResult;
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

	@Override
	public ServiceResult createBoard(BoardVO board) {
		try (
			SqlSession session = CustomSqlSessionFactoryBuilder.getSqlSessionFactory().openSession(false);
		) {
			int rowCnt = boardDAO.insertBoard(board, session);
			int check = 1;
			File saveFolder = new File("d:/boardFiles");
			if (!saveFolder.exists()) saveFolder.mkdirs();
			if (rowCnt > 0) {
				List<PdsVO> pdsList = board.getPdsList();
				if (pdsList != null) {
					check += pdsList.size();
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
			}
			ServiceResult result = ServiceResult.FAILED;
			if (rowCnt >= check) {
				result = ServiceResult.SUCCESS;
				session.commit();
			}
			return result;
		}
		
		
//		ServiceResult result = ServiceResult.SUCCESS;
//		if (boardDAO.insertBoard(board) > 0) {
//			List<PdsVO> pdsList = board.getPdsList();
//			if (pdsList !=null && pdsList.size() > 0) {
//				for (PdsVO pds : pdsList) {
//					pds.setBo_no(board.getBo_no());
//					int rowCnt = pdsDAO.insertPds(pds);
//					if (rowCnt < 0) {
//						result = ServiceResult.FAILED;
//						break;
//					}
//				}
//			}
//		} else {
//			result = ServiceResult.FAILED;
//		}
//		return result;
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
		return null;
	}

	@Override
	public ServiceResult removeBoard(BoardVO board) {
		return null;
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
