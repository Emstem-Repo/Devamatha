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
import com.kp.cms.bo.admin.InvStockReceipt;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.transactions.inventory.IUpdateWarrantyDetailsTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class UpdateWarrantyDetailsTransactionImpl implements IUpdateWarrantyDetailsTransaction {
	private static final Log log = LogFactory.getLog(UpdateWarrantyDetailsTransactionImpl.class);
	public static volatile UpdateWarrantyDetailsTransactionImpl updateWarrantyDetailsTransactionImpl = null;
	
	public static UpdateWarrantyDetailsTransactionImpl getInstance() {
		if (updateWarrantyDetailsTransactionImpl == null) {
			updateWarrantyDetailsTransactionImpl = new UpdateWarrantyDetailsTransactionImpl();
			return updateWarrantyDetailsTransactionImpl;
		}
		return updateWarrantyDetailsTransactionImpl;
	}
	
	/**
	 * This will retrieve all the AMC details
	 * 
	 * @return all InvAmc
	 * @throws Exception
	 */

	public List<InvStockReceipt> getStockReceiptItems(String orderNo) throws Exception {
		log.debug("inside getStockReceiptItems");
		Session session = null;
		
		try {
			/*SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();
			StringBuffer sqlString = new StringBuffer();
			
			sqlString.append("from InvStockReceipt inv where inv.isActive = 1" +
					" and inv.invPurchaseOrder.orderNo= '" + orderNo + "'");

			Query query = session.createQuery(sqlString.toString());			
			List<InvStockReceipt> list = query.list();
			session.flush();
			//session.close();
			//sessionFactory.close();
			log.debug("leaving getStockReceiptItems");
			return list;
		 } catch (Exception e) {
			 log.error("Error in getStockReceiptItems...",e);
			 session.flush();
			 session.close();
			 throw  new ApplicationException(e);
		 }
	}
	
	 /**
	  * saving to table
	  */
	public boolean addWarrantyDetails(List<InvAmc> amcBOList) throws Exception {
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
	
}
