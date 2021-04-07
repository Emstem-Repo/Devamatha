package com.kp.cms.actions.studentLogin;

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
import com.kp.cms.actions.usermanagement.StudentLoginAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.studentLogin.StudentCarPassForm;
import com.kp.cms.forms.usermanagement.LoginForm;
import com.kp.cms.handlers.studentLogin.StudentCarPassHandler;

public class StudentCarPassAction extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(StudentCarPassAction.class);	
	/**
	 * @param form
	 * @param mapping
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward initStudentCarPass(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		StudentCarPassForm studentCarPassForm = (StudentCarPassForm) form;
		studentCarPassForm.reset(mapping, request);
		HttpSession session=request.getSession();
		setRequiredDataToForm(studentCarPassForm,session);
		return mapping.findForward(CMSConstants.INIT_STUDENT_CARPASS);
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveStudentCarDetailsAndPrint(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
        StudentCarPassForm studentCarPassForm=(StudentCarPassForm) form;
        setUserId(request, studentCarPassForm);
		HttpSession session=request.getSession();
		Integer studentId=(Integer) session.getAttribute("studentId");
		studentCarPassForm.setStudentId(String.valueOf(studentId));
		ActionErrors errors = studentCarPassForm.validate(mapping, request);
		if (errors.isEmpty()) {
			try{
			String maxCarPassAvail=CMSConstants.MAXIMUM_CAR_PASS_AVAILABILITY;
			Long regCarPasses=StudentCarPassHandler.getInstance().getRegisterCarPasses();
			if(regCarPasses!=null){
				if(regCarPasses==Integer.parseInt(maxCarPassAvail) && studentCarPassForm.isCheckDisableText()==false){
					errors.add("error", new ActionError("knowledgepro.carPass.not.available"));
					addErrors(request, errors);
					studentCarPassForm.reset(mapping, request);
					return mapping.findForward(CMSConstants.INIT_STUDENT_CARPASS);
				}else{
					studentCarPassForm.setRegCarPasses(Integer.parseInt(regCarPasses.toString()));
					StudentCarPassHandler.getInstance().saveStudentCarDetailsAndPrint(studentCarPassForm);
				}
			}
			}catch(Exception e) {
					log.debug("leaving the studentLoginAction with exception");
					String msg = super.handleApplicationException(e);
					studentCarPassForm.setErrorMessage(msg);
					studentCarPassForm.setErrorStack(e.getMessage());
					return mapping.findForward(CMSConstants.STUDENT_ERROR_PAGE);
					//throw e;
				}
		}else{
				log.debug("leaving the saveStudentCarDetailsAndPrint with errors");
				addErrors(request, errors);				
				return mapping.findForward(CMSConstants.INIT_STUDENT_CARPASS);
			}
		return mapping.findForward(CMSConstants.STUDENT_CARPASS_APPLICATION);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward printCarPass(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.debug("Entering the getHallTicket");
		return mapping.findForward(CMSConstants.PRINT_STUDENT_CAR_PASS);
	}
	/**
	 * @param studentCarPassForm
	 */
	public void setRequiredDataToForm(StudentCarPassForm studentCarPassForm,HttpSession session){
		
	   	try {
	   		Integer studentId=(Integer) session.getAttribute("studentId");
			studentCarPassForm.setStudentId(String.valueOf(studentId));
			StudentCarPassHandler.getInstance().getStudentCarPassDetails(studentCarPassForm);
		} catch (Exception e) {
			log.debug("leaving the studentLoginAction with exception");
			String msg = super.handleApplicationException(e);
			studentCarPassForm.setErrorMessage(msg);
			studentCarPassForm.setErrorStack(e.getMessage());
		}
	}
}
