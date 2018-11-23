package kr.or.ddit.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * @author 이래훈
 * @since 2018. 11. 16.
 * @version 1.0
 * @see javax.servlet.http.HttpServlet
 * <pre>
 * [[개정이력(Modification Information)]]
 * 수정일                          수정자               수정내용
 * --------     --------    ----------------------
 * 2018. 11. 16.      이래훈       쿠키의 생성과 획득을 지원하는 유틸리티 클래스
 * Copyright (c) 2018 by DDIT All right reserved
 * </pre>
 */
public class CookieUtil {
	private HttpServletRequest request;
	private Map<String, Cookie> cookieMap = new LinkedHashMap<>();
	
	public CookieUtil(HttpServletRequest request) {
		super();
		this.request = request;
		generateCookieMap(request);
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
		generateCookieMap(request);
	}
	
	private void generateCookieMap(HttpServletRequest request) {
		cookieMap.clear();
		Cookie[] cookies = request.getCookies();
		if (cookies!=null) {
			for (Cookie tmp : cookies) {
				cookieMap.put(tmp.getName(), tmp);
			}
		}
	}
	
	private static String enc = "UTF-8";
	
	public static void setEnc(String enc) {
		CookieUtil.enc = enc;
	}
	
	/**
	 * 이름으로 쿠키 검색
	 * @param name 쿠키명
	 * @return 존재하지 않는 경우, null 반환
	 */
	public Cookie getCookie(String name) {
		return cookieMap.get(name);
	}
	
	/**
	 * 이름으로 검색 후 value획득 (UTF-8 방식의 디코딩)
	 * @param name
	 * @return
	 */
	public String getCookieValue(String name) {
		try {
			String value = null;
			if (cookieMap.containsKey(name)) {
				value = URLDecoder.decode(cookieMap.get(name).getValue(), enc);
			}
			return value;
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 쿠키 생성
	 * @param name 쿠키명
	 * @param value 쿠키값, 기본 UTF-8 방식으로 인코딩됨.
	 * @return
	 */
	public static Cookie createCookie(String name, String value) {
		try {
			value = URLEncoder.encode(value, enc);
			Cookie cookie = new Cookie(name, value);
			return cookie;
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 쿠키 생성
	 * @param name 쿠키명
	 * @param value 쿠키값, 기본 UTF-8 방식으로 인코딩됨.
	 * @param maxAge 초단위 만료 시간
	 * @return
	 */
	public static Cookie createCookie(String name, String value, int maxAge) {
		Cookie cookie = createCookie(name, value);
		cookie.setMaxAge(maxAge);
		return cookie;
	}
	
	public static enum TextType{PATH, DOMAIN}
	
	/**
	 * 쿠키 생성
	 * @param name 쿠키명
	 * @param value 쿠키값, 기본 UTF-8 방식으로 인코딩됨.
	 * @param text 도메인이나 경로로 사용할 문자열
	 * @param type text파라미터를 도메인으로 사용할지 경로로 사용할지를 결정하는 상수
	 * @return
	 */
	public static Cookie createCookie(String name, String value, String text, TextType type) {
		Cookie cookie = createCookie(name, value);
		if (type == TextType.PATH) {
			cookie.setPath(text);
		} else if (type == TextType.DOMAIN) {
			cookie.setDomain(text);
		}
		return cookie;
	}

	/**
	 * 쿠키 생성
	 * @param name 쿠키명
	 * @param value 쿠키값, 기본 UTF-8 방식으로 인코딩됨.
	 * @param text 도메인이나 경로로 사용할 문자열
	 * @param type text파라미터를 도메인으로 사용할지 경로로 사용할지를 결정하는 상수
	 * @param maxAge 초단위 만료 시간
	 * @return
	 */
	public static Cookie createCookie(String name, String value, String text, TextType type, int maxAge) {
		Cookie cookie = createCookie(name, value, text, type);
		cookie.setMaxAge(maxAge);
		return cookie;
	}

	/**
	 * 쿠키 생성
	 * @param name 쿠키명
	 * @param value 쿠키값, 기본 UTF-8 방식으로 인코딩됨.
	 * @param domain 도메인명
	 * @param path 경로
	 * @return
	 */
	public static Cookie createCookie(String name, String value, String domain, String path) {
		Cookie cookie = createCookie(name, value);
		cookie.setDomain(domain);
		cookie.setPath(path);
		return cookie;
	}
	
	/**
	 * 쿠키 생성
	 * @param name 쿠키명
	 * @param value 쿠키값, 기본 UTF-8 방식으로 인코딩됨.
	 * @param domain 도메인명
	 * @param path 경로
	 * @param maxAge 초단위 만료 시간
	 * @return
	 */
	public static Cookie createCookie(String name, String value, String domain, String path, int maxAge) {
		Cookie cookie = createCookie(name, value, domain, path);
		cookie.setMaxAge(maxAge);
		return cookie;
	}
}