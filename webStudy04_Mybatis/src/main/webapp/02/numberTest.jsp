<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
	table{border: 1px solid black;}
</style>
</head>
<body>
<%
	String minDanStr = request.getParameter("minDan");
	String maxDanStr = request.getParameter("maxDan");
	
	if (minDanStr == null || !minDanStr.matches("\\d") || maxDanStr == null || !maxDanStr.matches("[0-9]")) {
		response.sendError(400);
		return;
	}
%>
<form>
	최소단: <input type="number" name="minDan" value="<%=minDanStr %>" />
	최대단: <input type="number" name="maxDan" value="<%=maxDanStr %>" />
	<input type="submit" value="구구단" />
</form>
<table>
		<% 
			int	minDan = Integer.parseInt(minDanStr);
			int	maxDan = Integer.parseInt(maxDanStr);
			
			String pattern = "%d * %d = %d";
			for (int dan = minDan; dan <= maxDan; dan++){%>
			<tr>
			<%for(int mul = 1; mul <= 9; mul++) {%>
				<td>
				<%=String.format(pattern, dan, mul, dan*mul)%>
				</td>
			<%}%>
			</tr>
		<%}%>
</table>
<ul>
	<%
		for(int number = 1; number <= 50; number++) {
			%>
			<li><%=number %></li>
			<%
		}
	%>
</ul>
</body>
</html>