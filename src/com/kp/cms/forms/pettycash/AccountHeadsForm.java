package com.kp.cms.forms.pettycash;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.kp.cms.forms.BaseActionForm;

public class AccountHeadsForm extends BaseActionForm {

	private String academicYear;
	private FormFile theFile;
	private List<String> receiptNos;
	private List<String> regNos;

	public FormFile getTheFile() {
		return theFile;
	}

	public String getAcademicYear() {
		return academicYear;
	}

	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}

	public void setTheFile(FormFile theFile) {
		this.theFile = theFile;
	}
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
 		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		
		return actionErrors;
	}
	
	public void resetFields(){
		this.theFile=null;
		
	}

	public List<String> getReceiptNos() {
		return receiptNos;
	}

	public void setReceiptNos(List<String> receiptNos) {
		this.receiptNos = receiptNos;
	}

	public List<String> getRegNos() {
		return regNos;
	}

	public void setRegNos(List<String> regNos) {
		this.regNos = regNos;
	}
	

}
