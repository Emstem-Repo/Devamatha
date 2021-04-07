package com.kp.cms.bo.admin;

import java.util.Date;

public class AccountHeads {
	private int id;
	private String accCode;
	private String accName;
	private int bankAccNo;
	private Float amount;
	private Boolean fixedAmt;
	private String userCode;
	private String atStation;
	private String atTime;
	private Date atDate;
	private int academicYear;
	public AccountHeads() {
		
	}
	public AccountHeads(int id, String accCode, String accName, int bankAccNo,
			Float amount, Boolean fixedAmt, String userCode, String atStation,
			String atTime, Date atDate,int academicYear) {
		super();
		this.id = id;
		this.accCode = accCode;
		this.accName = accName;
		this.bankAccNo = bankAccNo;
		this.amount = amount;
		this.fixedAmt = fixedAmt;
		this.userCode = userCode;
		this.atStation = atStation;
		this.atTime = atTime;
		this.atDate = atDate;
		this.academicYear = academicYear;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
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
	public int getBankAccNo() {
		return bankAccNo;
	}
	public void setBankAccNo(int bankAccNo) {
		this.bankAccNo = bankAccNo;
	}
	public Float getAmount() {
		return amount;
	}
	public void setAmount(Float amount) {
		this.amount = amount;
	}
	public Boolean getFixedAmt() {
		return fixedAmt;
	}
	public void setFixedAmt(Boolean fixedAmt) {
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
	public Date getAtDate() {
		return atDate;
	}
	public void setAtDate(Date atDate) {
		this.atDate = atDate;
	}
	public int getAcademicYear() {
		return academicYear;
	}
	public void setAcademicYear(int academicYear) {
		this.academicYear = academicYear;
	}
	
}
