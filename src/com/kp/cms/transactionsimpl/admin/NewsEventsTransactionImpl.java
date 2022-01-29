package com.kp.cms.transactionsimpl.admin;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.NewsEvents;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.transactions.admin.INewsEventsTransaction;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;

public class NewsEventsTransactionImpl implements INewsEventsTransaction {

	private static final Logger log = Logger.getLogger(NewsEventsTransactionImpl.class);
	public static volatile NewsEventsTransactionImpl newsEventsTransactionImpl = null;

	/**
	 * This method is used to create single instance of
	 * NewsEventsTransactionImpl.
	 * @return instance of NewsEventsTransactionImpl.
	 */

	public static NewsEventsTransactionImpl getInstance() {
		if (newsEventsTransactionImpl == null) {
			newsEventsTransactionImpl = new NewsEventsTransactionImpl();
			return newsEventsTransactionImpl;
		}
		return newsEventsTransactionImpl;
	}
	
	@Override
	public List<NewsEvents> getNewsEventsDetails() throws Exception {
		// TODO Auto-generated method stub
		List<NewsEvents> list;
		Session session = null;
		try {
			/*SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();
			list = session.createQuery("from NewsEvents n ").list();

		} catch (Exception e) {
			log.error("Unable to getNewsEventsDetails" , e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		return list;
	}
	
	/**
	 * This method is used to get all news & events from database.
	 * priyatham added
	 */
	
	@Override
	public List<NewsEvents> getNewsEventsDetails(String required) throws Exception {
		List<NewsEvents> list;
		Session session = null;
		try {
			/*SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();
			list = session.createQuery("from NewsEvents n where n.required='"+required +"' or n.required='ALL'").list();

		} catch (Exception e) {
			log.error("Unable to getNewsEventsDetails" , e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		return list;
	}
	
	/**
	 *	This method is used to save news & events to database. 
	 */
	
	@Override
	public boolean saveNewsEvents(NewsEvents newsEvents) throws Exception {
		Session session = null;
		Transaction transaction = null;
		boolean isAdded = false;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			session.save(newsEvents);
			transaction.commit();
			isAdded = true;
		} catch (Exception e) {
			isAdded = false;
			log.error("Unable to saveNewsEvents", e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		return isAdded;
	}
	
	/**
	 * This method is used to check duplicate news & events in database.
	 */
	
	@Override
	public boolean checkDuplicateNewsEvents(String message) throws Exception {
		Session session = null;
		boolean isExist = false;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			
			Query query = session.createQuery("select n.description from NewsEvents n");
			List<String> list=query.list();
			 if(list!=null && !list.isEmpty() && list.contains(message)){
				 isExist = true;
			 }
		} catch (Exception e) {
			isExist = false;
			log.error("Error while checkDuplicateNewsEvents details...",e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		return isExist;
	}
	
	/**
	 * This method is used to delete news & events from database.
	 */
	
	@Override
	public boolean deleteNewsEvents(int id) throws Exception {
		Session session = null;
		Transaction transaction = null;
		boolean isDeleted = false;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			NewsEvents newsEvents = (NewsEvents) session.get(NewsEvents.class, id);
			session.delete(newsEvents);
			transaction.commit();
			isDeleted = true;
		} catch (Exception e) {
			isDeleted = false;
			log.error("Unable to delete FeeHeadings" , e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		return isDeleted;
	}
	
	@Override
	public NewsEvents getDetailsOnId(int id)throws Exception
	{
		Session session = null;
		NewsEvents newsEvents = null;
		try {
			//session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			Query query=session.createQuery("from NewsEvents e where e.id= :row");
			query.setInteger("row", id);
			newsEvents = (NewsEvents)query.uniqueResult();
		} catch (Exception e) {			
		log.error("Exception occured while getting the row based on the Id in getDetailsOnId :"+e);
			throw  new ApplicationException(e);
		} finally {
		if (session != null) {
			session.flush();
			//session.close();
		}
		}
		return newsEvents;	
	}

	@Override
	public boolean updateNewsEvents(NewsEvents newsEvents)throws Exception
	{
		Session session = null;
		Transaction transaction = null;
		try {
			//session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			session.update(newsEvents);
			transaction.commit();
			return true;
		} catch (Exception e) {
			if(transaction != null){
				transaction.rollback();
			}
			log.error("Exception occured while updating NewsEvents in IMPL :"+e);
			throw  new ApplicationException(e);
		} finally {
		if (session != null) {
			session.flush();
			session.close();		
		}
		}
	}
	
}