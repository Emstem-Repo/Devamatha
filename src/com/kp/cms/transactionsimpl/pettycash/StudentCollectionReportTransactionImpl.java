package com.kp.cms.transactionsimpl.pettycash;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;

import com.kp.cms.bo.admin.PcReceipts;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.to.pettycash.StudentCollectionReportTO;
import com.kp.cms.transactions.pettycash.IStudentCollectionReportTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class StudentCollectionReportTransactionImpl implements IStudentCollectionReportTransaction {

	
private static final Log log = LogFactory.getLog(StudentCollectionReportTransactionImpl.class);
	
	/**
	 * 
	 */
	public static volatile StudentCollectionReportTransactionImpl studentCollectionReportTransactionImpl=null;
	/**
	 * @return
	 * This method will return instance of this class
	 */
	public static StudentCollectionReportTransactionImpl getInstance(){
		if(studentCollectionReportTransactionImpl==null)
			studentCollectionReportTransactionImpl= new StudentCollectionReportTransactionImpl();
		return studentCollectionReportTransactionImpl;
	}
	@Override
	public List<PcReceipts> getStudentCollectionReport(String searchCriteria) throws ApplicationException {
		log.info("entered getStudentCollectionReport..");
		Session session = null;
		List studentCollectionSearchResult = null;
		try {
			/*SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();*/
    		session = HibernateUtil.getSession();
			studentCollectionSearchResult = session.createQuery(searchCriteria).list();
			
		} catch (Exception e) {
			log.error("error while getting the student collection details  "+e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		log.info("exit getStudentCollectionReport..");
		return studentCollectionSearchResult;
	
	}
	
	public List<StudentCollectionReportTO> getStudent(String searchCriteria) throws ApplicationException {
		log.info("entered getStudentCollectionReport..");
		Session session = null;
		List<StudentCollectionReportTO> studentCollectionSearchResult = null;
		try {
			/*SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();*/
    		 session = HibernateUtil.getSession();
			studentCollectionSearchResult = session.createQuery(searchCriteria).list();
			
		} catch (Exception e) {
			log.error("error while getting the student collection details  "+e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		log.info("exit getStudentCollectionReport..");
		return studentCollectionSearchResult;
	
	}
	@Override
	public List<PcReceipts> getStudentDetails() throws ApplicationException
	{
	Session session = null;
	List<PcReceipts> studentDetailsList = null;
	try {
		/*SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		session = sessionFactory.openSession();*/
		 session = HibernateUtil.getSession();
		studentDetailsList = session.createQuery("select from Student student student.pcReceiptses inner join AdmAppln.students").list();
		
	} catch (Exception e) {
		log.error("error while getting the student collection details  "+e);
		throw new ApplicationException(e);
	} finally {
		if (session != null) {
			session.flush();
			//session.close();
		}
	}
	log.info("exit getStudentDetails..");
	return studentDetailsList;

	}
}
