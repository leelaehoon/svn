package kr.or.ddit.prod.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.or.ddit.mvc.annotation.CommandHandler;
import kr.or.ddit.mvc.annotation.URIMapping;
import kr.or.ddit.prod.dao.IOtherDAO;
import kr.or.ddit.prod.dao.OtherDAOImpl;
import kr.or.ddit.vo.BuyerVO;

@CommandHandler
public class AjaxBuyerListController {
	IOtherDAO otherDAO = new OtherDAOImpl();
	
	@URIMapping("/prod/getBuyerList.do")
	public String process(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		String buyer_lgu = req.getParameter("prod_lgu");
		List<BuyerVO> buyerList = otherDAO.selectBuyerList(buyer_lgu);
		ObjectMapper mapper = new ObjectMapper();
		resp.setContentType("application/json;charset=UTF-8");
		mapper.writeValue(resp.getWriter(), buyerList);
		return null;
	}

}
