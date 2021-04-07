package com.kp.cms.bo.admission;

import java.io.Serializable;
import java.util.Date;

public class ReceivedThrough implements Serializable{
    private int id;
    private String receivedThrough;
    private Boolean slipRequired;
    private String createdBy;
    private Date createdDate;
    private String modifiedBy;
    private Date lastModifiedDate;
    private Boolean isActive;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getReceivedThrough() {
		return receivedThrough;
	}
	public void setReceivedThrough(String receivedThrough) {
		this.receivedThrough = receivedThrough;
	}
	public Boolean getSlipRequired() {
		return slipRequired;
	}
	public void setSlipRequired(Boolean slipRequired) {
		this.slipRequired = slipRequired;
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
}
