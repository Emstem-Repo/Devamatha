package com.kp.cms.forms.fee;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.fee.FeeGroupTO;


public class FeeGroupEntryForm extends BaseActionForm{
	
	private String feeGroupName;
	
	private String feeGroupId;
	
	private String feeGroupNameOriginal;
	
	private String method;	
	
	private String optional;
	
	private String originalOptional;
	
	private List<FeeGroupTO> feeGroupToList;	
	
	public String getFeeGroupName() {
		return feeGroupName;
	}

	public void setFeeGroupName(String feeGroupName) {
		this.feeGroupName = feeGroupName;
	}

	public String getFeeGroupId() {
		return feeGroupId;
	}

	public void setFeeGroupId(String feeGroupId) {
		this.feeGroupId = feeGroupId;
	}

	public String getFeeGroupNameOriginal() {
		return feeGroupNameOriginal;
	}

	public void setFeeGroupNameOriginal(String feeGroupNameOriginal) {
		this.feeGroupNameOriginal = feeGroupNameOriginal;
	}

	public List<FeeGroupTO> getFeeGroupToList() {
		return feeGroupToList;
	}

	public void setFeeGroupToList(List<FeeGroupTO> feeGroupToList) {
		this.feeGroupToList = feeGroupToList;
	}
	
	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}
	
	public String getOptional() {
		return optional;
	}

	public void setOptional(String optional) {
		this.optional = optional;
	}

	public String getOriginalOptional() {
		return originalOptional;
	}

	public void setOriginalOptional(String originalOptional) {
		this.originalOptional = originalOptional;
	}

	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = new ActionErrors();
		actionErrors = super.validate(mapping, request, formName);
		
		return actionErrors;
	}
	
	public void clearFields() {
		feeGroupName = null;
		feeGroupId = null;
		feeGroupNameOriginal = null;
		optional = null;
	}



}
