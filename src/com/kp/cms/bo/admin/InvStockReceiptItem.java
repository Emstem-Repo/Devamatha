package com.kp.cms.bo.admin;

// Generated Aug 27, 2009 2:58:28 PM by Hibernate Tools 3.2.0.b9

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * InvStockReceiptItem generated by hbm2java
 */
public class InvStockReceiptItem implements java.io.Serializable {

	private int id;
	private InvStockReceipt invStockReceipt;
	private InvItem invItem;
	private BigDecimal quantity;
	private BigDecimal purchasePrice;
	private String productNo;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private Boolean isActive;
	private Set<InvAmc> invAmcs = new HashSet<InvAmc>(0);

	public InvStockReceiptItem() {
	}

	public InvStockReceiptItem(int id) {
		this.id = id;
	}

	public InvStockReceiptItem(int id, InvStockReceipt invStockReceipt,
			InvItem invItem, BigDecimal quantity, BigDecimal purchasePrice,
			String productNo, String createdBy, Date createdDate,
			String modifiedBy, Date lastModifiedDate, Boolean isActive,
			Set<InvAmc> invAmcs) {
		this.id = id;
		this.invStockReceipt = invStockReceipt;
		this.invItem = invItem;
		this.quantity = quantity;
		this.purchasePrice = purchasePrice;
		this.productNo = productNo;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.lastModifiedDate = lastModifiedDate;
		this.isActive = isActive;
		this.invAmcs = invAmcs;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public InvStockReceipt getInvStockReceipt() {
		return this.invStockReceipt;
	}

	public void setInvStockReceipt(InvStockReceipt invStockReceipt) {
		this.invStockReceipt = invStockReceipt;
	}

	public InvItem getInvItem() {
		return this.invItem;
	}

	public void setInvItem(InvItem invItem) {
		this.invItem = invItem;
	}

	public BigDecimal getQuantity() {
		return this.quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getPurchasePrice() {
		return this.purchasePrice;
	}

	public void setPurchasePrice(BigDecimal purchasePrice) {
		this.purchasePrice = purchasePrice;
	}

	public String getProductNo() {
		return this.productNo;
	}

	public void setProductNo(String productNo) {
		this.productNo = productNo;
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

	public Set<InvAmc> getInvAmcs() {
		return this.invAmcs;
	}

	public void setInvAmcs(Set<InvAmc> invAmcs) {
		this.invAmcs = invAmcs;
	}

}
