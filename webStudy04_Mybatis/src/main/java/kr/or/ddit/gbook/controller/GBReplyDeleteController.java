package kr.or.ddit.gbook.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.or.ddit.ServiceResult;
import kr.or.ddit.gbook.service.GBReplyServiceImpl;
import kr.or.ddit.gbook.service.IGBReplyService;
import kr.or.ddit.mvc.annotation.CommandHandler;
import kr.or.ddit.mvc.annotation.URIMapping;
import kr.or.ddit.mvc.annotation.URIMapping.HttpMethod;
import kr.or.ddit.vo.GBReplyVO;
import kr.or.ddit.web.calculate.Mime;

@CommandHandler
public class GBReplyDeleteController {
	IGBReplyService service = new GBReplyServiceImpl();
	
	@URIMapping(value="/gbreply/gbreplyDelete.do", method=HttpMethod.POST)
	public String gbreplyDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String gbr_no = req.getParameter("gbr_no");
		String gbr_pass = req.getParameter("gbr_pass");
		
		if (!StringUtils.isNumeric(gbr_no) || StringUtils.isBlank(gbr_pass)) {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return null;
		}
		
		String view = null;
		String message = null;
		ServiceResult result = service.removeGBReply(new GBReplyVO(Long.parseLong(gbr_no), gbr_pass));
		
		switch (result) {
		case INVALIDPASSWORD:
			message = "비밀번호가 일치하지 않습니다.";
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
