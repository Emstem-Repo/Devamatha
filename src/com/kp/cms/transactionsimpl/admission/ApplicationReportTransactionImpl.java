package com.kp.cms.transactionsimpl.admission;


import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;

import com.kp.cms.bo.admin.OfflineDetails;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.transactions.admission.IApplicationReportTransaction;
import com.kp.cms.utilities.HibernateUtil;


/**
 * 
 * @author kshirod.k
 * TransactionImpl class for Application Report
 *
 */

public class ApplicationReportTransactionImpl implements
		IApplicationReportTransaction {
	private static final Log log = LogFactory.getLog(ApplicationReportTransactionImpl.class);

	/**
	 * Used to get issued applications
	 * From offline details table
	 * 
	 */

	public List<OfflineDetails> getIssuedApplications(int courseId, int year)throws Exception {
		log	.debug("Entering into ApplicationReportTransactionImpl of getIssuedApplications");
		Session session = null;
		List<OfflineDetails> issuedApplicationList;
		try {
			//session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			issuedApplicationList = session.createQuery(
					"from OfflineDetails details where details.course.id = "
							+ courseId + "and details.year =" + year
							+ "and details.isActive = 1 order by details.id").list();
		} catch (Exception e) {
			log.error("Error in getIssuedApplications of ApplicationReportTransactionImpl");
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		log	.debug("Leaving into ApplicationReportTransactionImpl of getIssuedApplications");
		return issuedApplicationList;
	}

	/**
	 * Used to get received applications
	 * From AdmAppln Table
	 */
	
	public List<Student> getreceivedApplications(int courseId, int year)throws Exception {
		log	.debug("Entering into ApplicationReportTransactionImpl of getreceivedApplications");
		Session session = null;
		List<Student> receivedApplicationList;
		try {
			//session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			receivedApplicationList = session.createQuery(
					"from Student s where s.admAppln.courseBySelectedCourseId.id = "
							+ courseId + "and s.admAppln.appliedYear =" + year
							+ "order by s.admAppln.id").list();
		} catch (Exception e) {
			log.error("Error in getreceivedApplications of ApplicationReportTransactionImpl");
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		log	.debug("Leaving into ApplicationReportTransactionImpl of getreceivedApplications");
		return receivedApplicationList;
	}

}
