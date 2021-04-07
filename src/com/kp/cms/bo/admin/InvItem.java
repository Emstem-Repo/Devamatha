package com.kp.cms.bo.admin;

// Generated Aug 27, 2009 2:58:28 PM by Hibernate Tools 3.2.0.b9

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * InvItem generated by hbm2java
 */
public class InvItem implements java.io.Serializable {

	private int id;
	private InvUom invUomByInvIssueUomId;
	private InvItemCategory invItemCategory;
	private InvUom invUomByInvPurchaseUomId;
	private String code;
	private String name;
	private String description;
	private BigDecimal purchaseCost;
	private Boolean isWarranty;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private Boolean isActive;
	private BigDecimal conversion;
	private Set<InvTx> invTxes = new HashSet<InvTx>(0);
	private Set<InvRequestItem> invRequestItems = new HashSet<InvRequestItem>(0);
	private Set<InvQuotationItem> invQuotationItems = new HashSet<InvQuotationItem>(
			0);
	private Set<InvStockTransferItem> invStockTransferItems = new HashSet<InvStockTransferItem>(
			0);
	private Set<InvStockReceiptItem> invStockReceiptItems = new HashSet<InvStockReceiptItem>(
			0);
	private Set<InvAmc> invAmcs = new HashSet<InvAmc>(0);
	private Set<InvIssueItem> invIssueItems = new HashSet<InvIssueItem>(0);
	private Set<InvSalvageItem> invSalvageItems = new HashSet<InvSalvageItem>(0);
	private Set<InvPurchaseReturnItem> invPurchaseReturnItems = new HashSet<InvPurchaseReturnItem>(
			0);
	private Set<InvPurchaseOrderItem> invPurchaseOrderItems = new HashSet<InvPurchaseOrderItem>(
			0);
	private Set<InvItemStock> invItemStocks = new HashSet<InvItemStock>(0);
	private InvSubCategoryBo invItemSubCategory;
	private InvItemType invItemType;
	private int minStockQuantity;
	private String remarks;

	public InvItem() {
	}

	public InvItem(int id) {
		this.id = id;
	}

	public InvItem(int id, InvUom invUomByInvIssueUomId,
			InvItemCategory invItemCategory, InvUom invUomByInvPurchaseUomId,
			String code, String name, BigDecimal purchaseCost,String description,
			Boolean isWarranty, String createdBy, Date createdDate,
			String modifiedBy, Date lastModifiedDate, Boolean isActive,BigDecimal conversion,
			Set<InvTx> invTxes,
			Set<InvRequestItem> invRequestItems,
			Set<InvStockTransferItem> invStockTransferItems,
			Set<InvStockReceiptItem> invStockReceiptItems, Set<InvAmc> invAmcs,
			Set<InvIssueItem> invIssueItems,
			Set<InvSalvageItem> invSalvageItems,
			Set<InvPurchaseReturnItem> invPurchaseReturnItems,
			Set<InvPurchaseOrderItem> invPurchaseOrderItems,
			Set<InvItemStock> invItemStocks, Set<InvQuotationItem> invQuotationItems,InvSubCategoryBo invItemSubCategory,InvItemType invItemType,
			int minStockQuantity,String remarks) {
		this.id = id;
		this.invUomByInvIssueUomId = invUomByInvIssueUomId;
		this.invItemCategory = invItemCategory;
		this.invUomByInvPurchaseUomId = invUomByInvPurchaseUomId;
		this.code = code;
		this.name = name;
		this.description = description;
		this.purchaseCost = purchaseCost;
		this.isWarranty = isWarranty;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.lastModifiedDate = lastModifiedDate;
		this.isActive = isActive;
		this.conversion = conversion;
		this.invTxes = invTxes;
		this.invRequestItems = invRequestItems;
		this.invStockTransferItems = invStockTransferItems;
		this.invStockReceiptItems = invStockReceiptItems;
		this.invAmcs = invAmcs;
		this.invIssueItems = invIssueItems;
		this.invSalvageItems = invSalvageItems;
		this.invPurchaseReturnItems = invPurchaseReturnItems;
		this.invPurchaseOrderItems = invPurchaseOrderItems;
		this.invItemStocks = invItemStocks;
		this.invQuotationItems = invQuotationItems;
		this.invItemSubCategory=invItemSubCategory;
		this.invItemType=invItemType;
		this.remarks=remarks;
		this.minStockQuantity=minStockQuantity;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public InvUom getInvUomByInvIssueUomId() {
		return this.invUomByInvIssueUomId;
	}

	public void setInvUomByInvIssueUomId(InvUom invUomByInvIssueUomId) {
		this.invUomByInvIssueUomId = invUomByInvIssueUomId;
	}

	public InvItemCategory getInvItemCategory() {
		return this.invItemCategory;
	}

	public void setInvItemCategory(InvItemCategory invItemCategory) {
		this.invItemCategory = invItemCategory;
	}

	public InvUom getInvUomByInvPurchaseUomId() {
		return this.invUomByInvPurchaseUomId;
	}

	public void setInvUomByInvPurchaseUomId(InvUom invUomByInvPurchaseUomId) {
		this.invUomByInvPurchaseUomId = invUomByInvPurchaseUomId;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getPurchaseCost() {
		return this.purchaseCost;
	}

	public void setPurchaseCost(BigDecimal purchaseCost) {
		this.purchaseCost = purchaseCost;
	}

	public Boolean getIsWarranty() {
		return this.isWarranty;
	}

	public void setIsWarranty(Boolean isWarranty) {
		this.isWarranty = isWarranty;
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

	public Set<InvRequestItem> getInvRequestItems() {
		return this.invRequestItems;
	}

	public void setInvRequestItems(Set<InvRequestItem> invRequestItems) {
		this.invRequestItems = invRequestItems;
	}

	public Set<InvStockTransferItem> getInvStockTransferItems() {
		return this.invStockTransferItems;
	}

	public void setInvStockTransferItems(
			Set<InvStockTransferItem> invStockTransferItems) {
		this.invStockTransferItems = invStockTransferItems;
	}

	public Set<InvStockReceiptItem> getInvStockReceiptItems() {
		return this.invStockReceiptItems;
	}

	public void setInvStockReceiptItems(
			Set<InvStockReceiptItem> invStockReceiptItems) {
		this.invStockReceiptItems = invStockReceiptItems;
	}

	public Set<InvAmc> getInvAmcs() {
		return this.invAmcs;
	}

	public void setInvAmcs(Set<InvAmc> invAmcs) {
		this.invAmcs = invAmcs;
	}

	public Set<InvIssueItem> getInvIssueItems() {
		return this.invIssueItems;
	}

	public void setInvIssueItems(Set<InvIssueItem> invIssueItems) {
		this.invIssueItems = invIssueItems;
	}

	public Set<InvSalvageItem> getInvSalvageItems() {
		return this.invSalvageItems;
	}

	public void setInvSalvageItems(Set<InvSalvageItem> invSalvageItems) {
		this.invSalvageItems = invSalvageItems;
	}

	public Set<InvPurchaseReturnItem> getInvPurchaseReturnItems() {
		return this.invPurchaseReturnItems;
	}

	public void setInvPurchaseReturnItems(
			Set<InvPurchaseReturnItem> invPurchaseReturnItems) {
		this.invPurchaseReturnItems = invPurchaseReturnItems;
	}

	public Set<InvPurchaseOrderItem> getInvPurchaseOrderItems() {
		return this.invPurchaseOrderItems;
	}

	public void setInvPurchaseOrderItems(
			Set<InvPurchaseOrderItem> invPurchaseOrderItems) {
		this.invPurchaseOrderItems = invPurchaseOrderItems;
	}

	public Set<InvItemStock> getInvItemStocks() {
		return this.invItemStocks;
	}

	public void setInvItemStocks(Set<InvItemStock> invItemStocks) {
		this.invItemStocks = invItemStocks;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getConversion() {
		return conversion;
	}

	public void setConversion(BigDecimal conversion) {
		this.conversion = conversion;
	}

	public Set<InvTx> getInvTxes() {
		return invTxes;
	}

	public void setInvTxes(Set<InvTx> invTxes) {
		this.invTxes = invTxes;
	}

	public Set<InvQuotationItem> getInvQuotationItems() {
		return invQuotationItems;
	}

	public void setInvQuotationItems(Set<InvQuotationItem> invQuotationItems) {
		this.invQuotationItems = invQuotationItems;
	}

	public InvSubCategoryBo getInvItemSubCategory() {
		return invItemSubCategory;
	}

	public void setInvItemSubCategory(InvSubCategoryBo invItemSubCategory) {
		this.invItemSubCategory = invItemSubCategory;
	}

	public InvItemType getInvItemType() {
		return invItemType;
	}

	public void setInvItemType(InvItemType invItemType) {
		this.invItemType = invItemType;
	}

	public int getMinStockQuantity() {
		return minStockQuantity;
	}

	public void setMinStockQuantity(int minStockQuantity) {
		this.minStockQuantity = minStockQuantity;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

}
