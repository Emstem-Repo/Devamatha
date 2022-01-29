package com.kp.cms.bo.admin;

import java.io.Serializable;
import java.util.Date;

public class ServicesDownTracker implements Serializable{
	
	private int id;
	private Services serviceId;
	private Date date;
	private String downFrom;
    private String downTill;
    private String remarks;
    private String createdBy;
    private Date createdDate;
    private String modifiedBy;
    private Date lastModifiedDate;
    private Boolean isActive;
    
    
    public ServicesDownTracker() {
	}
    
    
    public ServicesDownTracker(int id, Services serviceId, Date date, 
    		String downFrom, String downTill, String remarks, 
			String createdBy, Date createdDate,
			String modifiedBy, Date lastModifiedDate, Boolean isActive) {
		super();
		this.id = id;
		this.date = date;
		this.downFrom = downFrom;
		this.downTill = downTill;
		this.remarks = remarks;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.lastModifiedDate = lastModifiedDate;
		this.isActive = isActive;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public Services getServiceId() {
		return serviceId;
	}


	public void setServiceId(Services serviceId) {
		this.serviceId = serviceId;
	}


	public Date getDate() {
		return date;
	}


	public void setDate(Date date) {
		this.date = date;
	}


	public String getDownFrom() {
		return downFrom;
	}


	public void setDownFrom(String downFrom) {
		this.downFrom = downFrom;
	}


	public String getDownTill() {
		return downTill;
	}


	public void setDownTill(String downTill) {
		this.downTill = downTill;
	}


	public String getRemarks() {
		return remarks;
	}


	public void setRemarks(String remarks) {
		this.remarks = remarks;
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
