package com.kp.cms.transactionsimpl.reports;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;

import com.kp.cms.bo.admin.Student;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.transactions.reports.ISudentsListTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class StudentsListReportTransactionImpl implements ISudentsListTransaction{

private static final Log log = LogFactory.getLog(StudentsListReportTransactionImpl.class);
	
	public static volatile StudentsListReportTransactionImpl self=null;
	
	/**
	 * @return unique instance of StudentsListReportTransactionImpl class.
	 * This method gives instance of this method
	 */
	public static StudentsListReportTransactionImpl getInstance(){
		if(self==null)
			self= new StudentsListReportTransactionImpl();
		return self;
	}
	
	@Override
	public List<Student> getStudentsListReportDetails(String searchCriteria) throws Exception {
		Session session = null;
		List<Student> studentList;
		try {
			/*SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();
			Query query = session.createQuery(searchCriteria);
			studentList =  query.list();
		} catch (Exception e) {
			log.error("error while getting the getStudentsListReportDetails  ",e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		log.info("exit getStudentsListReportDetails..");
		
		return studentList;
	}
}