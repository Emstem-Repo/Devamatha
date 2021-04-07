package com.kp.cms.transactionsimpl.inventory;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.InvIssue;
import com.kp.cms.bo.admin.InvItemStock;
import com.kp.cms.bo.admin.InvSalvage;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.transactions.inventory.IsalvageItemTransaction;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;

public class SalvageItemTransactionImpl implements IsalvageItemTransaction{

	
private static final Log log = LogFactory.getLog(SalvageItemTransactionImpl.class);
	
	public static volatile SalvageItemTransactionImpl self=null;
	
	/**
	 * @return unique instance of LeaveReportTransactionImpl class.
	 * This method gives instance of this method
	 */
	public static SalvageItemTransactionImpl getInstance(){
		if(self==null)
			self= new SalvageItemTransactionImpl();
		return self;
	}

	@Override
	public int saveSalvageDetails(InvSalvage salvage, List<InvItemStock> updateStockList) throws Exception {
		log.info("call of saveSalvageDetails in SalvageItemTransactionImpl class.");
		Session session = null;
		Transaction transaction = null;
		int result= 0;
		InvItemStock invItemStock;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			result = (Integer)session.save(salvage);
			transaction.commit();
			session.flush();
			session.close();
			//Used to update the item stock
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			Iterator<InvItemStock> itr = updateStockList.iterator();
			int count = 0;
			while (itr.hasNext()) {
				invItemStock = itr.next();
				session.saveOrUpdate(invItemStock);
				if (++count % 20 == 0) {
					session.flush();
					session.clear();
				}
			}
			transaction.commit();
		} catch (Exception e) {
			result= 0;
			log.error("Unable to saveSalvageDetails" , e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		log.info("end of saveSalvageDetails in SalvageItemTransactionImpl class.");
		return result;
	}

	@Override
	public int saveItemIssueDetails(InvIssue invIssue,List<InvItemStock> updateStockList) throws Exception {
		log.info("call of saveItemIssueDetails in SalvageItemTransactionImpl class.");
		Session session = null;
		Transaction transaction = null;
		int result= 0;
		InvItemStock invItemStock;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			result = (Integer)session.save(invIssue);
			transaction.commit();
			session.flush();
			session.close();
			
			session = HibernateUtil.getSession();
			//Used to update the item stock
			transaction = session.beginTransaction();
			Iterator<InvItemStock> itr = updateStockList.iterator();
			int count = 0;
			while (itr.hasNext()) {
				invItemStock = itr.next();
				session.saveOrUpdate(invItemStock);
				if (++count % 20 == 0) {
					session.flush();
					session.clear();
				}
			}
			transaction.commit();
			
		} catch (Exception e) {
			result= 0;
			if(transaction != null){
				transaction.rollback();
			}
			log.error("Unable to saveSalvageDetails" , e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		log.info("end of saveItemIssueDetails in SalvageItemTransactionImpl class.");
		return result;
	}

	@Override
	public List<InvSalvage> getReportDetails(String searchCriteria)
			throws Exception {
		log.info("entered getReportDetails in SalvageItemTransactionImpl class ..");
		Session session = null;
		List<InvSalvage> salvageList = null;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			salvageList = session.createQuery(searchCriteria).list();
		
		} catch (Exception e) {
			log.error("error while getting the getReportDetails results  ",e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		log.info("exit getReportDetails in SalvageItemTransactionImpl class..");
		return salvageList;
	}
}