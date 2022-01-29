package com.kp.cms.to.admin;

import java.io.Serializable;
import java.util.Date;

public class DocTypeTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -886256295925904616L;
	private int id;
	private String name;
	private String description;
	private String docShortName;
	private String printName;
	
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private String isActive;
	private String cDate;
	private String lDate;
	private String isEducationalInfo;
	private Integer displayOrder;
	
	public DocTypeTO(){
		
	}
	public DocTypeTO(int id, String name, String description,
			String docShortName, String printName, String createdBy,
			Date createdDate, String modifiedBy, Date lastModifiedDate,
			String isActive, String cDate, String lDate,
			String isEducationalInfo, Integer displayOrder) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.docShortName = docShortName;
		this.printName = printName;
		this.createdBy = createdBy;
		this.createdDate = (Date)createdDate.clone();
		this.modifiedBy = modifiedBy;
		this.lastModifiedDate = (Date)lastModifiedDate.clone();
		this.isActive = isActive;
		this.cDate = cDate;
		this.lDate = lDate;
		this.isEducationalInfo = isEducationalInfo;
		this.displayOrder = displayOrder;
	}
	public String getIsEducationalInfo() {
		return isEducationalInfo;
	}
	public void setIsEducationalInfo(String isEducationalInfo) {
		this.isEducationalInfo = isEducationalInfo;
	}
	public String getCDate() {
		return cDate;
	}
	public void setCDate(String date) {
		cDate = date;
	}
	public String getLDate() {
		return lDate;
	}
	public void setLDate(String date) {
		lDate = date;
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDocShortName() {
		return docShortName;
	}
	public void setDocShortName(String docShortName) {
		this.docShortName = docShortName;
	}
	public String getPrintName() {
		return printName;
	}
	public void setPrintName(String printName) {
		this.printName = printName;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreatedDate() {
		return (Date)createdDate.clone();
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = (Date)createdDate.clone();
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public Date getLastModifiedDate() {
		return (Date)lastModifiedDate.clone();
	}
	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = (Date)lastModifiedDate.clone();
	}
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	public Integer getDisplayOrder() {
		return displayOrder;
	}
	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
	}
	
	
}