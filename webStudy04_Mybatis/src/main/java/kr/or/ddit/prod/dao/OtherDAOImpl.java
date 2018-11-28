package kr.or.ddit.prod.dao;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import kr.or.ddit.mybatis.CustomSqlSessionFactoryBuilder;
import kr.or.ddit.vo.BuyerVO;

public class OtherDAOImpl implements IOtherDAO {
	SqlSessionFactory sqlSessionFactory = CustomSqlSessionFactoryBuilder.getSqlSessionFactory();
			
	@Override
	public Map<String, Map<String, String>> selectLprodList() {
		try (
				SqlSession session = sqlSessionFactory.openSession();
				) {
			Map result = session.selectMap("kr.or.ddit.prod.dao.IOtherDAO.selectLprodList", "LPROD_GU"); 
			return result;
		}
	}

	@Override
	public List<BuyerVO> selectBuyerList(String buyer_lgu) {
		try (
				SqlSession session = sqlSessionFactory.openSession();
				) {
			IOtherDAO mapper = session.getMapper(IOtherDAO.class);
			return mapper.selectBuyerList(buyer_lgu);
		}
	}
}
