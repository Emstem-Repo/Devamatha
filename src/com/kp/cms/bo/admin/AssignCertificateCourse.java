package com.kp.cms.bo.admin;

import java.util.Date;
import java.util.Set;

public class AssignCertificateCourse implements java.io.Serializable {
	private int id;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private Boolean isActive;
	private String semType;
	private Integer academicYear;
	private Set<AssignCertificateCourseDetails> assignCertificateCourseDetails;
	private Course course;
	
	
	public AssignCertificateCourse() {
		super();
	}
	
	public AssignCertificateCourse(int id, String createdBy, Date createdDate,
			String modifiedBy, Date lastModifiedDate, Boolean isActive,
			String semType, Integer academicYear,
			Set<AssignCertificateCourseDetails> assignCertificateCourseDetails,
			Course course) {
		super();
		this.id = id;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.lastModifiedDate = lastModifiedDate;
		this.isActive = isActive;
		this.semType = semType;
		this.academicYear = academicYear;
		this.assignCertificateCourseDetails = assignCertificateCourseDetails;
		this.course = course;
	}


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
	public Set<AssignCertificateCourseDetails> getAssignCertificateCourseDetails() {
		return assignCertificateCourseDetails;
	}
	public void setAssignCertificateCourseDetails(
			Set<AssignCertificateCourseDetails> assignCertificateCourseDetails) {
		this.assignCertificateCourseDetails = assignCertificateCourseDetails;
	}
	public Course getCourse() {
		return course;
	}
	public void setCourse(Course course) {
		this.course = course;
	}
}
