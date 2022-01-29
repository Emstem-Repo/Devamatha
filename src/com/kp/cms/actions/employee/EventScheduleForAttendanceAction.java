package com.kp.cms.actions.employee;

import java.util.ArrayList;
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
import com.kp.cms.forms.attendance.AttendanceEntryForm;
import com.kp.cms.forms.employee.EventLocationBiometricDetailsForm;
import com.kp.cms.forms.employee.EventScheduleForAttendanceForm;
import com.kp.cms.forms.exam.ExamStudentEligibilityCheckForm;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.employee.EventLoactionBiometricDetailsHandler;
import com.kp.cms.handlers.employee.EventScheduleForAttendanceHandler;
import com.kp.cms.to.employee.EventLoactionBiometricDetailsTo;
import com.kp.cms.to.exam.ExamStudentEligibilityCheckTO;
import com.kp.cms.to.exam.KeyValueTO;
import com.kp.cms.utilities.CurrentAcademicYear;

public class EventScheduleForAttendanceAction extends BaseDispatchAction{
	private static final Log log = LogFactory.getLog(EventScheduleForAttendanceAction.class);
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initEventScheduleForAttendance(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		EventScheduleForAttendanceForm eventScheduleform = (EventScheduleForAttendanceForm) form;
		eventScheduleform.reset();
		setRequestedDataToForm(eventScheduleform);
		return mapping.findForward(CMSConstants.EVENT_SCHEDULE_FOR_ATTENDANCE);
	}
	/**
	 * @param eventScheduleform
	 * @throws Exception
	 */
	public void setRequestedDataToForm(EventScheduleForAttendanceForm eventScheduleform ) throws Exception {
		EventScheduleForAttendanceHandler.getInstance().getEventLocationData(eventScheduleform);
		
		int year=0;
		year=CurrentAcademicYear.getInstance().getAcademicyear();
		if(eventScheduleform.getYear()!=null && !eventScheduleform.getYear().isEmpty()){
			year=Integer.parseInt(eventScheduleform.getYear());
		}
		if(year==0){
			Calendar calendar = Calendar.getInstance();
			year = calendar.get(Calendar.YEAR);
		}
		Map<Integer,String> mapClass=CommonAjaxHandler.getInstance().getClassesByYear(year);
		eventScheduleform.setMapClass(mapClass);
		Map<Integer,String> mapDept=CommonAjaxHandler.getInstance().getDepartments();
		eventScheduleform.setMapDept(mapDept);
		EventScheduleForAttendanceHandler.getInstance().getstudentAndStaffData(eventScheduleform);
		
	}
	
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addStudentOrStaffDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		EventScheduleForAttendanceForm eventScheduleform = (EventScheduleForAttendanceForm) form;
		ActionMessages messages = new ActionMessages();
		 ActionErrors errors = eventScheduleform.validate(mapping, request);
		saveErrors(request, errors);
		validateStudentdata(eventScheduleform,errors,request);
		setUserId(request, eventScheduleform);
		if (errors.isEmpty()) {
			String a = eventScheduleform.getClassValues();
			String[] str = a.split(",");
			ArrayList<Integer> listClass = new ArrayList<Integer>();
			for (int x = 0; x < str.length; x++) {
				if (str[x] != null && str[x].length() > 0) {
					listClass.add(Integer.parseInt(str[x]));
				}
			}
			if (listClass.size() > 0) {
				//if Fromperiod and ToPeriod are not selectted
				if(eventScheduleform.getType().equalsIgnoreCase("Student") && eventScheduleform.getIsAttendanceRequired().equalsIgnoreCase("Yes")){
					if(eventScheduleform.getFromPeriod()==null || eventScheduleform.getFromPeriod().isEmpty() ||eventScheduleform.getToPeriod()==null || eventScheduleform.getToPeriod().isEmpty()){
						if(eventScheduleform.getFromPeriod()==null || eventScheduleform.getFromPeriod().isEmpty() ){
							errors.add("error", new ActionError("knowledgepro.from.period.required"));
						}else if(eventScheduleform.getToPeriod()==null || eventScheduleform.getToPeriod().isEmpty()){
							errors.add("error", new ActionError("knowledgepro.to.period.required"));
						}
						saveErrors(request, errors);
						setRequestedDataToForm(eventScheduleform);
						return mapping.findForward(CMSConstants.EVENT_SCHEDULE_FOR_ATTENDANCE);
					}
					
				}
				boolean isAdded=EventScheduleForAttendanceHandler.getInstance().addStudentOrStaffData(listClass,eventScheduleform);
				if(isAdded){
					if(eventScheduleform.getType().equalsIgnoreCase("Student")){
						ActionMessage message = new ActionMessage("knowledgepro.employee.event.student.attendance.add.success", "");
						messages.add("messages", message);
						saveMessages(request, messages);
						eventScheduleform.reset();
						}else if(eventScheduleform.getType().equalsIgnoreCase("Staff")){
							ActionMessage message = new ActionMessage("knowledgepro.employee.event.staff.attendance.add.success", "");
							messages.add("messages", message);
							saveMessages(request, messages);
							eventScheduleform.reset();
						}
				}
			}else {
				if(eventScheduleform.getType().equalsIgnoreCase("Student")){
				errors.add("error", new ActionError("knowledgepro.employee.event.student.attendance"));
				}else if(eventScheduleform.getType().equalsIgnoreCase("Staff")){
					errors.add("error", new ActionError("knowledgepro.employee.event.staff.attendance"));
				}
				saveErrors(request, errors);
			}
		} else {
			saveErrors(request, errors);
		}
		setRequestedDataToForm(eventScheduleform);
		return mapping.findForward(CMSConstants.EVENT_SCHEDULE_FOR_ATTENDANCE);
	}
	
	/**
	 * @param eventScheduleform
	 * @param errors
	 * @param request
	 * @throws Exception
	 */
	private void validateStudentdata(EventScheduleForAttendanceForm eventScheduleform, ActionErrors errors,HttpServletRequest request)  throws Exception{
		String fromTimeHours=eventScheduleform.getTimeFrom();
		String fromTimeminutes=eventScheduleform.getTimeFromMin();
		String toTimeHours=eventScheduleform.getTimeTo();
		String toTimeMinutes=eventScheduleform.getTimeToMIn();
		
		if(eventScheduleform.getType().equalsIgnoreCase("Student")){
			if(eventScheduleform.getYear()==null || eventScheduleform.getYear().isEmpty()){
				errors.add("error", new ActionError("knowledgepro.exam.ExamUpdateProcess.academicYear"));
				}
			}
		if(eventScheduleform.getTimeFrom()==null || eventScheduleform.getTimeFrom().isEmpty()||eventScheduleform.getTimeFrom().equalsIgnoreCase("00")){
			errors.add("error", new ActionError("knowledgepro.employee.from.time.required"));
		  }
		if(eventScheduleform.getTimeTo()==null || eventScheduleform.getTimeTo().isEmpty()||eventScheduleform.getTimeTo().equalsIgnoreCase("00")){
			errors.add("error", new ActionError("knowledgepro.employee.to.time.required"));
		  }
		if(fromTimeHours.equalsIgnoreCase(toTimeHours)){
			if(fromTimeminutes.equalsIgnoreCase(toTimeMinutes)){
				errors.add("error", new ActionError("knowledgepro.employee.times.equals"));
			}
		}
		if(Integer.parseInt(fromTimeHours) > Integer.parseInt(toTimeHours)){
			if(Integer.parseInt(fromTimeHours) > Integer.parseInt(toTimeHours)){
			errors.add("error", new ActionError("knowledgepro.message"));
			}
		}
		if((Integer.parseInt(fromTimeHours) == Integer.parseInt(toTimeHours)) && (Integer.parseInt(fromTimeminutes) > Integer.parseInt(toTimeMinutes))){
			errors.add("error", new ActionError("knowledgepro.message"));
			}
			saveErrors(request, errors);
	}
	
	
	public ActionForward editEventSchedule(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		EventScheduleForAttendanceForm eventScheduleform = (EventScheduleForAttendanceForm) form;
		eventScheduleform.setFlag(false);
		eventScheduleform.setFlag1(false);
		log.debug("Entering ediBiometricDetails ");
		try{
			EventScheduleForAttendanceHandler.getInstance().editBiometricDetails(
					eventScheduleform);
			request.setAttribute("Operation", "edit");
			//request.setAttribute("Update", "Update");// setting update attribute
			log.debug("Leaving ediBiometricDetails ");
		}catch (Exception e) {
			log.error("error in editing BiometricDetails...", e);
			String msg = super.handleApplicationException(e);
			eventScheduleform.setErrorMessage(msg);
			eventScheduleform.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.EVENT_SCHEDULE_FOR_ATTENDANCE);
	}
	
	
	public ActionForward updateStudentOrStaffDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		EventScheduleForAttendanceForm eventScheduleform = (EventScheduleForAttendanceForm) form;
		eventScheduleform.setFlag(false);
		ActionMessages messages = new ActionMessages();
		 ActionErrors errors = eventScheduleform.validate(mapping, request);
		saveErrors(request, errors);
		validateStudentdata(eventScheduleform,errors,request);
		setUserId(request, eventScheduleform);
		if (errors.isEmpty()) {
			String a = eventScheduleform.getClassValues();
			String[] str = a.split(",");
			ArrayList<Integer> listClass = new ArrayList<Integer>();
			for (int x = 0; x < str.length; x++) {
				if (str[x] != null && str[x].length() > 0) {
					listClass.add(Integer.parseInt(str[x]));
				}
			}
			if (listClass.size() > 0) {
				//if Fromperiod and ToPeriod are not selectted
				if(eventScheduleform.getType().equalsIgnoreCase("Student") && eventScheduleform.getIsAttendanceRequired().equalsIgnoreCase("Yes")){
					if(eventScheduleform.getFromPeriod()==null || eventScheduleform.getFromPeriod().isEmpty() ||eventScheduleform.getToPeriod()==null || eventScheduleform.getToPeriod().isEmpty()){
						if(eventScheduleform.getFromPeriod()==null || eventScheduleform.getFromPeriod().isEmpty() ){
							errors.add("error", new ActionError("knowledgepro.from.period.required"));
						}else if(eventScheduleform.getToPeriod()==null || eventScheduleform.getToPeriod().isEmpty()){
							errors.add("error", new ActionError("knowledgepro.to.period.required"));
						}
						saveErrors(request, errors);
						setRequestedDataToForm(eventScheduleform);
						return mapping.findForward(CMSConstants.EVENT_SCHEDULE_FOR_ATTENDANCE);
					}
					
				}
				boolean isUpdate=EventScheduleForAttendanceHandler.getInstance().updateStudentOrStaffDetails(listClass,eventScheduleform);
				if(isUpdate){
					if(eventScheduleform.getType().equalsIgnoreCase("Student")){
						ActionMessage message = new ActionMessage("knowledgepro.employee.event.student.attendance.update.success", "");
						messages.add("messages", message);
						saveMessages(request, messages);
						eventScheduleform.reset();
						}else if(eventScheduleform.getType().equalsIgnoreCase("Staff")){
							ActionMessage message = new ActionMessage("knowledgepro.employee.event.staff.attendance.update.success", "");
							messages.add("messages", message);
							saveMessages(request, messages);
							eventScheduleform.reset();
						}
				}else{
					request.setAttribute("Operation", "edit");
				}
			}else {
				if(eventScheduleform.getType().equalsIgnoreCase("Student")){
					Map<Integer,String> mapClass=CommonAjaxHandler.getInstance().getClassesByYear(Integer.parseInt(eventScheduleform.getYear()));
					Map<Integer, String> selectedClass=eventScheduleform.getMapSelectedClass();
					for (Integer key : selectedClass.keySet()) {
						if(mapClass.containsKey(key)){
							mapClass.remove(key);
						}
					}
					eventScheduleform.setMapClass(mapClass);
				errors.add("error", new ActionError("knowledgepro.employee.event.student.attendance"));
				}else if(eventScheduleform.getType().equalsIgnoreCase("Staff")){
					Map<Integer,String> mapDept=CommonAjaxHandler.getInstance().getDepartments();
					Map<Integer, String> selectedDeptMap=eventScheduleform.getMapSelectedDept();
					for (Integer key : selectedDeptMap.keySet()) {
						if(mapDept.containsKey(key)){
							mapDept.remove(key);
						}
					}
					eventScheduleform.setMapDept(mapDept);
					errors.add("error", new ActionError("knowledgepro.employee.event.staff.attendance"));
				}
				saveErrors(request, errors);
				request.setAttribute("Operation", "edit");
				return mapping.findForward(CMSConstants.EVENT_SCHEDULE_FOR_ATTENDANCE);
			}
		} else {
			request.setAttribute("Operation", "edit");
			saveErrors(request, errors);
		}
		setRequestedDataToForm(eventScheduleform);
		return mapping.findForward(CMSConstants.EVENT_SCHEDULE_FOR_ATTENDANCE);
	}
	
	
	
	public ActionForward deleteEventSchedule(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.debug("Entering ");
		EventScheduleForAttendanceForm eventScheduleform = (EventScheduleForAttendanceForm) form;
		ActionMessages messages = new ActionMessages();

		try {
			boolean isDeleted = EventScheduleForAttendanceHandler.getInstance()
					.deleteEventScheduleStudentOrStaffDetails(eventScheduleform);
			if (isDeleted) {
				
				if(eventScheduleform.getType().equalsIgnoreCase("Student")){
					ActionMessage message = new ActionMessage(
					"knowledgepro.employee.event.student.attendance.deleteSuccess");
					messages.add("messages", message);
					saveMessages(request, messages);
					}else if(eventScheduleform.getType().equalsIgnoreCase("Staff")){
						ActionMessage message = new ActionMessage(
						"knowledgepro.employee.event.staff.attendance.deleteSuccess");
						messages.add("messages", message);
						saveMessages(request, messages);
					}
			} else {
				if(eventScheduleform.getType().equalsIgnoreCase("Student")){
					ActionMessage message = new ActionMessage(
					"knowledgepro.employee.event.student.attendance.deleteFailure");
					messages.add("messages", message);
					saveMessages(request, messages);
					}else if(eventScheduleform.getType().equalsIgnoreCase("Staff")){
						ActionMessage message = new ActionMessage(
						"knowledgepro.employee.event.staff.attendance.deleteFailure");
						messages.add("messages", message);
						saveMessages(request, messages);
					}
			}
			setRequestedDataToForm(eventScheduleform);
		} catch (Exception e) {
			log.error("error submit biometric...", e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				eventScheduleform.setErrorMessage(msg);
				eventScheduleform.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				String msg = super.handleApplicationException(e);
				eventScheduleform.setErrorMessage(msg);
				eventScheduleform.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		}
		log.debug("Action class. Delete BiometricDetails ");
		return mapping.findForward(CMSConstants.EVENT_SCHEDULE_FOR_ATTENDANCE);
	}

	
}
