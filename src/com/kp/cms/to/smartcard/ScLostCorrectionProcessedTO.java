package com.kp.cms.to.smartcard;

import java.io.Serializable;

import com.kp.cms.to.admin.EmployeeTO;
import com.kp.cms.to.admin.StudentTO;

public class ScLostCorrectionProcessedTO implements Serializable {
	
	private int id;
	private String cardType;
	private String appliedDate;
	private String regNo;
	private String studentName;
	private String studentCourse;
	private String newSmartCardNo;
	private String checked;
	private StudentTO studentTO;
	private String remarks;
	private String reasonForRejection;
	private String processedDate;
	private String approvedDate;
	private String receivedDate;
	private String issuedDate;
	private String rejectedDate;
	private EmployeeTO employeeTO;
	private String empId;
	private String employeeName;
	private String employeeDepartment;
	private String reSendRemarks;
	
	
	public StudentTO getStudentTO() {
		return studentTO;
	}
	public void setStudentTO(StudentTO studentTO) {
		this.studentTO = studentTO;
	}
	public String getNewSmartCardNo() {
		return newSmartCardNo;
	}
	public void setNewSmartCardNo(String newSmartCardNo) {
		this.newSmartCardNo = newSmartCardNo;
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
	public String getChecked() {
		return checked;
	}
	public void setChecked(String checked) {
		this.checked = checked;
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
	public String getProcessedDate() {
		return processedDate;
	}
	public void setProcessedDate(String processedDate) {
		this.processedDate = processedDate;
	}
	public String getReceivedDate() {
		return receivedDate;
	}
	public void setReceivedDate(String receivedDate) {
		this.receivedDate = receivedDate;
	}
	public String getIssuedDate() {
		return issuedDate;
	}
	public void setIssuedDate(String issuedDate) {
		this.issuedDate = issuedDate;
	}
	public String getRejectedDate() {
		return rejectedDate;
	}
	public void setRejectedDate(String rejectedDate) {
		this.rejectedDate = rejectedDate;
	}
	public String getApprovedDate() {
		return approvedDate;
	}
	public void setApprovedDate(String approvedDate) {
		this.approvedDate = approvedDate;
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
	public EmployeeTO getEmployeeTO() {
		return employeeTO;
	}
	public void setEmployeeTO(EmployeeTO employeeTO) {
		this.employeeTO = employeeTO;
	}
	public String getReSendRemarks() {
		return reSendRemarks;
	}
	public void setReSendRemarks(String reSendRemarks) {
		this.reSendRemarks = reSendRemarks;
	}
	
	
}
