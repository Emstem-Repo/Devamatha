package com.kp.cms.transactionsimpl.fee;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.FeeAccount;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.transactions.fee.IFeeAccountTransaction;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;

/**
 * 
 *
 */
public class FeeAccountTransactionImpl implements IFeeAccountTransaction {
	private static final Log log = LogFactory
	.getLog(FeeAccountTransactionImpl.class);
	private static FeeAccountTransactionImpl feeAccountTransaction = null;
	
	public static FeeAccountTransactionImpl getInstance() {
		   if(feeAccountTransaction == null ){
			   feeAccountTransaction = new FeeAccountTransactionImpl();
			   return feeAccountTransaction;
		   }
		   return feeAccountTransaction;
	}
	
	@Override
	public List getAllFeeAccounts() throws Exception{
		log.info("call of getAllFeeAccounts in FeeAccountTransactionImpl class");
		Session session = null;
		try {
			 //SessionFactory sessionFactory = InitSessionFactory.getInstance();
			 session = HibernateUtil.getSession();
			 Query query = session.createQuery("from FeeAccount f where f.isActive = 1");
			 List<FeeAccount> list = query.list();
			 //session.close();
			 //sessionFactory.close();
			 log.info("end of getAllFeeAccounts in FeeAccountTransactionImpl class");
			 return list;
		 } catch (Exception e) {
			 log.error("Error during getAllFeeAccounts data..." , e);
			 throw e;
		 }
	}
	
	@Override
	public List getAllFeeAccountIds() throws Exception{
		log.info("call of getAllFeeAccountIds in FeeAccountTransactionImpl class");
		Session session = null;
		try {
			 //SessionFactory sessionFactory = InitSessionFactory.getInstance();
			 session = HibernateUtil.getSession();
			 Query query = session.createQuery("select f.id from FeeAccount f where f.isActive = 1");
			 List<Integer> list = query.list();
			 //session.close();
			 //sessionFactory.close();
			 log.info("end of getAllFeeAccountIds in FeeAccountTransactionImpl class");
			 return list;
		 } catch (Exception e) {
			 log.error("Error during getAllFeeAccountIds data..." , e);
			 throw e;
		 }
	}
	
	
	public FeeAccount existanceCheck(FeeAccount  feeAccount) throws Exception{
		log.info("call of existanceCheck in FeeAccountTransactionImpl class");
		Session session = null;
		try
		{
		//SessionFactory sessionFactory = InitSessionFactory.getInstance();
		session = HibernateUtil.getSession();
		String studenttypeHibernateQuery = "from FeeAccount where code=:Name";
		Query query = session.createQuery(studenttypeHibernateQuery);
		query.setString("Name", feeAccount.getCode());
		feeAccount =(FeeAccount) query.uniqueResult();
		log.info("end of existanceCheck in FeeAccountTransactionImpl class");
		return (feeAccount!=null)?feeAccount:null;
		}catch (Exception e) {
			
			log.error("Error during getting Subject loading...",e);
			if (session != null) {
				session.flush();
				//session.close();
			}
			throw  new ApplicationException(e);
		}
	}
	
	public boolean addFeeAccount(FeeAccount  feeAccount,String mode) throws Exception{
		log.info("call of addFeeAccount in FeeAccountTransactionImpl class");
		Session session = null;
		Transaction transaction = null;
		try {
			SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session=sessionFactory.openSession();
//			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			if(mode.equalsIgnoreCase("Add")){
				session.save(feeAccount);	
			}else{
				session.update(feeAccount);
			}
			transaction.commit();
			session.flush();
			session.close();
			log.info("end of addFeeAccount in FeeAccountTransactionImpl class");
			return true;
		} catch (ConstraintViolationException e) {
			transaction.rollback();
			log.error("Error during saving fee account data..." , e);
			throw new BusinessException(e);
		} catch (Exception e) {
			transaction.rollback();
			log.error("Error during fee account data..." , e);
			throw new ApplicationException(e);
		}
		
	}
	public List<FeeAccount> getFeeAccounts() throws Exception{
		log.info("call of getFeeAccounts in FeeAccountTransactionImpl class");
		Session session = null;
		try {
			 //SessionFactory sessionFactory = InitSessionFactory.getInstance();
			 session = HibernateUtil.getSession();
			 Query query = session.createQuery("from FeeAccount where isActive=1");
			 List<FeeAccount> list = query.list();
			 //session.close();
			 //sessionFactory.close();
			 log.info("end of getFeeAccounts in FeeAccountTransactionImpl class");
			 return list;
		 } catch (Exception e) {
			 log.error("Error during getFeeAccounts..." , e);
			 throw  new ApplicationException(e);
		 }
	}
	
	public FeeAccount loadFeeAccount(FeeAccount feeAccount) throws Exception{
		log.info("call of loadFeeAccount in FeeAccountTransactionImpl class");
		Session session = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			feeAccount=(FeeAccount) session.get(FeeAccount.class, feeAccount.getId());
			session.flush();
			//session.close();
			log.info("end of loadFeeAccount in FeeAccountTransactionImpl class");
			return feeAccount!=null?feeAccount:null;
		} catch (Exception e) {
			 log.error("Error during loadFeeAccount..." , e);
			 session.flush();
			 //session.close();
			 throw  new ApplicationException(e);
		 }
	}

	@Override
	public FeeAccount getFeeAccountData(int id) throws Exception {
		log.info("call of getFeeAccountData in FeeAccountTransactionImpl class.");
		Session session = null;
		FeeAccount feeAccount = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from FeeAccount fc where fc.isActive = 1 and fc.id = :Id");
			query.setInteger("Id", id);
			feeAccount = (FeeAccount) query.uniqueResult();
		} catch (Exception e) {
			log.error("Unable to getFeeAccountData", e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		log.info("end of getFeeAccountData in FeeAccountTransactionImpl class.");
		return feeAccount;
	}
}