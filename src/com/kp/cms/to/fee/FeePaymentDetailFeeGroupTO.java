package com.kp.cms.to.fee;


public class FeePaymentDetailFeeGroupTO {
	private int id;
	private int feePaymentDetailId;
	private int feeGroupId;
	private Boolean isOptional;
	private String amount;
	private String feeGroupName;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getFeePaymentDetailId() {
		return feePaymentDetailId;
	}
	public void setFeePaymentDetailId(int feePaymentDetailId) {
		this.feePaymentDetailId = feePaymentDetailId;
	}
	public int getFeeGroupId() {
		return feeGroupId;
	}
	public void setFeeGroupId(int feeGroupId) {
		this.feeGroupId = feeGroupId;
	}
	public Boolean getIsOptional() {
		return isOptional;
	}
	public void setIsOptional(Boolean isOptional) {
		this.isOptional = isOptional;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getFeeGroupName() {
		return feeGroupName;
	}
	public void setFeeGroupName(String feeGroupName) {
		this.feeGroupName = feeGroupName;
	}
	
}
