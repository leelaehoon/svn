package kr.or.ddit.web;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.ddit.web.calculate.Mime;
import kr.or.ddit.web.calculate.Operator;

public class CalculateServlet extends HttpServlet {
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		ServletContext application = getServletContext();
		String contentFolder = application.getInitParameter("contentFolder");
		File folder = new File(contentFolder);
		application.setAttribute("contentFolder", folder);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 파라미터 확보(입력태그의 name 속성값으로 이름 결정)
		String leftOpStr = req.getParameter("leftOp");
		String rightOpStr = req.getParameter("rightOp");
		String operatorStr = req.getParameter("operator");
		
		// 검증
		int leftOp = 0;
		int rightOp = 0;
		Operator operator = null;

		try {
			leftOp = Integer.parseInt(leftOpStr);
			rightOp = Integer.parseInt(rightOpStr);
			operator = Operator.valueOf(operatorStr.toUpperCase());
		} catch (Exception e) {
			// 불통 400 에러발생
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}

		// 통과
		//   연산자에 따라 연산 수행
		//   일반 텍스트의 형태로 연산 결과를 제공
		// 연산결과 : 2 * 3 = 6
		String pattern = "%d %s %d = %d";
		String result = String.format(pattern, leftOp, operator.getSign(), rightOp, operator.operate(leftOp, rightOp));
		String accept = req.getHeader("Accept");
		String subType = accept.substring(accept.indexOf("/") + 1, accept.indexOf(","));
		Mime mime = null;
		try {
			mime = Mime.valueOf(subType.toUpperCase());
		} catch (Exception e) {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		result = mime.dataMake(result);
		resp.setContentType(mime.getContentType());
		
		PrintWriter out = resp.getWriter();
		out.println(result);
		out.close();
				
	}
}
