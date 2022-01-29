package com.kp.cms.transactionsimpl.reports;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;

import com.kp.cms.bo.admin.Student;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.transactions.reports.ICategoryWiseStudentTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class CategoryWiseStudentTransactionImpl implements ICategoryWiseStudentTransaction{

	private static final Log log = LogFactory.getLog(CategoryWiseStudentTransactionImpl.class);
	
/**
 * Returns the students by categorywise
 */
	public List<Student> getStudents(String searchCriteria) throws Exception {
		log.info("entered getStudents of CategoryWiseStudentTransactionImpl");
		Session session = null;
		List<Student> studentList = null;
		try {
			/*SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();
			studentList = session.createQuery(searchCriteria).list();			
		} catch (Exception e) {
			log.error("error in getStudents of CategoryWiseStudentTransactionImpl", e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		log.info("exit getStudents.CategoryWiseStudentTransactionImpl.");
		return studentList;
	}

}
