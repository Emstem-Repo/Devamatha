package com.kp.cms.transactionsimpl.reports;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;

import com.kp.cms.bo.admin.ApplnDoc;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.transactions.reports.ICertificateDueReportTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class CertificateDueReportTransactionImpl implements ICertificateDueReportTransaction{
	private static final Log log = LogFactory.getLog(CertificateDueReportTransactionImpl.class);
	
	/**
	 * Gets certificate due students
	 */
	public List<ApplnDoc> getCerificateDueStudents(String searchedQuery) throws Exception {
		log.info("entered getCerificateDueStudents..");
		Session session = null;
		List<ApplnDoc> studentSearchResult = null;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			 session = HibernateUtil.getSession();
			 studentSearchResult = session.createQuery(searchedQuery).list();	
		} catch (Exception e) {
			log.error("error while getting the ApplnDoc in getCerificateDueStudents  "+e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		log.info("exit getCerificateDueStudents..");
		return studentSearchResult;
	}

}
