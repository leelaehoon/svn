package kr.or.ddit.listener;

import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

import kr.or.ddit.vo.MemberVO;

@WebListener
public class CustomHttpSessionAttributeListener implements HttpSessionAttributeListener {
	
    public void attributeAdded(HttpSessionBindingEvent event)  {
    	if ("authMember".equals(event.getName())) {
    		ServletContext application = event.getSession().getServletContext();
    		Set<MemberVO> applicationUsers = (Set<MemberVO>) application.getAttribute("applicationUsers");
    		applicationUsers.add((MemberVO) event.getValue());
		}
    }
    
    public void attributeRemoved(HttpSessionBindingEvent event)  { 
    	if ("authMember".equals(event.getName())) {
    		ServletContext application = event.getSession().getServletContext();
    		Set<MemberVO> applicationUsers = (Set<MemberVO>) application.getAttribute("applicationUsers");
    		applicationUsers.remove((MemberVO) event.getValue());
    	}
    }
    
    public void attributeReplaced(HttpSessionBindingEvent event)  { 
    	
    }
	
}
