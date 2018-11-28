package kr.or.ddit.prod.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.or.ddit.member.service.IMemberService;
import kr.or.ddit.member.service.MemberServiceImpl;
import kr.or.ddit.mvc.ICommandHandler;
import kr.or.ddit.prod.dao.IOtherDAO;
import kr.or.ddit.prod.dao.OtherDAOImpl;
import kr.or.ddit.prod.service.IProdService;
import kr.or.ddit.prod.service.ProdServiceImpl;
import kr.or.ddit.vo.BuyerVO;
import kr.or.ddit.vo.PagingInfoVO;
import kr.or.ddit.vo.ProdVO;

public class ProdListController implements ICommandHandler {

	@Override
	public String process(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		ProdVO searchVO = new ProdVO();
		searchVO.setProd_lgu(req.getParameter("prod_lgu"));
		searchVO.setProd_buyer(req.getParameter("prod_buyer"));
		searchVO.setProd_name(req.getParameter("prod_name"));
		
		int currentPage = 1;
		String page = req.getParameter("page");
		if (StringUtils.isNumeric(page)) {
			currentPage = Integer.parseInt(page);
		}
		PagingInfoVO pagingVO = new PagingInfoVO(7, 4);
		pagingVO.setCurrentPage(currentPage);
		pagingVO.setSearchVO(searchVO);
		
		IProdService service = new ProdServiceImpl();
		IOtherDAO otherDAO = new OtherDAOImpl();
		Map<String, Map<String, String>> lprodList = otherDAO.selectLprodList();
		List<BuyerVO> buyerList = otherDAO.selectBuyerList(null);
		req.setAttribute("lprodList", lprodList);
		req.setAttribute("buyerList", buyerList);
		
		
		long totalRecord = service.retrieveProdCount(pagingVO);
		pagingVO.setTotalRecord(totalRecord);
		List<ProdVO> prodList = service.retrieveProdList(pagingVO);
		pagingVO.setDataList(prodList);
		
		// Accept 헤더를 통해 동기/비동기 요청 여부를 확인하고
		String accept = req.getHeader("Accept");
		String view = null;
		if (StringUtils.containsIgnoreCase(accept, "json")) {
			// JSON
			resp.setContentType("application/json;charset=UTF-8");
			ObjectMapper mapper = new ObjectMapper();
			mapper.writeValue(resp.getWriter(), pagingVO);
		} else {
			// HTML
			req.setAttribute("pagingVO", pagingVO);
			view = "prod/prodList";
		}
		
		// 동기요청이라면 View Layer를 통해 응답이 전송
		// 비동기 요청이라면 PagingVO를 marshalling한 JSON 응답이 전송되도록
		
		return view;
	}

}
