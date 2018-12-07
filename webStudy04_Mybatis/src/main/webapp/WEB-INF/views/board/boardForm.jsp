<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="cPath" value="${pageContext.request.contextPath }" scope="application" />
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
	src="${cPath }/js/jquery-3.3.1.min.js"></script>
<script src="http://malsup.github.com/jquery.form.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"
	integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49"
	crossorigin="anonymous"></script>
<script
	src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"
	integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy"
	crossorigin="anonymous"></script>
<script type="text/javascript" src="${cPath }/js/ckeditor/ckeditor.js"></script>
</head>
<body>
<div class="container d-flex justify-content-center">
<form action="<c:url value='/board/boardInsert.do' />" method="post" enctype="multipart/form-data">
	<input type="hidden" name="bo_no" value="${board.bo_no }" />
	<input type="hidden" name="bo_ip" value="${pageContext.request.remoteAddr }" />
	<table class="table">
		<tr>
			<th>작성자</th>
			<td><div class="input-group">
					<input class="form-control" type="text" name="bo_writer"
						value="${board.bo_writer}" /><span class="input-group-text"
						class="error">${errors["bo_writer"]}</span>
				</div></td>
		</tr>
		<tr>
			<th>비밀번호</th>
			<td><div class="input-group">
					<input class="form-control" type="password" name="bo_pass"
						value="${board.bo_pass}" /><span class="input-group-text"
						class="error">${errors["bo_pass"]}</span>
				</div></td>
		</tr>
		<tr>
			<th>메일</th>
			<td><div class="input-group">
					<input class="form-control" type="email" name="bo_mail"
						value="${board.bo_mail}" /><span class="input-group-text"
						class="error">${errors["bo_mail"]}</span>
				</div></td>
		</tr>
		<tr>
			<th>제목</th>
			<td><div class="input-group">
					<input class="form-control" type="text" name="bo_title"
						value="${board.bo_title}" /><span class="input-group-text"
						class="error">${errors["bo_title"]}</span>
				</div></td>
		</tr>
		<tr>
			<th>내용</th>
			<td>
				<div class="input-group">
					<textarea class="form-control" name="bo_content" id="bo_content" cols="100" rows="10">${board.bo_content }</textarea>
				</div>
			</td>
		</tr>
		<tr>
			<th>첨부파일</th>
			<td>
				<input class="form-control" type="file" name="bo_file" />
				<input class="form-control" type="file" name="bo_file" />
				<input class="form-control" type="file" name="bo_file" />
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<div class="d-flex justify-content-end">
					<input class="btn btn-info" type="submit" value="작성" />
					<input class="btn btn-default" type="reset" value="취소" />
					<input class="btn btn-default" type="reset" value="뒤로가기" onclick="history.back();" />
				</div>
			</td>
		</tr>
	</table>
</form>
<script type="text/javascript">
	CKEDITOR.replace( 'bo_content' );
</script>
</div>
</body>
</html>