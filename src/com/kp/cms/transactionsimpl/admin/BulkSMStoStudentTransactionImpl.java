package com.kp.cms.transactionsimpl.admin;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;

import com.kp.cms.bo.admin.Student;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.transactions.admin.IBulkSMStoStudentTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class BulkSMStoStudentTransactionImpl implements IBulkSMStoStudentTransaction{
	private static volatile BulkSMStoStudentTransactionImpl bulkSMStoStudentTransactionImpl = null;
	private static final Log log = LogFactory.getLog(BulkSMStoStudentTransactionImpl.class);
	public BulkSMStoStudentTransactionImpl getInstance() {
		if(bulkSMStoStudentTransactionImpl== null)
		{
			bulkSMStoStudentTransactionImpl = new BulkSMStoStudentTransactionImpl();
		}
		return bulkSMStoStudentTransactionImpl;
	}
	
	public List<Student> getStudnetForClassIds(String classID) throws Exception {
		log.debug("call of getStudnetForClassIds method in BulkSMStoStudentTransactionImpl.class");
		List<Student> students = new ArrayList<Student>();
		Session session = null;
		try
		{
			session = HibernateUtil.getSession();
			String hqlQuery="from Student s where s.classSchemewise.id in ("+classID+") and isAdmitted = 1 and s.isActive = 1 and s.admAppln.isCancelled=0";
			Query query = session.createQuery(hqlQuery);
			students = query.list();
		}
		catch (Exception e) {
			log.error("Error in getStudnetForClassIds");
			log.error("Error is .."+e.getMessage());
			throw new ApplicationException(e);
		}
		finally
		{
			if(session!=null)
			{
				//session.close();
			}
		}
		log.debug("end of getStudnetForClassIds method in BulkSMStoStudentTransactionImpl.class");
		return students;
	}

	@Override
	public List<Integer> getStudentIds(Date smsDate) throws Exception {
		log.debug("call of getStudentIds method in BulkSMStoStudentTransactionImpl.class");
		List<Integer> studentIdList = new ArrayList<Integer>();
		Session session = null;
		try
		{
			session =  HibernateUtil.getSession();
			String hqlQuery = "select a.studentId from  SMSExamMarksMobileMessaging a where a.smsDate='"+smsDate+"'";
			Query query = session.createQuery(hqlQuery);
			studentIdList = query.list();
			
		}
		catch (Exception e) {
			log.error("Error in getStudentIds");
			
		}
		finally
		{
			if(session!=null)
			{
				session.flush();
			}
		}
		
		log.debug("end of getStudentIds method in BulkSMStoStudentTransactionImpl.class");
		return studentIdList;
	}

}
