package com.kp.cms.to.inventory;

import java.io.Serializable;

public class RequestVsIssueTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String requestedQuantity;
	private String issuedQuantity;
	private String nameWithCode;
	private String requestedBy;
	private String requestedDate;
	private String issuedDate;
	private String issuedTo;
	
	public String getRequestedQuantity() {
		return requestedQuantity;
	}
	public void setRequestedQuantity(String requestedQuantity) {
		this.requestedQuantity = requestedQuantity;
	}
	public String getIssuedQuantity() {
		return issuedQuantity;
	}
	public void setIssuedQuantity(String issuedQuantity) {
		this.issuedQuantity = issuedQuantity;
	}
	public String getNameWithCode() {
		return nameWithCode;
	}
	public void setNameWithCode(String nameWithCode) {
		this.nameWithCode = nameWithCode;
	}
	public String getRequestedBy() {
		return requestedBy;
	}
	public void setRequestedBy(String requestedBy) {
		this.requestedBy = requestedBy;
	}
	public String getIssuedTo() {
		return issuedTo;
	}
	public void setIssuedTo(String issuedTo) {
		this.issuedTo = issuedTo;
	}
	public String getRequestedDate() {
		return requestedDate;
	}
	public void setRequestedDate(String requestedDate) {
		this.requestedDate = requestedDate;
	}
	public String getIssuedDate() {
		return issuedDate;
	}
	public void setIssuedDate(String issuedDate) {
		this.issuedDate = issuedDate;
	}
}