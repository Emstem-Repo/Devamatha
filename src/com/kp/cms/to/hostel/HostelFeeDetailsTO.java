package com.kp.cms.to.hostel;

import java.io.Serializable;

  public class HostelFeeDetailsTO implements Serializable{
	
	private String feeTypeName;
	private String feeAmount;
	private String fineAmount;
	private String fineDate;
	private String fineDescription;
	
	public String getFeeTypeName() {
		return feeTypeName;
	}
	public void setFeeTypeName(String feeTypeName) {
		this.feeTypeName = feeTypeName;
	}
	public String getFeeAmount() {
		return feeAmount;
	}
	public void setFeeAmount(String feeAmount) {
		this.feeAmount = feeAmount;
	}
	public String getFineAmount() {
		return fineAmount;
	}
	public void setFineAmount(String fineAmount) {
		this.fineAmount = fineAmount;
	}
	public String getFineDate() {
		return fineDate;
	}
	public void setFineDate(String fineDate) {
		this.fineDate = fineDate;
	}
	public String getFineDescription() {
		return fineDescription;
	}
	public void setFineDescription(String fineDescription) {
		this.fineDescription = fineDescription;
	}

  }
