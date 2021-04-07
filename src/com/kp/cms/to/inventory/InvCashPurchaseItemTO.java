package com.kp.cms.to.inventory;

import java.util.Date;

public class InvCashPurchaseItemTO {
	private int id;
	private CashPurchaseTO cashPurchaseTO;
	private String quantity;
	private String purchasePrice;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private Boolean isActive;
	private String comments;
	private String itemName;
	
	public int getId() {
		return id;
	}
	public CashPurchaseTO getCashPurchaseTO() {
		return cashPurchaseTO;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}
	public Boolean getIsActive() {
		return isActive;
	}
	public String getComments() {
		return comments;
	}
	public String getItemName() {
		return itemName;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setCashPurchaseTO(CashPurchaseTO cashPurchaseTO) {
		this.cashPurchaseTO = cashPurchaseTO;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getQuantity() {
		return quantity;
	}
	public String getPurchasePrice() {
		return purchasePrice;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public void setPurchasePrice(String purchasePrice) {
		this.purchasePrice = purchasePrice;
	}
	
}
