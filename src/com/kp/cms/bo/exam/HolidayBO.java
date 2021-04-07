package com.kp.cms.bo.exam;

import java.util.Date;
import java.util.Set;



public class HolidayBO implements java.io.Serializable{
	
	private int holidayId;
	private String holidayName;
	private Date holidayDate;
	private Date createdDate;
	private Date lastModifiedDate;
	private boolean isActive;
	private String createdBy;
	private String modifiedBy;
	
	public HolidayBO(){
		
	}
	public HolidayBO(int holidayId, String createdBy,Date holidayDate,
			String modifiedBy, String holidayName, Date createdDate,
			Date lastModifiedDate,boolean isActive) {
		this.holidayId = holidayId;
		this.holidayDate= holidayDate;
		this.createdBy = createdBy;
		this.modifiedBy = modifiedBy;
		this.holidayName = holidayName;
		this.isActive = isActive;
		
		this.createdDate = createdDate;
		this.lastModifiedDate = lastModifiedDate;
		
	}
	
	
	public int getHolidayId() {
		return holidayId;
	}
	public void setHolidayId(int holidayId) {
		this.holidayId = holidayId;
	}
	public String getHolidayName() {
		return holidayName;
	}
	public void setHolidayName(String holidayName) {
		this.holidayName = holidayName;
	}
	public Date getHolidayDate() {
		return holidayDate;
	}
	public void setHolidayDate(Date holidayDate) {
		this.holidayDate = holidayDate;
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
	public boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
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

}
