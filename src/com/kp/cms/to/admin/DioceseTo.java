package com.kp.cms.to.admin;

import java.util.Date;




public class DioceseTo {

	private String dioceseName;
	private int dioceseId;
	private String createdBy;;
	private String modifiedBy;
	private Date createdDate;
	private Date lastModifiedDate;
	private SubReligionTo subReligionTo;
	public String getDioceseName() {
		return dioceseName;
	}
	public void setDioceseName(String dioceseName) {
		this.dioceseName = dioceseName;
	}
	public int getDioceseId() {
		return dioceseId;
	}
	public void setDioceseId(int dioceseId) {
		this.dioceseId = dioceseId;
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
	public SubReligionTo getSubReligionTo() {
		return subReligionTo;
	}
	public void setSubReligionTo(SubReligionTo subReligionTo) {
		this.subReligionTo = subReligionTo;
	}
	
	
	
}
