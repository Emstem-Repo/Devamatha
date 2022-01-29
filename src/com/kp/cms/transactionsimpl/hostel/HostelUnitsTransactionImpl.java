package com.kp.cms.transactionsimpl.hostel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.HlUnits;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.hostel.HostelUnitsForm;
import com.kp.cms.transactions.hostel.IHostelUnitsTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class HostelUnitsTransactionImpl implements IHostelUnitsTransaction {
	private static final Log log = LogFactory.getLog(HostelUnitsTransactionImpl.class);
	
	public boolean addHostelUnits(HlUnits hlUnits) throws Exception
	{
		log.info("Start of addHostelUnits of HostelUnits TransactionImpl");
		Session session = null;
		Transaction transaction = null;
		try {
			//session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			session.save(hlUnits);
			transaction.commit();
			return true;
		} catch (Exception e) {
			if(transaction != null){
				transaction.rollback();
			}
			log.error("Exception occured in adding HostelUnits in IMPL :"+e);
			throw  new ApplicationException(e);
		} finally {
		if (session != null) {
			session.flush();
			session.close();
		}
		log.info("End of addHostelUnits of HostelUnits TransactionImpl");
		}
	}
	
	public List<HlUnits> getHostelUnitsDetails()throws Exception
	{

		log.info("Start of getHostelUnitsDetails of HostelUnits TransactionImpl");
		Session session = null;
		List<HlUnits> hostelUnitsList;
		try {
		//	session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			hostelUnitsList = session.createQuery("from HlUnits e where e.isActive = 1 order by e.blocks.hlHostel.name, e.blocks.name, e.name ").list();
		} catch (Exception e) {			
			log.error("Exception occured in getHostelUnitsDetails in HostelUnitsIMPL :"+e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
		//	session.close();
			}
		}
		log.info("End of getHostelUnitsDetails of HostelUnits TransactionImpl");
		return hostelUnitsList;		
	}
	
	public boolean deleteHostelUnits(int id, String userId) throws Exception
	{
		log.info("Start of deleteHostelUnits of HostelUnits TransactionImpl");
		Session session = null;
		Transaction transaction = null;
		try {
			//session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			HlUnits hlUnits=(HlUnits)session.get(HlUnits.class,id);
			hlUnits.setIsActive(false);
			hlUnits.setLastModifiedDate(new Date());
			hlUnits.setModifiedBy(userId);
			session.update(hlUnits);
			transaction.commit();
			return true;
		} catch (Exception e) {
			if(transaction != null){
				transaction.rollback();
			}
			log.error("Exception occured in deleting HostelUnits in IMPL :"+e);	
			throw  new ApplicationException(e);
		} finally {
		if (session != null) {
			session.flush();
			session.close();
		}
		log.info("End of deleteHostelUnits of HostelUnits TransactionImpl");
		}
	}
	
	public boolean updateHostelUnits(HlUnits hlUnits)throws Exception
	{
		log.info("Start of updateHostelUnits of HostelUnits TransactionImpl");
		Session session = null;
		Transaction transaction = null;
		try {
			//session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			session.merge(hlUnits);
			transaction.commit();
			return true;
		} catch (Exception e) {
			if(transaction != null){
				transaction.rollback();
			}
			log.error("Exception occured while updating HostelUnits in IMPL :"+e);
			throw  new ApplicationException(e);
		} finally {
		if (session != null) {
			session.flush();
			session.close();		
		}
		log.info("End of updateHostelUnits of HostelUnits TransactionImpl");
		}
	}
	
	public boolean reActivateHostelUnits(String name, String userId)throws Exception
	{
		log.info("Start of reActivateHostelUnits of HostelUnits TransactionImpl");
		Session session = null;
		Transaction transaction = null;
			try {
				//SessionFactory sessionFactory = InitSessionFactory.getInstance();
				//session = sessionFactory.openSession();
				session = HibernateUtil.getSession();
				Query query = session.createQuery("from HlUnits e where e.name = :name");
				query.setString("name",name);
				HlUnits hlUnits = (HlUnits) query.uniqueResult();
				transaction = session.beginTransaction();
				hlUnits.setIsActive(true);
				hlUnits.setModifiedBy(userId);
				hlUnits.setLastModifiedDate(new Date());
				session.update(hlUnits);
				transaction.commit();
				return true;
				} catch (Exception e) {
				if(transaction != null){
					transaction.rollback();
				}
				log.error("Exception occured in reactivating of hostelUnits in IMPL :"+e);
				throw new ApplicationException(e);
				} finally {
					if (session != null) {
						session.flush();
						session.close();
					}
					log.info("End of reActivateHostelUnits of HostelUnits TransactionImpl");
				}
	}
	
	public HlUnits getUnitsDetailsonId(int id)throws Exception
	{
		log.info("Start of getDetailsonId of HostelUnits TransactionImpl");
		Session session = null;
		HlUnits hlUnits = null;
		try {
			//session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			
			Query query=session.createQuery("from HlUnits e where e.id= :row");
			query.setInteger("row", id);
			hlUnits = (HlUnits)query.uniqueResult();
		} catch (Exception e) {			
		log.error("Exception occured while getting the row based on the Id in IMPL :"+e);
			throw  new ApplicationException(e);
		} finally {
		if (session != null) {
			session.flush();
			//session.close();
		}
		}
		log.info("End of getDetailsonId of HostelUnits TransactionImpl");
		return hlUnits;	
	}
	
	public HlUnits checkForDuplicateonUnitsName(String blockId, String name)throws Exception
	{
		log.info("Start of checkForDuplicateonName of HostelUnits TransactionImpl");
		Session session = null;
		HlUnits hlUnits = null;
		try {
			//session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			Query query=session.createQuery("from HlUnits e where e.name='"+name+"' and e.blocks.id="+blockId);
			hlUnits = (HlUnits)query.uniqueResult();
		} catch (Exception e) {
		log.error("Exception occured in checking duplicate records for hostelUnits in IMPL :"+e);
			throw  new ApplicationException(e);
		} finally {
		if (session != null) {
			session.flush();
			//session.close();
		}
		}
		log.info("End of updateHostelUnits of HostelUnits TransactionImpl");
		return hlUnits;
	}
	
	public boolean checkForDuplicateonNameSameId(String name, HostelUnitsForm hostelUnitsForm)throws Exception
	{
		log.info("Start of checkForDuplicateonName1 of HostelUnits TransactionImpl");
		Session session = null;
		HlUnits hlUnits = null;
		boolean flag=false;
		try {
			//session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			String blockId = hostelUnitsForm.getBlockId();
			Query query=session.createQuery("from HlUnits e where e.name='"+name+"' and e.blocks.id="+blockId);
			hlUnits = (HlUnits)query.uniqueResult();
			if(hlUnits!=null){
			if(hlUnits.getId()!=0){
				if(hlUnits.getId()==hostelUnitsForm.getId())
				{
					flag=false;
				}
				else{
					flag=true;
				}
			}
			}
			
		} catch (Exception e) {
		log.error("Exception occured in checking duplicate records for hostelUnits in IMPL :"+e);
			throw  new ApplicationException(e);
		} finally {
		if (session != null) {
			session.flush();
			//session.close();
		}
		}
		log.info("End of updateHostelUnits of HostelUnits TransactionImpl");
		return flag;	
	}

}