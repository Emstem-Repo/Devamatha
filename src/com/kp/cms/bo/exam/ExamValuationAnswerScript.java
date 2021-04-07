package com.kp.cms.bo.exam;

import java.io.Serializable;
import java.util.Date;

import com.kp.cms.bo.admin.Employee;
import com.kp.cms.bo.admin.Subject;

public class ExamValuationAnswerScript implements Serializable{
	
	private int id;
	private ExamValidationDetails validationDetailsId;
	private int numberOfAnswerScripts;
	private Date issueDate;
	private String createdBy;
	private String modifiedBy;
	private Date createdDate;
	private Date lastModifiedDate;
	private Boolean isActive;
	private Boolean challanGenerated;
	
	public ExamValuationAnswerScript(){
		
	}
	
	public ExamValuationAnswerScript(int id,
			ExamValidationDetails validationDetailsId,
			int numberOfAnswerScripts, Date issueDate, String createdBy,
			String modifiedBy, Date createdDate, Date lastModifiedDate,
			Boolean isActive, Boolean challanGenerated) {
		super();
		this.id = id;
		this.validationDetailsId = validationDetailsId;
		this.numberOfAnswerScripts = numberOfAnswerScripts;
		this.issueDate = issueDate;
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
	public ExamValidationDetails getValidationDetailsId() {
		return validationDetailsId;
	}
	public void setValidationDetailsId(ExamValidationDetails validationDetailsId) {
		this.validationDetailsId = validationDetailsId;
	}
	public int getNumberOfAnswerScripts() {
		return numberOfAnswerScripts;
	}
	public void setNumberOfAnswerScripts(int numberOfAnswerScripts) {
		this.numberOfAnswerScripts = numberOfAnswerScripts;
	}
	public Date getIssueDate() {
		return issueDate;
	}
	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
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

	public Boolean getChallanGenerated() {
		return challanGenerated;
	}

	public void setChallanGenerated(Boolean challanGenerated) {
		this.challanGenerated = challanGenerated;
	}
	
	
	
}
