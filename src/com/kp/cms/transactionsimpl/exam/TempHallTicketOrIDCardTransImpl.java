package com.kp.cms.transactionsimpl.exam;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import com.kp.cms.transactions.exam.ITempHallTicketOrIDCardTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class TempHallTicketOrIDCardTransImpl implements ITempHallTicketOrIDCardTransaction{
	private static final Log log = LogFactory.getLog(TempHallTicketOrIDCardTransImpl.class);
	public static volatile TempHallTicketOrIDCardTransImpl tempHallTicketOrIDCardTransImpl=null;
	/**
	 * @return
	 * This method gives instance of this method
	 */
	public static TempHallTicketOrIDCardTransImpl getInstance(){
		if(tempHallTicketOrIDCardTransImpl==null){
			tempHallTicketOrIDCardTransImpl= new TempHallTicketOrIDCardTransImpl();}
		return tempHallTicketOrIDCardTransImpl;
	}
	@Override
	public Object[] printHallTicket(String regNo) throws Exception {

		Session session=null;
		Object[] objects=null;
		try{
			session=HibernateUtil.getSession();
			Query query=session.createQuery("select s,doc.document from Student s join s.admAppln adm left join adm.applnDocs doc with doc.isPhoto=1 where s.isActive=1 and s.registerNo=:regNo");
			query.setParameter("regNo",regNo);
			objects=(Object[])query.uniqueResult();
			
		}catch(Exception e){
			log.error("Error during getting BiometricDetails by id..." , e);
			session.flush();
			session.close();
		}
		return objects;
	}
}
