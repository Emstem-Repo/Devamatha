package com.kp.cms.transactionsimpl.inventory;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;

import com.kp.cms.bo.admin.InvAmc;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.transactions.inventory.IAmcDueIn30DaysReport;
import com.kp.cms.utilities.HibernateUtil;

public class AmcDueIn30DaysTransactionImpl implements IAmcDueIn30DaysReport {
	private static Log log = LogFactory.getLog(AmcDueIn30DaysTransactionImpl.class);
	
	public static volatile AmcDueIn30DaysTransactionImpl amcDueIn30DaysTransactionImpl = null;
	
	public static AmcDueIn30DaysTransactionImpl getInstance() {
		if (amcDueIn30DaysTransactionImpl == null) {
			amcDueIn30DaysTransactionImpl = new AmcDueIn30DaysTransactionImpl();
		}
		return amcDueIn30DaysTransactionImpl;
	}		
	/**
	 * This will retrieve all the AMC History details
	 * 
	 * @return all InvAmc
	 * @throws Exception
	 */

	public List<InvAmc> getAmcDueIn30Days(Date startDate, Date endDate, int invLocationId) throws Exception {
		log.debug("inside getAmcDueIn30Days");
		Session session = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			StringBuffer sqlString = new StringBuffer();
			if(invLocationId !=0){
				sqlString.append("from InvAmc main where main.isActive = 1 " +
						" and warrantyEndDate between '" + startDate + "' and '" + endDate + "' and invStockReceiptItem.invStockReceipt.invLocation.id = '" + invLocationId + "'");
			}else{
				sqlString.append("from InvAmc main where main.isActive = 1 " +
						" and warrantyEndDate between '" + startDate + "' and '" + endDate + "'" + "order by invStockReceiptItem.invStockReceipt.invLocation.name");
			}
			
			Query query = session.createQuery(sqlString.toString());			
			List<InvAmc> list = query.list();
			session.flush();
			//session.close();
			//sessionFactory.close();
			log.debug("leaving getAmcDueIn30Days");
			return list;
		 } catch (Exception e) {
			 log.error("Error in getAmcDueIn30Days...",e);
			 session.flush();
			 session.close();
			 throw  new ApplicationException(e);
		 }
	}
}
