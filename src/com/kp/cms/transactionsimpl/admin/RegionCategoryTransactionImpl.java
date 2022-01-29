package com.kp.cms.transactionsimpl.admin;

/**
 * 
 * @author Date Created : 19 Jan 2009 
 */

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.Region;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.transactions.admin.IRegionCategoryTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class RegionCategoryTransactionImpl implements
		IRegionCategoryTransaction {
	public static volatile RegionCategoryTransactionImpl regionCategoryTransaction = null;
	private static Log log = LogFactory
			.getLog(RegionCategoryTransactionImpl.class);

	public static RegionCategoryTransactionImpl getInstance() {
		if (regionCategoryTransaction == null) {
			regionCategoryTransaction = new RegionCategoryTransactionImpl();
			return regionCategoryTransaction;
		}
		return regionCategoryTransaction;
	}

	/**
	 * This will retrieve all the Region Category Records from database.
	 * 
	 * @return all religion
	 * @throws ApplicationException
	 * @throws Exception
	 */

	public List<Region> getRegionCategory() throws Exception {
		Session session = null;
		List result;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from Region r where isActive = 1 ");
			List<Region> list = query.list();
			session.flush();
			//session.close();
			//sessionFactory.close();
			result = list;
		} catch (Exception e) {
			log.error("Error during getting Region..." + e);
			session.flush();
			//session.close();
			throw new ApplicationException(e);
		}
		return result;
	}

	/**
	 * This method add/update new Region Category to Database.
	 * 
	 * @return true / flase based on result.
	 * @throws Exception
	 */

	public boolean addRegionCategory(Region region, String mode)
			throws Exception {
		Session session = null;
		Transaction tx = null;
		boolean result = false;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			tx.begin();
			if (mode.equalsIgnoreCase("Add")) {
				session.save(region);
			} else if (mode.equalsIgnoreCase("Edit")) {
				session.update(region);
			}
			tx.commit();
			session.flush();
			session.close();
			result = true;
		} catch (ConstraintViolationException e) {
			tx.rollback();
			log.error("Error during saving region data..." + e);
			throw new BusinessException(e);
		} catch (Exception e) {
			tx.rollback();
			log.error("Error during saving region data..." + e);
			throw new ApplicationException(e);
		}
		return result;
	}

	/**
	 * This will delete a Region from database.
	 * 
	 * @return
	 * @throws Exception
	 */

	public boolean deleteRegion(int regionId, Boolean activate)
			throws Exception {
		Session session = null;
		Transaction tx = null;
		boolean result = false;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			tx.begin();
			Region region = (Region) session.get(
					Region.class, regionId);
			region.setIsActive(activate==true?true:false);
			session.update(region);
			tx.commit();
			session.flush();
			session.close();
			result = true;
		} catch (ConstraintViolationException e) {
			tx.rollback();
			log.error("Error during deleting Region data..." + e);
			throw new BusinessException(e);
		} catch (Exception e) {
			tx.rollback();
			log.error("Error during deleting Region data..." + e);
			throw new ApplicationException(e);
		}
		return result;
	}

	public Region isRegionDuplcated(Region oldRegion) throws Exception {
		Session session = null;
		Region region;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from Region a where name = :regName");
			query.setString("regName", oldRegion.getName());
			region = (Region) query.uniqueResult();
			session.flush();
			//session.close();
			//sessionFactory.close();
		} catch (Exception e) {
			log.error("Error during duplcation checking..." + e);
			session.flush();
			//session.close();
			throw new ApplicationException(e);
		}
		return region;
	}

}
