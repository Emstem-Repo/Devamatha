package com.kp.cms.bo.phd;

import java.io.Serializable;
import java.util.Date;

public class PhdQualificationLevelBo implements Serializable{
	private int id;
	private String name;
	private Integer displayOrder;
	private Boolean fixedDisplay;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private Boolean isActive;

	/**Default QualificationLevelBO Constructor
	 * 
	 */
	public PhdQualificationLevelBo() {
		
	}
	/**One argument QualificationLevelBO  Constructor
	 * 
	 */
	public PhdQualificationLevelBo(int id) {
		this.id=id;
	}



	/**
	 * @param id
	 * @param name
	 * @param createdBy
	 * @param createdDate
	 * @param modifiedBy
	 * @param lastModifiedDate
	 * @param isActive
	 */
	public PhdQualificationLevelBo(int id, String name,Integer displayOrder,Boolean fixedDisplay, String createdBy,
			Date createdDate, String modifiedBy, Date lastModifiedDate,
			Boolean isActive) {
		super();
		this.id = id;
		this.name = name;
		this.setDisplayOrder(displayOrder);
		this.fixedDisplay=fixedDisplay;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.lastModifiedDate = lastModifiedDate;
		this.isActive=isActive;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}


	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
	}
	public Integer getDisplayOrder() {
		return displayOrder;
	}
	public Boolean isFixedDisplay() {
		return fixedDisplay;
	}
	public void setFixedDisplay(Boolean fixedDisplay) {
		this.fixedDisplay = fixedDisplay;
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
	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	public Boolean getIsActive() {
		return isActive;
	}


}
