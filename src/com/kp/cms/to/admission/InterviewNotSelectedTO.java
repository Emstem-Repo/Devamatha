package com.kp.cms.to.admission;

import java.io.Serializable;

public class InterviewNotSelectedTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String applicationNo;
	private String applicantName;
	private String interviewType;
	private String email;
	private boolean sendMailSelected;
	
	public String getApplicationNo() {
		return applicationNo;
	}
	public void setApplicationNo(String applicationNo) {
		this.applicationNo = applicationNo;
	}
	public String getApplicantName() {
		return applicantName;
	}
	public void setApplicantName(String applicantName) {
		this.applicantName = applicantName;
	}
	public String getInterviewType() {
		return interviewType;
	}
	public void setInterviewType(String interviewType) {
		this.interviewType = interviewType;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public boolean isSendMailSelected() {
		return sendMailSelected;
	}
	public void setSendMailSelected(boolean sendMailSelected) {
		this.sendMailSelected = sendMailSelected;
	}
}
