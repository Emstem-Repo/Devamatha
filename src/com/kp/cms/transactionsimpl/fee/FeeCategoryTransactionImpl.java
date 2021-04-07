package com.kp.cms.transactionsimpl.fee;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.fees.FeeCategory;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.transactions.fee.IFeeCategoryTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class FeeCategoryTransactionImpl implements IFeeCategoryTransaction{
	private static final Log log = LogFactory.getLog(FeeCategoryTransactionImpl.class);

	@Override
	public boolean addFeeCategory(FeeCategory feeCategory) throws Exception {
		Session session = null;
		Transaction transaction = null;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			session.save(feeCategory);
			transaction.commit();
			session.flush();
			//session.close();
			return true;
		} catch (Exception e) {
			if ( transaction != null){
				transaction.rollback();
			}
			if (session != null){
				session.flush();
				//session.close();
			}
			return false;
		}
	}

	@Override
	public boolean deleteFeeCategory(int feeCategoryId,
			String userId) throws Exception {
		Session session = null;
		Transaction transaction = null;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			FeeCategory feeCategory = (FeeCategory) session.get(FeeCategory.class, feeCategoryId);
			feeCategory.setIsActive(false);
			feeCategory.setLastModifiedDate(new Date());
			feeCategory.setModifiedBy(userId);
			session.update(feeCategory);
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
	public boolean reActivateFeeCategory(FeeCategory feeCategory, String userId)
			throws Exception {
		Session session = null;
		Transaction transaction = null;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			feeCategory.setModifiedBy(userId);
			feeCategory.setLastModifiedDate(new Date());
			feeCategory.setIsActive(true);
			session.update(feeCategory);
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
	public List<FeeCategory> getFeeCategories() throws Exception {
		Session session = null;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			List<FeeCategory> feeCategoryList = session.createQuery("from FeeCategory c where c.isActive=1").list();
			session.flush();
			//session.close();
			return feeCategoryList;
		} catch (Exception e) {
			if (session != null){
				session.flush();
				//session.close();
			}
			return null;
		}
	}

	@Override
	public FeeCategory isFeeCategoryDuplcated(FeeCategory oldFeeCategory)
			throws Exception {
		log.debug("impl: inside isFeeCategoryDuplcated");
		Session session = null;
		FeeCategory feeCategory ;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			
			Query query = session
					.createQuery("from FeeCategory c where name = :name");
			query.setString("name", oldFeeCategory.getName());
			feeCategory = (FeeCategory) query.uniqueResult();
			session.flush();
			//session.close();
			//sessionFactory.close();
			log.debug("impl: leaving isFeeCategoryDuplcated");
		} catch (Exception e) {
			log.error("Error during duplcation checking..." + e);
			session.flush();
			//session.close();
			throw new ApplicationException(e);
		}
		return feeCategory;
	}

	@Override
	public boolean updateFeeCategory(FeeCategory feeCategory) throws Exception {
		Session session = null;
		Transaction transaction = null;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			session.merge(feeCategory);
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
