<%@page import="kr.or.ddit.vo.PagingInfoVO"%>
<%@page import="kr.or.ddit.vo.MemberVO"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	PagingInfoVO pagingVO = (PagingInfoVO) request.getAttribute("pagingVO");
	List<MemberVO> memberList = pagingVO.getDataList();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>member/memberList.jsp</title>
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
	$(function () {
		
	})
</script>
<style type="text/css">
	table {
		height: 380px;
	}
</style>
</head>
<body>
	<h4>회원 목록</h4>
	<input type="button" class="button btn btn-info" value="신규등록"
		onclick="location.href='<%=request.getContextPath()%>/member/memberInsert.do'" />
	<table class="table table-hover">
		<thead class="thead-dark">
			<tr>
				<th>회원아이디</th>
				<th>회원명</th>
				<th>주소</th>
				<th>연락처</th>
				<th>이메일</th>
				<th>마일리지</th>
			</tr>
		</thead>
		<tbody>
			<%
				if (memberList.size() > 0) {
					for (MemberVO member : memberList) {
			%>
			<tr>
				<td><%=member.getMem_id()%></td>
				<td><a href="<%=request.getContextPath()%>/member/memberView.do?who=<%=member.getMem_id()%>"><%=member.getMem_name()%></a></td>
				<td><%=member.getAddress()%></td>
				<td><%=member.getMem_hometel()%></td>
				<td><%=member.getMem_mail()%></td>
				<td><%=member.getMem_mileage()%></td>
			</tr>
			<%
				}
				} else {
			%>
			<tr>
				<td colspan="6">존재하는 회원이 없습니다.</td>
			</tr>
			<%
				}
			%>
		</tbody>
		<tfoot>
			<tr>
				<td colspan="6">
					<nav aria-label="Page navigation example">
						<%=pagingVO.getPagingHTML() %>
					</nav>
				</td>
			</tr>
		</tfoot>
	</table>
</body>
</html>