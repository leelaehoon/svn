<%@page import="java.util.Arrays"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>12/jstlDesc.jsp</title>
</head>
<body>
<h4>JSTL (Jsp Standard Tag Library)</h4>
<pre>
	커스텀 태그들을 모아놓은 라이브러리 형태
	커스텀 태그의 사용방법 : &lt;prefix:tagName attributes... /&gt;
	
	** JSTL 사용을 위해서는 taglib 로딩
	
	1. Core tag
		1) EL 변수(속성) 지원 : 
			&lt;c:set var="속성명" value="속성값" scope="영역" /&gt; 
			&lt;c:remove var="속성명" scope="영역" /&gt; ** 속성 삭제시 영역을 명시할 것
			<c:set var="testAttr" value="테스트" scope="request" />
			${requestScope.testAttr }
			<c:remove var="testAttr" scope="session" />			
			${requestScope.testAttr }
		2) 제어문
			- 조건문 : if (조건식) { 조건블럭 }
				&lt;c:if test="조건식"&gt;
					조건블럭
				&lt;/c:if&gt;
				<c:if test="${empty requestScope.testAttr }">
					testAttr이 지워짐
				</c:if>
				<c:if test="${not empty requestScope.testAttr }">
					testAttr이 지워지지 않았음
				</c:if>
			- 다중조건문 : choose when(조건) otherwise
				<c:choose>
					<c:when test="${empty requestScope.testAttr }">
						testAttr이 지워짐
					</c:when>
					<c:otherwise>
						testAttr이 지워지지 않았음
					</c:otherwise>
				</c:choose>
			- 반복문 : forEach forTokens
				** LoopTagStatus객체 : begin, end, step, index, count, first(boolean), last(boolean) 
				for (선언절(var, begin); 조건절(end); 증감절(step)) {}
				&lt;c:forEach var="idx" begin="1" end="10" step="2" varStatus="lts"&gt;
				for (블럭 변수 선언(var) : 반복대상집합객체(items)) {}
				<c:set var="sampleList" value='<%=Arrays.asList("listvalue1","listvalue2") %>' />
				<c:forEach items="${sampleList }" var="element">
					<span>${element }</span>
				</c:forEach>
				forTokens : 문장(items), 구분자(delims), 토큰에 대한 레퍼런스 속성(var)
				<c:forTokens items="1,2,3,4,5" delims="," var="token" varStatus="vs">
					<c:if test="${vs.first }">
						<span style="color:red">${token * 1000}</span>
					</c:if>
					<c:if test="${not vs.first }">
						<span>${token * 1000}</span>
					</c:if>
				</c:forTokens>
		3) URL 재처리(Rewrite)
			- client-side방식의 절대 경로, 쿼리스트링, url rewriting 처리...
			<c:url value="/member/memberView.do" var="viewURL">
				<c:param name="who" value="aginor" />
			</c:url>
			${viewURL }
		4) 기타기능 : redirect, import, out
<%-- 			<c:redirect url="/member/memberList.do" context="/webStudy01" /> --%>
<%-- 			<c:import url="https://www.naver.com" var="naver"></c:import> --%>
<%-- 			<c:out value="${naver }" escapeXml="false"></c:out> --%>
	2. Fmt tag
	3. Fn library
	<c:set var="target" value="ABC123DEF" />
	<c:set var="search" value="123" />
	<c:set var="targetArray" value='<%=new String[]{"ab", "cd", "ef"} %>'/>
	${fn:substringAfter(target, search) }
	${fn:substringBefore(target, search) }
	${fn:join(targetArray, "|") }
	${fn:containsIgnoreCase(target, "abc") }
</pre>
</body>
</html>