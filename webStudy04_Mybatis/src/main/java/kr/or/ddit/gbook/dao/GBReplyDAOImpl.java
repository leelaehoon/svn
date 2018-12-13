package kr.or.ddit.gbook.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import kr.or.ddit.mybatis.CustomSqlSessionFactoryBuilder;
import kr.or.ddit.vo.GBReplyVO;
import kr.or.ddit.vo.PagingInfoVO;

public class GBReplyDAOImpl implements IGBReplyDAO {
	SqlSessionFactory sqlSessionFactory = CustomSqlSessionFactoryBuilder.getSqlSessionFactory();

	@Override
	public int insertGBReply(GBReplyVO gbreply) {
		try (
			SqlSession session = sqlSessionFactory.openSession(false);
		) {
			IGBReplyDAO mapper = session.getMapper(IGBReplyDAO.class);
			int rowCnt = mapper.insertGBReply(gbreply);
			if (rowCnt > 0) session.commit();
			return rowCnt;
		}
	}

	@Override
	public long selectTotalCount(PagingInfoVO<GBReplyVO> pagingVO) {
		return 0;
	}

	@Override
	public List<GBReplyVO> selectGBReplyList(PagingInfoVO<GBReplyVO> pagingVO) {
		return null;
	}

	@Override
	public GBReplyVO selectGBReply(long gbr_no) {
		try (
				SqlSession session = sqlSessionFactory.openSession();
				) {
			IGBReplyDAO mapper = session.getMapper(IGBReplyDAO.class);
			return mapper.selectGBReply(gbr_no);
		}
	}

	@Override
	public int updateGBReply(GBReplyVO gbreply) {
		return 0;
	}

	@Override
	public int deleteGBReply(long gbr_no) {
		try (
				SqlSession session = sqlSessionFactory.openSession(false);
				) {
			IGBReplyDAO mapper = session.getMapper(IGBReplyDAO.class);
			int rowCnt = mapper.deleteGBReply(gbr_no);
			if (rowCnt > 0) session.commit();
			return rowCnt;
		}
	}

}
