package com.kp.cms.transactionsimpl.admin;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.Recommendor;
import com.kp.cms.transactions.admin.IRecommendedTxn;
import com.kp.cms.utilities.HibernateUtil;

public class RecommendedTxnImpl implements IRecommendedTxn {
	
	private static volatile RecommendedTxnImpl recommendedTxnImpl = null;

	private static Log log = LogFactory.getLog(RecommendedTxnImpl.class);
	
	/**
	 * 
	 * @return List
	 */
	public List<Recommendor> getRecommendor() {
		log.info("call of getRecommendor in RecommendedTxnImpl class.");
		List<Recommendor> recommendlist = null;
		Session dbSession =null;
		try {
			//SessionFactory sessfactory = HibernateUtil.getSessionFactory();
			//dbSession = sessfactory.openSession();
			dbSession = HibernateUtil.getSession();
			recommendlist = new ArrayList<Recommendor>();
			Query q = dbSession.createQuery("from Recommendor r");
			recommendlist = q.list();
			dbSession.flush();
			//dbSession.close();
			return recommendlist;
		} catch (ConstraintViolationException e) {
			log.error("error occured in getRecommendor in RecommendedTxnImpl class.", e);
			dbSession.flush();
			dbSession.close();
			throw e;
		} catch (Exception e) {
			log.error("error occured in getRecommendor in RecommendedTxnImpl class.", e);
			if(dbSession!=null){
			dbSession.flush();
			dbSession.close();
			}
			log.info("call of getRecommendor in RecommendedTxnImpl class.");
			return recommendlist;
		}
	}
	public static RecommendedTxnImpl getInstance() {
		   if(recommendedTxnImpl == null ){
			   recommendedTxnImpl = new RecommendedTxnImpl();
			   return recommendedTxnImpl;
		   }
		   return recommendedTxnImpl;
	}
}