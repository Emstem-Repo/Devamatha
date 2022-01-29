package com.kp.cms.to.pettycash;

import java.io.Serializable;

public class ConsolidatedCollectionLedgerTO implements Serializable {
	private String date;
	private String accountNo;
	private String netAmount;
	private String dateDis;
	private String accountNoDis;
	private String netAmountDis;
	private short datePos;
	private short accountNoPos;
	private short netAmountPos;
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	public String getNetAmount() {
		return netAmount;
	}
	public void setNetAmount(String netAmount) {
		this.netAmount = netAmount;
	}
	public String getDateDis() {
		return dateDis;
	}
	public void setDateDis(String dateDis) {
		this.dateDis = dateDis;
	}
	public String getAccountNoDis() {
		return accountNoDis;
	}
	public void setAccountNoDis(String accountNoDis) {
		this.accountNoDis = accountNoDis;
	}
	public String getNetAmountDis() {
		return netAmountDis;
	}
	public void setNetAmountDis(String netAmountDis) {
		this.netAmountDis = netAmountDis;
	}
	public short getDatePos() {
		return datePos;
	}
	public void setDatePos(short datePos) {
		this.datePos = datePos;
	}
	public short getAccountNoPos() {
		return accountNoPos;
	}
	public void setAccountNoPos(short accountNoPos) {
		this.accountNoPos = accountNoPos;
	}
	public short getNetAmountPos() {
		return netAmountPos;
	}
	public void setNetAmountPos(short netAmountPos) {
		this.netAmountPos = netAmountPos;
	}
	
	
}
