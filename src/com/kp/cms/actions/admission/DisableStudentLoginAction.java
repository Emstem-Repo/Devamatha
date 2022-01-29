package com.kp.cms.actions.admission;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.kp.cms.forms.admission.DisableStudentLoginForm;
import com.kp.cms.handlers.admission.DisableStudentLoginHandler;

public class DisableStudentLoginAction extends BaseDispatchAction {
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initDisableStudentLogin(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DisableStudentLoginForm disableStudentLoginForm = (DisableStudentLoginForm)form;
		String formName = mapping.getName();
		request.getSession().setAttribute(CMSConstants.FORMNAME, formName);
		disableStudentLoginForm.setExamType("Regular");
		disableStudentLoginForm.setRemarks(null);
		disableStudentLoginForm.setProgramTypeId(null);
		return mapping.findForward(CMSConstants.INIT_DISABLE_STU_LOGIN);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward submitDisableStudentLogin(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DisableStudentLoginForm disableStudentLoginForm = (DisableStudentLoginForm)form;
		@SuppressWarnings("unused")
		boolean isSubmit = false;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = disableStudentLoginForm.validate(mapping, request);
		setUserId(request, disableStudentLoginForm);
		try{
			if(!errors.isEmpty()){
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.INIT_DISABLE_STU_LOGIN);
			}
			/*if(!disableStudentLoginForm.getExamType().equalsIgnoreCase("Regular") || !disableStudentLoginForm.getExamType().equalsIgnoreCase("Supplementary")){
				errors.add("error", new ActionError("knowledgepro.regular.supplementary.required"));
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.INIT_DISABLE_STU_LOGIN);
			}*/
			DisableStudentLoginHandler handler = DisableStudentLoginHandler.getInstance();
			isSubmit = handler.searchDisableStudentLogin(disableStudentLoginForm);
			if(isSubmit){
				ActionMessage message = new ActionError("knowledgepro.admission.disable.studentlogin.success");
				messages.add("messages", message);
				saveMessages(request, messages);
				disableStudentLoginForm.setExamType("Regular");
				disableStudentLoginForm.setRemarks(null);
				disableStudentLoginForm.setProgramTypeId(null);
			}else{
				errors.add("error", new ActionError("knowledgepro.admission.disable.studentlogin.failure"));
				saveErrors(request, errors);
				disableStudentLoginForm.setExamType("Regular");
				disableStudentLoginForm.setRemarks(null);
				disableStudentLoginForm.setProgramTypeId(null);
			}
		}catch (ApplicationException e1){
			int year = Integer.parseInt(disableStudentLoginForm.getAcademicYear());
			errors.add("error", new ActionError("knowledgepro.admission.disable.studentlogin.nostudents",year));
			saveErrors(request, errors);
			disableStudentLoginForm.setExamType("Regular");
			disableStudentLoginForm.setRemarks(null);
			disableStudentLoginForm.setProgramTypeId(null);
			return mapping.findForward(CMSConstants.INIT_DISABLE_STU_LOGIN);
		}
		catch (Exception e) {
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				disableStudentLoginForm.setErrorMessage(msg);
				disableStudentLoginForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		return mapping.findForward(CMSConstants.INIT_DISABLE_STU_LOGIN);
	}
}
