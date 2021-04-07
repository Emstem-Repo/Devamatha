package com.kp.cms.actions.fee;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
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
import com.kp.cms.bo.admin.Fee;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.fee.FeeAssignmentForm;
import com.kp.cms.handlers.admin.AdmittedThroughHandler;
import com.kp.cms.handlers.admin.ProgramTypeHandler;
import com.kp.cms.handlers.admission.AdmissionFormHandler;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.fee.FeeAssignmentHandler;
import com.kp.cms.handlers.fee.FeeCategoryHandler;
import com.kp.cms.handlers.fee.FeeDivisionHandler;
import com.kp.cms.handlers.fee.FeeGroupHandler;
import com.kp.cms.to.admin.AdmittedThroughTO;
import com.kp.cms.to.admin.CurrencyTO;
import com.kp.cms.to.admin.ProgramTypeTO;
import com.kp.cms.to.fee.FeeAccountAssignmentTO;
import com.kp.cms.to.fee.FeeCategoryTo;
import com.kp.cms.to.fee.FeeTO;
import com.kp.cms.utilities.CommonUtil;

/**
 * 
 * Date 15/jan/2009
 * Action class for Fee Assignment: handles CRUD operation of fee assignment
 */
@SuppressWarnings("deprecation")
public class FeeAssignmentAction extends BaseDispatchAction {
       
	private static final Log log = LogFactory.getLog(FeeAssignmentAction.class);
	
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
	public ActionForward initFeeAssignment(
						ActionMapping mapping,
					    ActionForm form,
					    HttpServletRequest request,
					    HttpServletResponse response) throws Exception {
		
	    FeeAssignmentForm feeAssignmentForm = (FeeAssignmentForm)form;
	    log.debug("Action : Entering initFeeAssignment ");

	    // clearing form id to null.
	    feeAssignmentForm.clear();
	    setUserId(request,feeAssignmentForm);
	    // setting the necessary data.
	    try{
	    	setRequiredDataToForm(feeAssignmentForm,request);
	    	request.getSession().removeAttribute("baseActionForm");
	    	
	    	//currencies
			List<CurrencyTO> currencyList = AdmissionFormHandler.getInstance().getCurrencies();
			feeAssignmentForm.setCurrencies(currencyList);
	    	
	    } catch(Exception e){
	    	log.debug(e.getMessage());
	    	log.error("error in loading fee Account...",e);
			String msg = super.handleApplicationException(e);
			feeAssignmentForm.setErrorMessage(msg);
			feeAssignmentForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
	    }
		log.debug("Action :Leaving initFeeAssignment with success");
		  
	return mapping.findForward(CMSConstants.FEE_ASSIGNMENT_ENTRY);	
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
	public ActionForward initAddFeeAssignmentAccount(
					    ActionMapping mapping,
					    ActionForm form,
					    HttpServletRequest request,
					    HttpServletResponse response) throws Exception {
		    
	    log.debug("Action : Entering initAddFeeAssignmentAccount ");
	    FeeAssignmentForm feeAssignmentForm = (FeeAssignmentForm)form;
	    ActionMessages errors = feeAssignmentForm.validate(mapping, request);
	    if(errors.isEmpty()) {
	        try {
	        	FeeAssignmentHandler.getInstance().initAddFeeAssignmentAccount(feeAssignmentForm);
	        boolean isPresent = FeeAssignmentHandler.getInstance().isSemesterWise(feeAssignmentForm);
	        if(isPresent){
	        	feeAssignmentForm.setIsSemesterWise(true);
	        }else {
	        	feeAssignmentForm.setIsSemesterWise(false);
	        }
		    } catch (DuplicateException e1) {
		    	errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.FEE_ADD_EXIST));
	    		saveErrors(request,errors);
	    		setRequiredDataToForm(feeAssignmentForm,request);
	    		return mapping.findForward(CMSConstants.FEE_ASSIGNMENT_ENTRY);
		    } catch (ReActivateException e1) {
		    	errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.FEE_ADD_EXIST_REACTIVATE));
	    		saveErrors(request,errors);
	    		setRequiredDataToForm(feeAssignmentForm,request);
	    		return mapping.findForward(CMSConstants.FEE_ASSIGNMENT_ENTRY);
		    } catch (Exception e) {
		    	log.debug(e.getMessage());
		    	log.debug("Action : Leaving initAddFeeAssignmentAccount");
				String msg = super.handleApplicationException(e);
				feeAssignmentForm.setErrorMessage(msg);
				feeAssignmentForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
		    }
	    } else {
	    		setRequiredDataToForm(feeAssignmentForm,request);
	    		request.setAttribute("validateError", true);
	    		saveErrors(request, errors);
	    		log.debug("Action : Leaving initAddFeeAssignmentAccount with success");
	    		return mapping.findForward(CMSConstants.FEE_ASSIGNMENT_ENTRY);
	    }
	    log.debug("Action : Leaving initAddFeeAssignmentAccount with success");
	    request.setAttribute("feeAccountoperation", "insert");
	return mapping.findForward(CMSConstants.FEE_ASSIGNMENT_ACCOUNT_ENTRY);	
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
	public ActionForward saveFeeAssignmentAccount(
						    ActionMapping mapping,
						    ActionForm form,
						    HttpServletRequest request,
						    HttpServletResponse response) throws Exception {
		    
	    log.debug("Action: Entering saveFeeAssignmentAccount ");
	    FeeAssignmentForm feeAssignmentForm = (FeeAssignmentForm)form;
//	    ActionMessages errors = new ActionErrors();
	    ActionMessages messages = new ActionMessages();
	    //validation if needed
		ActionMessages errors=feeAssignmentForm.validate(mapping, request);
		if(errors==null)
			errors= new ActionMessages();
	    boolean isAdded = false;
	    try {
	    		//validateCurrencies(errors, feeAssignmentForm.getFeeAssignmentList());
			
			if (errors != null && !errors.isEmpty()) {
				saveErrors(request, errors);

				setRequiredDataToForm(feeAssignmentForm,request);
				
				return mapping.findForward(CMSConstants.FEE_ASSIGNMENT_ACCOUNT_ENTRY);	
			}
	    	Fee fee = FeeAssignmentHandler.getInstance().getRecord(feeAssignmentForm);
	    	if(fee != null){
	    		errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.FEE_ADD_EXIST));
	    		saveErrors(request,errors);
	    		setRequiredDataToForm(feeAssignmentForm,request);
	    		return mapping.findForward(CMSConstants.FEE_ASSIGNMENT_ENTRY);
	    	}
	    	isAdded = FeeAssignmentHandler.getInstance().saveFeeAssignmentAccount(feeAssignmentForm);
	    } catch (Exception e) {
	    	log.debug("Action : Leaving saveFeeAssignmentAccount with Exception");
	    	// failure error message.
	    	log.debug(e.getMessage());
    		errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.FEE_ADD_FAILURE));
    		saveErrors(request,errors);
    		if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				feeAssignmentForm.setErrorMessage(msg);
				feeAssignmentForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
	    }
	    
	    if(isAdded) {
	    	ActionMessage message = new ActionMessage(CMSConstants.FEE_ADD_SUCCESS);
			messages.add(CMSConstants.MESSAGES, message);
			saveMessages(request, messages);
	    }
	    feeAssignmentForm.clear();
	    setRequiredDataToForm(feeAssignmentForm,request);
	    log.debug("Action : Leaving saveFeeAssignmentAccount with success");
		  
	return mapping.findForward(CMSConstants.FEE_ASSIGNMENT_ENTRY);	
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
	public ActionForward deleteFeeAssignment(
					    ActionMapping mapping,
					    ActionForm form,
					    HttpServletRequest request,
					    HttpServletResponse response) throws Exception{
			
		log.debug("Action :Entering deleteFeeAssignment");
		FeeAssignmentForm feeAssignmentForm = (FeeAssignmentForm)form;
		ActionMessages errors = new ActionErrors();
	    ActionMessages messages = new ActionMessages();
	    boolean isDeleted = false;
	    try {
		    if(feeAssignmentForm.getId() != null) {
		    	isDeleted = FeeAssignmentHandler.getInstance().deleteFeeAssignment(feeAssignmentForm);
		    }
	    } catch (Exception e) {
	    	// failure error message.
	    	log.debug("Action :Entering deleteFeeAssignment with exception");
    		errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.FEE_DELETE_FAILURE));
    		saveErrors(request,errors);
	    }
	    setRequiredDataToForm(feeAssignmentForm,request);
    	if(isDeleted) {
    		// success deleted
    		log.debug("Action :Entering deleteFeeAssignment with exception");
    		ActionMessage message = new ActionMessage(CMSConstants.FEE_DELETE_SUCCESS);
			messages.add(CMSConstants.MESSAGES, message);
			saveMessages(request, messages);
			feeAssignmentForm.clear();
    	}
    	log.debug("Action :Entering deleteFeeAssignment with success");
	return mapping.findForward(CMSConstants.FEE_ASSIGNMENT_ENTRY);	
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
	public ActionForward activateFeeAssignment(
					    ActionMapping mapping,
					    ActionForm form,
					    HttpServletRequest request,
					    HttpServletResponse response) throws Exception{
			
		log.debug("Action :Entering activateFeeAssignment");
		FeeAssignmentForm feeAssignmentForm = (FeeAssignmentForm)form;
		ActionMessages errors = new ActionErrors();
	    ActionMessages messages = new ActionMessages();
	    boolean isActivated = false;
	    try {
		    if(feeAssignmentForm.getId() != null) {
		    	isActivated = FeeAssignmentHandler.getInstance().activateFeeAssignment(feeAssignmentForm);
		    }
	    } catch (Exception e) {
	    	// failure error message.
	    	log.debug("Action :Entering activateFeeAssignment with exception");
    		errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.FEE_ACTIVATE_FAILURE));
    		saveErrors(request,errors);
	    }
	    setRequiredDataToForm(feeAssignmentForm,request);
    	if(isActivated){
    		// success Activated
    		feeAssignmentForm.clear();
    		ActionMessage message = new ActionMessage(CMSConstants.FEE_ACTIVATE_SUCCESS);
			messages.add(CMSConstants.MESSAGES, message);
			saveMessages(request, messages);
    	}
    	log.debug("Action :Entering activateFeeAssignment with success");
	return mapping.findForward(CMSConstants.FEE_ASSIGNMENT_ENTRY);	
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
	public ActionForward viewFeeAssignment(
					    ActionMapping mapping,
					    ActionForm form,
					    HttpServletRequest request,
					    HttpServletResponse response) throws Exception{
		    
		log.debug("Action : Entering viewFeeAssignment");
		FeeAssignmentForm feeAssignmentForm = (FeeAssignmentForm)form;
		ActionMessages errors = new ActionErrors();
		//setting the Fee Assignment Account details to form.	
		try{
			FeeAssignmentHandler.getInstance().viewFeeAssignment(feeAssignmentForm);
			setRequiredDataToForm(feeAssignmentForm,request);
		} catch(Exception e) {
			log.debug(e.getMessage());
			log.debug("Action : Leaving editFeeAdditional with Exception");
			errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.FEE_ADDITIONAL_LOAD_FAILURE));
			saveErrors(request, errors);
			return mapping.findForward(CMSConstants.FEE_VIEW_ASSIGNMENT);
		}
		log.debug("Leaving viewFeeAssignment with success");	
	return mapping.findForward(CMSConstants.FEE_VIEW_ASSIGNMENT);
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
	public ActionForward editFeeAssignment(
					    ActionMapping mapping,
					    ActionForm form,
					    HttpServletRequest request,
					    HttpServletResponse response) throws Exception{ 
			
		log.debug("Action : Entering editFeeAssignment");	
		FeeAssignmentForm feeAssignmentForm = (FeeAssignmentForm)form;
		ActionMessages errors = new ActionErrors();
		// setting pre-data for particular fee ID
		try {
			// sets the required data to form.
			setRequiredDataToForm(feeAssignmentForm,request);
			FeeAssignmentHandler.getInstance().editFeeAssignment(feeAssignmentForm);
			// the following are needed to pre load the data while editing because the below are ajax related fetching in UI.
			setOptionsDataInEditMode(feeAssignmentForm,request);
		} catch (Exception e ){
			errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.FEE_ACTIVATE_FAILURE));
			saveErrors(request, errors);
			log.debug("Action : Leaving editFeeAssignment with exception");
			return mapping.findForward(CMSConstants.FEE_ASSIGNMENT_ENTRY);
		} 			
		
		// setting method of operation to update
		request.setAttribute(CMSConstants.FEE_OPERATION,CMSConstants.EDIT_OPERATION);
		log.debug("Action : Leaving editFeeAssignment with success");
	return mapping.findForward(CMSConstants.FEE_ASSIGNMENT_ENTRY);
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
	public ActionForward initEditFeeAssignmentAccount(
					    ActionMapping mapping,
					    ActionForm form,
					    HttpServletRequest request,
					    HttpServletResponse response) throws Exception{
		    
	    log.debug("Action : Entering initEditFeeAssignmentAccount ");
	    FeeAssignmentForm feeAssignmentForm = (FeeAssignmentForm)form;
	    ActionMessages errors = feeAssignmentForm.validate(mapping, request);
	    try {
	       	if(errors.isEmpty()) {  
	       		boolean isPresent = FeeAssignmentHandler.getInstance().isSemesterWise(feeAssignmentForm);
		        if(isPresent){
		        	feeAssignmentForm.setIsSemesterWise(true);
		        }else {
		        	feeAssignmentForm.setIsSemesterWise(false);
		        }
		    	FeeAssignmentHandler.getInstance().initEditFeeAssignmentAccount(feeAssignmentForm);
		    	
	       	} else {
	    		setRequiredDataToForm(feeAssignmentForm,request);
	    		setOptionsDataInEditMode(feeAssignmentForm,request);
	    		saveErrors(request, errors);
	    		request.setAttribute("feeAccountoperation", "edit");
	    		log.debug("Action : Leaving addFeeAssignment with validation error");
	    		return mapping.findForward(CMSConstants.FEE_ASSIGNMENT_ENTRY);
		    }	
		} catch (DuplicateException e1) {
	    	errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.FEE_ADD_EXIST));
    		saveErrors(request,errors);
			request.setAttribute(CMSConstants.FEE_OPERATION,"edit");
			setOptionsDataInEditMode(feeAssignmentForm,request);
    		setRequiredDataToForm(feeAssignmentForm,request);
    		return mapping.findForward(CMSConstants.FEE_ASSIGNMENT_ENTRY);
	    } catch (ReActivateException e1) {
	    	errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.FEE_ADD_EXIST_REACTIVATE));
    		saveErrors(request,errors);
			request.setAttribute(CMSConstants.FEE_OPERATION,CMSConstants.EDIT_OPERATION);
    		setRequiredDataToForm(feeAssignmentForm,request);
    		setOptionsDataInEditMode(feeAssignmentForm,request);
    		return mapping.findForward(CMSConstants.FEE_ASSIGNMENT_ENTRY);
	    } catch(Exception e) {
	    	log.debug("Action : Leaving addFeeAssignment with Exception");
	    	String msg = super.handleApplicationException(e);
			feeAssignmentForm.setErrorMessage(msg);
			feeAssignmentForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
	  
	    log.debug("Action : Leaving addFeeAssignment with success");
	    request.setAttribute("feeAccountoperation", "edit");
	return mapping.findForward(CMSConstants.FEE_ASSIGNMENT_ACCOUNT_ENTRY);	
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
	public ActionForward updateFeeAssignmentAccount(
					    ActionMapping mapping,
					    ActionForm form,
					    HttpServletRequest request,
					    HttpServletResponse response) throws Exception{
	    
	    log.debug("Action: Entering updateFeeAssignmentAccount ");
	    FeeAssignmentForm feeAssignmentForm = (FeeAssignmentForm)form;
	  //validation if needed
		ActionMessages errors=feeAssignmentForm.validate(mapping, request);
		if(errors==null)
			errors= new ActionMessages();
	    ActionMessages messages = new ActionMessages();
	    boolean isUpdated = false;
		try {
			//validateCurrencies(errors, feeAssignmentForm.getFeeAssignmentList());
			
			if (errors != null && !errors.isEmpty()) {
				saveErrors(request, errors);
				request.setAttribute("feeAccountoperation", "edit");
				return mapping.findForward(CMSConstants.FEE_ASSIGNMENT_ACCOUNT_ENTRY);	
			}
			
			isUpdated = FeeAssignmentHandler.getInstance().updateFeeAssignmentAccount(feeAssignmentForm);
	    	feeAssignmentForm.clear();
		    setRequiredDataToForm(feeAssignmentForm,request);
	    }  catch (Exception e) {
	    	log.debug("Action: Leaving updateFeeAssignmentAccount with exception");
			String msg = super.handleApplicationException(e);
			feeAssignmentForm.setErrorMessage(msg);
			feeAssignmentForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
	    }
	    if(isUpdated) {
	    	ActionMessage message = new ActionMessage(CMSConstants.FEE_UPDATE_SUCCESS);
			messages.add(CMSConstants.MESSAGES, message);
			saveMessages(request, messages);
	    }
	    log.debug("Action: Leaving updateFeeAssignmentAccount with success");
	return mapping.findForward(CMSConstants.FEE_ASSIGNMENT_ENTRY);
	}	    
	
	/**
	 * @param errors
	 * @param feeAssignmentList
	 */
	private void validateCurrencies(ActionMessages errors,
			List<FeeAccountAssignmentTO> feeAssignmentList) {
		if(feeAssignmentList!=null){
			Iterator<FeeAccountAssignmentTO> itr = feeAssignmentList.iterator();
			while (itr.hasNext()) {
				FeeAccountAssignmentTO assignmentTO = (FeeAccountAssignmentTO) itr.next();
				if(assignmentTO.getAmount()>0.0 && (assignmentTO.getCurrencyId()==null || StringUtils.isEmpty(assignmentTO.getCurrencyId())))
				{
					if(errors.get(CMSConstants.FEEASSIGNMENT_CURRENCY_REQUIRED)!=null && !errors.get(CMSConstants.FEEASSIGNMENT_CURRENCY_REQUIRED).hasNext()) {
						ActionMessage error = new ActionMessage(CMSConstants.FEEASSIGNMENT_CURRENCY_REQUIRED);
						errors.add(CMSConstants.FEEASSIGNMENT_CURRENCY_REQUIRED,error);
					}
				}
			}
		}
		
	}

	/**
	 * This method sets the required data to form and request.
	 */
	public void setRequiredDataToForm(FeeAssignmentForm feeAssignmentForm,HttpServletRequest request) throws Exception{
		    
	    //setting programList to Request
		List<ProgramTypeTO> programTypeList = ProgramTypeHandler.getInstance().getProgramType();
		request.setAttribute("programTypeList", programTypeList);
	    // setting all Fee Details to form.
		List<FeeTO> feeList = FeeAssignmentHandler.getInstance().getAllFees();
		feeAssignmentForm.setFeeList(feeList);		
 		
 		List<FeeCategoryTo> feeCategoryList = FeeCategoryHandler.getInstance().getFeeCategories();
 		feeAssignmentForm.setFeeCategoryList(feeCategoryList);
	}
	
	/**
	 * This method will set the required data to form while editing.
	 * @param feeAssignmentForm
	 * @param request
	 */
	public void setOptionsDataInEditMode(FeeAssignmentForm feeAssignmentForm,HttpServletRequest request) {
		Map<Integer,String> programMap = new HashMap<Integer,String>();
		Map<Integer,String> courseMap = new HashMap<Integer,String>();
		Map<Integer,String> subjectGroupMap = new HashMap<Integer,String>();
		Map<Integer,Integer> semisterMap = new HashMap<Integer,Integer>();
		
		if(feeAssignmentForm.getProgramTypeId().length() != 0 ) {
			programMap = CommonAjaxHandler.getInstance().getProgramsByProgramType(Integer.parseInt(feeAssignmentForm.getProgramTypeId()));
		}	
		if(feeAssignmentForm.getProgramId().length() != 0) {
			courseMap = CommonAjaxHandler.getInstance().getCourseByProgram(Integer.parseInt(feeAssignmentForm.getProgramId()));
		}	
		if(feeAssignmentForm.getAcademicYear().length() != 0 && feeAssignmentForm.getCourseId().length() != 0 && feeAssignmentForm.getSemister().length() != 0) {
			subjectGroupMap = CommonAjaxHandler.getInstance().getSubjectGroupsByYearAndCourse(Integer.parseInt(feeAssignmentForm.getAcademicYear()), Integer.parseInt(feeAssignmentForm.getCourseId()),Integer.parseInt(feeAssignmentForm.getSemister()));
		}	
		if(feeAssignmentForm.getAcademicYear().length() != 0 && feeAssignmentForm.getCourseId().length() != 0) {
			semisterMap = CommonAjaxHandler.getInstance().getSemistersByYearAndCourse(Integer.parseInt(feeAssignmentForm.getAcademicYear()), Integer.parseInt(feeAssignmentForm.getCourseId()));
		}	
		request.setAttribute("programMap", programMap);
		request.setAttribute("courseMap", courseMap);
		request.setAttribute("subjectGroupMap", subjectGroupMap);
		request.setAttribute("semisterMap", semisterMap);
	}
}
