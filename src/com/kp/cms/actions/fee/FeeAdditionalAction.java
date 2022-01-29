package com.kp.cms.actions.fee;

import java.util.List;
import java.util.Map;

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
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.fee.FeeAdditionalForm;
import com.kp.cms.handlers.fee.FeeAdditionalHandler;
import com.kp.cms.handlers.fee.FeeDivisionHandler;
import com.kp.cms.handlers.fee.FeeGroupHandler;
import com.kp.cms.to.fee.FeeAdditionalTO;

/**
 * 
 * Date 15/jan/2009
 * Action class for Fee Assignment
 */
@SuppressWarnings("deprecation")
public class FeeAdditionalAction extends BaseDispatchAction {
       
	private static final Log log = LogFactory.getLog(FeeAdditionalAction.class);
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return set AccountsMap, FeeApplicableFees and feeAssignment list 
	 *         to request and  forward to feeAssignmentEntry
	 * @throws Exception
	 */
	public ActionForward initFeeAdditional(
						    ActionMapping mapping,
						    ActionForm form,
						    HttpServletRequest request,
						    HttpServletResponse response) throws Exception{
	
	    FeeAdditionalForm feeAdditionalForm = (FeeAdditionalForm)form;
	    log.debug("Action : Entering initFeeAssignment ");

	    // clearing form id to null.
	    feeAdditionalForm.clear();
	    setUserId(request,feeAdditionalForm);
	    // setting the necessary data.
	    try {
	    	setRequiredDataToForm(feeAdditionalForm,request);
	    } catch (Exception e){
	    	log.error("error in loading fee Account...",e);
			String msg = super.handleApplicationException(e);
			feeAdditionalForm.setErrorMessage(msg);
			feeAdditionalForm.setErrorStack(e.getMessage());
			log.debug("Action : Leaving initFeeAssignment with Exception");
			return mapping.findForward(CMSConstants.ERROR_PAGE);
	    }
		log.debug("Action : Leaving initFeeAssignment");
	return mapping.findForward(CMSConstants.FEE_ADDITIONAL_ASSIGNMENTENTRY);	
	}	
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return set addFeeAssignment to request and  forward to feeAssignmentEntry
	 * @throws Exception
	 */
	public ActionForward initAddFeeAdditional(
					    ActionMapping mapping,
					    ActionForm form,
					    HttpServletRequest request,
					    HttpServletResponse response) throws Exception{
		
	    FeeAdditionalForm feeAdditionalForm = (FeeAdditionalForm)form;
	    log.debug("Entering initAddFeeAssignmentAccount ");
	    ActionErrors errors = feeAdditionalForm.validate(mapping, request);
	    if(errors.isEmpty()) {
	        try {
	        	FeeAdditionalHandler.getInstance().initAddFeeAssignmentAccount(feeAdditionalForm);
		    } catch (DuplicateException e1) {
		    	errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.FEE_ADDITIONAL_ADD_EXIST));
	    		saveErrors(request,errors);
	    		setRequiredDataToForm(feeAdditionalForm,request);
	    		return mapping.findForward(CMSConstants.FEE_ADDITIONAL_ASSIGNMENTENTRY);
		    } catch (ReActivateException e1) {
		    	errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.FEE_ADDITIONAL_ADD_EXIST_REACTIVATE));
	    		saveErrors(request,errors);
	    		setRequiredDataToForm(feeAdditionalForm,request);
	    		return mapping.findForward(CMSConstants.FEE_ADDITIONAL_ASSIGNMENTENTRY);
		    } catch (Exception e) {
		    	log.debug(e.getMessage());
		    	log.error("error in loading fee Account...",e);
				String msg = super.handleApplicationException(e);
				feeAdditionalForm.setErrorMessage(msg);
				feeAdditionalForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
		    }
	    } else {
	    		setRequiredDataToForm(feeAdditionalForm,request);
	    		request.setAttribute("validateError", true);
	    		saveErrors(request, errors);
	    		return mapping.findForward(CMSConstants.FEE_ADDITIONAL_ASSIGNMENTENTRY);
	    }
	    log.debug("Leaving initAddFeeAdditionalAssignmentAccount");
	    request.setAttribute("feeAccountoperation", "insert");
	return mapping.findForward(CMSConstants.FEE_ADDITIONAL_ACCOUNT_ASSIGNMENTENTRY);	
	}
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return saves the fee assignment account details for particular course.
	 * @throws Exception
	 */
	public ActionForward saveFeeAdditional(
					    ActionMapping mapping,
					    ActionForm form,
					    HttpServletRequest request,
					    HttpServletResponse response) throws Exception {
		
	    FeeAdditionalForm feeAdditionalForm = (FeeAdditionalForm)form;
	    log.debug("Action : Entering saveFeeAssignmentAccount ");
	    ActionErrors errors = new ActionErrors();
	    ActionMessages messages = new ActionMessages();
	    boolean isAdded = false;
	    try {
	    	isAdded = FeeAdditionalHandler.getInstance().saveFeeAssignmentAccount(feeAdditionalForm);
	    } catch (Exception e) {
	    	log.warn("Action : Leaving saveFeeAssignmentAccount with Exception");
	    	log.debug(e.getMessage());
    		errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.FEE_ADDITIONAL_ADD_FAILURE));
    		saveErrors(request,errors);
    		if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				feeAdditionalForm.setErrorMessage(msg);
				feeAdditionalForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
	    }
	    if(isAdded) {
	    	ActionMessage message = new ActionMessage(CMSConstants.FEE_ADDITIONAL_ADD_SUCCESS);
			messages.add(CMSConstants.MESSAGES, message);
			saveMessages(request, messages);
	    }
	    feeAdditionalForm.clear();
	    setRequiredDataToForm(feeAdditionalForm,request);
	    log.debug("Action : Leaving saveFeeAssignmentAccount");
	return mapping.findForward(CMSConstants.FEE_ADDITIONAL_ASSIGNMENTENTRY);	
	}
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request 
	 * @param response
	 *        This action method is to delete single country. 
	 * @return returns error messages based on success / failure.
	 * @throws Exception
	 */
	public ActionForward deleteFeeAdditional(
					    ActionMapping mapping,
					    ActionForm form,
					    HttpServletRequest request,
					    HttpServletResponse response) throws Exception{
			
		log.debug("Action : Entering deleteFeeAssignment");
		FeeAdditionalForm feeAdditionalForm = (FeeAdditionalForm)form;
	    ActionErrors errors = new ActionErrors();
	    ActionMessages messages = new ActionMessages();
	    boolean isDeleted = false;
	    try {
		    if(feeAdditionalForm.getId() != null) {
		    	isDeleted = FeeAdditionalHandler.getInstance().deleteFeeAssignment(feeAdditionalForm);
		    }
	    } catch (Exception e) {
	    	log.debug(e.getMessage());
	    	log.debug("Action : Leaving deleteFeeAssignment with Exception");
	    	errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.FEE_ADDITIONAL_DELETE_FAILURE));
    		saveErrors(request,errors);
	    }
	    setRequiredDataToForm(feeAdditionalForm,request);
    	if(isDeleted){
    		// success deleted
    		ActionMessage message = new ActionMessage(CMSConstants.FEE_ADDITIONAL_DELETE_SUCCESS);
			messages.add(CMSConstants.MESSAGES, message);
			saveMessages(request, messages);
			feeAdditionalForm.clear();
			log.debug("Action : Leaving deleteFeeAssignment with success");
    	}
	return mapping.findForward(CMSConstants.FEE_ADDITIONAL_ASSIGNMENTENTRY);	
	}
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request 
	 * @param response
	 *        This action method Reactivate the Fee assignment. 
	 * @return returns error messages based on success / failure.
	 * @throws Exception
	 */
	public ActionForward activateFeeAdditional(
						    ActionMapping mapping,
						    ActionForm form,
						    HttpServletRequest request,
						    HttpServletResponse response) throws Exception {
			
		log.debug("Action : Entering activateFeeAdditional");
		FeeAdditionalForm feeAdditionalForm = (FeeAdditionalForm)form;
	    ActionErrors errors = new ActionErrors();
	    ActionMessages messages = new ActionMessages();
	    boolean isActivated = false;
	    try {
		    if(feeAdditionalForm.getId() != null) {
		    	isActivated = FeeAdditionalHandler.getInstance().activateFeeAssignment(feeAdditionalForm);
		    }
	    } catch (Exception e) {
	    	// failure error message.
    		errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.FEE_ADDITIONAL_ACTIVATE_FAILURE));
    		saveErrors(request,errors);
    		log.debug("Action : Leaving activateFeeAdditional with Exception");
	    }
	    setRequiredDataToForm(feeAdditionalForm,request);
    	if(isActivated){
    		// success Activated
    		feeAdditionalForm.clear();
    		ActionMessage message = new ActionMessage(CMSConstants.FEE_ADDITIONAL_ACTIVATE_SUCCESS);
			messages.add(CMSConstants.MESSAGES, message);
			saveMessages(request, messages);
			log.debug("Action : Leaving activateFeeAdditional with success");
    	}
	return mapping.findForward(CMSConstants.FEE_ADDITIONAL_ASSIGNMENTENTRY);	
	}
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return viewFeeAssigment set the particular fee and fee assignment details.
	 * @throws Exception
	 */
	public ActionForward viewFeeAdditional(
						    ActionMapping mapping,
						    ActionForm form,
						    HttpServletRequest request,
						    HttpServletResponse response) throws Exception{
		log.debug("Action : Entering viewFeeAdditional");
		FeeAdditionalForm feeAdditionalForm = (FeeAdditionalForm)form;
		//setting the Fee Assignment Account details to form.	
		ActionErrors errors = new ActionErrors();
		try{
			FeeAdditionalHandler.getInstance().viewFeeAssignment(feeAdditionalForm);
		} catch (Exception e) {
			log.debug(e.getMessage());
			log.debug("Action : Leaving viewFeeAdditional with Exception");
			errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.FEE_ADDITIONAL_LOAD_FAILURE));
			saveErrors(request, errors);
		}
		log.debug("Action : Leaving viewFeeAdditional with success");
	return mapping.findForward(CMSConstants.FEE_ADDITIONAL_VIEW_ASSIGNMENT);
	}	
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 *  This method loads pre-necessary data for fee.
	 * @return 
	 * @throws Exception
	 */
	public ActionForward editFeeAdditional(
						    ActionMapping mapping,
						    ActionForm form,
						    HttpServletRequest request,
						    HttpServletResponse response) throws Exception{ 
			
		log.debug("Action : Entering editFeeAdditional");
		FeeAdditionalForm feeAdditionalForm = (FeeAdditionalForm)form;
		ActionErrors errors = new ActionErrors();
		// setting pre-data for particular fee ID
		try {
			FeeAdditionalHandler.getInstance().editFeeAssignment(feeAdditionalForm);
		} catch (Exception e ){
			log.debug(e.getMessage());
			log.debug("Action : Leaving editFeeAdditional with Exception");
			errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.FEE_ADDITIONAL_LOAD_FAILURE));
			saveErrors(request, errors);
			return mapping.findForward(CMSConstants.FEE_ADDITIONAL_ASSIGNMENTENTRY);
		} 			
		// sets the required data to form.
		setRequiredDataToForm(feeAdditionalForm,request);
		
		// setting method of operation to update
		request.setAttribute("feeOperation","edit");
		log.debug("Action : Leaving editFeeAdditional with success");
	return mapping.findForward(CMSConstants.FEE_ADDITIONAL_ASSIGNMENTENTRY);
	}
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return set Data needed while editing the FeeAccount to request and  forward to feeAssignmentEntry
	 * @throws Exception
	 */
	public ActionForward initEditFeeAdditional(
					    ActionMapping mapping,
					    ActionForm form,
					    HttpServletRequest request,
					    HttpServletResponse response) throws Exception{
		
	    FeeAdditionalForm feeAdditionalForm = (FeeAdditionalForm)form;
	    log.debug("Entering initEditFeeAssignmentAccount ");
	    ActionErrors errors = feeAdditionalForm.validate(mapping, request);
	    if(errors.isEmpty()) {
	        try {
	        	FeeAdditionalHandler.getInstance().initEditFeeAssignmentAccount(feeAdditionalForm);
		    } catch (DuplicateException e1) {
		    	errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.FEE_ADDITIONAL_ADD_EXIST));
	    		saveErrors(request,errors);
	    		setRequiredDataToForm(feeAdditionalForm,request);
	    		return mapping.findForward(CMSConstants.FEE_ADDITIONAL_ASSIGNMENTENTRY);
		    } catch (ReActivateException e1) {
		    	errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.FEE_ADDITIONAL_ADD_EXIST_REACTIVATE));
	    		saveErrors(request,errors);
	    		setRequiredDataToForm(feeAdditionalForm,request);
	    		return mapping.findForward(CMSConstants.FEE_ADDITIONAL_ASSIGNMENTENTRY);
		    } catch (Exception e) {
		    	log.debug(e.getMessage());
		    	log.error("error in loading fee Account...",e);
				String msg = super.handleApplicationException(e);
				feeAdditionalForm.setErrorMessage(msg);
				feeAdditionalForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
		    }
	    } else {
	    		setRequiredDataToForm(feeAdditionalForm,request);
	    		request.setAttribute("validateError", true);
	    		request.setAttribute("feeOperation","edit");
	    		saveErrors(request, errors);
	    		log.debug("Action : Leaving initEditFeeAdditional with Failure");
	    		return mapping.findForward(CMSConstants.FEE_ADDITIONAL_ASSIGNMENTENTRY);
	    }
	    log.debug("Action : Leaving initEditFeeAdditional with success");
	    request.setAttribute("feeAccountoperation", "edit");
	return mapping.findForward(CMSConstants.FEE_ADDITIONAL_ACCOUNT_ASSIGNMENTENTRY);	
	}
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return Update the Fee and feeAssignment to Database.
	 * @throws Exception
	 */
	public ActionForward updateFeeAdditional(
					    ActionMapping mapping,
					    ActionForm form,
					    HttpServletRequest request,
					    HttpServletResponse response) throws Exception{
		
	    FeeAdditionalForm feeAdditionalForm = (FeeAdditionalForm)form;
	    log.debug("Action : Entering updateFeeAssignmentAccount ");
	    ActionMessages messages = new ActionMessages();
	    boolean isAdded = false;
	    ActionErrors errors = feeAdditionalForm.validate(mapping, request);
		try {
			if(errors.isEmpty()) {  
		    	isAdded = FeeAdditionalHandler.getInstance().updateFeeAssignmentAccount(feeAdditionalForm);
		    	feeAdditionalForm.clear();
			    setRequiredDataToForm(feeAdditionalForm,request);
			} else {
	    		setRequiredDataToForm(feeAdditionalForm,request);
	    		request.setAttribute("feeAccountoperation", "edit");
	    		saveErrors(request, errors);
	    		return mapping.findForward(CMSConstants.FEE_ADDITIONAL_ACCOUNT_ASSIGNMENTENTRY);
		    }	
	    } catch (Exception e) {
	    	log.warn("Action : Leaving updateFeeAssignmentAccount with Exception");
			String msg = super.handleApplicationException(e);
			feeAdditionalForm.setErrorMessage(msg);
			feeAdditionalForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
	    }
	    if(isAdded) {
	    	ActionMessage message = new ActionMessage(CMSConstants.FEE_ADDITIONAL_UPDATE_SUCCESS);
			messages.add("messages", message);
			saveMessages(request, messages);
	    }
	    log.debug("Action : Leaving updateFeeAssignmentAccount with success");
	return mapping.findForward(CMSConstants.FEE_ADDITIONAL_ASSIGNMENTENTRY);
	}	    
	
	/*
	 * This method sets the required data to form and request.
	 */
	public void setRequiredDataToForm(FeeAdditionalForm feeAdditionalForm,HttpServletRequest request) throws Exception{
	    //setting fee group map to form
		Map<Integer,String> feeGroupMap = FeeGroupHandler.getInstance().getFeeOptionalGroupsMap();
		feeAdditionalForm.setFeeGroupMap(feeGroupMap);
		// setting all Fee Details to form.
		List<FeeAdditionalTO> feeList = FeeAdditionalHandler.getInstance().getAllAdditionalFees();
		feeAdditionalForm.setFeeList(feeList);
	}
}
