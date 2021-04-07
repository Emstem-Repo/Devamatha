package com.kp.cms.to.exam;

import java.io.Serializable;

public class ExtendSupplyImprApplDateTo  implements Serializable{
private int id;
private String className;
private String startDate;
private String endDate;
private String checked;
private String extendedDate;
private String extendedFineDate;
private String extendedSuperFineDate;
private String fineAmount;
private String superFineAmount;
private String extendedFineStartDate;
private String extendedSuperFineStartDate;


public String getExtendedDate() {
	return extendedDate;
}
public void setExtendedDate(String extendedDate) {
	this.extendedDate = extendedDate;
}
public String getChecked() {
	return checked;
}
public void setChecked(String checked) {
	this.checked = checked;
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getClassName() {
	return className;
}
public void setClassName(String className) {
	this.className = className;
}
public String getStartDate() {
	return startDate;
}
public void setStartDate(String startDate) {
	this.startDate = startDate;
}
public String getEndDate() {
	return endDate;
}
public void setEndDate(String endDate) {
	this.endDate = endDate;
}
public String getExtendedFineDate() {
	return extendedFineDate;
}
public void setExtendedFineDate(String extendedFineDate) {
	this.extendedFineDate = extendedFineDate;
}
public String getExtendedSuperFineDate() {
	return extendedSuperFineDate;
}
public void setExtendedSuperFineDate(String extendedSuperFineDate) {
	this.extendedSuperFineDate = extendedSuperFineDate;
}
public String getFineAmount() {
	return fineAmount;
}
public void setFineAmount(String fineAmount) {
	this.fineAmount = fineAmount;
}
public String getSuperFineAmount() {
	return superFineAmount;
}
public void setSuperFineAmount(String superFineAmount) {
	this.superFineAmount = superFineAmount;
}
public String getExtendedFineStartDate() {
	return extendedFineStartDate;
}
public void setExtendedFineStartDate(String extendedFineStartDate) {
	this.extendedFineStartDate = extendedFineStartDate;
}
public String getExtendedSuperFineStartDate() {
	return extendedSuperFineStartDate;
}
public void setExtendedSuperFineStartDate(String extendedSuperFineStartDate) {
	this.extendedSuperFineStartDate = extendedSuperFineStartDate;
}


}
