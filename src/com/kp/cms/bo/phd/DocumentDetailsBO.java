package com.kp.cms.bo.phd;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class DocumentDetailsBO implements Serializable {
	
	private int id;
	private String documentName;
	private int submissionOrder;
	private BigDecimal guidesFees;
	private String createdBy;
	private String modifiedBy;
	private Date createdDate;
	private Date lastModifiedDate;
	private Boolean isActive;
	
	
	public DocumentDetailsBO(){
		
	}
    public DocumentDetailsBO(int id, String documentName,int submissionOrder,BigDecimal guidesFees, 
    		String createdBy, String modifiedBy, Date createdDate,Date lastModifiedDate,Boolean isActive)
      {
    	this.id=id;
    	this.documentName=documentName;
    	this.submissionOrder=submissionOrder;
    	this.guidesFees=guidesFees;
    	this.createdBy=createdBy;
    	this.modifiedBy=modifiedBy;
    	this.createdDate=createdDate;
    	this.lastModifiedDate=lastModifiedDate;
    	this.isActive=isActive;
		
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDocumentName() {
		return documentName;
	}
	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}
	public int getSubmissionOrder() {
		return submissionOrder;
	}
	public void setSubmissionOrder(int submissionOrder) {
		this.submissionOrder = submissionOrder;
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
	public BigDecimal getGuidesFees() {
		return guidesFees;
	}
	public void setGuidesFees(BigDecimal guidesFees) {
		this.guidesFees = guidesFees;
	}
	

}
