package com.kp.cms.bo.admin;

// Generated Aug 27, 2009 2:58:28 PM by Hibernate Tools 3.2.0.b9

import java.math.BigDecimal;
import java.util.Date;

/**
 * InvStockTransferItem generated by hbm2java
 */
public class InvStockTransferItem implements java.io.Serializable {

	private int id;
	private InvStockTransfer invStockTransfer;
	private InvItem invItem;
	private BigDecimal quantity;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private Boolean isActive;

	public InvStockTransferItem() {
	}

	public InvStockTransferItem(int id) {
		this.id = id;
	}

	public InvStockTransferItem(int id, InvStockTransfer invStockTransfer,
			InvItem invItem, BigDecimal quantity, String createdBy,
			Date createdDate, String modifiedBy, Date lastModifiedDate,
			Boolean isActive) {
		this.id = id;
		this.invStockTransfer = invStockTransfer;
		this.invItem = invItem;
		this.quantity = quantity;
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

	public InvStockTransfer getInvStockTransfer() {
		return this.invStockTransfer;
	}

	public void setInvStockTransfer(InvStockTransfer invStockTransfer) {
		this.invStockTransfer = invStockTransfer;
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

}
