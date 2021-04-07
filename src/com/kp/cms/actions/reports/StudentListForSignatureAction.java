package com.kp.cms.actions.reports;

import java.util.Calendar;
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
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.reports.StudentListForSignatureForm;
import com.kp.cms.handlers.admin.OrganizationHandler;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.reports.StudentListForSignatureHandler;
import com.kp.cms.to.admin.OrganizationTO;
import com.kp.cms.to.reports.StudentListForSignatureTO;

@SuppressWarnings("deprecation")
public class StudentListForSignatureAction extends BaseDispatchAction{
	private static final Log log = LogFactory.getLog(StudentListForSignatureAction.class);

	/**
	init method
	*/
	
	public ActionForward initStudentListForSignature(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response) throws Exception {
		StudentListForSignatureForm signatureForm = (StudentListForSignatureForm)form;
		try {
			signatureForm.resetFields();
			setpClassMapToRequest(request);
			HttpSession session = request.getSession(false);
			session.removeAttribute("studentList");
		}catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			signatureForm.setErrorMessage(msg);
			signatureForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.STUD_LIST_FOR_SIGN_SEARCH);
	}
	
	/**
	 * creating list and forwarding to jsp page
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward SearchStudentListForSignature(ActionMapping mapping, ActionForm form, 
					HttpServletRequest request, HttpServletResponse response) throws Exception{
			
		StudentListForSignatureForm signatureForm = (StudentListForSignatureForm)form;	
		HttpSession session = request.getSession(false);
		if(session.getAttribute("studentList")==null){
			ActionMessages messages = new ActionMessages();
			ActionErrors errors = signatureForm.validate(mapping, request);
			try {
				if(!errors.isEmpty()){
					saveErrors(request, errors);
					setpClassMapToRequest(request);
					return mapping.findForward(CMSConstants.STUD_LIST_FOR_SIGN_SEARCH);
				}
				OrganizationHandler orgHandler= OrganizationHandler.getInstance();
				List<OrganizationTO> tos=orgHandler.getOrganizationDetails();
				if(tos!=null && !tos.isEmpty())
				{
					OrganizationTO orgTO=tos.get(0);
					signatureForm.setOrganizationName(orgTO.getOrganizationName());
				}				
				List<StudentListForSignatureTO> studentList = StudentListForSignatureHandler.getInstance().getStudentListForSignature(signatureForm);
				session.setAttribute("studentList", studentList);
			}catch (BusinessException businessException) {
				log.info("Exception submitPerformaReport");
				String msgKey = super.handleBusinessException(businessException);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add(CMSConstants.MESSAGES, message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}catch (Exception exception) {	
				String msg = super.handleApplicationException(exception);
				signatureForm.setErrorMessage(msg);
				signatureForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		}
		return mapping.findForward(CMSConstants.STUD_LIST_FOR_SIGN_RESULT);
	}	
	
	public ActionForward printStudent(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		return mapping.findForward(CMSConstants.STUD_LIST_FOR_SIGN_PRINT);
		 
	 }
	
	/**
	 * Sets all the classes for the current year in request scope
	 */
	private void setpClassMapToRequest(HttpServletRequest request) throws ApplicationException {
		log.info("entering into setpClassMapToRequest of StudentListForSignatureAction class.");
		try {
			Calendar calendar = Calendar.getInstance();
			int currentYear = calendar.get(Calendar.YEAR);

			Map<Integer, String> classMap = CommonAjaxHandler.getInstance().getClassesByYear(currentYear);
			log.info("exit of setpClassMapToRequest of StudentListForSignatureAction class.");
			request.setAttribute("classMap", classMap);
		} catch (Exception e) {
			log.info("error in setpClassMapToRequest of StudentListForSignatureAction class.",e);
			throw new ApplicationException(e);
		}
	}
}
