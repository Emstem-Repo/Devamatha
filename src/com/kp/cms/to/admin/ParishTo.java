package com.kp.cms.to.admin;

import java.util.Date;

public class ParishTo {
	private String parishName;
	private int parishId;
	private String createdBy;;
	private String modifiedBy;
	private Date createdDate;
	private Date lastModifiedDate;
	private DioceseTo dioceseTo;
	public String getParishName() {
		return parishName;
	}
	public void setParishName(String parishName) {
		this.parishName = parishName;
	}
	public int getParishId() {
		return parishId;
	}
	public void setParishId(int parishId) {
		this.parishId = parishId;
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
	public DioceseTo getDioceseTo() {
		return dioceseTo;
	}
	public void setDioceseTo(DioceseTo dioceseTo) {
		this.dioceseTo = dioceseTo;
	}
	

}
