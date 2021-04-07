package com.kp.cms.transactionsimpl.reports;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;

import com.kp.cms.bo.admin.AttendanceStudent;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.transactions.reports.IStudentAbsenceDetailsTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class StudentAbsenceDetailsTransactionImpl implements IStudentAbsenceDetailsTransaction {
	
	private static final Log log = LogFactory.getLog(StudentAbsenceDetailsTransactionImpl.class);

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.reports.IStudentAbsenceDetailsTransaction#getStudentAbsenceDetails(java.lang.String)
	 */
	@Override
	public List<AttendanceStudent> getStudentAbsenceDetails(String searchCriteria)
			throws Exception {
		log.info("Entered getStudentAbsenceDetails");
		Session session = null;
		List<AttendanceStudent> studentAbsenceDetailsList = null;
		try {
			/*SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();
			studentAbsenceDetailsList = session.createQuery(searchCriteria).list();
			
		} catch (Exception e) {
			log.error("error while getting the student absence details  "+e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		log.info("Exit getStudentAbsenceDetails");
		return studentAbsenceDetailsList;
	}
}
