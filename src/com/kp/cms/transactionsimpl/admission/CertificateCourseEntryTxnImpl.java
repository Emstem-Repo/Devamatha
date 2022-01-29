package com.kp.cms.transactionsimpl.admission;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.CertificateCourse;
import com.kp.cms.bo.admin.Subject;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.admission.CertificateCourseEntryForm;
import com.kp.cms.transactions.admission.ICertificateCourseEntryTxn;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;

public class CertificateCourseEntryTxnImpl implements ICertificateCourseEntryTxn {
	public static volatile CertificateCourseEntryTxnImpl certificateCourseEntryTxnImpl = null;
	private static final Log log = LogFactory.getLog(CertificateCourseEntryTxnImpl.class);

	public static CertificateCourseEntryTxnImpl getInstance() {
		if (certificateCourseEntryTxnImpl == null) {
			certificateCourseEntryTxnImpl = new CertificateCourseEntryTxnImpl();
			return certificateCourseEntryTxnImpl;
		}
		return certificateCourseEntryTxnImpl;
	}
	/**
	 * 
	 * @param certificateCourse
	 * @return
	 * @throws Exception
	 */
	public boolean addCertificateCourse(CertificateCourse certificateCourse) throws Exception {
		log.debug("inside addCertificateCourse");
		Session session = null;
		Transaction transaction = null;
		try {
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			session.save(certificateCourse);
			transaction.commit();
			if (session != null) {
				session.flush();
				session.close();
			}
			log.debug("leaving addCertificateCourse");
			return true;
		} catch (ConstraintViolationException e) {
			transaction.rollback();
			log.error("Error during saving addCertificateCourse..." , e);
			throw new BusinessException(e);
		} catch (Exception e) {
			transaction.rollback();
			log.error("Error during saving addCertificateCourse data..." , e);
			throw new ApplicationException(e);
		}

	}
	/**
	 * 
	 */
	public List<CertificateCourse> getActiveCertificateCourses(int year,String semType)throws Exception
	{
		log.info("Start of getActiveCourses of CourseTransactionImpl");
		Session session = null;
		List<CertificateCourse> courseBoList;
		try {
			session = InitSessionFactory.getInstance().openSession();
			courseBoList = session.createQuery("from CertificateCourse c where c.isActive = 1 and c.year="+year+" and c.semType='"+semType+"'").list();
			} catch (Exception e) {
			log.error("Error in getActiveCertificateCourses of Course Impl",e);
			throw new ApplicationException(e);
			} finally {
			if (session != null) {
				session.flush();
				}
			}
			log.info("End of getActiveCertificateCourses of impl");
			return courseBoList;
	}
	
	public CertificateCourse isCertificateCourseNameDuplcated(CertificateCourse duplCertificateCourse) throws Exception {
		log.debug("inside isCertificateCourseNameDuplcated");
		Session session = null;
		CertificateCourse result = null;
		try {
			session = HibernateUtil.getSession();
			StringBuffer sqlString = new StringBuffer("from CertificateCourse c where upper(c.certificateCourseName) = :courseName and c.year= :year and c.semType=:semType");
			
			if(duplCertificateCourse.getId()!= 0){
				sqlString = sqlString.append(" and id != :tempId");
			}
			
			Query query = session.createQuery(sqlString.toString());
			query.setString("courseName", duplCertificateCourse.getCertificateCourseName().toUpperCase().trim());
			query.setInteger("year",duplCertificateCourse.getYear());
			query.setString("semType", duplCertificateCourse.getSemType());
			if(duplCertificateCourse.getId()!= 0){
				query.setInteger("tempId", duplCertificateCourse.getId());
			}
			result = (CertificateCourse) query.uniqueResult();
			session.flush();
		} catch (Exception e) {
			log.error("Error during duplcation checking..." , e);
			session.flush();
			//session.close();
			throw new ApplicationException(e);
		}
		log.debug("leaving isDocTypeDuplcated");
		return result;
	}
	/**
	 * 
	 * @param id
	 * @param activate
	 * @param certificateCourseEntryForm
	 * @return
	 * @throws Exception
	 */
	public boolean deleteCertificateCourseEntry(int id, Boolean activate, CertificateCourseEntryForm certificateCourseEntryForm) throws Exception {
		log.debug("inside deleteCertificateCourseEntry");
		Session session = null;
		Transaction transaction = null;
		boolean result = false;
		try {
//			session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			CertificateCourse certificateCourse = (CertificateCourse) session.get(CertificateCourse.class, id);
			if (activate) {
				certificateCourse.setIsActive(true);
			} else {
				certificateCourse.setIsActive(false);
			}
			certificateCourse.setModifiedBy(certificateCourseEntryForm.getUserId());
			certificateCourse.setLastModifiedDate(new Date());
			session.update(certificateCourse);
			transaction.commit();
			session.flush();
			session.close();
			log.debug("leaving deleteCertificateCourseEntry");
			result = true;
		} catch (ConstraintViolationException e) {
			transaction.rollback();
			log.error("Error while deleting data..." , e);
			throw new BusinessException(e);
		} catch (Exception e) {
			transaction.rollback();
			log.error("Error while deleting data..." , e);
			throw new ApplicationException(e);
		}
		return result;
	}
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<Subject> getSubjects() throws Exception 
	{
		
		Session session = null;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = HibernateUtil.getSession();
			String subjectHibernateQuery = "from Subject where isActive=1 and isCertificateCourse = 1 order by name";
			List<Subject> subjects = session.createQuery(subjectHibernateQuery).list();
			session.flush();
			//session.close();
			return subjects;
		} catch (Exception e) {
			
			log.error("Error during getting Subject loading...",e);
			if (session != null) {
				session.flush();
				//session.close();
			}
			throw  new ApplicationException(e);
			
		}
		
	}
	
	public CertificateCourse editCertificateCourse(CertificateCourseEntryForm certificateCourseEntryForm) throws Exception{
		CertificateCourse certificateCourse = null;
		Session session = null;
		try{
			session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
			int id = certificateCourseEntryForm.getId();
			certificateCourse = (CertificateCourse)session.get(CertificateCourse.class, id);
//			certificateCourse = (CertificateCourse)session.createQuery("from CertificateCourse c where =" +certificateCourse.getId()).uniqueResult();
			tx.commit();
			
		}catch (Exception e) {

			
			log.error("Error during getting Subject loading...",e);
			if (session != null) {
				session.flush();
				//session.close();
			}
			throw  new ApplicationException(e);
		}
		return certificateCourse;
	}
	
	public boolean updateCertificateCourse(CertificateCourse certificateCourse) throws Exception{
		Session session = null;
		Transaction transaction = null;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			session.update(certificateCourse);
			transaction.commit();
			session.flush();
			return true;
		} catch (Exception e) {
			if ( transaction != null){
				transaction.rollback();
			}
			if (session != null){
				session.flush();
				session.close();
			}
		return false;
		
	}
}
	@Override
	public List<CertificateCourse> getActiveCertificateCourses(int year)
			throws Exception {
		log.info("Start of getActiveCourses of CourseTransactionImpl");
		Session session = null;
		List<CertificateCourse> courseBoList;
		try {
			session = InitSessionFactory.getInstance().openSession();
			if(year!=0)
				courseBoList = session.createQuery("from CertificateCourse c where c.isActive = 1 and c.year="+year).list();
			else
				courseBoList = session.createQuery("from CertificateCourse c where c.isActive = 1").list();
			} catch (Exception e) {
			log.error("Error in getActiveCertificateCourses of Course Impl",e);
			throw new ApplicationException(e);
			} finally {
			if (session != null) {
				session.flush();
				}
			}
			log.info("End of getActiveCertificateCourses of impl");
			return courseBoList;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.ICertificateCourseEntryTxn#isSubjectDuplicate(com.kp.cms.bo.admin.CertificateCourse)
	 */
	@Override
	public CertificateCourse isSubjectDuplicate( CertificateCourse duplCertificateCourse) throws Exception {
		log.debug("inside isCertificateCourseNameDuplcated");
		Session session = null;
		CertificateCourse result = null;
		try {
			session = HibernateUtil.getSession();
			StringBuffer sqlString = new StringBuffer("from CertificateCourse c where c.subject.id=:subjectId and c.year= :year and c.semType=:semType");
			
			if(duplCertificateCourse.getId()!= 0){
				sqlString = sqlString.append(" and id != :tempId");
			}
			
			Query query = session.createQuery(sqlString.toString());
			query.setInteger("subjectId",duplCertificateCourse.getSubject().getId());
			query.setInteger("year",duplCertificateCourse.getYear());
			query.setString("semType", duplCertificateCourse.getSemType());
			if(duplCertificateCourse.getId()!= 0){
				query.setInteger("tempId", duplCertificateCourse.getId());
			}
			result = (CertificateCourse) query.uniqueResult();
			session.flush();
		} catch (Exception e) {
			log.error("Error during duplcation checking..." , e);
			session.flush();
			//session.close();
			throw new ApplicationException(e);
		}
		log.debug("leaving isDocTypeDuplcated");
		return result;
	}
}

