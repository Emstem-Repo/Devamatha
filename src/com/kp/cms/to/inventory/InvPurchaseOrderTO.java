package com.kp.cms.to.inventory;

import java.util.Date;
import java.util.List;

public class InvPurchaseOrderTO {
	private int id;
	private int vendorId;
	private String vendorName;
	private String vendorAddr;
	private String orderNo;
	private String orderDate;
	private String remarks;
	private String termsandconditions;
	private String deliverySite;
	private String totalCost;
	private String additionalCost;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private boolean isActive;
	private List<InvPurchaseOrderItemTO> purchaseItems;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getVendorId() {
		return vendorId;
	}
	public void setVendorId(int vendorId) {
		this.vendorId = vendorId;
	}
	public String getVendorName() {
		return vendorName;
	}
	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getTermsandconditions() {
		return termsandconditions;
	}
	public void setTermsandconditions(String termsandconditions) {
		this.termsandconditions = termsandconditions;
	}
	public String getDeliverySite() {
		return deliverySite;
	}
	public void setDeliverySite(String deliverySite) {
		this.deliverySite = deliverySite;
	}
	public String getTotalCost() {
		return totalCost;
	}
	public void setTotalCost(String totalCost) {
		this.totalCost = totalCost;
	}
	public String getAdditionalCost() {
		return additionalCost;
	}
	public void setAdditionalCost(String additionalCost) {
		this.additionalCost = additionalCost;
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
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	public List<InvPurchaseOrderItemTO> getPurchaseItems() {
		return purchaseItems;
	}
	public void setPurchaseItems(List<InvPurchaseOrderItemTO> purchaseItems) {
		this.purchaseItems = purchaseItems;
	}
	public String getVendorAddr() {
		return vendorAddr;
	}
	public void setVendorAddr(String vendorAddr) {
		this.vendorAddr = vendorAddr;
	}
}
