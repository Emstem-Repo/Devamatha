package com.kp.cms.to.admin;

import java.math.BigDecimal;
import java.util.Date;

public class CandidatePGIDetailsTO {
	private int id;
	private String refundGenerated;
	private String courseName;
	private String admApplnId;
	private String candidateRefNo;
	private String txnStatus;
	private BigDecimal txnAmount;
	private String txnDate;
	private String txnRefNo;
	private String bankId;
	private String bankRefNo;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getRefundGenerated() {
		return refundGenerated;
	}
	public void setRefundGenerated(String refundGenerated) {
		this.refundGenerated = refundGenerated;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public String getAdmApplnId() {
		return admApplnId;
	}
	public void setAdmApplnId(String admApplnId) {
		this.admApplnId = admApplnId;
	}
	public String getCandidateRefNo() {
		return candidateRefNo;
	}
	public void setCandidateRefNo(String candidateRefNo) {
		this.candidateRefNo = candidateRefNo;
	}
	public String getTxnStatus() {
		return txnStatus;
	}
	public void setTxnStatus(String txnStatus) {
		this.txnStatus = txnStatus;
	}
	public BigDecimal getTxnAmount() {
		return txnAmount;
	}
	public void setTxnAmount(BigDecimal txnAmount) {
		this.txnAmount = txnAmount;
	}
	public String getTxnDate() {
		return txnDate;
	}
	public void setTxnDate(String txnDate) {
		this.txnDate = txnDate;
	}
	public String getTxnRefNo() {
		return txnRefNo;
	}
	public void setTxnRefNo(String txnRefNo) {
		this.txnRefNo = txnRefNo;
	}
	public String getBankId() {
		return bankId;
	}
	public void setBankId(String bankId) {
		this.bankId = bankId;
	}
	public String getBankRefNo() {
		return bankRefNo;
	}
	public void setBankRefNo(String bankRefNo) {
		this.bankRefNo = bankRefNo;
	}
}
