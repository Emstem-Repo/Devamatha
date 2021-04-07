package com.kp.cms.actions.reports;

import java.util.ArrayList;
import java.util.Calendar;
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
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.actions.attendance.AttendanceSummaryReportAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.admission.DisciplinaryDetailsForm;
import com.kp.cms.forms.reports.StudentWiseAttendanceSummaryForm;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.attendance.AttendanceEntryHandler;
import com.kp.cms.handlers.attendance.AttendanceTypeHandler;
import com.kp.cms.handlers.attendance.StudentAttendanceSummaryHandler;
import com.kp.cms.handlers.attendance.TeacherDepartmentEntryHandler;
import com.kp.cms.handlers.reports.StudentWiseAttendanceSummaryHandler;
import com.kp.cms.to.admin.SubjectTO;
import com.kp.cms.to.attendance.AttendanceTypeTO;
import com.kp.cms.to.attendance.PeriodTO;
import com.kp.cms.to.exam.ExamMarksEntryDetailsTO;
import com.kp.cms.to.reports.StudentWiseAttendanceSummaryReportTO;
import com.kp.cms.to.usermanagement.StudentAttendanceTO;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.CurrentAcademicYear;

@SuppressWarnings("deprecation")
public class StudentWiseAttendanceSummaryAction extends BaseDispatchAction {

	private static final Log log = LogFactory
			.getLog(AttendanceSummaryReportAction.class);

	/**
	 * 
	 * Performs the initialize Absence Information Summary report.
	 * 
	 * @param mapping
	 *            The ActionMapping used to select this instance
	 * @param form
	 *            The optional ActionForm bean for this request (if any)
	 * @param request
	 *            The HTTP request we are processing
	 * @param response
	 *            The HTTP response we are creating
	 * @return The forward to which control should be transferred,
	 * @throws Exception
	 *             if an exception occurs
	 */
	public ActionForward initStudentWiseAttendanceSummary(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		StudentWiseAttendanceSummaryForm studentWiseAttendanceSummaryForm = (StudentWiseAttendanceSummaryForm) form;
		studentWiseAttendanceSummaryForm.resetFields();
		setRequiredDataToForm(studentWiseAttendanceSummaryForm, request);
		HttpSession session = request.getSession(false);
		session.removeAttribute("studentWiseAttendanceSummary");
		return mapping
				.findForward(CMSConstants.INIT_STUDENT_WISE_ATTENDANCE_SUMMARY);
	}
	
	/**
	 * 
	 * Performs the initialize Absence Information Summary report.
	 * 
	 * @param mapping
	 *            The ActionMapping used to select this instance
	 * @param form
	 *            The optional ActionForm bean for this request (if any)
	 * @param request
	 *            The HTTP request we are processing
	 * @param response
	 *            The HTTP response we are creating
	 * @return The forward to which control should be transferred,
	 * @throws Exception
	 *             if an exception occurs
	 */
	public ActionForward getIndividualStudentWiseAttendanceSummary(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		StudentWiseAttendanceSummaryForm loginForm = (StudentWiseAttendanceSummaryForm) form;
		ActionMessages messages = new ActionMessages();
		HttpSession session = request.getSession();
		String studentid = (String) session.getAttribute("studentid");
		String courseId = (String) session.getAttribute("stuCourseId");
		loginForm
				.setSubjectwiseAttendanceTOList(StudentAttendanceSummaryHandler
						.getInstance().getStudentSubjectWiseAttendanceList(
								Integer.valueOf(studentid.trim()), loginForm, courseId));
		ActionMessage message = new ActionMessage("knowledgepro.show.attendance.message");
		messages.add("messages", message);
		messages.add("messages",new ActionMessage("knowledgepro.show.attendance.totalmessage"));
		saveMessages(request, messages);
		
		if(loginForm.isMarkPresent()){
			return mapping.findForward("getIndividualStudentWiseAttendanceSummary");
		}
		else{
			return mapping.findForward("getIndividualStudentWiseAttendanceSummary2");
		}
	}
	
	
	public ActionForward getIndividualStudentWiseSubjectAndActivityAttendanceSummary(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		StudentWiseAttendanceSummaryForm loginForm = (StudentWiseAttendanceSummaryForm) form;
		ActionMessages messages = new ActionMessages();
		HttpSession session = request.getSession();
		String studentid = (String) session.getAttribute("studentid");
		String courseId = (String) session.getAttribute("stuCourseId");
		loginForm
				.setSubjectwiseAttendanceTOList(StudentAttendanceSummaryHandler
						.getInstance().getStudentWiseSubjectsAttendanceList(
								Integer.valueOf(studentid.trim()), loginForm, courseId));
		
		ActionMessage message = new ActionMessage("knowledgepro.show.attendance.message");
		messages.add("messages", message);
		messages.add("messages",new ActionMessage("knowledgepro.show.attendance.totalmessage"));
		saveMessages(request, messages);
		
		if(loginForm.isMarkPresent()){
			return mapping.findForward("getIndividualStudentWiseAttendanceSummary");
		}
		else{
			return mapping.findForward("getIndividualStudentWiseAttendanceSummary2");
		}
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
//	public ActionForward getMidSemResult(ActionMapping mapping,
//			ActionForm form, HttpServletRequest request,
//			HttpServletResponse response) throws Exception {
//		StudentWiseAttendanceSummaryForm studentWiseAttendanceSummaryForm = (StudentWiseAttendanceSummaryForm) form;
//		HttpSession session = request.getSession();
//		ActionMessages messages = new ActionMessages();
//		try{
//			String studentid = (String) session.getAttribute("studentid");
//			
//			String courseId = (String) session.getAttribute("stuCourseId");
//			studentWiseAttendanceSummaryForm.setSubjectwiseAttendanceTOList(StudentAttendanceSummaryHandler.getInstance().getMidSemMarksDetails(Integer.valueOf(studentid.trim()), studentWiseAttendanceSummaryForm, courseId));
//			List<StudentWiseSubjectSummaryTO> subjectSummaryTOs = studentWiseAttendanceSummaryForm.getSubjectwiseAttendanceTOList();
//			//List<ExamDefinitionBO> examDefinitionBOs = StudentAttendanceSummaryHandler.getInstance().getSortedExamNames(subjectSummaryTOs);
//			//studentWiseAttendanceSummaryForm.setExamDefinitionBO(examDefinitionBOs);
//			if(subjectSummaryTOs != null && !subjectSummaryTOs.isEmpty()){
//				Iterator<StudentWiseSubjectSummaryTO> studentIterator = subjectSummaryTOs.iterator();
//				List<ExamMarksEntryDetailsTO> entryDetailsTOs = new ArrayList<ExamMarksEntryDetailsTO>();
//				while (studentIterator.hasNext()) {
//					StudentWiseSubjectSummaryTO studentWiseSubjectSummaryTO = (StudentWiseSubjectSummaryTO) studentIterator.next();
//					List<ExamMarksEntryDetailsTO> entryDetailsTOs1 = studentWiseSubjectSummaryTO.getExamMarksEntryDetailsTOList();
//					if(entryDetailsTOs1 != null){
//						entryDetailsTOs.addAll(entryDetailsTOs1);
//					}
//				}
//				if(entryDetailsTOs == null || entryDetailsTOs.isEmpty()){
//					ActionMessage message = new ActionMessage("knowledgepro.studentlogin.markscard.publish");
//					messages.add("messages", message);
//					saveMessages(request, messages);
//					studentWiseAttendanceSummaryForm.setMarkPublished(true);
//				}
//			}else{
//				ActionMessage message = new ActionMessage("knowledgepro.studentlogin.markscard.publish");
//				messages.add("messages", message);
//				saveMessages(request, messages);
//			}
//			
//		}catch (Exception e) {
//			log.error("Error while getting the absence information summary"+e.getMessage());
//			String msg = super.handleApplicationException(e);
//			studentWiseAttendanceSummaryForm.setErrorMessage(msg);
//			studentWiseAttendanceSummaryForm.setErrorStack(e.getMessage());
//			return mapping.findForward(CMSConstants.STUDENT_MIDSEMRESULT);
//		}
//		
//		return mapping.findForward(CMSConstants.STUDENT_MIDSEMRESULT);
//		
//	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getInternalMarkDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		StudentWiseAttendanceSummaryForm studentWiseAttendanceSummaryForm = (StudentWiseAttendanceSummaryForm) form;
		HttpSession session = request.getSession();
		ActionMessages messages = new ActionMessages();
		try{
			String studentid = (String) session.getAttribute("studentid");
			
			String courseId = (String) session.getAttribute("stuCourseId");
			List<SubjectTO> subjectList = StudentAttendanceSummaryHandler.getInstance().getSubjectListWithMarks(Integer.valueOf(studentid.trim()), studentWiseAttendanceSummaryForm, courseId);
			//List<ExamDefinitionBO> examDefinitionBOs = StudentAttendanceSummaryHandler.getInstance().getSortedExamNames(subjectList);
			studentWiseAttendanceSummaryForm.setSubjectListWithMarks(subjectList);
			//studentWiseAttendanceSummaryForm.setExamDefinitionBO(examDefinitionBOs);
			if(subjectList != null && !subjectList.isEmpty()){
				Iterator<SubjectTO> studentIterator = subjectList.iterator();
				List<ExamMarksEntryDetailsTO> entryDetailsTOs = new ArrayList<ExamMarksEntryDetailsTO>();
				while (studentIterator.hasNext()) {
					SubjectTO studentSubjectList = (SubjectTO) studentIterator.next();
					List<ExamMarksEntryDetailsTO> entryDetailsTOs1 = studentSubjectList.getExamMarksEntryDetailsTOList();
					if(entryDetailsTOs1 != null){
						entryDetailsTOs.addAll(entryDetailsTOs1);
					}
				}
				if(entryDetailsTOs == null || entryDetailsTOs.isEmpty()){
					ActionMessage message = new ActionMessage("knowledgepro.studentlogin.markscard.publish");
					messages.add("messages", message);
					saveMessages(request, messages);
					studentWiseAttendanceSummaryForm.setMarkPublished(true);
				}
			}else{
				ActionMessage message = new ActionMessage("knowledgepro.studentlogin.markscard.publish");
				messages.add("messages", message);
				saveMessages(request, messages);
			}
			
		}catch (Exception e) {
			log.error("Error while getting the absence information summary"+e.getMessage());
			String msg = super.handleApplicationException(e);
			studentWiseAttendanceSummaryForm.setErrorMessage(msg);
			studentWiseAttendanceSummaryForm.setErrorStack(e.getMessage());
			ActionMessage message = new ActionMessage("knowledgepro.studentlogin.markscard.publish");
			messages.add("messages", message);
			saveMessages(request, messages);
			return mapping.findForward(CMSConstants.STUDENT_MIDSEMRESULT);
		}
		
		return mapping.findForward(CMSConstants.STUDENT_MIDSEMRESULT);
		
	}
	
	/**
	 * 
	 * Performs the initialize Absence Information Summary report.
	 * 
	 * @param mapping
	 *            The ActionMapping used to select this instance
	 * @param form
	 *            The optional ActionForm bean for this request (if any)
	 * @param request
	 *            The HTTP request we are processing
	 * @param response
	 *            The HTTP response we are creating
	 * @return The forward to which control should be transferred,
	 * @throws Exception
	 *             if an exception occurs
	 */
	public ActionForward getStudentWiseAttendanceSummary(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		StudentWiseAttendanceSummaryForm studentWiseAttendanceSummaryForm = (StudentWiseAttendanceSummaryForm) form;
		HttpSession session = request.getSession();
		if(session.getAttribute("studentWiseAttendanceSummary")==null){
			try {
	
				 ActionErrors errors = studentWiseAttendanceSummaryForm
						.validate(mapping, request);
				validateAttendanceDate(studentWiseAttendanceSummaryForm, errors);
				if (errors.isEmpty()) {
					List<StudentWiseAttendanceSummaryReportTO> studentWiseAttendanceSummaryReport = StudentWiseAttendanceSummaryHandler
							.getInstance()
							.getStudentWiseAttendanceSummaryReportTOList(
									studentWiseAttendanceSummaryForm);
					session.setAttribute("studentWiseAttendanceSummary",
							studentWiseAttendanceSummaryReport);
				} else {
					addErrors(request, errors);
					setRequiredDataToForm(studentWiseAttendanceSummaryForm, request);
					return mapping
							.findForward(CMSConstants.INIT_STUDENT_WISE_ATTENDANCE_SUMMARY);
				}
	
			} catch (Exception e) {		
				log.error("Error while getting the absence information summary"+e.getMessage());
				String msg = super.handleApplicationException(e);
				studentWiseAttendanceSummaryForm.setErrorMessage(msg);
				studentWiseAttendanceSummaryForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		}	
		return mapping
				.findForward(CMSConstants.GET_STUDENT_WISE_ATTENDANCE_SUMMARY);
	}

	/*
	 * This method sets the required data to form and request.
	 */
	/**
	 * @param attendanceSummaryReportForm
	 * @param request
	 * @throws Exception
	 */
	public void setRequiredDataToForm(
			StudentWiseAttendanceSummaryForm attendanceSummaryReportForm,
			HttpServletRequest request) throws Exception {
		// setting programList to Request
//		List<ProgramTypeTO> programTypeList = ProgramTypeHandler.getInstance()
//				.getProgramType();
//		request.setAttribute("programTypeList", programTypeList);
		List<AttendanceTypeTO> attendanceTypeList = AttendanceTypeHandler.getInstance().getAttendanceType(); 
		request.setAttribute("attendanceTypeList", attendanceTypeList);
		
		Calendar calendar = Calendar.getInstance();
		int currentYear = calendar.get(Calendar.YEAR);
		// code by hari
		int year=CurrentAcademicYear.getInstance().getAcademicyear();
		if(year!=0){
			currentYear=year;
		}// end

		Map<Integer, String> classMap = CommonAjaxHandler.getInstance().getClassesByYear(currentYear);
		attendanceSummaryReportForm.setClassMap(classMap);		
		
		
	}

	/**
	 * 
	 * Performs the initialize Absence Information Summary report.
	 * 
	 * @param mapping
	 *            The ActionMapping used to select this instance
	 * @param form
	 *            The optional ActionForm bean for this request (if any)
	 * @param request
	 *            The HTTP request we are processing
	 * @param response
	 *            The HTTP response we are creating
	 * @return The forward to which control should be transferred,
	 * @throws Exception
	 *             if an exception occurs
	 */
	public ActionForward initStudentWiseAttendanceSummaryAdmin(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("Entering into initStudentWiseAttendanceSummaryAdmin of StudentWiseAttendanceSummaryAction");
		StudentWiseAttendanceSummaryForm studentWiseAttendanceSummaryForm = (StudentWiseAttendanceSummaryForm) form;
		setRequiredDataToForm(studentWiseAttendanceSummaryForm, request);
		studentWiseAttendanceSummaryForm.resetFields();
		studentWiseAttendanceSummaryForm.setSubjectwiseAttendanceTOList(null);
		HttpSession session = request.getSession();
		session.removeAttribute("studentWiseAttendanceSummary");
		log.info("Entering into initStudentWiseAttendanceSummaryAdmin of StudentWiseAttendanceSummaryAction");
		return mapping
				.findForward(CMSConstants.GET_INDIVIDUAL_STUDENTATTENDANCE_ADMIN);
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return Gets student wise attendance summary by admin
	 * @throws Exception
	 */
	public ActionForward getStudentAttendanceSummaryByAdminReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering into getStudentAttendanceSummaryByAdminReport of StudentWiseAttendanceSummaryAction");
		StudentWiseAttendanceSummaryForm attendanceSummaryForm = (StudentWiseAttendanceSummaryForm) form;
		 ActionErrors errors = attendanceSummaryForm.validate(mapping, request);
		validateFields(attendanceSummaryForm,errors);
		try {
			if (errors.isEmpty()) {
				StudentWiseAttendanceSummaryHandler.getInstance()
						.getStudentWiseAttendanceSummaryByName(
								attendanceSummaryForm, errors);
				if(errors.isEmpty()){
					return mapping.findForward("getStudentAttendanceSummaryByAdminReport");
				}
			}
		} catch (Exception e) {		
			log.error("Error while getting the attendance summary"+e.getMessage());
			String msg = super.handleApplicationException(e);
			attendanceSummaryForm.setErrorMessage(msg);
			attendanceSummaryForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		addErrors(request, errors);
		attendanceSummaryForm.resetFields();
		log.info("Leaving into getStudentAttendanceSummaryByAdminReport of StudentWiseAttendanceSummaryAction");
		return mapping.findForward(CMSConstants.GET_INDIVIDUAL_STUDENTATTENDANCE_ADMIN);
	}

	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return Gets student wise attendance summary by admin
	 * @throws Exception
	 */
	public ActionForward getStudentAttendanceSummaryByAdmin(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering into getStudentAttendanceSummaryByAdmin of StudentWiseAttendanceSummaryAction");
		StudentWiseAttendanceSummaryForm attendanceSummaryForm = (StudentWiseAttendanceSummaryForm) form;
		String studentId = request.getParameter("studentID");
		 ActionErrors errors = attendanceSummaryForm.validate(mapping, request);
		attendanceSummaryForm.setStudentID(studentId);
		try {
			if (errors.isEmpty()) {
				StudentWiseAttendanceSummaryHandler.getInstance()
						.getStudentWiseAttendanceSummaryByAdmin(
								attendanceSummaryForm, errors,Integer.parseInt(studentId));
				if(errors.isEmpty()){
					return mapping.findForward(CMSConstants.GET_INDIVIDUAL_STUDENTATTENDANCE_ADMIN_RESULT);
				}
			}
		} catch (Exception e) {	
			log.error("Error while getting the attendance summary"+e.getMessage());
			String msg = super.handleApplicationException(e);
			attendanceSummaryForm.setErrorMessage(msg);
			attendanceSummaryForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		addErrors(request, errors);
		attendanceSummaryForm.resetFields();
		log.info("Leaving into getStudentAttendanceSummaryByAdmin of StudentWiseAttendanceSummaryAction");
		return mapping.findForward(CMSConstants.GET_INDIVIDUAL_STUDENTATTENDANCE_ADMIN_RESULT);
	
	}

	
	private void validateFields(StudentWiseAttendanceSummaryForm attendanceSummaryForm,
			ActionErrors errors) {
		log.info("entered validateForNumaric..");
		if (errors == null){
			errors = new ActionErrors();
		}		
		if(attendanceSummaryForm.getStartRegisterNo()== null || attendanceSummaryForm.getStartRegisterNo().isEmpty()){
			if(attendanceSummaryForm.getStartRollNo()== null || attendanceSummaryForm.getStartRollNo().isEmpty()){
				if(attendanceSummaryForm.getStudentName()== null || attendanceSummaryForm.getStudentName().isEmpty()){
					if(errors.get("knowledgepro.attendance.regno/rollno.studname")!=null && !errors.get("knowledgepro.attendance.regno/rollno.studname").hasNext()){									
						errors.add("knowledgepro.attendance.regno/rollno.studname",new ActionError("knowledgepro.attendance.regno/rollno.studname"));
					}

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
	 * @returns the absence information period details for the student
	 * @throws Exception
	 */
	public ActionForward getAbsencePeriodDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering into getAbsencePeriodDetails of StudentWiseAttendanceSummaryAction");
		StudentWiseAttendanceSummaryForm attendanceSummaryForm = (StudentWiseAttendanceSummaryForm) form;
		ActionErrors errors = new ActionErrors();
		try {
			attendanceSummaryForm.setSubjectAttendance("Yes");
			if (Integer.valueOf(attendanceSummaryForm.getClassesAbsent()) != 0) {
				List<PeriodTO> periodList = StudentWiseAttendanceSummaryHandler.getInstance().getAbsencePeriodDetails(attendanceSummaryForm);
				attendanceSummaryForm.setPeriodList(periodList);
			} else {
				attendanceSummaryForm.setPeriodList(null);
				errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.ATTENDANCE_NO_RESULTS_FOUND));
				addErrors(request, errors);
			}
		} catch (Exception e) {		
			log.error("Error while getting the AbsencePeriodDetails"+e.getMessage());
			String msg = super.handleApplicationException(e);
			attendanceSummaryForm.setErrorMessage(msg);
			attendanceSummaryForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Leaving into getAbsencePeriodDetails of StudentWiseAttendanceSummaryAction");
		return mapping.findForward(CMSConstants.ATTENDANCE_ABSENCE_PERIOD_DETAILS);	
	}
	
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @returns activity attendance of individual student
	 * @throws Exception
	 */
	
	
// uncomment this code to print activity attendence alone without additional subject
	
	
	/*public ActionForward getIndividualStudentWiseActivityAttendanceSummary(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering into getIndividualStudentWiseActivityAttendanceSummary of StudentWiseAttendanceSummaryAction");
		StudentWiseAttendanceSummaryForm loginForm = (StudentWiseAttendanceSummaryForm) form;
		HttpSession session = request.getSession();
		String studentid = (String) session.getAttribute("studentid");	
		String courseId = (String) session.getAttribute("stuCourseId");
		try {
			loginForm
			.setSubjectwiseAttendanceTOList(StudentAttendanceSummaryHandler
			.getInstance().getActivityWiseAttendanceList(
			Integer.valueOf(studentid.trim())));
				
			
								
			} catch (Exception e) {		
			log.error("Error while getting the getIndividualStudentWiseActivityAttendanceSummary"+e.getMessage());
			String msg = super.handleApplicationException(e);
			loginForm.setErrorMessage(msg);
			loginForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.STUDENT_ERROR_PAGE);
		}
			
			
		//code ends
		log.info("lEAVING into getIndividualStudentWiseActivityAttendanceSummary of StudentWiseAttendanceSummaryAction");
		return mapping
				.findForward(CMSConstants.ACTIVITY_ATTENDANCE_SUMMARY);
	}*/
	
	
	//Comment the below function before uncommenting the above code.
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getIndividualStudentWiseActivityAttendanceSummary(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		StudentWiseAttendanceSummaryForm loginForm = (StudentWiseAttendanceSummaryForm) form;
		ActionMessages messages = new ActionMessages();
		HttpSession session = request.getSession();
		String studentid = (String) session.getAttribute("studentid");
		String courseId = (String) session.getAttribute("stuCourseId");
		loginForm
				.setSubjectwiseAttendanceTOList(StudentAttendanceSummaryHandler
						.getInstance().getStudentSubjectWiseAttendanceSubjectList(
								Integer.valueOf(studentid.trim()), loginForm, courseId,false));
		
		ActionMessage message = new ActionMessage("knowledgepro.show.attendance.message");
		messages.add("messages", message);
		messages.add("messages",new ActionMessage("knowledgepro.show.attendance.totalmessage"));
		saveMessages(request, messages);
		
		if(loginForm.isMarkPresent()){
			return mapping.findForward("getIndividualStudentWiseAttendanceSummary");
		}
		else{
			return mapping.findForward("getIndividualStudentWiseAttendanceSummary2");
		}
	}
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @returns activity absence period details
	 * @throws Exception
	 */
	public ActionForward getActivityAbsencePeriodDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering into getActivityAbsencePeriodDetails of StudentWiseAttendanceSummaryAction");
		StudentWiseAttendanceSummaryForm attendanceSummaryForm = (StudentWiseAttendanceSummaryForm) form;
		ActionErrors errors = new ActionErrors();
		try {
			attendanceSummaryForm.setSubjectAttendance(null);
			if (Integer.valueOf(attendanceSummaryForm.getClassesAbsent()) != 0) {
				List<PeriodTO> periodList = StudentWiseAttendanceSummaryHandler.getInstance().getActivityAbsencePeriodDetails(attendanceSummaryForm);
				attendanceSummaryForm.setPeriodList(periodList);
			} else {
				attendanceSummaryForm.setPeriodList(null);
				errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.ATTENDANCE_NO_RESULTS_FOUND));
				addErrors(request, errors);
			}
		} catch (Exception e) {		
			log.error("Error while getting the getActivityAbsencePeriodDetails"+e.getMessage());
			String msg = super.handleApplicationException(e);
			attendanceSummaryForm.setErrorMessage(msg);
			attendanceSummaryForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Leaving into getActivityAbsencePeriodDetails of StudentWiseAttendanceSummaryAction");
		return mapping.findForward(CMSConstants.ATTENDANCE_ABSENCE_PERIOD_DETAILS);	
	}
	
	private void validateAttendanceDate(StudentWiseAttendanceSummaryForm studentWiseAttendanceSummaryForm,
			ActionMessages errors) {
			if(studentWiseAttendanceSummaryForm.getStartDate()!=null && !StringUtils.isEmpty(studentWiseAttendanceSummaryForm.getStartDate())&& !CommonUtil.isValidDate(studentWiseAttendanceSummaryForm.getStartDate())){
					errors.add("errors",new ActionError(CMSConstants.ATTANDANCE_REPORT_STARTDATE_INVALID));
			}
			if(studentWiseAttendanceSummaryForm.getEndDate()!=null && !StringUtils.isEmpty(studentWiseAttendanceSummaryForm.getEndDate())&& !CommonUtil.isValidDate(studentWiseAttendanceSummaryForm.getEndDate())){
					errors.add("errors",new ActionError(CMSConstants.ATTANDANCE_REPORT_ENDDATE_INVALID));
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
	public ActionForward addPrincipalCommnents(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
			StudentWiseAttendanceSummaryForm attendanceSummaryForm = (StudentWiseAttendanceSummaryForm) form;
			try {
				attendanceSummaryForm.setPrinciCommnentId(0);
				attendanceSummaryForm.setPrincipalRemarks(null);
				attendanceSummaryForm.setStaffRemarkList(StudentWiseAttendanceSummaryHandler.getInstance().getStaffRemarks(attendanceSummaryForm));
				StudentWiseAttendanceSummaryHandler.getInstance().getPrincipalRemarks(attendanceSummaryForm);
				
			} catch (Exception e) {		
				log.error("Error while getting the addPrincipalCommnents"+e.getMessage());
				String msg = super.handleApplicationException(e);
				attendanceSummaryForm.setErrorMessage(msg);
				attendanceSummaryForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		
			return mapping.findForward("principalComments");	
	}
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 *            This will add new Admitted Through
	 * @return to mapping admitted through entry
	 * @throws Exception
	 */
	public ActionForward addPrincipalRemark(ActionMapping mapping, ActionForm form, HttpServletRequest request,
											HttpServletResponse response) throws Exception {

		log.debug("inside addPrincipalRemark Action");
		StudentWiseAttendanceSummaryForm attendanceSummaryForm = (StudentWiseAttendanceSummaryForm) form;
		ActionMessages messages = new ActionMessages();
		try {
			
			setUserId(request, attendanceSummaryForm); //setting user is to update last changed details
			StudentWiseAttendanceSummaryHandler.getInstance().addPrincipalComments(attendanceSummaryForm);

		} catch (Exception e) {
			log.error("error in final submit of addPrincipalRemark page...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				attendanceSummaryForm.setErrorMessage(msg);
				attendanceSummaryForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		/*
		if (isAdded) {
			// success .
			ActionMessage message = new ActionMessage("knowledgepro.admin.admthrough.addsuccess",admittedThroughForm.getAdmittedThrough());
			messages.add("messages", message);
			saveMessages(request, messages);
			admittedThroughForm.reset(mapping, request);
		} else {
			// failed
			errors.add("error", new ActionError("knowledgepro.admin.admthrough.addfailure",admittedThroughForm.getAdmittedThrough()));
			saveErrors(request, errors);
		}*/
		log.debug("Leaving addPrincipalRemark Action");
		return mapping.findForward(CMSConstants.GET_INDIVIDUAL_STUDENTATTENDANCE_ADMIN_RESULT);

	}
	
	
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @returns the absence information period details for the student
	 * @throws Exception
	 */
	public ActionForward getApprovedLeavePeriodDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering into getAbsencePeriodDetails of StudentWiseAttendanceSummaryAction");
		StudentWiseAttendanceSummaryForm attendanceSummaryForm = (StudentWiseAttendanceSummaryForm) form;
		ActionErrors errors = new ActionErrors();
		try {
			attendanceSummaryForm.setSubjectAttendance("Yes");
			if (Integer.valueOf(attendanceSummaryForm.getLeaveApproved()) != 0) {
				List<PeriodTO> periodList = StudentWiseAttendanceSummaryHandler.getInstance().getApprovedLeavePeriodDetails(attendanceSummaryForm);
				attendanceSummaryForm.setPeriodList(periodList);
			} else {
				attendanceSummaryForm.setPeriodList(null);
				errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.ATTENDANCE_NO_RESULTS_FOUND));
				addErrors(request, errors);
			}
		} catch (Exception e) {		
			log.error("Error while getting the AbsencePeriodDetails"+e.getMessage());
			String msg = super.handleApplicationException(e);
			attendanceSummaryForm.setErrorMessage(msg);
			attendanceSummaryForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Leaving into getApprovedLeavePeriodDetails of StudentWiseAttendanceSummaryAction");
		return mapping.findForward(CMSConstants.ATTENDANCE_ABSENCE_PERIOD_DETAILS);	
	}
	
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @returns the absence information period details for the student
	 * @throws Exception
	 */
	public ActionForward getStudentAbscentWithCocularLeave(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering into getStudentAbscentWithCocularLeave of StudentWiseAttendanceSummaryAction");
		StudentWiseAttendanceSummaryForm attendanceSummaryForm = (StudentWiseAttendanceSummaryForm) form;
		try {
			String studentId=request.getSession().getAttribute("studentid").toString();
			List<StudentAttendanceTO> attList=StudentWiseAttendanceSummaryHandler.getInstance().getAttendanceDataByStudent(studentId,attendanceSummaryForm);
			attendanceSummaryForm.setAttList(attList);
		} catch (Exception e) {		
			log.error("Error while getting the AbsencePeriodDetails"+e.getMessage());
			String msg = super.handleApplicationException(e);
			attendanceSummaryForm.setErrorMessage(msg);
			attendanceSummaryForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Leaving into getStudentAbscentWithCocularLeave of StudentWiseAttendanceSummaryAction");
		return mapping.findForward(CMSConstants.STUDENT_ABSENCE_PERIOD_DETAILS);	
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initPreviousStudentAttendanceSummeryChrist(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		StudentWiseAttendanceSummaryForm loginForm = (StudentWiseAttendanceSummaryForm) form;
		HttpSession session = request.getSession();
		String studentid = (String) session.getAttribute("studentid");
		loginForm.setClassesId(null);
		Map<Integer, String> classMap=StudentAttendanceSummaryHandler.getInstance().setPreviousClassId(studentid);
		loginForm.setClassMap(classMap);
		 return mapping.findForward("initPreviousStudentAttendanceSummery");
		}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getPreviousStudentWiseSubjectSummaryChrist(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		StudentWiseAttendanceSummaryForm loginForm = (StudentWiseAttendanceSummaryForm) form;
		ActionMessages messages = new ActionMessages();
		loginForm.setClassName(StudentAttendanceSummaryHandler.getInstance().getClassesName(loginForm.getClassesId()));
		HttpSession session = request.getSession();
		String studentid = (String) session.getAttribute("studentid");
		String courseId = (String) session.getAttribute("stuCourseId");
		loginForm.setStudentName((String)session.getAttribute("studentName"));
		loginForm.setRegNo((String)session.getAttribute("stuRegNo"));
		loginForm.setSubjectwiseAttendanceTOList(StudentAttendanceSummaryHandler
				.getInstance().getPreviousStudentWiseSubjectsAttendanceList(Integer.valueOf(studentid.trim()), loginForm, courseId));
		loginForm.setTotalConducted(loginForm.getTotalConducted().substring(0,loginForm.getTotalConducted().indexOf(".")));
		loginForm.setTotalPresent(loginForm.getTotalPresent().substring(0,loginForm.getTotalPresent().indexOf(".")));
		loginForm.setTotalAbscent(loginForm.getTotalAbscent().substring(0,loginForm.getTotalAbscent().indexOf(".")));
		ActionMessage message = new ActionMessage("knowledgepro.show.attendance.message");
		messages.add("messages", message);
		messages.add("messages",new ActionMessage("knowledgepro.show.attendance.totalmessage"));
		saveMessages(request, messages);
		
		if(loginForm.isMarkPresent()){
			return mapping.findForward("getPreviousStudentWiseAttendanceInfoSummary");
		}
		else{
			return mapping.findForward("getPreviousStudentWiseAttendanceInfoSummary2");
		}
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward printPreviousAttendance (ActionMapping mapping, ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception {
			return mapping.findForward("printPreviousAttendance");
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward printPreviousAttendance1 (ActionMapping mapping, ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception {
     		return mapping.findForward("printPreviousAttendance1");
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initPreviousStudentAttendanceSummeryCjc(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		StudentWiseAttendanceSummaryForm loginForm = (StudentWiseAttendanceSummaryForm) form;
		HttpSession session = request.getSession();
		String studentid = (String) session.getAttribute("studentid");
		loginForm.setClassesId(null);
		Map<Integer, String> classMap=StudentAttendanceSummaryHandler.getInstance().setPreviousClassId(studentid);
		loginForm.setClassMap(classMap);
		 return mapping.findForward("initPreviousStudentAttendanceSummery");
		}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getPreviousStudentWiseAttendanceSummaryCjc(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		StudentWiseAttendanceSummaryForm loginForm = (StudentWiseAttendanceSummaryForm) form;
		ActionMessages messages = new ActionMessages();
		HttpSession session = request.getSession();
		loginForm.setClassName(StudentAttendanceSummaryHandler.getInstance().getClassesName(loginForm.getClassesId()));
		String studentid = (String) session.getAttribute("studentid");
		String courseId = (String) session.getAttribute("stuCourseId");
		loginForm.setStudentName((String)session.getAttribute("studentName"));
		loginForm.setRegNo((String)session.getAttribute("stuRegNo"));
		loginForm.setSubjectwiseAttendanceTOList(StudentAttendanceSummaryHandler.getInstance().getPreviousStudentSubjectWiseAttendanceList(
								Integer.valueOf(studentid.trim()), loginForm, courseId));
		loginForm.setTotalConducted(loginForm.getTotalConducted().substring(0,loginForm.getTotalConducted().indexOf(".")));
		loginForm.setTotalPresent(loginForm.getTotalPresent().substring(0,loginForm.getTotalPresent().indexOf(".")));
		loginForm.setTotalAbscent(loginForm.getTotalAbscent().substring(0,loginForm.getTotalAbscent().indexOf(".")));
		ActionMessage message = new ActionMessage("knowledgepro.show.attendance.message");
		messages.add("messages", message);
		messages.add("messages",new ActionMessage("knowledgepro.show.attendance.totalmessage"));
		saveMessages(request, messages);
		
		if(loginForm.isMarkPresent()){
			return mapping.findForward("getPreviousStudentWiseAttendanceInfoSummary");
		}
		else{
			return mapping.findForward("getPreviousStudentWiseAttendanceInfoSummary2");
		}
	}
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @returns the absence information period details for the student
	 * @throws Exception
	 */
	public ActionForward getPreviousAbsencePeriodDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering into getAbsencePeriodDetails of StudentWiseAttendanceSummaryAction");
		StudentWiseAttendanceSummaryForm attendanceSummaryForm = (StudentWiseAttendanceSummaryForm) form;
		ActionErrors errors = new ActionErrors();
		try {
			attendanceSummaryForm.setSubjectAttendance("Yes");
			if (Integer.valueOf(attendanceSummaryForm.getClassesAbsent()) != 0) {
				List<PeriodTO> periodList = StudentWiseAttendanceSummaryHandler.getInstance().getPreviousAbsencePeriodDetails(attendanceSummaryForm);
				attendanceSummaryForm.setPeriodList(periodList);
			} else {
				attendanceSummaryForm.setPeriodList(null);
				errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.ATTENDANCE_NO_RESULTS_FOUND));
				addErrors(request, errors);
			}
		} catch (Exception e) {		
			log.error("Error while getting the AbsencePeriodDetails"+e.getMessage());
			String msg = super.handleApplicationException(e);
			attendanceSummaryForm.setErrorMessage(msg);
			attendanceSummaryForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Leaving into getAbsencePeriodDetails of StudentWiseAttendanceSummaryAction");
		return mapping.findForward(CMSConstants.ATTENDANCE_ABSENCE_PERIOD_DETAILS);	
	}
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @returns activity absence period details
	 * @throws Exception
	 */
	public ActionForward getPreviousActivityAbsencePeriodDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering into getActivityAbsencePeriodDetails of StudentWiseAttendanceSummaryAction");
		StudentWiseAttendanceSummaryForm attendanceSummaryForm = (StudentWiseAttendanceSummaryForm) form;
		ActionErrors errors = new ActionErrors();
		try {
			attendanceSummaryForm.setSubjectAttendance(null);
			if (Integer.valueOf(attendanceSummaryForm.getClassesAbsent()) != 0) {
				List<PeriodTO> periodList = StudentWiseAttendanceSummaryHandler.getInstance().getPreviousActivityAbsencePeriodDetails(attendanceSummaryForm);
				attendanceSummaryForm.setPeriodList(periodList);
			} else {
				attendanceSummaryForm.setPeriodList(null);
				errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.ATTENDANCE_NO_RESULTS_FOUND));
				addErrors(request, errors);
			}
		} catch (Exception e) {		
			log.error("Error while getting the getActivityAbsencePeriodDetails"+e.getMessage());
			String msg = super.handleApplicationException(e);
			attendanceSummaryForm.setErrorMessage(msg);
			attendanceSummaryForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Leaving into getActivityAbsencePeriodDetails of StudentWiseAttendanceSummaryAction");
		return mapping.findForward(CMSConstants.ATTENDANCE_ABSENCE_PERIOD_DETAILS);	
	}
	
	
	
	public ActionForward getIndividualSessionWiseSubjectAndActivityAttendanceSummary(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		StudentWiseAttendanceSummaryForm loginForm = (StudentWiseAttendanceSummaryForm) form;
		ActionMessages messages = new ActionMessages();
		HttpSession session = request.getSession();
		String studentid = (String) session.getAttribute("studentid");
		String courseId = (String) session.getAttribute("stuCourseId");
		StudentAttendanceSummaryHandler.getInstance().getStudentWiseSessionAttendanceList(Integer.valueOf(studentid.trim()), loginForm, courseId);
		/*loginForm
				.setSubjectwiseAttendanceTOList(StudentAttendanceSummaryHandler
						.getInstance().getStudentWiseSubjectsAttendanceList(
								Integer.valueOf(studentid.trim()), loginForm, courseId));*/
		ActionMessage message = new ActionMessage("knowledgepro.show.attendance.message");
		messages.add("messages", message);
		messages.add("messages",new ActionMessage("knowledgepro.show.attendance.totalmessage"));
		saveMessages(request, messages);
		
		/*if(loginForm.isMarkPresent()){
			return mapping.findForward("getIndividualStudentWiseAttendanceSummary");
		}
		else{*/
			return mapping.findForward("getIndividualStudentSessionWiseAttendanceSummary");
		//}
	}
	
	
	public ActionForward getAmAbsenceDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering into getAbsencePeriodDetails of StudentWiseAttendanceSummaryAction");
		StudentWiseAttendanceSummaryForm disciplinaryDetailsForm=(StudentWiseAttendanceSummaryForm)form;
		ActionErrors errors = new ActionErrors();
		try {
			//disciplinaryDetailsForm.setSubjectAttendance("Yes");
			//double absent=Double.valueOf(disciplinaryDetailsForm.getClassesAbsent());
			//double cocurricular=Double.valueOf(disciplinaryDetailsForm.getCocurricularLeave());
			
			if(disciplinaryDetailsForm.getAmList().size()==0){
			
				disciplinaryDetailsForm.setPeriodList(null);
				errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.ATTENDANCE_NO_RESULTS_FOUND));
				addErrors(request, errors);
			}
		} catch (Exception e) {		
			log.error("Error while getting the AbsencePeriodDetails"+e.getMessage());
			String msg = super.handleApplicationException(e);
			disciplinaryDetailsForm.setErrorMessage(msg);
			disciplinaryDetailsForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Leaving into getAbsencePeriodDetails of StudentWiseAttendanceSummaryAction");
		return mapping.findForward(CMSConstants.ATTENDANCE_ABSENCE_PERIOD_DETAILS_AM1);	
	}
	
	
	public ActionForward getPmAbsenceDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering into getAbsencePeriodDetails of StudentWiseAttendanceSummaryAction");
		StudentWiseAttendanceSummaryForm disciplinaryDetailsForm=(StudentWiseAttendanceSummaryForm)form;
		ActionErrors errors = new ActionErrors();
		try {
			//disciplinaryDetailsForm.setSubjectAttendance("Yes");
			//double absent=Double.valueOf(disciplinaryDetailsForm.getClassesAbsent());
			//double cocurricular=Double.valueOf(disciplinaryDetailsForm.getCocurricularLeave());
			
			if(disciplinaryDetailsForm.getPmList().size()==0){
				disciplinaryDetailsForm.setPeriodList(null);
				errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.ATTENDANCE_NO_RESULTS_FOUND));
				addErrors(request, errors);
			}
		} catch (Exception e) {		
			log.error("Error while getting the AbsencePeriodDetails"+e.getMessage());
			String msg = super.handleApplicationException(e);
			disciplinaryDetailsForm.setErrorMessage(msg);
			disciplinaryDetailsForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Leaving into getAbsencePeriodDetails of StudentWiseAttendanceSummaryAction");
		return mapping.findForward(CMSConstants.ATTENDANCE_ABSENCE_PERIOD_DETAILS_PM1);	
	}
	
	
	public ActionForward getTeacherNotTakenAttendanceSummary(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		StudentWiseAttendanceSummaryForm loginForm = (StudentWiseAttendanceSummaryForm) form;
		
		HttpSession session = request.getSession();
		String studentid = (String) session.getAttribute("studentid");
		String courseId = (String) session.getAttribute("stuCourseId");
		StudentAttendanceSummaryHandler.getInstance().getTeacherNotTakenAttendanceSummary(Integer.valueOf(studentid.trim()), loginForm, courseId);
	
			return mapping.findForward("getTeacherNotTakenAttendanceSummary");
		
	}
	
	
	public ActionForward initTeacherClassWiseAttendanceNotTakenSummary(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		StudentWiseAttendanceSummaryForm attendanceSummaryReportForm = (StudentWiseAttendanceSummaryForm) form;
		
		HttpSession session = request.getSession();
		attendanceSummaryReportForm.resetFields();
		setUserId(request, attendanceSummaryReportForm);
		if(CMSConstants.FACULTY_ID==Integer.parseInt(session.getAttribute("rid").toString())){
			attendanceSummaryReportForm.setIsFaculty(true);
			attendanceSummaryReportForm.setTeacherIds(new String[]{attendanceSummaryReportForm.getUserId()});
		}
		setRequiredDataToForm1(attendanceSummaryReportForm, request);
		
		return mapping.findForward("initTeacherClassWiseAttendanceNotTakenSummary");
		
	}
	
	public void setRequiredDataToForm1(
			StudentWiseAttendanceSummaryForm attendanceSummaryReportForm,
			HttpServletRequest request) throws Exception {
		
		
		String currentYear = null;
		if(attendanceSummaryReportForm.getAcademicYear()!=null && !attendanceSummaryReportForm.getAcademicYear().isEmpty()){
			currentYear=attendanceSummaryReportForm.getAcademicYear();
		}
		int year=CurrentAcademicYear.getInstance().getAcademicyear();
		if(currentYear==null){
			currentYear=year+"";
		}
		
		if(attendanceSummaryReportForm.getIsFaculty()){
		if(attendanceSummaryReportForm.getTeacherIds()!=null){
			Map<Integer, String> classMap = AttendanceEntryHandler.getInstance().getClassesByTeacher(attendanceSummaryReportForm.getTeacherIds(),currentYear);
			attendanceSummaryReportForm.setClassMap(classMap);
		}
		}else{
		Map<Integer,String> teachersMap =TeacherDepartmentEntryHandler.getInstance().getTeacherDepartmentsName();
		attendanceSummaryReportForm.setTeachersMap(teachersMap);
		Map<Integer, String> classMap = CommonAjaxHandler.getInstance().getClassesByYear(Integer.parseInt(currentYear));
		attendanceSummaryReportForm.setClassMap(classMap);	
		}
		
		
	}
	
	public ActionForward getTeacherClassWiseAttendanceNotTakenSummary(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		StudentWiseAttendanceSummaryForm attendanceSummaryReportForm = (StudentWiseAttendanceSummaryForm) form;
		
		/*if(!attendanceSummaryReportForm.getIsFaculty()){
			if(attendanceSummaryReportForm.getTeacherIds()!=null){
				Map<Integer, String> classMap = AttendanceEntryHandler.getInstance().getClassesByTeacher(attendanceSummaryReportForm.getTeacherIds(),attendanceSummaryReportForm.getAcademicYear());
				attendanceSummaryReportForm.setClassMap(classMap);
			}
		}*/
		
		StudentAttendanceSummaryHandler.getInstance().getTeacherClassWiseAttendanceNotTakenSummary(attendanceSummaryReportForm);
		
			return mapping.findForward("teacherClassWiseAttendanceNotTakenSummary");
		
	}
	
	
	
}
