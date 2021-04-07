package com.kp.cms.to.fee;

import java.io.Serializable;

/**
 * 
 * Transfer Object class for Fee Heading related work.
 */
public class FeeHeadingTO implements Serializable{

	private static final long serialVersionUID = 1L;
	private int id;
	private String name;
	private FeeGroupTO feeGroupTO;
	private String createdBy;
	private String modifiedBy;
	private String createdDate;
	private String lastModifiedDate;
	private String isActive;

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	public FeeGroupTO getFeeGroupTO() {
		return feeGroupTO;
	}
	public void setFeeGroupTO(FeeGroupTO feeGroupTO) {
		this.feeGroupTO = feeGroupTO;
	}
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public String getLastModifiedDate() {
		return lastModifiedDate;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public void setLastModifiedDate(String lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	
	
}
