package com.kp.cms.actions.exam;

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
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;

import com.kp.cms.forms.admin.TCMasterForm;
import com.kp.cms.forms.exam.GrievanceMasterForm;
import com.kp.cms.handlers.admin.TCMasterHandler;
import com.kp.cms.handlers.exam.GrievanceMasterHandler;

import com.kp.cms.to.exam.GrievanceMasterTo;

public class GrievanceMasterAction extends BaseDispatchAction {
	
	private static final Log log = LogFactory.getLog(GrievanceMasterAction.class);

	/**
	 * setting counterList
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return forward to
	 *         TC_MASTER
	 * @throws Exception
	 */
	public ActionForward initGrievanceGen(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
									HttpServletResponse response) throws Exception {
		GrievanceMasterForm grievanceMasterForm = (GrievanceMasterForm) form;
		grievanceMasterForm.resetFields();
		try {
			
			setUserId(request, grievanceMasterForm);
			setRequestedDataToForm(grievanceMasterForm,request);
			
		} catch (Exception e) {
			log.error("error initTCMaster...", e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				grievanceMasterForm.setErrorMessage(msg);
				grievanceMasterForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
	
	
		return mapping.findForward(CMSConstants.GRIEVANCE_MASTER);
	}
	private void setRequestedDataToForm(GrievanceMasterForm grievanceMasterForm, HttpServletRequest request) throws Exception {


		List<GrievanceMasterTo> list=GrievanceMasterHandler.getInstance().getAllGrievanceNumber();
		grievanceMasterForm.setList(list);
	}
	
	
	public ActionForward addGrievanceMaster(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response) throws Exception {

		GrievanceMasterForm grievanceMasterForm = (GrievanceMasterForm) form;
		grievanceMasterForm.setId(0);
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = grievanceMasterForm.validate(mapping, request);
		boolean isAdded = false;
		try {
			if (!errors.isEmpty()) {
				saveErrors(request, errors);
				setRequestedDataToForm(grievanceMasterForm,request);
				//space should not get added in the table
				return mapping.findForward(CMSConstants.GRIEVANCE_MASTER);
			}
			isAdded = GrievanceMasterHandler.getInstance().addGrievanceMaster(grievanceMasterForm, "add"); 
			setRequestedDataToForm(grievanceMasterForm,request);
		} catch (DuplicateException e1) {
			errors.add("error", new ActionError("knowledgepro.admin.grievance.master.exists"));
			saveErrors(request, errors);
			setRequestedDataToForm(grievanceMasterForm,request);
			return mapping.findForward(CMSConstants.GRIEVANCE_MASTER);
		} catch (ReActivateException e1) {
			errors.add("error", new ActionError(CMSConstants.GRIEVANCE_MASTER_EXIST_REACTIVATE));
			saveErrors(request, errors);
			setRequestedDataToForm(grievanceMasterForm,request);
			return mapping.findForward(CMSConstants.GRIEVANCE_MASTER);
		} catch (Exception e) {
			log.error("error in final submit of disciplinary type page...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				grievanceMasterForm.setErrorMessage(msg);
				grievanceMasterForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		if (isAdded) {
			// success .
			ActionMessage message = new ActionMessage("knowledgepro.admin.grievancemaster.addsuccess");
			messages.add("messages", message);
			saveMessages(request, messages);
			grievanceMasterForm.resetFields();
		}
		else
		{
			// failed
			errors.add("error", new ActionError("knowledgepro.admin.grievancemaster.addfailure"));
			saveErrors(request, errors);
		}
		return mapping.findForward(CMSConstants.GRIEVANCE_MASTER);
	}
	
	public ActionForward updateGrievanceMaster(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response) throws Exception {

		GrievanceMasterForm grievanceMasterForm = (GrievanceMasterForm) form;
			ActionMessages messages = new ActionMessages();
			ActionErrors errors = grievanceMasterForm.validate(mapping, request);
			boolean isUpdated = false;
			try {
				if (!errors.isEmpty()) {
				saveErrors(request, errors);
				setRequestedDataToForm(grievanceMasterForm,request);
				request.setAttribute(CMSConstants.OPERATION, "edit");
				return mapping.findForward(CMSConstants.GRIEVANCE_MASTER);
			}
				isUpdated = GrievanceMasterHandler.getInstance().addGrievanceMaster(grievanceMasterForm, "update"); 
				setRequestedDataToForm(grievanceMasterForm,request);
			} catch (DuplicateException e1) {
				errors.add("error", new ActionError("knowledgepro.admin.grievance.master.exists"));
				saveErrors(request, errors);
				setRequestedDataToForm(grievanceMasterForm,request);
				request.setAttribute(CMSConstants.OPERATION, "edit");
				return mapping.findForward(CMSConstants.GRIEVANCE_MASTER);
			} catch (ReActivateException e1) {
				errors.add("error", new ActionError(CMSConstants.GRIEVANCE_MASTER_EXIST_REACTIVATE));
				saveErrors(request, errors);
				setRequestedDataToForm(grievanceMasterForm,request);
				request.setAttribute(CMSConstants.OPERATION, "edit");
				return mapping.findForward(CMSConstants.GRIEVANCE_MASTER);
			} catch (Exception e) {
				log.error("error in final submit of counter master type page...", e);
				if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				grievanceMasterForm.setErrorMessage(msg);
				grievanceMasterForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
				}
			}
			if (isUpdated) {
				// success .
				ActionMessage message = new ActionMessage("knowledgepro.admin.grievancemaster.updatesuccess");
				messages.add("messages", message);
				saveMessages(request, messages);
				grievanceMasterForm.resetFields();
			}
			else
			{
				// failed
				errors.add("error", new ActionError("knowledgepro.admin.grievancemaster.updatefailure"));
				saveErrors(request, errors);
			}
				request.setAttribute(CMSConstants.OPERATION, "add");
				return mapping.findForward(CMSConstants.GRIEVANCE_MASTER);
			}
	
	public ActionForward deleteGrievanceMaster(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	
		GrievanceMasterForm grievanceMasterForm = (GrievanceMasterForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = new ActionErrors();
		boolean isDeleted = false;
		try {
			if (grievanceMasterForm.getId() != 0) {
				int id = grievanceMasterForm.getId();
				isDeleted = GrievanceMasterHandler.getInstance().deleteGrievanceMaster(id, false, grievanceMasterForm);
			}
		} catch (Exception e) {
			log.error("error in deleteTCMaster...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				grievanceMasterForm.setErrorMessage(msg);
				grievanceMasterForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
	
		setRequestedDataToForm(grievanceMasterForm,request);
		if (isDeleted) {
			// success deleted
			ActionMessage message = new ActionMessage("knowledgepro.admin.grievancemaster.deletesuccess");
			messages.add("messages", message);
			saveMessages(request, messages);
			grievanceMasterForm.resetFields();
		} else {
			// failure error message.
			errors.add("error", new ActionError("knowledgepro.admin.grievancemaster.deletefailure"));
			saveErrors(request, errors);
		}
		return mapping.findForward(CMSConstants.GRIEVANCE_MASTER);
	}
	
	public ActionForward activateGrievanceMaster(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			 HttpServletResponse response) throws Exception {
		GrievanceMasterForm grievanceMasterForm = (GrievanceMasterForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		boolean isActivated = false;
		try {
			if (grievanceMasterForm.getDuplId() != 0) {
				int id = grievanceMasterForm.getDuplId();  //setting id for activate
				isActivated = GrievanceMasterHandler.getInstance().deleteGrievanceMaster(id, true, grievanceMasterForm);
				//using for activate & delete. so for identifying activate true/false param sending to handler
			}
		} catch (Exception e) {
			errors.add("error", new ActionError(CMSConstants.GRIEVANCE_MASTER_ACTIVATE_FAILURE));
			saveErrors(request, errors);
		}
		setRequestedDataToForm(grievanceMasterForm,request);
		if (isActivated) {
			ActionMessage message = new ActionMessage(CMSConstants.GRIEVANCE_MASTER_ACTIVATE_SUCCESS);
			messages.add("messages", message);
			saveMessages(request, messages);
			grievanceMasterForm.resetFields();
		}
		return mapping.findForward(CMSConstants.GRIEVANCE_MASTER);
	}
	
	



}
