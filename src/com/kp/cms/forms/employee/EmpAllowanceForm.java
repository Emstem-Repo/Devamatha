package com.kp.cms.forms.employee;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.EmpAllowanceTO;

public class EmpAllowanceForm extends BaseActionForm{
private int id;
private String allowanceType;
private String displayOrder;
List<EmpAllowanceTO> allowanceToList;
private String origAllowance;
private String origDisplayOrder;
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getAllowanceType() {
	return allowanceType;
}
public void setAllowanceType(String allowanceType) {
	this.allowanceType = allowanceType;
}
public String getDisplayOrder() {
	return displayOrder;
}
public void setDisplayOrder(String displayOrder) {
	this.displayOrder = displayOrder;
}
public void reset(){
	this.allowanceType=null;
	this.displayOrder=null;
	this.id=0;
}
public List<EmpAllowanceTO> getAllowanceToList() {
	return allowanceToList;
}
public void setAllowanceToList(List<EmpAllowanceTO> allowanceToList) {
	this.allowanceToList = allowanceToList;
}
public ActionErrors validate(ActionMapping mapping,
		HttpServletRequest request) {
	String formName = request.getParameter(CMSConstants.FORMNAME);
	ActionErrors actionErrors = super.validate(mapping, request, formName);

	return actionErrors;
}
public String getOrigAllowance() {
	return origAllowance;
}
public void setOrigAllowance(String origAllowance) {
	this.origAllowance = origAllowance;
}
public String getOrigDisplayOrder() {
	return origDisplayOrder;
}
public void setOrigDisplayOrder(String origDisplayOrder) {
	this.origDisplayOrder = origDisplayOrder;
}
}
