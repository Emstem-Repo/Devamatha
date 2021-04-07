package com.kp.cms.bo.admin;

import java.io.Serializable;
import java.util.Date;

public class HostelHolidaysBo implements Serializable{
	private int id;
	private Program programId;
	private Course courseId;
	private Date holidaysFrom;
	private Date holidaysTo;
	private String holidaysFromSession;
	private String holidaysToSession;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private Boolean isActive;
	private String holidaysOrVaction;
	private String description;
	private HlHostel hostelId;
	private HlBlocks blockId;
	private HlUnits unitId;
	
	
	public HlHostel getHostelId() {
		return hostelId;
	}
	public void setHostelId(HlHostel hostelId) {
		this.hostelId = hostelId;
	}
	public HlBlocks getBlockId() {
		return blockId;
	}
	public void setBlockId(HlBlocks blockId) {
		this.blockId = blockId;
	}
	public HlUnits getUnitId() {
		return unitId;
	}
	public void setUnitId(HlUnits unitId) {
		this.unitId = unitId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getHolidaysOrVaction() {
		return holidaysOrVaction;
	}
	public void setHolidaysOrVaction(String holidaysOrVaction) {
		this.holidaysOrVaction = holidaysOrVaction;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Program getProgramId() {
		return programId;
	}
	public void setProgramId(Program programId) {
		this.programId = programId;
	}
	public Course getCourseId() {
		return courseId;
	}
	public void setCourseId(Course courseId) {
		this.courseId = courseId;
	}
	public Date getHolidaysFrom() {
		return holidaysFrom;
	}
	public void setHolidaysFrom(Date holidaysFrom) {
		this.holidaysFrom = holidaysFrom;
	}
	public Date getHolidaysTo() {
		return holidaysTo;
	}
	public void setHolidaysTo(Date holidaysTo) {
		this.holidaysTo = holidaysTo;
	}
	
	public String getHolidaysFromSession() {
		return holidaysFromSession;
	}
	public void setHolidaysFromSession(String holidaysFromSession) {
		this.holidaysFromSession = holidaysFromSession;
	}
	public String getHolidaysToSession() {
		return holidaysToSession;
	}
	public void setHolidaysToSession(String holidaysToSession) {
		this.holidaysToSession = holidaysToSession;
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
}
