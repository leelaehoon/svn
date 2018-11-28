<%@page import="java.util.Objects"%>
<%@page import="java.util.Map.Entry"%>
<%@page import="java.util.Map"%>
<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	Map<String, Map<String, String>> lprodList = (Map) request.getAttribute("lprodList");
%>
<jsp:useBean id="prod" class="kr.or.ddit.vo.ProdVO" scope="request" />
<jsp:useBean id="errors" class="java.util.LinkedHashMap" scope="request" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
<link rel="stylesheet" href="https://jqueryui.com/resources/demos/style.css">
<script type="text/javascript" src="<%=request.getContextPath() %>/js/jquery-3.3.1.min.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
<script type="text/javascript">
	$(function () {
		var prod_lguTag = $("[name='prod_lgu']");
		var prod_buyerTag = $("[name='prod_buyer']");
		
		<%
		String message = (String) request.getAttribute("message");
		if(StringUtils.isNotBlank(message)) {
			%>
			alert("<%=message %>");
			<%
		}
		%>
		
		var pattern = "<option value='%v' %s>%T</option>";
		
		$("#prod_lgu").on("change", function () {
			var prod_lgu = $(this).val();
			$.ajax({
				url : "<%=request.getContextPath()%>/prod/getBuyerList.do",
				method : "get",
				data : {prod_lgu:prod_lgu},
				dataType : "json",
				success : function(resp) {
					var options = "";
					$.each(resp, function (i, buyer) {
						var selected = "";
						if (buyer.buyer_id == "<%=prod.getProd_buyer()%>") {
							selected = "selected";
						}
						options += pattern.replace("%v", buyer.buyer_id)
										  .replace("%s", selected)
										  .replace("%T", buyer.buyer_name);
					});
					prod_buyerTag.html(options);
				},
				error : function(resp) {
					
				}
			})
		})
		
		prod_lguTag.val("${prod.prod_lgu}");
		prod_lguTag.trigger("change");
		
		$(".datepicker").datepicker({
			dateFormat: "yy-mm-dd"
		})
		
		var prod_id = "${prod.prod_id}";
		
		if (prod_id) {
			var prod_idTag = "<tr><th>상품코드</th><td><div class='input-group'><input class='form-control' type='text' name='prod_id' readonly value='" + prod_id + "'/></div></td></tr>";
			$(".table").prepend(prod_idTag);
			$("[name='prod_lgu']").prop("disabled",true);
			$("[name='prod_buyer']").prop("disabled",true);
			$("[name='prod_insdate']").prop("disabled",true);
		}
		
	})
</script>
</head>
<body>
	<form method="post" class="container">
		<table class="table">
			<tr>
				<th>상품명</th>
				<td><div class="input-group">
						<input class="form-control" type="text" name="prod_name"
							value="${prod.prod_name}" /><span
							class="input-group-text" class="error"><%=errors.get("prod_name")%></span>
					</div></td>
			</tr>
			<tr>
				<th>분류코드</th>
				<td><div class="input-group">
						<select name="prod_lgu" class="form-control" id="prod_lgu">
							<option value="">분류코드선택</option>
							<% 
								for (Entry<String, Map<String, String>> tmp : lprodList.entrySet()) {
									%>
									<option value="<%=tmp.getKey() %>"><%=tmp.getValue().get("LPROD_NM") %></option> 
									<%
								}
								%>
						</select>
						<span class="input-group-text" class="error"><%=errors.get("prod_lgu")%></span>
					</div></td>
			</tr>
			<tr>
				<th>거래처코드</th>
				<td><div class="input-group">
						<select name="prod_buyer" id="prod_buyer" class="form-control">
							<option value="">분류코드선택해주세요</option>
						</select>
						<span class="input-group-text" class="error"><%=errors.get("prod_buyer")%></span>
					</div></td>
			</tr>
			<tr>
				<th>코스트</th>
				<td><div class="input-group">
						<input class="form-control" type="text" name="prod_cost"
							value="${prod.prod_cost}" />
							<span class="input-group-text" class="error"><%=errors.get("prod_cost")%></span>
					</div></td>
			</tr>
			<tr>
				<th>프라이스</th>
				<td><div class="input-group">
						<input class="form-control" type="text" name="prod_price"
							value="${prod.prod_price}" /><span
							class="input-group-text" class="error"><%=errors.get("prod_price")%></span>
					</div></td>
			</tr>
			<tr>
				<th>세일</th>
				<td><div class="input-group">
						<input class="form-control" type="text" name="prod_sale"
							value="${prod.prod_sale}" /><span
							class="input-group-text" class="error"><%=errors.get("prod_sale")%></span>
					</div></td>
			</tr>
			<tr>
				<th>상품개요</th>
				<td><div class="input-group">
						<input class="form-control" type="text" name="prod_outline"
							value="${prod.prod_outline}" /><span
							class="input-group-text" class="error"><%=errors.get("prod_outline")%></span>
					</div></td>
			</tr>
			<tr>
				<th>상품디테일</th>
				<td><div class="input-group">
						<input class="form-control" type="text" name="prod_detail"
							value="${prod.prod_detail}" /><span
							class="input-group-text" class="error"><%=errors.get("prod_detail")%></span>
					</div></td>
			</tr>
			<tr>
				<th>상품이미지</th>
				<td><div class="input-group">
						<input class="form-control" type="text" name="prod_img"
							value="${prod.prod_img}" /><span
							class="input-group-text" class="error"><%=errors.get("prod_img")%></span>
					</div></td>
			</tr>
			<tr>
				<th>토탈스톡</th>
				<td><div class="input-group">
						<input class="form-control" type="text" name="prod_totalstock"
							value="${prod.prod_totalstock}" /><span
							class="input-group-text" class="error"><%=errors.get("prod_totalstock")%></span>
					</div></td>
			</tr>
			<tr>
				<th>인스데이트</th>
				<td><div class="input-group">
						<input class="form-control datepicker" type="text" name="prod_insdate"
							value="${prod.prod_insdate}" /><span
							class="input-group-text" class="error"><%=errors.get("prod_insdate")%></span>
					</div></td>
			</tr>
			<tr>
				<th>프로퍼스톡</th>
				<td><div class="input-group">
						<input class="form-control" type="text" name="prod_properstock"
							value="${prod.prod_properstock}" /><span
							class="input-group-text" class="error"><%=errors.get("prod_properstock")%></span>
					</div></td>
			</tr>
			<tr>
				<th>상품사이즈</th>
				<td><div class="input-group">
						<input class="form-control" type="text" name="prod_size"
							value="${prod.prod_size}" /><span
							class="input-group-text" class="error"><%=errors.get("prod_size")%></span>
					</div></td>
			</tr>
			<tr>
				<th>상품칼라</th>
				<td><div class="input-group">
						<input class="form-control" type="text" name="prod_color"
							value="${prod.prod_color}" /><span
							class="input-group-text" class="error"><%=errors.get("prod_color")%></span>
					</div></td>
			</tr>
			<tr>
				<th>상품배달</th>
				<td><div class="input-group">
						<input class="form-control" type="text" name="prod_delivery"
							value="${prod.prod_delivery}" /><span
							class="input-group-text" class="error"><%=errors.get("prod_delivery")%></span>
					</div></td>
			</tr>
			<tr>
				<th>상품단위</th>
				<td><div class="input-group">
						<input class="form-control" type="text" name="prod_unit"
							value="${prod.prod_unit}" /><span
							class="input-group-text" class="error"><%=errors.get("prod_unit") %></span>
					</div></td>
			</tr>
			<tr>
				<th>입고량</th>
				<td><div class="input-group">
						<input class="form-control" type="text" name="prod_qtyin"
							value="${prod.prod_qtyin}" /><span
							class="input-group-text" class="error"><%=errors.get("prod_qtyin") %></span>
					</div></td>
			</tr>
			<tr>
				<th>출고량</th>
				<td><div class="input-group">
						<input class="form-control" type="text" name="prod_qtysale"
							value="${prod.prod_qtysale}" /><span
							class="input-group-text" class="error"><%=errors.get("prod_qtysale") %></span>
					</div></td>
			</tr>
			<tr>
				<th>마일리지</th>
				<td><div class="input-group">
						<input class="form-control" type="text" name="prod_mileage"
							value="${prod.prod_mileage}" /><span
							class="input-group-text" class="error"><%=errors.get("prod_mileage") %></span>
					</div></td>
			</tr>
			<tr>
				<td colspan="2">
					<input type="submit" class="btn btn-primary" value="등록"/>
					<input type="reset" class="btn btn-default" value="취소"/>
					<input type="button" class="btn btn-default" value="목록으로" onclick="location.href='<%=request.getContextPath() %>/prod/prodList.do';"/>
				</td>
			</tr>
		</table>
	</form>
</body>
</html>