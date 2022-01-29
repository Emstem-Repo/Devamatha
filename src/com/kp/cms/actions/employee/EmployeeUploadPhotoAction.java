package com.kp.cms.actions.employee;

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
import com.kp.cms.forms.employee.EmployeeUploadPhotoForm;
import com.kp.cms.handlers.employee.EmployeeUploadPhotoHandler;

public class EmployeeUploadPhotoAction extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(EmployeeUploadPhotoAction.class);
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initUploadEmployeePhotos(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		EmployeeUploadPhotoForm employeeUploadPhotoForm = (EmployeeUploadPhotoForm)form;
		try{
			employeeUploadPhotoForm.setFingerPrintId(null);
		}catch (Exception e) {
			log.error("Error occured in initUploadEmployeePhotos of EmployeeUploadPhotoAction", e);
		}
		return mapping.findForward(CMSConstants.UPLOAD_EMP_PHOTOS);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward uploadEmployeePhoto(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("entering into uploadEmployeePhoto  type action");	
		EmployeeUploadPhotoForm employeeUploadPhotoForm = (EmployeeUploadPhotoForm)form;
		 ActionErrors errors = employeeUploadPhotoForm.validate(mapping, request);
		ActionMessages messages = new ActionMessages();
		setUserId(request, employeeUploadPhotoForm);
		
		if(!errors.isEmpty()){
			saveErrors(request, errors);	
			return mapping.findForward(CMSConstants.UPLOAD_EMP_PHOTOS);
		}
		try{
			EmployeeUploadPhotoHandler employeeUploadPhotoHandler=EmployeeUploadPhotoHandler.getInstance();
			boolean isAdded=employeeUploadPhotoHandler.uploadPhotos(employeeUploadPhotoForm,request);
			if(isAdded){
				// success .
				ActionMessage message = new ActionMessage("knowledgepro.employee.uploademployeephoto.addsuccess");
				messages.add("messages", message);
				saveMessages(request, messages);
				employeeUploadPhotoForm.reset(mapping, request);
				employeeUploadPhotoForm.setFingerPrintId(null);
			}else {
				// failed
				errors.add("error", new ActionError("knowledgepro.employee.uploademployeephoto.addfailure"));
				saveErrors(request, errors);
			}
			
		}catch (Exception e) {
			log.error("Error occured in uploadEmployeePhotos of EmployeeUploadPhotoAction", e);
			String msg = super.handleApplicationException(e);
			if(msg.equals("The Images name should be fingerPrintId....")){
				ActionMessage message = new ActionMessage("knowledgepro.employee.uploademployeephoto.fingerprintid");
				messages.add("messages", message);
				saveMessages(request, messages);
				return  mapping.findForward(CMSConstants.UPLOAD_EMP_PHOTOS);
			}
			if(e instanceof NullPointerException){
				ActionMessage message = new ActionMessage("knowledgepro.employee.uploademployeephoto.NoPhotos");
				messages.add("messages", message);
				saveMessages(request, messages);
				return  mapping.findForward(CMSConstants.UPLOAD_EMP_PHOTOS);	
			}
			employeeUploadPhotoForm.setErrorMessage(msg);
			employeeUploadPhotoForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.UPLOAD_EMP_PHOTOS);
	}
}
