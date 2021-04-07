package com.kp.cms.bo.admin;

import java.util.Date;

public class ConductCertificateDetails implements java.io.Serializable {


	private int id;
	private Student student;
	private CharacterAndConduct characterAndConduct;
	private Date dateOfApplication;
	private String academicYear;
	private String classOfLeaving;
	private String academicDuration;
	private String courseName;
	private Classes classes;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private Boolean isActive;
	private Boolean isAided;
	private String ccNo;
	//private Boolean isKerala;
	
	/*public Boolean getIsKerala() {
		return isKerala;
	}
	public void setIsKerala(Boolean isKerala) {
		this.isKerala = isKerala;
	}*/
	public Boolean getIsAided() {
		return isAided;
	}
	public void setIsAided(Boolean isAided) {
		this.isAided = isAided;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}
	public CharacterAndConduct getCharacterAndConduct() {
		return characterAndConduct;
	}
	public void setCharacterAndConduct(CharacterAndConduct characterAndConduct) {
		this.characterAndConduct = characterAndConduct;
	}
	public Date getDateOfApplication() {
		return dateOfApplication;
	}
	public void setDateOfApplication(Date dateOfApplication) {
		this.dateOfApplication = dateOfApplication;
	}
	public String getAcademicYear() {
		return academicYear;
	}
	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}
	public String getClassOfLeaving() {
		return classOfLeaving;
	}
	public void setClassOfLeaving(String classOfLeaving) {
		this.classOfLeaving = classOfLeaving;
	}
	public String getAcademicDuration() {
		return academicDuration;
	}
	public void setAcademicDuration(String academicDuration) {
		this.academicDuration = academicDuration;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public Classes getClasses() {
		return classes;
	}
	public void setClasses(Classes classes) {
		this.classes = classes;
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
	public String getCcNo() {
		return ccNo;
	}
	public void setCcNo(String ccNo) {
		this.ccNo = ccNo;
	}
	
	
}
