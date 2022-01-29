package com.kp.cms.transactionsimpl.admin;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.Preferences;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.admin.PreferencesForm;
import com.kp.cms.transactions.admin.IPreferencesTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class PreferencesTransactionImpl implements IPreferencesTransaction {
	public static volatile PreferencesTransactionImpl preferencesTransactionImpl = null;
	private static final Log log = LogFactory.getLog(PreferencesTransactionImpl.class);

	public static PreferencesTransactionImpl getInstance() {
		if (preferencesTransactionImpl == null) {
			preferencesTransactionImpl = new PreferencesTransactionImpl();
			return preferencesTransactionImpl;
		}
		return preferencesTransactionImpl;
	}

	/**
	 * This method add new Preferences to Database.
	 * 
	 * @return true / false based on result.
	 */

	public boolean addPreferences(Preferences preferences, String mode) {
		Session session = null;
		Transaction tx = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			if (mode.equalsIgnoreCase("Add")) {
				session.save(preferences);
			} else {
				session.update(preferences);
			}

			tx.commit();
			session.flush();
			session.close();
			return true;
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			if (session != null) {
				session.flush();
				session.close();
			}
			log.error("Error addPreferences method of impl class..." , e);			
			return false;
		}

	}

	/**
	 * This will retrieve all the Preferences records from database.
	 * 
	 * @return all Preferences
	 */

	public List<Preferences> getPreferences(int courseId, Boolean isFirstPageDisplay) throws Exception {
		Session session = null;
		List<Preferences> result;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();			
			if (isFirstPageDisplay) {
				Query query = session.createQuery("from Preferences a where isActive=1 group by courseByCourseId.id ");
				List<Preferences> list = query.list();
				session.flush();
				//session.close();
				//sessionFactory.close();
				result = list;

			} else {
				Query query = session.createQuery("from Preferences a where courseByCourseId.id = :courseId and isActive=1");
				query.setInteger("courseId", courseId);
				List<Preferences> list = query.list();
				session.flush();
				//session.close();
				//sessionFactory.close();
				result = list;
			}

		} catch (Exception e) {
			log.error("Error during getting Preferences..." , e);
			//session.flush();
			//session.close();
			throw new ApplicationException(e);
		}
		return result;
	}

	/**
	 * This will delete a Preferences from database.
	 * 
	 * @return true/false
	 * @throws Exception
	 */

	public boolean deletePreferences(int prefId, Boolean activate, PreferencesForm preferencesForm)	throws Exception {
		Session session = null;
		Transaction tx = null;
		boolean result = false;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();			
			tx = session.beginTransaction();
			tx.begin();
			Preferences preferences = (Preferences) session.get(Preferences.class, prefId);
			preferences.setIsActive(activate);
			preferences.setModifiedBy(preferencesForm.getUserId());
			preferences.setLastModifiedDate(new Date());
			session.update(preferences);
			//session.delete(preferences);
			tx.commit();
			session.flush();
			//session.close();
			result = true;
		} catch (ConstraintViolationException e) {
			if(tx!=null)
			    tx.rollback();
			log.error("Error during deleting Preferences data...",e);
			throw new BusinessException(e);
		} catch (Exception e) {
			if(tx!=null)
		    	tx.rollback();
			log.error("Error during deleting Preferences data...",e);
			throw new ApplicationException(e);
		}
		return result;
	}

	/**
	 * This will delete a Preferences from database.
	 * 
	 * @return true/false
	 * @throws Exception
	 */

	public boolean deleteAllPreferences(int courseId, PreferencesForm preferencesForm) throws Exception {
		Session session = null;
		Transaction tx = null;
		boolean result = false;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();			
			tx = session.beginTransaction();
			tx.begin();
			Query updatePreference = session.createQuery("delete Preferences where courseByCourseId.id = :courseId");
			updatePreference.setInteger("courseId", courseId);
			updatePreference.executeUpdate();
			tx.commit();
			session.flush();
			session.close();
			result = true;
		} catch (ConstraintViolationException e) {
			if(tx!=null)
			     tx.rollback();
			log.error("Error during deleting All Preferences data...",e);
			throw new BusinessException(e);
		} catch (Exception e) {
			if(tx!=null)
			     tx.rollback();
			log.error("Error during deleting All Preferences data...", e);
			throw new ApplicationException(e);
		}
		return result;
	}

	/**
	 * duplication checking for preference
	 */
	public Preferences isPreferencesDuplcated(Preferences oldpreferences) throws Exception {
		Session session = null;
		Preferences preferences;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();			
			Query query = session.createQuery("from Preferences a where courseByCourseId.id = :courseId and courseByPrefCourseId.id = :prefCourseId");
			query.setInteger("courseId", oldpreferences.getCourseByCourseId().getId());
			query.setInteger("prefCourseId", oldpreferences.getCourseByPrefCourseId().getId());
			preferences = (Preferences) query.uniqueResult();
			session.flush();
			//session.close();
			//sessionFactory.close();
		} catch (Exception e) {
			log.error("Error during duplcation checking..." + e);
			//session.flush();
			//session.close();
			throw new ApplicationException(e);
		}
		return preferences;
	}

	/**
	 * This will retrieve all the Preferences records from database.
	 * 
	 * @return all Preferences
	 */

	public List<Preferences> getPreferencesWithId(int id) throws Exception {
		Session session = null;
		List<Preferences> result;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			  session = HibernateUtil.getSession();			
		 {
				Query query = session.createQuery("from Preferences a where id = :id");
				query.setInteger("id", id);
				List<Preferences> list = query.list();
				session.flush();
				//session.close();
				//sessionFactory.close();
				result = list;
			}

		} catch (Exception e) {
			log.error("Error during getting Preferences..." + e);
			//session.flush();
			//session.close();
			throw new ApplicationException(e);
		}
		return result;
	}

	
		
}
