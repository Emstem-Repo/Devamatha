package com.kp.cms.forms.fee;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.SingleFieldMasterTO;
import com.kp.cms.to.fee.InstallmentPaymentTO;

public class InstallmentPaymentForm extends BaseActionForm{
private String method;
private String searchByValue;
private String searchBy;
private String date;
private String financialYear;
private String payMentTypeId;
private String referenceNo;
private List<SingleFieldMasterTO> paymentTypeList;
private List<InstallmentPaymentTO> paymentTOList;
private String studentName;

public String getStudentName() {
	return studentName;
}

public void setStudentName(String studentName) {
	this.studentName = studentName;
}

public List<InstallmentPaymentTO> getPaymentTOList() {
	return paymentTOList;
}

public void setPaymentTOList(List<InstallmentPaymentTO> paymentTOList) {
	this.paymentTOList = paymentTOList;
}

public List<SingleFieldMasterTO> getPaymentTypeList() {
	return paymentTypeList;
}

public void setPaymentTypeList(List<SingleFieldMasterTO> paymentTypeList) {
	this.paymentTypeList = paymentTypeList;
}

public String getFinancialYear() {
	return financialYear;
}

public void setFinancialYear(String financialYear) {
	this.financialYear = financialYear;
}

public String getDate() {
	return date;
}

public void setDate(String date) {
	this.date = date;
}

public String getMethod() {
	return method;
}

public void setMethod(String method) {
	this.method = method;
}
public String getSearchByValue() {
	return searchByValue;
}

public String getSearchBy() {
	return searchBy;
}

public void setSearchByValue(String searchByValue) {
	this.searchByValue = searchByValue;
}

public void setSearchBy(String searchBy) {
	this.searchBy = searchBy;
}

public String getPayMentTypeId() {
	return payMentTypeId;
}

public String getReferenceNo() {
	return referenceNo;
}


public void setPayMentTypeId(String payMentTypeId) {
	this.payMentTypeId = payMentTypeId;
}

public void setReferenceNo(String referenceNo) {
	this.referenceNo = referenceNo;
}

public void clear(){
	this.searchBy = null;
	this.searchByValue = null;
	this.paymentTOList = null;
	this.date = null;
}
public ActionErrors validate(ActionMapping mapping,
		HttpServletRequest request) {
	String formName = request.getParameter(CMSConstants.FORMNAME);
	ActionErrors actionErrors = new ActionErrors();
	actionErrors = super.validate(mapping, request, formName);

	return actionErrors;
}
}
