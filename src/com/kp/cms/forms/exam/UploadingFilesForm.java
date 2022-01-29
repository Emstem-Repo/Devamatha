package com.kp.cms.forms.exam;

import org.apache.struts.upload.FormFile;

import com.kp.cms.forms.BaseActionForm;

public class UploadingFilesForm extends BaseActionForm{
	private FormFile formFile;

	public FormFile getFormFile() {
		return formFile;
	}

	public void setFormFile(FormFile formFile) {
		this.formFile = formFile;
	}
}
