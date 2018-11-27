package kr.or.ddit.prod.dao;

import java.sql.SQLException;
import java.util.List;

import com.ibatis.sqlmap.client.SqlMapClient;

import kr.or.ddit.db.ibatis.CustomSqlMapClientBuilder;
import kr.or.ddit.vo.PagingInfoVO;
import kr.or.ddit.vo.ProdVO;

public class ProdDAOImpl implements IProdDAO {
	SqlMapClient sqlMapClient = CustomSqlMapClientBuilder.getSqlMapClient();

	@Override
	public String insertProd(ProdVO prod) {
		try {
			// selectKey 엘리먼트에서 생성된 키 값
			return (String) sqlMapClient.insert("Prod.insertProd", prod);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public ProdVO selectProd(String prod_id) {
		try {
			return (ProdVO) sqlMapClient.queryForObject("Prod.selectProd", prod_id);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public long selectTotalRecord(PagingInfoVO<ProdVO> pagingVO) {
		try {
			return (Long) sqlMapClient.queryForObject("Prod.selectTotalRecord", pagingVO);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<ProdVO> selectProdList(PagingInfoVO<ProdVO> pagingVO) {
		try {
			return sqlMapClient.queryForList("Prod.selectProdList", pagingVO);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public int updateProd(ProdVO prod) {
		return 0;
	}

}
