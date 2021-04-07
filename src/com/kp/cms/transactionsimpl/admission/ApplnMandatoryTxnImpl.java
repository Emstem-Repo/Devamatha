package com.kp.cms.transactionsimpl.admission;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.ApplnMandatoryBO;
import com.kp.cms.transactions.admission.IApplnFormMandatoryTransactions;
import com.kp.cms.utilities.HibernateUtil;

public class ApplnMandatoryTxnImpl implements IApplnFormMandatoryTransactions{
	private static volatile ApplnMandatoryTxnImpl applnFormMandatoryTxnImpl = null;
	private static final Log log = LogFactory.getLog(ApplnMandatoryTxnImpl.class);
	private ApplnMandatoryTxnImpl() {
		
	}
	/**
	 * return singleton object of DocumentExamEntryHandler.
	 * @return
	 */
	public static ApplnMandatoryTxnImpl getInstance() {
		if (applnFormMandatoryTxnImpl == null) {
			applnFormMandatoryTxnImpl = new ApplnMandatoryTxnImpl();
		}
		return applnFormMandatoryTxnImpl;
	}
	
	public List<ApplnMandatoryBO> getList() throws Exception{
		List<ApplnMandatoryBO> bo = null;
		Session session = null;
		try{
			session = HibernateUtil.getSession();
			bo = session.createQuery("from ApplnMandatoryBO").list();
			session.flush();
		}catch (Exception e) {
			if (session != null){
				session.flush();
				//session.close();
			}
		}
		return bo;
	}
	
	public boolean updateMandatoryField(List<ApplnMandatoryBO> applnMandatoryBO) throws Exception{
		Session session = null;
		Transaction tx = null;
		try{
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			Iterator<ApplnMandatoryBO> itr = applnMandatoryBO.iterator();
			while (itr.hasNext()) {
				ApplnMandatoryBO applnMandbo = (ApplnMandatoryBO) itr.next();
				session.update(applnMandbo);
			}
//			session.merge(applnMandatoryBO);
			tx.commit();
			session.flush();
		}catch (Exception e) {
			if(tx!=null){
				tx.rollback();
			}
			log.error("Exception occured while reactivateDocExamType in DocumentExamEntryTransactionImpl :"+e);
			e.printStackTrace();
		}
		return true;
	}
}
