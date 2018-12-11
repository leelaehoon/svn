package kr.or.ddit.buyer.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.or.ddit.buyer.service.BuyerServiceImpl;
import kr.or.ddit.buyer.service.IBuyerService;
import kr.or.ddit.mvc.annotation.CommandHandler;
import kr.or.ddit.mvc.annotation.URIMapping;
import kr.or.ddit.vo.BuyerVO;
import kr.or.ddit.vo.PagingInfoVO;
import kr.or.ddit.web.calculate.Mime;

@CommandHandler
public class BuyerListController {
	IBuyerService service = new BuyerServiceImpl();

	@URIMapping("/buyer/buyerList.do")
	public String process(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		// 1.요청과의 매핑 설정
		// 2.요청분석 (주소, 파라미터, 메소드, 헤더들...)
		String searchWord = req.getParameter("searchWord");
		String searchType = req.getParameter("searchType");
		int currentPage = 1;
		String page = req.getParameter("page");
		if (StringUtils.isNumeric(page)) {
			currentPage = Integer.parseInt(page);
		}
		PagingInfoVO<BuyerVO> pagingVO = new PagingInfoVO(5, 2);
		pagingVO.setCurrentPage(currentPage);
		pagingVO.setSearchWord(searchWord);
		pagingVO.setSearchType(searchType);
		
		// 3.B.L.L와의 의존관계 형성
		// 4.로직 선택
		// 5.컨텐츠(Model) 확보
		long totalRecord = service.retrieveBuyerCount(pagingVO);
		pagingVO.setTotalRecord(totalRecord);
		List<BuyerVO> buyerList = service.retrieveBuyerList(pagingVO);
		pagingVO.setDataList(buyerList);
		// 6.V.L를 선택
		String accept = req.getHeader("accept");
		String view = null;
		// 7.Scope를 통해 Model공유
		if (StringUtils.containsIgnoreCase(accept, "json")) {
			resp.setContentType(Mime.JSON.getContentType());
			ObjectMapper mapper = new ObjectMapper();
			mapper.writeValue(resp.getWriter(), pagingVO);
		} else {
			req.setAttribute("pagingVO", pagingVO);
			view = "buyer/buyerList";
		}
		return view;
	}
}
