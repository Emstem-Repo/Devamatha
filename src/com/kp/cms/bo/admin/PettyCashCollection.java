package com.kp.cms.bo.admin;

import java.io.Serializable;
import java.util.Date;

public class PettyCashCollection implements Serializable{
	private int id;
	private int receiptNo;
	private String aplRegNo;
	private String name;
	private Date date;
	private String time;
	private String userCode;
	private String atStation;
	private Date atDate;
	private String atTime;
	private int academicYear;
	public PettyCashCollection() {
		
	}
	public PettyCashCollection(int id, int receiptNo, String aplRegNo,
			String name, Date date, String time, String userCode,
			String atStation, Date atDate, String atTime,int academicYear) {
		super();
		this.id = id;
		this.receiptNo = receiptNo;
		this.aplRegNo = aplRegNo;
		this.name = name;
		this.date = date;
		this.time = time;
		this.userCode = userCode;
		this.atStation = atStation;
		this.atDate = atDate;
		this.atTime = atTime;
		this.setAcademicYear(academicYear);
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
	public String getAplRegNo() {
		return aplRegNo;
	}
	public void setAplRegNo(String aplRegNo) {
		this.aplRegNo = aplRegNo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public Date getAtDate() {
		return atDate;
	}
	public void setAtDate(Date atDate) {
		this.atDate = atDate;
	}
	public String getAtTime() {
		return atTime;
	}
	public void setAtTime(String atTime) {
		this.atTime = atTime;
	}
	public void setAcademicYear(int academicYear) {
		this.academicYear = academicYear;
	}
	public int getAcademicYear() {
		return academicYear;
	}
	
}
