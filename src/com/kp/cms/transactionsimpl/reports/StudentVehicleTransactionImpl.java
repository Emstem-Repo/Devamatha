package com.kp.cms.transactionsimpl.reports;


import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;

import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.transactions.reports.IStudentVehicleTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class StudentVehicleTransactionImpl implements IStudentVehicleTransaction {
	private static final Log log = LogFactory.getLog(StudentVehicleTransactionImpl.class);
		
	
	/* (non-Javadoc)
	 * get the list of student vehicle details
	 * @see com.kp.cms.transactions.reports.IStudentVehicleTransaction#getStudentVehicleDetails(java.lang.String)
	 */
	public List getStudentVehicleDetails(String searchCriteria)
			throws Exception {
		log.info("Entered Student vehicle Report search trasaction Impl");
			Session session = null;
			List selectedCandidatesList = null;
			try {
				/*SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
				session = sessionFactory.openSession();*/
				session = HibernateUtil.getSession();
				Query selectedCandidatesQuery=session.createQuery(searchCriteria);
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
		
			log.info("Exit Student vehicle Report search trasaction Impl");
			return selectedCandidatesList;
		}
}
