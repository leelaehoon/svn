package kr.or.ddit.filter.wrapper;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.Part;

import org.apache.commons.lang3.StringUtils;

public class FileUploadRequestWrapper_3 extends HttpServletRequestWrapper {
	private Map<String, String[]> parameterMap;
	private Map<String, List<Part>> partMap;

	public FileUploadRequestWrapper_3(HttpServletRequest request) throws IOException, ServletException {
		super(request);
		
		parameterMap = new LinkedHashMap<>();
		partMap = new LinkedHashMap<>();
		
		parseRequest(request);
	}

	private void parseRequest(HttpServletRequest request) throws IOException, ServletException {
		request.setCharacterEncoding("UTF-8");
		
		Enumeration<String> parameterNames = request.getParameterNames();
		if (parameterNames!=null) {
			while (parameterNames.hasMoreElements()) {
				String name = parameterNames.nextElement();
				if (StringUtils.isNotBlank(name)) {
					String[] alreadyValues = parameterMap.get(name);
					String[] parameterValues = request.getParameterValues(name);
					if (alreadyValues==null) {
						if (parameterValues!=null && parameterValues.length > 0) {
							parameterMap.put(name, parameterValues);
						}
					} else {
						if (parameterValues!=null && parameterValues.length > 0) {
							String[] values = new String[alreadyValues.length+parameterValues.length];
							System.arraycopy(alreadyValues, 0, values, 0, alreadyValues.length);
							System.arraycopy(parameterValues, 0, values, alreadyValues.length, parameterValues.length);
							parameterMap.put(name, values);
						}
					}
				}
			}
		}
		
		Collection<Part> parts = request.getParts();
		if (parts!=null && parts.size()>0) {
			Iterator<Part> it = parts.iterator();
			while (it.hasNext()) {
				Part part = it.next();
				if (part!=null) {
					String partName = part.getName();
					if (StringUtils.isBlank(partName)) continue;
					
					List<Part> alreadyParts = partMap.get(partName);
					if (alreadyParts==null) {
						alreadyParts = new ArrayList<>();
					}
					alreadyParts.add(part);
					partMap.put(partName, alreadyParts);
				}
				
			}
		}
	}
	
	@Override
	public Map<String, String[]> getParameterMap() {
		return parameterMap;
	}
	
	@Override
	public String getParameter(String name) {
		String[] values = parameterMap.get(name);
		if (values==null) {
			return null;
		} else {
			return values[0];
		}
	}
	
	@Override
	public String[] getParameterValues(String name) {
		return parameterMap.get(name);
	}
	
	
	

}
