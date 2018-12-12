package kr.or.ddit.gbook.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.ddit.mvc.annotation.CommandHandler;
import kr.or.ddit.mvc.annotation.URIMapping;

@CommandHandler
public class GBookListController {
	
	@URIMapping("/gbook/gbookList.do")
	public String goGBookList(HttpServletRequest request, HttpServletResponse response) {
		return "guestbook/guestbookList";
	}
}
