package com.kp.cms.bo.admin;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class SyllabusEntry implements java.io.Serializable{
	private int id;
	private String courseObjective;
	private String learningOutcome;
	private String textBooksAndRefBooks;
	private String freeText;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private Boolean isActive;
	private Set<SyllabusEntryUnitsHours> syllabusEntryUnitsHours=new HashSet<SyllabusEntryUnitsHours>(0);
	private Integer totTeachingHrsPerSem;
	private Integer noOfLectureHrsPerWeek;
	private Integer maxMarks;
	private String credits;
	private Integer batchYear;
	private Boolean sendForApproval;
	private Boolean approved;
	private Subject subject;
	private String recommendedReading;
	private String changeInSyllabus;
	private String remarks;
	private String changeReason;
	private String briefDetalsAboutChange;
	private String briefDetailsExistingSyllabus;
	private String rejectReason;
	private Boolean finalApproval;
	private Boolean hodReject;
	private Boolean finalReject;
	
	
	public Boolean getHodReject() {
		return hodReject;
	}
	public void setHodReject(Boolean hodReject) {
		this.hodReject = hodReject;
	}
	public Boolean getFinalReject() {
		return finalReject;
	}
	public void setFinalReject(Boolean finalReject) {
		this.finalReject = finalReject;
	}
	public Boolean getFinalApproval() {
		return finalApproval;
	}
	public void setFinalApproval(Boolean finalApproval) {
		this.finalApproval = finalApproval;
	}
	public String getRejectReason() {
		return rejectReason;
	}
	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}
	public String getChangeInSyllabus() {
		return changeInSyllabus;
	}
	public void setChangeInSyllabus(String changeInSyllabus) {
		this.changeInSyllabus = changeInSyllabus;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getChangeReason() {
		return changeReason;
	}
	public void setChangeReason(String changeReason) {
		this.changeReason = changeReason;
	}
	public String getBriefDetalsAboutChange() {
		return briefDetalsAboutChange;
	}
	public void setBriefDetalsAboutChange(String briefDetalsAboutChange) {
		this.briefDetalsAboutChange = briefDetalsAboutChange;
	}
	public String getBriefDetailsExistingSyllabus() {
		return briefDetailsExistingSyllabus;
	}
	public void setBriefDetailsExistingSyllabus(String briefDetailsExistingSyllabus) {
		this.briefDetailsExistingSyllabus = briefDetailsExistingSyllabus;
	}
	public String getRecommendedReading() {
		return recommendedReading;
	}
	public void setRecommendedReading(String recommendedReading) {
		this.recommendedReading = recommendedReading;
	}
	public Subject getSubject() {
		return subject;
	}
	public void setSubject(Subject subject) {
		this.subject = subject;
	}
	public Integer getBatchYear() {
		return batchYear;
	}
	public void setBatchYear(Integer batchYear) {
		this.batchYear = batchYear;
	}
	public Boolean getSendForApproval() {
		return sendForApproval;
	}
	public void setSendForApproval(Boolean sendForApproval) {
		this.sendForApproval = sendForApproval;
	}
	public Boolean getApproved() {
		return approved;
	}
	public void setApproved(Boolean approved) {
		this.approved = approved;
	}
	public Integer getTotTeachingHrsPerSem() {
		return totTeachingHrsPerSem;
	}
	public void setTotTeachingHrsPerSem(Integer totTeachingHrsPerSem) {
		this.totTeachingHrsPerSem = totTeachingHrsPerSem;
	}
	public Integer getNoOfLectureHrsPerWeek() {
		return noOfLectureHrsPerWeek;
	}
	public void setNoOfLectureHrsPerWeek(Integer noOfLectureHrsPerWeek) {
		this.noOfLectureHrsPerWeek = noOfLectureHrsPerWeek;
	}
	public Integer getMaxMarks() {
		return maxMarks;
	}
	public void setMaxMarks(Integer maxMarks) {
		this.maxMarks = maxMarks;
	}
	public String getCredits() {
		return credits;
	}
	public void setCredits(String credits) {
		this.credits = credits;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCourseObjective() {
		return courseObjective;
	}
	public void setCourseObjective(String courseObjective) {
		this.courseObjective = courseObjective;
	}
	public String getLearningOutcome() {
		return learningOutcome;
	}
	public void setLearningOutcome(String learningOutcome) {
		this.learningOutcome = learningOutcome;
	}
	public String getTextBooksAndRefBooks() {
		return textBooksAndRefBooks;
	}
	public void setTextBooksAndRefBooks(String textBooksAndRefBooks) {
		this.textBooksAndRefBooks = textBooksAndRefBooks;
	}
	public String getFreeText() {
		return freeText;
	}
	public void setFreeText(String freeText) {
		this.freeText = freeText;
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
	
	public Set<SyllabusEntryUnitsHours> getSyllabusEntryUnitsHours() {
		return syllabusEntryUnitsHours;
	}
	public void setSyllabusEntryUnitsHours(
			Set<SyllabusEntryUnitsHours> syllabusEntryUnitsHours) {
		this.syllabusEntryUnitsHours = syllabusEntryUnitsHours;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	
	public SyllabusEntry(int id, String courseObjective,
			String learningOutcome, String textBooksAndRefBooks,
			String freeText, String createdBy, Date createdDate,
			String modifiedBy, Date lastModifiedDate, Boolean isActive,
			Set<SyllabusEntryUnitsHours> syllabusEntryUnitsHours,
			Integer totTeachingHrsPerSem, Integer noOfLectureHrsPerWeek,
			Integer maxMarks, String credits, Integer batchYear,
			Boolean sendForApproval, Boolean approved, Subject subject,
			String recommendedReading, String changeInSyllabus, String remarks,
			String changeReason, String briefDetalsAboutChange,
			String briefDetailsExistingSyllabus) {
		super();
		this.id = id;
		this.courseObjective = courseObjective;
		this.learningOutcome = learningOutcome;
		this.textBooksAndRefBooks = textBooksAndRefBooks;
		this.freeText = freeText;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.lastModifiedDate = lastModifiedDate;
		this.isActive = isActive;
		this.syllabusEntryUnitsHours = syllabusEntryUnitsHours;
		this.totTeachingHrsPerSem = totTeachingHrsPerSem;
		this.noOfLectureHrsPerWeek = noOfLectureHrsPerWeek;
		this.maxMarks = maxMarks;
		this.credits = credits;
		this.batchYear = batchYear;
		this.sendForApproval = sendForApproval;
		this.approved = approved;
		this.subject = subject;
		this.recommendedReading = recommendedReading;
		this.changeInSyllabus = changeInSyllabus;
		this.remarks = remarks;
		this.changeReason = changeReason;
		this.briefDetalsAboutChange = briefDetalsAboutChange;
		this.briefDetailsExistingSyllabus = briefDetailsExistingSyllabus;
	}
	public SyllabusEntry(){
		
	}
	
}
