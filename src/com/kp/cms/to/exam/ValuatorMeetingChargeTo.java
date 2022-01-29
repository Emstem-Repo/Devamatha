package com.kp.cms.to.exam;

import java.io.Serializable;

public class ValuatorMeetingChargeTo implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private int id;
	private String boardMeetingCharges;
	private String conveyanceCharge;
		
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getBoardMeetingCharges() {
		return boardMeetingCharges;
	}
	public void setBoardMeetingCharges(String boardMeetingCharges) {
		this.boardMeetingCharges = boardMeetingCharges;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getConveyanceCharge() {
		return conveyanceCharge;
	}
	public void setConveyanceCharge(String conveyanceCharge) {
		this.conveyanceCharge = conveyanceCharge;
	}
	
	
}
