package com.kp.cms.bo.exam;

import java.math.BigDecimal;
import java.util.Date;

public class ExamMidsemRepeatSetting implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int id;
	private ExamDefinitionBO midSemExamId;
	private Date applnStartDate;
	private Date applnEndDate;
	private Date feesEndDate;
	private BigDecimal feesPerSubject;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private Boolean isActive;
	
	public ExamMidsemRepeatSetting() {
		
	}
	
	public ExamMidsemRepeatSetting(int id,ExamDefinitionBO midSemExamId,Date applnStartDate,Date applnEndDate,Date feesEndDate, BigDecimal feesPerSubject,
	                              String createdBy, Date createdDate, String modifiedBy, Date lastModifiedDate, Boolean isActive){
		super();
		this.id = id;
		this.midSemExamId = midSemExamId;
		this.applnStartDate = applnStartDate;
		this.applnEndDate=applnEndDate;
		this.feesEndDate=feesEndDate;
		this.feesPerSubject=feesPerSubject;
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

	public ExamDefinitionBO getMidSemExamId() {
		return midSemExamId;
	}

	public void setMidSemExamId(ExamDefinitionBO midSemExamId) {
		this.midSemExamId = midSemExamId;
	}

	public Date getApplnStartDate() {
		return applnStartDate;
	}

	public void setApplnStartDate(Date applnStartDate) {
		this.applnStartDate = applnStartDate;
	}

	public Date getApplnEndDate() {
		return applnEndDate;
	}

	public void setApplnEndDate(Date applnEndDate) {
		this.applnEndDate = applnEndDate;
	}

	public Date getFeesEndDate() {
		return feesEndDate;
	}

	public void setFeesEndDate(Date feesEndDate) {
		this.feesEndDate = feesEndDate;
	}

	public BigDecimal getFeesPerSubject() {
		return feesPerSubject;
	}

	public void setFeesPerSubject(BigDecimal feesPerSubject) {
		this.feesPerSubject = feesPerSubject;
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

}
