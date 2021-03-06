package com.kp.cms.bo.admin;

// Generated Jan 22, 2009 3:35:47 PM by Hibernate Tools 3.2.0.b9

import java.math.BigDecimal;
import java.util.Date;

/**
 * FeeTransaction generated by hbm2java
 */
public class FeeTransaction implements java.io.Serializable {

	private int id;
	private Currency currency;
	private BankBranch bankBranch;
	private String createdBy;;
	private PaymentMode paymentMode;
	private FeeExcemption feeExcemption;
	private String modifiedBy;
	private FeeConcession feeConcession;
	private Fee fee;
	private String registerNumber;
	private Date paymentDate;
	private BigDecimal paidAmount;
	private String receiptNo;
	private String journalNo;
	private String challanRefNo;
	private Integer installmentNo;
	private Date createdDate;
	private Date lastModifiedDate;

	public FeeTransaction() {
	}

	public FeeTransaction(int id) {
		this.id = id;
	}

	public FeeTransaction(int id, Currency currency, BankBranch bankBranch,
			String createdBy, PaymentMode paymentMode,
			FeeExcemption feeExcemption, String modifiedBy,
			FeeConcession feeConcession, Fee fee, String registerNumber,
			Date paymentDate, BigDecimal paidAmount, String receiptNo,
			String journalNo, String challanRefNo, Integer installmentNo,
			Date createdDate, Date lastModifiedDate) {
		this.id = id;
		this.currency = currency;
		this.bankBranch = bankBranch;
		this.createdBy = createdBy;
		this.paymentMode = paymentMode;
		this.feeExcemption = feeExcemption;
		this.modifiedBy = modifiedBy;
		this.feeConcession = feeConcession;
		this.fee = fee;
		this.registerNumber = registerNumber;
		this.paymentDate = paymentDate;
		this.paidAmount = paidAmount;
		this.receiptNo = receiptNo;
		this.journalNo = journalNo;
		this.challanRefNo = challanRefNo;
		this.installmentNo = installmentNo;
		this.createdDate = createdDate;
		this.lastModifiedDate = lastModifiedDate;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Currency getCurrency() {
		return this.currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public BankBranch getBankBranch() {
		return this.bankBranch;
	}

	public void setBankBranch(BankBranch bankBranch) {
		this.bankBranch = bankBranch;
	}

	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy)  {
		this.createdBy = createdBy;
	}

	public PaymentMode getPaymentMode() {
		return this.paymentMode;
	}

	public void setPaymentMode(PaymentMode paymentMode) {
		this.paymentMode = paymentMode;
	}

	public FeeExcemption getFeeExcemption() {
		return this.feeExcemption;
	}

	public void setFeeExcemption(FeeExcemption feeExcemption) {
		this.feeExcemption = feeExcemption;
	}

	public String getModifiedBy()  {
		return this.modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public FeeConcession getFeeConcession() {
		return this.feeConcession;
	}

	public void setFeeConcession(FeeConcession feeConcession) {
		this.feeConcession = feeConcession;
	}

	public Fee getFee() {
		return this.fee;
	}

	public void setFee(Fee fee) {
		this.fee = fee;
	}

	public String getRegisterNumber() {
		return this.registerNumber;
	}

	public void setRegisterNumber(String registerNumber) {
		this.registerNumber = registerNumber;
	}

	public Date getPaymentDate() {
		return this.paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	public BigDecimal getPaidAmount() {
		return this.paidAmount;
	}

	public void setPaidAmount(BigDecimal paidAmount) {
		this.paidAmount = paidAmount;
	}

	public String getReceiptNo() {
		return this.receiptNo;
	}

	public void setReceiptNo(String receiptNo) {
		this.receiptNo = receiptNo;
	}

	public String getJournalNo() {
		return this.journalNo;
	}

	public void setJournalNo(String journalNo) {
		this.journalNo = journalNo;
	}

	public String getChallanRefNo() {
		return this.challanRefNo;
	}

	public void setChallanRefNo(String challanRefNo) {
		this.challanRefNo = challanRefNo;
	}

	public Integer getInstallmentNo() {
		return this.installmentNo;
	}

	public void setInstallmentNo(Integer installmentNo) {
		this.installmentNo = installmentNo;
	}

	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getLastModifiedDate() {
		return this.lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

}
