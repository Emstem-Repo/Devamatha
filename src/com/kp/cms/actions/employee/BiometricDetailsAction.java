package com.kp.cms.actions.employee;

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
import com.kp.cms.forms.employee.EmpLeaveAllotmentForm;
import com.kp.cms.handlers.employee.BiometricDetailsHandler;
import com.kp.cms.handlers.employee.EmpLeaveAllotmentHandler;
import com.kp.cms.to.employee.BiometricDetailsTO;
import com.kp.cms.to.employee.EmpLeaveAllotTO;

public class BiometricDetailsAction extends BaseDispatchAction{
	private static final Log log = LogFactory.getLog(BiometricDetailsAction.class);
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initBiometricDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BiometricDetailsForm biometricDetailsForm = (BiometricDetailsForm) form;
		biometricDetailsForm.reset();
		setRequestedDataToForm(biometricDetailsForm);
		return mapping.findForward(CMSConstants.EMPLOYEE_BIOMETRIC_DETAILS);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addBiometricDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		BiometricDetailsForm biometricDetailsForm = (BiometricDetailsForm) form;
		setUserId(request, biometricDetailsForm);
		ActionMessages messages = new ActionMessages();
		 ActionErrors errors = biometricDetailsForm.validate(mapping, request);
		if(errors.isEmpty()) {
			try{
				boolean isAdded = false;
				boolean isDuplicate=BiometricDetailsHandler.getInstance().duplicateCheck(biometricDetailsForm);
				if(!isDuplicate){
				isAdded = BiometricDetailsHandler.getInstance()
						.addBimetricDetails(biometricDetailsForm);
				if (isAdded){
					//ActionMessage message = new ActionMessage( "knowledgepro.employee.payScale.addsuccess");// Adding // added // message.
					messages.add(CMSConstants.MESSAGES, new ActionMessage( "knowledgepro.employee.biometric.added"));
					saveMessages(request, messages);
					setRequestedDataToForm(biometricDetailsForm);
					biometricDetailsForm.reset();
				} else {
					errors.add("error", new ActionError( "knowledgepro.employee.biometric.added.failed"));
					addErrors(request, errors);
					biometricDetailsForm.reset();
					setRequestedDataToForm(biometricDetailsForm);
				}
				}else{
					errors.add("error", new ActionError("knowledgepro.employee.biometric.duplicate"));
					addErrors(request, errors);
					setRequestedDataToForm(biometricDetailsForm);
					//payScaleForm.reset();
				}
			} catch (Exception exception) {
				log.error("Error occured in BiometricDetails Action", exception);
				String msg = super.handleApplicationException(exception);
				biometricDetailsForm.setErrorMessage(msg);
				biometricDetailsForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		} else {
			saveErrors(request, errors);
			setRequestedDataToForm(biometricDetailsForm);
			return mapping.findForward(CMSConstants.EMPLOYEE_BIOMETRIC_DETAILS);
		}
		return mapping.findForward(CMSConstants.EMPLOYEE_BIOMETRIC_DETAILS);
	}
	/**
	 * @param biometricDetailsForm
	 * @throws Exception
	 */
	public void setRequestedDataToForm(BiometricDetailsForm biometricDetailsForm) throws Exception {
		// 1. Set the Biometric list
		List<BiometricDetailsTO> biometricTo=BiometricDetailsHandler.getInstance().getBiometricList(biometricDetailsForm);
		biometricDetailsForm.setBiometricList(biometricTo);
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
		BiometricDetailsForm biometricDetailsForm = (BiometricDetailsForm) form;
		log.debug("Entering ediBiometricDetails ");
		try{
			BiometricDetailsHandler.getInstance().editBiometricDetails(
					biometricDetailsForm);
			request.setAttribute("Operation", "edit");
			//request.setAttribute("Update", "Update");// setting update attribute
			log.debug("Leaving ediBiometricDetails ");
		}catch (Exception e) {
			log.error("error in editing BiometricDetails...", e);
			String msg = super.handleApplicationException(e);
			biometricDetailsForm.setErrorMessage(msg);
			biometricDetailsForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.EMPLOYEE_BIOMETRIC_DETAILS);
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
		BiometricDetailsForm biometricDetailsForm = (BiometricDetailsForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = biometricDetailsForm.validate(mapping, request);
		boolean isUpdated = false;
        if(errors.isEmpty()){
		try {
			// This condition works when reset button will click in update mode
			if (isCancelled(request)) {
				biometricDetailsForm.reset(mapping, request);
				String formName = mapping.getName();
				request.getSession().setAttribute(CMSConstants.FORMNAME,
						formName);
				BiometricDetailsHandler.getInstance().editBiometricDetails(
						biometricDetailsForm);
				setRequestedDataToForm(biometricDetailsForm);
				request.setAttribute("Operation", "edit");
				return mapping.findForward(CMSConstants.EMPLOYEE_BIOMETRIC_DETAILS);
			}
			setUserId(request, biometricDetailsForm); // setting user id to update
			boolean isDuplicate=BiometricDetailsHandler.getInstance().duplicateCheck(biometricDetailsForm);
			if(!isDuplicate){
			isUpdated = BiometricDetailsHandler.getInstance()
					.updateBiometricDetails(biometricDetailsForm);
			if (isUpdated) {
				ActionMessage message = new ActionMessage(
						"knowledgepro.employee.biometric.update");
				messages.add("messages", message);
				saveMessages(request, messages);
				//employeeResumeForm.reset(mapping, request);
				biometricDetailsForm.reset();
			} else {
				errors.add("error", new ActionError(
						"knowledgepro.employee.biometric.update.failed"));
				//saveErrors(request, errors);
				addErrors(request, errors);
				biometricDetailsForm.reset();
			}}
		else{
				errors.add("error", new ActionError("knowledgepro.employee.biometric.duplicate"));
				addErrors(request, errors);
			}
		} catch (Exception e) {
			log.error("Error occured in edit Biometric Details", e);
			String msg = super.handleApplicationException(e);
			biometricDetailsForm.setErrorMessage(msg);
			biometricDetailsForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}}else{
			saveErrors(request, errors);
			setRequestedDataToForm(biometricDetailsForm);
			request.setAttribute("Operation", "edit");
			return mapping.findForward(CMSConstants.EMPLOYEE_BIOMETRIC_DETAILS);
		}
		setRequestedDataToForm(biometricDetailsForm);
		log.debug("Exit: action class updateBiometricDetails");
		return mapping.findForward(CMSConstants.EMPLOYEE_BIOMETRIC_DETAILS);

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
		BiometricDetailsForm biometricDetailsForm = (BiometricDetailsForm) form;
		ActionMessages messages = new ActionMessages();

		try {
			boolean isDeleted = BiometricDetailsHandler.getInstance()
					.deleteBiometricDetails(biometricDetailsForm);
			if (isDeleted) {
				ActionMessage message = new ActionMessage(
						"knowledgepro.employee.leave.allotment.delete");
				messages.add("messages", message);
				saveMessages(request, messages);
			} else {
				ActionMessage message = new ActionMessage(
						"knowledgepro.employee.leave.allotment.delete.failed");
				messages.add("messages", message);
				saveMessages(request, messages);
			}
			setRequestedDataToForm(biometricDetailsForm);
		} catch (Exception e) {
			log.error("error submit biometric...", e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				biometricDetailsForm.setErrorMessage(msg);
				biometricDetailsForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				String msg = super.handleApplicationException(e);
				biometricDetailsForm.setErrorMessage(msg);
				biometricDetailsForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		}
		log.debug("Action class. Delete BiometricDetails ");
		return mapping.findForward(CMSConstants.EMPLOYEE_BIOMETRIC_DETAILS);
	}
}
