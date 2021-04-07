package com.kp.cms.actions.admin;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.kp.cms.forms.admin.SendSmsToClassForm;
import com.kp.cms.handlers.admin.SendSmsToClassHandler;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.to.admin.StudentTO;
import com.kp.cms.utilities.CurrentAcademicYear;

public class SendSmsToClassAction extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(SendSmsToClassAction.class);
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request 
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 * This method will be invoked when cancel attendance is invoked.
	 */
	public ActionForward initSmsToClass(ActionMapping mapping,
													ActionForm form, HttpServletRequest request,
													HttpServletResponse response) throws Exception {
		
		log.info("Inside of initSmsToClass");
		SendSmsToClassForm sendSmsToClassForm = (SendSmsToClassForm) form;		
		try {
			setUserId(request, sendSmsToClassForm);
			sendSmsToClassForm.resetFields();
			setDataToFormCanceMode(sendSmsToClassForm);
		} catch (ApplicationException e) {
			String msg = super.handleApplicationException(e);
			sendSmsToClassForm.setErrorMessage(msg);
			sendSmsToClassForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception e) {
			String msg = super.handleApplicationException(e);
			sendSmsToClassForm.setErrorMessage(msg);
			sendSmsToClassForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Leaving into initSmsToClass");
		return mapping.findForward(CMSConstants.INIT_SMS_TO_CLASS);
	}
	
	/**
	 * 
	 * @param attendanceEntryForm
	 * @throws Exception
	 * 		   This method will load the predefined data in cancel mode.
	 */
	public void setDataToFormCanceMode(SendSmsToClassForm sendSmsToClassForm) throws Exception{
		// Setting classes map in to form
		Map<Integer,String> classMap = setupClassMapToRequest(sendSmsToClassForm);
		sendSmsToClassForm.setClassMap(classMap);
	}
	/**
	 * Sets all the classes for the current year in request scope
	 */
	
	public Map<Integer,String> setupClassMapToRequest(SendSmsToClassForm sendSmsToClassForm) throws Exception{
		log.info("Entering into setpClassMapToRequest of CreatePracticalBatchAction");
		Map<Integer,String> classMap = new HashMap<Integer, String>();
		try {
			Calendar calendar = Calendar.getInstance();
			int currentYear = calendar.get(Calendar.YEAR);
			int year=CurrentAcademicYear.getInstance().getAcademicyear();
			if(year!=0){
				currentYear=year;
			}
			classMap = CommonAjaxHandler.getInstance().getClassesByYear(currentYear);
			return classMap;
		} catch (Exception e) {
			log.debug(e.getMessage());
			log.error("Error occured in setpClassMapToRequest of CreatePracticalBatchAction");
		}
		log.info("Leaving into setpClassMapToRequest of CreatePracticalBatchAction");
		return classMap;
	}
	/**
	 * Method to select the candidates forSendSmsToClassAction entry based on the input selected
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward sendSmsToStudents(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered SendSmsToClassAction - getCandidates");
		
		SendSmsToClassForm sendSmsToClassForm = (SendSmsToClassForm) form;
		ActionMessages messages=new ActionMessages();
		ActionErrors errors = sendSmsToClassForm.validate(mapping, request);
		if (errors.isEmpty()) {
			try {
				List<StudentTO> studentList=SendSmsToClassHandler.getInstance().getStudentsForClass(sendSmsToClassForm);
				if (studentList.isEmpty()) {
					errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND));
					saveErrors(request, errors);
					setDataToFormCanceMode(sendSmsToClassForm);
					log.info("Exit SendSmsToClassAction - sendSmsToStudents size 0");
					return mapping.findForward(CMSConstants.INIT_SMS_TO_CLASS);
				}
				boolean isSent=SendSmsToClassHandler.getInstance().sendSMSToStudent(studentList,sendSmsToClassForm);
				if(isSent){
					ActionMessage message = new ActionMessage(CMSConstants.SMS_SENT_SUCCESSFULLY);
					messages.add(CMSConstants.MESSAGES, message);
					saveMessages(request, messages);
					sendSmsToClassForm.resetFields();
				}else{
					errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.SMS_SENT_FAILED));
		    		saveErrors(request,errors);
				}
			}  catch (Exception exception) {
				String msg = super.handleApplicationException(exception);
				sendSmsToClassForm.setErrorMessage(msg);
				sendSmsToClassForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		} else {
			addErrors(request, errors);
			setDataToFormCanceMode(sendSmsToClassForm);	
			log.info("Exit SendSmsToClassAction - sendSmsToStudents errors not empty ");
			return mapping.findForward(CMSConstants.INIT_SMS_TO_CLASS);
		}
		log.info("Entered SendSmsToClassAction - sendSmsToStudents");
		return mapping.findForward(CMSConstants.INIT_SMS_TO_CLASS);
	}
	
	public ActionForward initSmsToStudent(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

			log.info("Inside of initSmsToStudent");
			SendSmsToClassForm sendSmsToClassForm = (SendSmsToClassForm) form;		
			sendSmsToClassForm.resetFields();
			setDataToFormCanceMode(sendSmsToClassForm);
			log.info("Leaving into initSmsToStudent");
			return mapping.findForward(CMSConstants.INIT_SMS_TO_STUDENT);
	}
	
	public ActionForward getStudentDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

			log.info("Inside of getStudentDetails");
			SendSmsToClassForm sendSmsToClassForm = (SendSmsToClassForm) form;
			ActionErrors errors = sendSmsToClassForm.validate(mapping, request);
			if (errors.isEmpty()) {
			try{
				if((sendSmsToClassForm.getApplicationNumber()!=null && sendSmsToClassForm.getApplicationNumber().length()>0)  || 
						(sendSmsToClassForm.getRegisterNumber()!=null && sendSmsToClassForm.getRegisterNumber().length()>0)){
					StudentTO studentDetails = SendSmsToClassHandler.getInstance().getDetails(sendSmsToClassForm);
					if(studentDetails!=null){
						sendSmsToClassForm.setShowDetails(true);
						sendSmsToClassForm.setShowStudentList(false);
						sendSmsToClassForm.setStudentDetail(studentDetails);
						setDataToFormCanceMode(sendSmsToClassForm);
						return mapping.findForward(CMSConstants.INIT_SMS_TO_STUDENT);
					}else {
						errors.add("error", new ActionError("knowledgepro.pettycash.norecord"));
						saveErrors(request, errors);
						setDataToFormCanceMode(sendSmsToClassForm);
						return mapping.findForward(CMSConstants.INIT_SMS_TO_STUDENT);
					}
				}
				if(sendSmsToClassForm.getClassId()!=null && !sendSmsToClassForm.getClassId().isEmpty() ){
					List<StudentTO> studentLists=SendSmsToClassHandler.getInstance().getStudentsForClass(sendSmsToClassForm);
					if(studentLists!=null && !studentLists.isEmpty()){
						sendSmsToClassForm.setStudentList(studentLists);
						sendSmsToClassForm.setShowStudentList(true);
						sendSmsToClassForm.setShowDetails(false);
						sendSmsToClassForm.setListSize(studentLists.size());
						setDataToFormCanceMode(sendSmsToClassForm);
						return mapping.findForward(CMSConstants.INIT_SMS_TO_STUDENT);
					}
				}else {
					errors.add("errors", new ActionError("knowledgepro.hostel.appno.regno.staffidSMS"));
					saveErrors(request, errors);
					setDataToFormCanceMode(sendSmsToClassForm);
					return mapping.findForward(CMSConstants.INIT_SMS_TO_STUDENT);
				}
			}catch (Exception e) {
				// TODO: handle exception
				}
			}else {
				addErrors(request, errors);
				setDataToFormCanceMode(sendSmsToClassForm);	
				log.info("Exit SendSmsToClassAction - sendSmsToStudents errors not empty ");
				return mapping.findForward(CMSConstants.INIT_SMS_TO_STUDENT);
			}
			log.info("Leaving into getStudentDetails");
			return mapping.findForward(CMSConstants.INIT_SMS_TO_STUDENT);
	}
	
	
	public ActionForward sendSmsToStudentsList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered SendSmsToClassAction - getCandidates");
		
		SendSmsToClassForm sendSmsToClassForm = (SendSmsToClassForm) form;
		ActionMessages messages=new ActionMessages();
		ActionErrors errors = sendSmsToClassForm.validate(mapping, request);
		if (errors.isEmpty()) {
			try {
				if(sendSmsToClassForm.getMessage()==null || sendSmsToClassForm.getMessage().length()==0){
					errors.add("errors", new ActionError("knowledgepro.sms.student.requreid"));
					saveErrors(request, errors);
					return mapping.findForward(CMSConstants.INIT_SMS_TO_STUDENT);
				}
					boolean isSent = SendSmsToClassHandler.getInstance().getListForSMS(sendSmsToClassForm);
					if(isSent){
						ActionMessage message = new ActionMessage(CMSConstants.SMS_SENT_SUCCESSFULLY);
						messages.add(CMSConstants.MESSAGES, message);
						saveMessages(request, messages);
						sendSmsToClassForm.resetFields();
					}
			}  catch (Exception exception) {
				String msg = super.handleApplicationException(exception);
				sendSmsToClassForm.setErrorMessage(msg);
				sendSmsToClassForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		} else {
			addErrors(request, errors);
			setDataToFormCanceMode(sendSmsToClassForm);	
			log.info("Exit SendSmsToClassAction - sendSmsToStudents errors not empty ");
			return mapping.findForward(CMSConstants.INIT_SMS_TO_STUDENT);
		}
		log.info("Entered SendSmsToClassAction - sendSmsToStudents");
		return mapping.findForward(CMSConstants.INIT_SMS_TO_STUDENT);
	}
	
	
	public ActionForward sendSmsToSingleStudent(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered SendSmsToClassAction - getCandidates");
		
		SendSmsToClassForm sendSmsToClassForm = (SendSmsToClassForm) form;
		ActionMessages messages=new ActionMessages();
		ActionErrors errors = sendSmsToClassForm.validate(mapping, request);
		if (errors.isEmpty()) {
			try {
				if(sendSmsToClassForm.getMessage()==null || sendSmsToClassForm.getMessage().length()==0){
					errors.add("errors", new ActionError("knowledgepro.sms.student.requreid"));
					saveErrors(request, errors);
					return mapping.findForward(CMSConstants.INIT_SMS_TO_STUDENT);
				}
				if(sendSmsToClassForm.getStudentDetail().getMobileNo1()==null || sendSmsToClassForm.getStudentDetail().getMobileNo1().length()==0){
					errors.add("errors", new ActionError("knowledgepro.sms.student.mobile.requreid"));
					saveErrors(request, errors);
					return mapping.findForward(CMSConstants.INIT_SMS_TO_STUDENT);
				}
				if(sendSmsToClassForm.getStudentDetail().getMobileNo2()==null || sendSmsToClassForm.getStudentDetail().getMobileNo2().length()==0){
					errors.add("errors", new ActionError("knowledgepro.sms.student.mobile.requreid"));
					saveErrors(request, errors);
					return mapping.findForward(CMSConstants.INIT_SMS_TO_STUDENT);
				}
					boolean isSent = SendSmsToClassHandler.getInstance().sendSmsToSingleStudent(sendSmsToClassForm);
					if(isSent){
						ActionMessage message = new ActionMessage(CMSConstants.SMS_SENT_SUCCESSFULLY);
						messages.add(CMSConstants.MESSAGES, message);
						saveMessages(request, messages);
						sendSmsToClassForm.resetFields();
					}
					
			}  catch (Exception exception) {
				String msg = super.handleApplicationException(exception);
				sendSmsToClassForm.setErrorMessage(msg);
				sendSmsToClassForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		} else {
			addErrors(request, errors);
			setDataToFormCanceMode(sendSmsToClassForm);	
			log.info("Exit SendSmsToClassAction - sendSmsToStudents errors not empty ");
			return mapping.findForward(CMSConstants.INIT_SMS_TO_STUDENT);
		}
		log.info("Entered SendSmsToClassAction - sendSmsToStudents");
		return mapping.findForward(CMSConstants.INIT_SMS_TO_STUDENT);
	}
}
