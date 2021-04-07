package com.kp.cms.to.fee;

import java.util.List;



public class FeeChallanVerificationTO {
	
	private int id;
	private String admApplnId;
	private String feesPayId;
	private String isVerified;
	private String isActive;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAdmApplnId() {
		return admApplnId;
	}
	public void setAdmApplnId(String admApplnId) {
		this.admApplnId = admApplnId;
	}
	public String getFeesPayId() {
		return feesPayId;
	}
	public void setFeesPayId(String feesPayId) {
		this.feesPayId = feesPayId;
	}
	public String getIsVerified() {
		return isVerified;
	}
	public void setIsVerified(String isVerified) {
		this.isVerified = isVerified;
	}
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

}
