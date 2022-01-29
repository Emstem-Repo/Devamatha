package com.kp.cms.to.admin;

import java.util.Date;

/**
 * Manages Transaction related to Caste.
 * @author prashanth.mh
 */
public class CasteTO {
	private int casteId;
	private ReligionTO religionto;
	private String casteName;
	private String createdBy;;
	private String modifiedBy;
	private Date createdDate;
	private Date lastModifiedDate;
	private String isActive;
	private String cDate;
	private String lDate;
	private String isFeeExcemption;
	
	
	public String getCDate() {
		return cDate;
	}

	public void setCDate(String date) {
		cDate = date;
	}

	public String getLDate() {
		return lDate;
	}

	public void setLDate(String date) {
		lDate = date;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}


	public int getCasteId() {
		return casteId;
	}

	public void setCasteId(int casteId) {
		this.casteId = casteId;
	}

	public String getCasteName() {
		return casteName;
	}

	public void setCasteName(String casteName) {
		this.casteName = casteName;
	}

	public String getIsFeeExcemption() {
		return isFeeExcemption;
	}

	public void setIsFeeExcemption(String isFeeExcemption) {
		this.isFeeExcemption = isFeeExcemption;
	}

	public void setReligionto(ReligionTO religionto) {
		this.religionto = religionto;
	}

	public ReligionTO getReligionto() {
		return religionto;
	}
}
