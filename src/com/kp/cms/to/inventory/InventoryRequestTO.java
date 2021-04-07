package com.kp.cms.to.inventory;

import java.io.Serializable;

public class InventoryRequestTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String itemName;
	private String requestedBy;
	private String requestedQuantity;
	private String availableQuantity;
	private String difference;
	
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getRequestedBy() {
		return requestedBy;
	}
	public void setRequestedBy(String requestedBy) {
		this.requestedBy = requestedBy;
	}
	public String getRequestedQuantity() {
		return requestedQuantity;
	}
	public void setRequestedQuantity(String requestedQuantity) {
		this.requestedQuantity = requestedQuantity;
	}
	public String getAvailableQuantity() {
		return availableQuantity;
	}
	public void setAvailableQuantity(String availableQuantity) {
		this.availableQuantity = availableQuantity;
	}
	public String getDifference() {
		return difference;
	}
	public void setDifference(String difference) {
		this.difference = difference;
	}
}
