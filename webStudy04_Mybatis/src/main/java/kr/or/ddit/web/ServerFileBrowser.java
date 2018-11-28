package kr.or.ddit.web;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import kr.or.ddit.web.model2.FileListGenerator;

@WebServlet("/fileBrowser.do")
public class ServerFileBrowser extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 1.요청 분석
		req.setCharacterEncoding("UTF-8");
		ServletContext context = req.getServletContext();
		String parentPath = req.getParameter("parentPath");
		String fileName = req.getParameter("fileName");
		String selectBtn = req.getParameter("selectBtn");
		
		boolean flag = true;
		if (StringUtils.isBlank(parentPath)) {
			flag = false;
		}
		if (StringUtils.isBlank(fileName)) {
			flag = false;
		}
		// 2.의존 객체 생성
		FileListGenerator generator = new FileListGenerator();
		// 3.로직 메서드를 호출
		if ("move".equals(selectBtn)) {
			generator.copyFile(context.getRealPath("/") + "/copy", parentPath, fileName);
			generator.deleteFile(parentPath, fileName);
		} else if ("copy".equals(selectBtn)) {
			generator.copyFile(context.getRealPath("/") + "/copy", parentPath, fileName);
		} else if ("delete".equals(selectBtn)) {
			generator.deleteFile(parentPath, fileName);
		}
		
		if (StringUtils.isNotBlank(selectBtn)) {
			fileName = "";
		}
		File[] fileList = null;
		if (flag) {
			fileList = generator.getFileList(parentPath, fileName);
		}
		// 4.데이터 공유
		req.setAttribute("fileList", fileList);
		// 5.뷰레이어를 선택
		String view = "/WEB-INF/views/fileBrowser.jsp";
		// 6.이동
		RequestDispatcher rd = req.getRequestDispatcher(view);
		rd.forward(req, resp);
	}
}