package com.kp.cms.bo.admin;

import java.util.Date;

public class AttendanceCondonation {
	
	private int id;
	private Student student;
	private Classes classes;
	private Integer semister;
	private Double previousPercentage;
	private Double addedPercentage;
	private Double totalPercentage;
	private String createdBy;
	private String modifiedBy;
	private Date createDate;
	private Date modifiedDate;
	private Boolean isActive;
	
	
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
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public void setClasses(Classes classes) {
		this.classes = classes;
	}
	public Classes getClasses() {
		return classes;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	public Boolean getIsActive() {
		return isActive;
	}
	public void setSemister(Integer semister) {
		this.semister = semister;
	}
	public Integer getSemister() {
		return semister;
	}
	public void setPreviousPercentage(Double previousPercentage) {
		this.previousPercentage = previousPercentage;
	}
	public Double getPreviousPercentage() {
		return previousPercentage;
	}
	public void setAddedPercentage(Double addedPercentage) {
		this.addedPercentage = addedPercentage;
	}
	public Double getAddedPercentage() {
		return addedPercentage;
	}
	public void setTotalPercentage(Double totalPercentage) {
		this.totalPercentage = totalPercentage;
	}
	public Double getTotalPercentage() {
		return totalPercentage;
	}
	

}
