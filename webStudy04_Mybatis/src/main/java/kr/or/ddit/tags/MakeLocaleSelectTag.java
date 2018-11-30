package kr.or.ddit.tags;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class MakeLocaleSelectTag extends SimpleTagSupport {
	private String name;
	private String addClass;
	
	public void setName(String name) {
		this.name = name;
	}
	public void setAddClass(String addClass) {
		this.addClass = addClass;
	}
	
	@Override
	public void doTag() throws JspException, IOException {
		StringBuffer selectTag = new StringBuffer();
		String pattern = "<option value='%s'>%s</option>\n";
		selectTag.append("<select class='" + addClass + "' name='" + name + "'>\n");
		selectTag.append(String.format(pattern, "", "로케일 선택"));
		Locale[] locales = Locale.getAvailableLocales();
		for (Locale locale : locales) {
			if (locale.getDisplayName()!=null && locale.getDisplayName().trim().length()>0) {
				selectTag.append(String.format(pattern, locale.toLanguageTag(), locale.getDisplayName()));
			}
		}
		selectTag.append("</select>\n");
		getJspContext().getOut().println(selectTag);
	}
}









