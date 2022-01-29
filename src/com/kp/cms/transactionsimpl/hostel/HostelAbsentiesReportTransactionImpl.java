package com.kp.cms.transactionsimpl.hostel;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;

import com.kp.cms.bo.admin.HlGroup;
import com.kp.cms.bo.admin.HlHostel;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.transactions.hostel.IHostelAbsentiesReportTransactions;
import com.kp.cms.utilities.HibernateUtil;

public class HostelAbsentiesReportTransactionImpl implements IHostelAbsentiesReportTransactions{
	
	private static final Log log = LogFactory.getLog(HostelAbsentiesReportTransactionImpl.class);
	private static volatile HostelAbsentiesReportTransactionImpl hostelAbsentiesReportTransactionImpl = null;

	public static HostelAbsentiesReportTransactionImpl getInstance() {
		if (hostelAbsentiesReportTransactionImpl == null) {
			hostelAbsentiesReportTransactionImpl = new HostelAbsentiesReportTransactionImpl();
		}
		return hostelAbsentiesReportTransactionImpl;
	}
	
	public List<HlHostel> getHostelNames() throws Exception {
		log.debug("Entering getHostelNames of HostelAbsentiesReportTransactionImpl");
		Session session = null;
		List<HlHostel> hlhostellist = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
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
				log.debug("Exiting getHostelNames of HostelAbsentiesReportTransactionImpl");
		return hlhostellist;
	}
	
	public List<Object> getHostelAbsentDetails(String searchQuery) throws Exception {
		log.info("Entering getHostelReqReportDetailsBo HostelReqReportTransactionImpl");
		Session session = null;
		List<Object> reqReportDetailsBo;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
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
	
	public List<HlGroup> getHlGroupDetails() throws Exception {
		log.debug("Entering getHlGroupDetails of HostelAbsentiesReportTransactionImpl");
		Session session = null;
		List<HlGroup> hlGroupList=null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			String sqlString = "from HlGroup h where h.isActive = 1 order by h.name";
			hlGroupList = session.createQuery(sqlString).list();
			if(hlGroupList!= null && hlGroupList.size() > 0){
				return hlGroupList;
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
				log.debug("Exiting getHlGroupDetails of HostelAbsentiesReportTransactionImpl");
		return hlGroupList;
	}

}
