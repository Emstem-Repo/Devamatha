package com.kp.cms.to.reports;

import java.util.Date;

public class ReportNameSummaryTO {
	private String columnName;
	private String showColumn;
	private String position;
	private String reportName;
	private int configReportId;
	private String createdBy;
	private Date createdDate;
	
	public String getCreatedBy() {
		return createdBy;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public int getConfigReportId() {
		return configReportId;
	}
	public void setConfigReportId(int configReportId) {
		this.configReportId = configReportId;
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
	
	public String getShowColumn() {
		return showColumn;
	}
	public void setShowColumn(String showColumn) {
		this.showColumn = showColumn;
	}
	
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	
}
