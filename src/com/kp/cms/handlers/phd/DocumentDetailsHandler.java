package com.kp.cms.handlers.phd;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionErrors;

import com.kp.cms.bo.phd.DocumentDetailsBO;
import com.kp.cms.forms.phd.DocumentDetailsForm;
import com.kp.cms.helpers.phd.DocumentDetailsHelper;
import com.kp.cms.to.phd.DocumentDetailsTO;
import com.kp.cms.transactions.phd.IDocumentDetails;
import com.kp.cms.transactionsimpl.phd.DocumentDetailsTransactionImpl;
 
public class DocumentDetailsHandler {
	
public static volatile DocumentDetailsHandler documentDetailsHandler=null;
	
	public static DocumentDetailsHandler getInstance() {
		if (documentDetailsHandler == null) {
			documentDetailsHandler = new DocumentDetailsHandler();
			return documentDetailsHandler;
		}
		return documentDetailsHandler;
	}

	public List<DocumentDetailsTO> getDocumentDetailsList()
	{
		
		IDocumentDetails details=new DocumentDetailsTransactionImpl();
		List<DocumentDetailsBO> documentDetailsBOs=details.getDocumentDetailsList();
		List<DocumentDetailsTO> documentDetailsTO=DocumentDetailsHelper.getInstance().convertBOsToTO(documentDetailsBOs);
		return documentDetailsTO;
		
	}
	
	public boolean addOrUpdateDocument(DocumentDetailsForm form,String mode)
	{
		DocumentDetailsBO detailsBO=DocumentDetailsHelper.getInstance().convertFormTOBO(form,mode);
		IDocumentDetails transaction=new DocumentDetailsTransactionImpl();
		boolean flag=transaction.addOrUpdateDocument(detailsBO,mode);
		return flag;
		
	}
	
	public boolean duplicateCheck(DocumentDetailsForm form,ActionErrors errors,HttpSession session)
	{
		IDocumentDetails transaction=new DocumentDetailsTransactionImpl();
		boolean duplicate=transaction.duplicateCheck(form, errors, session);
		return duplicate;
		
	}
	
	public boolean reactivateDocument(DocumentDetailsForm detailsForm, String userId)
	{
		IDocumentDetails transaction=new DocumentDetailsTransactionImpl();
		return transaction.reactivateDocument(detailsForm);
	
		
	}
	
	public void editDocument(DocumentDetailsForm detailsForm)
	{
		IDocumentDetails transaction=new DocumentDetailsTransactionImpl();
		DocumentDetailsBO detailsBO=transaction.getDocumentById(detailsForm.getId());
		DocumentDetailsHelper.getInstance().setBoToForm(detailsForm,detailsBO);
	}
	
	public boolean deleteDocument(DocumentDetailsForm detailsForm){
		IDocumentDetails transaction=new DocumentDetailsTransactionImpl();
			boolean isDeleted=transaction.deleteDocument(detailsForm.getId());
			return isDeleted;
		}
}
