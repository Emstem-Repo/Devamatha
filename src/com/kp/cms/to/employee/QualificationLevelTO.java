package com.kp.cms.to.employee;

public class QualificationLevelTO {
private int id;
private String name;
private Integer displayOrder;
private String fixedDisplay;
private String phdQualification;
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public int getDisplayOrder() {
	return displayOrder;
}
public void setDisplayOrder(int displayOrder) {
	this.displayOrder = displayOrder;
}
public void setFixedDisplay(String fixedDisplay) {
	this.fixedDisplay = fixedDisplay;
}
public String getFixedDisplay() {
	return fixedDisplay;
}
public String getPhdQualification() {
	return phdQualification;
}
public void setPhdQualification(String phdQualification) {
	this.phdQualification = phdQualification;
}
public void setDisplayOrder(Integer displayOrder) {
	this.displayOrder = displayOrder;
}


}
