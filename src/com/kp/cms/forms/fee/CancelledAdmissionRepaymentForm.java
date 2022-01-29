package com.kp.cms.forms.fee;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admission.AdmApplnTO;

public class CancelledAdmissionRepaymentForm extends BaseActionForm {
	private String registerNo;
	private String applnNo;
	private AdmApplnTO admApplnTo;
	private String chequeNo;
	private String chequeDate;
	private String chequeIssuedDate;
	private String repaidAmt;
	
	public String getRegisterNo() {
		return registerNo;
	}
	public void setRegisterNo(String registerNo) {
		this.registerNo = registerNo;
	}
	public String getApplnNo() {
		return applnNo;
	}
	public void setApplnNo(String applnNo) {
		this.applnNo = applnNo;
	}
	
	public AdmApplnTO getAdmApplnTo() {
		return admApplnTo;
	}
	public void setAdmApplnTo(AdmApplnTO admApplnTo) {
		this.admApplnTo = admApplnTo;
	}
	public String getChequeNo() {
		return chequeNo;
	}
	public void setChequeNo(String chequeNo) {
		this.chequeNo = chequeNo;
	}
	public String getChequeDate() {
		return chequeDate;
	}
	public void setChequeDate(String chequeDate) {
		this.chequeDate = chequeDate;
	}
	public String getChequeIssuedDate() {
		return chequeIssuedDate;
	}
	public void setChequeIssuedDate(String chequeIssuedDate) {
		this.chequeIssuedDate = chequeIssuedDate;
	}
	public String getRepaidAmt() {
		return repaidAmt;
	}
	public void setRepaidAmt(String repaidAmt) {
		this.repaidAmt = repaidAmt;
	}
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		
		return actionErrors;
	}
	
	public void resetFields() {
		this.registerNo=null;
		this.applnNo=null;
		this.admApplnTo=null;
		this.chequeDate=null;
		this.chequeIssuedDate=null;
		this.chequeNo=null;
		this.repaidAmt=null;
	}
}
