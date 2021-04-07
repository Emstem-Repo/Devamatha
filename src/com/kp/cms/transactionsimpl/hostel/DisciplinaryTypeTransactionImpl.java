package com.kp.cms.transactionsimpl.hostel;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.HlDisciplinaryType;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.hostel.DisciplinaryTypeForm;
import com.kp.cms.transactions.hostel.IDisciplinaryTypeTransactions;
import com.kp.cms.utilities.HibernateUtil;

public class DisciplinaryTypeTransactionImpl implements IDisciplinaryTypeTransactions {
	public static volatile DisciplinaryTypeTransactionImpl disciplinaryTypeTransactionImpl = null;
	private static final Log log = LogFactory.getLog(DisciplinaryTypeTransactionImpl.class);
	public static DisciplinaryTypeTransactionImpl getInstance() {
		if (disciplinaryTypeTransactionImpl == null) {
			disciplinaryTypeTransactionImpl = new DisciplinaryTypeTransactionImpl();
			return disciplinaryTypeTransactionImpl;
		}
		return disciplinaryTypeTransactionImpl;
	}
	/**
	 * This will retrieve all the Disciplinary types from database for UI display.
	 * 
	 * @return all Disciplinary types
	 * @throws Exception
	 */

	public List<HlDisciplinaryType> getDisciplinary() throws Exception {
		log.debug("inside getDisciplinary");
		Session session = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from HlDisciplinaryType h where h.isActive = 1");
			List<HlDisciplinaryType> list = query.list();
			session.flush();
			//session.close();
			//sessionFactory.close();
			log.debug("leaving getDisciplinary");
			return list;
		 } catch (Exception e) {
			 log.error("Error in getDisciplinary...",e);
			 session.flush();
			 //session.close();
			 throw  new ApplicationException(e);
		 }
	}
	/**
	 * duplication checking for disciplinary type
	 */
	public HlDisciplinaryType isDisciplinaryTypeDuplcated(String name, int id) throws Exception {
		log.debug("inside isgetDisciplinaryTypeDuplcated");
		Session session = null;
		HlDisciplinaryType disciplinaryType;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			StringBuffer sqlString = new StringBuffer("from HlDisciplinaryType h where h.name = :tempname ");
			if(id!= 0){
				sqlString.append(" and id != :id");
			}
			Query query = session.createQuery(sqlString.toString());
			query.setString("tempname",  name);
			if(id!= 0){
				query.setInteger("id", id);
			}
			
			disciplinaryType = (HlDisciplinaryType) query.uniqueResult();
			session.flush();
			//session.close();
			//sessionFactory.close();
		} catch (Exception e) {
			log.error("Error during duplcation checking..." , e);
			session.flush();
			//session.close();
			throw new ApplicationException(e);
		}
		log.debug("leaving isgetDisciplinaryTypeDuplcated");
		return disciplinaryType;
	}
	/**
	 * This method add a single Disciplinary type to Database.
	 * 
	 * @return true / false based on result.
	 * @throws BusinessException 
	 */
	public boolean addDisciplinaryType(HlDisciplinaryType disciplinaryType, String mode) throws Exception {
		log.debug("inside addDisciplinaryType");
		Session session = null;
		Transaction transaction = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			if("edit".equalsIgnoreCase(mode)){
				session.update(disciplinaryType);
			}
			else
			{
				session.save(disciplinaryType);
			}
			transaction.commit();
			if (session != null) {
				session.flush();
				session.close();
			}
			log.debug("leaving addDisciplinaryType");
			return true;
		} catch (ConstraintViolationException e) {
			transaction.rollback();
			log.error("Error during in addDisciplinaryType..." , e);
			throw new BusinessException(e);
		} catch (Exception e) {
			transaction.rollback();
			log.error("Error in addDisciplinaryType..." , e);
			throw new ApplicationException(e);
		}

	}

	/**
	 * This method update the Disciplinary type to Database.
	 * 
	 * @return true / false based on result.
	 */
	public boolean updateDisciplinaryType(HlDisciplinaryType disciplinaryType) {
		log.debug("inside updateDisciplinaryType");
		Session session = null;
		Transaction transaction = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			session.update(disciplinaryType);
			transaction.commit();
			if (session != null) {
				session.flush();
				session.close();
			}
			log.debug("leaving updateDisciplinaryType");
			return true;
		} catch (Exception e) {
			log.debug("error in updateDisciplinaryType of Impl class",e);
			if (transaction != null) {
				transaction.rollback();
			}
			if (session != null) {
				session.flush();
				session.close();
			}
			return false;
		}
	}

	/**
	 * delete & reactivate
	 */
	public boolean deleteDisciplinaryType(int id, Boolean activate, DisciplinaryTypeForm disForm) throws Exception {
	Session session = null;
	Transaction tx = null;
	boolean result = false;
	try {
		//SessionFactory sessionFactory = InitSessionFactory.getInstance();
		//session = sessionFactory.openSession();
		session = HibernateUtil.getSession();
		tx = session.beginTransaction();
		tx.begin();
		HlDisciplinaryType diType = (HlDisciplinaryType) session.get(HlDisciplinaryType.class, id);
		if (activate) {
			diType.setIsActive(true);
		}else
		{
			diType.setIsActive(false);
		}
		diType.setModifiedBy(disForm.getUserId());
		diType.setLastModifiedDate(new Date());
		session.update(diType);
		tx.commit();
		session.flush();
		session.close();
		result = true;
	} catch (ConstraintViolationException e) {
		tx.rollback();
		log.error("Error in deleteRemarks..." , e);
		throw new BusinessException(e);
	} catch (Exception e) {
		tx.rollback();
		log.error("Error in deleteRemarks.." , e);
		throw new ApplicationException(e);
	}
	return result;
	}
	
}
