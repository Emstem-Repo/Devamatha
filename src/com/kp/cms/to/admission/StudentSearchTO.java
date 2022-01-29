package com.kp.cms.to.admission;

import java.io.Serializable;

public class StudentSearchTO implements Serializable{
	
	private String applicationId;

	private String applicantName;

	private String applicantDOB;
	
	private String applicantTotalWeightage;
	
	private String admApplnId;
	
	private String interviewSelectedId ;
	
	private String email;
	
    private String preRequisiteType;
	
	private String prerequisiteMarks;
	
	private int inWaitingList;
	
	private String applicantGender;
	
	private String interviewProgCrsId;
	
	private String selectedCrsId;
	private String selectedCrsName;
	private String examCenterName;
	private String journalNo;
	private String status;
	private String shortStatus;
	private Boolean photoAvaialble;

	public String getAdmApplnId() {
		return admApplnId;
	}

	public void setAdmApplnId(String admApplnId) {
		this.admApplnId = admApplnId;
	}

	public String getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}

	public String getApplicantName() {
		return applicantName;
	}

	public void setApplicantName(String applicantName) {
		this.applicantName = applicantName;
	}

	public String getApplicantDOB() {
		return applicantDOB;
	}

	public void setApplicantDOB(String applicantDOB) {
		this.applicantDOB = applicantDOB;
	}

	public String getApplicantTotalWeightage() {
		return applicantTotalWeightage;
	}

	public void setApplicantTotalWeightage(String applicantTotalWeightage) {
		this.applicantTotalWeightage = applicantTotalWeightage;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getInterviewSelectedId() {
		return interviewSelectedId;
	}

	public void setInterviewSelectedId(String interviewSelectedId) {
		this.interviewSelectedId = interviewSelectedId;
	}

	public String getPreRequisiteType() {
		return preRequisiteType;
	}

	public void setPreRequisiteType(String preRequisiteType) {
		this.preRequisiteType = preRequisiteType;
	}

	public String getPrerequisiteMarks() {
		return prerequisiteMarks;
	}

	public void setPrerequisiteMarks(String prerequisiteMarks) {
		this.prerequisiteMarks = prerequisiteMarks;
	}

	public int getInWaitingList() {
		return inWaitingList;
	}

	public void setInWaitingList(int inWaitingList) {
		this.inWaitingList = inWaitingList;
	}

	public String getApplicantGender() {
		return applicantGender;
	}

	public void setApplicantGender(String applicantGender) {
		this.applicantGender = applicantGender;
	}

	public String getInterviewProgCrsId() {
		return interviewProgCrsId;
	}

	public void setInterviewProgCrsId(String interviewProgCrsId) {
		this.interviewProgCrsId = interviewProgCrsId;
	}

	public String getSelectedCrsId() {
		return selectedCrsId;
	}

	public void setSelectedCrsId(String selectedCrsId) {
		this.selectedCrsId = selectedCrsId;
	}

	public String getSelectedCrsName() {
		return selectedCrsName;
	}

	public void setSelectedCrsName(String selectedCrsName) {
		this.selectedCrsName = selectedCrsName;
	}

	public String getExamCenterName() {
		return examCenterName;
	}

	public void setExamCenterName(String examCenterName) {
		this.examCenterName = examCenterName;
	}

	public String getJournalNo() {
		return journalNo;
	}

	public void setJournalNo(String journalNo) {
		this.journalNo = journalNo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getShortStatus() {
		return shortStatus;
	}

	public void setShortStatus(String shortStatus) {
		this.shortStatus = shortStatus;
	}

	public Boolean getPhotoAvaialble() {
		return photoAvaialble;
	}

	public void setPhotoAvaialble(Boolean photoAvaialble) {
		this.photoAvaialble = photoAvaialble;
	}

	

	

	
}
