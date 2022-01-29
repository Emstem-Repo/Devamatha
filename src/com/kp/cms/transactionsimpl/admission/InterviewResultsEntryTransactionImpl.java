package com.kp.cms.transactionsimpl.admission;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.bo.admin.ApplicantRecommendor;
import com.kp.cms.bo.admin.ApplnDoc;
import com.kp.cms.bo.admin.InterviewProgramCourse;
import com.kp.cms.bo.admin.InterviewResult;
import com.kp.cms.bo.admin.InterviewSubRounds;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.transactions.admission.IInterviewResultsEntryTransaction;
import com.kp.cms.utilities.HibernateUtil;

/**
 * @author prashanth.mh
 * 
 */
public class InterviewResultsEntryTransactionImpl implements
		IInterviewResultsEntryTransaction  {
	
	private static final Log log = LogFactory.getLog(InterviewResultsEntryTransactionImpl.class);
	
	/* (non-Javadoc)
	 * @see com.brio.cms.transactions.admission.IInterviewResultsEntryTransaction#getApplicantDetails(java.lang.String, int, int)
	 */
	@Override
	public AdmAppln getApplicantDetails(String applicationNumber,int applicationYear,int courseId) throws Exception{

		Session session = null;
		AdmAppln applicantDetails = null;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			applicantDetails = (AdmAppln) session.createQuery("from AdmAppln a where a.applnNo="+applicationNumber+" and a.appliedYear="+applicationYear+" and a.course.id="+courseId).uniqueResult();
		} catch (Exception e) {
			log.error("Error while getting applicant details..."+e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		return applicantDetails;
	}

	
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.IInterviewResultsEntryTransaction#getInterviewTypesByCourse(int, int, int)
	 */
	@Override
	public Map<Integer, String> getInterviewTypesByCourse(int courseId, int applicationId, int appliedYear) throws Exception {
		Map<Integer, String> interviewTypesMap = new LinkedHashMap<Integer, String>();
		Session session = null;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			int roundId = 0;
			int subrounds = 0;
			int mainRoundId =0;
			String interviewResultHQ = "select max(interviewResult.interviewProgramCourse.sequence), interviewResult.interviewProgramCourse.id from InterviewResult interviewResult where interviewResult.admAppln.id = :applicationId group by interviewResult.interviewProgramCourse.id";
			Query interviewResultQuery = session.createQuery(interviewResultHQ);
			interviewResultQuery.setInteger("applicationId", applicationId);
			
			Iterator it = interviewResultQuery.iterate();
			while (it.hasNext()) {
				Object[] row = (Object[]) it.next();
				if( row != null ){
					roundId = Integer.valueOf(row[0].toString());
					mainRoundId = Integer.valueOf(row[1].toString());
				}
			}
			
			String interviewSubroundsHQ = "select interviewSubrounds.id "+
			" from InterviewSubRounds interviewSubrounds where interviewSubrounds.interviewProgramCourse.id = :roundId and interviewSubrounds.isActive = 1";
			Query interviewSubroundsQuery = session.createQuery(interviewSubroundsHQ);
			interviewSubroundsQuery.setInteger("roundId", mainRoundId);
			
			Iterator subroundsIterator = interviewSubroundsQuery.iterate();
			while (subroundsIterator.hasNext()) {
				Integer row = (Integer) subroundsIterator.next();
				if( row != 0 ){
					subrounds++;
				}
			}
			
			Query query = session.createQuery("from InterviewSubRounds interviewSubrounds " +
						" where interviewSubrounds.id not in( " +
				 		" select interviewResult.interviewSubRounds.id " +
				 		" from InterviewResult interviewResult " +
				 		" where interviewResult.admAppln.id = :applicationId) " +
				 		" and interviewSubrounds.interviewProgramCourse.id = :interviewTypeId" +
				 		" and interviewSubrounds.isActive = 1"); 
				 query.setInteger("interviewTypeId", mainRoundId);
				 query.setInteger("applicationId", applicationId);
			
			 List subroundList =  query.list();
			 if(subroundList.isEmpty()){
				 subrounds = 0;
			 }
			 	 
			Query interviewTypeQuery = null;
			
			if( subrounds != 0){
				String interviewTypeHQ = " select interviewSelected.interviewProgramCourse.id, " +
				" interviewSelected.interviewProgramCourse.name " +
				" from InterviewSelected interviewSelected " +
				" where interviewSelected.isCardGenerated = 1 " +
				" and interviewSelected.admAppln.id = :applicationId " +
				" and interviewSelected.interviewProgramCourse.sequence >= :roundId " +
				" and interviewSelected.interviewProgramCourse.year = :appliedYear " +
				" and interviewSelected.interviewProgramCourse.course.id = :courseId " +
				" and interviewSelected.interviewProgramCourse.isActive = 1 " +
				" order by interviewSelected.interviewProgramCourse.sequence ";
				interviewTypeQuery = session.createQuery(interviewTypeHQ);
				interviewTypeQuery.setInteger("applicationId", applicationId);
				interviewTypeQuery.setInteger("roundId", roundId);
				interviewTypeQuery.setInteger("appliedYear", appliedYear);
				interviewTypeQuery.setInteger("courseId", courseId);
			}else {
				String interviewTypeHQ = " select interviewSelected.interviewProgramCourse.id, " +
				" interviewSelected.interviewProgramCourse.name " +
				" from InterviewSelected interviewSelected " +
				" where interviewSelected.isCardGenerated = 1 " +
				" and interviewSelected.admAppln.id = :applicationId " +
				" and interviewSelected.interviewProgramCourse.sequence > :roundId " +
				" and interviewSelected.interviewProgramCourse.year = :appliedYear " +
				" and interviewSelected.interviewProgramCourse.course.id = :courseId " +
				" and interviewSelected.interviewProgramCourse.isActive = 1 " +
				" order by interviewSelected.interviewProgramCourse.sequence ";
				interviewTypeQuery = session.createQuery(interviewTypeHQ);
				interviewTypeQuery.setInteger("applicationId", applicationId);
				interviewTypeQuery.setInteger("roundId", roundId);
				interviewTypeQuery.setInteger("appliedYear", appliedYear);
				interviewTypeQuery.setInteger("courseId", courseId);
			}	

			for (Iterator iterator = interviewTypeQuery.iterate(); iterator
					.hasNext();) {
				Object[] row = (Object[]) iterator.next();
				interviewTypesMap.put((Integer) row[0], (String) row[1].toString());
			}
		} catch (Exception e) {
			log.error("Error while getting interview types by course..."+e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		return interviewTypesMap;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.IInterviewResultsEntryTransaction#getInterviewTypesByProgram(int, int, int)
	 */
	@Override
	public Map<Integer, String> getInterviewTypesByProgram(int programId, int applicationId, int appliedYear) throws Exception {
		Map<Integer, String> interviewTypesMap = new LinkedHashMap<Integer, String>();
		Session session = null;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			
			int roundId = 0;
			int subrounds = 0;
			int mainRoundId =0;
			String interviewResultHQ = "select max(interviewResult.interviewProgramCourse.sequence), interviewResult.interviewProgramCourse.id from InterviewResult interviewResult where interviewResult.admAppln.id = :applicationId group by interviewResult.interviewProgramCourse.id";
			Query interviewResultQuery = session.createQuery(interviewResultHQ);
			interviewResultQuery.setInteger("applicationId", applicationId);
			
			Iterator it = interviewResultQuery.iterate();
			while (it.hasNext()) {
				String[] row = (String[]) it.next();
				if( row != null ){
					roundId = Integer.valueOf(row[0]);
					mainRoundId = Integer.valueOf(row[1]);
				}
			}
			

			String interviewSubroundsHQ = "select interviewSubrounds.id "+
			" from InterviewSubRounds interviewSubrounds where interviewSubrounds.interviewProgramCourse.id = :roundId  and interviewSubrounds.isActive = 1";
			Query interviewSubroundsQuery = session.createQuery(interviewSubroundsHQ);
			interviewSubroundsQuery.setInteger("roundId", mainRoundId);
			
			Iterator subroundsIterator = interviewSubroundsQuery.iterate();
			while (subroundsIterator.hasNext()) {
				Integer row = (Integer) subroundsIterator.next();
				if( row != 0 ){
					subrounds++;
				}
			}
			
			 Query query = session.createQuery("from InterviewSubRounds interviewSubrounds " +
						" where interviewSubrounds.id not in( " +
				 		" select interviewResult.interviewSubRounds.id " +
				 		" from InterviewResult interviewResult " +
				 		" where interviewResult.admAppln.id = :applicationId) " +
				 		" and interviewSubrounds.interviewProgramCourse.id = :interviewTypeId" +
				 		" and interviewSubrounds.isActive = 1"); 
				 query.setInteger("interviewTypeId", mainRoundId);
				 query.setInteger("applicationId", applicationId);
			
			 List subroundList =  query.list();
			 if(subroundList.isEmpty()){
				 subrounds = 0;
			 }
			 
			Query interviewTypeQuery = null;
			
			if( subrounds != 0){
				String interviewTypeHQ = " select interviewSelected.interviewProgramCourse.id, " +
				" interviewSelected.interviewProgramCourse.name " +
				" from InterviewSelected interviewSelected " +
				" where interviewSelected.isCardGenerated = 1 " +
				" and interviewSelected.admAppln.id = :applicationId " +
				" and interviewSelected.interviewProgramCourse.sequence >= :roundId " +
				" and interviewSelected.interviewProgramCourse.year = :appliedYear " +
				" and interviewSelected.interviewProgramCourse.program.id = :programId " +
				" and interviewSelected.interviewProgramCourse.isActive = 1 " +
				" order by interviewSelected.interviewProgramCourse.sequence ";
				interviewTypeQuery = session.createQuery(interviewTypeHQ);
				interviewTypeQuery.setInteger("applicationId", applicationId);
				interviewTypeQuery.setInteger("roundId", roundId);
				interviewTypeQuery.setInteger("appliedYear", appliedYear);
				interviewTypeQuery.setInteger("programId", programId);
			}else {
				String interviewTypeHQ = " select interviewSelected.interviewProgramCourse.id, " +
				" interviewSelected.interviewProgramCourse.name " +
				" from InterviewSelected interviewSelected " +
				" where interviewSelected.isCardGenerated = 1 " +
				" and interviewSelected.admAppln.id = :applicationId " +
				" and interviewSelected.interviewProgramCourse.sequence > :roundId " +
				" and interviewSelected.interviewProgramCourse.year = :appliedYear " +
				" and interviewSelected.interviewProgramCourse.program.id = :programId " +
				" and interviewSelected.interviewProgramCourse.isActive = 1 " +
				" order by interviewSelected.interviewProgramCourse.sequence ";
				interviewTypeQuery = session.createQuery(interviewTypeHQ);
				interviewTypeQuery.setInteger("applicationId", applicationId);
				interviewTypeQuery.setInteger("roundId", roundId);
				interviewTypeQuery.setInteger("appliedYear", appliedYear);
				interviewTypeQuery.setInteger("programId", programId);
			}
			
			for (Iterator iterator = interviewTypeQuery.iterate(); iterator
					.hasNext();) {
				Object[] row = (Object[]) iterator.next();
				interviewTypesMap.put((Integer) row[0], (String) row[1].toString());
			}
		} catch (Exception e) {
			log.error("Error while getting interview types by program..."+e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		return interviewTypesMap;
	}

	/* (non-Javadoc)
	 * @see com.brio.cms.transactions.admission.IInterviewResultsEntryTransaction#getInterviewStatus()
	 */
	@Override
	public Map<Integer, String> getInterviewStatus() throws Exception {
		Map<Integer, String> interviewStatusMap = new HashMap<Integer, String>();
		Session session = null;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();

			String interviewTypeHQ = "select id, name from InterviewStatus interviewStatus" +
					" where interviewStatus.isActive = 1";
			Query interviewTypeQuery = session.createQuery(interviewTypeHQ);

			Iterator iterator = interviewTypeQuery.iterate();
			while (iterator.hasNext()) {
				Object[] row = (Object[]) iterator.next();
				
				interviewStatusMap.put((Integer) row[0], (String) row[1].toString());
			}
		} catch (Exception e) {
			log.error("Error while getting interview status..." + e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		return interviewStatusMap;
	}
	
	/* (non-Javadoc)
	 * @see com.brio.cms.transactions.admission.IInterviewResultsEntryTransaction#addInterviewResult(java.util.List, com.brio.cms.bo.admin.InterviewResult)
	 */
	@Override
	public boolean addInterviewResult(List<ApplnDoc> uploadList, InterviewResult interviewResult) throws Exception {
		Session session = null;
		Transaction transaction = null;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session  = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();

			if (uploadList != null) {
				Iterator iterator = uploadList.iterator();
				while (iterator.hasNext()) {
					ApplnDoc applnDoc = (ApplnDoc) iterator.next();
					// update the table only if uploaded document is not null
					if (applnDoc.getDocument() != null) {
						session.saveOrUpdate(applnDoc);
					} else {
						// condition to update isVerified field if the document is not uploaded 
						String interviewTypeHQ = "update ApplnDoc set isVerified = "
								+ applnDoc.getIsVerified()
								+ " where id = "
								+ applnDoc.getId();
						Query query = session.createQuery(interviewTypeHQ);
						query.executeUpdate();
					}
				}
			}
			if(interviewResult.getSelectedPreference() != 0){
				String updatePreferenceHQ = "update AdmAppln admAppln set admAppln.courseBySelectedCourseId.id  = "
					+ interviewResult.getSelectedPreference()
					+ " where id = "
					+ interviewResult.getAdmAppln().getId();
			Query query = session.createQuery(updatePreferenceHQ);
			query.executeUpdate();
			}
			session.save(interviewResult);
			transaction.commit();
		} catch (ConstraintViolationException e) {
			if (transaction != null) {
				transaction.rollback();
			}
			log.error("Error while saving interview result data.."+e);
			throw new BusinessException(e);
		}catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			log.error("Error while saving interview result data ..."+e);
			throw new ApplicationException(e);
		}finally{
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see com.brio.cms.transactions.admission.IInterviewResultsEntryTransaction#addInterviewResult(java.util.List, com.brio.cms.bo.admin.InterviewResult, com.brio.cms.bo.admin.ApplicantRecommendor)
	 */
	@Override
	public boolean addInterviewResult(List<ApplnDoc> uploadList, InterviewResult interviewResult, ApplicantRecommendor applicantRecommendor) throws Exception {
		Session session = null;
		Transaction transaction = null;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();

			if (uploadList != null) {
				Iterator iterator = uploadList.iterator();
				while (iterator.hasNext()) {
					ApplnDoc applnDoc = (ApplnDoc) iterator.next();
					// update the table only if uploaded document is not null
					if (applnDoc.getDocument() != null) {
						session.saveOrUpdate(applnDoc);
					} else {
						// condition to update isVerified field if the document is not uploaded 
						String interviewTypeHQ = "update ApplnDoc set isVerified = "
								+ applnDoc.getIsVerified()
								+ " where id = "
								+ applnDoc.getId();
						Query query = session.createQuery(interviewTypeHQ);
						query.executeUpdate();
					}
				}
			}
			if(interviewResult.getSelectedPreference() != 0){
				String updatePreferenceHQ = "update AdmAppln admAppln set admAppln.courseBySelectedCourseId.id  = "
					+ interviewResult.getSelectedPreference()
					+ " where id = "
					+ interviewResult.getAdmAppln().getId();
			Query query = session.createQuery(updatePreferenceHQ);
			query.executeUpdate();
			}
			session.save(interviewResult);
			session.save(applicantRecommendor);
			transaction.commit();
		} catch (ConstraintViolationException e) {
			if (transaction != null) {
				transaction.rollback();
			}
			log.error("Error while saving interview result data.."+e);
			throw new BusinessException(e);
		}catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			log.error("Error while saving interview result data ..."+e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see com.brio.cms.transactions.admission.IInterviewResultsEntryTransaction#getReferredBy()
	 */
	@Override
	public Map<Integer, String> getReferredBy() throws Exception {
		Map<Integer, String> referredByMap = new HashMap<Integer, String>();
		Session session = null;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();

			String referredByHQ = "select id, name from Recommendor where isActive = 1";
			Query referredByQuery = session.createQuery(referredByHQ);

			Iterator iterator = referredByQuery.iterate();
			while (iterator.hasNext()) {
				Object[] row = (Object[]) iterator.next();
			
				referredByMap.put((Integer) row[0], (String) row[1].toString());
			}
		} catch (Exception e) {
			log.error("Error while getting referred by..."+e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		return referredByMap;
	}

	/* (non-Javadoc)
	 * @see com.brio.cms.transactions.admission.IInterviewResultsEntryTransaction#getGrades()
	 */
	@Override
	public Map<Integer, String> getGrades() throws Exception {
		Map<Integer, String> gradesMap = new HashMap<Integer, String>();
		Session session = null;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();

			String referredByHQ = "select id, grade from Grade where isActive = 1";
			Query referredByQuery = session.createQuery(referredByHQ);

			Iterator iterator = referredByQuery.iterate();
			while (iterator.hasNext()) {
				Object[] row = (Object[]) iterator.next();

				gradesMap.put((Integer) row[0], (String) row[1]);
			}
		} catch (Exception e) {
			log.error("Error while getting grades..."+e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		return gradesMap;
	}


	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.IInterviewResultsEntryTransaction#getApplicantDetailsByPreference(java.lang.String, int, int)
	 */
	@Override
	public AdmAppln getApplicantDetailsByPreference(String applicationNumber,
			int applicationYear, int courseId) throws ApplicationException {
		Session session = null;
		AdmAppln applicantDetails = null;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			applicantDetails = (AdmAppln) session.createQuery(
					"from AdmAppln a where a.applnNo=" + applicationNumber
							+ " and a.appliedYear=" + applicationYear
							+ " and a.courseBySelectedCourseId.id=" + courseId)
					.uniqueResult();
		} catch (Exception e) {
			log.error("Error while getting applicant details..." + e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		return applicantDetails;
	}


	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.IInterviewResultsEntryTransaction#getSelectedCourseId(java.lang.String, int)
	 */
	@Override
	public int getSelectedCourseId(String applicationNumber,
			int applicationYear) throws ApplicationException {

		Session session = null;
		AdmAppln applicantDetails = null;
		int selectedCourseId ;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			applicantDetails = (AdmAppln) session.createQuery(
					"from AdmAppln a where a.applnNo=" + applicationNumber
							+ " and a.appliedYear=" + applicationYear).uniqueResult();
			selectedCourseId = applicantDetails.getCourseBySelectedCourseId().getId();
		} catch (Exception e) {
			log.error("Error while getting applicant details..." + e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		return selectedCourseId;
	}


	@Override
	public int getInterviewersPerPanel(int mainroundId, int subroundId)
			throws Exception {
		Session session = null;
		InterviewProgramCourse interviewProgramCourse = null;
		InterviewSubRounds interviewSubRounds = null;
		int interviewersPerPanel = 0;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			if(subroundId != 0){
				interviewSubRounds = (InterviewSubRounds) session.createQuery("from InterviewSubRounds interviewSubRounds where interviewSubRounds.id = " + subroundId + " and interviewSubRounds.isActive = 1").uniqueResult();
				interviewersPerPanel = interviewSubRounds.getNoOfInterviewsPerPanel().intValue();
			}else if(mainroundId != 0){
				interviewProgramCourse = (InterviewProgramCourse) session.createQuery("from InterviewProgramCourse interviewProgramCourse where interviewProgramCourse.id = " + mainroundId + " and interviewProgramCourse.isActive = 1").uniqueResult();
				interviewersPerPanel = interviewProgramCourse.getNoOfInterviewsPerPanel().intValue();
			}
			
		} catch (Exception e) {
			log.error("Error while getting applicant details..." + e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		return interviewersPerPanel;
	}	
}