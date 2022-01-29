package com.kp.cms.to.inventory;

import java.util.Date;
import java.util.List;

import com.kp.cms.bo.admin.InvItem;

public class InvPurchaseOrderItemTO {
	private int id;
	private InvItemTO invItem;
	private InvItem origItem;
	private int countId;
	private int invPurchaseOrderId;
	private String quantity;
	private String unitCost;
	private String totalCost;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private boolean isActive;
	private String discount;
	private List<InvItemTO> itemList;
	private String selectedItemId;
	private String vat;
	private String vatCost;
	private String totalCostInclusiveVat;
	private int quotationItemId;
	private int poItemId;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public InvItemTO getInvItem() {
		return invItem;
	}
	public void setInvItem(InvItemTO invItem) {
		this.invItem = invItem;
	}
	public int getInvPurchaseOrderId() {
		return invPurchaseOrderId;
	}
	public void setInvPurchaseOrderId(int invPurchaseOrderId) {
		this.invPurchaseOrderId = invPurchaseOrderId;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public String getUnitCost() {
		return unitCost;
	}
	public void setUnitCost(String unitCost) {
		this.unitCost = unitCost;
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
	public int getCountId() {
		return countId;
	}
	public void setCountId(int countId) {
		this.countId = countId;
	}
	public String getTotalCost() {
		return totalCost;
	}
	public void setTotalCost(String totalCost) {
		this.totalCost = totalCost;
	}
	public InvItem getOrigItem() {
		return origItem;
	}
	public void setOrigItem(InvItem origItem) {
		this.origItem = origItem;
	}
	public String getDiscount() {
		return discount;
	}
	public void setDiscount(String discount) {
		this.discount = discount;
	}
	public List<InvItemTO> getItemList() {
		return itemList;
	}
	public void setItemList(List<InvItemTO> itemList) {
		this.itemList = itemList;
	}
	public String getSelectedItemId() {
		return selectedItemId;
	}
	public void setSelectedItemId(String selectedItemId) {
		this.selectedItemId = selectedItemId;
	}
	public String getVat() {
		return vat;
	}
	public void setVat(String vat) {
		this.vat = vat;
	}
	public String getVatCost() {
		return vatCost;
	}
	public void setVatCost(String vatCost) {
		this.vatCost = vatCost;
	}
	public String getTotalCostInclusiveVat() {
		return totalCostInclusiveVat;
	}
	public void setTotalCostInclusiveVat(String totalCostInclusiveVat) {
		this.totalCostInclusiveVat = totalCostInclusiveVat;
	}
	public int getQuotationItemId() {
		return quotationItemId;
	}
	public void setQuotationItemId(int quotationItemId) {
		this.quotationItemId = quotationItemId;
	}
	public int getPoItemId() {
		return poItemId;
	}
	public void setPoItemId(int poItemId) {
		this.poItemId = poItemId;
	}
	
}
