package com.kp.cms.transactionsimpl.fee;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;

import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.transactions.fee.ISlipRegisterTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class SlipRegisterTransactionImpl implements ISlipRegisterTransaction{
	private static final Log log = LogFactory.getLog(SlipRegisterTransactionImpl.class);
	@Override
	public List<Object[]> getSlipRegisterRecords(String query) throws Exception{		

		log.info("Entering into SlipRegisterTransactionImpl--- getSlipRegisterRecords");
		Session session = null;
		List<Object[]> slipRegisterList=new ArrayList<Object[]>();
		try {
			//session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			slipRegisterList = session.createQuery(query).list();
		} catch (Exception e) {		
			log.error("SlipRegisterTransactionImpl--- getSlipRegisterRecords :",e);
			throw  new ApplicationException(e);
		} finally {
		if (session != null) {
			session.flush();
			//session.close();
		}
		}
		log.info("Leaving into addresTransactionImpl --- addressDetails");
		return slipRegisterList;}

}
