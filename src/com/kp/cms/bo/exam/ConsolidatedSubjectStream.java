package com.kp.cms.bo.exam;

import java.util.Date;

public class ConsolidatedSubjectStream 
{
	private int id;
	private String streamName;
	private Date createdDate;
	private String createdBy;
	private Date lastModifiedDate;
	private String modifiedBy;
	private boolean isActive;
	
	public ConsolidatedSubjectStream() {
		super();
	}
	public ConsolidatedSubjectStream(int id, 
									 String streamName,
									 Date createdDate, 
									 String createdBy, 
									 Date lastModifiedDate,
									 String modifiedBy, 
									 boolean isActive) {
		super();
		this.id = id;
		this.streamName = streamName;
		this.createdDate = createdDate;
		this.createdBy = createdBy;
		this.lastModifiedDate = lastModifiedDate;
		this.modifiedBy = modifiedBy;
		this.isActive = isActive;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getStreamName() {
		return streamName;
	}
	public void setStreamName(String streamName) {
		this.streamName = streamName;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}
	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
	}
}
