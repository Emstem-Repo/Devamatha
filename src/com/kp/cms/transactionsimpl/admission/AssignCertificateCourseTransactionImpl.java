package com.kp.cms.transactionsimpl.admission;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.AssignCertificateCourse;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.admission.AssignCertificateCourseForm;
import com.kp.cms.transactions.admission.IAssignCertificateCourseTransaction;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;

public class AssignCertificateCourseTransactionImpl implements
		IAssignCertificateCourseTransaction {

	/**
	 * Singleton object of AssignCertificateCourseTransactionImpl
	 */
	private static volatile AssignCertificateCourseTransactionImpl assignCertificateCourseTransactionImpl = null;
	private static final Log log = LogFactory.getLog(AssignCertificateCourseTransactionImpl.class);
	private AssignCertificateCourseTransactionImpl() {
		
	}
	/**
	 * return singleton object of AssignCertificateCourseTransactionImpl.
	 * @return
	 */
	public static AssignCertificateCourseTransactionImpl getInstance() {
		if (assignCertificateCourseTransactionImpl == null) {
			assignCertificateCourseTransactionImpl = new AssignCertificateCourseTransactionImpl();
		}
		return assignCertificateCourseTransactionImpl;
	}
	@Override
	public List<AssignCertificateCourse> getAllAssignCertificateCourses(int year,String semType)
			throws Exception {
		Session session = null;
		List<AssignCertificateCourse> list = null;
		try {
			session = HibernateUtil.getSession();
			Query query=session.createQuery("select a from AssignCertificateCourse a where a.isActive=1 and a.course.isActive=1 and a.academicYear="+year+" and a.semType='"+semType+"' group by a.id");
			list = query.list();
			return list;
		} catch (Exception e) {
			log.error("Error while retrieving selected candidates.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.IAssignCertificateCourseTransaction#saveAssignCertificateCourse(com.kp.cms.bo.admin.AssignCertificateCourse)
	 */
	@Override
	public boolean saveAssignCertificateCourse(AssignCertificateCourse bo)
			throws Exception {
		Session session = null;
		Transaction transaction = null;
		try {
			session = InitSessionFactory.getInstance().openSession();
			transaction = session.beginTransaction();
			transaction.begin();
			session.saveOrUpdate(bo);
			transaction.commit();
			session.flush();
			return true;
		} catch (Exception e) {
			if ( transaction != null){
				transaction.rollback();
			}
			if (session != null){
				session.flush();
			}
			return false;
		}
	}
	@Override
	public boolean deleteAssignCertificateCourse(AssignCertificateCourseForm assignCertificateCourseForm, String mode) {
		Session session = null;
		Transaction transaction = null;
		try {
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			AssignCertificateCourse bo=(AssignCertificateCourse)session.get(AssignCertificateCourse.class,assignCertificateCourseForm.getId());
			if(bo!=null){
				if(mode.equals("delete")){
					bo.setIsActive(false);
				}else{
					bo.setIsActive(true);
				}
				bo.setModifiedBy(assignCertificateCourseForm.getUserId());
				bo.setLastModifiedDate(new Date());
				session.update(bo);
			}
			transaction.commit();
			session.flush();
			return true;
		} catch (Exception e) {
			if ( transaction != null){
				transaction.rollback();
			}
			if (session != null){
				session.flush();
			}
			return false;
		}
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.IAssignCertificateCourseTransaction#getAssignCertificateCourse(int)
	 */
	@Override
	public AssignCertificateCourse getAssignCertificateCourse(int id)
			throws Exception {
		Session session = null;
		Transaction transaction = null;
		AssignCertificateCourse bo=null;
		try {
			session = HibernateUtil.getSession();
			bo=(AssignCertificateCourse)session.get(AssignCertificateCourse.class,id);
			session.flush();
			return bo;
		} catch (Exception e) {
			if ( transaction != null){
				transaction.rollback();
			}
			if (session != null){
				session.flush();
			}
			return bo;
		}
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.IAssignCertificateCourseTransaction#checkDuplicateAssignCertificateCourse(java.lang.String)
	 */
	@Override
	public AssignCertificateCourse checkDuplicateAssignCertificateCourse(
			String query) throws Exception {
		Session session = null;
		Transaction transaction = null;
		AssignCertificateCourse bo=null;
		try {
			session = HibernateUtil.getSession();
			bo=(AssignCertificateCourse)session.createQuery(query).uniqueResult();
			session.flush();
			return bo;
		} catch (Exception e) {
			if ( transaction != null){
				transaction.rollback();
			}
			if (session != null){
				session.flush();
			}
			return bo;
		}
	}
	
	
}
