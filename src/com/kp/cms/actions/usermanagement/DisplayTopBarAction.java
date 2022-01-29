package com.kp.cms.actions.usermanagement;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.usermanagement.LoginForm;
import com.kp.cms.handlers.admin.MaintenanceAlertHandler;
import com.kp.cms.handlers.admin.NewsEventsHandler;
import com.kp.cms.to.admin.NewsEventsTO;

public class DisplayTopBarAction extends BaseDispatchAction{
	
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		LoginForm loginForm = (LoginForm) form;
		/*StringBuffer buffer = new StringBuffer();
		String description = "";
		NewsEventsTO eventsTO = null;
		try {
			Organisation organisation = OrganizationHandler.getInstance().getRequiredFile();
			if(organisation!=null){
				byte [] myFileBytes = organisation.getLogo();
				String fileName = organisation.getLogoName();
				String cType = organisation.getLogoContentType();				
				// set photo to session
				HttpSession session= request.getSession(false);
				session.setAttribute("studnetLoginNew",false);
				if(organisation.getLogoContentType()!=null){
					if(session!=null){
						session.setAttribute(CMSConstants.KNOWLEDGEPRO_LOGO, organisation.getLogo());
						session.setAttribute(CMSConstants.KNOWLEDGEPRO_TOPBAR, organisation.getTopbar());
					}
				}
			}	
			List<NewsEventsTO> newsEventsList = NewsEventsHandler.getInstance().getNewsEvents(CMSConstants.LOGIN_STUDENT);
			Iterator<NewsEventsTO> iterator = newsEventsList.iterator();
			while (iterator.hasNext()) {
				eventsTO = (NewsEventsTO ) iterator.next();
				description = buffer.append(eventsTO.getName()).append("<br></br>").toString();
			}
		loginForm.setDescription(buffer.toString());
		} catch (Exception e) {
			
		}*/
			try {
				List<NewsEventsTO> newsEventsList = NewsEventsHandler.getInstance()
					.getNewsEvents(CMSConstants.LOGIN_STUDENT);
			Iterator<NewsEventsTO> itr = newsEventsList.iterator();
			StringBuffer description = new StringBuffer();
			NewsEventsTO eventsTO = null;
			while (itr.hasNext()) {
				eventsTO = (NewsEventsTO) itr.next();
				description.append(eventsTO.getName()).append("<br/><br/>");
			}
			loginForm.setDescription(description.toString());
			} catch (Exception e) {
			}
		//added by mahi start
		loginForm.setServerDownMessage(null);
		 String maintenanceMessage =  MaintenanceAlertHandler.getInstance().getMaintenanceDetailsByDate();
		 if(maintenanceMessage!=null){
			 loginForm.setServerDownMessage(maintenanceMessage);
		 }
		 //end
		if(CMSConstants.LINK_FOR_CJC)
		{ 
			return mapping.findForward(CMSConstants.STUDENT_LOGIN_CJC);
		}else
		{
			return mapping.findForward(CMSConstants.LOGIN_DISPLAY);
		}
	}
}