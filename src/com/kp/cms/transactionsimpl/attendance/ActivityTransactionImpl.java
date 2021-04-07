package com.kp.cms.transactionsimpl.attendance;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.Activity;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.transactions.attandance.IActivityTransaction;
import com.kp.cms.utilities.HibernateUtil;


public class ActivityTransactionImpl  implements IActivityTransaction{
	public static volatile ActivityTransactionImpl activityTransactionImpl = null;
	private static final Log log = LogFactory.getLog(ActivityTransactionImpl.class);

	public static ActivityTransactionImpl getInstance() {
		if (activityTransactionImpl == null) {
			activityTransactionImpl = new ActivityTransactionImpl();
			return activityTransactionImpl;
		}
		return activityTransactionImpl;
	}
	/**
	 * getting all the activities
	 */

	public List<Activity> getActivities() throws Exception {
		log.debug("inside getActivities");
		Session session = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			
			Query query = session.createQuery("from Activity a where isActive=1");
			List<Activity> list = query.list();
			session.flush();
			//session.close();
//			sessionFactory.close();
			log.debug("leaving getActivities");
			return list;
		 } catch (Exception e) {
			 log.error("Error in getActivities...",e);
			 session.flush();
			 //session.close();
			 throw  new ApplicationException(e);
		 }
	}

	/**
	 * duplication checking
	 */
	public Activity isActivityNameDuplcated(Activity duplActivity) throws Exception {
		log.debug("inside isActivityNameDuplcated");
		Session session = null;
		Activity activity;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from Activity a where name = :activityName and attendanceType.id = :attId");
			query.setString("activityName", duplActivity.getName());
			query.setInteger("attId", duplActivity.getAttendanceType().getId());
			activity = (Activity) query.uniqueResult();
			session.flush();
			//session.close();
//			sessionFactory.close();
			log.debug("leaving isActivityNameDuplcated");
			return activity;
		} catch (Exception e) {
			log.error("Error during duplcation checking...", e);
			session.flush();
			//session.close();
			throw new ApplicationException(e);
		}
	}
	/**
	 * adding activity to table
	 */
	public boolean addActivity(Activity activity) throws Exception {
		log.debug("inside addActivity");
		Session session = null;
		Transaction tx = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			tx.begin();
			session.save(activity);
			tx.commit();
			if (session != null) {
				session.flush();
				session.close();
			}
			log.debug("leaving addActivity");
			return true;
		} catch (ConstraintViolationException e) {
			tx.rollback();
			log.error("Error during saving Activity data..." , e);
			throw new BusinessException(e);
		} catch (Exception e) {
			tx.rollback();
			log.error("Error during saving Activity data...", e);
			throw new ApplicationException(e);
		}

	}

	/**
	 * update activity
	 */
	public boolean updateActivity(Activity activity) throws Exception{
		log.debug("inside updateActivity");
		Session session = null;
		Transaction tx = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			tx.begin();
			session.update(activity);
			tx.commit();
			if (session != null) {
				session.flush();
				session.close();
			}
			return true;
		} catch (ConstraintViolationException e) {
			tx.rollback();
			log.error("Error during updateActivity data...", e);
			throw new BusinessException(e);
		} catch (Exception e) {
			tx.rollback();
			log.error("Error during updateActivity data..." + e);
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
		Transaction tx = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			tx.begin();
			Activity activity = (Activity) session.get(Activity.class, actId);
			if (activate) {
				activity.setIsActive(true);
			} else {
				activity.setIsActive(false);
			}
			activity.setLastModifiedDate(new Date());
			activity.setModifiedBy(userId);
			
			session.update(activity);
			tx.commit();
			session.flush();
			session.close();
			log.debug("leaving deleteActivity");
			return true;
		} catch (ConstraintViolationException e) {
			tx.rollback();
			log.error("Error during deleting activity data..." + e);
			throw new BusinessException(e);
		} catch (Exception e) {
			tx.rollback();
			log.error("Error during deleting activity data..." + e);
			throw new ApplicationException(e);
		}
	}
	
}
