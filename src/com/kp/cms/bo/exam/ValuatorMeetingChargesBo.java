package com.kp.cms.bo.exam;

import java.math.BigDecimal;
import java.util.Date;

public class ValuatorMeetingChargesBo implements java.io.Serializable{
 
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private BigDecimal conveyanceCharge;
	private String createdby;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private Boolean isActive;
    public ValuatorMeetingChargesBo() {
		
	}
	
	public ValuatorMeetingChargesBo(int id,BigDecimal conveyanceCharge,String createdby,
			                        Date createdDate,String modifiedBy,Date lastModifiedDate,Boolean isActive)
	{
		this.id=id;
		this.conveyanceCharge=conveyanceCharge;
		this.createdby=createdby;
		this.createdDate=createdDate;
		this.modifiedBy=modifiedBy;
		this.lastModifiedDate=lastModifiedDate;
		this.isActive=isActive;
		
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCreatedby() {
		return createdby;
	}
	public void setCreatedby(String createdby) {
		this.createdby = createdby;
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

	public BigDecimal getConveyanceCharge() {
		return conveyanceCharge;
	}

	public void setConveyanceCharge(BigDecimal conveyanceCharge) {
		this.conveyanceCharge = conveyanceCharge;
	}

	
}
