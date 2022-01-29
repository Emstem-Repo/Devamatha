package com.kp.cms.to.admission;

import java.io.Serializable;

public class ReceivedThroughTo implements Serializable{
	private int id;
    private String receivedThrough;
    private String slipRequired;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getReceivedThrough() {
		return receivedThrough;
	}
	public void setReceivedThrough(String receivedThrough) {
		this.receivedThrough = receivedThrough;
	}
	public String getSlipRequired() {
		return slipRequired;
	}
	public void setSlipRequired(String slipRequired) {
		this.slipRequired = slipRequired;
	}
}
