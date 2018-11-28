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
<link href="<%=request.getContextPath() %>/css/main.css" rel="stylesheet" />
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>
<nav class="navbar navbar-inverse">
  <div class="container-fluid">
    <div class="navbar-header">
      <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>                        
      </button>
      <a class="navbar-brand" href="#">Logo</a>
    </div>
    <div class="collapse navbar-collapse" id="myNavbar">
		<jsp:include page="/includee/header.jsp" />
      <ul class="nav navbar-nav navbar-right">
	      <%
	      	if (authMember==null) {
	      %>
        	<li><a href="<%=request.getContextPath() %>/login/loginForm.jsp"><span class="glyphicon glyphicon-log-in"></span> Login</a></li>
          <%
	      	} else {          
          %>
        	<li><a href="<%=request.getContextPath() %>/member/mypage.do"><span class="glyphicon glyphicon-user"></span> MyPage</a></li>
        	<li><a href="<%=request.getContextPath() %>/login/logout.jsp"><span class="glyphicon glyphicon-log-out"></span> Logout</a></li>
          <%
	      	}
          %>
      </ul>
    </div>
  </div>
</nav>

<div class="container-fluid text-center">    
  <div class="row content">
	<jsp:include page="/includee/left.jsp" />
    <div class="col-sm-10 text-left"> 
	    <div id="body">
		<%
			if(StringUtils.isNotBlank(includePage)){
				pageContext.include(includePage);
			} else {
				
			}
		%>
		</div>
    </div>
  </div>
</div>

<footer class="container-fluid text-center">
	<%
		pageContext.include("/includee/footer.jsp");
	%>
</footer>

</body>
</html>