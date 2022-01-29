package com.kp.cms.transactionsimpl.admin;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.ApplicationFee;
import com.kp.cms.bo.admin.EducationStream;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.transactions.admin.IEducationStreamTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class EducationStreamTransactionImpl implements IEducationStreamTransaction
{
	private static final Log log = LogFactory.getLog(ApplicationFeeTransactionImpl.class);
	
	
	public List<EducationStream> getEducStreams() 
	{
		Session session = null;
		try 
		{
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			List<EducationStream> educstreamList = session.createQuery("from EducationStream e where e.isActive=1").list();
			
			return educstreamList;
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
	
	
	public EducationStream isEducStreamDuplicated(EducationStream oldceducstream) throws Exception
	{
		log.debug("impl: inside isCastDuplcated");
		Session session=null;
		EducationStream educstream;
		try
		{
			session=HibernateUtil.getSession();
			Query query=session.createQuery("from EducationStream a where a.name=:name ");
			query.setString("name", oldceducstream.getName());
			educstream=(EducationStream) query.uniqueResult();
			
			log.debug("impl: leaving isCastDuplcated");
		}
		catch(Exception e)
		{
			log.error("Error during duplcation checking..." + e);
			session.flush();
			session.close();
			throw new ApplicationException(e);
		}
		return educstream;
	}
	
	
	public boolean addEducStream(EducationStream educstream) throws Exception
	{
		Session session=null;
		Transaction transaction=null;
		boolean isEducStreamAdded=false;
		try
		{
			session=HibernateUtil.getSession();
			transaction=session.beginTransaction();
			transaction.begin();
			session.save(educstream);
			isEducStreamAdded=true;
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
		return isEducStreamAdded;
	}
	
	public boolean updateEducStream(EducationStream educstream) throws Exception
	{
		Session session=null;
		Transaction transaction=null;
		try
		{
			session=HibernateUtil.getSession();
			transaction=session.beginTransaction();
			transaction.begin();
			session.saveOrUpdate(educstream);
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
	
	
	public boolean deleteEducStream(int educstreamId,String userId) throws Exception
	{
		Session session=null;
		Transaction transaction=null;
		try
		{
			session=HibernateUtil.getSession();
			transaction=session.beginTransaction();
			transaction.begin();
			EducationStream educstream=(EducationStream)session.get(EducationStream.class, educstreamId);
			educstream.setIsActive(false);
			educstream.setLastModifiedDate(new Date());
			educstream.setModifiedBy(userId);
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
	
	public boolean reActivateEducStream(EducationStream educstream,String userId) throws Exception
	{
		Session session=null;
		Transaction transaction=null;
		try
		{
			session=HibernateUtil.getSession();
			transaction=session.beginTransaction();
			transaction.begin();
			educstream.setIsActive(true);
			educstream.setCreatedBy(userId);
			educstream.setLastModifiedDate(new Date());
			session.update(educstream);
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
	
	public EducationStream getEducStreamList(int educstreamId)
	{
		Session session=null;
		EducationStream educstream=new EducationStream();
		try
		{
			session=HibernateUtil.getSession();
			educstream=(EducationStream)session.get(EducationStream.class, new Integer(educstreamId));
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
		return educstream;
	}

}
