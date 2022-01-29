package com.kp.cms.actions.fee;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.bo.admin.FeeHeading;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.fee.FeeHeadingsForm;
import com.kp.cms.handlers.fee.FeeGroupHandler;
import com.kp.cms.handlers.fee.FeeHeadingsHandler;
import com.kp.cms.to.fee.FeeGroupTO;
import com.kp.cms.to.fee.FeeHeadingTO;

@SuppressWarnings("deprecation")

public class FeeHeadingsAction extends BaseDispatchAction {

	private static final Logger log=Logger.getLogger(FeeHeadingsAction.class);
	
	/**
	 * Performs to load details when you click on FeeHeadings.
     * @param mapping  The ActionMapping used to select this instance
     * @param form     The optional ActionForm bean for this request (if any)
     * @param request  The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return The forward to which control should be transferred,
     * @throws Exception if an exception occurs
     */
	
	public ActionForward initFeeHeadings(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("call of initFeeHeadings method in FeeHeadingsAction class.");
		FeeHeadingsForm feeHeadingsForm=(FeeHeadingsForm)form;
		ActionErrors errors = new ActionErrors();
	    ActionMessages messages = new ActionMessages();
	    errors = feeHeadingsForm.validate(mapping, request);
	    if(errors.isEmpty()){
	    	//errors are empty and loading the fee heading details to form.
	    	getFeeHeadingDetails(feeHeadingsForm);
	    }else{
	    	//errors are present and displays error messages.
	    	errors.add(messages);
			saveErrors(request, errors);
	    }
		
		log.info("end of initFeeHeadings method in FeeHeadingsAction class.");
		return mapping.findForward(CMSConstants.FEE_HEADINGS_ENTRY);
	}
	
	/**
     * 
     * Performs the saving of one record of FeeHeadings action.
     * @param mapping  The ActionMapping used to select this instance
     * @param form     The optional ActionForm bean for this request (if any)
     * @param request  The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return The forward to which control should be transferred,
     * @throws Exception if an exception occurs
     */
	
	public ActionForward addFeeHeadings(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("call of addFeeHeadings method in FeeHeadingsAction class.");
		FeeHeadingsForm feeHeadingsForm=(FeeHeadingsForm)form;
		ActionErrors errors = new ActionErrors();
	    ActionMessages messages = new ActionMessages();
	    errors = feeHeadingsForm.validate(mapping, request);
	    try{
	    	if(errors.isEmpty()){
	    		//errors are empty
	    		setUserId(request, feeHeadingsForm); //setting of user id to form.
	    		int feeGroupId = Integer.valueOf(feeHeadingsForm.getFeeGroup());
	    		String feesName = feeHeadingsForm.getFeesName();
	    		//call of FeeHeadingsHandler class by passing id and name.
	    		FeeHeading feeHeading=FeeHeadingsHandler.getInstance().isFeeHeadingNameExist(feeGroupId, feesName);
				if(feeHeading!=null && feeHeading.getIsActive()){
					errors.add(CMSConstants.FEE_HEADING_NAME_EXIST,new ActionError(CMSConstants.FEE_HEADING_NAME_EXIST));
					saveErrors(request, errors);
				}
				else if(feeHeading!=null && !feeHeading.getIsActive()){
					errors.add(CMSConstants.FEE_HEADING_REACTIVATE,new ActionError(CMSConstants.FEE_HEADING_REACTIVATE));
					saveErrors(request, errors);
				}
				else{
	    		boolean isadded=FeeHeadingsHandler.getInstance().addFeeHeadings(feeHeadingsForm);
				if(isadded) {
					//if adding is success.
					ActionMessage message = new ActionMessage(CMSConstants.FEE_HEADING_ADDSUCCESS);
					messages.add("messages", message);
					saveMessages(request, messages);
					feeHeadingsForm.clearAll();
					feeHeadingsForm.reset(mapping, request);
				}if(!isadded) {
					//if adding is failure.
					ActionMessage message = new ActionMessage(CMSConstants.FEE_HEADING_ADDFAILURE);
					messages.add("messages", message);
					saveMessages(request, messages);
					feeHeadingsForm.clearAll();
					feeHeadingsForm.reset(mapping, request);
				}

				}//end of else
    			}//end of if
	    		else{
	    			errors.add(messages);
	    			saveErrors(request, errors);
	    		}
	    }catch (BusinessException businessException) {
			log.info("Exception addFeeHeadings");
			String msgKey = super.handleBusinessException(businessException);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add(CMSConstants.MESSAGES, message);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception exception) {	
			String msg = super.handleApplicationException(exception);
			feeHeadingsForm.setErrorMessage(msg);
			feeHeadingsForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
	    
		getFeeHeadingDetails(feeHeadingsForm);
			
		log.info("end of addFeeHeadings method in FeeHeadingsAction class.");
		return mapping.findForward(CMSConstants.FEE_HEADINGS_ENTRY);
	}
	
	/**
     * Performs the editing one record of FeeHeadings action.
     * @param mapping  The ActionMapping used to select this instance
     * @param form     The optional ActionForm bean for this request (if any)
     * @param request  The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return The forward to which control should be transferred,
     * @throws Exception if an exception occurs
     */
	
	public ActionForward editFeeHeadings(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		log.info("call of editFeeHeadings method in FeeHeadingsAction class.");
		FeeHeadingsForm feeHeadingsForm=(FeeHeadingsForm)form;
		ActionErrors errors = new ActionErrors();
	    ActionMessages messages = new ActionMessages();
	    errors = feeHeadingsForm.validate(mapping, request);
	    try{
	    	if(errors.isEmpty()){
	    		//errors are empty
	    		FeeHeadingTO feeHeadingTO =FeeHeadingsHandler.getInstance().editFeeHeadings(feeHeadingsForm.getFeeHeadingsId());
	    		feeHeadingsForm.setFeesName(feeHeadingTO.getName());
	    		feeHeadingsForm.setFeeGroup(String.valueOf(feeHeadingTO.getFeeGroupTO().getId()));
	    		request.setAttribute("feeHeadingsOperation","edit");
	    		HttpSession session=request.getSession(false);
	    		if(session == null){
	    			return mapping.findForward(CMSConstants.LOGIN_PAGE);
	    		}else{
		    		session.setAttribute("FNAME",feeHeadingsForm.getFeesName());
		    		session.setAttribute("FEEGROUP",feeHeadingsForm.getFeeGroup());
	    		}
	    	}else{
	    		//errors are present
				errors.add(messages);
				saveErrors(request, errors);
			}
	    }catch (BusinessException businessException) {
			log.info("Exception editFeeHeadings");
			String msgKey = super.handleBusinessException(businessException);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add(CMSConstants.MESSAGES, message);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception exception) {	
			String msg = super.handleApplicationException(exception);
			feeHeadingsForm.setErrorMessage(msg);
			feeHeadingsForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		getFeeHeadingDetails(feeHeadingsForm);
			
		log.info("end of editFeeHeadings method in FeeHeadingsAction class.");
		return mapping.findForward(CMSConstants.FEE_HEADINGS_ENTRY);
	}
	
	/**
     * Performs the updating of one record of FeeHeadings action.
     * @param mapping  The ActionMapping used to select this instance
     * @param form     The optional ActionForm bean for this request (if any)
     * @param request  The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return The forward to which control should be transferred,
     * @throws Exception if an exception occurs
     */
	
	public ActionForward updateFeeHeadings(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("call of updateFeeHeadings method in FeeHeadingsAction class.");
		FeeHeadingsForm feeHeadingsForm=(FeeHeadingsForm)form;
		ActionErrors errors = new ActionErrors();
	    ActionMessages messages = new ActionMessages();
	    errors = feeHeadingsForm.validate(mapping, request);
	    try{
	    	if(errors.isEmpty()){
	    		//errors are empty
	    		setUserId(request, feeHeadingsForm);
	    		int feeGroupId = Integer.valueOf(feeHeadingsForm.getFeeGroup());
	    		String feesName = feeHeadingsForm.getFeesName();
	    		HttpSession session=request.getSession(false);
	    		String fname = session.getAttribute("FNAME").toString();
	    		String fgroup = session.getAttribute("FEEGROUP").toString();
	    		if(!fname.equals(fname) && !fgroup.equals(feeHeadingsForm.getFeeGroup())){
	    			FeeHeading feeHeading=FeeHeadingsHandler.getInstance().isFeeHeadingNameExist(feeGroupId, feesName);
				if(feeHeading!=null && feeHeading.getIsActive() && feeHeading.getName().equals(feesName)){
					errors.add(CMSConstants.FEE_HEADING_NAME_EXIST,new ActionError(CMSConstants.FEE_HEADING_NAME_EXIST));
					saveErrors(request, errors);
				}else if(feeHeading!=null && !feeHeading.getIsActive()){
					errors.add(CMSConstants.FEE_HEADING_REACTIVATE,new ActionError(CMSConstants.FEE_HEADING_REACTIVATE));
					saveErrors(request, errors);
				}else{
	    		boolean isUpdated =FeeHeadingsHandler.getInstance().updateFeeHeadings(feeHeadingsForm);
	    		if(isUpdated){
	    			//if update is success.
	    			session.removeAttribute("FNAME");
	    			session.removeAttribute("FEEGROUP");
	    			ActionMessage message = new ActionMessage(CMSConstants.FEE_HEADING_UPDATESUCCESS);
					messages.add("messages", message);
					saveMessages(request, messages);
					feeHeadingsForm.clearAll();
					feeHeadingsForm.reset(mapping, request);
	    		}if(!isUpdated){
	    			//if update is failure.
	    			ActionMessage message = new ActionMessage(CMSConstants.FEE_HEADING_UPDATEFAILURE);
					messages.add("messages", message);
					saveMessages(request, messages);
					feeHeadingsForm.clearAll();
					feeHeadingsForm.reset(mapping, request);
	    		}
	    		}
	    		}else{
	    			boolean isUpdated =FeeHeadingsHandler.getInstance().updateFeeHeadings(feeHeadingsForm);
		    		if(isUpdated){
		    			//if update is success.
		    			session.removeAttribute("FNAME");
		    			session.removeAttribute("FEEGROUP");
		    			ActionMessage message = new ActionMessage(CMSConstants.FEE_HEADING_UPDATESUCCESS);
						messages.add("messages", message);
						saveMessages(request, messages);
						feeHeadingsForm.clearAll();
						feeHeadingsForm.reset(mapping, request);
		    		}if(!isUpdated){
		    			//if update is failure.
		    			ActionMessage message = new ActionMessage(CMSConstants.FEE_HEADING_UPDATEFAILURE);
						messages.add("messages", message);
						saveMessages(request, messages);
						feeHeadingsForm.clearAll();
						feeHeadingsForm.reset(mapping, request);
		    		}
	    		}
	    	}else{
	    		//errors are present
				errors.add(messages);
				saveErrors(request, errors);
			}
	    }catch (BusinessException businessException) {
			log.info("Exception updateFeeHeadings");
			String msgKey = super.handleBusinessException(businessException);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add(CMSConstants.MESSAGES, message);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception exception) {	
			String msg = super.handleApplicationException(exception);
			feeHeadingsForm.setErrorMessage(msg);
			feeHeadingsForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
	    // call of getFeeHeadingDetails method and setting to form.
		getFeeHeadingDetails(feeHeadingsForm);
			
		log.info("end of updateFeeHeadings method in FeeHeadingsAction class.");
		return mapping.findForward(CMSConstants.FEE_HEADINGS_ENTRY);
	}

	/**
     * Performs the deleting one record of FeeHeadings action.
     * @param mapping  The ActionMapping used to select this instance
     * @param form     The optional ActionForm bean for this request (if any)
     * @param request  The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return The forward to which control should be transferred,
     * @throws Exception if an exception occurs
     */
	
	public ActionForward deleteFeeHeadings(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("call of deleteFeeHeadings method in FeeHeadingsAction class.");
		FeeHeadingsForm feeHeadingsForm=(FeeHeadingsForm)form;
		ActionErrors errors = new ActionErrors();
	    ActionMessages messages = new ActionMessages();
	    errors = feeHeadingsForm.validate(mapping, request);
	    try{
	    	if(errors.isEmpty()){
	    		//errors are empty
	    		setUserId(request, feeHeadingsForm);
	    		boolean isDelete=FeeHeadingsHandler.getInstance().deleteFeeHeadings(feeHeadingsForm.getFeeHeadingsId(), feeHeadingsForm.getUserId());
				if(isDelete){
					//if delete is success.
					ActionMessage message = new ActionMessage(CMSConstants.FEE_HEADING_DELETESUCCESS);
					messages.add("messages", message);
					saveMessages(request, messages);
					feeHeadingsForm.clearAll();
					feeHeadingsForm.reset(mapping, request);
				}if(!isDelete){	
					// if delete is failure.
					ActionMessage message = new ActionMessage(CMSConstants.FEE_HEADING_DELETEFAILURE);
					messages.add("messages", message);
					saveMessages(request, messages);
					feeHeadingsForm.clearAll();
					feeHeadingsForm.reset(mapping, request);
				}
	    	}else{
	    		//errors are present
				errors.add(messages);
				saveErrors(request, errors);
			}
	    }catch (BusinessException businessException) {
			log.info("Exception deleteFeeHeadings");
			String msgKey = super.handleBusinessException(businessException);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add(CMSConstants.MESSAGES, message);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception exception) {	
			String msg = super.handleApplicationException(exception);
			feeHeadingsForm.setErrorMessage(msg);
			feeHeadingsForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
	    // 	call of getFeeHeadingDetails method and setting to form.
		getFeeHeadingDetails(feeHeadingsForm);
			
		log.info("end of deleteFeeHeadings method in FeeHeadingsAction class.");
		return mapping.findForward(CMSConstants.FEE_HEADINGS_ENTRY);
	}
	
	/**
     * Performs the reActivation of FeeHeadings action.
     * @param mapping  The ActionMapping used to select this instance
     * @param form     The optional ActionForm bean for this request (if any)
     * @param request  The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return The forward to which control should be transferred,
     * @throws Exception if an exception occurs
     */
	
	public ActionForward reActivateFeeHeadings(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("call of reActivateFeeHeadings method in FeeHeadingsAction class.");
		FeeHeadingsForm feeHeadingsForm=(FeeHeadingsForm)form;
		ActionErrors errors = new ActionErrors();
	    ActionMessages messages = new ActionMessages();
	    errors = feeHeadingsForm.validate(mapping, request);
	    try{
	    	//setting of user id and setting to form.
	    	setUserId(request, feeHeadingsForm);
	    	if(errors.isEmpty()){
	    		//errors are empty
	    		FeeHeadingsHandler.getInstance().reActivateFeeHeadings(feeHeadingsForm.getFeesName(), feeHeadingsForm.getUserId());
	    		ActionMessage message = new ActionMessage(CMSConstants.FEE_HEADING_REACTIVATESUCCESS);
				messages.add("messages", message);
				saveMessages(request, messages);
				feeHeadingsForm.clearAll();
	    	}else{
	    		//errors are present
				errors.add(messages);
				saveErrors(request, errors);
			}
	    	// call of getFeeHeadingDetails method and setting to form.
	    	getFeeHeadingDetails(feeHeadingsForm);
	    }catch (BusinessException businessException) {
			log.info("Exception reActivateFeeHeadings");
			String msgKey = super.handleBusinessException(businessException);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add(CMSConstants.MESSAGES, message);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception exception) {	
			String msg = super.handleApplicationException(exception);
			feeHeadingsForm.setErrorMessage(msg);
			feeHeadingsForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
	    log.info("end of reActivateFeeHeadings method in FeeHeadingsAction class.");
	    return mapping.findForward(CMSConstants.FEE_HEADINGS_ENTRY);
	}
	
	
	/**
	 * This method is used to load the details of FeeHeadings.
	 * @param feeHeadingsForm
	 * @throws Exception
	 */
	
	public void getFeeHeadingDetails(FeeHeadingsForm feeHeadingsForm) throws Exception {
		log.info("call of getFeeHeadingDetails method in FeeHeadingsAction class.");
		//getting the data of fee group and setting to list of type FeeGroupTO.
		List<FeeGroupTO> feeGroupToList=FeeGroupHandler.getInstance().getFeeGroups();
		feeHeadingsForm.setFeeGroupToList(feeGroupToList);
		//getting the data of fee heading and setting to list of type FeeHeadingTO.
		List<FeeHeadingTO> feeHeadingList = FeeHeadingsHandler.getInstance().getFeeHeadingDetails();
		feeHeadingsForm.setFeeHeadingList(feeHeadingList);
		log.info("end of getFeeHeadingDetails method in FeeHeadingsAction class.");
	}
}