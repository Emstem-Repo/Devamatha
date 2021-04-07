package com.kp.cms.actions.admission;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import com.kp.cms.forms.admission.ReceivedThroughForm;
import com.kp.cms.handlers.admission.ReceivedThroughHandler;
import com.kp.cms.to.admission.ReceivedThroughTo;

public class ReceivedThroughAction extends BaseDispatchAction{
	private static final Log log = LogFactory.getLog(ReceivedThroughAction.class);
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initReceivedThrough(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// TODO Auto-generated method stub
		ReceivedThroughForm receivedThroughForm = (ReceivedThroughForm)form;
		receivedThroughForm.reset();
		setRequestedDataToForm(receivedThroughForm);
		return mapping.findForward(CMSConstants.INIT_RECEIVED_THROUGH);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addReceivedThrough(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// TODO Auto-generated method stub
		ReceivedThroughForm receivedThroughForm = (ReceivedThroughForm)form;
		setUserId(request, receivedThroughForm);
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = receivedThroughForm.validate(mapping, request);
		HttpSession session=request.getSession();
		String mode ="add";
		if (errors.isEmpty()) {
		   try{
			   boolean isAdded = false;
			   boolean isDuplicate = ReceivedThroughHandler.getInstance().duplicateCheck(receivedThroughForm, errors, session);
			   if(!isDuplicate){
				   isAdded = ReceivedThroughHandler.getInstance().addReceivedThrough(receivedThroughForm,mode);
				   if (isAdded) {
						//ActionMessage message = new ActionMessage( "knowledgepro.employee.payScale.addsuccess");// Adding // added // message.
						messages.add(CMSConstants.MESSAGES, new ActionMessage( "knowledgepro.admission.receivedThrough.addsuccess"));
						saveMessages(request, messages);
						receivedThroughForm.reset();
					} else {
						errors.add("error", new ActionError( "knowledgepro.admission.receivedThrough.addfailure"));
						addErrors(request, errors);
						receivedThroughForm.reset();
					}
			   }else{
				   addErrors(request, errors);
			   }
		   }catch(Exception exception){
			   log.error("Error occured in caste Entry Action", exception);
				String msg = super.handleApplicationException(exception);
				receivedThroughForm.setErrorMessage(msg);
				receivedThroughForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
		   }
	    }else {
	    	setRequestedDataToForm(receivedThroughForm);
			saveErrors(request, errors);
			return mapping.findForward(CMSConstants.INIT_RECEIVED_THROUGH);
		}
		setRequestedDataToForm(receivedThroughForm);
		return mapping.findForward(CMSConstants.INIT_RECEIVED_THROUGH);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward editReceivedThrough(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ReceivedThroughForm receivedThroughForm = (ReceivedThroughForm)form;
		try {
			ReceivedThroughHandler.getInstance().editReceivedThrough(receivedThroughForm);
			request.setAttribute("operation", "edit");
			//request.setAttribute("Update", "Update");// setting update attribute
			log.debug("Leaving editReceivedThrough ");
		} catch (Exception e) {
			log.error("error in editReceivedThrough...", e);
			String msg = super.handleApplicationException(e);
			receivedThroughForm.setErrorMessage(msg);
			receivedThroughForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.INIT_RECEIVED_THROUGH);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateReceivedThrough(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ReceivedThroughForm receivedThroughForm = (ReceivedThroughForm)form;
		HttpSession session=request.getSession();
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = receivedThroughForm.validate(mapping, request);
		boolean isUpdated = false;
		String mode = "update";
        if(errors.isEmpty()){
		try {
			// This condition works when reset button will click in update mode
			if (isCancelled(request)) {
				receivedThroughForm.reset(mapping, request);
				String formName = mapping.getName();
				request.getSession().setAttribute(CMSConstants.FORMNAME,
						formName);
				ReceivedThroughHandler.getInstance().editReceivedThrough(receivedThroughForm);
				request.setAttribute("operation", "edit");
				return mapping.findForward(CMSConstants.INIT_RECEIVED_THROUGH);
			}
			setUserId(request, receivedThroughForm); // setting user id to update
			 boolean isDuplicate = ReceivedThroughHandler.getInstance().duplicateCheck(receivedThroughForm, errors, session);
			if(!isDuplicate){
			isUpdated = ReceivedThroughHandler.getInstance().addReceivedThrough(receivedThroughForm,mode);
			if (isUpdated) {
				ActionMessage message = new ActionMessage(
						"knowledgepro.admission.receivedThrough.update.success");
				messages.add("messages", message);
				saveMessages(request, messages);
				//employeeResumeForm.reset(mapping, request);
				receivedThroughForm.reset();
			} else {
				errors.add("error", new ActionError(
						"knowledgepro.admission.receivedThrough.update.failed"));
				//saveErrors(request, errors);
				addErrors(request, errors);
				receivedThroughForm.reset();
			}}else{
				request.setAttribute("payScaleOperation", "edit");
				errors.add("error", new ActionError("knowledgepro.employee.PayScale.duplicate"));
				addErrors(request, errors);
				//payScaleForm.reset();
			}
		} catch (Exception e) {
			log.error("Error occured in edit ReceivedThrough", e);
			String msg = super.handleApplicationException(e);
			receivedThroughForm.setErrorMessage(msg);
			receivedThroughForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}}else{
			saveErrors(request, errors);
			setRequestedDataToForm(receivedThroughForm);
			request.setAttribute("operation", "edit");
			return mapping.findForward(CMSConstants.INIT_RECEIVED_THROUGH);
		}
        setRequestedDataToForm(receivedThroughForm);
		return mapping.findForward(CMSConstants.INIT_RECEIVED_THROUGH);

	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteReceivedThrough(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ReceivedThroughForm receivedThroughForm = (ReceivedThroughForm)form;
		ActionMessages messages = new ActionMessages();

		try {
			boolean isDeleted = ReceivedThroughHandler.getInstance().deleteReceivedThrough(receivedThroughForm);
			if (isDeleted) {
				ActionMessage message = new ActionMessage(
						"knowledgepro.admission.receivedThrough.delete.success");
				messages.add("messages", message);
				saveMessages(request, messages);
			} else {
				ActionMessage message = new ActionMessage(
						"knowledgepro.admission.receivedThrough.delete.failed");
				messages.add("messages", message);
				saveMessages(request, messages);
			}
			receivedThroughForm.reset();
		} catch (Exception e) {
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				receivedThroughForm.setErrorMessage(msg);
				receivedThroughForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				String msg = super.handleApplicationException(e);
				receivedThroughForm.setErrorMessage(msg);
				receivedThroughForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		}
		setRequestedDataToForm(receivedThroughForm);
		log.debug("Action class. Delete ReceivedThrough ");
		return mapping.findForward(CMSConstants.INIT_RECEIVED_THROUGH);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward reactivateReceivedThrough(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ReceivedThroughForm receivedThroughForm = (ReceivedThroughForm)form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		HttpSession session=request.getSession();
		try {
			setUserId(request, receivedThroughForm);
			boolean isReactivate;
			//int olddocTypeId =Integer.parseInt(documentExamEntryForm.getDocTypeId());
			//String oldExamName = documentExamEntryForm.getExamName().trim();
			String userId = receivedThroughForm.getUserId();
			String duplicateId=session.getAttribute("ReactivateId").toString();
			receivedThroughForm.setId(Integer.parseInt(duplicateId));
			isReactivate = ReceivedThroughHandler.getInstance().reactivateReceivedThrough(receivedThroughForm, userId);
			if(isReactivate){
				messages.add(CMSConstants.MESSAGES,new ActionMessage("knowledgepro.admission.receivedThrough.details.activate"));
				receivedThroughForm.reset();
				saveMessages(request, messages);
			}
			else{
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.admission.receivedThrough.details.activate.failed"));
				addErrors(request, errors);
			}
			
		} catch (Exception e) {
			log.error("Error occured in reactivateReceivedThrough of ReceivedThroughAction", e);
			String msg = super.handleApplicationException(e);
			receivedThroughForm.setErrorMessage(msg);
			receivedThroughForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		setRequestedDataToForm(receivedThroughForm);
		log.info("Leaving into reactivateReceivedThrough of ReceivedThroughAction");
		return mapping.findForward(CMSConstants.INIT_RECEIVED_THROUGH); 
	}
	/**
	 * @param receivedThroughForm
	 * @throws Exception
	 */
	public void setRequestedDataToForm(ReceivedThroughForm receivedThroughForm) throws Exception {
		// 1. Set the ReceivedThrough list
		List<ReceivedThroughTo> receivedThroughList = ReceivedThroughHandler.getInstance().getReceivedThroughList();
		receivedThroughForm.setReceivedThroughList(receivedThroughList);
	}
}
