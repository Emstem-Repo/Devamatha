package com.kp.cms.transactionsimpl.exam;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.exam.ExamStudentFinalMarkDetailsBO;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.transactions.exam.IUploadExamStudentFinalMarksTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class UploadExamStudentFinalMarksTransactionImpl implements
		IUploadExamStudentFinalMarksTransaction {

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.exam.IUploadExamStudentFinalMarksTransaction#saveExamStudentOverallInternalMarkDetailsBOList(java.util.List, java.lang.String)
	 */
	@Override
	public boolean saveExamStudentOverallInternalMarkDetailsBOList(
			List<ExamStudentFinalMarkDetailsBO> list, String action)
			throws Exception {
		Session session = null;
		Transaction transaction = null;
		ExamStudentFinalMarkDetailsBO markDetailsBO;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			transaction.begin();
			Iterator<ExamStudentFinalMarkDetailsBO> tcIterator = list.iterator();
			int count = 0;
			while(tcIterator.hasNext()){
				markDetailsBO = tcIterator.next();
				ExamStudentFinalMarkDetailsBO oldDetails=getOldMarkDetails(markDetailsBO.getStudentId(), markDetailsBO.getSubjectId(), markDetailsBO.getClassId(),markDetailsBO.getExamId());
				if(oldDetails!=null)
				{
						oldDetails.setStudentTheoryMarks(markDetailsBO.getStudentTheoryMarks());
						oldDetails.setStudentPracticalMarks(markDetailsBO.getStudentPracticalMarks());
						oldDetails.setPassOrFail(markDetailsBO.getPassOrFail());
						oldDetails.setModifiedBy(markDetailsBO.getModifiedBy());
						oldDetails.setLastModifiedDate(new Date());
						session.update(oldDetails);
				}
				else
				{	
					session.save(markDetailsBO);
				}
				if(++count % 20 == 0){
					session.flush();
					session.clear();
				}
			}
			
			transaction.commit();
			session.flush();
			return true;
		} catch (ConstraintViolationException e) {
			transaction.rollback();
			throw new BusinessException(e);
		} catch (Exception e) {
			transaction.rollback();
			throw new ApplicationException(e);
		}
	}

	/**
	 * @param studentId
	 * @param subjectId
	 * @param classId
	 * @param examId
	 * @return
	 * @throws Exception
	 */
	private ExamStudentFinalMarkDetailsBO getOldMarkDetails(
			int studentId, int subjectId, int classId, int examId) throws Exception {
		Session session = null;
		ExamStudentFinalMarkDetailsBO oldDetails=null;
		try {
			session = HibernateUtil.getSession();
			oldDetails=(ExamStudentFinalMarkDetailsBO)session.createQuery("from ExamStudentFinalMarkDetailsBO e where e.classId="+classId+" and e.subjectId="+subjectId+" and e.studentId="+studentId+" and e.examId="+examId).uniqueResult();
			return oldDetails;
		} catch (Exception e) {
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.clear();
			}
		}
		
	}

}
