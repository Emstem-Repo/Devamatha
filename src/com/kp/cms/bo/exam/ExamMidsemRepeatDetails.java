package com.kp.cms.bo.exam;

import java.math.BigDecimal;
import java.util.Date;

import com.kp.cms.bo.admin.Subject;

public class ExamMidsemRepeatDetails implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private ExamMidsemRepeat examMidsemRepeat;
	private Subject subject;
	private Boolean isApplied;
	private BigDecimal amount;
	private Boolean isApproved;
	private Float attenPercent;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private Boolean isActive;
	private Boolean isRejected;
	
	public ExamMidsemRepeatDetails() {
		
	}
	
	public ExamMidsemRepeatDetails(int id,ExamMidsemRepeat examMidsemRepeat, Subject subject,
	Boolean isApplied, BigDecimal amount,Float attenPercent,
	Boolean isApproved,String createdBy, Date createdDate, Boolean isActive, 
	String modifiedBy, Date lastModifiedDate, Boolean isRejected){
		super();
		this.id = id;
		this.examMidsemRepeat = examMidsemRepeat;
		this.subject = subject;
		this.isApplied=isApplied;
		this.amount=amount;
		this.isApproved=isApproved;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.isActive = isActive;
		this.attenPercent=attenPercent;
		this.modifiedBy=modifiedBy;
		this.lastModifiedDate=lastModifiedDate;
		this.isRejected=isRejected;
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ExamMidsemRepeat getExamMidsemRepeat() {
		return examMidsemRepeat;
	}

	public void setExamMidsemRepeat(ExamMidsemRepeat examMidsemRepeat) {
		this.examMidsemRepeat = examMidsemRepeat;
	}

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	public Boolean getIsApplied() {
		return isApplied;
	}

	public void setIsApplied(Boolean isApplied) {
		this.isApplied = isApplied;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Boolean getIsApproved() {
		return isApproved;
	}

	public void setIsApproved(Boolean isApproved) {
		this.isApproved = isApproved;
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

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Float getAttenPercent() {
		return attenPercent;
	}

	public void setAttenPercent(Float attenPercent) {
		this.attenPercent = attenPercent;
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

	public Boolean getIsRejected() {
		return isRejected;
	}

	public void setIsRejected(Boolean isRejected) {
		this.isRejected = isRejected;
	}

	
	
}
