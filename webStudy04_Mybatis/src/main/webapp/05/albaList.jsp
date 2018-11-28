<%@page import="java.util.Map.Entry"%>
<%@page import="kr.or.ddit.web.SimpleFormProcessServlet"%>
<%@page import="kr.or.ddit.vo.AlbaVO"%>
<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	Map<String, AlbaVO> albaTable =(Map<String, AlbaVO>) application.getAttribute("albaTable");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>05/albaList.jsp</title>
</head>
<body>
<table>
	<thead>
		<tr>
			<th>알바생코드</th>
			<th>이름</th>
			<th>주소</th>
			<th>연락처</th>
		</tr>
	</thead>
	<tbody>
		<%
			// attr로 받아온 맵
			Map<String, AlbaVO> albaMap = albaTable;
			for(Entry<String, AlbaVO> entry : albaMap.entrySet()){
				String pattern = "<tr><td>%s</td><td>%s</td><td>%s</td><td>%s</td></tr>";
				AlbaVO alba = entry.getValue();
				out.println(String.format(pattern, alba.getCode(), alba.getName(), alba.getAddress(), alba.getTel()));
			}
		%>
	</tbody>
</table>
</body>
</html>