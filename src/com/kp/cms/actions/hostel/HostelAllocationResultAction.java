package com.kp.cms.actions.hostel;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.bo.admin.PcReceiptNumber;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.hostel.HostelAllocationForm;
import com.kp.cms.forms.hostel.HostelAllocationResultForm;
import com.kp.cms.forms.pettycash.LastReceiptNumberForm;
import com.kp.cms.handlers.pettycash.LastrceiptNumberHandler;

@SuppressWarnings("deprecation")
public class HostelAllocationResultAction extends BaseDispatchAction {
	private static Log log = LogFactory.getLog(HostelAllocationResultAction.class);
	/**
	 * initialize method
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initAllocation(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.debug("inside initAllocation");
		HostelAllocationForm hostelAllocationForm = (HostelAllocationForm) form;
		try {
			
		} catch (Exception e) {
			log.error("error in initAllocation...", e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				hostelAllocationForm.setErrorMessage(msg);
				hostelAllocationForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
			}
		log.debug("leaving initAllocation");
		return mapping.findForward(CMSConstants.INIT_HOSTEL_ALLOCATION);
	}	
	public ActionForward submitAllocation(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("Start of getHostelAllocationDetails of HostelAllocationAction");
		HostelAllocationResultForm hostelAllocationResultForm = (HostelAllocationResultForm) form;
		 ActionErrors errors = hostelAllocationResultForm.validate(mapping, request);
		try {
			if (errors.isEmpty()) {
				setUserId(request, hostelAllocationResultForm);
			}
			else{
				//assignListToForm(lastReceiptnumberForm);
				//setFinancialYearList(lastReceiptnumberForm);
				saveErrors(request, errors);
			}
		} catch (Exception e) {
			log.error("Error in adding LastReceiptNumber");
				String msg = super.handleApplicationException(e);
				hostelAllocationResultForm.setErrorMessage(msg);
				hostelAllocationResultForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} 
		log.info("End of getHostelAllocationDetails of HostelAllocationAction");
		return mapping.findForward(CMSConstants.DISPLAY_HOSTEL_ALLOCATION);
	}
	
		
}
