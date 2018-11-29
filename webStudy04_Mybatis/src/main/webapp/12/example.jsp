<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>12/example.jsp</title>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css"
	integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO"
	crossorigin="anonymous">
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/jquery-3.3.1.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"
	integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49"
	crossorigin="anonymous"></script>
<script
	src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"
	integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy"
	crossorigin="anonymous"></script>
<style type="text/css">
	.red{color:red;}
	.orange{color:orange;}
	.yellow{color:yellow;}
	.green{color:green;}
	.blue{color:blue;}
	.navy{color:navy;}
</style>
</head>
<body>
	<form class="container">
		<div class="form-group row">
			<div class="col-xs-2">
				최소단 : <input class="form-control" type="number" min="1" max="100" value="${param.minDan }" name="minDan"/>
			</div>
			<div class="col-xs-2">
				최대단 : <input class="form-control" type="number" min="1" max="100" value="${param.maxDan }" name="maxDan"/>
			</div>
			<div class="col-xs-2">
				<input class="btn btn-default" type="submit" value="전송" />
			</div>
		</div>
	</form>
	<c:if test="${not empty param.minDan and not empty param.maxDan and param.minDan lt param.maxDan}">
		<div class="container">
			<table class="table">
			<c:forEach var="idx" begin="${param.minDan }" end="${param.maxDan }" varStatus="lts">
				<c:choose>
					<c:when test="${lts.first }">
						<c:set var="color" value="red" />
					</c:when>
					<c:when test="${lts.count%2 eq 0 }">
						<c:set var="color" value="blue" />
					</c:when>
					<c:otherwise>
						<c:remove var="color"/>
					</c:otherwise>
				</c:choose>
				<tr class=${color }>
				<c:forEach var="idx2" begin="1" end="9">
					<td>${idx } * ${idx2 } = ${idx*idx2 }</td>
				</c:forEach>
				</tr>
			</c:forEach>
			</table>
		</div>
	</c:if>
	
	<form>
		당신의 나이는? <input type="number" name="age" value="${param.age }" />
		<input type="submit" value="전송" />
	</form>
<!-- 	age 파라미터가 있다면, -->
<!-- 	1. age가 20대면 "방가" red -->
<!-- 	2. age가 30대면 "방가 행님" orange -->
<!-- 	3. age가 40대면 "방가 큰행님" yellow -->
<!-- 	4. age가 50대면 "방가 왕큰행님" green -->
<!-- 	5. age가 그 이상이면 "어서오세요" blue -->
<!-- 	6. age가 20대 미만이면 "나가" purple -->
		<c:choose>
			<c:when test="${not empty param.age }">
				<c:choose>
				<c:when test="${param.age ge 60}">
					<c:set var="msg" value="60대이상" />
					<c:set var="color" value="red" />					
				</c:when>
				<c:when test="${param.age ge 50}">
					<c:set var="msg" value="50대" />					
					<c:set var="color" value="orange" />					
				</c:when>
				<c:when test="${param.age ge 40}">
					<c:set var="msg" value="40대" />					
					<c:set var="color" value="yellow" />					
				</c:when>
				<c:when test="${param.age ge 30}">
					<c:set var="msg" value="30대" />					
					<c:set var="color" value="green" />					
				</c:when>
				<c:when test="${param.age ge 20}">
					<c:set var="msg" value="20대" />					
					<c:set var="color" value="blue" />					
				</c:when>
				<c:otherwise>
					<c:set var="msg" value="나가" />					
					<c:set var="color" value="navy" />					
				</c:otherwise>
			</c:choose>
			</c:when>
			<c:otherwise>
					<c:set var="msg" value="나이를 입력해주세요" />					
					<c:set var="color" value="purple"  />					
			</c:otherwise>
		</c:choose>
	<div id="msgArea" style="color:${color}">
		${msg }
	</div>
</body>
</html>