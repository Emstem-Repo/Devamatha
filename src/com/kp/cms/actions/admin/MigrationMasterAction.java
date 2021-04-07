package com.kp.cms.actions.admin;

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
import com.kp.cms.forms.admin.MigrationMasterForm;
import com.kp.cms.handlers.admin.MigrationMasterHandler;
import com.kp.cms.to.admin.MigrationNumberTO;

/**
 * @author dIlIp
 *
 */
public class MigrationMasterAction extends BaseDispatchAction {
	
	private static final Log log = LogFactory.getLog(MigrationMasterAction.class);
	
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initMigrationMaster(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
		MigrationMasterForm migrationMasterForm = (MigrationMasterForm) form;
		migrationMasterForm.resetFields();
		setRequestedDataToForm(migrationMasterForm);
		setUserId(request, migrationMasterForm);
		
		return mapping.findForward("migrationMaster");
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addMigrationMaster(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response) throws Exception {

		MigrationMasterForm migrationMasterForm = (MigrationMasterForm) form;
		migrationMasterForm.setId(0);
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = migrationMasterForm.validate(mapping, request);
		boolean isAdded = false;
		try {
			if (!errors.isEmpty()) {
				saveErrors(request, errors);
				setRequestedDataToForm(migrationMasterForm);
				//	space should not get added in the table
				return mapping.findForward("migrationMaster");
			}
			isAdded = MigrationMasterHandler.getInstance().addMigrationMaster(migrationMasterForm, "add"); 
			setRequestedDataToForm(migrationMasterForm);
		} catch (DuplicateException e1) {
			errors.add("error", new ActionError("knowledgepro.admin.migration.master.exists", migrationMasterForm.getType()));
			saveErrors(request, errors);
			setRequestedDataToForm(migrationMasterForm);
			return mapping.findForward("migrationMaster");
		} catch (ReActivateException e1) {
			errors.add("error", new ActionError("knowledgepro.admin.migration.master.alreadyexist.reactivate"));
			saveErrors(request, errors);
			setRequestedDataToForm(migrationMasterForm);
			return mapping.findForward("migrationMaster");
		} catch (Exception e) {
			log.error("error in final submit of disciplinary type page...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				migrationMasterForm.setErrorMessage(msg);
				migrationMasterForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		if (isAdded) {
			// 	success .
			ActionMessage message = new ActionMessage("knowledgepro.admin.migration.master.addsuccess", migrationMasterForm.getType());
			messages.add("messages", message);
			saveMessages(request, messages);
			migrationMasterForm.resetFields();
		}
		else
		{
			// 	failed
			errors.add("error", new ActionError("knowledgepro.admin.migration.master.addfail", migrationMasterForm.getType()));
			saveErrors(request, errors);
		}
		return mapping.findForward("migrationMaster");
	}
	
	/**
	 * @param migrationMasterForm
	 * @throws Exception
	 */
	private void setRequestedDataToForm(MigrationMasterForm migrationMasterForm) throws Exception {
		List<MigrationNumberTO> list = MigrationMasterHandler.getInstance().getAllMigrationNumber();
		migrationMasterForm.setList(list);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateMigrationMaster(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response) throws Exception {

			MigrationMasterForm migrationMasterForm = (MigrationMasterForm) form;
			ActionMessages messages = new ActionMessages();
			ActionErrors errors = migrationMasterForm.validate(mapping, request);
			boolean isUpdated = false;
			try {
				if (!errors.isEmpty()) {
				saveErrors(request, errors);
				setRequestedDataToForm(migrationMasterForm);
				request.setAttribute(CMSConstants.OPERATION, "edit");
				return mapping.findForward("migrationMaster");
			}
				isUpdated = MigrationMasterHandler.getInstance().addMigrationMaster(migrationMasterForm, "update"); 
				setRequestedDataToForm(migrationMasterForm);
			} catch (DuplicateException e1) {
				errors.add("error", new ActionError("knowledgepro.admin.migration.master.exists", migrationMasterForm.getType()));
				saveErrors(request, errors);
				setRequestedDataToForm(migrationMasterForm);
				request.setAttribute(CMSConstants.OPERATION, "edit");
				return mapping.findForward("migrationMaster");
			} catch (ReActivateException e1) {
				errors.add("error", new ActionError("knowledgepro.admin.migration.master.alreadyexist.reactivate"));
				saveErrors(request, errors);
				setRequestedDataToForm(migrationMasterForm);
				request.setAttribute(CMSConstants.OPERATION, "edit");
				return mapping.findForward("migrationMaster");
			} catch (Exception e) {
				log.error("error in final submit of counter master type page...", e);
				if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				migrationMasterForm.setErrorMessage(msg);
				migrationMasterForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
				}
			}
			if (isUpdated) {
				// success .
				ActionMessage message = new ActionMessage("knowledgepro.admin.migration.master.updatesuccess", migrationMasterForm.getType());
				messages.add("messages", message);
				saveMessages(request, messages);
				migrationMasterForm.resetFields();
			}
			else
			{
				// failed
				errors.add("error", new ActionError("knowledgepro.admin.migration.master.updatefail", migrationMasterForm.getPrefix()));
				saveErrors(request, errors);
			}
				request.setAttribute(CMSConstants.OPERATION, "add");
				return mapping.findForward("migrationMaster");
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteMigrationMaster(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	
		MigrationMasterForm migrationMasterForm = (MigrationMasterForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = new ActionErrors();
		boolean isDeleted = false;
		try {
			if (migrationMasterForm.getId() != 0) {
				
				isDeleted = MigrationMasterHandler.getInstance().deleteMigrationMaster(migrationMasterForm.getId(), false, migrationMasterForm);
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
				migrationMasterForm.setErrorMessage(msg);
				migrationMasterForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
	
		setRequestedDataToForm(migrationMasterForm);
		if (isDeleted) {
			// success deleted
			ActionMessage message = new ActionMessage("knowledgepro.admin.tcmaster.deletesuccess");
			messages.add("messages", message);
			saveMessages(request, messages);
			migrationMasterForm.resetFields();
		} else {
			// failure error message.
			errors.add("error", new ActionError("knowledgepro.admin.tcmaster.deletefailure"));
			saveErrors(request, errors);
		}
		return mapping.findForward("migrationMaster");
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward activateMigrationMaster(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			 HttpServletResponse response) throws Exception {
		
		MigrationMasterForm migrationMasterForm = (MigrationMasterForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		boolean isActivated = false;
		try {
			if (migrationMasterForm.getDuplId() != 0) {
				
				isActivated = MigrationMasterHandler.getInstance().deleteMigrationMaster(migrationMasterForm.getDuplId(), true, migrationMasterForm);
				//using for activate & delete. so for identifying activate true/false param sending to handler
			}
		} catch (Exception e) {
			errors.add("error", new ActionError(CMSConstants.TC_MASTER_ACTIVATE_FAILURE));
			saveErrors(request, errors);
		}
		setRequestedDataToForm(migrationMasterForm);
		if (isActivated) {
			ActionMessage message = new ActionMessage(CMSConstants.TC_MASTER_ACTIVATE_SUCCESS);
			messages.add("messages", message);
			saveMessages(request, messages);
			migrationMasterForm.resetFields();
		}
		return mapping.findForward("migrationMaster");
	}

}
