package com.kp.cms.bo.hostel;

import java.io.Serializable;
import java.util.Date;

import com.kp.cms.bo.admin.HlAdmissionBo;

public class FineEntryBo implements Serializable{
	private int id;
	private String amount;
	private HlAdmissionBo hlAdmissionId;
	private FineCategoryBo fineCategoryId;
	private String remarks;
	private Boolean paid;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private Date date;
	private Boolean isActive;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public HlAdmissionBo getHlAdmissionId() {
		return hlAdmissionId;
	}
	public void setHlAdmissionId(HlAdmissionBo hlAdmissionId) {
		this.hlAdmissionId = hlAdmissionId;
	}
	public FineCategoryBo getFineCategoryId() {
		return fineCategoryId;
	}
	public void setFineCategoryId(FineCategoryBo fineCategoryId) {
		this.fineCategoryId = fineCategoryId;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	public Boolean getPaid() {
		return paid;
	}
	public void setPaid(Boolean paid) {
		this.paid = paid;
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
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	public FineEntryBo(){
		
	}
	public FineEntryBo(int id,String amount,HlAdmissionBo hlAdmissionId,FineCategoryBo fineCategoryId,String remarks,
	 String paid,String createdBy,Date createdDate,String modifiedBy,Date lastModifiedDate,Date date,Boolean isActive){
		this.id=id;
		this.amount=amount;
		this.hlAdmissionId=hlAdmissionId;
		this.fineCategoryId=fineCategoryId;
		this.remarks=remarks;
		this.date=date;
		this.paid=false;
		this.createdBy=createdBy;
		this.createdDate=createdDate;
		this.modifiedBy=modifiedBy;
		this.lastModifiedDate=lastModifiedDate;
		this.isActive=isActive;
	}
	
}
