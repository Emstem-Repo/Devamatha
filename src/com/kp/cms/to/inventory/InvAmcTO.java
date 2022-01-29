package com.kp.cms.to.inventory;

import java.util.Date;

import com.kp.cms.bo.admin.InvItem;
import com.kp.cms.bo.admin.InvItemCategory;
import com.kp.cms.bo.admin.InvStockReceiptItem;

public class InvAmcTO {
	private int id;
	private InvItemCategory invItemCategory;

	private InvItem invItem;
	private String itemNo;
	private String warrantyStartDate;
	private String warrantyEndDate;
	private String comments;
	private String newWarrantyStartDate;
	private String newWarrantyEndDate;
	private InvStockReceiptItem invStockReceiptItem;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private Boolean isActive;
	private Character warrantyAmcFalg;
	private String locationName;
	private String vendorname;
	private String amcWarranty;
	private int vendorId;
	private String amount;
	private boolean sendMailSelected;
	private String toMailAddress;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public InvItemCategory getInvItemCategory() {
		return invItemCategory;
	}

	public void setInvItemCategory(InvItemCategory invItemCategory) {
		this.invItemCategory = invItemCategory;
	}

	public InvItem getInvItem() {
		return invItem;
	}

	public void setInvItem(InvItem invItem) {
		this.invItem = invItem;
	}

	public String getItemNo() {
		return itemNo;
	}

	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}

	public String getWarrantyStartDate() {
		return warrantyStartDate;
	}

	public void setWarrantyStartDate(String warrantyStartDate) {
		this.warrantyStartDate = warrantyStartDate;
	}

	public String getWarrantyEndDate() {
		return warrantyEndDate;
	}

	public void setWarrantyEndDate(String warrantyEndDate) {
		this.warrantyEndDate = warrantyEndDate;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getNewWarrantyStartDate() {
		return newWarrantyStartDate;
	}

	public String getNewWarrantyEndDate() {
		return newWarrantyEndDate;
	}

	public void setNewWarrantyStartDate(String newWarrantyStartDate) {
		this.newWarrantyStartDate = newWarrantyStartDate;
	}

	public void setNewWarrantyEndDate(String newWarrantyEndDate) {
		this.newWarrantyEndDate = newWarrantyEndDate;
	}

	public InvStockReceiptItem getInvStockReceiptItem() {
		return invStockReceiptItem;
	}

	public void setInvStockReceiptItem(InvStockReceiptItem invStockReceiptItem) {
		this.invStockReceiptItem = invStockReceiptItem;
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

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Character getWarrantyAmcFalg() {
		return warrantyAmcFalg;
	}

	public void setWarrantyAmcFalg(Character warrantyAmcFalg) {
		this.warrantyAmcFalg = warrantyAmcFalg;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public String getVendorname() {
		return vendorname;
	}

	public void setVendorname(String vendorname) {
		this.vendorname = vendorname;
	}

	public String getAmcWarranty() {
		return amcWarranty;
	}

	public void setAmcWarranty(String amcWarranty) {
		this.amcWarranty = amcWarranty;
	}

	public int getVendorId() {
		return vendorId;
	}

	public String getAmount() {
		return amount;
	}

	public void setVendorId(int vendorId) {
		this.vendorId = vendorId;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public boolean isSendMailSelected() {
		return sendMailSelected;
	}

	public void setSendMailSelected(boolean sendMailSelected) {
		this.sendMailSelected = sendMailSelected;
	}

	public String getToMailAddress() {
		return toMailAddress;
	}

	public void setToMailAddress(String toMailAddress) {
		this.toMailAddress = toMailAddress;
	}
}
