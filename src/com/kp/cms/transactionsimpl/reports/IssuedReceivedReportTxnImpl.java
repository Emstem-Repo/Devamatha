package com.kp.cms.transactionsimpl.reports;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;

import com.kp.cms.bo.admin.InvTx;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.transactions.reports.IIssuedReceivedReportTxn;
import com.kp.cms.utilities.HibernateUtil;

public class IssuedReceivedReportTxnImpl implements IIssuedReceivedReportTxn{
private static final Log log = LogFactory.getLog(IssuedReceivedReportTxnImpl.class);
	
	public static volatile IssuedReceivedReportTxnImpl self=null;
	public static IssuedReceivedReportTxnImpl getInstance(){
		if(self==null){
			self= new IssuedReceivedReportTxnImpl();
		}
		return self;
	}
	private IssuedReceivedReportTxnImpl(){
		
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.inventory.IStocksReceiptTransaction#getItemTransactionsOnDate(java.sql.Date, int)
	 */

	public List<InvTx> getItemTransactionsOnDate(String dynamicQuery)
			throws Exception {

		Session session = null;
		
		try {
			 /*SessionFactory sessionFactory = InitSessionFactory.getInstance();
			 session =sessionFactory.openSession();*/
			session = HibernateUtil.getSession();
			 Query query = session.createQuery(dynamicQuery);

			 List<InvTx> list = query.list();
			
			 session.flush();
			 //session.close();
			 //sessionFactory.close();
			 return list;
		 } catch (Exception e) {
			 log.error("Error during getting item nos from amc table...",e);
			 session.flush();
			 session.close();
			 throw  new ApplicationException(e);
		 }
	
	
	
	}

}
