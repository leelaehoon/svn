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
<link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet"/>
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
<script type="text/javascript">
	function paging(page) {
		$.ajax({
			url : "${pageContext.request.contextPath}/reply/replyList.do",
			data : {
				bo_no:${board.bo_no},
				page:page
			},
			dataType : "json",
			success : function(resp) {
				var html = "";
				if (resp.dataList) {
					$.each(resp.dataList, function (idx, reply) {
						html += "<tr>";
						html += "<td>" + reply.rep_no + "</td>";
						html += "<td>" + reply.rep_writer + "</td>";
						html += "<td>" + reply.rep_content + "</td>";
						html += "<td>" + reply.rep_ip + "</td>";
						html += "<td>" + reply.rep_date + "</td>";
						html += "</tr>";
					});
				} else {
					html += "<tr><td colspan='5'>댓글이 없습니다.</td></tr>";
				}
				pagingNav.html(resp.pagingHTML);
				listBody.html(html);
			},
			error : function(resp) {
				console.log(resp.status);
			}
		});
	}
	
	$(function () {
		pagingNav = $("#pagingNav");
		listBody = $("#listBody");
		paging(1);
	});
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
				<c:choose>
					<c:when test="${not empty board.replyList }">
						<c:forEach items="${board.replyList }" var="reply">
							<tr>
								<td>${reply.rep_no }</td>
								<td>${reply.rep_writer }</td>
								<td>${reply.rep_content }</td>
								<td>${reply.rep_ip }</td>
								<td>${reply.rep_date }</td>
							</tr>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<tr>
							<td colspan="5">댓글이 없습니다.</td>
						</tr>
					</c:otherwise>
				</c:choose>
			</tbody>
			<tfoot>
				<tr>
					<td colspan="5">
						<nav aria-label="Page navigation example" id="pagingNav">
							${pagingVO.pagingHTML }
						</nav>
					</td>
				</tr>
			</tfoot>
		</table>
			</div>
		</div>
</body>
</html>