package com.kp.cms.bo.admin;

// Generated Jul 13, 2009 2:37:04 PM by Hibernate Tools 3.2.0.b9

import java.util.Date;

/**
 * ConfigReportsColumn generated by hbm2java
 */
public class ConfigReportsColumn implements java.io.Serializable {

	private int id;
	private String reportName;
	private String columnName;
	private Boolean showColumn;
	private Integer position;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;

	public ConfigReportsColumn() {
	}

	public ConfigReportsColumn(int id) {
		this.id = id;
	}

	public ConfigReportsColumn(int id, String reportName, String columnName,
			Boolean showColumn, Integer position, String createdBy,
			Date createdDate, String modifiedBy, Date lastModifiedDate) {
		this.id = id;
		this.reportName = reportName;
		this.columnName = columnName;
		this.showColumn = showColumn;
		this.position = position;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.lastModifiedDate = lastModifiedDate;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getReportName() {
		return this.reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	public String getColumnName() {
		return this.columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public Boolean getShowColumn() {
		return this.showColumn;
	}

	public void setShowColumn(Boolean showColumn) {
		this.showColumn = showColumn;
	}

	public Integer getPosition() {
		return this.position;
	}

	public void setPosition(Integer position) {
		this.position = position;
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

}
