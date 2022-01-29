package com.kp.cms.to.exam;

import java.io.Serializable;

public class NewStudentMarkCorrectionTo implements Serializable {
	
	private int studentId;
	private int subjectId;
	private String subjectName;
	private String subjectCode;
	private String theoryMarks;
	private String oldTheoryMarks;
	private String practicalMarks;
	private String oldPracticalMarks;
	private Boolean isOldMarks;
	private int marksEntryId;
	private int marksEntryDetailsId;
	private int internalOverAllId;
	private int regularOverAllId;
	private String mistake;
	private String retest;
	private String comments;
	private Boolean isTheory;
	private Boolean isPractical;
	private String gracing;
	

	public int getStudentId() {
		return studentId;
	}
	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}
	public int getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(int subjectId) {
		this.subjectId = subjectId;
	}
	public String getSubjectName() {
		return subjectName;
	}
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	public String getTheoryMarks() {
		return theoryMarks;
	}
	public void setTheoryMarks(String theoryMarks) {
		this.theoryMarks = theoryMarks;
	}
	public String getPracticalMarks() {
		return practicalMarks;
	}
	public void setPracticalMarks(String practicalMarks) {
		this.practicalMarks = practicalMarks;
	}
	public int getMarksEntryId() {
		return marksEntryId;
	}
	public void setMarksEntryId(int marksEntryId) {
		this.marksEntryId = marksEntryId;
	}
	public int getInternalOverAllId() {
		return internalOverAllId;
	}
	public void setInternalOverAllId(int internalOverAllId) {
		this.internalOverAllId = internalOverAllId;
	}
	public int getRegularOverAllId() {
		return regularOverAllId;
	}
	public void setRegularOverAllId(int regularOverAllId) {
		this.regularOverAllId = regularOverAllId;
	}
	public String getMistake() {
		return mistake;
	}
	public void setMistake(String mistake) {
		this.mistake = mistake;
	}
	public String getRetest() {
		return retest;
	}
	public void setRetest(String retest) {
		this.retest = retest;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getOldTheoryMarks() {
		return oldTheoryMarks;
	}
	public void setOldTheoryMarks(String oldTheoryMarks) {
		this.oldTheoryMarks = oldTheoryMarks;
	}
	public String getOldPracticalMarks() {
		return oldPracticalMarks;
	}
	public void setOldPracticalMarks(String oldPracticalMarks) {
		this.oldPracticalMarks = oldPracticalMarks;
	}
	public Boolean getIsOldMarks() {
		return isOldMarks;
	}
	public void setIsOldMarks(Boolean isOldMarks) {
		this.isOldMarks = isOldMarks;
	}
	public Boolean getIsTheory() {
		return isTheory;
	}
	public void setIsTheory(Boolean isTheory) {
		this.isTheory = isTheory;
	}
	public Boolean getIsPractical() {
		return isPractical;
	}
	public void setIsPractical(Boolean isPractical) {
		this.isPractical = isPractical;
	}
	public int getMarksEntryDetailsId() {
		return marksEntryDetailsId;
	}
	public void setMarksEntryDetailsId(int marksEntryDetailsId) {
		this.marksEntryDetailsId = marksEntryDetailsId;
	}
	public String getSubjectCode() {
		return subjectCode;
	}
	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}
	public String getGracing() {
		return gracing;
	}
	public void setGracing(String gracing) {
		this.gracing = gracing;
	}
}