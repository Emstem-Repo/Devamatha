package com.kp.cms.transactionsimpl.inventory;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.InvItemStock;
import com.kp.cms.bo.admin.InvPurchaseOrder;
import com.kp.cms.bo.admin.InvPurchaseReturn;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.transactions.inventory.IPurchaseReturnTransaction;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;

public class PurchaseReturnTransactionImpl implements
		IPurchaseReturnTransaction {
	private static final Log log = LogFactory.getLog(PurchaseReturnTransactionImpl.class);
	@Override
	public InvPurchaseOrder getPurchaseorderDetails(String purchaseOrderNo)
			throws Exception {

		Session session = null;
		InvPurchaseOrder order=null;
		try {
			 SessionFactory sessionFactory = InitSessionFactory.getInstance();
			 session =sessionFactory.openSession();
			 //session = HibernateUtil.getSession();

			 Query query = session.createQuery("from InvPurchaseOrder n where n.orderNo= :orderNO and n.isActive=1");
			 query.setString("orderNO", purchaseOrderNo);
			 query.setReadOnly(true);
			 List<InvPurchaseOrder> list = query.list();
			 if(list!=null && !list.isEmpty())
				 order=list.get(0);
			 session.flush();
			 return order;
		 } catch (Exception e) {
			 log.error("Error during getting purchase order...",e);
			 session.flush();
			 throw  new ApplicationException(e);
		 }
	
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.inventory.IPurchaseReturnTransaction#placeFinalPurchaseReturn(com.kp.cms.bo.admin.InvPurchaseReturn)
	 */
	@Override
	public int placeFinalPurchaseReturn(InvPurchaseReturn finalOrder, Set<InvItemStock> itemStocks)
			throws Exception {


		int result= 0;
		Session session = null;
		Transaction txn=null;
		try {
			 //SessionFactory sessionFactory = InitSessionFactory.getInstance();
			 //session =sessionFactory.openSession();
			 session = HibernateUtil.getSession();
			 txn= session.beginTransaction();
			 result=(Integer)session.save(finalOrder);
			 if(itemStocks!=null){
				 Iterator<InvItemStock> itemItr=itemStocks.iterator();
				 while (itemItr.hasNext()) {
					InvItemStock itemStock = (InvItemStock) itemItr.next();
					 session.saveOrUpdate(itemStock);
				}
				
			 }
			 
			 txn.commit();

			 session.flush();
			
		 }catch (ConstraintViolationException e) {
			 	txn.rollback();
				log.error("Error in placeFinalPurchaseReturn...",e);
				 throw new BusinessException(e);
		}catch (Exception e) {
			 txn.rollback();
			log.error("Error in placeFinalPurchaseReturn...",e);
			 throw new ApplicationException(e);
		 }
		return result;
	
	
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.inventory.IPurchaseReturnTransaction#getAlreadyReturnedQty(int, int)
	 */
	@Override
	public double getAlreadyReturnedQty(int itemId, int purchaseOrderId)
			throws Exception {
		Session session = null;
		double result=0;
		try {
			 //SessionFactory sessionFactory = InitSessionFactory.getInstance();
			 //session =sessionFactory.openSession();
			 session = HibernateUtil.getSession();
			 Query query = session.createQuery("select sum(n.quantity) from InvPurchaseReturnItem n where n.invPurchaseReturn.invPurchaseOrder.id= :orderID and n.invItem.id= :itemID and n.isActive=1");
			 
			 query.setInteger("orderID", purchaseOrderId);
			 query.setInteger("itemID", itemId);
			 query.setReadOnly(true);
			 BigDecimal qty =(BigDecimal) query.uniqueResult();
			 if(qty!=null)
				 result= qty.doubleValue();
			 session.flush();
			 return result;
		 } catch (Exception e) {
			 log.error("Error during getting purchase order...",e);
			 session.flush();
			 throw  new ApplicationException(e);
		 }
	}

}
