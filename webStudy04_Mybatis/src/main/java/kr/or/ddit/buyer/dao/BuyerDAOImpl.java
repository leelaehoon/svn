package kr.or.ddit.buyer.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import kr.or.ddit.mybatis.CustomSqlSessionFactoryBuilder;
import kr.or.ddit.vo.BuyerVO;
import kr.or.ddit.vo.PagingInfoVO;

public class BuyerDAOImpl implements IBuyerDAO {
	SqlSessionFactory sqlSessionFactory = CustomSqlSessionFactoryBuilder.getSqlSessionFactory();

	@Override
	public int insertBuyer(BuyerVO buyer) {
		try (
				SqlSession session = sqlSessionFactory.openSession(false);
				) {
			IBuyerDAO mapper = session.getMapper(IBuyerDAO.class);
			int rowCnt = mapper.insertBuyer(buyer);
			if (rowCnt>0) session.commit();
			return rowCnt;
		}
	}

	@Override
	public BuyerVO selectBuyer(String buyer_id) {
		try (
				SqlSession session = sqlSessionFactory.openSession();
				) {
			IBuyerDAO mapper = session.getMapper(IBuyerDAO.class);
			return mapper.selectBuyer(buyer_id);
		}
	}

	@Override
	public long selectTotalRecord(PagingInfoVO<BuyerVO> pagingVO) {
		try (
				SqlSession session = sqlSessionFactory.openSession();
				) {
			IBuyerDAO mapper = session.getMapper(IBuyerDAO.class);
			return mapper.selectTotalRecord(pagingVO);
		}
	}

	@Override
	public List<BuyerVO> selectBuyerList(PagingInfoVO<BuyerVO> pagingVO) {
		try (
				SqlSession session = sqlSessionFactory.openSession();
				) {
			IBuyerDAO mapper = session.getMapper(IBuyerDAO.class);
			return mapper.selectBuyerList(pagingVO);
		}
	}

	@Override
	public int updateBuyer(BuyerVO buyer) {
		try (
				SqlSession session = sqlSessionFactory.openSession(false);
				) {
			IBuyerDAO mapper = session.getMapper(IBuyerDAO.class);
			int rowCnt = mapper.updateBuyer(buyer);
			if (rowCnt>0) session.commit();
			return rowCnt;
		}
	}
	
}
