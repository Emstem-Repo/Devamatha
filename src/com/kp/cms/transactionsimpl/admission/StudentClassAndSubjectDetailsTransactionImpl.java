package com.kp.cms.transactionsimpl.admission;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;

import com.kp.cms.bo.admin.Student;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.admission.StudentClassAndSubjectDetailsForm;
import com.kp.cms.transactions.admission.IStudentClassAndSubjectDetailsTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class StudentClassAndSubjectDetailsTransactionImpl implements
		IStudentClassAndSubjectDetailsTransaction {
	/**
	 * Singleton object of StudentClassAndSubjectDetailsTransactionImpl
	 */
	private static volatile StudentClassAndSubjectDetailsTransactionImpl studentClassAndSubjectDetailsTransactionImpl = null;
	private static final Log log = LogFactory.getLog(StudentClassAndSubjectDetailsTransactionImpl.class);
	private StudentClassAndSubjectDetailsTransactionImpl() {
		
	}
	/**
	 * return singleton object of StudentClassAndSubjectDetailsTransactionImpl.
	 * @return
	 */
	public static StudentClassAndSubjectDetailsTransactionImpl getInstance() {
		if (studentClassAndSubjectDetailsTransactionImpl == null) {
			studentClassAndSubjectDetailsTransactionImpl = new StudentClassAndSubjectDetailsTransactionImpl();
		}
		return studentClassAndSubjectDetailsTransactionImpl;
	}
	/* (non-Javadoc)
	 * querying the database to get the student BO
	 * @see com.kp.cms.transactions.admission.IStudentClassAndSubjectDetailsTransaction#getStudentBO(com.kp.cms.forms.admission.StudentClassAndSubjectDetailsForm)
	 */
	@Override
	public Student getStudentBO(StudentClassAndSubjectDetailsForm stuForm) throws Exception {
		Session session = null;
		Student stu= null;
		String query="";
		try {
			session = HibernateUtil.getSession();
			if(stuForm.getRegNo()!=null && !stuForm.getRegNo().isEmpty()){
			 query= "from Student s where s.isAdmitted=1 and s.admAppln.isCancelled=0" +
					" and (s.isHide=0 or s.isHide is null) and s.registerNo='"+stuForm.getRegNo()+"'";
			}
			else if(stuForm.getApplnNo()!=null && !stuForm.getApplnNo().isEmpty()){
				query="from Student s where s.isAdmitted=1 and s.admAppln.isCancelled=0" +
				" and (s.isHide=0 or s.isHide is null) and s.admAppln.applnNo='"+stuForm.getApplnNo()+"'";
			}
			stu =(Student) session.createQuery(query).uniqueResult();
			
		} catch (Exception e) {
			log.error("Error while retrieving selected candidates.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		return stu;
	}
}
