package com.kp.cms.bo.phd;

/**
 * Dec 29, 2009
 * Created By 9Elements Team
 */
import java.io.Serializable;
import java.util.Date;

public class PhdSettingBO implements Serializable {
    
	private int id;
	private int reminderMailBefore;
	private int dueMailAfter;
	private int maxGuidesAssign;
	private Boolean isActive;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;

	public PhdSettingBO() {
	}

	public PhdSettingBO(int id,int reminderMailBefore,int dueMailAfter,int maxGuidesAssign,
			Boolean isActive,String createdBy,String modifiedBy,Date createdDate,Date lastModifiedDate) 
	{
		
		this.id = id;
		this.reminderMailBefore=reminderMailBefore;
		this.dueMailAfter=dueMailAfter;
		this.maxGuidesAssign=maxGuidesAssign;
		this.isActive = isActive;
		this.createdBy = createdBy;
		this.modifiedBy = modifiedBy;
		this.createdDate = createdDate;
		this.lastModifiedDate = lastModifiedDate;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getReminderMailBefore() {
		return reminderMailBefore;
	}

	public void setReminderMailBefore(int reminderMailBefore) {
		this.reminderMailBefore = reminderMailBefore;
	}

	public int getDueMailAfter() {
		return dueMailAfter;
	}

	public void setDueMailAfter(int dueMailAfter) {
		this.dueMailAfter = dueMailAfter;
	}

	public int getMaxGuidesAssign() {
		return maxGuidesAssign;
	}

	public void setMaxGuidesAssign(int maxGuidesAssign) {
		this.maxGuidesAssign = maxGuidesAssign;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
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

	

}
