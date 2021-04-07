package com.kp.cms.transactionsimpl.reports;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;

import com.kp.cms.bo.admin.AttendanceStudent;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.transactions.reports.IAttendanceFinalSummaryTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class AttendanceFinalSummaryTransactionImpl implements IAttendanceFinalSummaryTransaction{

	
	private static Log log = LogFactory.getLog(AttendanceFinalSummaryTransactionImpl.class);
	/**
	 * @see com.kp.cms.transactions.reports.IAttendanceFinalSummaryTransaction#getAttendanceFinalSummaryList(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<AttendanceStudent> getAttendanceFinalSummaryList(String attendanceFinalSummaryQuery)
			throws ApplicationException {
 		log.info("entering into getAttendanceFinalSummaryList of AttendanceFinalSummaryTransactionImpl class.");
		Session session = null;
		List<AttendanceStudent> studentSearchResult = null;
		/*try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			Query studentQuery = session
					.createQuery(attendanceFinalSummaryQuery);
			studentSearchResult = studentQuery.list();*/
		
		try {
				session = HibernateUtil.getSession();
				studentSearchResult = session.createQuery(attendanceFinalSummaryQuery).list();
		} catch (Exception e) {
			log.info("error in getAttendanceFinalSummaryList of AttendanceFinalSummaryTransactionImpl class.",e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		log.info("exit of getAttendanceFinalSummaryList of AttendanceFinalSummaryTransactionImpl class.");
		return studentSearchResult;

	}

}
