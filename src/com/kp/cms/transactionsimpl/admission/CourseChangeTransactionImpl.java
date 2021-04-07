package com.kp.cms.transactionsimpl.admission;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.bo.admin.Course;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.transactions.admission.ICourseChangeTransaction;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;

public class CourseChangeTransactionImpl implements ICourseChangeTransaction {
	private static final Log log = LogFactory.getLog(CourseChangeTransactionImpl.class);	
	public static volatile CourseChangeTransactionImpl courseChangeTransactionImpl = null;
	public static CourseChangeTransactionImpl getInstance() {
		if (courseChangeTransactionImpl == null) {
			courseChangeTransactionImpl = new CourseChangeTransactionImpl();
			return courseChangeTransactionImpl;
		}
		return courseChangeTransactionImpl;
	}	
	/**
	 * 
	 * @param selectedCourseID
	 * @param applicationNo
	 * @param year
	 * @return
	 * @throws Exception
	 */
	public boolean updateSelectedCourse(String selectedCourseID, String applicationNo, String year, String modifiedBy) throws Exception {
		log.info("entered updateAdmAppln..");
		boolean result = false;
		Session session = null;
		Transaction txn = null;
		try {
			session = HibernateUtil.getSession();
								
			AdmAppln admBO=(AdmAppln) session.createQuery("from AdmAppln a where a.applnNo = :applicationNo and a.appliedYear = :year").setString("applicationNo", applicationNo).setString("year", year).uniqueResult();
			Course course = new Course();
			course.setId(Integer.parseInt(selectedCourseID));
			admBO.setCourseBySelectedCourseId(course);
			admBO.setModifiedBy(modifiedBy);
			admBO.setLastModifiedDate(new Date());
			admBO.setCourseChangeDate(new Date());
			admBO.setApplicantSubjectGroups(null);
			
			//ra
			Course course1 = new Course();
			course1.setId(Integer.parseInt(selectedCourseID));
			admBO.setCourse(course1);
			
			txn = session.beginTransaction();
			session.saveOrUpdate(admBO);
			txn.commit();
			session.flush();
			session.close();
			result = true;
		} catch (ConstraintViolationException e) {
			if (session.isOpen())
				txn.rollback();
			
			throw new BusinessException(e);
		} catch (Exception e) {
			if (session.isOpen())
				txn.rollback();

			
			throw new ApplicationException(e);
		}
		return result;	
		
	}
		
		
	/**
	 * 
	 * @param applnNo
	 * @param year
	 * @return
	 * @throws Exception
	 */

	public AdmAppln getAdmAppln(int applnNo, String year) throws Exception {
		log.debug("inside getAdmAppln");
		Session session = null;
		try {
			
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from AdmAppln a where a.applnNo = " + applnNo + " and " +
				" a.appliedYear =  '" + year + "'");
			AdmAppln admAppln = (AdmAppln) query.uniqueResult();
			log.debug("leaving getAdmAppln");
			return admAppln;
		 } catch (Exception e) {
			 log.error("Error during getting Grades...",e);
			 session.flush();
			 session.close();
			 throw  new ApplicationException(e);
		 }
	}
	
	/**
	 * 
	 * @param id
	 * @return courseMap <key,value>
	 */
	public List<Course> getCoursesByProgramType(int programTypeId)throws Exception
	{
		log.info("Start of getCoursesByProgramType");
		Session session = null;
		List<Course> courseBoList;
		try {
			session = InitSessionFactory.getInstance().openSession();
			courseBoList = session.createQuery("from Course course where course.isActive = 1 and course.program.programType.id = "+
					+ programTypeId + " order by course.name asc").list();
			} catch (Exception e) {
			log.error("Error in getActiveCourses of Course Impl",e);
			throw new ApplicationException(e);
			} finally {
			if (session != null) {
				session.flush();
				session.close();
				}
			}
			log.info("End of getActiveCourses of CourseTransactionImpl");
			return courseBoList;
	}

}
