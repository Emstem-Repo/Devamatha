package com.kp.cms.bo.hostel;

import java.io.Serializable;
import java.util.Date;

public class FineCategoryBo implements Serializable{

	private int id;
	private String createdBy;;
	private String modifiedBy;
	private String name;
	private String amount;
	private Date createdDate;
	private Date lastModifiedDate;
	private Boolean isActive;
	
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	
	public FineCategoryBo(){
		
	}
	public FineCategoryBo(int id,String name,String amount,Boolean isActive,String createdBy,Date createdDate,String modifiedBy,Date lastModifiedDate){
		this.id=id;
		this.name=name;
		this.amount=amount;
		this.createdBy=createdBy;
		this.createdDate=createdDate;
		this.modifiedBy=modifiedBy;
		this.lastModifiedDate=lastModifiedDate;
		this.isActive=isActive;
	}

}
