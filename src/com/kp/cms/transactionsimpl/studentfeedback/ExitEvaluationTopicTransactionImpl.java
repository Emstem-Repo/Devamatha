package com.kp.cms.transactionsimpl.studentfeedback;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.EducationStream;
import com.kp.cms.bo.studentfeedback.ExitEvaluationTopic;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.transactions.studentfeedback.IExitEvaluationTopicTransaction;
import com.kp.cms.transactionsimpl.admin.ApplicationFeeTransactionImpl;
import com.kp.cms.utilities.HibernateUtil;

public class ExitEvaluationTopicTransactionImpl implements IExitEvaluationTopicTransaction {
	
	private static final Log log = LogFactory.getLog(ExitEvaluationTopicTransactionImpl.class);
	
	public List<ExitEvaluationTopic> getExitEvalTopics() 
	{
		Session session = null;
		try 
		{
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			List<ExitEvaluationTopic> exitEvalTopicList = session.createQuery("from ExitEvaluationTopic e where e.isActive=1").list();
			
			return exitEvalTopicList;
		} 
		catch (Exception e) 
		{
			if (session != null)
			{
				session.flush();
				session.close();
			}
			return null;
		}
	}
	
	public ExitEvaluationTopic isExitEvalTopicDuplicated(ExitEvaluationTopic oldExitEvalTopic) throws Exception
	{
		log.debug("impl: inside isCastDuplcated");
		Session session=null;
		ExitEvaluationTopic exitEvalTopic;
		try
		{
			session=HibernateUtil.getSession();
			Query query=session.createQuery("from ExitEvaluationTopic a where a.name=:name ");
			query.setString("name", oldExitEvalTopic.getName());
			exitEvalTopic=(ExitEvaluationTopic) query.uniqueResult();
			
			log.debug("impl: leaving isCastDuplcated");
		}
		catch(Exception e)
		{
			log.error("Error during duplcation checking..." + e);
			session.flush();
			session.close();
			throw new ApplicationException(e);
		}
		return exitEvalTopic;
	}
	
	public boolean addExitEvalTopic(ExitEvaluationTopic exitEvalTopic) throws Exception
	{
		Session session=null;
		Transaction transaction=null;
		boolean isExitEvalTopicAdded=false;
		try
		{
			session=HibernateUtil.getSession();
			transaction=session.beginTransaction();
			transaction.begin();
			session.save(exitEvalTopic);
			isExitEvalTopicAdded=true;
			transaction.commit();
			session.close();
		}
		catch(Exception e)
		{
			if(transaction!=null)
			{
				transaction.rollback();
			}
			else
			{
				session.flush();
				session.close();
			}
		}
		return isExitEvalTopicAdded;
	}
	
	public boolean updateExitEvalTopic(ExitEvaluationTopic exitEvalTopic) throws Exception
	{
		Session session=null;
		Transaction transaction=null;
		try
		{
			session=HibernateUtil.getSession();
			transaction=session.beginTransaction();
			transaction.begin();
			session.saveOrUpdate(exitEvalTopic);
			transaction.commit();
			session.flush();
			session.close();
			return true;
		}
		catch(Exception e)
		{
			if(transaction!=null)
			{
				transaction.rollback();
			}
			if(session!=null)
			{
				session.flush();
				session.close();
			}
			return false;
		}
	}
	
	public boolean deleteExitEvalTopic(int exitEvalTopicId,String userId) throws Exception
	{
		Session session=null;
		Transaction transaction=null;
		try
		{
			session=HibernateUtil.getSession();
			transaction=session.beginTransaction();
			transaction.begin();
			ExitEvaluationTopic exitEvalTopic=(ExitEvaluationTopic)session.get(EducationStream.class, exitEvalTopicId);
			exitEvalTopic.setIsActive(false);
			exitEvalTopic.setLastModifiedDate(new Date());
			exitEvalTopic.setModifiedBy(userId);
			transaction.commit();
			session.close();
			return true;
		}
		catch(Exception e)
		{
			if(transaction!=null)
			{
				transaction.rollback();
			}
			if(session!=null)
			{
				session.flush();
				session.close();
			}
			return false;
		}
	}
	
	public boolean reActivateExitEvalTopic(ExitEvaluationTopic exitEvalTopic,String userId) throws Exception
	{
		Session session=null;
		Transaction transaction=null;
		try
		{
			session=HibernateUtil.getSession();
			transaction=session.beginTransaction();
			transaction.begin();
			exitEvalTopic.setIsActive(true);
			exitEvalTopic.setCreatedBy(userId);
			exitEvalTopic.setLastModifiedDate(new Date());
			session.update(exitEvalTopic);
			transaction.commit();
			
			session.close();
			return true;
		}
		catch(Exception e)
		{
			if(transaction!=null)
			{
				transaction.rollback();
			}
			if(session!=null)
			{
				session.flush();
				session.close();
			}
			return false;
		}
	}
	
	public ExitEvaluationTopic getExitEvalTopicList(int exitEvalTopicId)
	{
		Session session=null;
		ExitEvaluationTopic exitEvalTopic=new ExitEvaluationTopic();
		try
		{
			session=HibernateUtil.getSession();
			exitEvalTopic=(ExitEvaluationTopic)session.get(ExitEvaluationTopic.class, new Integer(exitEvalTopicId));
			session.flush();
			session.close();
			
		}
		catch(Exception e)
		{
			if(session!=null)
			{
				session.flush();
				session.close();
			}
		}
		return exitEvalTopic;
	}

}
