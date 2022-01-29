package com.kp.cms.bo.employee;

import java.io.Serializable;
import java.util.Date;

import com.kp.cms.bo.admin.Employee;
import com.kp.cms.bo.admin.QualificationLevelBO;

public class GuestEducationalDetails implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String course;
	private String specialization;
	private String grade;
	private int yearOfCompletion;
	private String institute;
	private boolean active;
	private QualificationLevelBO empQualificationLevel;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	private GuestFaculty guest;
	
	
	public GuestEducationalDetails(int id) {
		super();
		this.id = id;
	}
	public GuestEducationalDetails() {
		super();
	}
	public GuestEducationalDetails(int id, String course,
			String specialization, String grade, int yearOfCompletion,
			String institute, boolean active,
			QualificationLevelBO empQualificationLevel, String createdBy,
			Date createdDate, String modifiedBy, Date modifiedDate,
			GuestFaculty guest) {
		super();
		this.id = id;
		this.course = course;
		this.specialization = specialization;
		this.grade = grade;
		this.yearOfCompletion = yearOfCompletion;
		this.institute = institute;
		this.active = active;
		this.empQualificationLevel = empQualificationLevel;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
		this.guest = guest;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCourse() {
		return course;
	}
	public void setCourse(String course) {
		this.course = course;
	}
	public String getSpecialization() {
		return specialization;
	}
	public void setSpecialization(String specialization) {
		this.specialization = specialization;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public int getYearOfCompletion() {
		return yearOfCompletion;
	}
	public void setYearOfCompletion(int yearOfCompletion) {
		this.yearOfCompletion = yearOfCompletion;
	}
	public String getInstitute() {
		return institute;
	}
	public void setInstitute(String institute) {
		this.institute = institute;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public QualificationLevelBO getEmpQualificationLevel() {
		return empQualificationLevel;
	}
	public void setEmpQualificationLevel(QualificationLevelBO empQualificationLevel) {
		this.empQualificationLevel = empQualificationLevel;
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
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public GuestFaculty getGuest() {
		return guest;
	}
	public void setGuest(GuestFaculty guest) {
		this.guest = guest;
	}
}
