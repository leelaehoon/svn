<%@page import="java.util.List"%>
<%@page import="kr.or.ddit.vo.PagingInfoVO"%>
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
	function ${pagingVO.funcName}(page) {
		document.searchForm.page.value = page;
		document.searchForm.submit();
	}
	
	$(function () {
		$("#listBody").on("click", "tr", function () {
			var buyer_id = $(this).find("td:first").text();
			location.href = "${pageContext.request.contextPath}/buyer/buyerView.do?what=" + buyer_id;
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
<input type="button" class="btn btn-primary" value="신규등록" onclick="location.href='${pageContext.request.contextPath}/buyer/buyerInsert.do'"/>
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
		<c:set var="buyerList" value="${pagingVO.dataList }" />
		<c:choose>
			<c:when test="${not empty buyerList }">
				<c:forEach items="${buyerList }" var="buyer">
					<tr>
						<td>${buyer.buyer_id}</td>
						<td>${buyer.buyer_name}</td>
						<td>${buyer.address}</td>
						<td>${buyer.buyer_comtel}</td>
						<td>${buyer.buyer_charger}</td>
					</tr>
				</c:forEach>
			</c:when>
			<c:otherwise>
				<tr><td colspan="5">거래처 정보가 없습니다.</td></tr>
			</c:otherwise>
		</c:choose>
	</tbody>
	<tfoot>
			<tr>
				<td colspan="6">
					<nav aria-label="Page navigation example">
						${pagingVO.pagingHTML}
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
								document.searchForm.searchType.value = "${empty pagingVO.searchType ? 'all':pagingVO.searchType}";
							</script>
							<div class="col-xs-2">
								<input type="text" name="searchWord" class="form-control" value="${pagingVO.searchWord}"/>
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