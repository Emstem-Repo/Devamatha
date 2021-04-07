package com.kp.cms.to.fee;


public class FeeGroupAccountTO {
	
	private Integer feeGroupId;
	private Integer feeAccountId;
	private Double totalAmount;
	private Double exemptionAmount;
	
	/**
	 * @return the feeGroupId
	 */
	public Integer getFeeGroupId() {
		return feeGroupId;
	}
	/**
	 * @param feeGroupId the feeGroupId to set
	 */
	public void setFeeGroupId(Integer feeGroupId) {
		this.feeGroupId = feeGroupId;
	}
	/**
	 * @return the feeAccountId
	 */
	public Integer getFeeAccountId() {
		return feeAccountId;
	}
	/**
	 * @param feeAccountId the feeAccountId to set
	 */
	public void setFeeAccountId(Integer feeAccountId) {
		this.feeAccountId = feeAccountId;
	}
	/**
	 * @return the totalAmount
	 */
	public Double getTotalAmount() {
		return totalAmount;
	}
	/**
	 * @param totalAmount the totalAmount to set
	 */
	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}
	
	public Double getExemptionAmount() {
		return exemptionAmount;
	}
	public void setExemptionAmount(Double exemptionAmount) {
		this.exemptionAmount = exemptionAmount;
	}
}