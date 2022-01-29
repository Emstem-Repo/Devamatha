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

import com.kp.cms.bo.admin.ApplicationNumber;
import com.kp.cms.bo.admin.CourseApplicationNumber;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.forms.admission.ApplicationNumberForm;
import com.kp.cms.transactions.admission.IApplicationNumberTransaction;
import com.kp.cms.utilities.HibernateUtil;


public class ApplicationNumberTransactionImpl implements IApplicationNumberTransaction{
	private static Log log = LogFactory.getLog(ApplicationNumberTransactionImpl.class);
	public static volatile ApplicationNumberTransactionImpl applicationNumberTransactionImpl = null;
	
	public static ApplicationNumberTransactionImpl getInstance() {
		if (applicationNumberTransactionImpl == null) {
			applicationNumberTransactionImpl = new ApplicationNumberTransactionImpl();
			return applicationNumberTransactionImpl;
		}
		return applicationNumberTransactionImpl;
	}

	/***
	 * this method will add/update record in application number table 
	 */
	public boolean addApplicaionNumber(ApplicationNumber applicationNumber, String mode) throws DuplicateException, Exception {
		log.debug("inside addApplicaionNumber in Impl");
		Session session = null;
		Transaction transaction = null;
		boolean result = false;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = HibernateUtil.getSessionFactory().openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			if(mode.equalsIgnoreCase("Add")){
				session.save(applicationNumber);
				}
			else{
				//session.saveOrUpdate(applicationNumber);
				session.merge(applicationNumber);
			}
			transaction.commit();
			if (session != null) {
				session.flush();
				session.close();
			}
			//sessionFactory.close();
			result = true;
		} catch (ConstraintViolationException e) {
			transaction.rollback();
			log.error("Error during saving Application Number data...", e);
			throw new BusinessException(e);
		} catch (Exception e) {
			transaction.rollback();
			log.error("Error during saving Application Number data...", e);
			throw new ApplicationException(e);
		}
		log.debug("leaving addApplicaionNumber in impl");
		return result;
	}

	/**
	 * 
	 * This method returns a ApplicationNumber object with all the records, when there is id returns a single record according to the id
	 * 
	 * @throws ApplicationException
	 */

	public List<ApplicationNumber> getApplicationNumber(int id,int currentYear) throws Exception {
		log.debug("inside getApplicationNumber in impl");
		Session session = null;
		List<ApplicationNumber> applicationNumberList;

		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			if (id != 0) {
				Query query = session.createQuery("from ApplicationNumber a where id = :id and isActive=1");
				query.setInteger("id", id);
				List<ApplicationNumber> list = query.list();
				session.flush();
//				session.close();
				//sessionFactory.close();
				applicationNumberList = list;
			} else {
				Query query = session.createQuery("from ApplicationNumber a where isActive=1 and a.year = :currentYear");
				query.setInteger("currentYear", currentYear);
				List<ApplicationNumber> list = query.list();
				session.flush();
//				session.close();
				//sessionFactory.close();
				applicationNumberList = list;
			}

		} catch (Exception e) {
			log.error("Error during getting applicaition numbers...",e);
			session.flush();
			session.close();
			throw new ApplicationException(e);
		}
		log.debug("leaving getApplicationNumber in impl");
		return applicationNumberList;
	}
	
	/**
	 * This will delete a single Application Number from database.
	 * 
	 * @return
	 * @throws Exception 
	 */

	public boolean deleteApplicationNumber(int appId,Boolean activate, ApplicationNumberForm applicationNumberForm) throws Exception {
		log.debug("inside deleteApplicationNumber in impl");
		Session session = null;
		Transaction tx = null;
		boolean result = false;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			tx.begin();
			ApplicationNumber applicationNumber=(ApplicationNumber)session.get(ApplicationNumber.class, appId);
			applicationNumber.setIsActive(activate);
			applicationNumber.setModifiedBy(applicationNumberForm.getUserId());
			applicationNumber.setLastModifiedDate(new Date());
			session.update(applicationNumber);
			//session.delete(applicationNumber);
			tx.commit();
			session.flush();
//			session.close();
			result = true;
		} catch (ConstraintViolationException e) {
			tx.rollback();
			log.error("Error during deleting Application No. data...", e);
			throw new BusinessException(e);
		} catch (Exception e) {
			tx.rollback();
			log.error("Error during deleting Application No. data...", e);
			throw new ApplicationException(e);
		}
		log.debug("inside deleteApplicationNumber in impl");
		return result;
	}

	/**
	 * method used for checking duplication
	 */
	public Boolean isApplNoDuplcated(int year, int onLineFrom, int OnlineTo, int OffLineFrom, int OffLineTo, int id) throws Exception {
		log.debug("inside isApplNoDuplcated in impl");
		Session session = null;
		boolean result = false;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			StringBuffer sqlString = new StringBuffer("");
			if(OffLineTo > 0){
				sqlString.append("from ApplicationNumber where year = :year and isActive=1 and "
					+ "((:onlineStart between onlineApplnNoFrom and onlineApplnNoTo) OR"
					+ "(:onlineStart between offlineApplnNoFrom and offlineApplnNoTo) OR"
					+ "(:onlineEnd between onlineApplnNoFrom and onlineApplnNoTo) OR"
					+ "(:onlineEnd between offlineApplnNoFrom and offlineApplnNoTo) OR"
					+ "(:offlineStart between onlineApplnNoFrom and onlineApplnNoTo) OR"
					+ "(:offlineStart between offlineApplnNoFrom and offlineApplnNoTo) OR"
					+ "(:offlineEnd between onlineApplnNoFrom and onlineApplnNoTo) OR"
					+ "(:offlineEnd between offlineApplnNoFrom and offlineApplnNoFrom) OR" 
					+ "(:onlineStart <= onlineApplnNoFrom and :onlineEnd >= onlineApplnNoTo) OR"
					+ "(:onlineStart <= offlineApplnNoFrom and :onlineEnd >= offlineApplnNoTo) OR"
					+ "(:offlineStart <= onlineApplnNoFrom and :offlineEnd >= onlineApplnNoTo) OR"
					+ "(:offlineStart <= offlineApplnNoFrom and :offlineEnd >= offlineApplnNoTo))");
				
					if(id != 0){
						sqlString = sqlString.append(" and id != :editId");
					}
			}
			else
			{
				sqlString.append("from ApplicationNumber where year = :year and isActive=1 and "
					+ "((:onlineStart between onlineApplnNoFrom and onlineApplnNoTo) OR"
					+ "(:onlineStart between offlineApplnNoFrom and offlineApplnNoTo) OR"
					+ "(:onlineEnd between onlineApplnNoFrom and onlineApplnNoTo) OR"
					+ "(:onlineEnd between offlineApplnNoFrom and offlineApplnNoTo) OR"
					+ "(:onlineStart <= onlineApplnNoFrom and :onlineEnd >= onlineApplnNoTo) OR"
					+ "(:onlineStart <= offlineApplnNoFrom and :onlineEnd >= offlineApplnNoTo))");
				
					if(id != 0){
						sqlString = sqlString.append(" and id != :editId");
					}
			}
				
			Query query = session.createQuery(sqlString.toString());

			query.setInteger("year", year);
			query.setInteger("onlineStart", onLineFrom);
			query.setInteger("onlineEnd", OnlineTo);
			if(OffLineTo > 0){
				query.setInteger("offlineStart", OffLineFrom );
				query.setInteger("offlineEnd", OffLineTo);
			}
			if(id != 0){
				query.setInteger("editId", id);
			}

			List<ApplicationNumber> list = query.list();
			if (!list.isEmpty()) {
				result = true;
			}
			session.flush();
//			session.close();
			//sessionFactory.close();
		} catch (Exception e) {
			log.error("Error during duplcation checking..." + e);
			session.flush();
			session.close();
			throw new ApplicationException(e);
		}
		log.debug("leaving isApplNoDuplcated in impl");
		return result;
	}	
	
	/**
	 * getting CourseApplicationNumber data based on id
	 */
	public CourseApplicationNumber getCourseApplNo(int id) throws Exception {
		log.debug("inside CourseApplicationNumber");
		Session session = null;
		CourseApplicationNumber courseApplicationNumber;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from CourseApplicationNumber where id = :tempId");
			query.setInteger("tempId", id);
			courseApplicationNumber = (CourseApplicationNumber) query.uniqueResult();
			session.flush();
//			session.close();
			//sessionFactory.close();
			log.debug("leaving CourseApplicationNumber");
			return courseApplicationNumber;
		} catch (Exception e) {
			session.flush();
			session.close();
			throw new ApplicationException(e);
		}
	}

	/**
	 * duplication checking for application number
	 */
	public Boolean checkIsCourseDuplicated(int courseId, int year) throws Exception{
		log.debug("inside checkIsCourseDuplicated");
		Session session = null;
		List<Object[]> list;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from ApplicationNumber appNumber join appNumber.courseApplicationNumbers " +
											" couseAppNos with couseAppNos.course.id = :courseId and couseAppNos.isActive = 1 " +  
											" where appNumber.year = :tempYear and appNumber.isActive = 1");

			
			query.setInteger("courseId", courseId);
			query.setInteger("tempYear", year);
			
			list =  query.list();
			session.flush();
//			session.close();
			//sessionFactory.close();
			log.debug("leaving checkIsCourseDuplicated");
			if(!list.isEmpty()){
				return true;
			}
			else
			{
				return false;
			}
		} catch (Exception e) {
			log.error("Error during duplcation checking..." + e);
			session.flush();
			session.close();
			throw new ApplicationException(e);
		}
	}

	/**
	 * This will delete a single Application Number from database.
	 * 
	 * @return
	 * @throws Exception 
	 */

	public boolean deleteCourseApplicationNumber(List<CourseApplicationNumber> courseApplicationList) throws Exception {
		log.info("Inside odeleteCourseApplicationNumber");
		CourseApplicationNumber courseApplicationNumber;
		Transaction tx=null;
		Session session = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			Iterator<CourseApplicationNumber> itr = courseApplicationList.iterator();
			int count = 0;
			tx = session.beginTransaction();
			tx.begin();
			while (itr.hasNext()) {
				courseApplicationNumber = itr.next();
				session.delete(courseApplicationNumber);
				if (++count % 20 == 0) {
					session.flush();
					session.clear();
				}
			}
			tx.commit();
			session.close();
			//sessionFactory.close();
			log.info("End of deleteCourseApplicationNumber");
			return true;
		} catch (Exception e) {
			if(tx!=null){
				tx.rollback();
			}
			log.error("Error occured in deleteCourseApplicationNumber");
			throw new ApplicationException(e);
		}
	}
	/**
	 * getting CourseApplicationNumber based on the id
	 */
	public List<CourseApplicationNumber> getCourseApplicationNumber(int id) throws Exception {
		log.debug("inside getCourseApplicationNumber");
		Session session = null;
		List<CourseApplicationNumber> courseApplicationNumberList;

		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from CourseApplicationNumber where applicationNumber.id = :applId");
			query.setInteger("applId", id);
			List<CourseApplicationNumber> list = query.list();
			session.flush();
//			session.close();
			//sessionFactory.close();
			courseApplicationNumberList = list;
			log.debug("leaving getCourseApplicationNumber");
		} catch (Exception e) {
			log.error("Error during getting applicaition numbers..." + e);
			session.flush();
			session.close();
			throw new ApplicationException(e);
		}
		return courseApplicationNumberList;
	}
	
	
	
}
