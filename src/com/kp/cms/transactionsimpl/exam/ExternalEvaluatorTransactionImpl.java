package com.kp.cms.transactionsimpl.exam;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.exam.ExamValuators;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.exam.ExternalEvaluatorForm;
import com.kp.cms.transactions.exam.IExternalEvaluatorTransaction;
import com.kp.cms.transactionsimpl.exam.ExternalEvaluatorTransactionImpl;
import com.kp.cms.utilities.HibernateUtil;

public class ExternalEvaluatorTransactionImpl implements IExternalEvaluatorTransaction {
	
private static final Log log = LogFactory.getLog(ExternalEvaluatorTransactionImpl.class);
	
	/**
	 * Used for adding a externalEvaluator 
	 */
	
public boolean addExternalEvaluator(ExamValuators examValuators) throws Exception
{
	log.info("Start of addExternalEvaluator of ExternalEvaluator TransactionImpl");
	Session session = null;
	Transaction transaction = null;
	try {
		//session = InitSessionFactory.getInstance().openSession();
		session = HibernateUtil.getSession();
		transaction = session.beginTransaction();
		session.save(examValuators);
		transaction.commit();
		return true;
	} catch (Exception e) {
		if(transaction != null){
			transaction.rollback();
		}
		log.error("Exception occured in adding ExternalEvaluator in IMPL :"+e);
		throw  new ApplicationException(e);
	} finally {
	if (session != null) {
		session.flush();
		session.close();
	}
	log.info("End of addExternalEvaluator of ExternalEvaluator TransactionImpl");
	}
}

	public List<ExamValuators> getExternalEvaluatorDetails()throws Exception{
		log.info("Start of getExternalEvaluatorDetails of ExternalEvaluator TransactionImpl");
		Session session = null;
		List<ExamValuators> externalEvaluatorList;
		try {
		//	session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			externalEvaluatorList = session.createQuery("from ExamValuators e where isActive = 1 ").list();
		} catch (Exception e) {			
			log.error("Exception occured in getExternalEvaluatorDetails in ExternalEvaluatorIMPL :"+e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
		//	session.close();
			}
		}
		log.info("End of getExternalEvaluatorDetails of ExternalEvaluator TransactionImpl");
		return externalEvaluatorList;		
	}
	
	public boolean deleteExternalEvaluator(int id, String userId) throws Exception{
		log.info("Start of deleteExternalEvaluator of ExternalEvaluator TransactionImpl");
		Session session = null;
		Transaction transaction = null;
		try {
			//session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			ExamValuators examValuators=(ExamValuators)session.get(ExamValuators.class,id);
			examValuators.setIsActive(false);
			examValuators.setLastModifiedDate(new Date());
			examValuators.setModifiedBy(userId);
			session.update(examValuators);
			transaction.commit();
			return true;
		} catch (Exception e) {
			if(transaction != null){
				transaction.rollback();
			}
			log.error("Exception occured in deleting ExternalEvaluator in IMPL :"+e);	
			throw  new ApplicationException(e);
		} finally {
		if (session != null) {
			session.flush();
			session.close();
		}
		log.info("End of deleteExternalEvaluator of ExternalEvaluator TransactionImpl");
		}
	}
	
	public boolean updateExternalEvaluator(ExamValuators examValuators)throws Exception
	{
		log.info("Start of updateExternalEvaluator of ExternalEvaluator TransactionImpl");
		Session session = null;
		Transaction transaction = null;
		try {
			//session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			session.merge(examValuators);
			transaction.commit();
			return true;
		} catch (Exception e) {
			if(transaction != null){
				transaction.rollback();
			}
			log.error("Exception occured while updating ExternalEvaluator in IMPL :"+e);
			throw  new ApplicationException(e);
		} finally {
		if (session != null) {
			session.flush();
			session.close();		
		}
		log.info("End of updateExternalEvaluator of ExternalEvaluator TransactionImpl");
		}
	}
	
	public boolean reActivateExternalEvaluator(String name, String userId)throws Exception
	{
		log.info("Start of reActivateExternalEvaluator of ExternalEvaluator TransactionImpl");
		Session session = null;
		Transaction transaction = null;
			try {
				//SessionFactory sessionFactory = InitSessionFactory.getInstance();
				//session = sessionFactory.openSession();
				session = HibernateUtil.getSession();
				Query query = session.createQuery("from ExamValuators e where e.name = :name");
				query.setString("name",name);
				ExamValuators examValuators = (ExamValuators) query.uniqueResult();
				transaction = session.beginTransaction();
				examValuators.setIsActive(true);
				examValuators.setModifiedBy(userId);
				examValuators.setLastModifiedDate(new Date());
				session.update(examValuators);
				transaction.commit();
				return true;
				} catch (Exception e) {
				if(transaction != null){
					transaction.rollback();
				}
				log.error("Exception occured in reactivating of externalEvaluator in IMPL :"+e);
				throw new ApplicationException(e);
				} finally {
					if (session != null) {
						session.flush();
						session.close();
					}
					log.info("End of reActivateExternalEvaluator of ExternalEvaluator TransactionImpl");
				}			
		}
		
	public ExamValuators getDetailsonId(int id)throws Exception
	{
		log.info("Start of getDetailsonId of ExternalEvaluator TransactionImpl");
		Session session = null;
		ExamValuators examValuators = null;
		try {
			//session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			
			Query query=session.createQuery("from ExamValuators e where e.id= :row");
			query.setInteger("row", id);
			examValuators = (ExamValuators)query.uniqueResult();
		} catch (Exception e) {			
		log.error("Exception occured while getting the row based on the Id in IMPL :"+e);
			throw  new ApplicationException(e);
		} finally {
		if (session != null) {
			session.flush();
			//session.close();
		}
		}
		log.info("End of getDetailsonId of ExternalEvaluator TransactionImpl");
		return examValuators;	
	}
	
	public ExamValuators checkForDuplicateonName(String name)throws Exception
	{
		log.info("Start of checkForDuplicateonName of ExternalEvaluator TransactionImpl");
		Session session = null;
		ExamValuators examValuators = null;
		try {
			//session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			Query query=session.createQuery("from ExamValuators e where e.name= :name");
			query.setString("name", name);
			examValuators = (ExamValuators)query.uniqueResult();
		} catch (Exception e) {
		log.error("Exception occured in checking duplicate records for externalEvaluator in IMPL :"+e);
			throw  new ApplicationException(e);
		} finally {
		if (session != null) {
			session.flush();
			//session.close();
		}
		}
		log.info("End of updateExternalEvaluator of ExternalEvaluator TransactionImpl");
		return examValuators;	
	}
	
	public boolean checkForDuplicateonName1(String name, ExternalEvaluatorForm externalEvaluatorForm)throws Exception
	{
		log.info("Start of checkForDuplicateonName of ExternalEvaluator TransactionImpl");
		Session session = null;
		ExamValuators examValuators = null;
		boolean flag=false;
		try {
			//session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			Query query=session.createQuery("from ExamValuators e where e.name= :name");
			query.setString("name", name);
			examValuators = (ExamValuators)query.uniqueResult();
			if(examValuators!=null){
			if(examValuators.getId()!=0){
				if(examValuators.getId()==externalEvaluatorForm.getId())
				{
					flag=false;
				}
				else{
					flag=true;
				}
			}
			}
			
		} catch (Exception e) {
		log.error("Exception occured in checking duplicate records for externalEvaluator in IMPL :"+e);
			throw  new ApplicationException(e);
		} finally {
		if (session != null) {
			session.flush();
			//session.close();
		}
		}
		log.info("End of updateExternalEvaluator of ExternalEvaluator TransactionImpl");
		return flag;	
	}

}
