package com.kp.cms.to.reports;

public class CiaOverAllReportTO {
	private int subjectId;
	private String subjectName;
	private String theoryMarks;
	private String practicalMarks;
	private String semester;
	
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
	public String getSemester() {
		return semester;
	}
	public void setSemester(String semester) {
		this.semester = semester;
	}

	
}
