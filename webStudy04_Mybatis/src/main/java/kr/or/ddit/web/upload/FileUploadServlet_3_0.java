package kr.or.ddit.web.upload;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.commons.lang3.StringUtils;

import kr.or.ddit.vo.FileVO;

//@WebServlet("/upload")
//@MultipartConfig
public class FileUploadServlet_3_0 extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
//		File saveFolder = new File("D:/saveFiles");
//		/saveFiles에 저장
		String saveUrl = "/saveFiles";
		String savePath = getServletContext().getRealPath(saveUrl);
		File saveFolder = new File(savePath);
		if (!saveFolder.exists()) {
			saveFolder.mkdirs();
		}
		
		String uploader = req.getParameter("uploader");
		
		Part uploadFile = req.getPart("uploadFile");
		String fileMime = uploadFile.getContentType();
		if (!StringUtils.startsWithIgnoreCase(fileMime, "image/")) {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		
//		Content-Disposition: form-data; name="uploadFile"; filename="Penguins.jpg"
		String header = uploadFile.getHeader("Content-Disposition");
		int idx = header.lastIndexOf("filename=") + "filename=".length();
		String originalFileName = header.substring(idx).replace("\"", "");
		String saveName = UUID.randomUUID().toString();
		
		// Middle tier에 파일의 body를 저장
		File saveFile = new File(saveFolder, saveName);
		byte[] buffer = new byte[1024];
		int pointer = -1;
		try(
			InputStream in = uploadFile.getInputStream();
			FileOutputStream fos = new FileOutputStream(saveFile);
		) {
			while ((pointer = in.read(buffer))!=-1) {
				fos.write(buffer, 0, pointer);
			}
		}
		
		// Database에 파일의 메타데이터를 저장
		long fileSize = uploadFile.getSize();
		
		System.out.printf("데이터베이스에 저장할 메타데이터 : 업로더(%s), 원본명(%s),\n 파일크기(%d), 파일종류(%s), 저장위치(%s), 저장URL(%s)", uploader, originalFileName, fileSize, fileMime, saveFile.getAbsolutePath(), saveUrl+"/"+saveName);
		FileVO fileVO = new FileVO();
		fileVO.setUploader(uploader);
		fileVO.setOriginalFileName(originalFileName);
		fileVO.setSaveFileName(saveName);
		// 웹리소스로 저장하는 경우에만 생성되는 메타데이터.
		fileVO.setSaveFileUrl(saveUrl+"/"+saveName);
		fileVO.setFileSize(fileSize);
		fileVO.setFileMime(fileMime);
		req.getSession().setAttribute("fileVO", fileVO);
		
		resp.sendRedirect(req.getContextPath()+"/13/fileForm.jsp");
	}
}
