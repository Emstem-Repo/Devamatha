package com.kp.cms.actions.attendance;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
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
import com.kp.cms.forms.attendance.CocurricularAttendnaceEntryForm;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.attendance.AttendanceReEntryHandler;
import com.kp.cms.handlers.attendance.CocurricularAttendnaceEntryHandler;
import com.kp.cms.to.admin.PeriodTONew;
import com.kp.cms.to.attendance.AttendanceReEntryTo;
import com.kp.cms.to.attendance.CocurricularAttendnaceEntryTo;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.CurrentAcademicYear;

public class CocurricularAttendnaceEntryAction extends BaseDispatchAction{
	private static final String FROM_DATEFORMAT="dd/MM/yyyy";
	private static final String SQL_DATEFORMAT="dd-MMM-yyyy";
	private static final Log log = LogFactory.getLog(CocurricularAttendnaceEntryAction.class);
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
	public ActionForward initCocurricularAttendnaceEntry(ActionMapping mapping,ActionForm form, HttpServletRequest request,HttpServletResponse response) throws Exception
	{
       log.debug("call of initCocurricularAttendnaceEntry method in CocurricularAttendnaceEntryAction.class");
       CocurricularAttendnaceEntryForm cocurricularAttendnaceEntryForm =(CocurricularAttendnaceEntryForm) form;
       try
       {
    	   cocurricularAttendnaceEntryForm.clearAll();
    	   setRequiredDatatoForm(cocurricularAttendnaceEntryForm, request);
       }
       catch (Exception e) {
	 	e.printStackTrace();
	 	log.error("Error in initCocurricularAttendnaceEntry method in CocurricularAttendnaceEntryAction.class");
	 	log.error("Error is .." + e.getMessage());
	 	String msg = super.handleApplicationException(e);
	 	cocurricularAttendnaceEntryForm.setErrorMessage(msg);
	 	cocurricularAttendnaceEntryForm.setErrorStack(e.getMessage());
		return mapping.findForward(CMSConstants.ERROR_PAGE);
	   }
       log.debug("end of initCocurricularAttendnaceEntry method in CocurricularAttendnaceEntryAction.class");
        return mapping.findForward(CMSConstants.COCURRICULAR_ATTENDANCE_ENTRY);
	}
	/*
	 *  setting required data to form
	 * */
	public  void setRequiredDatatoForm(CocurricularAttendnaceEntryForm cocurricularAttendnaceEntryForm,HttpServletRequest request) {
		log.debug("call of setRequiredDatatoForm method in CocurricularAttendnaceEntryAction.class");
		Map<Integer, String> classMap =  new HashMap<Integer, String>();
		try {
			Calendar calendar = Calendar.getInstance();
			int currentYear = calendar.get(Calendar.YEAR);
			int year=CurrentAcademicYear.getInstance().getAcademicyear();
			if(year!=0){
				currentYear=year;
			}
			if(cocurricularAttendnaceEntryForm.getYear()!=null && !cocurricularAttendnaceEntryForm.getYear().isEmpty()){
				currentYear=Integer.parseInt(cocurricularAttendnaceEntryForm.getYear());
			}
			classMap = CommonAjaxHandler.getInstance().getClassesByYear(currentYear);
			request.setAttribute("classMap", classMap);
			cocurricularAttendnaceEntryForm.setClassMap(classMap);
			//raghu default value
			cocurricularAttendnaceEntryForm.setOprationMode("approve");
			
		} catch (Exception e) {
			log.debug(e.getMessage());
			log.error("Error occured in setRequiredDatatoForm of CocurricularAttendnaceEntryAction.class");
		}
		log.debug("end of setRequiredDatatoForm method in CocurricularAttendnaceEntryAction.class");
		
	}
	/*
	 *  forward to second page
	 * */
	public ActionForward initCocurricularAttendnaceEntrySecondPage(ActionMapping mapping,ActionForm form, HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		log.debug("call of initCocurricularAttendnaceEntrySecondPage method in CocurricularAttendnaceEntryAction.class");
		CocurricularAttendnaceEntryForm cocurricularAttendnaceEntryForm =(CocurricularAttendnaceEntryForm) form;
		ActionErrors errors = cocurricularAttendnaceEntryForm.validate(mapping, request);
		validateTime(cocurricularAttendnaceEntryForm, errors);
		setUserId(request, cocurricularAttendnaceEntryForm);
		try
		{   	
			if(errors.isEmpty())
		{
			List<Student> stuList=CocurricularAttendnaceEntryHandler.getInstance().getStudents(cocurricularAttendnaceEntryForm);
			if(stuList==null || stuList.isEmpty()){
				errors.add("error", new ActionError("admissionFormForm.attendanceReentry.student.notExist"));
			}
		}
			if(errors.isEmpty())
				
			{
				
				Map<Date, CocurricularAttendnaceEntryTo> cocurricularAttendnaceEntryTos = CocurricularAttendnaceEntryHandler.getInstance().getCocurricularAttendanceMap(cocurricularAttendnaceEntryForm);
				if(cocurricularAttendnaceEntryTos.isEmpty()){
					errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND));
					saveErrors(request, errors);
					setRequiredDatatoForm(cocurricularAttendnaceEntryForm,request);
					log.info("Exit initAttendanceReEntrySecondPage - getSelectedCandidates size 0");
					return mapping.findForward(CMSConstants.COCURRICULAR_ATTENDANCE_ENTRY);
				}else{
					Map<Integer, String> cocurricularActivity= new HashMap<Integer, String>();
					cocurricularActivity =  CocurricularAttendnaceEntryHandler.getInstance().getActivityMap();
					cocurricularAttendnaceEntryForm.setCocurriculartActivityMap(cocurricularActivity);
					Collection<CocurricularAttendnaceEntryTo> clist=cocurricularAttendnaceEntryTos.values();
					List<CocurricularAttendnaceEntryTo> list=new ArrayList<CocurricularAttendnaceEntryTo>(clist);
					Collections.sort(list);					
					cocurricularAttendnaceEntryForm.setList(list);
					cocurricularAttendnaceEntryForm.setCocurricularAttendanceEntryToList(cocurricularAttendnaceEntryTos);
				}
			}
			else
			{
				setRequiredDatatoForm(cocurricularAttendnaceEntryForm,request);
		 		saveErrors(request,errors);
		 		return mapping.findForward(CMSConstants.COCURRICULAR_ATTENDANCE_ENTRY); 
			}
			
		}
		catch (Exception e) {
			log.debug(e.getMessage());
			setRequiredDatatoForm(cocurricularAttendnaceEntryForm,request);
	 		saveErrors(request,errors);
	 		return mapping.findForward(CMSConstants.COCURRICULAR_ATTENDANCE_ENTRY);
		}
		log.debug("end of initCocurricularAttendnaceEntrySecondPage method in CocurricularAttendnaceEntryAction.class");
		return mapping.findForward(CMSConstants.COCURRICULAR_ATTENDANCE_ENTRY_SECOND);
	}
	/**
	 * Method to validate the time format
	 * @param errors 
	 * @param cocurricularAttendnaceEntryForm 
	 * @param interviewBatchEntryForm
	 * @param errors
	 */
	private void validateTime(CocurricularAttendnaceEntryForm cocurricularAttendnaceEntryForm, ActionErrors errors) throws Exception {
		if(CommonUtil.checkForEmpty(cocurricularAttendnaceEntryForm.getFromDate()) && CommonUtil.checkForEmpty(cocurricularAttendnaceEntryForm.getToDate())
				&& CommonUtil.isValidDate(cocurricularAttendnaceEntryForm.getFromDate()) &&  CommonUtil.isValidDate(cocurricularAttendnaceEntryForm.getToDate())){
			Date startDate = CommonUtil.ConvertStringToDate(cocurricularAttendnaceEntryForm.getFromDate());
			Date endDate = CommonUtil.ConvertStringToDate(cocurricularAttendnaceEntryForm.getToDate());
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
	public ActionForward saveCocurricularDetails(ActionMapping mapping,ActionForm form, HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		log.debug("call of saveCocurricularDetails method in CocurricularAttendnaceEntryAction.class");
		CocurricularAttendnaceEntryForm cocurricularAttendnaceEntryForm =(CocurricularAttendnaceEntryForm) form;
		ActionErrors errors = cocurricularAttendnaceEntryForm.validate(mapping, request);
		setUserId(request, cocurricularAttendnaceEntryForm);
		ActionMessages messages = new ActionMessages();
		boolean isAdded= false;
		if(errors.isEmpty())
		{
			try
			{
				

				isAdded =  CocurricularAttendnaceEntryHandler.getInstance().saveCocurricularLeaveDetails(cocurricularAttendnaceEntryForm);

				if(isAdded)
				{
					ActionMessage message = new ActionMessage("knowledgepro.attendance.cocurricular.added.successfully" ,cocurricularAttendnaceEntryForm.getStudentName());
					messages.add("messages", message);
					/*if(cocurricularAttendnaceEntryForm.getCurrLeaveCount()>25){
						errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.attendance.cocurricular.countLimit"));
						saveErrors(request, errors);
					}*/
					saveMessages(request, messages);
					cocurricularAttendnaceEntryForm.clearAll();
					setRequiredDatatoForm(cocurricularAttendnaceEntryForm, request);
				}
				else
				{
					errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.attendance.cocurricular.added.failure",cocurricularAttendnaceEntryForm.getStudentName()));
					saveErrors(request, errors);
					//ActionMessage message = new ActionMessage("knowledgepro.attendance.cocurricular.added.failure",cocurricularAttendnaceEntryForm.getStudentName());
					//messages.add("messages", message);
					//saveMessages(request, messages);
					cocurricularAttendnaceEntryForm.clearAll();
					setRequiredDatatoForm(cocurricularAttendnaceEntryForm, request);
				}
			}
			catch (Exception e) {
				log.debug(e.getMessage());
				setRequiredDatatoForm(cocurricularAttendnaceEntryForm,request);
				errors.add("error", new ActionError(e.getMessage()));
		 		saveErrors(request,errors);
		 		return mapping.findForward(CMSConstants.COCURRICULAR_ATTENDANCE_ENTRY);
			}
		}
		else
		{
			errors.add("error", new ActionError("knowledgepro.attendance.cocurricular.added.failure"));
		}
		log.debug("end of saveCocurricularDetails method in CocurricularAttendnaceEntryAction.class");
		
		return mapping.findForward(CMSConstants.COCURRICULAR_ATTENDANCE_ENTRY);
	}
	
}
