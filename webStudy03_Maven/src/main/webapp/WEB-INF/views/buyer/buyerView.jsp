<%@page import="kr.or.ddit.vo.BuyerVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<jsp:useBean id="buyer" class="kr.or.ddit.vo.BuyerVO" scope="request" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<table>
		<tr>
			<th>거래처코드</th>
			<td><%=buyer.getBuyer_id()%></td>
		</tr>
		<tr>
			<th>거래처명</th>
			<td><%=buyer.getBuyer_name()%></td>
		</tr>
		<tr>
			<th>분류코드</th>
			<td><%=buyer.getBuyer_lgu()%></td>
		</tr>
		<tr>
			<th>거래처은행</th>
			<td><%=buyer.getBuyer_bank()%></td>
		</tr>
		<tr>
			<th>거래처계좌번호</th>
			<td><%=buyer.getBuyer_bankno()%></td>
		</tr>
		<tr>
			<th>거래처예금주</th>
			<td><%=buyer.getBuyer_bankname()%></td>
		</tr>
		<tr>
			<th>우편번호</th>
			<td><%=buyer.getBuyer_zip()%></td>
		</tr>
		<tr>
			<th>주소</th>
			<td><%=buyer.getBuyer_add1()%></td>
		</tr>
		<tr>
			<th>상세주소</th>
			<td><%=buyer.getBuyer_add2()%></td>
		</tr>
		<tr>
			<th>연락처</th>
			<td><%=buyer.getBuyer_comtel()%></td>
		</tr>
		<tr>
			<th>팩스번호</th>
			<td><%=buyer.getBuyer_fax()%></td>
		</tr>
		<tr>
			<th>거래처메일</th>
			<td><%=buyer.getBuyer_mail()%></td>
		</tr>
		<tr>
			<th>거래처담당자</th>
			<td><%=buyer.getBuyer_charger()%></td>
		</tr>
		<tr>
			<th>텔렉스트</th>
			<td><%=buyer.getBuyer_telext()%></td>
		</tr>
	</table>
</body>
</html>