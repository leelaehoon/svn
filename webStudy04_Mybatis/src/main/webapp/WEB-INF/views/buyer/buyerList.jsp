<%@page import="java.util.Objects"%>
<%@page import="oracle.net.aso.b"%>
<%@page import="kr.or.ddit.vo.PagingInfoVO"%>
<%@page import="kr.or.ddit.vo.BuyerVO"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	PagingInfoVO<BuyerVO> pagingVO = (PagingInfoVO<BuyerVO>) request.getAttribute("pagingVO");
	List<BuyerVO> buyerList = pagingVO.getDataList();
%>
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
	function <%=pagingVO.getFuncName()%>(page) {
		document.searchForm.page.value = page;
		document.searchForm.submit();
	}
	
	$(function () {
		$("#listBody").on("click", "tr", function () {
			var buyer_id = $(this).find("td:first").text();
			location.href = "<%=request.getContextPath()%>/buyer/buyerView.do?what=" + buyer_id;
		});
	});
</script>
<style type="text/css">
	tr {
		cursor: pointer;
	}
</style>
</head>
<body class="container">
<input type="button" class="btn btn-primary" value="신규등록" onclick="location.href='<%=request.getContextPath()%>/buyer/buyerInsert.do'"/>
<table class="table table-hover">
	<thead class="thead-dark">
		<tr>
			<th>거래처코드</th>
			<th>거래처명</th>
			<th>거래처주소</th>
			<th>연락처</th>
			<th>담당자명</th>
		</tr>
	</thead>
	<tbody id="listBody">
		<%
			if (buyerList.size() > 0) {
				for (BuyerVO buyer : buyerList) {
					%>
					<tr>
						<td><%=buyer.getBuyer_id() %></td>
						<td><%=buyer.getBuyer_name() %></td>
						<td><%=buyer.getAddress() %></td>
						<td><%=buyer.getBuyer_comtel() %></td>
						<td><%=buyer.getBuyer_charger() %></td>
					</tr>
					<%					
				}
			} else {
				%>
				<tr><td colspan="5">거래처 정보가 없습니다.</td></tr>
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
					<form name="searchForm">
						<div class="form-group row">
							<input type="hidden" name="page" />
							<div class="col-xs-2">
								<select name="searchType" class="form-control">
									<option value="all">전체</option>
									<option value="name">이름</option>
									<option value="address">지역</option>
								</select>
							</div>
							<script type="text/javascript">
								document.searchForm.searchType.value = "<%=Objects.toString(pagingVO.getSearchType(), "all")%>";
							</script>
							<div class="col-xs-2">
								<input type="text" name="searchWord" class="form-control" value="<%=Objects.toString(pagingVO.getSearchWord(), "") %>"/>
							</div>
							<div class="col-xs-2">
								<input type="submit" value="검색" class="button btn btn-info" />
							</div>
						</div>
					</form>
				</td>
			</tr>
	</tfoot>
</table>
</body>
</html>