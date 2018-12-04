package kr.or.ddit.filter.wrapper;


import java.io.IOException;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.Part;


public class FileUploadRequestWrapper_3 extends HttpServletRequestWrapper {
	private Map<String, Part> partMap;
	
	public FileUploadRequestWrapper_3(HttpServletRequest request) throws IOException, ServletException {
		super(request);
		
		partMap = new LinkedHashMap<>();
		
		parseRequest(request);
	}
	
	private void parseRequest(HttpServletRequest request) throws IOException, ServletException {
		Collection<Part> parts = request.getParts();
		if (parts!=null) {
			Iterator<Part> it = parts.iterator();
			while (it.hasNext()) {
				Part part = it.next();
				partMap.put(part.getName(), part);
			}
		}
	}
	
	public Map<String, Part> getPartMap() {
		return partMap;
	}

	public Enumeration<String> getPartNames() {
		final Iterator<String> it = partMap.keySet().iterator();
		return new Enumeration<String>() {

			@Override
			public boolean hasMoreElements() {
				return it.hasNext();
			}

			@Override
			public String nextElement() {
				return it.next();
			}
		};
	}
	
}
