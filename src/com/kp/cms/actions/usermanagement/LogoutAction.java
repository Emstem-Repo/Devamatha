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

public class LogoutAction extends BaseDispatchAction {
	
	/**
	 * performs logout action.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward logout(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		LoginForm loginForm = (LoginForm) form;

		HttpSession session = request.getSession();
		if (session != null) {
			session.invalidate();
		}
		StringBuffer description = new StringBuffer();
		NewsEventsTO eventsTO = null;
		try {
			Organisation organisation = OrganizationHandler.getInstance().getRequiredFile();
			if(organisation!=null){
				// set photo to session
				HttpSession session1 = request.getSession();
				if(organisation.getLogoContentType()!=null){
					if(session!=null){
						session1.setAttribute(CMSConstants.KNOWLEDGEPRO_LOGO, organisation.getLogo());
						session1.setAttribute(CMSConstants.KNOWLEDGEPRO_TOPBAR, organisation.getTopbar());
					}
				}
			}
			List<NewsEventsTO> newsEventsList = NewsEventsHandler.getInstance()
					.getNewsEvents();
			Iterator itr = newsEventsList.iterator();
			while (itr.hasNext()) {
				eventsTO = (NewsEventsTO) itr.next();
				if(eventsTO.getRequired().equalsIgnoreCase(CMSConstants.LOGIN_ADMIN)||eventsTO.getRequired().equalsIgnoreCase("ALL")){
					description = description.append(eventsTO.getName());
					description = description .append("<br/><br/>");
				}
			}
			loginForm.setDescription(description.toString());
			loginForm.setServerDownMessage(null);
		} catch (Exception e) {

		}
		if(CMSConstants.LINK_FOR_CJC){
			return mapping.findForward("loginPage");
		}else{
			return mapping.findForward(CMSConstants.LOGIN_PAGE_NEW);
		}
		
	}
}
