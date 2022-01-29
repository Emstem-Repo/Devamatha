package com.kp.cms.forms.admin;

import org.apache.struts.upload.FormFile;

import com.kp.cms.forms.BaseActionForm;

public class SyllabusUploadForm  extends BaseActionForm{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private FormFile thefile;

	public FormFile getThefile() {
		return thefile;
	}

	public void setThefile(FormFile thefile) {
		this.thefile = thefile;
	}
	
	public void reset(){
		this.thefile=null;
	}
}

