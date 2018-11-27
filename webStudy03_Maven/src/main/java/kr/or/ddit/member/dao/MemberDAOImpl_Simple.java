package kr.or.ddit.member.dao;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;

import kr.or.ddit.db.ConnectionFactory;
import kr.or.ddit.db.ibatis.SampleDataMapper;
import kr.or.ddit.vo.MemberVO;
import kr.or.ddit.vo.PagingInfoVO;

public class MemberDAOImpl_Simple implements IMemberDAO {


	@Override
	public MemberVO selectMember(String mem_id) {
		MemberVO member = null;
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT                                               ");
	    sql.append(" MEM_ID,    MEM_PASS,    MEM_NAME,    MEM_REGNO1,     ");
	    sql.append(" MEM_REGNO2,    TO_CHAR(MEM_BIR, 'YYYY-MM-DD') MEM_BIR,    MEM_ZIP,    MEM_ADD1,     ");
	    sql.append(" MEM_ADD2,    MEM_HOMETEL,    MEM_COMTEL,    MEM_HP,  ");
	    sql.append(" MEM_MAIL,    MEM_JOB,    MEM_LIKE,    MEM_MEMORIAL,  ");
	    sql.append(" TO_CHAR(MEM_MEMORIALDAY, 'YYYY-MM-DD') MEM_MEMORIALDAY,    MEM_MILEAGE,    MEM_DELETE       ");
		sql.append(" FROM MEMBER                                          ");
		sql.append(" WHERE MEM_ID = ?		                              "); // ? = 쿼리파라미터
		try (Connection conn = ConnectionFactory.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());) {
			pstmt.setString(1, mem_id);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				member =  (MemberVO) SampleDataMapper.resultSetToJavaObject(rs, MemberVO.class);
			}
			return member;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public int insertMember(MemberVO member) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<MemberVO> selectMemberList(PagingInfoVO pagingVO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int updateMember(MemberVO member) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteMember(String mem_id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long selectTotalRecord(PagingInfoVO pagingVO) {
		// TODO Auto-generated method stub
		return 0;
	}

}
