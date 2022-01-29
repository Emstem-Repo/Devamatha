package com.kp.cms.to.pettycash;

import java.util.Date;

public class AccountHeadsTo {
	private String id;
	private String accCode;
	private String accName;
	private String bankAccNo;
	private String amount;
	private String fixedAmt;
	private String userCode;
	private String atStation;
	private String atTime;
	private Date atDate;
	private String academicYear;
	private String date;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAccCode() {
		return accCode;
	}
	public void setAccCode(String accCode) {
		this.accCode = accCode;
	}
	public String getAccName() {
		return accName;
	}
	public void setAccName(String accName) {
		this.accName = accName;
	}
	public String getBankAccNo() {
		return bankAccNo;
	}
	public void setBankAccNo(String bankAccNo) {
		this.bankAccNo = bankAccNo;
	}
	
	
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getFixedAmt() {
		return fixedAmt;
	}
	public void setFixedAmt(String fixedAmt) {
		this.fixedAmt = fixedAmt;
	}
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public String getAtStation() {
		return atStation;
	}
	public void setAtStation(String atStation) {
		this.atStation = atStation;
	}
	public String getAtTime() {
		return atTime;
	}
	public void setAtTime(String atTime) {
		this.atTime = atTime;
	}
	
	public String getAcademicYear() {
		return academicYear;
	}
	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}
	public void setAtDate(Date atDate) {
		this.atDate = atDate;
	}
	public Date getAtDate() {
		return atDate;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
}
