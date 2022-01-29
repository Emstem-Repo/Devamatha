package com.kp.cms.bo.admin;

import java.io.Serializable;

public class ApplnMandatoryBO implements Serializable{
	private int id;
	private String displayName;
	private String propertyName;
	private Boolean validate;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public String getPropertyName() {
		return propertyName;
	}
	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}
	
	public Boolean getValidate() {
		return validate;
	}
	public void setValidate(Boolean validate) {
		this.validate = validate;
	}
	
	
}
