package com.kp.cms.bo.serviceLearning;

import java.util.Date;

import com.kp.cms.bo.admin.Classes;
import com.kp.cms.bo.admin.Student;

public class ServiceLearningActivityBo {
	private int id;
	private String learningAndActivity;
	private DepartmentNameEntry departmentNameEntry;
	private ProgrammeEntryBo programmeEntryBo;
	private String attendedHours;
	private Student student;
	private String createdBy;;
	private String modifiedBy;
	private Date createdDate;
	private Date lastModifiedDate;
	private Boolean isActive;
	private Boolean isMarkSecured;
	private Classes classes;
	private Date startDate;
	private Date endDate;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getLearningAndActivity() {
		return learningAndActivity;
	}
	public void setLearningAndActivity(String learningAndActivity) {
		this.learningAndActivity = learningAndActivity;
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
	public String getAttendedHours() {
		return attendedHours;
	}
	public void setAttendedHours(String attendedHours) {
		this.attendedHours = attendedHours;
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
	public Boolean getIsMarkSecured() {
		return isMarkSecured;
	}
	public void setIsMarkSecured(Boolean isMarkSecured) {
		this.isMarkSecured = isMarkSecured;
	}
	public Classes getClasses() {
		return classes;
	}
	public void setClasses(Classes classes) {
		this.classes = classes;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

}
