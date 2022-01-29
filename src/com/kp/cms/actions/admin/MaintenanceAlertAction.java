package com.kp.cms.actions.admin;

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
import com.kp.cms.forms.admin.MaintenanceAlertForm;
import com.kp.cms.handlers.admin.MaintenanceAlertHandler;

@SuppressWarnings("deprecation")
public class MaintenanceAlertAction extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(MaintenanceAlertAction.class);
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initMaintenanceAlert(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
			MaintenanceAlertForm alertForm=(MaintenanceAlertForm) form; 
			alertForm.reset();
		try{
			setRequiredDataToForm(alertForm);
		}catch (Exception e) {
			log.error("error in editing venueDetails...", e);
			String msg = super.handleApplicationException(e);
			alertForm.setErrorMessage(msg);
			alertForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		
		return mapping.findForward(CMSConstants.INIT_MAINTENANACE_ALERT);
	}
	
	
	public void setRequiredDataToForm(MaintenanceAlertForm alertForm) throws Exception{
		MaintenanceAlertHandler.getInstance().getMaintenanceDetails(alertForm);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addMaintenanceAlert(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		MaintenanceAlertForm alertForm=(MaintenanceAlertForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors=new ActionErrors();
		setUserId(request, alertForm);
		String mode="Add";
		try {
			boolean isDuplicate=MaintenanceAlertHandler.getInstance().duplicateCheck(alertForm);
			if(!isDuplicate){
			boolean isAdded=false;
			isAdded=MaintenanceAlertHandler.getInstance().addOrUpdateMaintenanceAlert(alertForm,mode);
			if(isAdded){
				messages.add(CMSConstants.MESSAGES, new ActionMessage("knowledgepro.admin.maintenance.alert.addSucess"));
				saveMessages(request, messages);
				alertForm.reset();
			} else {
				errors.add("error", new ActionError("knowledgepro.admin.maintenance.alert.addFailure"));
				addErrors(request, errors);
				alertForm.reset();
			}
			}else{
				errors.add("error", new ActionError("knowledgepro.admin.maintenance.alert.duplicate",alertForm.getMaintenanceDate()));
				addErrors(request, errors);
			}
			setRequiredDataToForm(alertForm);
		} catch (Exception e) {
			log.error("error in editing venueDetails...", e);
			String msg = super.handleApplicationException(e);
			alertForm.setErrorMessage(msg);
			alertForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.INIT_MAINTENANACE_ALERT);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward editMaintenanceAlert(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		MaintenanceAlertForm alertForm=(MaintenanceAlertForm) form;
		try {
			// employeeResumeForm.reset(mapping, request);
			// String formName = mapping.getName();
			// request.getSession().setAttribute(CMSConstants.FORMNAME,
			// formName);
			MaintenanceAlertHandler.getInstance().editMaintenanceDetails(alertForm);
			request.setAttribute("operation", "edit");
			//request.setAttribute("Update", "Update");// setting update attribute
		} catch (Exception e) {
			log.error("error in editing venueDetails...", e);
			String msg = super.handleApplicationException(e);
			alertForm.setErrorMessage(msg);
			alertForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.INIT_MAINTENANACE_ALERT);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateMaintenanceAlert(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		MaintenanceAlertForm alertForm=(MaintenanceAlertForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors=new ActionErrors();
		setUserId(request, alertForm);
		String mode="Update";
		try {
			boolean isDuplicate=MaintenanceAlertHandler.getInstance().duplicateCheck(alertForm);
			if(!isDuplicate){
			boolean isAdded=false;
			isAdded=MaintenanceAlertHandler.getInstance().addOrUpdateMaintenanceAlert(alertForm,mode);
			if(isAdded){
				messages.add(CMSConstants.MESSAGES, new ActionMessage("knowledgepro.admin.maintenance.alert.update.success"));
				saveMessages(request, messages);
				alertForm.reset();
			} else {
				errors.add("error", new ActionError("knowledgepro.admin.maintenance.alert.update.failure"));
				addErrors(request, errors);
				alertForm.reset();
			}
			}else{
				errors.add("error", new ActionError("knowledgepro.admin.maintenance.alert.duplicate",alertForm.getMaintenanceDate()));
				addErrors(request, errors);
			}
			setRequiredDataToForm(alertForm);
		} catch (Exception e) {
			log.error("error in editing venueDetails...", e);
			String msg = super.handleApplicationException(e);
			alertForm.setErrorMessage(msg);
			alertForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.INIT_MAINTENANACE_ALERT);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteMaintenance(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		MaintenanceAlertForm alertForm=(MaintenanceAlertForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors=new ActionErrors();
		setUserId(request, alertForm);
		String mode="Delete";
		try {
			boolean isDeleted=false;
			isDeleted=MaintenanceAlertHandler.getInstance().deleteMaintenanceAlert(alertForm,mode);
			if(isDeleted){
				messages.add(CMSConstants.MESSAGES, new ActionMessage("knowledgepro.admin.maintenance.alert.delete.success"));
				saveMessages(request, messages);
				alertForm.reset();
			} else {
				errors.add("error", new ActionError("knowledgepro.admin.maintenance.alert.delete.failure"));
				addErrors(request, errors);
				alertForm.reset();
			}
			setRequiredDataToForm(alertForm);
		} catch (Exception e) {
			log.error("error in editing venueDetails...", e);
			String msg = super.handleApplicationException(e);
			alertForm.setErrorMessage(msg);
			alertForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.INIT_MAINTENANACE_ALERT);
	}
}
