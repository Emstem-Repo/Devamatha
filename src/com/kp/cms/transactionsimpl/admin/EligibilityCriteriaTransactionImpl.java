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

import com.kp.cms.bo.admin.EligibilityCriteria;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.forms.admin.EligibilityCriteriaForm;
import com.kp.cms.transactions.admin.IEligibilityCriteria;
import com.kp.cms.utilities.HibernateUtil;

public class EligibilityCriteriaTransactionImpl implements IEligibilityCriteria{
	private static final Log log = LogFactory	.getLog(EligibilityCriteriaTransactionImpl.class);
	public static volatile EligibilityCriteriaTransactionImpl eligibilityCriteriaTransactionImpl = null;
	public static EligibilityCriteriaTransactionImpl getInstance() {
		if (eligibilityCriteriaTransactionImpl == null) {
			eligibilityCriteriaTransactionImpl = new EligibilityCriteriaTransactionImpl();
			return eligibilityCriteriaTransactionImpl;
		}
		return eligibilityCriteriaTransactionImpl;
	}
	
	/***
	 * this method will add/update record in eligibilityCriteria table 
	 */
	public boolean addCriteria(EligibilityCriteria eligibilityCriteria, String mode) 
										throws  DuplicateException, Exception {
		Session session = null;
		Transaction transaction = null;
		boolean result = false;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = HibernateUtil.getSessionFactory().openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
				session.saveOrUpdate(eligibilityCriteria);
			transaction.commit();
			if (session != null) {
				session.flush();
				session.close();
			}
			//sessionFactory.close();
			result = true;
		} catch (ConstraintViolationException e) {
			if(transaction!=null)
			    transaction.rollback();
			log.error("Error during saving Eligibility criteria data..." , e);
			throw new BusinessException(e);
		} catch (Exception e) {
			if(transaction!=null)
			     transaction.rollback();
			log.error("Error during saving Eligibility criteria data..." , e);
			throw new ApplicationException(e);
		}
		return result;
	}
	
	/**
	 * getting all the active EligibilityCriteria records for UI display
	 */
	public List<EligibilityCriteria> getEligibilityCriteria(int id) throws Exception {
		Session session = null;
		List<EligibilityCriteria> criteriaList;

		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			if (id != 0) {
				Query query = session.createQuery("from EligibilityCriteria a where id = :id and isActive=1");
				query.setInteger("id", id);
				criteriaList = query.list();
				session.flush();
				//session.close();
				//sessionFactory.close();
			} else {
				Query query = session.createQuery("from EligibilityCriteria a where isActive=1");
				criteriaList = query.list();
				session.flush();
				//session.close();
				//sessionFactory.close();
			}

		} catch (Exception e) {
			log.error("Error during getting applicaition numbers..." , e);
			//session.flush();
			//session.close();
			throw new ApplicationException(e);
		}
		return criteriaList;
	}
	
	/**
	 * checking for duplication
	 */
	
	public Boolean isCourseYearDuplicated(int courseId, int year, int id)  throws Exception {
		Session session = null;
		EligibilityCriteria eligibilityCriteria;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			StringBuffer sqlString = new StringBuffer("from EligibilityCriteria where course.id = :tempCourseId and year = :tempYear and isActive = 1");
			if(id!= 0){
				sqlString = sqlString.append(" and id != :criteriaId ");
			}
			Query query = session.createQuery(sqlString.toString());
				
			query.setInteger("tempCourseId", courseId);
			query.setInteger("tempYear", year);
			if(id!= 0){
				query.setInteger("criteriaId", id);
			}
			eligibilityCriteria = (EligibilityCriteria) query.uniqueResult();
			session.flush();
			//session.close();
			//sessionFactory.close();
			if(eligibilityCriteria!= null){
				return true;
			}
			else
			{
				return false;
			}
		} catch (Exception e) {
			log.error("Error during duplcation checking..." , e);
			//session.flush();
			//session.close();
			throw new ApplicationException(e);
		}
	}

	/**
	 * This will delete criteria from table.
	 * 
	 * @return
	 * @throws Exception 
	 */

	public boolean deleteCriteria(int id, Boolean activate,
			EligibilityCriteriaForm eligibilityCriteriaForm) throws Exception {
		Session session = null;
		Transaction tx = null;
		boolean result = false;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			tx.begin();
			EligibilityCriteria eligibilityCriteria=(EligibilityCriteria)session.get(EligibilityCriteria.class, id);
			eligibilityCriteria.setIsActive(activate);
			eligibilityCriteria.setLastModifiedDate(new Date());
			eligibilityCriteria.setModifiedBy(eligibilityCriteriaForm.getUserId());
			session.update(eligibilityCriteria);
			//session.delete(eligibilityCriteria);
			tx.commit();
			session.flush();
			session.close();
			result = true;
		} catch (ConstraintViolationException e) {
			if(tx!=null)
			    tx.rollback();
			log.error("Error during deleting Eligibility criteria. data..." , e);
			throw new BusinessException(e);
		} catch (Exception e) {
			if(tx!=null)
			    tx.rollback();
			log.error("Error during deleting Eligibility criteria data..." , e);
			throw new ApplicationException(e);
		}
		return result;
	}

}
