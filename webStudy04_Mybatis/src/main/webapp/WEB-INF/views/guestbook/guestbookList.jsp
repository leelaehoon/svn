<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
	src="${pageContext.request.contextPath }/js/jquery-3.3.1.min.js"></script>
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
	$(function() {
		var searchForm = $("#searchForm");
		var gbList = $("#gbList");
		var pagingNav = $("#pagingNav");
		
		searchForm.ajaxForm({
			dataType : 'json',
			success : function (resp) {
				var tags = "";
				$.each(resp.dataList, function (idx, gbook) {
					tags += "<div class='media border p-3'>";
					tags += "<img src='img_avatar3.png' alt='" + gbook.gb_writer + "' class='mr-3 mt-3 rounded-circle' style='width: 60px;'>"
					tags += "<div class='media-body'>";
					tags += "<h4>" + gbook.gb_writer + " <small><i>" + gbook.gb_date + "</i></small></h4>";
					tags += gbook.gb_content + "</div></div>";
				})
				gbList.html(tags);
				pagingNav.html(resp.pagingHTML);
			},
			error : function (resp) {
				
			}
		});
	})
	
	function ${pagingVO.funcName}(page) {
		$("#searchForm").find("[name='page']").val(page);
		$("#searchForm").submit();
	}
</script>
</head>
<body>
	<div class="container">
		<table class="table">
			<thead>
				<tr>
					<td>
						<form method="post">
							<input type="hidden" name="gb_ip"
								value="${pageContext.request.remoteAddr }" />
							<div class="container-fluid">
								<div class="row">
									<div class="col-sm-2">
										<div class="custom-file">
											<input type="file" class="custom-file-input" id="customFile">
											<label class="custom-file-label" for="customFile">Choose
												file</label>
										</div>
									</div>
									<div class="col-sm-10">
										<div class="form-inline">
											<label for="gb_writer">작성자:</label> <input type="text"
												class="form-control" id="gb_writer" name="gb_writer"
												placeholder="writer"> <label for="gb_pass">비밀번호:</label>
											<input type="password" class="form-control" id="gb_pass"
												name="gb_pass" placeholder="password">
										</div>
										<br />
										<div class="form-group">
											<textarea class="form-control" rows="5" id="gb_content"
												name="gb_content" placeholder="방명록을 남겨주세요"></textarea>
										</div>
									</div>
								</div>
							</div>
							<div class="d-flex justify-content-end">
								<button type="submit" class="btn btn-primary">작성</button>
								<button type="reset" class="btn btn-default">취소</button>
							</div>
						</form>
					</td>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>
						<form id="searchForm"
							action="<c:url value='/gbook/gbookList.do' />">
							<input type="hidden" name="page" />
							<div class="container-fluid">
								<div class="row">
									<div class="col-sm-2">
										<select name="searchType" class="form-control" id="sel1">
											<option value="all">전체</option>
											<option value="writer">작성자</option>
											<option value="content">내용</option>
										</select>
									</div>
									<div class="col-sm-8">
										<input type="text" name="searchWord" class="form-control"
											placeholder="검색내용">
									</div>
									<div class="col-sm-2">
										<input type="submit" class="btn btn-info" value="검색" />
									</div>
								</div>
							</div>
						</form> <br /> 
						<c:set var="gbookList" value="${pagingVO.dataList }" />
						<div id="gbList">
						<c:if test="${not empty gbookList}">
							<c:forEach items="${gbookList }" var="gbook">
								<div class="media border p-3">
									<img src="img_avatar3.png" alt="${gbook.gb_writer }"
										class="mr-3 mt-3 rounded-circle" style="width: 60px;">
									<div class="media-body">
										<h4>
											${gbook.gb_writer } <small><i>${gbook.gb_date }</i></small>
										</h4>
										${gbook.gb_content }
									</div>
								</div>
							</c:forEach>
						</c:if>
						</div>
					</td>
				</tr>
			</tbody>
			<tfoot>
				<tr>
					<td>
						<nav aria-label="Page navigation example" id="pagingNav">
							${pagingVO.pagingHTML }</nav>
					</td>
				</tr>
			</tfoot>
		</table>
	</div>
</body>
</html>





