package com.kp.cms.bo.admin;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class SyllabusEntryUnitsHours implements java.io.Serializable{
	private int id;
	private Integer teachingHours;
	private String units;
	private SyllabusEntry syllabusEntry;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private Boolean isActive;
	private Set<SyllabusEntryHeadingDesc> syllabusEntryHeadingDescs=new HashSet<SyllabusEntryHeadingDesc>(0);
	private Integer unitNo;
	
	
	public Integer getUnitNo() {
		return unitNo;
	}
	public void setUnitNo(Integer unitNo) {
		this.unitNo = unitNo;
	}
	public Set<SyllabusEntryHeadingDesc> getSyllabusEntryHeadingDescs() {
		return syllabusEntryHeadingDescs;
	}
	public void setSyllabusEntryHeadingDescs(
			Set<SyllabusEntryHeadingDesc> syllabusEntryHeadingDescs) {
		this.syllabusEntryHeadingDescs = syllabusEntryHeadingDescs;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public Integer getTeachingHours() {
		return teachingHours;
	}
	public void setTeachingHours(Integer teachingHours) {
		this.teachingHours = teachingHours;
	}
	public String getUnits() {
		return units;
	}
	public void setUnits(String units) {
		this.units = units;
	}
	public SyllabusEntry getSyllabusEntry() {
		return syllabusEntry;
	}
	public void setSyllabusEntry(SyllabusEntry syllabusEntry) {
		this.syllabusEntry = syllabusEntry;
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
	public SyllabusEntryUnitsHours(){
		
	}
	public SyllabusEntryUnitsHours(int id, Integer teachingHours, String units,
			SyllabusEntry syllabusEntry, String createdBy, Date createdDate,
			String modifiedBy, Date lastModifiedDate, Boolean isActive,
			Set<SyllabusEntryHeadingDesc> syllabusEntryHeadingDescs,
			Integer unitNo) {
		super();
		this.id = id;
		this.teachingHours = teachingHours;
		this.units = units;
		this.syllabusEntry = syllabusEntry;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.lastModifiedDate = lastModifiedDate;
		this.isActive = isActive;
		this.syllabusEntryHeadingDescs = syllabusEntryHeadingDescs;
		this.unitNo = unitNo;
	}
	
	
}
