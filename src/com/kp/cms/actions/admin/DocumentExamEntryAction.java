package com.kp.cms.actions.admin;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import com.kp.cms.bo.admin.DocTypeExams;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.admin.DocumentExamEntryForm;
import com.kp.cms.handlers.admin.DocumentExamEntryHandler;
import com.kp.cms.handlers.admin.DocumentTypeHandler;
import com.kp.cms.to.admin.DocTypeExamsTO;
import com.kp.cms.to.admin.DocTypeTO;

public class DocumentExamEntryAction extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(DocumentExamEntryAction.class);
	
	
	/**
	 * setting the required data to the request
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initGetDocumentExams(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DocumentExamEntryForm documentExamEntryForm=(DocumentExamEntryForm)form;
		//It use for Help,Don't Remove
		HttpSession session=request.getSession();
		session.setAttribute("field","Document Entry");
		log.info("Entering into initGetDocumentExams in DocumentExamEntryAction");
		try{
			documentExamEntryForm.reset();
			setRequestedDataToForm(documentExamEntryForm);
		}catch (Exception e) {
			e.printStackTrace();
			log.error("Error occured in DocumentExamEntryAction", e);
			String msg = super.handleApplicationException(e);
			documentExamEntryForm.setErrorMessage(msg);
			documentExamEntryForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
			
		}
		log.info("Exit from initGetDocumentExams in DocumentExamEntryAction");
		return mapping.findForward(CMSConstants.INIT_GET_DOCUMENT_EXAM);
	}


	/**
	 * getting the list of document type and setting into form
	 * @param documentExamEntryForm
	 */
	private void setRequestedDataToForm(
			DocumentExamEntryForm documentExamEntryForm) throws Exception{
		//getting the docType list which are Active
		List<DocTypeTO> docTypeList = DocumentTypeHandler.getInstance().getDocTypes();
		documentExamEntryForm.setDocList(docTypeList);
		//setting the list of Document Exam to form for display
		List<DocTypeExamsTO> docTypeExamList=DocumentExamEntryHandler.getInstance().getDocTypeExams();
		documentExamEntryForm.setDocTypeExamsList(docTypeExamList);
	}
	

	/**
	 * adding the docExam to database
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addDocExam(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering into addDocExam in DocumentExamEntryAction");
		DocumentExamEntryForm documentExamEntryForm=(DocumentExamEntryForm)form;
		
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = documentExamEntryForm.validate(mapping, request);

		boolean isDocExamAdded = false;
		if (errors.isEmpty()) {
			try{
				int docId=Integer.parseInt(documentExamEntryForm.getDocTypeId());
				String examName = documentExamEntryForm.getExamName().trim();
				DocTypeExams type=DocumentExamEntryHandler.getInstance().checkDuplicate(docId,examName);
				if(type!=null){
					if(type.getIsActive()){
						errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.DOC_TYPE_EXAM_DUPLICATE));
						setRequestedDataToForm(documentExamEntryForm);
						addErrors(request, errors);
					}
					else if(!type.getIsActive()){
						errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.DOC_TYPE_EXAM_REACTIVATE));
						setRequestedDataToForm(documentExamEntryForm);
						addErrors(request, errors);
					}
				}
				else{
					setUserId(request, documentExamEntryForm);
					isDocExamAdded = DocumentExamEntryHandler.getInstance().addDocExam(documentExamEntryForm,request);
					if(isDocExamAdded){
						messages.add(CMSConstants.MESSAGES,new ActionMessage(CMSConstants.DOC_TYPE_EXAM_ADD_SUCCESS));
						documentExamEntryForm.reset();
						setRequestedDataToForm(documentExamEntryForm);
						saveMessages(request, messages);
					}
					else{
						setRequestedDataToForm(documentExamEntryForm);
						errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.DOC_TYPE_EXAM_ADD_FAILED));
						addErrors(request, errors);
					}
				}
			}catch (Exception e) {
				log.error("Error occured in caste Entry Action", e);
				String msg = super.handleApplicationException(e);
				documentExamEntryForm.setErrorMessage(msg);
				documentExamEntryForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
				
			}
		} else {
			saveErrors(request, errors);
			setRequestedDataToForm(documentExamEntryForm);
		}
		log.info("Exit from addDocExam in DocumentExamEntryAction");
		return mapping.findForward(CMSConstants.INIT_GET_DOCUMENT_EXAM); 
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteDocExamType(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering into  deleteDocExamType action");
		DocumentExamEntryForm documentExamEntryForm=(DocumentExamEntryForm)form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		try {
			setUserId(request, documentExamEntryForm);
			boolean isDeleted=false;
			int docTypeExamId = Integer.parseInt(documentExamEntryForm.getDocTypeExamId());
			String userId=documentExamEntryForm.getUserId();
			//Request the handler to delete the selected roomtype
			isDeleted = DocumentExamEntryHandler.getInstance().deleteDocExamType(docTypeExamId, userId);
			//If success then append the success message
			if (isDeleted) {
				messages.add(CMSConstants.MESSAGES,new ActionMessage(CMSConstants.DOC_TYPE_EXAM_DELETE_SUCCESS));
				setRequestedDataToForm(documentExamEntryForm);
				saveMessages(request, messages);
			}
			//Else add the error message
			else {
				setRequestedDataToForm(documentExamEntryForm);
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.DOC_TYPE_EXAM_DELETE_FAILED));
				addErrors(request, errors);
			}
			documentExamEntryForm.reset();
		} catch (Exception e) {
			log.error("Error occured in deleteDocExamType of DocumentExamEntryAction", e);
			String msg = super.handleApplicationException(e);
			documentExamEntryForm.setErrorMessage(msg);
			documentExamEntryForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("exit from  deleteDocExamType action");
		return mapping.findForward(CMSConstants.INIT_GET_DOCUMENT_EXAM); 	
	}
	/**
	 * Used when edit button is clicked
	 */

	public ActionForward editDocExamEntry(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering into editRoomType of RoomType Action");
		DocumentExamEntryForm documentExamEntryForm=(DocumentExamEntryForm)form;
		try {
			DocumentExamEntryHandler.getInstance().getDetailByDocExamId(documentExamEntryForm, request);
			setRequestedDataToForm(documentExamEntryForm);
			request.setAttribute(CMSConstants.OPERATION, CMSConstants.EDIT_OPERATION);
		} catch (Exception e) {
			log.error("Error occured in editRoomType of RoomTypeAction", e);
			String msg = super.handleApplicationException(e);
			documentExamEntryForm.setErrorMessage(msg);
			documentExamEntryForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Leaving into editRoomType of RoomType Action");
		return mapping.findForward(CMSConstants.INIT_GET_DOCUMENT_EXAM); 	
	}
	
	/**
	 * updating the docTypeExam
	 */
	public ActionForward updateDocTypeExam(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering into updateDocTypeExam of DocumentExamEntry Action");
		DocumentExamEntryForm documentExamEntryForm=(DocumentExamEntryForm)form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = documentExamEntryForm.validate(mapping, request);
		try {			
			//This condition works when reset button will click in update mode
			if(isCancelled(request)){
				DocumentExamEntryHandler.getInstance().getDetailByDocExamId(documentExamEntryForm, request);
				setRequestedDataToForm(documentExamEntryForm);
				request.setAttribute(CMSConstants.OPERATION, CMSConstants.EDIT_OPERATION);
			}
			// This condition when when there will be no validation error occurs
			else if(errors.isEmpty()) {
				setUserId(request, documentExamEntryForm);
				boolean isUpdated;
				int olddocTypeId =Integer.parseInt(documentExamEntryForm.getOlddocTypeId());
				String oldExamName = documentExamEntryForm.getOldExamName().trim();
				//This condition works when the user keeps as the same hostel and roomtype as it was saved
				if(olddocTypeId == Integer.parseInt(documentExamEntryForm.getDocTypeId().trim()) && oldExamName.equalsIgnoreCase(documentExamEntryForm.getExamName().trim())){
					isUpdated = DocumentExamEntryHandler.getInstance().updateDocExamType(documentExamEntryForm);
					//If update is success
					if(isUpdated){
						messages.add(CMSConstants.MESSAGES,new ActionMessage(CMSConstants.DOC_TYPE_EXAM_UPDATE_SUCCESS));
						setRequestedDataToForm(documentExamEntryForm);
						documentExamEntryForm.reset();
						saveMessages(request, messages);
						return mapping.findForward(CMSConstants.INIT_GET_DOCUMENT_EXAM); 
					}
					//If update is failure
					else{
						setRequestedDataToForm(documentExamEntryForm);
						errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.DOC_TYPE_EXAM__UPDATE_FAILED));
						addErrors(request, errors);
						return mapping.findForward(CMSConstants.INIT_GET_DOCUMENT_EXAM); 
					}
				}
				//This condition works when if the user changed hostel or roomtype or both
				else if(olddocTypeId != Integer.parseInt(documentExamEntryForm.getDocTypeId().trim()) || !oldExamName.equalsIgnoreCase(documentExamEntryForm.getExamName().trim())){
					int docId=Integer.parseInt(documentExamEntryForm.getDocTypeId());
					String examName = documentExamEntryForm.getExamName().trim();			
					DocTypeExams type=DocumentExamEntryHandler.getInstance().checkDuplicate(docId,examName);
					if(type!=null){
						if(type.getIsActive()){
							errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.DOC_TYPE_EXAM_DUPLICATE));
							setRequestedDataToForm(documentExamEntryForm);
							addErrors(request, errors);
							request.setAttribute(CMSConstants.OPERATION, CMSConstants.EDIT_OPERATION);
							return mapping.findForward(CMSConstants.INIT_GET_DOCUMENT_EXAM); 
						}
						else if(!type.getIsActive()){
							errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.DOC_TYPE_EXAM_REACTIVATE));
							setRequestedDataToForm(documentExamEntryForm);
							addErrors(request, errors);
							request.setAttribute(CMSConstants.OPERATION, CMSConstants.EDIT_OPERATION);
							return mapping.findForward(CMSConstants.INIT_GET_DOCUMENT_EXAM); 
						}
					}
					else{
						//Request the handler in order to update the roomtype
						isUpdated = DocumentExamEntryHandler.getInstance().updateDocExamType(documentExamEntryForm);
						//If update is success
						if(isUpdated){
							messages.add(CMSConstants.MESSAGES,new ActionMessage(CMSConstants.DOC_TYPE_EXAM_UPDATE_SUCCESS));
							setRequestedDataToForm(documentExamEntryForm);
							documentExamEntryForm.reset();
							saveMessages(request, messages);
							return mapping.findForward(CMSConstants.INIT_GET_DOCUMENT_EXAM); 
						}
						//If update is failure
						else{
							setRequestedDataToForm(documentExamEntryForm);
							errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.DOC_TYPE_EXAM__UPDATE_FAILED));
							addErrors(request, errors);
							return mapping.findForward(CMSConstants.INIT_GET_DOCUMENT_EXAM); 
						}
					}
				}
				}
			//This else part will work when validation error occurs. Only gets the data and save the errors
			else{
				setRequestedDataToForm(documentExamEntryForm);
				saveMessages(request, messages);
				request.setAttribute(CMSConstants.OPERATION, CMSConstants.EDIT_OPERATION);
				addErrors(request, errors);
			}
		} catch (Exception e) {
			log.error("Error occured in editRoomType of RoomTypeAction", e);
			String msg = super.handleApplicationException(e);
			documentExamEntryForm.setErrorMessage(msg);
			documentExamEntryForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}	
		log.info("exit from updateDocTypeexam  deleteDocExamType action");
		return mapping.findForward(CMSConstants.INIT_GET_DOCUMENT_EXAM); 
	}
	/**
	 * Used to reactivate roomtype
	 */
	public ActionForward reactivateDocExamType(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering into reactivateRoomType of RoomType Action");
		DocumentExamEntryForm documentExamEntryForm=(DocumentExamEntryForm)form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		try {
			setUserId(request, documentExamEntryForm);
			boolean isReactivate;
			int olddocTypeId =Integer.parseInt(documentExamEntryForm.getDocTypeId());
			String oldExamName = documentExamEntryForm.getExamName().trim();
			String userId = documentExamEntryForm.getUserId();
			isReactivate = DocumentExamEntryHandler.getInstance().reactivateDocExamType(olddocTypeId,oldExamName,userId);
			if(isReactivate){
				messages.add(CMSConstants.MESSAGES,new ActionMessage(CMSConstants.DOC_TYPE_EXAM_REACTIVATE_SUCCESS));
				setRequestedDataToForm(documentExamEntryForm);
				documentExamEntryForm.reset();
				saveMessages(request, messages);
			}
			else{
				setRequestedDataToForm(documentExamEntryForm);
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.DOC_TYPE_EXAM_REACTIVATE_FAILURE));
				addErrors(request, errors);
			}
			
		} catch (Exception e) {
			log.error("Error occured in reactivateRoomType of RoomTypeAction", e);
			String msg = super.handleApplicationException(e);
			documentExamEntryForm.setErrorMessage(msg);
			documentExamEntryForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Leaving into reactivateRoomType of RoomType Action");
		return mapping.findForward(CMSConstants.INIT_GET_DOCUMENT_EXAM); 
	}
}
