package com.kp.cms.transactionsimpl.admission;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.InterviewProgramCourse;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.transactions.admission.IInterviewDefinitionTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class InterviewDefinitionTransactionImpl implements
		IInterviewDefinitionTransaction {

	private static final Log log = LogFactory.getLog(InterviewDefinitionTransactionImpl.class);

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.IInterviewDefinitionTransaction#getInterviewDefinition(int)
	 */
	@Override
	public List<InterviewProgramCourse> getInterviewDefinition(int id)
			throws Exception {
		Session session = null;
		List<InterviewProgramCourse> interviewDefinitionList = null;

		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			if (id != 0) {
				Query query = session.createQuery("from InterviewProgramCourse interviewProgramCourse where interviewProgramCourse.id = :id and interviewProgramCourse.isActive = 1");
				query.setInteger("id", id);
				interviewDefinitionList = query.list();
			} else {
				Query query = session.createQuery("from InterviewProgramCourse interviewProgramCourse where interviewProgramCourse.isActive = 1");
				interviewDefinitionList = query.list();
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
		return interviewDefinitionList;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.IInterviewDefinitionTransaction#addInterviewDefinition(com.kp.cms.bo.admin.InterviewProgramCourse, java.lang.String, java.lang.Boolean)
	 */
	@Override
	public boolean addInterviewDefinition(InterviewProgramCourse interviewProgramCourse, String mode, Boolean originalNotChanged) throws DuplicateException, Exception {
		Session session = null;
		Transaction transaction = null;
		boolean result = false;
		try {
			//session = HibernateUtil.getSessionFactory().openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			if (mode.equalsIgnoreCase("Add")) {
				session.save(interviewProgramCourse);
			}else{
				session.update(interviewProgramCourse);
			}
			transaction.commit();
			result = true;
		} catch (ConstraintViolationException e) {
			log.error("Error during saving Interview Definition..." + e);
			if (transaction != null) {
				transaction.rollback();
			}
			throw new BusinessException(e);
		} catch (Exception e) {
			log.error("Error during saving Interview Definition..." + e);
			if (transaction != null) {
				transaction.rollback();
			}
			throw new ApplicationException(e);
		} finally {
			if ( session!= null){
				session.flush();
				session.close();
			}
		}
		return result;
	}
	
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.IInterviewDefinitionTransaction#deleteInterviewDefinition(int, java.lang.Boolean, java.lang.String)
	 */
	@Override
	public boolean deleteInterviewDefinition(int id, Boolean activate, String userId) throws Exception {
		Session session = null;
		Transaction transaction = null;
		boolean result = false;
		try {
			//session = HibernateUtil.getSessionFactory().openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			InterviewProgramCourse interviewProgramCourse = (InterviewProgramCourse) session.get(InterviewProgramCourse.class, id);
			if (activate) {
				interviewProgramCourse.setIsActive(true);
				interviewProgramCourse.setLastModifiedDate(new Date());
				interviewProgramCourse.setModifiedBy(userId);
			} else {
				interviewProgramCourse.setIsActive(false);
				interviewProgramCourse.setLastModifiedDate(new Date());
				interviewProgramCourse.setModifiedBy(userId);
			}
			session.update(interviewProgramCourse);
			transaction.commit();
			result = true;
		} catch (ConstraintViolationException e) {
			log.error("Error while deleting Interview Definition entry..." + e);
			if (transaction != null){
				transaction.rollback();
			}
			throw new BusinessException(e);
		} catch (Exception e) {
			log.error("Error while deleting Interview Definition entry..." + e);
			if (transaction != null){
				transaction.rollback();
			}
			throw new ApplicationException(e);
		} finally {
			if ( session!= null){
				session.flush();
				session.close();
			}
		}
		return result;
	}
	
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.IInterviewDefinitionTransaction#isInterviewDefinitionDuplicated(com.kp.cms.bo.admin.InterviewProgramCourse)
	 */
	public InterviewProgramCourse isInterviewDefinitionDuplicated(
			InterviewProgramCourse oldInterviewProgramCourse) throws Exception {
		Session session = null;
		InterviewProgramCourse interviewProgramCourse;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from InterviewProgramCourse interviewProgramCourse where interviewProgramCourse.course.id = :courseId and interviewProgramCourse.sequence = :sequence and interviewProgramCourse.year = :year");
			query.setInteger("courseId", oldInterviewProgramCourse.getCourse().getId());
			query.setInteger("sequence", Integer.parseInt(oldInterviewProgramCourse.getSequence()));
			query.setInteger("year", oldInterviewProgramCourse.getYear());
			interviewProgramCourse = (InterviewProgramCourse) query.uniqueResult();
			
		} catch (Exception e) {
			log.error("Error during duplcation checking..." + e);
			throw new ApplicationException(e);
		} finally{
			if( session!= null){
				session.flush();
				//session.close();
			}
		}
		return interviewProgramCourse;
	}
	/**
	 * 
	 * @param id
	 * @param year
	 * @return
	 */
	public List<InterviewProgramCourse> getInterviewTypebyProgram(int id, int year) {
		Session session = null;
		List<InterviewProgramCourse> intProgramCourseList = new ArrayList<InterviewProgramCourse>();
		try {

			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			String que="from InterviewProgramCourse where ";
			if(id>0){
			que=que	+"program.id = :programId and ";
			}
			que=que	+"year= :year and isActive = true order by sequence";

			Query query = session
					.createQuery(que);
			if(id>0){
				query.setInteger("programId", id);
			}
			query.setInteger("year", year);

			intProgramCourseList = query.list();

			return intProgramCourseList;
		} catch (Exception e) {
			log.debug("Exception" + e.getMessage());
		} finally {
			//if (session != null)
				//session.close();
		}

		return intProgramCourseList;
	}

	@Override
	public List<InterviewProgramCourse> getInterviewDefinitionList(int year)
			throws Exception {
		Session session = null;
		List<InterviewProgramCourse> interviewDefinitionList;

		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from InterviewProgramCourse interviewProgramCourse where interviewProgramCourse.isActive = 1 and interviewProgramCourse.year="+year);
			interviewDefinitionList = query.list();
		} catch (Exception e) {
			log.error("Error while getting applicant details..." + e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		return interviewDefinitionList;
	}


}