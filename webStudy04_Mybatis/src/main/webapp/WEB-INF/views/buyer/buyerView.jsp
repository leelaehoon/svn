<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
</div>
</body>
</html>