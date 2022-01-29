package com.kp.cms.helpers.attendance;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Activity;
import com.kp.cms.bo.admin.Attendance;
import com.kp.cms.bo.admin.AttendanceClass;
import com.kp.cms.bo.admin.AttendancePeriod;
import com.kp.cms.bo.admin.AttendanceStudent;
import com.kp.cms.bo.admin.AttendanceType;
import com.kp.cms.bo.admin.Period;
import com.kp.cms.bo.admin.StuCocurrLeave;
import com.kp.cms.bo.admin.StuCocurrLeaveDetails;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.attendance.ApproveLeaveForm;
import com.kp.cms.to.attendance.ActivityAttendenceTO;
import com.kp.cms.to.attendance.StudentLeaveTO;
import com.kp.cms.transactions.attandance.IActivityAttendenceTransaction;
import com.kp.cms.transactionsimpl.attendance.ActivityAttendenceTransactionImpl;
import com.kp.cms.utilities.CommonUtil;

/**
 * Single instance class for the ActivityAttendenceHelper object.
 *
 */
public class ActivityAttendenceHelper {
	private static Map<Integer, String> pMap = null;
	static {
		pMap = new HashMap<Integer, String>();
		pMap.put(13, "01");
		pMap.put(14, "02");
		pMap.put(15, "03");
		pMap.put(16, "04");
		pMap.put(17, "05");
		pMap.put(18, "06");
		pMap.put(19, "07");
		pMap.put(20, "08");
		pMap.put(21, "09");
		pMap.put(22, "10");
		pMap.put(23, "11");
		pMap.put(24, "12");
	}
	/**
	 * Singleton object of the activityAttendenceHelper object.
	 */
	private static volatile ActivityAttendenceHelper activityAttendenceHelper = null;
	private static final Log log = LogFactory.getLog(ActivityAttendenceHelper.class);
	private ActivityAttendenceHelper() {

	}

	/**
	 * 
	 * @return single instance of the ActivityAttendenceHelper object.
	 */
	public static ActivityAttendenceHelper getInstance() {
		if (activityAttendenceHelper == null) {
			activityAttendenceHelper = new ActivityAttendenceHelper();
		}
		return activityAttendenceHelper;
	}

	



	/**
	 * Returns empty string if the register no,s are valid, invalid register no,s otherwise.
	 * @param activityAttendanceForm - Represents the ActivityAttendanceForm object.
	 * @param registerNoList - Represents the register no,s list.
	 * @throws ApplicationException
	 */
	public String isRegisterNoValied(
			ApproveLeaveForm activityAttendanceForm,
			List<String> registerNoList) throws ApplicationException {
		log.info("entering into isRegisterNoValied of ActivityAttendenceHelper class.");
		IActivityAttendenceTransaction activityAttendenceTransaction = new ActivityAttendenceTransactionImpl();
		List<Student> studentList = activityAttendenceTransaction
				.getStudentListByClassSchemeWiseId(Integer
						.valueOf(activityAttendanceForm.getClassSchemewiseId()));

		Iterator<Student> studentIterator = studentList.iterator();
		HashMap<String, Student> studentRegisterNoHashMap = new HashMap<String, Student>();
		StringBuffer invalidRegisterNo = null;
		if (activityAttendanceForm.isRegisterNo()) {
			while (studentIterator.hasNext()) {
				Student student = (Student) studentIterator.next();
				if(student.getRegisterNo()!=null)
				studentRegisterNoHashMap.put(student.getRegisterNo().trim(), student);
			}
		} else {
			while (studentIterator.hasNext()) {
				Student student = (Student) studentIterator.next();
				if(student.getRollNo()!=null)
				studentRegisterNoHashMap.put(student.getRollNo().trim(), student);
			}
		}

		Iterator<String> registerNoIterator = registerNoList.iterator();
		while (registerNoIterator.hasNext()) {
			String registerNo = (String) registerNoIterator.next();
			if (studentRegisterNoHashMap.get(registerNo.trim()) == null) {
				if(invalidRegisterNo == null) {
					invalidRegisterNo = new StringBuffer(registerNo);
				} else {
					invalidRegisterNo.append(' ');
					invalidRegisterNo.append(registerNo);
				}
				
			}

		}
		
		if(invalidRegisterNo == null) {
			invalidRegisterNo = new StringBuffer();
		} 
		
		log.info("exit of  isRegisterNoValied of ActivityAttendenceHelper class.");
		return invalidRegisterNo.toString();
	}

	/**
	 * Get the period object from the period id.
	 * @param periodId
	 * @return
	 * @throws ApplicationException
	 */
	public Period getPeriod(int periodId) throws ApplicationException {
		log.info("entering into getPeriod of ActivityAttendenceHelper class.");
		IActivityAttendenceTransaction activityAttendenceTransaction = new ActivityAttendenceTransactionImpl();
		log.info("exit of  getPeriod of ActivityAttendenceHelper class.");
		return activityAttendenceTransaction.getPeriod(periodId);
	}


	
	/**
	 * Converts from BO to TO
	 * @param modifyActivityAttendanceList
	 * @return
	 * @throws ApplicationException
	 * @throws ParseException
	 */
	public List<ActivityAttendenceTO> modifyActivityAttendenceBOtoTO(List<Attendance> modifyActivityAttendanceList)
			throws ApplicationException,
			ParseException {
		log.info("entering into modifyActivityAttendenceBOtoTO of ActivityAttendenceHelper class.");
		List<ActivityAttendenceTO> modifyActivityAttendenceList = new ArrayList<ActivityAttendenceTO>();
		
		if(modifyActivityAttendanceList!=null && !modifyActivityAttendanceList.isEmpty()){
			Iterator<Attendance> attendanceIterator = modifyActivityAttendanceList.iterator();
			
			while (attendanceIterator.hasNext()) {
				Attendance attendance = (Attendance) attendanceIterator.next();
				ActivityAttendenceTO activityAttendenceTO = new ActivityAttendenceTO();
				
				activityAttendenceTO.setId(attendance.getId());
				
				if(attendance.getAttendanceType() !=null){
					AttendanceType attendanceType = new AttendanceType();
					attendanceType.setName(attendance.getAttendanceType().getName());
					activityAttendenceTO.setAttendanceType(attendanceType);
				}
				
				if(attendance.getActivity()!=null){
					Activity activity = new Activity();
					activity.setName(attendance.getActivity().getName());
					activityAttendenceTO.setActivity(activity);
				}
				
				activityAttendenceTO.setAttendanceDate(attendance.getAttendanceDate());
				
				Set<AttendanceClass> attendanceClassesSet = attendance.getAttendanceClasses();
				Iterator<AttendanceClass> attendanceClassesItr =attendanceClassesSet.iterator();
				while (attendanceClassesItr.hasNext()) {
					AttendanceClass attendanceClass = (AttendanceClass) attendanceClassesItr.next();
					if( attendanceClass !=null && attendanceClass.getClassSchemewise()!=null && attendanceClass.getClassSchemewise().getClasses()!=null ){
						activityAttendenceTO.setClassName(attendanceClass.getClassSchemewise().getClasses().getName());
					}
				}

				StringBuffer registerNumber = null;
				StringBuffer periodName = null ;
				
				Set<AttendanceStudent> attendanceStudentSet = attendance.getAttendanceStudents();
				Iterator<AttendanceStudent> attendanceStudentItr =attendanceStudentSet.iterator();
				while (attendanceStudentItr.hasNext()) {
					AttendanceStudent attendanceStudent = (AttendanceStudent) attendanceStudentItr.next();
					if (attendanceStudent != null
							&& attendanceStudent.getStudent() != null
							&& attendanceStudent.getStudent().getClassSchemewise() != null
							&& attendanceStudent.getStudent().getClassSchemewise().getClasses() != null
							&& attendanceStudent.getStudent().getClassSchemewise().getClasses().getCourse() != null
							&& attendanceStudent.getStudent().getClassSchemewise().getClasses().getCourse().getProgram() != null
							&& attendanceStudent.getStudent().getClassSchemewise().getClasses().getCourse().getProgram().getIsRegistrationNo()) {
						if (registerNumber == null && !attendanceStudent.getStudent().getRegisterNo().isEmpty()) {
							registerNumber = new StringBuffer(attendanceStudent.getStudent().getRegisterNo());
						} else if(!attendanceStudent.getStudent().getRegisterNo().isEmpty()){
							registerNumber.append(',');
							registerNumber.append(attendanceStudent.getStudent().getRegisterNo());
						}
					} else {
						if (registerNumber == null && !attendanceStudent.getStudent().getRollNo().isEmpty()) {
							registerNumber = new StringBuffer(attendanceStudent.getStudent().getRollNo());
						} else if(!attendanceStudent.getStudent().getRollNo().isEmpty()){
							registerNumber.append(',');
							registerNumber.append(attendanceStudent.getStudent().getRollNo());
						}
					}
				}
				if(registerNumber != null){
					activityAttendenceTO.setRegisterNumber(registerNumber.toString());
				}
				
				Set<AttendancePeriod> attendancePeriodSet = attendance.getAttendancePeriods();
				Iterator<AttendancePeriod> attendancePeriodItr =attendancePeriodSet.iterator();
				while (attendancePeriodItr.hasNext()) {
					AttendancePeriod attendancePeriod = (AttendancePeriod) attendancePeriodItr.next();
					if( attendancePeriod !=null && attendancePeriod.getPeriod()!=null){
						if(periodName == null && !attendancePeriod.getPeriod().getPeriodName().isEmpty()){
							periodName = new StringBuffer(attendancePeriod.getPeriod().getPeriodName());
						}else{
							periodName = periodName.append(","+attendancePeriod.getPeriod().getPeriodName());
						}
					}
				}
				if(periodName != null){
					activityAttendenceTO.setPeriodName(periodName.toString());
				}
				modifyActivityAttendenceList.add(activityAttendenceTO);
			}
		}
		log.info("exit of  modifyActivityAttendenceBOtoTO of ActivityAttendenceHelper class.");
		return modifyActivityAttendenceList;
	}
	
	
	/**
	 * Converts from BO to TO
	 * @param attendance
	 * @return
	 * @throws ApplicationException
	 * @throws ParseException
	 */
	public ActivityAttendenceTO getActivityAttendanceByIdBOtoTO(
			Attendance attendance) throws ApplicationException, ParseException {
		log.info("entering into getActivityAttendanceByIdBOtoTO of ActivityAttendenceHelper class.");
		ActivityAttendenceTO getActivityAttendanceByIdTO = new ActivityAttendenceTO();

		if (attendance != null) {

			getActivityAttendanceByIdTO.setId(attendance.getId());
			
			if (attendance.getAttendanceType() != null) {
				AttendanceType attendanceType = new AttendanceType();
				attendanceType.setId(attendance.getAttendanceType().getId());
				getActivityAttendanceByIdTO.setAttendanceType(attendanceType);
			}

			if (attendance.getActivity() != null) {
				Activity activity = new Activity();
				activity.setId(attendance.getActivity().getId());
				getActivityAttendanceByIdTO.setActivity(activity);
			}
			
			getActivityAttendanceByIdTO.setFromDate(CommonUtil.ConvertStringToDateFormat(attendance.getAttendanceDate().toString(),"yyyy-MM-dd", "dd/MM/yyyy"));
			getActivityAttendanceByIdTO.setToDate(CommonUtil.ConvertStringToDateFormat(attendance.getAttendanceDate().toString(),"yyyy-MM-dd", "dd/MM/yyyy"));

			Map<Integer,Integer> attendanceClassMap = new HashMap<Integer, Integer>();
			Set<AttendanceClass> attendanceClassesSet = attendance.getAttendanceClasses();
			Iterator<AttendanceClass> attendanceClassesItr = attendanceClassesSet.iterator();
			while (attendanceClassesItr.hasNext()) {
				AttendanceClass attendanceClass = (AttendanceClass) attendanceClassesItr.next();
				if (attendanceClass != null && attendanceClass.getClassSchemewise() != null) {
					attendanceClassMap.put(attendanceClass.getClassSchemewise().getId(), attendanceClass.getId());
					getActivityAttendanceByIdTO.setAttendanceClassMap(attendanceClassMap);
					getActivityAttendanceByIdTO.setClassId(attendanceClass.getClassSchemewise().getClasses().getId());
				}
			}

			StringBuffer registerNumber = null;
			
			Map<Integer,Integer> attendanceStudentMap = new HashMap<Integer, Integer>();
			Set<AttendanceStudent> attendanceStudentSet = attendance.getAttendanceStudents();
			Iterator<AttendanceStudent> attendanceStudentItr = attendanceStudentSet.iterator();
			while (attendanceStudentItr.hasNext()) {
				AttendanceStudent attendanceStudent = (AttendanceStudent) attendanceStudentItr.next();
				if (attendanceStudent != null
						&& attendanceStudent.getStudent() != null
						&& attendanceStudent.getStudent().getClassSchemewise() != null
						&& attendanceStudent.getStudent().getClassSchemewise().getClasses() != null
						&& attendanceStudent.getStudent().getClassSchemewise().getClasses().getCourse() != null
						&& attendanceStudent.getStudent().getClassSchemewise().getClasses().getCourse().getProgram() != null
						&& attendanceStudent.getStudent().getClassSchemewise().getClasses().getCourse().getProgram().getIsRegistrationNo()) {
					attendanceStudentMap.put(attendanceStudent.getStudent().getId(), attendanceStudent.getId());
					if (registerNumber == null && !attendanceStudent.getStudent().getRegisterNo().isEmpty()) {
						registerNumber = new StringBuffer(attendanceStudent.getStudent().getRegisterNo());
					} else if(!attendanceStudent.getStudent().getRegisterNo().isEmpty()) {
						registerNumber.append(',');
						registerNumber.append(attendanceStudent.getStudent().getRegisterNo());
					}
				} else {
					attendanceStudentMap.put(attendanceStudent.getStudent().getId(), attendanceStudent.getId());
					if (registerNumber == null && !attendanceStudent.getStudent().getRollNo().isEmpty()) {
						registerNumber = new StringBuffer(attendanceStudent.getStudent().getRollNo());
					} else if(!attendanceStudent.getStudent().getRollNo().isEmpty()){
						registerNumber.append(',');
						registerNumber.append(attendanceStudent.getStudent().getRollNo());
					}
				}
			}
			getActivityAttendanceByIdTO.setAttendanceStudentMap(attendanceStudentMap);
			getActivityAttendanceByIdTO.setRegisterNumber(registerNumber.toString());

			Map<Integer,Integer> attendancePeriodMap = new HashMap<Integer, Integer>();
			List<AttendancePeriod> attendancePeriodList = new ArrayList<AttendancePeriod>();
			attendancePeriodList.addAll(attendance.getAttendancePeriods());
			
			Collections.sort(attendancePeriodList);
			
			for(int i=0; i<attendancePeriodList.size(); i++){
				attendancePeriodMap.put(attendancePeriodList.get(i).getPeriod().getId(), attendancePeriodList.get(i).getId());
				String fromPeriodId = String.valueOf(attendancePeriodList.get(0).getPeriod().getId());
				String toPeriodId = String.valueOf(attendancePeriodList.get(attendancePeriodList.size()-1).getPeriod().getId());
				getActivityAttendanceByIdTO.setFromPeriodId(fromPeriodId);
				getActivityAttendanceByIdTO.setToPeriodId(toPeriodId);
				getActivityAttendanceByIdTO.setAttendancePeriodMap(attendancePeriodMap);
			}
		}
		log.info("exit of  getActivityAttendanceByIdBOtoTO of ActivityAttendenceHelper class.");
		return getActivityAttendanceByIdTO;
	}

	/**
	 * Converts from BO to TO
	 * @param list
	 * @return
	 */
	public List<StudentLeaveTO> convertStudentLeaveBOtoTO(
			List<StuCocurrLeave> list) {
		log.info("entering into convertStudentLeaveBOtoTO of ApproveLeaveHelper class.");
		List<StudentLeaveTO> studentList = new ArrayList<StudentLeaveTO>();
		StudentLeaveTO studentLeaveTO;
		StuCocurrLeave studentLeave;
		Iterator<StuCocurrLeave> itr = list.iterator();
		
		while (itr.hasNext()) {
			try {
				studentLeave = itr.next();
				StringBuffer registerNo = new StringBuffer();
				studentLeaveTO = new StudentLeaveTO();
				studentLeaveTO.setId(studentLeave.getId());
				studentLeaveTO.setLeaveType(studentLeave.getActivity().getName());
				studentLeaveTO.setStartDate(studentLeave.getStartDate());
				studentLeaveTO.setEndDate(studentLeave.getEndDate());
				if(studentLeave.getClassSchemewise()!=null)
					studentLeaveTO.setClassName(studentLeave.getClassSchemewise().getClasses().getName());
				if(studentLeave.getStartPeriod()!=null){
					int st=Integer.parseInt(studentLeave.getStartPeriod().getStartTime().substring(0,2));
					int st1=Integer.parseInt(studentLeave.getStartPeriod().getEndTime().substring(0,2));
					if(st<=12){
						studentLeaveTO.setStartPeriod(studentLeave.getStartPeriod().getPeriodName()+"("+studentLeave.getStartPeriod().getStartTime().substring(0,5)+"-"+studentLeave.getStartPeriod().getEndTime().substring(0,5)+")");
					}else{
						studentLeaveTO.setStartPeriod(studentLeave.getStartPeriod().getPeriodName()+"("+pMap.get(st)+studentLeave.getStartPeriod().getStartTime().substring(2,5)+"-"+pMap.get(st1)+studentLeave.getStartPeriod().getEndTime().substring(2,5)+")");
					}
				}
				if(studentLeave.getEndPeriod()!=null){
					int et=Integer.parseInt(studentLeave.getEndPeriod().getStartTime().substring(0,2));
					int et1=Integer.parseInt(studentLeave.getEndPeriod().getEndTime().substring(0,2));
					if(et<=12){
						studentLeaveTO.setEndPeriod(studentLeave.getEndPeriod().getPeriodName()+"("+studentLeave.getEndPeriod().getStartTime().substring(0,5)+"-"+studentLeave.getEndPeriod().getEndTime().substring(0,5)+")");
					}else{
						studentLeaveTO.setEndPeriod(studentLeave.getEndPeriod().getPeriodName()+"("+pMap.get(et)+studentLeave.getEndPeriod().getStartTime().substring(2,5)+"-"+pMap.get(et1)+studentLeave.getEndPeriod().getEndTime().substring(2,5)+")");
					}
				}
				Iterator<StuCocurrLeaveDetails> itr1 = studentLeave.getStuCocurrLeaveDetailses().iterator();
				boolean isRegCheck = studentLeave.getClassSchemewise().getClasses().getCourse().getProgram().getIsRegistrationNo();
				while(itr1.hasNext()){
					if(isRegCheck){
						if(registerNo.length()>0) {
							registerNo.append(',');
							registerNo.append(' ');
						}
						registerNo.append(itr1.next().getStudent().getRegisterNo());
						
					} else {
						if(registerNo.length()>0) {
							registerNo.append(',');
							registerNo.append(' ');
						}
						registerNo.append(itr1.next().getStudent().getRollNo());
						
					}	
				}
				if(registerNo != null && !registerNo.toString().isEmpty()) {
					registerNo.substring(0,registerNo.length()-1);
				}
			
				
				studentLeaveTO.setRollOrRegNos(registerNo.toString());
				studentList.add(studentLeaveTO);
			} catch (Exception e) {
				// TODO: handle exception
			}
			
		}
		log.info("exit of convertStudentLeaveBOtoTO of ApproveLeaveHelper class.");
		return studentList;
	}
	
	
	/**
	 * @param stuRegNoMAp
	 * @param registerNoList
	 * @return
	 */
	public ArrayList<String> getStudentRegNos(Map<String,String> stuRegNoMAp,ArrayList<String> registerNoList) {
		ArrayList<String> regNoList=new ArrayList<String>();
			if(stuRegNoMAp!=null && !stuRegNoMAp.isEmpty()){
				Iterator<String> iterator = registerNoList.iterator();
				while (iterator.hasNext()) {
					String registerNo = (String) iterator.next();
					if(stuRegNoMAp.containsKey(registerNo)){
						regNoList.add(registerNo);
						}
				}
			}
			
		return 	regNoList;
		}
	
	/**
	 * @param stuRegNoMAp
	 * @param registerNoList
	 * @return
	 */
	public String validateRegNos(Map<String,String> stuRegNoMAp,ArrayList<String> registerNoList) {
		String regNoList="";
			if(stuRegNoMAp!=null && !stuRegNoMAp.isEmpty()){
				Iterator<String> iterator = registerNoList.iterator();
				while (iterator.hasNext()) {
					String registerNo = (String) iterator.next();
					if(!stuRegNoMAp.containsKey(registerNo)){
						if(regNoList==null || regNoList.isEmpty()){
							regNoList=registerNo;
						}else{
							regNoList=regNoList+","+registerNo;
						}
					}
				}
			}
			
		return 	regNoList;
		}
			
}