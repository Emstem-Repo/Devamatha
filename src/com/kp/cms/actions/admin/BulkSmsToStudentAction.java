package com.kp.cms.actions.admin;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
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
import com.kp.cms.forms.admin.BulkSmsToStudentForm;
import com.kp.cms.handlers.admin.BulkSMStoStudentHandler;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.to.admin.StudentTO;
import com.kp.cms.utilities.CurrentAcademicYear;
import com.kp.cms.utilities.StudentNameComparator;

public class BulkSmsToStudentAction extends BaseDispatchAction{
	private static final Log log = LogFactory.getLog(BulkSmsToStudentAction.class);
	/**
	 *  sending generic sms to selected class students parent mobile number
	 * @param mapping
	 * @param form
	 * @param request 
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	
	public ActionForward initBulkSMStoStudent(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		log.debug("call of initBulkSMStoStudent method in BulkSmsToStudentAction.class");
		BulkSmsToStudentForm bulkSmsToStudentForm = (BulkSmsToStudentForm)form;
		ActionMessages messages = new ActionMessages();
		try
		{
			bulkSmsToStudentForm.clearAll();
			setRequiredDataToForm(request,bulkSmsToStudentForm);
		}
		catch (ApplicationException e) {
			String msg = super.handleApplicationException(e);
			bulkSmsToStudentForm.setErrorMessage(msg);
			bulkSmsToStudentForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		catch (Exception e) {
			e.printStackTrace();
			log.error("Error in BulkSmsToStudentAction.class.." + e.getMessage());
			String msg = super.handleApplicationException(e);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.debug("end of initBulkSMStoStudent method in BulkSmsToStudentAction.class");
		return mapping.findForward(CMSConstants.BULK_SMS_TO_STUDENTS_FIRST_PAGE);
		
	}
	private void setRequiredDataToForm(HttpServletRequest request,BulkSmsToStudentForm bulkSmsToStudentForm)throws Exception {
		log.debug("call of setRequiredDataToForm method in BulkSmsToStudentAction.class");
		setUserId(request, bulkSmsToStudentForm);
		if(bulkSmsToStudentForm.getAcademicYear()==null || bulkSmsToStudentForm.getAcademicYear().isEmpty())
		{
			Calendar calendar = Calendar.getInstance();
			int currentYear = calendar.get(Calendar.YEAR);
			int year = CurrentAcademicYear.getInstance().getAcademicyear();
			if (year != 0) {
				currentYear = year;
			}
			bulkSmsToStudentForm.setAcademicYear(Integer.toString(currentYear));
		}
		Map<Integer, String> classMap= new HashMap<Integer, String>();
		classMap = CommonAjaxHandler.getInstance().getClassesByYear(Integer.parseInt(bulkSmsToStudentForm.getAcademicYear()));
		bulkSmsToStudentForm.setClassMap(classMap);
		log.debug("end of setRequiredDataToForm method in BulkSmsToStudentAction.class");
		
	}
	public ActionForward getStudentListForSMS(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception
	{
		log.debug("call of getStudentListForSMS method in BulkSmsToStudentAction.class");
		BulkSmsToStudentForm bulkSmsToStudentForm = (BulkSmsToStudentForm)form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = bulkSmsToStudentForm.validate(mapping, request);
		try
		{
			
			if(errors.isEmpty())
			{
				if(bulkSmsToStudentForm.getClassIds()!=null)
				{
					String name = "";
					for(String id:bulkSmsToStudentForm.getClassIds())
					{
						name= name+bulkSmsToStudentForm.getClassMap().get(Integer.parseInt(id)) + " , ";
					}
					bulkSmsToStudentForm.setClassNamesForDisplay(name);
				}
				
				List<StudentTO> studentList = new ArrayList<StudentTO>();
				studentList =  BulkSMStoStudentHandler.getInstance().getStudentListForClassIds(bulkSmsToStudentForm);
				StudentNameComparator studentName = new StudentNameComparator();
				Collections.sort(studentList, studentName);
				bulkSmsToStudentForm.setStudentList(studentList);
				
				
			}
			else
			{
				addErrors(request, errors);
				setRequiredDataToForm(request,bulkSmsToStudentForm);
				log.error("Exit sendSMSExamMarksToStudent - SmsExamMarksToStudentAction.class");
				return mapping.findForward(CMSConstants.BULK_SMS_TO_STUDENTS_FIRST_PAGE);
			}
		}
		catch (Exception e) {
			log.error("Error in getStudentListForSMS method in BulkSmsToStudentAction.class");
			String msg = super.handleApplicationException(e);
			
			return mapping.findForward(CMSConstants.ERROR_PAGE);
			
		}
		log.debug("end  of getStudentListForSMS method in BulkSmsToStudentAction.class");
		return mapping.findForward(CMSConstants.BULK_SMS_TO_STUDENTS_SECOND_PAGE);
	}
	public ActionForward sendBulkSMStoStudent(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		log.debug("call of sendBulkSMStoStudent method in BulkSmsToStudentAction.class ");
		BulkSmsToStudentForm bulkSmsToStudentForm = (BulkSmsToStudentForm)form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = bulkSmsToStudentForm.validate(mapping, request);
		boolean isSmsSent=false;
		int studentCounts =0;
		try
		{
			if(!errors.isEmpty())
			{
				addErrors(request, errors);

				setRequiredDataToForm(request,bulkSmsToStudentForm);
				log.error("Exit sendBulkSMStoStudent - BulkSmsToStudentAction.class");
			}
			else
			{
				// sent
				List<StudentTO> smsStudentList = new ArrayList<StudentTO>();
				Iterator<StudentTO> iterator = bulkSmsToStudentForm.getStudentList().iterator();
				while(iterator.hasNext())
				{
					StudentTO  studentTO = iterator.next();
					if(studentTO.isChecked())
					{
						smsStudentList.add(studentTO);
						studentCounts = studentCounts+1;
					}
				}
				if(studentCounts<=0)
				{
					ActionMessage message = new ActionMessage("knowledgepro.new.attendance.new.sms.sending.norecords");
					messages.add("messages", message);
					saveMessages(request, messages);
					bulkSmsToStudentForm.clearAll();
					setRequiredDataToForm(request, bulkSmsToStudentForm);
				}
				else
				{

					isSmsSent  = BulkSMStoStudentHandler.getInstance().sentSMStoStudents(bulkSmsToStudentForm,smsStudentList);
				}


				if(isSmsSent)
				{
					ActionMessage message = new ActionMessage("knowledgepro.new.attendance.new.percentage.sms.sending.success",studentCounts);
					messages.add("messages", message);
					saveMessages(request, messages);
					bulkSmsToStudentForm.clearAll();
					setRequiredDataToForm(request, bulkSmsToStudentForm);
				}
				else
				{
					ActionMessage message = new ActionMessage("knowledgepro.admin.sms.marks.student.failure");
					messages.add("messages", message);
					saveMessages(request, messages);
					bulkSmsToStudentForm.clearAll();
					setRequiredDataToForm(request, bulkSmsToStudentForm);
				}

			}
		}
		catch (Exception e) {
			e.printStackTrace();
			log.error("Error in BulkSmsToStudentAction.class.." + e.getMessage());
			String msg = super.handleApplicationException(e);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.debug("end of sendBulkSMStoStudent method in BulkSmsToStudentAction.class ");
		return mapping.findForward(CMSConstants.BULK_SMS_TO_STUDENTS_FIRST_PAGE);
	}
}
