/**
 * 게시글에서 상세조회 댓글 처리
 */
	function replyMaker(resp) {
		if (resp.error) {
			alert(resp.error);
		} else {
			var html = "";
			if (resp.dataList) {
				$.each(resp.dataList, function (idx, reply) {
					html += "<tr id='TR_" + reply.rep_no + "'>";
					html += "<td>" + reply.rep_no + "</td>";
					html += "<td>" + reply.rep_writer + "</td>";
					html += "<td>" + reply.rep_content + "</td>";
					html += "<td>" + reply.rep_ip + "</td>";
					html += "<td>" + reply.rep_date;
					html += " <input class='replyModBtn' data-toggle='modal' type='button' value='수정'>";
					html += " <input class='replyDelBtn' type='button' value='삭제'></td>";
					html += "</tr>";
				});
			} else {
				html += "<tr><td colspan='5'>댓글이 없습니다.</td></tr>";
			}
			pagingNav.html(resp.pagingHTML);
			listBody.html(html);
			tempForm[0].reset();
		}
	}
	
	function pagingReply(page, bo_no) {
		$.ajax({
			url :$.getContextPath() + "/reply/replyList.do",
			data : {
				bo_no:bo_no,
				page:page
			},
			dataType : "json",
			success : replyMaker,
			error : function(resp) {
				console.log(resp.status);
			}
		});
	}
	
	$(function () {
		pagingNav = $("#pagingNav");
		listBody = $("#listBody");
		
		tempForm = $("[name='tempForm']");
		var replyForm = $("[name='replyForm']");
		var modifyModal = $("#repModModal");
		var originAction = replyForm.attr("action");
		
		$("#replyInsertBtn").on("click", function () {
			$("[name='rep_writer']").val($("[name='writer']").val());
			$("[name='rep_pass']").val($("[name='pass']").val());
			$("[name='rep_content']").val($("[name='content']").val());
			replyForm.submit();
		});
		
		replyForm.ajaxForm({
			dataType:'json',
			success: replyMaker,
			error: function (resp) {
				alert(resp.status)
			}
		});
		
		var originTag = "";
		
		listBody.on("click", ".replyDelBtn", function () {
			var chk = confirm("정말 삭제하시겠습니까?");
			if (chk) {
				var deleteTag = "비밀번호:<input name='pass' type='password'/><input class='replyDelConfirm' type='button' value='확인' /><input class='cancel' type='button' value='취소'>";
				originTag = $(this).parent().html(); 
				$(this).parent().html(deleteTag);
			}
		});
		
		listBody.on("click", ".replyModBtn", function () {
			var trId = $(this).closest("tr").prop("id");
			var rep_no = trId.substring(trId.indexOf("_")+1);
			modifyModal.find("#rep_no").val(rep_no);
			modifyModal.modal("show"); 
		});
		
		$("#modalBtn").on("click", function () {
			replyForm.attr("action", $.getContextPath() + "/reply/replyUpdate.do");
			var rep_no = modifyModal.find("#rep_no").val();
			var rep_pass = modifyModal.find("#rep_pass").val();
			replyForm.find("[name='rep_no']").val(rep_no);
			replyForm.find("[name='rep_pass']").val(rep_pass);
			replyForm.submit();
			replyForm.attr("action", originAction);
			modifyModal.find("#rep_pass").val("");
			modifyModal.modal("hide");
		});
		
		listBody.on("click", ".replyDelConfirm", function () {
			replyForm.attr("action", $.getContextPath() + "/reply/replyDelete.do");
			var rep_no = $(this).parent().siblings("td:first").text();
			var rep_pass = $(this).parent().children("[name='pass']").val();
			replyForm.find("[name='rep_no']").val(rep_no);
			replyForm.find("[name='rep_pass']").val(rep_pass);
			replyForm.submit();
			replyForm.attr("action", originAction);
		});
		
		listBody.on("click", ".cancel", function () {
			$(this).parent().html(originTag);
		})
	});