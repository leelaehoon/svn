package kr.or.ddit.web.modulize;

public enum ServiceType {
	GUGUDAN("/01/gugudanform.html"),
	GUGUDANPROCESS("/gugudan.do"),
	LYRICS("/02/musicForm.jsp"),
	CALENDAR("/04/calendar.jsp"), 
	IMAGE("/imageForm");
	
	private String ServicePage;

	private ServiceType(String servicePage) {
		this.ServicePage = servicePage;
	}
	
	public String getServicePage() {
		return ServicePage;
	}
}
