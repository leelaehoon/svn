<%@page import="java.util.Locale"%>
<%@page import="java.text.DateFormatSymbols"%>
<%@page import="java.util.Calendar"%>
<%@page import="static java.util.Calendar.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<style type="text/css">
	.sunday{
		background-color: red; 
	}
	.saturday{
		background-color: blue;
	}
	table{
		width: 100%;
		height: 500px;
		border-collapse: collapse;
	}
	td,th{
		border: 1px solid black;
	}
</style>
<script type="text/javascript">
	function eventHandler(year, month) {
		var form = document.calForm
		if((year && month) || month==0){
			form.year.value = year
			form.month.value = month
		}
		form.submit()
		return false;
	}
</script>
<%
	request.setCharacterEncoding("UTF-8");
	String yearStr = request.getParameter("year");
	String monthStr = request.getParameter("month");
	String language = request.getParameter("language");
	
	Locale clientLocale = request.getLocale();
	if(language != null && language.trim().length() > 0){
		clientLocale = Locale.forLanguageTag(language);
	}
	DateFormatSymbols symbols = new DateFormatSymbols(clientLocale);

	Calendar cal = getInstance();
	
	if(yearStr != null && yearStr.matches("\\d{4}")
			&& monthStr != null && monthStr.matches("1[0-1]|\\d")){
		cal.set(YEAR, Integer.parseInt(yearStr));
		cal.set(MONTH, Integer.parseInt(monthStr));
	}
	
	int currentYear = cal.get(YEAR);
	int currentMonth = cal.get(MONTH);
	
	cal.set(DAY_OF_MONTH, 1);
	int firstDayOfWeek = cal.get(DAY_OF_WEEK);
	int offset = firstDayOfWeek - 1;
	int lastDate = cal.getActualMaximum(DAY_OF_MONTH);
	
	cal.add(MONTH,-1);
	int beforeYear = cal.get(YEAR);
	int beforeMonth = cal.get(MONTH);
	
	cal.add(MONTH, 2);
	int nextYear = cal.get(YEAR);
	int nextMonth = cal.get(MONTH);
	
	Locale[] locales = getAvailableLocales();
%>
<form name="calForm" method="post">
<input type="hidden" name="command" value="calendar" />
<h4>
<a href="javascript:eventHandler(<%=beforeYear%>, <%=beforeMonth%>)">이전달</a>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<input type="number" name="year" value="<%=currentYear %>" onblur="eventHandler()"/>년
<select name="month" onchange="eventHandler()">
	<%	
		String[] monthStrings = symbols.getShortMonths();
		for(int idx = 0; idx < monthStrings.length-1; idx++){
			out.println(String.format("<option value='%d' %s>%s</option>", idx, idx==currentMonth?"selected":"", monthStrings[idx]));
		}
	%>
</select>
<select name="language" onchange="eventHandler()">
	<%
		for(Locale tmp : locales){
			out.println(String.format("<option value='%s' %s>%s</option>", tmp.toLanguageTag(), tmp.equals(clientLocale)?"selected":"", tmp.getDisplayLanguage(clientLocale)));
		}
	%>
</select>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<a onclick="eventHandler(<%=nextYear%>, <%=nextMonth%>)">다음달</a>
</h4>
</form>
<table>
<thead>
	<tr>
		<%
			String[] dateStrings = symbols.getShortWeekdays();
			for(int idx = Calendar.SUNDAY; idx <= Calendar.SATURDAY; idx++){
				out.println(String.format("<th>%s</th>", dateStrings[idx]));
			}
		%>
	</tr>
</thead>
<tbody>
<%
	int dayCount = 1;
	for(int row=1; row<=6; row++){
		%>
		<tr>
		<%
		for(int col=1; col<=7; col++){
			int dateChar = dayCount++ - offset;
			if(dateChar < 1 || dateChar > lastDate){
				out.println("<td>&nbsp;</td>");
			}else{
				String clzValue= "normal";
				if(col==1){
					clzValue = "sunday";
				}else if(col==7){
					clzValue = "saturday";
				}
				out.println(String.format("<td class='%s'>%d</td>", clzValue, dateChar));
			}
		}
		%>
		</tr>
		<%
	}
%>
</tbody>
</table>