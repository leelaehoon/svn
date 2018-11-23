<%@page import="java.util.Calendar"%>
<%@page import="java.util.Locale"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%!
	private Calendar calendar = Calendar.getInstance();
	private int year;
	private int month;
	
%>
<%
	String lang = request.getParameter("lang");
	Locale currentLocale = request.getLocale();
	
	
	if(lang!=null && lang.trim().length()!=0){
		currentLocale = Locale.forLanguageTag(lang);
	}
	
	String yearStr = request.getParameter("year");
	String monthStr = request.getParameter("month");
	
	year = calendar.get(Calendar.YEAR);
	month = calendar.get(Calendar.MONTH);
	
	if (yearStr != null && yearStr.trim().length() != 0) {
		year = Integer.parseInt(yearStr);
	}
	if (monthStr != null && monthStr.trim().length() != 0) {
		month = Integer.parseInt(monthStr);
		if (month < 0) {
			month = 11;
			year--;
		} else if (month > 11) {
			month = 0;
			year++;
		}
	}
	
	calendar.set(year, month, 1);
	int endDay = calendar.getActualMaximum(Calendar.DATE);
	int startDOW = calendar.get(Calendar.DAY_OF_WEEK);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
	function changeHandler() {
		document.calendarForm.submit();
	}
</script>
<style type="text/css">
	a{text-decoration: none;}
</style>
</head>
<body>
<%
	Locale[] locales = Calendar.getAvailableLocales();
%>
<form name="calendarForm">
	<select name="lang" onchange="changeHandler()">
	<option value="">언어 선택</option>
	<%
		String optPattern = "<option value='%s' %s>%s</option>";
		for(Locale tmp : locales){
			String value = tmp.toLanguageTag();
			String text = tmp.getDisplayLanguage();
			String selected = "";
			if(currentLocale.equals(tmp)){
				selected = "selected";
			}
			if(text!=null && text.trim().length()!=0){
				out.println(String.format(optPattern, value, selected, text));
			}
		}
	%>
	</select>
	<br>
	<a href="<%=request.getContextPath() %>/04/calendarTest.jsp?year=<%=year%>&month=<%=month-1%>&lang=<%=currentLocale.toLanguageTag()%>">&lt;</a>
	<label>
	<select name="year" onchange="changeHandler()">
		<%
			for(int y = year-10; y < year+10; y++){
				String selected = "";
				if(year==y){
					selected = "selected";
				}
				out.println(String.format(optPattern, y, selected, y));
			}
		%>
	</select>년
	</label>
	<label>
	<select name="month" onchange="changeHandler()">
		<%
			for(int m = 0; m < 12; m++){
				String selected = "";
				if(month == m){
					selected = "selected";
				}
				out.println(String.format(optPattern, m, selected, m+1));
			}
		%>
	</select>월
	</label>
	<a href="<%=request.getContextPath() %>/04/calendarTest.jsp?year=<%=year%>&month=<%=month+1%>&lang=<%=currentLocale.toLanguageTag()%>">&gt;</a>
</form>
<table>
	<thead>
		<tr>
			<%
				for(int i = 1; i < 8; i++){
					calendar.set(Calendar.DAY_OF_WEEK, i);
					String dayOfWeekStr = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, currentLocale);
					out.print("<th width='30px'><div align='center'>" + dayOfWeekStr + "</div></th>");
				}
			%>
		</tr>
	</thead>
	<tbody>
		<%
			String pattern = "<td width='30px' height='30px'><div align='center'>%d</div></td>";
			for(int week = 0; week < ((startDOW+endDay)%7 == 0 ? (startDOW+endDay)/7:(startDOW+endDay)/7+1); week++){
				out.print("<tr>");
				if(week==0){
					for(int dayOfWeek = 1; dayOfWeek <= 7; dayOfWeek++){
						if(dayOfWeek < startDOW){
							out.print("<td></td>");
						} else {
							out.print(String.format(pattern, dayOfWeek-startDOW+1));
						}
					}
				} else {
					for(int dayOfWeek = 1; dayOfWeek <= 7; dayOfWeek++){
						if(dayOfWeek-startDOW+1+week*7<=endDay){
							out.print(String.format(pattern, (dayOfWeek-startDOW+1)+(week*7)));
						} else {
							out.print("<td></td>");
						}
					}
				}
				out.print("</tr>");
			}
		%>
	</tbody>
</table>
</body>
</html>