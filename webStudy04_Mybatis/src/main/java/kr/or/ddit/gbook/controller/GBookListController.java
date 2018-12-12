package kr.or.ddit.gbook.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.or.ddit.gbook.service.GBookServiceImpl;
import kr.or.ddit.gbook.service.IGBookService;
import kr.or.ddit.mvc.annotation.CommandHandler;
import kr.or.ddit.mvc.annotation.URIMapping;
import kr.or.ddit.vo.GBookVO;
import kr.or.ddit.vo.PagingInfoVO;
import kr.or.ddit.web.calculate.Mime;

@CommandHandler
public class GBookListController {
	IGBookService service = new GBookServiceImpl();
	
	@URIMapping("/gbook/gbookList.do")
	public String goGBookList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PagingInfoVO<GBookVO> pagingVO = new PagingInfoVO<>();
		String page = req.getParameter("page");
		pagingVO.setSearchType(req.getParameter("searchType"));
		pagingVO.setSearchWord(req.getParameter("searchWord"));
		long currentPage = 1;
		if (StringUtils.isNumeric(page)) {
			currentPage = Long.parseLong(page); 
		}
		long totalRecord = service.retrieveTotalCount(pagingVO);
		pagingVO.setTotalRecord(totalRecord);
		pagingVO.setCurrentPage(currentPage);
		List<GBookVO> gbookList = service.retrieveGBookList(pagingVO);
		pagingVO.setDataList(gbookList);
		String accept = req.getHeader("accept");
		String view = null;
		if (StringUtils.containsIgnoreCase(accept, "json")) {
			resp.setContentType(Mime.JSON.getContentType());
			ObjectMapper mapper = new ObjectMapper();
			mapper.writeValue(resp.getWriter(), pagingVO);
		} else {
			req.setAttribute("pagingVO", pagingVO);
			view = "guestbook/guestbookList";
		}
		
		return view;
	}
}
