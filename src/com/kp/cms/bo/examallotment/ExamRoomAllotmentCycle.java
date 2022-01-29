package com.kp.cms.bo.examallotment;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class ExamRoomAllotmentCycle implements Serializable{
	
	private int id;
	private String cycle;
	private String midOrEnd;
	private String session;
	private String createdBy;
	private String modifiedBy;
	private Date createdDate;
	private Date lastModifiedDate;
	private Boolean isActive;
	private ExaminationSessions examinationSessions;
	
	
	
	public ExamRoomAllotmentCycle() {
		
	}
	public ExamRoomAllotmentCycle(int id, String cycle, String midOrEnd,
			String session,
			
			String createdBy, String modifiedBy, Date createdDate,
			Date lastModifiedDate, Boolean isActive) {
		super();
		this.id = id;
		this.cycle = cycle;
		this.midOrEnd = midOrEnd;
		this.session = session;
		this.createdBy = createdBy;
		this.modifiedBy = modifiedBy;
		this.createdDate = createdDate;
		this.lastModifiedDate = lastModifiedDate;
		this.isActive = isActive;
	}
	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public String getCycle() {
		return cycle;
	}



	public void setCycle(String cycle) {
		this.cycle = cycle;
	}



	public String getMidOrEnd() {
		return midOrEnd;
	}



	public void setMidOrEnd(String midOrEnd) {
		this.midOrEnd = midOrEnd;
	}
	public String getSession() {
		return session;
	}



	public void setSession(String session) {
		this.session = session;
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
	public ExaminationSessions getExaminationSessions() {
		return examinationSessions;
	}
	public void setExaminationSessions(ExaminationSessions examinationSessions) {
		this.examinationSessions = examinationSessions;
	}
	
	
	
}
