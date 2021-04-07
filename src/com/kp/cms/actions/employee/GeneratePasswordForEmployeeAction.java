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
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.employee.GeneratePasswordForEmployeeForm;
import com.kp.cms.handlers.employee.GeneratePasswordForEmployeeHandler;

public class GeneratePasswordForEmployeeAction extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(GeneratePasswordForEmployeeAction.class);
	/**
	 * initializes generate password search
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initGeneratePassword(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		GeneratePasswordForEmployeeForm gpForm=(GeneratePasswordForEmployeeForm)form;
		try {
			// initialize program type
			setUserId(request, gpForm);
			gpForm.resetFields();
		}
		catch (Exception e) {
			log.error("error in init application detail page...",e);
				throw e;
		}
		return mapping.findForward(CMSConstants.GENERATE_PASSWORD_EMPLOYEE);
	}
	/**
	 * generates password for employees
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward generatePassword(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		GeneratePasswordForEmployeeForm gnForm=(GeneratePasswordForEmployeeForm)form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		setUserId(request, gnForm);
		boolean isGenerated=false;
		try {
			validateForm(gnForm,errors);
			if (errors != null && !errors.isEmpty()) {
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.GENERATE_PASSWORD_EMPLOYEE);
			}
			isGenerated=GeneratePasswordForEmployeeHandler.getInstance().updatePassword(gnForm);
			if(isGenerated){
				messages.add(CMSConstants.MESSAGES, new ActionMessage("admin.generatepassword.confirm.label"));
				saveMessages(request, messages);
				log.info("Exit GeneratePasswordForEmployeeAction - generatePassword");
			}
			else {
				errors.add(CMSConstants.ERRORS,new ActionError("knowledgepro.student.changepassword.failed"));
				saveErrors(request, errors);
				log.info("Error GeneratePasswordForEmployeeAction - generatePassword");
			}
		} catch (ApplicationException e) {
			log.error("error in GeneratePasswordForEmployeeAction...",e);
			String msg = super.handleApplicationException(e);
			gnForm.setErrorMessage(msg);
			gnForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} 
		catch (Exception e) {
			log.error("error in GeneratePasswordForEmployeeAction...",e);
				throw e;
		}
		return mapping.findForward(CMSConstants.GENERATE_PASSWORD_EMPLOYEE);
	}
	/**
	 * validation 
	 * @param gnForm
	 * @param errors
	 */
	private void validateForm(GeneratePasswordForEmployeeForm gnForm,ActionMessages errors) {
		if(errors==null){
			errors= new ActionMessages();
		}
		if(gnForm.getEmployeeType()==null && gnForm.getEmployeeType().isEmpty()){
				errors.add(CMSConstants.ERRORS,new ActionError(CMSConstants.GENERATEPASSWORD_EMPLOYEE_SELECT));
			}
		}
}
