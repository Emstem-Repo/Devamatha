package com.kp.cms.transactionsimpl.fee;

import java.util.Date;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.StudentCancellationDetails;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.fee.CancelledAdmissionRepaymentForm;
import com.kp.cms.to.admin.StudentTO;
import com.kp.cms.transactions.fee.ICancelledAdmissionRepaymentTransaction;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;

public class CancelledAdmissionRepaymentTransactionimpl implements
		ICancelledAdmissionRepaymentTransaction {
	/**
	 * Singleton object of CancelledAdmissionRepaymentTransactionimpl
	 */
	private static volatile CancelledAdmissionRepaymentTransactionimpl cancelledAdmissionRepaymentTransactionimpl = null;
	private static final Log log = LogFactory.getLog(CancelledAdmissionRepaymentTransactionimpl.class);
	private CancelledAdmissionRepaymentTransactionimpl() {
		
	}
	/**
	 * return singleton object of CancelledAdmissionRepaymentTransactionimpl.
	 * @return
	 */
	public static CancelledAdmissionRepaymentTransactionimpl getInstance() {
		if (cancelledAdmissionRepaymentTransactionimpl == null) {
			cancelledAdmissionRepaymentTransactionimpl = new CancelledAdmissionRepaymentTransactionimpl();
		}
		return cancelledAdmissionRepaymentTransactionimpl;
	}
	/* (non-Javadoc)
	 * returns the AdmAppln if student is cancelled
	 * @see com.kp.cms.transactions.fee.ICancelledAdmissionRepaymentTransaction#getAdmBO(com.kp.cms.forms.fee.CancelledAdmissionRepaymentForm)
	 */
	@Override
	public Student getStudentBO(CancelledAdmissionRepaymentForm cancelledAdmissionRepaymentForm)
			throws Exception {
		Session session = null;
		Student stu= null;
		String query="";
		try {
			session = HibernateUtil.getSession();
			if(cancelledAdmissionRepaymentForm.getRegisterNo()!=null && !cancelledAdmissionRepaymentForm.getRegisterNo().isEmpty()){
			 query= "from Student s where  s.admAppln.isCancelled=1" +
					" and s.registerNo='"+cancelledAdmissionRepaymentForm.getRegisterNo()+"'";
			}
			else if(cancelledAdmissionRepaymentForm.getApplnNo()!=null && !cancelledAdmissionRepaymentForm.getApplnNo().isEmpty()){
				query="from Student s where  s.admAppln.isCancelled=1" +
				" and s.admAppln.applnNo='"+cancelledAdmissionRepaymentForm.getApplnNo()+"'";
			}
			stu =(Student) session.createQuery(query).uniqueResult();
			
		} catch (Exception e) {
			log.error("Error while retrieving selected student.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		return stu;
	}
	/* (non-Javadoc)
	 * checking duplicate for the student 
	 * @see com.kp.cms.transactions.fee.ICancelledAdmissionRepaymentTransaction#checkDuplicate(com.kp.cms.forms.fee.CancelledAdmissionRepaymentForm)
	 */
	@Override
	public StudentCancellationDetails checkDuplicate(CancelledAdmissionRepaymentForm cancelledAdmissionRepaymentForm)
			throws Exception {
		StudentCancellationDetails stuBO;
		Session session = null;
		String query="";
		try {
			session = HibernateUtil.getSession();
			if(cancelledAdmissionRepaymentForm.getAdmApplnTo()!=null){
			 query= "from StudentCancellationDetails s where  s.admAppln.id=" +cancelledAdmissionRepaymentForm.getAdmApplnTo().getId();
			}
			stuBO =(StudentCancellationDetails) session.createQuery(query).uniqueResult();
			
		} catch (Exception e) {
			log.error("Error while retrieving selected student.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		return stuBO;
	}
	/* (non-Javadoc)
	 * saving the details entered to database
	 * @see com.kp.cms.transactions.fee.ICancelledAdmissionRepaymentTransaction#saveCancelledRepaymentDetails(com.kp.cms.bo.admin.StudentCancellationDetails)
	 */
	@Override
	public boolean saveCancelledRepaymentDetails(StudentCancellationDetails studentCancelDetailsBO) throws Exception {
		Session session = null;
		boolean saved=false;
		Transaction txn=null;
		try {
			session = InitSessionFactory.getInstance().openSession();
			txn=session.beginTransaction();
			if(studentCancelDetailsBO!=null){
						session.saveOrUpdate(studentCancelDetailsBO);
						saved=true;
				}
			txn.commit();
			return saved;
		} catch (Exception e) {
			if(txn!=null)
				txn.rollback();
				saved=false;
			log.error("Error while retrieving selected candidates.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
	}
}
