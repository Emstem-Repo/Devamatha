package com.kp.cms.transactionsimpl.admin;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.Caste;
import com.kp.cms.bo.admin.ExtraCreditActivityType;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.transactions.admin.IExtraCreditActivityTypeTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class ExtraCreditActivityTypeImpl implements IExtraCreditActivityTypeTransaction{
	 private static final Log log = LogFactory.getLog(ExtraCreditActivityTypeImpl.class);
	 
	 private static volatile ExtraCreditActivityTypeImpl extraCreditActivityTypeImpl =null;
	 
	 public ExtraCreditActivityTypeImpl(){
		 
	 }
	 public static ExtraCreditActivityTypeImpl getInstance(){
		 if(extraCreditActivityTypeImpl==null){
			 extraCreditActivityTypeImpl = new ExtraCreditActivityTypeImpl();
			 return extraCreditActivityTypeImpl;
		 }else{
			 return extraCreditActivityTypeImpl;
		 }
	 }
	 
	 
	 
	 public List<ExtraCreditActivityType> getActivityList(){
			Session session = null;
			try {
				session = HibernateUtil.getSession();
				List<ExtraCreditActivityType> activityList = session.createQuery("from ExtraCreditActivityType c where c.isActive=1").list();
				session.flush();
				return activityList;
			} catch (Exception e) {
				if (session != null){
					session.flush();
				}
				return null;
			}
		}
	 
	 public ExtraCreditActivityType isActivityDuplicated(
				ExtraCreditActivityType extraCreditActivityType1)throws Exception {
			log.debug("impl: inside isActivityDuplicated");
			Session session = null;
			ExtraCreditActivityType type ;
			try {
				session = HibernateUtil.getSession();
				Query query = session
						.createQuery("from ExtraCreditActivityType c where c.name = :name");
				query.setString("name", extraCreditActivityType1.getName());
				type = (ExtraCreditActivityType) query.uniqueResult();
				session.flush();
				log.debug("impl: leaving isActivityDuplicated");
			} catch (Exception e) {
				log.error("Error during duplcation checking..." + e);
				session.flush();
				throw new ApplicationException(e);
			}
			return type;
		}
	@Override
	public boolean addActivity(ExtraCreditActivityType extraCreditActivityType)
			throws Exception {
		Session session = null;
		Transaction transaction = null;
		try {
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			session.save(extraCreditActivityType);
			transaction.commit();
			session.flush();
			return true;
		} catch (Exception e) {
			if ( transaction != null){
				transaction.rollback();
			}
			if (session != null){
				session.flush();
			}
			return false;
		}
	}
	@Override
	public boolean editActivity(ExtraCreditActivityType extraCreditActivityType)
			throws Exception {
		Session session = null;
		Transaction transaction = null;
		try {
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			session.merge(extraCreditActivityType);
			transaction.commit();
			session.flush();
			session.close();
			return true;
		} catch (Exception e) {
			if ( transaction != null){
				transaction.rollback();
			}
			if (session != null){
				session.flush();
				session.close();
			}
			return false;
	}
  }
	@Override
	public boolean deleteCaste(int activityId, String userId)
			throws Exception {
		Session session = null;
		Transaction transaction = null;
		try {
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			ExtraCreditActivityType activityType = (ExtraCreditActivityType) session.get(ExtraCreditActivityType.class, activityId);
			activityType.setIsActive(false);
			activityType.setLastModifiedDate(new Date());
			activityType.setModifiedBy(userId);
			session.update(activityType);
			transaction.commit();
			session.flush();
			session.close();
			return true;
		} catch (Exception e) {
			if ( transaction != null){
				transaction.rollback();
			}
			if (session != null){
				session.flush();
				session.close();
			}
			return false;
	}
  }
	@Override
	public boolean reActivateCaste(ExtraCreditActivityType type, String userId)
			throws Exception {
		Session session = null;
		Transaction transaction = null;
		try {
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			type.setModifiedBy(userId);
			type.setLastModifiedDate(new Date());
			type.setIsActive(true);
			session.update(type);
			transaction.commit();
			session.flush();
			session.close();
			return true;
		} catch (Exception e) {
			if ( transaction != null){
				transaction.rollback();
			}
			if (session != null){
				session.flush();
				session.close();
			}
			return false;
	}
  }
	
	
	
	
	
}



