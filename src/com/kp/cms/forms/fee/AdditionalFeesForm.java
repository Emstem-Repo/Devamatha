package com.kp.cms.forms.fee;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.kp.cms.forms.BaseActionForm;

public class AdditionalFeesForm extends BaseActionForm {
	private String academicYear;
	private FormFile theFile;
	private List<Integer> billNoList;
	private List<String> feeCodeList;

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

	public List<Integer> getBillNoList() {
		return billNoList;
	}

	public void setBillNoList(List<Integer> billNoList) {
		this.billNoList = billNoList;
	}

	public List<String> getFeeCodeList() {
		return feeCodeList;
	}

	public void setFeeCodeList(List<String> feeCodeList) {
		this.feeCodeList = feeCodeList;
	}
	
}
