package com.kp.cms.to.fee;

public class FeeAmountTO {
	
	private int feeAccountId;
	private Double amount;
	
	public FeeAmountTO(int feeAccountId,Double amount) {
		// TODO Auto-generated constructor stub
		this.feeAccountId = feeAccountId;
		this.amount = amount;
	}
	
	/**
	 * @return the feeAccountId
	 */
	public int getFeeAccountId() {
		return feeAccountId;
	}
	/**
	 * @param feeAccountId the feeAccountId to set
	 */
	public void setFeeAccountId(int feeAccountId) {
		this.feeAccountId = feeAccountId;
	}
	/**
	 * @return the amount
	 */
	public Double getAmount() {
		return amount;
	}
	/**
	 * @param amount the amount to set
	 */
	public void setAmount(Double amount) {
		this.amount = amount;
	}
 
}
