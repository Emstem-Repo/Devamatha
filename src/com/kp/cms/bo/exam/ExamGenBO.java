package com.kp.cms.bo.exam;

import java.io.Serializable;
import java.util.Date;

/**
 * Dec 17, 2009 Created By 9Elements Team
 */
@SuppressWarnings("serial")
public class ExamGenBO implements Serializable, IExamGenBO {

	protected Integer id;
	protected String name;
	protected String createdBy;;
	protected String modifiedBy;
	protected Date createdDate;
	protected Date lastModifiedDate;
	protected boolean isActive;

	public ExamGenBO() {
		super();
	}

	public ExamGenBO(IExamGenBO obj) {
		this.id = obj.getId();
		this.name = obj.getName();
		this.lastModifiedDate = obj.getLastModifiedDate();
		this.modifiedBy = obj.getModifiedBy();
		this.isActive = obj.getIsActive();
		this.createdBy = obj.getCreatedBy();
		this.createdDate = obj.getCreatedDate();

	}

	public ExamGenBO(int id, String name, String createdBy, Date createdDate,
			Date lastModifiedDate, String modifiedBy, boolean isActive) {
		super();
		this.id = id;
		this.name = name;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.lastModifiedDate = lastModifiedDate;
		this.modifiedBy = modifiedBy;
		this.isActive = isActive;
	}

	public ExamGenBO(Integer id, String name, String modifiedBy) {
		super();
		this.id = id;
		this.name = name;
		this.modifiedBy = modifiedBy;
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

	@Override
	public boolean getIsActive() {
		
		return isActive;
	}

	@Override
	public void setIsActive(boolean isActive) {
		this.isActive = isActive;

	}

}
