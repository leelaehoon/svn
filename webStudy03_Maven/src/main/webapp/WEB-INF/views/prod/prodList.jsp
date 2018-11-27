<%@page import="java.util.Objects"%>
<%@page import="java.util.Map.Entry"%>
<%@page import="kr.or.ddit.vo.BuyerVO"%>
<%@page import="java.util.Map"%>
<%@page import="kr.or.ddit.vo.ProdVO"%>
<%@page import="java.util.List"%>
<%@page import="kr.or.ddit.vo.PagingInfoVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	PagingInfoVO<ProdVO> pagingVO = (PagingInfoVO<ProdVO>) request.getAttribute("pagingVO");
	List<ProdVO> prodList = pagingVO.getDataList();
	
	Map<String, String> lprodList = (Map) request.getAttribute("lprodList");
	List<BuyerVO> buyerList = (List) request.getAttribute("buyerList");
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
		$("[name='searchForm']").find("[name='page']").val(page);
// 		document.searchForm.page.value = page;
		$("[name='searchForm']").submit();
// 		document.searchForm.submit();
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
			location.href = "<%=request.getContextPath()%>/prod/prodView.do?what=" + prod_id;
		});
		
		$("[name='searchForm']").on("submit", function (event) {
			event.preventDefault();
			var data = $(this).serialize(); // queryString 생성
			$.ajax({
				data : data,
				dataType : "json",
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
					<%
						for (Entry<String, String> entry : lprodList.entrySet()) {
							%>
							<option value="<%=entry.getKey() %>"><%=entry.getValue() %></option>
							<%
						}
					%>
				</select>
			</div>
			<div class="col-xs-2">
				<select name="prod_buyer" class="form-control">
					<option value="">거래처선택</option>
					<%
						for (BuyerVO buyer : buyerList) {
							%>
							<option value="<%=buyer.getBuyer_id() %>" class="<%=buyer.getBuyer_lgu() %>" ><%=buyer.getBuyer_name() %></option>
							<%
						}
					%>
				</select>
			</div>
			<div class="col-xs-2">
				<input type="text" name="prod_name" class="form-control" value="${pagingVO.searchVO.prod_name}"/>
			</div>
			<div class="col-xs-2">
				<input type="submit" value="검색" class="btn btn-primary" />
			</div>
			<div class="col-xs-2">
				<input type="button" class="btn btn-info" value="신규상품등록" onclick="location.href='<%=request.getContextPath() %>/prod/prodInsert.do';" />
			</div>
		</div>
	</form>
	
	<table class="table table-hover">
		<thead class="thead-dark">
			<tr>
				<th>상품코드</th>
				<th>상품명</th>
				<th>분류명</th>
				<th>거래처명</th>
				<th>판매가</th>
				<th>상품개요</th>
				<th>마일리지</th>
			</tr>
		</thead>
		<tbody id="listBody">
			<% if (prodList.size()>0) {
				
			
				for (ProdVO prod : prodList) {
			%>
			<tr>
				<td><%=prod.getProd_id()%></td>
				<td><%=prod.getProd_name()%></td>
				<td><%=prod.getLprod_nm()%></td>
				<td><%=prod.getBuyer_name()%></td>
				<td><%=prod.getProd_price()%></td>
				<td><%=prod.getProd_outline()%></td>
				<td><%=prod.getProd_mileage()%></td>
			</tr>
			<%
				}
			} else {
				%>
				<tr><td colspan="7">상품정보가 없습니다.</td></tr>
				<%
			}
			%>
		</tbody>
		<tfoot>
			<tr>
				<td colspan="7">
					<nav aria-label="Page navigation example" id="pagingNav">
						<%=pagingVO.getPagingHTML()%>
					</nav>
				</td>
			</tr>
		</tfoot>
	</table>
</body>
</html>