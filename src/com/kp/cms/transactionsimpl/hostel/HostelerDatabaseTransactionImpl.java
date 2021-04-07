package com.kp.cms.transactionsimpl.hostel;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;

import com.kp.cms.bo.admin.HlApplicationForm;
import com.kp.cms.bo.admin.HlRoomTransaction;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.transactions.hostel.IHostelerDatabaseTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class HostelerDatabaseTransactionImpl implements IHostelerDatabaseTransaction{
	private static final Log log = LogFactory.getLog(HostelerDatabaseTransactionImpl.class);

/**
 * Used to get the students
 */
	public List<HlRoomTransaction> submitHostelerDatabase(String seachCriteria) throws Exception {
		log.info("entered submitHostelerDatabase..");
		Session session = null;
		List<HlRoomTransaction> studentSearchResult = null;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			studentSearchResult = session.createQuery(seachCriteria).list();			
		} catch (Exception e) {
			log.error("error while getting the results of submitHostelerDatabase  "+e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		log.info("exit submitHostelerDatabase..");
		return studentSearchResult;
	}
	
	public List<HlApplicationForm> getRequisitionDetailsToShow(String dynamicQuery) throws ApplicationException {
		log.info("Entering into RequisitionTransactionImpl--- getRequisitionDetailsToShow");
		Session session = null;
		List<HlApplicationForm> requisitionList;
		try {
			//session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			requisitionList = session.createQuery(dynamicQuery).list();
					} catch (Exception e) {		
			log.error("Exception occured in getting all getRequisitionDetailsToShow Details in IMPL :",e);
			throw  new ApplicationException(e);
				} finally {
				if (session != null) {
					session.flush();
					//session.close();
				}
				}
		log.info("Leaving into RequisitionTransactionImpl --- getRequisitionDetailsToShow");
		return requisitionList;
		}
}
