package com.kp.cms.to.exam;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ExamRevaluationStudentTO implements Serializable {

	private Integer marksEntryId;
	private Integer detailId;
	private int studentId;
	private String regNo;
	private String rolNo;
	private String studentName;
	private String marks;
	private String currentMarks;
	private String previousTheoryMarks;
	private String previousPracticalMarks;
	private String currentTheoryMarks;
	private String currentPracticalMarks;
	private String isTheoryPractical;

	public ExamRevaluationStudentTO() {
		super();
	}

	public ExamRevaluationStudentTO(Integer marksEntryId, Integer detailId,
			int studentId, String studentName, String regNo, String rolNo,
			String marks) {
		super();
		this.marksEntryId = marksEntryId;
		this.detailId = detailId;
		this.studentId = studentId;
		this.studentName = studentName;
		this.regNo = regNo;
		this.rolNo = rolNo;
		this.marks = marks;
	}

	public Integer getMarksEntryId() {
		return marksEntryId;
	}

	public void setMarksEntryId(Integer marksEntryId) {
		this.marksEntryId = marksEntryId;
	}

	public Integer getDetailId() {
		return detailId;
	}

	public void setDetailId(Integer detailId) {
		this.detailId = detailId;
	}

	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}

	public String getRegNo() {
		return regNo;
	}

	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}

	public String getRolNo() {
		return rolNo;
	}

	public void setRolNo(String rolNo) {
		this.rolNo = rolNo;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getMarks() {
		return marks;
	}

	public void setMarks(String marks) {
		this.marks = marks;
	}

	public void setCurrentMarks(String currentMarks) {
		this.currentMarks = currentMarks;
	}

	public String getCurrentMarks() {
		return currentMarks;
	}

	public void setPreviousTheoryMarks(String previousTheoryMarks) {
		this.previousTheoryMarks = previousTheoryMarks;
	}

	public String getPreviousTheoryMarks() {
		return previousTheoryMarks;
	}

	public void setPreviousPracticalMarks(String previousPracticalMarks) {
		this.previousPracticalMarks = previousPracticalMarks;
	}

	public String getPreviousPracticalMarks() {
		return previousPracticalMarks;
	}

	public void setCurrentTheoryMarks(String currentTheoryMarks) {
		this.currentTheoryMarks = currentTheoryMarks;
	}

	public String getCurrentTheoryMarks() {
		return currentTheoryMarks;
	}

	public void setCurrentPracticalMarks(String currentPracticalMarks) {
		this.currentPracticalMarks = currentPracticalMarks;
	}

	public String getCurrentPracticalMarks() {
		return currentPracticalMarks;
	}

	public void setIsTheoryPractical(String isTheoryPractical) {
		this.isTheoryPractical = isTheoryPractical;
	}

	public String getIsTheoryPractical() {
		return isTheoryPractical;
	}

}
