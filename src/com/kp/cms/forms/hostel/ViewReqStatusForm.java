package com.kp.cms.forms.hostel;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.hostel.VReqStatusTo;
import com.kp.cms.to.hostel.VRequisitionsTO;

public class ViewReqStatusForm extends BaseActionForm {

	/**
	 * 
	 */  
	private List<VReqStatusTo> vReqStatusTo ;
	private static final long serialVersionUID = 1L;
	private String requisitionno;
	public String getRequisitionno() {
		return requisitionno;
	}
	public List<VReqStatusTo> getVReqStatusTo() {
		return vReqStatusTo;
	}
	public void setVReqStatusTo(List<VReqStatusTo> reqStatusTo) {
		vReqStatusTo = reqStatusTo;
	}
	public void setRequisitionno(String requisitionno) {
		this.requisitionno = requisitionno;
	}
	public void resetFields(ActionMapping mapping, HttpServletRequest request) {
		
		this.requisitionno=null;
	}

	
	
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		return actionErrors;
	}
}
