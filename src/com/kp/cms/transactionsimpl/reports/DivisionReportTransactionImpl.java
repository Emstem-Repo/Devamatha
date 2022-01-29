package com.kp.cms.transactionsimpl.reports;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;

import com.kp.cms.bo.admin.FeeDivision;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.transactions.reports.IDivisionReportTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class DivisionReportTransactionImpl implements IDivisionReportTransaction{
	private static final Log log = LogFactory.getLog(DivisionReportTransactionImpl.class);
	public static volatile DivisionReportTransactionImpl divisionReportTransactionImpl = null;

	public static DivisionReportTransactionImpl getInstance() {
		if (divisionReportTransactionImpl == null) {
			divisionReportTransactionImpl = new DivisionReportTransactionImpl();
			return divisionReportTransactionImpl;
		}
		return divisionReportTransactionImpl;
	}

	/**
	 * 
	 * @param divId
	 * @return
	 * @throws Exception
	 */
	public List<FeeDivision> getFeeDivisions(String divId) throws Exception {
		log.debug("inside getFeeDivisions");
		Session session = null;
		
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			StringBuffer queryString = new StringBuffer("from FeeDivision f where f.isActive = 1 ");
			if(divId!= null && !divId.trim().isEmpty() ){
				queryString.append(" and f.id = '" + divId + "'");
			}
			Query query = session.createQuery(queryString.toString());
			List<FeeDivision> list = query.list();
			session.flush();
			//session.close();
			//sessionFactory.close();
			log.debug("leaving getFeeDivisions");
			return list;
		 } catch (Exception e) {
			 log.error("Error in getFeeDivisions...",e);
			 session.flush();
			 session.close();
			 throw  new ApplicationException(e);
		 }
	}

}
