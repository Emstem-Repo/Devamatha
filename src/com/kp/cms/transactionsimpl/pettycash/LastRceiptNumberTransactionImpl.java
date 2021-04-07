package com.kp.cms.transactionsimpl.pettycash;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.PcFinancialYear;
import com.kp.cms.bo.admin.PcReceiptNumber;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.transactions.pettycash.ILastReceiptnumbertransaction;
import com.kp.cms.utilities.HibernateUtil;

public class LastRceiptNumberTransactionImpl implements ILastReceiptnumbertransaction{

	private static final Log log = LogFactory.getLog(LastRceiptNumberTransactionImpl.class);
	
	private static volatile LastRceiptNumberTransactionImpl receiptNumberTransactionImpl;
	
	public static LastRceiptNumberTransactionImpl getInstance()
	{
		if(receiptNumberTransactionImpl==null)
		{
			receiptNumberTransactionImpl = new LastRceiptNumberTransactionImpl();
		return receiptNumberTransactionImpl;
		}
		return receiptNumberTransactionImpl;
	}
	
	/**
	 * Gets all receiptnumber details
	 */
	@Override
	public List<PcReceiptNumber> getReceiptnumberdetails() throws Exception {
		log.info("Inside of getReceiptnumberdetails of LastRceiptNumberTransactionImpl");
		Session session = null;
		List<PcReceiptNumber> receiptNumberList;
		try {
			//session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			receiptNumberList = session.createQuery("from PcReceiptNumber number where number.isActive = 1 order by number.id").list();
			
		} catch (Exception e) {			
			log.error("Exception occured in getting all receiptnumbers in LastRceiptNumberTransactionImpl :"+e);
			throw  new ApplicationException(e);
		} finally {
		if (session != null) {
			session.flush();
//			session.close();
		}
		}
		log.info("exiting of getReceiptnumberdetails of LastRceiptNumberTransactionImpl");
		return receiptNumberList;		
	}
	
	
	public List<PcFinancialYear> getFinancialYearList() throws Exception {
		log.debug("inside getFinancialYear list");
		Session session = null;
		try {
			/*SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();
			String sqlString ="from PcFinancialYear year where year.isActive = true";
			Query query = session.createQuery(sqlString);
			List<PcFinancialYear> fcFinancialYearList = query.list();
			return fcFinancialYearList;
		} catch (Exception e) {
			log.error("Exception occured in getting all financial years in getFinancialYearList :"+e);
			throw  new ApplicationException(e);		
	}
		finally
		{
			if (session != null) {
				session.flush();
				//session.close();
			}	
		}
	}
	
	public PcReceiptNumber getLastReceiptNumberYear(int year)throws Exception
	{
		log.info("Inside of getLastReceiptNumberYear of LastRceiptNumberTransactionImpl");
		Session session = null;
		PcReceiptNumber number = null;
		try {
			//session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			Query query=session.createQuery("from PcReceiptNumber number where number.pcFinancialYear.id = :row");
			query.setInteger("row", year);
			number = (PcReceiptNumber)query.uniqueResult();
		} catch (Exception e) {			
		log.warn("Exception occured while getting the row based on the year in IMPL :"+e);
			throw  new ApplicationException(e);
		} finally {
		if (session != null) {
			session.flush();
			session.close();
		}
		}
		log.info("Exiting getLastReceiptNumberYear of LastRceiptNumberTransactionImpl");
		return number;
	}

	
	
	/**
	 * Used to add lastreceiptNumber
	 */
	public boolean addLastReceiptNumber(PcReceiptNumber number)throws Exception
	{
		log.info("Inside of addLastReceiptNumber of LastRceiptNumberTransactionImpl");
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
			log.error("Exception occured in adding last receipt number in IMPL :"+e);
			throw  new ApplicationException(e);
		} finally {
		if (session != null) {
			session.flush();
			session.close();
		}
		log.info("Exiting addLastReceiptNumber of LastRceiptNumberTransactionImpl");
		}
	}
	
	/**
	 * Used while deleting
	 */
	public boolean deleteLastReceiptNumber(int receiptId, String userId)throws Exception
	{
		log.info("Inside of deleteLastReceiptNumber of LastRceiptNumberTransactionImpl");
		Session session = null;
		Transaction transaction = null;
		try {
			//session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			PcReceiptNumber pcReceiptNumber=(PcReceiptNumber)session.get(PcReceiptNumber.class,receiptId);
			pcReceiptNumber.setIsActive(false);
			pcReceiptNumber.setModifiedBy(userId);
			pcReceiptNumber.setLastModifiedDate(new Date());
			session.update(pcReceiptNumber);
			transaction.commit();
			return true;
		} catch (Exception e) {
			if(transaction != null){
				transaction.rollback();
			}
			log.error("Exception occured in deleting deleteLastReceiptNumber in IMPL :"+e);
			throw  new ApplicationException(e);
		} finally {
		if (session != null) {
			session.flush();
			session.close();
		}
		}
	}
	
	
	/**
	 * Reactivation
	 * 
	 */
	public boolean reActivateLastReceiptNumber(int year, String userId)throws Exception
	{
		log.info("Inside of reActivateLastReceiptNumber of LastRceiptNumberTransactionImpl");
		Session session = null;
		Transaction transaction = null;
			try {
				/*SessionFactory sessionFactory = InitSessionFactory.getInstance();
				session = sessionFactory.openSession();*/
				session = HibernateUtil.getSession();
				Query query = session.createQuery("from PcReceiptNumber number where number.pcFinancialYear.id = :year");
				query.setInteger("year",year);
				PcReceiptNumber number = (PcReceiptNumber) query.uniqueResult();
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
				log.error("Exception occured in reactivating of PcReceiptNumber in IMPL :"+e);
				throw new ApplicationException(e);
				} finally {
					if (session != null) {
						session.flush();
						//session.close();
					}
					log.info("End of reActivateLastReceiptNumber of LastRceiptNumberTransactionImpl");
				}
		}
	
	
	public PcReceiptNumber getLastReceiptNumberDetailsonId(int id)throws Exception
	{
		log.info("Inside of getLastReceiptNumberDetailsonId of LastRceiptNumberTransactionImpl");
		Session session = null;
		PcReceiptNumber number = null;
		try {
			//session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			Query query=session.createQuery("from PcReceiptNumber number where number.id= :row");
			query.setInteger("row", id);
			number = (PcReceiptNumber)query.uniqueResult();
		} catch (Exception e) {			
		log.error("Exception occured while getting the row based on the Id in IMPL :"+e);
			throw  new ApplicationException(e);
		} finally {
		if (session != null) {
			session.flush();
			//session.close();
		}
		}
		log.info("End of getLastReceiptNumberDetailsonId of LastRceiptNumberTransactionImpl");
		return number;
	}
	
	public boolean updateLastReceiptNumber(PcReceiptNumber number)throws Exception
	{
		log.info("Inside of updateLastReceiptNumber of LastRceiptNumberTransactionImpl");
		Session session = null;
		Transaction transaction = null;
		try {
//			SessionFactory sessionFactory = InitSessionFactory.getInstance();
//			session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			session.update(number);
			transaction.commit();
			return true;
		} catch (Exception e) {	
			if(transaction != null){
				transaction.rollback();
			}
			log.error("Exception occured while updating PcReceiptNumber in IMPL :"+e);
			throw  new ApplicationException(e);
		} finally {
		if (session != null) {
			session.flush();
			session.close();
		}
		log.info("Exiting updateLastReceiptNumber of LastRceiptNumberTransactionImpl");
		}
	}
}
