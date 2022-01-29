package com.kp.cms.actions.usermanagement;

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
import com.kp.cms.bo.admin.StudentLogin;
import com.kp.cms.bo.admin.Users;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.forms.usermanagement.ChangePasswordForm;
import com.kp.cms.handlers.usermanagement.ChangePasswordHandler;

/**
 * 
 * @author 
 * 
 */
@SuppressWarnings("deprecation")
public class ChangePasswordAction extends BaseDispatchAction {
	private static Log log = LogFactory.getLog(ChangePasswordAction.class);
	/*
	 * forward to change password
	 */
	public ActionForward initChangePassword(ActionMapping mapping, ActionForm form, HttpServletRequest request,
											HttpServletResponse response) throws Exception {
		ChangePasswordForm changePasswordForm = (ChangePasswordForm)form;
		setUserId(request, changePasswordForm); // setting user for updating last changed details
		setUserName(request, changePasswordForm);
		return mapping.findForward(CMSConstants.CHANGE_PASSWORD);

	}
	/**
	 * this method will update password in users table
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updatePassword(ActionMapping mapping,	ActionForm form, HttpServletRequest request,
										HttpServletResponse response) throws Exception {

		log.debug("inside UpdatePasswordAction Action");
		ChangePasswordForm changePasswordForm = (ChangePasswordForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = changePasswordForm.validate(mapping, request);
		boolean isChanged = false;
		try {
			if (!errors.isEmpty()) {
				saveErrors(request, errors);
				 resetFields(changePasswordForm);  //resetting all password fields when validation fails
				return mapping.findForward(CMSConstants.CHANGE_PASSWORD);
			}
			Users users = ChangePasswordHandler.getInstance().isValiedUser(changePasswordForm);  //checking existing password entered is valid or not
			if(users == null){
				errors.add("error", new ActionError("knowledgepro.usermanagement.existing.pass.not.valid"));
				saveErrors(request, errors);
				 resetFields(changePasswordForm);  //resetting all password fields when validation fails
				return mapping.findForward(CMSConstants.CHANGE_PASSWORD);
			}
			if(changePasswordForm.getExistingPwd().equals(changePasswordForm.getNewPwd())){   //showing message if existing & new password is same
				errors.add("error", new ActionError("knowledgepro.usermanagement.new.pass.same"));
				saveErrors(request, errors);
				 resetFields(changePasswordForm);  //resetting all password fields when validation fails
				return mapping.findForward(CMSConstants.CHANGE_PASSWORD);
			}
			
			if(!changePasswordForm.getNewPwd().equals(changePasswordForm.getReTypeNewPwd())){  //showing message if new password and retyped password is different
				errors.add("error", new ActionError("knowledgepro.usermanagement.new.pass.not.equalto.retypeNew"));
				saveErrors(request, errors);
				 resetFields(changePasswordForm);  //resetting all password fields when validation fails
				return mapping.findForward(CMSConstants.CHANGE_PASSWORD);
			}
			
			isChanged = ChangePasswordHandler.getInstance().changePass(changePasswordForm);  //update password
		} catch (Exception e) {
			log.error("error in ChangePassword...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				changePasswordForm.setErrorMessage(msg);
				changePasswordForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		if (isChanged) {
			// success .
			ChangePasswordHandler.getInstance().sendMailToUser(changePasswordForm);
			ActionMessage message = new ActionMessage("knowledgepro.usermanagement.pass.change.success");
			messages.add("messages", message);
			saveMessages(request, messages);
			changePasswordForm.reset(mapping, request);
		} else {
			// failed
			errors.add("error", new ActionError("knowledgepro.usermanagement.pass.change.success"));
			saveErrors(request, errors);
		}
		log.debug("Leaving ChangePassword Action");
		return mapping.findForward(CMSConstants.CHANGE_PASSWORD);
	}
	/**
	 * setting user name to form for password validation
	 * @param request
	 * @param form
	 */
	public void setUserName(HttpServletRequest request, BaseActionForm form){
		ChangePasswordForm changePasswordForm = (ChangePasswordForm) form;
		HttpSession session = request.getSession(false);
		if(session.getAttribute("username")!=null){
			changePasswordForm.setTempUname(session.getAttribute("username").toString());
		}
	}
	
	public ActionForward initChangeStudentPassword(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ChangePasswordForm changePasswordForm = (ChangePasswordForm)form;
		setUserId(request, changePasswordForm); // setting user for updating last changed details
		setUserName(request, changePasswordForm);
		return mapping.findForward(CMSConstants.CHANGE_STUDENT_PASSWORD);
		
	}
	
	/**
	 * this method will update password in users table
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateStudentPassword(ActionMapping mapping,	ActionForm form, HttpServletRequest request,
										HttpServletResponse response) throws Exception {

		log.debug("inside updateStudentPassword Action");
		ChangePasswordForm changePasswordForm = (ChangePasswordForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = changePasswordForm.validate(mapping, request);
		boolean isChanged = false;
		Boolean tempPassLogin = false;
		if(request.getSession().getAttribute("tempPasswordLogin") != null) {
			tempPassLogin = (Boolean)request.getSession().getAttribute("tempPasswordLogin");
		}
		try {
			changePasswordForm.setTempPassword(tempPassLogin);
			if (!errors.isEmpty()) {
				saveErrors(request, errors);
				 resetFields(changePasswordForm);  //resetting all password fields when validation fails
				return mapping.findForward(CMSConstants.CHANGE_STUDENT_PASSWORD);
			}
			StudentLogin studentLogin = ChangePasswordHandler.getInstance().isValidStudent(changePasswordForm);  //checking existing password entered is valid or not
			if(studentLogin == null){
				errors.add("error", new ActionError("knowledgepro.usermanagement.existing.pass.not.valid"));
				saveErrors(request, errors);
				 resetFields(changePasswordForm);  //resetting all password fields when validation fails
				return mapping.findForward(CMSConstants.CHANGE_STUDENT_PASSWORD);
			}
			if(changePasswordForm.getExistingPwd().equals(changePasswordForm.getNewPwd())){   //showing message if existing & new password is same
				errors.add("error", new ActionError("knowledgepro.usermanagement.new.pass.same"));
				saveErrors(request, errors);
				 resetFields(changePasswordForm);  //resetting all password fields when validation fails
				return mapping.findForward(CMSConstants.CHANGE_STUDENT_PASSWORD);
			}
			
			if(!changePasswordForm.getNewPwd().equals(changePasswordForm.getReTypeNewPwd())){  //showing message if new password and retyped password is different
				errors.add("error", new ActionError("knowledgepro.usermanagement.new.pass.not.equalto.retypeNew"));
				saveErrors(request, errors);
				 resetFields(changePasswordForm);  //resetting all password fields when validation fails
				return mapping.findForward(CMSConstants.CHANGE_STUDENT_PASSWORD);
			}
			
			isChanged = ChangePasswordHandler.getInstance().changeStudentPass(changePasswordForm);  //update password
		} catch (Exception e) {
			log.error("error in ChangePassword...", e);
			errors.add("error", new ActionError(CMSConstants.CHANGE_STUDENT_PASSWORD_FAILED));
			saveErrors(request, errors);
			return mapping.findForward(CMSConstants.CHANGE_STUDENT_PASSWORD);
		}
		if (isChanged) {
			// success .
			ChangePasswordHandler.getInstance().sendMailToUser(changePasswordForm);
			ActionMessage message = new ActionMessage("knowledgepro.usermanagement.pass.change.success");
			messages.add("messages", message);
			saveMessages(request, messages);
			changePasswordForm.reset(mapping, request);
		} else {
			// failed
			errors.add("error", new ActionError("knowledgepro.usermanagement.pass.change.success"));
			saveErrors(request, errors);
		}
		log.debug("Leaving ChangePassword Action");
		if(tempPassLogin){
			changePasswordForm.setTempPassword(false);
			request.getSession().setAttribute("tempPasswordLogin",false);
			return mapping.findForward(CMSConstants.CHANGE_STUDENT_PASSWORD_LOGIN);
		}else{
			return mapping.findForward(CMSConstants.CHANGE_STUDENT_PASSWORD);
		}
	}
	/**
	 * resetting passwords when validation fails
	 * @param changePasswordForm
	 */
	public void resetFields(ChangePasswordForm changePasswordForm) {
		changePasswordForm.setExistingPwd(null);
		changePasswordForm.setNewPwd(null);
		changePasswordForm.setReTypeNewPwd(null);
	}
	
}
