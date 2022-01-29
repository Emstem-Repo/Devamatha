package com.kp.cms.bo.admin;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class RemarkType {
	private int id;
	private String remarkType;
	private String color;
	private int maxOccurrences;
	private String createdBy;;
	private String modifiedBy;
	private Date createdDate;
	private Date lastModifiedDate;
	private Boolean isActive;
	private Set<StudentRemarks> studentRemarkses = new HashSet<StudentRemarks>(
			0);
	public RemarkType() {
	}

	public RemarkType(int id) {
		this.id = id;
	}

	public RemarkType(int id, String createdBy,
			String modifiedBy, String remarkType, Date createdDate,
			Date lastModifiedDate,Set<StudentRemarks> studentRemarkses) {
		this.id = id;
		this.createdBy = createdBy;
		this.modifiedBy = modifiedBy;
		this.remarkType = remarkType;
		this.createdDate = createdDate;
		this.lastModifiedDate = lastModifiedDate;
		this.studentRemarkses = studentRemarkses;
	}

	public int getId() {
		return id;
	}

	public String getRemarkType() {
		return remarkType;
	}

	public String getColor() {
		return color;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setRemarkType(String remarkType) {
		this.remarkType = remarkType;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}


	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public int getMaxOccurrences() {
		return maxOccurrences;
	}

	public void setMaxOccurrences(int maxOccurrences) {
		this.maxOccurrences = maxOccurrences;
	}


	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	public Boolean getIsActive() {
		return isActive;
	}
	public Set<StudentRemarks> getStudentRemarkses() {
		return this.studentRemarkses;
	}

	public void setStudentRemarkses(Set<StudentRemarks> studentRemarkses) {
		this.studentRemarkses = studentRemarkses;
	}

}
