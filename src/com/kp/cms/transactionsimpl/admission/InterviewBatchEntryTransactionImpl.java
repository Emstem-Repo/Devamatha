package com.kp.cms.transactionsimpl.admission;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.InterviewResult;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.to.admission.InterviewResultTO;
import com.kp.cms.transactions.admission.IInterviewBatchEntryTransaction;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.HibernateUtil;

public class InterviewBatchEntryTransactionImpl implements IInterviewBatchEntryTransaction {
	
	private static final Log log = LogFactory.getLog(InterviewBatchEntryTransactionImpl.class);
	
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.IInterviewBatchEntryTransaction#getSelectedCandidates(com.kp.cms.to.admission.InterviewResultTO)
	 */
	@Override
	public List getSelectedCandidates(InterviewResultTO interviewBatchEntryTO) throws Exception {
		Session session = null;
		List selectedCandidatesList = null;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			Query selectedCandidatesQuery;
			String startTime = interviewBatchEntryTO.getStartTimeHours() +":"+ interviewBatchEntryTO.getStartTimeMins();
			String endTime = interviewBatchEntryTO.getEndTimeHours() +":"+ interviewBatchEntryTO.getEndTimeMins();
			
			if(interviewBatchEntryTO.getInterviewSubroundId() != 0){
				if(interviewBatchEntryTO.getInterviewDate() != null && !interviewBatchEntryTO.getInterviewDate().isEmpty() && !startTime.equals("00:00") && !endTime.equals("00:00")){
					
					selectedCandidatesQuery = session.createQuery("select admAppln.id, " +
							" admAppln.applnNo, " +
							" admAppln.appliedYear, " +
							" admAppln.personalData.firstName," +
							" admAppln.personalData.middleName, " +
							" admAppln.personalData.lastName, " +
							" admAppln.courseBySelectedCourseId.id, " +
							" interviewResult " +
							" from AdmAppln admAppln " +
							" join admAppln.interviewSelecteds interviewSelected with interviewSelected.interviewProgramCourse.id = :interviewRound and interviewSelected.isCardGenerated=1 " +
							" join admAppln.interviewCards interviewCard with interviewCard.time >= :startTime and interviewCard.time <= :endTime " +
							" left outer join admAppln.interviewResults interviewResult with interviewResult.interviewSubRounds.id = :subRound " +
							" where admAppln.courseBySelectedCourseId.id = :courseId " +
							" and admAppln.appliedYear = :appliedYear " +
							" and interviewCard.interview.date = :interviewDate" +
							" group by admAppln.id" +
							" order by admAppln.applnNo");
					selectedCandidatesQuery.setInteger("appliedYear", Integer.parseInt(interviewBatchEntryTO.getAppliedYear()));
					selectedCandidatesQuery.setInteger("courseId", Integer.parseInt(interviewBatchEntryTO.getCourseId()));
					selectedCandidatesQuery.setInteger("interviewRound", interviewBatchEntryTO.getInterviewTypeId());
					selectedCandidatesQuery.setInteger("subRound", interviewBatchEntryTO.getInterviewSubroundId());
					selectedCandidatesQuery.setString("startTime", startTime);
					selectedCandidatesQuery.setString("endTime", endTime);
					selectedCandidatesQuery.setDate("interviewDate", CommonUtil.ConvertStringToSQLDate(interviewBatchEntryTO.getInterviewDate()));
				} else if(interviewBatchEntryTO.getInterviewDate() != null && !interviewBatchEntryTO.getInterviewDate().isEmpty()){
					
					selectedCandidatesQuery = session.createQuery("select admAppln.id, " +
							" admAppln.applnNo, " +
							" admAppln.appliedYear, " +
							" admAppln.personalData.firstName," +
							" admAppln.personalData.middleName, " +
							" admAppln.personalData.lastName, " +
							" admAppln.courseBySelectedCourseId.id, " +
							" interviewResult " +
							" from AdmAppln admAppln " +
							" join admAppln.interviewSelecteds interviewSelected with interviewSelected.interviewProgramCourse.id = :interviewRound and interviewSelected.isCardGenerated=1 " +
							" join admAppln.interviewCards interviewCard " +
							" left outer join admAppln.interviewResults interviewResult with interviewResult.interviewSubRounds.id = :subRound " +
							" where admAppln.courseBySelectedCourseId.id = :courseId " +
							" and admAppln.appliedYear = :appliedYear " +
							" and interviewCard.interview.date = :interviewDate" +
							" group by admAppln.id" +
							" order by admAppln.applnNo");
					selectedCandidatesQuery.setInteger("appliedYear", Integer.parseInt(interviewBatchEntryTO.getAppliedYear()));
					selectedCandidatesQuery.setInteger("courseId", Integer.parseInt(interviewBatchEntryTO.getCourseId()));
					selectedCandidatesQuery.setInteger("interviewRound", interviewBatchEntryTO.getInterviewTypeId());
					selectedCandidatesQuery.setInteger("subRound", interviewBatchEntryTO.getInterviewSubroundId());
					selectedCandidatesQuery.setDate("interviewDate", CommonUtil.ConvertStringToSQLDate(interviewBatchEntryTO.getInterviewDate()));
				} else{
					selectedCandidatesQuery = session.createQuery("select admAppln.id, " +
							" admAppln.applnNo, " +
							" admAppln.appliedYear, " +
							" admAppln.personalData.firstName," +
							" admAppln.personalData.middleName, " +
							" admAppln.personalData.lastName, " +
							" admAppln.courseBySelectedCourseId.id, " +
							" interviewResult " +
							" from AdmAppln admAppln " +
							" join admAppln.interviewSelecteds interviewSelected with interviewSelected.interviewProgramCourse.id = :interviewRound and interviewSelected.isCardGenerated=1 " +
							" left outer join admAppln.interviewResults interviewResult with interviewResult.interviewSubRounds.id = :subRound " +
							" where admAppln.courseBySelectedCourseId.id = :courseId " +
							" and admAppln.appliedYear = :appliedYear" +
							" group by admAppln.id" +
							" order by admAppln.applnNo");
					selectedCandidatesQuery.setInteger("appliedYear", Integer.parseInt(interviewBatchEntryTO.getAppliedYear()));
					selectedCandidatesQuery.setInteger("courseId", Integer.parseInt(interviewBatchEntryTO.getCourseId()));
					selectedCandidatesQuery.setInteger("interviewRound", interviewBatchEntryTO.getInterviewTypeId());
					selectedCandidatesQuery.setInteger("subRound", interviewBatchEntryTO.getInterviewSubroundId());
				}
			}else{
				if(interviewBatchEntryTO.getInterviewDate() != null && !interviewBatchEntryTO.getInterviewDate().isEmpty() && !startTime.equals("00:00") && !endTime.equals("00:00")){
					
					selectedCandidatesQuery = session.createQuery("select admAppln.id, " +
							" admAppln.applnNo, " +
							" admAppln.appliedYear, " +
							" admAppln.personalData.firstName," +
							" admAppln.personalData.middleName, " +
							" admAppln.personalData.lastName, " +
							" admAppln.courseBySelectedCourseId.id, " +
							" interviewResult " +
							" from AdmAppln admAppln " +
							" join admAppln.interviewSelecteds interviewSelected with interviewSelected.interviewProgramCourse.id = :interviewRound and interviewSelected.isCardGenerated=1 " +
							" join admAppln.interviewCards interviewCard with interviewCard.time >= :startTime and interviewCard.time <= :endTime  " +
							" left outer join admAppln.interviewResults interviewResult with interviewResult.interviewProgramCourse.id = :interviewRound " +
							" where admAppln.courseBySelectedCourseId.id = :courseId " +
							" and admAppln.appliedYear = :appliedYear " +
							" and interviewCard.interview.date = :interviewDate" +
							" group by admAppln.id" +
							" order by admAppln.applnNo");
					selectedCandidatesQuery.setInteger("appliedYear", Integer.parseInt(interviewBatchEntryTO.getAppliedYear()));
					selectedCandidatesQuery.setInteger("courseId", Integer.parseInt(interviewBatchEntryTO.getCourseId()));
					selectedCandidatesQuery.setInteger("interviewRound", interviewBatchEntryTO.getInterviewTypeId());
					selectedCandidatesQuery.setString("startTime", startTime);
					selectedCandidatesQuery.setString("endTime", endTime);
					selectedCandidatesQuery.setDate("interviewDate", CommonUtil.ConvertStringToSQLDate(interviewBatchEntryTO.getInterviewDate()));
				} else if (interviewBatchEntryTO.getInterviewDate() != null && !interviewBatchEntryTO.getInterviewDate().isEmpty()){
					
					selectedCandidatesQuery = session.createQuery("select admAppln.id, " +
							" admAppln.applnNo, " +
							" admAppln.appliedYear, " +
							" admAppln.personalData.firstName," +
							" admAppln.personalData.middleName, " +
							" admAppln.personalData.lastName, " +
							" admAppln.courseBySelectedCourseId.id, " +
							" interviewResult " +
							" from AdmAppln admAppln " +
							" join admAppln.interviewSelecteds interviewSelected with interviewSelected.interviewProgramCourse.id = :interviewRound and interviewSelected.isCardGenerated=1 " +
							" join admAppln.interviewCards interviewCard " +
							" left outer join admAppln.interviewResults interviewResult with interviewResult.interviewProgramCourse.id = :interviewRound " +
							" where admAppln.courseBySelectedCourseId.id = :courseId " +
							" and admAppln.appliedYear = :appliedYear " +
							" and interviewCard.interview.date = :interviewDate" +
							" group by admAppln.id" +
							" order by admAppln.applnNo");
					selectedCandidatesQuery.setInteger("appliedYear", Integer.parseInt(interviewBatchEntryTO.getAppliedYear()));
					selectedCandidatesQuery.setInteger("courseId", Integer.parseInt(interviewBatchEntryTO.getCourseId()));
					selectedCandidatesQuery.setInteger("interviewRound", interviewBatchEntryTO.getInterviewTypeId());
					selectedCandidatesQuery.setDate("interviewDate", CommonUtil.ConvertStringToSQLDate(interviewBatchEntryTO.getInterviewDate()));
				}else{
					selectedCandidatesQuery = session.createQuery("select admAppln.id, " +
							" admAppln.applnNo, " +
							" admAppln.appliedYear, " +
							" admAppln.personalData.firstName," +
							" admAppln.personalData.middleName, " +
							" admAppln.personalData.lastName, " +
							" admAppln.courseBySelectedCourseId.id, " +
							" interviewResult " +
							" from AdmAppln admAppln " +
							" join admAppln.interviewSelecteds interviewSelected with interviewSelected.interviewProgramCourse.id = :interviewRound and interviewSelected.isCardGenerated=1 " +
							" left outer join admAppln.interviewResults interviewResult with interviewResult.interviewProgramCourse.id = :interviewRound " +
							" where admAppln.courseBySelectedCourseId.id = :courseId " +
							" and admAppln.appliedYear = :appliedYear" +
							" group by admAppln.id" +
							" order by admAppln.applnNo");
					selectedCandidatesQuery.setInteger("appliedYear", Integer.parseInt(interviewBatchEntryTO.getAppliedYear()));
					selectedCandidatesQuery.setInteger("courseId", Integer.parseInt(interviewBatchEntryTO.getCourseId()));
					selectedCandidatesQuery.setInteger("interviewRound", interviewBatchEntryTO.getInterviewTypeId());
				}
			}
			
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
		return selectedCandidatesList;
	}
	
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.IInterviewBatchEntryTransaction#batchResultUpdate(java.util.List)
	 */
	@Override
	public boolean batchResultUpdate(List<InterviewResult> batchUpdateList) throws Exception {
		Session session = null;
		Transaction transaction = null;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();

			if (batchUpdateList != null) {
				int count = 0;
				Iterator<InterviewResult> iterator = batchUpdateList.iterator();
				while (iterator.hasNext()) {
					InterviewResult interviewResult = iterator.next();
					session.saveOrUpdate(interviewResult);
					if(++count % 20 == 0){
						session.flush();
						session.clear();
					}
				}
				transaction.commit();
			}
		} catch (ConstraintViolationException e) {
			log.error("Error while saving interview result data.."+e);
			if (transaction != null) {
				transaction.rollback();
			}
			throw new BusinessException(e);
		} catch (Exception e) {
			log.error("Error while saving interview result data ..."+e);
			if (transaction != null) {
				transaction.rollback();
			}
			throw new ApplicationException(e);
		} finally{
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		return true;
	}
}