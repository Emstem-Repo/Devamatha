package com.kp.cms.to.smartcard;

import java.io.Serializable;
import java.util.Date;

public class ScLostCorrectionTo implements Serializable {
	
	private int id;
	private String cardType;
	private String status;
	private String appliedOn;
	private String oldSmartCardNo;
	private String remarks;
	private String issuedDate;
	private String cancelledDate;
	
	public String getOldSmartCardNo() {
		return oldSmartCardNo;
	}
	public void setOldSmartCardNo(String oldSmartCardNo) {
		this.oldSmartCardNo = oldSmartCardNo;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCardType() {
		return cardType;
	}
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getAppliedOn() {
		return appliedOn;
	}
	public void setAppliedOn(String appliedOn) {
		this.appliedOn = appliedOn;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getIssuedDate() {
		return issuedDate;
	}
	public void setIssuedDate(String issuedDate) {
		this.issuedDate = issuedDate;
	}
	public String getCancelledDate() {
		return cancelledDate;
	}
	public void setCancelledDate(String cancelledDate) {
		this.cancelledDate = cancelledDate;
	}
	
	
}
