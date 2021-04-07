package com.kp.cms.to.admin;

import java.util.List;

public class SyllabusEntryPreviewTo {
	private String courseObjective;
	private String lerningOutcome;
	private String totTeachHrsPerSem;
	private String noOfLectureHrsPerWeek;
	private String credits;
	private String maxMarks;
	private String departmentName;
	private String parentDepartment;
	private String questionBankRequired;
	private String theoryOrPractical;
	private String batchYear;
	private String subjectCode;
	private String subjectName;
	private List<SyllabusEntryUnitsHoursTo> syllabusEntryUnitsHoursTos;
	private String textBooksAndRefBooks;
	private String freeText;
	private String recommendedReading;
	
	
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
	public String getRecommendedReading() {
		return recommendedReading;
	}
	public void setRecommendedReading(String recommendedReading) {
		this.recommendedReading = recommendedReading;
	}
	public String getBatchYear() {
		return batchYear;
	}
	public void setBatchYear(String batchYear) {
		this.batchYear = batchYear;
	}
	public String getSubjectCode() {
		return subjectCode;
	}
	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}
	public String getSubjectName() {
		return subjectName;
	}
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	public String getCourseObjective() {
		return courseObjective;
	}
	public void setCourseObjective(String courseObjective) {
		this.courseObjective = courseObjective;
	}
	public String getLerningOutcome() {
		return lerningOutcome;
	}
	public void setLerningOutcome(String lerningOutcome) {
		this.lerningOutcome = lerningOutcome;
	}
	public String getTotTeachHrsPerSem() {
		return totTeachHrsPerSem;
	}
	public void setTotTeachHrsPerSem(String totTeachHrsPerSem) {
		this.totTeachHrsPerSem = totTeachHrsPerSem;
	}
	public String getNoOfLectureHrsPerWeek() {
		return noOfLectureHrsPerWeek;
	}
	public void setNoOfLectureHrsPerWeek(String noOfLectureHrsPerWeek) {
		this.noOfLectureHrsPerWeek = noOfLectureHrsPerWeek;
	}
	public String getCredits() {
		return credits;
	}
	public void setCredits(String credits) {
		this.credits = credits;
	}
	public String getMaxMarks() {
		return maxMarks;
	}
	public void setMaxMarks(String maxMarks) {
		this.maxMarks = maxMarks;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	public String getParentDepartment() {
		return parentDepartment;
	}
	public void setParentDepartment(String parentDepartment) {
		this.parentDepartment = parentDepartment;
	}
	public String getQuestionBankRequired() {
		return questionBankRequired;
	}
	public void setQuestionBankRequired(String questionBankRequired) {
		this.questionBankRequired = questionBankRequired;
	}
	public String getTheoryOrPractical() {
		return theoryOrPractical;
	}
	public void setTheoryOrPractical(String theoryOrPractical) {
		this.theoryOrPractical = theoryOrPractical;
	}
	public List<SyllabusEntryUnitsHoursTo> getSyllabusEntryUnitsHoursTos() {
		return syllabusEntryUnitsHoursTos;
	}
	public void setSyllabusEntryUnitsHoursTos(
			List<SyllabusEntryUnitsHoursTo> syllabusEntryUnitsHoursTos) {
		this.syllabusEntryUnitsHoursTos = syllabusEntryUnitsHoursTos;
	}
	
	
}
