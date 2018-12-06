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
    <script src="http://malsup.github.com/jquery.form.js"></script> 
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"
	integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49"
	crossorigin="anonymous"></script>
<script
	src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"
	integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy"
	crossorigin="anonymous"></script>
<script type="text/javascript" src="<c:url value='/js/replyProcess.js' />" ></script>
<script type="text/javascript">
	$.getContextPath = function () {
		return "${pageContext.request.contextPath}";
	}
</script>
</head>
<body>
		<div class="container d-flex justify-content-center">
			<div class="d-flex align-items-start flex-column bd-highlight mb-3">
			<table class="table">
				<thead>
					<tr>
						<th>글번호:${board.bo_no }</th>
						<th>제목:${board.bo_title }</th>
						<th>작성자:${board.bo_writer }</th>
						<th>날짜:${board.bo_date }</th>
						<th>조회:${board.bo_hit }</th>
						<th>추천:${board.bo_rcmd }</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td colspan="6">
							<div>
								${board.bo_content }
							</div>
						</td>
					</tr>
				</tbody>
			</table>
			<div>
				<form name="tempForm" onsubmit="return false;">
					작성자: <input type="text" name="writer" />
					패스워드: <input type="password" name="pass" />
					내용: <textarea name="content" cols="100" rows="3"></textarea>
					<input id="replyInsertBtn" class='btn btn-primary' type="button" value="등록" />
				</form>
				
				<form action='<c:url value="/reply/replyInsert.do" />' name="replyForm" method="post" >
					<input type="hidden" name="rep_no" />
					<input type="hidden" name="bo_no" value="${param.what }" />
					<input type="hidden" name="rep_writer" />
					<input type="hidden" name="rep_ip" value="${pageContext.request.remoteAddr }" />
					<input type="hidden" name="rep_pass" />
					<input type="hidden" name="rep_content" />
				</form>
			</div>
		<table>
			<thead>
				<tr>
					<th>번호</th>
					<th>작성자</th>
					<th>내용</th>
					<th>아이피</th>
					<th>날짜</th>
				</tr>
			</thead>
			<tbody id="listBody">
			</tbody>
			<tfoot>
				<tr>
					<td colspan="5">
						<nav aria-label="Page navigation example" id="pagingNav">
						</nav>
					</td>
				</tr>
			</tfoot>
		</table>
			</div>
		</div>

	<!-- Modal -->
	<div class="modal fade" id="repModModal" tabindex="-1" role="dialog"
		aria-labelledby="exampleModalLabel" aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="exampleModalLabel">Modal title</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<input type="hidden" id="bo_no" value="${param.what }" />
					<input type="hidden" id="rep_no" />
					비밀번호 : <input type="password" id="rep_pass" />
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-primary" id="modalBtn">수정</button>
					<button type="button" class="btn btn-secondary" data-dismiss="modal">취소</button>
				</div>
			</div>
		</div>
	</div>
<script type="text/javascript">
	function paging(page) {
		pagingReply(page, ${board.bo_no});
	}
	paging(1);
</script>
</body>
</html>