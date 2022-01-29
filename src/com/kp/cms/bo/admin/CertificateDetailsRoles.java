package com.kp.cms.bo.admin;

import java.io.Serializable;
import java.util.Date;

public class CertificateDetailsRoles implements Serializable{
	
	private int id;
	private Roles certificateRolesId;
	private CertificateDetails certificateId;
	private String createdby;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private Boolean isActive;
	private String description;
	
	public CertificateDetailsRoles()
	{
		
	}
	public CertificateDetailsRoles(int id,Roles certificateRolesId,CertificateDetails certificateId,String createdby,Date createdDate,
			                  String modifiedBy,Date lastModifiedDate,Boolean isActive,String description){
		this.id=id;
		this.certificateRolesId=certificateRolesId;
		this.certificateId=certificateId;
		this.createdby=createdby;
		this.createdDate=createdDate;
		this.modifiedBy=modifiedBy;
		this.lastModifiedDate=lastModifiedDate;
		this.isActive=isActive;
		this.description=description;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Roles getCertificateRolesId() {
		return certificateRolesId;
	}
	public void setCertificateRolesId(Roles certificateRolesId) {
		this.certificateRolesId = certificateRolesId;
	}
	public CertificateDetails getCertificateId() {
		return certificateId;
	}
	public void setCertificateId(CertificateDetails certificateId) {
		this.certificateId = certificateId;
	}
	public String getCreatedby() {
		return createdby;
	}
	public void setCreatedby(String createdby) {
		this.createdby = createdby;
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
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}
