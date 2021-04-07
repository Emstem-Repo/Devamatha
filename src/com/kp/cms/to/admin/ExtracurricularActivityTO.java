package com.kp.cms.to.admin;

import java.io.Serializable;
import java.util.Date;


public class ExtracurricularActivityTO implements Serializable{
	private int id;
	private String organisationId;
	private String name;
	private String createdBy;
	private String createdDate;
	private String modifiedBy;
	private String lastModifiedDate;
	private boolean isActive;
	private OrganizationTO organizationTO;
	
	private Date extraCurricularCreatedDate;
	private Date extraCurricularLastModifiedDate;
	private String tempActive;
	
	public ExtracurricularActivityTO(){
		
	}
	public ExtracurricularActivityTO(int id, String organisationId,
			String name, String createdBy, String createdDate,
			String modifiedBy, String lastModifiedDate, boolean isActive,
			OrganizationTO organizationTO, Date extraCurricularCreatedDate,
			Date extraCurricularLastModifiedDate, String tempActive) {
		super();
		this.id = id;
		this.organisationId = organisationId;
		this.name = name;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.lastModifiedDate = lastModifiedDate;
		this.isActive = isActive;
		this.organizationTO = organizationTO;
		this.extraCurricularCreatedDate = (Date)extraCurricularCreatedDate.clone();
		this.extraCurricularLastModifiedDate = (Date)extraCurricularLastModifiedDate.clone();
		this.tempActive = tempActive;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getOrganisationId() {
		return organisationId;
	}
	public void setOrganisationId(String organisationId) {
		this.organisationId = organisationId;
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
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public String getLastModifiedDate() {
		return lastModifiedDate;
	}
	public void setLastModifiedDate(String lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	public OrganizationTO getOrganizationTO() {
		return organizationTO;
	}
	public void setOrganizationTO(OrganizationTO organizationTO) {
		this.organizationTO = organizationTO;
	}
	public Date getExtraCurricularCreatedDate() {
		return (Date)extraCurricularCreatedDate.clone();
	}
	public void setExtraCurricularCreatedDate(Date extraCurricularCreatedDate) {
		this.extraCurricularCreatedDate = (Date)extraCurricularCreatedDate.clone();
	}
	public Date getExtraCurricularLastModifiedDate() {
		return (Date)extraCurricularLastModifiedDate.clone();
	}
	public void setExtraCurricularLastModifiedDate(
			Date extraCurricularLastModifiedDate) {
		this.extraCurricularLastModifiedDate = (Date)extraCurricularLastModifiedDate.clone();
	}
	public String getTempActive() {
		return tempActive;
	}
	public void setTempActive(String tempActive) {
		this.tempActive = tempActive;
	}
	
	
}