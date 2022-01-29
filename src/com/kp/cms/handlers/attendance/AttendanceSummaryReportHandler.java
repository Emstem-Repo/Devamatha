package com.kp.cms.handlers.attendance;

import java.util.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Subject;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.attendance.AttendanceSummaryReportForm;
import com.kp.cms.helpers.attendance.AttendanceSummaryReportHelper;
import com.kp.cms.to.attendance.AttendanceSummaryReportTO;
import com.kp.cms.to.attendance.MonthlyAttendanceSummaryTO;
import com.kp.cms.transactions.ajax.ICommonAjax;
import com.kp.cms.transactions.attandance.IAttendanceSummaryReportTxnImpl;
import com.kp.cms.transactions.attandance.IAttendanceTeacherReportTxn;
import com.kp.cms.transactionsimpl.ajax.CommonAjaxImpl;
import com.kp.cms.transactionsimpl.attendance.AttendanceSummaryReportTxnImpl;
import com.kp.cms.transactionsimpl.attendance.AttendanceTeacherReportTxn;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.CurrentAcademicYear;

/**
 * @author kalyan.c
 *
 */
public class AttendanceSummaryReportHandler {
	private static final Log log = LogFactory.getLog(AttendanceSummaryReportHandler.class);
	public static volatile AttendanceSummaryReportHandler self=null;
	/**
	 * @return
	 * This method gives instance of this method
	 */
	public static AttendanceSummaryReportHandler getInstance(){
		if(self==null){
			self= new AttendanceSummaryReportHandler();}
		return self;
	}
	/**
	 * 
	 */
	private AttendanceSummaryReportHandler(){
		
	}

	/**
	 * @param attendanceSummaryReportForm
	 * @return
	 * @throws Exception
	 * This method will give the candidates based on search criteria
	 */
	public Map<Integer, List<MonthlyAttendanceSummaryTO>> getMonthlyStudentAttendanceResults(
			AttendanceSummaryReportForm attendanceSummaryReportForm) throws Exception {
		log.info("entered getMonthlyStudentAttendanceResults..");
		IAttendanceSummaryReportTxnImpl attendanceSummary = AttendanceSummaryReportTxnImpl.getInstance(); 
		
		List attendanceResults = attendanceSummary.getStudentAttendance(AttendanceSummaryReportHelper.getMonthlySelectionSearchCriteria(attendanceSummaryReportForm));
		Map<Integer, List<MonthlyAttendanceSummaryTO>> studentSearchTo = AttendanceSummaryReportHelper.convertMonthlyBoToTo(attendanceResults,attendanceSummaryReportForm);
		log.info("exit getMonthlyStudentAttendanceResults..");
		return studentSearchTo;
	}
	
	/**
	 * Gets subject from curriculumscheme
	 * @throws ApplicationException 
	 */
	public Map<Integer,Subject> getSubjectByCourseIdTermYear(int courseId,int year,int term) throws ApplicationException
	{
		IAttendanceSummaryReportTxnImpl attendanceSummary = AttendanceSummaryReportTxnImpl.getInstance(); 
		
		return attendanceSummary.getSubjectByCourseIdTermYear(courseId, year,term);
	}

	
	
	/**
	 * @param attendanceSummaryReportForm
	 * @return
	 * @throws Exception
	 * This method will give the candidates based on search criteria
	 */
	@SuppressWarnings("unchecked")
	public Map<Integer, List<AttendanceSummaryReportTO>> getStudentAttendanceResults(
			AttendanceSummaryReportForm attendanceSummaryReportForm) throws Exception {
		log.info("entered getStudentAttendanceResults..");
		IAttendanceSummaryReportTxnImpl attendanceSummary = AttendanceSummaryReportTxnImpl.getInstance(); 
		String attendanceQuery = AttendanceSummaryReportHelper.getSelectionSearchCriteria(attendanceSummaryReportForm);
		List attendanceResults = attendanceSummary.getStudentAttendance(attendanceQuery);
		List detainedStudentList = attendanceSummary.getDetainedOrDiscontinuedStudents();
		Map<Integer, List<AttendanceSummaryReportTO>> studentSearchTo = AttendanceSummaryReportHelper.convertBoToTo(attendanceResults,attendanceSummaryReportForm,detainedStudentList);
		log.info("exit getStudentAttendanceResults..");
		return studentSearchTo;
	}
	@SuppressWarnings("unchecked")
	public Map<Integer, List<AttendanceSummaryReportTO>> getTeacherViewAttendance(
			AttendanceSummaryReportForm attendanceSummaryReportForm) throws Exception {
		log.info("entered getStudentAttendanceResults..");
		IAttendanceSummaryReportTxnImpl attendanceSummary = AttendanceSummaryReportTxnImpl.getInstance(); 
		List attendanceType=attendanceSummary.getAttendanceType();
		List classesList=AttendanceSummaryReportHelper.ConvertStrToList(attendanceSummaryReportForm);
		int year=CurrentAcademicYear.getInstance().getAcademicyear();
		attendanceSummaryReportForm.setAcademicYear(String.valueOf(year));
		IAttendanceTeacherReportTxn txn=AttendanceTeacherReportTxn.getInstance();
		Date startDate=txn.getStartDate(classesList,year);
		String attendanceQuery = AttendanceSummaryReportHelper.getSelectionSearchCriteriaForTeacherView(attendanceSummaryReportForm,attendanceType,startDate.toString());
		List attendanceResults = attendanceSummary.getStudentAttendance(attendanceQuery);
		List detainedStudentList = attendanceSummary.getDetainedOrDiscontinuedStudents();
		attendanceSummaryReportForm.setCoCurricularLeave(true);
		Map<Integer, List<AttendanceSummaryReportTO>> studentSearchTo = AttendanceSummaryReportHelper.convertBoToTo(attendanceResults,attendanceSummaryReportForm,detainedStudentList);
 		log.info("exit getStudentAttendanceResults..");
		return studentSearchTo;
	}
	public Map<Integer, String> getClassByYearUserId(int year, AttendanceSummaryReportForm attendanceSummaryReportForm) {
		IAttendanceSummaryReportTxnImpl attendanceSummary = AttendanceSummaryReportTxnImpl.getInstance(); 
		Map<Integer, String> classMap = attendanceSummary.getClassByYearUserId(year,attendanceSummaryReportForm);
		classMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(classMap);
		return classMap;
	}
	
	public Map<Integer, String> getSubjectByCourseTermYear(int courseId,int year, int term, AttendanceSummaryReportForm attendanceSummaryReportForm) throws ApplicationException {
		IAttendanceSummaryReportTxnImpl attendanceSummary = AttendanceSummaryReportTxnImpl.getInstance(); 
		Map<Integer, String> SubjectByCourseIdTermYearMap = attendanceSummary.getSubjectByCourseTermYear(courseId, year, term, attendanceSummaryReportForm);
		SubjectByCourseIdTermYearMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(SubjectByCourseIdTermYearMap);
        return SubjectByCourseIdTermYearMap;
	}
	
	public Map<Integer, String> getSubjectByClassIDs(Set<Integer> classesIdsSet) throws ApplicationException {
		IAttendanceSummaryReportTxnImpl attendanceSummary = AttendanceSummaryReportTxnImpl.getInstance(); 
		Map<Integer, String> SubjectByCourseIdTermYearMap = attendanceSummary.getSubjectByClass(classesIdsSet);
		if(SubjectByCourseIdTermYearMap!=null && !SubjectByCourseIdTermYearMap.isEmpty())
		SubjectByCourseIdTermYearMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(SubjectByCourseIdTermYearMap);
        return SubjectByCourseIdTermYearMap;
	}
	
}
