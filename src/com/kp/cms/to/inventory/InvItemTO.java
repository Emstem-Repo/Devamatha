package com.kp.cms.to.inventory;


public class InvItemTO  implements Comparable<InvItemTO>{
	private int id;
	private int invUomByInvIssueUomId;
	private String invUomName;
	private int invItemCategoryId;
	private int invUomByInvPurchaseUomId;
	private String code;
	private String name;
	private String purchaseCost;
	private boolean isWarranty;
	private String createdBy;
	private String createdDate;
	private String modifiedBy;
	private String lastModifiedDate;
	private boolean isActive;
	private String nameWithCode;
	public String getNameWithCode() {
		return nameWithCode;
	}
	public void setNameWithCode(String nameWithCode) {
		this.nameWithCode = nameWithCode;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getInvUomByInvIssueUomId() {
		return invUomByInvIssueUomId;
	}
	public void setInvUomByInvIssueUomId(int invUomByInvIssueUomId) {
		this.invUomByInvIssueUomId = invUomByInvIssueUomId;
	}
	public int getInvItemCategoryId() {
		return invItemCategoryId;
	}
	public void setInvItemCategoryId(int invItemCategoryId) {
		this.invItemCategoryId = invItemCategoryId;
	}
	public int getInvUomByInvPurchaseUomId() {
		return invUomByInvPurchaseUomId;
	}
	public void setInvUomByInvPurchaseUomId(int invUomByInvPurchaseUomId) {
		this.invUomByInvPurchaseUomId = invUomByInvPurchaseUomId;
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
	public String getPurchaseCost() {
		return purchaseCost;
	}
	public void setPurchaseCost(String purchaseCost) {
		this.purchaseCost = purchaseCost;
	}
	public boolean isWarranty() {
		return isWarranty;
	}
	public void setWarranty(boolean isWarranty) {
		this.isWarranty = isWarranty;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public String getLastModifiedDate() {
		return lastModifiedDate;
	}
	public void setLastModifiedDate(String lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	public String getInvUomName() {
		return invUomName;
	}
	public void setInvUomName(String invUomName) {
		this.invUomName = invUomName;
	}
	@Override
	public int compareTo(InvItemTO o) {
		return getNameWithCode().compareTo(o.getNameWithCode());
	}
	
}
