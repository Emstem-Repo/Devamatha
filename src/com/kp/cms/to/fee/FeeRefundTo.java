package com.kp.cms.to.fee;

import java.io.Serializable;

public class FeeRefundTo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private String studentName;
	private String className;
	private String registerNo;
	private String challanAmount;
	private String feeGroupName;
	private Integer studentId;
	private String challanPrintedDate;
	
	public String getChallanPrintedDate() {
		return challanPrintedDate;
	}
	public void setChallanPrintedDate(String challanPrintedDate) {
		this.challanPrintedDate = challanPrintedDate;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getRegisterNo() {
		return registerNo;
	}
	public void setRegisterNo(String registerNo) {
		this.registerNo = registerNo;
	}
	public String getChallanAmount() {
		return challanAmount;
	}
	public void setChallanAmount(String challanAmount) {
		this.challanAmount = challanAmount;
	}
	public String getFeeGroupName() {
		return feeGroupName;
	}
	public void setFeeGroupName(String feeGroupName) {
		this.feeGroupName = feeGroupName;
	}
	public Integer getStudentId() {
		return studentId;
	}
	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
	}
	
}
