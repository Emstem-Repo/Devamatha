package com.kp.cms.bo.admin;

import java.util.Date;

public class SyllabusEntryHeadingDesc implements java.io.Serializable{
	private int id;
	private String heading;
	private String description;
	private SyllabusEntryUnitsHours syllabusEntryUnitsHours;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private Boolean isActive;
	private Integer headingNo;
	
	
	public Integer getHeadingNo() {
		return headingNo;
	}
	public void setHeadingNo(Integer headingNo) {
		this.headingNo = headingNo;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getHeading() {
		return heading;
	}
	public void setHeading(String heading) {
		this.heading = heading;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public SyllabusEntryUnitsHours getSyllabusEntryUnitsHours() {
		return syllabusEntryUnitsHours;
	}
	public void setSyllabusEntryUnitsHours(
			SyllabusEntryUnitsHours syllabusEntryUnitsHours) {
		this.syllabusEntryUnitsHours = syllabusEntryUnitsHours;
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
	public SyllabusEntryHeadingDesc(){
		
	}
	public SyllabusEntryHeadingDesc(int id, String heading, String description,
			SyllabusEntryUnitsHours syllabusEntryUnitsHours, String createdBy,
			Date createdDate, String modifiedBy, Date lastModifiedDate,
			Boolean isActive, Integer headingNo) {
		super();
		this.id = id;
		this.heading = heading;
		this.description = description;
		this.syllabusEntryUnitsHours = syllabusEntryUnitsHours;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.lastModifiedDate = lastModifiedDate;
		this.isActive = isActive;
		this.headingNo = headingNo;
	}
}
