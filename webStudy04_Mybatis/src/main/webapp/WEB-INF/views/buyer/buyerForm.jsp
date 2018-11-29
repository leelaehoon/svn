<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
<link rel="stylesheet" href="https://jqueryui.com/resources/demos/style.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.3.1.min.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
<script type="text/javascript">
	$(function () {
		<c:if test="${not empty message }">
			alert("${message}")
		</c:if>
		
		$("[name='buyer_lgu']").val("${buyer.buyer_lgu}");
	})
</script>
</head>
<body>
<form action="${pageContext.request.contextPath}/buyer/buyerInsert.do" method="post" class="form-horizontal">
	<table class="table">
		<tr>
			<th>거래처명</th>
			<td><input type="text" name="buyer_name"
				value="${buyer.buyer_name}" /><span class="error">${errors["buyer_name"]}</span></td>
		</tr>
		<tr>
			<th>분류코드</th>
			<td>
				<select name="buyer_lgu">
					<option value="">상품분류선택</option>
					<c:forEach items="${lprodList.entrySet() }" var="tmp">
							<option value="${tmp.key}">${tmp.value["LPROD_NM"]}</option> 
					</c:forEach>
				</select>
			</td>
		</tr>
		<tr>
			<th>거래처은행</th>
			<td><input type="text" name="buyer_bank"
				value="${buyer.buyer_bank}" /><span class="error">${errors["buyer_bank"]}</span></td>
		</tr>
		<tr>
			<th>거래처계좌번호</th>
			<td><input type="text" name="buyer_bankno"
				value="${buyer.buyer_bankno}" /><span class="error">${errors["buyer_bankno"]}</span></td>
		</tr>
		<tr>
			<th>거래처예금주</th>
			<td><input type="text" name="buyer_bankname"
				value="${buyer.buyer_bankname}" /><span class="error">${errors["buyer_bankname"]}</span></td>
		</tr>
		<tr>
			<th>우편번호</th>
			<td><input type="text" name="buyer_zip"
				value="${buyer.buyer_zip}" /><span class="error">${errors["buyer_zip"]}</span></td>
		</tr>
		<tr>
			<th>주소</th>
			<td><input type="text" name="buyer_add1"
				value="${buyer.buyer_add1}" /><span class="error">${errors["buyer_add1"]}</span></td>
		</tr>
		<tr>
			<th>상세주소</th>
			<td><input type="text" name="buyer_add2"
				value="${buyer.buyer_add2}" /><span class="error">${errors["buyer_add2"]}</span></td>
		</tr>
		<tr>
			<th>연락처</th>
			<td><input type="text" name="buyer_comtel"
				value="${buyer.buyer_comtel}" /><span class="error">${errors["buyer_comtel"]}</span></td>
		</tr>
		<tr>
			<th>팩스번호</th>
			<td><input type="text" name="buyer_fax"
				value="${buyer.buyer_fax}" /><span class="error">${errors["buyer_fax"]}</span></td>
		</tr>
		<tr>
			<th>거래처메일</th>
			<td><input type="email" name="buyer_mail"
				value="${buyer.buyer_mail}" /><span class="error">${errors["buyer_mail"]}</span></td>
		</tr>
		<tr>
			<th>거래처담당자</th>
			<td><input type="text" name="buyer_charger"
				value="${buyer.buyer_charger}" /><span class="error">${errors["buyer_charger"]}</span></td>
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