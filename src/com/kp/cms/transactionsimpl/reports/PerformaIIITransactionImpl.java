package com.kp.cms.transactionsimpl.reports;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;

import com.kp.cms.bo.admin.Student;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.transactions.reports.IPerformaIIITransaction;
import com.kp.cms.utilities.HibernateUtil;

public class PerformaIIITransactionImpl implements IPerformaIIITransaction{

	private static final Log log = LogFactory.getLog(PerformaIIITransactionImpl.class);
	
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.reports.IPerformaIIITransaction#getCourseIntakeDetails(java.lang.String)
	 */
	@Override
	public List<Student> getCourseIntakeDetails(String searchCriteria)
			throws Exception {
		log.info("entered getStudentAttendance..");
		Session session = null;
		List<Student> courseIntakeDetailsList = null;
		try {
			/*SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();
			courseIntakeDetailsList = session.createQuery(searchCriteria).list();
			
		} catch (Exception e) {
			log.error("error while getting the student attendance results  "+e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		log.info("exit getStudentAttendance..");
		return courseIntakeDetailsList;
	}
}
