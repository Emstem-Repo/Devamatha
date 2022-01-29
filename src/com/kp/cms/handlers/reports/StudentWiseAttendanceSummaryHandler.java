package com.kp.cms.handlers.reports;

import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;

import com.kp.cms.bo.admin.AttendanceStudent;
import com.kp.cms.bo.admin.PrincipalRemarks;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.StudentRemarks;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.reports.StudentWiseAttendanceSummaryForm;
import com.kp.cms.handlers.attendance.StudentAttendanceSummaryHandler;
import com.kp.cms.helpers.attendance.StudentAttendanceSummaryHelper;
import com.kp.cms.helpers.reports.StudentWiseAttendanceSummaryHelper;
import com.kp.cms.to.admin.StudentTO;
import com.kp.cms.to.admission.StudentRemarksTO;
import com.kp.cms.to.attendance.PeriodTO;
import com.kp.cms.to.reports.StudentWiseAttendanceSummaryReportTO;
import com.kp.cms.to.reports.StudentWiseSubjectSummaryTO;
import com.kp.cms.to.usermanagement.StudentAttendanceTO;
import com.kp.cms.transactions.exam.INewExamMarksEntryTransaction;
import com.kp.cms.transactions.reports.IStudentWiseAttendanceSummaryTransaction;
import com.kp.cms.transactionsimpl.exam.NewExamMarksEntryTransactionImpl;
import com.kp.cms.transactionsimpl.reports.StudentWiseAttendanceSummaryTransactionImpl;

@SuppressWarnings("deprecation")
public class StudentWiseAttendanceSummaryHandler {
	private static final Log log = LogFactory.getLog(StudentWiseAttendanceSummaryHandler.class);
	private static volatile StudentWiseAttendanceSummaryHandler studentWiseAttendanceSummaryHandler = null;
	
	private StudentWiseAttendanceSummaryHandler() {
		
	}
	
	public static StudentWiseAttendanceSummaryHandler getInstance() {
		if (studentWiseAttendanceSummaryHandler == null) {
			studentWiseAttendanceSummaryHandler = new StudentWiseAttendanceSummaryHandler();
		}
		return studentWiseAttendanceSummaryHandler;
	}
	
	/**
	 * Get the List of StudentWiseAttendanceSummaryReportTO objects.
	 * @param studentWiseAttendanceSummaryForm
	 * @return
	 * @throws ApplicationException
	 */
	public List<StudentWiseAttendanceSummaryReportTO> getStudentWiseAttendanceSummaryReportTOList(
			StudentWiseAttendanceSummaryForm studentWiseAttendanceSummaryForm)
			throws ApplicationException {
		log.info("Entering into getStudentWiseAttendanceSummaryReportTOList of StudentWiseAttendanceSummaryHandler");
		IStudentWiseAttendanceSummaryTransaction studentWiseAttendanceSummaryTransaction = new StudentWiseAttendanceSummaryTransactionImpl();

		List conductedClassesList = studentWiseAttendanceSummaryTransaction
				.getStudentWiseAttendanceSummaryInformation(StudentWiseAttendanceSummaryHelper
						.getInsatnce()
						.getAbsenceInformationSummaryQueryForConducted(
								studentWiseAttendanceSummaryForm));

		List classesPresentList = studentWiseAttendanceSummaryTransaction
				.getStudentWiseAttendanceSummaryInformation(StudentWiseAttendanceSummaryHelper
						.getInsatnce()
						.getAbsenceInformationSummaryQueryForPresent(
								studentWiseAttendanceSummaryForm));

		List classesOnLeave = studentWiseAttendanceSummaryTransaction
				.getStudentWiseAttendanceSummaryInformation(StudentWiseAttendanceSummaryHelper
						.getInsatnce()
						.getAbsenceInformationSummaryQueryForIsOnLeave(
								studentWiseAttendanceSummaryForm));

		Map<Integer, StudentWiseAttendanceSummaryReportTO> conductedClassesMap = StudentWiseAttendanceSummaryHelper
				.getInsatnce().convertBoToTo(conductedClassesList);

		Map<Integer, Map<Integer, StudentWiseSubjectSummaryTO>> classesPresentMap = StudentWiseAttendanceSummaryHelper
				.getInsatnce().convertFromListToMap(classesPresentList, true);
		Map<Integer, Map<Integer, StudentWiseSubjectSummaryTO>> classesOnLeaveMap = StudentWiseAttendanceSummaryHelper
				.getInsatnce().convertFromListToMap(classesOnLeave, false);

		List<StudentWiseAttendanceSummaryReportTO> studentWiseAttendanceSummaryReportTOList = StudentWiseAttendanceSummaryHelper
				.getInsatnce().convertToList(conductedClassesMap,
						classesPresentMap, classesOnLeaveMap);
		log.info("Leaving into getStudentWiseAttendanceSummaryReportTOList of StudentWiseAttendanceSummaryHandler");
		return studentWiseAttendanceSummaryReportTOList;

	}
	
	/***
	 * Used to get the the students attendance details. RollNo/Regd No. entered by Admin
	 */
	
	public void getStudentWiseAttendanceSummaryByAdmin(StudentWiseAttendanceSummaryForm attendanceSummaryForm, ActionErrors errors,int studentID)throws Exception{
		log.info("Entering into getStudentWiseAttendanceSummaryByAdmin of StudentWiseAttendanceSummaryHandler");
		IStudentWiseAttendanceSummaryTransaction transaction = new StudentWiseAttendanceSummaryTransactionImpl();
//		String regdNo = attendanceSummaryForm.getStartRegisterNo().trim();
		Student student = transaction.getStudentByRegdRollNo(studentID);
//		if(studentID != 0){
			List<StudentWiseSubjectSummaryTO> summaryList = StudentAttendanceSummaryHandler
			.getInstance().getSubjectWiseAttendanceList(studentID);
			if(summaryList!=null && !summaryList.isEmpty()){
				Map<Integer, Integer> orderMap=null;
				// added for sorting subjects by subjectOrder
				int courseId=0;
				int semNo=0;
				int semAcademicYear=0;
				if(student!=null && student.getAdmAppln().getCourseBySelectedCourseId()!=null)
				 courseId=student.getAdmAppln().getCourseBySelectedCourseId().getId();
				if(student.getClassSchemewise()!=null && student.getClassSchemewise().getClasses()!=null)
					semNo=student.getClassSchemewise().getClasses().getTermNumber();
				if(student.getClassSchemewise()!=null && student.getClassSchemewise().getCurriculumSchemeDuration()!=null)
					semAcademicYear=student.getClassSchemewise().getCurriculumSchemeDuration().getAcademicYear();
				
				IStudentWiseAttendanceSummaryTransaction studentWiseAttendanceSummaryTransaction = new StudentWiseAttendanceSummaryTransactionImpl();
				 orderMap = studentWiseAttendanceSummaryTransaction.getSubjectOrder(courseId,semNo,semAcademicYear);
				
				Iterator<StudentWiseSubjectSummaryTO> itr=summaryList.iterator();
				while (itr.hasNext()) {
					StudentWiseSubjectSummaryTO studentWiseSubjectSummaryTO = (StudentWiseSubjectSummaryTO) itr.next();
					if(studentWiseSubjectSummaryTO.getSubjectID()!=null && orderMap.get(Integer.parseInt(studentWiseSubjectSummaryTO.getSubjectID()))!= null){
						studentWiseSubjectSummaryTO.setOrder(orderMap.get(Integer.parseInt(studentWiseSubjectSummaryTO.getSubjectID())));
					}
				}
				Collections.sort(summaryList);
				attendanceSummaryForm.setSubjectwiseAttendanceTOList(summaryList);
				
				if(student!=null){
					attendanceSummaryForm.setStudent(student);
				}
			}
			else{
				errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.ATTENDANCE_NO_RESULTS_FOUND));
			}
//		}
//		else{
//			errors.add(CMSConstants.ERRORS, new ActionError("knowledgepro.admission.noresultsfound"));
//		}
			log.info("Leaving into getStudentWiseAttendanceSummaryByAdmin of StudentWiseAttendanceSummaryHandler");
	}
	
	public void getStudentWiseAttendanceSummaryByName(StudentWiseAttendanceSummaryForm attendanceSummaryForm, ActionErrors errors)throws Exception{
		log.info("Entering into getStudentWiseAttendanceSummaryByName of StudentWiseAttendanceSummaryHandler");
		IStudentWiseAttendanceSummaryTransaction transaction = new StudentWiseAttendanceSummaryTransactionImpl();	
		String dynamicQuery = StudentAttendanceSummaryHelper.getSelectionSearchCriteria(attendanceSummaryForm);
		List<Object[]> studentList = transaction.getStudentBySearch(dynamicQuery);
		if(studentList!=null && !studentList.isEmpty()){
			List<StudentTO> studentTOList = StudentAttendanceSummaryHelper.populateTOList(studentList);
			attendanceSummaryForm.setStudentTOList(studentTOList);
		}else{
			errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.ATTENDANCE_NO_RESULTS_FOUND));
		}
		log.info("Leaving into getStudentWiseAttendanceSummaryByName of StudentWiseAttendanceSummaryHandler");
	}
	
	/**
	 * 
	 * @param attendanceSummaryForm
	 * @throws Exception 
	 * @returns the student absence period details information
	 */
	public List<PeriodTO> getAbsencePeriodDetails(
			StudentWiseAttendanceSummaryForm attendanceSummaryForm) throws Exception {
		log.info("Entering into getAbsencePeriodDetails of StudentWiseAttendanceSummaryHandler");
		boolean isSubjectAttendance = true;
		IStudentWiseAttendanceSummaryTransaction transaction = new StudentWiseAttendanceSummaryTransactionImpl();
		String absenceSearchCriteria ="";
		List<AttendanceStudent> attendanceStudentList =null;
		List<Integer> periodList=null;
	    absenceSearchCriteria = StudentAttendanceSummaryHelper.getInstance().getAbsenceSearchCriteria(attendanceSummaryForm);
	    attendanceStudentList = transaction.getAbsencePeriodDetails(absenceSearchCriteria);
		periodList=transaction.getPeriodList(attendanceSummaryForm);
		log.info("Leaving into getAbsencePeriodDetails of StudentWiseAttendanceSummaryHandler");
		return StudentAttendanceSummaryHelper.getInstance().populateAbsencePeriodInformations(attendanceStudentList, attendanceSummaryForm, isSubjectAttendance,periodList);
	}
	/**
	 * 
	 * @param attendanceSummaryForm
	 * @returns activity absence period details
	 */
	public List<PeriodTO> getActivityAbsencePeriodDetails(
			StudentWiseAttendanceSummaryForm attendanceSummaryForm) throws Exception{
		log.info("Entering into getActivityAbsencePeriodDetails of StudentWiseAttendanceSummaryHandler");
		boolean isSubjectAttendance = false;
		IStudentWiseAttendanceSummaryTransaction transaction = new StudentWiseAttendanceSummaryTransactionImpl();
		String absenceSearchCriteria = StudentAttendanceSummaryHelper.getInstance().getActivityAbsenceSearchCriteria(attendanceSummaryForm);
		List<AttendanceStudent> attendanceStudentList = transaction.getAbsencePeriodDetails(absenceSearchCriteria);
		log.info("Leaving into getActivityAbsencePeriodDetails of StudentWiseAttendanceSummaryHandler");
		List<Integer> periodList=transaction.getPeriodList(attendanceSummaryForm);
		return StudentAttendanceSummaryHelper.getInstance().populateAbsencePeriodInformations(attendanceStudentList, attendanceSummaryForm, isSubjectAttendance,periodList);
	}
	
	public List<StudentRemarksTO> getStaffRemarks(
			StudentWiseAttendanceSummaryForm attendanceSummaryForm) throws Exception{
		log.info("Entering into getStaffRemarks of StudentWiseAttendanceSummaryHandler");
		IStudentWiseAttendanceSummaryTransaction transaction = new StudentWiseAttendanceSummaryTransactionImpl();
		List<StudentRemarks> staffRemarkList = transaction.getStaffRemarks(Integer.parseInt(attendanceSummaryForm.getStudentID()));
		List<StudentRemarksTO> remarkTos =  StudentAttendanceSummaryHelper.getInstance().convertRemarkBoToTO(staffRemarkList);
		log.info("Leaving into getActivityAbsencePeriodDetails of StudentWiseAttendanceSummaryHandler");
		return remarkTos;
	}
	/**
	 * 
	 * @param attendanceSummaryForm
	 * @return
	 * @throws Exception
	 */
	public boolean addPrincipalComments(StudentWiseAttendanceSummaryForm attendanceSummaryForm) throws Exception{
		log.info("Entering into addPrincipalComments of StudentWiseAttendanceSummaryHandler");
		IStudentWiseAttendanceSummaryTransaction transaction = new StudentWiseAttendanceSummaryTransactionImpl();
		PrincipalRemarks principalRemarks = new PrincipalRemarks();
		Student student = new Student();
		student.setId(Integer.parseInt(attendanceSummaryForm.getStudentID()));
		principalRemarks.setStudent(student);
		principalRemarks.setComments(attendanceSummaryForm.getPrincipalRemarks());
		principalRemarks.setId(attendanceSummaryForm.getPrinciCommnentId());
		principalRemarks.setCreatedBy(attendanceSummaryForm.getUserId());
		principalRemarks.setModifiedBy(attendanceSummaryForm.getUserId());
		principalRemarks.setCreatedDate(new Date());
		principalRemarks.setLastModifiedDate(new Date());
		principalRemarks.setIsActive(true);
		return transaction.addPrincipalRemarks(principalRemarks);
	}
	/**
	 * 
	 * @param attendanceSummaryForm
	 * @throws Exception
	 */
	public void getPrincipalRemarks(StudentWiseAttendanceSummaryForm attendanceSummaryForm) throws Exception{
		log.info("Entering into getStaffRemarks of StudentWiseAttendanceSummaryHandler");
		IStudentWiseAttendanceSummaryTransaction transaction = new StudentWiseAttendanceSummaryTransactionImpl();
		PrincipalRemarks principalRemarks = transaction.getPricipalRemarks(Integer.parseInt(attendanceSummaryForm.getStudentID()));
		if(principalRemarks!= null && principalRemarks.getComments()!= null){
			attendanceSummaryForm.setPrincipalRemarks(principalRemarks.getComments());
			attendanceSummaryForm.setPrinciCommnentId(principalRemarks.getId());
		}
		log.info("Leaving into getPrincipalRemarks of StudentWiseAttendanceSummaryHandler");
	}

	/**
	 * 
	 * @param attendanceSummaryForm
	 * @throws Exception 
	 * @returns the student absence period details information
	 */
	public List<PeriodTO> getApprovedLeavePeriodDetails(
			StudentWiseAttendanceSummaryForm attendanceSummaryForm) throws Exception {
		log.info("Entering into getApprovedLeavePeriodDetails of StudentWiseAttendanceSummaryHandler");
		boolean isSubjectAttendance = true;
		IStudentWiseAttendanceSummaryTransaction transaction = new StudentWiseAttendanceSummaryTransactionImpl();
		String approvedLeaveSearchCriteria = StudentAttendanceSummaryHelper.getInstance().getApprovedLeaveSearchCriteria(attendanceSummaryForm);
		List<AttendanceStudent> attendanceStudentList = transaction.getAbsencePeriodDetails(approvedLeaveSearchCriteria);
		List<Integer> periodList=transaction.getPeriodList(attendanceSummaryForm);
		log.info("Leaving into getAbsencePeriodDetails of StudentWiseAttendanceSummaryHandler");
		return StudentAttendanceSummaryHelper.getInstance().populateAbsencePeriodInformations(attendanceStudentList, attendanceSummaryForm, isSubjectAttendance,periodList);
	}

	/**
	 * @param studentId
	 * @return
	 * @throws Exception
	 */
	public List<StudentAttendanceTO> getAttendanceDataByStudent(String studentId,StudentWiseAttendanceSummaryForm attendanceSummaryForm) throws Exception {
		String query=StudentWiseAttendanceSummaryHelper.getInsatnce().getAttendanceDataByStudentQuery(studentId);
		INewExamMarksEntryTransaction transaction=NewExamMarksEntryTransactionImpl.getInstance();
		List<AttendanceStudent> attList=transaction.getDataForQuery(query);
		String periodQuery=StudentWiseAttendanceSummaryHelper.getInsatnce().getPeriodsForStudent(studentId);
		List<String> periodList=transaction.getDataForQuery(periodQuery);
		Map<String,Integer> posMap=StudentWiseAttendanceSummaryHelper.getInsatnce().getPositionsForPeriod(periodList);
		attendanceSummaryForm.setPeriodNameList(periodList);
		return StudentWiseAttendanceSummaryHelper.getInsatnce().convertAttendanceStudentBotoTo(attList,posMap,studentId,attendanceSummaryForm);
	}
	/**
	 * @param attendanceSummaryForm
	 * @return
	 * @throws Exception
	 */
	public List<PeriodTO> getPreviousAbsencePeriodDetails(StudentWiseAttendanceSummaryForm attendanceSummaryForm) throws Exception{
		log.info("Entering into getAbsencePeriodDetails of StudentWiseAttendanceSummaryHandler");
		boolean isSubjectAttendance = true;
		IStudentWiseAttendanceSummaryTransaction transaction = new StudentWiseAttendanceSummaryTransactionImpl();
		String absenceSearchCriteria ="";
		List<AttendanceStudent> attendanceStudentList =null;
		List<String> periodList=null;
		absenceSearchCriteria = StudentAttendanceSummaryHelper.getInstance().getPreviousAbsenceSearchCriteria(attendanceSummaryForm);
		attendanceStudentList = transaction.getAbsencePeriodDetails(absenceSearchCriteria);
	     periodList=transaction.getPreviousPeriodList(attendanceSummaryForm);
		log.info("Leaving into getAbsencePeriodDetails of StudentWiseAttendanceSummaryHandler");
		return StudentAttendanceSummaryHelper.getInstance().PreviousAbsencePeriodInformations(attendanceStudentList, attendanceSummaryForm, isSubjectAttendance,periodList);
	}

	public List<PeriodTO> getPreviousActivityAbsencePeriodDetails(StudentWiseAttendanceSummaryForm attendanceSummaryForm) throws Exception{
		log.info("Entering into getActivityAbsencePeriodDetails of StudentWiseAttendanceSummaryHandler");
		boolean isSubjectAttendance = false;
		IStudentWiseAttendanceSummaryTransaction transaction = new StudentWiseAttendanceSummaryTransactionImpl();
		String absenceSearchCriteria = StudentAttendanceSummaryHelper.getInstance().getPreviousActivityAbsenceSearchCriteria(attendanceSummaryForm);
		List<AttendanceStudent> attendanceStudentList = transaction.getAbsencePeriodDetails(absenceSearchCriteria);
		log.info("Leaving into getActivityAbsencePeriodDetails of StudentWiseAttendanceSummaryHandler");
		List<String> periodList=transaction.getPreviousPeriodList(attendanceSummaryForm);
		return StudentAttendanceSummaryHelper.getInstance().PreviousAbsencePeriodInformations(attendanceStudentList, attendanceSummaryForm, isSubjectAttendance,periodList);
	}

}
