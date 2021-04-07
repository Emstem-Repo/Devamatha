package com.kp.cms.handlers.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.DocTypeExams;
import com.kp.cms.forms.admin.DocumentExamEntryForm;
import com.kp.cms.helpers.admin.DocumentExamEntryHelper;
import com.kp.cms.to.admin.DocTypeExamsTO;
import com.kp.cms.transactions.admin.IDocumentExamEntryTransaction;
import com.kp.cms.transactionsimpl.admin.DocumentExamEntryTransactionImpl;

public class DocumentExamEntryHandler {
	/**
	 * Singleton object of DocumentExamEntryHandler
	 */
	private static volatile DocumentExamEntryHandler documentExamEntryHandler = null;
	private static final Log log = LogFactory.getLog(DocumentExamEntryHandler.class);
	private DocumentExamEntryHandler() {
		
	}
	/**
	 * return singleton object of DocumentExamEntryHandler.
	 * @return
	 */
	public static DocumentExamEntryHandler getInstance() {
		if (documentExamEntryHandler == null) {
			documentExamEntryHandler = new DocumentExamEntryHandler();
		}
		return documentExamEntryHandler;
	}
	IDocumentExamEntryTransaction transaction=new DocumentExamEntryTransactionImpl();
	/**
	 * getting the list of doctypeExams from database
	 * @return
	 */
	public List<DocTypeExamsTO> getDocTypeExams() throws Exception{
		List<DocTypeExams> docTypeExamsList=transaction.getDocTypeExams();
		return DocumentExamEntryHelper.getInstance().convertBOListtoTO(docTypeExamsList);
	}
	/**
	 * checking the code is already exist
	 * @throws Exception
	 */
	public DocTypeExams checkDuplicate(int docId, String examName) throws Exception{
		return transaction.checkDuplicate(docId,examName);
	}
	/**
	 * Adding the DocExams to database
	 * @param documentExamEntryForm
	 * @param request
	 * @return
	 */
	public boolean addDocExam(DocumentExamEntryForm documentExamEntryForm,
			HttpServletRequest request) throws Exception{
		DocTypeExams docTypeExams =DocumentExamEntryHelper.getInstance().convertFormtoBO(documentExamEntryForm);
		return transaction.addDocExam(docTypeExams);
	}
	/**
	 * making DocExamType is active is false 
	 * @param docTypeExamId
	 * @param userId
	 * @return
	 */
	public boolean deleteDocExamType(int docTypeExamId, String userId) throws Exception{
		return transaction.deleteDocExamType(docTypeExamId,userId);
	}
	/**
	 * updating docExamType in database
	 * @param documentExamEntryForm
	 * @return
	 */
	public boolean updateDocExamType(DocumentExamEntryForm documentExamEntryForm) throws Exception{
		DocTypeExams docTypeExams=DocumentExamEntryHelper.getInstance().convertFormToDocExamTypeBo(documentExamEntryForm);
		return transaction.updateDocExamType(docTypeExams);
	}
	/**
	reactivate The docExamType 
	 */
	public boolean reactivateDocExamType(int olddocTypeId, String oldExamName,
			String userId) throws Exception{
		return transaction.reactivateDocExamType(olddocTypeId, oldExamName, userId);
	}
	/**
	edit The docExamType 
	 */
	public void getDetailByDocExamId(DocumentExamEntryForm documentExamEntryForm,HttpServletRequest request)throws Exception {
		DocTypeExams doc=transaction.editDocTypeExam(documentExamEntryForm.getDocTypeExamId());
		if(doc!=null){
			DocumentExamEntryHelper.getInstance().setBodataToForm(doc,documentExamEntryForm);
		}
	}
	
}
