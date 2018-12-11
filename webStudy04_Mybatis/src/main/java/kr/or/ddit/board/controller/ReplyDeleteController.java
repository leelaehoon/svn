package kr.or.ddit.board.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.or.ddit.ServiceResult;
import kr.or.ddit.board.service.IReplyService;
import kr.or.ddit.board.service.ReplyServiceImpl;
import kr.or.ddit.mvc.annotation.CommandHandler;
import kr.or.ddit.mvc.annotation.URIMapping;
import kr.or.ddit.mvc.annotation.URIMapping.HttpMethod;
import kr.or.ddit.vo.ReplyVO;
import kr.or.ddit.web.calculate.Mime;

@CommandHandler
public class ReplyDeleteController {
	IReplyService service = new ReplyServiceImpl();

	@URIMapping(value="/reply/replyDelete.do", method=HttpMethod.POST)
	public String process(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		ReplyVO reply = new ReplyVO();
		try {
			BeanUtils.populate(reply, req.getParameterMap());
		} catch (IllegalAccessException | InvocationTargetException e) {
			throw new RuntimeException(e);
		}
		
		if (reply.getBo_no() < 0 || reply.getRep_no() < 0) {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return null;
		}
		
		// 자바빈의 setter를 통해 객체의 상태를 설정 -JavaBean Pattern -> Builder Pattern
		
		Map<String, String> errors = new HashMap<>();
		ServiceResult result = service.removeReply(reply);
		switch (result) {
		case INVALIDPASSWORD:
			errors.put("error", "비밀번호 오류");
			break;
		case FAILED:
			errors.put("error", "서버 오류");
			break;
		default:
			return "redirect:/reply/replyList.do?bo_no=" + reply.getBo_no();
		}
		resp.setContentType(Mime.JSON.getContentType());
		ObjectMapper mapper = new ObjectMapper();
		mapper.writeValue(resp.getWriter(), errors);
		
		return null;
	}

}
