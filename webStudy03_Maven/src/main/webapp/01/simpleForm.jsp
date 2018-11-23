<%@page import="java.util.LinkedHashMap"%>
<%@page import="kr.or.ddit.vo.AlbaVO"%>
<%@page import="java.util.Arrays"%>
<%@page import="java.util.Objects"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.Map.Entry"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%--
	Map<String, String> gradeMap =(Map<String, String>) application.getAttribute("gradeMap");
	Map<String, String> licenseMap =(Map<String, String>) application.getAttribute("licenseMap");
	AlbaVO alba = (AlbaVO) request.getAttribute("alba");
	Map<String, String> errors =(Map<String, String>) request.getAttribute("errors");
	if(alba==null) alba = new AlbaVO();
	if(errors==null) errors = new LinkedHashMap<>();
--%>
<jsp:useBean id="gradeMap" class="java.util.HashMap" scope="application" />
<jsp:useBean id="licenseMap" class="java.util.LinkedHashMap" scope="application" />
<jsp:useBean id="alba" class="kr.or.ddit.vo.AlbaVO" scope="request" />
<jsp:useBean id="errors" class="java.util.LinkedHashMap" scope="request" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>simpleForm</title>
<style type="text/css">
	.error{
		color: red;
	}
</style>
</head>
<body>
<%-- 알바몬에서 알바생의 프로필을 입력받으려고 함. --%>
<%-- 이름, 나이, 주소, 전화번호, 성별, 경력, 학력, 자격증 --%>
<form action="<%=request.getContextPath()%>/albamon" method="post">
<table>
	<tr>
		<th>이름</th>
		<td>
			<input type="text" name="name" value="<%=Objects.toString(alba.getName(), "") %>"/>
			<span class="error">
				<%=Objects.toString(errors.get("name"), "") %>
			</span>
		</td>
	</tr>
	<tr>
		<th>나이</th>
		<td>
			<input type="number" name="age" min="15" max="40" value="<%=Objects.toString(alba.getAge(), "") %>"/>
		</td>
	</tr>
	<tr>
		<th>전화번호</th>
		<td>
			<input type="text" name="tel" placeholder="000-0000-0000"
				pattern="\d{3}-\d{3,4}-\d{4}" required="required" value="<%=Objects.toString(alba.getTel(), "") %>"
			/>
			<span class="error">
				<%=Objects.toString(errors.get("tel"), "") %>
			</span>
		</td>
	</tr>
	<tr>
		<th>주소</th>
		<td>
			<input type="text" name="address" required="required" value="<%=Objects.toString(alba.getAddress(), "") %>"/>
			<span class="error">
				<%=Objects.toString(errors.get("address"), "") %>
			</span>
		</td>
	</tr>
	<tr>
		<th>성별</th>
		<td>
			<label><input type="radio" name="gender" value="M" <%="M".equals(alba.getGender())?"checked":"" %>/>남</label>
			<label><input type="radio" name="gender" value="F" <%="F".equals(alba.getGender())?"checked":"" %>/>여</label>
		</td>
	</tr>
	<tr>
		<th>학력</th>
		<td>
			<select name="grade">
				<option value="">학력</option>
				<%
					String pattern = "<option value='%s' %s>%s</option>";
					for (Object obj : gradeMap.entrySet()) {
						Entry entry = (Entry)obj;
						String selected = "";
						if(entry.getKey().equals(alba.getGrade())){
							selected = "selected";
						}
						out.println(String.format(pattern, entry.getKey(), selected, entry.getValue()));
					}
				%>
			</select>
		</td>
	</tr>
	<tr>
		<th>경력</th>
		<td>
			<textarea rows="3" cols="100" name="career"><%=Objects.toString(alba.getCareer(), "") %></textarea>
		</td>
	</tr>
	<tr>
		<th>자격증</th>
		<td>
			<select name="license" multiple="multiple" size="10">
				<%
					if(alba.getLicense()!=null){
						Arrays.sort(alba.getLicense());
					}
					for (Object obj :  licenseMap.entrySet()) {
						Entry entry = (Entry) obj;
						String selected = "";
						if(alba.getLicense()!=null && Arrays.binarySearch(alba.getLicense(), entry.getKey()) > -1){
							selected = "selected";
						}
						out.println(String.format(pattern, entry.getKey(), selected, entry.getValue()));
					}
				%>
			</select>
		</td>
	</tr>
	<tr>
		<td colspan="2">
			<input type="submit" value="등록" />
			<input type="reset" value="취소" />
			<input type="button" value="버튼" />
		</td>
	</tr>
</table>
</form>
</body>
</html>