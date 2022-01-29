package com.kp.cms.actions.attendance;

import java.util.Calendar;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.attendance.NewAttendanceSmsForm;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.attendance.NewAttendanceSmsHandler;
import com.kp.cms.utilities.CurrentAcademicYear;

public class NewAttendanceSmsAction extends BaseDispatchAction
{
	private static Log log = LogFactory.getLog(NewAttendanceSmsAction.class);
	
	public ActionForward initNewAttendanceSms(ActionMapping mapping, ActionForm form,	HttpServletRequest request, 
			HttpServletResponse response) throws Exception 
	{

		log.debug("Entering initNewAttendanceSms ");
		NewAttendanceSmsForm newAttendanceSmsForm = (NewAttendanceSmsForm)form;
		try {
		String formName = mapping.getName();
		request.getSession().setAttribute(CMSConstants.FORMNAME, formName);
		setRequiredDataToForm(newAttendanceSmsForm,request);
		} catch (Exception e) {
		log.error("error submit initNewAttendanceSms page...", e);
		if (e instanceof ApplicationException) {
		String msg = super.handleApplicationException(e);
		newAttendanceSmsForm.setErrorMessage(msg);
		newAttendanceSmsForm.setErrorStack(e.getMessage());
		return mapping.findForward(CMSConstants.ERROR_PAGE);
		} else {
		throw e;
		}
		}
		
		log.debug("Leaving initNewAttendanceSms ");
		
		return mapping.findForward(CMSConstants.NEW_ATTENDANCE_SMS);
		}
	private void setRequiredDataToForm(NewAttendanceSmsForm newAttendanceSmsForm,HttpServletRequest request) throws Exception 
	{
		Calendar calendar = Calendar.getInstance();
		int currentYear = calendar.get(Calendar.YEAR);
		int year=CurrentAcademicYear.getInstance().getAcademicyear();
		 if(year!=0){
			currentYear=year;
		 }
		 Map<Integer, String> classMap= CommonAjaxHandler.getInstance().getClassesByYear(currentYear);
			request.setAttribute("classMap", classMap);		
	}
	public ActionForward getAbsentees(ActionMapping mapping, ActionForm form,	HttpServletRequest request, 
			HttpServletResponse response) throws Exception 
	{
		log.debug("Entering getAbsentees ");
		NewAttendanceSmsForm newAttendanceSmsForm = (NewAttendanceSmsForm)form;
		ActionMessages messages = new ActionMessages();
		ActionMessage message = null;
		ActionMessages errors=new ActionErrors();
		errors=newAttendanceSmsForm.validate(mapping, request);
		
		try {
				if(errors.isEmpty())
				{
					NewAttendanceSmsHandler.getInstance().getAbsentees(newAttendanceSmsForm);
					NewAttendanceSmsHandler.getInstance().getStudents(newAttendanceSmsForm);	
					boolean isMsgSent=false;
					if (newAttendanceSmsForm.getAbsenteesList() == null || newAttendanceSmsForm.getAbsenteesList().size()<=0) 
					{
						message = new ActionMessage(
								CMSConstants.ATTENDANCE_ENTRY_NORECORD);
						messages.add(CMSConstants.MESSAGES, message);
						addMessages(request, messages);
						setRequiredDataToForm(newAttendanceSmsForm,request);
						return	mapping.findForward(CMSConstants.NEW_ATTENDANCE_SMS);
					}
				}
				else
				{
					saveErrors(request, errors);					
					return	mapping.findForward(CMSConstants.NEW_ATTENDANCE_SMS);
				}
				
				} catch (Exception e) {
					log.debug(e.getMessage());
					throw e;
					
				}
		
				return mapping.findForward(CMSConstants.NEW_ATTENDANCE_SMS_ABSENTEES);
	}
	public ActionForward sendSms(ActionMapping mapping, ActionForm form,	HttpServletRequest request, 
			HttpServletResponse response) throws Exception 
	{
		log.debug("Entering getAbsentees ");
		NewAttendanceSmsForm newAttendanceSmsForm = (NewAttendanceSmsForm)form;
		ActionMessages messages = new ActionMessages();
		ActionMessage message = null;
		try {
				boolean isMsgSent=false;
				if (newAttendanceSmsForm.getAbsenteesList() != null && !newAttendanceSmsForm.getAbsenteesList().isEmpty()) 
				{
					isMsgSent=NewAttendanceSmsHandler.getInstance().sendSMS(newAttendanceSmsForm);
					if(isMsgSent)
					{
						message = new ActionMessage(
								CMSConstants.NEW_SMS_SEND_SUCCESS);
						messages.add(CMSConstants.MESSAGES, message);
						addMessages(request, messages);
					}
					else
					{
						message = new ActionMessage(
								CMSConstants.NEW_SMS_SEND_FAILED);
						messages.add(CMSConstants.MESSAGES, message);
						addMessages(request, messages);
						setRequiredDataToForm(newAttendanceSmsForm,request);
						return	mapping.findForward(CMSConstants.NEW_ATTENDANCE_SMS);
					}
					
				}				
				
				} catch (Exception e) {
					log.debug(e.getMessage());
					throw e;
					
				}
				setRequiredDataToForm(newAttendanceSmsForm,request);
				newAttendanceSmsForm.setAttendancedate(null);
				newAttendanceSmsForm.setClasses(null);
		
				return mapping.findForward(CMSConstants.NEW_ATTENDANCE_SMS);
	}
}
