package com.kp.cms.to.inventory;

import java.util.Date;

import com.kp.cms.bo.admin.InvItem;
import com.kp.cms.bo.admin.InvStockReceipt;


public class InvPurchaseReturnItemTO {
	private int id;

	private InvItem invItemId;
	private String quantity;
	private String recievedQty;
	private InvStockReceipt stockReciept;
	// integer unit of received qty only
	private double receivedQuantity;
	private double alreadyRtndQty;
	private String alreadyRtndUnits;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private boolean isActive;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public InvItem getInvItemId() {
		return invItemId;
	}
	public void setInvItemId(InvItem invItemId) {
		this.invItemId = invItemId;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}
	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
	public boolean isActive() {
		return isActive;
	}
	public String getRecievedQty() {
		return recievedQty;
	}
	public void setRecievedQty(String recievedQty) {
		this.recievedQty = recievedQty;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	public double getReceivedQuantity() {
		return receivedQuantity;
	}
	public void setReceivedQuantity(double receivedQuantity) {
		this.receivedQuantity = receivedQuantity;
	}
	public InvStockReceipt getStockReciept() {
		return stockReciept;
	}
	public void setStockReciept(InvStockReceipt stockReciept) {
		this.stockReciept = stockReciept;
	}
	public double getAlreadyRtndQty() {
		return alreadyRtndQty;
	}
	public void setAlreadyRtndQty(double alreadyRtndQty) {
		this.alreadyRtndQty = alreadyRtndQty;
	}
	public String getAlreadyRtndUnits() {
		return alreadyRtndUnits;
	}
	public void setAlreadyRtndUnits(String alreadyRtndUnits) {
		this.alreadyRtndUnits = alreadyRtndUnits;
	}
	
	
}
