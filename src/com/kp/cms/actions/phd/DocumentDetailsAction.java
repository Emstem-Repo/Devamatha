package com.kp.cms.actions.phd;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.phd.DocumentDetailsForm;
import com.kp.cms.handlers.phd.DocumentDetailsHandler;
import com.kp.cms.to.phd.DocumentDetailsTO;

public class DocumentDetailsAction extends BaseDispatchAction {
	 
	public ActionForward initDocumentDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	{
		DocumentDetailsForm detailsForm= (DocumentDetailsForm) form;
        detailsForm.reset();
		setRequestedDataToForm(detailsForm);
		return mapping.findForward(CMSConstants.PHD_DOCUMENT);
		
		
	}
	
	public ActionForward addDocument(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	{
		DocumentDetailsForm detailsForm= (DocumentDetailsForm) form;
		setUserId(request, detailsForm);
		ActionMessages messages=new ActionMessages();
		ActionErrors errors=detailsForm.validate(mapping, request);
		HttpSession session=request.getSession();
		String mode="add";
		if(errors.isEmpty())
		{
			try{
			boolean save=false;
			boolean isDuplicate=DocumentDetailsHandler.getInstance().duplicateCheck(detailsForm,errors,session);
			if(!isDuplicate)
			{
				 save=DocumentDetailsHandler.getInstance().addOrUpdateDocument(detailsForm,mode);
				if(save)
				{
					messages.add(CMSConstants.MESSAGES, new ActionMessage( "knowledgepro.phd.document.addsuccess"));
					saveMessages(request, messages);
					detailsForm.reset();
				}
				else
				{
					errors.add("error", new ActionError( "knowledgepro.phd.document.addfailure"));
					addErrors(request, errors);
					detailsForm.reset();
				}
			}
			else{
				addErrors(request, errors);
			}
				
			}
			catch (Exception exception) {
				String msg = super.handleApplicationException(exception);
				detailsForm.setErrorMessage(msg);
				detailsForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		} else {
			saveErrors(request, errors);
			setRequestedDataToForm(detailsForm);
			return mapping.findForward(CMSConstants.PHD_DOCUMENT);
		}
		saveErrors(request, errors);
		setRequestedDataToForm(detailsForm);
		return mapping.findForward(CMSConstants.PHD_DOCUMENT);
		}
	
	public ActionForward reactivateDocument(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DocumentDetailsForm detailsForm =   (DocumentDetailsForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		HttpSession session=request.getSession();
		try {
			setUserId(request, detailsForm);
			boolean isReactivate;
			//int olddocTypeId =Integer.parseInt(documentExamEntryForm.getDocTypeId());
			//String oldExamName = documentExamEntryForm.getExamName().trim();
			String userId = detailsForm.getUserId();
			String duplicateId=session.getAttribute("ReactivateId").toString();
			detailsForm.setId(Integer.parseInt(duplicateId));
			isReactivate = DocumentDetailsHandler.getInstance().reactivateDocument(detailsForm,userId);
			if(isReactivate){
				messages.add(CMSConstants.MESSAGES,new ActionMessage("knowledgepro.phd.document.details.activate"));
				setRequestedDataToForm(detailsForm);
				detailsForm.reset();
				saveMessages(request, messages);
			}
			else{
				setRequestedDataToForm(detailsForm);
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.phd.document.details.activate.failed"));
				addErrors(request, errors);
			}
			
		} catch (Exception e) {
			String msg = super.handleApplicationException(e);
			detailsForm.setErrorMessage(msg);
			detailsForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.PHD_DOCUMENT); 
	}
	
	public ActionForward editDocument(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	{
		DocumentDetailsForm detailsForm= (DocumentDetailsForm) form;
		try{
		DocumentDetailsHandler.getInstance().editDocument(detailsForm);
		
		request.setAttribute("documentOperation", "edit");
		} catch (Exception e) {
			
			String msg = super.handleApplicationException(e);
			detailsForm.setErrorMessage(msg);
			detailsForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.PHD_DOCUMENT);
		
	}
	
	public ActionForward updateDocument(ActionMapping mapping,ActionForm form, HttpServletRequest request, HttpServletResponse response)
	{
		DocumentDetailsForm detailsForm=  (DocumentDetailsForm) form;
		HttpSession session=request.getSession();
		ActionMessages messages=new ActionMessages();
		ActionErrors errors=detailsForm.validate(mapping, request);
		boolean isUpdated=false;
		String mode="update";
		if(errors.isEmpty())
		{
			try
			{
		 if(isCancelled(request))
		 {
			 detailsForm.reset(mapping, request);
			String formName=mapping.getName();
			request.getSession().setAttribute(CMSConstants.FORMNAME, formName);
			DocumentDetailsHandler.getInstance().editDocument(detailsForm);
			request.setAttribute("documentOperation", "edit");
			return mapping.findForward(CMSConstants.PHD_DOCUMENT);
		 }
		
		setUserId(request, detailsForm);
		
		boolean isDuplicate=DocumentDetailsHandler.getInstance().duplicateCheck(detailsForm, errors, session);
		if(!isDuplicate)
		{
			isUpdated=DocumentDetailsHandler.getInstance().addOrUpdateDocument(detailsForm,mode);
			if (isUpdated) {
				ActionMessage message = new ActionMessage(
						"knowledgepro.phd.document.update.success");
				messages.add("messages", message);
				saveMessages(request, messages);
				//employeeResumeForm.reset(mapping, request);
				detailsForm.reset();
			} else {
				errors.add("error", new ActionError(
						"knowledgepro.phd.document.update.failed"));
				//saveErrors(request, errors);
				addErrors(request, errors);
				detailsForm.reset();
			}
		}
			else{
				request.setAttribute("documentOperation", "edit");
				errors.add("error", new ActionError("knowledgepro.phd.document.duplicate"));
				addErrors(request, errors);
				//payScaleForm.reset();
			}
		} catch (Exception e) {
			String msg = super.handleApplicationException(e);
			detailsForm.setErrorMessage(msg);
			detailsForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}}else{
			saveErrors(request, errors);
			setRequestedDataToForm(detailsForm);
			request.setAttribute("documentOperation", "edit");
			return mapping.findForward(CMSConstants.PHD_DOCUMENT);
		}
		setRequestedDataToForm(detailsForm);
		return mapping.findForward(CMSConstants.PHD_DOCUMENT);

		
		
	}
	
	public ActionForward deleteDocument(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DocumentDetailsForm detailsForm =   (DocumentDetailsForm) form;
		ActionMessages messages = new ActionMessages();

		try {
			boolean isDeleted = DocumentDetailsHandler.getInstance()
					.deleteDocument(detailsForm);
			if (isDeleted) {
				ActionMessage message = new ActionMessage(
						"knowledgepro.phd.document.delete.success");
				messages.add("messages", message);
				saveMessages(request, messages);
			} else {
				ActionMessage message = new ActionMessage(
						"knowledgepro.phd.document.delete.failed");
				messages.add("messages", message);
				saveMessages(request, messages);
			}
			detailsForm.reset();
			setRequestedDataToForm(detailsForm);
		} catch (Exception e) {
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				detailsForm.setErrorMessage(msg);
				detailsForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				String msg = super.handleApplicationException(e);
				detailsForm.setErrorMessage(msg);
				detailsForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		}
		return mapping.findForward(CMSConstants.PHD_DOCUMENT);
	}
	
	public void setRequestedDataToForm(DocumentDetailsForm  documentDetailsForm)
	{
		List<DocumentDetailsTO> documentDetailsTOList=DocumentDetailsHandler.getInstance().getDocumentDetailsList();
		documentDetailsForm.setDocumentDetailsTOList(documentDetailsTOList);
	}

}
