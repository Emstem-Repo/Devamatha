package com.kp.cms.bo.admin;

// Generated Nov 26, 2009 4:25:51 PM by Hibernate Tools 3.2.0.b9

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * DocTypeExams generated by hbm2java
 */

public class DocTypeExams implements java.io.Serializable {

	private int id;
	private DocType docType;
	private String name;
	private Boolean isActive;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private Set<EdnQualification> ednQualifications = new HashSet<EdnQualification>(
			0);

	public DocTypeExams() {
	}

	public DocTypeExams(int id) {
		this.id = id;
	}

	public DocTypeExams(int id, DocType docType, String name, Boolean isActive,
			String createdBy, Date createdDate, String modifiedBy,
			Date lastModifiedDate, Set<EdnQualification> ednQualifications) {
		this.id = id;
		this.docType = docType;
		this.name = name;
		this.isActive = isActive;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.lastModifiedDate = lastModifiedDate;
		this.ednQualifications = ednQualifications;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public DocType getDocType() {
		return this.docType;
	}

	public void setDocType(DocType docType) {
		this.docType = docType;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getIsActive() {
		return this.isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}


	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getModifiedBy() {
		return this.modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}


	public Date getLastModifiedDate() {
		return this.lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}


	public Set<EdnQualification> getEdnQualifications() {
		return this.ednQualifications;
	}

	public void setEdnQualifications(Set<EdnQualification> ednQualifications) {
		this.ednQualifications = ednQualifications;
	}

}
