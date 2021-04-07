package com.kp.cms.to.exam;

import java.io.Serializable;

public class SubjectMarksTO implements Serializable {
	
	private int subjectId;
	private String theoryRegMin;
	private String practicalRegMin;
	private String theoryIntMin;
	private String practicalIntMin;
	private String finalTheoryMin;
	private String finalPracticalMin;
	private String finalTheoryMarks;
	private String finalPracticalMarks;
	private String subjectFinalMinimum;
	
	public String getTheoryRegMin() {
		return theoryRegMin;
	}
	public void setTheoryRegMin(String theoryRegMin) {
		this.theoryRegMin = theoryRegMin;
	}
	public String getPracticalRegMin() {
		return practicalRegMin;
	}
	public void setPracticalRegMin(String practicalRegMin) {
		this.practicalRegMin = practicalRegMin;
	}
	public String getTheoryIntMin() {
		return theoryIntMin;
	}
	public void setTheoryIntMin(String theoryIntMin) {
		this.theoryIntMin = theoryIntMin;
	}
	public String getPracticalIntMin() {
		return practicalIntMin;
	}
	public void setPracticalIntMin(String practicalIntMin) {
		this.practicalIntMin = practicalIntMin;
	}
	public int getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(int subjectId) {
		this.subjectId = subjectId;
	}
	public String getFinalTheoryMin() {
		return finalTheoryMin;
	}
	public void setFinalTheoryMin(String finalTheoryMin) {
		this.finalTheoryMin = finalTheoryMin;
	}
	public String getFinalPracticalMin() {
		return finalPracticalMin;
	}
	public void setFinalPracticalMin(String finalPracticalMin) {
		this.finalPracticalMin = finalPracticalMin;
	}
	public String getFinalTheoryMarks() {
		return finalTheoryMarks;
	}
	public void setFinalTheoryMarks(String finalTheoryMarks) {
		this.finalTheoryMarks = finalTheoryMarks;
	}
	public String getFinalPracticalMarks() {
		return finalPracticalMarks;
	}
	public void setFinalPracticalMarks(String finalPracticalMarks) {
		this.finalPracticalMarks = finalPracticalMarks;
	}
	public String getSubjectFinalMinimum() {
		return subjectFinalMinimum;
	}
	public void setSubjectFinalMinimum(String subjectFinalMinimum) {
		this.subjectFinalMinimum = subjectFinalMinimum;
	}
}