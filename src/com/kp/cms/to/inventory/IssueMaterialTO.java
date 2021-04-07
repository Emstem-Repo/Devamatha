package com.kp.cms.to.inventory;

import java.io.Serializable;

public class IssueMaterialTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String raisedBy;
	private String raisedOn;
	private String status;
	private String invLocationId;
	private String invRequestId;
	private String inventoryName;
	
	public String getRaisedBy() {
		return raisedBy;
	}
	public String getRaisedOn() {
		return raisedOn;
	}
	public String getStatus() {
		return status;
	}
	public void setRaisedBy(String raisedBy) {
		this.raisedBy = raisedBy;
	}
	public void setRaisedOn(String raisedOn) {
		this.raisedOn = raisedOn;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getInvLocationId() {
		return invLocationId;
	}
	public void setInvLocationId(String invLocationId) {
		this.invLocationId = invLocationId;
	}
	public String getInvRequestId() {
		return invRequestId;
	}
	public void setInvRequestId(String invRequestId) {
		this.invRequestId = invRequestId;
	}
	public String getInventoryName() {
		return inventoryName;
	}
	public void setInventoryName(String inventoryName) {
		this.inventoryName = inventoryName;
	}	
}
