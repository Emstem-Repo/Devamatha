package com.kp.cms.actions.attendance;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import com.kp.cms.bo.admin.ClassSchemewise;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.attendance.MonthlyAttendanceEntryForm;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.attendance.MonthlyAttendanceEntryHandler;
import com.kp.cms.handlers.usermanagement.UserInfoHandler;
import com.kp.cms.to.attendance.MonthlyAttendanceDaysTO;
import com.kp.cms.to.attendance.MonthlyAttendanceTO;
import com.kp.cms.utilities.CurrentAcademicYear;

/**
 * 
 * A dispatch action to handle actions related to monthly attendance.
 * 
 */
public class MonthlyAttendanceEntryAction extends BaseDispatchAction {
	
	private static final Log log = LogFactory.getLog(MonthlyAttendanceEntryAction.class);

	/**
	 * Initializes monthly attendance entry form.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initMonthlyAttendanceEntry(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("entering into initMonthlyAttendanceEntry of MonthlyAttendanceEntryAction class.");
		String actionName = (String) request.getParameter("operation");
		MonthlyAttendanceEntryForm monthlyAttendanceEntryForm = (MonthlyAttendanceEntryForm) form;
		try {
			if (actionName == null) {
				monthlyAttendanceEntryForm.setOperationMode("");
			} else {
				monthlyAttendanceEntryForm.setOperationMode(actionName);
			}
			monthlyAttendanceEntryForm.resetEntries();
			initsetDataToForm(monthlyAttendanceEntryForm);
		} catch (Exception e) {
			log.error("Error while initializing monthly attendance"+e.getMessage());
			String msg = super.handleApplicationException(e);
			monthlyAttendanceEntryForm.setErrorMessage(msg);
			monthlyAttendanceEntryForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("exit of initMonthlyAttendanceEntry of MonthlyAttendanceEntryAction class.");
		return mapping.findForward(CMSConstants.INIT_MONTHLY_ATTENDANCE);

	}

	/**
	 * Performs search action and get the students for monthly attendance.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getMonthlyAttendanceList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("entering into getMonthlyAttendanceList of MonthlyAttendanceEntryAction class.");
		MonthlyAttendanceEntryForm monthlyAttendanceEntryForm = (MonthlyAttendanceEntryForm) form;
		try {
			ActionMessages messages = new ActionMessages();
			ActionErrors errors = monthlyAttendanceEntryForm.validate(mapping, request);
			if (errors.isEmpty()) {

				monthlyAttendanceEntryForm
						.setRegNoDisplay(MonthlyAttendanceEntryHandler
								.getInstance().getIsRegisterNo(
										monthlyAttendanceEntryForm));
				Set<Integer> classSet = new HashSet<Integer>();

				for (String str : monthlyAttendanceEntryForm.getClasses()) {
					classSet.add(Integer.parseInt(str));

				}

				Set<Integer> teacherSet = new HashSet<Integer>();
				if(monthlyAttendanceEntryForm.getTeachers() != null) {
					for (String str : monthlyAttendanceEntryForm.getTeachers()) {
						teacherSet.add(Integer.parseInt(str));
					}
				}
				
				monthlyAttendanceEntryForm.setSelectedTeacher(teacherSet);
				monthlyAttendanceEntryForm.setSelectedClasses(classSet);
				Calendar calander = null;

				calander = Calendar.getInstance();
				calander.set(Integer.valueOf(monthlyAttendanceEntryForm
						.getYear1()), Integer
						.valueOf(monthlyAttendanceEntryForm.getMonth()), 1);

				int days = calander.getActualMaximum(Calendar.DAY_OF_MONTH);

				List<Integer> noOfDaysList = new LinkedList<Integer>();
				
				List<MonthlyAttendanceDaysTO> daysList  = new ArrayList<MonthlyAttendanceDaysTO>();
				
				for (int index = 0; index < days; index++) {
					MonthlyAttendanceDaysTO monthlyAttendanceTO = new MonthlyAttendanceDaysTO();
					monthlyAttendanceTO.setDayId(index);
					daysList.add(monthlyAttendanceTO);
					
					noOfDaysList.add(index);
				}

				monthlyAttendanceEntryForm.setNoOfDaysList(noOfDaysList);
				monthlyAttendanceEntryForm.setDaysList(daysList);
			
				monthlyAttendanceEntryForm.setNoOfDaysInMonth(days);
		       List attendanceEntryByDate = MonthlyAttendanceEntryHandler
						.getInstance().getAttendanceByDate(
								monthlyAttendanceEntryForm);
				if (attendanceEntryByDate == null
						|| (attendanceEntryByDate != null && attendanceEntryByDate.isEmpty())) {
					if (monthlyAttendanceEntryForm.getOperationMode()
							.equalsIgnoreCase("editAttendance")
							|| monthlyAttendanceEntryForm.getOperationMode()
									.equalsIgnoreCase("cancelAttendance")) {
						ActionMessage errorMessage = new ActionMessage(CMSConstants.ATTENDANCE_ENTRY_NORECORD );
						messages.add("error", errorMessage);
						
						addErrors(request, messages);
						initSetDataOnError(monthlyAttendanceEntryForm);
						return mapping
						.findForward(CMSConstants.INIT_MONTHLY_ATTENDANCE);
						
					}
				}
			
				List<MonthlyAttendanceTO> monthlyAttendanceTOList = MonthlyAttendanceEntryHandler
						.getInstance().constructMonthlyAttendanceTO(
								monthlyAttendanceEntryForm, attendanceEntryByDate,daysList);
				
				if(monthlyAttendanceTOList == null
						|| (monthlyAttendanceTOList != null && monthlyAttendanceTOList.isEmpty())) {
					ActionMessage errorMessage = new ActionMessage(CMSConstants.ATTENDANCE_ENTRY_NORECORD );
					messages.add("error", errorMessage);
					
					addErrors(request, messages);
					initSetDataOnError(monthlyAttendanceEntryForm);
					return mapping
					.findForward(CMSConstants.INIT_MONTHLY_ATTENDANCE);
				}

				monthlyAttendanceEntryForm
						.setMonthlyAttendanceTOList(monthlyAttendanceTOList);
				log.info("exit of getMonthlyAttendanceList of MonthlyAttendanceEntryAction class.");
				if (monthlyAttendanceEntryForm.getOperationMode()
						.equalsIgnoreCase("editAttendance")) {
					return mapping
							.findForward(CMSConstants.UPDATE_MONTHLY_ATTENDANCE);
				} else if (monthlyAttendanceEntryForm.getOperationMode()
						.equalsIgnoreCase("cancelAttendance")) {
					return mapping
							.findForward(CMSConstants.CANCEL_MONTHLY_ATTENDANCE);
				} else {
					return mapping
							.findForward(CMSConstants.GET_MONTHLY_ATTENDANCE);
				}

			} else {
				addErrors(request, errors);
				initSetDataOnError(monthlyAttendanceEntryForm);
				return mapping
						.findForward(CMSConstants.INIT_MONTHLY_ATTENDANCE);
			}

		} catch (Exception e) {
			log.error("Error while getting monthly attendance"+e.getMessage());
			String msg = super.handleApplicationException(e);
			monthlyAttendanceEntryForm.setErrorMessage(msg);
			monthlyAttendanceEntryForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}

	}

	/**
	 * Persists the monthly attendance.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward persistMonthlyAttendanceList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("entering into persistMonthlyAttendanceList of MonthlyAttendanceEntryAction class.");
		ActionMessages messages = new ActionMessages();
		ActionMessage message = null;
		MonthlyAttendanceEntryForm monthlyAttendanceEntryForm = (MonthlyAttendanceEntryForm) form;
		try {
			setUserId(request, monthlyAttendanceEntryForm);
			MonthlyAttendanceEntryHandler.getInstance()
					.persistMonthlyAttendance(monthlyAttendanceEntryForm);
			monthlyAttendanceEntryForm.resetEntries();
			initsetDataToForm(monthlyAttendanceEntryForm);
			message = new ActionMessage(
					"knowledgepro.attendanceentry.monthlyattendance.addsuccess");
			messages.add("messages", message);
			addMessages(request, messages);
			log.info("exit of persistMonthlyAttendanceList of MonthlyAttendanceEntryAction class.");
			return mapping.findForward(CMSConstants.INIT_MONTHLY_ATTENDANCE);
		} catch (Exception e) {
			log.error("Error while persisting monthly attendance"+e.getMessage());
			String msg = super.handleApplicationException(e);
			monthlyAttendanceEntryForm.setErrorMessage(msg);
			monthlyAttendanceEntryForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
	}
	
	/**
	 * Updates monthly attendance.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateMonthlyAttendanceList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("entering into updateMonthlyAttendanceList of MonthlyAttendanceEntryAction class.");
		ActionMessages messages = new ActionMessages();
		ActionMessage message = null;
		MonthlyAttendanceEntryForm monthlyAttendanceEntryForm = (MonthlyAttendanceEntryForm) form;
		try {
			setUserId(request, monthlyAttendanceEntryForm);
			MonthlyAttendanceEntryHandler.getInstance().persistMonthlyAttendance(
					monthlyAttendanceEntryForm);
			monthlyAttendanceEntryForm.resetEntries();
			initsetDataToForm(monthlyAttendanceEntryForm);
			message = new ActionMessage(
					"knowledgepro.attendanceentry.monthlyattendance.updatesuccess");
			messages.add("messages", message);
			addMessages(request, messages);
			request.setAttribute("operation", "editAttendance");
			log.info("exit of updateMonthlyAttendanceList of MonthlyAttendanceEntryAction class.");
			return mapping.findForward(CMSConstants.INIT_MONTHLY_ATTENDANCE);
		} catch (Exception e) {
			log.error("Error while updating monthly attendance"+e.getMessage());
			String msg = super.handleApplicationException(e);
			monthlyAttendanceEntryForm.setErrorMessage(msg);
			monthlyAttendanceEntryForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
	
	}
	
	/**
	 * Cancels monthly attendance.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward cancelMonthlyAttendanceList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("entering into cancelMonthlyAttendanceList of MonthlyAttendanceEntryAction class.");
		ActionMessages messages = new ActionMessages();
		ActionMessage message = null;
		MonthlyAttendanceEntryForm monthlyAttendanceEntryForm = (MonthlyAttendanceEntryForm) form;
		
		setUserId(request, monthlyAttendanceEntryForm);
		MonthlyAttendanceEntryHandler.getInstance().cancelMonthlyAttendance(
				monthlyAttendanceEntryForm);
		monthlyAttendanceEntryForm.resetEntries();
		initsetDataToForm(monthlyAttendanceEntryForm);
		message = new ActionMessage(
				"knowledgepro.attendanceentry.monthlyattendance.cancelsuccess");
		messages.add("messages", message);
		addMessages(request, messages);
		request.setAttribute("operation", "cancelAttendance");
		log.info("exit of cancelMonthlyAttendanceList of MonthlyAttendanceEntryAction class.");
		return mapping.findForward(CMSConstants.INIT_MONTHLY_ATTENDANCE);
	}
	

	/**
	 * set the request data to form.
	 * @param monthlyAttendanceEntryForm
	 * @throws Exception
	 */
	private void initsetDataToForm(
			MonthlyAttendanceEntryForm monthlyAttendanceEntryForm)
			throws Exception {
		log.info("entering into initsetDataToForm of MonthlyAttendanceEntryAction class.");
		// This will load the all attendance type in application.
		MonthlyAttendanceEntryHandler attendanceTypeHandler = MonthlyAttendanceEntryHandler
				.getInstance();
		attendanceTypeHandler
				.getAttendanceTypeMandatory(monthlyAttendanceEntryForm,null);

		if (monthlyAttendanceEntryForm.getAttendanceTypeId() != null
				&& monthlyAttendanceEntryForm.getAttendanceTypeId().length() != 0) {
			Set<Integer> set = new HashSet<Integer>();
			set.add(Integer.valueOf(monthlyAttendanceEntryForm
					.getAttendanceTypeId()));

			// This will add activities in to map they belong to particular
			// attendancetypeid.
			Map<Integer, String> activityMap = CommonAjaxHandler.getInstance()
					.getActivityByAttendenceType(set);
			monthlyAttendanceEntryForm.setActivityMap(activityMap);
		}
		Map<Integer, String> teachersMap = UserInfoHandler.getInstance()
				.getTeachingStaff();
		monthlyAttendanceEntryForm.setTeachersMap(teachersMap);

		// Getting the class map current academic year.
		Map<Integer, String> classMap = setpClassMapToRequest();
		monthlyAttendanceEntryForm.setClassMap(classMap);

		Calendar calendar = Calendar.getInstance();
		int currentYear = calendar.get(Calendar.YEAR);
		// code by hari
		int year=CurrentAcademicYear.getInstance().getAcademicyear();
		if(year!=0){
			currentYear=year;
		}// end

		monthlyAttendanceEntryForm.setAcademicYear(String.valueOf(currentYear));
		log.info("exit of initsetDataToForm of MonthlyAttendanceEntryAction class.");
	}
	
	/**
	 *  This method will load the data on error mode.
	 */
	
	public void initSetDataOnError(MonthlyAttendanceEntryForm attendanceEntryForm) throws Exception {
		log.info("entering into initSetDataOnError of MonthlyAttendanceEntryAction class.");
		Map<Integer,String> subjectMap = new LinkedHashMap<Integer,String>();
		Map<Integer,String> batchMap = new HashMap<Integer,String>();
		if(attendanceEntryForm.getAttendanceTypeId()!=null && attendanceEntryForm.getAttendanceTypeId().length() !=0) {
			Set<Integer> set = new HashSet<Integer>();
			set.add(Integer.valueOf(attendanceEntryForm.getAttendanceTypeId()));
			
			// This will add activities in to map they belong to particular attendancetypeid.
			Map<Integer,String> activityMap = CommonAjaxHandler.getInstance().getActivityByAttendenceType(set);
			attendanceEntryForm.setActivityMap(activityMap);
		}
		
		if(attendanceEntryForm.getClasses()!= null && attendanceEntryForm.getClasses().length != 0) {
			Set<Integer> classesIdsSet = new HashSet<Integer>(); 
			for(String str :attendanceEntryForm.getClasses()) {
				classesIdsSet.add(Integer.parseInt(str));
			}
		
			List<ClassSchemewise> classSchemewiseList = CommonAjaxHandler.getInstance().getDetailsonClassSchemewiseId(classesIdsSet);
			Iterator<ClassSchemewise> itr = classSchemewiseList.iterator();
			ClassSchemewise classSchemewise;
			while(itr.hasNext()) {
				classSchemewise = itr.next();
				if(classSchemewise.getCurriculumSchemeDuration().getAcademicYear()!=null && classSchemewise.getClasses().getCourse().getId()!= 0 && classSchemewise.getClasses().getTermNumber()!=0){
					int year=classSchemewise.getCurriculumSchemeDuration().getAcademicYear();
					int courseId=classSchemewise.getClasses().getCourse().getId();
					int term=classSchemewise.getClasses().getTermNumber();
					Map<Integer,String> tempMap = CommonAjaxHandler.getInstance().getSubjectByCourseIdTermYear(courseId,year,term);
					subjectMap.putAll(tempMap);
				}
			}
			if (attendanceEntryForm.getSubjectId() != null
					&& attendanceEntryForm.getSubjectId().length() != 0) {
				batchMap = CommonAjaxHandler.getInstance()
						.getBatchesBySubjectAndClassIds(
								Integer.parseInt(attendanceEntryForm
										.getSubjectId()), classesIdsSet);

			}
				
			attendanceEntryForm.setBatchMap(batchMap);
			attendanceEntryForm.setSubjectMap(subjectMap);
		} else {
			attendanceEntryForm.setSubjectMap(subjectMap);
			attendanceEntryForm.setBatchMap(batchMap);
		}
		//setpClassMapToRequest(attendanceEntryForm);
		log.info("exit of initSetDataOnError of MonthlyAttendanceEntryAction class.");
	}

	/**
	 * Sets all the classes for the current year in request scope
	 */
	private Map<Integer, String> setpClassMapToRequest() throws ApplicationException {
		log.info("entering into setpClassMapToRequest of MonthlyAttendanceEntryAction class.");
		try {
			Calendar calendar = Calendar.getInstance();
			int currentYear = calendar.get(Calendar.YEAR);
			// code by hari
			int year=CurrentAcademicYear.getInstance().getAcademicyear();
			if(year!=0){
				currentYear=year;
			}// end

			Map<Integer, String> classMap = CommonAjaxHandler.getInstance()
					.getClassesByYear(currentYear);
			log.info("exit of setpClassMapToRequest of MonthlyAttendanceEntryAction class.");
			return classMap;
		} catch (Exception e) {
			log.info("error in setpClassMapToRequest of MonthlyAttendanceEntryAction class.",e);
			throw new ApplicationException(e);
		}
	}
}