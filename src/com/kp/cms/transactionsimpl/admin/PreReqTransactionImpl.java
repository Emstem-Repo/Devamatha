package com.kp.cms.transactionsimpl.admin;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.CoursePrerequisite;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.transactions.admin.IPreReqDefTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class PreReqTransactionImpl implements IPreReqDefTransaction{
	private static final Log log = LogFactory.getLog(PreReqTransactionImpl.class);
	
	private static volatile PreReqTransactionImpl preReqTransactionImpl= null;
	public static PreReqTransactionImpl getInstance() {
	      if(preReqTransactionImpl == null) {
	    	  preReqTransactionImpl = new PreReqTransactionImpl();
	    	  return preReqTransactionImpl;
	      }
	      return preReqTransactionImpl;
	}
	
	/**
	 * this method used to add new record in table
	 */
	public boolean addPreReqDef(CoursePrerequisite  coursePrerequisite1,String mode) throws Exception{
		Session session = null;
		Transaction transaction = null;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			
			if(mode.equalsIgnoreCase("Add"))
			{
				
				session.save(coursePrerequisite1);
			}
			else
			{
				session.merge(coursePrerequisite1);
			}
			transaction.commit();
			session.flush();
			session.close();
			return true;
		} catch (ConstraintViolationException e) {
		 	if(transaction!=null)
			      transaction.rollback();
			log.error("Error in addPreReqDef impl", e);
			throw new BusinessException(e);
		} catch (Exception e) {
			if(transaction!=null)
			     transaction.rollback();
			log.error("Error ddPreReqDef impl", e);
			throw new ApplicationException(e);
		}
		
	}
	/**
	 * getting all the records for UI display
	 */
	public List<CoursePrerequisite>getPreReqDef()throws Exception {
		List<CoursePrerequisite> deflist=null;
		Session session = null;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			String subjectHibernateQuery = "from CoursePrerequisite where isActive=1";
			deflist = session.createQuery(subjectHibernateQuery).list();
			session.flush();
			//session.close();
			return deflist;
		} catch (Exception e) {
			
			log.error("Error in getPreReqDef impl...",e);
			if (session != null) {
				session.flush();
				//session.close();
			}
			throw  new ApplicationException(e);
			
		}
						
	}
	
	/**
	 * duplication checking
	 */
	public List<CoursePrerequisite> existanceCheck(CoursePrerequisite coursePrerequisite) throws Exception	{
		Session session = null;
		try
		{
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			List<CoursePrerequisite> coursePrereqList=null;
			String studenttypeHibernateQuery = "from CoursePrerequisite where course.id=:id and isActive=1";
			Query query = session.createQuery(studenttypeHibernateQuery);
			query.setInteger("id", coursePrerequisite.getCourse().getId());
			coursePrereqList =query.list();
			return (coursePrereqList!=null)?coursePrereqList:null;
			}catch (Exception e) {
				
			log.error("Error in existanceCheck",e);
			if (session != null) {
				session.flush();
				//session.close();
			}
			throw  new ApplicationException(e);
			
		}
	}

}
