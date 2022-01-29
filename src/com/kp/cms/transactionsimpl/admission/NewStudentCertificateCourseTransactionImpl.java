package com.kp.cms.transactionsimpl.admission;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.StudentCertificateCourse;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.transactions.admission.INewStudentCertificateCourseTxn;
import com.kp.cms.utilities.InitSessionFactory;

public class NewStudentCertificateCourseTransactionImpl implements INewStudentCertificateCourseTxn {
	/**
	 * Singleton object of NewStudentCertificateCourseTransactionImpl
	 */
	private static volatile NewStudentCertificateCourseTransactionImpl newStudentCertificateCourseTransactionImpl = null;
	private static final Log log = LogFactory.getLog(NewStudentCertificateCourseTransactionImpl.class);
	private NewStudentCertificateCourseTransactionImpl() {
		
	}
	/**
	 * return singleton object of NewStudentCertificateCourseTransactionImpl.
	 * @return
	 */
	public static NewStudentCertificateCourseTransactionImpl getInstance() {
		if (newStudentCertificateCourseTransactionImpl == null) {
			newStudentCertificateCourseTransactionImpl = new NewStudentCertificateCourseTransactionImpl();
		}
		return newStudentCertificateCourseTransactionImpl;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.INewStudentCertificateCourseTxn#saveCertificateCourse(com.kp.cms.bo.admin.StudentCertificateCourse, int)
	 */
	public synchronized int saveCertificateCourse( StudentCertificateCourse studentCertificateCourse, int groupId) throws ReActivateException,Exception {
		log.info("Entered into saveCertificateCourse");
		Session session = null;
		Transaction transaction = null;
		try {
			SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			transaction.begin();
			if(studentCertificateCourse.getIsExtraCurricular())
				session.save(studentCertificateCourse);
			else{
				synchronized (NewStudentCertificateCourseTransactionImpl.class) {
					Object[] obj=(Object[])session.createQuery("select cg.maxIntake,count(s) from CertificateCourseGroup cg" +
							" left join cg.certificateCourse cc" +
							" left join cc.studentCertificateCourses s with (s.isCancelled=0 and s.groups.id=:groupId) where cg.groups.id=:groupId" +
					" and cg.certificateCourse.id=:cid group by cg.certificateCourse.id,cg.groups.id").setInteger("groupId", groupId).setInteger("cid", studentCertificateCourse.getCertificateCourse().getId()).uniqueResult();
					if(obj!=null){
						if(obj[0]!=null && obj[1]!=null){
							if(Integer.parseInt(obj[0].toString())>Integer.parseInt(obj[1].toString())){
								session.save(studentCertificateCourse);
							}else
								throw new ReActivateException();
						}
					}else
						throw new ReActivateException();
					
				}
			}
			transaction.commit();
			session.flush();
			session.close();
			log.info("Exit from saveCertificateCourse");
			return studentCertificateCourse.getId();
		}catch (ReActivateException e) {
			log.info("Error in saveCertificateCourse");
			if ( transaction != null){
				transaction.rollback();
			}
			if (session != null){
				session.flush();
				session.close();
			}
			
			throw new ReActivateException();
		} catch (Exception e) {
			if ( transaction != null){
				transaction.rollback();
			}
			if (session != null){
				session.flush();
				session.close();
			}
			return 0;
		}
	}
}
