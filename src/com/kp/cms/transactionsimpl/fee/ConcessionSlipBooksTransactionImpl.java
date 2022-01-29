package com.kp.cms.transactionsimpl.fee;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.FeeFinancialYear;
import com.kp.cms.bo.admin.FeeVoucher;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.fee.ConcessionSlipBooksForm;
import com.kp.cms.transactions.fee.IConcessionSlipBooksTxn;
import com.kp.cms.utilities.HibernateUtil;

public class ConcessionSlipBooksTransactionImpl implements IConcessionSlipBooksTxn{
	private static final Log log = LogFactory.getLog(ConcessionSlipBooksTransactionImpl.class);
	public static ConcessionSlipBooksTransactionImpl concessionSlipBooksTransactionImpl = null;

	public static ConcessionSlipBooksTransactionImpl getInstance() {
		if (concessionSlipBooksTransactionImpl == null) {
			concessionSlipBooksTransactionImpl = new ConcessionSlipBooksTransactionImpl();
			return concessionSlipBooksTransactionImpl;
		}
		return concessionSlipBooksTransactionImpl;
	}	
	/**
	 * duplication checking for Slip book
	 */
	public FeeVoucher isBookNoDuplcated(ConcessionSlipBooksForm slipBooksForm) throws Exception {
		log.debug("inside isBookNoDuplcated");
		Session session = null;
		FeeVoucher feeVoucher;
		FeeVoucher result = feeVoucher = new FeeVoucher();
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			
			StringBuffer sqlString = new StringBuffer("from FeeVoucher f where f.slipBookNumber = '" + slipBooksForm.getBookNo() + "'");
			if(slipBooksForm.getId()!= 0){
				sqlString.append(" and id != :id");
			}
			Query query = session.createQuery(sqlString.toString());
			if(slipBooksForm.getId()!= 0){
				query.setInteger("id", slipBooksForm.getId());
			}
			
			feeVoucher = (FeeVoucher) query.uniqueResult();
			session.flush();
			//session.close();
			//sessionFactory.close();
			result = feeVoucher;
		} catch (Exception e) {
			log.error("Error during duplcation checking..." , e);
			session.flush();
			//session.close();
			throw new ApplicationException(e);
		}
		log.debug("leaving isBookNoDuplcated");
		return result;
	}
	
	/**
	 * This method add a one slip book entry to table.
	 * 
	 * @return true / false based on result.
	 * @throws BusinessException 
	 */
	public boolean addSlipBook(FeeVoucher feeVoucher, String mode) throws Exception {
		log.debug("inside addSlipBook");
		Session session = null;
		Transaction transaction = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			if("edit".equalsIgnoreCase(mode)){
				session.update(feeVoucher);
			}
			else
			{
				session.save(feeVoucher);
			}
			transaction.commit();
			if (session != null) {
				session.flush();
				session.close();
			}
			log.debug("leaving addSlipBook");
			return true;
		} catch (ConstraintViolationException e) {
			transaction.rollback();
			log.error("Error during in addSlipBook..." , e);
			throw new BusinessException(e);
		} catch (Exception e) {
			transaction.rollback();
			log.error("Error in addSlipBook..." , e);
			throw new ApplicationException(e);
		}

	}
	
	/**
	 * This will retrieve all the slip books from database for UI display.
	 * 
	 * @return all FeeVoucher
	 * @throws Exception
	 */

	public List<FeeVoucher> getSlipBookDetails() throws Exception {
		log.debug("inside getSlipBookDetails");
		Session session = null;
		
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from FeeVoucher i where i.isActive = 1");
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
	/**
	 * delete & reactivate
	 */
	public boolean deleteSlipBook(int id, Boolean activate, ConcessionSlipBooksForm slipBooksForm) throws Exception {
		Session session = null;
		Transaction tx = null;
		boolean result = false;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			tx.begin();
			FeeVoucher feeVoucher = (FeeVoucher) session.get(FeeVoucher.class, id);
			if (activate) {
				feeVoucher.setIsActive(true);
			}else
			{
				feeVoucher.setIsActive(false);
			}
			feeVoucher.setModifiedBy(slipBooksForm.getUserId());
			feeVoucher.setLastModifiedDate(new Date());
			session.update(feeVoucher);
			tx.commit();
			session.flush();
			session.close();
			result = true;
		} catch (ConstraintViolationException e) {
			tx.rollback();
			log.error("Error in deleteSlipBook..." , e);
			throw new BusinessException(e);
		} catch (Exception e) {
			tx.rollback();
			log.error("Error in deleteSlipBook.." , e);
			throw new ApplicationException(e);
		}
		return result;
	}
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public FeeFinancialYear getCurrentFeeFinancialYear() throws Exception {
		log.info("Start of getFeeFinancialYearDetails of FeeFinancialYearTransactionImpl");
		Session session = null;
		FeeFinancialYear feeFinancial = new FeeFinancialYear();
		try {
			//session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			feeFinancial = (FeeFinancialYear) session.createQuery(
					"from FeeFinancialYear f where f.isActive = 1 and f.isCurrent = 1")
					.uniqueResult();
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
		return feeFinancial;
	}	
	
	
}
