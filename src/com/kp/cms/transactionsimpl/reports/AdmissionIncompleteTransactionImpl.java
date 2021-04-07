package com.kp.cms.transactionsimpl.reports;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;

import com.kp.cms.bo.admin.Student;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.transactions.reports.IAdmissionIncompleteTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class AdmissionIncompleteTransactionImpl implements IAdmissionIncompleteTransaction{
	private static final Log log = LogFactory.getLog(AdmissionIncompleteTransactionImpl.class);
	
	/**
	 * Gets incomplete admission students
	 */
	public List<Student> getIncompleteAdmssionStudents(String searchCriteria)
			throws Exception {
		log.info("entered getIncompleteAdmssionStudents..");
		Session session = null;
		List<Student> studentList = null;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			 session = HibernateUtil.getSession();
			 studentList = session.createQuery(searchCriteria).list();	
			 log.info("exit getIncompleteAdmssionStudents..");
			 return studentList;
		} catch (Exception e) {
			log.error("error while getting the student in getIncompleteAdmssionStudents "+e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}				
	}
}
