package kr.or.ddit.board.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import kr.or.ddit.ServiceResult;
import kr.or.ddit.board.service.BoardServiceImpl;
import kr.or.ddit.board.service.IBoardService;
import kr.or.ddit.mvc.annotation.CommandHandler;
import kr.or.ddit.mvc.annotation.URIMapping;
import kr.or.ddit.mvc.annotation.URIMapping.HttpMethod;
import kr.or.ddit.vo.BoardVO;

@CommandHandler
public class BoardDeleteController {
	IBoardService service = new BoardServiceImpl();

	@URIMapping(value="/board/boardDelete.do", method=HttpMethod.POST)
	public String process(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		String bo_no = req.getParameter("bo_no");
		String bo_pass = req.getParameter("bo_pass");
		HttpSession session = req.getSession();
		
		if (!StringUtils.isNumeric(bo_no)) {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return null;
		}
		if (StringUtils.isBlank(bo_pass)) {
			session.setAttribute("message", "비밀번호를 입력해주세요!");
			return "redirect:/board/boardView.do?what=" + bo_no;
		}
		
		String view = null;

		ServiceResult result = service.removeBoard(new BoardVO(Long.parseLong(bo_no), bo_pass));
		switch (result) {
		case INVALIDPASSWORD:
			session.setAttribute("message", "비밀번호가 일치하지 않습니다!");
			view = "redirect:/board/boardView.do?what=" + bo_no;
			break;
		case FAILED:
			session.setAttribute("message", "서버 오류!");
			view = "redirect:/board/boardView.do?what=" + bo_no;
			break;
		default:
			view = "redirect:/board/boardList.do";
			break;
		}
		
		return view;
	}
}
