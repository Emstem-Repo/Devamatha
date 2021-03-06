package com.kp.cms.bo.admin;

// Generated Aug 27, 2009 2:58:28 PM by Hibernate Tools 3.2.0.b9

import java.math.BigDecimal;
import java.util.Date;

/**
 * InvPurchaseOrderItem generated by hbm2java
 */
public class InvPurchaseOrderItem implements java.io.Serializable {

	private int id;
	private InvItem invItem;
	private InvPurchaseOrder invPurchaseOrder;
	private BigDecimal quantity;
	private BigDecimal unitCost;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private Boolean isActive;
	private BigDecimal unitDiscount;
	private BigDecimal vat;
	private BigDecimal vatCost;

	public InvPurchaseOrderItem() {
	}

	public InvPurchaseOrderItem(int id) {
		this.id = id;
	}

	public InvPurchaseOrderItem(int id, InvItem invItem,
			InvPurchaseOrder invPurchaseOrder, BigDecimal quantity,
			BigDecimal unitCost, String createdBy, Date createdDate,
			String modifiedBy, Date lastModifiedDate, Boolean isActive) {
		this.id = id;
		this.invItem = invItem;
		this.invPurchaseOrder = invPurchaseOrder;
		this.quantity = quantity;
		this.unitCost = unitCost;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.lastModifiedDate = lastModifiedDate;
		this.isActive = isActive;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public InvItem getInvItem() {
		return this.invItem;
	}

	public void setInvItem(InvItem invItem) {
		this.invItem = invItem;
	}

	public InvPurchaseOrder getInvPurchaseOrder() {
		return this.invPurchaseOrder;
	}

	public void setInvPurchaseOrder(InvPurchaseOrder invPurchaseOrder) {
		this.invPurchaseOrder = invPurchaseOrder;
	}

	public BigDecimal getQuantity() {
		return this.quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getUnitCost() {
		return this.unitCost;
	}

	public void setUnitCost(BigDecimal unitCost) {
		this.unitCost = unitCost;
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

	public BigDecimal getUnitDiscount() {
		return unitDiscount;
	}

	public void setUnitDiscount(BigDecimal unitDiscount) {
		this.unitDiscount = unitDiscount;
	}

	public BigDecimal getVat() {
		return vat;
	}

	public void setVat(BigDecimal vat) {
		this.vat = vat;
	}

	public BigDecimal getVatCost() {
		return vatCost;
	}

	public void setVatCost(BigDecimal vatCost) {
		this.vatCost = vatCost;
	}

}
