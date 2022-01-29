package com.kp.cms.transactionsimpl.fee;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.FeeBillNumber;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.transactions.fee.IFeeBillNumberTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class FeeBillNumberTransactionImpl implements IFeeBillNumberTransaction {
	
	private static final Log log = LogFactory.getLog(FeeBillNumberTransactionImpl.class);
	
	/**
	 * Gets all fee bill number details
	 */
	public List<FeeBillNumber> getFeeBillNumberDetails()throws Exception
	{
		log.info("Inside of getFeeBillNumberDetails of FeeBillNumberTransactionImpl");
		Session session = null;
		List<FeeBillNumber> feeList=new ArrayList<FeeBillNumber>();
		try {
			//session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			feeList = session.createQuery("from FeeBillNumber number where number.isActive = 1 order by number.id").list();
			
		} catch (Exception e) {			
			log.error("Exception occured in getting all FeeBillNumberDetails in FeeBillNumberIMPL :"+e);
			throw  new ApplicationException(e);
		} finally {
		if (session != null) {
			session.flush();
			//session.close();
		}
		}
		log.info("End of getFeeBillNumberDetails of FeeBillNumberTransactionImpl");
		return feeList;		
	}
	/**
	 * Used to add FeeBillNumber
	 */
	public boolean addFeeBillNumber(FeeBillNumber number)throws Exception
	{
		log.info("Inside of addFeeBillNumber of FeeBillNumberTransactionImpl");
		Session session = null;
		Transaction transaction = null;
		try {
			//session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			session.save(number);
			transaction.commit();
			return true;
		} catch (Exception e) {	
			if(transaction != null){
				transaction.rollback();
			}
			log.error("Exception occured in adding FeeBillNumber in IMPL :"+e);
			throw  new ApplicationException(e);
		} finally {
		if (session != null) {
			session.flush();
			//session.close();
		}
		log.info("End of addFeeBillNumber of FeeBillNumberTransactionImpl");
		}
	}
	
	/**
	 * Used while deleting
	 */
	public boolean deleteFeeBillNumber(int feeId, String userId)throws Exception
	{
		log.info("Inside of deleteFeeBillNumber of FeeBillNumberTransactionImpl");
		Session session = null;
		Transaction transaction = null;
		try {
			//session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			FeeBillNumber feeBillNumber=(FeeBillNumber)session.get(FeeBillNumber.class,feeId);
			feeBillNumber.setIsActive(false);
			feeBillNumber.setModifiedBy(userId);
			feeBillNumber.setLastModifiedDate(new Date());
			session.update(feeBillNumber);
			transaction.commit();
			return true;
		} catch (Exception e) {
			if(transaction != null){
				transaction.rollback();
			}
			log.error("Exception occured in deleting FeeBillNumber in IMPL :"+e);
			throw  new ApplicationException(e);
		} finally {
		if (session != null) {
			session.flush();
			session.close();
		}
		}
	}
	
	/**
	 * Check for duplicate on bill no and year
	 */
	
	public FeeBillNumber getFeeBillNoYear(int year)throws Exception
	{
		log.info("Inside of getFeeBillNoYear of FeeBillNumberTransactionImpl");
		Session session = null;
		FeeBillNumber number = null;
		try {
			//session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			Query query=session.createQuery("from FeeBillNumber number where number.feeFinancialYear.id = :row");
			query.setInteger("row", year);
			number = (FeeBillNumber)query.uniqueResult();
		} catch (Exception e) {			
		log.warn("Exception occured while getting the row based on the year in IMPL :"+e);
			throw  new ApplicationException(e);
		} finally {
		if (session != null) {
			session.flush();
			//session.close();
		}
		}
		log.info("End of getFeeBillNoYear of FeeBillNumberTransactionImpl");
		return number;
	}
	
	/**
	 * Reactivation
	 * 
	 */
	public boolean reActivateFeeBillNumber(int year, String userId)throws Exception
	{
		log.info("Inside of reActivateFeeBillNumber of FeeBillNumberTransactionImpl");
		Session session = null;
		Transaction transaction = null;
			try {
				//SessionFactory sessionFactory = InitSessionFactory.getInstance();
				//session = sessionFactory.openSession();
				session = HibernateUtil.getSession();
				Query query = session.createQuery("from FeeBillNumber number where number.feeFinancialYear.id = :year");
				query.setInteger("year",year);
				FeeBillNumber number = (FeeBillNumber) query.uniqueResult();
				transaction = session.beginTransaction();
				number.setIsActive(true);
				number.setModifiedBy(userId);
				number.setLastModifiedDate(new Date());
				session.update(number);
				transaction.commit();
				return true;
				} catch (Exception e) {
				if(transaction != null){
					transaction.rollback();
				}
				log.error("Exception occured in reactivating of FeeBillNumber in IMPL :"+e);
				throw new ApplicationException(e);
				} finally {
					if (session != null) {
						session.flush();
						session.close();
					}
					log.info("End of reActivateFeeBillNumber of FeeBillNumberTransactionImpl");
				}
		}
	
	/**
	 * Used in editing
	 */
	
	public FeeBillNumber getFeeBillNumberDetailsonId(int id)throws Exception
	{
		log.info("Inside of getFeeBillNumberDetailsonId of FeeBillNumberTransactionImpl");
		Session session = null;
		FeeBillNumber number = null;
		try {
			//session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			Query query=session.createQuery("from FeeBillNumber number where number.id= :row");
			query.setInteger("row", id);
			number = (FeeBillNumber)query.uniqueResult();
		} catch (Exception e) {			
		log.error("Exception occured while getting the row based on the Id in IMPL :"+e);
			throw  new ApplicationException(e);
		} finally {
		if (session != null) {
			session.flush();
			//session.close();
		}
		}
		log.info("End of getFeeBillNumberDetailsonId of FeeBillNumberTransactionImpl");
		return number;
	}
	
	/**
	 * Used in update 
	 */
	
	public boolean updateFeeBillNumber(FeeBillNumber number)throws Exception
	{
		log.info("Inside of updateFeeBillNumber of FeeBillNumberTransactionImpl");
		Session session = null;
		Transaction transaction = null;
		try {
			//session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			session.update(number);
			transaction.commit();
			return true;
		} catch (Exception e) {	
			if(transaction != null){
				transaction.rollback();
			}
			log.error("Exception occured while updating FeeBillNumber in IMPL :"+e);
			throw  new ApplicationException(e);
		} finally {
		if (session != null) {
			session.flush();
			session.close();
		}
		log.info("End of updateFeeBillNumber of FeeBillNumberTransactionImpl");
		}
	}
	
	}
