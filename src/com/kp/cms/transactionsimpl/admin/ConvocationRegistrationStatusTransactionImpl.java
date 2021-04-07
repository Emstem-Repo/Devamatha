package com.kp.cms.transactionsimpl.admin;

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

import com.kp.cms.bo.admin.ConvocationRegistration;
import com.kp.cms.bo.admin.ConvocationSession;
import com.kp.cms.bo.exam.ExternalEvaluatorsDepartment;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.admin.ConvocationRegistrationStatusForm;
import com.kp.cms.handlers.admin.ConvocationRegistrationStatusHandler;
import com.kp.cms.transactions.admin.IConvocationRegistrationStatusTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class ConvocationRegistrationStatusTransactionImpl implements IConvocationRegistrationStatusTransaction{
	private static volatile ConvocationRegistrationStatusTransactionImpl convocationRegistrationStatusTransactionImpl = null;
	private static final Log log = LogFactory.getLog(ConvocationRegistrationStatusTransactionImpl.class);
	
	/**
	 * @return
	 */
	
	public static ConvocationRegistrationStatusTransactionImpl getInstance() {
		if (convocationRegistrationStatusTransactionImpl == null) {
			convocationRegistrationStatusTransactionImpl = new ConvocationRegistrationStatusTransactionImpl();
		}
		return convocationRegistrationStatusTransactionImpl;
	}
	
	
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admin.IConvocationRegistrationStatusTransaction#getGuestPassCount(com.kp.cms.forms.admin.ConvocationRegistrationStatusForm)
	 */
	public ConvocationRegistration getGuestPassCount(ConvocationRegistrationStatusForm form) throws Exception {
		Session session = null;
		ConvocationRegistration bo=null;
		try {
			session = HibernateUtil.getSession();
			if(form.getYear()!=null  && !form.getYear().isEmpty() && form.getStudentId()!=0){
				String str = "select cr from ConvocationRegistration cr where cr.isActive=1 " +
								" and cr.academicYear="+"'"+form.getYear()+"'"+" and cr.student.id="+form.getStudentId();
				Query query=session.createQuery(str);
				 bo= (ConvocationRegistration)query.uniqueResult();
			}
			
				return bo;
		} catch (Exception e) {
			log.error("Error while retrieving selected candidates.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admin.IConvocationRegistrationStatusTransaction#updateData(com.kp.cms.bo.admin.ConvocationRegistration)
	 */
	public boolean updateData(ConvocationRegistration bo) throws Exception {
		log.info("Start of addCourse of CourseTransactionImpl");
		Session session = null;
		Transaction transaction = null;
		boolean result = false;
		ConvocationRegistration currentbo;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			if(bo!=null){
				if(bo.getId()>0){
					String str = "select cr from ConvocationRegistration cr where  cr.id="+bo.getId();
						Query query=session.createQuery(str);
						currentbo= (ConvocationRegistration)query.uniqueResult();
					if(currentbo!=null){
						if(bo.isGuestpassIssued()){
						currentbo.setGuestpassIssued(bo.isGuestpassIssued());
						currentbo.setPassNo1(bo.getPassNo1());
						currentbo.setPassNo2(bo.getPassNo2());
						currentbo.setModifiedBy(bo.getModifiedBy());
						currentbo.setLastModifiedDate(new Date());
						session.update(currentbo);
						result = true;
						}
					}
				}
			}
			transaction.commit();
			session.close();
			
		} catch (ConstraintViolationException e) {
			transaction.rollback();
			log.error("Error during saving course data..." , e);
			throw new BusinessException(e);
		} catch (Exception e) {
			transaction.rollback();
			log.error("Error during saving course data..." , e);
			throw new ApplicationException(e);
		}
		log.info("End of addCourse of CourseTransactionImpl");
		return result;
	}
	//checking the student register number weather correct or not
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admin.IConvocationRegistrationStatusTransaction#getStudentId(java.lang.String)
	 */
	public Object[] getStudentId(String regNo) throws Exception {
		Session session = null;
		Object[] obj = null;
		try {
			session = HibernateUtil.getSession();
			if(regNo!=null && !regNo.isEmpty()){
				String str = "select cr.student.id ,cr.student.classSchemewise.classes.course.name,cr.student.classSchemewise.classes.course.id  from ConvocationRegistration cr "+
								" where cr.student.registerNo="+"'"+regNo+"'";
				Query selectedCandidatesQuery=session.createQuery(str);
				obj = (Object[])selectedCandidatesQuery.uniqueResult();
			}
			
			return obj;
		} catch (Exception e) {
			log.error("Error while retrieving selected candidates.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		
	}
	public ConvocationSession getDateAndTime(ConvocationRegistrationStatusForm form) throws Exception {
		Session session = null;
		ConvocationSession bo=null;
		try {
			session = HibernateUtil.getSession();
			if(form.getYear()!=null  && !form.getYear().isEmpty() && form.getStudentId()!=0){
				String str = "select c.session from ConvocationCourse c where c.isActive=1" +
								" and c.course="+form.getCourseid();
				Query query=session.createQuery(str);
				 bo= (ConvocationSession)query.uniqueResult();
			}
			
				return bo;
		} catch (Exception e) {
			log.error("Error while retrieving selected candidates.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		
	}
}
