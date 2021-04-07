package com.kp.cms.forms.admission;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admission.ReceivedThroughTo;

public class ReceivedThroughForm extends BaseActionForm{
     private int id;
     private String receivedThrough;
     private String slipRequired;
     private String origReceivedThrough;
     private String origSlipRequired;
     private List<ReceivedThroughTo> receivedThroughList;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getReceivedThrough() {
		return receivedThrough;
	}
	public void setReceivedThrough(String receivedThrough) {
		this.receivedThrough = receivedThrough;
	}
	public String getSlipRequired() {
		return slipRequired;
	}
	public void setSlipRequired(String slipRequired) {
		this.slipRequired = slipRequired;
	}
    public void reset(){
    	this.id = 0;
    	this.receivedThrough=null;
    	this.slipRequired =null;
    }
    public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter(CMSConstants.FORMNAME);
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}
	public String getOrigReceivedThrough() {
		return origReceivedThrough;
	}
	public void setOrigReceivedThrough(String origReceivedThrough) {
		this.origReceivedThrough = origReceivedThrough;
	}
	public String getOrigSlipRequired() {
		return origSlipRequired;
	}
	public void setOrigSlipRequired(String origSlipRequired) {
		this.origSlipRequired = origSlipRequired;
	}
	public List<ReceivedThroughTo> getReceivedThroughList() {
		return receivedThroughList;
	}
	public void setReceivedThroughList(List<ReceivedThroughTo> receivedThroughList) {
		this.receivedThroughList = receivedThroughList;
	}
}
