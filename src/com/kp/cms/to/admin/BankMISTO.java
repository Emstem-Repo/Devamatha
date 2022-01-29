package com.kp.cms.to.admin;

import java.io.Serializable;
import java.util.Date;

public class BankMISTO implements Serializable {
	
	private Date txnDate;
	private String txnBranch;
	private String journalNo;
	private String refNumber;

	
	public String getJournalNo() {
		return journalNo;
	}
	public void setJournalNo(String journalNo) {
		this.journalNo = journalNo;
	}
	public Date getTxnDate() {
		return txnDate;
	}
	public void setTxnDate(Date txnDate) {
		this.txnDate = txnDate;
	}
	public String getTxnBranch() {
		return txnBranch;
	}
	public void setTxnBranch(String txnBranch) {
		this.txnBranch = txnBranch;
	}
	public String getRefNumber() {
		return refNumber;
	}
	public void setRefNumber(String refNumber) {
		this.refNumber = refNumber;
	}
}