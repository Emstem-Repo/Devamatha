package com.kp.cms.to.admission;

import java.util.Date;
import java.util.Map;

public class InterviewTimeSelectionTO implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private int interviewSelectionScheduleId;
	private String time;
	private Boolean isActive;
	private Integer createdBy;
	private Date createdDate;
	private Integer modifiedBy;
	private Date lastModifiedDate;
	private Map<String,String> hoursMap;
	private Map<String,String> minsMap;
	private String hours;
	private String mins;
	private String  maxCandidates;
	private String timeTemplate;
	private String candidatesTemplate;
	private String henceHours;
	private String henceMins;
	private String timeHenceTemplate;
	private String TempHours;
	private String TempMin;
	
	
	public String getTimeHenceTemplate() {
		return timeHenceTemplate;
	}
	public void setTimeHenceTemplate(String timeHenceTemplate) {
		this.timeHenceTemplate = timeHenceTemplate;
	}
	public String getHenceHours() {
		return henceHours;
	}
	public void setHenceHours(String henceHours) {
		this.henceHours = henceHours;
	}
	public String getHenceMins() {
		return henceMins;
	}
	public void setHenceMins(String henceMins) {
		this.henceMins = henceMins;
	}
	public Map<String, String> getHoursMap() {
		return hoursMap;
	}
	public void setHoursMap(Map<String, String> hoursMap) {
		this.hoursMap = hoursMap;
	}
	public Map<String, String> getMinsMap() {
		return minsMap;
	}
	public void setMinsMap(Map<String, String> minsMap) {
		this.minsMap = minsMap;
	}
	public String getHours() {
		return hours;
	}
	public void setHours(String hours) {
		this.hours = hours;
	}
	public String getMins() {
		return mins;
	}
	public void setMins(String mins) {
		this.mins = mins;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public String getMaxCandidates() {
		return maxCandidates;
	}
	public void setMaxCandidates(String maxCandidates) {
		this.maxCandidates = maxCandidates;
	}
	public String getTimeTemplate() {
		return timeTemplate;
	}
	public void setTimeTemplate(String timeTemplate) {
		this.timeTemplate = timeTemplate;
	}
	public String getCandidatesTemplate() {
		return candidatesTemplate;
	}
	public void setCandidatesTemplate(String candidatesTemplate) {
		this.candidatesTemplate = candidatesTemplate;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getInterviewSelectionScheduleId() {
		return interviewSelectionScheduleId;
	}
	public void setInterviewSelectionScheduleId(int interviewSelectionScheduleId) {
		this.interviewSelectionScheduleId = interviewSelectionScheduleId;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public Boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	public Integer getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Integer getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(Integer modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}
	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
	public String getTempHours() {
		return TempHours;
	}
	public void setTempHours(String tempHours) {
		TempHours = tempHours;
	}
	public String getTempMin() {
		return TempMin;
	}
	public void setTempMin(String tempMin) {
		TempMin = tempMin;
	}

}
