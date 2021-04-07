package com.kp.cms.actions.usermanagement;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.bo.admin.Organisation;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.usermanagement.LoginForm;
import com.kp.cms.handlers.admin.MaintenanceAlertHandler;
import com.kp.cms.handlers.admin.NewsEventsHandler;
import com.kp.cms.handlers.admin.OrganizationHandler;
import com.kp.cms.to.admin.NewsEventsTO;

public class DisplayNewsEventsAction extends Action {
	
	private static final Log log = LogFactory.getLog(DisplayNewsEventsAction.class);
	
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		LoginForm loginForm = (LoginForm) form;
		boolean attendanceLogin=false;
		if(request.getParameter("att")!=null && request.getParameter("att").equalsIgnoreCase("true"))
			attendanceLogin=true;
		loginForm.setAttendanceLogin(attendanceLogin);
		
		loginForm.setLoginType(null);
		StringBuffer buffer = new StringBuffer();
		String description = "";
		NewsEventsTO eventsTO = null;
		try {
			HttpSession session= request.getSession(false);
			Organisation organisation = OrganizationHandler.getInstance().getRequiredFile();
			if(organisation!=null){
				// set photo to session
				if(organisation.getLogoContentType()!=null){
					if(session!=null){
						session.setAttribute(CMSConstants.KNOWLEDGEPRO_LOGO, organisation.getLogo());
						session.setAttribute(CMSConstants.KNOWLEDGEPRO_TOPBAR, organisation.getTopbar());
					}
				}
			}	
			
			List<NewsEventsTO> newsEventsList = NewsEventsHandler.getInstance().getNewsEvents(CMSConstants.LOGIN_ADMIN);
			Iterator<NewsEventsTO> iterator = newsEventsList.iterator();
			while (iterator.hasNext()) {
				eventsTO = (NewsEventsTO ) iterator.next();
				description = buffer.append("<p>").append(eventsTO.getName()).append("</p>").append("<br></br>").toString();
			}
		loginForm.setDescription(description);
		//added by mahi start
		 loginForm.setServerDownMessage(null);
		 String maintenanceMessage =  MaintenanceAlertHandler.getInstance().getMaintenanceDetailsByDate();
		 if(maintenanceMessage!=null){
			 loginForm.setServerDownMessage(maintenanceMessage);
			 session.setAttribute("serverDownMessage", maintenanceMessage);
		 }
		 //end
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		if(CMSConstants.LINK_FOR_CJC)
		{
			loginForm.setLinkForCJC(true);
		return mapping.findForward(CMSConstants.LOGIN_DISPLAY);
		}else{
			loginForm.setLinkForCJC(false);
			return mapping.findForward(CMSConstants.LOGIN_DISPLAY_NEW);
		}
			
	}	
}