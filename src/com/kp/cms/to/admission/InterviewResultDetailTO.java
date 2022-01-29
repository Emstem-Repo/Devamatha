package com.kp.cms.to.admission;

import java.io.Serializable;

public class InterviewResultDetailTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private int interviewResultId;
	private int gradeObtainedId;
	private String comments;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getInterviewResultId() {
		return interviewResultId;
	}
	public void setInterviewResultId(int interviewResultId) {
		this.interviewResultId = interviewResultId;
	}
	public int getGradeObtainedId() {
		return gradeObtainedId;
	}
	public void setGradeObtainedId(int gradeObtainedId) {
		this.gradeObtainedId = gradeObtainedId;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
}
