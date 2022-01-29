package com.kp.cms.transactionsimpl.exam;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.ApplnDoc;
import com.kp.cms.bo.exam.ConsolidatedSubjectStream;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.exam.ConsolidatedSubjectStreamForm;
import com.kp.cms.transactions.exam.IConsolidatedSubjectStreamTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class ConsolidatedSubjectStreamTransactionImpl implements IConsolidatedSubjectStreamTransaction
{
	private static volatile ConsolidatedSubjectStreamTransactionImpl obj;
	
	public static ConsolidatedSubjectStreamTransactionImpl getInstance()
	{
		if(obj == null)
		{
			obj = new ConsolidatedSubjectStreamTransactionImpl();
		}
		return obj;
	}
	
	@SuppressWarnings("unchecked")
	public List<ConsolidatedSubjectStream> getConsolidatedSubjectStreams() throws Exception
	{
		Session session = null;
		
		try
		{
			session = HibernateUtil.getSession();
			Query query = session.createQuery(" from ConsolidatedSubjectStream stream where isActive = 1 order by stream.streamName");
			List<ConsolidatedSubjectStream> consolidatedSubjectStreams = query.list();
			session.flush();
			return consolidatedSubjectStreams;
		}
		catch(Exception ex)
		{
			if(session != null)
			{
				session.flush();
			}
			throw new ApplicationException();
		}
	}
	
	public boolean addConsolidatedSubjectStream(ConsolidatedSubjectStream consolidatedSubjectStream, String mode) throws Exception
	{
		Session session = null;
		Transaction tx = null;
		
		try
		{
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			if(mode.equalsIgnoreCase("Add"))
			{
				session.save(consolidatedSubjectStream);
			}
			else if(mode.equalsIgnoreCase("Edit"))
			{
				session.update(consolidatedSubjectStream);
			}
			tx.commit();
			session.flush();
			return true;
		}
		catch(ConstraintViolationException e) 
		{
			if(tx!=null)
				tx.rollback();
			throw new BusinessException(e);
		}
		catch(Exception e) 
		{
			if(tx!=null)
			     tx.rollback();
			throw new ApplicationException(e);
		}
	}
	
	public boolean deleteConsolidatedSubjectStream(int dupId, boolean activate, ConsolidatedSubjectStreamForm consolidatedSubjectStreamForm) throws Exception
	{
		Session session = null;
		Transaction tx = null;
		
		try
		{
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			ConsolidatedSubjectStream consolidatedSubjectStream = (ConsolidatedSubjectStream) session.get(ConsolidatedSubjectStream.class, dupId);
			if(activate)
			{
				consolidatedSubjectStream.setIsActive(true);
			}
			else
			{
				consolidatedSubjectStream.setIsActive(false);
			}
			consolidatedSubjectStream.setModifiedBy(consolidatedSubjectStreamForm.getUserId());
			consolidatedSubjectStream.setLastModifiedDate(new Date());
			session.update(consolidatedSubjectStream);
			tx.commit();
			session.flush();
			return true;
		}
		catch(ConstraintViolationException e) 
		{
			if(tx!=null)
				tx.rollback();
			throw new BusinessException(e);
		}
		catch(Exception e) 
		{
			if(tx!=null)
			     tx.rollback();
			throw new ApplicationException(e);
		}
	}
	
	public ConsolidatedSubjectStream isDuplicate(ConsolidatedSubjectStream oldConsolidatedSubjectStream) throws Exception
	{
		Session session = null;
		
		try
		{
			session = HibernateUtil.getSession();
			Query query = session.createQuery(" from ConsolidatedSubjectStream where streamName = :streamName");
			query.setString("streamName", oldConsolidatedSubjectStream.getStreamName());
			ConsolidatedSubjectStream consolidatedSubjectStream = (ConsolidatedSubjectStream)query.uniqueResult();
			session.flush();
			return consolidatedSubjectStream;
		}
		catch (Exception e) 
		{
			throw new ApplicationException(e);
		}
	}

	@Override
	public ApplnDoc getStudentPhoto(int admId) throws Exception {
		Session session = null;
		ApplnDoc doc=new ApplnDoc();
		try{
			session=HibernateUtil.getSession();
			String string="from ApplnDoc a where a.admAppln.id= :admId and a.isPhoto=1 ";
			Query query =session.createQuery(string)
									.setInteger("admId", admId);
			doc = (ApplnDoc)query.uniqueResult();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return doc;
	}
}
