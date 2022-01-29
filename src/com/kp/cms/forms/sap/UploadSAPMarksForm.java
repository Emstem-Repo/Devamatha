package com.kp.cms.forms.sap;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.kp.cms.forms.BaseActionForm;

public class UploadSAPMarksForm extends BaseActionForm {
	private FormFile thefile;
	private String dupRegNumMsg;
	private String dupRegNum;
	private boolean duPliUploadedRegNum;
	private boolean dupliRegNum;
	private boolean wrgDateFormat;
	private String displayWrgDateMsg;
	private boolean emptyDate;
	private String displayEmptyDate;
	private String examDate;
	private Boolean emptyStatus;
	private String emptyStatusMsg;
	private String emptuStatusRegNum;

	public FormFile getThefile() {
		return thefile;
	}

	public void setThefile(FormFile thefile) {
		this.thefile = thefile;
	}

	public String getDupRegNumMsg() {
		return dupRegNumMsg;
	}

	public void setDupRegNumMsg(String dupRegNumMsg) {
		this.dupRegNumMsg = dupRegNumMsg;
	}

	public String getDupRegNum() {
		return dupRegNum;
	}

	public void setDupRegNum(String dupRegNum) {
		this.dupRegNum = dupRegNum;
	}

	public boolean isDuPliUploadedRegNum() {
		return duPliUploadedRegNum;
	}

	public void setDuPliUploadedRegNum(boolean duPliUploadedRegNum) {
		this.duPliUploadedRegNum = duPliUploadedRegNum;
	}

	public boolean isDupliRegNum() {
		return dupliRegNum;
	}

	public void setDupliRegNum(boolean dupliRegNum) {
		this.dupliRegNum = dupliRegNum;
	}
	
	public void resetFields(){
		this.dupRegNumMsg=null;
		this.dupRegNum=null;
		this.duPliUploadedRegNum=false;
		this.dupliRegNum=false;
		this.wrgDateFormat=false;
		this.displayWrgDateMsg=null;
		this.emptyDate=false;
		this.displayEmptyDate=null;
		this.emptyStatus=false;
	}
	 public ActionErrors validate(ActionMapping mapping,
				HttpServletRequest request) {
			String formName = request.getParameter("formName");
			ActionErrors actionErrors = super.validate(mapping, request, formName);

			return actionErrors;
		}

	public boolean isWrgDateFormat() {
		return wrgDateFormat;
	}

	public void setWrgDateFormat(boolean wrgDateFormat) {
		this.wrgDateFormat = wrgDateFormat;
	}

	public String getDisplayWrgDateMsg() {
		return displayWrgDateMsg;
	}

	public void setDisplayWrgDateMsg(String displayWrgDateMsg) {
		this.displayWrgDateMsg = displayWrgDateMsg;
	}

	

	public String getDisplayEmptyDate() {
		return displayEmptyDate;
	}

	public void setDisplayEmptyDate(String displayEmptyDate) {
		this.displayEmptyDate = displayEmptyDate;
	}

	public boolean isEmptyDate() {
		return emptyDate;
	}

	public void setEmptyDate(boolean emptyDate) {
		this.emptyDate = emptyDate;
	}

	public String getExamDate() {
		return examDate;
	}

	public void setExamDate(String examDate) {
		this.examDate = examDate;
	}

	public Boolean getEmptyStatus() {
		return emptyStatus;
	}

	public void setEmptyStatus(Boolean emptyStatus) {
		this.emptyStatus = emptyStatus;
	}

	public String getEmptyStatusMsg() {
		return emptyStatusMsg;
	}

	public void setEmptyStatusMsg(String emptyStatusMsg) {
		this.emptyStatusMsg = emptyStatusMsg;
	}

	public String getEmptuStatusRegNum() {
		return emptuStatusRegNum;
	}

	public void setEmptuStatusRegNum(String emptuStatusRegNum) {
		this.emptuStatusRegNum = emptuStatusRegNum;
	}

	
}
