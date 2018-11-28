package kr.or.ddit.web;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/song")
public class MusicLyricsServlet extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String song = req.getParameter("song");
		int status = 0;
		String message = null;
		if (song == null || song.trim().length() == 0) {
			status = HttpServletResponse.SC_BAD_REQUEST;
			message = "가사파일을 선택해주세요";
		}
		File folder = (File) getServletContext().getAttribute("contentFolder");	
		File songFile = new File(folder, song);
		if (!songFile.exists()) {
			status = HttpServletResponse.SC_NOT_FOUND;
			message = "해당 곡은 가사가 없습니다";
		}
		if (status != 0) {
			resp.sendError(status, message);
			return;
		}
		resp.setContentType("text/html;charset=UTF-8");
//		java 1.7 : try with resource 구문
		try (
//			Closable 객체를 할당
			FileInputStream fis = new FileInputStream(songFile);
			InputStreamReader isr = new InputStreamReader(fis, "MS949");
			BufferedReader br = new BufferedReader(isr);
			PrintWriter out = resp.getWriter(); 
		){
			String temp = null;
			StringBuffer html = new StringBuffer();
			while ((temp = br.readLine()) != null) {
				html.append("<p>" + temp + "</p>");
			}
			out.println(html);
		}
	}
	
	/*
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html;charset=UTF-8");

		ServletContext context = req.getServletContext();
		File folder = new File("d:/contents");
		String[] filenames = folder.list(new FilenameFilter() {

			@Override
			public boolean accept(File dir, String name) {
				String mime = context.getMimeType(name);
				return mime.startsWith("text/");
			}
		});

		InputStream is = this.getClass().getResourceAsStream("musicForm.html");
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(isr);
		StringBuffer html = new StringBuffer();
		String temp = null;

		while ((temp = br.readLine()) != null) {
			html.append(temp);
		}

		StringBuffer code = new StringBuffer();
		String pattern = "<option>%s</option>\n";

		for (int i = 0; i < filenames.length; i++) {
			code.append(String.format(pattern, filenames[i]));
		}

		int start = html.indexOf("@musicList");
		int end = start + "@musicList".length();
		String replaceText = code.toString();

		html.replace(start, end, replaceText);

		PrintWriter out = resp.getWriter();
		out.print(html.toString());
		out.close();
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html;charset=UTF-8");
		// server-side 데이터 검증
		// 요청 파라미터 확보 : 파라미터명(image)
		String musicName = req.getParameter("music");
		if (musicName == null || musicName.trim().length() == 0) {
			resp.sendError(400);
			return;
		}
		File folder = new File("d:/contents");
		File lyricsFile = new File(folder, musicName);
		if (!lyricsFile.exists()) {
			resp.sendError(404);
			return;
		}
		
		// 이미지 스트리밍....
		FileInputStream fis = new FileInputStream(lyricsFile);
		InputStreamReader isr = new InputStreamReader(fis, "EUC-KR");
		BufferedReader br = new BufferedReader(isr);
		String pattern = "%s %s";
		String temp = null;
		
		StringBuffer html = new StringBuffer();
		html.append("<html>");
		html.append("<head>");
		html.append("</head>");
		html.append("<body>");
		while ((temp = br.readLine()) != null) {
			String data = temp + "<br>";
			html.append(String.format(pattern, temp,"<br>"));
		}
		br.close();
		html.append("</body>");
		html.append("</html>");
		
		PrintWriter out = resp.getWriter();
		out.print(html);
		out.close();
	}
	*/
}
