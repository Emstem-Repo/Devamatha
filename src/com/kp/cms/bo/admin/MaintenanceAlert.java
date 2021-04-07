package com.kp.cms.bo.admin;

import java.io.Serializable;
import java.util.Date;

public class MaintenanceAlert implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	    private int id;
	    private String maintenanceMessage;
	    private Date maintenanceDate;
	    private String maintenanceFromTime;
	    private String maintenanceToTime;
	    private String createdBy;
	    private Date createdDate;
	    private String modifiedBy;
	    private Date lastModifiedDate;
	    private Boolean isActive;
		
	    public MaintenanceAlert() {
		}
	    
	    
	    public MaintenanceAlert(int id, String maintenanceMessage,
				Date maintenanceDate, String maintenanceFromTime,
				String maintenanceToTime, String createdBy, Date createdDate,
				String modifiedBy, Date lastModifiedDate, Boolean isActive) {
			super();
			this.id = id;
			this.maintenanceMessage = maintenanceMessage;
			this.maintenanceDate = maintenanceDate;
			this.maintenanceFromTime = maintenanceFromTime;
			this.maintenanceToTime = maintenanceToTime;
			this.createdBy = createdBy;
			this.createdDate = createdDate;
			this.modifiedBy = modifiedBy;
			this.lastModifiedDate = lastModifiedDate;
			this.isActive = isActive;
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
		public Date getMaintenanceDate() {
			return maintenanceDate;
		}
		public void setMaintenanceDate(Date maintenanceDate) {
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
		public String getCreatedBy() {
			return createdBy;
		}
		public void setCreatedBy(String createdBy) {
			this.createdBy = createdBy;
		}
		public Date getCreatedDate() {
			return createdDate;
		}
		public void setCreatedDate(Date createdDate) {
			this.createdDate = createdDate;
		}
		public String getModifiedBy() {
			return modifiedBy;
		}
		public void setModifiedBy(String modifiedBy) {
			this.modifiedBy = modifiedBy;
		}
		public Date getLastModifiedDate() {
			return lastModifiedDate;
		}
		public void setLastModifiedDate(Date lastModifiedDate) {
			this.lastModifiedDate = lastModifiedDate;
		}
		public Boolean getIsActive() {
			return isActive;
		}
		public void setIsActive(Boolean isActive) {
			this.isActive = isActive;
		}
	    
	    
}
