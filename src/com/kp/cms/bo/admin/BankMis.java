package com.kp.cms.bo.admin;

// Generated Jul 29, 2009 5:05:34 PM by Hibernate Tools 3.2.0.b9

import java.util.Date;

/**
 * BankMis generated by hbm2java
 */
public class BankMis implements java.io.Serializable {

	private int id;
	private Date txnDate;
	private String journalNo;
	private String txnBranch;
	private String refNo;

	public BankMis() {
	}

	public BankMis(int id) {
		this.id = id;
	}

	public BankMis(int id, Date txnDate, String journalNo, String txnBranch,
			String refNo) {
		this.id = id;
		this.txnDate = (Date)txnDate.clone();
		this.journalNo = journalNo;
		this.txnBranch = txnBranch;
		this.refNo = refNo;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getTxnDate() {
		return (Date)this.txnDate.clone();
	}

	public void setTxnDate(Date txnDate) {
		this.txnDate = (Date)txnDate.clone();
	}

	public String getJournalNo() {
		return this.journalNo;
	}

	public void setJournalNo(String journalNo) {
		this.journalNo = journalNo;
	}

	public String getTxnBranch() {
		return this.txnBranch;
	}

	public void setTxnBranch(String txnBranch) {
		this.txnBranch = txnBranch;
	}

	public String getRefNo() {
		return this.refNo;
	}

	public void setRefNo(String refNo) {
		this.refNo = refNo;
	}

}