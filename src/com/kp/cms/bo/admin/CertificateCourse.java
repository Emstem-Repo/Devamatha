package com.kp.cms.bo.admin;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class CertificateCourse implements Serializable{
	private int id;
	private String certificateCourseName;
	private Integer maxIntake;
	private Users users;
	private String startTime;
	private String endTime;
	private String venue;
	private String createdBy;
	private String modifiedBy;
	private Date createdDate;
	private Date lastModifiedDate;
	private Boolean isActive;
	private Integer year;
	private String semType;
	private Set<StudentCertificateCourse> studentCertificateCourses = new HashSet<StudentCertificateCourse>(0);
//	private Set<Subject> subject=new HashSet<Subject>(0);
	private Subject subject=new Subject();
	private Boolean extracurricular;
	private Set<CertificateCourseGroup> groups;
	private String description;
	
	public CertificateCourse() {
		
	}


	public Boolean getExtracurricular() {
		return extracurricular;
	}


	public void setExtracurricular(Boolean extracurricular) {
		this.extracurricular = extracurricular;
	}


	public CertificateCourse(int id, String certificateCourseName, Users users,
			String startTime, String endTime, String venue, String createdBy,
			String modifiedBy, Date createdDate, Date lastModifiedDate,
			Boolean isActive, Integer maxIntake,Integer year,String semType,Boolean extracurricular,String description) {
		super();
		this.id = id;
		this.certificateCourseName = certificateCourseName;
		this.users = users;
		this.startTime = startTime;
		this.endTime = endTime;
		this.venue = venue;
		this.createdBy = createdBy;
		this.modifiedBy = modifiedBy;
		this.createdDate = createdDate;
		this.lastModifiedDate = lastModifiedDate;
		this.isActive = isActive;
		this.maxIntake = maxIntake;
		this.year=year;
		this.semType=semType;
		this.extracurricular=extracurricular;
		this.description=description;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getCertificateCourseName() {
		return certificateCourseName;
	}


	public void setCertificateCourseName(String certificateCourseName) {
		this.certificateCourseName = certificateCourseName;
	}


	public Users getUsers() {
		return users;
	}


	public void setUsers(Users users) {
		this.users = users;
	}


	public String getStartTime() {
		return startTime;
	}


	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}


	public String getEndTime() {
		return endTime;
	}


	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}


	public String getVenue() {
		return venue;
	}


	public void setVenue(String venue) {
		this.venue = venue;
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


	public Integer getMaxIntake() {
		return maxIntake;
	}


	public void setMaxIntake(Integer maxIntake) {
		this.maxIntake = maxIntake;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}


	public String getSemType() {
		return semType;
	}


	public void setSemType(String semType) {
		this.semType = semType;
	}


	public Set<StudentCertificateCourse> getStudentCertificateCourses() {
		return studentCertificateCourses;
	}


	public void setStudentCertificateCourses(
			Set<StudentCertificateCourse> studentCertificateCourses) {
		this.studentCertificateCourses = studentCertificateCourses;
	}


	public Subject getSubject() {
		return subject;
	}


	public void setSubject(Subject subject) {
		this.subject = subject;
	}


	public Set<CertificateCourseGroup> getGroups() {
		return groups;
	}


	public void setGroups(Set<CertificateCourseGroup> groups) {
		this.groups = groups;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


//	public Set<Subject> getSubject() {
//		return subject;
//	}
//
//
//	public void setSubject(Set<Subject> subject) {
//		this.subject = subject;
//	}
}
