package com.kp.cms.transactionsimpl.fee;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.kp.cms.bo.admin.FeePayment;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.transactions.fee.IBulkChallanPrintTransaction;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;

public class BulkChallanPrintTransactionimpl implements
		IBulkChallanPrintTransaction {
	/**
	 * Singleton object of BulkChallanPrintTransactionimpl
	 */
	private static volatile BulkChallanPrintTransactionimpl bulkChallanPrintTransactionimpl = null;
	private static final Log log = LogFactory.getLog(BulkChallanPrintTransactionimpl.class);
	private BulkChallanPrintTransactionimpl() {
		
	}
	/**
	 * return singleton object of BulkChallanPrintTransactionimpl.
	 * @return
	 */
	public static BulkChallanPrintTransactionimpl getInstance() {
		if (bulkChallanPrintTransactionimpl == null) {
			bulkChallanPrintTransactionimpl = new BulkChallanPrintTransactionimpl();
		}
		return bulkChallanPrintTransactionimpl;
	}
	/* (non-Javadoc)
	 * Querying the database to get the challans printed in the input date range
	 * @see com.kp.cms.transactions.fee.IBulkChallanPrintTransaction#getChallanData(java.lang.String, java.lang.String)
	 */
	@Override
	public List<FeePayment> getChallanData(String fromDate, String toDate) throws Exception {
		Session session = null;
		List<FeePayment> feePayment = null;
		try {
			 //SessionFactory sessionFactory = InitSessionFactory.getInstance();
			 session = HibernateUtil.getSession();
			 Query query = session.createQuery("from FeePayment f where f.challenPrintedDate>= '" +fromDate+
			 		"' and f.challenPrintedDate<='" +toDate+
			 		"' and f.isCancelChallan=0  and f.feePaymentMode.id="+CMSConstants.smartCardPaymentMode);
			
			if(query.list() != null && !query.list().isEmpty()){
				feePayment = query.list();
			}
			return feePayment;
		 } catch (Exception e) {
			 throw e;
		 } finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
	}
	@Override
	public byte[] getLogoById(int accId) throws Exception {
		byte[] result= null;
		Session session = null;
		try {
			 SessionFactory sessionFactory = InitSessionFactory.getInstance();
			 session =sessionFactory.openSession();
			 String query="select bo.logo from FeeAccount bo where bo.id=:id and bo.isActive=1";
			 Query qr = session.createQuery(query);
			 qr.setInteger("id",accId);
			 
			 result=(byte[])qr.uniqueResult();
			 
			
		 }catch (Exception e) {
			 throw e; 
		 }finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		return result;
	}
}
