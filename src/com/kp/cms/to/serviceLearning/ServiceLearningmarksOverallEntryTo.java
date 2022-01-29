package com.kp.cms.to.serviceLearning;

public class ServiceLearningmarksOverallEntryTo {
	private String id;
	private String overallMark;
	private String studentName;
	private String registerNo;
	private String studentId;
	private String remark;
	private String mark;
	private DepartmentNameEntryTo departmentNameEntryTo;
	private ProgrammeEntryTo programmeEntryTo;
	public String getOverallMark() {
		return overallMark;
	}
	public void setOverallMark(String overallMark) {
		this.overallMark = overallMark;
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
	public String getStudentId() {
		return studentId;
	}
	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getMark() {
		return mark;
	}
	public void setMark(String mark) {
		this.mark = mark;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
}
