package kr.or.ddit.member.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;

import kr.or.ddit.CommonException;
import kr.or.ddit.ServiceResult;
import kr.or.ddit.filter.wrapper.FileUploadRequestWrapper;
import kr.or.ddit.member.service.IMemberService;
import kr.or.ddit.member.service.MemberServiceImpl;
import kr.or.ddit.mvc.annotation.CommandHandler;
import kr.or.ddit.mvc.annotation.URIMapping;
import kr.or.ddit.mvc.annotation.URIMapping.HttpMethod;
import kr.or.ddit.validator.GeneralValidator;
import kr.or.ddit.validator.InsertGroup;
import kr.or.ddit.vo.MemberVO;

@CommandHandler
public class MemberInsertController {
	IMemberService service = new MemberServiceImpl();
	
	@URIMapping("/member/memberInsert.do")
	public String getProcess(HttpServletRequest req, HttpServletResponse resp) {
		return "member/memberForm";
	}
	
	@URIMapping(value="/member/memberInsert.do", method=HttpMethod.POST)
	public String postProcess(HttpServletRequest req, HttpServletResponse resp) {
		MemberVO member = new MemberVO(); // Command Object
		req.setAttribute("member", member);
//		member.setMem_id(req.getParameter("mem_id"));
		try {
			BeanUtils.populate(member, req.getParameterMap());
		} catch (IllegalAccessException | InvocationTargetException e) {
			throw new CommonException(e);
		}
		
		String goPage = null;
		String message = null;	
		Map<String, List<CharSequence>> errors = new LinkedHashMap<>();
		req.setAttribute("errors", errors);
		GeneralValidator validator = new GeneralValidator();
		boolean valid = validator.validate(member, errors, InsertGroup.class);
		if (valid) {
			if (req instanceof FileUploadRequestWrapper) {
				FileItem fileItem = ((FileUploadRequestWrapper) req).getFileItem("mem_image");
				if (fileItem!=null) {
					member.setMem_img(fileItem.get());
				}
			}
			ServiceResult result = service.registMember(member);
			switch (result) {
			case PKDUPLICATED :  
				goPage = "member/memberForm";
				message = "아이디가 중복됩니다!";
				break;
			case FAILED : 
				goPage = "member/memberForm";
				message = "서버 오류로 인한 실패!";
				break;
			case SUCCESS : 
				goPage = "redirect:/member/memberList.do";
				break;
			}
			req.setAttribute("message", message);
		} else {
			goPage = "member/memberForm";
		}
		return goPage;
	}
}