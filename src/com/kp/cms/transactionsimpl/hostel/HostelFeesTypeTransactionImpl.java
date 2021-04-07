package com.kp.cms.transactionsimpl.hostel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.HlFeeType;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.transactions.hostel.IHostelFeesTypeTransaction;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;

/**
 * @author kolli.ramamohan
 * @version 1.0
 * @since
 */
public class HostelFeesTypeTransactionImpl implements IHostelFeesTypeTransaction {

	private static final Logger log = Logger.getLogger(HostelFeesTypeTransactionImpl.class);

	/** 
	 * Used to get FeeFinancialYear Details
	 * @see com.kp.cms.transactions.fee.IFeeFinancialYearTransaction#getFeeFinancialYearDetails()
	 * @param void
	 * @return List<FeeFinancialYear>
	 * @throws Exception
	 */
	public List<HlFeeType> getHostelFeesTypeDetails() throws Exception {
		log.info("Start of getHostelFeesTypeDetails of HostelFeesTypeTransactionImpl");
		Session session = null;
		List<HlFeeType> hostelFeesTypeList;
		try {
			//session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			hostelFeesTypeList = session.createQuery("from HlFeeType hlFeeType where hlFeeType.isActive = 1").list();
		} catch (Exception e) {
			log.error("Exception occured in getHostelFeesTypeDetails in HostelFeesTypeTransactionImpl : "+ e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		log.info("End of getHostelFeesTypeDetails of HostelFeesTypeTransactionImpl");
		return hostelFeesTypeList;
	}
	
	/** 
	 * Used to get HlFeeType
	 * @see com.kp.cms.transactions.fee.IHostelFeesTypeTransaction#getHostelFeesType(java.lang.String)
	 * @param java.lang.String
	 * @return com.kp.cms.bo.admin.HlFeeType
	 * @throws Exception
	 */
	public HlFeeType getHostelFeesType(String feeType)
			throws Exception {
		log.info("Start of getHostelFeesType of HostelFeesTypeTransactionImpl");
		Session session = null;
		Transaction transaction = null;
		HlFeeType hlFeeType = null;
		try {
			//session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			Query query = session.createQuery("from HlFeeType hlFeeType where hlFeeType.name=:feesType");
			query.setString("feesType", feeType);
			hlFeeType = (HlFeeType) query.uniqueResult();
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			log.error("Exception occured in getHostelFeesType in HostelFeesTypeTransactionImpl : "+ e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		log.info("End of getHostelFeesType of HostelFeesTypeTransactionImpl");
		return hlFeeType;
	}
	
	/** 
	 * Used to add FeeFinancialYear
	 * @see com.kp.cms.transactions.fee.IFeeFinancialYearTransaction#addFeeFinancialYear(com.kp.cms.bo.admin.FeeFinancialYear)
	 * @param com.kp.cms.bo.admin.FeeFinancialYear
	 * @return boolean
	 * @throws Exception
	 */
	public boolean addHostelFeesType(HlFeeType hlFeeType)throws Exception {
		log.info("Start of addHostelFeesType of HostelFeesTypeTransactionImpl");
		Session session = null;
		Transaction transaction = null;
		try {
			//SessionFactory sessFactory = InitSessionFactory.getInstance();
			//session = sessFactory.openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			if (hlFeeType != null) {
				session.save(hlFeeType);
			}
			transaction.commit();
			log.info("End of addHostelFeesType of HostelFeesTypeTransactionImpl");
			return true;
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			log.error("Exception occured in addHostelFeesType in HostelFeesTypeTransactionImpl : "+ e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
	}
	
	/** 
	 * Used to get HlFeeType with Id
	 * @see com.kp.cms.transactions.fee.IHostelFeesTypeTransaction#getHostelFeesTypeDetailsWithId(int)
	 * @param int
	 * @return com.kp.cms.bo.admin.HlFeeType
	 * @throws Exception
	 */
	public HlFeeType getHostelFeesTypeDetailsWithId(int id)	throws Exception {
		log.info("Start of getHostelFeesTypeDetailsWithId of HostelFeesTypeTransactionImpl");
		Session session = null;
		HlFeeType hlFeeType = null;
		try {
			//session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from HlFeeType hlFeeType where hlFeeType.id= :rowId");
			query.setInteger("rowId", id);
			hlFeeType = (HlFeeType) query.uniqueResult();
		} catch (Exception e) {
			log.error("Exception occured in getHostelFeesTypeDetailsWithId in HostelFeesTypeTransactionImpl : "+ e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		log.info("End of getHostelFeesTypeDetailsWithId of HostelFeesTypeTransactionImpl");
		return hlFeeType;
	}

	/** 
	 * Used to update HlFeeType
	 * @see com.kp.cms.transactions.hostel.IHostelFeesTypeTransaction#updateHostelFeesType(com.kp.cms.bo.admin.HlFeeType)
	 * @param com.kp.cms.bo.admin.HlFeeType
	 * @return boolean
	 * @throws Exception
	 */
	public boolean updateHostelFeesType(HlFeeType feeType) throws Exception {
		log.info("Start of updateHostelFeesType of HostelFeesTypeTransactionImpl");
		Session session = null;
		Transaction transaction = null;
		try {
			//session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			session.update(feeType);
			transaction.commit();
			log.info("End of updateHostelFeesType of HostelFeesTypeTransactionImpl");
			return true;
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			log.error("Exception occured in updateHostelFeesType in HostelFeesTypeTransactionImpl : "+ e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
	}
	
	/** 
	 * Used to delete HlFeeType Details
	 * @see com.kp.cms.transactions.fee.IHostelFeesTypeTransaction#deleteHostelFeesTypeDetails(int, java.lang.String)
	 * @param int, java.lang.String
	 * @return boolean
	 * @throws Exception
	 */
	public boolean deleteHostelFeesTypeDetails(int feeId, String userId) throws Exception {
		log.info("Start of deleteHostelFeesTypeDetails of HostelFeesTypeTransactionImpl");
		Session session = null;
		Transaction transaction = null;
		try {
			//session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			HlFeeType hlFeeType = (HlFeeType) session.get(HlFeeType.class, feeId);			
			hlFeeType.setIsActive(false);
			hlFeeType.setModifiedBy(userId);
			hlFeeType.setLastModifiedDate(new Date());
			session.update(hlFeeType);
			transaction.commit();
			log.info("End of deleteHostelFeesTypeDetails of HostelFeesTypeTransactionImpl");
			return true;
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			log.error("Exception occured in deleteHostelFeesTypeDetails in HostelFeesTypeTransactionImpl : "+ e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
	}

	/** 
	 * Used to reactivate HlFeeType
	 * @see com.kp.cms.transactions.hostel.IHostelFeesTypeTransaction#reActivateHostelFeesType(java.lang.String, java.lang.String)
	 * @param java.lang.String, java.lang.String
	 * @return boolean
	 * @throws Exception
	 */
	public boolean reActivateHostelFeesType(String feeType, String userId) throws Exception {
		log.info("Start of reActivateHostelFeesType of HostelFeesTypeTransactionImpl");
		Session session = null;
		Transaction transaction = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			Query query = session.createQuery("from HlFeeType feeType where feeType.name= :feeName");
			query.setString("feeName", feeType);
			HlFeeType hlFeeType = (HlFeeType) query.uniqueResult();
			
			hlFeeType.setIsActive(true);
			hlFeeType.setModifiedBy(userId);
			hlFeeType.setLastModifiedDate(new Date());
			session.update(hlFeeType);
			transaction.commit();
			log.info("End of reActivateHostelFeesType of HostelFeesTypeTransactionImpl");
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
