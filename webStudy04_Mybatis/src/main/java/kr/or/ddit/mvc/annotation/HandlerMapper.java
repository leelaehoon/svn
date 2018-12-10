package kr.or.ddit.mvc.annotation;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.ddit.mvc.annotation.URIMapping.HttpMethod;
import kr.or.ddit.utils.ReflectionUtil;

/**
 * 1. 특정 패키지 아래의 커맨드핸들러들을 수집하고
 * 2. 해당 핸들러내의 핸들러메소드에 대한 정보를 수집하고
 * 3. 1번과 2번의 데이터로 handlerMap을 형성함
 * 4. 웹상의 요청을 통해 해당 요청을 처리할 수 있는 핸들러에 대한 정보(URIMappingInfo)를 handlerMap에서 찾음
 */
public class HandlerMapper {
	public Map<URIMappingCondition, URIMappingInfo> handlerMap;
	
	public HandlerMapper(String...basePackages) {
		handlerMap = new LinkedHashMap<>();
		List<Class<?>> classList = ReflectionUtil.getClassesWithAnnotationAtBasePackages(CommandHandler.class, basePackages);
		for (Class<?> handlerClz : classList) {
			List<Method> methodList = ReflectionUtil.getMethodsWithAnnotationAtClass(handlerClz, URIMapping.class, String.class, HttpServletRequest.class, HttpServletResponse.class);
			try {
				Object commandHandler = handlerClz.newInstance();
				for (Method temp : methodList) {
					URIMapping uriMapping = temp.getAnnotation(URIMapping.class);
					URIMappingCondition condition = new URIMappingCondition(uriMapping.value(), uriMapping.method());
					URIMappingInfo mappingInfo = new URIMappingInfo(condition, commandHandler, temp);
					handlerMap.put(condition, mappingInfo);
				}
			} catch (InstantiationException | IllegalAccessException e) {
				throw new RuntimeException("핸들러 객체 생성 중 문제발생", e);
			}
		} // for end
	}
	
	public URIMappingInfo findCommandHandler(HttpServletRequest request) {
		String uri = request.getRequestURI();
		int cpLength = request.getContextPath().length();
		uri = uri.substring(cpLength).split(";")[0]; // /board/boardList.do
		HttpMethod method = HttpMethod.valueOf(request.getMethod().toUpperCase());
		URIMappingCondition condition = new URIMappingCondition(uri, method);
		return handlerMap.get(condition);
	}
}
