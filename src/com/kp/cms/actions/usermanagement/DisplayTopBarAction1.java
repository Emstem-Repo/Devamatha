package com.kp.cms.actions.usermanagement;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.bo.admin.Organisation;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.usermanagement.LoginForm;
import com.kp.cms.handlers.admin.NewsEventsHandler;
import com.kp.cms.handlers.admin.OrganizationHandler;
import com.kp.cms.to.admin.NewsEventsTO;

public class DisplayTopBarAction1 extends BaseDispatchAction{
	
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		LoginForm loginForm = (LoginForm) form;
		StringBuffer buffer = new StringBuffer();
		String description = "";
		NewsEventsTO eventsTO = null;
		try {
			Organisation organisation = OrganizationHandler.getInstance().getRequiredFile();
			if(organisation!=null){
				// set photo to session
				HttpSession session= request.getSession(false);
				session.setAttribute("studnetLoginNew",true);
				if(organisation.getLogoContentType1()!=null){
					if(session!=null){
						session.setAttribute(CMSConstants.KNOWLEDGEPRO_LOGO, organisation.getLogo1());
						session.setAttribute(CMSConstants.KNOWLEDGEPRO_TOPBAR, organisation.getTopbar1());
					}
				}
			}	
			List<NewsEventsTO> newsEventsList = NewsEventsHandler.getInstance().getNewsEvents();
			Iterator<NewsEventsTO> iterator = newsEventsList.iterator();
			while (iterator.hasNext()) {
				eventsTO = (NewsEventsTO ) iterator.next();
				description = buffer.append(eventsTO.getName()).append("<br></br>").toString();
			}
		loginForm.setDescription(description);
		} catch (Exception e) {
			
		}
		if(CMSConstants.LINK_FOR_CJC)
		{ 
			return mapping.findForward(CMSConstants.STUDENT_LOGIN_CJC);
		}else
		{
			return mapping.findForward(CMSConstants.LOGIN_DISPLAY);
		}
	}
}