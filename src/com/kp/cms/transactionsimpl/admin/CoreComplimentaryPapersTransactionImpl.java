package com.kp.cms.transactionsimpl.admin;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import com.kp.cms.bo.admin.CoreComplimentaryPapers;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.transactions.admin.ICoreComplimentaryPapersTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class CoreComplimentaryPapersTransactionImpl implements ICoreComplimentaryPapersTransaction
{
	private static final Log log = LogFactory.getLog(CoreComplimentaryPapersTransactionImpl.class);
	@Override
	public boolean addCoreComplimentaryPapers(CoreComplimentaryPapers corecomp)
			throws Exception 
	{
		Session session = null;
		Transaction transaction = null;
		try
		{
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			session.save(corecomp);
			transaction.commit();
			return true;
		} 
		catch (Exception e)
		{
			if ( transaction != null)
			{
				transaction.rollback();
			}
			return false;
		}
		finally
		{
			
			if (session != null){
				session.flush();
				session.close();
			}
		}
	}
	@Override
	public boolean updateCoreComplimentaryPapers(
			CoreComplimentaryPapers corecomp) throws Exception 
	{
		Session session = null;
		Transaction transaction = null;
		try
		{
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			session.merge(corecomp);
			transaction.commit();
			return true;
		}
		catch (Exception e) 
		{
			if ( transaction != null){
				transaction.rollback();
			}
			return false;
		}
		finally
		{
			
			if (session != null){
				session.flush();
				session.close();
			}
		}

	}
	@Override
	public boolean deleteCoreComplimentaryPapers(int id, String userId)
			throws Exception 
	{
		Session session=null;
		Transaction transaction=null;
		try
		{
			session=HibernateUtil.getSession();
			transaction=session.beginTransaction();
			transaction.begin();
			CoreComplimentaryPapers corecomp=(CoreComplimentaryPapers)session.get(CoreComplimentaryPapers.class, id);
			corecomp.setIsActive(false);
			corecomp.setLastModifiedDate(new Date());
			corecomp.setModifiedBy(userId);
			session.update(corecomp);
			return true;
		}
		catch(Exception e)
		{
			if(transaction!=null)
			{
				transaction.rollback();
			}
			return false;
		}
		finally
		{
			if(session!=null)
			{
				session.flush();
				session.close();
			}
		}
	}
	
	@Override
	public List<CoreComplimentaryPapers> getCoreComplimentaryPapers()
	{
		Session session = null;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			List<CoreComplimentaryPapers> corecomp = session.createQuery("from CoreComplimentaryPapers c where c.isActive=1").list();
			return corecomp;
		} 
		catch (Exception e) 
		{
			return null;
		}
		finally
		{
			if(session!=null)
			{
				session.flush();
			}
		}

	}
	@Override
	public CoreComplimentaryPapers isCoreComplimentaryPapersDuplicate(
			CoreComplimentaryPapers oldcorecomp) throws Exception
	{
		log.debug("impl: inside isCoreComplimentaryPapersDuplcated");
		Session session = null;
		CoreComplimentaryPapers corecomp ;
		try {
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from CoreComplimentaryPapers c where course.id = :cid");
			query.setInteger("cid",oldcorecomp.getCourse().getId());
			corecomp = (CoreComplimentaryPapers) query.uniqueResult();
			log.debug("impl: leaving isCastDuplcated");
		} catch (Exception e) {
			log.error("Error during duplcation checking..." + e);
			throw new ApplicationException(e);
		}
		finally
		{
			if(session!=null)
			{
				session.flush();
			}
		}
		return corecomp;
	}
	
	@Override
	public boolean isReactivateCoreComplimentaryPapers(
			CoreComplimentaryPapers corecomp, String userId) throws Exception 
	{
		Session session = null;
		Transaction transaction = null;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			corecomp.setModifiedBy(userId);
			corecomp.setLastModifiedDate(new Date());
			corecomp.setIsActive(true);
			session.update(corecomp);
			transaction.commit();
			return true;
		} catch (Exception e) {
			if ( transaction != null){
				transaction.rollback();
			}
			return false;
		}
		finally
		{
			if(session!=null)
			{
				session.flush();
				session.close();
			}
		}
	}
	
	public CoreComplimentaryPapers getCoreOrComplementarySubjects(BaseActionForm baseActionForm,int courseID) throws Exception{
		log.debug("Txn Impl : Entering addAttendance ");
		Session session = null; 
		Transaction tx = null;
		int nbeds=0;
		CoreComplimentaryPapers coreComplimentaryPapers=null;
		
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			 session = HibernateUtil.getSession();
			 tx = session.beginTransaction();
			 tx.begin();
			 
		String attQuery = "from CoreComplimentaryPapers where is_active = 1 and course_id="+courseID+" ";
			
			 Query q =session.createQuery(attQuery);
			 coreComplimentaryPapers =(CoreComplimentaryPapers) q.uniqueResult();
             
	}
		catch (Exception e) {
			log.debug("Error during getting applicaition numbers..." + e);
			session.flush();
			//session.close();
			throw new ApplicationException(e);
		}
		return coreComplimentaryPapers;
	}
		


}
