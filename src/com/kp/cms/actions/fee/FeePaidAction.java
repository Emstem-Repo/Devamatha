package com.kp.cms.actions.fee;

import java.util.List;

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
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.exceptions.DataNotFoundException;
import com.kp.cms.forms.fee.FeePaidForm;
import com.kp.cms.handlers.fee.FeeDivisionHandler;
import com.kp.cms.handlers.fee.FeePaidHandler;
import com.kp.cms.to.fee.FeeDivisionTO;
import com.kp.cms.utilities.CommonUtil;

@SuppressWarnings("deprecation")
/**
 * Action class for FeePaid modules.
 * This action will be used in marking fee payment as paid.
 */
public class FeePaidAction extends BaseDispatchAction{
	private static final Log log = LogFactory.getLog(FeePaidAction.class);
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return Redirect the control to feeApplicationSearch.
	 * @throws Exception
	 *         will clear the form and redirect to markAsPaid.jsp page.
	 */
	public ActionForward initFeePaidSearch(ActionMapping mapping,
										   ActionForm form, HttpServletRequest request,
										   HttpServletResponse response) throws Exception {
	 	
		log.debug("Entering initFeePaidSearch ");
	 	
		FeePaidForm feePaidForm = (FeePaidForm)form;
		feePaidForm.setPaidDate(CommonUtil.getTodayDate());

		setAccListToRequest(request);
	 	feePaidForm.clear();
	 	log.debug("Leaving initFeePaidSearch ");
	 	
	 	return mapping.findForward(CMSConstants.FEE_PAYS_INITFEEPAIDSEARCH);
	}	
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return redirect to payment search with necessary data.
	 * @throws Exception
	 */
	public ActionForward feePaidSearch(ActionMapping mapping,
						 			   ActionForm form, HttpServletRequest request,
						 			   HttpServletResponse response) throws Exception {
		 	
		log.debug("Entering initFeePaidSearch ");
	 	FeePaidForm feePaidForm = (FeePaidForm)form;
	 	feePaidForm.setFeePaymentList(null);
	 	ActionErrors errors = new ActionErrors();
	 	errors = feePaidForm.validate(mapping, request);
	 	try {
	 		if(errors.isEmpty()) {
	 			FeePaidHandler.getInstance().feePaymentSearch(feePaidForm,"paidSearch");
	 		 	setAccListToRequest(request);
	 		} else {
	 			saveErrors(request, errors);
	 		 	setAccListToRequest(request);
	 			return mapping.findForward(CMSConstants.FEE_PAYS_INITFEEPAIDSEARCH);
	 		}
	 	} catch(DataNotFoundException e) {
	 		errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.KNOWLEDGEPRO_NORECORDS));
    		saveErrors(request,errors);
 		 	setAccListToRequest(request);
    		return mapping.findForward(CMSConstants.FEE_PAYS_INITFEEPAIDSEARCH);
	 	} catch (Exception e) {
	 		log.debug(e.getMessage());
	 		log.warn("Exception while searching");
			String msg = super.handleApplicationException(e);
			feePaidForm.setErrorMessage(msg);
			feePaidForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
	 	}
	 	
	 	log.debug("Leaving initFeePaidSearch ");
		return mapping.findForward(CMSConstants.FEE_PAYS_INITFEEPAIDSEARCH);
	}	
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return This method will mark the selected record as paid.
	 * @throws Exception
	 */
	public ActionForward markAsPaid(ActionMapping mapping,
									ActionForm form, HttpServletRequest request,
									HttpServletResponse response) throws Exception {
		 	
		log.debug("Entering markAsPaid ");
	 	ActionErrors errors = new ActionErrors();
	 	FeePaidForm feePaidForm = (FeePaidForm)form;
	 	try {
	 		if(feePaidForm.getPaidDate() == null || feePaidForm.getPaidDate().trim().isEmpty()){
	 			errors.add(CMSConstants.ERRORS, new ActionError("knowledgepro.fee.installment.date"));
	 			saveErrors(request, errors);
	 		 	setAccListToRequest(request);
	 			return mapping.findForward(CMSConstants.FEE_PAYS_INITFEEPAIDSEARCH);	
	 		}
	 		FeePaidHandler.getInstance().markAsPaid(feePaidForm);
	 		ActionMessages messages = new ActionMessages();
    		ActionMessage message = new ActionMessage(CMSConstants.FEE_PAYS_SUCCESS);
			messages.add(CMSConstants.MESSAGES, message);
			saveMessages(request, messages);
 		 	setAccListToRequest(request);
			feePaidForm.clear();
	 	} catch(BusinessException e) {
	 		errors.add(CMSConstants.ERRORS, new ActionError(e.getMessage()));
    		saveErrors(request,errors);
 		 	setAccListToRequest(request);
	 	} catch (Exception e) {
	 		log.debug(e.getMessage());
	 		errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.FEE_PAYS_FAILURE));
    		saveErrors(request,errors);
    		feePaidForm.setFeePaymentList(null);
 		 	setAccListToRequest(request);
	 	}
	 	log.debug("Leaving markAsPaid ");
	 	return mapping.findForward(CMSConstants.FEE_PAYS_INITFEEPAIDSEARCH);
	}	
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return Redirect the control to feeApplicationSearch. Clears the FeePaidForm.
	 * @throws Exception
	 */
	public ActionForward initChallanCancel(ActionMapping mapping,
										   ActionForm form, HttpServletRequest request,
										   HttpServletResponse response) throws Exception {
	 	
		log.debug("Entering initFeePaidSearch ");
	 	
		FeePaidForm feePaidForm = (FeePaidForm)form;
	 	feePaidForm.clear();
	 	log.debug("Leaving initFeePaidSearch ");
	 	
	 	return mapping.findForward(CMSConstants.FEE_PAYS_INITFEECHALLANSEARCH);
	}	
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return redirect to payment search with necessary data.
	 * @throws Exception
	 */
	public ActionForward feeChallanSearch(ActionMapping mapping,
						 			   ActionForm form, HttpServletRequest request,
						 			   HttpServletResponse response) throws Exception {
		 	
		log.debug("Entering feeChallanSearch ");
	 	FeePaidForm feePaidForm = (FeePaidForm)form;
	 	feePaidForm.setFeePaymentList(null);
	 	ActionErrors errors = new ActionErrors();
	 	errors = feePaidForm.validate(mapping, request);
	 	try {
	 		if(errors.isEmpty()) {
	 			FeePaidHandler.getInstance().feePaymentSearch(feePaidForm,"cancelSearch");
	 		} else {
	 			saveErrors(request, errors);
	 			return mapping.findForward(CMSConstants.FEE_PAYS_INITFEECHALLANSEARCH);
	 		}
	 	} catch(DataNotFoundException e) {
	 		errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.KNOWLEDGEPRO_NORECORDS));
    		saveErrors(request,errors);
    		return mapping.findForward(CMSConstants.FEE_PAYS_INITFEECHALLANSEARCH);
	 	} catch (Exception e) {
	 		log.debug(e.getMessage());
	 		log.warn("Exception while searching");
			String msg = super.handleApplicationException(e);
			feePaidForm.setErrorMessage(msg);
			feePaidForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
	 	}
	 	
	 	log.debug("Leaving feeChallanSearch ");
		return mapping.findForward(CMSConstants.FEE_PAYS_FEECHALLANSEARCH);
	}
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return This method will mark the selected record as paid.
	 * @throws Exception
	 */
	public ActionForward markAsCancel(ActionMapping mapping,
									ActionForm form, HttpServletRequest request,
									HttpServletResponse response) throws Exception {
		 	
		log.debug("Entering markAsCancel ");
	 	ActionErrors errors = new ActionErrors();
	 	FeePaidForm feePaidForm = (FeePaidForm)form;
	 	try {
	 		FeePaidHandler.getInstance().markAsCancel(feePaidForm);
	 		ActionMessages messages = new ActionMessages();
    		ActionMessage message = new ActionMessage(CMSConstants.FEE_CANCELCHALLAN_SUCCESS);
			messages.add("messages", message);
			saveMessages(request, messages);
			feePaidForm.clear();
	 	} catch (Exception e) {
	 		log.debug(e.getMessage());
	 		errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.FEE_CANCELCHALLAN_FAILURE));
    		saveErrors(request,errors);
    		feePaidForm.setFeePaymentList(null);
	 	}
	 	log.debug("Leaving markAsCancel ");
	 	return mapping.findForward(CMSConstants.FEE_PAYS_INITFEECHALLANSEARCH);
	}
	public void setAccListToRequest(HttpServletRequest request)throws Exception{	
		List<FeeDivisionTO> feeDivisionList = FeeDivisionHandler.getInstance()
		.getFeeDivisionList();// loading subjects from database
		request.setAttribute("feeDivList", feeDivisionList);
	}
}
