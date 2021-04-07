package com.kp.cms.forms.inventory;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.bo.admin.InvTx;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.inventory.IssueMaterialTO;
import com.kp.cms.to.inventory.ItemTO;

public class IssueMaterialForm extends BaseActionForm{
	
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private String method;
private String requisitionNo;
private IssueMaterialTO materialTO;
private List<ItemTO> itemList;
private List<InvTx> invTxList;
private String department;

public String getDepartment() {
	return department;
}

public void setDepartment(String department) {
	this.department = department;
}

public List<InvTx> getInvTxList() {
	return invTxList;
}

public void setInvTxList(List<InvTx> invTxList) {
	this.invTxList = invTxList;
}

public String getMethod() {
	return method;
}

public void setMethod(String method) {
	this.method = method;
}

public String getRequisitionNo() {
	return requisitionNo;
}

public void setRequisitionNo(String requisitionNo) {
	this.requisitionNo = requisitionNo;
}
public IssueMaterialTO getMaterialTO() {
	return materialTO;
}

public List<ItemTO> getItemList() {
	return itemList;
}

public void setMaterialTO(IssueMaterialTO materialTO) {
	this.materialTO = materialTO;
}

public void setItemList(List<ItemTO> itemList) {
	this.itemList = itemList;
}

public ActionErrors validate(ActionMapping mapping,
		HttpServletRequest request) {
	String formName = request.getParameter(CMSConstants.FORMNAME);
	ActionErrors actionErrors = super.validate(mapping, request, formName);

	return actionErrors;
}
public void clear(){
	this.requisitionNo = null;
	this.materialTO = null;
	this.itemList=null;
	this.invTxList = null;
}
}
