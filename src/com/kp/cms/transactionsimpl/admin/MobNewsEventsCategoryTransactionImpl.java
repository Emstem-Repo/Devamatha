package com.kp.cms.transactionsimpl.admin;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.Currency;
import com.kp.cms.bo.admin.MobNewsEventsCategory;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.transactions.admin.IMobNewsEventsCategoryTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class MobNewsEventsCategoryTransactionImpl implements IMobNewsEventsCategoryTransaction {
   
	
	private static final Logger log = Logger.getLogger(MobNewsEventsCategoryTransactionImpl.class);
	public static volatile MobNewsEventsCategoryTransactionImpl mobNewsEventsCategoryTransactionImpl = null;
	

	public static MobNewsEventsCategoryTransactionImpl getInstance() {
		if (mobNewsEventsCategoryTransactionImpl == null) {
			mobNewsEventsCategoryTransactionImpl = new MobNewsEventsCategoryTransactionImpl();
			return mobNewsEventsCategoryTransactionImpl;
			
			
		}
		return mobNewsEventsCategoryTransactionImpl;
	}

	/**
	 * This method is used to get all currency data form database.
	 */

	
	@Override
	public List<MobNewsEventsCategory> getMobNewsEventsCategory() throws Exception {
		log.info("call of getMobNewsEventsCategorys in MobNewsEventsCategoryTransactionImpl class.");
		Session session = null;
		List<MobNewsEventsCategory> mobNewsEventsCategoryList = null;
        try
        {
        	session = HibernateUtil.getSession();	
        	mobNewsEventsCategoryList = session.createQuery(
			"from MobNewsEventsCategory c where c.isActive = 1").list();
        	
        }catch (Exception e) {
			log.error("Unable to get getCurrencyMasters" ,e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		log.info("end of mobNewsEventsCategoryList in MobNewsEventsCategoryTransactionImpl class.");
		return mobNewsEventsCategoryList;
		
		
		
	}

	/**
	 * This method is used to create single instance of MobNewsEventsCategoryTransactionImpl.
	 * @return instance of MobNewsEventsCategoryTransactionImpl.
	 */
	public MobNewsEventsCategory isCategoryExis(String category) throws Exception {
		log.debug("inside isCategoryExisExist");
		Session session = null;
		MobNewsEventsCategory mobNewsEventsCategory;
		try {
			/*SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();
			String sqlString = "from MobNewsEventsCategory c where c.category ='"+category+"'";
			Query query = session.createQuery(sqlString);
			mobNewsEventsCategory = (MobNewsEventsCategory) query.uniqueResult();
			session.flush();
			//session.close();
			//sessionFactory.close();
			log.debug("mobNewsEventsCategory");
			return mobNewsEventsCategory;
		} catch (Exception e) {
			log.error("Error during duplcation checking..." , e);
			session.flush();
			//session.close();
			throw new ApplicationException(e);
		}
	}
	/**
	 * This method is used for adding a record of MobNewsEventsCategory to database. 
	 */

	@Override
	public boolean addMobNewsEventsCategory(
			MobNewsEventsCategory mobNewsEventsCategory) throws Exception {
		log.info("call of addMobNewsEventsCategory in MobNewsEventsCategory class.");
		Session session = null;
		Transaction transaction =null;
		boolean isAdded = false;
		try
		{
			session=HibernateUtil.getSession();
			transaction =  session.beginTransaction();
			session.save(mobNewsEventsCategory);
			transaction.commit();
			isAdded = true;
			session.flush();
		}catch (Exception e) {
			isAdded = false;
			
			log.error("Unable to addMobNewsEventsCategory" , e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		log.info("end of addMobNewsEventsCategory in MobNewsEventsCategoryTransactionImpl class.");
		return isAdded;
	}
	/**
	 * This method is used for deleting a record based on id.
	 */
	@Override
	public boolean deleteMobNewsEventsCategory(int id, String userId)
			throws Exception {
		log.info("call of deleteMobNewsEventsCategory in MobNewsEventsCategoryTransactionImpl class.");
		Session session = null;
		Transaction transaction = null;
		boolean isDeleted = false;
		try {
			/*SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			MobNewsEventsCategory mobNewsEventsCategory = (MobNewsEventsCategory) session.get(MobNewsEventsCategory.class, id);
			mobNewsEventsCategory.setLastModifiedDate(new Date());
			mobNewsEventsCategory.setModifiedBy(userId);
			mobNewsEventsCategory.setIsActive(Boolean.FALSE);
			session.update(mobNewsEventsCategory);
			transaction.commit();
			isDeleted = true;
		} catch (Exception e) {
			isDeleted = false;
			log.error("Unable to delete FeeHeadings" ,e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		log.info("end of deleteMobNewsEventsCategory in MobNewsEventsCategoryTransactionImpl class.");
		return isDeleted;
	}

	@Override
	public MobNewsEventsCategory editMobNewsEventsCategory(int id) throws Exception {
		
		log.info("call of editMobNewsEventsCategory in MobNewsEventsCategoryTransactionImpl class.");
		Session session=null;
		MobNewsEventsCategory mobNewsEventsCategory=null;
		try
		{
			session= HibernateUtil.getSession();
			Query query = session
			.createQuery("from MobNewsEventsCategory c where c.id = :Id");
	        query.setInteger("Id", id);
	        mobNewsEventsCategory = (MobNewsEventsCategory) query.uniqueResult();
			
			
		}catch (Exception e) {
			log.error("Unable to editMobNewsEventsCategory", e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		return mobNewsEventsCategory;
	}

	@Override
	public boolean reActivateMobNewsEventsCategory(int  dupId,
			String userId) throws Exception {
		log.info("call of reActivateMobNewsEventsCategory in MobNewsEventsCategoryTransactionImpl class.");
		Session session = null;
		Transaction transaction = null;
		boolean isActivated = false;
		try {
			/*SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from MobNewsEventsCategory c where c.id = :id");
			query.setInteger("id", dupId);
			MobNewsEventsCategory mobNewsEventsCategory = (MobNewsEventsCategory) query.uniqueResult();
			transaction = session.beginTransaction();
			mobNewsEventsCategory.setIsActive(true);
			mobNewsEventsCategory.setModifiedBy(userId);
			mobNewsEventsCategory.setLastModifiedDate(new Date());
			session.update(mobNewsEventsCategory);
			transaction.commit();
			isActivated = true;
		} catch (Exception e) {
			isActivated = false;
			log.error("Unable to reActivateMobNewsEventsCategory" , e);
			if (transaction != null) {
				transaction.rollback();
			}
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		log.info("end of reActivateMobNewsEventsCategory in MobNewsEventsCategoryTransactionImpl class.");
		return isActivated;
	}	
	/**
	 * This method is for update of category
	 */

	@Override
	public boolean updateMobNewsEventsCategory(
			MobNewsEventsCategory mobNewsEventsCategory) throws Exception {
		log.info("call of updateMobNewsEventsCategory in MobNewsEventsCategoryTransactionImpl class.");
		Session session=null;
		Transaction transaction=null;
		boolean isUpdated = false;
		try
		{
			session=HibernateUtil.getSession();
			transaction=session.beginTransaction();
			transaction.begin();
			session.update(mobNewsEventsCategory);
			transaction.commit();
			isUpdated=true;
			}
			catch (Exception e) {
				isUpdated = false;
				log.error("Unable to updateMobNewsEventsCategory", e);
				throw new ApplicationException(e);
			} finally {
				if (session != null) {
					session.flush();
					session.close();
				}
			}
			log.info("end of updateMobNewsEventsCategory in MobNewsEventsCategoryTransactionImpl class.");
			return isUpdated;
		}

	/**
	 * This method is for reactivation of category
	 */
	
			
		
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

