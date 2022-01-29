package com.kp.cms.to.exam;

import java.io.Serializable;

public class ExamStudentDetentionDetailsTO implements Serializable {
	/**
	 * Dec 23, 2009
	 * Created By 9Elements Team
	 */
	private int studentId;
	private String detentionDate;
	private String reason;
	private Integer schemeNo;
	private String details;
	private String regNo;
	private String batch;
	private String className;
	private String disContinuedDate;
	private String discontinuedReason;
	private String rejoinDate;
	private String rejoinReason;
	
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
	public void setDetentionDate(String detentionDate) {
		this.detentionDate = detentionDate;
	}
	public String getDetentionDate() {
		return detentionDate;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	public String getDetails() {
		return details;
	}
	public String getRegNo() {
		return regNo;
	}
	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}
	public String getBatch() {
		return batch;
	}
	public void setBatch(String batch) {
		this.batch = batch;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getDisContinuedDate() {
		return disContinuedDate;
	}
	public void setDisContinuedDate(String disContinuedDate) {
		this.disContinuedDate = disContinuedDate;
	}
	public String getDiscontinuedReason() {
		return discontinuedReason;
	}
	public void setDiscontinuedReason(String discontinuedReason) {
		this.discontinuedReason = discontinuedReason;
	}
	public String getRejoinDate() {
		return rejoinDate;
	}
	public void setRejoinDate(String rejoinDate) {
		this.rejoinDate = rejoinDate;
	}
	public String getRejoinReason() {
		return rejoinReason;
	}
	public void setRejoinReason(String rejoinReason) {
		this.rejoinReason = rejoinReason;
	}
	
	
}
