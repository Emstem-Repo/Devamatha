package com.kp.cms.to.fee;

import java.io.Serializable;

public class FeePaymentDetailEditTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int feePaymentDetailId;
	private String totalAmount;
	private String concessionAmount;
	private String installmentAmount;
	private String scholarshipAmount;
	private String discountAmount;
	private String excessShortAmount;
	private String accountName;
	
	
	public String getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}
	public String getConcessionAmount() {
		return concessionAmount;
	}
	public void setConcessionAmount(String concessionAmount) {
		this.concessionAmount = concessionAmount;
	}
	public String getInstallmentAmount() {
		return installmentAmount;
	}
	public void setInstallmentAmount(String installmentAmount) {
		this.installmentAmount = installmentAmount;
	}
	public String getScholarshipAmount() {
		return scholarshipAmount;
	}
	public void setScholarshipAmount(String scholarshipAmount) {
		this.scholarshipAmount = scholarshipAmount;
	}
	public String getDiscountAmount() {
		return discountAmount;
	}
	public void setDiscountAmount(String discountAmount) {
		this.discountAmount = discountAmount;
	}
	public String getExcessShortAmount() {
		return excessShortAmount;
	}
	public void setExcessShortAmount(String excessShortAmount) {
		this.excessShortAmount = excessShortAmount;
	}
	public int getFeePaymentDetailId() {
		return feePaymentDetailId;
	}
	public void setFeePaymentDetailId(int feePaymentDetailId) {
		this.feePaymentDetailId = feePaymentDetailId;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
}
