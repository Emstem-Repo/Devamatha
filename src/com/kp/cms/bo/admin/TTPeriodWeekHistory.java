package com.kp.cms.bo.admin;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

public class TTPeriodWeekHistory implements Serializable {
	
	private int id;
	private Period period;
	private TTClassesHistory ttClassesHistory;
	private String weekDay;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private Boolean isActive;
    private Set<TTSubjectBatchHistory> ttSubjectBatchsHistory;
    
	public TTPeriodWeekHistory() {
		super();
	}

	public TTPeriodWeekHistory(int id, Period period, TTClassesHistory ttClasses,
			String weekDay, String createdBy, Date createdDate,
			String modifiedBy, Date lastModifiedDate, Boolean isActive) {
		super();
		this.id = id;
		this.period = period;
		this.ttClassesHistory = ttClasses;
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

	public TTClassesHistory getTtClassesHistory() {
		return ttClassesHistory;
	}

	public void setTtClassesHistory(TTClassesHistory ttClassesHistory) {
		this.ttClassesHistory = ttClassesHistory;
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

	public Set<TTSubjectBatchHistory> getTtSubjectBatchsHistory() {
		return ttSubjectBatchsHistory;
	}

	public void setTtSubjectBatchsHistory(
			Set<TTSubjectBatchHistory> ttSubjectBatchsHistory) {
		this.ttSubjectBatchsHistory = ttSubjectBatchsHistory;
	}
}