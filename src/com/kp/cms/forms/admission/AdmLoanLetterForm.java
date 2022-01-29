package com.kp.cms.forms.admission;

import java.util.List;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admission.AdmLoanLetterDetailsTO;

public class AdmLoanLetterForm extends BaseActionForm {
	
	private String admittedDate;
	private String applicationNo;
	private String registerNo;
	private Boolean checked1;
	private List<AdmLoanLetterDetailsTO> studentInfoList;
	
	public String getAdmittedDate() {
		return admittedDate;
	}
	public void setAdmittedDate(String admittedDate) {
		this.admittedDate = admittedDate;
	}
	public String getApplicationNo() {
		return applicationNo;
	}
	public void setApplicationNo(String applicationNo) {
		this.applicationNo = applicationNo;
	}
	public String getRegisterNo() {
		return registerNo;
	}
	public void setRegisterNo(String registerNo) {
		this.registerNo = registerNo;
	}
	public List<AdmLoanLetterDetailsTO> getStudentInfoList() {
		return studentInfoList;
	}
	public void setStudentInfoList(List<AdmLoanLetterDetailsTO> studentInfoList) {
		this.studentInfoList = studentInfoList;
	}

	public Boolean getChecked1() {
		return checked1;
	}
	public void setChecked1(Boolean checked1) {
		this.checked1 = checked1;
	}
	public void resetFields() {
		this.admittedDate=null;
		this.applicationNo=null;
		this.registerNo=null;
		this.setStudentInfoList(null);
		
	}
	
	public void clearList() {
		this.setStudentInfoList(null);
		
	}
	
}
