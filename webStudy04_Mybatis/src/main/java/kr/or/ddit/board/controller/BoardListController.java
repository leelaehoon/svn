package kr.or.ddit.board.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.or.ddit.board.service.BoardServiceImpl;
import kr.or.ddit.board.service.IBoardService;
import kr.or.ddit.mvc.annotation.CommandHandler;
import kr.or.ddit.mvc.annotation.URIMapping;
import kr.or.ddit.vo.BoardVO;
import kr.or.ddit.vo.PagingInfoVO;
import kr.or.ddit.web.calculate.Mime;

@CommandHandler
public class BoardListController {
	IBoardService service = new BoardServiceImpl();
	
	@URIMapping("/board/boardList.do")
	public String process(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		String page = req.getParameter("page");
		String searchType = req.getParameter("searchType");
		String searchWord = req.getParameter("searchWord");
		long currentPage = 1;
		if (StringUtils.isNumeric(page)) {
			currentPage = Long.parseLong(page);
		}
		PagingInfoVO<BoardVO> pagingVO = new PagingInfoVO<>(20,10);
		pagingVO.setSearchType(searchType);
		pagingVO.setSearchWord(searchWord);
		
		long totalRecord = service.retrieveBoardCount(pagingVO);
		
		pagingVO.setTotalRecord(totalRecord);
		pagingVO.setCurrentPage(currentPage);
		
		List<BoardVO> boardList = service.retrieveBoardList(pagingVO);
		pagingVO.setDataList(boardList);

		String accpet = req.getHeader("accept");
		String view = null;
		if (StringUtils.containsIgnoreCase(accpet, "json")) {
			resp.setContentType(Mime.JSON.getContentType());
			ObjectMapper mapper = new ObjectMapper();
			mapper.writeValue(resp.getWriter(), pagingVO);
		} else {
			req.setAttribute("pagingVO", pagingVO);
			view = "board/boardList";
		}
		return view;
	}

}
