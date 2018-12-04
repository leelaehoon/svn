<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>member/memberView.jsp</title>
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
<link rel="stylesheet" href="https://jqueryui.com/resources/demos/style.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.3.1.min.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
<style type="text/css">
	#imageArea {
		float: right;
		width: 150px;
		height: 200px;
		border: 1px solid black;
	}
	#imageArea>img {
		width: 100%;
		height: 100%;
	}
</style>
<script type="text/javascript">
	$(function () {
		<c:if test="${not empty message}">
			alert("${message}")
			<c:remove var="message" scope="session" />
		</c:if >
		
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
		
		var imageArea = $("#imageArea");
		
		$('[name="mem_image"]').on("change", function () {
			var files = $(this).prop("files")
			if (!files) return
			for (var idx = 0; idx < files.length; idx++) {
// 				console.log(files[idx])
				var reader = new FileReader();
				reader.onloadend = function (event) {
// 					console.log(event.target.result)
					var imgTag = new Image();
					imgTag.src = event.target.result;
					imageArea.html(imgTag);
				}
				reader.readAsDataURL(files[idx]);
			}
		})
	})
</script>
</head>
<body>
	<c:set var="mutable" value="false" />
	<c:if test="${not empty sessionScope.authMember and 'ROLE_ADMIN' ne sessionScope.authMember.mem_auth}">
		<c:if test="${sessionScope.authMember.mem_id eq member.mem_id }">
			<c:set var="mutable" value="true" />
		</c:if>
	</c:if>
	<c:if test="${mutable }">
		<form name="delForm" action="${pageContext.request.contextPath }/member/memberDelete.do" method="post">
			<input type="hidden" name="mem_id" value="${member.mem_id}" />
			<input type="hidden" name="mem_pass" />
		</form>
	<form action="${pageContext.request.contextPath}/member/memberUpdate.do" method="post" enctype="multipart/form-data">
	</c:if>
	<table class="table table-sm table-hover">
		<tr>
			<th>회원아이디</th>
			<td>${member.mem_id}<input type="hidden" name="mem_id"
				value="${member.mem_id}" /><span class="error">${errors["mem_id"]}</span></td>
		</tr>
		<tr>
			<th>비밀번호</th>
			<td><input type="text" name="mem_pass"
				value="${member.mem_pass}" /><span class="error">${errors["mem_pass"]}</span></td>
		</tr>
		<tr>
			<th>회원명</th>
			<td><input type="text" name="mem_name"
				value="${member.mem_name}" /><span class="error">${errors["mem_name"]}</span></td>
		</tr>
		<tr>
			<th>회원이미지</th>
			<td>
				<input type="file" name="mem_image" accept="image/*"/>
				<div id="imageArea">
					<c:if test="${not empty member.mem_img }">
						<img src="data:image/*;base64,${member.mem_imgToBase64 }" />
					</c:if>
				</div>
			</td>
		</tr>
		<tr>
			<th>주민번호1</th>
			<td><input type="text" name="mem_regno1" disabled
				value="${member.mem_regno1}" /><span class="error">${errors["mem_regno1"]}</span></td>
		</tr>
		<tr>
			<th>주민번호2</th>
			<td><input type="text" name="mem_regno2" disabled
				value="${member.mem_regno2}" /><span class="error">${errors["mem_regno2"]}</span></td>
		</tr>
		<tr>
			<th>생일</th>
			<td><input type="text" class="datePicker" name="mem_bir" disabled
				value="${member.mem_bir}" /><span class="error">${errors["mem_bir"]}</span></td>
		</tr>
		<tr>
			<th>우편번호</th>
			<td><input type="text" name="mem_zip"
				value="${member.mem_zip}" /><span class="error">${errors["mem_zip"]}</span></td>
		</tr>
		<tr>
			<th>주소</th>
			<td><input type="text" name="mem_add1"
				value="${member.mem_add1}" /><span class="error">${errors["mem_add1"]}</span></td>
		</tr>
		<tr>
			<th>상세주소</th>
			<td><input type="text" name="mem_add2"
				value="${member.mem_add2}" /><span class="error">${errors["mem_add2"]}</span></td>
		</tr>
		<tr>
			<th>집전화번호</th>
			<td><input type="text" name="mem_hometel"
				value="${member.mem_hometel}" /><span class="error">${errors["mem_hometel"]}</span></td>
		</tr>
		<tr>
			<th>회사전화번호</th>
			<td><input type="text" name="mem_comtel"
				value="${member.mem_comtel}" /><span class="error">${errors["mem_comtel"]}</span></td>
		</tr>
		<tr>
			<th>휴대폰</th>
			<td><input type="text" name="mem_hp"
				value="${member.mem_hp}" /><span class="error">${errors["mem_hp"]}</span></td>
		</tr>
		<tr>
			<th>이메일</th>
			<td><input type="text" name="mem_mail"
				value="${member.mem_mail}" /><span class="error">${errors["mem_mail"]}</span></td>
		</tr>
		<tr>
			<th>직업</th>
			<td><input type="text" name="mem_job"
				value="${member.mem_job}" /><span class="error">${errors["mem_job"]}</span></td>
		</tr>
		<tr>
			<th>취미</th>
			<td><input type="text" name="mem_like"
				value="${member.mem_like}" /><span class="error">${errors["mem_like"]}</span></td>
		</tr>
		<tr>
			<th>기념일</th>
			<td><input type="text" name="mem_memorial"
				value="${member.mem_memorial}" /><span class="error">${errors["mem_memorial"]}</span></td>
		</tr>
		<tr>
			<th>기념일자</th>
			<td><input type="text" name="mem_memorialday" class="datePicker"
				value="${member.mem_memorialday}" /><span class="error">${errors["mem_memorialday"]}</span></td>
		</tr>
		<tr>
			<th>마일리지</th>
			<td>${member.mem_mileage}</td>
		</tr>
		<tr>
			<th>탈퇴여부</th>
			<td>${'Y' eq member.mem_delete ? "탈퇴" : "활동중"}</td>
		</tr>
		<tr>
			<td colspan="2">
				<input type="button" class="btn btn-default" onclick="location.href='${pageContext.request.contextPath}/member/memberList.do'" value="뒤로가기"/>
				<c:if test="${mutable }">
					<input type="submit" class="btn btn-primary" value="수정"/>
					<input type="reset"  class="btn btn-default" value="취소"/>
					<input type="button" id="delBtn" class="btn btn-default" value="탈퇴"/>
				</c:if>
			</td>
		</tr>
	</table>
	<c:if test="${mutable }">
	</form>
	</c:if>
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
			<c:set var="prodList" value="${member.prodList }"/>
			<c:choose>
				<c:when test="${not empty prodList }">
					<c:forEach items="${prodList }" var="tmp">
						<tr>
							<td>${tmp.prod_id}</td>
							<td>${tmp.prod_name}</td>
							<td>${tmp.prod_cost}</td>
							<td>${tmp.prod_price}</td>
							<td>${tmp.prod_outline}</td>
						</tr>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<tr><td colspan="5">구매목록이 없습니다.</td></tr>
				</c:otherwise>
			</c:choose>
		</tbody>
	</table>
</div>
</body>
</html>