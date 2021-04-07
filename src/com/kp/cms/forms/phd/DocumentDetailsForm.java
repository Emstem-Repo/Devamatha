package com.kp.cms.forms.phd;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.phd.DocumentDetailsTO;

public class DocumentDetailsForm extends BaseActionForm {
	
	private int id;
	private String documentName;
	private String submissionOrder;
	private String origDocumentName;
	private String origSubmissionOrder;
	private String guideFees;
	
	public List<DocumentDetailsTO> documentDetailsTOList;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDocumentName() {
		return documentName;
	}
	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}
	
	
	public String getSubmissionOrder() {
		return submissionOrder;
	}
	public void setSubmissionOrder(String submissionOrder) {
		this.submissionOrder = submissionOrder;
	}
	public String getOrigDocumentName() {
		return origDocumentName;
	}
	public void setOrigDocumentName(String origDocumentName) {
		this.origDocumentName = origDocumentName;
	}
	public String getOrigSubmissionOrder() {
		return origSubmissionOrder;
	}
	public void setOrigSubmissionOrder(String origSubmissionOrder) {
		this.origSubmissionOrder = origSubmissionOrder;
	}
	
	
	
	public List<DocumentDetailsTO> getDocumentDetailsTOList() {
		return documentDetailsTOList;
	}
	public void setDocumentDetailsTOList(
			List<DocumentDetailsTO> documentDetailsTOList) {
		this.documentDetailsTOList = documentDetailsTOList;
	}
	public void reset()
	{
		this.documentName=null;
		this.submissionOrder=null;
		this.guideFees=null;
		this.id=0;
		
	}
	
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter(CMSConstants.FORMNAME);
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	
	}
	public String getGuideFees() {
		return guideFees;
	}
	public void setGuideFees(String guideFees) {
		this.guideFees = guideFees;
	}
	

}
