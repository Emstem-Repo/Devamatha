package com.kp.cms.bo.exam;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.kp.cms.bo.admin.Subject;
import com.kp.cms.bo.admin.Users;

public class ExamRemunerationDetails implements Serializable{
	
	private int id;
	private ExamValuationRemuneration remuneration;
	private Subject subject;
	private String createdBy;
	private String modifiedBy;
	private Date createdDate;
	private Date lastModifiedDate;
	private Boolean isActive;
	private int totalScripts;
	private String type;
	private Boolean isBoardMeetingApplicable;
	
	
	public ExamRemunerationDetails(){
		
	}
	
	public ExamRemunerationDetails(int id,
			ExamValuationRemuneration remuneration, Subject subject,
			String createdBy, String modifiedBy, Date createdDate,
			Date lastModifiedDate, Boolean isActive, int totalScripts,
			String type,Boolean isBoardMeetingApplicable) {
		super();
		this.id = id;
		this.remuneration = remuneration;
		this.subject = subject;
		this.createdBy = createdBy;
		this.modifiedBy = modifiedBy;
		this.createdDate = createdDate;
		this.lastModifiedDate = lastModifiedDate;
		this.isActive = isActive;
		this.totalScripts = totalScripts;
		this.type = type;
		this.isBoardMeetingApplicable=isBoardMeetingApplicable;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public ExamValuationRemuneration getRemuneration() {
		return remuneration;
	}
	public void setRemuneration(ExamValuationRemuneration remuneration) {
		this.remuneration = remuneration;
	}
	public Subject getSubject() {
		return subject;
	}
	public void setSubject(Subject subject) {
		this.subject = subject;
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
	public int getTotalScripts() {
		return totalScripts;
	}
	public void setTotalScripts(int totalScripts) {
		this.totalScripts = totalScripts;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	public Boolean getIsBoardMeetingApplicable() {
		return isBoardMeetingApplicable;
	}

	public void setIsBoardMeetingApplicable(Boolean isBoardMeetingApplicable) {
		this.isBoardMeetingApplicable = isBoardMeetingApplicable;
	}
	
	
	
	
	
}
