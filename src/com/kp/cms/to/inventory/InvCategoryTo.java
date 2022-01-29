package com.kp.cms.to.inventory;

import java.io.Serializable;

import com.kp.cms.bo.admin.InvItemCategory;

public class InvCategoryTo implements Serializable,Comparable<InvCategoryTo>{

	private int id;
	private String categoryName;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	
	@Override
	public int compareTo(InvCategoryTo o) {
		return getCategoryName().compareTo(o.getCategoryName());
	}

	
}
