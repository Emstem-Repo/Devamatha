package com.kp.cms.actions.attendance;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
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
import com.kp.cms.bo.admin.Student;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.attendance.AttendanceReEntryForm;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.attendance.AttendanceReEntryHandler;
import com.kp.cms.to.attendance.AttendanceReEntryTo;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.CurrentAcademicYear;

public class AttendanceReEntryAction extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(AttendanceEntryAction.class);
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request 
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 * This method will be invoke when the particular link clicked.
	 * 
	 */
	public ActionForward initAttendanceEntry(ActionMapping mapping,
													ActionForm form, HttpServletRequest request,
													HttpServletResponse response) throws Exception {
		
		log.info("Inside of initAttendanceEntry");
		AttendanceReEntryForm attendanceReEntryForm = (AttendanceReEntryForm) form;		
		try {
			setUserId(request, attendanceReEntryForm);
			attendanceReEntryForm.clearAll();
			// setting the necessary data to form on loading
			initsetDataToForm(attendanceReEntryForm,request);
		}  catch (Exception e) {
			String msg = super.handleApplicationException(e);
			attendanceReEntryForm.setErrorMessage(msg);
			attendanceReEntryForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Leaving into initAttendanceEntry");
		return mapping.findForward(CMSConstants.ATTENDANCE_RE_ENTRY_FIRST);
	}
	/**
	 * @param attendanceReEntryForm
	 */
	private void initsetDataToForm(AttendanceReEntryForm attendanceReEntryForm,HttpServletRequest request) throws Exception{
		Map<Integer,String> classMap = setupClassMapToRequest(attendanceReEntryForm,request);
		attendanceReEntryForm.setClassMap(classMap);
	}
	
	
	/**
	 * Sets all the classes for the current year in request scope
	 */
	public Map<Integer,String> setupClassMapToRequest(AttendanceReEntryForm attendanceReEntryForm,HttpServletRequest request) throws Exception{
		log.info("Entering into setpClassMapToRequest of CreatePracticalBatchAction");
		Map<Integer,String> classMap = new HashMap<Integer, String>();
		try {
			Calendar calendar = Calendar.getInstance();
			int currentYear = calendar.get(Calendar.YEAR);
			int year=CurrentAcademicYear.getInstance().getAcademicyear();
			if(year!=0){
				currentYear=year;
			}
			if(attendanceReEntryForm.getYear()!=null && !attendanceReEntryForm.getYear().isEmpty()){
				currentYear=Integer.parseInt(attendanceReEntryForm.getYear());
			}
			classMap = CommonAjaxHandler.getInstance().getClassesByYear(currentYear);
			request.setAttribute("classMap", classMap);
			return classMap;
		} catch (Exception e) {
			log.debug(e.getMessage());
			log.error("Error occured in setpClassMapToRequest of CreatePracticalBatchAction");
		}
		log.info("Leaving into setpClassMapToRequest of CreatePracticalBatchAction");
		return classMap;
	}
	
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request 
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 * This will be called when second page of attendance will be needed.
	 * this method will redirect to different pages based on the requirement.
	 */
	public ActionForward initAttendanceReEntrySecondPage(ActionMapping mapping,
													ActionForm form, HttpServletRequest request,
													HttpServletResponse response) throws Exception {
		
		log.info("Inside of initAttendanceEntrySecondPage");
		AttendanceReEntryForm attendanceReEntryForm = (AttendanceReEntryForm) form;		
		ActionErrors errors=attendanceReEntryForm.validate(mapping, request);
		validateTime(attendanceReEntryForm, errors);
		try { 
			if(errors.isEmpty()) {
				List<Student> stuList=AttendanceReEntryHandler.getInstance().getStudents(attendanceReEntryForm);
				if(stuList==null || stuList.isEmpty()){
					errors.add("error", new ActionError("admissionFormForm.attendanceReentry.student.notExist"));
				}
//				List<Object[]> attList=AttendanceReEntryHandler.getInstance().getDuplicateAttendance(attendanceReEntryForm);
//				if(attList!=null && !attList.isEmpty()){
//					if(attendanceReEntryForm.getRange()!=null)
//					errors.add("error",new ActionError("knowledgePro.attendanceReentry.student.attendanceAlreadyExist",attendanceReEntryForm.getRange()));
//				}
				if(errors.isEmpty()){
					Map<Date, AttendanceReEntryTo> attendanceReEntryTos=AttendanceReEntryHandler.getInstance().getAttendanceList(attendanceReEntryForm);
					if(attendanceReEntryTos.isEmpty()){
						errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND));
						saveErrors(request, errors);
						initsetDataToForm(attendanceReEntryForm,request);
						log.info("Exit initAttendanceReEntrySecondPage - getSelectedCandidates size 0");
						return mapping.findForward(CMSConstants.ATTENDANCE_RE_ENTRY_FIRST);
					}else{
						Collection<AttendanceReEntryTo> clist=attendanceReEntryTos.values();
						List<AttendanceReEntryTo> list=new ArrayList<AttendanceReEntryTo>(clist);
						Collections.sort(list);
						attendanceReEntryForm.setList(list);
						attendanceReEntryForm.setAttendanceReEntryTos(attendanceReEntryTos);
					}
				}else{
					initsetDataToForm(attendanceReEntryForm,request);
					saveErrors(request, errors);
					return mapping.findForward(CMSConstants.ATTENDANCE_RE_ENTRY_FIRST);
				}
			} else {
				initsetDataToForm(attendanceReEntryForm,request);
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.ATTENDANCE_RE_ENTRY_FIRST);
			}
		}  catch (Exception e) {
	 		log.debug(e.getMessage());
	 		initsetDataToForm(attendanceReEntryForm,request);
	 		saveErrors(request,errors);
	 		return mapping.findForward(CMSConstants.ATTENDANCE_RE_ENTRY_FIRST);
		}
		return mapping.findForward(CMSConstants.ATTENDANCE_RE_ENTRY_SECOND);
	}
	
	/**
	 * Method to validate the time format
	 * @param interviewBatchEntryForm
	 * @param errors
	 */
	private void validateTime(AttendanceReEntryForm attendanceReEntryForm, ActionErrors errors) {
		if(CommonUtil.checkForEmpty(attendanceReEntryForm.getFromDate()) && CommonUtil.checkForEmpty(attendanceReEntryForm.getToDate()) && CommonUtil.isValidDate(attendanceReEntryForm.getFromDate()) &&  CommonUtil.isValidDate(attendanceReEntryForm.getToDate())){
			Date startDate = CommonUtil.ConvertStringToDate(attendanceReEntryForm.getFromDate());
			Date endDate = CommonUtil.ConvertStringToDate(attendanceReEntryForm.getToDate());

			Calendar cal1 = Calendar.getInstance();
			cal1.setTime(startDate);
			Calendar cal2 = Calendar.getInstance();
			cal2.setTime(endDate);
			long daysBetween = CommonUtil.getDaysBetweenDates(cal1, cal2);
			if(daysBetween <= 0) {
				errors.add("error", new ActionError("admissionFormForm.attendance.startdate.enddate"));
			}
		}
	}
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request 
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 * This will be called when second page of attendance will be needed.
	 * this method will redirect to different pages based on the requirement.
	 */
	public ActionForward saveAttendanceReentry(ActionMapping mapping,
													ActionForm form, HttpServletRequest request,
													HttpServletResponse response) throws Exception {
		
		log.info("Inside of initAttendanceEntrySecondPage");
		AttendanceReEntryForm attendanceReEntryForm = (AttendanceReEntryForm) form;		
		ActionErrors errors=new ActionErrors();
		ActionMessages messages=new ActionMessages();
		try {
			boolean result=false;
			setUserId(request, attendanceReEntryForm);
			List<AttendanceReEntryTo> list=attendanceReEntryForm.getList();
			if(list!=null && !list.isEmpty()){
				result=AttendanceReEntryHandler.getInstance().saveAttendanceReEntry(list,attendanceReEntryForm);
			}
			if (result) {
				ActionMessage message = new ActionMessage("knowledgepro.attendancereentry.added.successfully");
				messages.add("messages", message);
				saveMessages(request, messages);
				attendanceReEntryForm.clearAll();
				initsetDataToForm(attendanceReEntryForm,request);
			}else{
				// failed
				errors.add("error", new ActionError("knowledgepro.attendanceentry.added.failure"));
				saveErrors(request, errors);
				initsetDataToForm(attendanceReEntryForm,request);
			}
			}  catch (Exception e) {
	 		log.debug(e.getMessage());
	 		initsetDataToForm(attendanceReEntryForm,request);
	 		return mapping.findForward(CMSConstants.ATTENDANCE_RE_ENTRY_FIRST);
		}
		return mapping.findForward(CMSConstants.ATTENDANCE_RE_ENTRY_FIRST);
	}
}
