package com.kp.cms.forms.admin;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.MaintenanceTo;

public class MaintenanceAlertForm extends BaseActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String maintenanceMessage;
	private String maintenanceDate;
	private String maintenanceFromTime;
	private String maintenanceToTime;
	private int id;
	private MaintenanceTo maintenanceTo;
	private String origMaintenanceMessage;
	private String origMaintenanceDate;
	private String origMaintenanceFromTime;
	private String origMaintenanceToTime;
	
	
	public String getOrigMaintenanceMessage() {
		return origMaintenanceMessage;
	}
	public void setOrigMaintenanceMessage(String origMaintenanceMessage) {
		this.origMaintenanceMessage = origMaintenanceMessage;
	}
	public String getOrigMaintenanceDate() {
		return origMaintenanceDate;
	}
	public void setOrigMaintenanceDate(String origMaintenanceDate) {
		this.origMaintenanceDate = origMaintenanceDate;
	}
	public String getOrigMaintenanceFromTime() {
		return origMaintenanceFromTime;
	}
	public void setOrigMaintenanceFromTime(String origMaintenanceFromTime) {
		this.origMaintenanceFromTime = origMaintenanceFromTime;
	}
	public String getOrigMaintenanceToTime() {
		return origMaintenanceToTime;
	}
	public void setOrigMaintenanceToTime(String origMaintenanceToTime) {
		this.origMaintenanceToTime = origMaintenanceToTime;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public MaintenanceTo getMaintenanceTo() {
		return maintenanceTo;
	}
	public void setMaintenanceTo(MaintenanceTo maintenanceTo) {
		this.maintenanceTo = maintenanceTo;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMaintenanceMessage() {
		return maintenanceMessage;
	}
	public void setMaintenanceMessage(String maintenanceMessage) {
		this.maintenanceMessage = maintenanceMessage;
	}
	public String getMaintenanceDate() {
		return maintenanceDate;
	}
	public void setMaintenanceDate(String maintenanceDate) {
		this.maintenanceDate = maintenanceDate;
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
	
	public void reset(){
		this.maintenanceDate=null;
		this.maintenanceFromTime=null;
		this.maintenanceMessage=null;
		this.maintenanceToTime=null;
		this.id=0;
		this.maintenanceTo=null;
		this.origMaintenanceDate=null;
		this.origMaintenanceFromTime=null;
		this.origMaintenanceMessage=null;
		this.origMaintenanceToTime=null;
	}
}
