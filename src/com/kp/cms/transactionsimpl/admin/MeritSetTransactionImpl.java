package com.kp.cms.transactionsimpl.admin;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.MeritSet;
import com.kp.cms.transactions.admin.IMeritSetTransactions;
import com.kp.cms.utilities.HibernateUtil;

/**
 * An implementation class for ManageMeritSetTransactions.
 * 
 */
public class MeritSetTransactionImpl implements
		IMeritSetTransactions {

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admin.IManageMeritSetTransactions#addMeritSet(java.lang.String)
	 */
	@Override	
	public boolean addMeritSet(String meritSetName) {
		boolean isMeritSetAdded = false;
		Session session =null;
		Transaction transaction = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			
			MeritSet meritSet = new MeritSet();
			meritSet.setName(meritSetName);
			meritSet.setIsActive(true);
			session.save(meritSet);
			
			transaction.commit();

				session.flush();
				 session.close();


			isMeritSetAdded = true;

		} catch (Exception e) {
			if ( transaction != null ){
				transaction.rollback();
			}
			if (session != null){
				session.flush();
				 session.close();
			}
			isMeritSetAdded = false;
		}

		return isMeritSetAdded;
	}

	
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admin.IMeritSetTransactions#deleteMeritSet(int)
	 */
	@Override
	public boolean deleteMeritSet(int meritSetId) {
		boolean isMeritSetdeleted = false;
		Session session = null;
		Transaction transaction = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			
			transaction = session.beginTransaction();
			transaction.begin();
			MeritSet meritSet = (MeritSet) session.get(
					MeritSet.class, meritSetId);			
			meritSet.setIsActive(false);
			session.update(meritSet);
			
			transaction.commit();
			session.flush();
			 session.close();

			isMeritSetdeleted = true;
		} catch (Exception e) {
			if ( transaction != null ){
				transaction.rollback();
			}
			if (session != null){
				session.flush();
				 session.close();
			}
			isMeritSetdeleted = false;
		}
		return isMeritSetdeleted;
	}


	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admin.IManageMeritSetTransactions#getAllMeritSets()
	 */
	@Override
	public List<MeritSet> getAllMeritSets() {
		Session session = null;
		List<MeritSet> meritSetBoList = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			meritSetBoList = session.createQuery("from MeritSet m where isActive =1").list();
			session.flush();
			 //session.close();
		} catch (Exception e) {
			if (session != null){
				session.flush();
				 //session.close();
			}
		}
		return meritSetBoList;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admin.IManageMeritSetTransactions#editMeritSet(int, java.lang.String)
	 */
	@Override
	public boolean editMeritSet(int meritSetId, String meritSetName) {
		boolean isMeritSetAdded = false;
		Session session =null;
		Transaction transaction = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			MeritSet meritSet = (MeritSet) session.get(
					MeritSet.class, meritSetId);
			meritSet.setName(meritSetName);
			session.update(meritSet);
			
			transaction.commit();
			session.flush();
			 session.close();

			isMeritSetAdded = true;

		} catch (Exception e) {
			if ( transaction != null ){
				transaction.rollback();
			}
			if (session != null){
				session.flush();
				 session.close();
			}
			isMeritSetAdded = false;
		}

		return isMeritSetAdded;
	}
}
