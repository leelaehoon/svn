<%@page import="java.util.TimeZone"%>
<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>12/fmtDesc.jsp</title>
<style type="text/css">
	input[type='image'] {
		width: 50px;
		height: 50px;
		border: 1px solid black;
		border-radius: 100%;
	}
	div{
		width: 50%;
		margin: auto;
	}
</style>
</head>
<body>
<div>
	<h4>국제화태그(format tag, fmt tag)</h4>
	<input type="image" src="<c:url value='https://image.flaticon.com/icons/svg/197/197582.svg' />" onclick="location.href='?locale=ko_KR'" />
	<input type="image" src="<c:url value='https://image.flaticon.com/icons/svg/197/197484.svg' />" onclick="location.href='?locale=en_US'" />
	<input type="image" src="<c:url value='https://image.flaticon.com/icons/svg/197/197604.svg' />" onclick="location.href='?locale=ja_JP'" />
	<input type="image" src="<c:url value='https://image.flaticon.com/icons/svg/197/197375.svg' />" onclick="location.href='?locale=zh_CN'" />
	<input type="image" src="<c:url value='https://image.flaticon.com/icons/svg/197/197571.svg' />" onclick="location.href='?locale=de_DE'" />
	<br>
	<input type="image" src="<c:url value='https://image.flaticon.com/icons/svg/197/197583.svg' />" onclick="location.href='?locale=fr_BE'" />
	<input type="image" src="<c:url value='https://image.flaticon.com/icons/svg/197/197593.svg' />" onclick="location.href='?locale=es_ES'" />
	<input type="image" src="<c:url value='https://image.flaticon.com/icons/svg/197/197573.svg' />" onclick="location.href='?locale=es_AR'" />
	<input type="image" src="<c:url value='https://image.flaticon.com/icons/svg/197/197386.svg' />" onclick="location.href='?locale=pt_BR'" />
	<input type="image" src="<c:url value='https://image.flaticon.com/icons/svg/197/197518.svg' />" onclick="location.href='?locale=tr_TR'" />
</div>
<pre>
	1. 국제화(i18n, internationalization)
		setLocale, setBundle/bundle, message
		1) 언어 결정
		2) 메시지 번들 작성(basename_locale) : 로케일 정보는 basename 포함되지 않음
		** setLocale : 로딩할 메시지 번들의 로케일을 결정
		3) 메시지 번들 로딩(setBundle/bundle)
		4) 메시지 키를 이용해 메시지를 출력(message)
		<c:if test="${not empty param.locale }">
			<fmt:setLocale value="${param.locale }"/>
		</c:if>
		<fmt:bundle basename="kr.or.ddit.msgs.message">
			<fmt:message key="bow" />	
		</fmt:bundle>
		<%
			String[] zoneIds = TimeZone.getAvailableIDs();
			TimeZone zone = TimeZone.getTimeZone(zoneIds[3]);
		%>
		<%=zone.getDisplayName() %>
	2. 지역화(l10n, localization)
		파싱(parseNumber, parseDate), 포맷팅(formatNumber, formatDate)
		<fmt:formatDate value="<%=new Date() %>" type="both" timeZone="<%=zone %>" dateStyle="long" timeStyle="long" />
		<fmt:formatNumber value="100000" type="currency" var="numStr" />
		${numStr }
		------------------------
		<fmt:parseNumber value="${numStr }" type="currency" parseLocale="${param.locale }" var="number" />
		${number*3 }
</pre>
</body>
</html>