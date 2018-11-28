<%@page import="java.util.Map.Entry"%>
<%@page import="java.util.Map"%>
<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<jsp:useBean id="buyer" class="kr.or.ddit.vo.BuyerVO" scope="request"></jsp:useBean>
<jsp:useBean id="errors" class="java.util.LinkedHashMap" scope="request"></jsp:useBean>
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
		<%
			String message = (String) request.getAttribute("message");
			if(StringUtils.isNotBlank(message)) {
				%>
				alert("<%=message %>")
				<%
			}
		%>
		
		$("[name='buyer_lgu']").val("${buyer.buyer_lgu}");
	})
</script>
</head>
<body>
<form action="<%=request.getContextPath() %>/buyer/buyerInsert.do" method="post" class="form-horizontal">
	<table class="table">
		<tr>
			<th>거래처명</th>
			<td><input type="text" name="buyer_name"
				value="${buyer.buyer_name}" /><span class="error"><%=errors.get("buyer_name")%></span></td>
		</tr>
		<tr>
			<th>분류코드</th>
			<td>
				<select name="buyer_lgu">
					<option value="">상품분류선택</option>
					<%
						Map<String, Map<String, String>> lprodList = (Map) request.getAttribute("lprodList");
						for (Entry<String, Map<String, String>> tmp : lprodList.entrySet()) {
							%>
							<option value="<%=tmp.getKey() %>"><%=tmp.getValue().get("LPROD_NM") %></option> 
							<%
						}
					%>
				</select>
			</td>
		</tr>
		<tr>
			<th>거래처은행</th>
			<td><input type="text" name="buyer_bank"
				value="${buyer.buyer_bank}" /><span class="error"><%=errors.get("buyer_bank")%></span></td>
		</tr>
		<tr>
			<th>거래처계좌번호</th>
			<td><input type="text" name="buyer_bankno"
				value="${buyer.buyer_bankno}" /><span class="error"><%=errors.get("buyer_bankno")%></span></td>
		</tr>
		<tr>
			<th>거래처예금주</th>
			<td><input type="text" name="buyer_bankname"
				value="${buyer.buyer_bankname}" /><span class="error"><%=errors.get("buyer_bankname")%></span></td>
		</tr>
		<tr>
			<th>우편번호</th>
			<td><input type="text" name="buyer_zip"
				value="${buyer.buyer_zip}" /><span class="error"><%=errors.get("buyer_zip")%></span></td>
		</tr>
		<tr>
			<th>주소</th>
			<td><input type="text" name="buyer_add1"
				value="${buyer.buyer_add1}" /><span class="error"><%=errors.get("buyer_add1")%></span></td>
		</tr>
		<tr>
			<th>상세주소</th>
			<td><input type="text" name="buyer_add2"
				value="${buyer.buyer_add2}" /><span class="error"><%=errors.get("buyer_add2")%></span></td>
		</tr>
		<tr>
			<th>연락처</th>
			<td><input type="text" name="buyer_comtel"
				value="${buyer.buyer_comtel}" /><span class="error"><%=errors.get("buyer_comtel")%></span></td>
		</tr>
		<tr>
			<th>팩스번호</th>
			<td><input type="text" name="buyer_fax"
				value="${buyer.buyer_fax}" /><span class="error"><%=errors.get("buyer_fax")%></span></td>
		</tr>
		<tr>
			<th>거래처메일</th>
			<td><input type="email" name="buyer_mail"
				value="${buyer.buyer_mail}" /><span class="error"><%=errors.get("buyer_mail")%></span></td>
		</tr>
		<tr>
			<th>거래처담당자</th>
			<td><input type="text" name="buyer_charger"
				value="${buyer.buyer_charger}" /><span class="error"><%=errors.get("buyer_charger")%></span></td>
		</tr>
		<tr>
			<td colspan="2">
				<input type="submit" class="btn btn-primary" value="등록" />
				<input type="reset" class="btn btn-default" value="취소" />
			</td>
		</tr>
	</table>
</form>
</body>
</html>