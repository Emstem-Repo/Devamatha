package com.kp.cms.actions.attendance;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import com.kp.cms.forms.attendance.ApproveLeaveForm;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.attendance.ActivityAttendenceHandler;
import com.kp.cms.handlers.attendance.ApproveLeaveHandler;
import com.kp.cms.handlers.attendance.AttendanceTypeHandler;
import com.kp.cms.to.attendance.ApproveLeaveTO;
import com.kp.cms.to.attendance.AttendanceTypeTO;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.CurrentAcademicYear;

/**
 * Performs activity attendance related actions.
 */
@SuppressWarnings("deprecation")

public class ActivityAttendanceAction extends BaseDispatchAction {
	
	private static final Log log = LogFactory.getLog(ActivityAttendanceAction.class);


	/**
	 * Initializes activity attendance screen.

	 * 
	 * 
	 * 
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initActivityAttendence(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("entering into initActivityAttendence of ActivityAttendanceAction class.");
		ApproveLeaveForm activityAttendanceForm = (ApproveLeaveForm) form;
		try {
			activityAttendanceForm.resetFields();
			setClassMapToRequest(request, activityAttendanceForm);
			setAttendanceTypeListToRequest(request, activityAttendanceForm);
		} catch (Exception e) {
			log.error("Error while initializing activity attendance"+e.getMessage());
			String msg = super.handleApplicationException(e);
			activityAttendanceForm.setErrorMessage(msg);
			activityAttendanceForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("exit of initActivityAttendence of ActivityAttendanceAction class.");
		return mapping.findForward(CMSConstants.INIT_ACTIVITY_ATTENDANCE);
	}

	/**
	 * Persists activity attendance.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward submitActivityAttendence(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("entering into submitActivityAttendence of ActivityAttendanceAction class.");
		ActionMessages messages = new ActionMessages();
		ActionMessage message = null;

		ApproveLeaveForm activityAttendanceForm = (ApproveLeaveForm) form;
		ActionErrors errors = activityAttendanceForm.validate(mapping, request);
		validateDate(activityAttendanceForm,messages);
		if(activityAttendanceForm.getCheckyesNo()==null || activityAttendanceForm.getCheckyesNo().isEmpty()){
		validateDateRange(activityAttendanceForm,messages);
		}else if(activityAttendanceForm.getCheckyesNo().equalsIgnoreCase("No")){
			activityAttendanceForm.setCheckyesNo(null);
			setClassMapToRequest(request, activityAttendanceForm);
			log.info("exit of submitActivityAttendence of ActivityAttendanceAction class.");
			return mapping.findForward(CMSConstants.INIT_ACTIVITY_ATTENDANCE);
		}
		activityAttendanceForm.setCheckyesNo(null);
		try {
			//validations for periods
			validationForPeriods(activityAttendanceForm,errors);
			
			if (errors.isEmpty() && messages.isEmpty()) {
				setUserId(request, activityAttendanceForm);
//				Calendar calendar = Calendar.getInstance();
//				int currentYear = calendar.get(Calendar.YEAR);
//				/*// code by hari
//				int year=CurrentAcademicYear.getInstance().getAcademicyear();
//				if(year!=0){
//					currentYear=year;
//				}// end
//*/				activityAttendanceForm.setYear(String.valueOf(currentYear));
				
				
				
				
				ActionMessage errorMessage =null;		
				
				String inputStr = activityAttendanceForm.getRegisterNoEntry();
				String patternStr = ",";
				String[] registerNoString = inputStr.split(patternStr);
				ArrayList<String> registerNoList = new ArrayList<String>();
				Set<String> registerNoSet = new HashSet<String>();
				
				for (String registerNo : registerNoString) {
					if (registerNo.trim().length() > 0 /*&& registerNoSet.add(registerNo.trim())*/) {
						registerNoList.add(registerNo.trim());
					}
				}
				// code modified by chandra
				
				String registerNumList=ActivityAttendenceHandler.getInstance().validateRegisterNumbers(activityAttendanceForm,registerNoList);
				
				if(registerNumList!=null && !registerNumList.isEmpty()){
					message = new ActionMessage("knowledgepro.attendance.activityattendence.invalid.class",registerNumList);
					messages.add(CMSConstants.MESSAGES,message);
					addErrors(request, messages);
					setClassMapToRequest(request, activityAttendanceForm);
					mapSelectedClassByStyleClass(activityAttendanceForm);
					setAttendanceTypeListToRequest(request, activityAttendanceForm);
					setActivityTypeToRequest(request, activityAttendanceForm);
					setPeriodsToForm(activityAttendanceForm);
					return mapping.findForward(CMSConstants.INIT_ACTIVITY_ATTENDANCE);
				}
				String a = activityAttendanceForm.getClassValues();
				String[] str = a.split(",");
				//added by mahi
				 if(activityAttendanceForm.getFromPeriod()!=null && !activityAttendanceForm.getFromPeriod().isEmpty()){
					 activityAttendanceForm.setFutureFromPeriod(activityAttendanceForm.getFromPeriod());
					 if(activityAttendanceForm.getPeriodMap()!=null && !activityAttendanceForm.getPeriodMap().isEmpty()){
						 String fromPeriodName=activityAttendanceForm.getPeriodMap().get(Integer.parseInt(activityAttendanceForm.getFromPeriod()));
						 String[] fromName=fromPeriodName.split("\\[");
						 activityAttendanceForm.setFromPeriodName(fromName[0].trim());
					 }
				 }if(activityAttendanceForm.getToPeriod()!=null && !activityAttendanceForm.getToPeriod().isEmpty()){
					 activityAttendanceForm.setFutureToPeriod(activityAttendanceForm.getToPeriod());
					 if(activityAttendanceForm.getPeriodMap()!=null && !activityAttendanceForm.getPeriodMap().isEmpty()){
						 String toPeriodName=activityAttendanceForm.getPeriodMap().get(Integer.parseInt(activityAttendanceForm.getToPeriod()));
						 String[] toName=toPeriodName.split("\\[");
						 activityAttendanceForm.setToPeriodName(toName[0].trim());
					 }
				 }
				 //end
				for (int x = 0; x < str.length; x++) {
					if (str[x] != null && str[x].length() > 0) {
						String classId=(str[x]).toString();
						activityAttendanceForm.setClassSchemewiseId(classId);
						activityAttendanceForm.setRegisterNo(ActivityAttendenceHandler.getInstance().getIsRegisterNo(activityAttendanceForm));
						ArrayList<String> regNoList=ActivityAttendenceHandler.getInstance().getStudentListByclass(activityAttendanceForm,registerNoList);
						activityAttendanceForm.setRegisterNoList(regNoList);
						
						if(regNoList!=null && !regNoList.isEmpty()){
							Iterator<String> iterator = regNoList.iterator();
							while (iterator.hasNext()) {
								String registerNo = (String) iterator.next();
								registerNoSet.add(registerNo.trim());
							}
						
				
				String error = "";
				/*if(regNoList.isEmpty()) {
					
					if (activityAttendanceForm.isRegisterNo()) {
						error = "knowledgepro.attendance.activityattendence.invaliedregisterno";
					} else {
						error = "knowledgepro.attendance.activityattendence.invaliedrollno";
					}
				} else*/ 
				if(!regNoList.isEmpty()){
					activityAttendanceForm.setRegisterNoSet(registerNoSet);
				}
				
				
				if(error.isEmpty()) {
					 errorMessage = ActivityAttendenceHandler.getInstance().validateActivityAttendence(activityAttendanceForm,regNoList);
				} else {
					errorMessage = new ActionMessage(error );				
					}
				}
				
			}
		}
				
				if (errorMessage == null) {
					//activityAttendanceForm.setRegisterNoList(registerNoList);
					boolean flag=false;
					for (int x = 0; x < str.length; x++) {
						if (str[x] != null && str[x].length() > 0) {
							String classId=(str[x]).toString();
							activityAttendanceForm.setClassSchemewiseId(classId);
							activityAttendanceForm.setRegisterNo(ActivityAttendenceHandler.getInstance().getIsRegisterNo(activityAttendanceForm));
							ArrayList<String> regNoList=ActivityAttendenceHandler.getInstance().getStudentListByclass(activityAttendanceForm,registerNoList);
							activityAttendanceForm.setRegisterNoList(regNoList);
							if(regNoList!=null && !regNoList.isEmpty()){
								if(activityAttendanceForm.getFullDay()!=null && activityAttendanceForm.getFullDay().equalsIgnoreCase("No")){
									Map<Integer, List<ApproveLeaveTO>> approveLeaveToMap = ActivityAttendenceHandler.getInstance().
																							constructCoCcurricularLeave(activityAttendanceForm, "add");
									ActivityAttendenceHandler.getInstance().updateCoCcurricularLeaveForAddMethod(approveLeaveToMap, activityAttendanceForm, "add");
									flag=true;
								}else if(activityAttendanceForm.getFullDay()!=null && activityAttendanceForm.getFullDay().equalsIgnoreCase("Yes")){
									Map<Integer, List<ApproveLeaveTO>> approveLeaveToMap1 = ActivityAttendenceHandler.getInstance().
																		constructCoCcurricularLeaveForFullDay(activityAttendanceForm, "add");
									ActivityAttendenceHandler.getInstance().updateCoCcurricularLeaveForAddMethod(approveLeaveToMap1, activityAttendanceForm, "add");
									flag=true;
								}
							}
						}
					}
					if(flag){
						
						message = new ActionMessage("knowledgepro.attendance.activityattendence.addsuccess");
						messages.add(CMSConstants.MESSAGES, message);
						addMessages(request, messages);
					}

				} else {

					messages.add(CMSConstants.MESSAGES, errorMessage);
					addErrors(request, messages);
					setClassMapToRequest(request, activityAttendanceForm);
					mapSelectedClassByStyleClass(activityAttendanceForm);
					setAttendanceTypeListToRequest(request, activityAttendanceForm);
					setActivityTypeToRequest(request, activityAttendanceForm);
					setPeriodsToForm(activityAttendanceForm);
					return mapping.findForward(CMSConstants.INIT_ACTIVITY_ATTENDANCE);
				}
			}else {
				addErrors(request, errors);
				addMessages(request, messages);
				setClassMapToRequest(request, activityAttendanceForm);
				mapSelectedClassByStyleClass(activityAttendanceForm);
				setAttendanceTypeListToRequest(request, activityAttendanceForm);
				setActivityTypeToRequest(request, activityAttendanceForm);
				setPeriodsToForm(activityAttendanceForm);
				return mapping.findForward(CMSConstants.INIT_ACTIVITY_ATTENDANCE);
			}
			activityAttendanceForm.resetFields();
			setClassMapToRequest(request, activityAttendanceForm);
			setAttendanceTypeListToRequest(request, activityAttendanceForm);
			setActivityTypeToRequest(request, activityAttendanceForm);
		} catch (Exception e) {
			log.error("Error while submitting activity attendance"+e.getMessage());
			String msg = super.handleApplicationException(e);
			activityAttendanceForm.setErrorMessage(msg);
			activityAttendanceForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		
		log.info("exit of submitActivityAttendence of ActivityAttendanceAction class.");
		return mapping.findForward(CMSConstants.INIT_ACTIVITY_ATTENDANCE);
	}

	private void validateDateRange(ApproveLeaveForm approveLeaveForm,	ActionMessages actionErrors) {
		log.info("entering into validateDate of ApproveLeaveAction class.");
		ActionMessage message=null;
		if (approveLeaveForm.getFromDate() != null
				&& approveLeaveForm.getFromDate().length() != 0
				&& approveLeaveForm.getToDate() != null
				&& approveLeaveForm.getToDate().length() != 0) {
			Date startDate = CommonUtil.ConvertStringToDate(approveLeaveForm
					.getFromDate());
			Date endDate = CommonUtil.ConvertStringToDate(approveLeaveForm
					.getToDate());
			if(!startDate.after(new Date())&& !endDate.after(new Date())){
			Calendar cal1 = Calendar.getInstance();
			cal1.setTime(startDate);
			Calendar cal2 = Calendar.getInstance();
			cal2.setTime(endDate);
			long daysBetween = CommonUtil.getDaysBetweenDates(cal1, cal2);
			if (daysBetween >15) {
				message=new ActionMessage("knowledgepro.attendance.activityattendence.dateRange");
				actionErrors.add(CMSConstants.MESSAGES, message);
			}}
		}
		log.info("exit of validateDate of ApproveLeaveAction class.");
	
		// TODO Auto-generated method stub
		
	}

	/**
	 * Sets all the classes for the current year in request scope
	 * 
	 * @param activityAttendanceForm
	 */

	public void setClassMapToRequest(HttpServletRequest request,
			ApproveLeaveForm activityAttendanceForm)
			throws ApplicationException {
		log.info("entering into setClassMapToRequest of ActivityAttendanceAction class.");
		try {
			Calendar calendar = Calendar.getInstance();
			int currentYear = calendar.get(Calendar.YEAR);
			// code by hari
			int year=CurrentAcademicYear.getInstance().getAcademicyear();
			if(year!=0){
				currentYear=year;
			}// end
			
			activityAttendanceForm.setYear(String.valueOf(currentYear));
			Map<Integer,String> classMap = CommonAjaxHandler.getInstance().getClassesByYear(
					currentYear);
			request.setAttribute("classMap", classMap);
			activityAttendanceForm.setClassMap(classMap);
			
			/*if(activityAttendanceForm.getClassSchemewiseId() != null  && !activityAttendanceForm.getClassSchemewiseId().equals("")) {
				Map<Integer,String> periodMap =	CommonAjaxHandler.getInstance().getPeriodByClassSchemewiseId(Integer.valueOf(activityAttendanceForm.getClassSchemewiseId()));
				activityAttendanceForm.setPeriodMap(periodMap);
			}*/
			
		} catch (Exception e) {
			log.error("error in setClassMapToRequest of ActivityAttendanceAction class.",e);
			throw new ApplicationException(e);
		}
		log.info("exit of setClassMapToRequest of ActivityAttendanceAction class.");
	}

	/**
	 * This method sets the attendanceType list to Request useful in populating
	 * in attendanceType selection.
	 * 
	 * @param request
	 * 
	 */
	public void setAttendanceTypeListToRequest(HttpServletRequest request,
			ApproveLeaveForm activityAttendanceForm) throws Exception {
		log.info("entering into setAttendanceTypeListToRequest of ActivityAttendanceAction class.");
		List<AttendanceTypeTO> attendanceTypeList = AttendanceTypeHandler
				.getInstance().getAttendanceType();
		activityAttendanceForm.setAttendanceTypeList(attendanceTypeList);
		log.info("exit of setAttendanceTypeListToRequest of ActivityAttendanceAction class.");
	}
	
	/**
	 * Method to forward to Modify Activity Attendance search UI.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initModifyActivity(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("entering into initModifyActivity of ActivityAttendanceAction class.");
		ApproveLeaveForm activityAttendanceForm = (ApproveLeaveForm) form;
		activityAttendanceForm.setLeaveList(null);
		activityAttendanceForm.resetFields();
		log.info("exit of initModifyActivity of ActivityAttendanceAction class.");
		return mapping.findForward(CMSConstants.INIT_MODIFY_ACTIVITY_ATTENDANCE);
	}
	
	/**
	 * This method lists all the activity attendance based on the selection criteria
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initModifyActivityAttendence(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("entering into initModifyActivityAttendence of ActivityAttendanceAction class.");
		ApproveLeaveForm approveLeaveForm = (ApproveLeaveForm) form;
		approveLeaveForm.setLeaveList(null);
		 ActionMessages errors = approveLeaveForm.validate(mapping, request);
		boolean dateError = false;
		if (approveLeaveForm.getFromDate() != null
				&& !StringUtils.isEmpty(approveLeaveForm.getFromDate())
				&& !CommonUtil.isValidDate(approveLeaveForm.getFromDate())) {
			errors.add(CMSConstants.ERRORS, new ActionError(
					CMSConstants.ATTENDANCE_ATTENDANCE_FROMDATREINVALID));
			dateError = true;
		}
		if (approveLeaveForm.getToDate() != null
				&& !StringUtils.isEmpty(approveLeaveForm.getToDate())
				&& !CommonUtil.isValidDate(approveLeaveForm.getToDate())) {
			errors.add(CMSConstants.ERRORS, new ActionError(
					CMSConstants.ATTENDANCE_ATTENDANCE_TODATEINVALID));
			dateError = true;
		}
		
		if (!dateError) {
			validateDate(approveLeaveForm, errors);
		}
		
	
			
			try {
				if (errors.isEmpty()) {
					approveLeaveForm.setDummyFromDate(approveLeaveForm.getFromDate());
					approveLeaveForm.setDummyToDate(approveLeaveForm.getToDate());
					ActivityAttendenceHandler.getInstance().getCoCcurricularLeaves(
							approveLeaveForm);
					if(approveLeaveForm.getLeaveList().isEmpty()) {
						errors.add(CMSConstants.ERROR, new ActionMessage(
								CMSConstants.KNOWLEDGEPRO_NORECORDS));
						saveErrors(request, errors);
					}
				} else {
					saveErrors(request, errors);
				}
			} catch (Exception e) {
				String msg = super.handleApplicationException(e);
				approveLeaveForm.setErrorMessage(msg);
				approveLeaveForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
	
		log.info("exit of initModifyActivityAttendence of ActivityAttendanceAction class.");
		return mapping.findForward(CMSConstants.INIT_MODIFY_ACTIVITY_ATTENDANCE);
	}
	
	/**
	 * Performs date validations.
	 * 
	 * @param approveLeaveForm
	 * @return actions messages if validation fails, empty action messages
	 *         otherwise
	 */
	public void validateDate(ApproveLeaveForm approveLeaveForm,
			ActionMessages actionErrors) {
		log.info("entering into validateDate of ApproveLeaveAction class.");
		ActionMessage message=null;
		if (approveLeaveForm.getFromDate() != null
				&& approveLeaveForm.getFromDate().length() != 0
				&& approveLeaveForm.getToDate() != null
				&& approveLeaveForm.getToDate().length() != 0) {
			Date startDate = CommonUtil.ConvertStringToDate(approveLeaveForm
					.getFromDate());
			Date endDate = CommonUtil.ConvertStringToDate(approveLeaveForm
					.getToDate());
			if(!startDate.after(new Date())&& !endDate.after(new Date())){
			Calendar cal1 = Calendar.getInstance();
			cal1.setTime(startDate);
			Calendar cal2 = Calendar.getInstance();
			cal2.setTime(endDate);
			long daysBetween = CommonUtil.getDaysBetweenDates(cal1, cal2);
			if (daysBetween <= 0) {
				actionErrors.add("error", new ActionMessage(
						CMSConstants.KNOWLEDGEPRO_STARTDATE_CONNOTBELESS));
			}}
			else{
				message=new ActionMessage("knowledgepro.attendance.activityattendence.dateError");
				actionErrors.add(CMSConstants.MESSAGES, message);
			}
		}
		log.info("exit of validateDate of ApproveLeaveAction class.");
	}
	
	/**
	 * Method to select the populate activity attendance by attendanceId to display in UI
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getActivityAttendanceById(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("entering into getActivityAttendanceById of ActivityAttendanceAction class.");
		ApproveLeaveForm activityAttendanceForm = (ApproveLeaveForm) form;
	
		ApproveLeaveForm approveLeaveForm = (ApproveLeaveForm) form;
		ActionMessages errors = new ActionErrors();
		try {
			if (approveLeaveForm.getId() != null) {
				ActivityAttendenceHandler.getInstance().editCoCurricularLeave(approveLeaveForm);
				setClassMapToRequest(request, activityAttendanceForm);
				setAttendanceTypeListToRequest(request, activityAttendanceForm);
				setActivityTypeToRequest(request, activityAttendanceForm);
				setPeriodsToForm(approveLeaveForm);
			}
		} catch (Exception e) {
			log.debug("Action :Entering deleteFeeAssignment with exception");
			errors.add(CMSConstants.ERROR, new ActionMessage(
					CMSConstants.LEAVE_DELETE_FAILURE));
			saveErrors(request, errors);
		}
		log.debug("leaving getActivityAttendanceById of ActivityAttendanceAction class.");
	
		return mapping.findForward(CMSConstants.EDIT_ACTIVITY_ATTENDANCE);
	}
	
	/**
	 * Reset activity attendance on edit
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward resetActivity(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("entering into getActivityAttendanceById of ActivityAttendanceAction class.");
		ApproveLeaveForm activityAttendanceForm = (ApproveLeaveForm) form;
		
		log.debug("Action :Entering editLeave");
		ApproveLeaveForm approveLeaveForm = (ApproveLeaveForm) form;
		ActionMessages errors = new ActionErrors();
		try {
			if (approveLeaveForm.getId() != null) {
				ActivityAttendenceHandler.getInstance().editCoCurricularLeave(approveLeaveForm);
				setClassMapToRequest(request, activityAttendanceForm);
				setAttendanceTypeListToRequest(request, activityAttendanceForm);
				setActivityTypeToRequest(request, activityAttendanceForm);
			}
		} catch (Exception e) {
			log.debug("Action :Entering deleteFeeAssignment with exception");
			errors.add(CMSConstants.ERROR, new ActionMessage(
					CMSConstants.LEAVE_DELETE_FAILURE));
			saveErrors(request, errors);
		}
		log.debug("Action :leaving editLeave with success");
	
		return mapping.findForward(CMSConstants.EDIT_ACTIVITY_ATTENDANCE);
	}
	

	
	/**
	 * Method to set all active activity type wrt attendance type to the request object
	 * @param request
	 * @param activityAttendanceForm
	 */
	public void setActivityTypeToRequest(HttpServletRequest request,
			ApproveLeaveForm activityAttendanceForm) {
		log.info("entering into setActivityTypeToRequest of ActivityAttendanceAction class.");
		if (activityAttendanceForm.getAttendanceTypeId() != null && (!activityAttendanceForm.getAttendanceTypeId().equals("") )) {
			Set<Integer> attendanceTypeSet = new HashSet<Integer>();
			
			attendanceTypeSet.add(Integer.parseInt(activityAttendanceForm.getAttendanceTypeId()));
			
			Map<Integer,String> activityMap = CommonAjaxHandler.getInstance().getActivityByAttendenceType(attendanceTypeSet);
			HttpSession session = request.getSession(false);
			session.setAttribute("activityMap", activityMap);
		}
		log.info("exit of setActivityTypeToRequest of ActivityAttendanceAction class.");
	}
	
	/**
	 * Method to update the modify activity attendance
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward submitModifyActivityAttendence(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("entering into submitModifyActivityAttendence of ActivityAttendanceAction class.");
		ActionMessages messages = new ActionMessages();
		ActionMessage message = null;

		ApproveLeaveForm approveLeaveForm = (ApproveLeaveForm) form;
		ActionMessages errors = approveLeaveForm.validate(mapping, request);
		if (approveLeaveForm.getFromDate() != null
				&& !StringUtils.isEmpty(approveLeaveForm.getFromDate())
				&& !CommonUtil.isValidDate(approveLeaveForm.getFromDate())) {
			errors.add(CMSConstants.ERRORS, new ActionError(
					CMSConstants.ATTENDANCE_ATTENDANCE_FROMDATREINVALID));
		}
		if (approveLeaveForm.getToDate() != null
				&& !StringUtils.isEmpty(approveLeaveForm.getToDate())
				&& !CommonUtil.isValidDate(approveLeaveForm.getToDate())) {
			errors.add(CMSConstants.ERRORS, new ActionError(
					CMSConstants.ATTENDANCE_ATTENDANCE_TODATEINVALID));
		}
		if(approveLeaveForm.getCheckyesNo()==null || approveLeaveForm.getCheckyesNo().isEmpty()){
			validateDateRange(approveLeaveForm,messages);
			}else if(approveLeaveForm.getCheckyesNo().equalsIgnoreCase("No")){
				approveLeaveForm.setCheckyesNo(null);
				setClassMapToRequest(request, approveLeaveForm);
				log.info("exit of submitActivityAttendence of ActivityAttendanceAction class.");
				return mapping.findForward(CMSConstants.EDIT_ACTIVITY_ATTENDANCE);
			}
		 approveLeaveForm.setCheckyesNo(null);
		try {
			if (errors.isEmpty() && messages.isEmpty()) {
				Calendar calendar = Calendar.getInstance();
				
				
				int currentYear = calendar.get(Calendar.YEAR);
				// code by hari
				int year=CurrentAcademicYear.getInstance().getAcademicyear();
				if(year!=0){
					currentYear=year;
				}// end
/*
 * 
 *  
 *  */
				approveLeaveForm.setYear(String.valueOf(currentYear));

				approveLeaveForm.setRegisterNo(ApproveLeaveHandler
						.getInstance().getIsRegisterNo(approveLeaveForm));

				String inputStr = approveLeaveForm.getRegisterNoEntry();
				String patternStr = ",";
				String[] registerNoString = inputStr.split(patternStr);
				ArrayList<String> registerNoList = new ArrayList<String>();
				Set<String> registerNoSet = new HashSet<String>();
				for (String registerNo : registerNoString) {
					if (registerNo.trim().length() > 0 && registerNoSet.add(registerNo.trim())) {
						registerNoList.add(registerNo);
						
					}
				}
				approveLeaveForm.setRegisterNoSet(registerNoSet);

				ActionMessage errorMessage = ActivityAttendenceHandler.getInstance()
						.validateModifyAttendence(approveLeaveForm,
								registerNoList);

				if (errorMessage == null) {
					approveLeaveForm.setRegisterNoList(registerNoList);

					
					Map<Integer, List<ApproveLeaveTO>> approveLeaveToMap = ActivityAttendenceHandler
					.getInstance().constructCoCcurricularLeave(
							approveLeaveForm, "update");
					ActivityAttendenceHandler.getInstance().updateCoCcurricularLeave(
							approveLeaveToMap, approveLeaveForm, "update");
					approveLeaveForm.resetFields();
					approveLeaveForm.setFromDate(approveLeaveForm.getDummyFromDate());
					approveLeaveForm.setToDate(approveLeaveForm.getDummyToDate());
					ActivityAttendenceHandler.getInstance().getCoCcurricularLeaves(
							approveLeaveForm);
					message = new ActionMessage(
							CMSConstants.ATTENDANCE_LEAVE_MODIFYSUCCESS);
					messages.add(CMSConstants.MESSAGES, message);
					addMessages(request, messages);
				} else {
					setClassMapToRequest(request, approveLeaveForm);
					messages.add(CMSConstants.MESSAGES, errorMessage);
					addErrors(request, messages);
					return mapping.findForward(CMSConstants.EDIT_ACTIVITY_ATTENDANCE);
				}
			} else {
				setClassMapToRequest(request, approveLeaveForm);
				addErrors(request, errors);
				addMessages(request, messages);
				return mapping.findForward(CMSConstants.EDIT_ACTIVITY_ATTENDANCE);
			}

		
		} catch (Exception e) {
			errors.add(CMSConstants.ERROR, new ActionMessage(
					CMSConstants.ATTENDANCE_LEAVE_MODIFYFAILURE));
			saveErrors(request, errors);
		}
		log.info("exit of submitModifyActivityAttendence of ActivityAttendanceAction class.");
		return mapping.findForward(CMSConstants.INIT_MODIFY_ACTIVITY_ATTENDANCE);
//		return mapping.findForward(CMSConstants.EDIT_ACTIVITY_ATTENDANCE);
	}
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 *            This action method is to delete single leave approval.
	 * @return returns error messages based on success / failure.
	 * @throws Exception
	 */
	public ActionForward deleteCoCcurricularLeave(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		log.debug("Action :Entering deleteLeave");
		ApproveLeaveForm approveLeaveForm = (ApproveLeaveForm) form;
		ActionMessages errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		try {
			if (approveLeaveForm.getId() != null) {
				ActivityAttendenceHandler.getInstance().editCoCurricularLeave(
						approveLeaveForm);
				Map<Integer, List<ApproveLeaveTO>> approveLeaveToMap = ActivityAttendenceHandler
						.getInstance().constructCoCcurricularLeaveForDelete(
								approveLeaveForm);

				ActivityAttendenceHandler.getInstance()
						.deleteCoCcurricularLeave(approveLeaveForm,
								approveLeaveToMap);

				ActivityAttendenceHandler.getInstance().getCoCcurricularLeaves(
						approveLeaveForm);
				ActionMessage message = new ActionMessage(
						CMSConstants.LEAVE_DELETE_SUCCESS);
				messages.add(CMSConstants.MESSAGES, message);
				saveMessages(request, messages);
			}
		} catch (Exception e) {
			// failure error message.
			log.debug("Action :Entering deleteFeeAssignment with exception");
			errors.add(CMSConstants.ERROR, new ActionMessage(
					CMSConstants.LEAVE_DELETE_FAILURE));
			saveErrors(request, errors);
		}
		log.debug("Action :leaving+ deleteLeave with success");
		return mapping
				.findForward(CMSConstants.INIT_MODIFY_ACTIVITY_ATTENDANCE);
	}
	
	
	//code return by balaji
	//this below methods for view co curricular leave
	
	/**
	 * Method to forward to Modify Activity Attendance search UI.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initViewActivity(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("entering into initModifyActivity of ActivityAttendanceAction class.");
		ApproveLeaveForm activityAttendanceForm = (ApproveLeaveForm) form;
		activityAttendanceForm.setLeaveList(null);
		activityAttendanceForm.resetFields();
		log.info("exit of initModifyActivity of ActivityAttendanceAction class.");
		return mapping.findForward(CMSConstants.INIT_VIEW_ACTIVITY_ATTENDANCE);
	}
	
	/**
	 * This method lists all the activity attendance based on the selection criteria
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initViewActivityAttendence(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("entering into initModifyActivityAttendence of ActivityAttendanceAction class.");
		ApproveLeaveForm approveLeaveForm = (ApproveLeaveForm) form;
		approveLeaveForm.setLeaveList(null);
		 ActionMessages errors = approveLeaveForm.validate(mapping, request);
		boolean dateError = false;
		if (approveLeaveForm.getFromDate() != null
				&& !StringUtils.isEmpty(approveLeaveForm.getFromDate())
				&& !CommonUtil.isValidDate(approveLeaveForm.getFromDate())) {
			errors.add(CMSConstants.ERRORS, new ActionError(
					CMSConstants.ATTENDANCE_ATTENDANCE_FROMDATREINVALID));
			dateError = true;
		}
		if (approveLeaveForm.getToDate() != null
				&& !StringUtils.isEmpty(approveLeaveForm.getToDate())
				&& !CommonUtil.isValidDate(approveLeaveForm.getToDate())) {
			errors.add(CMSConstants.ERRORS, new ActionError(
					CMSConstants.ATTENDANCE_ATTENDANCE_TODATEINVALID));
			dateError = true;
		}
		
		if (!dateError) {
			validateDate(approveLeaveForm, errors);
		}
			try {
				if (errors.isEmpty()) {
					approveLeaveForm.setDummyFromDate(approveLeaveForm.getFromDate());
					approveLeaveForm.setDummyToDate(approveLeaveForm.getToDate());
					ActivityAttendenceHandler.getInstance().getCoCcurricularLeaves(
							approveLeaveForm);
					if(approveLeaveForm.getLeaveList().isEmpty()) {
						errors.add(CMSConstants.ERROR, new ActionMessage(
								CMSConstants.KNOWLEDGEPRO_NORECORDS));
						saveErrors(request, errors);
					}
				} else {
					saveErrors(request, errors);
				}
			} catch (Exception e) {
				String msg = super.handleApplicationException(e);
				approveLeaveForm.setErrorMessage(msg);
				approveLeaveForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
	
		log.info("exit of initModifyActivityAttendence of ActivityAttendanceAction class.");
		return mapping.findForward(CMSConstants.INIT_VIEW_ACTIVITY_ATTENDANCE);
	}
	
	
	/**
	 * Method to select the populate activity attendance by attendanceId to display in UI
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getActivityAttendanceByIdForView(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("entering into getActivityAttendanceById of ActivityAttendanceAction class.");
		ApproveLeaveForm activityAttendanceForm = (ApproveLeaveForm) form;
	
		ApproveLeaveForm approveLeaveForm = (ApproveLeaveForm) form;
		ActionMessages errors = new ActionErrors();
		try {
			if (approveLeaveForm.getId() != null) {
				ActivityAttendenceHandler.getInstance().editCoCurricularLeave(approveLeaveForm);
				setClassMapToRequest(request, activityAttendanceForm);
				setAttendanceTypeListToRequest(request, activityAttendanceForm);
				setActivityTypeToRequest(request, activityAttendanceForm);
			}
		} catch (Exception e) {
			log.debug("Action :Entering deleteFeeAssignment with exception");
			errors.add(CMSConstants.ERROR, new ActionMessage(
					CMSConstants.LEAVE_DELETE_FAILURE));
			saveErrors(request, errors);
		}
		log.debug("leaving getActivityAttendanceById of ActivityAttendanceAction class.");
	
		return mapping.findForward(CMSConstants.VIEW_ACTIVITY_ATTENDANCE);
	}
	
	public ActionForward getPeriodsByClassSchemewisevalues(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ApproveLeaveForm activityAttendanceForm = (ApproveLeaveForm) form;
		Map<Integer, String> periodMap = new HashMap<Integer, String>();
		try {
					
			String allClasses[] = activityAttendanceForm.getSelectedClassesArray1().split(",");
			

			Set<Integer> classesIdsSet = new HashSet<Integer>();
			if(allClasses.length > 0){
			for (int i = 0; i < allClasses.length; i++) {
				classesIdsSet.add(Integer.parseInt(allClasses[i]));
			}
			
			periodMap = CommonAjaxHandler.getInstance().getPeriodByClassSchemewiseValues(classesIdsSet);
			}

		} catch (Exception e) {
			log.debug(e.getMessage());
		}
		request.setAttribute("periodOptionMap", periodMap);
		//added by mahi
		activityAttendanceForm.setPeriodMap(periodMap);
		//end
		return mapping.findForward("ajaxResponseForPeriodMap");
	}
	
	
	public void validationForPeriods(ApproveLeaveForm approveLeaveForm,ActionErrors actionErrors) {
			
		log.info("entering into validatePeriods of ApproveLeaveAction class.");
		if(approveLeaveForm.getFullDay()!=null && approveLeaveForm.getFullDay().equalsIgnoreCase("No")){
			if((approveLeaveForm.getFromPeriod()==null || approveLeaveForm.getFromPeriod().isEmpty())
					|| (approveLeaveForm.getToPeriod()==null || approveLeaveForm.getToPeriod().isEmpty())){
				if(approveLeaveForm.getFromPeriod()==null || approveLeaveForm.getFromPeriod().isEmpty()){
					actionErrors.add("error", new ActionMessage("knowledgepro.from.period.required"));
				}
				if(approveLeaveForm.getToPeriod()==null || approveLeaveForm.getToPeriod().isEmpty()){
					actionErrors.add("error", new ActionMessage("knowledgepro.to.period.required"));
				}
			}
		log.info("exit of validateDate of ApproveLeaveAction class.");
		}
	}
	
	@SuppressWarnings("unchecked")
	public void mapSelectedClassByStyleClass(ApproveLeaveForm objform) throws Exception{
		Map<Integer,String> MapSelectedClass=new HashMap<Integer, String>();
		Map<Integer,String> map=objform.getClassMap();
		
		String a = objform.getClassValues();
		String[] str = a.split(",");
		for (int x = 0; x < str.length; x++) {
			if (str[x] != null && str[x].length() > 0) {
				Integer classId=Integer.parseInt(str[x]);
				if(map.containsKey(classId)){
					MapSelectedClass.put(classId, map.get(classId));
					map.remove(classId);
				}
				
			}
		}
		objform.setMapSelectedClass(CommonUtil.sortMapByValue(MapSelectedClass));
		objform.setClassMap(map);
	}
	
	public void setPeriodsToForm(ApproveLeaveForm objform){
		String a = objform.getClassValues();
		Set<Integer> classesIdsSet = new HashSet<Integer>();
		Map<Integer, String> periodMap = new HashMap<Integer, String>();
		String[] str = a.split(",");
		if(str!=null && str.length !=0){
		for (int x = 0; x < str.length; x++) {
			if (str[x] != null && str[x].length() > 0) {
				Integer classId=Integer.parseInt(str[x]);
				classesIdsSet.add(classId);
			}
		}
		periodMap = CommonAjaxHandler.getInstance().getPeriodByClassSchemewiseValues(classesIdsSet);
		objform.setPeriodMap(periodMap);
		objform.setFromPeriod(objform.getFromPeriod());
		objform.setToPeriod(objform.getToPeriod());
		
		}
		
		
	}
	
	public ActionForward getClassesBySemType(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ApproveLeaveForm activityAttendanceForm = (ApproveLeaveForm) form;
		Map<Integer, String> classMap = new HashMap<Integer, String>();
		try {
			classMap = ActivityAttendenceHandler.getInstance().getClassesBySemisterType(activityAttendanceForm);
		} catch (Exception e) {
			log.debug(e.getMessage());
		}
		request.setAttribute("optionMap", classMap);
		return mapping.findForward("ajaxResponseForOptions");
	}
			
}