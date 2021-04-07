package com.kp.cms.actions.reports;

import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
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
import org.apache.struts.action.ActionMessages;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.bo.admin.ClassSchemewise;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.attendance.AttendanceSummaryReportForm;
import com.kp.cms.forms.reports.StudentAbsenceDetailsForm;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.attendance.AttendanceTypeHandler;
import com.kp.cms.handlers.reports.StudentAbsenceDetailsHandler;
import com.kp.cms.to.attendance.AttendanceTypeTO;
import com.kp.cms.to.reports.StudentAbsenceDetailsTO;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.CurrentAcademicYear;

@SuppressWarnings("deprecation")
public class StudentAbsenceDetailsAction extends BaseDispatchAction {
	
	private static final Log log = LogFactory.getLog(StudentAbsenceDetailsAction.class);
	private static final String STUDENT_ABSENCE_DETAILS = "studentAbsenceDetails";

	/**
	 * Method to set the required data to the form to display in studentAbsenceDetails.jsp
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initStudentAbsenceDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("Entered initStudentAbsenceDetails");
		StudentAbsenceDetailsForm studentAbsenceDetailsForm = (StudentAbsenceDetailsForm)form;
		studentAbsenceDetailsForm.resetFields();
		setRequiredDataToForm(studentAbsenceDetailsForm,request);
		HttpSession session = request.getSession(false);
		session.removeAttribute(STUDENT_ABSENCE_DETAILS);
		log.info("Exit initStudentAbsenceDetails");
		return mapping.findForward(CMSConstants.STUDENTS_ATTENDANCE_DETAILS);
	}
	
	public ActionForward initStudentAbsenceDetailsReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("Entered initStudentAbsenceDetails");
		//StudentAbsenceDetailsForm studentAbsenceDetailsForm = (StudentAbsenceDetailsForm)form;
		log.info("Exit initStudentAbsenceDetails");
		return mapping.findForward(CMSConstants.STUDENTS_ATTENDANCE_DETAILS_LINK);
	}
	/**
	 * Method to fetch to student absence details result to display in studentAbsenceDetailsReport.jsp
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward submitStudentAbsenceDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered submitStudentAbsenceDetails");	
		StudentAbsenceDetailsForm studentAbsenceDetailsForm = (StudentAbsenceDetailsForm)form;
		HttpSession session = request.getSession(false);
		if(session.getAttribute(STUDENT_ABSENCE_DETAILS) == null){
			 ActionMessages errors = studentAbsenceDetailsForm.validate(mapping, request);
			validateAttendanceDate(studentAbsenceDetailsForm, errors);
			if (errors.isEmpty()) {	
				try {
					List<StudentAbsenceDetailsTO> studentAbsenceDetailsList = StudentAbsenceDetailsHandler.getInstance().getStudentAbsenceDetails(studentAbsenceDetailsForm);
					if(studentAbsenceDetailsList.isEmpty()){
						errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND));
						saveErrors(request, errors);
						studentAbsenceDetailsForm.resetFields();
						setRequiredDataToForm(studentAbsenceDetailsForm,request);
						return mapping.findForward(CMSConstants.STUDENTS_ATTENDANCE_DETAILS);
					}
					if(studentAbsenceDetailsList !=null){			
						session.setAttribute(STUDENT_ABSENCE_DETAILS, studentAbsenceDetailsList );
				}
				}catch (Exception exception) {	
					String msg = super.handleApplicationException(exception);
					studentAbsenceDetailsForm.setErrorMessage(msg);
					studentAbsenceDetailsForm.setErrorStack(exception.getMessage());
					return mapping.findForward(CMSConstants.ERROR_PAGE);
				}
			} else {
				addErrors(request, errors);
				setRequiredDataToForm(studentAbsenceDetailsForm,request);
				return mapping.findForward(CMSConstants.STUDENTS_ATTENDANCE_DETAILS);
			}
		}
		log.info("Exit submitStudentAbsenceDetails");
		return mapping.findForward(CMSConstants.STUDENTS_ATTENDANCE_DETAILS_RESULT);
	}

	
	/**
	 * This method sets the required data to the form
	 * @param request
	 * @throws Exception
	 */
	public void setRequiredDataToForm(StudentAbsenceDetailsForm studentAbsenceDetailsForm,HttpServletRequest request) throws Exception{
		log.info("Entered setRequiredDataToForm");	
	    
	    //setting AttendanceTypeList to Form
		List<AttendanceTypeTO> attendanceTypeList = AttendanceTypeHandler.getInstance().getAttendanceType(); 
		studentAbsenceDetailsForm.setAttendanceTypeList(attendanceTypeList);
		if(studentAbsenceDetailsForm.getClassesName()!=null && studentAbsenceDetailsForm.getClassesName().length>0){
			Map<Integer, String> subjectMap = setSubjectMapForForm(studentAbsenceDetailsForm);
			request.setAttribute("subjectMaps", subjectMap);
		}
		// Getting the class map of the current academic year.
		Map<Integer, String> classMap = setpClassMapToRequest(studentAbsenceDetailsForm.getAcademicYear());
		studentAbsenceDetailsForm.setClassMap(classMap);
		if(studentAbsenceDetailsForm.getAcademicYear()==null){
			int year=CurrentAcademicYear.getInstance().getAcademicyear();
			if(year>0)
				studentAbsenceDetailsForm.setAcademicYear(Integer.toString(year));
		}
		log.info("Exit setRequiredDataToForm");	
	}
	private Map<Integer, String> setSubjectMapForForm(StudentAbsenceDetailsForm studentAbsenceDetailsForm) {
		Map<Integer, String> subjectsMap = new HashMap<Integer, String>();

		try {
			String selectedClasses[] = studentAbsenceDetailsForm.getClassesName();

			Set<Integer> classesIdsSet = new HashSet<Integer>();
			for (int i = 0; i < selectedClasses.length; i++) {
				classesIdsSet.add(Integer.parseInt(selectedClasses[i]));
			}

			List<ClassSchemewise> classSchemewiseList = CommonAjaxHandler
					.getInstance().getDetailsonClassSchemewiseId(classesIdsSet);
			Iterator<ClassSchemewise> itr = classSchemewiseList.iterator();
			ClassSchemewise classSchemewise;
			while (itr.hasNext()) {
				classSchemewise = itr.next();
				if (classSchemewise.getCurriculumSchemeDuration()
						.getAcademicYear() != null
						&& classSchemewise.getClasses().getCourse().getId() != 0
						&& classSchemewise.getClasses().getTermNumber() != 0) {
					int year = classSchemewise.getCurriculumSchemeDuration()
							.getAcademicYear();
					int courseId = classSchemewise.getClasses().getCourse()
							.getId();
					int term = classSchemewise.getClasses().getTermNumber();
					Map<Integer, String> tempMap = CommonAjaxHandler.getInstance()
							.getSubjectByCourseIdTermYear(courseId, year, term);
					subjectsMap.putAll(tempMap);
				}
			}

		} catch (Exception e) {
			log.debug(e.getMessage());
		}

		return subjectsMap;
	}
	/**
	 * Sets all the classes for the current year in request scope
	 */
	private Map<Integer, String> setpClassMapToRequest(String academicYear) throws ApplicationException {
		log.info("entering into setpClassMapToRequest of StudentAbsenceDetailsAction class.");
		try {
			Calendar calendar = Calendar.getInstance();
			int currentYear = calendar.get(Calendar.YEAR);
			// code by hari
			if(academicYear==null){
				int year=CurrentAcademicYear.getInstance().getAcademicyear();
				if(year!=0){
					currentYear=year;
				}// end
				}else{
					currentYear=Integer.parseInt(academicYear);
				}

			Map<Integer, String> classMap = CommonAjaxHandler.getInstance().getClassesByYear(currentYear);
			log.info("exit of setpClassMapToRequest of StudentAbsenceDetailsAction class.");
			return classMap;
		} catch (Exception e) {
			log.info("error in setpClassMapToRequest of StudentAbsenceDetailsAction class.",e);
			throw new ApplicationException(e);
		}
	}
	
	/**
	 * This method validates the date format
	 * @param studentAbsenceDetailsForm
	 * @param errors
	 */
	private void validateAttendanceDate(StudentAbsenceDetailsForm studentAbsenceDetailsForm, ActionMessages errors) {
		if(studentAbsenceDetailsForm.getStartDate()!=null && !StringUtils.isEmpty(studentAbsenceDetailsForm.getStartDate())&& !CommonUtil.isValidDate(studentAbsenceDetailsForm.getStartDate())){
			errors.add(CMSConstants.ERROR,new ActionError(CMSConstants.ATTANDANCE_REPORT_STARTDATE_INVALID));
		}
		if(studentAbsenceDetailsForm.getEndDate()!=null && !StringUtils.isEmpty(studentAbsenceDetailsForm.getEndDate())&& !CommonUtil.isValidDate(studentAbsenceDetailsForm.getEndDate())){
			errors.add(CMSConstants.ERROR,new ActionError(CMSConstants.ATTANDANCE_REPORT_ENDDATE_INVALID));
		}
	}	
	/**
	 * printing the student data in new window 
	 */
	public ActionForward printStudentAbsenceReport(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered printStudentAbsenceReport");
		log.info("Exit printStudentAbsenceReport");
		return mapping.findForward(CMSConstants.STUDENT_ABSENCE_REPORT_PRINT);
	}
}