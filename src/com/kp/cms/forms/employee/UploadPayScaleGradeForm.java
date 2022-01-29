package com.kp.cms.forms.employee;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.kp.cms.forms.BaseActionForm;

public class UploadPayScaleGradeForm extends BaseActionForm {
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
