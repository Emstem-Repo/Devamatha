package com.kp.cms.to.reports;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class ConfigureColumnForReportTO implements Serializable {

	private int id;
	private String reportName;
	private String columnName;
	private Boolean showColumn;
	private Integer position;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private List<ReportNameSummaryTO> reportNameSummaryList;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getReportName() {
		return reportName;
	}
	public void setReportName(String reportName) {
		this.reportName = reportName;
	}
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public Boolean getShowColumn() {
		return showColumn;
	}
	public void setShowColumn(Boolean showColumn) {
		this.showColumn = showColumn;
	}
	public Integer getPosition() {
		return position;
	}
	public void setPosition(Integer position) {
		this.position = position;
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
	public List<ReportNameSummaryTO> getReportNameSummaryList() {
		return reportNameSummaryList;
	}
	public void setReportNameSummaryList(
			List<ReportNameSummaryTO> reportNameSummaryList) {
		this.reportNameSummaryList = reportNameSummaryList;
	}
}