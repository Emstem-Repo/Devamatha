package com.kp.cms.bo.admin;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

public class TTPeriodWeek implements Serializable {
	
	private int id;
	private Period period;
	private TTClasses ttClasses;
	private String weekDay;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private Boolean isActive;
    private Set<TTSubjectBatch> ttSubjectBatchs;
    
	public TTPeriodWeek() {
		super();
	}

	public TTPeriodWeek(int id, Period period, TTClasses ttClasses,
			String weekDay, String createdBy, Date createdDate,
			String modifiedBy, Date lastModifiedDate, Boolean isActive) {
		super();
		this.id = id;
		this.period = period;
		this.ttClasses = ttClasses;
		this.weekDay = weekDay;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.lastModifiedDate = lastModifiedDate;
		this.isActive = isActive;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Period getPeriod() {
		return period;
	}

	public void setPeriod(Period period) {
		this.period = period;
	}

	public TTClasses getTtClasses() {
		return ttClasses;
	}

	public void setTtClasses(TTClasses ttClasses) {
		this.ttClasses = ttClasses;
	}

	public String getWeekDay() {
		return weekDay;
	}

	public void setWeekDay(String weekDay) {
		this.weekDay = weekDay;
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

	public Set<TTSubjectBatch> getTtSubjectBatchs() {
		return ttSubjectBatchs;
	}

	public void setTtSubjectBatchs(Set<TTSubjectBatch> ttSubjectBatchs) {
		this.ttSubjectBatchs = ttSubjectBatchs;
	}
}