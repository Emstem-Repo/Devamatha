package com.kp.cms.transactionsimpl.pettycash;

import java.util.Date;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.PcBankAccNumber;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.handlers.pettycash.PcAccountEntryHandler;
import com.kp.cms.transactions.pettycash.IpcAccountEntryTransaction;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.PropertyUtil;

public class PcAccountEntryTransactionImpl extends PropertyUtil implements
		IpcAccountEntryTransaction {
	private static final Log log = LogFactory.getLog(PcAccountEntryHandler.class);
	@Override
	public PcBankAccNumber checkDuplicate(String accNo) throws Exception {
		Session session = null;
		PcBankAccNumber pb1=null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			Query selectedQuery=session.createQuery("from PcBankAccNumber p where p.accountNo='"+accNo+"'");
			pb1 =(PcBankAccNumber)selectedQuery.uniqueResult();
			return pb1;
		} catch (Exception e) {
			log.error("Error while retrieving selected candidates.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
	}

	@Override
	public boolean deletePcBankAccNo(String bankAccid, String userId)
			throws Exception {
		Session session = null;
		PcBankAccNumber pb1=null;
		boolean isDelete=false;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			Transaction transaction=session.beginTransaction();
			Query selectedQuery=session.createQuery("from PcBankAccNumber p where p.id="+bankAccid);
			pb1 =(PcBankAccNumber)selectedQuery.uniqueResult();
			pb1.setIsActive(false);
			pb1.setLastModifiedDate(new Date());
			pb1.setModifiedBy(userId);
			session.update(pb1);
			transaction.commit();
			isDelete=true;
		} catch (Exception e) {
			isDelete=false;
			log.error("Error while retrieving selected candidates.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}

		return isDelete;
	}

	@Override
	public boolean reactivatePcBankAccNo(String bankAccid, String userId)
			throws Exception {
		Session session = null;
		PcBankAccNumber pb1=null;
		boolean isReactive=false;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			Transaction transaction=session.beginTransaction();
			Query selectedQuery=session.createQuery("from PcBankAccNumber p where p.id="+bankAccid);
			pb1 =(PcBankAccNumber)selectedQuery.uniqueResult();
			pb1.setIsActive(true);
			pb1.setLastModifiedDate(new Date());
			pb1.setModifiedBy(userId);
			session.update(pb1);
			transaction.commit();
			isReactive=true;
		} catch (Exception e) {
			isReactive=false;
			log.error("Error while retrieving selected candidates.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}

		return isReactive;
	}

	@Override
	public boolean savePcBankAccNo(PcBankAccNumber pb1) throws Exception {
		return save(pb1);
	}

	@Override
	public boolean updatePcBankAccNo(PcBankAccNumber pb1) throws Exception {
		return update(pb1);
	}

	@Override
	public PcBankAccNumber getPcBankAccDetailsWithId(String bankAccid)
			throws Exception {
		Session session = null;
		PcBankAccNumber pb1=null;
		boolean isReactive=false;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			Query selectedQuery=session.createQuery("from PcBankAccNumber p where p.id="+bankAccid);
			pb1 =(PcBankAccNumber)selectedQuery.uniqueResult();
		} catch (Exception e) {
			isReactive=false;
			log.error("Error while retrieving selected candidates.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		return pb1;
	}

}
