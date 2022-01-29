package com.kp.cms.actions.reports;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
import org.apache.struts.action.ActionMessages;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.reports.StudentsAttendanceReportForm;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.attendance.AttendanceTypeHandler;
import com.kp.cms.handlers.reports.StudentsAttendanceReportHandler;
import com.kp.cms.to.attendance.AttendanceTypeTO;
import com.kp.cms.to.reports.StudentsAttendanceReportTO;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.CurrentAcademicYear;

@SuppressWarnings("deprecation")
public class StudentsAttendanceReportAction extends BaseDispatchAction {

	private static final Log log = LogFactory.getLog(StudentsAttendanceReportAction.class);
	private static final String STUDENT_ATTENDANCE_REPORT = "studentAttendanceReport";
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initStudentAttendanceReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		StudentsAttendanceReportForm studentsAttendanceReportForm = (StudentsAttendanceReportForm) form;
		
		try{
			log.info("Entered initStudentAttendanceReport");
			studentsAttendanceReportForm.resetFields();
			studentsAttendanceReportForm.setIsMonthlyAttendance(CMSConstants.KNOWLEDGEPRO_FALSE);
			setRequiredDataToForm(studentsAttendanceReportForm);
			HttpSession session = request.getSession(false);
			session.removeAttribute(STUDENT_ATTENDANCE_REPORT);
			log.info("Exit initStudentAttendanceReport");
		} catch (Exception exception) {	
			String msg = super.handleApplicationException(exception);
			studentsAttendanceReportForm.setErrorMessage(msg);
			studentsAttendanceReportForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.STUDENTS_ATTENDANCE_REPORT);
	}

	/**
	 * Method is to get the candidates based on the search criteria
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward submitStudentAttendanceReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("Entered submitStudentAttendanceReport");
		StudentsAttendanceReportForm studentsAttendanceReportForm = (StudentsAttendanceReportForm) form;
		studentsAttendanceReportForm.setDownloadExcel(null);
		studentsAttendanceReportForm.setMode(null);
		HttpSession session = request.getSession(false);
		if(session.getAttribute(STUDENT_ATTENDANCE_REPORT)==null){
			studentsAttendanceReportForm.setMode(null);
			studentsAttendanceReportForm.setDownloadExcel(null);
			 ActionMessages errors = studentsAttendanceReportForm.validate(mapping, request);
			validateAttendanceDate(studentsAttendanceReportForm, errors);
			if (errors.isEmpty()) {
				try {
						Map<String, List<StudentsAttendanceReportTO>> studentsAttendanceResultMap = StudentsAttendanceReportHandler.getInstance().getStudentsAttendanceResults(studentsAttendanceReportForm);
						if(studentsAttendanceResultMap.isEmpty()){
							errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND));
							saveErrors(request, errors);
							studentsAttendanceReportForm.resetFields();
							studentsAttendanceReportForm.setIsMonthlyAttendance(CMSConstants.KNOWLEDGEPRO_FALSE);
							setRequiredDataToForm(studentsAttendanceReportForm);
							return mapping.findForward(CMSConstants.STUDENTS_ATTENDANCE_REPORT);
						}
						if (studentsAttendanceResultMap != null) {
							List<StudentsAttendanceReportTO> finalList = new ArrayList<StudentsAttendanceReportTO>();
							Iterator<String> itr = studentsAttendanceResultMap.keySet().iterator();
							while (itr.hasNext()) {
								String className = (String) itr.next();
								Iterator<StudentsAttendanceReportTO> it = studentsAttendanceResultMap.get(className).iterator();
								while (it.hasNext()) {
									StudentsAttendanceReportTO studentAttendance = (StudentsAttendanceReportTO) it.next();
									
									StudentsAttendanceReportTO studentsAttendanceTO = new StudentsAttendanceReportTO();
									
									studentsAttendanceTO.setClassName(className);
									studentsAttendanceTO.setRegisterNumber(studentAttendance.getRegisterNumber());
									studentsAttendanceTO.setStudentName(studentAttendance.getStudentName());
									studentsAttendanceTO.setClassesHeld(studentAttendance.getClassesHeld());
									studentsAttendanceTO.setClassesAttended(studentAttendance.getClassesAttended());
									if(studentAttendance.getPercentage().endsWith("*")){
									studentsAttendanceTO.setPercentage(studentAttendance.getPercentage());
									}
									else{
									Float per = Float.parseFloat(studentAttendance.getPercentage());
									//studentsAttendanceTO.setPercentage(String.valueOf(Math.round(per)));
									studentsAttendanceTO.setPercentage(String.valueOf(CommonUtil.roundToDecimal(per, 2)));
									}
									finalList.add(studentsAttendanceTO);
								}
							}
							session.setAttribute(STUDENT_ATTENDANCE_REPORT, finalList);
						}
				} catch (Exception exception) {	
					String msg = super.handleApplicationException(exception);
					studentsAttendanceReportForm.setErrorMessage(msg);
					studentsAttendanceReportForm.setErrorStack(exception.getMessage());
					return mapping.findForward(CMSConstants.ERROR_PAGE);
				}
			} else {
				addErrors(request, errors);
				setRequiredDataToForm(studentsAttendanceReportForm);
				return mapping.findForward(CMSConstants.STUDENTS_ATTENDANCE_REPORT);
			}
		}
		else{
			//start Added by mehaboob excel and csv
			if(request.getParameter("mode")!=null){
			 if(request.getParameter("mode").equalsIgnoreCase("excel") && request.getParameter("mode1")==null){
				List<StudentsAttendanceReportTO> attendanceReportTOList=(List<StudentsAttendanceReportTO>) session.getAttribute(STUDENT_ATTENDANCE_REPORT);
				StudentsAttendanceReportHandler.getInstance().convertToToExcel(attendanceReportTOList,request);
				studentsAttendanceReportForm.setDownloadExcel("downloadExcel");
				studentsAttendanceReportForm.setMode("excel");
			}else if(request.getParameter("mode").equalsIgnoreCase("CSV") && request.getParameter("mode1")==null){
				List<StudentsAttendanceReportTO> attendanceReportTOList=(List<StudentsAttendanceReportTO>) session.getAttribute(STUDENT_ATTENDANCE_REPORT);
				StudentsAttendanceReportHandler.getInstance().convertToToExcel(attendanceReportTOList,request);
				studentsAttendanceReportForm.setDownloadExcel("downloadCSV");
				studentsAttendanceReportForm.setMode("CSV");
			}
			}
			// end 
		}
		
		log.info("Exit submitStudentAttendanceReport");
		return mapping.findForward(CMSConstants.STUDENTS_ATTENDANCE_REPORT_RESULT);
	}

	/**
	 * Method to set the required values to the form to display it in the UI.
	 * @param request
	 * @throws Exception
	 */
	public void setRequiredDataToForm(StudentsAttendanceReportForm studentsAttendanceReportForm) throws Exception {
		log.info("entered setRequiredDataToForm..");

		 //setting AttendanceTypeList to Form
		List<AttendanceTypeTO> attendanceTypeList = AttendanceTypeHandler.getInstance().getAttendanceType(); 
		studentsAttendanceReportForm.setAttendanceTypeList(attendanceTypeList);
		// Getting the class map of the current academic year.
		Map<Integer, String> classMap = setpClassMapToRequest();
		studentsAttendanceReportForm.setClassMap(classMap);

		log.info("Exit setRequiredDataToForm..");
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


			Map<Integer, String> classMap = CommonAjaxHandler.getInstance().getClassesByYear(currentYear);
			log.info("exit of setpClassMapToRequest of MonthlyAttendanceEntryAction class.");
			return classMap;
		} catch (Exception e) {
			log.info("error in setpClassMapToRequest of MonthlyAttendanceEntryAction class.",e);
			throw new ApplicationException(e);
		}
	}

	/**
	 * Method to validate the manually entered date format
	 * @param studentsAttendanceReportForm
	 * @param errors
	 */
	private void validateAttendanceDate(StudentsAttendanceReportForm studentsAttendanceReportForm,ActionMessages errors) {
		
		if (studentsAttendanceReportForm.getStartDate() != null && !StringUtils.isEmpty(studentsAttendanceReportForm.getStartDate()) && !CommonUtil.isValidDate(studentsAttendanceReportForm.getStartDate())) {
			errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ATTANDANCE_REPORT_STARTDATE_INVALID));
		}
		if (studentsAttendanceReportForm.getEndDate() != null && !StringUtils.isEmpty(studentsAttendanceReportForm.getEndDate()) && !CommonUtil.isValidDate(studentsAttendanceReportForm.getEndDate())) {
			errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ATTANDANCE_REPORT_ENDDATE_INVALID));
		}
		//if start date greater than end date then showing error message
		if(errors.isEmpty()){
			if(CommonUtil.checkForEmpty(studentsAttendanceReportForm.getStartDate()) && CommonUtil.checkForEmpty(studentsAttendanceReportForm.getEndDate())){
				Date startDate = CommonUtil.ConvertStringToDate(studentsAttendanceReportForm.getStartDate());
				Date endDate = CommonUtil.ConvertStringToDate(studentsAttendanceReportForm.getEndDate());

				Calendar cal1 = Calendar.getInstance();
				cal1.setTime(startDate);
				Calendar cal2 = Calendar.getInstance();
				cal2.setTime(endDate);
				long daysBetween = CommonUtil.getDaysBetweenDates(cal1, cal2);
				if(daysBetween <= 0) {
					errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.KNOWLEDGEPRO_STARTDATE_CONNOTBELESS));
				}
			}
		}
	}
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initMonthlyStudentAttendanceReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		StudentsAttendanceReportForm studentsAttendanceReportForm = (StudentsAttendanceReportForm) form;
		
		try{
			log.info("Entered initStudentAttendanceReport");
			studentsAttendanceReportForm.resetFields();
			studentsAttendanceReportForm.setIsMonthlyAttendance(CMSConstants.KNOWLEDGEPRO_TRUE);
			setRequiredDataToForm(studentsAttendanceReportForm);
			HttpSession session = request.getSession(false);
			session.removeAttribute(STUDENT_ATTENDANCE_REPORT);
			log.info("Exit initStudentAttendanceReport");
		} catch (Exception exception) {	
			String msg = super.handleApplicationException(exception);
			studentsAttendanceReportForm.setErrorMessage(msg);
			studentsAttendanceReportForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.MONTHLY_STUDENTS_ATTENDANCE_REPORT);
	}
	
	/**
	 * Method is to get the candidates based on the search criteria
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward submitMonthlyStudentAttendanceReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("Entered submitStudentAttendanceReport");
		StudentsAttendanceReportForm studentsAttendanceReportForm = (StudentsAttendanceReportForm) form;

		HttpSession session = request.getSession();
		if(session.getAttribute(STUDENT_ATTENDANCE_REPORT)==null){
			 ActionMessages errors = studentsAttendanceReportForm.validate(mapping, request);
			validateAttendanceDate(studentsAttendanceReportForm, errors);
			if (errors.isEmpty()) {
				try {
					Map<String, List<StudentsAttendanceReportTO>> monthlyStudentsAttendanceResultMap = StudentsAttendanceReportHandler.getInstance().getMonthlyStudentsAttendanceResults(studentsAttendanceReportForm);
					if(monthlyStudentsAttendanceResultMap.isEmpty()){
						errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND));
						saveErrors(request, errors);
						studentsAttendanceReportForm.resetFields();
						studentsAttendanceReportForm.setIsMonthlyAttendance(CMSConstants.KNOWLEDGEPRO_TRUE);
						setRequiredDataToForm(studentsAttendanceReportForm);
						return mapping.findForward(CMSConstants.MONTHLY_STUDENTS_ATTENDANCE_REPORT);
					}
					if (monthlyStudentsAttendanceResultMap != null) {
						List<StudentsAttendanceReportTO> finalList = new ArrayList<StudentsAttendanceReportTO>();
						Iterator<String> itr = monthlyStudentsAttendanceResultMap.keySet().iterator();
						while (itr.hasNext()) {
							String className = (String) itr.next();
							Iterator<StudentsAttendanceReportTO> it = monthlyStudentsAttendanceResultMap.get(className).iterator();
							while (it.hasNext()) {
								StudentsAttendanceReportTO studentAttendance = (StudentsAttendanceReportTO) it.next();
								
								StudentsAttendanceReportTO studentsAttendanceTO = new StudentsAttendanceReportTO();
								
								studentsAttendanceTO.setClassName(className);
								studentsAttendanceTO.setRegisterNumber(studentAttendance.getRegisterNumber());
								studentsAttendanceTO.setStudentName(studentAttendance.getStudentName());
								studentsAttendanceTO.setClassesHeld(studentAttendance.getClassesHeld());
								studentsAttendanceTO.setClassesAttended(studentAttendance.getClassesAttended());
								studentsAttendanceTO.setPercentage(studentAttendance.getPercentage());
								
								finalList.add(studentsAttendanceTO);
							}
						}
						request.setAttribute(CMSConstants.CONDITIONS_OPERATION, "monthly");
						session.setAttribute(STUDENT_ATTENDANCE_REPORT, finalList);
					}
				} catch (Exception exception) {	
					String msg = super.handleApplicationException(exception);
					studentsAttendanceReportForm.setErrorMessage(msg);
					studentsAttendanceReportForm.setErrorStack(exception.getMessage());
					return mapping.findForward(CMSConstants.ERROR_PAGE);
				}
			} else {
				addErrors(request, errors);
				setRequiredDataToForm(studentsAttendanceReportForm);
				return mapping.findForward(CMSConstants.MONTHLY_STUDENTS_ATTENDANCE_REPORT);
			}
		}	
		log.info("Exit submitStudentAttendanceReport");
		return mapping.findForward(CMSConstants.STUDENTS_ATTENDANCE_REPORT_RESULT);
	}
	/**
	 * printing the student data in new window 
	 */
	public ActionForward printStudentReport(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered printStudentReport");
		log.info("Exit printStudentReport");
		return mapping.findForward(CMSConstants.STUDENT_REPORT_PRINT);
	}
}