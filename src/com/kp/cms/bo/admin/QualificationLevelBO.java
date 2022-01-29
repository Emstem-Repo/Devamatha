package com.kp.cms.bo.admin;

import java.io.Serializable;
import java.util.Date;

public class QualificationLevelBO implements Serializable{
private int id;
private String name;
private Integer displayOrder;
private Boolean fixedDisplay;
private String createdBy;
private Date createdDate;
private String modifiedBy;
private Date lastModifiedDate;
private Boolean isActive;
private Boolean phdQualification;

/**Default QualificationLevelBO Constructor
 * 
 */
public QualificationLevelBO() {
	
}
/**One argument QualificationLevelBO  Constructor
 * 
 */
public QualificationLevelBO(int id) {
	this.id=id;
}



/**
 * @param id
 * @param name
 * @param createdBy
 * @param createdDate
 * @param modifiedBy
 * @param lastModifiedDate
 * @param isActive
 */
public QualificationLevelBO(int id, String name,Integer displayOrder,Boolean fixedDisplay,Boolean phdQualification, String createdBy,
		Date createdDate, String modifiedBy, Date lastModifiedDate,
		Boolean isActive) {
	super();
	this.id = id;
	this.name = name;
	this.setDisplayOrder(displayOrder);
	this.fixedDisplay=fixedDisplay;
	this.phdQualification=phdQualification;
	this.createdBy = createdBy;
	this.createdDate = createdDate;
	this.modifiedBy = modifiedBy;
	this.lastModifiedDate = lastModifiedDate;
	this.isActive=isActive;
}
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


public void setDisplayOrder(Integer displayOrder) {
	this.displayOrder = displayOrder;
}
public Integer getDisplayOrder() {
	return displayOrder;
}
public Boolean isFixedDisplay() {
	return fixedDisplay;
}
public void setFixedDisplay(Boolean fixedDisplay) {
	this.fixedDisplay = fixedDisplay;
}
public Boolean getFixedDisplay() {
	return fixedDisplay;
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
public void setLastModifiedDate(Date lastModifiedDate) {
	this.lastModifiedDate = lastModifiedDate;
}
public Date getLastModifiedDate() {
	return lastModifiedDate;
}
public void setIsActive(Boolean isActive) {
	this.isActive = isActive;
}
public Boolean getIsActive() {
	return isActive;
}
public Boolean isPhdQualification() {
	return phdQualification;
}
public void setPhdQualification(Boolean phdQualification) {
	this.phdQualification = phdQualification;
}


}
