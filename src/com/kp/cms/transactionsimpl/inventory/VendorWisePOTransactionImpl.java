package com.kp.cms.transactionsimpl.inventory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;

import com.kp.cms.bo.admin.InvCounter;
import com.kp.cms.bo.admin.InvPurchaseOrder;
import com.kp.cms.bo.admin.InvPurchaseReturn;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.transactions.inventory.IVendorWisePOTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class VendorWisePOTransactionImpl implements IVendorWisePOTransaction{
	
	private static final Log log = LogFactory.getLog(VendorWisePOTransactionImpl.class);
	/**
	 * Gets purchase order details by vendor Id
	 */
	public List<InvPurchaseOrder> getPurchaseOrdersByVendor(int vendorId)
			throws Exception {
		log.info("Inside of getPurchaseOrdersByVendor of VendorWisePOTransactionImpl");
		List<InvPurchaseOrder> puchaseList = null;
		Session session = null;
		try {
			//session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			puchaseList = session.createQuery
			("from InvPurchaseOrder i where i.isActive = 1 and i.invVendor.isActive = 1 and i.invVendor.id = " + vendorId +"order by i.id").list();		
			log.info("End of getPurchaseOrdersByVendor of VendorWisePOTransactionImpl");
			return puchaseList;
		} catch (Exception e) {
			log.error("Error occured in getPurchaseOrdersByVendor of VendorWisePOTransactionImpl",e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
	}
	/**
	 * Gets purchase order bu purchase order ID
	 */
	public InvPurchaseOrder getPurchaseOrderDetailsByID(int purchaseOrderID)
			throws Exception {
		log.info("Inside of getPurchaseOrderDetailsByID of VendorWisePOTransactionImpl");
		InvPurchaseOrder order = null;
		Session session = null;
		try {
			//session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			order = (InvPurchaseOrder) session.createQuery
			("from InvPurchaseOrder i where i.isActive = 1 and i.id = " + purchaseOrderID ).uniqueResult();		
			log.info("End of getPurchaseOrderDetailsByID of VendorWisePOTransactionImpl");
			return order;
		} catch (Exception e) {
			log.error("Error occured in getPurchaseOrderDetailsByID of VendorWisePOTransactionImpl",e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
	}
	/**
	 * Used to get all purchase order Nos
	 */
	public List<String> getAllPurchaseOrderNos() throws Exception {
		log.info("Inside of getAllPurchaseOrderNos of VendorWisePOTransactionImpl");
		List<String> orderNoList = new ArrayList<String>();
		Session session = null;
		String prefix="";
		try {
			//session = InitSessionFactory.getInstance().openSession();
			 session = HibernateUtil.getSession();
			List<Integer> list = session.createQuery("select i.orderNo from InvPurchaseOrder i where i.isActive = 1 and i.orderNo is not null order by i.id").list();	
			 Query query = session.createQuery("select a from InvCounter a where a.type= :counter and a.isActive=1");
			 query.setString("counter",CMSConstants.PURCHASE_ORDER_COUNTER);
			 InvCounter invCounter=(InvCounter)query.uniqueResult();
			 if(invCounter!=null){
				 prefix=invCounter.getPrefix();
			 }
			if(list!=null && !list.isEmpty()){
				Iterator<Integer> itr=list.iterator();
				while (itr.hasNext()) {
					Integer integer = (Integer) itr.next();
					if(integer>0)
					orderNoList.add(prefix+integer);
				}
			}
			
			log.info("End of getAllPurchaseOrderNos of VendorWisePOTransactionImpl");
			return orderNoList;
		} catch (Exception e) {
			log.error("Error occured in getAllPurchaseOrderNos of VendorWisePOTransactionImpl",e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
	}
	/**
	 * Gets puchase returns base on purchase no
	 */
	public List<InvPurchaseReturn> getPurchaseReturnsByPurchaseOrderNo(
			String purchaseNumber) throws Exception {
		log.info("Inside of getPurchaseReturnsByPurchaseOrderNo of VendorWisePOTransactionImpl");
		Session session = null;
		List<InvPurchaseReturn> boList = null;
		try { 
			 //session = InitSessionFactory.getInstance().openSession();
			 session = HibernateUtil.getSession();
			 Query query = session.createQuery("from InvPurchaseReturn i where i.isActive = 1 and" +
			 								   " i.invPurchaseOrder.isActive = 1" +
			 								   " and i.invPurchaseOrder.orderNo = :purchaseNumber");				                          
			 query.setString("purchaseNumber",purchaseNumber);
			 boList = query.list();	
			 log.info("End of getPurchaseReturnsByPurchaseOrderNo of VendorWisePOTransactionImpl");
			 return boList;
			}
			catch (Exception e) {	
				log.error("Exception occured while getPurchaseReturnsByPurchaseOrderNo in VendorWisePOTransactionImpl :"+e);
				throw  new ApplicationException(e);
			} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}	
			}
		}	
	/**
	 * Gets purchase returns based on the purchase return id
	 */
	public InvPurchaseReturn getPurchaseReturnDetailsByID(int purchaseReturnId)
			throws Exception {
		log.info("Inside of getPurchaseReturnDetailsByID of VendorWisePOTransactionImpl");
		InvPurchaseReturn purchaseReturn = null;
		Session session = null;
		try {
			//session = InitSessionFactory.getInstance().openSession();
			 session = HibernateUtil.getSession();
			purchaseReturn = (InvPurchaseReturn) session.createQuery
			("from InvPurchaseReturn i where i.isActive = 1 and i.id = " + purchaseReturnId ).uniqueResult();		
			log.info("End of getPurchaseReturnDetailsByID of VendorWisePOTransactionImpl");
			return purchaseReturn;
		} catch (Exception e) {
			log.error("Error occured in getPurchaseReturnDetailsByID of VendorWisePOTransactionImpl",e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
	}
}
