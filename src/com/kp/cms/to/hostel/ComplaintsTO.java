package com.kp.cms.to.hostel;

import java.io.Serializable;
import java.util.Date;

public class ComplaintsTO implements Serializable {
	
	private int  id;
	private String  type;
	private String  createdBy;
	private Date  createdDate;
	private String  modifiedBy;
	private Date  lastModifiedDate;
	private Boolean  isActive;
	private String approvedDate;
	private String approvedBy;
	private String complaintTypeName;
	private String messageTypeName;
	private String statusId;
	private String complaintId;
	private String statusName;
	private String commonId;
	private String title;
	private String desc;
	private String actionTaken;
	private String hostelName;
	private String requisitionNo;
	private String logDate;
	

	/**
	 * @return the logDate
	 */
	public String getLogDate() {
		return logDate;
	}
	/**
	 * @param logDate the logDate to set
	 */
	public void setLogDate(String logDate) {
		this.logDate = logDate;
	}
	/**
	 * @return the hostelName
	 */
	/**
	 * @return the requisitionNo
	 */
	public String getRequisitionNo() {
		return requisitionNo;
	}
	/**
	 * @return the hostelName
	 */
	public String getHostelName() {
		return hostelName;
	}
	/**
	 * @param hostelName the hostelName to set
	 */
	public void setHostelName(String hostelName) {
		this.hostelName = hostelName;
	}
	/**
	 * @param requisitionNo the requisitionNo to set
	 */
	public void setRequisitionNo(String requisitionNo) {
		this.requisitionNo = requisitionNo;
	}
	public String getActionTaken() {
		return actionTaken;
	}
	public void setActionTaken(String actionTaken) {
		this.actionTaken = actionTaken;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getComplaintTypeName() {
		return complaintTypeName;
	}
	public void setComplaintTypeName(String complaintTypeName) {
		this.complaintTypeName = complaintTypeName;
	}
	public String getMessageTypeName() {
		return messageTypeName;
	}
	public void setMessageTypeName(String messageTypeName) {
		this.messageTypeName = messageTypeName;
	}
	public String getStatusId() {
		return statusId;
	}
	public void setStatusId(String statusId) {
		this.statusId = statusId;
	}
	public String getComplaintId() {
		return complaintId;
	}
	public void setComplaintId(String complaintId) {
		this.complaintId = complaintId;
	}
	public String getStatusName() {
		return statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	public String getCommonId() {
		return commonId;
	}
	public void setCommonId(String commonId) {
		this.commonId = commonId;
	}
	public int getId() {
		
		return id;
	}
	public void setId(int id) {
		
		this.id = id;
	}
	public String getType() {
		
		return type;
	}
	public void setType(String type) {
		
		this.type = type;
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
	public String getApprovedDate() {
		return approvedDate;
	}
	public void setApprovedDate(String approvedDate) {
		this.approvedDate = approvedDate;
	}
	public String getApprovedBy() {
		return approvedBy;
	}
	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}
	
	
}