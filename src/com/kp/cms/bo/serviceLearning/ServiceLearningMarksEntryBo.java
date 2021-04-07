package com.kp.cms.bo.serviceLearning;

import java.util.Date;

import com.kp.cms.bo.admin.Student;

public class ServiceLearningMarksEntryBo {

	private int id;
	private DepartmentNameEntry departmentNameEntry;
	private ProgrammeEntryBo programmeEntryBo;
	private String mark;
	private String remark;
	private Student student;
	private String createdBy;;
	private String modifiedBy;
	private Date createdDate;
	private Date lastModifiedDate;
	private Boolean isActive;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public DepartmentNameEntry getDepartmentNameEntry() {
		return departmentNameEntry;
	}
	public void setDepartmentNameEntry(DepartmentNameEntry departmentNameEntry) {
		this.departmentNameEntry = departmentNameEntry;
	}
	public ProgrammeEntryBo getProgrammeEntryBo() {
		return programmeEntryBo;
	}
	public void setProgrammeEntryBo(ProgrammeEntryBo programmeEntryBo) {
		this.programmeEntryBo = programmeEntryBo;
	}
	
	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}
	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
	public Boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
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
	
	
}
