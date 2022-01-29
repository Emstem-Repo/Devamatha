package com.kp.cms.transactionsimpl.employee;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;

import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.transactions.employee.IEmployeeSearchTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class EmployeeSearchTxnImpl implements IEmployeeSearchTransaction {
	
	private static final Log log = LogFactory.getLog(EmployeeSearchTxnImpl.class);

	@Override
	public List<Object[]> getEmployeeDetails(String search) throws Exception {
		log.info("entered getEmployeeDetails in  EmployeeSearchTxnImpl class..");
		Session session = null;
		List<Object[]> studentSearchResult = null;
		try {
			//session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			
			studentSearchResult = session.createQuery(search).list();			
		} catch (Exception e) {
			log.error("error occurred in getEmployeeDetails of EmployeeSearchTxnImpl class.. ",e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		log.info("exit getEmployeeDetails in  EmployeeSearchTxnImpl class..");
		return studentSearchResult;
	}

}
