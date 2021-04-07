package com.kp.cms.actions.admission;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.admission.PublishAdmitCardForm;
import com.kp.cms.handlers.admission.PublishAdmitCardHandler;

public class PublishAdmitCardAction extends BaseDispatchAction {
	
	/**
     * 
     * Performs the submit action.
     *
     * @param mapping  The ActionMapping used to select this instance
     * @param form     The optional ActionForm bean for this request (if any)
     * @param request  The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return The forward to which control should be transferred,
     * @throws Exception if an exception occurs
     */
	public ActionForward initPublishAdmitCard(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
			PublishAdmitCardForm publishAdmitCardForm = (PublishAdmitCardForm) form;
			
			publishAdmitCardForm.setCandidatesList(PublishAdmitCardHandler.getInstance().getSelectedList());
		
		return mapping.findForward(CMSConstants.PUBLISH_ADMIT_CARD);
		
	}

}
