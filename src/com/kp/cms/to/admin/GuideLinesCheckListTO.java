package com.kp.cms.to.admin;

public class GuideLinesCheckListTO {
	private OrganizationTO organizationTO;
	private String description;
	private int id;
	private boolean checked;
	private boolean tempChecked;
	public OrganizationTO getOrganizationTO() {
		return organizationTO;
	}
	public String getDescription() {
		return description;
	}
	public void setOrganizationTO(OrganizationTO organizationTO) {
		this.organizationTO = organizationTO;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	public boolean isTempChecked() {
		return tempChecked;
	}
	public void setTempChecked(boolean tempChecked) {
		this.tempChecked = tempChecked;
	}
	

}
