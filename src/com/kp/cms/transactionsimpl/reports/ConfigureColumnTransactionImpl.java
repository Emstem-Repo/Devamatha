package com.kp.cms.transactionsimpl.reports;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.ConfigReportsColumn;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.transactions.reports.IConfigureColumnTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class ConfigureColumnTransactionImpl implements IConfigureColumnTransaction{

	
	private static final Log log = LogFactory.getLog(ConfigureColumnTransactionImpl.class);
	
	public static volatile ConfigureColumnTransactionImpl self=null;
	
	/**
	 * @return unique instance of ConfigureColumnTransactionImpl class.
	 * This method gives instance of this method
	 */
	public static ConfigureColumnTransactionImpl getInstance(){
		if(self==null)
			self= new ConfigureColumnTransactionImpl();
		return self;
	}

	@Override
	public boolean saveColumnForReport(ConfigReportsColumn configReportsColumn)
			throws Exception {
		log.info("call of saveColumnForReport in ConfigureColumnTransactionImpl class.");
		Session session = null;
		Transaction transaction = null;
		boolean isAdded = false;
		try {
			/*SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			session.save(configReportsColumn);
			transaction.commit();
			isAdded = true;
		} catch (Exception e) {
			isAdded = false;
			log.error("Unable to saveColumnForReport" , e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		log.info("end of saveColumnForReport in ConfigureColumnTransactionImpl class.");
		return isAdded;
	}

	@Override
	public int getMaxPosition(String reportName) throws Exception {
		log.info("call of getMaxPosition in ConfigureColumnTransactionImpl class.");
		Session session = null;
		Transaction transaction = null;
		try {
			/*SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			Query query = session.createQuery("select max(c.position) from ConfigReportsColumn c where c.reportName = :repNmae");
			query.setString("repName",reportName);
			if(query != null){
				
			}
			transaction.commit();
		} catch (Exception e) {
			log.error("Unable to getMaxPosition" , e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		log.info("end of getMaxPosition in ConfigureColumnTransactionImpl class.");
		return 0;
	}

/**
 * Used to get all report names
 */
	public List<String> getReportNames()throws Exception {
		log.info("Inside of getReportNames of ConfigureColumnTransactionImpl class");
		Session session = null;
		List<String> reportnameList = null;
		try {
			/*SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();
			reportnameList = session.createQuery("select distinct(config.reportName) from ConfigReportsColumn config").list();
		} catch (Exception e) {
			log.error("Error occured in getReportNames of ConfigureColumnTransactionImpl class",e);
			throw new ApplicationException();
		}
		finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		log.info("End of getReportNames of ConfigureColumnTransactionImpl class");
		return reportnameList;
	}

/**
 * Used to get details based on the report name
 */
public List<Object[]> getDetailsOnReportName(String reportName) throws Exception {
	log.info("Inside of getDetailsOnReportName of ConfigureColumnTransactionImpl class");
	Session session = null;
	List<Object[]> detailsList = null;
	try {
		/*SessionFactory sessionFactory = InitSessionFactory.getInstance();
		session = sessionFactory.openSession();*/
		session = HibernateUtil.getSession();
		Query query = session.createQuery("select config.columnName, config.showColumn, config.position, config.id, config.createdBy, config.createdDate  from ConfigReportsColumn config where config.reportName = :repName");
		query.setString("repName", reportName);
		detailsList = query.list();
	} catch (Exception e) {
		log.error("Error occured in getDetailsOnReportName ConfigureColumnTransactionImpl class",e);
		throw new ApplicationException();
	}
	finally {
		if (session != null) {
			session.flush();
			//session.close();
		}
	}
	log.info("End of getDetailsOnReportName of ConfigureColumnTransactionImpl class");
	return detailsList;
}

/**
 * Used in updating the details
 */
public boolean updateConfigReport(List<ConfigReportsColumn> newList) throws Exception {
	log.info("Inside of updateConfigReport of AssignPrivilege TransactionImpl");
	ConfigReportsColumn configReportsColumn;
	Transaction tx=null;
	Session session = null;
	try {
		/*SessionFactory sessionFactory = InitSessionFactory.getInstance();
		session = sessionFactory.openSession();*/
		session = HibernateUtil.getSession();
		Iterator<ConfigReportsColumn> itr = newList.iterator();
		int count = 0;
		tx = session.beginTransaction();
		tx.begin();
		while (itr.hasNext()) {
			configReportsColumn = itr.next();
			session.saveOrUpdate(configReportsColumn);
			if (++count % 20 == 0) {
				session.flush();
				session.clear();
			}
		}
		tx.commit();
		//session.close();
		//sessionFactory.close();
		log.info("End of updateConfigReport of ConfigureColumnTransactionImpl");
		return true;
	} catch (Exception e) {
		if(tx!=null){
			tx.rollback();
		}
		session.flush();
		session.clear();
		session.close();
		log.error("Error occured updateConfigReport of ConfigureColumnTransactionImpl",e);
		throw new ApplicationException(e);
	}
}
}