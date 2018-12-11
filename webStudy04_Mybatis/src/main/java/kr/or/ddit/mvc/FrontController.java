package kr.or.ddit.mvc;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.or.ddit.mvc.annotation.HandlerInvoker;
import kr.or.ddit.mvc.annotation.HandlerMapper;
import kr.or.ddit.mvc.annotation.URIMappingInfo;

public class FrontController extends HttpServlet {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	private HandlerMapper handlerMapper;
	private HandlerInvoker handlerInvoker;
	private ViewProcessor viewProcessor;
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		String basePackage = config.getInitParameter("basePackage");
		logger.info("{}", basePackage);
		handlerMapper = new HandlerMapper(basePackage);
		handlerInvoker = new HandlerInvoker();
		viewProcessor = new ViewProcessor();
		viewProcessor.setPrefix(config.getInitParameter("prefix"));
		viewProcessor.setSuffix(config.getInitParameter("suffix"));
	}
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		URIMappingInfo mappingInfo = handlerMapper.findCommandHandler(request);
		if (mappingInfo != null) {
			String viewName = handlerInvoker.invokeHandler(mappingInfo, request, response);
			viewProcessor.viewProcess(viewName, request, response);
		} else {
			response.sendError(HttpServletResponse.SC_NOT_FOUND, request.getRequestURI());
		}
	}
}
















