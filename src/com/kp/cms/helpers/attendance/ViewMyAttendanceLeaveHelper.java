package com.kp.cms.helpers.attendance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import com.kp.cms.bo.admin.Attendance;
import com.kp.cms.bo.admin.AttendanceClass;
import com.kp.cms.bo.admin.AttendanceInstructor;
import com.kp.cms.bo.admin.AttendancePeriod;
import com.kp.cms.bo.admin.AttendanceStudent;
import com.kp.cms.forms.attendance.ViewMyAttendanceLeaveForm;
import com.kp.cms.to.attendance.ViewMyAttendanceLeaveTo;

public class ViewMyAttendanceLeaveHelper {
	public static volatile ViewMyAttendanceLeaveHelper attendanceLeaveHelper = null;
	public static ViewMyAttendanceLeaveHelper getInstance(){
		if(attendanceLeaveHelper == null){
			attendanceLeaveHelper = new ViewMyAttendanceLeaveHelper();
			return attendanceLeaveHelper;
		}
		return attendanceLeaveHelper;
	}
	/**
	 * @param attendance
	 * @return
	 * @throws Exception
	 */
	public Map<String, ViewMyAttendanceLeaveTo>populateBOToTO(List<Attendance> attendance,ViewMyAttendanceLeaveForm attendanceLeaveForm)throws Exception {
		Map<String, ViewMyAttendanceLeaveTo> map =new HashMap<String, ViewMyAttendanceLeaveTo>();
		if(attendance!=null && !attendance.toString().isEmpty()){
			Iterator<Attendance> itr = attendance.iterator();
			while (itr.hasNext()) {
				Attendance attn = (Attendance) itr.next();
				ViewMyAttendanceLeaveTo leaveTo =new ViewMyAttendanceLeaveTo();
				leaveTo.setSubjectName(attn.getSubject().getName());
				Set<AttendanceClass> attendanceClassSet = attn.getAttendanceClasses();
				Iterator<AttendanceClass> iterator = attendanceClassSet.iterator();
				String className = "";
				while (iterator.hasNext()) {
					AttendanceClass attenClass = (AttendanceClass) iterator .next();
					if(attenClass!=null){
						if(className.isEmpty()){
							className =  attenClass.getClassSchemewise().getClasses().getName();
						}else{
							className = className +", "+attenClass.getClassSchemewise().getClasses().getName();
						}
					}
				}
				leaveTo.setClassName(className);
				Set<AttendancePeriod> attendancePeriods = attn.getAttendancePeriods();
				Iterator<AttendancePeriod> iterator2 = attendancePeriods.iterator();
				String periodName = "";
				if(leaveTo.getPeriodName() != null && !leaveTo.getPeriodName().isEmpty()){
					periodName = leaveTo.getPeriodName();
				}
				while (iterator2.hasNext()) {
					AttendancePeriod attendancePeriod = (AttendancePeriod) iterator2 .next();
					if(!periodName.isEmpty()){
						String startTime = attendancePeriod.getPeriod().getStartTime();
						String endTime = attendancePeriod.getPeriod().getEndTime();
						periodName = periodName +", "+attendancePeriod.getPeriod().getPeriodName().concat(" ("+startTime+"-"+endTime+")");
					}else{
						String startTime = attendancePeriod.getPeriod().getStartTime();
						String endTime = attendancePeriod.getPeriod().getEndTime();
						periodName = periodName + attendancePeriod.getPeriod().getPeriodName().concat(" ("+startTime+"-"+endTime+")");
					}
				}
				leaveTo.setPeriodName(periodName);
				Set<AttendanceStudent> attendanceStudents = attn.getAttendanceStudents();
				StringBuilder regNos =new StringBuilder();
				if(leaveTo.getStudentRegNo() != null && !leaveTo.getStudentRegNo().isEmpty()){
					regNos.append(leaveTo.getStudentRegNo());
				}
				Iterator<AttendanceStudent> iterator3 = attendanceStudents.iterator();
				while (iterator3.hasNext()) {
					AttendanceStudent attendanceStudent = (AttendanceStudent) iterator3.next();
					if(!attendanceStudent.getIsPresent()){
						if(attendanceStudent.getStudent().getRegisterNo()!=null && !attendanceStudent.getStudent().getRegisterNo().isEmpty()){
							if(regNos.toString().isEmpty()){
								regNos.append(attendanceStudent.getStudent().getRegisterNo());
								}else{
								regNos.append(", ").append(attendanceStudent.getStudent().getRegisterNo());
								}
						}
					}
				}
				leaveTo.setStudentRegNo(regNos.toString());
				Set<AttendanceInstructor> attendanceInstructorSet = attn.getAttendanceInstructors();
				String teacherName = "";
				Iterator<AttendanceInstructor> iterator4 = attendanceInstructorSet.iterator();
				while (iterator4.hasNext()) {
					AttendanceInstructor attendanceInstructor = (AttendanceInstructor) iterator4 .next();
					if(attendanceInstructor!=null){
						teacherName = attendanceInstructor.getUsers().getUserName();
					}
				}
				attendanceLeaveForm.setTeacherName(teacherName);
				map.put(leaveTo.getClassName(), leaveTo);
			}
			
		}
		return map;
	}
}
