package com.kp.cms.to.pettycash;

import java.io.Serializable;
import java.util.List;

public class ConsolidatedCollectionReportTO implements Serializable{
	private String accountCode;
	private String accountName;
	private String totalNumber;
	private String fixedAmount;
	private double totalAmount;
	private double amount;
	private String accNo;
	private List<AccountHeadTO> accountList;
	public List<AccountHeadTO> getAccountList() {
		return accountList;
	}
	public void setAccountList(List<AccountHeadTO> accountList) {
		this.accountList = accountList;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getAccountCode() {
		return accountCode;
	}
	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getTotalNumber() {
		return totalNumber;
	}
	public void setTotalNumber(String totalNumber) {
		this.totalNumber = totalNumber;
	}
	
	public String getFixedAmount() {
		return fixedAmount;
	}
	public void setFixedAmount(String fixedAmount) {
		this.fixedAmount = fixedAmount;
	}
	public double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}
	public String getAccNo() {
		return accNo;
	}
	public void setAccNo(String accNo) {
		this.accNo = accNo;
	}
	
}
