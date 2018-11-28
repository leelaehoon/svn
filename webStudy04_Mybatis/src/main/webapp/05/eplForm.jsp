<%@page import="java.util.Map.Entry"%>
<%@page import="java.util.LinkedHashMap"%>
<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%!
	public Map<String, String[]> eplMap = new LinkedHashMap<>();
{
	// git commit용 주석
	eplMap.put("E001", new String[]{"맨체스터시티", "/WEB-INF/EPL/MCI.jsp"});	
	eplMap.put("E002", new String[]{"리버풀", "/WEB-INF/EPL/LIV.jsp"});	
	eplMap.put("E003", new String[]{"첼시", "/WEB-INF/EPL/CHE.jsp"});	
	eplMap.put("E004", new String[]{"토트넘", "/WEB-INF/EPL/TOT.jsp"});	
	eplMap.put("E005", new String[]{"아스날", "/WEB-INF/EPL/ARS.jsp"});	
	eplMap.put("E006", new String[]{"맨체스터유나이티드", "/WEB-INF/EPL/MU.jsp"});	
}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>05/empForm.jsp</title>
<script type="text/javascript">
	function eventHandler() {
		document.forms[0].submit();
	}
</script>
</head>
<body>
<form action="<%=request.getContextPath() %>/05/getEPL.jsp">
	<select name="team" onchange="eventHandler()">
		<option value="">팀 선택</option>
		<%
			for(Entry<String, String[]> tmp : eplMap.entrySet()){
				out.println(String.format("<option value='%s'>%s</option>", tmp.getKey(), tmp.getValue()[0]));
			}
		%>
		<option value="E007">에버턴</option>
	</select>
</form>
</body>
</html>