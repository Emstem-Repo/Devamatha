package com.kp.cms.actions.employee;

import java.util.List;
import java.util.Map;

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

import com.kp.cms.forms.employee.EmployeeSettingsForm;
import com.kp.cms.forms.employee.HolidayDetailsForm;
import com.kp.cms.handlers.employee.EmployeeSettingsHandler;
import com.kp.cms.handlers.employee.HolidayDetailsHandler;
import com.kp.cms.to.employee.EmployeeSettingsTO;
import com.kp.cms.to.employee.HolidayDetailsTO;

public class EmployeeSettingsAction extends BaseDispatchAction{
	private static final Log log = LogFactory.getLog(EmployeeSettingsAction.class);
	public ActionForward initEmployeeSettings(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {		
		log.info("Entring into Action initEmployeeSettings");
		EmployeeSettingsForm empSettingsForm=(EmployeeSettingsForm)form; 
		try {
			empSettingsForm.reset();
			setRequestedDataToForm(empSettingsForm);
		} catch (Exception e) {
				log.error("Error in saving EmployeeSettings",e);
				String msg = super.handleApplicationException(e);
				empSettingsForm.setErrorMessage(msg);
				empSettingsForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Leaving into Action initEmployeeSettings");
		return mapping.findForward(CMSConstants.EMPLOYEE_SETTINGS);
	}
	/**
	 * @param empSettingsForm
	 * @throws Exception
	 */
	public void setRequestedDataToForm(EmployeeSettingsForm empSettingsForm) throws Exception {
		// 1. Set the PayScale list
		List<EmployeeSettingsTO> empsettTo = EmployeeSettingsHandler.getInstance().getEmpSettingsList();
		empSettingsForm.setEmpSettListTO(empsettTo);
        Map<Integer,String> empLeave=EmployeeSettingsHandler.getInstance().getEmpLeveType();
		empSettingsForm.setEmpLeave(empLeave);
	}
	public ActionForward editEmployeeSettings(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		EmployeeSettingsForm empSettingsForm = (EmployeeSettingsForm) form;
		log.debug("Entering EditHolidays ");
		try {
			EmployeeSettingsHandler.getInstance().editEmpSettings(empSettingsForm);
			request.setAttribute("Operation", "edit");
			log.debug("Leaving EditEmpSettings ");
		} catch (Exception e) {
			log.error("error in editing EmpSettings...", e);
			String msg = super.handleApplicationException(e);
			empSettingsForm.setErrorMessage(msg);
			empSettingsForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.EMPLOYEE_SETTINGS);
	}
	public ActionForward updateEmpSettings(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.debug("Enter: updateHolidays Action");
		EmployeeSettingsForm empSettingsForm = (EmployeeSettingsForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = empSettingsForm.validate(mapping, request);
		boolean isUpdated = false;
        if(errors.isEmpty()){
		try {
			// This condition works when reset button will click in update mode
			if (isCancelled(request)) {
				empSettingsForm.reset(mapping, request);
				String formName = mapping.getName();
				request.getSession().setAttribute(CMSConstants.FORMNAME,
						formName);
				EmployeeSettingsHandler.getInstance().editEmpSettings(empSettingsForm);
				request.setAttribute("Operation", "edit");
				return mapping.findForward(CMSConstants.EMPLOYEE_SETTINGS);
			}
			boolean isCheck=true;
			setUserId(request, empSettingsForm); // setting user id to update
			if(empSettingsForm.getCurrentApplicationNo()!=null && !empSettingsForm.getCurrentApplicationNo().isEmpty()){
			isCheck=EmployeeSettingsHandler.getInstance().checkApplicationNo(empSettingsForm);
			}
			if(isCheck){
			isUpdated = EmployeeSettingsHandler.getInstance()
					.updateEmpSettings(empSettingsForm);
			if (isUpdated) {
				ActionMessage message = new ActionMessage(
						"knowledgepro.employee.settings.update");
				messages.add("messages", message);
				saveMessages(request, messages);
				empSettingsForm.reset();
			} else {
				errors.add("error", new ActionError("knowledgepro.employee.settings.update.failed"));
				addErrors(request, errors);
				empSettingsForm.reset();
			}
			}else{
				errors.add("error", new ActionError(
				"knowledgepro.employee.settings.MaxAppNo"));
		        //saveErrors(request, errors);
		        addErrors(request, errors);
		        request.setAttribute("Operation", "edit");
			}
		} catch (Exception e) {
			log.error("Error occured in edit EmpSettings", e);
			String msg = super.handleApplicationException(e);
			empSettingsForm.setErrorMessage(msg);
			empSettingsForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}}else{
			//saveErrors(request, errors);
			addErrors(request, errors);
			setRequestedDataToForm(empSettingsForm);
			request.setAttribute("Operation", "edit");
			return mapping.findForward(CMSConstants.EMPLOYEE_SETTINGS);
		}
		setRequestedDataToForm(empSettingsForm);
		log.debug("Exit: action class updateEmpSettings");
		return mapping.findForward(CMSConstants.EMPLOYEE_SETTINGS);

	}
}
