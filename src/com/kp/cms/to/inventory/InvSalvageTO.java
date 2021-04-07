package com.kp.cms.to.inventory;

import java.io.Serializable;
import java.util.Date;

public class InvSalvageTO implements Serializable {
	
	private int id;
	private String remarks;
	private Date date;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private Boolean isActive;
	private ItemTO itemTO;
	private String quatity;
	private InvLocationTO invLocationTO;
	private String salvageDate;
	private String issuedTo;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
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
	public ItemTO getItemTO() {
		return itemTO;
	}
	public void setItemTO(ItemTO itemTO) {
		this.itemTO = itemTO;
	}
	public String getQuatity() {
		return quatity;
	}
	public void setQuatity(String quatity) {
		this.quatity = quatity;
	}
	public InvLocationTO getInvLocationTO() {
		return invLocationTO;
	}
	public void setInvLocationTO(InvLocationTO invLocationTO) {
		this.invLocationTO = invLocationTO;
	}
	public String getSalvageDate() {
		return salvageDate;
	}
	public void setSalvageDate(String salvageDate) {
		this.salvageDate = salvageDate;
	}
	public String getIssuedTo() {
		return issuedTo;
	}
	public void setIssuedTo(String issuedTo) {
		this.issuedTo = issuedTo;
	}
	
}