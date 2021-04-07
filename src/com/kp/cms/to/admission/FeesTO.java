package com.kp.cms.to.admission;

import java.io.Serializable;

@SuppressWarnings("serial")
public class FeesTO implements Serializable{
	private String schmeNo;
	private String totalFee;
	private String installmentGiven;
	private String installmentPaid;
	private String feesPaid;
	private String feesPending;
	private String concessionGiven;
	private String balanceAmount;
	public String getTotalFee() {
		return totalFee;
	}
	public void setTotalFee(String totalFee) {
		this.totalFee = totalFee;
	}
	public String getInstallmentGiven() {
		return installmentGiven;
	}
	public void setInstallmentGiven(String installmentGiven) {
		this.installmentGiven = installmentGiven;
	}
	public String getInstallmentPaid() {
		return installmentPaid;
	}
	public void setInstallmentPaid(String installmentPaid) {
		this.installmentPaid = installmentPaid;
	}
	public String getConcessionGiven() {
		return concessionGiven;
	}
	public void setConcessionGiven(String concessionGiven) {
		this.concessionGiven = concessionGiven;
	}
	public String getBalanceAmount() {
		return balanceAmount;
	}
	public void setBalanceAmount(String balanceAmount) {
		this.balanceAmount = balanceAmount;
	}
	public String getFeesPaid() {
		return feesPaid;
	}
	public void setFeesPaid(String feesPaid) {
		this.feesPaid = feesPaid;
	}
	public String getFeesPending() {
		return feesPending;
	}
	public void setFeesPending(String feesPending) {
		this.feesPending = feesPending;
	}
	public FeesTO(String feesPaid, String feesPending) {
		super();
		this.feesPaid = feesPaid;
		this.feesPending = feesPending;
	}
	public FeesTO() {
		super();
	}
	public void setSchmeNo(String schmeNo) {
		this.schmeNo = schmeNo;
	}
	public String getSchmeNo() {
		return schmeNo;
	}
	

}
