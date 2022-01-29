package com.kp.cms.to.hostel;

import java.io.Serializable;

public class HostelAdminMessageTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String admnApplId;
	private String hostelId;
	private String roomId;
	private String hostelName;
	private String roomNo;
	private String messageType;
	private String status;
	private String sentDate;
	private String fromName;
	private String messageTypeId;
	private String statusId;
	private String leaveTypeId;
	private String leaveId;
	private String commonId;
	
	
	
	public String getCommonId() {
		return commonId;
	}
	public void setCommonId(String commonId) {
		this.commonId = commonId;
	}
	public String getLeaveId() {
		return leaveId;
	}
	public void setLeaveId(String leaveId) {
		this.leaveId = leaveId;
	}
	public String getLeaveTypeId() {
		return leaveTypeId;
	}
	public void setLeaveTypeId(String leaveTypeId) {
		this.leaveTypeId = leaveTypeId;
	}
	
	
	public String getStatusId() {
		return statusId;
	}
	public void setStatusId(String statusId) {
		this.statusId = statusId;
	}
	public String getRoomNo() {
		return roomNo;
	}
	public String getMessageTypeId() {
		return messageTypeId;
	}
	public void setMessageTypeId(String messageTypeId) {
		this.messageTypeId = messageTypeId;
	}
	public String getAdmnApplId() {
		return admnApplId;
	}
	public void setAdmnApplId(String admnApplId) {
		this.admnApplId = admnApplId;
	}
	public String getHostelId() {
		return hostelId;
	}
	public void setHostelId(String hostelId) {
		this.hostelId = hostelId;
	}
	public String getRoomId() {
		return roomId;
	}
	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}
	public void setRoomNo(String roomNo) {
		this.roomNo = roomNo;
	}
	public String getMessageType() {
		return messageType;
	}
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getSentDate() {
		return sentDate;
	}
	public void setSentDate(String sentDate) {
		this.sentDate = sentDate;
	}
	public String getFromName() {
		return fromName;
	}
	public void setFromName(String fromName) {
		this.fromName = fromName;
	}
	public String getHostelName() {
		return hostelName;
	}
	public void setHostelName(String hostelName) {
		this.hostelName = hostelName;
	}
	
	
	
}
