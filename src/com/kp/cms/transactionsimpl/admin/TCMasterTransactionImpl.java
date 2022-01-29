package com.kp.cms.transactionsimpl.admin;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.TCNumber;
import com.kp.cms.bo.admin.TCPrefix;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.admin.TCMasterForm;
import com.kp.cms.transactions.admin.ITCMasterTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class TCMasterTransactionImpl implements ITCMasterTransaction {
	/**
	 * Singleton object of TCMasterTransactionImpl
	 */
	private static volatile TCMasterTransactionImpl tCMasterTransactionImpl = null;
	private static final Log log = LogFactory.getLog(TCMasterTransactionImpl.class);
	private TCMasterTransactionImpl() {
		
	}
	/**
	 * return singleton object of TCMasterTransactionImpl.
	 * @return
	 */
	public static TCMasterTransactionImpl getInstance() {
		if (tCMasterTransactionImpl == null) {
			tCMasterTransactionImpl = new TCMasterTransactionImpl();
		}
		return tCMasterTransactionImpl;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admin.ITCMasterTransaction#getAllTCNumber()
	 */
	@Override
	public List<TCNumber> getAllTCNumber() throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			List<TCNumber> tcList = session.createQuery("from TCNumber t where t.isActive=1").list();
			session.flush();
			return tcList;
		} catch (Exception e) {
			if (session != null){
				session.flush();
			}
			return null;
		}
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admin.ITCMasterTransaction#addTCMaster(com.kp.cms.bo.admin.TCNumber, java.lang.String)
	 */
	@Override
	public boolean addTCMaster(TCNumber bo,String mode) throws Exception {
		Session session = null;
		Transaction transaction = null;
		try {
			/*session = InitSessionFactory.getInstance().openSession();
			transaction = session.beginTransaction();
			transaction.begin();*/
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			if(mode.equals("add")){
				session.save(bo);
			}else{
				session.merge(bo);
			}
			transaction.commit();
			session.flush();
			return true;
		} 
		catch (RuntimeException e) {
			if ( transaction != null){
				transaction.rollback();
			}
			return false;
		}catch (Exception e) {
			if ( transaction != null){
				transaction.rollback();
			}
			return false;
		}
	}
	
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admin.ITCMasterTransaction#isTCNumberDuplcated(com.kp.cms.forms.admin.TCMasterForm)
	 */
	public TCNumber isTCNumberDuplcated(TCMasterForm tcMasterForm) throws Exception {
		Session session = null;
		TCNumber tcNumber;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			//StringBuffer sqlString = new StringBuffer("from TCNumber i where ((i.type = :type and i.year=:year) or (i.year=:year and i.prefix=:prefix)) and i.tcFor=:tcFor");
			StringBuffer sqlString = new StringBuffer("from TCNumber i where ((i.type = :type and i.year=:year)) and i.tcFor=:tcFor");
			Query query = session.createQuery(sqlString.toString());
			query.setString("type", tcMasterForm.getType());
			query.setInteger("year", Integer.parseInt(tcMasterForm.getYear()));
			//query.setString("prefix", tcMasterForm.getPrefix());
			query.setString("tcFor", tcMasterForm.getCollegeName());
			tcNumber = (TCNumber) query.uniqueResult();
			session.flush();
			//session.close();
			//sessionFactory.close();
		} 
		catch (RuntimeException e) {
			log.error("Error during duplcation checking..." , e);
			throw new ApplicationException(e);
		}catch (Exception e) {
			log.error("Error during duplcation checking..." , e);
			throw new ApplicationException(e);
		}
		return tcNumber;
	}
	
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admin.ITCMasterTransaction#deleteTCMaster(int, java.lang.Boolean, com.kp.cms.forms.admin.TCMasterForm)
	 */
	public boolean deleteTCMaster(int id, Boolean activate, TCMasterForm tcMasterForm) throws Exception {
		Session session = null;
		Transaction tx = null;
		boolean result = false;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			tx.begin();
			TCNumber tcNumber = (TCNumber) session.get(TCNumber.class, id);
			if (activate) {
				tcNumber.setIsActive(true);
			}else
			{
				tcNumber.setIsActive(false);
			}
			tcNumber.setModifiedBy(tcMasterForm.getUserId());
			tcNumber.setLastModifiedDate(new Date());
			session.update(tcNumber);
			tx.commit();
			session.flush();
			session.close();
			result = true;
		} catch (ConstraintViolationException e) {
			if(tx!=null)
			     tx.rollback();
			log.error("Error in deleteCounter..." , e);
			throw new BusinessException(e);
		} catch (Exception e) {
			if(tx!=null)
			     tx.rollback();
			log.error("Error in deleteCounter.." , e);
			throw new ApplicationException(e);
		}
		return result;
	}
	@Override
	public List<TCPrefix> getTcPrefix() throws Exception {
        Session session = null;
        try {
			session = HibernateUtil.getSession();
			String str = "from TCPrefix where isActive=1";
			List<TCPrefix> tcPrefix = session.createQuery(str).list();
			session.flush();
			return tcPrefix;
        }catch (Exception e) {
			if (session != null){
				session.flush();
			}	
			throw  new ApplicationException(e);
        }
  }
}
