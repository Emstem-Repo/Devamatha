package com.kp.cms.transactionsimpl.admission;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;

import com.kp.cms.bo.admin.Student;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.handlers.reports.ScoreSheetHandler;
import com.kp.cms.transactions.admission.IAssignSecondLanguageTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class AssignSecondLanguageTransactionImpl implements
		IAssignSecondLanguageTransaction {
	private static final Log log = LogFactory.getLog(ScoreSheetHandler.class);
	@Override
	public List<Student> getDataForQuery(String query) throws Exception {
		Session session = null;
		List<Student> selectedCandidatesList = null;
		try {
			/*SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();*/
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
				//session.close();
			}
		}
	}

}
