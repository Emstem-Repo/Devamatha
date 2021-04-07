package com.kp.cms.transactionsimpl.admission;

import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.bo.admin.Course;
import com.kp.cms.bo.admin.PersonalData;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.admission.InterviewResultEntryForm;
import com.kp.cms.transactions.admission.IFinalMeritListSearchTransaction;
import com.kp.cms.utilities.HibernateUtil;


public class FinalMeritListTransactionImpl implements
		IFinalMeritListSearchTransaction {
	
	private static final Log log = LogFactory.getLog(FinalMeritListTransactionImpl.class);

	/** 
	 * @see com.kp.cms.transactions.admission.IFinalMeritListSearchTransaction#getFinalMeritListSearch(com.kp.cms.forms.admission.FinalMeritListForm, java.lang.Boolean, java.lang.Boolean)
	 */
	@Override
	public List<PersonalData> getFinalMeritListSearch(String finalMeritListqQuery) throws Exception {
		log.info("entering into getFinalMeritListSearch of FinalMeritListTransactionImpl class");
		Session session = null;
		List<PersonalData> meritListSearchResult = null;
		try {

			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			meritListSearchResult = session.createQuery(finalMeritListqQuery).list();
		} catch (Exception e) {
			log.error("error in getFinalMeritListSearch",e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		log.info("entering into getFinalMeritListSearch of FinalMeritListTransactionImpl class");
		return meritListSearchResult;
	}
	
	/** 
	 * @see com.kp.cms.transactions.admission.IFinalMeritListSearchTransaction#getMaxIntakeFromCourse(int)
	 */
	public int getMaxIntakeFromCourse(int courseId) throws ApplicationException {
		log.info("entering into getMaxIntakeFromCourse of FinalMeritListTransactionImpl class");
		Session session = null;
		int maxIntake = 0;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			String courseIntake = " select course.maxIntake from Course course where course.id =  :courseId and course.isActive = 1";
			Query courseQuery = session.createQuery(courseIntake);
			courseQuery.setInteger("courseId", courseId);
			maxIntake = (Integer) courseQuery.uniqueResult();
		} catch (Exception e) {
			log.error("error in getMaxIntakeFromCourse",e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		log.info("entering into getMaxIntakeFromCourse of FinalMeritListTransactionImpl class");
		return maxIntake;

	}
	
	/** 
	 * @see com.kp.cms.transactions.admission.IFinalMeritListSearchTransaction#getMaxIntakeFromCourse(int)
	 */
	public Map<Integer, Integer> getMaxIntakeFromProgram(int programId) throws ApplicationException {
		log.info("entering into getMaxIntakeFromCourse of FinalMeritListTransactionImpl class");
		Session session = null;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			String programIntake = " from Course course " +
					" where course.program.id = :programId" +
					" and course.program.isActive = 1";
			Query courseQuery = session.createQuery(programIntake);
			courseQuery.setInteger("programId", programId);
			
			Map<Integer,Integer> courseIntakeMap = new LinkedHashMap<Integer, Integer>();
			 Iterator <Course> courseIntakeIterator = courseQuery.iterate();
			 
			 while (courseIntakeIterator.hasNext()) {
				Course courseIntake = (Course) courseIntakeIterator.next();
				courseIntakeMap.put(Integer.valueOf(courseIntake.getId()), courseIntake.getMaxIntake());
			}
			 
			 return courseIntakeMap;
		} catch (Exception e) {
			log.error("error in getMaxIntakeFromCourse",e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
	}

	/** 
	 * @param isApproved 
	 * @see com.kp.cms.transactions.admission.IFinalMeritListSearchTransaction#updateSelectCandidates(java.lang.String[], boolean, java.lang.String)
	 */
	@Override
	public void updateSelectCandidates(String[] selectedCandidates,
			boolean isSelected, boolean isApproved, String userId,String mode) throws Exception {
		log.info("entering into updateSelectCandidates of FinalMeritListTransactionImpl class");
		Session session = null;
		Transaction transaction = null;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			for (String selectedCandidate : selectedCandidates) {
				AdmAppln admAppln = (AdmAppln) session.get(AdmAppln.class,
						Integer.valueOf(selectedCandidate));
				admAppln.setIsSelected(isSelected);
				admAppln.setIsFinalMeritApproved(isApproved);
				admAppln.setLastModifiedDate(new Date());
				admAppln.setModifiedBy(userId);
				if(mode.equalsIgnoreCase("update"))
					admAppln.setFinalMeritListApproveDate(new Date());
				else
					admAppln.setFinalMeritListApproveDate(null);
				
				session.update(admAppln);
			}
			transaction.commit();
			
		} catch (Exception e) {
			
			if (transaction != null) {
				transaction.rollback();
			}
				
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		log.info("entering into updateSelectCandidates of FinalMeritListTransactionImpl class");
	}
	
	
	/** 
	 * @see com.kp.cms.transactions.admission.IFinalMeritListSearchTransaction#updateSelectCandidates(java.lang.String[], boolean, java.lang.String)
	 */
	@Override
	public void approveSelectCandidates(String[] selectedCandidates,
			boolean isApproved, String userId) throws Exception {
		log.info("entering into approveSelectCandidates of FinalMeritListTransactionImpl class");
		Session session = null;
		Transaction transaction = null;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			for (String selectedCandidate : selectedCandidates) {
				AdmAppln admAppln = (AdmAppln) session.get(AdmAppln.class,
						Integer.valueOf(selectedCandidate));
				admAppln.setIsFinalMeritApproved(true);
				admAppln.setLastModifiedDate(new Date());
				admAppln.setModifiedBy(userId);
				admAppln.setAdmStatus(null);
				admAppln.setFinalMeritListApproveDate(new Date());
				session.update(admAppln);
			}
			transaction.commit();
			
		} catch (Exception e) {
			
			if (transaction != null) {
				transaction.rollback();
			}
				
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		log.info("entering into updateSelectCandidates of FinalMeritListTransactionImpl class");
	}

	/** 
	 * @see com.kp.cms.transactions.admission.IFinalMeritListSearchTransaction#updateSelectedPreference(com.kp.cms.forms.admission.InterviewResultEntryForm)
	 */
	@Override
	public void updateSelectedPreference(
			InterviewResultEntryForm interviewResultEntryForm)
			throws ApplicationException {
		log.info("entering into updateSelectedPreference of FinalMeritListTransactionImpl class");
		Session session = null;
		Transaction transaction = null;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
		
				AdmAppln admAppln = (AdmAppln) session.get(AdmAppln.class,
						interviewResultEntryForm.getApplicantDetails().getApplicationId());
				if(interviewResultEntryForm.getSelectedPrefId()!=null){
					Course course = new Course();
					course.setId(Integer.valueOf(interviewResultEntryForm.getSelectedPrefId()));
					admAppln.setCourseBySelectedCourseId(course);
					admAppln.setIsPreferenceUpdated(true);
				}
				admAppln.setLastModifiedDate(new Date());
				admAppln.setModifiedBy(interviewResultEntryForm.getUserId());
				if(interviewResultEntryForm.getApplicantDetails()!=null){
				if(interviewResultEntryForm.getApplicantDetails().getVerifiedBy()!=null){
					admAppln.setVerifiedBy(interviewResultEntryForm.getApplicantDetails().getVerifiedBy());
				}
				}
				session.update(admAppln);
			
			transaction.commit();
			
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		log.info("entering into updateSelectedPreference of FinalMeritListTransactionImpl class");
	}
}