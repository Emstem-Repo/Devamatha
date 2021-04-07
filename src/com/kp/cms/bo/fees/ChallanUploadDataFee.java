package com.kp.cms.bo.fees;

import java.util.Date;

public class ChallanUploadDataFee {
	private int id;
	private String challanNo;
	private Date challanDate;
	private Date createdDate;
	private String createdBy;
	private double amount;
	
	public ChallanUploadDataFee() {
	}

	public ChallanUploadDataFee(int id) {
		this.id = id;
	}

	public ChallanUploadDataFee(int id, String challanNo, Date challanDate,
			Date createdDate, String createdBy) {
		super();
		this.id = id;
		this.challanNo = challanNo;
		this.challanDate = challanDate;
		this.createdDate = createdDate;
		this.createdBy = createdBy;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getChallanNo() {
		return challanNo;
	}
	public void setChallanNo(String challanNo) {
		this.challanNo = challanNo;
	}
	public Date getChallanDate() {
		return challanDate;
	}
	public void setChallanDate(Date challanDate) {
		this.challanDate = challanDate;
	}	
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	
}
