package com.kp.cms.to.admission;

import java.io.Serializable;

public class ApplicationStatusUpdateTO implements Serializable{
private int id;
private String applicationNo;
private String applicationStatus;
private String selected;
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getApplicationNo() {
	return applicationNo;
}
public void setApplicationNo(String applicationNo) {
	this.applicationNo = applicationNo;
}
public String getApplicationStatus() {
	return applicationStatus;
}
public void setApplicationStatus(String applicationStatus) {
	this.applicationStatus = applicationStatus;
}
public String getSelected() {
	return selected;
}
public void setSelected(String selected) {
	this.selected = selected;
}

}
