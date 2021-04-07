package com.kp.cms.transactionsimpl.admin;

import java.util.Date;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;
import com.kp.cms.bo.admin.Grade;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.admin.GradesForm;
import com.kp.cms.transactions.admin.IGradesTransaction;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;


public class GradesTransactionImpl  implements IGradesTransaction{
	public static volatile GradesTransactionImpl gradesTransactionImpl = null;
	private static final Log log = LogFactory.getLog(GradesTransactionImpl.class);

	public static GradesTransactionImpl getInstance() {
		if (gradesTransactionImpl == null) {
			gradesTransactionImpl = new GradesTransactionImpl();
			return gradesTransactionImpl;
		}
		return gradesTransactionImpl;
	}
	
	/**
	 * This will retrieve all the Grades from database for UI display.
	 * 
	 * @return all Grades
	 * @throws Exception
	 */

	public List<Grade> geGrades() throws Exception {
		Session session = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from Grade g where g.isActive = 1");
			List<Grade> list = query.list();
//			session.flush();
//			session.close();
//			sessionFactory.close();
			return list;
		 } catch (Exception e) {
			 log.error("Error during getting Grades...",e);
			 //session.flush();
			 //session.close();
			 throw  new ApplicationException(e);
		 }
	}
	/**
	 * duplication checking for grade entry
	 */
	public Grade isGradeDuplcated(Grade duplGrade) throws Exception {
		Session session = null;
		Grade grade;
		try {
			SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();
			Query query = session
					.createQuery("from Grade g where g.grade = :tempGrade");
			query.setString("tempGrade", duplGrade.getGrade());
			grade = (Grade) query.uniqueResult();
			session.flush();
			session.close();
			sessionFactory.close();
		} catch (Exception e) {
			log.error("Error during duplcation checking..." , e);
			//session.flush();
			//session.close();
			throw new ApplicationException(e);
		}
		return grade;
	}
	/**
	 * This method add a single Grade to Database.
	 * 
	 * @return true / false based on result.
	 * @throws BusinessException 
	 */
	public boolean addGrades(Grade grade) throws Exception {
		Session session = null;
		Transaction transaction = null;
		try {
			SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();
			if(session!=null){
			    transaction = session.beginTransaction();
			    transaction.begin();
			    session.save(grade);
			    transaction.commit();
			    session.flush();
			    session.close();
			    return true;
			}
			else
			    return false;
		} catch (ConstraintViolationException e) {
			if(transaction!=null)
			    transaction.rollback();
			log.error("Error during saving grade data..." , e);
			throw new BusinessException(e);
		} catch (Exception e) {
			if(transaction!=null)
			    transaction.rollback();
			log.error("Error during saving grade data..." , e);
			throw new ApplicationException(e);
		}

	}

	/**
	 * This method update the Grade to Database.
	 * 
	 * @return true / false based on result.
	 */
	public boolean updateGrades(Grade grade) {
		Session session = null;
		Transaction transaction = null;
		try {
			SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();
			if(session!=null){
			     transaction = session.beginTransaction();
			     transaction.begin();
			     session.update(grade);
			     transaction.commit();
				 session.flush();
				 session.close();
			     return true;
			}
			else
				return false;
		} catch (Exception e) {
			log.debug("error in updateGrades of Impl class",e);
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
	 * This method delete grade from table.
	 * 
	 * @return true / false based on result.
	 */
	public boolean deleteGrade(int id,Boolean activate,GradesForm gradesForm) throws Exception {
		Session session = null;
		Transaction transaction = null;
		boolean result = false;
		try {
			SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			transaction.begin();
			Grade grade=(Grade)session.get(Grade.class, id);
			grade.setLastModifiedDate(new Date());
			grade.setModifiedBy(gradesForm.getUserId());
			grade.setIsActive(activate);
			session.update(grade);
			//session.delete(grade);
			transaction.commit();
			session.flush();
			session.close();
			result = true;
		} catch (ConstraintViolationException e) {
			if(transaction!=null)
			    transaction.rollback();
			log.error("Error during deleting college data..." , e);
			throw new BusinessException(e);
		} catch (Exception e) {
			if(transaction!=null)
			    transaction.rollback();
			log.error("Error during deleting college data..." , e);
			throw new ApplicationException(e);
		}
		return result;
	}
	
	
	/**
	 * duplication checking for mark
	 */
	public Grade isMarkDuplcated(Grade duplGrade) throws Exception {
		Session session = null;
		Grade grade;
		try {
			SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();
			Query query = session
					.createQuery("from Grade g where g.marks = :tempMark");
			query.setInteger("tempMark", duplGrade.getMarks());
			grade = (Grade) query.uniqueResult();
			session.flush();
			session.close();
			sessionFactory.close();
		} catch (Exception e) {
			log.error("Error during duplcation checking..." , e);
			//session.flush();
		//	session.close();
			throw new ApplicationException(e);
		}
		return grade;
	}	
	
}
