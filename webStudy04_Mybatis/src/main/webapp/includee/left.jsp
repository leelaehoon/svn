<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script type="text/javascript">
	function goIndex(command) {
		var form = document.leftForm
		form.command.value = command
		form.submit()		
	}
</script>
<div class="col-sm-2 sidenav">
	<p><a href="javascript:goIndex('gugudan')">구구단</a></p>
	<p><a href="javascript:goIndex('lyrics')">가사파일</a></p>
	<p><a href="javascript:goIndex('calendar')">달력</a></p>
	<p><a href="javascript:goIndex('image')">이미지뷰어</a></p>
</div>
<form name="leftForm" action="<%=request.getContextPath() %>/" method="post">
	<input type="hidden" name="command" value=""/>
</form>