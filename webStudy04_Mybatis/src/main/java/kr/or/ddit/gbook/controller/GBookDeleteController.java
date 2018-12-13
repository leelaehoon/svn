package kr.or.ddit.gbook.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.or.ddit.ServiceResult;
import kr.or.ddit.gbook.service.GBookServiceImpl;
import kr.or.ddit.gbook.service.IGBookService;
import kr.or.ddit.mvc.annotation.CommandHandler;
import kr.or.ddit.mvc.annotation.URIMapping;
import kr.or.ddit.mvc.annotation.URIMapping.HttpMethod;
import kr.or.ddit.vo.GBookVO;
import kr.or.ddit.web.calculate.Mime;

@CommandHandler
public class GBookDeleteController {
	IGBookService service = new GBookServiceImpl();
	
	@URIMapping(value="/gbook/gbookDelete.do", method=HttpMethod.POST)
	public String deleteGBook(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String gb_no = req.getParameter("gb_no");
		String gb_pass = req.getParameter("gb_pass");
		
		if (!StringUtils.isNumeric(gb_no) || StringUtils.isBlank(gb_pass)) {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return null;
		}
		
		ServiceResult result = service.removeGBook(new GBookVO(Long.parseLong(gb_no), gb_pass));
		String view = null;
		String message = null;
		switch (result) {
		case INVALIDPASSWORD:
			message = "비밀번호가 틀립니다";
			break;
		case FAILED:
			message = "서버오류!";
			break;
		default:
			view = "redirect:/gbook/gbookList.do";
			break;
		}
		
		if (view == null) {
			resp.setContentType(Mime.JSON.getContentType());
			ObjectMapper mapper = new ObjectMapper();
			mapper.writeValue(resp.getWriter(), message);
		}
		
		return view;
	}
}
