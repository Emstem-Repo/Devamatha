package com.kp.cms.transactionsimpl.admission;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;

import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.transactions.admission.IPrintShortageAttendanceTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class PrintShortageAttendanceTransactionImpl implements
		IPrintShortageAttendanceTransaction {
	private static final Log log = LogFactory.getLog(PrintShortageAttendanceTransactionImpl.class);
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.IPrintShortageAttendanceTransaction#getListOfDetails(java.lang.String)
	 */
	@Override
	public List<Object[]> getListOfDetails(String listQuery) throws Exception {
		Session session = null;
		List<Object[]> selectedCandidatesList = null;
		try {
			session = HibernateUtil.getSession();
			Query selectedCandidatesQuery=session.createQuery(listQuery);
			selectedCandidatesList = selectedCandidatesQuery.list();
			return selectedCandidatesList;
		} catch (Exception e) {
			log.error("Error while retrieving selected candidates.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
	}
	public Map<Integer,Integer> getMapOfStudent(String listQuery) throws Exception {
		Session session = null;
		List<Object[]> selectedCandidatesList = null;
		Map<Integer, Integer> finalMap=new HashMap<Integer, Integer>();
		try {
			session = HibernateUtil.getSession();
			Query selectedCandidatesQuery=session.createQuery(listQuery);
			selectedCandidatesList = selectedCandidatesQuery.list();
			if(selectedCandidatesList!=null && !selectedCandidatesList.isEmpty()){
				Iterator<Object[]> itr=selectedCandidatesList.iterator();
				while (itr.hasNext()) {
					Object[] objects = (Object[]) itr.next();
					finalMap.put(Integer.parseInt(objects[0].toString()),Integer.parseInt(objects[1].toString()));
				}
			}
			return finalMap;
		} catch (Exception e) {
			log.error("Error while retrieving selected candidates.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
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
