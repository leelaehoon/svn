package kr.or.ddit.member.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import kr.or.ddit.ServiceResult;
import kr.or.ddit.member.service.IMemberService;
import kr.or.ddit.member.service.MemberServiceImpl;
import kr.or.ddit.mvc.annotation.CommandHandler;
import kr.or.ddit.mvc.annotation.URIMapping;
import kr.or.ddit.mvc.annotation.URIMapping.HttpMethod;
import kr.or.ddit.vo.MemberVO;

@CommandHandler
public class MemberDeleteController {
	IMemberService service = new MemberServiceImpl();
	
	@URIMapping(value="/member/memberDelete.do", method=HttpMethod.POST)
	public String process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String mem_id = req.getParameter("mem_id");
		String mem_pass = req.getParameter("mem_pass");
		if (StringUtils.isBlank(mem_id) || StringUtils.isBlank(mem_pass)) {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return null;
		}
		ServiceResult result = service.removeMember(new MemberVO(mem_id, mem_pass));
		String view = null;
		String message = null;
		switch (result) {
		case INVALIDPASSWORD:
			message = "비번 오류";
//			view = "/member/memberView.do?who=" + mem_id;
			view = "redirect:/member/mypage.do";
			break;
		case FAILED:
			message = "서버 오류";
//			view = "/member/memberView.do?who=" + mem_id;
			view = "redirect:/member/mypage.do";
			break;
		default:
//			view = "/member/memberList.do";
			view = "redirect:/common/message.jsp";
			message = "탈퇴약관 : 일주일내에 절대 같은 아이디로 재가입 불가";
			req.getSession().setAttribute("goLink", "/");
			req.getSession().setAttribute("isRemoved", new Boolean(true));
//			req.getSession().invalidate();
			break;
		}
		
		req.getSession().setAttribute("message", message);
		
		return view;
	}
}
