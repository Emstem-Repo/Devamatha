package com.kp.cms.bo.admin;

// Generated Aug 27, 2009 2:58:28 PM by Hibernate Tools 3.2.0.b9

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * InvPurchaseReturn generated by hbm2java
 */
public class InvPurchaseReturn implements java.io.Serializable {

	private int id;
	private InvPurchaseOrder invPurchaseOrder;
	private String vendorBillNo;
	private Date vendorBillDate;
	private Date purchaseReturnDate;
	private String reasonForReturn;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private Boolean isActive;
	private InvLocation invLocation;
	private Set<InvPurchaseReturnItem> invPurchaseReturnItems = new HashSet<InvPurchaseReturnItem>(
			0);

	public InvPurchaseReturn() {
	}

	public InvPurchaseReturn(int id) {
		this.id = id;
	}

	public InvPurchaseReturn(int id, InvPurchaseOrder invPurchaseOrder, InvLocation invLocation,
			String vendorBillNo, Date vendorBillDate,Date purchaseReturnDate, String reasonForReturn,
			String createdBy, Date createdDate, String modifiedBy,
			Date lastModifiedDate, Boolean isActive,
			Set<InvPurchaseReturnItem> invPurchaseReturnItems) {
		this.id = id;
		this.invPurchaseOrder = invPurchaseOrder;
		this.vendorBillNo = vendorBillNo;
		this.purchaseReturnDate=purchaseReturnDate;
		this.vendorBillDate = vendorBillDate;
		this.reasonForReturn = reasonForReturn;
		this.invLocation = invLocation;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.lastModifiedDate = lastModifiedDate;
		this.isActive = isActive;
		this.invPurchaseReturnItems = invPurchaseReturnItems;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public InvPurchaseOrder getInvPurchaseOrder() {
		return this.invPurchaseOrder;
	}

	public void setInvPurchaseOrder(InvPurchaseOrder invPurchaseOrder) {
		this.invPurchaseOrder = invPurchaseOrder;
	}

	public String getVendorBillNo() {
		return this.vendorBillNo;
	}

	public void setVendorBillNo(String vendorBillNo) {
		this.vendorBillNo = vendorBillNo;
	}

	public Date getVendorBillDate() {
		return this.vendorBillDate;
	}

	public void setVendorBillDate(Date vendorBillDate) {
		this.vendorBillDate = vendorBillDate;
	}

	public Date getPurchaseReturnDate() {
		return purchaseReturnDate;
	}

	public void setPurchaseReturnDate(Date purchaseReturnDate) {
		this.purchaseReturnDate = purchaseReturnDate;
	}

	public String getReasonForReturn() {
		return this.reasonForReturn;
	}

	public void setReasonForReturn(String reasonForReturn) {
		this.reasonForReturn = reasonForReturn;
	}

	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getModifiedBy() {
		return this.modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getLastModifiedDate() {
		return this.lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public Boolean getIsActive() {
		return this.isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Set<InvPurchaseReturnItem> getInvPurchaseReturnItems() {
		return this.invPurchaseReturnItems;
	}

	public void setInvPurchaseReturnItems(
			Set<InvPurchaseReturnItem> invPurchaseReturnItems) {
		this.invPurchaseReturnItems = invPurchaseReturnItems;
	}

	public InvLocation getInvLocation() {
		return invLocation;
	}

	public void setInvLocation(InvLocation invLocation) {
		this.invLocation = invLocation;
	}

}
