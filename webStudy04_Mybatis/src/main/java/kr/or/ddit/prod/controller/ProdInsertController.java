package kr.or.ddit.prod.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;


import kr.or.ddit.ServiceResult;
import kr.or.ddit.filter.wrapper.FileUploadRequestWrapper;
import kr.or.ddit.mvc.annotation.CommandHandler;
import kr.or.ddit.mvc.annotation.URIMapping;
import kr.or.ddit.mvc.annotation.URIMapping.HttpMethod;
import kr.or.ddit.prod.dao.IOtherDAO;
import kr.or.ddit.prod.dao.OtherDAOImpl;
import kr.or.ddit.prod.service.IProdService;
import kr.or.ddit.prod.service.ProdServiceImpl;
import kr.or.ddit.vo.ProdVO;

@CommandHandler
public class ProdInsertController {
	IOtherDAO otherDAO = new OtherDAOImpl();
	IProdService service = new ProdServiceImpl();
	
	@URIMapping("/prod/prodInsert.do")
	public String getProcess(HttpServletRequest req, HttpServletResponse resp) {
		Map<String, Map<String, String>> lprodList = otherDAO.selectLprodList();
		req.setAttribute("lprodList", lprodList);
		return "prod/prodForm";
	}
	
	@URIMapping(value="/prod/prodInsert.do", method=HttpMethod.POST)
	public String postProcess(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		Map<String, Map<String, String>> lprodList = otherDAO.selectLprodList();
		req.setAttribute("lprodList", lprodList);
		ProdVO prod = new ProdVO();
		req.setAttribute("prod", prod);
		try {
			BeanUtils.populate(prod, req.getParameterMap());
		} catch (IllegalAccessException | InvocationTargetException e) {
			throw new RuntimeException(e);
		}
		
		String view = null;
		String message = null;
		Map<String, String> errors = new LinkedHashMap<>();
		req.setAttribute("errors", errors);
		boolean valid = validate(prod, errors);
		if (valid) {
			if (req instanceof FileUploadRequestWrapper) {
				String prodImagesUrl = "/prodImages";
				String prodImagesPath = req.getServletContext().getRealPath(prodImagesUrl);
				File prodImagesFolder = new File(prodImagesPath);
				FileItem fileItem = ((FileUploadRequestWrapper) req).getFileItem("prod_image");
				if (fileItem!=null) {
					String saveName = UUID.randomUUID().toString();
					File saveFile = new File(prodImagesFolder, saveName);
					try (
					 	InputStream in = fileItem.getInputStream();
					) {
						FileUtils.copyInputStreamToFile(in, saveFile);
						prod.setProd_img(saveName);
					}
				}
			}
			
			ServiceResult result = service.createProd(prod);
			if (ServiceResult.SUCCESS.equals(result)) {
				view = "redirect:/prod/prodView.do?what=" + prod.getProd_id();
			} else {
				req.setAttribute("message", "서버오류!!");
				view = "prod/prodForm";
			}
		} else {
			view = "prod/prodForm";
		}
		return view;
	}

	private boolean validate(ProdVO prod, Map<String, String> errors) {
		boolean valid = true;
		if (StringUtils.isBlank(prod.getProd_name())) {
			valid = false;
			errors.put("prod_name", "상품명 누락");
		}
		if (StringUtils.isBlank(prod.getProd_lgu())) {
			valid = false;
			errors.put("prod_lgu", "분류코드 누락");
		}
		if (StringUtils.isBlank(prod.getProd_buyer())) {
			valid = false;
			errors.put("prod_buyer", "거래처코드 누락");
		}
		if (prod.getProd_cost()==null || prod.getProd_cost() < 0) {
			valid = false;
			errors.put("prod_cost", "코스트 누락");
		}
		if (prod.getProd_price()==null || prod.getProd_price() < 0) {
			valid = false;
			errors.put("prod_price", "프라이스 누락");
		}
		if (prod.getProd_sale()==null || prod.getProd_sale() < 0) {
			valid = false;
			errors.put("prod_sale", "세일 누락");
		}
		if (StringUtils.isBlank(prod.getProd_outline())) {
			valid = false;
			errors.put("prod_outline", "상품개요 누락");
		}
		if (prod.getProd_totalstock()==null || prod.getProd_totalstock() < 0) {
			valid = false;
			errors.put("prod_totalstock", "토탈스톡 누락");
		}
		if (prod.getProd_properstock()==null || prod.getProd_properstock() < 0) {
			valid = false;
			errors.put("prod_properstock", "프로퍼스톡 누락");
		}
		if (StringUtils.isNotBlank(prod.getProd_insdate())) {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			// formatting : 특정 타입의 데이터를 일정 형식의 문자열로 변환.
			// parsing : 일정한 형식의 문자열을 특정 타입의 데이터로 변환.
			try {
				formatter.parse(prod.getProd_insdate());
			} catch (ParseException e) {
				valid = false;
				errors.put("mem_bir", "날짜 형식 확인");
			}
		}
		return valid;
	}
}
