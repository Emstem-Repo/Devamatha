package com.kp.cms.transactionsimpl.admission;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.InterviewProgramCourse;
import com.kp.cms.bo.admin.InterviewSubRounds;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.transactions.admission.IInterviewSubroundsTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class InterviewSubroundsTransactionImpl implements
		IInterviewSubroundsTransaction {
	
	private static final Log log = LogFactory.getLog(InterviewSubroundsTransactionImpl.class);

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.IInterviewSubroundsTransaction#getInterviewSubrounds(int)
	 */
	@Override
	public List<InterviewSubRounds> getInterviewSubrounds(int id)
			throws Exception {
		Session session = null;
		List<InterviewSubRounds> interviewSubroundsList = null;

		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			if (id != 0) {
				Query query = session
						.createQuery("from InterviewSubRounds interviewSubRounds where interviewSubRounds.id = :id and interviewSubRounds.isActive = 1");
				query.setInteger("id", id);
				interviewSubroundsList = query.list();
			} else {
				Query query = session
						.createQuery("from InterviewSubRounds interviewSubRounds where interviewSubRounds.isActive = 1");
				interviewSubroundsList = query.list();
			}
		} catch (Exception e) {
			log.error("Error while getting interview subrounds..." + e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		return interviewSubroundsList;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.IInterviewSubroundsTransaction#addInterviewSubround(com.kp.cms.bo.admin.InterviewSubRounds, java.lang.String, java.lang.Boolean)
	 */
	@Override
	public boolean addInterviewSubround(InterviewSubRounds interviewSubrounds,
			String mode, Boolean originalChangedNotChanged)
			throws DuplicateException, Exception {
		Session session = null;
		Transaction transaction = null;
		boolean result = false;
		try {
			//session = HibernateUtil.getSessionFactory().openSession();
			session = HibernateUtil.getSession();
			
			transaction = session.beginTransaction();
			transaction.begin();
			if (mode.equalsIgnoreCase("Add")) {
				session.save(interviewSubrounds);
			}else{
				session.update(interviewSubrounds);
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
	 * @see com.kp.cms.transactions.admission.IInterviewSubroundsTransaction#deleteInterviewSubround(int, java.lang.Boolean, java.lang.String)
	 */
	@Override
	public boolean deleteInterviewSubround(int id, Boolean activate,
			String userId) throws Exception {
		Session session = null;
		Transaction transaction = null;
		boolean result = false;
		try {
			//session = HibernateUtil.getSessionFactory().openSession();
			session = HibernateUtil.getSession();

			transaction = session.beginTransaction();
			transaction.begin();
			InterviewSubRounds interviewSubRounds = (InterviewSubRounds) session.get(InterviewSubRounds.class, id);
			if (activate) {
				interviewSubRounds.setIsActive(true);
				interviewSubRounds.setLastModifiedDate(new Date());
				interviewSubRounds.setModifiedBy(userId);
			} else {
				interviewSubRounds.setIsActive(false);
				interviewSubRounds.setLastModifiedDate(new Date());
				interviewSubRounds.setModifiedBy(userId);
			}
			session.update(interviewSubRounds);
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
	 * @see com.kp.cms.transactions.admission.IInterviewSubroundsTransaction#isInterviewSubroundDuplicated(com.kp.cms.bo.admin.InterviewSubRounds)
	 */
	@Override
	public InterviewSubRounds isInterviewSubroundDuplicated(
			InterviewSubRounds oldInterviewSubrounds) throws Exception {
		Session session = null;
		InterviewSubRounds interviewSubRounds;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();

			Query query = session.createQuery("from InterviewSubRounds interviewSubRounds where interviewSubRounds.interviewProgramCourse.id = :interviewTypeId and interviewSubRounds.name = :subroundName");
			query.setInteger("interviewTypeId", oldInterviewSubrounds.getInterviewProgramCourse().getId());
			query.setString("subroundName", oldInterviewSubrounds.getName());
			interviewSubRounds = (InterviewSubRounds) query.uniqueResult();
			
		} catch (Exception e) {
			log.error("Error during duplcation checking..." + e);
			throw new ApplicationException(e);
		} finally{
			if( session!= null){
				session.flush();
				//session.close();
			}
		}
		return interviewSubRounds;
	}

	@Override
	public boolean copyInterviewDefinition(List<InterviewSubRounds> intSubRounds)
			throws Exception {

		Session session = null;
		Transaction transaction = null;
		try{
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			Iterator<InterviewSubRounds> interviewList = intSubRounds.iterator();
			while (interviewList.hasNext()) {
				InterviewSubRounds iPC = (InterviewSubRounds) interviewList.next();
				session.save(iPC);
			}
			transaction.commit();
			session.flush();
		}catch (Exception e) {
			if(transaction!=null){
				transaction.rollback();
				throw new ApplicationException(e);
			}
		}finally{
			if(session!=null){
				session.flush();
			}
		}
		return true;
		
	
	}

	public boolean checkDuplicate(String query) throws Exception {

		Session session = null;
		List<InterviewSubRounds> intSubRounds=null;
		try{
			session = HibernateUtil.getSession();
			intSubRounds=session.createQuery(query).list();
			if(intSubRounds!=null && !intSubRounds.isEmpty()){
				return true;
			}
			return false;
		}catch (Exception e) {
			throw new ApplicationException(e);
		}finally{
			if(session!=null){
				session.flush();
			}
		}
	
	}

	@Override
	public String getInterviewProgramCourseId(String courseId, int year,String sequence) throws Exception {
		Session session = null;
		Integer id;
		try{
			session = HibernateUtil.getSession();
			String query=" select interviewProgramCourse.id from InterviewProgramCourse interviewProgramCourse where interviewProgramCourse.course.id = " +courseId+
								" and interviewProgramCourse.sequence = "+sequence+" and interviewProgramCourse.year = "+year;
			Query q=session.createQuery(query);
			id=(Integer)q.uniqueResult();
			if(id!=null && id!=0){
			return String.valueOf(id);
			}
			else
				return null;
		}catch (Exception e) {
			throw new ApplicationException(e);
		}finally{
			if(session!=null){
				session.flush();
			}
		}
	
	
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.IInterviewSubroundsTransaction#getInterviewSubroundsList(int)
	 */
	@Override
	public List<InterviewSubRounds> getInterviewSubroundsList(int year)
			throws Exception {
		Session session = null;
		List<InterviewSubRounds> interviewSubroundsList;

		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from InterviewSubRounds interviewSubRounds where interviewSubRounds.isActive = 1 and interviewSubRounds.interviewProgramCourse.year="+year);
			interviewSubroundsList = query.list();
		} catch (Exception e) {
			log.error("Error while getting interview subrounds..." + e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		return interviewSubroundsList;
	}


}