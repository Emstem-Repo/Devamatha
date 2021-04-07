package com.kp.cms.to.admin;

import java.util.Date;

public class ApplicationFeeTO 
{
	private int appfeeId;
	private ReligionSectionTO religionSectionTO;
	private String appfeeName;
	private String createdBy;;
	private String modifiedBy;
	private Date createdDate;
	private Date lastModifiedDate;
	private String isActive;
	private String year;
	private String amount;
	private ProgramTypeTO programType;
	
	
	public ProgramTypeTO getProgramType() {
		return programType;
	}
	public void setProgramType(ProgramTypeTO programType) {
		this.programType = programType;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public int getAppfeeId() {
		return appfeeId;
	}
	public void setAppfeeId(int appfeeId) {
		this.appfeeId = appfeeId;
	}
	
	public ReligionSectionTO getReligionSectionTO() {
		return religionSectionTO;
	}
	public void setReligionSectionTO(ReligionSectionTO religionSectionTO) {
		this.religionSectionTO = religionSectionTO;
	}
	public String getAppfeeName() {
		return appfeeName;
	}
	public void setAppfeeName(String appfeeName) {
		this.appfeeName = appfeeName;
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
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	
	

}
