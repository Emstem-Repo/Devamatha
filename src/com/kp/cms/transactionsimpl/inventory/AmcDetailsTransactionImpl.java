package com.kp.cms.transactionsimpl.inventory;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.InvAmc;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.transactions.inventory.IAmcDetailsTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class AmcDetailsTransactionImpl implements IAmcDetailsTransaction {
	private static final Log log = LogFactory.getLog(AmcDetailsTransactionImpl.class);
	public static volatile AmcDetailsTransactionImpl amcDetailsTransactionImpl = null;
	
	public static AmcDetailsTransactionImpl getInstance() {
		if (amcDetailsTransactionImpl == null) {
			amcDetailsTransactionImpl = new AmcDetailsTransactionImpl();
			return amcDetailsTransactionImpl;
		}
		return amcDetailsTransactionImpl;
	}	

	
	/**
	 * This will retrieve all the AMC details
	 * 
	 * @return all InvAmc
	 * @throws Exception
	 */

	public List<InvAmc> getAmcDetails(int itemCategId, String itemNo) throws Exception {
		log.debug("inside getAmcDetails");
		Session session = null;
		
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			StringBuffer sqlString = new StringBuffer();
			
			sqlString.append("from InvAmc main"+
					" where warrantyStartDate = (select max(warrantyStartDate) from InvAmc sub where sub.invItemCategory.id = "+
					" main.invItemCategory.id and sub.itemNo = main.itemNo) and  main.isActive = 1 and invItemCategory.id = '" + itemCategId + "'" +
					" and main.warrantyAmcFlag = 'A'");
			if(itemNo != null && !itemNo.trim().isEmpty()){
				sqlString.append(" and main.itemNo= '" + itemNo + "'");
			}
			sqlString.append("group by main.itemNo");
			Query query = session.createQuery(sqlString.toString());			
			List<InvAmc> list = query.list();
			session.flush();
			//session.close();
			//sessionFactory.close();
			log.debug("leaving getAmcDetails");
			return list;
		 } catch (Exception e) {
			 log.error("Error in getAmcDetails...",e);
			 session.flush();
			 session.close();
			 throw  new ApplicationException(e);
		 }
	}
	/**
	 * This will retrieve all the warranty details
	 * 
	 * @return all InvAmc
	 * @throws Exception
	 */

	public List<InvAmc> getWarrenty(int itemCategId, String itemNo) throws Exception {
		log.debug("inside getAmcDetails");
		Session session = null;
		
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			StringBuffer sqlString = new StringBuffer();
			sqlString.append("from InvAmc main"+
					" where  main.isActive = 1 and invItemCategory.id = '" + itemCategId + "'" +
					" and main.warrantyAmcFlag = 'W'");
			if(itemNo != null && !itemNo.trim().isEmpty()){
				sqlString.append(" and main.itemNo= '" + itemNo + "'");
			}
			Query query = session.createQuery(sqlString.toString());			
			List<InvAmc> list = query.list();
			session.flush();
			//session.close();
			//sessionFactory.close();
			log.debug("leaving getAmcDetails");
			return list;
		 } catch (Exception e) {
			 log.error("Error in getAmcDetails...",e);
			 session.flush();
			 session.close();
			 throw  new ApplicationException(e);
		 }
	}

	 /**
	  * saving to table
	  */
	public boolean addAmcDetails(List<InvAmc> amcBOList) throws Exception {
		log.debug("inside addAmcDetails");
		Session session = null;
		Transaction transaction = null;
		InvAmc invAmc;
		try {
			//session = HibernateUtil.getSessionFactory().openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			Iterator<InvAmc> iterator = amcBOList.iterator();
			int count = 0;
			while(iterator.hasNext()){
				invAmc = iterator.next();
				session.save(invAmc);
				if(++count % 20 == 0){
					session.flush();
					session.clear();
				}
			}
			
			transaction.commit();
			session.flush();
			session.close();
			log.debug("leaving addAmcDetails");
			return true;
		} catch (ConstraintViolationException e) {
			transaction.rollback();
			log.error("Error in addAmcDetails impl...", e);
			throw new BusinessException(e);
		} catch (Exception e) {
			transaction.rollback();
			log.error("Error in addAmcDetails impl...", e);
			throw new ApplicationException(e);
		}
	}
	
	/**
	 * This will retrieve all the AMC History details
	 * 
	 * @return all InvAmc
	 * @throws Exception
	 */

	public List<InvAmc> getAmcHistoryDetails(int itemCategId, String selectedItemNo) throws Exception {
		log.debug("inside getAmcDetails");
		Session session = null;
		
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			StringBuffer sqlString = new StringBuffer();
			sqlString.append("from InvAmc main"+
					" where main.isActive = 1 and main.invItemCategory.id = '" + itemCategId + "'" +
					" and main.warrantyAmcFlag = 'A' and main.itemNo= '" + selectedItemNo + "'");
			Query query = session.createQuery(sqlString.toString());			
			List<InvAmc> list = query.list();
			session.flush();
			//session.close();
			//sessionFactory.close();
			log.debug("leaving getAmcHistoryDetails");
			return list;
		 } catch (Exception e) {
			 log.error("Error in getAmcHistoryDetails...",e);
			 session.flush();
			 session.close();
			 throw  new ApplicationException(e);
		 }
	}

}