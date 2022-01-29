package com.kp.cms.bo.admin;

import java.io.Serializable;
import java.util.Date;

public class AdmapplnAdditionalInfo implements Serializable {


	private int id;
	private AdmAppln admAppln;
	private String createdBy;
	private String modifiedBy;
	private Date createdDate;
	private Date lastModifiedDate;
	private Date admissionScheduledDate;
	private String admissionScheduledTime;
	private String titleFather;
	private String titleMother;
	private ApplicantFeedback applicantFeedback;
	private Currency internationalApplnFeeCurrency;
	private Boolean backLogs;
	private Boolean isComedk;
	private String commToBeSentTo;
	private Boolean isSpotAdmission;
	private String listName;
	private Boolean isSaypass;

	

	public AdmapplnAdditionalInfo() {
	}

	public AdmapplnAdditionalInfo(int id) {
		this.id = id;
	}

	public AdmapplnAdditionalInfo(int id, SubjectGroup subjectGroup,Boolean isComedk,
			AdmAppln admAppln, String createdBy, String modifiedBy, 
			Date createdDate, Date lastModifiedDate,
			Date admissionScheduledDate,String admissionScheduledTime,
			Boolean backLogs,String commToBeSentTo,
			Boolean isSpotAdmission, String listName) {
		this.id = id;
		this.admAppln = admAppln;
		this.createdBy = createdBy;
		this.modifiedBy = modifiedBy;
		this.createdDate = createdDate;
		this.lastModifiedDate = lastModifiedDate;
		this.admissionScheduledDate=admissionScheduledDate;
		this.admissionScheduledTime=admissionScheduledTime;
		this.backLogs=backLogs;
		this.commToBeSentTo=commToBeSentTo;
		this.isComedk=isComedk;
		this.isSpotAdmission=isSpotAdmission;
		this.listName=listName;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public AdmAppln getAdmAppln() {
		return this.admAppln;
	}

	public void setAdmAppln(AdmAppln admAppln) {
		this.admAppln = admAppln;
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

	public Date getAdmissionScheduledDate() {
		return admissionScheduledDate;
	}

	public void setAdmissionScheduledDate(Date admissionScheduledDate) {
		this.admissionScheduledDate = admissionScheduledDate;
	}

	public String getAdmissionScheduledTime() {
		return admissionScheduledTime;
	}

	public void setAdmissionScheduledTime(String admissionScheduledTime) {
		this.admissionScheduledTime = admissionScheduledTime;
	}

	public String getTitleFather() {
		return titleFather;
	}

	public void setTitleFather(String titleFather) {
		this.titleFather = titleFather;
	}

	public String getTitleMother() {
		return titleMother;
	}

	public void setTitleMother(String titleMother) {
		this.titleMother = titleMother;
	}

	public ApplicantFeedback getApplicantFeedback() {
		return applicantFeedback;
	}

	public void setApplicantFeedback(ApplicantFeedback applicantFeedback) {
		this.applicantFeedback = applicantFeedback;
	}

	public Currency getInternationalApplnFeeCurrency() {
		return internationalApplnFeeCurrency;
	}

	public void setInternationalApplnFeeCurrency(
			Currency internationalApplnFeeCurrency) {
		this.internationalApplnFeeCurrency = internationalApplnFeeCurrency;
	}

	public Boolean getBackLogs() {
		return backLogs;
	}

	public void setBackLogs(Boolean backLogs) {
		this.backLogs = backLogs;
	}
	
	public Boolean getIsComedk() {
		return isComedk;
	}

	public void setIsComedk(Boolean isComedk) {
		this.isComedk = isComedk;
	}

	public String getCommToBeSentTo() {
		return commToBeSentTo;
	}

	public void setCommToBeSentTo(String commToBeSentTo) {
		this.commToBeSentTo = commToBeSentTo;
	}
	
	public Boolean getIsSpotAdmission() {
		return isSpotAdmission;
	}

	public void setIsSpotAdmission(Boolean isSpotAdmission) {
		this.isSpotAdmission = isSpotAdmission;
	}

	public String getListName() {
		return listName;
	}

	public void setListName(String listName) {
		this.listName = listName;
	}

	public Boolean getIsSaypass() {
		return isSaypass;
	}

	public void setIsSaypass(Boolean isSaypass) {
		this.isSaypass = isSaypass;
	}



}
