package com.kp.cms.to.admission;

public class InterviewResultReportTO {
	private String interviewDate;
	private String interviewRound;
	private String grade;
	private String comments;
	private int interviewId;
	
	public String getInterviewDate() {
		return interviewDate;
	}
	public void setInterviewDate(String interviewDate) {
		this.interviewDate = interviewDate;
	}
	public String getInterviewRound() {
		return interviewRound;
	}
	public void setInterviewRound(String interviewRound) {
		this.interviewRound = interviewRound;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public int getInterviewId() {
		return interviewId;
	}
	public void setInterviewId(int interviewId) {
		this.interviewId = interviewId;
	}
	
	
}
