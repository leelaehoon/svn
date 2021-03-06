<%@page import="kr.or.ddit.vo.ProdVO"%>
<%@page import="java.util.List"%>
<%@page import="kr.or.ddit.CommonException"%>
<%@page import="kr.or.ddit.member.service.MemberServiceImpl"%>
<%@page import="kr.or.ddit.member.service.IMemberService"%>
<%@page import="kr.or.ddit.vo.MemberVO"%>
<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>member/memberView.jsp</title>
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
<link rel="stylesheet" href="https://jqueryui.com/resources/demos/style.css">
<script type="text/javascript" src="<%=request.getContextPath() %>/js/jquery-3.3.1.min.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>

<script type="text/javascript">
	$(function () {
		<%
			String message = (String) request.getAttribute("message");
			if (StringUtils.isBlank(message)) {
				message = (String) session.getAttribute("message");
				session.removeAttribute("message");
			}
			if (StringUtils.isNotBlank(message)) {
				%>
				alert("<%=message %>")
				<%
			}
		%>
		
		$(".datePicker").datepicker({
			dateFormat: "yy-mm-dd"
		})
		$("#delBtn").on("click", function () {
			var chk = confirm("정말로 탈퇴하시겠습니까?")
			if (chk) {
				var password = prompt("비밀번호 입력")
				if (password) {
					$("[name='mem_pass']").val(password)
					document.delForm.submit()
				}
			}
		})
	})
</script>
</head>
<body>
<jsp:useBean id="member" class="kr.or.ddit.vo.MemberVO" scope="request" />
<jsp:useBean id="errors" class="java.util.LinkedHashMap" scope="request" />
			<%
				boolean mutable = false;
				MemberVO authMember = (MemberVO) session.getAttribute("authMember");
				if (authMember!=null && "ROLE_USER".equals(authMember.getMem_auth())) {
					if (authMember.getMem_id().equals(member.getMem_id())) {
						mutable = true;
					}
				}
				if (mutable) {
			%>
<form name="delForm" action="<%=request.getContextPath() %>/member/memberDelete.do" method="post">
	<input type="hidden" name="mem_id" value="<%=member.getMem_id() %>" />
	<input type="hidden" name="mem_pass" />
</form>
<form action="<%=request.getContextPath() %>/member/memberUpdate.do" method="post">
<%
				}
%>
	<table class="table table-sm table-hover">
		<tr>
			<th>회원아이디</th>
			<td><%=member.getMem_id()%><input type="hidden" name="mem_id"
				value="<%=member.getMem_id()%>" /><span class="error"><%=errors.get("mem_id")%></span></td>
		</tr>
		<tr>
			<th>비밀번호</th>
			<td><input type="text" name="mem_pass"
				value="<%=member.getMem_pass()%>" /><span class="error"><%=errors.get("mem_pass")%></span></td>
		</tr>
		<tr>
			<th>회원명</th>
			<td><input type="text" name="mem_name"
				value="<%=member.getMem_name()%>" /><span class="error"><%=errors.get("mem_name")%></span></td>
		</tr>
		<tr>
			<th>주민번호1</th>
			<td><input type="text" name="mem_regno1" disabled
				value="<%=member.getMem_regno1()%>" /><span class="error"><%=errors.get("mem_regno1")%></span></td>
		</tr>
		<tr>
			<th>주민번호2</th>
			<td><input type="text" name="mem_regno2" disabled
				value="<%=member.getMem_regno2()%>" /><span class="error"><%=errors.get("mem_regno2")%></span></td>
		</tr>
		<tr>
			<th>생일</th>
			<td><input type="text" class="datePicker" name="mem_bir" disabled
				value="<%=member.getMem_bir()%>" /><span class="error"><%=errors.get("mem_bir")%></span></td>
		</tr>
		<tr>
			<th>우편번호</th>
			<td><input type="text" name="mem_zip"
				value="<%=member.getMem_zip()%>" /><span class="error"><%=errors.get("mem_zip")%></span></td>
		</tr>
		<tr>
			<th>주소</th>
			<td><input type="text" name="mem_add1"
				value="<%=member.getMem_add1()%>" /><span class="error"><%=errors.get("mem_add1")%></span></td>
		</tr>
		<tr>
			<th>상세주소</th>
			<td><input type="text" name="mem_add2"
				value="<%=member.getMem_add2()%>" /><span class="error"><%=errors.get("mem_add2")%></span></td>
		</tr>
		<tr>
			<th>집전화번호</th>
			<td><input type="text" name="mem_hometel"
				value="<%=member.getMem_hometel()%>" /><span class="error"><%=errors.get("mem_hometel")%></span></td>
		</tr>
		<tr>
			<th>회사전화번호</th>
			<td><input type="text" name="mem_comtel"
				value="<%=member.getMem_comtel()%>" /><span class="error"><%=errors.get("mem_comtel")%></span></td>
		</tr>
		<tr>
			<th>휴대폰</th>
			<td><input type="text" name="mem_hp"
				value="<%=member.getMem_hp()%>" /><span class="error"><%=errors.get("mem_hp")%></span></td>
		</tr>
		<tr>
			<th>이메일</th>
			<td><input type="text" name="mem_mail"
				value="<%=member.getMem_mail()%>" /><span class="error"><%=errors.get("mem_mail")%></span></td>
		</tr>
		<tr>
			<th>직업</th>
			<td><input type="text" name="mem_job"
				value="<%=member.getMem_job()%>" /><span class="error"><%=errors.get("mem_job")%></span></td>
		</tr>
		<tr>
			<th>취미</th>
			<td><input type="text" name="mem_like"
				value="<%=member.getMem_like()%>" /><span class="error"><%=errors.get("mem_like")%></span></td>
		</tr>
		<tr>
			<th>기념일</th>
			<td><input type="text" name="mem_memorial"
				value="<%=member.getMem_memorial()%>" /><span class="error"><%=errors.get("mem_memorial")%></span></td>
		</tr>
		<tr>
			<th>기념일자</th>
			<td><input type="text" name="mem_memorialday" class="datePicker"
				value="<%=member.getMem_memorialday() %>" /><span class="error"><%=errors.get("mem_memorialday") %></span></td>
		</tr>
		<tr>
			<th>마일리지</th>
			<td><%=member.getMem_mileage() %></td>
		</tr>
		<tr>
			<th>탈퇴여부</th>
			<td><%="Y".equals(member.getMem_delete())?"탈퇴":"활동중"%></td>
		</tr>
		<tr>
			<td colspan="2">
				<input type="button" class="btn btn-default" onclick="location.href='<%=request.getContextPath() %>/member/memberList.do'" value="뒤로가기"/>
				<% 
				if (mutable) {
				%>
				<input type="submit" class="btn btn-primary" value="수정"/>
				<input type="reset"  class="btn btn-default" value="취소"/>
				<input type="button" id="delBtn" class="btn btn-default" value="탈퇴"/>
				<%
				}
				%>
			</td>
		</tr>
	</table>
	<%
	if (mutable) {
	%>
</form>
<%
	}
%>
<div class="container">
<h4>구매 상품 목록</h4>
</div>
<div class="container">
	<table class="table table-hover">
		<thead class="thead-dark">
			<tr>
				<th>상품코드</th>
				<th>상품명</th>
				<th>구매가</th>
				<th>판매가</th>
				<th>상품개요</th>
			</tr>
		</thead>
		<tbody>
			<%
				List<ProdVO> prodList = member.getProdList();
				if (prodList != null && prodList.size() > 0) {
					for (ProdVO tmp : prodList) {
						%>
						<tr>
							<td><%=tmp.getProd_id() %></td>
							<td><%=tmp.getProd_name() %></td>
							<td><%=tmp.getProd_cost() %></td>
							<td><%=tmp.getProd_price() %></td>
							<td><%=tmp.getProd_outline() %></td>
						</tr>
						<%
					}
					
				} else {
					%>
					<tr><td colspan="5">구매목록이 없습니다.</td></tr>
					<%
				}
			%>
		</tbody>
	</table>
</div>
</body>
</html>