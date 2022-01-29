package com.kp.cms.forms.reports;

import com.kp.cms.forms.BaseActionForm;

public class AdmissionIncompleteForm extends BaseActionForm {

	private String method;

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}
	public void clear(){
		this.setProgramTypeId(null);
		this.setProgramId(null);
		this.setCourseId(null);
	}
}
