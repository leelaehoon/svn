<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
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
<c:url value="/prod/prodView.do" var="prodView"></c:url>
<script type="text/javascript">
	function ${pagingVO.funcName}(page) {
		$("[name='searchForm']").find("[name='page']").val(page);
		$("[name='searchForm']").submit();
	}
	
	$(function () {
		var prod_lguTag = $("[name='prod_lgu']");
		var prod_buyerTag = $("[name='prod_buyer']");
		
		prod_lguTag.on("change", function () {
			var lprod_gu = $(this).val();
			var buyerOptions = prod_buyerTag.find("option");
			$(buyerOptions).hide();
			if (lprod_gu) {
				var buyerOptions2 = prod_buyerTag.find("option."+lprod_gu);
				$(buyerOptions2).show();
			} else {
				$(buyerOptions).show();	
			}
		});
		
		prod_lguTag.val("${pagingVO.searchVO.prod_lgu}");
		prod_lguTag.trigger("change");
		prod_buyerTag.val("${pagingVO.searchVO.prod_buyer}");
		
		var listBody = $("#listBody");		
		var pagingNav = $("#pagingNav");
		
		$("#listBody").on("click", "tr", function () {
			var prod_id = $(this).find("td:first").text();
			location.href = "${prodView}?what=" + prod_id;
		});
		
		$("[name='searchForm']").on("submit", function (event) {
			event.preventDefault();
			var data = $(this).serialize(); // queryString 생성
			$.ajax({
				data : data,
				dataType : "json", // Accept/Content-type
				success : function(resp) {
					var tags = "";
					var pattern = "<td>%T</td>"
					var prodList = resp.dataList;
					var pagingHTML = resp.pagingHTML;
					if (prodList.length) {
						$.each(prodList, function (idx, prod) {
							tags += "<tr>";
							tags += pattern.replace("%T", prod.prod_id);
							tags += pattern.replace("%T", prod.prod_name);
							tags += pattern.replace("%T", prod.lprod_nm);
							tags += pattern.replace("%T", prod.buyer_name);
							tags += pattern.replace("%T", prod.prod_price);
							tags += pattern.replace("%T", prod.prod_outline);
							tags += pattern.replace("%T", prod.prod_mileage);
							tags += "</tr>";
						})
					} else {
						tags += "<tr><td colspan='7'>상품목록이 없습니다.</td></tr>";
					}
					listBody.html(tags);
					pagingNav.html(pagingHTML);
					$("[name='page']").val("");
				},
				error : function(resp) {

				}
			});
			
			return false;
		});
	});
</script>
<style type="text/css">
	tr:hover {
		cursor: pointer;
	}
	input[type='image'] {
		width: 50px;
		height: 50px;
		border: 1px solid black;
		border-radius: 100%;
	}
</style>
</head>
<body class="container">
	<!-- 스크린사이즈 7 -->
	<!-- 블럭사이즈 4 -->
	<form name="searchForm">
		<input type="hidden" name="page" />
		<div class="form-group row">
			<div class="col-xs-2">
				<select name="prod_lgu" class="form-control">
					<option value="">분류</option>
					<c:forEach items="${lprodList.entrySet() }" var="tmp">
						<option value="${tmp.key}">${tmp.value["LPROD_NM"]}</option> 
					</c:forEach>
				</select>
			</div>
			<div class="col-xs-2">
				<select name="prod_buyer" class="form-control">
					<option value="">거래처선택</option>
					<c:forEach items="${buyerList }" var="buyer">
						<option value="${buyer.buyer_id}" class="${buyer.buyer_lgu}" >${buyer.buyer_name}</option>
					</c:forEach>
				</select>
			</div>
			<div class="col-xs-2">
				<input type="text" name="prod_name" class="form-control" value="${pagingVO.searchVO.prod_name}"/>
			</div>
			<div class="col-xs-2">
				<input type="submit" value="검색" class="btn btn-primary" />
			</div>
			<div class="col-xs-2">
				<input type="button" class="btn btn-info" value="신규상품등록" onclick="location.href='${pageContext.request.contextPath}/prod/prodInsert.do';" />
			</div>
		</div>
	</form>
	<input type="image" src="<c:url value='https://image.flaticon.com/icons/svg/197/197582.svg' />" onclick="location.href='?locale=ko'" />
	<input type="image" src="<c:url value='https://image.flaticon.com/icons/svg/197/197484.svg' />" onclick="location.href='?locale=en'" />
	<c:if test="${not empty param.locale }">
		<fmt:setLocale value="${param.locale }"/>
	</c:if>
	<table class="table table-hover">
		<thead class="thead-dark">
			<fmt:bundle basename="kr.or.ddit.msgs.message">
				<tr>
					<th><fmt:message key="prod.prod_id" /></th>
					<th><fmt:message key="prod.prod_name" /></th>
					<th><fmt:message key="prod.prod_lgu" /></th>
					<th><fmt:message key="prod.prod_buyer" /></th>
					<th><fmt:message key="prod.prod_price" /></th>
					<th><fmt:message key="prod.prod_outline" /></th>
					<th><fmt:message key="prod.prod_mileage" /></th>
				</tr>
			</fmt:bundle>
		</thead>
		<tbody id="listBody">
			<c:set var="prodList" value="${pagingVO.dataList }" />
			<c:if test="${not empty prodList }">
				<c:forEach items="${prodList }" var="prod">
					<tr>
						<td>${prod.prod_id}</td>
						<td>${prod.prod_name}</td>
						<td>${prod.lprod_nm}</td>
						<td>${prod.buyer_name}</td>
						<td>${prod.prod_price}</td>
						<td>${prod.prod_outline}</td>
						<td>${prod.prod_mileage}</td>
					</tr>
				</c:forEach>
			</c:if>
			<c:if test="${empty prodList }">
				<tr><td colspan="7">상품정보가 없습니다.</td></tr>
			</c:if>
		</tbody>
		<tfoot>
			<tr>
				<td colspan="7">
					<nav aria-label="Page navigation example" id="pagingNav">
						${pagingVO.pagingHTML}
					</nav>
				</td>
			</tr>
		</tfoot>
	</table>
</body>
</html>