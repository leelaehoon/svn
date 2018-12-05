package kr.or.ddit.board.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import kr.or.ddit.mybatis.CustomSqlSessionFactoryBuilder;
import kr.or.ddit.vo.PagingInfoVO;
import kr.or.ddit.vo.ReplyVO;

public class ReplyDAOImpl implements IReplyDAO {
	SqlSessionFactory sqlSessionFactory = CustomSqlSessionFactoryBuilder.getSqlSessionFactory();
	
	@Override
	public int insertReply(ReplyVO reply) {
		return 0;
	}

	@Override
	public long selectTotalRecord(PagingInfoVO<ReplyVO> pagingVO) {
		try (
			SqlSession sqlSession = sqlSessionFactory.openSession();
		) {
			IReplyDAO mapper = sqlSession.getMapper(IReplyDAO.class);
			return mapper.selectTotalRecord(pagingVO);
		}
	}

	@Override
	public List<ReplyVO> selectReplyList(PagingInfoVO<ReplyVO> pagingVO) {
		try (
				SqlSession sqlSession = sqlSessionFactory.openSession();
				) {
			IReplyDAO mapper = sqlSession.getMapper(IReplyDAO.class);
			return mapper.selectReplyList(pagingVO);
		}
	}

	@Override
	public ReplyVO selectReply(long rep_no) {
		return null;
	}

	@Override
	public int updateReply(ReplyVO reply) {
		return 0;
	}

	@Override
	public int deleteReply(long rep_no) {
		return 0;
	}

}
