package kr.or.ddit.board.dao;

import org.apache.ibatis.session.SqlSessionFactory;

import kr.or.ddit.mybatis.CustomSqlSessionFactoryBuilder;
import kr.or.ddit.vo.PdsVO;

public class PdsDAOImpl implements IPdsDAO {
	SqlSessionFactory sqlSessionFactory = CustomSqlSessionFactoryBuilder.getSqlSessionFactory();

	@Override
	public int insertPds(PdsVO pds) {
		return 0;
	}

	@Override
	public PdsVO selectPDs(long pds_no) {
		return null;
	}

	@Override
	public int deletePds(long pds_no) {
		return 0;
	}

}
