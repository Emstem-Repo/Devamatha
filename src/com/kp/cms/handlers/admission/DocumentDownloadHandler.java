package com.kp.cms.handlers.admission;

import com.kp.cms.bo.admin.ApplnDoc;
import com.kp.cms.transactionsimpl.admission.DocumentDownloadTransactionImpl;

public class DocumentDownloadHandler {
	private static volatile DocumentDownloadHandler documentDownloadHandler = null;

	private DocumentDownloadHandler() {
	}

	public static DocumentDownloadHandler getInstance() {
		if (documentDownloadHandler == null) {
			documentDownloadHandler = new DocumentDownloadHandler();
		}
		return documentDownloadHandler;
	}

	/**
	 * @param documentId
	 * @return applnDoc
	 */
	public ApplnDoc getDocument(int documentId) throws Exception{
		DocumentDownloadTransactionImpl transactionImpl = new DocumentDownloadTransactionImpl();	
		
		return transactionImpl.getDocument(documentId);
	}
}