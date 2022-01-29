package com.kp.cms.transactionsimpl.admin;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.Recommendor;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.transactions.admin.IRecommendedByTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class RecommendedByTransactionImpl implements IRecommendedByTransaction{
	
	
	private static final Log log = LogFactory.getLog(RecommendedByTransactionImpl.class);
	
	/**
	 * Used for adding a recommendedBy 
	 */
	
	public boolean addRecommendedBy(Recommendor recommendor) throws Exception
	{
		Session session = null;
		Transaction transaction = null;
		try {
			//session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			session.save(recommendor);
			transaction.commit();
			return true;
		} catch (Exception e) {
			if(transaction != null){
				transaction.rollback();
			}
			log.error("Exception occured in adding RecommendedBy in IMPL :"+e);
			throw  new ApplicationException(e);
		} finally {
		if (session != null) {
			session.flush();
			session.close();
		}
		}
	}
	
	/**
	 * Gets all the recommendedBy records from DB where isActive=1
	 */
	

	public List<Recommendor> getRecommendedByDetails()throws Exception{
		Session session = null;
		List<Recommendor> recommendedByList;
		try {
			//session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			recommendedByList = session.createQuery("from Recommendor rec where isActive = 1 ").list();
		} catch (Exception e) {			
			log.error("Exception occured in getRecommendedByDetails in RecommendedByIMPL :"+e);
			throw  new ApplicationException(e);
		} finally {
		if (session != null) {
			session.flush();
			//session.close();
		}
		}
		return recommendedByList;		
	}
	
	/**
	 * Deletes a recommendedBy (Makes isActive=1)
	 * 
	 */
	
	public boolean deleteRecommendedBy(int id, String userId) throws Exception{
		Session session = null;
		Transaction transaction = null;
		try {
			//session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			Recommendor recommendor=(Recommendor)session.get(Recommendor.class,id);
			recommendor.setIsActive(false);
			recommendor.setLastModifiedDate(new Date());
			recommendor.setModifiedBy(userId);
			session.update(recommendor);
			transaction.commit();
			return true;
		} catch (Exception e) {
			if(transaction != null){
				transaction.rollback();
			}
			log.error("Exception occured in deleting RecommendedBy in IMPL :"+e);	
			throw  new ApplicationException(e);
		} finally {
		if (session != null) {
			session.flush();
			session.close();
		}
		}
	}
	
	/***
	 * Updates a recommendedBy 
	 */
	
	public boolean updateRecommendedBy(Recommendor recommendor)throws Exception
	{
		Session session = null;
		Transaction transaction = null;
		try {
			//session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			session.update(recommendor);
			transaction.commit();
			return true;
		} catch (Exception e) {
			if(transaction != null){
				transaction.rollback();
			}
			log.error("Exception occured while updating RecommendedBy in IMPL :"+e);
			throw  new ApplicationException(e);
		} finally {
		if (session != null) {
			session.flush();
			session.close();		
		}
		}
	}
	
	/**
	 * Check for duplicate record on code 
	 */
	
	public Recommendor checkForDuplicateonCode(String code)throws Exception
	{
		Session session = null;
		Recommendor recommendor = null;
		try {
			//session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			Query query=session.createQuery("from Recommendor rec where rec.code= :cd");
			query.setString("cd", code);
			recommendor = (Recommendor)query.uniqueResult();
		} catch (Exception e) {			
		log.error("Exception occured in checking duplicate records for recommendedBy in IMPL :"+e);
			throw  new ApplicationException(e);
		} finally {
		if (session != null) {
			session.flush();
			//session.close();
		}
		}
		return recommendor;	
	}
	
	/**
	 * Reactivate a recommendedBy (Makes isActive=0 to 1)
	 */
	public boolean reActivateRecommendedBy(String code, String userId)throws Exception
	{
		Session session = null;
		Transaction transaction = null;
			try {
				//SessionFactory sessionFactory = InitSessionFactory.getInstance();
				//session = sessionFactory.openSession();
				session = HibernateUtil.getSession();
				Query query = session.createQuery("from Recommendor rec where rec.code = :re");
				query.setString("re",code);
				Recommendor recommendor = (Recommendor) query.uniqueResult();
				transaction = session.beginTransaction();
				recommendor.setIsActive(true);
				recommendor.setModifiedBy(userId);
				recommendor.setLastModifiedDate(new Date());
				session.update(recommendor);
				transaction.commit();
				return true;
				} catch (Exception e) {
				if(transaction != null){
					transaction.rollback();
				}
				log.error("Exception occured in reactivating of recommendedBy in IMPL :"+e);
				throw new ApplicationException(e);
				} finally {
					if (session != null) {
						session.flush();
						session.close();
					}
				}			
			}
	
	/**
	 * Returns a row based on the id
	 */
	public Recommendor getDetailsonId(int id)throws Exception
	{
		Session session = null;
		Recommendor recommendor = null;
		try {
			//session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			
			Query query=session.createQuery("from Recommendor rec where rec.id= :row");
			query.setInteger("row", id);
			recommendor = (Recommendor)query.uniqueResult();
		} catch (Exception e) {			
		log.error("Exception occured while getting the row based on the Id in IMPL :"+e);
			throw  new ApplicationException(e);
		} finally {
		if (session != null) {
			session.flush();
			//session.close();
		}
		}
		return recommendor;	
	}
}
