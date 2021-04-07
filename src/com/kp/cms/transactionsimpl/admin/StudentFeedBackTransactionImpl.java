package com.kp.cms.transactionsimpl.admin;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.StudentFeedBack;
import com.kp.cms.bo.admin.StudentLogin;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.transactions.admin.IStudentFeedBackTransaction;
import com.kp.cms.utilities.InitSessionFactory;

public class StudentFeedBackTransactionImpl implements IStudentFeedBackTransaction{
	
	
	public static volatile StudentFeedBackTransactionImpl studentFeedBackTransactionImpl = null;
	private static final Log log = LogFactory.getLog(StudentFeedBackTransactionImpl.class);

	public static StudentFeedBackTransactionImpl getInstance() {
		if (studentFeedBackTransactionImpl == null) {
			studentFeedBackTransactionImpl = new StudentFeedBackTransactionImpl();
			return studentFeedBackTransactionImpl;
		}
		return studentFeedBackTransactionImpl;
	}
	
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admin.IStudentFeedBackTransaction#addStudentFeedBack(com.kp.cms.bo.admin.StudentFeedBack)
	 */
	public boolean addStudentFeedBack(StudentFeedBack studentFeedBack) throws Exception
	{
		log.info("call of addStudentFeedBack method in StudentFeedBackTransactionImpl class.");
		Session session = null;
		Transaction transaction=null;
		boolean isadded=false;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = InitSessionFactory.getInstance().openSession();
			transaction=session.beginTransaction();
			session.save(studentFeedBack);
			transaction.commit();
			isadded=true;
			} catch (Exception e) {
				isadded=false;
			log.error("Unable to get student feedback"+e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		log.info("end of addStudentFeedBack method in StudentFeedBackTransactionImpl class.");
		return isadded;
	}

	/**
	 * @param studentFeedBackForm
	 * @return
	 * @throws Exception
	 */
	public String getStudentEmailId(String userName) throws Exception{
		Session session=null;
		int stuId = 0;
		String emailId="";
		StudentLogin studentLogin;
		Student student;
		try{
			session = InitSessionFactory.getInstance().openSession();
			if(userName != null && !userName.isEmpty()){
				String hqlQuery = "from StudentLogin studentLogin where studentLogin.userName = '"+userName+"'";
				Query query = session.createQuery(hqlQuery);
				studentLogin = (StudentLogin) query.uniqueResult();
				stuId = studentLogin.getStudent().getId();
			}
			if(stuId != 0){
				String hqlQuery = "from Student student where student.id = '"+stuId+"'";
				Query query = session.createQuery(hqlQuery);
				student = (Student) query.uniqueResult();
				emailId = student.getAdmAppln().getPersonalData().getEmail();
			}
		}catch (Exception e) {
			throw  new ApplicationException(e);
		}
		return emailId;
	}
		
}


