package com.kp.cms.transactionsimpl.admin;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.Currency;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.transactions.admin.ICurrencyMasterTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class CurrencyMasterTransactionImpl implements ICurrencyMasterTransaction {

	private static final Logger log = Logger.getLogger(CurrencyMasterTransactionImpl.class);
	public static volatile CurrencyMasterTransactionImpl currencyMasterTransactionImpl = null;

	/**
	 * This method is used to create single instance of CurrencyMasterTransactionImpl.
	 * @return instance of CurrencyMasterTransactionImpl.
	 */

	public static CurrencyMasterTransactionImpl getInstance() {
		if (currencyMasterTransactionImpl == null) {
			currencyMasterTransactionImpl = new CurrencyMasterTransactionImpl();
			return currencyMasterTransactionImpl;
		}
		return currencyMasterTransactionImpl;
	}

	/**
	 * This method is used to get all currency data form database.
	 */

	@Override
	public List<Currency> getCurrencyMasters() throws Exception {
		Session session = null;
		List<Currency> currencyList;

		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
//			SessionFactory sessionFactory = InitSessionFactory.getInstance();
			currencyList = session.createQuery(
					"from Currency c where c.isActive = 1").list();

		} catch (Exception e) {
			log.error("Unable to get getCurrencyMasters" ,e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		return currencyList;
	}

	/**
	 * This method is used to check duplicate entry of currency name.
	 */

	@Override
	public Currency isCurrencyMasterExist(String currencyName, int id) throws Exception {
		Session session = null;
		Currency currency;
		try {
			/*SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();
			String sqlString = "from Currency a where name = :curName";
			if(id !=0){
				sqlString = sqlString + " and id != :curId";
			}
			Query query = session.createQuery(sqlString);
			query.setString("curName", currencyName);
			if(id !=0){
				query.setInteger("curId", id);
			}
			
			currency = (Currency) query.uniqueResult();
			session.flush();
			//session.close();
			//sessionFactory.close();
			return currency;
		} catch (Exception e) {
			log.error("Error during duplcation checking..." , e);
			session.flush();
			//session.close();
			throw new ApplicationException(e);
		}
	}
	
	/**
	 * This method is used to check duplicate entry of currency short name.
	 */
	
	@Override
	public Currency isCurrencyShortNameExist(String currencyShortName, int id)throws Exception{
		Session session = null;
		Currency currency;
		try {
			/*SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();
			String sqlString = "from Currency a where a.currencyCode = :curShortName";
			if(id !=0){
				sqlString = sqlString + " and id != :curId";
			}
			Query query = session.createQuery(sqlString);
			query.setString("curShortName", currencyShortName);
			if(id !=0){
				query.setInteger("curId", id);
			}
			
			currency = (Currency) query.uniqueResult();
			session.flush();
			//session.close();
			//sessionFactory.close();
			return currency;
		} catch (Exception e) {
			log.error("Error during duplcation checking..." , e);
			session.flush();
			//session.close();
			throw new ApplicationException(e);
		}
	}

	/**
	 * This method is used for adding a record of currency to database. 
	 */

	@Override
	public boolean addCurrencyMaster(Currency currency) throws Exception {
		Session session = null;
		Transaction transaction = null;
		boolean isAdded = false;
		try {
			/*SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			session.save(currency);
			transaction.commit();
			isAdded = true;
		} catch (Exception e) {
			isAdded = false;
			log.error("Unable to addCurrencyMaster" , e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		return isAdded;
	}

	/**
	 * This method is used for deleting a record based on id.
	 */

	@Override
	public boolean deleteCurrencyMaster(int id, String userId) throws Exception {
		Session session = null;
		Transaction transaction = null;
		boolean isDeleted = false;
		try {
			/*SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			Currency currency = (Currency) session.get(Currency.class, id);
			currency.setLastModifiedDate(new Date());
			currency.setModifiedBy(userId);
			currency.setIsActive(Boolean.FALSE);
			session.update(currency);
			transaction.commit();
			isDeleted = true;
		} catch (Exception e) {
			isDeleted = false;
			log.error("Unable to delete FeeHeadings" ,e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		return isDeleted;
	}

	/**
	 * This method is used for getting one record based on id.
	 */

	@Override
	public Currency editCurrencyMaster(int id) throws Exception {
		Session session = null;
		Currency currency = null;
		try {
			/*SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from Currency c where c.id = :Id");
			query.setInteger("Id", id);
			currency = (Currency) query.uniqueResult();
		} catch (Exception e) {
			log.error("Unable to editCurrencyMaster", e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		return currency;
	}

	/**
	 * This method is used for updated currency name.
	 */

	@Override
	public boolean updateCurrencyMaster(Currency currency) throws Exception {
		Session session = null;
		Transaction transaction = null;
		boolean isUpdated = false;
		try {
			/*SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			session.update(currency);
			transaction.commit();
			isUpdated = true;
		} catch (Exception e) {
			isUpdated = false;
			log.error("Unable to updateCurrencyMaster", e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		return isUpdated;
	}

	/**
	 * This method is for reactivation of currency name.
	 */

	@Override
	public boolean reActivateCurrencyMaster(String currencyName, String userId)
			throws Exception {
		Session session = null;
		Transaction transaction = null;
		boolean isActivated = false;
		try {
			/*SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from Currency c where c.name = :name");
			query.setString("name", currencyName);
			Currency currency = (Currency) query.uniqueResult();
			transaction = session.beginTransaction();
			currency.setIsActive(true);
			currency.setModifiedBy(userId);
			currency.setLastModifiedDate(new Date());
			session.update(currency);
			transaction.commit();
			isActivated = true;
		} catch (Exception e) {
			isActivated = false;
			log.error("Unable to reActivateCurrencyMaster" , e);
			if (transaction != null) {
				transaction.rollback();
			}
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		return isActivated;
	}	
}