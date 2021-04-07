package com.kp.cms.actions.admin;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.admin.SendSmsToClassForm;
import com.kp.cms.forms.admin.SheduledSMSForm;
import com.kp.cms.handlers.admin.SendSmsToClassHandler;
import com.kp.cms.handlers.admin.SheduledSMSHandler;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.to.admin.StudentTO;
import com.kp.cms.utilities.CurrentAcademicYear;

public class SheduledSMSAction extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(SheduledSMSAction.class);
	
	public ActionForward initSheduledSMS(ActionMapping mapping, ActionForm form,
			HttpServletRequest request,HttpServletResponse response)throws Exception{
		SheduledSMSForm sheduledSMSForm = (SheduledSMSForm) form;		
		try {
			setUserId(request, sheduledSMSForm);
			sheduledSMSForm.resetFields();
			setDataToFormCanceMode(sheduledSMSForm);
		} catch (ApplicationException e) {
			String msg = super.handleApplicationException(e);
			sheduledSMSForm.setErrorMessage(msg);
			sheduledSMSForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception e) {
			String msg = super.handleApplicationException(e);
			sheduledSMSForm.setErrorMessage(msg);
			sheduledSMSForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Leaving into initSmsToClass");
		return mapping.findForward(CMSConstants.INITSHUDULEDSMS);
	}

	
	public void setDataToFormCanceMode(SheduledSMSForm sheduledSMSForm) throws Exception{
		// Setting classes map in to form
		Map<Integer,String> classMap = setupClassMapToRequest(sheduledSMSForm);
		sheduledSMSForm.setClassMap(classMap);
	}
	
	public Map<Integer,String> setupClassMapToRequest(SheduledSMSForm sheduledSMSForm) throws Exception{
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
		}
		return classMap;
	}
	
	public ActionForward sheduledSMS(ActionMapping mapping, ActionForm form,
			HttpServletRequest request,HttpServletResponse response)throws Exception{
		SheduledSMSForm sheduledSMSForm = (SheduledSMSForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = sheduledSMSForm.validate(mapping, request);
		if(errors.isEmpty()){
			try{
				String[] classIds = sheduledSMSForm.getClassIds();
				String claIds =null;
				if(classIds!=null && !classIds[0].equalsIgnoreCase("0")){
					for(int i=0;i<classIds.length;i++){
						if(claIds!=null){
							claIds = claIds+","+classIds[i];
						}else {
							claIds = classIds[i];
						}
					}
				}
				if(!StringUtils.isNumeric(sheduledSMSForm.getHours()) || !StringUtils.isNumeric(sheduledSMSForm.getMin())){
					errors.add("errors", new ActionError("knowledgepro.sms.student.validate.time"));
					saveErrors(request, errors);
					setDataToFormCanceMode(sheduledSMSForm);
					return mapping.findForward(CMSConstants.INITSHUDULEDSMS);
				}
				if(claIds!=null && errors.isEmpty()){
				List<StudentTO> studentList=SheduledSMSHandler.getInstance().getStudentForClass(claIds);
				if(studentList==null || studentList.isEmpty()){
					errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND));
					saveErrors(request, errors);
					setDataToFormCanceMode(sheduledSMSForm);
					log.info("Exit SendSmsToClassAction - sendSmsToStudents size 0");
					return mapping.findForward(CMSConstants.INITSHUDULEDSMS);
				}
				boolean isSent = SheduledSMSHandler.getInstance().sent(studentList,sheduledSMSForm);
				if(isSent){
					ActionMessage message = new ActionMessage(CMSConstants.SMS_SENT_SUCCESSFULLY);
					messages.add(CMSConstants.MESSAGES, message);
					saveMessages(request, messages);
					sheduledSMSForm.resetFields();
				}
				}else {
					errors.add("errors", new ActionError("knowledgepro.attendanceentry.class.required"));
					saveErrors(request, errors);
					setDataToFormCanceMode(sheduledSMSForm);
					return mapping.findForward(CMSConstants.INITSHUDULEDSMS);
				}
			}catch (Exception e) {
				// TODO: handle exception
				String msg = super.handleApplicationException(e);
				sheduledSMSForm.setErrorMessage(msg);
				sheduledSMSForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
			
		}else {
			addErrors(request, errors);
			setDataToFormCanceMode(sheduledSMSForm);	
			log.info("Exit SendSmsToClassAction - sendSmsToStudents errors not empty ");
			return mapping.findForward(CMSConstants.INITSHUDULEDSMS);
		}
		return mapping.findForward(CMSConstants.INITSHUDULEDSMS);
	}
}
