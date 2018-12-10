package kr.or.ddit.board.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.io.FileUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.or.ddit.filter.wrapper.FileUploadRequestWrapper;
import kr.or.ddit.mvc.ICommandHandler;
import kr.or.ddit.web.calculate.Mime;

public class UploadImageController implements ICommandHandler {

	@Override
	public String process(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		String boardImagesUrl = "/boardImages";
		String boardImagesPath = req.getServletContext().getRealPath(boardImagesUrl);
		File saveFolder = new File(boardImagesPath);
		if (!saveFolder.exists()) saveFolder.mkdirs();
		FileItem fileItem = ((FileUploadRequestWrapper) req).getFileItem("upload");
		Map<String, Object> result = new LinkedHashMap<>();
		int uploaded = 0;
		String url = null;
		Map<String, Object> error = new LinkedHashMap<>();
		if (fileItem != null) {
			uploaded = 1;
			String saveName = UUID.randomUUID().toString();
			File saveFile = new File(saveFolder, saveName);
			try (
				InputStream in = fileItem.getInputStream();
			) {
				FileUtils.copyInputStreamToFile(in, saveFile);
			}
			error.put("number", HttpServletResponse.SC_CREATED);
			error.put("message", "성공");
			url = req.getContextPath() + "/boardImages/" + saveName;
		} else {
			error.put("number", HttpServletResponse.SC_NO_CONTENT);
			error.put("message", "몰라");
		}
		
		result.put("fileName", fileItem.getName());
		result.put("uploaded", uploaded);
		result.put("error", error);
		result.put("url", url);
		resp.setContentType(Mime.JSON.getContentType());
		ObjectMapper mapper = new ObjectMapper();
		mapper.writeValue(resp.getWriter(), result);
		return null;
	}

}
