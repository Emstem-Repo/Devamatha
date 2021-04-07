package com.kp.cms.forms.reports;

import com.kp.cms.forms.BaseActionForm;

public class BirtFeeReportForm extends BaseActionForm{
	
	String reportName;
	int count;
	boolean cjc;
	
	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getReportName() {
		return reportName;
	}

	public boolean isCjc() {
		return cjc;
	}

	public void setCjc(boolean cjc) {
		this.cjc = cjc;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}
}
