package kr.or.ddit.board.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.or.ddit.ServiceResult;
import kr.or.ddit.board.dao.IReplyDAO;
import kr.or.ddit.board.dao.ReplyDAOImpl;
import kr.or.ddit.board.service.IReplyService;
import kr.or.ddit.board.service.ReplyServiceImpl;
import kr.or.ddit.mvc.ICommandHandler;
import kr.or.ddit.vo.ReplyVO;
import kr.or.ddit.web.calculate.Mime;

public class ReplyInsertController implements ICommandHandler {

	@Override
	public String process(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		ReplyVO reply = new ReplyVO(); // COMMAND OBJECT
		try {
			BeanUtils.populate(reply, req.getParameterMap());
		} catch (IllegalAccessException | InvocationTargetException e) {
			throw new RuntimeException(e);
		}
		
		Map<String, String> errors = new HashMap<>();
		boolean valid = validate(reply, errors);
		if (valid) {
			IReplyService service = new ReplyServiceImpl();
			ServiceResult result = service.createReply(reply);
			
			if (ServiceResult.SUCCESS.equals(result)) {
				return "redirect:/reply/replyList.do?bo_no=" + reply.getBo_no();
			} else {
				resp.setContentType(Mime.JSON.getContentType());
				ObjectMapper mapper = new ObjectMapper();
				errors.put("error", "서버 오류");
				mapper.writeValue(resp.getWriter(), errors);
			}
		} else {
			resp.setContentType(Mime.JSON.getContentType());
			ObjectMapper mapper = new ObjectMapper();
			errors.put("error", "검증 실패");
			mapper.writeValue(resp.getWriter(), errors);
		}
		
		return null;
	}

	private boolean validate(ReplyVO reply, Map<String, String> errors) {
		boolean valid = true;
		if (StringUtils.isBlank(reply.getRep_writer())) {
			valid = false;
			errors.put("rep_writer", "작성자 누락");
		}
		if (StringUtils.isBlank(reply.getRep_pass())) {
			valid = false;
			errors.put("rep_pass", "비밀번호 누락");
		}
		if (StringUtils.isNotBlank(reply.getRep_content()) && reply.getRep_content().length()>100 ) {
			valid = false;
			errors.put("rep_content", "내용의 길이는 100글자 미만");
		}
		return valid;
	}

}
