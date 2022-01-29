package com.kp.cms.actions.employee;

import java.util.HashMap;
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
import com.kp.cms.forms.employee.BiometricDetailsForm;
import com.kp.cms.forms.employee.EventLocationBiometricDetailsForm;
import com.kp.cms.handlers.employee.BiometricDetailsHandler;
import com.kp.cms.handlers.employee.EventLoactionBiometricDetailsHandler;
import com.kp.cms.to.employee.EventLoactionBiometricDetailsTo;

public class EventLoactionBiometricDetailsAction extends BaseDispatchAction{
	private static final Log log = LogFactory.getLog(EventLoactionBiometricDetailsAction.class);
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initEventLoactionBiometricDetailsAction(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		EventLocationBiometricDetailsForm eventLocationBiometricDetailsForm = (EventLocationBiometricDetailsForm)form;
		eventLocationBiometricDetailsForm.reset();
		setRequestedDataToForm(eventLocationBiometricDetailsForm);
		return mapping.findForward(CMSConstants.EVENT_LOCATION_BIOMETRIC_DETAILS);
	}
	public void setRequestedDataToForm(EventLocationBiometricDetailsForm eventLocationBiometricDetailsForm) throws Exception {
		// 1. Set the Biometric list
		List<EventLoactionBiometricDetailsTo> biometricTo=EventLoactionBiometricDetailsHandler.getInstance().getBiometricList();
		eventLocationBiometricDetailsForm.setBiometricList(biometricTo);
		EventLoactionBiometricDetailsHandler.getInstance().getEventLocationData(eventLocationBiometricDetailsForm);
	}
	public ActionForward addBiometricDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		EventLocationBiometricDetailsForm eventLocationBiometricDetailsForm = (EventLocationBiometricDetailsForm)form;
		setUserId(request, eventLocationBiometricDetailsForm);
		ActionMessages messages = new ActionMessages();
		 ActionErrors errors = eventLocationBiometricDetailsForm.validate(mapping, request);
		if(errors.isEmpty()) {
			try{
				boolean isAdded = false;
				boolean isDuplicate=EventLoactionBiometricDetailsHandler.getInstance().duplicateCheck(eventLocationBiometricDetailsForm);
				if(!isDuplicate){
				isAdded = EventLoactionBiometricDetailsHandler.getInstance()
						.addBimetricDetails(eventLocationBiometricDetailsForm);
				if (isAdded){
					//ActionMessage message = new ActionMessage( "knowledgepro.employee.payScale.addsuccess");// Adding // added // message.
					messages.add(CMSConstants.MESSAGES, new ActionMessage( "knowledgepro.employee.biometric.event.location.added"));
					saveMessages(request, messages);
					setRequestedDataToForm(eventLocationBiometricDetailsForm);
					eventLocationBiometricDetailsForm.reset();
				} else {
					errors.add("error", new ActionError( "knowledgepro.employee.biometric.event.location.added.failed"));
					addErrors(request, errors);
					eventLocationBiometricDetailsForm.reset();
					setRequestedDataToForm(eventLocationBiometricDetailsForm);
				}
				}else{
					
					setRequestedDataToForm(eventLocationBiometricDetailsForm);
					//payScaleForm.reset();
					return mapping.findForward(CMSConstants.EVENT_LOCATION_BIOMETRIC_DETAILS);
				}
			} catch (Exception exception) {
				log.error("Error occured in EventLoactionBiometricDetailsAction Action", exception);
				String msg = super.handleApplicationException(exception);
				eventLocationBiometricDetailsForm.setErrorMessage(msg);
				eventLocationBiometricDetailsForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		} else {
			saveErrors(request, errors);
			setRequestedDataToForm(eventLocationBiometricDetailsForm);
			return mapping.findForward(CMSConstants.EVENT_LOCATION_BIOMETRIC_DETAILS);
		}
		return mapping.findForward(CMSConstants.EVENT_LOCATION_BIOMETRIC_DETAILS);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward editBiometricDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		EventLocationBiometricDetailsForm eventLocationBiometricDetailsForm = (EventLocationBiometricDetailsForm)form;
		log.debug("Entering ediBiometricDetails ");
		try{
			EventLoactionBiometricDetailsHandler.getInstance().editBiometricDetails(
					eventLocationBiometricDetailsForm);
			request.setAttribute("Operation", "edit");
			//request.setAttribute("Update", "Update");// setting update attribute
			log.debug("Leaving ediBiometricDetails ");
		}catch (Exception e) {
			log.error("error in editing BiometricDetails...", e);
			String msg = super.handleApplicationException(e);
			eventLocationBiometricDetailsForm.setErrorMessage(msg);
			eventLocationBiometricDetailsForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.EVENT_LOCATION_BIOMETRIC_DETAILS);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateBiometricDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.debug("Enter: updatePayScale Action");
		EventLocationBiometricDetailsForm eventLocationBiometricDetailsForm = (EventLocationBiometricDetailsForm)form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = eventLocationBiometricDetailsForm.validate(mapping, request);
		boolean isUpdated = false;
        if(errors.isEmpty()){
		try {
			// This condition works when reset button will click in update mode
			if (isCancelled(request)) {
				eventLocationBiometricDetailsForm.reset(mapping, request);
				String formName = mapping.getName();
				request.getSession().setAttribute(CMSConstants.FORMNAME,
						formName);
				EventLoactionBiometricDetailsHandler.getInstance().editBiometricDetails(
						eventLocationBiometricDetailsForm);
				setRequestedDataToForm(eventLocationBiometricDetailsForm);
				request.setAttribute("Operation", "edit");
				return mapping.findForward(CMSConstants.EVENT_LOCATION_BIOMETRIC_DETAILS);
			}
			setUserId(request, eventLocationBiometricDetailsForm); // setting user id to update
			boolean isDuplicate=EventLoactionBiometricDetailsHandler.getInstance().duplicateCheckInUpdateMode(eventLocationBiometricDetailsForm);
			if(!isDuplicate){
			isUpdated = EventLoactionBiometricDetailsHandler.getInstance()
					.updateBiometricDetails(eventLocationBiometricDetailsForm);
			if (isUpdated) {
				ActionMessage message = new ActionMessage(
						"knowledgepro.employee.biometric.event.location.update");
				messages.add("messages", message);
				saveMessages(request, messages);
				//employeeResumeForm.reset(mapping, request);
				eventLocationBiometricDetailsForm.reset();
			} else {
				errors.add("error", new ActionError(
						"knowledgepro.employee.biometric.event.location.update.failed"));
				//saveErrors(request, errors);
				addErrors(request, errors);
				eventLocationBiometricDetailsForm.reset();
			}}
		} catch (Exception e) {
			log.error("Error occured in edit Biometric Details", e);
			String msg = super.handleApplicationException(e);
			eventLocationBiometricDetailsForm.setErrorMessage(msg);
			eventLocationBiometricDetailsForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}}else{
			saveErrors(request, errors);
			setRequestedDataToForm(eventLocationBiometricDetailsForm);
			request.setAttribute("Operation", "edit");
			return mapping.findForward(CMSConstants.EVENT_LOCATION_BIOMETRIC_DETAILS);
		}
		setRequestedDataToForm(eventLocationBiometricDetailsForm);
		log.debug("Exit: action class updateBiometricDetails");
		return mapping.findForward(CMSConstants.EVENT_LOCATION_BIOMETRIC_DETAILS);

	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteBiometricDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.debug("Entering ");
		EventLocationBiometricDetailsForm eventLocationBiometricDetailsForm = (EventLocationBiometricDetailsForm)form;
		ActionMessages messages = new ActionMessages();

		try {
			boolean isDeleted = EventLoactionBiometricDetailsHandler.getInstance()
					.deleteBiometricDetails(eventLocationBiometricDetailsForm);
			if (isDeleted) {
				ActionMessage message = new ActionMessage(
						"knowledgepro.employee.biometric.event.location.deleteSuccess");
				messages.add("messages", message);
				saveMessages(request, messages);
			} else {
				ActionMessage message = new ActionMessage(
						"knowledgepro.employee.biometric.event.location.deleteFailure");
				messages.add("messages", message);
				saveMessages(request, messages);
			}
			setRequestedDataToForm(eventLocationBiometricDetailsForm);
		} catch (Exception e) {
			log.error("error submit biometric...", e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				eventLocationBiometricDetailsForm.setErrorMessage(msg);
				eventLocationBiometricDetailsForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				String msg = super.handleApplicationException(e);
				eventLocationBiometricDetailsForm.setErrorMessage(msg);
				eventLocationBiometricDetailsForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		}
		log.debug("Action class. Delete BiometricDetails ");
		return mapping.findForward(CMSConstants.EVENT_LOCATION_BIOMETRIC_DETAILS);
	}
}
