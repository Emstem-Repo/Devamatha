package com.kp.cms.transactionsimpl.admin;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.College;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.transactions.admin.IInstituteTransaction;
import com.kp.cms.utilities.HibernateUtil;


public class InstituteTransactionImpl  implements IInstituteTransaction{
	public static volatile InstituteTransactionImpl instituteTransactionImpl = null;
	private static final Log log = LogFactory.getLog(InstituteTransactionImpl.class);

	public static InstituteTransactionImpl getInstance() {
		if (instituteTransactionImpl == null) {
			instituteTransactionImpl = new InstituteTransactionImpl();
			return instituteTransactionImpl;
		}
		return instituteTransactionImpl;
	}
	
	/**
	 * This will retrieve all the Institute from database.
	 * 
	 * @return all Institues
	 * @throws Exception
	 */

	public List<College> getCollegeNames() throws Exception {
		Session session = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from College p where isActive=1");
			List<College> list = query.list();
			session.flush();
			//session.close();
			//sessionFactory.close();
			return list;
		 } catch (Exception e) {
			 log.error("Error during getting College Names...",e);
			// session.flush();
			 //session.close();
			 throw  new ApplicationException(e);
		 }
	}

	/**
	 * institute name duplication
	 */
	public College isInstituteNameDuplcated(College duplCollege) throws Exception {
		Session session = null;
		College college;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from College a where name = :instituteName and university.id = :universityId");
			query.setString("instituteName", duplCollege.getName());
			query.setInteger("universityId", duplCollege.getUniversity().getId());
			college = (College) query.uniqueResult();
			session.flush();
			//session.close();
			//sessionFactory.close();
			return college;
		} catch (Exception e) {
			log.error("Error during duplcation checking..." , e);
			//session.flush();
			//session.close();
			throw new ApplicationException(e);
		}
	}
	/**
	 * This method add a single Institute to Database.
	 * 
	 * @return true / false based on result.
	 * @throws BusinessException 
	 */
	public boolean addInstitute(College college) throws Exception {
		Session session = null;
		Transaction transaction = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			if(session!=null){
				transaction = session.beginTransaction();
				transaction.begin();
				session.save(college);
				transaction.commit();
				session.flush();
				session.close();
				return true;
			}else 
				return false;
		} catch (ConstraintViolationException e) {
			if(transaction!=null)
			     transaction.rollback();
			log.error("Error during saving institute data..." , e);
			throw new BusinessException(e);
		} catch (Exception e) {
			if(transaction!=null)
			     transaction.rollback();
			log.error("Error during saving institute data..." , e);
			throw new ApplicationException(e);
		}

	}

	/**
	 * This method update the Institute to Database.
	 * 
	 * @return true / false based on result.
	 */
	public boolean updateInstitute(College college) throws Exception{
		Session session = null;
		Transaction transaction = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			if(session!=null){
				transaction = session.beginTransaction();
				transaction.begin();
				session.update(college);
				transaction.commit();
				if (session != null) {
					session.flush();
					session.close();
				}
				return true;
			}else
				return false;
		} catch (ConstraintViolationException e) {
			if(transaction!=null)
			     transaction.rollback();
			log.error("Error during updateInstitute data..." , e);
			throw new BusinessException(e);
		} catch (Exception e) {
			if(transaction!=null)
			     transaction.rollback();
			log.error("Error during updateInstitute data..." , e);
			throw new ApplicationException(e);
		}
	}

	/**
	 * This method delete institute from table.
	 * 
	 * @return true / false based on result.
	 */
	public boolean deleteInstitute(int instId, Boolean activate, String userId) throws Exception {
		Session session = null;
		Transaction transaction = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			College college = (College) session.get(College.class, instId);
			if (activate) {
				college.setIsActive(true);
			} else {
				college.setIsActive(false);
			}
			college.setModifiedBy(userId);
			college.setLastModifiedDate(new Date());
			session.update(college);
			transaction.commit();
			session.flush();
			session.close();
			return true;
		} catch (ConstraintViolationException e) {
			if(transaction!=null)
			     transaction.rollback();
			log.error("Error during deleting college data..." , e);
			throw new BusinessException(e);
		} catch (Exception e) {
			transaction.rollback();
			log.error("Error during deleting college data...", e);
			throw new ApplicationException(e);
		}
	}
	
}
