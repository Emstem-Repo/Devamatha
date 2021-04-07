package com.kp.cms.transactionsimpl.admin;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.FlushMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.AdmittedThrough;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.admin.AdmittedThroughForm;
import com.kp.cms.transactions.admin.IAdmittedThroughTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class AdmittedThroughTransactionImpl implements
		IAdmittedThroughTransaction {
	private static final Log log = LogFactory
			.getLog(AdmittedThroughTransactionImpl.class);
	public static volatile AdmittedThroughTransactionImpl admittedThroughTransactionImpl = null;
	public static AdmittedThroughTransactionImpl getInstance() {
		if (admittedThroughTransactionImpl == null) {
			admittedThroughTransactionImpl = new AdmittedThroughTransactionImpl();
			return admittedThroughTransactionImpl;
		}
		return admittedThroughTransactionImpl;
	}

	/**
	 * This will retrieve all the Admitted Through records from database.
	 * 
	 * @return all AdmittedThrough
	 */

	public List<AdmittedThrough> getAdmittedThrough() throws Exception {
		Session session = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from AdmittedThrough a where isActive=1");
			query.setFlushMode(FlushMode.COMMIT);
			List<AdmittedThrough> list = query.list();
			//session.flush();
			//session.close();
			//sessionFactory.close();
			return list;
		} catch (Exception e) {
			log.error("Error during getting Admitted Through..." ,e);
			//session.flush();
			//session.close();
			throw new ApplicationException(e);
		}
	}

	/**
	 * This method add new Admitted Through to Database.
	 * 
	 * @return true / flase based on result.
	 * @throws Exception
	 */

	public boolean addAdmittedThrough(AdmittedThrough admittedThrough, String mode) throws Exception {
		Session session = null;
		Transaction transaction = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			if (mode.equalsIgnoreCase("Add")) {
				session.save(admittedThrough);
			} else if (mode.equalsIgnoreCase("Edit")) {
				session.update(admittedThrough);
			}
			transaction.commit();
			session.flush();
			//session.close();
			return true;
		} catch (ConstraintViolationException e) {
			if(transaction!=null)
				transaction.rollback();
			log.error("Error during saving admitted Through data..." ,e);
			throw new BusinessException(e);
		} catch (Exception e) {
			if(transaction!=null)
			     transaction.rollback();
			log.error("Error during saving admitted Through data..." ,e);
			throw new ApplicationException(e);
		}
	}

	/**
	 * This will delete a Admitted Through from database.
	 * 
	 * @return true/false
	 * @throws Exception
	 */

	public boolean deleteAdmittedThrough(int admId, Boolean activate, AdmittedThroughForm admittedThroughForm)
			throws Exception {
		Session session = null;
		Transaction transaction = null;
		boolean result = false;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			AdmittedThrough admittedThrough = (AdmittedThrough) session.get(
					AdmittedThrough.class, admId);
			if (activate) {
				admittedThrough.setIsActive(true);
			}else
			{
				admittedThrough.setIsActive(false);
			}
			admittedThrough.setModifiedBy(admittedThroughForm.getUserId());
			admittedThrough.setLastModifiedDate(new Date());
			session.update(admittedThrough);
			transaction.commit();
			session.flush();
			session.close();
			result = true;
		} catch (ConstraintViolationException e) {
			if(transaction!=null)
			     transaction.rollback();
			log.error("Error during deleting Admitted Through data..." ,e);
			throw new BusinessException(e);
		} catch (Exception e) {
			if(transaction!=null)
				transaction.rollback();
			log.error("Error during deleting Admitted Through data..." ,e);
			throw new ApplicationException(e);
		}
		return result;
	}
	/**
	 * duplication for admitted through
	 */
	public AdmittedThrough isAdmittedThroughDuplcated(AdmittedThrough oldadmittedThrough) throws Exception {
		Session session = null;
		AdmittedThrough admittedThrough;
		//AdmittedThrough result = admittedThrough = new AdmittedThrough();
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from AdmittedThrough a where name = :admThrough");
			query.setString("admThrough", oldadmittedThrough.getName());
			admittedThrough =(AdmittedThrough)query.uniqueResult();
			session.flush();
			//session.close();
			//sessionFactory.close();
			//result = admittedThrough;
		} catch (Exception e) {
			log.error("Error during duplcation checking..." ,e);
			//session.flush();
			//session.close();
			throw new ApplicationException(e);
		}
		return admittedThrough;
	}

}