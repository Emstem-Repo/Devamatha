package com.kp.cms.transactionsimpl.admission;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.bo.admin.InterviewProgramCourse;
import com.kp.cms.bo.admin.InterviewSelected;
import com.kp.cms.bo.admin.PersonalData;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.admission.StudentSearchForm;
import com.kp.cms.handlers.exam.NewSecuredMarksEntryHandler;
import com.kp.cms.transactions.admission.IStudentSearchTransaction;
import com.kp.cms.utilities.HibernateUtil;

/**
 * An implementation class for the Interview selection transaction.
 *
 */
public class StudentSearchTransactionImpl implements IStudentSearchTransaction {

	private static Log log = LogFactory
			.getLog(StudentSearchTransactionImpl.class);

	/** 
	 * @see
	 * com.kp.cms.transactions.admission.IStudentSearchTransaction#getStudentSearch
	 * (java.lang.String)
	 */
	@Override
	public List<PersonalData> getStudentSearch(String searchCriteria)
			throws ApplicationException {
		log.info("entering of getStudentSearch in StudentSearchTransactionImpl class.");
		Session session = null;
		List<PersonalData> studentSearchResult = null;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			studentSearchResult = session.createQuery(searchCriteria).list();

		} catch (Exception e) {

			log.error("error while getting the student search results  " ,e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
//				session.close();
			}
		}
		log.info("exit of getStudentSearch in StudentSearchTransactionImpl class.");
		return studentSearchResult;
	}

	/** @see com.kp.cms.transactions.admission.IStudentSearchTransaction#
	 * updateSelectedCandidatesList(java.lang.String[], java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public void updateSelectedCandidatesList(String[] selectedCandidates,
			Map<Integer, String> studentInterviewMap, String userId) throws Exception {
		log.info("entering of updateSelectedCandidatesList in StudentSearchTransactionImpl class.");
		Session session = null;
		Transaction transaction = null;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			for (String selectedCandidate : selectedCandidates) {
				InterviewSelected interViewSelected = new InterviewSelected();
				AdmAppln admAppln = new AdmAppln();
				admAppln.setId(Integer.valueOf(selectedCandidate.trim()));
				interViewSelected.setAdmAppln(admAppln);

				InterviewProgramCourse interviewProgramCourse = new InterviewProgramCourse();

				interviewProgramCourse.setId(Integer.parseInt(studentInterviewMap.get(admAppln.getId())));
				interViewSelected
						.setInterviewProgramCourse(interviewProgramCourse);
				interViewSelected.setCreatedDate(new Date());
				interViewSelected.setIsCardGenerated(false);
				interViewSelected.setCreatedBy(userId);
				session.save(interViewSelected);
				String sequence=NewSecuredMarksEntryHandler.getInstance().getPropertyValue(Integer.parseInt(studentInterviewMap.get(Integer.valueOf(selectedCandidate.trim()))),"InterviewProgramCourse",true,"sequence");
				AdmAppln admAppln2=(AdmAppln)session.get(AdmAppln.class, Integer.valueOf(selectedCandidate.trim()));
				if(admAppln2.getInterScheduleSelection()!=null && admAppln2.getInterScheduleSelection().getId()>0){
					admAppln2.setAdmStatus(null);
				}else
				{
					if(sequence!=null && sequence.equalsIgnoreCase("1")){
						admAppln2.setAdmStatus(CMSConstants.COMMON_MSG_ADM_STATUS);
					}else{
						admAppln2.setAdmStatus(null);
					}
				}
				admAppln2.setModifiedBy(userId);
				admAppln2.setLastModifiedDate(new Date());
				session.update(admAppln2);	
			}
			transaction.commit();
		} catch (Exception e) {
			log.error("error while getting the student search results  " , e);
			if (transaction != null) {
				transaction.rollback();
			}
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
		     	session.close();
			}

		}
		log.info("exit of updateSelectedCandidatesList in StudentSearchTransactionImpl class.");
	}

	/** @see com.kp.cms.transactions.admission.IStudentSearchTransaction#
	 * getSelectedStudents(java.lang.String)
	 */
	@Override
	public List getSelectedStudents(String searchCriteria) throws Exception {
		log.info("entering of getSelectedStudents in StudentSearchTransactionImpl class.");
		Session session = null;
		List<PersonalData> studentSearchResult = null;
		try {

			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			studentSearchResult = session.createQuery(searchCriteria).list();

		} catch (Exception e) {
			log.error("error while getting the student search results  " , e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
//				session.close();
			}
		}
		log.info("exit of getSelectedStudents in StudentSearchTransactionImpl class.");
		return studentSearchResult;

	}

	/** @see com.kp.cms.transactions.admission.IStudentSearchTransaction#
	 * removeSelectedCandidate(java.lang.String[])
	 */
	@Override
	public void removeSelectedCandidate(String[] selectedCandidates)
			throws Exception {
		log.info("entering of removeSelectedCandidate in StudentSearchTransactionImpl class.");
		Session session = null;
		Transaction transaction = null;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			for (String selectedCandidate : selectedCandidates) {
				if(selectedCandidate!=null){
					InterviewSelected interViewSelected = (InterviewSelected) session
					.get(InterviewSelected.class, Integer
							.valueOf(selectedCandidate));
					String sequence=NewSecuredMarksEntryHandler.getInstance().getPropertyValue(interViewSelected.getInterviewProgramCourse().getId(),"InterviewProgramCourse",true,"sequence");
					if(sequence!=null && sequence.equalsIgnoreCase("1")){
						AdmAppln admAppln=interViewSelected.getAdmAppln();
						admAppln.setAdmStatus(null);
						admAppln.setLastModifiedDate(new Date());
						session.update(admAppln);
					}
					session.delete(interViewSelected);
				}
			}
			transaction.commit();
		} catch (Exception e) {
			log.error("error while getting the student search results  " , e);
			if (transaction != null) {
				transaction.rollback();
			}
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
//				session.close();
			}

		}
		log.info("exit of removeSelectedCandidate in StudentSearchTransactionImpl class.");
	}

	/** 
	 * @see com.kp.cms.transactions.admission.IStudentSearchTransaction#updateBypassInterviewCandidatesList(com.kp.cms.forms.admission.StudentSearchForm, boolean)
	 */
	@Override
	public void updateBypassInterviewCandidatesList(
			StudentSearchForm studentSearchForm, boolean isSelected)
			throws ApplicationException {
		log.info("entering of updateBypassInterviewCandidatesList in StudentSearchTransactionImpl class.");
		Session session = null;
		Transaction transaction = null;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			for (String selectedCandidate : studentSearchForm
					.getSelectedCandidates()) {
				AdmAppln admAppln = (AdmAppln) session.get(AdmAppln.class,
						Integer.valueOf(selectedCandidate));
				admAppln.setIsSelected(isSelected);
				admAppln.setIsBypassed(isSelected);
				admAppln.setAdmStatus(null);
				admAppln.setIsFinalMeritApproved(!studentSearchForm.isNeedApproval());
				admAppln.setLastModifiedDate(new Date());
				admAppln.setModifiedBy(studentSearchForm.getUserId());
				session.update(admAppln);
			}
			transaction.commit();

		} catch (Exception e) {
			log.error("error in updateBypassInterviewCandidatesList " ,e );
			if (transaction != null) {
				transaction.rollback();
			}
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		log.info("exit of updateBypassInterviewCandidatesList in StudentSearchTransactionImpl class.");
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.IStudentSearchTransaction#getpreviousRoundId(java.lang.String)
	 */
	@Override
	public int getpreviousRoundId(String currentRoundId) throws Exception {
		int previousId=0;
		log.info("entering of getpreviousRoundId in StudentSearchTransactionImpl class.");
		Session session = null;
		
		try {

			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			InterviewProgramCourse roundObj=(InterviewProgramCourse)session.get(InterviewProgramCourse.class, Integer.parseInt(currentRoundId));
			if(roundObj.getSequence()!=null && !StringUtils.isEmpty(roundObj.getSequence())
					&& StringUtils.isNumeric(roundObj.getSequence()) && Integer.parseInt(roundObj.getSequence())>1){
				String sqlQr="from InterviewProgramCourse pc " +
				"where pc.sequence = :SequenceID and pc.course.id=:CourseID and pc.year=:YEAR";
				
//			String sqlQr="select pc.sequence from InterviewProgramCourse pc where " +
//					":SequenceID =(select count(distinct pc1.sequence) from " +
//					"InterviewProgramCourse pc1 where pc1.sequence > pc.sequence) and pc.course.id=:CourseID and pc.year=:YEAR";
			Query query=session.createQuery(sqlQr);
			query.setInteger("SequenceID", (Integer.parseInt(roundObj.getSequence())-1));
			query.setInteger("CourseID", (roundObj.getCourse().getId()));
			query.setInteger("YEAR", (roundObj.getYear()));
			
			InterviewProgramCourse prevSeq=(InterviewProgramCourse)query.uniqueResult();
			if(prevSeq!=null)
				previousId=prevSeq.getId();
			
			}
			
		} catch (Exception e) {
			log.error("error while getting theprevious round id  " , e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
//				session.close();
			}
		}
		log.info("exit of getpreviousRoundId in StudentSearchTransactionImpl class.");
		return previousId;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.IStudentSearchTransaction#getAdmAppln(java.lang.String)
	 */
	@Override
	public AdmAppln getAdmAppln(String selectedCandidate) throws Exception {
		log.info("entering of getStudentSearch in StudentSearchTransactionImpl class.");
		Session session = null;
		AdmAppln admAppln=null;
		try {
			session = HibernateUtil.getSession();
			admAppln =(AdmAppln) session.get(AdmAppln.class, Integer.parseInt(selectedCandidate.trim()));
		} catch (Exception e) {
			log.error("error while getting the student search results  " ,e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		log.info("exit of getStudentSearch in StudentSearchTransactionImpl class.");
		return admAppln;
	}
}