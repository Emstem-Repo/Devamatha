package com.kp.cms.bo.admin;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

public class TTClasses implements Serializable {
	
	private int id;
	private ClassSchemewise classSchemewise;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private Boolean isActive;
	private Boolean isApproved;
	private Set<TTPeriodWeek> ttPeriodWeeks;
	
	public TTClasses() {
		super();
	}
	
	public TTClasses(int id, ClassSchemewise classSchemewise, String createdBy,
			Date createdDate, String modifiedBy, Date lastModifiedDate,
			Boolean isActive, Boolean isApproved) {
		super();
		this.id = id;
		this.classSchemewise = classSchemewise;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.lastModifiedDate = lastModifiedDate;
		this.isActive = isActive;
		this.isApproved = isApproved;
	}


	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public ClassSchemewise getClassSchemewise() {
		return classSchemewise;
	}
	public void setClassSchemewise(ClassSchemewise classSchemewise) {
		this.classSchemewise = classSchemewise;
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
	public Boolean getIsApproved() {
		return isApproved;
	}
	public void setIsApproved(Boolean isApproved) {
		this.isApproved = isApproved;
	}

	public Set<TTPeriodWeek> getTtPeriodWeeks() {
		return ttPeriodWeeks;
	}

	public void setTtPeriodWeeks(Set<TTPeriodWeek> ttPeriodWeeks) {
		this.ttPeriodWeeks = ttPeriodWeeks;
	}
}