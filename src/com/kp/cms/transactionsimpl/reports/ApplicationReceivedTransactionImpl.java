package com.kp.cms.transactionsimpl.reports;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;

import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.bo.admin.CourseApplicationNumber;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.transactions.reports.IApplicationReceivedTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class ApplicationReceivedTransactionImpl implements IApplicationReceivedTransaction{
	private static final Log log = LogFactory.getLog(ApplicationReceivedTransactionImpl.class);
	
	/**
	 * Gets application ranges online/offline
	 */
	public List<Integer> getAppNoRangeList(String searchQuery, boolean isOnline)
			throws Exception {
		log.info("Entering into getAppNoRangeList of ApplicationReceivedTransactionImpl");
		Session session = null;
		List<Integer> applicationNoList = new ArrayList<Integer>();
		try {
			 //SessionFactory sessionFactory = InitSessionFactory.getInstance();
			 //session =sessionFactory.openSession();
			 session = HibernateUtil.getSession();
			 Query query = session.createQuery(searchQuery);
			 List<CourseApplicationNumber> list = query.list();
			 if(list!=null){
				 Iterator<CourseApplicationNumber> appItr=list.iterator();
				 	while (appItr.hasNext()) {
				 		CourseApplicationNumber crsAppNo = (CourseApplicationNumber) appItr.next();						
							if(crsAppNo.getApplicationNumber()!=null){
								if(isOnline){
									int onlineFrom = Integer.parseInt(crsAppNo.getApplicationNumber().getOnlineApplnNoFrom().trim());
									int onlineTo = Integer.parseInt(crsAppNo.getApplicationNumber().getOnlineApplnNoTo());									
									while(onlineFrom <= onlineTo){
										applicationNoList.add(onlineFrom);
										++onlineFrom;
									}
								}
								else{
									int offlineFrom = Integer.parseInt(crsAppNo.getApplicationNumber().getOfflineApplnNoFrom().trim());
									int offlineTo = Integer.parseInt(crsAppNo.getApplicationNumber().getOfflineApplnNoTo());									
									while(offlineFrom <= offlineTo){
										applicationNoList.add(offlineFrom);
										++offlineFrom;
									}
								}
							}
						}
						
					}			 
			 log.info("Leaving into getAppNoRangeList of ApplicationReceivedTransactionImpl");
			 return applicationNoList;
		 } catch (Exception e) {
			 log.error("Error during getting getAppNoRangeList in ApplicationReceivedTransactionImpl",e);
			throw new ApplicationException(e);
		 }
		 finally {
				if (session != null) {
					session.flush();
					//session.close();
				}
			}
	}
	/**
	 * Gets admAppln objects based on year and course (if selected)
	 */
	public List<AdmAppln> getAllAdmAppls(String searchCondition) throws Exception {
		log.info("Entering into getAllAdmAppls of ApplicationReceivedTransactionImpl");
		Session session = null;
		List<AdmAppln> studentList = null;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			 //session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			 studentList = session.createQuery(searchCondition).list();	
		} catch (Exception e) {
			log.error("error in getAllAdmAppls of ApplicationReceivedTransactionImpl"+e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		log.info("Leaving into getAllAdmAppls of ApplicationReceivedTransactionImpl");
		return studentList;
	}
}
