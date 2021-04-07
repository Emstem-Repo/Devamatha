package com.kp.cms.bo.admin;

import java.math.BigDecimal;

// Generated Oct 29, 2009 11:50:57 AM by Hibernate Tools 3.2.0.b9

/**
 * InterviewResultDetail generated by hbm2java
 */
public class InterviewResultDetail implements java.io.Serializable {

	private int id;
	private InterviewResult interviewResult;
	private Grade grade;
	private BigDecimal percentage;
	private String comments;

	public InterviewResultDetail() {
	}

	public InterviewResultDetail(int id) {
		this.id = id;
	}

	public InterviewResultDetail(int id, InterviewResult interviewResult,
			Grade grade) {
		this.id = id;
		this.interviewResult = interviewResult;
		this.grade = grade;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public InterviewResult getInterviewResult() {
		return this.interviewResult;
	}

	public void setInterviewResult(InterviewResult interviewResult) {
		this.interviewResult = interviewResult;
	}

	public Grade getGrade() {
		return this.grade;
	}

	public void setGrade(Grade grade) {
		this.grade = grade;
	}

	public BigDecimal getPercentage() {
		return percentage;
	}

	public void setPercentage(BigDecimal percentage) {
		this.percentage = percentage;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

}