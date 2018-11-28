<%@page import="kr.or.ddit.vo.MemberVO"%>
<%@page import="kr.or.ddit.web.modulize.ServiceType"%>
<%@page import="java.util.Objects"%>
<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	MemberVO authMember = (MemberVO)session.getAttribute("authMember");
	String cmdParam = request.getParameter("command");
	int statusCode = 0;
	String includePage = null;
	if(StringUtils.isNotBlank(cmdParam)){
		try{
			ServiceType sType = ServiceType.valueOf(cmdParam.toUpperCase());
			includePage = sType.getServicePage();
		}catch(IllegalArgumentException e){
			statusCode = HttpServletResponse.SC_NOT_FOUND;		
		}
	}
	if(statusCode!=0){
		response.sendError(statusCode);
		return;
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>/index.jsp</title>
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
<link rel="stylesheet" href="https://jqueryui.com/resources/demos/style.css">
<script type="text/javascript" src="<%=request.getContextPath() %>/js/jquery-3.3.1.min.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
</head>
<body>
<div id="top">
	<jsp:include page="/includee/header.jsp" />
</div>
<div id="left">
	<jsp:include page="/includee/left.jsp" />
</div>


<div id="body">
<%
	if(StringUtils.isNotBlank(includePage)){
		pageContext.include(includePage);
	} else {
		%>
		<h4>웰컴 페이지</h4>
		<pre>
			처음부터 웰컴 페이지로 접속하거나,
			로그인에 성공해서 웰컴 페이지로 접속하는 경우의 수가 있음.
			
			<%
				if(authMember!=null){
					%>
				<a href="<%=request.getContextPath() %>/member/mypage.do"><%=authMember.getMem_name() %></a>님(<%=authMember.getMem_auth() %>), <a href="<%=request.getContextPath() %>/login/logout.jsp">로그아웃</a>
					<%			
				}else{
					%>
				<a href="<%=request.getContextPath() %>/login/loginForm.jsp">로그인 하러가기</a>
					<%
				}
			%>
		</pre>
		<%
	}
%>
</div>


<div id="footer">
	<%
		pageContext.include("/includee/footer.jsp");
	%>
</div>
</body>
</html>