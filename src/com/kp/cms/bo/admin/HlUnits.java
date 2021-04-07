package com.kp.cms.bo.admin;

// Generated Dec 18, 2009 3:48:30 PM by Hibernate Tools 3.2.0.b9

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * HlRoom generated by hbm2java
 */
public class HlUnits implements java.io.Serializable {

	private int id;
	private HlBlocks blocks;
	private String name;
	private Integer floorNo;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private Boolean isActive;
	private String primaryContactName;
	private String primaryContactDesignation;
	private String primaryContactPhone;
	private String primaryContactMobile;
	private String primaryContactEmail;
	private String secondaryContactName;
	private String secondaryContactDesignation;
	private String secondaryContactPhone;
	private String secondaryContactMobile;
	private String secondaryContactEmail;
	private Boolean onlineLeave;
	private String applyBeforeTime;
	private String leaveBeforeNoOfDays;
	private String applyBeforeNextDayTime;
	private Boolean smsForParents;
	private String intervalMails;
	private Boolean smsForPrimaryCon;
	private Boolean smsForSecondCon;
	private Boolean smsOnEvening;
	private Boolean smsOnMorning;
	private Boolean punchExepSundaySession;
	public HlUnits() {
	}

	public HlUnits(int id, HlBlocks blocks, String name, Integer floorNo,
			String createdBy, Date createdDate, String modifiedBy,
			Date lastModifiedDate, Boolean isActive,
			String primaryContactName, String primaryContactDesignation,
			String primaryContactPhone, String primaryContactMobile, String primaryContactEmail,
			String secondaryContactName, String secondaryContactDesignation,
			String secondaryContactPhone, String secondaryContactMobile, String secondaryContactEmail,
			Boolean onlineLeave,String applyBeforeTime,String leaveBeforeNoOfDays,String applyBeforeNextDayTime,
			Boolean smsForParents,String intervalMails,Boolean smsForPrimaryCon,Boolean smsForSecondCon,
			Boolean smsOnEvening,Boolean smsOnMorning) {
		super();
		this.id = id;
		this.blocks = blocks;
		this.name = name;
		this.floorNo = floorNo;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.lastModifiedDate = lastModifiedDate;
		this.isActive = isActive;
		this.primaryContactDesignation = primaryContactDesignation;
		this.primaryContactEmail = primaryContactEmail;
		this.primaryContactMobile = primaryContactMobile;
		this.primaryContactName = primaryContactName;
		this.primaryContactPhone = primaryContactPhone;
		this.onlineLeave= onlineLeave;
		this.applyBeforeTime= applyBeforeNextDayTime;
		this.leaveBeforeNoOfDays= leaveBeforeNoOfDays;
		this.applyBeforeNextDayTime= applyBeforeNextDayTime;
		this.smsForParents= smsForParents;
		this.intervalMails= intervalMails;
		this.smsForPrimaryCon= smsForPrimaryCon;
		this.smsForSecondCon= smsForSecondCon;
		this.smsOnEvening= smsOnEvening;
		this.smsOnMorning= smsOnMorning;
	}

	public Boolean getOnlineLeave() {
		return onlineLeave;
	}

	public void setOnlineLeave(Boolean onlineLeave) {
		this.onlineLeave = onlineLeave;
	}

	public Boolean getSmsForParents() {
		return smsForParents;
	}

	public void setSmsForParents(Boolean smsForParents) {
		this.smsForParents = smsForParents;
	}

	public Boolean getSmsForPrimaryCon() {
		return smsForPrimaryCon;
	}

	public void setSmsForPrimaryCon(Boolean smsForPrimaryCon) {
		this.smsForPrimaryCon = smsForPrimaryCon;
	}

	public Boolean getSmsForSecondCon() {
		return smsForSecondCon;
	}

	public void setSmsForSecondCon(Boolean smsForSecondCon) {
		this.smsForSecondCon = smsForSecondCon;
	}

	public Boolean getSmsOnEvening() {
		return smsOnEvening;
	}

	public void setSmsOnEvening(Boolean smsOnEvening) {
		this.smsOnEvening = smsOnEvening;
	}

	public Boolean getSmsOnMorning() {
		return smsOnMorning;
	}

	public void setSmsOnMorning(Boolean smsOnMorning) {
		this.smsOnMorning = smsOnMorning;
	}

	public String getApplyBeforeTime() {
		return applyBeforeTime;
	}

	public void setApplyBeforeTime(String applyBeforeTime) {
		this.applyBeforeTime = applyBeforeTime;
	}

	public String getLeaveBeforeNoOfDays() {
		return leaveBeforeNoOfDays;
	}

	public void setLeaveBeforeNoOfDays(String leaveBeforeNoOfDays) {
		this.leaveBeforeNoOfDays = leaveBeforeNoOfDays;
	}

	public String getApplyBeforeNextDayTime() {
		return applyBeforeNextDayTime;
	}

	public void setApplyBeforeNextDayTime(String applyBeforeNextDayTime) {
		this.applyBeforeNextDayTime = applyBeforeNextDayTime;
	}

	
	public String getIntervalMails() {
		return intervalMails;
	}

	public void setIntervalMails(String intervalMails) {
		this.intervalMails = intervalMails;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public HlBlocks getBlocks() {
		return blocks;
	}

	public void setBlocks(HlBlocks blocks) {
		this.blocks = blocks;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public Integer getFloorNo() {
		return floorNo;
	}

	public void setFloorNo(Integer floorNo) {
		this.floorNo = floorNo;
	}

	public String getPrimaryContactName() {
		return primaryContactName;
	}

	public void setPrimaryContactName(String primaryContactName) {
		this.primaryContactName = primaryContactName;
	}

	public String getPrimaryContactDesignation() {
		return primaryContactDesignation;
	}

	public void setPrimaryContactDesignation(String primaryContactDesignation) {
		this.primaryContactDesignation = primaryContactDesignation;
	}

	public String getPrimaryContactPhone() {
		return primaryContactPhone;
	}

	public void setPrimaryContactPhone(String primaryContactPhone) {
		this.primaryContactPhone = primaryContactPhone;
	}

	public String getPrimaryContactMobile() {
		return primaryContactMobile;
	}

	public void setPrimaryContactMobile(String primaryContactMobile) {
		this.primaryContactMobile = primaryContactMobile;
	}

	public String getPrimaryContactEmail() {
		return primaryContactEmail;
	}

	public void setPrimaryContactEmail(String primaryContactEmail) {
		this.primaryContactEmail = primaryContactEmail;
	}

	public String getSecondaryContactName() {
		return secondaryContactName;
	}

	public void setSecondaryContactName(String secondaryContactName) {
		this.secondaryContactName = secondaryContactName;
	}

	public String getSecondaryContactDesignation() {
		return secondaryContactDesignation;
	}

	public void setSecondaryContactDesignation(String secondaryContactDesignation) {
		this.secondaryContactDesignation = secondaryContactDesignation;
	}

	public String getSecondaryContactPhone() {
		return secondaryContactPhone;
	}

	public void setSecondaryContactPhone(String secondaryContactPhone) {
		this.secondaryContactPhone = secondaryContactPhone;
	}

	public String getSecondaryContactMobile() {
		return secondaryContactMobile;
	}

	public void setSecondaryContactMobile(String secondaryContactMobile) {
		this.secondaryContactMobile = secondaryContactMobile;
	}

	public String getSecondaryContactEmail() {
		return secondaryContactEmail;
	}

	public void setSecondaryContactEmail(String secondaryContactEmail) {
		this.secondaryContactEmail = secondaryContactEmail;
	}

	public Boolean getPunchExepSundaySession() {
		return punchExepSundaySession;
	}

	public void setPunchExepSundaySession(Boolean punchExepSundaySession) {
		this.punchExepSundaySession = punchExepSundaySession;
	}
	
}