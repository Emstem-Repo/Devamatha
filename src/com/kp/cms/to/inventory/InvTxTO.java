package com.kp.cms.to.inventory;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.kp.cms.bo.admin.InvItem;
import com.kp.cms.bo.admin.InvLocation;

public class InvTxTO {
	private int id;
	private InvLocation invLocation;
	private InvItem invItem;
	private int referenceId;
	private String txType;
	private Date txDate;
	private String txDisplayDate;
	private double quantity;
	private double openingBalance;
	private double closingBalance;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private boolean isActive;
	private List<StockReportTO> listInvTo;
	private String operation;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public InvLocation getInvLocation() {
		return invLocation;
	}
	public void setInvLocation(InvLocation invLocation) {
		this.invLocation = invLocation;
	}
	public InvItem getInvItem() {
		return invItem;
	}
	public void setInvItem(InvItem invItem) {
		this.invItem = invItem;
	}
	public int getReferenceId() {
		return referenceId;
	}
	public void setReferenceId(int referenceId) {
		this.referenceId = referenceId;
	}
	public String getTxType() {
		return txType;
	}
	public void setTxType(String txType) {
		this.txType = txType;
	}
	public Date getTxDate() {
		return txDate;
	}
	public void setTxDate(Date txDate) {
		this.txDate = txDate;
	}
	public double getQuantity() {
		return quantity;
	}
	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}
	public double getOpeningBalance() {
		return openingBalance;
	}
	public void setOpeningBalance(double openingBalance) {
		this.openingBalance = openingBalance;
	}
	public double getClosingBalance() {
		return closingBalance;
	}
	public void setClosingBalance(double closingBalance) {
		this.closingBalance = closingBalance;
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
	public String getTxDisplayDate() {
		return txDisplayDate;
	}
	public void setTxDisplayDate(String txDisplayDate) {
		this.txDisplayDate = txDisplayDate;
	}
	public void setListInvTo(List<StockReportTO> listInvTo) {
		this.listInvTo = listInvTo;
	}
	public List<StockReportTO> getListInvTo() {
		return listInvTo;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	public String getOperation() {
		return operation;
	}
	
	
	
}
