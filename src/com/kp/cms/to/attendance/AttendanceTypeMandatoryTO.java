package com.kp.cms.to.attendance;

import java.io.Serializable;
/**
 * 
 * @author kshirod.k
 * A TO class for AttandanceTypeMandatory
 *
 */
public class AttendanceTypeMandatoryTO implements Serializable{
	
	private int id;
	private String name;
	private String createdBy;
	private String createdDate;
	private String isMandatory;
	private boolean isActive;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the is_Mandatory
	 */
	public String getIsMandatory() {
		return isMandatory;
	}
	/**
	 * @param is_Mandatory the is_Mandatory to set
	 */
	public void setIsMandatory(String isMandatory) {
		this.isMandatory = isMandatory;
	}
	/**
	 * @return the isActive
	 */
	public boolean isActive() {
		return isActive;
	}
	/**
	 * @param isActive the isActive to set
	 */
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
}
