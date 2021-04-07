package com.kp.cms.forms.reports;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.reports.ConfigureColumnForReportTO;

public class ColumnReportForm extends BaseActionForm{
	
	
	private String method;
	private String reportName;
	private String columnName;
	private List<ConfigureColumnForReportTO> reportNameList;
	private ConfigureColumnForReportTO reportTO;
	
	
	public ConfigureColumnForReportTO getReportTO() {
		return reportTO;
	}
	public void setReportTO(ConfigureColumnForReportTO reportTO) {
		this.reportTO = reportTO;
	}
	public List<ConfigureColumnForReportTO> getReportNameList() {
		return reportNameList;
	}
	public void setReportNameList(List<ConfigureColumnForReportTO> reportNameList) {
		this.reportNameList = reportNameList;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
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
	
	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		this.columnName = null;
		this.reportName = null;
	}

	@Override
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter(CMSConstants.FORMNAME);
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}
}