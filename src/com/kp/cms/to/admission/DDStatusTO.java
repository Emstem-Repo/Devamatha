package com.kp.cms.to.admission;

import java.util.Date;


public class DDStatusTO {
	
	private Integer applnNo;
	private String recievedDDNo;
	private String recievedDDDate;
	//raghu
	private String recievedChallanNo;
	private String recievedChallanDate;
	private Boolean isDDRecieved;	
	private Boolean isChallanRecieved;
	private String studentName;
	private Integer admId;
	private String enterdChallanNo;
	private String tempChecked;
	private String checked;
	private String tempChecked1;
	private String courseName;
	private String casteName;
	private Integer stuRegAppId;
	private Double total;
	private Integer stuFeePaymentId;

	public Integer getApplnNo() {
		return applnNo;
	}
	public void setApplnNo(Integer applnNo) {
		this.applnNo = applnNo;
	}
	public String getRecievedDDNo() {
		return recievedDDNo;
	}
	public void setRecievedDDNo(String recievedDDNo) {
		this.recievedDDNo = recievedDDNo;
	}
	public String getRecievedDDDate() {
		return recievedDDDate;
	}
	public void setRecievedDDDate(String recievedDDDate) {
		this.recievedDDDate = recievedDDDate;
	}
	
	public String getRecievedChallanNo() {
		return recievedChallanNo;
	}
	public void setRecievedChallanNo(String recievedChallanNo) {
		this.recievedChallanNo = recievedChallanNo;
	}
	public String getRecievedChallanDate() {
		return recievedChallanDate;
	}
	public void setRecievedChallanDate(String recievedChallanDate) {
		this.recievedChallanDate = recievedChallanDate;
	}
	public Boolean getIsDDRecieved() {
		return isDDRecieved;
	}
	public void setIsDDRecieved(Boolean isDDRecieved) {
		this.isDDRecieved = isDDRecieved;
	}
	public Boolean getIsChallanRecieved() {
		return isChallanRecieved;
	}
	public void setIsChallanRecieved(Boolean isChallanRecieved) {
		this.isChallanRecieved = isChallanRecieved;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public Integer getAdmId() {
		return admId;
	}
	public void setAdmId(Integer admId) {
		this.admId = admId;
	}
	public String getEnterdChallanNo() {
		return enterdChallanNo;
	}
	public void setEnterdChallanNo(String enterdChallanNo) {
		this.enterdChallanNo = enterdChallanNo;
	}
	public String getTempChecked() {
		return tempChecked;
	}
	public void setTempChecked(String tempChecked) {
		this.tempChecked = tempChecked;
	}
	public String getChecked() {
		return checked;
	}
	public void setChecked(String checked) {
		this.checked = checked;
	}
	public String getTempChecked1() {
		return tempChecked1;
	}
	public void setTempChecked1(String tempChecked1) {
		this.tempChecked1 = tempChecked1;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public String getCasteName() {
		return casteName;
	}
	public void setCasteName(String casteName) {
		this.casteName = casteName;
	}
	public Integer getStuRegAppId() {
		return stuRegAppId;
	}
	public void setStuRegAppId(Integer stuRegAppId) {
		this.stuRegAppId = stuRegAppId;
	}
	public Double getTotal() {
		return total;
	}
	public void setTotal(Double total) {
		this.total = total;
	}
	public Integer getStuFeePaymentId() {
		return stuFeePaymentId;
	}
	public void setStuFeePaymentId(Integer stuFeePaymentId) {
		this.stuFeePaymentId = stuFeePaymentId;
	}
}
