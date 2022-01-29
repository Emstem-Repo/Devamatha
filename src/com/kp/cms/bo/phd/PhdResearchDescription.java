package com.kp.cms.bo.phd;

import java.io.Serializable;
import java.util.Date;

public class PhdResearchDescription implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private PhdResearchPublication researchPublication;
	private PhdDocumentSubmissionBO documetSubmission;
	private String issn;
	private String issueNumber;
	private String level;
	private String nameJournal;
	private String title;
	private String datePhd;
	private String volumeNo;
	private String description;
	private Boolean isActive;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	
	
	public PhdResearchDescription(){
		
	}
	
	/**
	 * @param id
	 * @param researchPublication
	 * @param documetSubmission
	 * @param description
	 * @param researchDate
	 * @param isActive
	 * @param createdBy
	 * @param createdDate
	 * @param modifiedBy
	 * @param modifiedDate
	 */
	public PhdResearchDescription(int id,PhdResearchPublication researchPublication,PhdDocumentSubmissionBO documetSubmission,
		String issn,String issueNumber,String level,String nameJournal,String title,String datePhd,String monthPhd,String yearPhd,String volumeNo,String description
	    ,Boolean isActive,String createdBy,Date createdDate,String modifiedBy,Date modifiedDate) {
		super();
		this.id = id;
		this.researchPublication = researchPublication;
		this.documetSubmission = documetSubmission;
		this.issn=issn;
		this.issueNumber=issueNumber;
		this.level=level;
		this.nameJournal=nameJournal;
		this.title=title;
		this.datePhd=datePhd;
		this.volumeNo=volumeNo;
		this.description = description;
		this.isActive = isActive;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public PhdResearchPublication getResearchPublication() {
		return researchPublication;
	}
	public void setResearchPublication(PhdResearchPublication researchPublication) {
		this.researchPublication = researchPublication;
	}
	public PhdDocumentSubmissionBO getDocumetSubmission() {
		return documetSubmission;
	}
	public void setDocumetSubmission(PhdDocumentSubmissionBO documetSubmission) {
		this.documetSubmission = documetSubmission;
	}
	
	public String getIssn() {
		return issn;
	}

	public void setIssn(String issn) {
		this.issn = issn;
	}

	public String getIssueNumber() {
		return issueNumber;
	}

	public void setIssueNumber(String issueNumber) {
		this.issueNumber = issueNumber;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getNameJournal() {
		return nameJournal;
	}

	public void setNameJournal(String nameJournal) {
		this.nameJournal = nameJournal;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDatePhd() {
		return datePhd;
	}

	public void setDatePhd(String datePhd) {
		this.datePhd = datePhd;
	}
    public String getVolumeNo() {
		return volumeNo;
	}

	public void setVolumeNo(String volumeNo) {
		this.volumeNo = volumeNo;
	}

	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
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
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	
	
}
