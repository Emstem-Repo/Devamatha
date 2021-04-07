package com.kp.cms.transactions.admission;

import com.kp.cms.bo.admin.ApplnDoc;

/**
 * Interface for DocumentDownloadTransactionImpl
 */
public interface IDocumentDownloadTransaction {
	
	public ApplnDoc getDocument(int documentId) throws Exception;
}
