package com.kp.cms.to.serviceLearning;

import com.kp.cms.to.attendance.ClassesTO;

public class ServiceLearningActivityTo {
	private String id;
	private String learningAndActivity;
	private DepartmentNameEntryTo departmentNameEntryTo;
	private ProgrammeEntryTo programmeEntryTo;
	private String attendedHours;
	private String studentId;
	private String studentName;
	private String registerNo;
	private String mark;
	private String remark;
	private String classname;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLearningAndActivity() {
		return learningAndActivity;
	}
	public void setLearningAndActivity(String learningAndActivity) {
		this.learningAndActivity = learningAndActivity;
	}
	public DepartmentNameEntryTo getDepartmentNameEntryTo() {
		return departmentNameEntryTo;
	}
	public void setDepartmentNameEntryTo(DepartmentNameEntryTo departmentNameEntryTo) {
		this.departmentNameEntryTo = departmentNameEntryTo;
	}
	public ProgrammeEntryTo getProgrammeEntryTo() {
		return programmeEntryTo;
	}
	public void setProgrammeEntryTo(ProgrammeEntryTo programmeEntryTo) {
		this.programmeEntryTo = programmeEntryTo;
	}
	public String getAttendedHours() {
		return attendedHours;
	}
	public void setAttendedHours(String attendedHours) {
		this.attendedHours = attendedHours;
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
	public String getRegisterNo() {
		return registerNo;
	}
	public void setRegisterNo(String registerNo) {
		this.registerNo = registerNo;
	}
	public String getMark() {
		return mark;
	}
	public void setMark(String mark) {
		this.mark = mark;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getClassname() {
		return classname;
	}
	public void setClassname(String classname) {
		this.classname = classname;
	}

}
