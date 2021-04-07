package com.kp.cms.handlers.attendance;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.forms.attendance.AttendanceTeacherReportForm;
import com.kp.cms.helpers.attendance.TeacherClassAttendanceHelper;
import com.kp.cms.to.attendance.AttendanceTeacherReportTO;
import com.kp.cms.to.attendance.PeriodTO;
import com.kp.cms.to.attendance.StudentAbsentDetailsTO;
import com.kp.cms.transactions.attandance.ITeacherClassAttendanceTxn;
import com.kp.cms.transactionsimpl.attendance.TeacherClassAttendanceTxnImpl;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.CurrentAcademicYear;

public class TeacherClassAttendanceHandler {
	private static final Log log = LogFactory.getLog(TeacherClassAttendanceHandler.class);
	public static volatile TeacherClassAttendanceHandler self=null;
	/**
	 * @return
	 * This method gives instance of this method
	 */
	public static TeacherClassAttendanceHandler getInstance(){
		if(self==null){
			self= new TeacherClassAttendanceHandler();}
		return self;
	}
	/**
	 * 
	 */
	private TeacherClassAttendanceHandler(){
		
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
		ITeacherClassAttendanceTxn attendanceSummary = TeacherClassAttendanceTxnImpl.getInstance();
		List attendanceResults = attendanceSummary.getTeacherAttendance(TeacherClassAttendanceHelper.getSelectionSearchCriteria(attendanceTeacherReportForm));
		
		List<AttendanceTeacherReportTO> teacherSearchTo = TeacherClassAttendanceHelper.convertBoToTo(attendanceResults,attendanceTeacherReportForm);
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
		ITeacherClassAttendanceTxn attendanceSummary = TeacherClassAttendanceTxnImpl.getInstance();
		List attendanceResults = attendanceSummary.getTeacherAttendance(TeacherClassAttendanceHelper.getSelectionMonthlyCriteria(attendanceTeacherReportForm));
		
		List<AttendanceTeacherReportTO> teacherSearchTo = TeacherClassAttendanceHelper.convertMonthlyBoToTo(attendanceResults,attendanceTeacherReportForm);
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
		ITeacherClassAttendanceTxn attendanceSummary = TeacherClassAttendanceTxnImpl.getInstance();
		List periodList=null;
		if(attendanceTeacherReportForm.getSubjectId()!=null && !attendanceTeacherReportForm.getSubjectId().isEmpty()){
			periodList= attendanceSummary.getPeriodDetails(Integer.parseInt(attendanceTeacherReportForm.getUserId()),attendanceTeacherReportForm.getClassId(), Integer.parseInt(attendanceTeacherReportForm.getSubjectId()),
				attendanceTeacherReportForm.getStartDate(), attendanceTeacherReportForm.getEndDate(),attendanceTeacherReportForm.getBatchName());
		}else{
			periodList= attendanceSummary.getActivityPeriodDetails(Integer.parseInt(attendanceTeacherReportForm.getUserId()), attendanceTeacherReportForm.getClassId(), 
					attendanceTeacherReportForm.getStartDate(), attendanceTeacherReportForm.getEndDate());
		}
		String[] ids=attendanceTeacherReportForm.getClassId().split(",");
		List<String> classids=new ArrayList<String>();
		for (int i = 0; i < ids.length; i++) {
			String string = ids[i];
			classids.add(string);
		}
		List<PeriodTO> periodTOList=TeacherClassAttendanceHelper.createPeriodTOs(periodList,classids);
		
		log.info("Leaving into getAttendanceTeacherPeriodDetails");
		return periodTOList;
	}
	
	/**
	 * @param attendanceTeacherReportForm
	 * @return
	 * @throws Exception
	 */
	public List<PeriodTO> getPreviousAttendanceTeacherPeriodDetails(
			AttendanceTeacherReportForm attendanceTeacherReportForm) throws Exception {
		log.info("Entering into getAttendanceTeacherPeriodDetails");
		ITeacherClassAttendanceTxn attendanceSummary = TeacherClassAttendanceTxnImpl.getInstance();
		List periodList=null;
		if(attendanceTeacherReportForm.getSubjectId()!=null && !attendanceTeacherReportForm.getSubjectId().isEmpty()){
			periodList= attendanceSummary.getPeriodDetails(Integer.parseInt(attendanceTeacherReportForm.getUserId()),attendanceTeacherReportForm.getClassId(), Integer.parseInt(attendanceTeacherReportForm.getSubjectId()),
				attendanceTeacherReportForm.getPreviousStartDate(), attendanceTeacherReportForm.getPreviousEndDate(),attendanceTeacherReportForm.getBatchName());
		}else{
			periodList= attendanceSummary.getActivityPeriodDetails(Integer.parseInt(attendanceTeacherReportForm.getUserId()),attendanceTeacherReportForm.getClassId(), 
					attendanceTeacherReportForm.getPreviousStartDate(), attendanceTeacherReportForm.getPreviousEndDate());
		}
		String[] ids=attendanceTeacherReportForm.getClassId().split(",");
		List<String> classids=new ArrayList<String>();
		for (int i = 0; i < ids.length; i++) {
			String string = ids[i];
			classids.add(string);
		}
		List<PeriodTO> periodTOList=TeacherClassAttendanceHelper.createPeriodTOs(periodList,classids);
		
		log.info("Leaving into getAttendanceTeacherPeriodDetails");
		return periodTOList;
	}
	
	/**
	 * @param attendanceTeacherReportForm
	 * @return
	 * @throws Exception
	 */
	public List<AttendanceTeacherReportTO> getPreviousAttendanceTeacherSummaryReports(
			AttendanceTeacherReportForm attendanceTeacherReportForm) throws Exception {
		log.info("entered getTeacherAttendanceResults..");
		ITeacherClassAttendanceTxn attendanceSummary = TeacherClassAttendanceTxnImpl.getInstance();
		List<AttendanceTeacherReportTO> teacherSearchTo; 
		Date previousStartDate=null;
		Date previousEndDate=null;
		List<Object[]> attendanceResults=null;
		String userName="";
		CurrentAcademicYear year=CurrentAcademicYear.getInstance();
		int academicYear=year.getAcademicyear();
		List<Object[]> classesList=attendanceSummary.getPreviousClassesByTeacherId(Integer.parseInt(attendanceTeacherReportForm.getUserId()), academicYear);
		if(classesList!=null && !classesList.isEmpty()){
		if(attendanceTeacherReportForm.getPreviousStartDate()!=null)
			previousStartDate=(CommonUtil.ConvertStringToDate(attendanceTeacherReportForm.getPreviousStartDate()));
		if(attendanceTeacherReportForm.getPreviousEndDate()!=null)
			previousEndDate=(CommonUtil.ConvertStringToDate(attendanceTeacherReportForm.getPreviousEndDate()));	
		
		attendanceResults= attendanceSummary.getTeacherAttendance(TeacherClassAttendanceHelper.getSelectionSearchCriteriaForPreviousTeacherSummary(attendanceTeacherReportForm,previousStartDate,previousEndDate,classesList ));
		userName=attendanceSummary.getUserName(Integer.parseInt(attendanceTeacherReportForm.getUserId()));
		attendanceTeacherReportForm.setUserName(userName);
		teacherSearchTo= TeacherClassAttendanceHelper.convertListBosToListTos(attendanceResults,attendanceTeacherReportForm);
		}
		else{
			Calendar xmas = new GregorianCalendar(2011, Calendar.JUNE, 01);
			Date startDate=xmas.getTime();
			 attendanceResults= attendanceSummary.getTeacherAttendance(TeacherClassAttendanceHelper.getSelectionSearchCriteriaForTeacherSummary(attendanceTeacherReportForm,startDate));
				userName=attendanceSummary.getUserName(Integer.parseInt(attendanceTeacherReportForm.getUserId()));
				attendanceTeacherReportForm.setUserName(userName);
				teacherSearchTo= TeacherClassAttendanceHelper.convertListBosToListTos(attendanceResults,attendanceTeacherReportForm);
		}
		log.info("exit getTeacherAttendanceResults..");
		return teacherSearchTo;
		
	}
	/**
	 * @param attendanceTeacherReportForm
	 * @return
	 * @throws Exception
	 */
	public List<AttendanceTeacherReportTO> getAttendanceTeacherSummaryReports(
			AttendanceTeacherReportForm attendanceTeacherReportForm) throws Exception {
		log.info("entered getTeacherAttendanceResults..");
		ITeacherClassAttendanceTxn attendanceSummary = TeacherClassAttendanceTxnImpl.getInstance();
		List<AttendanceTeacherReportTO> teacherSearchTo; 
		Date previousStartDate=null;
		Date previousEndDate=null;
		List<Object[]> attendanceResults=null;
		String userName="";
		int academicYear=CurrentAcademicYear.getInstance().getAcademicyear();
		List<Integer> classesList=attendanceSummary.getClassesByTeacherIdReport(Integer.parseInt(attendanceTeacherReportForm.getUserId()), academicYear);
		if(classesList!=null && !classesList.isEmpty()){
		previousStartDate=TeacherClassAttendanceHelper.getStartDate(classesList, academicYear);	
		previousEndDate=TeacherClassAttendanceHelper.getEndDate(classesList, academicYear);
		attendanceTeacherReportForm.setPreviousStartDate(CommonUtil.ConvertStringToDateFormat(
				previousStartDate.toString(), "yyyy-mm-dd","dd/mm/yyyy"));
		attendanceTeacherReportForm.setPreviousEndDate(CommonUtil.ConvertStringToDateFormat(
				previousEndDate.toString(), "yyyy-mm-dd","dd/mm/yyyy"));
		//attendanceResults= attendanceSummary.getTeacherAttendance(TeacherClassAttendanceHelper.getSelectionSearchCriteriaForTeacherSummary(attendanceTeacherReportForm,currentStartDate));
		//attendanceResults= attendanceSummary.getTeacherAttendance(TeacherClassAttendanceHelper.getSelectionSearchCriteriaForTeacherSummaryReport(attendanceTeacherReportForm,classesList));
		attendanceResults= attendanceSummary.getTeacherAttendanceNew(attendanceTeacherReportForm,classesList);
		
		userName=attendanceSummary.getUserName(Integer.parseInt(attendanceTeacherReportForm.getUserId()));
		attendanceTeacherReportForm.setUserName(userName);
		teacherSearchTo= TeacherClassAttendanceHelper.convertListBosToListTos(attendanceResults,attendanceTeacherReportForm);
		}
		else{
			Calendar xmas = new GregorianCalendar(2011, Calendar.JUNE, 01);
			Date startDate=xmas.getTime();
			 attendanceResults= attendanceSummary.getTeacherAttendance(TeacherClassAttendanceHelper.getSelectionSearchCriteriaForTeacherSummary(attendanceTeacherReportForm,startDate));
				userName=attendanceSummary.getUserName(Integer.parseInt(attendanceTeacherReportForm.getUserId()));
				attendanceTeacherReportForm.setUserName(userName);
				teacherSearchTo= TeacherClassAttendanceHelper.convertListBosToListTos(attendanceResults,attendanceTeacherReportForm);
		}
		log.info("exit getTeacherAttendanceResults..");
		return teacherSearchTo;
		
	}
//	getting absent students
	/**
	 * @param attendanceTeacherReportForm
	 * @return
	 * @throws Exception
	 */
	public List<StudentAbsentDetailsTO> getAbsentStudents(AttendanceTeacherReportForm attendanceTeacherReportForm)throws Exception{
		List studentTO=null;
		List<StudentAbsentDetailsTO> student=new ArrayList<StudentAbsentDetailsTO>();
		ITeacherClassAttendanceTxn attendanceSummary = TeacherClassAttendanceTxnImpl.getInstance();
		try{
		studentTO=attendanceSummary.getAbsentStudents(attendanceTeacherReportForm);
		student=TeacherClassAttendanceHelper.convertListToTO(studentTO,attendanceTeacherReportForm);
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
		ITeacherClassAttendanceTxn attendanceSummary = TeacherClassAttendanceTxnImpl.getInstance();
		List teacherDepartmentIdList=attendanceSummary.getUserIdsFromTeacherDepartment(attendanceTeacherReportForm);
		List userIdList=attendanceSummary.getUserIds(attendanceTeacherReportForm);
		return TeacherClassAttendanceHelper.getInchargeTeacherClasses(attendanceTeacherReportForm, userIdList,teacherDepartmentIdList);
	}
}
