package com.kp.cms.bo.examallotment;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class ExaminationSessions implements Serializable{
	
	private int id;
	private String session;
	private String sessionDescription;
	private Integer orderNO;
	private String timings;
	private String createdBy;
	private String modifiedBy;
	private Date createdDate;
	private Date lastModifiedDate;
	private Boolean isActive;
	
	
	public Integer getOrderNO() {
		return orderNO;
	}
	public void setOrderNO(Integer orderNO) {
		this.orderNO = orderNO;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSession() {
		return session;
	}
	public void setSession(String session) {
		this.session = session;
	}
	public String getSessionDescription() {
		return sessionDescription;
	}
	public void setSessionDescription(String sessionDescription) {
		this.sessionDescription = sessionDescription;
	}
	public String getTimings() {
		return timings;
	}
	public void setTimings(String timings) {
		this.timings = timings;
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
	public ExaminationSessions(){
		
	}
	public ExaminationSessions(int id, String session,
			String sessionDescription, Integer orderNO, String timings,
			String createdBy, String modifiedBy, Date createdDate,
			Date lastModifiedDate, Boolean isActive) {
		super();
		this.id = id;
		this.session = session;
		this.sessionDescription = sessionDescription;
		this.orderNO = orderNO;
		this.timings = timings;
		this.createdBy = createdBy;
		this.modifiedBy = modifiedBy;
		this.createdDate = createdDate;
		this.lastModifiedDate = lastModifiedDate;
		this.isActive = isActive;
	}
	
	
}
