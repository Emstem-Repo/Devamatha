package com.kp.cms.transactionsimpl.pettycash;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;

import com.kp.cms.bo.admin.PcBankAccNumber;
import com.kp.cms.bo.admin.PcReceipts;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.transactions.pettycash.ICollectionsTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class CollectionsTransactionImpl implements ICollectionsTransaction{

	private static final Log log = LogFactory.getLog(CollectionsTransactionImpl.class);
	@Override
	public List<PcBankAccNumber> getAccountNos() throws Exception {
		
	
		log.info("Entering into CollectionsTransactionImpl--- getAccountNos");
		Session session = null;
		List<PcBankAccNumber> accontList;
		try {
			//session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
				
		String 	query="from PcBankAccNumber pba where pba.isActive = 1"; 
			accontList = session.createQuery(query).list();
		} 
		catch (Exception e) {		
			log.error("Exception occured in getting all AccountNos Details in IMPL :",e);
			throw  new ApplicationException(e);
		   } finally {
		   if (session != null) {
			session.flush();
			//session.close();
	     	}
		}
		log.info("Leaving into  CollectionsTransactionImpl getAccountNos --- ");
		
		return accontList;
		

	}
	@Override
	public List<PcReceipts> getCollectionsDetails(String dynamicQuery)throws Exception {
			log.info("Entering into CollectionsTransactionImpl--- getCollectionsDetails");
			Session session = null;
			List<PcReceipts> collectionsList;
			try {
				//session = InitSessionFactory.getInstance().openSession();
				session = HibernateUtil.getSession();
			collectionsList = session.createQuery(dynamicQuery).list();
			} 
			catch (Exception e) {		
				log.error("Exception occured in getting all getCollectionsDetails in IMPL :",e);
				throw  new ApplicationException(e);	
			} finally {
			   if (session != null) {
				session.flush();
				//session.close();
		     	}
			}
			log.info("Leaving into  CollectionsTransactionImpl getCollectionsDetails --- ");
			
			return collectionsList;
	}



		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}



