package com.kp.cms.transactionsimpl.fee;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.FeeFinancialYear;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.transactions.fee.IFeeFinancialYearTransaction;
import com.kp.cms.utilities.HibernateUtil;

/**
 * @author kolli.ramamohan
 * @version 1.0
 * @since
 */
public class FeeFinancialYearTransactionImpl implements
		IFeeFinancialYearTransaction {

	private static final Logger log = Logger
			.getLogger(FeeFinancialYearTransactionImpl.class);

	/** 
	 * Used to get FeeFinancialYear Details
	 * @see com.kp.cms.transactions.fee.IFeeFinancialYearTransaction#getFeeFinancialYearDetails()
	 * @param void
	 * @return List<FeeFinancialYear>
	 * @throws Exception
	 */
	public List<FeeFinancialYear> getFeeFinancialYearDetails() throws Exception {
		log.info("Start of getFeeFinancialYearDetails of FeeFinancialYearTransactionImpl");
		Session session = null;
		List<FeeFinancialYear> feeFinancialYearList = new ArrayList<FeeFinancialYear>();
		try {
			//session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			feeFinancialYearList = session.createQuery(
					"from FeeFinancialYear number where number.isActive = 1")
					.list();
		} catch (Exception e) {
			log.error("Exception occured in getFeeFinancialYearDetails in FeeFinancialYearTransactionImpl : "+ e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		log.info("End of getFeeFinancialYearDetails of FeeFinancialYearTransactionImpl");
		return feeFinancialYearList;
	}
	
	/** 
	 * Used to get FeeFinancialYear
	 * @see com.kp.cms.transactions.fee.IFeeFinancialYearTransaction#getFeeFinancialYear(java.lang.String)
	 * @param java.lang.String
	 * @return com.kp.cms.bo.admin.FeeFinancialYear
	 * @throws Exception
	 */
	public FeeFinancialYear getFeeFinancialYear(String feeFinancialYear)
			throws Exception {
		log.info("Start of getFeeFinancialYear of FeeFinancialYearTransactionImpl");
		Session session = null;
		Transaction transaction = null;
		FeeFinancialYear feeFinancialYearr = null;
		try {
			//session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			Query query = session
					.createQuery("from FeeFinancialYear feeFinancialYear where feeFinancialYear.year=:year");
			query.setString("year", feeFinancialYear);
			feeFinancialYearr = (FeeFinancialYear) query.uniqueResult();
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			log.error("Exception occured in getFeeFinancialYear in FeeFinancialYearTransactionImpl : "+ e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		log.info("End of getFeeFinancialYear of FeeFinancialYearTransactionImpl");
		return feeFinancialYearr;
	}
	
	/** 
	 * Used to add FeeFinancialYear
	 * @see com.kp.cms.transactions.fee.IFeeFinancialYearTransaction#addFeeFinancialYear(com.kp.cms.bo.admin.FeeFinancialYear)
	 * @param com.kp.cms.bo.admin.FeeFinancialYear
	 * @return boolean
	 * @throws Exception
	 */
	public boolean addFeeFinancialYear(FeeFinancialYear feeFinancialYear)
			throws Exception {
		log.info("Start of addFeeFinancialYear of FeeFinancialYearTransactionImpl");
		Session session = null;
		Transaction transaction = null;
		try {
/*			SessionFactory sessFactory = InitSessionFactory.getInstance();
			session = sessFactory.openSession();*/
			session = HibernateUtil.getSession();
			
			transaction = session.beginTransaction();
			if (feeFinancialYear != null) {
				session.save(feeFinancialYear);
			}
			transaction.commit();
			log.info("End of addFeeFinancialYear of FeeFinancialYearTransactionImpl");
			return true;
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			log.error("Exception occured in addFeeFinancialYear in FeeFinancialYearTransactionImpl : "+ e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
	}	
	
	/** 
	 * Used to get FeeFinancialYear with Id
	 * @see com.kp.cms.transactions.fee.IFeeFinancialYearTransaction#getFeeFinancialDetailsWithId(int)
	 * @param int
	 * @return com.kp.cms.bo.admin.FeeFinancialYear
	 * @throws Exception
	 */
	public FeeFinancialYear getFeeFinancialDetailsWithId(int id)
			throws Exception {
		log.info("Start of getFeeFinancialDetailsWithId of FeeFinancialYearTransactionImpl");
		Session session = null;
		FeeFinancialYear feeFinancialYear = null;
		try {
			//session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from FeeFinancialYear feeFinancialYear where feeFinancialYear.id= :rowId");
			query.setInteger("rowId", id);
			feeFinancialYear = (FeeFinancialYear) query.uniqueResult();
		} catch (Exception e) {
			log.error("Exception occured in getFeeFinancialDetailsWithId in FeeFinancialYearTransactionImpl : "+ e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		log.info("End of getFeeFinancialDetailsWithId of FeeFinancialYearTransactionImpl");
		return feeFinancialYear;
	}

	/** 
	 * Used to update FeeFinancialYear
	 * @see com.kp.cms.transactions.fee.IFeeFinancialYearTransaction#updateFeeFinancialYear(com.kp.cms.bo.admin.FeeFinancialYear)
	 * @param com.kp.cms.bo.admin.FeeFinancialYear
	 * @return boolean
	 * @throws Exception
	 */
	public boolean updateFeeFinancialYear(FeeFinancialYear feeFinancialYear)
			throws Exception {
		log.info("Start of updateFeeFinancialYear of FeeFinancialYearTransactionImpl");
		Session session = null;
		Transaction transaction = null;
		try {
			//session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			session.merge(feeFinancialYear);
			transaction.commit();
			log.info("End of updateFeeFinancialYear of FeeFinancialYearTransactionImpl");
			return true;
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			log.error("Exception occured in updateFeeFinancialYear in FeeFinancialYearTransactionImpl : "+ e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
	}
	
	/** 
	 * Used to delete FeeFinancialYear Details
	 * @see com.kp.cms.transactions.fee.IFeeFinancialYearTransaction#deleteFeeFinancialYearDetails(int, java.lang.String)
	 * @param int, java.lang.String
	 * @return boolean
	 * @throws Exception
	 */
	public boolean deleteFeeFinancialYearDetails(int feeId, String userId)
			throws Exception {
		log.info("Start of deleteFeeFinancialYearDetails of FeeFinancialYearTransactionImpl");
		Session session = null;
		Transaction transaction = null;
		try {
			//session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			FeeFinancialYear feeFinancialYear = (FeeFinancialYear) session.get(
					FeeFinancialYear.class, feeId);
			feeFinancialYear.setIsCurrent(false);
			feeFinancialYear.setIsActive(false);
			feeFinancialYear.setModifiedBy(userId);
			feeFinancialYear.setLastModifiedDate(new Date());
			session.update(feeFinancialYear);
			transaction.commit();
			log.info("End of deleteFeeFinancialYearDetails of FeeFinancialYearTransactionImpl");
			return true;
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			log.error("Exception occured in deleteFeeFinancialYearDetails in FeeFinancialYearTransactionImpl : "+ e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
	}

	/** 
	 * Used to reactivate FeeFinancialYear
	 * @see com.kp.cms.transactions.fee.IFeeFinancialYearTransaction#reActivateFeeFinancialYear(java.lang.String, java.lang.String)
	 * @param java.lang.String, java.lang.String
	 * @return boolean
	 * @throws Exception
	 */
	public boolean reActivateFeeFinancialYear(String year, String userId)
			throws Exception {
		log.info("Start of reActivateFeeFinancialYear of FeeFinancialYearTransactionImpl");
		Session session = null;
		Transaction transaction = null;
		try {
/*			SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();
			
			Query query = session
					.createQuery("from FeeFinancialYear feeFinancialYear where feeFinancialYear.year = :year");
			query.setString("year", year);
			FeeFinancialYear feeFinancialYear = (FeeFinancialYear) query
					.uniqueResult();
			transaction = session.beginTransaction();
			feeFinancialYear.setIsActive(true);
			feeFinancialYear.setModifiedBy(userId);
			feeFinancialYear.setLastModifiedDate(new Date());
			session.update(feeFinancialYear);
			transaction.commit();
			log.info("End of reActivateFeeFinancialYear of FeeFinancialYearTransactionImpl");
			return true;
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			log.error("Exception occured in reActivateFeeFinancialYear in FeeFinancialYearTransactionImpl : "
					+ e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
	}
}
