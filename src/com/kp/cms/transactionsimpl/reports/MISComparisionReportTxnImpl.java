package com.kp.cms.transactionsimpl.reports;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;

import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.bo.admin.BankMis;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.transactions.reports.IMISComparisionReportTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class MISComparisionReportTxnImpl implements IMISComparisionReportTransaction{
	
	private static final Log log = LogFactory.getLog(MISComparisionReportTxnImpl.class);
	
	
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.reports.IMISComparisionReportTransaction#getComparisionDetailsFromApplication(java.util.Date)
	 */
	@Override
	public List<AdmAppln> getComparisionDetailsFromApplication(
			Date transactionDate) throws Exception {
		log.info("entered getComparisionDetailsFromApplication..");
		Session session = null;
		List<AdmAppln> applicationDetailsList = null;
		try {
			/*SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();
			Query detailsQuery = session.createQuery("from AdmAppln admAppln where admAppln.date =:transactionDate order by admAppln.applnNo");
			detailsQuery.setDate("transactionDate", transactionDate);
			
			applicationDetailsList = detailsQuery.list();
		} catch (Exception e) {
			log.error("error while getting the getComparisionDetailsFromApplication  "+e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		log.info("exit getComparisionDetailsFromApplication..");
		return applicationDetailsList;
	}
	
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.reports.IMISComparisionReportTransaction#getComparisionDetailsFromBank(java.util.Date)
	 */
	@Override
	public List<BankMis> getComparisionDetailsFromBank(Date transactionDate)
			throws Exception {
		log.info("entered getComparisionDetailsFromBank..");
		Session session = null;
		List<BankMis> bankDetailsList = null;
		try {
			/*SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();
			Query detailsQuery = session.createQuery("from BankMis bankMis where bankMis.txnDate =:transactionDate");
			detailsQuery.setDate("transactionDate", transactionDate);
			
			bankDetailsList = detailsQuery.list();
		} catch (Exception e) {
			log.error("error while getting the getComparisionDetailsFromBank  "+e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		log.info("exit getComparisionDetailsFromBank..");
		return bankDetailsList;
	}
}
