package com.kp.cms.to.attendance;

import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.kp.cms.bo.admin.Activity;
import com.kp.cms.bo.admin.AttendanceClass;
import com.kp.cms.bo.admin.AttendancePeriod;
import com.kp.cms.bo.admin.AttendanceStudent;
import com.kp.cms.bo.admin.AttendanceType;

public class ActivityAttendenceTO {

	private int id;
	private int classId;
	private Activity activity;
	private AttendanceType attendanceType;
	private Date attendanceDate;
	private String fromDate;
	private String toDate;
	private String className;
	private String registerNumber;
	private String periodName;
	private String fromPeriodId;
	private String toPeriodId;
	private Set<AttendanceStudent> attendanceStudents = new HashSet<AttendanceStudent>(
			0);
	private Set<AttendanceClass> attendanceClasses = new HashSet<AttendanceClass>(
			0);
	private Set<AttendancePeriod> attendancePeriods = new HashSet<AttendancePeriod>(
			0);
	private Map<Integer, Integer> attendanceClassMap;
	private Map<Integer, Integer> attendanceStudentMap;
	private Map<Integer, Integer> attendancePeriodMap;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getClassId() {
		return classId;
	}

	public void setClassId(int classId) {
		this.classId = classId;
	}

	public String getRegisterNumber() {
		return registerNumber;
	}

	public void setRegisterNumber(String registerNumber) {
		this.registerNumber = registerNumber;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getPeriodName() {
		return periodName;
	}

	public void setPeriodName(String periodName) {
		this.periodName = periodName;
	}

	public Activity getActivity() {
		return activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}

	public Date getAttendanceDate() {
		return attendanceDate;
	}

	public void setAttendanceDate(Date attendanceDate) {
		this.attendanceDate = attendanceDate;
	}

	public Set<AttendanceStudent> getAttendanceStudents() {
		return attendanceStudents;
	}

	public void setAttendanceStudents(Set<AttendanceStudent> attendanceStudents) {
		this.attendanceStudents = attendanceStudents;
	}

	public Set<AttendanceClass> getAttendanceClasses() {
		return attendanceClasses;
	}

	public void setAttendanceClasses(Set<AttendanceClass> attendanceClasses) {
		this.attendanceClasses = attendanceClasses;
	}

	public Set<AttendancePeriod> getAttendancePeriods() {
		return attendancePeriods;
	}

	public void setAttendancePeriods(Set<AttendancePeriod> attendancePeriods) {
		this.attendancePeriods = attendancePeriods;
	}

	public AttendanceType getAttendanceType() {
		return attendanceType;
	}

	public void setAttendanceType(AttendanceType attendanceType) {
		this.attendanceType = attendanceType;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public String getFromPeriodId() {
		return fromPeriodId;
	}

	public void setFromPeriodId(String fromPeriodId) {
		this.fromPeriodId = fromPeriodId;
	}

	public String getToPeriodId() {
		return toPeriodId;
	}

	public void setToPeriodId(String toPeriodId) {
		this.toPeriodId = toPeriodId;
	}

	public Map<Integer, Integer> getAttendanceClassMap() {
		return attendanceClassMap;
	}

	public void setAttendanceClassMap(Map<Integer, Integer> attendanceClassMap) {
		this.attendanceClassMap = attendanceClassMap;
	}

	public Map<Integer, Integer> getAttendanceStudentMap() {
		return attendanceStudentMap;
	}

	public void setAttendanceStudentMap(
			Map<Integer, Integer> attendanceStudentMap) {
		this.attendanceStudentMap = attendanceStudentMap;
	}

	public Map<Integer, Integer> getAttendancePeriodMap() {
		return attendancePeriodMap;
	}

	public void setAttendancePeriodMap(Map<Integer, Integer> attendancePeriodMap) {
		this.attendancePeriodMap = attendancePeriodMap;
	}
}