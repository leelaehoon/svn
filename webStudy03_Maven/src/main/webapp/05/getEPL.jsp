<%@page import="java.util.LinkedHashMap"%>
<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- 1. 파라미터 확보
2. 검증(필수데이터 검증, 유효데이터 검증)
3. 불통
	1) 필수데이터 누락 : 400
	2) 우리가 관리하지 않는 멤버를 요구한 경우 : 404
4. 통과
	이동(맵에 있는 개인 페이지, 클라이언트가 멤버 개인페이지의 주소를 모르도록)
	이동(맵에 있는 개인 페이지, getEPL에서 원본 요청을 모두 처리했을 경우, UI 페이지로 이동할 때) -->
<%!
	public Map<String, String[]> eplMap = new LinkedHashMap<>();
{
	eplMap.put("E001", new String[]{"맨체스터시티", "/WEB-INF/EPL/MCI.jsp"});	
	eplMap.put("E002", new String[]{"리버풀", "/WEB-INF/EPL/LIV.jsp"});	
	eplMap.put("E003", new String[]{"첼시", "/WEB-INF/EPL/CHE.jsp"});	
	eplMap.put("E004", new String[]{"토트넘", "/WEB-INF/EPL/TOT.jsp"});	
	eplMap.put("E005", new String[]{"아스날", "/WEB-INF/EPL/ARS.jsp"});	
	eplMap.put("E006", new String[]{"맨체스터유나이티드", "/WEB-INF/EPL/MU.jsp"});	
}
%>
<%
	request.setCharacterEncoding("UTF-8");
	String team = request.getParameter("team");
	int statusCode = 0;
	
	if(team==null || team.trim().length()==0){
		statusCode = HttpServletResponse.SC_BAD_REQUEST;
	} else if(!eplMap.containsKey(team)) {
		statusCode = HttpServletResponse.SC_NOT_FOUND;
	}
	
	if(statusCode!=0){
		response.sendError(statusCode);
		return;
	}
	
	String[] value = eplMap.get(team);
	String goPage = value[1];
	
	RequestDispatcher rd = request.getRequestDispatcher(goPage);
	rd.forward(request, response);
	/* response.sendRedirect(request.getContextPath() + goPage); */
%>