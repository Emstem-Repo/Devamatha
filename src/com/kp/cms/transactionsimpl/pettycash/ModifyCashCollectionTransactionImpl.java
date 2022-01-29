package com.kp.cms.transactionsimpl.pettycash;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.PcAccountHead;
import com.kp.cms.bo.admin.PcReceiptNumber;
import com.kp.cms.bo.admin.PcReceipts;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.transactions.pettycash.IModifyCashCollectionTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class ModifyCashCollectionTransactionImpl implements IModifyCashCollectionTransaction{

	
	private static final Log log = LogFactory.getLog(ModifyCashCollectionTransactionImpl.class);
	
	private static volatile ModifyCashCollectionTransactionImpl modifyCashCollectionTransactionImpl;
	
	public static ModifyCashCollectionTransactionImpl getInstance()
	{
		if(modifyCashCollectionTransactionImpl==null)
		{
			modifyCashCollectionTransactionImpl = new ModifyCashCollectionTransactionImpl();
		return modifyCashCollectionTransactionImpl;
		}
		return modifyCashCollectionTransactionImpl;
	}

	@Override
	public List<PcReceipts> getDetailsToFill(String recNumber,String financialYearId) throws Exception {
	
	
		log.info("inside getDetailsToFill method");
		List<PcReceipts> pcReceiptList;
		String lastRecNumber;
		Session session = null;
		String maxNumber;
		try {
			/*SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from PcReceipts p where p.number="+"'"+recNumber+"'"+" and p.isCancelled=0 and p.isActive=1 and p.pcFinancialYear.id="+financialYearId);
			//receiptNumberList = query.list();
			pcReceiptList = query.list();
			session.flush();
			//session.close();
			//sessionFactory.close();
		} catch (Exception e) {
			log.error("Error during getting receipt Number..");
			if (session != null) {
				session.flush();
				session.close();
			}
			throw new ApplicationException(e);
		}
		log.info("leaving getDetailsToFill method");
		return pcReceiptList;
	}

	
	@Override
	public boolean updateOrSaveCashCollection(PcReceipts pcReceipts, String mode)throws Exception {
		log.debug("inside updateOrSaveCashCollection");
		Session session = null;
		PcReceiptNumber pcReceiptNumber;
		StringBuffer sqlString =null;
		Transaction transaction = null;
		boolean isUpdated = false;
		try {
			/*SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			
			if("Update".equalsIgnoreCase(mode))
			{	
				session.save(pcReceipts);
				transaction.commit();
				session.flush();
				session.close();
				//sessionFactory.close();
			}
			else
			{
				session.save(pcReceipts);	
				session.flush();
				session.close();
				//sessionFactory.close();
			}
			isUpdated = true;
			log.debug("leaving updateOrSaveCashCollection in impl");
		} catch (Exception e) {
			e.printStackTrace();
			if(transaction!=null){
				transaction.rollback();
			}
			log.error("Error during duplcation checking..." + e);			
			throw new ApplicationException(e);
		}
		finally{
			if(session!=null){
				session.flush();
				session.close();
			}
		}
		return isUpdated;
	}

	@Override
	public PcAccountHead getAccountheadtoAdd(int id) throws Exception {

		
	PcAccountHead accountHead = null;
		
		Session session = null;
		String maxNumber;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from PcAccountHead p where p.id="+id+" and p.isActive=1");
			//receiptNumberList = query.list();
			accountHead =(PcAccountHead)query.uniqueResult();
		} catch (Exception e) {
			if (session != null) {
				session.flush();
				session.close();
			}
			throw new ApplicationException(e);
		}
		finally{
			if(session!=null){
				session.flush();
				//session.close();
			}
		}
		log.info("leaving getDetailsToFill method");
		return accountHead;
	}

	@Override
	public PcAccountHead getAccountHeaddetails(int id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean updateCashCollection(List<PcReceipts> updatedList)
			throws Exception {
		log.info("Inside of updateCashCollection of ModifyCashCollectionTransactionImpl");
		PcReceipts pcReceipts;
		Transaction tx=null;
		Session session = null;
		try {
			/*SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();
			if(updatedList!=null){
				Iterator<PcReceipts> itr = updatedList.iterator();
				int count = 0;
				tx = session.beginTransaction();
				tx.begin();
				while (itr.hasNext()) {
					pcReceipts = itr.next();
					session.saveOrUpdate(pcReceipts);
					if (++count % 20 == 0) {
						session.flush();
						session.clear();
					}
				}
			}
			tx.commit();
			session.close();
			//sessionFactory.close();
			log.info("End of addPrivilege of AssignPrivilege TransactionImpl");
			return true;
		} catch (Exception e) {
			if(tx!=null){
				tx.rollback();
			}
			session.flush();
			session.clear();
			session.close();
			log.error("Error occured addPrivilege of AssignPrivilege TransactionImpl");
			throw new ApplicationException(e);
		}
	}
}
