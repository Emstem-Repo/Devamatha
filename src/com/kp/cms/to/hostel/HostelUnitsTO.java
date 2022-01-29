package com.kp.cms.to.hostel;

import java.io.Serializable;
import java.util.Date;

public class HostelUnitsTO {
	
	private int id;
	private String name;
	private HostelTO hostelTO;
	private HostelBlocksTO hostelBlocksTO;
	private String hostelName;
	private String blockName;
	private Integer noOfFloors;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private String isActive;
	private String primaryContactName;
	private String primaryContactDesignation;
	private String primaryContactPhone;
	private String primaryContactMobile;
	private String primaryContactEmail;
	private String secContactName;
	private String secContactDesignation;
	private String secContactPhone;
	private String secContactMobile;
	private String secContactEmail;
	private String onlineLeave;
	private String applyBeforeHours;
	private String applyBeforeMin;
	private String leaveBeforeNoOfDays;
	private String applyBeforeNextDayHours;
	private String applyBeforeNextDayMin;
	private String smsForParents;
	private String intervalMails;
	private String smsForPrimaryCon;
	private String smsForSecondCon;
	private String smsOnEvening;
	private String smsOnMorning;
	private int noOfAbsance;
	private String punchExepSundaySession;
	
	
	//fields for hostel leave view
	
	private String startDate;
	private String endDate;
	private int hlLeaveId;
	private String status;
	
	public String getOnlineLeave() {
		return onlineLeave;
	}
	public void setOnlineLeave(String onlineLeave) {
		this.onlineLeave = onlineLeave;
	}
	public String getApplyBeforeHours() {
		return applyBeforeHours;
	}
	public void setApplyBeforeHours(String applyBeforeHours) {
		this.applyBeforeHours = applyBeforeHours;
	}
	public String getApplyBeforeMin() {
		return applyBeforeMin;
	}
	public void setApplyBeforeMin(String applyBeforeMin) {
		this.applyBeforeMin = applyBeforeMin;
	}
	public String getLeaveBeforeNoOfDays() {
		return leaveBeforeNoOfDays;
	}
	public void setLeaveBeforeNoOfDays(String leaveBeforeNoOfDays) {
		this.leaveBeforeNoOfDays = leaveBeforeNoOfDays;
	}
	public String getApplyBeforeNextDayHours() {
		return applyBeforeNextDayHours;
	}
	public void setApplyBeforeNextDayHours(String applyBeforeNextDayHours) {
		this.applyBeforeNextDayHours = applyBeforeNextDayHours;
	}
	public String getApplyBeforeNextDayMin() {
		return applyBeforeNextDayMin;
	}
	public void setApplyBeforeNextDayMin(String applyBeforeNextDayMin) {
		this.applyBeforeNextDayMin = applyBeforeNextDayMin;
	}
	public String getSmsForParents() {
		return smsForParents;
	}
	public void setSmsForParents(String smsForParents) {
		this.smsForParents = smsForParents;
	}
	public String getIntervalMails() {
		return intervalMails;
	}
	public void setIntervalMails(String intervalMails) {
		this.intervalMails = intervalMails;
	}
	public String getSmsForPrimaryCon() {
		return smsForPrimaryCon;
	}
	public void setSmsForPrimaryCon(String smsForPrimaryCon) {
		this.smsForPrimaryCon = smsForPrimaryCon;
	}
	public String getSmsForSecondCon() {
		return smsForSecondCon;
	}
	public void setSmsForSecondCon(String smsForSecondCon) {
		this.smsForSecondCon = smsForSecondCon;
	}
	public String getSmsOnEvening() {
		return smsOnEvening;
	}
	public void setSmsOnEvening(String smsOnEvening) {
		this.smsOnEvening = smsOnEvening;
	}
	public String getSmsOnMorning() {
		return smsOnMorning;
	}
	public void setSmsOnMorning(String smsOnMorning) {
		this.smsOnMorning = smsOnMorning;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public HostelTO getHostelTO() {
		return hostelTO;
	}
	public void setHostelTO(HostelTO hostelTO) {
		this.hostelTO = hostelTO;
	}
	public HostelBlocksTO getHostelBlocksTO() {
		return hostelBlocksTO;
	}
	public void setHostelBlocksTO(HostelBlocksTO hostelBlocksTO) {
		this.hostelBlocksTO = hostelBlocksTO;
	}
	public String getHostelName() {
		return hostelName;
	}
	public void setHostelName(String hostelName) {
		this.hostelName = hostelName;
	}
	public String getBlockName() {
		return blockName;
	}
	public void setBlockName(String blockName) {
		this.blockName = blockName;
	}
	public Integer getNoOfFloors() {
		return noOfFloors;
	}
	public void setNoOfFloors(Integer noOfFloors) {
		this.noOfFloors = noOfFloors;
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
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
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
	public String getSecContactName() {
		return secContactName;
	}
	public void setSecContactName(String secContactName) {
		this.secContactName = secContactName;
	}
	public String getSecContactDesignation() {
		return secContactDesignation;
	}
	public void setSecContactDesignation(String secContactDesignation) {
		this.secContactDesignation = secContactDesignation;
	}
	public String getSecContactPhone() {
		return secContactPhone;
	}
	public void setSecContactPhone(String secContactPhone) {
		this.secContactPhone = secContactPhone;
	}
	public String getSecContactMobile() {
		return secContactMobile;
	}
	public void setSecContactMobile(String secContactMobile) {
		this.secContactMobile = secContactMobile;
	}
	public String getSecContactEmail() {
		return secContactEmail;
	}
	public void setSecContactEmail(String secContactEmail) {
		this.secContactEmail = secContactEmail;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public int getHlLeaveId() {
		return hlLeaveId;
	}
	public void setHlLeaveId(int hlLeaveId) {
		this.hlLeaveId = hlLeaveId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getNoOfAbsance() {
		return noOfAbsance;
	}
	public void setNoOfAbsance(int noOfAbsance) {
		this.noOfAbsance = noOfAbsance;
	}
	public String getPunchExepSundaySession() {
		return punchExepSundaySession;
	}
	public void setPunchExepSundaySession(String punchExepSundaySession) {
		this.punchExepSundaySession = punchExepSundaySession;
	}
	
	
}
