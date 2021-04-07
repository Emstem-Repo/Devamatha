package com.kp.cms.bo.admission;

import java.math.BigDecimal;
import java.util.Date;

import com.kp.cms.bo.admin.Caste;
import com.kp.cms.bo.admin.College;
import com.kp.cms.bo.admin.Religion;

public class AdmSelectionSettingsDetails implements java.io.Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private BigDecimal cutOffPercentage;
	private AdmSelectionSettings admSelectionSettingsId;
	private Boolean	isCaste;
	private Boolean isReligion;
	private Boolean	isInstitution;
	private Caste casteId;
	private	Religion religionId;
	private	College	institutionId;
	
	private int selectionOrder;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private Boolean isActive;
	
	public AdmSelectionSettingsDetails() {
		super();
		// TODO Auto-generated constructor stub
	}
	public AdmSelectionSettingsDetails(BigDecimal cutOffPercentage,
			AdmSelectionSettings admSelectionSettingsId, Boolean isCaste,
			Boolean isReligion, Boolean isInstitution, Caste casteId,
			Religion religionId, College institutionId, int selectionOrder,
			String createdBy, Date createdDate, String modifiedBy,
			Date lastModifiedDate, Boolean isActive) {
		super();
		this.cutOffPercentage = cutOffPercentage;
		this.admSelectionSettingsId = admSelectionSettingsId;
		this.isCaste = isCaste;
		this.isReligion = isReligion;
		this.isInstitution = isInstitution;
		this.casteId = casteId;
		this.religionId = religionId;
		this.institutionId = institutionId;
		this.selectionOrder = selectionOrder;
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
	public BigDecimal getCutOffPercentage() {
		return cutOffPercentage;
	}
	public void setCutOffPercentage(BigDecimal cutOffPercentage) {
		this.cutOffPercentage = cutOffPercentage;
	}
	public AdmSelectionSettings getAdmSelectionSettingsId() {
		return admSelectionSettingsId;
	}
	public void setAdmSelectionSettingsId(
			AdmSelectionSettings admSelectionSettingsId) {
		this.admSelectionSettingsId = admSelectionSettingsId;
	}
	public Boolean getIsCaste() {
		return isCaste;
	}
	public void setIsCaste(Boolean isCaste) {
		this.isCaste = isCaste;
	}
	public Boolean getIsReligion() {
		return isReligion;
	}
	public void setIsReligion(Boolean isReligion) {
		this.isReligion = isReligion;
	}
	Boolean getIsInstitution() {
		return isInstitution;
	}
	public void setIsInstitution(Boolean isInstitution) {
		this.isInstitution = isInstitution;
	}
	public Caste getCasteId() {
		return casteId;
	}
	public void setCasteId(Caste casteId) {
		this.casteId = casteId;
	}
	public Religion getReligionId() {
		return religionId;
	}
	public void setReligionId(Religion religionId) {
		this.religionId = religionId;
	}
	public College getInstitutionId() {
		return institutionId;
	}
	public void setInstitutionId(College institutionId) {
		this.institutionId = institutionId;
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
	public int getSelectionOrder() {
		return selectionOrder;
	}
	public void setSelectionOrder(int selectionOrder) {
		this.selectionOrder = selectionOrder;
	}
	
	
}
