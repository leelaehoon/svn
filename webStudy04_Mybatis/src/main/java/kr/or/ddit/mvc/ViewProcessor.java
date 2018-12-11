package kr.or.ddit.mvc;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ViewProcessor {
	private String prefix;
	private String suffix;
	
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}
	
	/**
	 * 논리적인 view name을 받아서 해당 view layer로 forwarding이나 redirecting을 하기위한 메소드
	 * @param viewName
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ServletException 
	 */
	public void viewProcess(String viewName, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		if (viewName!=null) {
			boolean isRedirect = viewName.startsWith("redirect:");
			if (isRedirect) {
				viewName = viewName.substring("redirect:".length());
				response.sendRedirect(request.getContextPath() + viewName);
			} else {
				RequestDispatcher rd = request.getRequestDispatcher(prefix + viewName + suffix); ///WEB-INF/views/board/boardList.jsp
				rd.forward(request, response);
			}
		} else {
			if (!response.isCommitted()) {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "커맨드 핸들러에서 뷰가 선택되지 않았습니다.");
			}
		}
	}
}
