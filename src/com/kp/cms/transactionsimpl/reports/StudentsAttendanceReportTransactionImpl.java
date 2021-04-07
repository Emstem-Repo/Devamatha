package com.kp.cms.transactionsimpl.reports;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;

import com.kp.cms.bo.admin.AttendanceStudent;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.transactions.reports.IStudentAttendanceReportTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class StudentsAttendanceReportTransactionImpl implements IStudentAttendanceReportTransaction {
	
	private static final Log log = LogFactory.getLog(StudentsAttendanceReportTransactionImpl.class);

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.reports.IStudentAttendanceReportTransaction#getStudentsAttendance(java.lang.String)
	 */
	@Override
	public List<AttendanceStudent> getStudentsAttendance(String searchCriteria) throws Exception {
		log.info("entered getStudentAttendance..");
		Session session = null;
		List<AttendanceStudent> studentsAttendanceResult = null;
		try {
			/*SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();
			studentsAttendanceResult = session.createQuery(searchCriteria).list();
			
		} catch (Exception e) {
			log.error("error while getting the student attendance results  "+e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		log.info("exit getStudentAttendance..");
		return studentsAttendanceResult;
	}
	public List<Integer> getDetainedOrDiscontinuedStudents() throws Exception
	{
		List<Integer> studentList=null;
		Session session=null;
		try
		{
			session=HibernateUtil.getSession();
			/*	String query="select s.student.id from ExamStudentDetentionRejoinDetails s " +
							"where s.id=(select max(id) from ExamStudentDetentionRejoinDetails b " +
							"where b.student.id=s.student.id) and (s.detain=1 or s.discontinued=1)";
			*/
			String query="select s.student.registerNo from ExamStudentDetentionRejoinDetails s " +
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
