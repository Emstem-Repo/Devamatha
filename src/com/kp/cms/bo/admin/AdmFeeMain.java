package com.kp.cms.bo.admin;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class AdmFeeMain implements Serializable{
	private int id;
	   private Integer billNo;
	   private Date date;
	   private String time;
	   private String applnRegNo;
	   private String classes;
	   private BigDecimal normalFee;
	   private BigDecimal maintFee;
	   private Boolean excemption;
	   private Boolean moneyPaid;
	   private String userCode;
	   private BigDecimal maintConcesion;
	   private String concDesc;
	   private Integer academicYear;
	   public AdmFeeMain(){
		   
	   }
	   public AdmFeeMain(int id, Integer billNo, Date date, String time,
			String applnRegNo, String classes, BigDecimal normalFee,
			BigDecimal maintFee, Boolean excemption, Boolean moneyPaid,
			String userCode, BigDecimal maintConcesion, String concDesc) {
		super();
		this.id = id;
		this.billNo = billNo;
		this.date = date;
		this.time = time;
		this.applnRegNo = applnRegNo;
		this.classes = classes;
		this.normalFee = normalFee;
		this.maintFee = maintFee;
		this.excemption = excemption;
		this.moneyPaid = moneyPaid;
		this.userCode = userCode;
		this.maintConcesion = maintConcesion;
		this.concDesc = concDesc;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
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
	public BigDecimal getNormalFee() {
		return normalFee;
	}
	public void setNormalFee(BigDecimal normalFee) {
		this.normalFee = normalFee;
	}
	public BigDecimal getMaintFee() {
		return maintFee;
	}
	public void setMaintFee(BigDecimal maintFee) {
		this.maintFee = maintFee;
	}
	public Boolean getExcemption() {
		return excemption;
	}
	public void setExcemption(Boolean excemption) {
		this.excemption = excemption;
	}
	public Boolean getMoneyPaid() {
		return moneyPaid;
	}
	public void setMoneyPaid(Boolean moneyPaid) {
		this.moneyPaid = moneyPaid;
	}
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public BigDecimal getMaintConcesion() {
		return maintConcesion;
	}
	public void setMaintConcesion(BigDecimal maintConcesion) {
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
}
