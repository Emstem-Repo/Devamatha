package com.kp.cms.to.inventory;

import java.util.List;

import com.kp.cms.bo.admin.InvRequestItem;

public class InvRequestTO {

	private String reqId;
	private String requestedBy;
	private String raisedOn;
	private String status;
	private String comments;
	private String department;
	
	private String description;
	private String requistionNo;
	private String deliveryDate;
	private String currentStatus;
	
	private List<ItemTO> itemTOList;
	
	public String getReqId() {
		return reqId;
	}
	public String getRequestedBy() {
		return requestedBy;
	}
	public String getRaisedOn() {
		return raisedOn;
	}
	public String getStatus() {
		return status;
	}
	public String getComments() {
		return comments;
	}
	public void setReqId(String reqId) {
		this.reqId = reqId;
	}
	public void setRequestedBy(String requestedBy) {
		this.requestedBy = requestedBy;
	}
	public void setRaisedOn(String raisedOn) {
		this.raisedOn = raisedOn;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getDescription() {
		return description;
	}
	public String getRequistionNo() {
		return requistionNo;
	}
	public String getDeliveryDate() {
		return deliveryDate;
	}
	public List<ItemTO> getItemTOList() {
		return itemTOList;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setRequistionNo(String requistionNo) {
		this.requistionNo = requistionNo;
	}
	public void setDeliveryDate(String deliveryDate) {
		this.deliveryDate = deliveryDate;
	}
	public void setItemTOList(List<ItemTO> itemTOList) {
		this.itemTOList = itemTOList;
	}
	public String getCurrentStatus() {
		return currentStatus;
	}
	public void setCurrentStatus(String currentStatus) {
		this.currentStatus = currentStatus;
	}
	
	
	
}
