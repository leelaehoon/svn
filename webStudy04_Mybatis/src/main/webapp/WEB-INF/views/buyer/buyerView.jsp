<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/2.2.9/semantic.min.css"/>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/2.2.9/semantic.min.js"></script>
</head>
<body>
<div class="ui raised very padded text container segment">
	<table class="ui inverted grey table">
		<tr>
			<th>거래처코드</th>
			<td>${buyer.buyer_id}</td>
		</tr>
		<tr>
			<th>거래처명</th>
			<td>${buyer.buyer_name}</td>
		</tr>
		<tr>
			<th>분류코드</th>
			<td>${buyer.buyer_lgu}</td>
		</tr>
		<tr>
			<th>거래처은행</th>
			<td>${buyer.buyer_bank}</td>
		</tr>
		<tr>
			<th>거래처계좌번호</th>
			<td>${buyer.buyer_bankno}</td>
		</tr>
		<tr>
			<th>거래처예금주</th>
			<td>${buyer.buyer_bankname}</td>
		</tr>
		<tr>
			<th>우편번호</th>
			<td>${buyer.buyer_zip}</td>
		</tr>
		<tr>
			<th>주소</th>
			<td>${buyer.buyer_add1}</td>
		</tr>
		<tr>
			<th>상세주소</th>
			<td>${buyer.buyer_add2}</td>
		</tr>
		<tr>
			<th>연락처</th>
			<td>${buyer.buyer_comtel}</td>
		</tr>
		<tr>
			<th>팩스번호</th>
			<td>${buyer.buyer_fax}</td>
		</tr>
		<tr>
			<th>거래처메일</th>
			<td>${buyer.buyer_mail}</td>
		</tr>
		<tr>
			<th>거래처담당자</th>
			<td>${buyer.buyer_charger}</td>
		</tr>
		<tr>
			<th>텔렉스트</th>
			<td>${buyer.buyer_telext}</td>
		</tr>
	</table>
	<c:set var="authorized" value="${not empty sessionScope.authMember and 'ROLE_ADMIN' eq sessionScope.authMember.mem_auth }"/>
	<c:if test="${authorized }">
		<input class="btn btn-success" type="button" value="상품수정" onclick="location.href='${pageContext.request.contextPath}/buyer/buyerUpdate.do?what=${buyer.buyer_id}'"/>
	</c:if>
</div>
<div class="ui raised very padded text container segment">
	<h4>등록한 상품</h4>
	<table class="ui inverted grey table">
		<thead>
			<tr>
				<th>상품명</th>
				<th>원가</th>
				<th>판매가</th>
				<th>상품개요</th>
				<th>상품상세</th>
			</tr>
		</thead>
		<tbody>
			<c:choose>
				<c:when test="${not empty buyer.prodList }">
					<c:forEach items="${buyer.prodList }" var="prod">
						<tr>
							<td>${prod.prod_name }</td>
							<td>${prod.prod_cost }</td>
							<td>${prod.prod_price }</td>
							<td>${prod.prod_outline }</td>
							<td>${prod.prod_detail }</td>
						</tr>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<tr>
						<td colspan="5">
							등록한 상품이 없습니다.
						</td>
					</tr>
				</c:otherwise>
			</c:choose>
		</tbody>
	</table>
</div>
</body>
</html>