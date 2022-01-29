package com.kp.cms.transactionsimpl.attendance;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.AttendanceStudent;
import com.kp.cms.bo.admin.TermsConditionChecklist;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.transactions.attandance.IRemoveAttendanceTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class RemoveAttendanceTransactionImpl implements
		IRemoveAttendanceTransaction {
	/**
	 * Singleton object of RemoveAttendanceTransactionImpl
	 */
	private static volatile RemoveAttendanceTransactionImpl removeAttendanceTransactionImpl = null;
	private static final Log log = LogFactory.getLog(RemoveAttendanceTransactionImpl.class);
	private RemoveAttendanceTransactionImpl() {
		
	}
	/**
	 * return singleton object of RemoveAttendanceTransactionImpl.
	 * @return
	 */
	public static RemoveAttendanceTransactionImpl getInstance() {
		if (removeAttendanceTransactionImpl == null) {
			removeAttendanceTransactionImpl = new RemoveAttendanceTransactionImpl();
		}
		return removeAttendanceTransactionImpl;
	}
	@Override
	public List<AttendanceStudent> getAttStudentList(String query)
			throws Exception {
		Session session = null;
		List<AttendanceStudent> selectedCandidatesList = null;
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
				//session.close();
			}
		}
		
	}
	@Override
	public boolean saveAttendanceReEntry(List<Integer> finalList, String userId)
			throws Exception {
		log.debug("inside addTermsConditionCheckList");
		Session session = null;
		Transaction transaction = null;
		AttendanceStudent bo;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			transaction.begin();
			Iterator<Integer> tcIterator = finalList.iterator();
			int count = 0;
			while(tcIterator.hasNext()){
				int id = tcIterator.next();
				bo=(AttendanceStudent)session.get(AttendanceStudent.class, id);
				session.delete(bo);
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
}
