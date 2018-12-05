package kr.or.ddit.board.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import kr.or.ddit.mybatis.CustomSqlSessionFactoryBuilder;
import kr.or.ddit.vo.BoardVO;
import kr.or.ddit.vo.PagingInfoVO;

public class BoardDAOImpl implements IBoardDAO {
	SqlSessionFactory sqlSessionFactory = CustomSqlSessionFactoryBuilder.getSqlSessionFactory();
	
	@Override
	public int insertBoard(BoardVO board) {
		return 0;
	}

	@Override
	public long selectTotalRecord(PagingInfoVO<BoardVO> pagingVO) {
		try (
			SqlSession sqlSession = sqlSessionFactory.openSession();
		) {
			IBoardDAO mapper = sqlSession.getMapper(IBoardDAO.class);
			return mapper.selectTotalRecord(pagingVO);
		}
	}

	@Override
	public List<BoardVO> selectBoardList(PagingInfoVO<BoardVO> pagingVO) {
		try (
				SqlSession sqlSession = sqlSessionFactory.openSession();
				) {
			IBoardDAO mapper = sqlSession.getMapper(IBoardDAO.class);
			return mapper.selectBoardList(pagingVO);
		}
	}

	@Override
	public BoardVO selectBoard(long bo_no) {
		try (
				SqlSession sqlSession = sqlSessionFactory.openSession();
				) {
			IBoardDAO mapper = sqlSession.getMapper(IBoardDAO.class);
			return mapper.selectBoard(bo_no);
		}
	}

	@Override
	public void incrementHit(long bo_no) {
		try (
				SqlSession sqlSession = sqlSessionFactory.openSession(false);
				) {
			IBoardDAO mapper = sqlSession.getMapper(IBoardDAO.class);
			mapper.incrementHit(bo_no);
			sqlSession.commit();
		}
	}

	@Override
	public void incrementRcmd(long bo_no) {

	}

	@Override
	public int updateBoard(BoardVO board) {
		return 0;
	}

	@Override
	public int deleteBoard(long bo_no) {
		return 0;
	}

}
