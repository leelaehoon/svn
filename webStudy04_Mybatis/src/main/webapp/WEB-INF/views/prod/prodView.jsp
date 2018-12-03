<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css"
	integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO"
	crossorigin="anonymous">
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery-3.3.1.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"
	integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49"
	crossorigin="anonymous"></script>
<script
	src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"
	integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy"
	crossorigin="anonymous"></script>
<script type="text/javascript">
</script>
</head>
<body class="container">
	<input type="button" class="btn btn-info" value="Back" onclick="location.href='${pageContext.request.contextPath}/prod/prodList.do'"/>
	<table class="table table-dark">
		<tr>
			<th>상품코드</th>
			<td>${prod.prod_id}</td>
		</tr>
		<tr>
			<th>상품명</th>
			<td>${prod.prod_name}</td>
		</tr>
		<tr>
			<th>분류명</th>
			<td>${prod.lprod_nm}</td>
		</tr>
		<tr>
			<th>거래처정보</th>
			<td>
				<table>
					<thead>
						<tr>
							<th>거래처명</th>
							<th>소재지</th>
							<th>담당자명</th>
							<th>연락처</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>${prod.buyer.buyer_name}</td>
							<td>${prod.buyer.buyer_add1}</td>
							<td>${prod.buyer.buyer_charger}</td>
							<td>${prod.buyer.buyer_comtel}</td>
						</tr>
					</tbody>
				</table>
			</td>
		</tr>
		<tr>
			<th>코스트</th>
			<td>${prod.prod_cost}</td>
		</tr>
		<tr>
			<th>프라이스</th>
			<td>${prod.prod_price}</td>
		</tr>
		<tr>
			<th>세일</th>
			<td>${prod.prod_sale}</td>
		</tr>
		<tr>
			<th>상품개요</th>
			<td>${prod.prod_outline}</td>
		</tr>
		<tr>
			<th>상품디테일</th>
			<td>${prod.prod_detail}</td>
		</tr>
		<tr>
			<th>상품이미지</th>
			<td>
				<img src="<c:url value='/prodImages/${prod.prod_img}' />" />
			</td>
		</tr>
		<tr>
			<th>토탈스톡</th>
			<td>${prod.prod_totalstock}</td>
		</tr>
		<tr>
			<th>인스데이트</th>
			<td>${prod.prod_insdate}</td>
		</tr>
		<tr>
			<th>프로퍼스톡</th>
			<td>${prod.prod_properstock}</td>
		</tr>
		<tr>
			<th>상품사이즈</th>
			<td>${prod.prod_size}</td>
		</tr>
		<tr>
			<th>상품칼라</th>
			<td>${prod.prod_color}</td>
		</tr>
		<tr>
			<th>상품배달</th>
			<td>${prod.prod_delivery}</td>
		</tr>
		<tr>
			<th>상품단위</th>
			<td>${prod.prod_unit}</td>
		</tr>
		<tr>
			<th>입고량</th>
			<td>${prod.prod_qtyin}</td>
		</tr>
		<tr>
			<th>출고량</th>
			<td>${prod.prod_qtysale}</td>
		</tr>
		<tr>
			<th>마일리지</th>
			<td>${prod.prod_mileage}</td>
		</tr>
	</table>
	<c:set var="authorized" value="${not empty sessionScope.authMember and 'ROLE_ADMIN' eq sessionScope.authMember.mem_auth }"/>
	<c:if test="${authorized }">
	<input class="btn btn-success" type="button" value="상품수정" onclick="location.href='${pageContext.request.contextPath}/prod/prodUpdate.do?what=${prod.prod_id}'"/>
	<h4>구매자 목록</h4>
	<table class="table table-light">
		<thead>
			<tr>
				<th>회원아이디</th>
				<th>회원명</th>
				<th>주소</th>
				<th>연락처</th>
				<th>이메일</th>
			</tr>
		</thead>
		<tbody>
			<c:set var="customers" value="${prod.customers }" />
			<c:choose>
				<c:when test="${not empty customers }">
					<c:forEach items="${customers }" var="tmp">
						<tr>
							<td>${tmp.mem_id}</td>
							<td>${tmp.mem_name}</td>
							<td>${tmp.address}</td>
							<td>${tmp.mem_hp}</td>
							<td>${tmp.mem_mail}</td>
						</tr>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<tr>
						<td colspan="5">구매자가 없습니다.</td>
					</tr>
				</c:otherwise>
			</c:choose>
		</tbody>
	</table>
	</c:if>
</body>
</html>