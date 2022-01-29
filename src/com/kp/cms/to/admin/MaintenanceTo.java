package com.kp.cms.to.admin;

import java.io.Serializable;

public class MaintenanceTo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String maintenanceDate;
	private String maintenanceMessage;
	private String maintenanceFromTime;
	private String maintenanceToTime;
	private int id;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMaintenanceDate() {
		return maintenanceDate;
	}
	public void setMaintenanceDate(String maintenanceDate) {
		this.maintenanceDate = maintenanceDate;
	}
	public String getMaintenanceMessage() {
		return maintenanceMessage;
	}
	public void setMaintenanceMessage(String maintenanceMessage) {
		this.maintenanceMessage = maintenanceMessage;
	}
	public String getMaintenanceFromTime() {
		return maintenanceFromTime;
	}
	public void setMaintenanceFromTime(String maintenanceFromTime) {
		this.maintenanceFromTime = maintenanceFromTime;
	}
	public String getMaintenanceToTime() {
		return maintenanceToTime;
	}
	public void setMaintenanceToTime(String maintenanceToTime) {
		this.maintenanceToTime = maintenanceToTime;
	}
	
}
