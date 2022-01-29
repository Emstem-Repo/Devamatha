package com.kp.cms.bo.admin;

import java.util.Date;

public class ActivityLearning implements java.io.Serializable {
	
	
	private String name;
	private int id;
	private String createdBy;;
	private String modifiedBy;
	private Date createdDate;
	private Date lastModifiedDate;
	private boolean isActive;
	private int appliedYear;
	private Course course;
	private String minMark;
	private String maxMark;
	private String creditInfo;
	
	private ExtraCreditActivityType extraCreditActivityType;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public ExtraCreditActivityType getExtraCreditActivityType() {
		return extraCreditActivityType;
	}
	public void setExtraCreditActivityType(
			ExtraCreditActivityType extraCreditActivityType) {
		this.extraCreditActivityType = extraCreditActivityType;
	}
	public int getAppliedYear() {
		return appliedYear;
	}
	public void setAppliedYear(int appliedYear) {
		this.appliedYear = appliedYear;
	}
	public Course getCourse() {
		return course;
	}
	public void setCourse(Course course) {
		this.course = course;
	}
	public String getMinMark() {
		return minMark;
	}
	public void setMinMark(String minMark) {
		this.minMark = minMark;
	}
	public String getMaxMark() {
		return maxMark;
	}
	public void setMaxMark(String maxMark) {
		this.maxMark = maxMark;
	}
	public String getCreditInfo() {
		return creditInfo;
	}
	public void setCreditInfo(String creditInfo) {
		this.creditInfo = creditInfo;
	}
	
	
	
	

}
