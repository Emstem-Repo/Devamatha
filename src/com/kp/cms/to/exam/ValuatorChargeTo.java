package com.kp.cms.to.exam;

import java.io.Serializable;

public class ValuatorChargeTo implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private int id;
	private String programTypeId;
	private String valuatorcharge;
	private String reviewercharge;
	private String projectevaluationminor;
	private String projectevaluationmajor;
	private String boardMeetingCharge;
		
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getProgramTypeId() {
		return programTypeId;
	}
	public void setProgramTypeId(String programTypeId) {
		this.programTypeId = programTypeId;
	}
	public String getValuatorcharge() {
		return valuatorcharge;
	}
	public void setValuatorcharge(String valuatorcharge) {
		this.valuatorcharge = valuatorcharge;
	}
	public String getReviewercharge() {
		return reviewercharge;
	}
	public void setReviewercharge(String reviewercharge) {
		this.reviewercharge = reviewercharge;
	}
	public String getProjectevaluationminor() {
		return projectevaluationminor;
	}
	public void setProjectevaluationminor(String projectevaluationminor) {
		this.projectevaluationminor = projectevaluationminor;
	}
	public String getProjectevaluationmajor() {
		return projectevaluationmajor;
	}
	public void setProjectevaluationmajor(String projectevaluationmajor) {
		this.projectevaluationmajor = projectevaluationmajor;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getBoardMeetingCharge() {
		return boardMeetingCharge;
	}
	public void setBoardMeetingCharge(String boardMeetingCharge) {
		this.boardMeetingCharge = boardMeetingCharge;
	}
	
	
}
