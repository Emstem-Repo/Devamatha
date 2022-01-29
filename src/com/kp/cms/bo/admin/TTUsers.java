package com.kp.cms.bo.admin;

import java.io.Serializable;
import java.util.Date;

public class TTUsers  implements Serializable{

	private int id;
	private TTSubjectBatch ttSubjectBatch;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private Boolean isActive;
	private Users users;
	
	
	public TTUsers() {
		super();
	}
	
	public TTUsers(int id, TTSubjectBatch ttSubjectBatch, String createdBy,
			Date createdDate, String modifiedBy, Date lastModifiedDate,
			Boolean isActive,Users users) {
		super();
		this.id = id;
		this.ttSubjectBatch = ttSubjectBatch;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.lastModifiedDate = lastModifiedDate;
		this.isActive = isActive;
		this.users=users;
	}



	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public TTSubjectBatch getTtSubjectBatch() {
		return ttSubjectBatch;
	}
	public void setTtSubjectBatch(TTSubjectBatch ttSubjectBatch) {
		this.ttSubjectBatch = ttSubjectBatch;
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
	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}
	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
	public Boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Users getUsers() {
		return users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}
}