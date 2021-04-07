package com.kp.cms.transactionsimpl.hostel;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;

import com.kp.cms.bo.admin.HlHostel;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.transactions.hostel.IHostelDailyReportTransactions;
import com.kp.cms.utilities.HibernateUtil;

public class HostelDailyReportTransactionImpl implements IHostelDailyReportTransactions{
	
	private static final Log log = LogFactory.getLog(HostelDailyReportTransactionImpl.class);
	private static volatile HostelDailyReportTransactionImpl hostelDailyReportTransactionImpl = null;

	public static HostelDailyReportTransactionImpl getInstance() {
		if (hostelDailyReportTransactionImpl == null) {
			hostelDailyReportTransactionImpl = new HostelDailyReportTransactionImpl();
		}
		return hostelDailyReportTransactionImpl;
	}
	
	public List<HlHostel> getHostelNames() throws Exception {
		log.debug("Entering getHostelNames of HostelDailyReportTransactionImpl");
		Session session = null;
		List<HlHostel> hlhostellist = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			String sqlString = "from HlHostel h where h.isActive = 1 order by h.name";
			hlhostellist = session.createQuery(sqlString).list();
			if(hlhostellist!= null && hlhostellist.size() > 0){
				return hlhostellist;
			}
		}
			catch (Exception e) {
				log.error("Exception ocured at getting all records of hostels :",e);
					throw  new ApplicationException(e);
				} finally {
					if (session != null) {
						session.flush();
						//session.close();
					}
				}
				log.debug("Exiting getHostelNames of HostelDailyReportTransactionImpl");
		return hlhostellist;
	}
	
	public List<Object> getHostelDailyReportDetails(String searchQuery) throws Exception {
		log.info("Entering getHostelReqReportDetailsBo HostelReqReportTransactionImpl");
		Session session = null;
		List<Object> reqReportDetailsBo;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			reqReportDetailsBo = session.createQuery(searchQuery).list();
		} catch (Exception e) {
			log.error("error while getting the getApplicantHostelDetailsList  "+e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		log.info("Exiting getHostelReqReportDetailsBo HostelReqReportTransactionImpl");
		return reqReportDetailsBo;
	}

}
