<%@page import="kr.or.ddit.utils.CookieUtil"%>
<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@page import="java.util.Objects"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String savedId = new CookieUtil(request).getCookieValue("idCookie");
	String message = (String) session.getAttribute("message");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>login/loginForm.jsp</title>
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
<link rel="stylesheet" href="https://jqueryui.com/resources/demos/style.css">
<script type="text/javascript" src="<%=request.getContextPath() %>/js/jquery-3.3.1.min.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
<script type="text/javascript">
	<%
		if (StringUtils.isNotBlank(message)) {
	%>
			alert("<%=message%>");
	<%
			session.removeAttribute("message");
		}
	%>
</script>
</head>
<body>
<form action="<%=request.getContextPath() %>/login/loginCheck.jsp" method="post">
  <div class="form-group">
    <label for="mem_id">Email address:</label>
    <input type="text" class="form-control" id="mem_id" name="mem_id" value="<%=Objects.toString(savedId, "") %>">
  </div>
  <div class="form-group">
    <label for="mem_pass">Password:</label>
    <input type="password" class="form-control" id="mem_pass" name="mem_pass">
  </div>
  <div class="checkbox">
    <label><input type="checkbox" name="idChecked" value="idSaved" <%=StringUtils.isNotBlank(savedId)?"checked":"" %>/> Remember me</label>
  </div>
  <input type="submit" class="btn btn-default" value="Login" />
  
  
<!-- 	<ul> -->
<!-- 		<li> -->
<%-- 			아이디 : <input type="text" name="mem_id" value="<%=Objects.toString(savedId, "") %>" /> --%>
<!-- 			<label for="idChecked"> -->
<%-- 				<input type="checkbox" name="idChecked" value="idSaved" <%=StringUtils.isNotBlank(savedId)?"checked":"" %>/>아이디기억하기 --%>
<!-- 			</label> -->
<!-- 		</li> -->
<!-- 		<li> -->
<!-- 			비밀번호 : <input type="password" name="mem_pass" /> -->
<!-- 			<input type="submit" value="로그인" /> -->
<!-- 		</li> -->
<!-- 	</ul> -->
</form>
</body>
</html>