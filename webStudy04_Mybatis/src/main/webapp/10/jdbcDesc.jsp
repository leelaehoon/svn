<%@page import="kr.or.ddit.db.ConnectionFactory"%>
<%@page import="kr.or.ddit.vo.DataBasePropertyVO"%>
<%@page import="java.sql.ResultSetMetaData"%>
<%@page import="java.util.LinkedHashMap"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>10/jdbcDesc</title>
</head>
<body>
<h4>JDBC(Java Database Connectivity)</h4>
<pre>
	데이터베이스 연결 프로그래밍 단계
	1. 드라이버를 빌드패스에 추가
	2. 드라이버 클래스 로딩
	3. 연결 객체(Connection) 생성
	4. 쿼리 객체 생성
		Statement
		PreparedStatement <!--쿼리문 고정 (SQL Injection방지) -->
		CallableStatement <!--함수나 프로시저 호출 -->
	5. 쿼리 실행(CRUD)
		ResultSet executeQuery : SELECT
		int(실행에 영향을 받은 레코드수) executeUpdate : INSERT/UPDATE/DELETE
	6. 결과 집합 사용
	7. 자원의 해제 : finally 블럭 / try~with~resource구문
	<%
		String[] headers = null;
		List<DataBasePropertyVO> dataList = new ArrayList<>();
		
		try(
			Connection conn = ConnectionFactory.getConnection();
			Statement stmt = conn.createStatement();
		){
			out.println(conn.getClass());
			StringBuffer sql = new StringBuffer();
			sql.append(" SELECT *                 ");
			sql.append(" FROM DATABASE_PROPERTIES ");
			ResultSet rs = stmt.executeQuery(sql.toString());
			ResultSetMetaData rsmd = rs.getMetaData();
			headers = new String[rsmd.getColumnCount()];
			for (int idx = 1; idx <= rsmd.getColumnCount(); idx++) {
				headers[idx-1] = rsmd.getColumnName(idx);
			}
			while(rs.next()){
				// 레코드 한건 == VO
				DataBasePropertyVO vo = new DataBasePropertyVO();
				vo.setProperty_name(rs.getString("PROPERTY_NAME"));
				vo.setProperty_value(rs.getString("PROPERTY_VALUE"));
				vo.setDescription(rs.getString("DESCRIPTION"));			
				dataList.add(vo);
			} // while end
		} // try block end
	%>
</pre>
<table>
	<thead>
		<tr>
		<% for (String head : headers) {
			%>
			<th><%= head %></th>
			<%
		   }
		%>
		</tr>
	</thead>
	<tbody>
		<%
			for (DataBasePropertyVO vo : dataList) {
				%>
				<tr>
					<td><%=vo.getProperty_name() %></td>
					<td><%=vo.getProperty_value() %></td>
					<td><%=vo.getDescription() %></td>
				</tr>				
				<%
			}
		%>
	</tbody>
</table>
</body>
</html>

















