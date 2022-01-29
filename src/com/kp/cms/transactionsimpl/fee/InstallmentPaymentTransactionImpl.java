package com.kp.cms.transactionsimpl.fee;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.FeeFinancialYear;
import com.kp.cms.bo.admin.FeePayment;
import com.kp.cms.bo.admin.FeePaymentMode;
import com.kp.cms.bo.admin.PcFinancialYear;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.transactions.fee.IInstallmentPaymentTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class InstallmentPaymentTransactionImpl implements IInstallmentPaymentTransaction{
	private static final Log log = LogFactory.getLog(InstallmentPaymentTransactionImpl.class);
	/**
	 * Gets all payment types
	 */
	public List<FeePaymentMode> getAllPaymentType() throws Exception {
		log.info("Inside of getAllPaymentType of InstallmentPaymentTransactionImpl");
		Session session = null;
		List<FeePaymentMode> boList=new ArrayList<FeePaymentMode>();
		try {
			//session =InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			boList = session.createQuery("from FeePaymentMode fee where fee.isActive = 1 order by fee.id").list();
			log.info("End of getAllPaymentType of InstallmentPaymentTransactionImpl");
			return boList;
		} catch (Exception e) {
		log.error("Exception ocured at getting all records of Fee Payment Mode :",e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
	}
	/**
	 * Gets student payment details
	 */	
	public List<FeePayment> getStudentPaymentDetails(String searchCriteria)
			throws Exception {
		log.info("entered getStudentPaymentDetails..");
		Session session = null;
		List<FeePayment> feePaymentList = null;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = HibernateUtil.getSession();
			feePaymentList = session.createQuery(searchCriteria).list();
			log.info("exit getStudentPaymentDetails..");
			return feePaymentList;
		} catch (Exception e) {
			log.error("error while getting the student in getStudentPaymentDetails "+e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
	}	
	/**
	 * Used to update FeePayment Details for Installment Payment
	 */	
	public boolean submitInstallMentPaymentDetails(
			List<FeePayment> installmentPaymentBOList) throws Exception {
		log.info("entered submitInstallMentPaymentDetails of InstallmentPaymentTransactionImpl");
		Transaction tx=null;
		Session session = null;
		FeePayment payment;
		boolean isSuccess = false;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			Iterator<FeePayment> itr = installmentPaymentBOList.iterator();
			int count = 0;
			tx = session.beginTransaction();
			tx.begin();
			while (itr.hasNext()) {
				payment = itr.next();
				session.saveOrUpdate(payment);
				if (++count % 20 == 0) {
					session.flush();
					session.clear();
				}
			}
			tx.commit();
			session.close();
			//sessionFactory.close();
			isSuccess = true;
		} catch (Exception e) {
			if(tx!=null){
				tx.rollback();
			}
			session.flush();
			session.clear();
			//session.close();
			log.error("Error occured submitInstallMentPaymentDetails of InstallmentPaymentTransactionImpl");
			throw new ApplicationException(e);
		}
		log.info("End of submitInstallMentPaymentDetails of InstallmentPaymentTransactionImpl");
		return isSuccess;
	}
	public List<FeeFinancialYear> getAllFinancialYear() throws Exception {
		log.info("Inside of getAllPaymentType of InstallmentPaymentTransactionImpl");
		Session session = null;
		List<FeeFinancialYear> boList=new ArrayList<FeeFinancialYear>();
		try {
			//session =InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			boList = session.createQuery("from FeeFinancialYear fee where fee.isActive = 1 order by fee.year").list();
			log.info("End of getAllPaymentType of InstallmentPaymentTransactionImpl");
			return boList;
		} catch (Exception e) {
		log.error("Exception ocured at getting all records of Fee Payment Mode :",e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
	}
	public List<PcFinancialYear> getAllPCFinancialYear() throws Exception{
		log.info("Inside of getAllPaymentType of InstallmentPaymentTransactionImpl");
		Session session = null;
		List<PcFinancialYear> boList=new ArrayList<PcFinancialYear>();
		try {
			//session =InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			boList = session.createQuery("from PcFinancialYear pc where pc.isActive = 1 order by pc.financialYear").list();
			log.info("End of getAllPCFinancialYear of InstallmentPaymentTransactionImpl");
			return boList;
		} catch (Exception e) {
		log.error("Exception ocured at getting all records of PcFinancial Year Mode :",e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
	}
}
