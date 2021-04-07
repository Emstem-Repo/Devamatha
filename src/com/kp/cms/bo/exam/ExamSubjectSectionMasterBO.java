package com.kp.cms.bo.exam;

import java.util.Date;

/**
 * Dec 14, 2009 Created By 9Elements
 */
public class ExamSubjectSectionMasterBO implements java.io.Serializable  {
	
	private int id;
	private String name;
	private int isinitialise;
	private String createdBy;;
	private String modifiedBy;
	private Date createdDate;
	private Date lastModifiedDate;
	private boolean isActive;
	
	public ExamSubjectSectionMasterBO(String name) {
		super();
		this.name = name;
	}
	public ExamSubjectSectionMasterBO() {
		super();
	}
	public ExamSubjectSectionMasterBO(int id, String name, int isinitialise,
			String createdBy, Date createdDate, String modifiedBy,
			Date lastModifiedDate, boolean isActive) {
		super();
		this.id = id;
		this.name = name;
		this.isinitialise = isinitialise;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.lastModifiedDate = lastModifiedDate;
		this.isActive = isActive;
	}
	
	
	
	public ExamSubjectSectionMasterBO(int id,String name, int isinitialise,
			String modifiedBy, Date lastModifiedDate) {
		super();
		this.id = id;
		this.name = name;
		this.isinitialise = isinitialise;
		this.modifiedBy = modifiedBy;
		this.lastModifiedDate = lastModifiedDate;
	}
	
	public ExamSubjectSectionMasterBO(int id,String modifiedBy,
			Date lastModifiedDate, boolean isActive) {
		super();
		this.id = id;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getIsinitialise() {
		return isinitialise;
	}
	public void setIsinitialise(int isinitialise) {
		this.isinitialise = isinitialise;
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
	public boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
	}
	
	
	
	
}
