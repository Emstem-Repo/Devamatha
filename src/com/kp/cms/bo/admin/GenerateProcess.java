package com.kp.cms.bo.admin;

import java.util.Date;

public class GenerateProcess implements java.io.Serializable  {
	
	private int id;
	private String createdBy;;
	private String modifiedBy;
	private Date createdDate;
	private Date lastModifiedDate;
	private String email;
	private String status;
	private Integer applnNo;
	private String interviewComments;
	private AdmAppln admAppln;
	private Date dateOfBirth;
	private String templateDescription;
	private String isInterviewSelected;
	private String interviewTime;
	private Date interviewDate;
	
	public GenerateProcess() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public GenerateProcess(int id, String createdBy, String modifiedBy,
			Date createdDate, Date lastModifiedDate, String email,
			String status, Integer applnNo, String interviewComments,
			AdmAppln admAppln,Date dateOfBirth,String templateDescription,String isInterviewSelected) {
		super();
		this.id = id;
		this.createdBy = createdBy;
		this.modifiedBy = modifiedBy;
		this.createdDate = createdDate;
		this.lastModifiedDate = lastModifiedDate;
		this.email = email;
		this.status = status;
		this.applnNo = applnNo;
		this.interviewComments = interviewComments;
		this.admAppln = admAppln;
		this.dateOfBirth=dateOfBirth;
		this.templateDescription=templateDescription;
		this.isInterviewSelected=isInterviewSelected;
	}



	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Integer getApplnNo() {
		return applnNo;
	}
	public void setApplnNo(Integer applnNo) {
		this.applnNo = applnNo;
	}
	public String getInterviewComments() {
		return interviewComments;
	}
	public void setInterviewComments(String interviewComments) {
		this.interviewComments = interviewComments;
	}
	public AdmAppln getAdmAppln() {
		return admAppln;
	}
	public void setAdmAppln(AdmAppln admAppln) {
		this.admAppln = admAppln;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getTemplateDescription() {
		return templateDescription;
	}

	public void setTemplateDescription(String templateDescription) {
		this.templateDescription = templateDescription;
	}

	public String getIsInterviewSelected() {
		return isInterviewSelected;
	}

	public void setIsInterviewSelected(String isInterviewSelected) {
		this.isInterviewSelected = isInterviewSelected;
	}

	public String getInterviewTime() {
		return interviewTime;
	}

	public void setInterviewTime(String interviewTime) {
		this.interviewTime = interviewTime;
	}

	public Date getInterviewDate() {
		return interviewDate;
	}

	public void setInterviewDate(Date interviewDate) {
		this.interviewDate = interviewDate;
	}
	
	
	
}
