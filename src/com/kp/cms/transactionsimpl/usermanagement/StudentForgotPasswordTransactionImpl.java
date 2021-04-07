package com.kp.cms.transactionsimpl.usermanagement;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.StudentLogin;
import com.kp.cms.bo.admin.Users;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.usermanagement.StudentForgotPasswordForm;
import com.kp.cms.transactions.usermanagement.IStudentForgotPasswordTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class StudentForgotPasswordTransactionImpl implements
		IStudentForgotPasswordTransaction {

	@Override
	public StudentLogin checkValidData(String query) throws Exception {
		Session session = null;
		StudentLogin studentLogin = null;
		try {
			session = HibernateUtil.getSession();
			Query selectedCandidatesQuery=session.createQuery(query);
			studentLogin =(StudentLogin)selectedCandidatesQuery.uniqueResult();
			return studentLogin;
		} catch (Exception e) {
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		
	}

	@Override
	public StudentLogin changePassword(
			StudentForgotPasswordForm studentForgotPasswordForm, String encpass, String iserverPassword)
			throws Exception {
		Session session = null;
		StudentLogin studentLogin = null;
		try {
			session = HibernateUtil.getSession();
			Transaction transaction=session.beginTransaction();
			studentLogin=(StudentLogin)session.get(StudentLogin.class,studentForgotPasswordForm.getStudentLogin().getId());
			studentLogin.setPassword(encpass);
			studentLogin.setIserverPassword(iserverPassword);
			session.update(studentLogin);
			transaction.commit();
			return studentLogin;
		} catch (Exception e) {
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.usermanagement.IStudentForgotPasswordTransaction#checkValidUser(java.lang.String)
	 */
	@Override
	public Users checkValidUser(String query) throws Exception {
		Session session = null;
		Users user = null;
		try {
			session = HibernateUtil.getSession();
			Query selectedCandidatesQuery=session.createQuery(query);
			user =(Users)selectedCandidatesQuery.uniqueResult();
			return user;
		} catch (Exception e) {
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		
	}

}
