package com.kp.cms.to.inventory;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ItemTO implements Serializable,Comparable<ItemTO>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String createdBy;;
	private String modifiedBy;
	private Date createdDate;
	private Date lastModifiedDate;
	private String code;
	private String name;
	private String description;
	private BigDecimal purchaseCost;
	private BigDecimal conversion;
	private Boolean isWarranty;
	private int itemCategoryId;
	private String itemCategoryName;
	private int purchaseUomId;
	private int issueUomId;
	private String purchaseUomName;
	private String issueUomName;
	private String quantityIssued;
	private String nameWithCode;
	private String requestedQuantity;
	private String invLocationId;
	private String vendorName;
	private String availableQuantity;
	private String itemLabel;
	private String remarks;
	private String totalCost;
	private String purchaseDate;
	private String vendorId;
	private String itemType;
	private String itemSubCategory;
	private int minStockQuantity;
	private int itemSubCategoryId;
	private int itemTypeId;
	
	public String getTotalCost() {
		return totalCost;
	}
	public void setTotalCost(String totalCost) {
		this.totalCost = totalCost;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getItemLabel() {
		return itemLabel;
	}
	public void setItemLabel(String itemLabel) {
		this.itemLabel = itemLabel;
	}
	public String getAvailableQuantity() {
		return availableQuantity;
	}
	public void setAvailableQuantity(String availableQuantity) {
		this.availableQuantity = availableQuantity;
	}
	public String getQuantityIssued() {
		return quantityIssued;
	}
	public void setQuantityIssued(String quantityIssued) {
		this.quantityIssued = quantityIssued;
	}
	public int getItemCategoryId() {
		return itemCategoryId;
	}
	public void setItemCategoryId(int itemCategoryId) {
		this.itemCategoryId = itemCategoryId;
	}
	public String getItemCategoryName() {
		return itemCategoryName;
	}
	public void setItemCategoryName(String itemCategoryName) {
		this.itemCategoryName = itemCategoryName;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}
	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public BigDecimal getPurchaseCost() {
		return purchaseCost;
	}
	public void setPurchaseCost(BigDecimal purchaseCost) {
		this.purchaseCost = purchaseCost;
	}
	public Boolean getIsWarranty() {
		return isWarranty;
	}
	public void setIsWarranty(Boolean isWarranty) {
		this.isWarranty = isWarranty;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNameWithCode() {
		return nameWithCode;
	}
	public void setNameWithCode(String nameWithCode) {
		this.nameWithCode = nameWithCode;
	}
	public int getPurchaseUomId() {
		return purchaseUomId;
	}
	public void setPurchaseUomId(int purchaseUomId) {
		this.purchaseUomId = purchaseUomId;
	}
	public int getIssueUomId() {
		return issueUomId;
	}
	public void setIssueUomId(int issueUomId) {
		this.issueUomId = issueUomId;
	}
	public BigDecimal getConversion() {
		return conversion;
	}
	public void setConversion(BigDecimal conversion) {
		this.conversion = conversion;
	}
	public String getPurchaseUomName() {
		return purchaseUomName;
	}
	public void setPurchaseUomName(String purchaseUomName) {
		this.purchaseUomName = purchaseUomName;
	}
	public String getIssueUomName() {
		return issueUomName;
	}
	public void setIssueUomName(String issueUomName) {
		this.issueUomName = issueUomName;
	}
	public String getRequestedQuantity() {
		return requestedQuantity;
	}
	public void setRequestedQuantity(String requestedQuantity) {
		this.requestedQuantity = requestedQuantity;
	}
	public String getVendorName() {
		return vendorName;
	}
	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}
	public String getInvLocationId() {
		return invLocationId;
	}
	public void setInvLocationId(String invLocationId) {
		this.invLocationId = invLocationId;
	}
	public String getPurchaseDate() {
		return purchaseDate;
	}
	public void setPurchaseDate(String puchaseDate) {
		this.purchaseDate = puchaseDate;
	}
	public String getVendorId() {
		return vendorId;
	}
	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
	}
	public String getItemType() {
		return itemType;
	}
	public void setItemType(String itemType) {
		this.itemType = itemType;
	}
	public String getItemSubCategory() {
		return itemSubCategory;
	}
	public void setItemSubCategory(String itemSubCategory) {
		this.itemSubCategory = itemSubCategory;
	}
	public int getMinStockQuantity() {
		return minStockQuantity;
	}
	public void setMinStockQuantity(int minStockQuantity) {
		this.minStockQuantity = minStockQuantity;
	}
	public int getItemSubCategoryId() {
		return itemSubCategoryId;
	}
	public void setItemSubCategoryId(int itemSubCategoryId) {
		this.itemSubCategoryId = itemSubCategoryId;
	}
	public int getItemTypeId() {
		return itemTypeId;
	}
	public void setItemTypeId(int itemTypeId) {
		this.itemTypeId = itemTypeId;
	}
	@Override
	public int compareTo(ItemTO o) {
		return getName().compareTo(o.getName());
	}	
	
}