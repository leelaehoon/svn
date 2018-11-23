package kr.or.ddit.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.or.ddit.utils.CookieUtil;
import kr.or.ddit.utils.CookieUtil.TextType;

@WebServlet(value="/imageService")
public class ImageServiceServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// server-side 데이터 검증
		// 요청 파라미터 확보 : 파라미터명(image)
		String imageStr = req.getParameter("imgSel");
		if (imageStr==null || imageStr.trim().length() == 0) {
			resp.sendError(400);
			return;
		}

		ServletContext context = req.getServletContext();
		
		File folder = (File) context.getAttribute("contentFolder");	
		File imageFile = new File(folder, imageStr);
		if (!imageFile.exists()) {
			resp.sendError(404);
			return;
		}
		
		// 쿠키값 : A,B
		String imgCookieValue = new CookieUtil(req).getCookieValue("imageCookie");
		String[] cookieValues = null;
		ObjectMapper mapper = new ObjectMapper();
		if (StringUtils.isBlank(imgCookieValue)) {
			cookieValues = new String[] {imageFile.getName()};
		} else {
			String[] cValues = mapper.readValue(imgCookieValue, String[].class);
			cookieValues = new String[cValues.length+1];
			System.arraycopy(cValues, 0, cookieValues, 0, cValues.length);
			cookieValues[cookieValues.length-1] = imageFile.getName();
		}
//		imgCookieValue = Arrays.toString(cookieValues);
//		imgCookieValue = imgCookieValue.replaceAll("[\\[\\]\\s]", "");
		// marshalling
		imgCookieValue = mapper.writeValueAsString(cookieValues);
		
		System.out.println(imgCookieValue);
		
		Cookie imageCookie = CookieUtil.createCookie("imageCookie", imgCookieValue, req.getContextPath(), TextType.PATH, 60*60*24*3);
		resp.addCookie(imageCookie);
		
		resp.setContentType(context.getMimeType(imageStr));
		
		// 이미지 스트리밍....
		FileInputStream fis = new FileInputStream(imageFile);
		OutputStream out = resp.getOutputStream();
		byte[] buffer = new byte[1024];
		int pointer = -1;
		while ((pointer = fis.read(buffer)) != -1) { // -1 : EOF 문자 (End Of File)
			out.write(buffer, 0, pointer);
		}
		fis.close();
//		out.close();
	}
}
