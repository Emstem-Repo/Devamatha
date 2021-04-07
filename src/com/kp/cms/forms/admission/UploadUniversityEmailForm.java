package com.kp.cms.forms.admission;

import org.apache.struts.upload.FormFile;

import com.kp.cms.forms.BaseActionForm;

public class UploadUniversityEmailForm extends BaseActionForm {
	private FormFile thefile;

	public FormFile getThefile() {
		return thefile;
	}

	public void setThefile(FormFile thefile) {
		this.thefile = thefile;
	}

	public void resetFields() {
		this.thefile=null;
	}
}
