package com.kp.cms.actions.attendance;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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

import com.ibm.icu.util.StringTokenizer;
import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.bo.admin.AttendancePeriod;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.attendance.AttendanceEntryForm;
import com.kp.cms.forms.pettycash.CollectionLedgerForm;
import com.kp.cms.forms.reports.ScoreSheetForm;
import com.kp.cms.handlers.admin.ProgramTypeHandler;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.attendance.AttendanceSlipDetailsHandler;
import com.kp.cms.to.admin.ProgramTypeTO;
import com.kp.cms.to.attendance.AttendanceTO;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.CurrentAcademicYear;
//AttendanceSlipDetailsAction.java
public class AttendanceSlipDetailsAction extends BaseDispatchAction {
	
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
	public ActionForward initAttendanceSlipDetails(ActionMapping mapping,
													ActionForm form, HttpServletRequest request,
													HttpServletResponse response) throws Exception {
		
		log.info("Inside of initAttendanceSlipDetails");
		AttendanceEntryForm attendanceEntryForm = (AttendanceEntryForm) form;		
		try {
			setUserId(request, attendanceEntryForm);
			attendanceEntryForm.clearAll();// clearing the data for displaying the search jsp
			// setting the necessary data to form on loading
			if(request.getSession().getAttribute("SlipDetails")!=null || request.getSession().getAttribute("PeriodList")!=null){
				request.getSession().setAttribute("SlipDetails", null);
				request.getSession().setAttribute("PeriodList",  null);
			}
			initsetDataToForm(attendanceEntryForm,request);
		}  catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			attendanceEntryForm.setErrorMessage(msg);
			attendanceEntryForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Leaving into initAttendanceEntry");
		return mapping.findForward(CMSConstants.ATTENDANCE_SLIP_DETAILS);
	}
	/**
	 * @param attendanceEntryForm
	 */
	private void initsetDataToForm(AttendanceEntryForm attendanceEntryForm,HttpServletRequest request) throws Exception{
		Map<Integer,String> classMap = setupClassMapToRequest(attendanceEntryForm,request);
		attendanceEntryForm.setClassMap(classMap);
	}
	
	
	/**
	 * Sets all the classes for the current year in request scope
	 */
	public Map<Integer,String> setupClassMapToRequest(AttendanceEntryForm attendanceEntryForm,HttpServletRequest request) throws Exception{
		log.info("Entering into setpClassMapToRequest of CreatePracticalBatchAction");
		Map<Integer,String> classMap = new HashMap<Integer, String>();
		try {
			Calendar calendar = Calendar.getInstance();
			int currentYear = calendar.get(Calendar.YEAR);
			int year=CurrentAcademicYear.getInstance().getAcademicyear();
			if(year!=0){
				currentYear=year;
			}
			if(attendanceEntryForm.getYear()!=null && !attendanceEntryForm.getYear().isEmpty()){
				currentYear=Integer.parseInt(attendanceEntryForm.getYear());
			}
			classMap = CommonAjaxHandler.getInstance().getClassesByYear(currentYear);
			request.setAttribute("classMap", classMap);
			return classMap;
		} catch (Exception e) {
			log.debug(e.getMessage());
			log.error("Error occured in setupClassMapToRequest of CreatePracticalBatchAction");
		}
		log.info("Leaving into setupClassMapToRequest of CreatePracticalBatchAction");
		return classMap;
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getAttendanceSlipDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
		AttendanceEntryForm attendanceEntryForm = (AttendanceEntryForm) form;
		 ActionErrors errors = attendanceEntryForm.validate(mapping, request);
		validateTime(attendanceEntryForm, errors);
		validateClasses(attendanceEntryForm, errors);
		if (errors.isEmpty()) {
		
			try {
				initsetDataToForm(attendanceEntryForm,request);
				AttendanceSlipDetailsHandler attendanceSlipDetailsHandler=AttendanceSlipDetailsHandler.getInstance();
				Map<String, Map<String,List<AttendanceTO>>> slipDetails=attendanceSlipDetailsHandler.getAttendanceSlipDetails(attendanceEntryForm, request);
				attendanceEntryForm.setClassName(attendanceSlipDetailsHandler.getClassNames(attendanceEntryForm));
				List<String> periodList=attendanceSlipDetailsHandler.getPeriodList(attendanceEntryForm,request);
				if (slipDetails.isEmpty()) {
					errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND));
					saveErrors(request, errors);
					initsetDataToForm(attendanceEntryForm, request);
					log.info("Exit Slip Deatils - getAttendanceSlipDetails size 0");
					return mapping.findForward(CMSConstants.ATTENDANCE_SLIP_DETAILS);
				} 
				request.getSession().setAttribute("PeriodList",periodList);
				request.getSession().setAttribute("SlipDetails", slipDetails);
				
			}  catch (Exception exception) {
				String msg = super.handleApplicationException(exception);
				attendanceEntryForm.setErrorMessage(msg);
				attendanceEntryForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		} else {
			addErrors(request, errors);
			initsetDataToForm(attendanceEntryForm, request);			
			log.info("Exit AttendanceSlipDeatails Result - getAttendanceSlipDetails errors not empty ");
			return mapping.findForward(CMSConstants.ATTENDANCE_SLIP_DETAILS);
		}
		log.info("Entered AttendanceSlipDetailsAction - getSlipDetails");
		return mapping.findForward(CMSConstants.ATTENDANCE_SLIP_DETAILS);
	}
	
	/*
	 * validate the start date and end date,checks start date is greater than end date
	*/
	private void validateTime(AttendanceEntryForm attendanceEntryForm, ActionErrors errors) {
		
		if(attendanceEntryForm.getFromDate()!=null && !StringUtils.isEmpty(attendanceEntryForm.getFromDate())&& !CommonUtil.isValidDate(attendanceEntryForm.getFromDate())){
			if (errors.get(CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID) != null&& !errors.get(CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID).hasNext()) {
				errors.add(CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID,new ActionError(CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID));
			}
		}
		if(attendanceEntryForm.getToDate()!=null && !StringUtils.isEmpty(attendanceEntryForm.getToDate())&& !CommonUtil.isValidDate(attendanceEntryForm.getToDate())){
			if (errors.get(CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID) != null&& !errors.get(CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID).hasNext()) {
				errors.add(CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID,new ActionError(CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID));
			}
		}
		if(CommonUtil.checkForEmpty(attendanceEntryForm.getFromDate()) && CommonUtil.checkForEmpty(attendanceEntryForm.getToDate())){
			Date startDate = CommonUtil.ConvertStringToDate(attendanceEntryForm.getFromDate());
			Date endDate = CommonUtil.ConvertStringToDate(attendanceEntryForm.getToDate());

			Calendar cal1 = Calendar.getInstance();
			cal1.setTime(startDate);
			Calendar cal2 = Calendar.getInstance();
			cal2.setTime(endDate);
			long daysBetween = CommonUtil.getDaysBetweenDates(cal1, cal2);
			if(daysBetween <= 0) {
				errors.add("error", new ActionError(CMSConstants.KNOWLEDGEPRO_STARTDATE_CONNOTBELESS));
			}
		}
	}
	
	public ActionForward printAttendanceSlipDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered into printAttendanceSlipDetails");
		
		log.info("Exit from printAttendanceSlipDetails");
		return mapping.findForward(CMSConstants.PRINT_ATTENDANCE_SLIPDETAILS);
	} 
	
	private void validateClasses(AttendanceEntryForm attendanceEntryForm,ActionErrors errors){
		String[] classes=attendanceEntryForm.getClasses();
		StringBuilder className = new StringBuilder();
		for(int i=0;i<classes.length;i++){
			//className=className+classes[i];
			className.append(classes[i]);
		}
		if(className.toString().isEmpty()){
			errors.add(CMSConstants.ERROR,new ActionError("errors.required","class"));
		}
	}
	
}
