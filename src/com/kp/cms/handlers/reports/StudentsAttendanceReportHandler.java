package com.kp.cms.handlers.reports;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.AttendanceStudent;
import com.kp.cms.forms.reports.StudentsAttendanceReportForm;
import com.kp.cms.helpers.reports.StudentsAttendanceReportHelper;
import com.kp.cms.to.reports.StudentsAttendanceReportTO;
import com.kp.cms.transactions.reports.IStudentAttendanceReportTransaction;
import com.kp.cms.transactionsimpl.reports.StudentsAttendanceReportTransactionImpl;

public class StudentsAttendanceReportHandler {
	
	private static final Log log = LogFactory.getLog(StudentsAttendanceReportHandler.class);
	
	private static volatile StudentsAttendanceReportHandler studentsAttendanceReportHandler= null;
	
	private StudentsAttendanceReportHandler(){
	}
	
	/**
	 * This method returns the single instance of this StudentsAttendanceReportHandler.
	 * @return
	 */
	public static StudentsAttendanceReportHandler getInstance() {
      if(studentsAttendanceReportHandler == null) {
    	  studentsAttendanceReportHandler = new StudentsAttendanceReportHandler();
      }
      return studentsAttendanceReportHandler;
	}
	
	/**
	 * 
	 * @param studentsAttendanceReportForm
	 * @return
	 * @throws Exception
	 */
	public Map<String, List<StudentsAttendanceReportTO>> getStudentsAttendanceResults(
			StudentsAttendanceReportForm studentsAttendanceReportForm) throws Exception {
		log.info("Entered getStudentsAttendanceResults");
		IStudentAttendanceReportTransaction studentsAttendanceSummary = new StudentsAttendanceReportTransactionImpl();
		StudentsAttendanceReportHelper studentsAttendanceReportHelper = new StudentsAttendanceReportHelper();
		List<AttendanceStudent> classesAttendedWithLeaveList = null;
		List<AttendanceStudent> classesAttendedWithCoCurricualLeave=null;
		List<AttendanceStudent> classesHeldList = studentsAttendanceSummary.getStudentsAttendance(studentsAttendanceReportHelper.getClassesHeld(studentsAttendanceReportForm));
		List<AttendanceStudent> classesAttendedList = studentsAttendanceSummary.getStudentsAttendance(studentsAttendanceReportHelper.getClassesAttended(studentsAttendanceReportForm));
		if(studentsAttendanceReportForm.getLeaveType()){
			classesAttendedWithLeaveList = studentsAttendanceSummary.getStudentsAttendance(studentsAttendanceReportHelper.getClassesAttendedWithLeave(studentsAttendanceReportForm));
		}
		if(studentsAttendanceReportForm.getCoCurricularLeave()){
			classesAttendedWithCoCurricualLeave = studentsAttendanceSummary.getStudentsAttendance(studentsAttendanceReportHelper.getClassesAttendedWithCoCurricalLeave(studentsAttendanceReportForm));
		}
		List detainedStudentList = studentsAttendanceSummary.getDetainedOrDiscontinuedStudents();
		Map<String, List<StudentsAttendanceReportTO>> studentsAttendanceResultMap = studentsAttendanceReportHelper.convertBoToTo(classesHeldList, classesAttendedList, classesAttendedWithLeaveList, studentsAttendanceReportForm.getFromPercentage(), studentsAttendanceReportForm.getToPercentage(),classesAttendedWithCoCurricualLeave,detainedStudentList);
		log.info("Exit getStudentsAttendanceResults");
		return studentsAttendanceResultMap;
	}
	
	/**
	 * 
	 * @param studentsAttendanceReportForm
	 * @return
	 * @throws Exception
	 */
	public Map<String, List<StudentsAttendanceReportTO>> getMonthlyStudentsAttendanceResults(
			StudentsAttendanceReportForm studentsAttendanceReportForm) throws Exception {
		log.info("Entered getStudentsAttendanceResults");
		IStudentAttendanceReportTransaction studentsAttendanceSummary = new StudentsAttendanceReportTransactionImpl();
		StudentsAttendanceReportHelper studentsAttendanceReportHelper = new StudentsAttendanceReportHelper();
		
		List<AttendanceStudent> classesHeldAttendedList = studentsAttendanceSummary.getStudentsAttendance(studentsAttendanceReportHelper.getClassesHeldAttended(studentsAttendanceReportForm));
		Map<String, List<StudentsAttendanceReportTO>> studentsAttendanceResultMap = studentsAttendanceReportHelper.convertMonthlyBoToTo(classesHeldAttendedList, studentsAttendanceReportForm.getFromPercentage(), studentsAttendanceReportForm.getToPercentage());
		log.info("Exit getStudentsAttendanceResults");
		return studentsAttendanceResultMap;
	}
	/**
	 * @param attendanceReportTOList
	 * @param request
	 * @throws Exception
	 * added by mehaboob
	 */
	public void convertToToExcel(List<StudentsAttendanceReportTO> attendanceReportTOList,HttpServletRequest request) throws Exception{
		StudentsAttendanceReportHelper studentsAttendanceReportHelper = new StudentsAttendanceReportHelper();
		studentsAttendanceReportHelper.convertToToExcel(attendanceReportTOList,request);
	}
	
}
