package com.kp.cms.forms.inventory;

import com.kp.cms.forms.BaseActionForm;

public class POTermsConditionsForm extends BaseActionForm {
	
private String tcDescription;
private String id;
private String savedTcDescription;
private boolean tcDescExists;
private boolean tcDescInActive;

public String getTcDescription() {
	return tcDescription;
}

public void setTcDescription(String tcDescription) {
	this.tcDescription = tcDescription;
}

public String getId() {
	return id;
}

public void setId(String id) {
	this.id = id;
}

public String getSavedTcDescription() {
	return savedTcDescription;
}

public void setSavedTcDescription(String savedTcDescription) {
	this.savedTcDescription = savedTcDescription;
}

public boolean isTcDescExists() {
	return tcDescExists;
}

public void setTcDescExists(boolean tcDescExists) {
	this.tcDescExists = tcDescExists;
}

public boolean isTcDescInActive() {
	return tcDescInActive;
}

public void setTcDescInActive(boolean tcDescInActive) {
	this.tcDescInActive = tcDescInActive;
}

public void resetFields() {
	this.tcDescription=null;
	this.tcDescExists=false;
	this.tcDescInActive=false;
}
}
