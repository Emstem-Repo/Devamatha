package com.kp.cms.transactionsimpl.admin;

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
import com.kp.cms.bo.admin.TermsConditions;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.to.admin.TermsConditionChecklistTO;
import com.kp.cms.transactions.admin.ITermsConditionTransaction;
import com.kp.cms.utilities.HibernateUtil;

/**
 * 
 * This method returns list of Terms&Condition object @ An
 *         implementation class for ITermsConditionTransaction
 */

public class TermsConditionTransactionImpl implements ITermsConditionTransaction {
	private static final Log log = LogFactory.getLog(TermsConditionTransactionImpl.class);
	public static volatile TermsConditionTransactionImpl termsConditionTransactionImpl = null;

	public static TermsConditionTransactionImpl getInstance() {
		if (termsConditionTransactionImpl == null) {
			termsConditionTransactionImpl = new TermsConditionTransactionImpl();
			return termsConditionTransactionImpl;
		}
		return termsConditionTransactionImpl;
	}

	/**
	 * 
	 * This method returns a termscondition object with all the records
	 * 
	 * @throws ApplicationException
	 */

	public List<TermsConditions> getTermsCondition(int id) throws Exception {
		log.debug("inside getTermsCondition");
		Session session = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			if (id != 0) {
				Query query = session
						.createQuery("from TermsConditions condition where id = :id and isActive=1");
				query.setInteger("id", id);
				List<TermsConditions> list = query.list();
				session.flush();
				//session.close();
				//sessionFactory.close();
				log.debug("leaving getTermsCondition");
				return list;
			} else {
				Query query = session
						.createQuery("from TermsConditions T where isActive=1");
				List<TermsConditions> list = query.list();
				session.flush();
				//session.close();
				//sessionFactory.close();
				log.debug("leaving getTermsCondition");
				return list;
			}

		} catch (Exception e) {
			log.error("Error during getting terms and conditions...", e);
			session.flush();
			//session.close();
			throw new ApplicationException(e);
		}
	}

	/**
	 * getting terms&conditions based on year&id
	 */
	public List<TermsConditions> getTermsConditionForYear(int id, int year) throws Exception {
		log.debug("inside getTermsConditionForYear");
		Session session = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from TermsConditions condition where condition.course.id = :id and condition.year=:year  and condition.isActive=1");
			query.setInteger("id", id);
			query.setInteger("year", year);
			List<TermsConditions> list = query.list();
			session.flush();
			//session.close();
			//sessionFactory.close();
			log.debug("leaving getTermsConditionForYear");
			return list;
		} catch (Exception e) {
			log.error("Error during getting terms and conditions...", e);
			session.flush();
			//session.close();
			throw new ApplicationException(e);
		}
	}

	/**
	 * 
	 * This method adds a record into the termscondition
	 * 
	 * @throws DuplicateException
	 *             , Exception
	 */

	public boolean addTermsCondition(TermsConditions termsConditions, String mode, Boolean originalNotChanged) throws Exception {
		log.debug("inside addTermsCondition");
		Session session = null;
		Transaction transaction = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			transaction.begin();
			if (mode.equalsIgnoreCase("Add")) {
				session.save(termsConditions);
			} else {
				session.update(termsConditions);
			}
			transaction.commit();
			session.flush();
			session.close();
			log.debug("leaving addTermsCondition");
			return true;
		} catch (ConstraintViolationException e) {
			transaction.rollback();
			log.error("Error during saving Terms And Conditions data...", e);
			throw new BusinessException(e);
		} catch (Exception e) {
			transaction.rollback();
			log.error("Error during saving Terms And Conditions data...", e);
			throw new ApplicationException(e);
		}
	}

	/**
	 * delete terms&condition
	 */
	public boolean deleteTermsCondition(int id, Boolean activate, String uId)throws Exception {
		log.debug("inside deleteTermsCondition");
		Session session = null;
		Transaction tx = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			tx.begin();
			TermsConditions termsConditions = (TermsConditions) session.get(TermsConditions.class, id);
			if (activate) {
				termsConditions.setIsActive(true);
			} else {
				termsConditions.setIsActive(false);
			}
			termsConditions.setModifiedBy(uId);
			termsConditions.setLastModifiedDate(new Date());
			session.update(termsConditions);
			tx.commit();
			session.flush();
			session.close();
			log.debug("leaving deleteTermsCondition");
			return true;
		} catch (ConstraintViolationException e) {
			tx.rollback();
			log.error("Error during deleting Terms And Conditions. data..."	, e);
			throw new BusinessException(e);
		} catch (Exception e) {
			tx.rollback();
			log.error("Error during deleting Terms And Conditions data..." + e);
			throw new ApplicationException(e);
		}
	}

	/**
	 * duplication checking for terms&conditions
	 */
	public TermsConditions isTermsAndConditionsDuplcated(TermsConditions oldTermsConditions) throws Exception {
		log.debug("inside isTermsAndConditionsDuplcated");
		Session session = null;
		TermsConditions termsConditions;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from TermsConditions where course.id = :courseId and year = :year");
			query
					.setInteger("courseId", oldTermsConditions.getCourse()
							.getId());
			query.setInteger("year", oldTermsConditions.getYear());
			termsConditions = (TermsConditions) query.uniqueResult();
			session.flush();
			//session.close();
			//sessionFactory.close();
			log.debug("leaving isTermsAndConditionsDuplcated");
			return termsConditions;
		} catch (Exception e) {
			log.error("Error during duplcation checking..." + e);
			session.flush();
			//session.close();
			throw new ApplicationException(e);
		}
	}

	 /**
	  * saving to table
	  */
	public boolean addTermsConditionCheckList(List<TermsConditionChecklist> tcBOList) throws Exception {
		log.debug("inside addTermsConditionCheckList");
		Session session = null;
		Transaction transaction = null;
		TermsConditionChecklist tcLChecklist;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			transaction.begin();
			Iterator<TermsConditionChecklist> tcIterator = tcBOList.iterator();
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

	/**
	 * getting data for ui display 
	 */
	public List<TermsConditionChecklist> getTermsConditionCheckList() throws Exception {
		log.debug("inside getTermsConditionCheckList");
		Session session = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from TermsConditionChecklist tc where tc.isActive=1 group by tc.course.id");
			List<TermsConditionChecklist> list = query.list();
			session.flush();
			//session.close();
			//sessionFactory.close();
			log.debug("leaving getTermsConditionCheckList");
			return list;

		} catch (Exception e) {
			log.error("Error in getTermsConditionCheckList...", e);
			session.flush();
			//session.close();
			throw new ApplicationException(e);
		}
	}	
	/**
	 * getting list of check list based on the course and year for view
	 */
	public List<TermsConditionChecklist> getTermsConditionCheckListWithCourseId(int courseId/*, Integer year*/) throws Exception {
		log.debug("inside getTermsConditionCheckListWithCourseId");
		Session session = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from TermsConditionChecklist tc where tc.isActive=1 and course.id = :courseId ");
				//	" and tc.year = :academicYear");
			query.setInteger("courseId", courseId);
			//query.setInteger("academicYear", year);
			List<TermsConditionChecklist> list = query.list();
			session.flush();
			//session.close();
			//sessionFactory.close();
			log.debug("leaving getTermsConditionCheckList");
			return list;

		} catch (Exception e) {
			log.error("Error in getTermsConditionCheckList...", e);
			session.flush();
			//session.close();
			throw new ApplicationException(e);
		}
	}
	/**
	 * deleting t & c based on course & year
	 */
	public boolean deleteTermsConditionCheckList(int courseId/*, int year*/)throws Exception {
		log.debug("inside deleteTermsConditionCheckList");
		Session session = null;
		Transaction tx = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
		    tx= session.beginTransaction();
			Query query = session.createQuery("delete from TermsConditionChecklist tc where tc.course.id = :courseId" );
					//" and tc.year = :academicYear");
			query.setInteger("courseId", courseId);
			//query.setInteger("academicYear", year);
   		    query.executeUpdate();
			tx.commit();
			session.flush();
			session.close();
			//sessionFactory.close();
			log.debug("leaving deleteTermsCondition");
			return true;
		} catch (ConstraintViolationException e) {
			tx.rollback();
			log.error("Error during deleting Terms And Conditions. data..."	, e);
			throw new BusinessException(e);
		} catch (Exception e) {
			tx.rollback();
			log.error("Error during deleting Terms And Conditions data..." + e);
			throw new ApplicationException(e);
		}
	}

	@Override
	public boolean deleteTermsConditionCheckListById(int id) throws Exception {
		log.debug("inside deleteTermsConditionCheckList");
		Session session = null;
		Transaction tx = null;
		try {
			session = HibernateUtil.getSession();
		    tx= session.beginTransaction();
			Query query = session.createQuery("delete from TermsConditionChecklist tc where tc.id = :id" );
			query.setInteger("id", id);
   		    query.executeUpdate();
			tx.commit();
			session.flush();
			session.close();
			log.debug("leaving deleteTermsCondition");
			return true;
		} catch (ConstraintViolationException e) {
			tx.rollback();
			log.error("Error during deleting Terms And Conditions. data..."	, e);
			throw new BusinessException(e);
		} catch (Exception e) {
			tx.rollback();
			log.error("Error during deleting Terms And Conditions data..." + e);
			throw new ApplicationException(e);
		}
	}

	@Override
	public boolean updateTermsConditionList(
			List<TermsConditionChecklistTO> termsConditionList,String userid)
			throws Exception {
		log.debug("inside addTermsConditionCheckList");
		Session session = null;
		Transaction transaction = null;
		TermsConditionChecklistTO to;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			transaction.begin();
			Iterator<TermsConditionChecklistTO> tcIterator = termsConditionList.iterator();
			int count = 0;
			while(tcIterator.hasNext()){
				to = tcIterator.next();
				TermsConditionChecklist bo=(TermsConditionChecklist)session.get(TermsConditionChecklist.class, to.getId());
				if(to.getIsMandatory().equals("Yes"))
					bo.setIsMandatory(true);
				else
					bo.setIsMandatory(false);
				
				bo.setChecklistDescription(to.getChecklistDescription());
				
				bo.setModifiedBy(userid);
				bo.setLastModifiedDate(new Date());
				session.update(bo);
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
