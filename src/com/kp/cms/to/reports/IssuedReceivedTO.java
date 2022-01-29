package com.kp.cms.to.reports;

public class IssuedReceivedTO {
	private String inventoryLocation;
	private String item;
	private String txType;
	private String txDate;
	private double openingBalance;
	private double currentBalance;
	private double differenceBalance;
	
	public String getInventoryLocation() {
		return inventoryLocation;
	}
	public String getItem() {
		return item;
	}
	public String getTxType() {
		return txType;
	}
	public String getTxDate() {
		return txDate;
	}
	public double getCurrentBalance() {
		return currentBalance;
	}
	public void setInventoryLocation(String inventoryLocation) {
		this.inventoryLocation = inventoryLocation;
	}
	public void setItem(String item) {
		this.item = item;
	}
	public void setTxType(String txType) {
		this.txType = txType;
	}
	public void setTxDate(String txDate) {
		this.txDate = txDate;
	}
	public double getOpeningBalance() {
		return openingBalance;
	}
	public void setOpeningBalance(double openingBalance) {
		this.openingBalance = openingBalance;
	}
	public void setCurrentBalance(double currentBalance) {
		this.currentBalance = currentBalance;
	}
	public double getDifferenceBalance() {
		return differenceBalance;
	}
	public void setDifferenceBalance(double differenceBalance) {
		this.differenceBalance = differenceBalance;
	}
	

}
