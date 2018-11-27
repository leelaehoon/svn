package kr.or.ddit.buyer.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import kr.or.ddit.buyer.service.BuyerServiceImpl;
import kr.or.ddit.buyer.service.IBuyerService;
import kr.or.ddit.mvc.ICommandHandler;
import kr.or.ddit.vo.BuyerVO;
import oracle.net.aso.b;

public class BuyerViewController implements ICommandHandler {

	@Override
	public String process(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		String buyer_id = req.getParameter("what");
		
		if (StringUtils.isBlank(buyer_id)) {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return null;
		}
		
		IBuyerService service = new BuyerServiceImpl();
		BuyerVO buyer = service.retrieveBuyer(buyer_id);
		req.setAttribute("buyer", buyer);
		
		return "buyer/buyerView";
	}
}
