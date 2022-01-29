package com.kp.cms.to.admin;

import java.io.Serializable;
import java.util.Date;

public class DocTypeExamsTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2794655452984910845L;
	private int id;
	private String docTypeId;
	private String name;
	private boolean isActive;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private String docTypeName;
	
	public DocTypeExamsTO(){
		
	}
	public DocTypeExamsTO(int id, String docTypeId, String name,
			boolean isActive, String createdBy, Date createdDate,
			String modifiedBy, Date lastModifiedDate, String docTypeName) {
		super();
		this.id = id;
		this.docTypeId = docTypeId;
		this.name = name;
		this.isActive = isActive;
		this.createdBy = createdBy;
		this.createdDate = (Date)createdDate.clone();
		this.modifiedBy = modifiedBy;
		this.lastModifiedDate = (Date)lastModifiedDate.clone();
		this.docTypeName = docTypeName;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDocTypeId() {
		return docTypeId;
	}
	public void setDocTypeId(String docTypeId) {
		this.docTypeId = docTypeId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
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
	public String getDocTypeName() {
		return docTypeName;
	}
	public void setDocTypeName(String docTypeName) {
		this.docTypeName = docTypeName;
	}
	
	
}
