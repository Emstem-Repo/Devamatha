package com.kp.cms.forms.reports;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;

public class UploadFormatForm extends BaseActionForm{
	
	private int id;
	private String method;
	private String uploadFormatFile;
	private String formatType;
	private String mode;
	private String downloadExcel;
	private String downloadCSV;
	private String fileNo;

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getUploadFormatFile() {
		return uploadFormatFile;
	}

	public void setUploadFormatFile(String uploadFormatFile) {
		this.uploadFormatFile = uploadFormatFile;
	}

	public String getFormatType() {
		return formatType;
	}

	public void setFormatType(String formatType) {
		this.formatType = formatType;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getDownloadExcel() {
		return downloadExcel;
	}

	public void setDownloadExcel(String downloadExcel) {
		this.downloadExcel = downloadExcel;
	}

	public String getDownloadCSV() {
		return downloadCSV;
	}

	public void setDownloadCSV(String downloadCSV) {
		this.downloadCSV = downloadCSV;
	}
	public String getFileNo() {
		return fileNo;
	}

	public void setFileNo(String fileNo) {
		this.fileNo = fileNo;
	}
	
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		return actionErrors;
	}

	public void resetFields() {
		this.id=0;
		this.method=null;
		this.uploadFormatFile=null;
		this.formatType=null;
	}

}
