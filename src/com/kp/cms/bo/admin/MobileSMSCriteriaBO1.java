package com.kp.cms.bo.admin;

import java.util.Date;

public class MobileSMSCriteriaBO1 implements  java.io.Serializable{

	private int id;
	private ClassSchemewise classSchemewise;
	private String smsTime;
	private Boolean isSmsBlocked;
	private Date smsBlockStartDate;
	private Date smsBlockEndDate;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private Boolean isActive;
	private String year;
	public MobileSMSCriteriaBO1()
	{
		
	}
	public MobileSMSCriteriaBO1(int id,ClassSchemewise classSchemewise,String smsTime,Boolean isSmsBlocked
			,Date smsBlockStartDate,Date smsBlockEndDate,String createdBy,Date createdDate,
			String modifiedBy,Date lastModifiedDate,Boolean isActive,String year)
	{
		this.id=id;
		this.classSchemewise=classSchemewise;
		this.smsTime=smsTime;
		this.isSmsBlocked=isSmsBlocked;
		this.smsBlockStartDate=smsBlockStartDate;
		this.smsBlockEndDate=smsBlockEndDate;
		this.createdBy=createdBy;
		this.createdDate=createdDate;
		this.modifiedBy=modifiedBy;
		this.lastModifiedDate=lastModifiedDate;
		this.isActive=isActive;
		this.year=year;
	}
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public ClassSchemewise getClassSchemewise() {
		return classSchemewise;
	}
	public void setClassSchemewise(ClassSchemewise classSchemewise) {
		this.classSchemewise = classSchemewise;
	}
	public String getSmsTime() {
		return smsTime;
	}
	public void setSmsTime(String smsTime) {
		this.smsTime = smsTime;
	}
	public boolean getIsSmsBlocked() {
		return isSmsBlocked;
	}
	public void setIsSmsBlocked(boolean isSmsBlocked) {
		this.isSmsBlocked = isSmsBlocked;
	}
	public Date getSmsBlockStartDate() {
		return smsBlockStartDate;
	}
	public void setSmsBlockStartDate(Date smsBlockStartDate) {
		this.smsBlockStartDate = smsBlockStartDate;
	}
	public Date getSmsBlockEndDate() {
		return smsBlockEndDate;
	}
	public void setSmsBlockEndDate(Date smsBlockEndDate) {
		this.smsBlockEndDate = smsBlockEndDate;
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
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	
	
	
	
	
	
}
