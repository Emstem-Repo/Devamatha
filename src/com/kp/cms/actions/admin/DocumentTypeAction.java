package com.kp.cms.actions.admin;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.admin.DocumentTypeForm;
import com.kp.cms.handlers.admin.DocumentTypeHandler;
import com.kp.cms.to.admin.DocTypeTO;

@SuppressWarnings("deprecation")
public class DocumentTypeAction extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(DocumentTypeAction.class);

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return forward to docTypeEntry
	 * @throws Exception
	 */
	public ActionForward initDocumentType(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
									HttpServletResponse response) throws Exception {

		log.debug("Entering initDocumentType");
		DocumentTypeForm documentTypeForm = (DocumentTypeForm) form;
		try {
			String formName = mapping.getName();
			request.getSession().setAttribute(CMSConstants.FORMNAME, formName);
			documentTypeForm.reset(mapping, request);
			setDocumentTypeListToRequest(request);  //documentTypeList to request to display in the UI
			setUserId(request, documentTypeForm); //setting userId for updating last changed details
			
		} catch (Exception e) {
			log.error("error in initDocumentType method", e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				documentTypeForm.setErrorMessage(msg);
				documentTypeForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}

		log.debug("Leaving initDocumentType ");

		return mapping.findForward(CMSConstants.DOC_TYPE_ENTRY);
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 *            This will add new doc type record
	 * @return to mapping docTypeEntry
	 * @throws Exception
	 */
	public ActionForward addDocType(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
									HttpServletResponse response) throws Exception {

		log.debug("inside addDocType Action");
		DocumentTypeForm documentTypeForm = (DocumentTypeForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = documentTypeForm.validate(mapping, request);
		boolean isAdded;
		try {
			if (errors != null && !errors.isEmpty()) {
				saveErrors(request, errors);
				setDocumentTypeListToRequest(request);
				//if space is entered then it should not allow in the table
				if(documentTypeForm.getName().trim().isEmpty()){
					documentTypeForm.setName(null);
				}
				if(documentTypeForm.getDocShortName().trim().isEmpty()){
					documentTypeForm.setDocShortName(null);
				}
				
				return mapping.findForward(CMSConstants.DOC_TYPE_ENTRY);
			}
			isAdded = DocumentTypeHandler.getInstance().addDocumnetType(documentTypeForm);
			setDocumentTypeListToRequest(request);
		} catch (DuplicateException e1) {
			errors.add("error", new ActionError("knowledgepro.admin.document.exists"));
			saveErrors(request, errors);
			setDocumentTypeListToRequest(request);
			return mapping.findForward(CMSConstants.DOC_TYPE_ENTRY);
		} catch (ReActivateException e1) {
			errors.add("error", new ActionError(CMSConstants.DOC_TYPE_REACTIVATE));
			saveErrors(request, errors);
			setDocumentTypeListToRequest(request);
			return mapping.findForward(CMSConstants.DOC_TYPE_ENTRY);
		} catch (Exception e) {
			log.error("error in final submit addDocType...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				documentTypeForm.setErrorMessage(msg);
				documentTypeForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		if (isAdded) {
			// success .
			ActionMessage message = new ActionMessage("knowledgepro.admin.DocType.addsuccess");
			messages.add("messages", message);
			saveMessages(request, messages);
			documentTypeForm.reset(mapping, request);
		}
		else
		{
			// failed
			errors.add("error", new ActionError("knowledgepro.admin.DocType.addfailure"));
			saveErrors(request, errors);
		}
		log.debug("Leaving addDocType Action");
		return mapping.findForward(CMSConstants.DOC_TYPE_ENTRY);
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward This action method will called when particular
	 *         document type need to be deleted based on the id.
	 * @throws Exception
	 */
	
	public ActionForward deleteDocType(ActionMapping mapping, ActionForm form,HttpServletRequest request, 
									HttpServletResponse response) throws Exception {

		log.debug("inside delete deleteDocType: Action");
		DocumentTypeForm documentTypeForm = (DocumentTypeForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = new ActionErrors();
		boolean isDeleted = false;
		try {
			if (documentTypeForm.getId() != 0) {
				int docTypeid = documentTypeForm.getId();
				isDeleted = DocumentTypeHandler.getInstance().deleteDocType(docTypeid, false, documentTypeForm); 
			}
			setDocumentTypeListToRequest(request);
		} catch (Exception e) {
    		errors.add("error", new ActionError("knowledgepro.admin.document.deletefailure"));
    		saveErrors(request,errors);
		}
		setDocumentTypeListToRequest(request);

		if (isDeleted) {
			// success deleted
			ActionMessage message = new ActionMessage("knowledgepro.admin.document.deletesuccess");
			messages.add("messages", message);
			saveMessages(request, messages);
			documentTypeForm.reset(mapping, request);
		} 
		log.debug("inside delete deleteDocType Action");
		return mapping.findForward(CMSConstants.DOC_TYPE_ENTRY);
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return This action method will called when particular docType need to be
	 *         updated based on the id.
	 * @throws Exception
	 */
	public ActionForward updateDocType(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
										HttpServletResponse response) throws Exception {

		log.debug("inside update updateDocType Action");
		DocumentTypeForm documentTypeForm = (DocumentTypeForm) form;
		ActionMessages messages = new ActionMessages();
		 ActionErrors errors = documentTypeForm.validate(mapping, request);
		boolean isUpdated = false;
		try {
			if (!errors.isEmpty()) {
				saveErrors(request, errors);
				setDocumentTypeListToRequest(request);
				if(documentTypeForm.getName().trim().isEmpty()){
					documentTypeForm.setName(null);
				}
				if(documentTypeForm.getDocShortName().trim().isEmpty()){
					documentTypeForm.setDocShortName(null);
				}
					
				request.setAttribute("docTypeOperation", "edit");  //for identifying add/edit in jsp to check
				return mapping.findForward(CMSConstants.DOC_TYPE_ENTRY);
			}
			if (documentTypeForm.getId() != 0) {
				isUpdated = DocumentTypeHandler.getInstance().updateDocType(documentTypeForm) ;
			}
			setDocumentTypeListToRequest(request);
		} catch (DuplicateException e1) {
			errors.add("error", new ActionError("knowledgepro.admin.document.exists"));
			saveErrors(request, errors);
			setDocumentTypeListToRequest(request);
			request.setAttribute("docTypeOperation", "edit");
			return mapping.findForward(CMSConstants.DOC_TYPE_ENTRY);
		} catch (ReActivateException e1) {
			errors.add("error", new ActionError(CMSConstants.DOC_TYPE_REACTIVATE));
			saveErrors(request, errors);
			setDocumentTypeListToRequest(request);
			return mapping.findForward(CMSConstants.DOC_TYPE_ENTRY);
		} catch (Exception e) {
			log.error("error in updateDocType...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				request.setAttribute("docTypeOperation", "edit");
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				documentTypeForm.setErrorMessage(msg);
				documentTypeForm.setErrorStack(e.getMessage());
				request.setAttribute("docTypeOperation", "edit");
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}

		if (isUpdated) {
			// successfully updated.
			ActionMessage message = new ActionMessage("knowledgepro.admin.documentType.updatesuccess");
			messages.add("messages", message);
			saveMessages(request, messages);
			documentTypeForm.reset(mapping, request);
		} else {
			// failed to update.
			errors.add("error", new ActionError("knowledgepro.admin.document.updatefailure"));
			saveErrors(request, errors);
			request.setAttribute("docTypeOperation", "edit");
			return mapping.findForward(CMSConstants.DOC_TYPE_ENTRY);
		}
		log.debug("leaving update updateDocType action");
		request.setAttribute("docTypeOperation", "add");
		return mapping.findForward(CMSConstants.DOC_TYPE_ENTRY);
	}


	/**
	 * 
	 * @param request
	 *            This method sets the docTypeList to Request used to display
	 *            docTypeList record on UI.
	 * @throws Exception
	 */
	public void setDocumentTypeListToRequest(HttpServletRequest request) throws Exception {
		log.debug("inside setDocumentTypeListToRequest");
		List<DocTypeTO> docTypeList = DocumentTypeHandler.getInstance().getDocTypes(); 
		request.setAttribute("docTypeList", docTypeList);
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward activateDocType(ActionMapping mapping, ActionForm form,
						HttpServletRequest request, HttpServletResponse response) throws Exception {

		log.debug("inside activateDocType in Action");
		DocumentTypeForm docForm = (DocumentTypeForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		boolean isActivated = false;
		try {
			int docId = docForm.getDuplId();
			isActivated = DocumentTypeHandler.getInstance().deleteDocType(docId, true, docForm); 
		} catch (Exception e) {
			errors.add("error", new ActionError(CMSConstants.DOC_TYPE_ACTIVATE_FAILURE));
			saveErrors(request, errors);
		}
		setDocumentTypeListToRequest(request);
		if (isActivated) {
			ActionMessage message = new ActionMessage(CMSConstants.DOC_TYPE_ACTIVATE_SUCCESS);
			messages.add("messages", message);
			saveMessages(request, messages);
		}
		log.debug("exit activateDocType method in Action");
		return mapping.findForward(CMSConstants.DOC_TYPE_ENTRY);
	}

	
}


