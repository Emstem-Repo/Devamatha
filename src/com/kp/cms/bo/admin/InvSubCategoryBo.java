package com.kp.cms.bo.admin;

import java.util.Date;

public class InvSubCategoryBo implements java.io.Serializable{
	
	private int id;
	private InvItemCategory invItemCategory;
	private String subCategoryName;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private Boolean isActive;
	
	public InvSubCategoryBo()
	{
		
	}
	public InvSubCategoryBo(int id,InvItemCategory invItemCategory,String  subCategoryName,String createdBy,Date createdDate,
			            String modifiedBy,Date lastModifiedDate,Boolean isActive)
	{
		
		this.id=id;
		this.invItemCategory=invItemCategory;
		this.subCategoryName=subCategoryName;
		this.createdBy=createdBy;
		this.createdDate=createdDate;
		this.modifiedBy=modifiedBy;
		this.lastModifiedDate=lastModifiedDate;
		this.isActive=isActive;
	}
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
	public String getSubCategoryName() {
		return subCategoryName;
	}
	public void setSubCategoryName(String subCategoryName) {
		this.subCategoryName = subCategoryName;
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
	public Boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	
}
