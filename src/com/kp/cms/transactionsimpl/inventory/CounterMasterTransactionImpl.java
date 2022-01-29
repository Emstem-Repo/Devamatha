package com.kp.cms.transactionsimpl.inventory;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.InvCounter;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.inventory.CounterMasterForm;
import com.kp.cms.transactions.inventory.ICounterMasterTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class CounterMasterTransactionImpl implements ICounterMasterTransaction{
	private static final Log log = LogFactory.getLog(CounterMasterTransactionImpl.class);
	public static volatile CounterMasterTransactionImpl counterMasterTransactionImpl = null;

	public static CounterMasterTransactionImpl getInstance() {
		if (counterMasterTransactionImpl == null) {
			counterMasterTransactionImpl = new CounterMasterTransactionImpl();
			return counterMasterTransactionImpl;
		}
		return counterMasterTransactionImpl;
	}	
	

	/**
	 * This will retrieve all the counter types from database for UI display.
	 * 
	 * @return all InvCounter
	 * @throws Exception
	 */

	public List<InvCounter> getCounterDetails() throws Exception {
		log.debug("inside getCounterDetails");
		Session session = null;
		
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from InvCounter i where i.isActive = 1");
			List<InvCounter> list = query.list();
			session.flush();
			//session.close();
			//sessionFactory.close();
			log.debug("leaving getCounterDetails");
			return list;
		 } catch (Exception e) {
			 log.error("Error in getCounterDetails...",e);
			 session.flush();
			 session.close();
			 throw  new ApplicationException(e);
		 }
	}
	/**
	 * duplication checking for Counter master
	 */
	public InvCounter isCounterDuplcated(CounterMasterForm counterMasterForm) throws Exception {
		log.debug("inside isCounterDuplcated");
		Session session = null;
		InvCounter invCounter;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			StringBuffer sqlString = new StringBuffer("from InvCounter i where i.type = :type and i.year=:academicYear");
			if(counterMasterForm.getType().equalsIgnoreCase(CMSConstants.PURCHASE_ORDER_COUNTER)){
				sqlString.append(" and i.invCompany.id ="+counterMasterForm.getCompanyId());
			}
			if(counterMasterForm.getId()!= 0){
				sqlString.append(" and id != :id");
			}
			Query query = session.createQuery(sqlString.toString());
			query.setString("type", counterMasterForm.getType());
			query.setInteger("academicYear", Integer.parseInt(counterMasterForm.getYear()));
			if(counterMasterForm.getId()!= 0){
				query.setInteger("id", counterMasterForm.getId());
			}
			
			invCounter = (InvCounter) query.uniqueResult();
			session.flush();
			//session.close();
			//sessionFactory.close();
		} catch (Exception e) {
			log.error("Error during duplcation checking..." , e);
			session.flush();
			session.close();
			throw new ApplicationException(e);
		}
		log.debug("leaving isCounterDuplcated");
		return invCounter;
	}

	/**
	 * This method add a single Disciplinary type to Database.
	 * 
	 * @return true / false based on result.
	 * @throws BusinessException 
	 */
	public boolean addCounterMaster(InvCounter invCounter, String mode) throws Exception {
		log.debug("inside addCounterMaster");
		Session session = null;
		Transaction transaction = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			if("edit".equalsIgnoreCase(mode)){
				session.update(invCounter);
			}
			else
			{
				session.save(invCounter);
			}
			transaction.commit();
			if (session != null) {
				session.flush();
				session.close();
			}
			log.debug("leaving addCounterMaster");
			return true;
		} catch (ConstraintViolationException e) {
			transaction.rollback();
			log.error("Error during in addCounterMaster..." , e);
			throw new BusinessException(e);
		} catch (Exception e) {
			transaction.rollback();
			log.error("Error in addCounterMaster..." , e);
			throw new ApplicationException(e);
		}

	}

	/**
	 * delete & reactivate
	 */
	public boolean deleteCounter(int id, Boolean activate, CounterMasterForm counterMasterForm) throws Exception {
		Session session = null;
		Transaction tx = null;
		boolean result = false;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			tx.begin();
			InvCounter invCounter = (InvCounter) session.get(InvCounter.class, id);
			if (activate) {
				invCounter.setIsActive(true);
			}else
			{
				invCounter.setIsActive(false);
			}
			invCounter.setModifiedBy(counterMasterForm.getUserId());
			invCounter.setLastModifiedDate(new Date());
			session.update(invCounter);
			tx.commit();
			session.flush();
			session.close();
			result = true;
		} catch (ConstraintViolationException e) {
			tx.rollback();
			log.error("Error in deleteCounter..." , e);
			throw new BusinessException(e);
		} catch (Exception e) {
			tx.rollback();
			log.error("Error in deleteCounter.." , e);
			throw new ApplicationException(e);
		}
		return result;
	}


	@Override
	public boolean isAlreadyExists(String query) throws Exception {
		log.debug("inside getCounterDetails");
		Session session = null;
		boolean isExists=false;
		try {
			session = HibernateUtil.getSession();
			Query q = session.createQuery(query);
			List<Object> list = q.list();
			session.flush();
			log.debug("leaving getCounterDetails");
			if(list!=null && !list.isEmpty()){
				isExists=true;
			}
			return isExists;
		 } catch (Exception e) {
			 log.error("Error in getCounterDetails...",e);
			 session.flush();
			 session.close();
			 throw  new ApplicationException(e);
		 }
	}


	@Override
	public InvCounter getCounterDetails(int id) throws Exception {
		log.debug("inside getCounterDetails");
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			InvCounter list =(InvCounter)session.get(InvCounter.class, id);
			session.flush();
			log.debug("leaving getCounterDetails");
			return list;
		 } catch (Exception e) {
			 log.error("Error in getCounterDetails...",e);
			 session.flush();
			 session.close();
			 throw  new ApplicationException(e);
		 }
	}
	
}
