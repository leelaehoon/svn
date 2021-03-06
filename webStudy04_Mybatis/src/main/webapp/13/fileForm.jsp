<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>13/fileForm.jsp</title>
</head>
<body>
<h4>파일 업로드 양식</h4>
<!-- 클라이언트로부터 이미지를 업로드 받고, 다시 현재 페이지로 돌아와서 -->
<!-- 하단의 div 태그내에 해당 이미지가 출력되도록.. -->
<!-- 단, 이미지 파일의 메타데이터들도 함께 출력되도록 -->
<form action="<c:url value='/upload_3' />" method="post" enctype="multipart/form-data" >
	<ul>
		<li>
			업로더: <input type="text" name="uploader" />
		</li>
		<li>
			업로드할 파일 : <input type="file" name="uploadFile" />
			<input type="submit" value="업로드" />
		</li>
	</ul>
</form>
<c:set var="fileVO" value="${sessionScope.fileVO }" />
<c:remove var="fileVO" scope="session"/>
<c:if test="${not empty fileVO }" >
	<div>
		<img src="<c:url value='${fileVO.saveFileUrl }' />" title="${fileVO.saveFileUrl }" />
	</div>
</c:if>
</body>
</html>