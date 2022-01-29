package com.kp.cms.to.inventory;

import java.io.Serializable;


public class CashPurchaseTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int locationId;
	private String cashPurchaseDate;
	private String vendorName;

	public String getCashPurchaseDate() {
		return cashPurchaseDate;
	}
	public String getVendorName() {
		return vendorName;
	}
	public void setCashPurchaseDate(String cashPurchaseDate) {
		this.cashPurchaseDate = cashPurchaseDate;
	}
	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}
	public int getLocationId() {
		return locationId;
	}
	public void setLocationId(int locationId) {
		this.locationId = locationId;
	}


}
