package com.kp.cms.to.exam;

import java.io.Serializable;

/**
 * Feb 6, 2010 Created By 9Elements Team
 */
@SuppressWarnings("serial")
public class ExamRevaluationDetailsTO implements Serializable {

	private Integer id;
	private Integer examMasterId;
	private int subjectId;
	private String subjectName;
	private String theoryMarks;
	private String practicalMarks;
	private String isTheoryPractical;
	private String examCode;
	private String currentTheoryMarks;
	private String currentPracticalMarks;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public void setExamMasterId(Integer examMasterid) {
		this.examMasterId = examMasterid;
	}

	public Integer getExamMasterId() {
		return examMasterId;
	}

	public void setIsTheoryPractical(String isTheoryPractical) {
		this.isTheoryPractical = isTheoryPractical;
	}

	public String getIsTheoryPractical() {
		return isTheoryPractical;
	}

	public String getExamCode() {
		return examCode;
	}

	public void setExamCode(String examCode) {
		this.examCode = examCode;
	}

	 public String getCurrentTheoryMarks() {
	 return currentTheoryMarks;
	 }
	
	 public void setCurrentTheoryMarks(String currentTheoryMarks) {
	 this.currentTheoryMarks = currentTheoryMarks;
	 }
	
	 public String getCurrentPracticalMarks() {
	 return currentPracticalMarks;
	 }
	
	 public void setCurrentPracticalMarks(String currentPracticalMarks) {
	 this.currentPracticalMarks = currentPracticalMarks;
	 }

}
