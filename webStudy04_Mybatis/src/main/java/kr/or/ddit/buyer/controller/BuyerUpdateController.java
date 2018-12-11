package kr.or.ddit.buyer.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;

import kr.or.ddit.ServiceResult;
import kr.or.ddit.buyer.service.BuyerServiceImpl;
import kr.or.ddit.buyer.service.IBuyerService;
import kr.or.ddit.mvc.annotation.CommandHandler;
import kr.or.ddit.mvc.annotation.URIMapping;
import kr.or.ddit.mvc.annotation.URIMapping.HttpMethod;
import kr.or.ddit.prod.dao.IOtherDAO;
import kr.or.ddit.prod.dao.OtherDAOImpl;
import kr.or.ddit.vo.BuyerVO;

@CommandHandler
public class BuyerUpdateController {
	IOtherDAO otherDAO = new OtherDAOImpl();
	IBuyerService service = new BuyerServiceImpl();
	
	@URIMapping("/buyer/buyerUpdate.do")
	public String getProcess(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		Map<String, Map<String, String>> lprodList = otherDAO.selectLprodList();
		req.setAttribute("lprodList", lprodList);
		BuyerVO buyer = new BuyerVO();
		String buyer_id = req.getParameter("what");
		if (StringUtils.isBlank(buyer_id)) {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return null;
		}
		buyer = service.retrieveBuyer(buyer_id);
		req.setAttribute("buyer", buyer);
		
		return "buyer/buyerForm";
	}
	
	@URIMapping(value="/buyer/buyerUpdate.do", method=HttpMethod.POST)
	public String postProcess(HttpServletRequest req, HttpServletResponse resp) {
		Map<String, Map<String, String>> lprodList = otherDAO.selectLprodList();
		req.setAttribute("lprodList", lprodList);
		BuyerVO buyer = new BuyerVO();
		req.setAttribute("buyer", buyer);
		try {
			BeanUtils.populate(buyer, req.getParameterMap());
		} catch (IllegalAccessException | InvocationTargetException e) {
			throw new RuntimeException(e);
		}
		String buyer_id = req.getParameter("what");
		buyer.setBuyer_id(buyer_id);
		
		
		String goPage = null;
		String message = null;	
		Map<String, String> errors = new LinkedHashMap<>();
		req.setAttribute("errors", errors);
		boolean valid = validate(buyer, errors);
		
		if (valid) {
			ServiceResult result = service.modifyBuyer(buyer);
			switch (result) {
			case FAILED : 
				goPage = "buyer/buyerForm";
				message = "서버 오류로 인한 실패!";
				break;
			case SUCCESS : 
				goPage = "redirect:/buyer/buyerView.do?what=" + buyer.getBuyer_id();
				break;
			}
			req.setAttribute("message", message);
		} else {
			goPage = "buyer/buyerForm";
		}
		return goPage;
	}

	private boolean validate(BuyerVO buyer, Map<String, String> errors) {
		boolean valid = true;
		if (StringUtils.isBlank(buyer.getBuyer_name())) {
			valid = false;
			errors.put("buyer_name", "거래처명 누락");
		}
		if (StringUtils.isBlank(buyer.getBuyer_lgu())) {
			valid = false;
			errors.put("buyer_lgu", "분류코드 누락");
		}
		if (StringUtils.isBlank(buyer.getBuyer_comtel())) {
			valid = false;
			errors.put("buyer_comtel", "연락처 누락");
		}
		if (StringUtils.isBlank(buyer.getBuyer_fax())) {
			valid = false;
			errors.put("buyer_fax", "팩스번호 누락");
		}
		if (StringUtils.isBlank(buyer.getBuyer_mail())) {
			valid = false;
			errors.put("buyer_mail", "거래처메일 누락");
		}
		return valid;
	}
	
}
