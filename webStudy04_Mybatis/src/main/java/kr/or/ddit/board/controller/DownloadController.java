package kr.or.ddit.board.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import kr.or.ddit.board.service.BoardServiceImpl;
import kr.or.ddit.board.service.IBoardService;
import kr.or.ddit.mvc.ICommandHandler;
import kr.or.ddit.vo.PdsVO;

public class DownloadController implements ICommandHandler {

	@Override
	public String process(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		String pds_noStr = req.getParameter("what");
		if (!StringUtils.isNumeric(pds_noStr)) {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return null;
		}
		IBoardService service = new BoardServiceImpl();
		PdsVO pds = service.downloadPds(Long.parseLong(pds_noStr));
		resp.setContentType("application/octet-stream");
		String agent = req.getHeader("User-Agent");
		String filename = pds.getPds_filename();
		if (StringUtils.containsIgnoreCase(agent, "msie") || StringUtils.containsIgnoreCase(agent, "trident")) {
			filename = URLEncoder.encode(filename, "UTF-8");
		} else {
			filename = new String(filename.getBytes("UTF-8"), "ISO-8859-1");
		}
		resp.setHeader("Content-Disposition", "attachment; filename = " + filename);
		resp.setHeader("Content-Length", String.valueOf(pds.getPds_size()));
		
		File saveFolder = new File("d:/boardFiles");
		File saveFile = new File(saveFolder, pds.getPds_savename());
		FileUtils.copyFile(saveFile, resp.getOutputStream());
		
		return null;
	}
}
