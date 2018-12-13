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
import org.apache.commons.fileupload.FileItem;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.or.ddit.ServiceResult;
import kr.or.ddit.filter.wrapper.FileUploadRequestWrapper;
import kr.or.ddit.gbook.service.GBookServiceImpl;
import kr.or.ddit.gbook.service.IGBookService;
import kr.or.ddit.mvc.annotation.CommandHandler;
import kr.or.ddit.mvc.annotation.URIMapping;
import kr.or.ddit.mvc.annotation.URIMapping.HttpMethod;
import kr.or.ddit.validator.GeneralValidator;
import kr.or.ddit.validator.InsertGroup;
import kr.or.ddit.vo.GBookVO;
import kr.or.ddit.web.calculate.Mime;

@CommandHandler
public class GBookInsertController {
	IGBookService service = new GBookServiceImpl();
	
	@URIMapping(value="/gbook/gbookInsert.do", method=HttpMethod.POST)
	public String insertGBook(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		GBookVO gbook = new GBookVO();
		try {
			BeanUtils.populate(gbook, req.getParameterMap());
		} catch (IllegalAccessException | InvocationTargetException e) {
			throw new RuntimeException(e);
		}
		
		GeneralValidator validator = new GeneralValidator();
		Map<String, List<CharSequence>> errors = new LinkedHashMap<>();
		boolean valid = validator.validate(gbook, errors, InsertGroup.class);
		String view = null;
		String message = null;
		
		if (valid) {
			if (req instanceof FileUploadRequestWrapper) {
				FileItem fileItem = ((FileUploadRequestWrapper) req).getFileItem("gb_img");
				if (fileItem != null) {
					gbook.setGb_image(fileItem.get());
				}
			}
			ServiceResult result = service.createGBook(gbook);
			if (ServiceResult.SUCCESS.equals(result)) {
				view = "redirect:/gbook/gbookList.do";
			} else {
				message = "서버 오류!";
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










