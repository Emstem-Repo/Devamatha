package com.kp.cms.to.admin;


public class CandidatePrerequisiteMarksTO {
	private int id;
	private int admApplnId;
	private int prerequisiteId;
	private String prerequisiteName; 
	private String prerequisiteMarksObtained;
	private String prerequisiteTotalMarks;
	private String weightageAdjustedMarks;
	private String rollNo;
	private String examMonth;
	private String examYear;
	private String createdBy;
	private String createdDate;
	private String modifiedBy;
	private String lastModifiedDate;
	private boolean isActive;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getAdmApplnId() {
		return admApplnId;
	}
	public void setAdmApplnId(int admApplnId) {
		this.admApplnId = admApplnId;
	}
	public int getPrerequisiteId() {
		return prerequisiteId;
	}
	public void setPrerequisiteId(int prerequisiteId) {
		this.prerequisiteId = prerequisiteId;
	}
	public String getPrerequisiteMarksObtained() {
		return prerequisiteMarksObtained;
	}
	public void setPrerequisiteMarksObtained(String prerequisiteMarksObtained) {
		this.prerequisiteMarksObtained = prerequisiteMarksObtained;
	}
	public String getWeightageAdjustedMarks() {
		return weightageAdjustedMarks;
	}
	public void setWeightageAdjustedMarks(String weightageAdjustedMarks) {
		this.weightageAdjustedMarks = weightageAdjustedMarks;
	}
	public String getRollNo() {
		return rollNo;
	}
	public void setRollNo(String rollNo) {
		this.rollNo = rollNo;
	}
	public String getExamMonth() {
		return examMonth;
	}
	public void setExamMonth(String examMonth) {
		this.examMonth = examMonth;
	}
	public String getExamYear() {
		return examYear;
	}
	public void setExamYear(String examYear) {
		this.examYear = examYear;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public String getLastModifiedDate() {
		return lastModifiedDate;
	}
	public void setLastModifiedDate(String lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	public String getPrerequisiteName() {
		return prerequisiteName;
	}
	public void setPrerequisiteName(String prerequisiteName) {
		this.prerequisiteName = prerequisiteName;
	}
	public String getPrerequisiteTotalMarks() {
		return prerequisiteTotalMarks;
	}
	public void setPrerequisiteTotalMarks(String prerequisiteTotalMarks) {
		this.prerequisiteTotalMarks = prerequisiteTotalMarks;
	}
	
}
