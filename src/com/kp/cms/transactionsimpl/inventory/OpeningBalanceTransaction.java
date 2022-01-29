package com.kp.cms.transactionsimpl.inventory;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.InvItem;
import com.kp.cms.bo.admin.InvItemStock;
import com.kp.cms.bo.admin.InvLocation;
import com.kp.cms.bo.admin.InvTx;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.transactions.inventory.IOpeningBalanceTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class OpeningBalanceTransaction implements IOpeningBalanceTransaction {
	private static final Log log = LogFactory.getLog(OpeningBalanceTransaction.class);
	@Override
	public boolean saveItemStock(int locationID, int itemID, double qty, String user)
			throws Exception {
		boolean result= false;
		Session session = null;
		Transaction txn=null;
		try {
			 //SessionFactory sessionFactory = InitSessionFactory.getInstance();
			 //session =sessionFactory.openSession();
			 session = HibernateUtil.getSession();
			 Query query = session.createQuery("from InvItemStock n where n.isActive=1 and n.invLocation.id=:locationid and n.invItem.id= :itemId");
			 query.setInteger("locationid", locationID);
			 query.setInteger("itemId", itemID);
			 query.setReadOnly(true);
			 InvItemStock itemstock =(InvItemStock) query.uniqueResult();
			 
			 if(itemstock!=null)
			 {
				 
				double exitingQty=0.0;
				double openingQty=0.0;
				if(itemstock.getAvailableStock()!=null){
					exitingQty=itemstock.getAvailableStock().doubleValue();
					openingQty=itemstock.getAvailableStock().doubleValue();
				}
				double newqty= exitingQty+qty;
//				itemstock.setAvailableStock(BigDecimal.valueOf(newqty));
//				itemstock.setModifiedBy(user);
//				itemstock.setLastModifiedDate(new Date());
//
				
				query = session.createQuery("update InvItemStock n set n.availableStock=:newStock,n.modifiedBy=:modifiedBy,n.lastModifiedDate=:lastModifiedDate where n.id=:ID");
				
				 query.setInteger("ID",itemstock.getId());
				 query.setBigDecimal("newStock", new BigDecimal(newqty));
				 query.setString("modifiedBy",user);
				 query.setDate("lastModifiedDate",new Date());
				 
				 query.executeUpdate();
				
				InvTx trans= new InvTx();
				trans.setInvItem(itemstock.getInvItem());
				trans.setInvLocation(itemstock.getInvLocation());
				trans.setReferenceId(itemstock.getId());
				trans.setTxDate(new Date());
				trans.setTxType(CMSConstants.RECEIPT_TX_TYPE);
				trans.setQuantity(new BigDecimal(qty));
				trans.setOpeningBalance(new BigDecimal(openingQty));
				trans.setClosingBalance(new BigDecimal(newqty));
				trans.setIsActive(true);
				trans.setCreatedBy(user);
				trans.setCreatedDate(new Date());
				txn= session.beginTransaction();
					session.saveOrUpdate(itemstock);
				
					session.saveOrUpdate(trans);
				 txn.commit();
			 }else{
				 txn= session.beginTransaction();
				 itemstock = new InvItemStock();
				
					itemstock.setAvailableStock(BigDecimal.valueOf(qty));
					itemstock.setCreatedBy(user);
					itemstock.setCreatedDate(new Date());
					InvItem item= new InvItem();
					item.setId(itemID);
					itemstock.setInvItem(item);
					
					InvLocation location= new InvLocation();
					location.setId(locationID);
					itemstock.setInvLocation(location);
					itemstock.setIsActive(true);
					txn= session.beginTransaction();
					int ref=(Integer)session.save(itemstock);
					
					InvTx trans= new InvTx();
					trans.setInvItem(itemstock.getInvItem());
					trans.setInvLocation(itemstock.getInvLocation());
					trans.setReferenceId(ref);
					trans.setTxDate(new Date());
					trans.setTxType(CMSConstants.RECEIPT_TX_TYPE);
					trans.setQuantity(new BigDecimal(qty));
					trans.setOpeningBalance(new BigDecimal(qty));
					trans.setClosingBalance(new BigDecimal(qty));
					trans.setCreatedBy(user);
					trans.setCreatedDate(new Date());
					trans.setIsActive(true);
				
					 session.saveOrUpdate(trans);
					 txn.commit();
			 }
			
			 
			

			 session.flush();
			 session.close();
			 //sessionFactory.close();
			 result=true;
		 }catch (ConstraintViolationException e) {
			 	txn.rollback();
				log.error("Error in saveItemStock...",e);
				 throw new BusinessException(e);
		}catch (Exception e) {
			 txn.rollback();
			log.error("Error in saveItemStock...",e);
			 throw new ApplicationException(e);
		 }
		return result;
	
	}
	
}
