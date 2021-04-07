package com.kp.cms.transactionsimpl.admin;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.Occupation;
import com.kp.cms.transactions.admin.IOccupationTransaction;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;

/**
 * An implementation class for IOccupationTransaction.
 * 
 */
public class OccupationTransactionImpl implements IOccupationTransaction {

	/**
	 * Represents the log statement for the class.
	 */
	private static final Log log = LogFactory.getLog(OccupationTransactionImpl.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.kp.cms.transactions.admin.IManageOccupationTransaction#addOccupation
	 * (java.lang.String)
	 */
	@Override
	public boolean addOccupation(String occupationName) {
		boolean isOccupationAdded = false;
		Session session = null;
		Transaction transaction = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();

			Occupation occupation = new Occupation();
			occupation.setName(occupationName);
			occupation.setIsActive(true);
			session.save(occupation);

			transaction.commit();
			session.flush();
			//session.close();

			isOccupationAdded = true;

		} catch (Exception e) {
			log.error("Error while adding occupation object." , e);
			if ( transaction != null){
				transaction.rollback();
			}
			if (session != null){
				session.flush();
				//session.close();
			}
			isOccupationAdded = false;
		}

		return isOccupationAdded;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.kp.cms.transactions.admin.IManageOccupationTransaction#deleteOccupation
	 * (int)
	 */
	@Override
	public boolean deleteOccupation(int occupationId) {
		boolean isOccupationdeleted = false;
		Session session = null;
		Transaction transaction = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();

			Occupation occupation  = (Occupation) session.get(
					Occupation.class, occupationId);	
			occupation.setIsActive(false);

			session.update(occupation);
			transaction.commit();
			session.flush();
			session.close();

			isOccupationdeleted = true;
		} catch (Exception e) {
			log.error("Error while adding occupation object." , e);
			if ( transaction != null){
				transaction.rollback();
			}
			if (session != null){
				session.flush();
				//session.close();
			}
			isOccupationdeleted = false;
		}
		return isOccupationdeleted;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.kp..cms.transactions.admin.IManageOccupationTransaction#
	 * getAllOccupations()
	 */
	@Override
	public List<Occupation> getOccupationList() {
		Session session = null;
		List<Occupation> occupationBoList = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			occupationBoList = session.createQuery(
					"from Occupation o where o.isActive=1 order by o.name").list();
			session.flush();
			//session.close();
		} catch (Exception e) {
			if (session != null){
				session.flush();
				//session.close();
			}
		}
		return occupationBoList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.kp.cms.transactions.admin.IManageOccupationTransaction#editOccupation
	 * (int, java.lang.String)
	 */
	@Override
	public boolean editOccupation(int occupationId, String occupationName) {
		boolean isOccupationAdded = false;
		Session session = null;
		Transaction transaction = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();

			Occupation occupation  = (Occupation) session.get(
					Occupation.class, occupationId);	

			occupation.setName(occupationName);

			session.update(occupation);
			transaction.commit();
			session.flush();
			session.close();

			isOccupationAdded = true;

		} catch (Exception e) {
			log.error("Error while adding occupation object." , e);
			if ( transaction != null){
				transaction.rollback();
			}
			if (session != null){
				session.flush();
				//session.close();
			}
			isOccupationAdded = false;
		}
		return isOccupationAdded;
	}
}