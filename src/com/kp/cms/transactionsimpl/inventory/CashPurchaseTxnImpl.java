package com.kp.cms.transactionsimpl.inventory;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.InvCashPurchase;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.transactions.inventory.ICashPurchaseTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class CashPurchaseTxnImpl implements ICashPurchaseTransaction {
	
	private static final Log log = LogFactory.getLog(CashPurchaseTxnImpl.class);

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.inventory.ICashPurchaseTransaction#itemAdded(java.util.List)
	 */
	@Override
	public boolean itemAdded(List<InvCashPurchase> transferItemList) throws Exception {
		Session session = null;
		Transaction transaction = null;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			InvCashPurchase invCashPurchase;

			if (transferItemList != null) {
				int count = 0;
				Iterator<InvCashPurchase> iterator = transferItemList.iterator();
				while (iterator.hasNext()) {
					invCashPurchase = iterator.next();
					session.save(invCashPurchase);
					if(++count % 20 == 0){
						session.flush();
						session.clear();
					}
				}
			}
			transaction.commit();
		} catch (ConstraintViolationException e) {
			log.error("Error while saving InvCashPurchase data.."+e);
			if (transaction != null) {
				transaction.rollback();
			}
			throw new BusinessException(e);
		} catch (Exception e) {
			log.error("Error while saving InvCashPurchase data ..."+e);
			if (transaction != null) {
				transaction.rollback();
			}
			throw new ApplicationException(e);
		} finally{
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.inventory.ICashPurchaseTransaction#itemAdded(com.kp.cms.bo.admin.InvCashPurchase)
	 */
	@Override
	public boolean itemAdded(InvCashPurchase invCashPurchase) throws Exception {
		Session session = null;
		Transaction transaction = null;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();

			if (invCashPurchase != null) {
				session.save(invCashPurchase);
			}
			transaction.commit();
		} catch (ConstraintViolationException e) {
			log.error("Error while saving InvCashPurchase data.."+e);
			if (transaction != null) {
				transaction.rollback();
			}
			throw new BusinessException(e);
		} catch (Exception e) {
			log.error("Error while saving InvCashPurchase data ..."+e);
			if (transaction != null) {
				transaction.rollback();
			}
			throw new ApplicationException(e);
		} finally{
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		return true;
	}
}