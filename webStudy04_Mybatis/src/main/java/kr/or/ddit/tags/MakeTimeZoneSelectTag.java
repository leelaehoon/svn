package kr.or.ddit.tags;

import java.io.IOException;
import java.util.TimeZone;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class MakeTimeZoneSelectTag extends SimpleTagSupport {
	private String name;
	private String onchange;
	public void setName(String name) {
		this.name = name;
	}
	public void setOnchange(String onchange) {
		this.onchange = onchange;
	}
	
	@Override
	public void doTag() throws JspException, IOException {
		StringBuffer selectTag = new StringBuffer();
		String pattern = "<option value='%s'>%s</option>\n";
		if (onchange!=null && onchange.trim().length()>0) {
			selectTag.append("<select name='" + name + "' onchange='" + onchange + "();'>\n");
		} else {
			selectTag.append("<select name='" + name + "'>\n");
		}
		selectTag.append(String.format(pattern, "", "타임존 선택"));
		String[] zoneIds = TimeZone.getAvailableIDs();
		for (String zoneId : zoneIds) {
			selectTag.append(String.format(pattern, zoneId, TimeZone.getDefault().getTimeZone(zoneId).getDisplayName()));
		}
		selectTag.append("</select>\n");
		getJspContext().getOut().println(selectTag);
	}
}
