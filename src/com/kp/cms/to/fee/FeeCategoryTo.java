package com.kp.cms.to.fee;

import java.io.Serializable;

public class FeeCategoryTo implements Serializable{
	
	private int feeCategoryId;
	private String feeCategoryName;
	
	public int getFeeCategoryId() {
		return feeCategoryId;
	}
	public void setFeeCategoryId(int feeCategoryId) {
		this.feeCategoryId = feeCategoryId;
	}
	public String getFeeCategoryName() {
		return feeCategoryName;
	}
	public void setFeeCategoryName(String feeCategoryName) {
		this.feeCategoryName = feeCategoryName;
	}
	
	
}
