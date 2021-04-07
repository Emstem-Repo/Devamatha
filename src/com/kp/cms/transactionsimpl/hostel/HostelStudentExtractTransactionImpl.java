package com.kp.cms.transactionsimpl.hostel;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;

import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.transactions.hostel.IHostelStudentExtractTransactions;
import com.kp.cms.utilities.HibernateUtil;

public class HostelStudentExtractTransactionImpl implements IHostelStudentExtractTransactions{
	
	private static final Log log = LogFactory.getLog(HostelStudentExtractTransactionImpl.class);
	private static volatile HostelStudentExtractTransactionImpl hostelStudentExtractTransactionImpl = null;

	public static HostelStudentExtractTransactionImpl getInstance() {
		if (hostelStudentExtractTransactionImpl == null) {
			hostelStudentExtractTransactionImpl = new HostelStudentExtractTransactionImpl();
		}
		return hostelStudentExtractTransactionImpl;
	}
	
	public List<Object> getStudentExtractDetailsBo(String searchQuery) throws Exception {
		log.info("Entering getStudentExtractDetailsBo HostelStudentExtractTransactionImpl");
		Session session = null;
		List<Object> reqReportDetailsBo;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			reqReportDetailsBo = session.createQuery(searchQuery).list();
		} catch (Exception e) {
			log.error("error while getting the HostelStudentExtractTransactionImpl  "+e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		log.info("Exiting getStudentExtractDetailsBo HostelStudentExtractTransactionImpl");
		return reqReportDetailsBo;
	}

}
