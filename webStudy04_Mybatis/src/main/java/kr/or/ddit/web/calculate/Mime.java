package kr.or.ddit.web.calculate;

public enum Mime {
	PLAIN("text/plain;charset=UTF-8", (a)->{return a;}),
	JSON("application/json;charset=UTF-8", (a)->{return "{\"result\":\"" + a + "\"}";}),
	HTML("text/html;charset=UTF-8", (a)->{return "<p>" + a + "</p>";}),
	XML("application/xml;charset=UTF-8", (a)->{return a;});
	
	private String contentType;
	private ResponseDataMaker responseDataMaker;
	
	Mime(String contentType, ResponseDataMaker responseDataMaker) {
		this.contentType = contentType;
		this.responseDataMaker = responseDataMaker;
	}
	
	public String getContentType() {
		return this.contentType;
	}
	
	public String dataMake(String result) {
		return responseDataMaker.dataMake(result);
	}
}
