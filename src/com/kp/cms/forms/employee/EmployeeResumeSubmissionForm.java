package com.kp.cms.forms.employee;

import com.kp.cms.forms.BaseActionForm;

public class EmployeeResumeSubmissionForm extends BaseActionForm{

	private String printPage;

	public String getPrintPage() {
		return printPage;
	}

	public void setPrintPage(String printPage) {
		this.printPage = printPage;
	}
	public void clearFields(){
		printPage = null;
	}
}
