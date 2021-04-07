package com.kp.cms.bo.fees;

import java.util.Date;
import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.bo.admin.FeePayment;


public class FeeChallanVerificationDetails implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private AdmAppln admApplnId;
	private FeePayment feePayId;
	private Boolean isVerified;
	private String createdBy;;
	private String modifiedBy;
	private Date createdDate;
	private Date lastModifiedDate;
	private Boolean isActive;
	
	public FeeChallanVerificationDetails(int id) {
		super();
		this.id = id;
	}
	
	
	public FeeChallanVerificationDetails() {
		super();
		// TODO Auto-generated constructor stub
	}
	public FeeChallanVerificationDetails(int id, AdmAppln admApplnId,
			FeePayment feePayId, Boolean isVerified, String createdBy,
			String modifiedBy, Date createdDate, Date lastModifiedDate,
			Boolean isActive) {
		super();
		this.id = id;
		this.admApplnId = admApplnId;
		this.feePayId = feePayId;
		this.isVerified = isVerified;
		this.createdBy = createdBy;
		this.modifiedBy = modifiedBy;
		this.createdDate = createdDate;
		this.lastModifiedDate = lastModifiedDate;
		this.isActive = isActive;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public AdmAppln getAdmApplnId() {
		return admApplnId;
	}
	public void setAdmApplnId(AdmAppln admApplnId) {
		this.admApplnId = admApplnId;
	}
	
	public Boolean getIsVerified() {
		return isVerified;
	}
	public void setIsVerified(Boolean isVerified) {
		this.isVerified = isVerified;
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
	public Boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	public FeePayment getFeePayId() {
		return feePayId;
	}
	public void setFeePayId(FeePayment feePayId) {
		this.feePayId = feePayId;
	}
	
	

}
