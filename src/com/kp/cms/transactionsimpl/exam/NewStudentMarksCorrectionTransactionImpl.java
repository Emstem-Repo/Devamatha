package com.kp.cms.transactionsimpl.exam;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.TermsConditionChecklist;
import com.kp.cms.bo.exam.ExamMarksVerificationEntryBO;
import com.kp.cms.bo.exam.MarksEntry;
import com.kp.cms.bo.exam.MarksEntryCorrectionDetails;
import com.kp.cms.bo.exam.MarksEntryDetails;
import com.kp.cms.bo.exam.StudentFinalMarkDetails;
import com.kp.cms.bo.exam.StudentOverallInternalMarkDetails;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.to.exam.NewStudentMarkCorrectionTo;
import com.kp.cms.transactions.exam.INewStudentMarksCorrectionTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class NewStudentMarksCorrectionTransactionImpl implements
		INewStudentMarksCorrectionTransaction {
	/**
	 * Singleton object of NewStudentMarksCorrectionTransactionImpl
	 */
	private static volatile NewStudentMarksCorrectionTransactionImpl newStudentMarksCorrectionTransactionImpl = null;
	private static final Log log = LogFactory.getLog(NewStudentMarksCorrectionTransactionImpl.class);
	private NewStudentMarksCorrectionTransactionImpl() {
		
	}
	/**
	 * return singleton object of NewStudentMarksCorrectionTransactionImpl.
	 * @return
	 */
	public static NewStudentMarksCorrectionTransactionImpl getInstance() {
		if (newStudentMarksCorrectionTransactionImpl == null) {
			newStudentMarksCorrectionTransactionImpl = new NewStudentMarksCorrectionTransactionImpl();
		}
		return newStudentMarksCorrectionTransactionImpl;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.exam.INewStudentMarksCorrectionTransaction#saveMarksEntryCorrection(java.util.List)
	 */
	public boolean saveMarksEntryCorrection(List<MarksEntryCorrectionDetails> boList) throws Exception {
		log.debug("inside saveMarksEntryCorrection");
		Session session = null;
		Transaction transaction = null;
		MarksEntryCorrectionDetails tcLChecklist;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			transaction.begin();
			Iterator<MarksEntryCorrectionDetails> tcIterator = boList.iterator();
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
			log.debug("leaving saveMarksEntryCorrection");
			return true;
		} catch (ConstraintViolationException e) {
			transaction.rollback();
			log.error("Error in saveMarksEntryCorrection impl...", e);
			throw new BusinessException(e);
		} catch (Exception e) {
			transaction.rollback();
			log.error("Error in saveMarksEntryCorrection impl...", e);
			throw new ApplicationException(e);
		}
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.exam.INewStudentMarksCorrectionTransaction#updateModifiedMarks(java.util.List, java.lang.String)
	 */
	public void updateModifiedMarks(List<NewStudentMarkCorrectionTo> marksList,String userId,String marksCardNo) throws Exception {
		log.debug("inside updateModifiedMarks");
		Session session = null;
		Transaction transaction = null;
		NewStudentMarkCorrectionTo to;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			transaction.begin();
			Iterator<NewStudentMarkCorrectionTo> tcIterator = marksList.iterator();
			int count = 0;
			while(tcIterator.hasNext()){
				to = tcIterator.next();
				boolean isTheoryChanged=false;
				boolean isPracticalChanged=false;
				if(to.getIsTheory()){
					if(to.getOldTheoryMarks()!=null && !to.getTheoryMarks().equals(to.getOldTheoryMarks())){
						isTheoryChanged=true;
					}
				}
				if(to.getIsPractical()){
					if(to.getOldPracticalMarks()!=null && !to.getPracticalMarks().equals(to.getOldPracticalMarks())){
						isPracticalChanged=true;
					}
				}
				if(isPracticalChanged || isTheoryChanged){
					if(to.getMarksEntryDetailsId()>0){
						MarksEntryDetails marksEntryDetails=(MarksEntryDetails)session.get(MarksEntryDetails.class, to.getMarksEntryDetailsId());
						marksEntryDetails.setModifiedBy(userId);
						marksEntryDetails.setLastModifiedDate(new Date());
						if(isPracticalChanged)
							marksEntryDetails.setPracticalMarks(to.getPracticalMarks());
						if(isTheoryChanged)
							marksEntryDetails.setTheoryMarks(to.getTheoryMarks());
						// /* code added by chandra
						if(to.getGracing()!=null && to.getGracing().equalsIgnoreCase("on"))
							marksEntryDetails.setIsGracing(true);
						else
							marksEntryDetails.setIsGracing(false);
						// */
						
						MarksEntry marksEntry=(MarksEntry)session.get(MarksEntry.class,to.getMarksEntryId());
						marksEntry.setMarksCardNo(marksCardNo);
						session.update(marksEntry);
						session.update(marksEntryDetails);
						/* code added by Sudhir */
						String str = "from ExamMarksVerificationEntryBO exam where exam.examId = "+marksEntry.getExam().getId()+
									 " and exam.studentId="+marksEntry.getStudent().getId()+" and exam.subjectId="+marksEntryDetails.getSubject().getId();
						if(marksEntry.getEvaluatorType()!=null && !marksEntry.getEvaluatorType().toString().isEmpty()){
							str = str + " and exam.evaluatorTypeId="+marksEntry.getEvaluatorType();
						}
						Query query = session.createQuery(str);
						ExamMarksVerificationEntryBO examVerifyBo = (ExamMarksVerificationEntryBO) query.uniqueResult();
						if(examVerifyBo!=null && !examVerifyBo.toString().isEmpty()){
							examVerifyBo.setVmarks(marksEntryDetails.getTheoryMarks());
							examVerifyBo.setModifiedBy(userId);
							examVerifyBo.setLastModifiedDate(new Date());
							session.update(examVerifyBo);
						}
						/* code added by Sudhir */
					}
					if(to.getInternalOverAllId()>0){
						StudentOverallInternalMarkDetails internalMarkDetails=(StudentOverallInternalMarkDetails)session.get(StudentOverallInternalMarkDetails.class, to.getInternalOverAllId());
						internalMarkDetails.setLastModifiedDate(new Date());
						if(isPracticalChanged)
							internalMarkDetails.setPracticalTotalSubInternalMarks(to.getPracticalMarks());
						if(isTheoryChanged)
							internalMarkDetails.setTheoryTotalSubInternalMarks(to.getTheoryMarks());
						
						session.update(internalMarkDetails);
					}
					if(to.getRegularOverAllId()>0){
						StudentFinalMarkDetails details=(StudentFinalMarkDetails)session.get(StudentFinalMarkDetails.class,to.getRegularOverAllId());
						details.setLastModifiedDate(new Date());
						details.setModifiedBy(userId);
						if(isPracticalChanged)
							details.setStudentPracticalMarks(to.getPracticalMarks());
						if(isTheoryChanged)
							details.setStudentTheoryMarks(to.getTheoryMarks());
						
						session.update(details);
					}
				}
				if(++count % 20 == 0){
					session.flush();
					session.clear();
				}
			}
			transaction.commit();
			session.flush();
			//session.close();
			log.debug("leaving updateModifiedMarks");
		} catch (ConstraintViolationException e) {
			transaction.rollback();
			log.error("Error in updateModifiedMarks impl...", e);
			throw new BusinessException(e);
		} catch (Exception e) {
			transaction.rollback();
			log.error("Error in updateModifiedMarks impl...", e);
			throw new ApplicationException(e);
		}
	
		
		
	}
	@Override
	public Integer getStudentId(String regNo, String schemeNo,String rollNO) throws Exception {
		Session session = null;
		Integer id = null;
		try {
			session = HibernateUtil.getSession();
			if(regNo!=null && !regNo.isEmpty()){
				Query selectedCandidatesQuery=session.createQuery("select s.id from Student s where s.isHide=0 and s.admAppln.isCancelled=0 and s.registerNo='"+regNo+"'");
				id = (Integer)selectedCandidatesQuery.uniqueResult();
				if(id==null || id==0){
					Query query=session.createQuery("select stu.student.id " +
								 " from StudentOldRegisterNumber stu " +
								 " where stu.isActive=1 and stu.registerNo='"+regNo+"' and stu.schemeNo="+schemeNo+" order by stu.student.id ");
					id = (Integer)query.uniqueResult();
				}
			}else if(rollNO!=null && !rollNO.isEmpty()){
				Query selectedCandidatesQuery=session.createQuery("select s.id from Student s where s.isHide=0 and s.admAppln.isCancelled=0 and s.rollNo='"+rollNO+"'");
				id = (Integer)selectedCandidatesQuery.uniqueResult();
			}
			return id;
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
