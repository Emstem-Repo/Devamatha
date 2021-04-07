package com.kp.cms.transactionsimpl.admission;

import org.hibernate.Session;

import com.kp.cms.bo.admin.ApplnDoc;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.transactions.admission.IDocumentDownloadTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class DocumentDownloadTransactionImpl implements
		IDocumentDownloadTransaction {

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.IDocumentDownloadTransaction#getDocument(int)
	 */
	@Override
	public ApplnDoc getDocument(int documentId) throws Exception {
		Session session = null;
		ApplnDoc applnDoc = null;
		
		try {
//			SessionFactory sessionFactory = InitSessionFactory.getInstance();
//			session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			applnDoc = (ApplnDoc) session.createQuery(
					"from ApplnDoc where id = :documentId").setInteger("documentId", documentId)
					.uniqueResult();
		} catch (Exception e) {
			throw  new ApplicationException(e);
		} finally{	
			if (session != null) {
				session.flush();
//				session.close();
			}
		}
		return applnDoc;
	}
}
