package com.kp.cms.actions.employee;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
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
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.employee.EducationMasterForm;
import com.kp.cms.handlers.employee.EducationMasterHandler;
import com.kp.cms.to.admin.SingleFieldMasterTO;
import com.kp.cms.to.employee.EmpEducationMasterTO;

import org.apache.commons.logging.LogFactory;

@SuppressWarnings("deprecation")
public class EducationMasterAction extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(EducationMasterAction.class);
	/**
	 * setting qualification & education list to request
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return forward to
	 *         EDUCATION_MASTER
	 * @throws Exception
	 */
	public ActionForward initEducationMaster(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
									HttpServletResponse response) throws Exception {
		log.debug("Entering initEducationMaster ");
		EducationMasterForm educationMasterForm = (EducationMasterForm) form;
		try {
			String formName = mapping.getName();
			request.getSession().setAttribute(CMSConstants.FORMNAME, formName);
			setQualificationListToRequest(request);
			setEducationListRequest(request);
			setUserId(request, educationMasterForm);
			initFields(educationMasterForm);
		} catch (Exception e) {
			log.error("error initEducationMaster...", e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				educationMasterForm.setErrorMessage(msg);
				educationMasterForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
	
		log.debug("Leaving initEducationMaster ");
	
		return mapping.findForward(CMSConstants.EDUCATION_MASTER);
	}
	
	/**
	 * Method to set all active Qualification to the request
	 * @param request
	 * @throws Exception
	 */
	public void setQualificationListToRequest(HttpServletRequest request) throws Exception{
		List<SingleFieldMasterTO> qualificationList = EducationMasterHandler.getInstance().getQualification();
		
		if( qualificationList != null ){
			request.setAttribute("qualificationList", qualificationList);
		}
	}
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 *            This will add new  education level
	 * @return to mapping EDUCATION_MASTER
	 * @throws Exception
	 */
	public ActionForward addEducationMaster(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
									HttpServletResponse response) throws Exception {

		log.debug("inside addEducationMaster Action");
		EducationMasterForm educationMasterForm = (EducationMasterForm) form;
		educationMasterForm.setId(0);
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = educationMasterForm.validate(mapping, request);
		boolean isAdded = false;
		try {
			if (!errors.isEmpty()) {
				saveErrors(request, errors);
				setQualificationListToRequest(request);
				setEducationListRequest(request);
				return mapping.findForward(CMSConstants.EDUCATION_MASTER);
			}
			isAdded = EducationMasterHandler.getInstance().addEducationMaster(educationMasterForm, "add"); 
			setQualificationListToRequest(request);
			setEducationListRequest(request);
		} catch (DuplicateException e1) {
			errors.add("error", new ActionError("knowledgepro.employee.education.master.exists"));
			saveErrors(request, errors);
			setEducationListRequest(request);
			setQualificationListToRequest(request);
			return mapping.findForward(CMSConstants.EDUCATION_MASTER);
		} catch (ReActivateException e1) {
			errors.add("error", new ActionError(CMSConstants.EDUCATION_MASTER_EXIST_REACTIVATE));
			saveErrors(request, errors);
			setEducationListRequest(request);
			setQualificationListToRequest(request);
			return mapping.findForward(CMSConstants.EDUCATION_MASTER);
		} catch (Exception e) {
			log.error("error in final submit of education page...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				educationMasterForm.setErrorMessage(msg);
				educationMasterForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		if (isAdded) {
			// success .
			ActionMessage message = new ActionMessage("knowledgepro.employee.education.master.addsuccess");
			messages.add("messages", message);
			saveMessages(request, messages);
			initFields(educationMasterForm);
		}
		else
		{
			// failed
			errors.add("error", new ActionError("knowledgepro.employee.education.master.addfailure"));
			saveErrors(request, errors);
		}
		log.debug("Leaving addEducationMaster Action");
		return mapping.findForward(CMSConstants.EDUCATION_MASTER);
	}
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 *            This will update  education level
	 * @return to mapping EDUCATION_MASTER
	 * @throws Exception
	 */
	public ActionForward updateEducationMaster(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
									HttpServletResponse response) throws Exception {

		log.debug("inside addEducationMaster Action");
		EducationMasterForm educationMasterForm = (EducationMasterForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = educationMasterForm.validate(mapping, request);
		boolean isAdded = false;
		try {
			if (!errors.isEmpty()) {
				saveErrors(request, errors);
				setQualificationListToRequest(request);
				setEducationListRequest(request);
				request.setAttribute(CMSConstants.OPERATION, CMSConstants.EDIT_OPERATION);
				return mapping.findForward(CMSConstants.EDUCATION_MASTER);
			}
			isAdded = EducationMasterHandler.getInstance().addEducationMaster(educationMasterForm, "edit"); 
			setQualificationListToRequest(request);
			setEducationListRequest(request);
		} catch (DuplicateException e1) {
			errors.add("error", new ActionError("knowledgepro.employee.education.master.exists"));
			saveErrors(request, errors);
			setQualificationListToRequest(request);
			setEducationListRequest(request);
			request.setAttribute(CMSConstants.OPERATION, CMSConstants.EDIT_OPERATION);
			return mapping.findForward(CMSConstants.EDUCATION_MASTER);
		} catch (ReActivateException e1) {
			errors.add("error", new ActionError(CMSConstants.EDUCATION_MASTER_EXIST_REACTIVATE));
			saveErrors(request, errors);
			setQualificationListToRequest(request);
			setEducationListRequest(request);
			request.setAttribute(CMSConstants.OPERATION, CMSConstants.EDIT_OPERATION);
			return mapping.findForward(CMSConstants.EDUCATION_MASTER);
		} catch (Exception e) {
			log.error("error in final submit of education page...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				educationMasterForm.setErrorMessage(msg);
				educationMasterForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		if (isAdded) {
			// success .
			ActionMessage message = new ActionMessage("knowledgepro.employee.education.master.updatesuccess");
			messages.add("messages", message);
			saveMessages(request, messages);
			initFields(educationMasterForm);
		}
		else
		{
			// failed
			errors.add("error", new ActionError("knowledgepro.employee.education.master.updatefailure"));
			saveErrors(request, errors);
		}
		request.setAttribute(CMSConstants.OPERATION, "add");
		log.debug("Leaving addEducationMaster Action");
		return mapping.findForward(CMSConstants.EDUCATION_MASTER);
	}
		
	public void initFields(EducationMasterForm educationMasterForm){
		educationMasterForm.setEducation(null);
		educationMasterForm.setQualificationId(null);
	}
	
	/**
	 * 
	 * @param request
	 *            This method sets the educationList to Request used to display
	 *            educationList record on UI.
	 * @throws Exception
	 */
	public void setEducationListRequest(HttpServletRequest request) throws Exception {
		log.debug("inside setEducationListRequest");
		List<EmpEducationMasterTO> educationList = EducationMasterHandler.getInstance().getEducationMasterDetails();
		request.setAttribute("educationList", educationList);
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response.. this will delete the education record
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteEducation(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	
		log.debug("inside deleteEducation");
		EducationMasterForm educationMasterForm = (EducationMasterForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = new ActionErrors();
		boolean isDeleted = false;
		try {
			if (educationMasterForm.getId() != 0) {
				int id = educationMasterForm.getId();
				isDeleted = EducationMasterHandler.getInstance().deleteEducation(id, false, educationMasterForm);
			}
		} catch (Exception e) {
			log.error("error in deleteEducation...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				educationMasterForm.setErrorMessage(msg);
				educationMasterForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
	
		setQualificationListToRequest(request);
		setEducationListRequest(request);
		if (isDeleted) {
			// success deleted
			ActionMessage message = new ActionMessage("knowledgepro.employee.education.deletesuccess");
			messages.add("messages", message);
			saveMessages(request, messages);
			initFields(educationMasterForm);
		} else {
			// failure error message.
			errors.add("error", new ActionError("knowledgepro.employee.education.deletefailure"));
			saveErrors(request, errors);
		}
		log.debug("leaving deleteEducation");
		return mapping.findForward(CMSConstants.EDUCATION_MASTER);
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response.. this method is to activate the education master
	 * @return
	 * @throws Exception
	 */
	public ActionForward activateEducation(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			 HttpServletResponse response) throws Exception {
		log.debug("Entering activateEducation");
		EducationMasterForm educationMasterForm = (EducationMasterForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		boolean isActivated = false;
		try {
			if (educationMasterForm.getDuplId() != 0) {
				int id = educationMasterForm.getDuplId();  //setting id for activate
				isActivated = EducationMasterHandler.getInstance().deleteEducation(id, true, educationMasterForm); 
				//using for activate & delete. so for identifying activate true/false param sending to handler
			}
		} catch (Exception e) {
			errors.add("error", new ActionError(CMSConstants.EDUCATION_MASTER_ACTIVATE_FAILURE));
			saveErrors(request, errors);
		}
		setQualificationListToRequest(request);
		setEducationListRequest(request);
		if (isActivated) {
			ActionMessage message = new ActionMessage(CMSConstants.EDUCATION_MASTER_ACTIVATE_SUCCESS);
			messages.add("messages", message);
			saveMessages(request, messages);
			initFields(educationMasterForm);
		}
		log.debug("leaving activateEducation");
		return mapping.findForward(CMSConstants.EDUCATION_MASTER);
	}
		
	
}
