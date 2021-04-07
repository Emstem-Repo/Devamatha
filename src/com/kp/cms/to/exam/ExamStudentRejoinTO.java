package com.kp.cms.to.exam;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ExamStudentRejoinTO implements Serializable {
	
	/**
	 * Dec 23, 2009
	 * Created By 9Elements Team
	 */
	private int studentId;
	private String rejoinDate;
	private String reason;
	private Integer schemeNo;
	private String details;
	public int getStudentId() {
		return studentId;
	}
	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}
	
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public Integer getSchemeNo() {
		return schemeNo;
	}
	public void setSchemeNo(Integer schemeNo) {
		this.schemeNo = schemeNo;
	}
	
	public void setRejoinDate(String rejoinDate) {
		this.rejoinDate = rejoinDate;
	}
	public String getRejoinDate() {
		return rejoinDate;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	public String getDetails() {
		return details;
	}
	
	
}
