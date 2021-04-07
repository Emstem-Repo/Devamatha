package com.kp.cms.forms.admission;

import org.apache.struts.upload.FormFile;

import com.kp.cms.forms.BaseActionForm;

public class AdmissionDataUploadForm extends BaseActionForm { 
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String applicationYear;
	private FormFile csvFile;
	
	public String getApplicationYear() {
		return applicationYear;
	}
	public void setApplicationYear(String applicationYear) {
		this.applicationYear = applicationYear;
	}
	public FormFile getCsvFile() {
		return csvFile;
	}
	public void setCsvFile(FormFile csvFile) {
		this.csvFile = csvFile;
	}
	
	@Override
	public void clear() {
		super.setProgramId("");
		super.setProgramTypeId("");
		super.setCourseId("");
		this.csvFile=null;
		this.setApplicationYear("");
	}
}