<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>11/elObject.jsp</title>
</head>
<body>
<h4>EL의 기본객체</h4>
${pageContext.request }
<pre>
	<%
		pageContext.setAttribute("test 5", "테스트 벨류");
	%>
	1. Scope 객체(Map&lt;String, Object&gt;) : pageScope, requestScope, sessionScope, applicationScope
<%-- 		${pageScope.test 5 },  --%>	${pageScope["test 5"] }
			${not empty sessionScope.authMember?sessionScope.authMember["mem_id"]:"로그인놉" }
	2. Parameter 객체 : param(Map&lt;String, String&gt;), paramValues(Map&lt;String, String[]&gt;)
			${param.test }, ${param["test"] }
			${paramValues.test[1] }, ${paramValues["test"][1] }
	3. Request Header 객체 : header(), headerValues(Map&lt;String, String[]&gt;)
			${header["accept"] }
			${header["user-agent"] }
<%-- 			${headerValues.user-agent } --%>
			${headerValues["user-agent"] }
	4. Cookie 객체 : cookie(Map&lt;String, Cookie&gt;)
			${cookie.JSESSIONID.value }, ${cookie["JSESSIONID"]["value"] }
	5. Context Parameter 객체 : initParam(Map&lt;String, String&gt;)
			${initParam.contentFolder }, <%=application.getInitParameter("contentFolder") %>
			${initParam["contentFolder"] }
	6. pageContext : PageContext
			<%=request.getContextPath() %>
			${pageContext.request.contextPath }
			${pageContext.request.remoteAddr }, <%=request.getRemoteAddr() %>
</pre>
</body>
</html>