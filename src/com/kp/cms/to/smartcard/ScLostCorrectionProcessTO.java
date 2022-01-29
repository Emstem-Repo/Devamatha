package com.kp.cms.to.smartcard;

import java.io.Serializable;
import java.util.Date;

public class ScLostCorrectionProcessTO implements Serializable {
	
	private int id;
	private String cardType;
	private String status;
	private String appliedDate;
	private String regNo;
	private String studentName;
	private String studentCourse;
	private String newSmartCardNum;
	//private String oldSmartCardNum;
	private String isDownload;
	private String checked;
	private String tempChecked;
	private String remarks;
	private String reasonForRejection;
	private String approvedDate;
	private String processedDate;
	private String rejectedDate;
	private String oldSmartCardNo;
	private String accNo;
	
	private short cardTypePosition;
	private short appliedDatePosition;
	private short regNoPosition;
	private short studentNamePosition;
	private short studentCoursePosition;
	private short remarksPosition;
	private short oldSmartCardNumPosition;
	private short accNoPosition;
	//private short processedDatePosition;
	private String empId;
	private String employeeName;
	private String employeeDepartment;
	private short empIdPosition;
	private short employeeNamePosition;
	private short employeeDepartmentPosition;
	
	
	public String getIsDownload() {
		return isDownload;
	}
	public void setIsDownload(String isDownload) {
		this.isDownload = isDownload;
	}
	public String getChecked() {
		return checked;
	}
	public void setChecked(String checked) {
		this.checked = checked;
	}
	public String getNewSmartCardNum() {
		return newSmartCardNum;
	}
	public void setNewSmartCardNum(String newSmartCardNum) {
		this.newSmartCardNum = newSmartCardNum;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCardType() {
		return cardType;
	}
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getAppliedDate() {
		return appliedDate;
	}
	public void setAppliedDate(String appliedDate) {
		this.appliedDate = appliedDate;
	}
	public String getRegNo() {
		return regNo;
	}
	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public String getStudentCourse() {
		return studentCourse;
	}
	public void setStudentCourse(String studentCourse) {
		this.studentCourse = studentCourse;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getReasonForRejection() {
		return reasonForRejection;
	}
	public void setReasonForRejection(String reasonForRejection) {
		this.reasonForRejection = reasonForRejection;
	}
	public String getTempChecked() {
		return tempChecked;
	}
	public void setTempChecked(String tempChecked) {
		this.tempChecked = tempChecked;
	}
	public String getApprovedDate() {
		return approvedDate;
	}
	public void setApprovedDate(String approvedDate) {
		this.approvedDate = approvedDate;
	}
	public String getProcessedDate() {
		return processedDate;
	}
	public void setProcessedDate(String processedDate) {
		this.processedDate = processedDate;
	}
	public String getRejectedDate() {
		return rejectedDate;
	}
	public void setRejectedDate(String rejectedDate) {
		this.rejectedDate = rejectedDate;
	}
	public short getCardTypePosition() {
		return cardTypePosition;
	}
	public void setCardTypePosition(short cardTypePosition) {
		this.cardTypePosition = cardTypePosition;
	}
	public short getAppliedDatePosition() {
		return appliedDatePosition;
	}
	public void setAppliedDatePosition(short appliedDatePosition) {
		this.appliedDatePosition = appliedDatePosition;
	}
	public short getRegNoPosition() {
		return regNoPosition;
	}
	public void setRegNoPosition(short regNoPosition) {
		this.regNoPosition = regNoPosition;
	}
	public short getStudentNamePosition() {
		return studentNamePosition;
	}
	public void setStudentNamePosition(short studentNamePosition) {
		this.studentNamePosition = studentNamePosition;
	}
	public short getStudentCoursePosition() {
		return studentCoursePosition;
	}
	public void setStudentCoursePosition(short studentCoursePosition) {
		this.studentCoursePosition = studentCoursePosition;
	}
	public short getRemarksPosition() {
		return remarksPosition;
	}
	public void setRemarksPosition(short remarksPosition) {
		this.remarksPosition = remarksPosition;
	}
	public short getOldSmartCardNumPosition() {
		return oldSmartCardNumPosition;
	}
	public void setOldSmartCardNumPosition(short oldSmartCardNumPosition) {
		this.oldSmartCardNumPosition = oldSmartCardNumPosition;
	}
	public short getAccNoPosition() {
		return accNoPosition;
	}
	public void setAccNoPosition(short accNoPosition) {
		this.accNoPosition = accNoPosition;
	}
	public String getOldSmartCardNo() {
		return oldSmartCardNo;
	}
	public void setOldSmartCardNo(String oldSmartCardNo) {
		this.oldSmartCardNo = oldSmartCardNo;
	}
	public String getAccNo() {
		return accNo;
	}
	public void setAccNo(String accNo) {
		this.accNo = accNo;
	}
	public String getEmpId() {
		return empId;
	}
	public void setEmpId(String empId) {
		this.empId = empId;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public String getEmployeeDepartment() {
		return employeeDepartment;
	}
	public void setEmployeeDepartment(String employeeDepartment) {
		this.employeeDepartment = employeeDepartment;
	}
	public short getEmpIdPosition() {
		return empIdPosition;
	}
	public void setEmpIdPosition(short empIdPosition) {
		this.empIdPosition = empIdPosition;
	}
	public short getEmployeeNamePosition() {
		return employeeNamePosition;
	}
	public void setEmployeeNamePosition(short employeeNamePosition) {
		this.employeeNamePosition = employeeNamePosition;
	}
	public short getEmployeeDepartmentPosition() {
		return employeeDepartmentPosition;
	}
	public void setEmployeeDepartmentPosition(short employeeDepartmentPosition) {
		this.employeeDepartmentPosition = employeeDepartmentPosition;
	}
	

}
