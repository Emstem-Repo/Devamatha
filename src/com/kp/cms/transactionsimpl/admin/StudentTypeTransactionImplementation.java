package com.kp.cms.transactionsimpl.admin;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.StudentType;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.transactions.admin.IStudentTypeTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class StudentTypeTransactionImplementation implements
		IStudentTypeTransaction {

	private static Log log = LogFactory
			.getLog(StudentTypeTransactionImplementation.class);

	public List<StudentType> getStudentType() throws Exception {
		Session session = null;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			String studenttypeHibernateQuery = "from StudentType where isActive=1";
			List<StudentType> studentTypes = session.createQuery(
					studenttypeHibernateQuery).list();
			session.flush();
			//session.close();
			return studentTypes;
		} catch (Exception e) {
			if (session != null) {
				session.flush();
				//session.close();
			}
			return null;
		}
	}

	public boolean addStudentType(String name, String desc) throws Exception {
		Session session = null;
		Transaction transaction = null;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			
			StudentType studentType = new StudentType();
			studentType.setName(name);
			studentType.setDescription(desc);
			studentType.setCreatedDate(new Date());
			studentType.setIsActive(true);
			studentType.setLastModifiedDate(new Date());
			session.save(studentType);
			transaction.commit();
			return true;
		} catch (ConstraintViolationException e) {
			log.error("Error during saving program type data..." + e);
			if (transaction != null) {
				transaction.rollback();
			}
			throw new BusinessException(e);
		} catch (Exception e) {
			log.error("Error during saving program type data..." + e);
			if (transaction != null) {
				transaction.rollback();
			}
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}

		}
	}

	public boolean editStudentType(int id, String name, String desc)
			throws Exception {
		Session session = null;
		Transaction transaction = null;
		try {

			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			
			StudentType studentType = new StudentType();
			studentType.setId(id);
			studentType.setName(name);
			studentType.setDescription(desc);
			studentType.setLastModifiedDate(new Date());
			studentType.setIsActive(true);
			session.update(studentType);
			transaction.commit();
			return true;

		} catch (ConstraintViolationException e) {
			log.error("Error during saving program type data..." + e);
			if (transaction != null) {
				transaction.rollback();
			}
			throw new BusinessException(e);
		} catch (Exception e) {
			log.error("Error during saving program type data..." + e);
			if (transaction != null) {
				transaction.rollback();
			}
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}

		}
	}

	public boolean deleteStudentType(int id, String name, String desc)
			throws Exception {
		Session session = null;
		Transaction transaction = null;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
		
			StudentType studentType = new StudentType();
			studentType.setId(id);
			studentType.setName(name);
			studentType.setDescription(desc);
			studentType.setLastModifiedDate(new Date());
			studentType.setIsActive(false);
			session.update(studentType);
			transaction.commit();
			session.flush();
			//session.close();
			return true;
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			if (session != null) {
				session.flush();
				//session.close();
			}
			return false;
		}

	}

	public StudentType existanceCheck(String name) throws Exception {
		Session session = null;
		StudentType studentType = null;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			String studenttypeHibernateQuery = "from StudentType where name=:typeName";
			Query query = session.createQuery(studenttypeHibernateQuery);
			query.setString("typeName", name);
			studentType = (StudentType) query.uniqueResult();

		} catch (Exception e) {
			if (session != null) {
				session.flush();
				//session.close();
			}
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		return studentType;

	}

	public boolean reActivateStudentType(String name) throws Exception {
		Session session = null;
		Transaction transaction = null;
		boolean isActivated = false;
		try {
			//SessionFactory sessionFactory =  HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from StudentType stype where stype.name = :name");
			query.setString("name", name);
			StudentType studentType = (StudentType) query.uniqueResult();
			transaction = session.beginTransaction();
			studentType.setIsActive(true);
			studentType.setLastModifiedDate(new Date());
			session.update(studentType);
			isActivated = true;
			transaction.commit();
		} catch (ConstraintViolationException e) {
			log.error("Error during saving program type data..." + e);
			if (transaction != null) {
				transaction.rollback();
			}
			throw new BusinessException(e);
		} catch (Exception e) {
			log.error("Unable to reActivateStudentType" + e);
			if (transaction != null) {
				transaction.rollback();
			}
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		return isActivated;
	}

}
