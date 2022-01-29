package com.kp.cms.transactionsimpl.usermanagement;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.StudentLogin;
import com.kp.cms.bo.admin.Users;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.usermanagement.ChangePasswordForm;
import com.kp.cms.helpers.usermanagement.Base64Coder;
import com.kp.cms.transactions.usermanagement.IChangePassword;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.EncryptUtil;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.PasswordGenerator;

public class ChangePasswordImpl implements IChangePassword{
	public static ChangePasswordImpl changePasswordImpl = null;
	private static final Log log = LogFactory.getLog(ChangePasswordImpl.class);
	/**
	 * update password method
	 */
	public boolean changePassword(ChangePasswordForm changePasswordForm) throws Exception {
		log.debug("inside changePassword");
		Session session = null;
		Transaction tx = null;
		boolean result = false;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			tx.begin();
			Users users = (Users) session.get(Users.class, Integer.parseInt(changePasswordForm.getUserId()));
			users.setPwd(EncryptUtil.getInstance().encryptDES(changePasswordForm.getNewPwd()));
			users.setAndroidPwd(Base64Coder.encodeString(changePasswordForm.getNewPwd()));
			users.setModifiedBy(changePasswordForm.getUserId());
			users.setLastModifiedDate(new Date());
			if(users.getEmployee()!=null)
				changePasswordForm.setEmail(users.getEmployee().getEmail());
			else if (users.getGuest()!=null) {
				changePasswordForm.setEmail(users.getGuest().getEmail());
			}
			session.update(users);
			tx.commit();
			session.flush();
			session.close();
			result = true;
		} catch (ConstraintViolationException e) {
			tx.rollback();
			log.error("Error in changePassword :impl..." , e);
			throw new BusinessException(e);
		} catch (Exception e) {
			tx.rollback();
			log.error("Error in changePassword impl..",e);
			throw new ApplicationException(e);
		}
		return result;
	}
	/**
	 * checking validation
	 */
	public Users verifyUser(ChangePasswordForm changePasswordForm) throws ApplicationException {
		Session session = null;
		Users user = null;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = HibernateUtil.getSession();
			//session = sessionFactory.openSession();
			String userQueryData = " from Users users where users.userName = :userName and users.pwd = :password "
					+ " and users.isActive = true " +
					" and users.employee.dob = '" + CommonUtil.ConvertStringToSQLDate(changePasswordForm.getDob()) + "'" ;
			Query userQuery = session.createQuery(userQueryData);
			userQuery.setString("userName", changePasswordForm.getTempUname());
			userQuery.setString("password", EncryptUtil.getInstance().encryptDES(changePasswordForm.getExistingPwd()));
			user = (Users) userQuery.uniqueResult();
			if(user==null){
				String userQueryData1 = " from Users users where users.userName = :userName and users.pwd = :password "
					+ " and users.isActive = true " +
					" and users.guest.dob = '" + CommonUtil.ConvertStringToSQLDate(changePasswordForm.getDob()) + "'" ;
			Query userQuery1 = session.createQuery(userQueryData1);
			userQuery1.setString("userName", changePasswordForm.getTempUname());
			userQuery1.setString("password", EncryptUtil.getInstance().encryptDES(changePasswordForm.getExistingPwd()));
			user = (Users) userQuery1.uniqueResult();
			}
		} catch (Exception e) {
			log.error("Error in verifyUser impl.." ,e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		return user;
	}
	
	/**
	 * update password of student who has logged in.
	 */
	public boolean changeStudentPassword(ChangePasswordForm changePasswordForm) throws Exception {
		log.debug("inside changePassword");
		Session session = null;
		Transaction tx = null;
		boolean result = false;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			
			tx = session.beginTransaction();
			tx.begin();
			StudentLogin studentLogin = (StudentLogin) session.get(StudentLogin.class, Integer.parseInt(changePasswordForm.getUserId()));
			studentLogin.setPassword(EncryptUtil.getInstance().encryptDES(changePasswordForm.getNewPwd()));
			studentLogin.setIserverPassword(PasswordGenerator.getPassword().substring(0, 3)+changePasswordForm.getNewPwd());
			studentLogin.setModifiedBy(changePasswordForm.getUserId());
			studentLogin.setLastModifiedDate(new Date());
			studentLogin.setIsTempPassword(false);	
			
			if(studentLogin.getIsStudent()){
				changePasswordForm.setEmail(studentLogin.getStudent().getAdmAppln().getPersonalData().getEmail());
			}
			else{
				changePasswordForm.setEmail(studentLogin.getStudent().getAdmAppln().getPersonalData().getFatherEmail());
			}
			
			session.update(studentLogin);
			tx.commit();
			session.flush();
			session.close();
			result = true;
		} catch (ConstraintViolationException e) {
			tx.rollback();
			log.error("Error in changePassword :impl..." , e);
			throw new BusinessException(e);
		} catch (Exception e) {
			tx.rollback();
			log.error("Error in changePassword impl..", e);
			throw new ApplicationException(e);
		}
		return result;
	}
	/**
	 * checking validation
	 */
	public StudentLogin verifyStudent(ChangePasswordForm changePasswordForm) throws ApplicationException {
		Session session = null;
		StudentLogin studentLogin = null;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			String userQueryData = " from StudentLogin studentLogin where studentLogin.userName = :userName and studentLogin.password = :password "
					+ " and studentLogin.isActive = true and" +
					" studentLogin.student.admAppln.personalData.dateOfBirth = '" + CommonUtil.ConvertStringToSQLDate(changePasswordForm.getDob()) + "'";					
			Query userQuery = session.createQuery(userQueryData);
			userQuery.setString("userName", changePasswordForm.getTempUname());
			userQuery.setString("password", EncryptUtil.getInstance().encryptDES(changePasswordForm.getExistingPwd()));
			studentLogin = (StudentLogin) userQuery.uniqueResult();

		} catch (Exception e) {
			log.error("Error in verifyStudent :impl...", e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		return studentLogin;
	}
	
}
