package kr.or.ddit.gbook.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import kr.or.ddit.mybatis.CustomSqlSessionFactoryBuilder;
import kr.or.ddit.vo.GBookVO;
import kr.or.ddit.vo.PagingInfoVO;

public class GBookDAOImpl implements IGBookDAO {
	SqlSessionFactory sqlSessionFactory = CustomSqlSessionFactoryBuilder.getSqlSessionFactory();

	@Override
	public int insertGBook(GBookVO gbook) {
		return 0;
	}

	@Override
	public long selectTotalCount(PagingInfoVO<GBookVO> pagingVO) {
		try (
			SqlSession session = sqlSessionFactory.openSession();
		) {
			IGBookDAO mapper = session.getMapper(IGBookDAO.class);
			return mapper.selectTotalCount(pagingVO);
		}
	}

	@Override
	public List<GBookVO> selectGBookList(PagingInfoVO<GBookVO> pagingVO) {
		try (
				SqlSession session = sqlSessionFactory.openSession();
				) {
			IGBookDAO mapper = session.getMapper(IGBookDAO.class);
			return mapper.selectGBookList(pagingVO);
		}
	}

	@Override
	public int updateGBook(GBookVO gbook) {
		return 0;
	}

	@Override
	public int deleteGBook(long gb_no) {
		return 0;
	}

}
