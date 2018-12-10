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
<c:url value="/board/boardView.do" var="boardView" />
<script type="text/javascript">
	$(function () {
		var listBody = $("#listBody");		
		var pagingNav = $("#pagingNav");
		var searchForm = $('[name="searchForm"]');
		
		$(window).on("popstate", function (event) {
			if (event.originalEvent.state) {
				var pagingVO = event.originalEvent.state;
				var tags = "";
				var boardList = pagingVO.dataList;
				var pagingHTML = pagingVO.pagingHTML;
				if (boardList.length) {
					$.each(boardList, function (idx, board) {
						tags += "<tr>";
						tags += "<td>" + board.bo_no +"</td>";     
						tags += "<td><a href='${boardView}?what=" + board.bo_no + "'>" + board.bo_title +"</a><td>";
						tags += "<td>" + board.bo_writer +"</td>"; 
						tags += "<td>" + board.bo_date +"</td>";   
						tags += "<td>" + board.bo_hit +"</td>";   
						tags += "<td>" + board.bo_rcmd +"</td>";  
						tags += "</tr>";
					})
				} else {
					tags += "<tr><td colspan='6'>게시글이 없습니다.</td></tr>";
				}
				listBody.html(tags);
				pagingNav.html(pagingHTML);
			} else {
				location.reload();
			}
		});
		
		searchForm.ajaxForm({
			dataType : 'json',
			success : function (resp) {
				var tags = "";
				var boardList = resp.dataList;
				var pagingHTML = resp.pagingHTML;
				if (boardList) {
					$.each(boardList, function (idx, board) {
						tags += "<tr>";
						tags += "<td>" + board.bo_no +"</td>";     
						tags += "<td><a href='${boardView}?what=" + board.bo_no + "'>" + board.bo_title +"</a><td>";
						tags += "<td>" + board.bo_writer +"</td>"; 
						tags += "<td>" + board.bo_date +"</td>";   
						tags += "<td>" + board.bo_hit +"</td>";   
						tags += "<td>" + board.bo_rcmd +"</td>";  
						tags += "</tr>";
					})
				} else {
					tags += "<tr><td colspan='6'>게시글이 없습니다.</td></tr>";
				}
				listBody.html(tags);
				pagingNav.html(pagingHTML);
				// 비동기 처리 성공 -> puch state on history (state, title, url)
				var pageNum = searchForm.find("[name='page']").val();
				var queryString = searchForm.serialize();
				history.pushState(resp, pageNum +"페이지", "?" + queryString);
				searchForm.find("[name='page']").val("");
			}
		});
	});

	function ${pagingVO.funcName}(page) {
		$("[name='searchForm']").find("[name='page']").val(page);
		$("[name='searchForm']").submit();
	}
</script>
</head>
<body>
	<div class="container managergroup">
		<table class="bbsList">
			<thead class="head">
				<tr>
					<th>번호</th>
					<th>제목</th>
					<th>글쓴이</th>
					<th>날짜</th>
					<th>조회</th>
					<th>추천</th>
				</tr>
			</thead>
			<tbody id="listBody" class="body">
				<c:set var="boardList" value="${pagingVO.dataList }" />
				<c:choose>
					<c:when test="${not empty boardList }">
						<c:forEach items="${boardList }" var="board" >
							<tr>
								<td>${board.bo_no }</td>
								<td><a href="${boardView }?what=${board.bo_no}">${board.bo_title }</a></td>
								<td>${board.bo_writer }</td>
								<td>${board.bo_date }</td>
								<td>${board.bo_hit }</td>
								<td>${board.bo_rcmd }</td>
							</tr>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<tr>
							<td colspan="6">게시글이 없습니다.</td>
						</tr>
					</c:otherwise>
				</c:choose>
			</tbody>
			<tfoot class="foot">
				<tr>
					<td colspan="6">
						<nav aria-label="Page navigation example" id="pagingNav">
							${pagingVO.pagingHTML }
						</nav>
					</td>
				</tr>
				<tr>
					<td colspan="6">
						<div class="d-flex justify-content-center">
							<form name="searchForm">
								<div class="form-group row">
									<input type="hidden" name="page" />
									<div class="col-xs-2 col-centered">
										<select name="searchType" class="form-control">
											<option value="all">전체</option>
											<option value="title">제목</option>
											<option value="writer">글쓴이</option>
											<option value="content">내용</option>
										</select>
									</div>
									<script type="text/javascript">
										document.searchForm.searchType.value = "${empty pagingVO.searchType ? 'all':pagingVO.searchType}";
									</script>	
									<div class="col-xs-2 col-centered">
										<input class="form-control" type="text" name="searchWord" value="${pagingVO.searchWord }" />
									</div>
									<div class="col-xs-2 col-centered">
										<input class="form-control" type="submit" value="검색" />
									</div>
								</div>
							</form>
							<div class="col-xs-2 col-centered">
								<input class="form-control" type="button" value="글쓰기" onclick="location.href='<c:url value='/board/boardInsert.do' />'" />
							</div>
						</div>
					</td>
				</tr>
			</tfoot>
		</table>
	</div>
</body>
</html>