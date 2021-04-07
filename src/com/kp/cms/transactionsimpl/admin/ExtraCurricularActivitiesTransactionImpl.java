package com.kp.cms.transactionsimpl.admin;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.ExtracurricularActivity;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.transactions.admin.IExtraCurricularActivitiesTransaction;
import com.kp.cms.utilities.HibernateUtil;


public class ExtraCurricularActivitiesTransactionImpl  implements IExtraCurricularActivitiesTransaction{
	public static volatile ExtraCurricularActivitiesTransactionImpl extraCurricularActivitiesTransactionImpl = null;
	private static final Log log = LogFactory.getLog(ExtraCurricularActivitiesTransactionImpl.class);

	public static ExtraCurricularActivitiesTransactionImpl getInstance() {
		if (extraCurricularActivitiesTransactionImpl == null) {
			extraCurricularActivitiesTransactionImpl = new ExtraCurricularActivitiesTransactionImpl();
			return extraCurricularActivitiesTransactionImpl;
		}
		return extraCurricularActivitiesTransactionImpl;
	}
	
	/**
	 * This will retrieve all the Activities from database. for UI display
	 * 
	 * @return all activities
	 * @throws Exception
	 */

	public List<ExtracurricularActivity> getActivities() throws Exception {
		log.debug("inside getActivities");
		Session session = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from ExtracurricularActivity p where isActive=1");
			List<ExtracurricularActivity> list = query.list();
			session.flush();
			//session.close();
			//sessionFactory.close();
			log.debug("leaving getActivities");
			return list;
		 } catch (Exception e) {
			 log.error("Error during getting College Names...",e);
			 //session.flush();
			 //session.close();
			 throw  new ApplicationException(e);
		 }
	}

	/**
	 * checking for activity name duplication
	 */
	public ExtracurricularActivity isActivityNameDuplcated(ExtracurricularActivity duplExtracurricularActivity) throws Exception {
		log.debug("inside isActivityNameDuplcated");
		Session session = null;
		ExtracurricularActivity extracurricularActivity;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from ExtracurricularActivity a where name = :activityName and organisation.id = :orgId");
			query.setString("activityName", duplExtracurricularActivity.getName());
			query.setInteger("orgId", duplExtracurricularActivity.getOrganisation().getId());
			extracurricularActivity = (ExtracurricularActivity) query.uniqueResult();
			session.flush();
			//session.close();
			//sessionFactory.close();
			log.debug("leaving isActivityNameDuplcated");
			return extracurricularActivity;
		} catch (Exception e) {
			log.error("Error during duplcation checking..." , e);
			//session.flush();
			//session.close();
			throw new ApplicationException(e);
		}
	}
	/**
	 * This method add a single ExtracurricularActivity to table.
	 * 
	 * @return true / false based on result.
	 * @throws BusinessException 
	 */
	public boolean addActivity(ExtracurricularActivity extracurricularActivity) throws Exception {
		log.debug("inside addActivity");
		Session session = null;
		Transaction transaction = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			session.save(extracurricularActivity);
			transaction.commit();
			if (session != null) {
				session.flush();
				session.close();
			}
			log.debug("leaving addActivity");
			return true;
		} catch (ConstraintViolationException e) {
			transaction.rollback();
			log.error("Error during saving ExtracurricularActivity data..." , e);
			throw new BusinessException(e);
		} catch (Exception e) {
			if(transaction!=null)
			     transaction.rollback();
			log.error("Error during saving ExtracurricularActivity data..." , e);
			throw new ApplicationException(e);
		}

	}

	/**
	 * This method update the activity to table.
	 * 
	 * @return true / flase based on result.
	 */
	public boolean updateActivity(ExtracurricularActivity extracurricularActivity) throws Exception{
		log.debug("inside updateActivity");
		Session session = null;
		Transaction transaction = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			session.update(extracurricularActivity);
			transaction.commit();
			if (session != null) {
				session.flush();
				session.close();
			}
			return true;
		} catch (ConstraintViolationException e) {
			transaction.rollback();
			log.error("Error during updateActivity data..." , e);
			throw new BusinessException(e);
		} catch (Exception e) {
			if(transaction!=null)
			     transaction.rollback();
			log.error("Error during updateActivity data...", e);
			throw new ApplicationException(e);
		}
	}

	/**
	 * This method delete activity from table.
	 * 
	 * @return true / false based on result.
	 */
	public boolean deleteActivity(int actId, Boolean activate, String userId) throws Exception {
		log.debug("inside deleteInstitute");
		Session session = null;
		Transaction transaction = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			ExtracurricularActivity extracurricularActivity = (ExtracurricularActivity) session.get(ExtracurricularActivity.class, actId);
			if (activate) {
				extracurricularActivity.setIsActive(true);
			} else {
				extracurricularActivity.setIsActive(false);
			}
			extracurricularActivity.setLastModifiedDate(new Date());
			extracurricularActivity.setModifiedBy(userId);
			
			session.update(extracurricularActivity);
			transaction.commit();
			session.flush();
			session.close();
			log.debug("leaving deleteActivity");
			return true;
		} catch (ConstraintViolationException e) {
			if(transaction!=null)
			     transaction.rollback();
			log.error("Error during deleting ExtracurricularActivity data..." , e);
			throw new BusinessException(e);
		} catch (Exception e) {
			transaction.rollback();
			log.error("Error during deleting ExtracurricularActivity data..." , e);
			throw new ApplicationException(e);
		}
	}
	
}
