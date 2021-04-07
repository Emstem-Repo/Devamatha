package com.kp.cms.transactionsimpl.inventory;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;

import com.kp.cms.bo.admin.InvCashPurchaseItem;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.transactions.inventory.ICashPurchaseReportTran;
import com.kp.cms.utilities.HibernateUtil;


public class CashpurchaseReportImpl implements ICashPurchaseReportTran{
	private static final Log log = LogFactory.getLog(CashpurchaseReportImpl.class);
	public static volatile CashpurchaseReportImpl cashpurchaseReportImpl= null;
	
	public static CashpurchaseReportImpl getInstance(){
		if(cashpurchaseReportImpl == null){
			cashpurchaseReportImpl = new CashpurchaseReportImpl();
		}
		return cashpurchaseReportImpl;
	}
	/**
	 * 
	 */
	public List<InvCashPurchaseItem> getCashPurchaseItems(Date startDate, Date endDate, int locationId) throws Exception{
		log.debug("start getCashPurchaseItems in impl");
		Session session = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from InvCashPurchaseItem i where i.invCashPurchase.isActive = 1 and " + 
					" i.isActive = 1 and i.invCashPurchase.cashPurchaseDate between '" + startDate + "'" + " and '" + endDate + "'" +
					" and i.invCashPurchase.invLocation.id = " + locationId);
			List<InvCashPurchaseItem> list = query.list();
			session.flush();
			//session.close();
			//sessionFactory.close();
			log.debug("exit getCashPurchaseItems");
			return list;
		 } catch (Exception e) {
			 log.error("Error in getCashPurchaseItems...",e);
			 session.flush();
			 session.close();
			 throw  new ApplicationException(e);
		 }
		
	}
	
}
