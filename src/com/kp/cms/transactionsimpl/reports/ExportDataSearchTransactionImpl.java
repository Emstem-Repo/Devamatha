package com.kp.cms.transactionsimpl.reports;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;

import com.kp.cms.bo.admin.Student;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.transactions.reports.IExportDataSearchTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class ExportDataSearchTransactionImpl implements IExportDataSearchTransaction{
private static final Log log = LogFactory.getLog(ExportDataSearchTransactionImpl.class);
	
	public static volatile ExportDataSearchTransactionImpl self=null;
	
	/**
	 * @return unique instance of LeaveReportTransactionImpl class.
	 * This method gives instance of this method
	 */
	public static ExportDataSearchTransactionImpl getInstance(){
		if(self==null)
			self= new ExportDataSearchTransactionImpl();
		return self;
	}

	@Override
	public List<Student> getSearchCriteria(String search) throws Exception {
		log.info("entered getSearchCriteria..");
			Session session = null;
			List<Student> leaveList = null;
			try {
				//SessionFactory sessionFactory = InitSessionFactory.getInstance();
				//session = sessionFactory.openSession();
				session = HibernateUtil.getSession();
				leaveList = session.createQuery(search).list();
			
			} catch (Exception e) {
				log.error("error while getting the getSearchCriteria results  ",e);
				throw new ApplicationException(e);
			} finally {
				if (session != null) {
					session.flush();
					//session.close();
				}
			}
			log.info("exit getSearchCriteria..");
			
			return leaveList;
	}
	
	public List<Integer> getDetainedOrDiscontinuedStudents() throws Exception
	{
		List<Integer> studentList=null;
		Session session=null;
		try
		{
			session=HibernateUtil.getSession();
			
			String query="select s.student.id from ExamStudentDetentionRejoinDetails s " +
			"where (s.detain=1 or s.discontinued=1) and (s.rejoin = 0 or s.rejoin is null)";
			studentList=session.createQuery(query).list();
		}
		catch (Exception e) 
		{
			throw  new ApplicationException(e);
		}
		
		return studentList;
	}
	
}