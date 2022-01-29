package com.kp.cms.to.inventory;

import java.io.Serializable;

public class InvSubCategoryTo implements Serializable{
	
	private String invItemCategory;
	private String subCategoryName;
	private int id;
	
	public String getInvItemCategory() {
		return invItemCategory;
	}
	public void setInvItemCategory(String invItemCategory) {
		this.invItemCategory = invItemCategory;
	}
	public String getSubCategoryName() {
		return subCategoryName;
	}
	public void setSubCategoryName(String subCategoryName) {
		this.subCategoryName = subCategoryName;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

}
