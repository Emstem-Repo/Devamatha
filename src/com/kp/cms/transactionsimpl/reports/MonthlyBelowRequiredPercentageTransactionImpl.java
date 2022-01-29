package com.kp.cms.transactionsimpl.reports;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;

import com.kp.cms.bo.admin.AttendanceStudent;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.transactions.reports.IMonthlyBelowRequiredPercentageTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class MonthlyBelowRequiredPercentageTransactionImpl implements IMonthlyBelowRequiredPercentageTransaction{

	private static final Log log = LogFactory.getLog(MonthlyBelowRequiredPercentageTransactionImpl.class);

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.reports.IStudentAttendanceReportTransaction#getStudentsAttendance(java.lang.String)
	 */
	@Override
	public List<AttendanceStudent> getStudentsAttendance(String searchCriteria) throws Exception {
		log.info("entered getStudentAttendance of MonthlyBelowRequiredPercentageTransactionImpl");
		Session session = null;
		List<AttendanceStudent> studentsAttendanceResult = null;
		try {
			/*SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();
			studentsAttendanceResult = session.createQuery(searchCriteria).list();			
		} catch (Exception e) {
			log.error("error while getting the student attendance results in MonthlyBelowRequiredPercentageTransactionImpl", e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		log.info("exit getStudentAttendance.MonthlyBelowRequiredPercentageTransactionImpl.");
		return studentsAttendanceResult;
	}
}
