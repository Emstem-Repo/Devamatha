package com.kp.cms.transactionsimpl.inventory;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.InvCounter;
import com.kp.cms.bo.admin.InvItemStock;
import com.kp.cms.bo.admin.InvStockTransfer;
import com.kp.cms.bo.admin.InvTx;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.inventory.StockTransferForm;
import com.kp.cms.transactions.inventory.IStockTransferTransaction;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;

public class StockTransferTransactionImpl implements IStockTransferTransaction{
	
	private static final Log log = LogFactory.getLog(StockTransferTransactionImpl.class);
	
	/**
	 * Used to get Transfer No. for Stock transfer from InvCounter
	 */
	/*public String getTransferNo() throws Exception {
		log.info("Inside of getTransferNo of StockTransferTransactionImpl");
		String stockTransferCounter = CMSConstants.INVENTORY_COUNTER_TYPE;
		Session session = null;
		int generatedNo=0;
		String prefix=null;
		int startno=0;
		String generatedOrderNo=null;
		try {
			 SessionFactory sessionFactory = InitSessionFactory.getInstance();
			 session =sessionFactory.openSession();
			 session = HibernateUtil.getSession();

			 Query query = session.createQuery("from InvCounter a where a.type= :counter and a.isActive=1");
			 query.setString("counter", stockTransferCounter);

			 query.setReadOnly(true);
			 InvCounter cntr=(InvCounter)query.uniqueResult(); 
			 if(cntr!=null){
				 prefix=cntr.getPrefix();
				 startno=cntr.getStartNo();
			 }
			 String qry2="";
			 if(prefix!=null){
			 	qry2="select max(p.transferNo) from InvStockTransfer p where p.isActive=1 and p.transferNo LIKE '"+prefix+"%'";
			 }
			 else{
				 qry2="select max(p.transferNo) from InvStockTransfer p where p.isActive=1";
			 }
			 Query query2 = session.createQuery(qry2);
			 query2.setReadOnly(true);
			 		
			 int maxOrder=0;
			String maxorderNo=(String)query2.uniqueResult(); 
			 
			 if(prefix!=null && maxorderNo!=null && maxorderNo.lastIndexOf(prefix)!=-1){
				 String tempMax=maxorderNo.substring((maxorderNo.substring(maxorderNo.lastIndexOf(prefix), prefix.length()).length()),maxorderNo.length());
				 if(tempMax!=null && StringUtils.isNumeric(tempMax))
				 {
					 maxOrder=Integer.parseInt(tempMax);
				 }
			 }
			
			 if(maxOrder==0 || maxOrder<startno)
			 {
				 generatedNo=startno;
			 }
			 else{				
				 generatedNo=++maxOrder;
			 }
			 if(prefix!=null && generatedNo!=0)
				 generatedOrderNo=prefix+generatedNo;			 
			 session.flush();
			 session.close();
			 //sessionFactory.close();			
		 } catch (Exception e) {
			 log.error("Error during getTransferNo getting transaction no....",e);			
			 session.flush();
			 session.close();
			throw new ApplicationException(e);
		 }
		 log.info("Leaving getTransferNo of StockTransferTransactionImpl");
		 return generatedOrderNo;
	}*/
	/**
	 * Used to get Stock items based on the inventory location Id
	 */
	public List<InvItemStock> getItemStockOnInventory(int invLocationId)
			throws Exception {
		log.info("Inside of getItemStockOnInventory of StockTransferTransactionImpl");
		List<InvItemStock> itemStockList = null;
		Session session = null;
		try {
			//session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			itemStockList = session.createQuery
			("from InvItemStock i where i.isActive = 1 and i.invLocation.isActive = 1 and i.invLocation.id = " + invLocationId +"order by i.id").list();		
			log.info("End of getItemStockOnInventory of StockTransferTransactionImpl");
			return itemStockList;
		} catch (Exception e) {
			log.error("Error occured in getItemStockOnInventory of StockTransferTransactionImpl");
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
	}
	/**
	 * This method is used to transfer the stock (Save pupose)
	 * And
	 * Update the inventory present stock
	 */
	public boolean submitStockTransfer(InvStockTransfer stockTransfer,
			List<InvItemStock> newStockList, List<InvTx> invTXList) throws Exception {
		log.info("Entering into submitStockTransfer of StockTransferTransactionImpl");
		Session session = null;
		Transaction transaction = null;
		InvItemStock invItemStock;
		InvTx invTx;
		try {
			session = InitSessionFactory.getInstance().openSession();
			transaction = session.beginTransaction();
			//Used to save the stock transfer
			int referenceId = (Integer)session.save(stockTransfer);
			Query query = session.createQuery("select a from InvCounter a where a.type= :counter and a.isActive=1");
			query.setString("counter", CMSConstants.INVENTORY_COUNTER_TYPE);
			query.setLockMode("a", LockMode.UPGRADE);
			InvCounter counter =(InvCounter) query.uniqueResult();
			counter.setCurrentNo(stockTransfer.getTransferNo()+1);
			transaction.commit();
			session.flush();
			session.close();
			session = InitSessionFactory.getInstance().openSession();
			//Used to update the present stock
			transaction = session.beginTransaction();
			Iterator<InvItemStock> itr = newStockList.iterator();
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
			session.flush();
			session.close();
			session = InitSessionFactory.getInstance().openSession();
			//Used to save invTX
			transaction = session.beginTransaction();
			Iterator<InvTx> it = invTXList.iterator();
			int temp = 0;
			while (it.hasNext()) {
				invTx = it.next();
				invTx.setReferenceId(referenceId);
				session.save(invTx);
				if (++temp % 20 == 0) {
					session.flush();
					session.clear();
				}
			}
			transaction.commit();	
			log.info("Leaving into StockTransferTransactionImpl of submitRoomType");
			return true;
		} catch (Exception e) {	
			if(transaction != null){
				transaction.rollback();
			}
			log.error("Exception occured while transferring Stock in StockTransferTransactionImpl :"+e);
			throw  new ApplicationException(e);
		} finally {
		if (session != null) {
			session.flush();
			session.close();
		}
		}
	}
	
	
	/**
	 * Used to get Transfer No. for Stock transfer from InvCounter
	 */
	public String getTransferNo(StockTransferForm transferForm) throws Exception {
		log.info("Inside of getTransferNo of StockTransferTransactionImpl");
		String stockTransferCounter = CMSConstants.INVENTORY_COUNTER_TYPE;
		Session session = null;
		int currentNo=0;
		String prefix=null;
		int startno=0;
		String generatedOrderNo=null;
		try {
			 session = HibernateUtil.getSession();
			 Query query = session.createQuery("from InvCounter a where a.type= :counter and a.isActive=1");
			 query.setString("counter", stockTransferCounter);
			 query.setReadOnly(true);
			 InvCounter cntr=(InvCounter)query.uniqueResult(); 
			 if(cntr!=null){
				 prefix=cntr.getPrefix();
				 startno=cntr.getStartNo();
				 if(cntr.getCurrentNo()!=null){
					 currentNo=cntr.getCurrentNo();
				 }else{
					 currentNo=startno;
				 }
			 }
			 generatedOrderNo=prefix+currentNo;		
			 transferForm.setPrefix(prefix);
			 session.flush();
			 session.close();
		 } catch (Exception e) {
			 log.error("Error during getTransferNo getting transaction no....",e);			
			 session.flush();
			 session.close();
			throw new ApplicationException(e);
		 }
		 log.info("Leaving getTransferNo of StockTransferTransactionImpl");
		 return generatedOrderNo;
	}
}
