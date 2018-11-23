package kr.or.ddit.web;

import java.io.IOException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import kr.or.ddit.vo.AlbaVO;

@WebServlet(value="/albamon", loadOnStartup=1)
public class SimpleFormProcessServlet extends HttpServlet {
	public Map<String, String> gradeMap;
	public Map<String, String> licenseMap;
	{
		gradeMap = new HashMap<>();
		licenseMap = new LinkedHashMap<>();
		
		gradeMap.put("G001", "고졸");
		gradeMap.put("G002", "대졸");
		gradeMap.put("G003", "석사");
		gradeMap.put("G004", "박사");
		
		licenseMap.put("L001", "정보처리산업기사");
		licenseMap.put("L002", "정보처리기사");
		licenseMap.put("L003", "정보보안산업기사");
		licenseMap.put("L004", "정보보안기사");
		licenseMap.put("L005", "SQLD");
		licenseMap.put("L006", "SQLP");
	}
	public Map<String, AlbaVO> albaTable = new LinkedHashMap<>(); 
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		getServletContext().setAttribute("gradeMap", gradeMap);
		getServletContext().setAttribute("licenseMap", licenseMap);
		getServletContext().setAttribute("albaTable", albaTable);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// VO 객체 생성, 파라미터 할당
		// VO를 대상으로 검증
		// (이름, 주소, 전화번호)
		// 1) 통과
		// code 생성 ("alba_001")
		// map.put(code, vo)
		// 이동(/05/albaList.jsp, 요청 처리 완료 후 이동)
		// 2) 불통
		// 이동(/01/simpleForm.jsp, 기존 입력데이터를 전달한채 이동)
		
//		req.setCharacterEncoding("UTF-8");
//		Enumeration<String> names = req.getParameterNames();
//		while (names.hasMoreElements()) {
//			String name = names.nextElement();
//			System.out.printf("%s : %s\n",name, Arrays.toString(req.getParameterValues(name)));
//		}
//		
		req.setCharacterEncoding("UTF-8");
		AlbaVO alba = new AlbaVO();
		alba.setName(req.getParameter("name"));
		alba.setTel(req.getParameter("tel"));
		alba.setAddress(req.getParameter("address"));
		alba.setGrade(req.getParameter("grade"));
		alba.setGender(req.getParameter("gender"));
		alba.setLicense(req.getParameterValues("license"));
		alba.setCareer(req.getParameter("career"));
		String ageStr = req.getParameter("age");
		
		if (ageStr!=null || ageStr.matches("\\d{2}")) {
			alba.setAge(Integer.parseInt(ageStr));
		}
		
		String name = alba.getName();
		String address = alba.getAddress();
		String tel = alba.getTel();
		
		req.setAttribute("alba", alba);
		
		boolean pass = true;
		Map<String, String> errors = new LinkedHashMap<>();
		req.setAttribute("errors", errors);
		if (StringUtils.isBlank(alba.getName())) {
			pass = false;
			errors.put("name", "이름 누락");
		}
		if (StringUtils.isBlank(alba.getAddress())) {
			pass = false;
			errors.put("tel", "연락처 누락");
		}
		if (StringUtils.isBlank(alba.getTel())) {
			pass = false;
			errors.put("address", "주소 누락");
		}
		
		boolean redirect = false;
		String goPage = null;
		if (pass) {
			alba.setCode(String.format("alba_%03d", albaTable.size()+1));
			albaTable.put(alba.getCode(), alba);
			goPage = "/05/albaList.jsp";
			redirect =true;
		} else {
			goPage = "/01/simpleForm.jsp";
		}
		
		if(redirect) {
			resp.sendRedirect(req.getContextPath() + goPage);
		} else {
			RequestDispatcher rd = req.getRequestDispatcher(goPage);
			rd.forward(req, resp);
		}
	}
}
























