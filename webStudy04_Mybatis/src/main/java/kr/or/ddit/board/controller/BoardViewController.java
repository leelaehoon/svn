package kr.or.ddit.board.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.or.ddit.board.dao.BoardDAOImpl;
import kr.or.ddit.board.dao.IBoardDAO;
import kr.or.ddit.board.service.BoardServiceImpl;
import kr.or.ddit.board.service.IBoardService;
import kr.or.ddit.board.service.IReplyService;
import kr.or.ddit.board.service.ReplyServiceImpl;
import kr.or.ddit.mvc.annotation.CommandHandler;
import kr.or.ddit.mvc.annotation.URIMapping;
import kr.or.ddit.mvc.annotation.URIMapping.HttpMethod;
import kr.or.ddit.utils.CookieUtil;
import kr.or.ddit.utils.CookieUtil.TextType;
import kr.or.ddit.vo.BoardVO;
import kr.or.ddit.vo.PagingInfoVO;
import kr.or.ddit.vo.ReplyVO;

@CommandHandler
public class BoardViewController {
	IBoardService service = new BoardServiceImpl();
	IBoardDAO boardDAO = new BoardDAOImpl();
	
	@URIMapping("/board/boardView.do")
	public String process(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		String what = req.getParameter("what");
		if (!StringUtils.isNumeric(what)) {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return null;
		}
		long bo_no = Long.parseLong(what);
		BoardVO board = service.retrieveBoard(bo_no);
		req.setAttribute("board", board);
		
		return "board/boardView";
	}
	
	@URIMapping("/board/recommand.do")
	public String recommand(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String bo_no = req.getParameter("bo_no");
		
		if (!StringUtils.isNumeric(bo_no)) {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return null;
		}
		
		String path = req.getContextPath() + "/board/boardView.do?what=" + bo_no;
		System.out.println(path);
		Cookie recommandCookie = CookieUtil.createCookie("recommandCookie", "already recommanded", path, TextType.PATH, 60*60*24*365);
		resp.addCookie(recommandCookie);
		boardDAO.incrementRcmd(Long.parseLong(bo_no));
		resp.sendError(HttpServletResponse.SC_ACCEPTED);
		return null;
	}
}