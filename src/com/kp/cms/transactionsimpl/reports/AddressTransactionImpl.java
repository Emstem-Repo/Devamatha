package com.kp.cms.transactionsimpl.reports;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;

import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.transactions.reports.IAddressTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class AddressTransactionImpl implements IAddressTransaction {
	private static final Log log = LogFactory.getLog(AddressTransactionImpl.class);
	
	
	@SuppressWarnings("unchecked")
	public List<Object[]> getAddressDetails(String query) throws Exception{		

		log.info("Entering into AddressTransactionImpl--- getAddressDetails");
		Session session = null;
		List<Object[]> addressList;
		try {
			session = HibernateUtil.getSession();
			addressList = session.createQuery(query).list();
		} catch (Exception e) {		
			log.error("Exception occured in getting all address Details in IMPL :",e);
			throw  new ApplicationException(e);
		} finally {
		if (session != null) {
			session.flush();
			//session.close();
		}
		}
		log.info("Leaving into addresTransactionImpl --- addressDetails");
		return addressList;}

}
