package com.kp.cms.to.attendance;

public class ApproveCocurricularLeaveTO {
	private int id;
	private String periodName;
	private String periodId;
	private String subjectName;
	private String subjectId;
	private String activityName;
	private String activityId;
	private String attendanceDate;
	private String classId;
	private String className;
	private String registerNumber;
	private String studentId;
	private String studentName;
	private boolean checked;
	private boolean tempChecked;
	private boolean approvedByTeacher;
	private boolean approvedByAuthoriser;
	private String approveTeacherName;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPeriodName() {
		return periodName;
	}
	public void setPeriodName(String periodName) {
		this.periodName = periodName;
	}
	
	public String getSubjectName() {
		return subjectName;
	}
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	public String getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}
	public String getActivityName() {
		return activityName;
	}
	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}
	public String getAttendanceDate() {
		return attendanceDate;
	}
	public void setAttendanceDate(String attendanceDate) {
		this.attendanceDate = attendanceDate;
	}
	public String getClassId() {
		return classId;
	}
	public void setClassId(String classId) {
		this.classId = classId;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getRegisterNumber() {
		return registerNumber;
	}
	public void setRegisterNumber(String registerNumber) {
		this.registerNumber = registerNumber;
	}
	public String getStudentId() {
		return studentId;
	}
	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	public boolean isTempChecked() {
		return tempChecked;
	}
	public void setTempChecked(boolean tempChecked) {
		this.tempChecked = tempChecked;
	}
	public String getActivityId() {
		return activityId;
	}
	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}
	public String getPeriodId() {
		return periodId;
	}
	public void setPeriodId(String periodId) {
		this.periodId = periodId;
	}
	public boolean isApprovedByTeacher() {
		return approvedByTeacher;
	}
	public void setApprovedByTeacher(boolean approvedByTeacher) {
		this.approvedByTeacher = approvedByTeacher;
	}
	public boolean isApprovedByAuthoriser() {
		return approvedByAuthoriser;
	}
	public void setApprovedByAuthoriser(boolean approvedByAuthoriser) {
		this.approvedByAuthoriser = approvedByAuthoriser;
	}
	public String getApproveTeacherName() {
		return approveTeacherName;
	}
	public void setApproveTeacherName(String approveTeacherName) {
		this.approveTeacherName = approveTeacherName;
	}
	
	
	

}
