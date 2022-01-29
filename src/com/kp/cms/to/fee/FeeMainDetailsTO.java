package com.kp.cms.to.fee;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class FeeMainDetailsTO implements Serializable{
	private Integer billNo;
	   private Date date;
	   private String time;
	   private String applnRegNo;
	   private String classes;
	   private String normalFee;
	   private String maintFee;
	   private String excemption;
	   private String moneyPaid;
	   private String userCode;
	   private String maintConcesion;
	   private String concDesc;
	   private Integer academicYear;
	   private int addFee22;
		private int addFee993;
		private String date1;
		private String feesCode;
	public Integer getBillNo() {
		return billNo;
	}
	public void setBillNo(Integer billNo) {
		this.billNo = billNo;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getApplnRegNo() {
		return applnRegNo;
	}
	public void setApplnRegNo(String applnRegNo) {
		this.applnRegNo = applnRegNo;
	}
	public String getClasses() {
		return classes;
	}
	public void setClasses(String classes) {
		this.classes = classes;
	}
	public String getNormalFee() {
		return normalFee;
	}
	public void setNormalFee(String normalFee) {
		this.normalFee = normalFee;
	}
	public String getMaintFee() {
		return maintFee;
	}
	public void setMaintFee(String maintFee) {
		this.maintFee = maintFee;
	}
	public String getExcemption() {
		return excemption;
	}
	public void setExcemption(String excemption) {
		this.excemption = excemption;
	}
	public String getMoneyPaid() {
		return moneyPaid;
	}
	public void setMoneyPaid(String moneyPaid) {
		this.moneyPaid = moneyPaid;
	}
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public String getMaintConcesion() {
		return maintConcesion;
	}
	public void setMaintConcesion(String maintConcesion) {
		this.maintConcesion = maintConcesion;
	}
	public String getConcDesc() {
		return concDesc;
	}
	public void setConcDesc(String concDesc) {
		this.concDesc = concDesc;
	}
	public Integer getAcademicYear() {
		return academicYear;
	}
	public void setAcademicYear(Integer academicYear) {
		this.academicYear = academicYear;
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
	public String getDate1() {
		return date1;
	}
	public void setDate1(String date1) {
		this.date1 = date1;
	}
	public String getFeesCode() {
		return feesCode;
	}
	public void setFeesCode(String feesCode) {
		this.feesCode = feesCode;
	}
}
