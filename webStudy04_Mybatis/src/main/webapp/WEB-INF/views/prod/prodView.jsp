<%@page import="kr.or.ddit.vo.MemberVO"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:useBean id="prod" class="kr.or.ddit.vo.ProdVO" scope="request" />
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
	src="<%=request.getContextPath()%>/js/jquery-3.3.1.min.js"></script>
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
	<input type="button" class="btn btn-info" value="Back" onclick="location.href='<%=request.getContextPath() %>/prod/prodList.do'"/>
	<table class="table table-dark">
		<tr>
			<th>상품코드</th>
			<td><%=prod.getProd_id()%></td>
		</tr>
		<tr>
			<th>상품명</th>
			<td><%=prod.getProd_name()%></td>
		</tr>
		<tr>
			<th>분류명</th>
			<td><%=prod.getLprod_nm()%></td>
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
							<td><%=prod.getBuyer().getBuyer_name() %></td>
							<td><%=prod.getBuyer().getBuyer_add1() %></td>
							<td><%=prod.getBuyer().getBuyer_charger() %></td>
							<td><%=prod.getBuyer().getBuyer_comtel() %></td>
						</tr>
					</tbody>
				</table>
			</td>
		</tr>
		<tr>
			<th>코스트</th>
			<td><%=prod.getProd_cost()%></td>
		</tr>
		<tr>
			<th>프라이스</th>
			<td><%=prod.getProd_price()%></td>
		</tr>
		<tr>
			<th>세일</th>
			<td><%=prod.getProd_sale()%></td>
		</tr>
		<tr>
			<th>상품개요</th>
			<td><%=prod.getProd_outline()%></td>
		</tr>
		<tr>
			<th>상품디테일</th>
			<td><%=prod.getProd_detail()%></td>
		</tr>
		<tr>
			<th>상품이미지</th>
			<td><%=prod.getProd_img()%></td>
		</tr>
		<tr>
			<th>토탈스톡</th>
			<td><%=prod.getProd_totalstock()%></td>
		</tr>
		<tr>
			<th>인스데이트</th>
			<td><%=prod.getProd_insdate()%></td>
		</tr>
		<tr>
			<th>프로퍼스톡</th>
			<td><%=prod.getProd_properstock()%></td>
		</tr>
		<tr>
			<th>상품사이즈</th>
			<td><%=prod.getProd_size()%></td>
		</tr>
		<tr>
			<th>상품칼라</th>
			<td><%=prod.getProd_color()%></td>
		</tr>
		<tr>
			<th>상품배달</th>
			<td><%=prod.getProd_delivery()%></td>
		</tr>
		<tr>
			<th>상품단위</th>
			<td><%=prod.getProd_unit() %></td>
		</tr>
		<tr>
			<th>입고량</th>
			<td><%=prod.getProd_qtyin() %></td>
		</tr>
		<tr>
			<th>출고량</th>
			<td><%=prod.getProd_qtysale() %></td>
		</tr>
		<tr>
			<th>마일리지</th>
			<td><%=prod.getProd_mileage() %></td>
		</tr>
	</table>
	<%
		boolean authorized = false;
		MemberVO authMember = (MemberVO) session.getAttribute("authMember");
		authorized = authMember!=null && "ROLE_ADMIN".equals(authMember.getMem_auth());
		if (authorized) {
	%>
	<input class="btn btn-success" type="button" value="상품수정" onclick="location.href='<%=request.getContextPath()%>/prod/prodUpdate.do?what=<%=prod.getProd_id() %>'"/>
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
			<%
				List<MemberVO> customers = prod.getCustomers();
				if (customers!=null && customers.size() > 0) {
					for (MemberVO tmp : customers) {
						%>
						<tr>
							<td><%=tmp.getMem_id() %></td>
							<td><%=tmp.getMem_name() %></td>
							<td><%=tmp.getAddress() %></td>
							<td><%=tmp.getMem_hp() %></td>
							<td><%=tmp.getMem_mail() %></td>
						</tr>
						<%
					}
				} else {
					%>
					<tr>
						<td colspan="5">구매자가 없습니다.</td>
					</tr>
					<%
				}
			%>
		</tbody>
	</table>
	<%
		}
	%>
</body>
</html>