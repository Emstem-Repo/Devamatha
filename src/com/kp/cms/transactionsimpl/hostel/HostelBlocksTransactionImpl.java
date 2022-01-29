package com.kp.cms.transactionsimpl.hostel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.HlBlocks;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.hostel.HostelBlocksForm;
import com.kp.cms.transactions.hostel.IHostelBlocksTransaction;
import com.kp.cms.transactionsimpl.hostel.HostelBlocksTransactionImpl;
import com.kp.cms.utilities.HibernateUtil;

public class HostelBlocksTransactionImpl implements IHostelBlocksTransaction {
	
	private static final Log log = LogFactory.getLog(HostelBlocksTransactionImpl.class);
	
	public boolean addHostelBlocks(HlBlocks hlBlocks) throws Exception
	{
		log.info("Start of addHostelBlocks of HostelBlocks TransactionImpl");
		Session session = null;
		Transaction transaction = null;
		try {
			//session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			session.save(hlBlocks);
			transaction.commit();
			return true;
		} catch (Exception e) {
			if(transaction != null){
				transaction.rollback();
			}
			log.error("Exception occured in adding HostelBlocks in IMPL :"+e);
			throw  new ApplicationException(e);
		} finally {
		if (session != null) {
			session.flush();
			session.close();
		}
		log.info("End of addHostelBlocks of HostelBlocks TransactionImpl");
		}
	}
	
	public List<HlBlocks> getHostelBlocksDetails()throws Exception{
		log.info("Start of getHostelBlocksDetails of HostelBlocks TransactionImpl");
		Session session = null;
		List<HlBlocks> hostelBlocksList;
		try {
		//	session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			hostelBlocksList = session.createQuery("from HlBlocks e where e.isActive = 1 ").list();
		} catch (Exception e) {			
			log.error("Exception occured in getHostelBlocksDetails in HostelBlocksIMPL :"+e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
		//	session.close();
			}
		}
		log.info("End of getHostelBlocksDetails of HostelBlocks TransactionImpl");
		return hostelBlocksList;		
	}
	
	public boolean deleteHostelBlocks(int id, String userId) throws Exception
	{
		log.info("Start of deleteHostelBlocks of HostelBlocks TransactionImpl");
		Session session = null;
		Transaction transaction = null;
		try {
			//session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			HlBlocks hlBlocks=(HlBlocks)session.get(HlBlocks.class,id);
			hlBlocks.setIsActive(false);
			hlBlocks.setLastModifiedDate(new Date());
			hlBlocks.setModifiedBy(userId);
			session.update(hlBlocks);
			transaction.commit();
			return true;
		} catch (Exception e) {
			if(transaction != null){
				transaction.rollback();
			}
			log.error("Exception occured in deleting HostelBlocks in IMPL :"+e);	
			throw  new ApplicationException(e);
		} finally {
		if (session != null) {
			session.flush();
			session.close();
		}
		log.info("End of deleteHostelBlocks of HostelBlocks TransactionImpl");
		}
	}
	
	public boolean updateHostelBlocks(HlBlocks hlBlocks)throws Exception
	{
		log.info("Start of updateHostelBlocks of HostelBlocks TransactionImpl");
		Session session = null;
		Transaction transaction = null;
		try {
			//session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			session.merge(hlBlocks);
			transaction.commit();
			return true;
		} catch (Exception e) {
			if(transaction != null){
				transaction.rollback();
			}
			log.error("Exception occured while updating HostelBlocks in IMPL :"+e);
			throw  new ApplicationException(e);
		} finally {
		if (session != null) {
			session.flush();
			session.close();		
		}
		log.info("End of updateHostelBlocks of HostelBlocks TransactionImpl");
		}
	}
	
	public boolean reActivateHostelBlocks(String name, String userId)throws Exception
	{

		log.info("Start of reActivateHostelBlocks of HostelBlocks TransactionImpl");
		Session session = null;
		Transaction transaction = null;
			try {
				//SessionFactory sessionFactory = InitSessionFactory.getInstance();
				//session = sessionFactory.openSession();
				session = HibernateUtil.getSession();
				Query query = session.createQuery("from HlBlocks e where e.name = :name");
				query.setString("name",name);
				HlBlocks hlBlocks = (HlBlocks) query.uniqueResult();
				transaction = session.beginTransaction();
				hlBlocks.setIsActive(true);
				hlBlocks.setModifiedBy(userId);
				hlBlocks.setLastModifiedDate(new Date());
				session.update(hlBlocks);
				transaction.commit();
				return true;
				} catch (Exception e) {
				if(transaction != null){
					transaction.rollback();
				}
				log.error("Exception occured in reactivating of hostelBlocks in IMPL :"+e);
				throw new ApplicationException(e);
				} finally {
					if (session != null) {
						session.flush();
						session.close();
					}
					log.info("End of reActivateHostelBlocks of HostelBlocks TransactionImpl");
				}			
	}
	
	public HlBlocks getDetailsonId(int id)throws Exception
	{
		log.info("Start of getDetailsonId of HostelBlocks TransactionImpl");
		Session session = null;
		HlBlocks hlBlocks = null;
		try {
			//session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			
			Query query=session.createQuery("from HlBlocks e where e.id= :row");
			query.setInteger("row", id);
			hlBlocks = (HlBlocks)query.uniqueResult();
		} catch (Exception e) {			
		log.error("Exception occured while getting the row based on the Id in IMPL :"+e);
			throw  new ApplicationException(e);
		} finally {
		if (session != null) {
			session.flush();
			//session.close();
		}
		}
		log.info("End of getDetailsonId of HostelBlocks TransactionImpl");
		return hlBlocks;	
	}
	
	public HlBlocks checkForDuplicateonName(String hostelId, String name)throws Exception
	{
		log.info("Start of checkForDuplicateonName of HostelBlocks TransactionImpl");
		Session session = null;
		HlBlocks hlBlocks = null;
		try {
			//session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			Query query=session.createQuery("from HlBlocks e where e.name='"+name+"' and e.hlHostel.id="+hostelId+" and e.isActive=1");
			hlBlocks = (HlBlocks)query.uniqueResult();
		} catch (Exception e) {
		log.error("Exception occured in checking duplicate records for hostelBlocks in IMPL :"+e);
			throw  new ApplicationException(e);
		} finally {
		if (session != null) {
			session.flush();
			//session.close();
		}
		}
		log.info("End of updateHostelBlocks of HostelBlocks TransactionImpl");
		return hlBlocks;
	}
	
	public boolean checkForDuplicateonName1(String name, HostelBlocksForm hostelBlocksForm)throws Exception
	{
		log.info("Start of checkForDuplicateonName1 of HostelBlocks TransactionImpl");
		Session session = null;
		HlBlocks hlBlocks = null;
		boolean flag=false;
		try {
			//session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			String hostelId = hostelBlocksForm.getHostelId();
			Query query=session.createQuery("from HlBlocks e where e.name='"+name+"' and e.hlHostel.id="+hostelId+" and e.isActive=1");
//			query.setString("name", name);
			hlBlocks = (HlBlocks)query.uniqueResult();
			if(hlBlocks!=null){
			if(hlBlocks.getId()!=0){
				if(hlBlocks.getId()==hostelBlocksForm.getId())
				{
					flag=false;
				}
				else{
					flag=true;
				}
			}
			}
			
		} catch (Exception e) {
		log.error("Exception occured in checking duplicate records for hostelBlocks in IMPL :"+e);
			throw  new ApplicationException(e);
		} finally {
		if (session != null) {
			session.flush();
			//session.close();
		}
		}
		log.info("End of updateHostelBlocks of HostelBlocks TransactionImpl");
		return flag;	
	}

}
