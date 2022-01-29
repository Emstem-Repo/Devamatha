package com.kp.cms.to.hostel;

import java.math.BigDecimal;

public class HlDamageTO 
{
	private int id;
	private String staffId;
	private String date;
	private String time;
	private String dateAndTimeDisplay;
	private String description;
	private BigDecimal amount;
	private String applnNoRegisterStaffIdRollNo; // this property is to set 
											//either applicationNo or registerId or staffId or rollNo
	private String billNo;
	private String status;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getStaffId() {
		return staffId;
	}
	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	/*public String getDateAndTimeDisplay() {
		return dateAndTimeDisplay;
	}
	public void setDateAndTimeDisplay(String dateAndTimeDisplay) {
		this.dateAndTimeDisplay = dateAndTimeDisplay;
	}*/
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public String getDateAndTimeDisplay() {
		return dateAndTimeDisplay;
	}
	public void setDateAndTimeDisplay(String dateAndTimeDisplay) {
		this.dateAndTimeDisplay = dateAndTimeDisplay;
	}
	public String getApplnNoRegisterStaffIdRollNo() {
		return applnNoRegisterStaffIdRollNo;
	}
	public void setApplnNoRegisterStaffIdRollNo(String applnNoRegisterStaffIdRollNo) {
		this.applnNoRegisterStaffIdRollNo = applnNoRegisterStaffIdRollNo;
	}
	public String getBillNo() {
		return billNo;
	}
	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
