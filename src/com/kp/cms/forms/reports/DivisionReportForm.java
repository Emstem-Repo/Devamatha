package com.kp.cms.forms.reports;

import com.kp.cms.forms.BaseActionForm;

public class DivisionReportForm extends BaseActionForm{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String divId;
	private String print;
	private int maxAccCount;
	
	public String getDivId() {
		return divId;
	}
	public void setDivId(String divId) {
		this.divId = divId;
	}
	public String getPrint() {
		return print;
	}
	public void setPrint(String print) {
		this.print = print;
	}
	public int getMaxAccCount() {
		return maxAccCount;
	}
	public void setMaxAccCount(int maxAccCount) {
		this.maxAccCount = maxAccCount;
	}


}
