package com.kp.cms.bo.admin;

import java.io.Serializable;
import java.util.Date;

public class ParishBO implements Serializable{
	private int id;
	private DioceseBO dioceseId;
	private String name;
	private String createdBy;;
	private String modifiedBy;
	private Date createdDate;
	private Date lastModifiedDate;
	private Boolean isActive;
	public ParishBO()
	{
		
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public DioceseBO getDioceseId() {
		return dioceseId;
	}
	public void setDioceseId(DioceseBO dioceseId) {
		this.dioceseId = dioceseId;
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
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
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

}
