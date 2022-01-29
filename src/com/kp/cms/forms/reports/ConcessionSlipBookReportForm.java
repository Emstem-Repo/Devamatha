package com.kp.cms.forms.reports;

import com.kp.cms.forms.BaseActionForm;

public class ConcessionSlipBookReportForm extends BaseActionForm {
	private String type;
	private String organizationName;
	private String print;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public String getPrint() {
		return print;
	}

	public void setPrint(String print) {
		this.print = print;
	}
	
}
