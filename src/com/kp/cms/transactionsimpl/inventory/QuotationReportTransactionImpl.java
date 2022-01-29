package com.kp.cms.transactionsimpl.inventory;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;

import com.kp.cms.bo.admin.InvCounter;
import com.kp.cms.bo.admin.InvQuotation;
import com.kp.cms.bo.admin.InvQuotationItem;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.transactions.inventory.IQuotationReportTransaction;
import com.kp.cms.utilities.HibernateUtil;
public class QuotationReportTransactionImpl implements IQuotationReportTransaction {

	private static Log log = LogFactory.getLog(QuotationReportTransactionImpl.class);
	
	public static volatile QuotationReportTransactionImpl quotationReportTransactionImpl = null;
	
	public static QuotationReportTransactionImpl getInstance() {
		if (quotationReportTransactionImpl == null) {
			quotationReportTransactionImpl = new QuotationReportTransactionImpl();
			return quotationReportTransactionImpl;
		}
		return quotationReportTransactionImpl;
	}		
	/**
	 * getting quotation details
	 */
	public InvQuotation getQuotationDetails(String quotationNo) throws Exception {
		log.debug("inside getQuotationDetails");
		Session session = null;
		
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			
			StringBuffer sqlString = new StringBuffer();
			sqlString.append("from InvQuotation i where i.isActive = 1 " +
					"and i.quoteNo = '" + quotationNo + "'");
			Query query = session.createQuery(sqlString.toString());			
			InvQuotation invQuotation = (InvQuotation) query.uniqueResult();
			session.flush();
			//session.close();
			//sessionFactory.close();
			log.debug("leaving getQuotationDetails");
			return invQuotation;
		 } catch (Exception e) {
			 log.error("Error in getQuotationDetails...",e);
			 session.flush();
			 session.close();
			 throw  new ApplicationException(e);
		 }
	}
	/**
	 * getting quotation details based on selected id
	 */
	public List<InvQuotationItem> getQuotationItemsId(int quotId) throws Exception {
		log.debug("inside getQuotationItemsId");
		Session session = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			StringBuffer sqlString = new StringBuffer();
			sqlString.append("from InvQuotationItem i where i.isActive = 1 " +
					"and invQuotation.id =" + quotId);
			Query query = session.createQuery(sqlString.toString());			
			List<InvQuotationItem> list = query.list();
			session.flush();
			//session.close();
			//sessionFactory.close();
			log.debug("leaving getQuotationItemsId");
			return list;
		 } catch (Exception e) {
			 log.error("Error in getQuotationItemsId...",e);
			 session.flush();
			 session.close();
			 throw  new ApplicationException(e);
		 }
	}
	@Override
	public String getPrefixByType(String type) throws Exception {
		log.debug("inside getQuotationItemsId");
		Session session = null;
		String prefix="";
		try {
			session = HibernateUtil.getSession();
			 Query query = session.createQuery("select a from InvCounter a where a.type= :counter and a.isActive=1");
			 query.setString("counter",type);			
			InvCounter invCounter=(InvCounter)query.uniqueResult();
			if(invCounter!=null){
				prefix=invCounter.getPrefix();
			}
			session.flush();
			log.debug("leaving getQuotationItemsId");
			return prefix;
		 } catch (Exception e) {
			 log.error("Error in getQuotationItemsId...",e);
			 session.flush();
			 session.close();
			 throw  new ApplicationException(e);
		 }
	}

	
	
}
