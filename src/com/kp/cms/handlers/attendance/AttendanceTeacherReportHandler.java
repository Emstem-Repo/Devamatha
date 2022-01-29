package com.kp.cms.handlers.attendance;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.forms.attendance.AttendanceTeacherReportForm;
import com.kp.cms.helpers.attendance.AttendanceTeacherReportHelper;
import com.kp.cms.to.admin.StudentTO;
import com.kp.cms.to.attendance.AttendanceTeacherReportTO;
import com.kp.cms.to.attendance.PeriodTO;
import com.kp.cms.to.attendance.StudentAbsentDetailsTO;
import com.kp.cms.transactions.attandance.IAttendanceTeacherReportTxn;
import com.kp.cms.transactionsimpl.attendance.AttendanceTeacherReportTxn;
import com.kp.cms.utilities.CurrentAcademicYear;

public class AttendanceTeacherReportHandler {
	private static final Log log = LogFactory.getLog(AttendanceTeacherReportHandler.class);
	public static volatile AttendanceTeacherReportHandler self=null;
	/**
	 * @return
	 * This method gives instance of this method
	 */
	public static AttendanceTeacherReportHandler getInstance(){
		if(self==null){
			self= new AttendanceTeacherReportHandler();}
		return self;
	}
	/**
	 * 
	 */
	private AttendanceTeacherReportHandler(){
		
	}

	/**
	 * @param attendanceSummaryReportForm
	 * @return
	 * @throws Exception
	 * This method will give the Teachers details based on the selection criteria
	 */
	public List<AttendanceTeacherReportTO> getTeacherAttendanceResults(
			AttendanceTeacherReportForm attendanceTeacherReportForm) throws Exception {
		log.info("entered getTeacherAttendanceResults..");
//		IAttendanceSummaryReportTxnImpl attendanceSummary = AttendanceSummaryReportTxnImpl.getInstance(); 
		IAttendanceTeacherReportTxn attendanceSummary = AttendanceTeacherReportTxn.getInstance();
		List attendanceResults = attendanceSummary.getTeacherAttendance(AttendanceTeacherReportHelper.getSelectionSearchCriteria(attendanceTeacherReportForm));
		
		List<AttendanceTeacherReportTO> teacherSearchTo = AttendanceTeacherReportHelper.convertBoToTo(attendanceResults,attendanceTeacherReportForm);
		log.info("exit getTeacherAttendanceResults..");
		return teacherSearchTo;
	}
	/**
	 * @param attendanceSummaryReportForm
	 * @return
	 * @throws Exception
	 * This method will give the Teachers details based on the selection criteria
	 */
	public List<AttendanceTeacherReportTO> getMonthlyTeacherAttendanceResults(
			AttendanceTeacherReportForm attendanceTeacherReportForm) throws Exception {
		log.info("entered getMonthlyTeacherAttendanceResults..");
//		IAttendanceSummaryReportTxnImpl attendanceSummary = AttendanceSummaryReportTxnImpl.getInstance(); 
		IAttendanceTeacherReportTxn attendanceSummary = AttendanceTeacherReportTxn.getInstance();
		List attendanceResults = attendanceSummary.getTeacherAttendance(AttendanceTeacherReportHelper.getSelectionMonthlyCriteria(attendanceTeacherReportForm));
		
		List<AttendanceTeacherReportTO> teacherSearchTo = AttendanceTeacherReportHelper.convertMonthlyBoToTo(attendanceResults,attendanceTeacherReportForm);
		log.info("exit getMonthlyTeacherAttendanceResults..");
		return teacherSearchTo;
	}
	
	/**
	 * 
	 * @param attendanceTeacherReportForm
	 * @return
	 * @throws Exception
	 */
	public List<PeriodTO> getAttendanceTeacherPeriodDetails(
			AttendanceTeacherReportForm attendanceTeacherReportForm) throws Exception {
		log.info("Entering into getAttendanceTeacherPeriodDetails");
		IAttendanceTeacherReportTxn attendanceSummary = AttendanceTeacherReportTxn.getInstance();
		List periodList=null;
		if(attendanceTeacherReportForm.getSubjectId()!=null && !attendanceTeacherReportForm.getSubjectId().isEmpty()){
			periodList= attendanceSummary.getPeriodDetails(Integer.parseInt(attendanceTeacherReportForm.getUserId()), Integer.parseInt(attendanceTeacherReportForm.getClassId()), Integer.parseInt(attendanceTeacherReportForm.getSubjectId()),
				attendanceTeacherReportForm.getStartDate(), attendanceTeacherReportForm.getEndDate(),attendanceTeacherReportForm.getBatchName());
		}else{
			periodList= attendanceSummary.getActivityPeriodDetails(Integer.parseInt(attendanceTeacherReportForm.getUserId()), Integer.parseInt(attendanceTeacherReportForm.getClassId()), 
					attendanceTeacherReportForm.getStartDate(), attendanceTeacherReportForm.getEndDate());
		}
		List<PeriodTO> periodTOList=AttendanceTeacherReportHelper.createPeriodTOs(periodList,attendanceTeacherReportForm.getClassId());
		
		log.info("Leaving into getAttendanceTeacherPeriodDetails");
		return periodTOList;
	}
	public List<AttendanceTeacherReportTO> getAttendanceTeacherSummaryReports(
			AttendanceTeacherReportForm attendanceTeacherReportForm) throws Exception {
		log.info("entered getTeacherAttendanceResults..");
//		IAttendanceSummaryReportTxnImpl attendanceSummary = AttendanceSummaryReportTxnImpl.getInstance(); 
		IAttendanceTeacherReportTxn attendanceSummary = AttendanceTeacherReportTxn.getInstance();
		List<AttendanceTeacherReportTO> teacherSearchTo; 
		Date startDate=null;
		List attendanceResults=null;
		String userName="";
		CurrentAcademicYear year=CurrentAcademicYear.getInstance();
		int academicYear=year.getAcademicyear();
		List<Integer> classesList=attendanceSummary.getClassesByTeacherId(Integer.parseInt(attendanceTeacherReportForm.getUserId()), academicYear);
		if(classesList!=null && !classesList.isEmpty()){
		startDate=AttendanceTeacherReportHelper.getStartDate(classesList, academicYear);
		attendanceResults= attendanceSummary.getTeacherAttendance(AttendanceTeacherReportHelper.getSelectionSearchCriteriaForTeacherSummary(attendanceTeacherReportForm,startDate));
		userName=attendanceSummary.getUserName(Integer.parseInt(attendanceTeacherReportForm.getUserId()));
		attendanceTeacherReportForm.setUserName(userName);
		teacherSearchTo= AttendanceTeacherReportHelper.convertBoToTo(attendanceResults,attendanceTeacherReportForm);
		}
		else{
			Calendar xmas = new GregorianCalendar(2011, Calendar.JUNE, 01);
			 startDate=xmas.getTime();
			 attendanceResults= attendanceSummary.getTeacherAttendance(AttendanceTeacherReportHelper.getSelectionSearchCriteriaForTeacherSummary(attendanceTeacherReportForm,startDate));
				userName=attendanceSummary.getUserName(Integer.parseInt(attendanceTeacherReportForm.getUserId()));
				attendanceTeacherReportForm.setUserName(userName);
				teacherSearchTo= AttendanceTeacherReportHelper.convertBoToTo(attendanceResults,attendanceTeacherReportForm);
		}
		log.info("exit getTeacherAttendanceResults..");
		return teacherSearchTo;
		
	}
//	getting absent students
	public List<StudentAbsentDetailsTO> getAbsentStudents(AttendanceTeacherReportForm attendanceTeacherReportForm)throws Exception{
		List studentTO=null;
		List<StudentAbsentDetailsTO> student=new ArrayList<StudentAbsentDetailsTO>();
		IAttendanceTeacherReportTxn attendanceSummary = AttendanceTeacherReportTxn.getInstance();
		try{
		studentTO=attendanceSummary.getAbsentStudents(attendanceTeacherReportForm);
		student=AttendanceTeacherReportHelper.convertListToTO(studentTO,attendanceTeacherReportForm);
		}catch(Exception e){
			log.error("error on getting students");
		}
		return student;
	}
	
	/**
	 * @param attendanceTeacherReportForm
	 * @return
	 * @throws Exception
	 */
	public List<AttendanceTeacherReportTO> getInchargeTeachersClassDetails(AttendanceTeacherReportForm attendanceTeacherReportForm)throws Exception{
		IAttendanceTeacherReportTxn attendanceSummary = AttendanceTeacherReportTxn.getInstance();
		List teacherDepartmentIdList=attendanceSummary.getUserIdsFromTeacherDepartment(attendanceTeacherReportForm);
		List userIdList=attendanceSummary.getUserIds(attendanceTeacherReportForm);
		return AttendanceTeacherReportHelper.getInchargeTeacherClasses(attendanceTeacherReportForm, userIdList,teacherDepartmentIdList);
	}

}
