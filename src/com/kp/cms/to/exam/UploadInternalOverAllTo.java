package com.kp.cms.to.exam;

public class UploadInternalOverAllTo {
	
	private String examId;
	private String studentId;
	private String classId;
	private String subjectId;
	private String semesterNo;
	private String theoryMark="0";
	private String practicalMark="0";
	private boolean isPass;
	private String marksType;
	private String theoryAttendance="0";
	private String practicalAttendance="0";
	
	public String getExamId() {
		return examId;
	}
	public void setExamId(String examId) {
		this.examId = examId;
	}
	public String getStudentId() {
		return studentId;
	}
	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	public String getClassId() {
		return classId;
	}
	public void setClassId(String classId) {
		this.classId = classId;
	}
	public String getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}
	public String getSemesterNo() {
		return semesterNo;
	}
	public void setSemesterNo(String semesterNo) {
		this.semesterNo = semesterNo;
	}
	public String getTheoryMark() {
		return theoryMark;
	}
	public void setTheoryMark(String theoryMark) {
		this.theoryMark = theoryMark;
	}
	public String getPracticalMark() {
		return practicalMark;
	}
	public void setPracticalMark(String practicalMark) {
		this.practicalMark = practicalMark;
	}
	public boolean isPass() {
		return isPass;
	}
	public void setPass(boolean isPass) {
		this.isPass = isPass;
	}
	public String getMarksType() {
		return marksType;
	}
	public void setMarksType(String marksType) {
		this.marksType = marksType;
	}
	public String getTheoryAttendance() {
		return theoryAttendance;
	}
	public void setTheoryAttendance(String theoryAttendance) {
		this.theoryAttendance = theoryAttendance;
	}
	public String getPracticalAttendance() {
		return practicalAttendance;
	}
	public void setPracticalAttendance(String practicalAttendance) {
		this.practicalAttendance = practicalAttendance;
	}
}