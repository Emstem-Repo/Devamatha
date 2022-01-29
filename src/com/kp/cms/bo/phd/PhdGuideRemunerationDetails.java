package com.kp.cms.bo.phd;

/**
 * Dec 29, 2009
 * Created By 9Elements Team
 */
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class PhdGuideRemunerationDetails implements Serializable {
    
	private int id;
	private PhdGuideRemunerations guideRemunerationId;
	private DocumentDetailsBO documentId;
	private BigDecimal amount;
	private Boolean isActive;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;

	public PhdGuideRemunerationDetails() {
	}

	public PhdGuideRemunerationDetails(int id,PhdGuideRemunerations guideRemunerationId,DocumentDetailsBO documentId,BigDecimal amount,
			Boolean isActive,String createdBy,String modifiedBy,Date createdDate,Date lastModifiedDate) 
	{
		
		this.id = id;
		this.guideRemunerationId=guideRemunerationId;
		this.documentId=documentId;
		this.amount=amount;
		this.isActive = isActive;
		this.createdBy = createdBy;
		this.modifiedBy = modifiedBy;
		this.createdDate = createdDate;
		this.lastModifiedDate = lastModifiedDate;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public PhdGuideRemunerations getGuideRemunerationId() {
		return guideRemunerationId;
	}

	public void setGuideRemunerationId(PhdGuideRemunerations guideRemunerationId) {
		this.guideRemunerationId = guideRemunerationId;
	}

	public DocumentDetailsBO getDocumentId() {
		return documentId;
	}

	public void setDocumentId(DocumentDetailsBO documentId) {
		this.documentId = documentId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
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


	

}
