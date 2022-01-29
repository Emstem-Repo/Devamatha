package com.kp.cms.transactionsimpl.admin;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.ApplicationFee;
import com.kp.cms.bo.admin.ApplicationFee;
import com.kp.cms.bo.admin.Caste;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.transactions.admin.IApplicationFeeTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class ApplicationFeeTransactionImpl implements IApplicationFeeTransaction
{
	private static final Log log = LogFactory.getLog(ApplicationFeeTransactionImpl.class);
	public List<ApplicationFee> getAppFees() 
	{
		Session session = null;
		try 
		{
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			List<ApplicationFee> appfeeList = session.createQuery("from ApplicationFee a where a.isActive=1").list();
			
			return appfeeList;
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
	
	
	public ApplicationFee isAppFeeDuplicated(ApplicationFee oldcappfee) throws Exception
	{
		log.debug("impl: inside isCastDuplcated");
		Session session=null;
		ApplicationFee appfee;
		try
		{
			session=HibernateUtil.getSession();
			Query query=session.createQuery("from ApplicationFee a where a.accademicYear=:year " +
					"and a.religionSection.id=:cid and a.programType.id=:pid ");
			query.setString("year", oldcappfee.getAccademicYear());
			query.setInteger("cid", oldcappfee.getReligionSection().getId());
			query.setInteger("pid", oldcappfee.getProgramType().getId());
			appfee=(ApplicationFee) query.uniqueResult();
			
			log.debug("impl: leaving isCastDuplcated");
		}
		catch(Exception e)
		{
			log.error("Error during duplcation checking..." + e);
			session.flush();
			session.close();
			throw new ApplicationException(e);
		}
		return appfee;
	}
	
	
	public boolean addAppFee(ApplicationFee appFee) throws Exception
	{
		Session session=null;
		Transaction transaction=null;
		boolean isAppFeeAdded=false;
		try
		{
			session=HibernateUtil.getSession();
			transaction=session.beginTransaction();
			transaction.begin();
			session.save(appFee);
			isAppFeeAdded=true;
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
		return isAppFeeAdded;
	}
	
	public boolean updateAppFee(ApplicationFee appFee4) throws Exception
	{
		Session session=null;
		Transaction transaction=null;
		try
		{
			session=HibernateUtil.getSession();
			transaction=session.beginTransaction();
			transaction.begin();
			session.saveOrUpdate(appFee4);
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
	
	
	public boolean deleteAppFee(int appfeeId,String userId) throws Exception
	{
		Session session=null;
		Transaction transaction=null;
		try
		{
			session=HibernateUtil.getSession();
			transaction=session.beginTransaction();
			transaction.begin();
			ApplicationFee appFee=(ApplicationFee)session.get(ApplicationFee.class, appfeeId);
			appFee.setIsActive(false);
			appFee.setLastModifiedDate(new Date());
			appFee.setModifiedBy(userId);
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
	
	public boolean reActivateAppFee(ApplicationFee appfee,String userId) throws Exception
	{
		Session session=null;
		Transaction transaction=null;
		try
		{
			session=HibernateUtil.getSession();
			transaction=session.beginTransaction();
			transaction.begin();
			appfee.setIsActive(true);
			appfee.setCreatedBy(userId);
			appfee.setLastModifiedDate(new Date());
			session.update(appfee);
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
	
	public ApplicationFee getAppFeeList(int appfeeId)
	{
		Session session=null;
		ApplicationFee appfee=new ApplicationFee();
		try
		{
			session=HibernateUtil.getSession();
			appfee=(ApplicationFee)session.get(ApplicationFee.class, new Integer(appfeeId));
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
		return appfee;
	}
	
	
}

