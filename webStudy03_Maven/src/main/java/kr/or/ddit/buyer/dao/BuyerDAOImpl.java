package kr.or.ddit.buyer.dao;

import java.sql.SQLException;
import java.util.List;

import com.ibatis.sqlmap.client.SqlMapClient;

import kr.or.ddit.db.ibatis.CustomSqlMapClientBuilder;
import kr.or.ddit.vo.BuyerVO;
import kr.or.ddit.vo.PagingInfoVO;

public class BuyerDAOImpl implements IBuyerDAO {
	SqlMapClient sqlMapClient = CustomSqlMapClientBuilder.getSqlMapClient();

	@Override
	public String insertBuyer(BuyerVO buyer) {
		try {
			return (String) sqlMapClient.insert("Buyer.insertBuyer", buyer);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public BuyerVO selectBuyer(String buyer_id) {
		try {
			return (BuyerVO) sqlMapClient.queryForObject("Buyer.selectBuyer", buyer_id);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public long selectTotalRecord(PagingInfoVO<BuyerVO> pagingVO) {
		try {
			return (Long) sqlMapClient.queryForObject("Buyer.selectTotalRecord", pagingVO);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<BuyerVO> selectBuyerList(PagingInfoVO<BuyerVO> pagingVO) {
		try {
			return sqlMapClient.queryForList("Buyer.selectBuyerList", pagingVO);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public int updateBuyer(BuyerVO buyer) {
		return 0;
	}
}
