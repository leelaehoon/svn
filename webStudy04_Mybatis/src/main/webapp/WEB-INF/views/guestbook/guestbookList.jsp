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
		replyDivHide();
		searchForm = $("#searchForm");
		gbList = $("#gbList");
		pagingNav = $("#pagingNav");
		gbookInsertForm = $("#gbookInsertForm");
		gbookModal = $("#gbookModal");
		action = gbookInsertForm.attr("action");
		gbrForm = $("#gbrForm");
		gbrAction = gbrForm.attr("action");
		
		searchForm.ajaxForm({
			dataType : 'json',
			success : listMaker,
			error : function (resp) {
				alert("에러");
			}
		});
		
		gbookInsertForm.ajaxForm({
			dataType : 'json',
			success : listMaker,
			error : function (resp) {
				alert("에러");
			}
		});
		
		gbrForm.ajaxForm({
			dataType : 'json',
			success : listMaker,
			error : function (resp) {
				alert("에러");
			}
		});
		
		// 댓글 열고 닫기
		gbList.on("click", ".openReply", function () {
			var replyDiv = $(this).siblings(".gbreplyList")
			if ("[댓글열기]" == $(this).text()) {
				if (replyDiv) {
					replyDiv.show();
				}
				$(this).text("[댓글닫기]");
			} else {
				if (replyDiv) {
					replyDiv.hide();
				}
				$(this).text("[댓글열기]");
			}
		});
		
		// 방명록 삭제
		gbList.on("click", ".gbookDelBtn", function () {
			var check = confirm("정말 삭제하시겠습니까?");
			if (check) {
				var gbookDel = "gbookDel";
				var gb_no = $(this).attr("id");
				gb_no = gb_no.substring(gb_no.indexOf(gbookDel)+gbookDel.length);
				gbookModal.find("#modalBtn").val("방명록삭제");
				gbookModal.find("#target_no").val(gb_no);
				gbookModal.modal("show");
			}
		});
		
		// 댓글 작성
		gbList.on("click", ".gbreplyInsert", function () {
			var gbrInsert ="gbrInsert";
			var gbreplyList = $(this).parent().parent();
			var gb_no = gbreplyList.attr("id");
			gb_no = gb_no.substring(gb_no.indexOf(gbrInsert)+gbrInsert.length);
			var gbr_writer = gbreplyList.find("[name='gbr_writer']").val();
			var gbr_pass = gbreplyList.find("[name='gbr_pass']").val();
			var gbr_content = gbreplyList.find("[name='gbr_content']").val();
			gbrForm.find("[name='gb_no']").val(gb_no);
			gbrForm.find("[name='gbr_writer']").val(gbr_writer);
			gbrForm.find("[name='gbr_pass']").val(gbr_pass);
			gbrForm.find("[name='gbr_content']").val(gbr_content);
			gbrForm.attr("action", "<c:url value='/gbreply/gbreplyInsert.do' />");
			gbrForm.submit();
			gbrForm.attr("action", gbrAction);
		});
		
		// 방명록 댓글 삭제
		gbList.on("click", ".gbreplyDelBtn", function () {
			var check = confirm("정말 삭제하시겠습니까?");
			if (check) {
				var gbreplyDel = "gbreplyDel";
				var gbr_no = $(this).attr("id");
				gbr_no = gbr_no.substring(gbreplyDel.indexOf(gbreplyDel)+gbreplyDel.length);
				gbookModal.find("#modalBtn").val("댓글삭제");
				gbookModal.find("#target_no").val(gbr_no);
				gbookModal.modal("show");
			}
		});
		
		// 모달버튼 이벤트 처리
		$("#modalBtn").on("click", function () {
			var btnText = $(this).val();
			if ("방명록삭제" == btnText) {
				var gb_no = $("#target_no").val();
				var gb_pass = $("#target_pass").val();
				gbookInsertForm.attr("action", "<c:url value='/gbook/gbookDelete.do' />");
				gbookInsertForm.find("[name='gb_no']").val(gb_no);
				gbookInsertForm.find("[name='gb_pass']").val(gb_pass);
				gbookInsertForm.submit();
				gbookInsertForm.attr("action", action);
				gbookModal.modal("hide");			
			} else if ("댓글삭제" == btnText) {
				var gbr_no = $("#target_no").val();
				var gbr_pass = $("#target_pass").val();
				gbrForm.find("[name='gbr_no']").val(gbr_no);
				gbrForm.find("[name='gbr_pass']").val(gbr_pass);
				gbrForm.submit();
				gbookModal.modal("hide");			
			}
		});
	})
	
	// 리스트 노가다
	function listMaker(resp) {
		var tags = "";
		$.each(resp.dataList, function (idx, gbook) {
			tags += "<div class='media border p-3'>";
			tags += "<img src='data:image/*;base64," + gbook.gb_imgToBase64 + "' alt='" + gbook.gb_writer + "' class='mr-3 mt-3 rounded-circle' style='width: 60px;'>"
			tags += "<div class='media-body'>";
			tags += "<h4>" + gbook.gb_writer + " <small><i>" + gbook.gb_date + "</i></small> ";
			tags += "<small><input type='button'  class='btn btn-success ' value='수정' /></small> ";
			tags += "<small><input type='button' id='gbookDel" + gbook.gb_no + "' class='btn btn-danger gbookDelBtn' value='삭제' /></small></h4>";
			tags += gbook.gb_content + "<br /><br />";
			tags += "<span class='openReply'>[댓글열기]</span>";
			tags += "<div class='gbreplyList' id='gbrInsert" + gbook.gb_no + "'>";
			tags += "<div class='container-fluid'><div class='row'><div class='col-sm-10'>";
			tags += "<div class='form-inline'>";
			tags += "작성자: <input type='text' class='form-control'	name='gbr_writer' placeholder='writer'> ";
			tags += "비밀번호: <input type='password' class='form-control' name='gbr_pass' placeholder='password'></div><br /><div class='form-group'>";
			tags += "<textarea class='form-control' rows='5' name='gbr_content' placeholder='댓글을 남겨주세요'></textarea></div></div></div></div>";
			tags += "<div class='d-flex justify-content-end'>";
			tags += "<button type='button' class='btn btn-primary '>작성</button></div>";
			if (gbook.gbreplyList) {
				$.each(gbook.gbreplyList, function (idxx, gbreply) {
					tags += "<div class='media p-3'>";
					tags += "<div class='media-body'>";
					tags += "<h6>" + gbreply.gbr_writer + " <small><i>" + gbreply.gbr_date + "</i></small> ";
					tags += "<small><input type='button'  class='btn btn-success ' value='수정' /></small>";
					tags += "<small><input type='button' id='gbreplyDel" + gbreply.gbr_no + "' class='btn btn-danger gbreplyDelBtn' value='삭제' /></small></h6>";
					tags += gbreply.gbr_content;
					tags += "</div></div>";
				})
			}
			tags += "</div></div></div>";
		})
		gbList.html(tags);
		pagingNav.html(resp.pagingHTML);
		replyDivHide();
	}
	
	function ${pagingVO.funcName}(page) {
		$("#searchForm").find("[name='page']").val(page);
		$("#searchForm").submit();
	}
	
	function replyDivHide() {
		$(".gbreplyList").hide();
	}
</script>
</head>
<body>
	<form id="gbrForm" action="<c:url value='/gbreply/gbreplyDelete.do' />"
		method="post">
		<input type="hidden" name="gb_no" /> 
		<input type="hidden" name="gbr_pass" />
		<input type="hidden" name="gbr_writer" />
		<input type="hidden" name="gbr_content" />
	</form>
	<div class="container">
		<table class="table">
			<thead>
				<tr>
					<td>
						<form id="gbookInsertForm" method="post"
							action="<c:url value='/gbook/gbookInsert.do' />"
							enctype="multipart/form-data">
							<input type="hidden" name="gb_no" /> <input type="hidden"
								name="gb_ip" value="${pageContext.request.remoteAddr }" />
							<div class="container-fluid">
								<div class="row">
									<div class="col-sm-2">
										<div class="custom-file">
											<input type="file" class="custom-file-input" id="gb_img"
												name="gb_img" accept="image/*"> <label
												class="custom-file-label" for="gb_img">Choose file</label>
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
						</form> <br /> <c:set var="gbookList" value="${pagingVO.dataList }" />
						<div id="gbList">
							<c:if test="${not empty gbookList}">
								<c:forEach items="${gbookList }" var="gbook">
									<div class="media border p-3">
										<img src="data:image/*;base64,${gbook.gb_imgToBase64 }"
											alt="${gbook.gb_writer }" class="mr-3 mt-3 rounded-circle"
											style="width: 60px;">
										<div class="media-body">
											<h4>
												${gbook.gb_writer } <small><i>${gbook.gb_date }</i></small>
												<small><input type="button" class="btn btn-success "
													value="수정" /></small> <small><input type="button"
													id="gbookDel${gbook.gb_no }"
													class="btn btn-danger gbookDelBtn" value="삭제" /></small>
											</h4>
											${gbook.gb_content } <br /> <br /> <span class="openReply">[댓글열기]</span>
											<div class="gbreplyList" id="gbrInsert${gbook.gb_no }">

												<div class="container-fluid">
													<div class="row">
														<div class="col-sm-10">
															<div class="form-inline">
																작성자: <input type="text" class="form-control"
																	name="gbr_writer" placeholder="writer"> 비밀번호: <input
																	type="password" class="form-control" name="gbr_pass"
																	placeholder="password">
															</div>
															<br />
															<div class="form-group">
																<textarea class="form-control" rows="5"
																	name="gbr_content" placeholder="댓글을 남겨주세요"></textarea>
															</div>
														</div>
													</div>
												</div>
												<div class="d-flex justify-content-end">
													<button type="button" class="btn btn-primary gbreplyInsert">작성</button>
												</div>

												<c:if test="${not empty gbook.gbreplyList }">
													<c:forEach items="${gbook.gbreplyList }" var="gbreply">
														<div class="media p-3">
															<div class="media-body">
																<h6>${gbreply.gbr_writer }
																	<small><i>${gbreply.gbr_date }</i></small> <small><input
																		type="button" class="btn btn-success " value="수정" /></small>
																	<small><input type="button"
																		id="gbreplyDel${gbreply.gbr_no }"
																		class="btn btn-danger gbreplyDelBtn" value="삭제" /></small>
																</h6>
																${gbreply.gbr_content }
															</div>
														</div>
													</c:forEach>
												</c:if>
											</div>
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

	<!-- Modal -->
	<div class="modal fade" id="gbookModal" tabindex="-1" role="dialog"
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
					<input type="hidden" id="target_no" /> 비밀번호 : <input
						type="password" id="target_pass" />
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-primary" id="modalBtn">삭제</button>
					<button type="button" class="btn btn-secondary"
						data-dismiss="modal">취소</button>
				</div>
			</div>
		</div>
	</div>
</body>
</html>





