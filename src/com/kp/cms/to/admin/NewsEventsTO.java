package com.kp.cms.to.admin;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class NewsEventsTO implements Serializable {
	
	private int id;
	private String name;
	
	private String createdBy;
	private Date createdDate;
	private String cDate;
	private String lDate;
	private String required;
	private String modifiedBy;
	private Date lastModifiedDate;
	private String isActive;
	
	public NewsEventsTO(){
		
	}
	public NewsEventsTO(int id, String name, String createdBy,
			Date createdDate, String cDate, String lDate, String required,
			String modifiedBy, Date lastModifiedDate, String isActive) {
		super();
		this.id = id;
		this.name = name;
		this.createdBy = createdBy;
		this.createdDate = (Date)createdDate.clone();
		this.cDate = cDate;
		this.lDate = lDate;
		this.required = required;
		this.modifiedBy = modifiedBy;
		this.lastModifiedDate = (Date)lastModifiedDate.clone();
		this.isActive = isActive;
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
	public String getRequired() {
		return required;
	}
	public void setRequired(String required) {
		this.required = required;
	}
	
}