package com.kp.cms.to.admin;

public class CertificatePurposeTO {
	
	private int id;
	private String purposeName;
	private boolean tempChecked;
	private boolean checked;
	private int certificateIdPurpose;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPurposeName() {
		return purposeName;
	}
	public void setPurposeName(String purposeName) {
		this.purposeName = purposeName;
	}
	
	
	public int getCertificateIdPurpose() {
		return certificateIdPurpose;
	}
	public void setCertificateIdPurpose(int certificateIdPurpose) {
		this.certificateIdPurpose = certificateIdPurpose;
	}
	public boolean isTempChecked() {
		return tempChecked;
	}
	public void setTempChecked(boolean tempChecked) {
		this.tempChecked = tempChecked;
	}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	
	

}
