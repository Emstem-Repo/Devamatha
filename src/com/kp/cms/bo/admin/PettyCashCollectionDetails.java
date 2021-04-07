package com.kp.cms.bo.admin;

import java.io.Serializable;
import java.util.Date;

public class PettyCashCollectionDetails implements Serializable{
	private int id;
	private int receiptNo;
	private String accCode;
	private Float amount;
	private String aplRegNo;
	private String puDgPg;
	private Date date;
	private String atStation;
	private String userCode;
	private String atTime;
	private Date atDate;
	private int academicYear;
	public PettyCashCollectionDetails() {
		
	}
	public PettyCashCollectionDetails(int id, int receiptNo, String accCode,
			Float amount, String aplRegNo, String puDgPg, Date date,
			String atStation, String userCode, String atTime, Date atDate,
			int academicYear) {
		super();
		this.id = id;
		this.receiptNo = receiptNo;
		this.accCode = accCode;
		this.amount = amount;
		this.aplRegNo = aplRegNo;
		this.puDgPg = puDgPg;
		this.date = date;
		this.atStation = atStation;
		this.userCode = userCode;
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
	public int getReceiptNo() {
		return receiptNo;
	}
	public void setReceiptNo(int receiptNo) {
		this.receiptNo = receiptNo;
	}
	public String getAccCode() {
		return accCode;
	}
	public void setAccCode(String accCode) {
		this.accCode = accCode;
	}
	public Float getAmount() {
		return amount;
	}
	public void setAmount(Float amount) {
		this.amount = amount;
	}
	public String getAplRegNo() {
		return aplRegNo;
	}
	public void setAplRegNo(String aplRegNo) {
		this.aplRegNo = aplRegNo;
	}
	public String getPuDgPg() {
		return puDgPg;
	}
	public void setPuDgPg(String puDgPg) {
		this.puDgPg = puDgPg;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getAtStation() {
		return atStation;
	}
	public void setAtStation(String atStation) {
		this.atStation = atStation;
	}
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
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
