package com.kp.cms.transactionsimpl.reports;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;

import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.transactions.reports.IFreshersListTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class FreshersListTransactionImpl implements IFreshersListTransaction {
	private static final Log log = LogFactory.getLog(FreshersListTransactionImpl.class);
	
	/**
	 * Returns the fresher students
	 */
	public List<Object[]> getFresherStudentList(String searchCriteria)throws Exception {
		log.info("entered getFresherStudentList..");
		Session session = null;
		List<Object[]> studentList = null;
		try {
			/*SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();
			 studentList = session.createQuery(searchCriteria).list();	
		} catch (Exception e) {
			log.error("error while getting the student in getFresherStudentList "+e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		log.info("exit getFresherStudentList..");
		return studentList;
	}
}
