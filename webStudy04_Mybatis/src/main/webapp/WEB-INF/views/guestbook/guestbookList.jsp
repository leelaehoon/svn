<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/jquery-3.3.1.min.js"></script>
	    <script src="http://malsup.github.com/jquery.form.js"></script> 
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>
	<div class="container">
		<form method="post" action="<c:url value='/gbook/gbookInsert.do' />">
			<input type="hidden" name="gb_ip" />
			<div class="input-group">
				<div id="imgArea">
				</div>
				<input id="gb_img" type="file" class="form-control" name="gb_img">
			</div>
			<div class="input-group">
				<span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span> 
				<input id="gb_writer" type="text" class="form-control" name="gb_writer" placeholder="작성자">
			</div>
			<div class="input-group">
				<span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span> 
				<input id="gb_pass" type="password" class="form-control" name="gb_pass" placeholder="비밀번호">
			</div>
			<br />
			<div class="input-group">
				<span class="input-group-addon">Text</span> 
				<textarea class="form-control" name="gb_content" id="gb_content" cols="100" rows="3" placeholder="방명록을 남겨주세요"></textarea>
			</div>
			<br />
			<div class="input-group">
				<input class="btn btn-primary" type="submit" value="작성" />
				<input class="btn btn-default" type="reset" value="취소" />
			</div>
		</form>
	</div>
	<div id="gbookList">
	</div>
</body>
</html>





