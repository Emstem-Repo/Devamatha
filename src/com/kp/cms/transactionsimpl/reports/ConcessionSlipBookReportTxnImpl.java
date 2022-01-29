package com.kp.cms.transactionsimpl.reports;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;

import com.kp.cms.bo.admin.FeeVoucher;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.transactions.reports.IConcessionSlipBookReportTxn;
import com.kp.cms.utilities.HibernateUtil;

public class ConcessionSlipBookReportTxnImpl implements IConcessionSlipBookReportTxn {
	private static final Log log = LogFactory.getLog(ConcessionSlipBookReportTxnImpl.class);
	public static volatile ConcessionSlipBookReportTxnImpl concessionSlipBookReportTxnImpl = null;

	public static ConcessionSlipBookReportTxnImpl getInstance() {
		if (concessionSlipBookReportTxnImpl == null) {
			concessionSlipBookReportTxnImpl = new ConcessionSlipBookReportTxnImpl();
			return concessionSlipBookReportTxnImpl;
		}
		return concessionSlipBookReportTxnImpl;
	}
	/**
	 * 
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public List<FeeVoucher> getSlipBookDetails(String type) throws Exception {
		log.debug("inside getSlipBookDetails");
		Session session = null;
		
		try {
			/*SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();
			StringBuffer queryString = new StringBuffer("from FeeVoucher i where i.isActive = 1");
			if(type!= null && !type.trim().isEmpty()){
				queryString.append(" and type = '" + type + "'");
			}
			queryString.append(" order by type");
			Query query = session.createQuery(queryString.toString());
			List<FeeVoucher> list = query.list();
			session.flush();
			//session.close();
			//sessionFactory.close();
			log.debug("leaving getSlipBookDetails");
			return list;
		 } catch (Exception e) {
			 log.error("Error in getSlipBookDetails...",e);
			 session.flush();
			 //session.close();
			 throw  new ApplicationException(e);
		 }
	}
	
}
