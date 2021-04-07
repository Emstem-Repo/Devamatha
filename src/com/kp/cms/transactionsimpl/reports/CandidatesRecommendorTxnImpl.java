package com.kp.cms.transactionsimpl.reports;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;

import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.transactions.reports.ICandidatesRecommendorTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class CandidatesRecommendorTxnImpl implements ICandidatesRecommendorTransaction{
	
private static final Log log = LogFactory.getLog(CandidatesRecommendorTxnImpl.class);
	
	public static volatile CandidatesRecommendorTxnImpl self=null;
	
	/**
	 * @return unique instance of LeaveReportTransactionImpl class.
	 * This method gives instance of this method
	 */
	public static CandidatesRecommendorTxnImpl getInstance(){
		if(self==null)
			self= new CandidatesRecommendorTxnImpl();
		return self;
	}
	
	@Override
	public List<Object[]> getAppRecommendorDetails(String query)
			throws Exception {
		log.info("entered getAppRecommendorDetails..");
		Session session = null;
		List<Object[]> apprecomList = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			apprecomList = session.createQuery(query).list();
		
		} catch (Exception e) {
			log.error("error while getting the getAppRecommendorDetails results  ",e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		log.info("exit getAppRecommendorDetails..");
		return apprecomList;
	}
}