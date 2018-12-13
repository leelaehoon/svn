package kr.or.ddit.gbook.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import kr.or.ddit.ServiceResult;
import kr.or.ddit.gbook.service.GBReplyServiceImpl;
import kr.or.ddit.gbook.service.IGBReplyService;
import kr.or.ddit.mvc.annotation.CommandHandler;
import kr.or.ddit.mvc.annotation.URIMapping;
import kr.or.ddit.mvc.annotation.URIMapping.HttpMethod;
import kr.or.ddit.validator.GeneralValidator;
import kr.or.ddit.validator.InsertGroup;
import kr.or.ddit.validator.UpdateGroup;
import kr.or.ddit.vo.GBReplyVO;
import kr.or.ddit.web.calculate.Mime;

@CommandHandler
public class GBReplyInsertController {
	IGBReplyService service = new GBReplyServiceImpl();
	
	@URIMapping(value="/gbreply/gbreplyInsert.do", method=HttpMethod.POST)
	public String gbreplyInsert(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		GBReplyVO gbreply = new GBReplyVO();
		try {
			BeanUtils.populate(gbreply, req.getParameterMap());
		} catch (IllegalAccessException | InvocationTargetException e) {
			throw new RuntimeException(e);
		}
		gbreply.setGbr_ip(req.getRemoteAddr());
		
		GeneralValidator validator = new GeneralValidator();
		Map<String, List<CharSequence>> errors = new LinkedHashMap<>();
		String view = null;
		String message = null;
		boolean valid = validator.validate(gbreply, errors, InsertGroup.class);
		
		if (valid) {
			ServiceResult result = service.createGBReply(gbreply);
			if (ServiceResult.SUCCESS.equals(result)) {
				view = "redirect:/gbook/gbookList.do";
			} else {
				message = "서버에러!";
			}
		} else {
			message = "검증 실패!";
		}
		
		if (view == null) {
			resp.setContentType(Mime.JSON.getContentType());
			ObjectMapper mapper = new ObjectMapper();
			mapper.writeValue(resp.getWriter(), errors);
		}
		
		return view;
	}
}









