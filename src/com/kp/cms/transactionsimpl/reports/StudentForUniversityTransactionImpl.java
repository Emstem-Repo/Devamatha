package com.kp.cms.transactionsimpl.reports;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;

import com.kp.cms.bo.admin.OfflineDetails;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.transactions.reports.IStudentForUniversityTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class StudentForUniversityTransactionImpl implements
		IStudentForUniversityTransaction {
	
	private static final Log log = LogFactory.getLog(StudentForUniversityTransactionImpl.class);
	
	/**
	 * getting the list of objects for Search criteria
	 * @param SearchCriteria
	 * @return
	 * @throws Exception
	 */
	public List getAllStudentForUniversity(String SearchCriteria) throws Exception {
		log.info("Entering the getAllStudents in TransactionImpl");
		Session session = null;
		List<OfflineDetails> selectedCandidatesList = null;
			try {
				/*SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
				session = sessionFactory.openSession();*/
				session = HibernateUtil.getSession();
				Query selectedCandidatesQuery=session.createQuery(SearchCriteria);
				selectedCandidatesList = selectedCandidatesQuery.list();
			} catch (Exception e) {
				log.error("Error while retrieving selected candidates.." +e);
				throw  new ApplicationException(e);
			} finally {
				if (session != null) {
					session.flush();
					//session.close();
				}
			}
			log.info("Exit from getAllStudents in TransactionImpl");
			return selectedCandidatesList;
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
