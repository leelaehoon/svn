<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<!-- <meta http-equiv="Refresh" content="3;url=http://www.naver.com"> -->
<title>04/autoRequest.jsp</title>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-3.3.1.min.js"></script>
</head>
<body>
<h4>자동 요청을 발생시키는 방법</h4>
<h4><span id="countArea"></span>초 뒤에 네이버로 이동함</h4>
<!-- getServerTime.jsp 쪽으로 1초마다 비동기 요청을 발생시키고,
JSON형태로 전송된 응답데이터에서 serverTime프로퍼티로 부터 확보
serverClock span태그에 현재 서버의 시각이 갱신되도록 -->
<pre>
	1. Server-side
		<%
			/* response.setIntHeader("Refresh", 1); */
		%>
		현재 서버의 시간 : <span id="serverClock"></span>
	2. Client-side
		HTML : meta태그 이용
		Javascript : 스케쥴링 함수 이용
</pre>
<script type="text/javascript">
	var serverClock = $("#serverClock")
	setInterval(function () {
		$.ajax({
			url : "<%=request.getContextPath() %>/05/getServerTime.jsp",
			dataType : "json", // Accept|Content-Type
			success : function(resp) {
				serverClock.html(resp.serverTime)
			},
			error : function(resp) {
				console.log(resp.status + ", " + resp.responseText)
			}
		})
	}, 1000)
/* 	var wait = 1
	var count = wait
	var spanTag = document.getElementById("countArea")
	setInterval(function () {
		count--
		spanTag.innerHTML = count
	}, 1000)
	setTimeout(function () {
		location.reload()
		location.href="http://www.naver.com" 
	}, wait*1000) */
</script>
</body>
</html>













