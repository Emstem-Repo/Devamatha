package com.kp.cms.to.admin;

import java.io.Serializable;

public class SyllabusUploadTo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String academicYear;
	private String subjectCode;
	private String subjectName;
	private String teachingHoursPerSem;
	private String teachingHoursPerWeek;
	private String courseDescription;
	private String learningOutcome;
	private String textBook;
	private String unitNo;
	private String teachingHours;
	private String headings;
	private String contents;
	private int syllabusEntryNo;
	
	
	
	public int getSyllabusEntryNo() {
		return syllabusEntryNo;
	}
	public void setSyllabusEntryNo(int syllabusEntryNo) {
		this.syllabusEntryNo = syllabusEntryNo;
	}
	public String getAcademicYear() {
		return academicYear;
	}
	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
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
	public String getTeachingHoursPerSem() {
		return teachingHoursPerSem;
	}
	public void setTeachingHoursPerSem(String teachingHoursPerSem) {
		this.teachingHoursPerSem = teachingHoursPerSem;
	}
	public String getTeachingHoursPerWeek() {
		return teachingHoursPerWeek;
	}
	public void setTeachingHoursPerWeek(String teachingHoursPerWeek) {
		this.teachingHoursPerWeek = teachingHoursPerWeek;
	}
	public String getCourseDescription() {
		return courseDescription;
	}
	public void setCourseDescription(String courseDescription) {
		this.courseDescription = courseDescription;
	}
	public String getLearningOutcome() {
		return learningOutcome;
	}
	public void setLearningOutcome(String learningOutcome) {
		this.learningOutcome = learningOutcome;
	}
	public String getTextBook() {
		return textBook;
	}
	public void setTextBook(String textBook) {
		this.textBook = textBook;
	}
	public String getUnitNo() {
		return unitNo;
	}
	public void setUnitNo(String unitNo) {
		this.unitNo = unitNo;
	}
	public String getTeachingHours() {
		return teachingHours;
	}
	public void setTeachingHours(String teachingHours) {
		this.teachingHours = teachingHours;
	}
	public String getHeadings() {
		return headings;
	}
	public void setHeadings(String headings) {
		this.headings = headings;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	
	

}
