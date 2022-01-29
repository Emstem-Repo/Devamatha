package com.kp.cms.bo.admin;

import java.io.Serializable;

public class FeesFeeDetails implements Serializable{
	private int id;
	private String feesCode;
	private int billNo;
	private int addFee22;
	private int addFee993;
	private int academicYear;
	public FeesFeeDetails() {
		
	}
	public FeesFeeDetails(int id, String feesCode, int billNo, int addFee22,
			int addFee993, int academicYear) {
		super();
		this.id = id;
		this.feesCode = feesCode;
		this.billNo = billNo;
		this.addFee22 = addFee22;
		this.addFee993 = addFee993;
		this.academicYear = academicYear;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFeesCode() {
		return feesCode;
	}
	public void setFeesCode(String feesCode) {
		this.feesCode = feesCode;
	}
	public int getBillNo() {
		return billNo;
	}
	public void setBillNo(int billNo) {
		this.billNo = billNo;
	}
	public int getAddFee22() {
		return addFee22;
	}
	public void setAddFee22(int addFee22) {
		this.addFee22 = addFee22;
	}
	public int getAddFee993() {
		return addFee993;
	}
	public void setAddFee993(int addFee993) {
		this.addFee993 = addFee993;
	}
	public int getAcademicYear() {
		return academicYear;
	}
	public void setAcademicYear(int academicYear) {
		this.academicYear = academicYear;
	}
	
}
