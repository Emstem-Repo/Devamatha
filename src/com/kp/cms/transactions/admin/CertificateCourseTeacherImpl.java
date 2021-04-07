package com.kp.cms.transactions.admin;

import java.util.Date;
import java.util.Iterator;
import java.util.List;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.CertificateCourseTeacher;
import com.kp.cms.bo.admin.DocTypeExams;
import com.kp.cms.bo.admin.Period;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.handlers.admission.CertificateCourseTeacherHandler;
import com.kp.cms.transactions.admission.ICertificateCourseEntryTxn;
import com.kp.cms.transactions.admission.ICertificateCourseTeacherTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class CertificateCourseTeacherImpl implements ICertificateCourseTeacherTransaction  {
	
	/**
	 * Singleton object of DocumentExamEntryHandler
	 */
	private static volatile CertificateCourseTeacherImpl certificateCourseTeacherImpl = null;
	private static final Log log = LogFactory.getLog(CertificateCourseTeacherImpl.class);
	public CertificateCourseTeacherImpl() {
		
	}
	/**
	 * return singleton object of DocumentExamEntryHandler.
	 * @return
	 */
	public static CertificateCourseTeacherImpl getInstance() {
		if (certificateCourseTeacherImpl == null) {
			certificateCourseTeacherImpl = new CertificateCourseTeacherImpl();
		}
		return certificateCourseTeacherImpl;
	}
	/**
	 * getting the list of doctypeExams from database
	 * @return
	 */
	
	public boolean addCertificateCourseTeacher(CertificateCourseTeacher certificateCourseTeacher) throws Exception {
		Session session = null;
		Transaction transaction = null;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			session.save(certificateCourseTeacher);
			transaction.commit();
			session.flush();
			session.close();
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
	public List<CertificateCourseTeacher> getList(){
		List<CertificateCourseTeacher> certificateCourseTeachers = null;
		Session session = null;
		try{
			session = HibernateUtil.getSession();
			 certificateCourseTeachers =session.createQuery("from CertificateCourseTeacher c where c.isActive = 1").list();
			session.flush();
		}catch (Exception e) {
			if (session != null){
				session.flush();
				//session.close();
			}
		}
		return certificateCourseTeachers;
	}
	public CertificateCourseTeacher checkDuplicate(int cerCourseId,int teacherId) throws Exception{
		CertificateCourseTeacher certificateCourseTeacher = null;
		Session session = null;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			certificateCourseTeacher =(CertificateCourseTeacher)session.createQuery("from CertificateCourseTeacher c where c.users.id="+teacherId +"and c.certificateCourse.id="+cerCourseId).uniqueResult();
			session.flush();
			//session.close();
			return certificateCourseTeacher;
		} catch (Exception e) {
			if (session != null){
				session.flush();
				//session.close();
			}
		}
		return certificateCourseTeacher;
	}
	
	public boolean reActive(int oldId, String userId){
		Session session = null;
		CertificateCourseTeacher type = null;
		Transaction transaction = null;
		try { 
		 //session =sessionFactory.openSession();
		 session = HibernateUtil.getSession();
		 Query query = session.createQuery("from CertificateCourseTeacher c where c.id=:oldId");
		 query.setInteger("oldId", oldId);
		 type = (CertificateCourseTeacher) query.uniqueResult();	
		 transaction = session.beginTransaction();
		 type.setIsActive(true);
		 type.setModifiedBy(userId);
		 type.setLastModifiedDate(new Date());
		 session.update(type);
		 transaction.commit();
		 return true;
		}
		catch (Exception e) {	
			if(transaction!=null){
				transaction.rollback();
			}
			log.error("Exception occured while reactivateDocExamType in DocumentExamEntryTransactionImpl :"+e);
			e.printStackTrace();
		} finally {
		if (session != null) {
			//sessionFactory.close();
			session.flush();
			session.close();
		}
		log.info("Leaving into DocumentExamEntryTransactionImpl of reactivateDocExamType");
		}
		 return true;
	}
	
	public CertificateCourseTeacher editCertificateCourseTeacher(String certificateCourseTeachId) throws Exception {
		log.info("Entering into DocumentExamEntryTransactionImpl of deleteDocExamType");
		Session session = null;
		Transaction transaction = null;
		CertificateCourseTeacher certificateCourseTeacher=null;
		try {
			//session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			int certificateCourseTeacId=Integer.parseInt(certificateCourseTeachId);
			certificateCourseTeacher=(CertificateCourseTeacher)session.get(CertificateCourseTeacher.class,certificateCourseTeacId);
			transaction.commit();
			return certificateCourseTeacher;
		} catch (Exception e) {	
			if(transaction != null){
				transaction.rollback();
			}
			log.error("Exception occured while delete docTypeExams in DocumentExamEntryTransactionImpl :"+e);
			throw  new ApplicationException(e);
		} finally {
		if (session != null) {
			session.flush();
			//session.close();
		}
		log.info("Leaving into DocumentExamEntryTransactionImpl of deleteDocExamType");
		}
	}
	
	public boolean deleteCertificateCourseTeacher(int CertificateCourTeaId , String userId ) throws Exception{
		Session session = null;
		Transaction transaction = null;
		try{
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			CertificateCourseTeacher cetriCertificateCourseTeacher = (CertificateCourseTeacher)session.get(CertificateCourseTeacher.class, CertificateCourTeaId );
			cetriCertificateCourseTeacher.setIsActive(false);
			cetriCertificateCourseTeacher.setModifiedBy(userId);
			cetriCertificateCourseTeacher.setLastModifiedDate(new Date());
			session.update(cetriCertificateCourseTeacher);
			transaction.commit();
			return true;
		} catch (Exception e) {	
		if(transaction != null){
			transaction.rollback();
			return false;
		}
		log.error("Exception occured while deleteCertificateCourseTeacher in CertificateCourseTeacherImpl :"+e);
		throw  new ApplicationException(e);
		} finally {
			if (session != null) {
		session.flush();
		session.close();
			}
			log.info("Leaving into CertificateCourseTeacherImpl of deleteCertificateCourseTeacher");
		}
	}
	
	public boolean updateCertificateCourseTeacher(CertificateCourseTeacher certificateCourseTeacher)
	throws Exception {
		Session session = null;
		Transaction transaction = null;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			session.merge(certificateCourseTeacher);
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
}
