
package com.kp.cms.handlers.admin;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.DocType;
import com.kp.cms.bo.admin.DocTypeExams;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.admin.DocumentTypeForm;
import com.kp.cms.helpers.admin.DocumentTypeHelper;
import com.kp.cms.to.admin.DocTypeTO;
import com.kp.cms.transactions.admin.IDocumentTypeTransaction;
import com.kp.cms.transactionsimpl.admin.DocumentTypeTransactionImpl;

/**
 * 
 * @author
 *
 */
public class DocumentTypeHandler {
	private static final Log log = LogFactory.getLog(DocumentTypeHandler.class);
	public static volatile DocumentTypeHandler documentTypeHandler = null;

	public static DocumentTypeHandler getInstance() {
		if (documentTypeHandler == null) {
			documentTypeHandler = new DocumentTypeHandler();
			return documentTypeHandler;
		}
		return documentTypeHandler;
	}
	/**
	 * 
	 * @return list of docTypeTO objects, will be used in UI to dispaly.
	 * @throws Exception
	 */
	public List<DocTypeTO> getDocTypes() throws Exception {
		log.debug("inside getDocTypes");
		IDocumentTypeTransaction iDocumentTypeTransaction = DocumentTypeTransactionImpl.getInstance();
		List<DocType> doctypeList = iDocumentTypeTransaction.getDocTypes(); 
		List<DocTypeTO> docTypeTOList = DocumentTypeHelper.getInstance().copydocTypeBosToTos(doctypeList);
		log.debug("leaving getDocTypes");
		return docTypeTOList;
	}
	  
	/**
	 * this will add a new document type
	 * @param documentTypeForm
	 * @return
	 * @throws Exception
	 */
	public boolean addDocumnetType(DocumentTypeForm documentTypeForm)	throws Exception {
		log.debug("inside addDocumnetType");
		IDocumentTypeTransaction iDocumentTypeTransaction = DocumentTypeTransactionImpl.getInstance();
		boolean isAdded = false;
		
		DocType dupldocType = DocumentTypeHelper.getInstance().copyDataFromFormToBO(documentTypeForm);

		dupldocType = iDocumentTypeTransaction.isDocTypeDuplcated(dupldocType);
		if (dupldocType != null && dupldocType.getIsActive()) {
			throw new DuplicateException();
		}
		else if(dupldocType != null && !dupldocType.getIsActive()){
			documentTypeForm.setDuplId(dupldocType.getId());
			throw new ReActivateException();
		}
			
		
		DocType duplShortName = DocumentTypeHelper.getInstance().copyDataFromFormToBO(documentTypeForm); 
		duplShortName = iDocumentTypeTransaction.isDocShortNameDuplcated(duplShortName);
		
		if (duplShortName != null) {
			throw new DuplicateException();
		}

		DocType duplDiplayOrde = DocumentTypeHelper.getInstance().copyDataFromFormToBO(documentTypeForm); 
		duplDiplayOrde = iDocumentTypeTransaction.isDisplayOrderDuplcated(duplDiplayOrde);
		if (duplDiplayOrde != null) {
			throw new DuplicateException();
		}
		 DocType docType = DocumentTypeHelper.getInstance().copyDataFromFormToBO(documentTypeForm);
		isAdded = iDocumentTypeTransaction.addDocType(docType);
		log.debug("leaving addDocumnetType");
		return isAdded;
	}
	
	/**
	 * method for updating doc type. creating documenttype BO from form
	 * @param 
	 * @return boolean true / false based on result.
	 * @throws Exception
	 */
	public boolean updateDocType(DocumentTypeForm documentTypeForm) throws Exception {
		log.debug("inside updateDocType");
		IDocumentTypeTransaction iDocumentTypeTransaction = DocumentTypeTransactionImpl.getInstance(); 
		boolean isUpdate = false;

		DocType dupldocType = DocumentTypeHelper.getInstance().copyDataFromFormToBO(documentTypeForm);

		dupldocType = iDocumentTypeTransaction.isDocTypeDuplcated(dupldocType);
		if (dupldocType != null && dupldocType.getIsActive()) {
			throw new DuplicateException();
		}
		else if(dupldocType != null && !dupldocType.getIsActive()){
			documentTypeForm.setDuplId(dupldocType.getId());
			throw new ReActivateException();
		}
		
		DocType duplShortName = DocumentTypeHelper.getInstance().copyDataFromFormToBO(documentTypeForm); 
		duplShortName = iDocumentTypeTransaction.isDocShortNameDuplcated(duplShortName);
		
		if (duplShortName != null ) {
			throw new DuplicateException();
		}
		DocType duplDiplayOrde = DocumentTypeHelper.getInstance().copyDataFromFormToBO(documentTypeForm); 
		duplDiplayOrde = iDocumentTypeTransaction.isDisplayOrderDuplcated(duplDiplayOrde);
		if (duplDiplayOrde != null) {
			throw new DuplicateException();
		}
						
		 DocType docType = DocumentTypeHelper.getInstance().copyDataFromFormToBO(documentTypeForm);
		
		
		isUpdate = iDocumentTypeTransaction.updateDocType(docType) ;
		log.debug("leaving updateDocType");
		return isUpdate;
	}

	/**
	 * method is used to delete doc type from table
	 * @param 
	 * @return boolean true / false based on result.
	 * @throws Exception 
	 */
	public boolean deleteDocType(int docId, Boolean activate, DocumentTypeForm documentTypeForm)throws Exception {
		log.debug("inside deleteDocType");
		IDocumentTypeTransaction iDocumentTypeTransaction = DocumentTypeTransactionImpl.getInstance();
		boolean result = iDocumentTypeTransaction.deleteDocType(docId, activate, documentTypeForm);
		log.debug("leaving deleteDocType");
		return result;
	}
	
	
}
