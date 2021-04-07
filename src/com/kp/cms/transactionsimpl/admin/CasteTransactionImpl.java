package com.kp.cms.transactionsimpl.admin;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.Caste;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.transactions.admin.ICasteTransaction;
import com.kp.cms.utilities.HibernateUtil;

/**
 * An implementation class for ICasteTransaction.
 * @author
 */
public class CasteTransactionImpl implements ICasteTransaction {
	private static final Log log = LogFactory.getLog(CasteTransactionImpl.class);
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admin.ICasteTransaction#getCastes()
	 */
	@Override
	public List<Caste> getCastes(){
		Session session = null;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			List<Caste> casteList = session.createQuery("from Caste c where c.isActive=1").list();
			session.flush();
			//session.close();
			return casteList;
		} catch (Exception e) {
			if (session != null){
				session.flush();
				//session.close();
			}
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admin.ICasteTransaction#addCaste(java.lang.String)
	 */
	@Override
	public boolean addCaste(Caste caste) throws Exception{
		Session session = null;
		Transaction transaction = null;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			session.save(caste);
			transaction.commit();
			session.flush();
			//session.close();
			return true;
		} catch (Exception e) {
			if ( transaction != null){
				transaction.rollback();
			}
			if (session != null){
				session.flush();
				//session.close();
			}
			return false;
		}
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admin.ICasteTransaction#deleteCaste(int)
	 */
	@Override
	public boolean deleteCaste(int casteId,String userId) throws Exception {
		Session session = null;
		Transaction transaction = null;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			Caste caste = (Caste) session.get(Caste.class, casteId);
			caste.setIsActive(false);
			caste.setLastModifiedDate(new Date());
			caste.setModifiedBy(userId);
			session.update(caste);
			transaction.commit();
			session.flush();
			session.close();
			return true;
		} catch (Exception e) {
			if ( transaction != null){
				transaction.rollback();
			}
			if (session != null){
				session.flush();
				session.close();
			}
			return false;
		}
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admin.ICasteTransaction#editCaste(int, java.lang.String)
	 */
	@Override
	public boolean updateCaste(Caste caste) throws Exception{
		Session session = null;
		Transaction transaction = null;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			session.merge(caste);
			transaction.commit();
			session.flush();
			session.close();
			return true;
		} catch (Exception e) {
			if ( transaction != null){
				transaction.rollback();
			}
			if (session != null){
				session.flush();
				session.close();
			}
			return false;
		}
	}
	/**
	 * duplication checking for Caste
	 */
	public Caste isCastDuplcated(Caste oldcaste) throws Exception {
		log.debug("impl: inside isCastDuplcated");
		Session session = null;
		Caste caste ;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			
			Query query = session
					.createQuery("from Caste c where name = :name and religion.id = :rid");
			query.setString("name", oldcaste.getName());
			query.setInteger("rid",oldcaste.getReligion().getId());
			caste = (Caste) query.uniqueResult();
			session.flush();
			//session.close();
			//sessionFactory.close();
			log.debug("impl: leaving isCastDuplcated");
		} catch (Exception e) {
			log.error("Error during duplcation checking..." + e);
			session.flush();
			//session.close();
			throw new ApplicationException(e);
		}
		return caste;
	}
	/* reActivate the deleted caste
	 * (non-Javadoc)
	 * @see com.kp.cms.transactions.admin.ICasteTransaction#reActivateCaste(com.kp.cms.bo.admin.Caste, java.lang.String)
	 */
	public boolean reActivateCaste(Caste caste,String userId) throws Exception{
		Session session = null;
		Transaction transaction = null;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			caste.setModifiedBy(userId);
			caste.setLastModifiedDate(new Date());
			caste.setIsActive(true);
			session.update(caste);
			transaction.commit();
			session.flush();
			session.close();
			return true;
		} catch (Exception e) {
			if ( transaction != null){
				transaction.rollback();
			}
			if (session != null){
				session.flush();
				session.close();
			}
			return false;
		}
	}
}