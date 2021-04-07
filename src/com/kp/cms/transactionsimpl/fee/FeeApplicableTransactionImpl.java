package com.kp.cms.transactionsimpl.fee;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;

import com.kp.cms.bo.admin.Country;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.transactions.fee.IFeeApplicableTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class FeeApplicableTransactionImpl implements IFeeApplicableTransaction{
		
	private static final Log log = LogFactory.getLog(FeeApplicableTransactionImpl.class);
		private static FeeApplicableTransactionImpl feeApplicableTransactionImpl = null;
			
		public static FeeApplicableTransactionImpl getInstance() {
			   if(feeApplicableTransactionImpl == null ){
				   feeApplicableTransactionImpl = new FeeApplicableTransactionImpl();
				   return feeApplicableTransactionImpl;
			   }
			   return feeApplicableTransactionImpl;
		}
		
		/**
		 * 
		 * @return List containing all the FeeApplicable.
		 * @throws Exception
		 */
		@Override
		public List getAllFeeApplicable() throws Exception{
			log.info("call of getAllFeeApplicable in FeeApplicableTransactionImpl class");
			Session session = null;
			try {
				 //SessionFactory sessionFactory = InitSessionFactory.getInstance();
				 session = HibernateUtil.getSession();
				 
				 Query query = session.createQuery("from ApplicableFees");
				 List<Country> list = query.list();
				 //session.close();
				 //sessionFactory.close();
				 log.info("end of getAllFeeApplicable in FeeApplicableTransactionImpl class");
				 return list;
			 } catch (Exception e) {
				 log.info("error occured in getAllFeeApplicable in FeeApplicableTransactionImpl class", e);
				 throw new ApplicationException(e);
			 }
		}
}
