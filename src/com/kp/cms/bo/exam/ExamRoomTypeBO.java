package com.kp.cms.bo.exam;

import java.util.Date;

public class ExamRoomTypeBO implements java.io.Serializable ,IExamGenBO {
	private Integer id;
	private String name;
	private String desc;
	private String createdBy;;
	private String modifiedBy;
	private Date createdDate;
	private Date lastModifiedDate;
	private boolean isActive;

	public ExamRoomTypeBO() {
		super();
	}

	
	public ExamRoomTypeBO(int id, String name,
			String desc, String createdBy, Date createdDate,
			Date lastModifiedDate, String modifiedBy, boolean isActive) {
		super();
		this.id = id;
		this.name = name;
		this.desc = desc;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.lastModifiedDate = lastModifiedDate;
		this.modifiedBy = modifiedBy;
		this.isActive = isActive;
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getDesc() {
		return desc;
	}


	public void setDesc(String desc) {
		this.desc = desc;
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


	@Override
	public void setIsActive(boolean isActive) {
		this.isActive=isActive;
		
	}


	
	


}
