package com.kp.cms.to.admission;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class AssignCertificateCourseTO implements Serializable{
	
	private int id;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private Boolean isActive;
	private String semType;
	private Integer academicYear;
	private String courseName;
	private List<AssignCertificateCourseDetailsTO> assignCertificateCourseDetailsTOs;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
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
	public String getSemType() {
		return semType;
	}
	public void setSemType(String semType) {
		this.semType = semType;
	}
	public Integer getAcademicYear() {
		return academicYear;
	}
	public void setAcademicYear(Integer academicYear) {
		this.academicYear = academicYear;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public List<AssignCertificateCourseDetailsTO> getAssignCertificateCourseDetailsTOs() {
		return assignCertificateCourseDetailsTOs;
	}
	public void setAssignCertificateCourseDetailsTOs(
			List<AssignCertificateCourseDetailsTO> assignCertificateCourseDetailsTOs) {
		this.assignCertificateCourseDetailsTOs = assignCertificateCourseDetailsTOs;
	}
}
