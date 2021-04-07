package com.kp.cms.transactionsimpl.pettycash;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.PcFinancialYear;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.transactions.pettycash.IPCFinancialYearTransaction;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;

/**
 * @author kolli.ramamohan
 * @version 1.0
 * @since
 */
public class PCFinancialYearTransactionImpl implements
		IPCFinancialYearTransaction {

	private static final Logger log = Logger
			.getLogger(PCFinancialYearTransactionImpl.class);

	/** 
	 * Used to get PCFinancialYear Details
	 * @see com.kp.cms.transactions.fee.IPCFinancialYearTransaction#getPCFinancialYearDetails()
	 * @param void
	 * @return List<PCFinancialYear>
	 * @throws Exception
	 */
	public List<PcFinancialYear> getPcFinancialYearDetails() throws Exception {
		log.info("Start of getPCFinancialYearDetails of PCFinancialYearTransactionImpl");
		Session session = null;
		List<PcFinancialYear> pcFinancialYearList;
		try {
			session = InitSessionFactory.getInstance().openSession();
//			session = HibernateUtil.getSession();
			pcFinancialYearList = session.createQuery("from PcFinancialYear number where number.isActive = 1").list();
		} catch (Exception e) {
			log.error("Exception occured in getPCFinancialYearDetails in PCFinancialYearTransactionImpl : "+ e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		log.info("End of getPCFinancialYearDetails of PCFinancialYearTransactionImpl");
		return pcFinancialYearList;
	}
	
	/** 
	 * Used to get PCFinancialYear
	 * @see com.kp.cms.transactions.fee.IPCFinancialYearTransaction#getPCFinancialYear(java.lang.String)
	 * @param java.lang.String
	 * @return com.kp.cms.bo.admin.PCFinancialYear
	 * @throws Exception
	 */
	public PcFinancialYear getPcFinancialYear(String pcFinancialYear)
			throws Exception {
		log.info("Start of getPCFinancialYear of PCFinancialYearTransactionImpl");
		Session session = null;
		Transaction transaction = null;
		PcFinancialYear pcFinancialYearr = null;
		try {
			//session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			Query query = session
					.createQuery("from PcFinancialYear pcFinancialYear where pcFinancialYear.financialYear=:year");
			query.setString("year", pcFinancialYear);
			pcFinancialYearr = (PcFinancialYear) query.uniqueResult();
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			log.error("Exception occured in getPCFinancialYear in PCFinancialYearTransactionImpl : "+ e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		log.info("End of getPCFinancialYear of PCFinancialYearTransactionImpl");
		return pcFinancialYearr;
	}
	
	/** 
	 * Used to add PCFinancialYear
	 * @see com.kp.cms.transactions.fee.IPCFinancialYearTransaction#addPCFinancialYear(com.kp.cms.bo.admin.PCFinancialYear)
	 * @param com.kp.cms.bo.admin.PCFinancialYear
	 * @return boolean
	 * @throws Exception
	 */
	public boolean addPcFinancialYear(PcFinancialYear pcFinancialYear)
			throws Exception {
		log.info("Start of addPCFinancialYear of PCFinancialYearTransactionImpl");
		Session session = null;
		Transaction transaction = null;
		try {
			/*SessionFactory sessFactory = InitSessionFactory.getInstance();
			session = sessFactory.openSession();*/
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			if (pcFinancialYear != null) {
				session.save(pcFinancialYear);
			}
			transaction.commit();
			log.info("End of addPCFinancialYear of PCFinancialYearTransactionImpl");
			return true;
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			log.error("Exception occured in addPCFinancialYear in PCFinancialYearTransactionImpl : "+ e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
	}	
	
	/** 
	 * Used to get PCFinancialYear with Id
	 * @see com.kp.cms.transactions.PC.IPCFinancialYearTransaction#getPCFinancialDetailsWithId(int)
	 * @param int
	 * @return com.kp.cms.bo.admin.PCFinancialYear
	 * @throws Exception
	 */
	public PcFinancialYear getPcFinancialDetailsWithId(int id)
			throws Exception {
		log.info("Start of getPCFinancialDetailsWithId of PCFinancialYearTransactionImpl");
		Session session = null;
		PcFinancialYear pcFinancialYear = null;
		try {
			//session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from PcFinancialYear pcFinancialYear where pcFinancialYear.id= :rowId");
			query.setInteger("rowId", id);
			pcFinancialYear = (PcFinancialYear) query.uniqueResult();
		} catch (Exception e) {
			log.error("Exception occured in getPCFinancialDetailsWithId in PCFinancialYearTransactionImpl : "+ e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		log.info("End of getPCFinancialDetailsWithId of PCFinancialYearTransactionImpl");
		return pcFinancialYear;
	}

	/** 
	 * Used to update PCFinancialYear
	 * @see com.kp.cms.transactions.PC.IPCFinancialYearTransaction#updatePCFinancialYear(com.kp.cms.bo.admin.PCFinancialYear)
	 * @param com.kp.cms.bo.admin.PCFinancialYear
	 * @return boolean
	 * @throws Exception
	 */
	public boolean updatePcFinancialYear(PcFinancialYear pcFinancialYear)
			throws Exception {
		log.info("Start of updatePCFinancialYear of PCFinancialYearTransactionImpl");
		Session session = null;
		Transaction transaction = null;
		try {
			session = InitSessionFactory.getInstance().openSession();
//			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			session.update(pcFinancialYear);
			transaction.commit();
			log.info("End of updatePCFinancialYear of PCFinancialYearTransactionImpl");
			return true;
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			log.error("Exception occured in updatePCFinancialYear in PCFinancialYearTransactionImpl : "+ e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
	}
	
	/** 
	 * Used to delete PCFinancialYear Details
	 * @see com.kp.cms.transactions.PC.IPCFinancialYearTransaction#deletePCFinancialYearDetails(int, java.lang.String)
	 * @param int, java.lang.String
	 * @return boolean
	 * @throws Exception
	 */
	public boolean deletePcFinancialYearDetails(int feeId, String userId)
			throws Exception {
		log.info("Start of deletePCFinancialYearDetails of PCFinancialYearTransactionImpl");
		Session session = null;
		Transaction transaction = null;
		try {
			//session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
 		    transaction = session.beginTransaction();
			PcFinancialYear pcFinancialYear = (PcFinancialYear) session.get(
					PcFinancialYear.class, feeId);
			pcFinancialYear.setIsCurrent(false);
			pcFinancialYear.setIsActive(false);
			pcFinancialYear.setModifiedBy(userId);
			pcFinancialYear.setLastModifiedDate(new Date());
			session.update(pcFinancialYear);
			transaction.commit();
			log.info("End of deletePCFinancialYearDetails of PCFinancialYearTransactionImpl");
			return true;
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			log.error("Exception occured in deletePCFinancialYearDetails in PCFinancialYearTransactionImpl : "+ e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
	}

	/** 
	 * Used to reactivate PCFinancialYear
	 * @see com.kp.cms.transactions.fee.IPCFinancialYearTransaction#reActivatePCFinancialYear(java.lang.String, java.lang.String)
	 * @param java.lang.String, java.lang.String
	 * @return boolean
	 * @throws Exception
	 */
	public boolean reActivatePcFinancialYear(String year, String userId)
			throws Exception {
		log.info("Start of reActivatePCFinancialYear of PCFinancialYearTransactionImpl");
		Session session = null;
		Transaction transaction = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from PcFinancialYear pcFinancialYear where pcFinancialYear.financialYear = :year");
			query.setString("year", year);
			PcFinancialYear pcFinancialYear = (PcFinancialYear) query
					.uniqueResult();
			transaction = session.beginTransaction();
			pcFinancialYear.setIsActive(true);
			pcFinancialYear.setModifiedBy(userId);
			pcFinancialYear.setLastModifiedDate(new Date());
			session.update(pcFinancialYear);
			transaction.commit();
			log.info("End of reActivatePCFinancialYear of PCFinancialYearTransactionImpl");
			return true;
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			log.error("Exception occured in reActivatePCFinancialYear in PCFinancialYearTransactionImpl : "
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
