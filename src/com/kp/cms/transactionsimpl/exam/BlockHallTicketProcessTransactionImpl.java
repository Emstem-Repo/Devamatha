package com.kp.cms.transactionsimpl.exam;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.TermsConditionChecklist;
import com.kp.cms.bo.exam.ExamBlockUnblockHallTicketBO;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.transactions.exam.IBlockHallTicketProcessTransaction;
import com.kp.cms.utilities.HibernateUtil;

/**
 * @author user
 *
 */
public class BlockHallTicketProcessTransactionImpl implements IBlockHallTicketProcessTransaction {
	/**
	 * Singleton object of BlockHallTicketProcessTransactionImpl
	 */
	private static volatile BlockHallTicketProcessTransactionImpl blockHallTicketProcessTransactionImpl = null;
	private static final Log log = LogFactory.getLog(BlockHallTicketProcessTransactionImpl.class);
	private BlockHallTicketProcessTransactionImpl() {
		
	}
	/**
	 * return singleton object of BlockHallTicketProcessTransactionImpl.
	 * @return
	 */
	public static BlockHallTicketProcessTransactionImpl getInstance() {
		if (blockHallTicketProcessTransactionImpl == null) {
			blockHallTicketProcessTransactionImpl = new BlockHallTicketProcessTransactionImpl();
		}
		return blockHallTicketProcessTransactionImpl;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.exam.IBlockHallTicketProcessTransaction#getAttendanceForClasses(java.util.List)
	 */
	@Override
	public List<Object[]> getAttendanceForClasses(List<Integer> classesList) throws Exception {
		Session session = null;
		List selectedCandidatesList = null;
		try {
			/*SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();
			Query selectedCandidatesQuery=session.createQuery("select ac.classSchemewise.classes, attStu.student, " +
					" sum(a.hoursHeld), sum(case when (attStu.isPresent=1 ) then a.hoursHeld else 0 end), sum(case when (attStu.isCoCurricularLeave=1) then a.hoursHeld else 0 end), " +
					" sum(case when (attStu.isOnLeave=1) then a.hoursHeld else 0 end)" +
					" from Attendance a join a.attendanceStudents attStu" +
					" join a.attendanceClasses ac where ac.classSchemewise.id= attStu.student.classSchemewise.id" +
					" and attStu.student.admAppln.isCancelled=0 and a.isCanceled=0 and attStu.student.isHide=0 " +
					" and ac.classSchemewise.classes.id in (:classesList) group by attStu.student.id ").setParameterList("classesList",classesList);
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
	public boolean saveHallTickets( List<ExamBlockUnblockHallTicketBO> hallTicketList) throws Exception {
		log.debug("inside addTermsConditionCheckList");
		Session session = null;
		Transaction transaction = null;
		ExamBlockUnblockHallTicketBO tcLChecklist;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			transaction.begin();
			Iterator<ExamBlockUnblockHallTicketBO> tcIterator = hallTicketList.iterator();
			int count = 0;
			while(tcIterator.hasNext()){
				tcLChecklist = tcIterator.next();
				session.saveOrUpdate(tcLChecklist);
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
	public Map<Integer, Double> getLeaveMap(List<Integer> classesList)
			throws Exception {
		Session session = null;
		List selectedCandidatesList = null;
		Map<Integer, Double> map=new HashMap<Integer, Double>();
		try {
			session = HibernateUtil.getSession();
			Query selectedCandidatesQuery=session.createQuery("select attStu.student.id, sum(a.hoursHeld)" +
					" from Attendance a join a.attendanceStudents attStu " +
					" join a.attendanceClasses ac where ac.classSchemewise.id= attStu.student.classSchemewise.id and attStu.student.admAppln.isCancelled=0 and a.isCanceled=0 " +
					"and attStu.student.isHide=0 and ac.classSchemewise.classes.id in (:classesList)" +
					" and attStu.isOnLeave=1 group by ac.classSchemewise.id,attStu.student.id").setParameterList("classesList",classesList);
			selectedCandidatesList = selectedCandidatesQuery.list();
			if(selectedCandidatesList!=null && !selectedCandidatesList.isEmpty()){
				Iterator<Object[]> itr=selectedCandidatesList.iterator();
				while (itr.hasNext()) {
					Object[] obj= (Object[]) itr.next();
					if(obj[0]!=null && obj[1]!=null)
						map.put(Integer.parseInt(obj[0].toString()),Double.parseDouble(obj[1].toString()));
				}
			}
			return map;
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
}
