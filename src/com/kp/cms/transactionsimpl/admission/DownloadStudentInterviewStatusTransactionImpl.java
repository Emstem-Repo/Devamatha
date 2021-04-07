package com.kp.cms.transactionsimpl.admission;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;

import com.kp.cms.bo.admin.GenerateProcess;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.transactions.admission.IDownloadStudentInterviewStatusTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class DownloadStudentInterviewStatusTransactionImpl implements
		IDownloadStudentInterviewStatusTransaction {
	
	/**
	 * Singleton object of DownloadStudentInterviewStatusTransactionImpl
	 */
	private static volatile DownloadStudentInterviewStatusTransactionImpl transactionImpl = null;
	private static final Log log = LogFactory.getLog(DownloadStudentInterviewStatusTransactionImpl.class);
	private DownloadStudentInterviewStatusTransactionImpl() {
		
	}
	/**
	 * return singleton object of DownloadStudentInterviewStatusTransactionImpl.
	 * @return
	 */
	public static DownloadStudentInterviewStatusTransactionImpl getInstance() {
		if (transactionImpl == null) {
			transactionImpl = new DownloadStudentInterviewStatusTransactionImpl();
		}
		return transactionImpl;
	}
	@Override
	public List<GenerateProcess> getStudentList(String query) throws Exception {
		Session session = null;
		List<GenerateProcess> selectedCandidatesList = null;
		try {
			session = HibernateUtil.getSession();
			Query selectedCandidatesQuery=session.createQuery(query);
			selectedCandidatesList = selectedCandidatesQuery.list();
			return selectedCandidatesList;
		} catch (Exception e) {
			log.error("Error while retrieving selected candidates.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		
	}

}
