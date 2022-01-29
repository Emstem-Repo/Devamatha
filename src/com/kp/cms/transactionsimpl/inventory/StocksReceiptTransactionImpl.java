package com.kp.cms.transactionsimpl.inventory;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.InvItemStock;
import com.kp.cms.bo.admin.InvPurchaseOrder;
import com.kp.cms.bo.admin.InvStockReceipt;
import com.kp.cms.bo.admin.InvTx;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.transactions.inventory.IStocksReceiptTransaction;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.HibernateUtil;

/**
 * Txn. Impl. class for Stocks Reciept
 * 
 */
public class StocksReceiptTransactionImpl implements IStocksReceiptTransaction {
	private static final Log log = LogFactory
			.getLog(StocksReceiptTransactionImpl.class);

	@Override
	public int updateFinalStockReceipt(InvStockReceipt finalOrder,
			List<InvItemStock> itemStockList) throws Exception {

		int result = 0;
		Session session = null;
		Transaction txn = null;
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			// session =sessionFactory.openSession();
			session = HibernateUtil.getSession();
			txn = session.beginTransaction();
			result = (Integer) session.save(finalOrder);
			if (itemStockList != null) {
				Iterator<InvItemStock> stkItr = itemStockList.iterator();
				while (stkItr.hasNext()) {
					InvItemStock invItemStock = (InvItemStock) stkItr.next();
					session.saveOrUpdate(invItemStock);
				}
			}
			txn.commit();

			session.flush();
			session.close();
			// sessionFactory.close();

		} catch (ConstraintViolationException e) {
			txn.rollback();
			log.error("Error in placeFinalPurchaseReturn...", e);
			throw new BusinessException(e);
		} catch (Exception e) {
			txn.rollback();
			log.error("Error in placeFinalPurchaseReturn...", e);
			throw new ApplicationException(e);
		}
		return result;

	}

	@Override
	public List<String> getAllItemNosInAMC() throws Exception {

		Session session = null;
		InvPurchaseOrder order = null;
		try {
			/*
			 * SessionFactory sessionFactory = InitSessionFactory.getInstance();
			 * session =sessionFactory.openSession();
			 */
			session = HibernateUtil.getSession();

			Query query = session
					.createQuery("select n.itemNo from InvAmc n where n.isActive=1");

			query.setReadOnly(true);
			List<String> list = query.list();

			session.flush();
			// session.close();
			// sessionFactory.close();
			return list;
		} catch (Exception e) {
			log.error("Error during getting item nos from amc table...", e);
			session.flush();
			session.close();
			throw new ApplicationException(e);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.kp.cms.transactions.inventory.IStocksReceiptTransaction#
	 * updateTransactionDetails(com.kp.cms.bo.admin.InvTx)
	 */
	@Override
	public void updateTransactionDetails(InvTx txnDet) throws Exception {
		Session session = null;
		Transaction txn = null;
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			// session =sessionFactory.openSession();
			session = HibernateUtil.getSession();
			txn = session.beginTransaction();
			session.save(txnDet);

			txn.commit();

			session.flush();
			session.close();
			// sessionFactory.close();
		} catch (ConstraintViolationException e) {
			txn.rollback();
			log.error("Error in updateTransactionDetails...", e);
			throw new BusinessException(e);
		} catch (Exception e) {
			txn.rollback();
			log.error("Error in updateTransactionDetails...", e);
			throw new ApplicationException(e);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.kp.cms.transactions.inventory.IStocksReceiptTransaction#
	 * getItemTransactionsOnDate(java.sql.Date, int)
	 */
	@Override
	public List<InvTx> getItemTransactionsOnDate(String txDate, int locationId,String endDate)
			throws Exception {

		Session session = null;

		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			// session =sessionFactory.openSession();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from InvTx invtx where invtx.id in ( select max(invtx1.id) from InvTx invtx1 where invtx1.isActive=1 and invtx1.invLocation.id="
							+ locationId
							+ " and invtx1.txDate >= '"
							+ CommonUtil.ConvertStringToSQLDate(txDate)+"' and invtx1.txDate <='"+CommonUtil.ConvertStringToSQLDate(endDate)
							+ "' group by invtx1.invItem.id)");

			List<InvTx> list = query.list();

			session.flush();
			// session.close();
			// sessionFactory.close();
			return list;
		} catch (Exception e) {
			log.error("Error during getting item nos from amc table...", e);
			session.flush();
			session.close();
			throw new ApplicationException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.kp.cms.transactions.inventory.IStocksReceiptTransaction#
	 * getItemTransactionsOnDetail(java.lang.String, int, int)
	 */
	@Override
	public List<InvTx> getItemTransactionsOnDetail(String selDt, int txnId,
			int selLoc) throws Exception {
		Session session = null;
		List<InvTx> list = null;
		try {
			/*
			 * SessionFactory sessionFactory = InitSessionFactory.getInstance();
			 * session =sessionFactory.openSession();
			 */
			session = HibernateUtil.getSession();
			InvTx txn = (InvTx) session.get(InvTx.class, txnId);
			if (txn != null && txn.getIsActive() != null && txn.getIsActive()
					&& txn.getInvItem() != null) {
				int itemid = txn.getInvItem().getId();
				Query query = session
						.createQuery("from InvTx invtx where invtx.invLocation.id="
								+ selLoc
								+ " and invtx.txDate='"
								+ selDt
								+ "' and invtx.invItem.id=" + itemid);
				list = query.list();
			}

			session.flush();
			// session.close();
			// sessionFactory.close();
			return list;
		} catch (Exception e) {
			log.error("Error during getting item nos from amc table...", e);
			session.flush();
			session.close();
			throw new ApplicationException(e);
		}
	}

	@Override
	public double getAlreadyRecievedQty(int itemId, int purchaseOrderId)
			throws Exception {
		Session session = null;
		double result = 0;
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			// session =sessionFactory.openSession();
			session = HibernateUtil.getSession();

			Query query = session
					.createQuery("select sum(n.quantity) from InvStockReceiptItem n where n.invStockReceipt.invPurchaseOrder.id= :orderID and n.invItem.id= :itemID and n.isActive=1");

			query.setInteger("orderID", purchaseOrderId);
			query.setInteger("itemID", itemId);
			query.setReadOnly(true);
			BigDecimal qty = (BigDecimal) query.uniqueResult();
			if (qty != null)
				result = qty.doubleValue();
			session.flush();
			// session.close();
			// sessionFactory.close();
			return result;
		} catch (Exception e) {
			log.error("Error during getting purchase order...", e);
			session.flush();
			session.close();
			throw new ApplicationException(e);
		}
	}

	public List getStockdetails(String date, int locationId)
			throws Exception {
		Session session = null;
		List list = null;
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			// session =sessionFactory.openSession();
			session = HibernateUtil.getSession();

			Query query = session
					.createQuery("select t.invItem.id,t.invItem.name, t.openingBalance, t.closingBalance,t.txType,t.txDate from " +
							"InvLocation invl join invl.invTxes t where invl.id= "+locationId+" and t.txDate<='"+date+"' order by t.invItem.id");

			
			
			list = query.list();
				
			session.flush();
			// session.close();
			// sessionFactory.close();
			return list;
		} catch (Exception e) {
			log.error("Error during getting purchase order...", e);
			session.flush();
			session.close();
			throw new ApplicationException(e);
		}
	}

}
