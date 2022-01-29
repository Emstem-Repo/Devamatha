package com.kp.cms.actions.hostel;

import java.util.ArrayList;
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
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.employee.BiometricDetailsForm;
import com.kp.cms.forms.hostel.AssignRoomMasterForm;
import com.kp.cms.forms.hostel.AvailableSeatsForm;
import com.kp.cms.forms.hostel.BiometricForm;
import com.kp.cms.forms.hostel.HlAdmissionForm;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.employee.BiometricDetailsHandler;
import com.kp.cms.handlers.hostel.AvailableSeatsHandler;
import com.kp.cms.handlers.hostel.BiometricHandler;
import com.kp.cms.handlers.hostel.HostelEntryHandler;
import com.kp.cms.to.employee.BiometricDetailsTO;
import com.kp.cms.to.hostel.HostelTO;

public class BiometricAction  extends BaseDispatchAction{
	private static final Log log=LogFactory.getLog(BiometricAction.class);
	/**
	 * initBiometric() to display initial fields
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initBiometric(ActionMapping mapping,ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception
	{

		log.debug("Entering initBiometric ");
		BiometricForm biometricForm = (BiometricForm) form;
		try {
			String formName = mapping.getName();
			request.getSession().setAttribute(CMSConstants.FORMNAME, formName);
			setDataToForm(biometricForm);
			setUserId(request, biometricForm);
			biometricForm.reset();
		} catch (Exception e) {
			log.error("error initBiometric...", e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				biometricForm.setErrorMessage(msg);
				biometricForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
	}
	log.debug("Leaving initAssignRoomMaster ");
	setRequestedDataToForm(biometricForm);
		return mapping.findForward(CMSConstants.INIT_BIOMETRIC);
	}
	/**
	 * @param roomForm
	 * @throws Exception
	 */
	private void setDataToForm(BiometricForm biometricForm) throws Exception{
		 List<HostelTO> hostelList = HostelEntryHandler.getInstance().getHostelDetails();
		biometricForm.setHostelList(hostelList);
		if(biometricForm.getHostelId() != null && !biometricForm.getHostelId().trim().isEmpty()){
			biometricForm.setBlockMap(HostelEntryHandler.getInstance().getBlocks(biometricForm.getHostelId()));
		}
		if(biometricForm.getBlock() != null && !biometricForm.getBlock().trim().isEmpty()){
			biometricForm.setUnitMap(HostelEntryHandler.getInstance().getUnits(biometricForm.getBlock()));
		}
	}
	
	/**
	 * adding the biometric details
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addBiometricDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BiometricForm biometricForm = (BiometricForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = biometricForm.validate(mapping, request);
		try {
			if (errors.isEmpty()) {
				setUserId(request, biometricForm);
				boolean duplicatecheck = BiometricHandler.getInstance().duplicateCheck(biometricForm);
				if (!duplicatecheck){
					boolean isAdded = BiometricHandler.getInstance().addBiometricDetails(biometricForm);
					if (isAdded) {
						ActionMessage message = new ActionError( "knowledgepro.employee.biometric.added");
						messages.add("messages", message);
						saveMessages(request, messages);
						biometricForm.reset();
					} else {
						biometricForm.reset();
						errors .add( "error", new ActionError( "knowledgepro.employee.biometric.added.failed"));
						saveErrors(request, errors);
					}
				}else {
						ActionMessage message = new ActionError( "knowledgepro.employee.biometric.duplicate");
						messages.add("messages", message);
						saveMessages(request, messages);
					}
				} else {
				saveErrors(request, errors);
			}

		} catch (BusinessException businessException) {
			log.info("Exception submitOpenConnectionDetails");
			String msgKey = super.handleBusinessException(businessException);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add(CMSConstants.MESSAGES, message);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			biometricForm.setErrorMessage(msg);
			biometricForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		setDataToForm(biometricForm);
		setRequestedDataToForm(biometricForm);
		return mapping .findForward(CMSConstants.INIT_BIOMETRIC);
	}
	/**
	 * @param biometricDetailsForm
	 * @throws Exception
	 */
	public void setRequestedDataToForm(BiometricForm biometricForm) throws Exception {
		// 1. Set the Biometric list
		List<BiometricDetailsTO> biometricTo=BiometricHandler.getInstance().getBiometricList();
		biometricForm.setBiometricList(biometricTo);
	}
	/**
	 * editing the biometric details
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
		BiometricForm biometricForm = (BiometricForm) form;
		log.debug("Entering ediBiometricDetails ");
		try{
			BiometricHandler.getInstance().editBiometricDetails(biometricForm);
			request.setAttribute("Operation", "edit");
			//request.setAttribute("Update", "Update");// setting update attribute
			log.debug("Leaving ediBiometricDetails ");
		}catch (Exception e) {
			log.error("error in editing BiometricDetails...", e);
			String msg = super.handleApplicationException(e);
			biometricForm.setErrorMessage(msg);
			biometricForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		setDataToForm(biometricForm);
		return mapping.findForward(CMSConstants.INIT_BIOMETRIC);
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
		BiometricForm biometricForm = (BiometricForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = biometricForm.validate(mapping, request);
		boolean isUpdated = false;
        if(errors.isEmpty()){
		try {
			// This condition works when reset button will click in update mode
			if (isCancelled(request)) {
				biometricForm.reset(mapping, request);
				String formName = mapping.getName();
				request.getSession().setAttribute(CMSConstants.FORMNAME,formName);
				BiometricHandler.getInstance().editBiometricDetails(biometricForm);
				setDataToForm(biometricForm);
				request.setAttribute("Operation", "edit");
				return mapping.findForward(CMSConstants.INIT_BIOMETRIC);
			}
			setUserId(request, biometricForm); // setting user id to update
			boolean isDuplicate=BiometricHandler.getInstance().duplicateCheck(biometricForm);
			if(!isDuplicate){
			isUpdated = BiometricHandler.getInstance().updateBiometricDetails(biometricForm);
			if (isUpdated) {
				ActionMessage message = new ActionMessage("knowledgepro.employee.biometric.update");
				messages.add("messages", message);
				saveMessages(request, messages);
				//employeeResumeForm.reset(mapping, request);
				biometricForm.reset();
			} else {
				request.setAttribute("Operation", "edit");
				errors.add("error", new ActionError(
						"knowledgepro.employee.biometric.update.failed"));
				//saveErrors(request, errors);
				addErrors(request, errors);
				biometricForm.reset();
			}}
		else{
			request.setAttribute("Operation", "edit");
				errors.add("error", new ActionError("knowledgepro.employee.biometric.duplicate"));
				addErrors(request, errors);
			}
		} catch (Exception e) {
			log.error("Error occured in edit Biometric Details", e);
			String msg = super.handleApplicationException(e);
			biometricForm.setErrorMessage(msg);
			biometricForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}}else{
			saveErrors(request, errors);
			setRequestedDataToForm(biometricForm);
			request.setAttribute("Operation", "edit");
			return mapping.findForward(CMSConstants.INIT_BIOMETRIC);
		}
		setRequestedDataToForm(biometricForm);
		log.debug("Exit: action class updateBiometricDetails");
		return mapping.findForward(CMSConstants.INIT_BIOMETRIC);

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
		BiometricForm biometricForm = (BiometricForm) form;
		ActionMessages messages = new ActionMessages();

		try {
			boolean isDeleted = BiometricHandler.getInstance().deleteBiometricDetails(biometricForm);
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
			setRequestedDataToForm(biometricForm);
		} catch (Exception e) {
			log.error("error submit biometric...", e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				biometricForm.setErrorMessage(msg);
				biometricForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				String msg = super.handleApplicationException(e);
				biometricForm.setErrorMessage(msg);
				biometricForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		}
		log.debug("Action class. Delete BiometricDetails ");
		return mapping.findForward(CMSConstants.INIT_BIOMETRIC);
	}
}
