package com.kp.cms.transactionsimpl.reports;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;

import com.kp.cms.bo.admin.OfflineDetails;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.transactions.reports.IOfflineIssueTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class OfflineIssueTransactionImpl implements IOfflineIssueTransaction {
	
	private static final Log log = LogFactory.getLog(OfflineIssueTransactionImpl.class);


	public List<OfflineDetails> getAllOfflineDetails(String SearchCriteria) throws Exception {
		Session session = null;
		List<OfflineDetails> selectedCandidatesList = null;
			try {
				/*SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
				session = sessionFactory.openSession();*/
				session = HibernateUtil.getSession();
				Query selectedCandidatesQuery=session.createQuery(SearchCriteria);
				selectedCandidatesList = selectedCandidatesQuery.list();
			} catch (Exception e) {
				log.error("Error while retrieving selected candidates.." +e);
				throw  new ApplicationException(e);
			} finally {
				if (session != null) {
					session.flush();
					//session.close();
				}
			}
			return selectedCandidatesList;
	}
}
