package com.kp.cms.forms.admin;

import org.apache.struts.upload.FormFile;

import com.kp.cms.forms.BaseActionForm;

public class DatabaseForm extends BaseActionForm {

	private String mode;
	private String method;
	private FormFile thefile;
	
	public FormFile getThefile() {
		return thefile;
	}

	public void setThefile(FormFile thefile) {
		this.thefile = thefile;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}
}