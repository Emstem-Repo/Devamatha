package com.kp.cms.transactionsimpl.attendance;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.Attendance;
import com.kp.cms.bo.admin.AttendanceStudent;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.attendance.AttendanceReEntryForm;
import com.kp.cms.helpers.attendance.AttendanceReEntryHelper;
import com.kp.cms.transactions.attandance.IAttendanceReEntryTransactin;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.HibernateUtil;

public class AttendanceReEntryTransactionImpl implements
		IAttendanceReEntryTransactin {
	private static final String FROM_DATEFORMAT = "dd/MM/yyyy";
	private static final String SQL_DATEFORMAT = "dd-MMM-yyyy";
	private static final Log log = LogFactory.getLog(AttendanceReEntryHelper.class);
	@Override
	public List<Object[]> getDuplicateAttendance(String query,AttendanceReEntryForm attendanceReEntryForm)
			throws Exception {
		Session session = null;
		List<Object[]> selectedCandidatesList = null;
		try {
			session = HibernateUtil.getSession();
			Query selectedCandidatesQuery=session.createQuery(query);
			selectedCandidatesList = selectedCandidatesQuery.list();
			if(selectedCandidatesList!=null && !selectedCandidatesList.isEmpty()){
				Iterator<Object[]> itr=selectedCandidatesList.iterator();
				while (itr.hasNext()) {
					Object[] objects = (Object[]) itr.next();
					if(objects[0]!=null){
						attendanceReEntryForm.setRange(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate((Date)objects[0]),
								AttendanceReEntryTransactionImpl.SQL_DATEFORMAT,
								AttendanceReEntryTransactionImpl.FROM_DATEFORMAT));
					}if(objects[1]!=null){
						attendanceReEntryForm.setRange(attendanceReEntryForm.getRange()+" to "+CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate((Date)objects[1]),
								AttendanceReEntryTransactionImpl.SQL_DATEFORMAT,
								AttendanceReEntryTransactionImpl.FROM_DATEFORMAT));
					}
					if(objects[0]==null && objects[1]==null){
						attendanceReEntryForm.setRange(null);
					}
				}
			}
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

	@Override
	public List<Student> getStudent(String query) throws Exception {
		Session session = null;
		List<Student> selectedCandidatesList = null;
		try {
			session = HibernateUtil.getSession();
			Query selectedCandidatesQuery=session.createQuery(query);
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

	@Override
	public List<Attendance> getAttendanceList(String query) throws Exception {
		Session session = null;
		List<Attendance> selectedCandidatesList = null;
		try {
			session = HibernateUtil.getSession();
			Query selectedCandidatesQuery=session.createQuery(query);
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

	@Override
	public Student getStudentByRegNo(String studentByRegnoQuery)
			throws Exception {
		Session session = null;
		Student selectedCandidatesList = null;
		try {
			session = HibernateUtil.getSession();
			Query selectedCandidatesQuery=session.createQuery(studentByRegnoQuery);
			selectedCandidatesList = (Student)selectedCandidatesQuery.uniqueResult();
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

	@Override
	public boolean saveAttendanceStudent(List<AttendanceStudent> finalList)
			throws Exception {
		Session session = null;
		Transaction transaction = null;
		AttendanceStudent tcLChecklist;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			transaction.begin();
			Iterator<AttendanceStudent> tcIterator = finalList.iterator();
			int count = 0;
			while(tcIterator.hasNext()){
				tcLChecklist = tcIterator.next();
				session.save(tcLChecklist);
				if(++count % 20 == 0){
					session.flush();
					session.clear();
				}
			}
			
			transaction.commit();
			session.flush();
			//session.close();
			log.debug("leaving addTermsConditionCheckList");
			return true;
		} catch (ConstraintViolationException e) {
			transaction.rollback();
			log.error("Error in addTermsConditionCheckList impl...", e);
			throw new BusinessException(e);
		} catch (Exception e) {
			transaction.rollback();
			log.error("Error in addTermsConditionCheckList impl...", e);
			throw new ApplicationException(e);
		}
	}

	@Override
	public List<Integer> getAttendanceIdByAttendanceStudent(String query)
			throws Exception {
		Session session = null;
		List<Integer> selectedCandidatesList = null;
		try {
			session = HibernateUtil.getSession();
			Query selectedCandidatesQuery=session.createQuery(query);
			selectedCandidatesList = selectedCandidatesQuery.list();
			if(selectedCandidatesList!=null)
				return selectedCandidatesList;
			else
				return new ArrayList<Integer>();
		} catch (Exception e) {
			log.error("Error while retrieving selected candidates.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
	}

}
