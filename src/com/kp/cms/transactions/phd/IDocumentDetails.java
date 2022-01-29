package com.kp.cms.transactions.phd;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionErrors;

import com.kp.cms.bo.phd.DocumentDetailsBO;
import com.kp.cms.forms.phd.DocumentDetailsForm;

public interface IDocumentDetails {
	public List<DocumentDetailsBO> getDocumentDetailsList();
	public boolean addOrUpdateDocument(DocumentDetailsBO detailsBO,String mode);
	public boolean duplicateCheck(DocumentDetailsForm detailsForm, ActionErrors errors, HttpSession session);
	public boolean reactivateDocument(DocumentDetailsForm detailsForm);
	public DocumentDetailsBO getDocumentById(int id);
	public boolean deleteDocument(int id);

}
