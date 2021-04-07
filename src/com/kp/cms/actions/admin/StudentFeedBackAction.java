package com.kp.cms.actions.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
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
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.admin.StudentFeedBackForm;
import com.kp.cms.handlers.admin.StudentFeedBackHandler;

public class StudentFeedBackAction extends BaseDispatchAction{
	private static final Log log = LogFactory.getLog(StudentFeedBackAction.class);
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initStudentFeedback(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		StudentFeedBackForm studentFeedBackForm = (StudentFeedBackForm) form;
		studentFeedBackForm.resetFields();
//		HttpSession session = request.getSession(false);
//		String username = (String) session.getAttribute("USERNAME");
//		String emailId = StudentFeedBackHandler.getInstance().getStudentEmailId(username);
//		studentFeedBackForm.setEmail(emailId);
		
		return mapping.findForward(CMSConstants.INIT_STUDENT_FEEDBACK);
		
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "deprecation", "deprecation" })
	public ActionForward addStudentFeedback(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		StudentFeedBackForm studentFeedBackForm = (StudentFeedBackForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = studentFeedBackForm.validate(mapping, request);
		try {
			HttpSession session = request.getSession();
			String studentid = (String) session.getAttribute("studentid");
			validateMobileNo(studentFeedBackForm, errors);
			if(errors.isEmpty()){
				if(studentFeedBackForm.getFeedBack() != null && !StringUtils.isEmpty(studentFeedBackForm.getFeedBack()) && studentFeedBackForm.getEmail() != null && !StringUtils.isEmpty(studentFeedBackForm.getEmail())){
					boolean isadded=StudentFeedBackHandler.getInstance().addStudentFeedBack(studentFeedBackForm,studentid);
					String username = (String) session.getAttribute("USERNAME");
					studentFeedBackForm.setUsername(username);
					//boolean isSent=
							StudentFeedBackHandler.getInstance().sendMailToAdmin(studentFeedBackForm);
//					if(isSent){
//						ActionMessage message = new ActionMessage(CMSConstants.STUDENT_FEEDBACK_MAIL_SENT);
//						messages.add("messages", message);
//						saveMessages(request, messages);	
//					}
//					else{
//						errors.add("errors",new ActionError(CMSConstants.STUDENT_FEEDBACK_MAIL_SENT_FAILED));
//						saveErrors(request, errors);
//					}
					//add success
					if(isadded){
						ActionMessage message = new ActionMessage(CMSConstants.STUDENT_FEEDBACK_ADDSUCCESS);
						messages.add("messages", message);
						saveMessages(request, messages);
						studentFeedBackForm.resetFields();
					}
					//add fails
					if(!isadded){
						errors.add("errors",new ActionError(CMSConstants.STUDENT_FEEDBACK_ADDFAILURE));
						saveErrors(request, errors);
					}
				}
			}else{
				saveErrors(request, errors);
			}
			
		}catch (BusinessException businessException) {
			log.error("error occured in execute of StudentReqStatusAction class.",businessException);
			String msgKey = super.handleBusinessException(businessException);
			studentFeedBackForm.setErrorMessage(msgKey);
			studentFeedBackForm.setErrorStack(businessException.getMessage());
			return mapping.findForward(CMSConstants.STUDENT_ERROR_PAGE);
		} catch (Exception exception) {
			log.error("error occured in execute of StudentReqStatusAction class.",exception);
			String msg = super.handleApplicationException(exception);
			studentFeedBackForm.setErrorMessage(msg);
			studentFeedBackForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.STUDENT_ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.INIT_STUDENT_FEEDBACK);
		
	}
	@SuppressWarnings("deprecation")
	private void validateMobileNo(StudentFeedBackForm studentFeedBackForm, ActionErrors errors){
		if(studentFeedBackForm.getMobileNo().length()>10 || studentFeedBackForm.getMobileNo().length()<10){
			errors.add(CMSConstants.STUDENTFEEDBACK_MOBILENO,new ActionError(CMSConstants.STUDENTFEEDBACK_MOBILENO));
			
		}
	}
}
