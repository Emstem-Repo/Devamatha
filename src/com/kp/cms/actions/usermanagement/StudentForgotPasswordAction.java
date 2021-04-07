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
import org.apache.struts.action.ActionMessages;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.bo.admin.Organisation;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.usermanagement.StudentForgotPasswordForm;
import com.kp.cms.handlers.admin.OrganizationHandler;
import com.kp.cms.handlers.usermanagement.StudentForgotPasswordHandler;

public class StudentForgotPasswordAction extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(StudentForgotPasswordAction.class);
	/**
	 * Method to set the required data to the form to display it in forgot.jsp
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initForgotPassword(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered initForgotPassword input");
		StudentForgotPasswordForm studentForgotPasswordForm = (StudentForgotPasswordForm) form;
		Organisation organisation = OrganizationHandler.getInstance().getRequiredFile();
		if(organisation!=null){
			// set photo to session
			if(organisation.getLogoContentType()!=null){
				HttpSession session= request.getSession(false);
				if(session!=null){
					session.setAttribute(CMSConstants.KNOWLEDGEPRO_LOGO, organisation.getLogo());
					session.setAttribute(CMSConstants.KNOWLEDGEPRO_TOPBAR, organisation.getTopbar());
				}
			}
		}
		studentForgotPasswordForm.resetFields();
		log.info("Exit initForgotPassword input");
		
		return mapping.findForward(CMSConstants.FORGOT_PASSWORD);
	}
	/**
	 * Method to select the candidates for score sheet result entry based on the input selected
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward resetForgotPassword(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered ScoreSheetAction - getCandidates");
		
		StudentForgotPasswordForm studentForgotPasswordForm = (StudentForgotPasswordForm) form;
		ActionMessages messages=new ActionMessages(); 
		ActionErrors errors = studentForgotPasswordForm.validate(mapping, request);
		if (errors.isEmpty()) {
			try {
				boolean isValidData=StudentForgotPasswordHandler.getInstance().checkValidData(studentForgotPasswordForm);
				if (!isValidData) {
					errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.usermanagement.forgotpass.failure"));
					saveErrors(request, errors);
					log.info("Exit Interview Batch Result - getSelectedCandidates size 0");
					return mapping.findForward(CMSConstants.FORGOT_PASSWORD);
				} 
//				StudentForgotPasswordHandler.getInstance().resetPassword(studentForgotPasswordForm);
				StudentForgotPasswordHandler.getInstance().sendMailToStudent(studentForgotPasswordForm);
				messages.add(CMSConstants.MESSAGES, new ActionError("knowledgepro.usermanagement.forgotpass.success",studentForgotPasswordForm.getStudentLogin().getStudent().getAdmAppln().getPersonalData().getEmail()));
				studentForgotPasswordForm.resetFields();
				saveMessages(request, messages);
			}  catch (Exception exception) {
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.usermanagement.forgotpass.failure"));
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.FORGOT_PASSWORD);
			}
		} else {
			addErrors(request, errors);
			log.info("Exit Interview Batch Result - getSelectedCandidates errors not empty ");
			return mapping.findForward(CMSConstants.FORGOT_PASSWORD);
		}
		log.info("Entered ScoreSheetAction - getCandidates");
		return mapping.findForward(CMSConstants.FORGOT_PASSWORD);
	}
	
	/**
	 * Method to set the required data to the form to display it in forgot.jsp
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initAdminForgotPassword(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered initForgotPassword input");
		StudentForgotPasswordForm studentForgotPasswordForm = (StudentForgotPasswordForm) form;
		Organisation organisation = OrganizationHandler.getInstance().getRequiredFile();
		if(organisation!=null){
			// set photo to session
			if(organisation.getLogoContentType()!=null){
				HttpSession session= request.getSession(false);
				if(session!=null){
					session.setAttribute(CMSConstants.KNOWLEDGEPRO_LOGO, organisation.getLogo());
					session.setAttribute(CMSConstants.KNOWLEDGEPRO_TOPBAR, organisation.getTopbar());
				}
			}
		}
		studentForgotPasswordForm.resetFields();
		log.info("Exit initForgotPassword input");
		
		return mapping.findForward(CMSConstants.ADMIN_FORGOT_PASSWORD);
	}
	/**
	 * Method to select the candidates for score sheet result entry based on the input selected
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward resetAdminForgotPassword(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered ScoreSheetAction - getCandidates");
		
		StudentForgotPasswordForm studentForgotPasswordForm = (StudentForgotPasswordForm) form;
		ActionMessages messages=new ActionMessages(); 
		ActionErrors errors = studentForgotPasswordForm.validate(mapping, request);
		if (errors.isEmpty()) {
			try {
				boolean isValidData=StudentForgotPasswordHandler.getInstance().checkValidUser(studentForgotPasswordForm);
				if (!isValidData) {
					errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.usermanagement.admin.forgotpass.validuser"));
					saveErrors(request, errors);
					log.info("Exit Interview Batch Result - getSelectedCandidates size 0");
					return mapping.findForward(CMSConstants.ADMIN_FORGOT_PASSWORD);
				} 
				boolean isSend = StudentForgotPasswordHandler.getInstance().sendMailToUser(studentForgotPasswordForm);
				if(isSend){
					String emails = "";
					if(studentForgotPasswordForm.getUser().getEmployee() != null && studentForgotPasswordForm.getUser().getEmployee().getWorkEmail() != null){
						emails= emails +studentForgotPasswordForm.getUser().getEmployee().getWorkEmail();
					}
					if(studentForgotPasswordForm.getUser().getEmployee() != null && studentForgotPasswordForm.getUser().getEmployee().getOtherEmail() != null &&
							!studentForgotPasswordForm.getUser().getEmployee().getWorkEmail().equalsIgnoreCase(studentForgotPasswordForm.getUser().getEmployee().getOtherEmail()) ){
						emails=emails+","+studentForgotPasswordForm.getUser().getEmployee().getOtherEmail();
					}
					messages.add(CMSConstants.MESSAGES, new ActionError("knowledgepro.usermanagement.forgotpass.success",emails));
					studentForgotPasswordForm.resetFields();
					saveMessages(request, messages);
				}else{
					messages.add(CMSConstants.ERROR, new ActionError("knowledgepro.usermanagement.admin.forgotpass.failure"));
					studentForgotPasswordForm.resetFields();
					saveMessages(request, messages);
				}
			}  catch (Exception exception) {
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.usermanagement.admin.forgotpass.failure"));
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.ADMIN_FORGOT_PASSWORD);
			}
		} else {
			addErrors(request, errors);
			log.info("Exit Interview Batch Result - getSelectedCandidates errors not empty ");
			return mapping.findForward(CMSConstants.ADMIN_FORGOT_PASSWORD);
		}
		log.info("Entered ScoreSheetAction - getCandidates");
		return mapping.findForward(CMSConstants.ADMIN_FORGOT_PASSWORD);
	}
}
