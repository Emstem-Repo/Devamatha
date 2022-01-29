package com.kp.cms.transactionsimpl.usermanagement;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpSession;
import org.apache.velocity.runtime.directive.Foreach;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.bo.admin.ApplnDoc;
import com.kp.cms.bo.admin.ConvocationRegistration;
import com.kp.cms.bo.admin.Employee;
import com.kp.cms.bo.admin.OpenSyllabusEntry;
import com.kp.cms.bo.admin.PeersEvaluationOpenSession;
import com.kp.cms.bo.admin.PersonalData;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.StudentLogin;
import com.kp.cms.bo.admin.Users;
import com.kp.cms.bo.admission.PublishStudentEdit;
import com.kp.cms.bo.admission.PublishStudentEditClasswise;
import com.kp.cms.bo.examallotment.ExamInviligatorDuties;
import com.kp.cms.bo.sap.SapRegistration;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.admin.SyllabusEntryForm;
import com.kp.cms.forms.usermanagement.LoginForm;
import com.kp.cms.transactions.usermanagement.ILoginTransaction;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.HibernateUtil;

/**
 * An implementation class for the Login transaction.
 * 
 */
public class LoginTransactionImpl implements ILoginTransaction {

	private static final String FROM_DATEFORMAT = "dd/MM/yyyy";
	private static final String SQL_DATEFORMAT = "dd-MMM-yyyy";
	/**
	 * @see com.kp.cms.transactions.usermanagement.ILoginTransaction#verifyUser(com.kp.cms.forms.usermanagement.LoginForm)
	 */
	@Override
	public Users verifyUser(LoginForm loginForm) throws ApplicationException {
		Session session = null;
		Users user = null;
		try {
			// SessionFactory sessionFactory =
			// HibernateUtil.getSessionFactory();
			// session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			String userQueryData="Select users from Users users " +
					"left join users.employee e with e.isActive=1 and e.active=1 " +
					"left join users.guest g with g.isActive=1 and g.active=1 " +
					"where users.userName = :userName and users.pwd = :password " +
					"and users.isActive = true and users.active=true ";
			/*String userQueryData = " from Users users where users.userName = :userName and users.pwd = :password"
					+ " and users.isActive = true and users.active=true and (users.employee.active=true and users.employee.isActive=true)";*/
			/*String userQueryData ="from Users users left join users.employee e with e.isActive=1 and e.active=1 " +
					"left join users.guest g with g.isActive=1 and g.active=1 " +
					"where users.userName = :userName and users.pwd = :password " +
					"and users.isActive = true and users.active=true";*/
			Query userQuery = session.createQuery(userQueryData);
			userQuery.setString("userName", loginForm.getUserName());
			userQuery.setString("password", loginForm.getEncryptedPassword());
			user = (Users) userQuery.uniqueResult();
			if(user==null)
			{
				String userQueryData1 = " from Users users where users.userName = :userName and users.pwd = :password"
					+ " and users.isActive = true and users.active=true and users.employee.id is null";
			Query userQuery1 = session.createQuery(userQueryData1);
			userQuery1.setString("userName", loginForm.getUserName());
			userQuery1.setString("password", loginForm.getEncryptedPassword());
			user = (Users) userQuery1.uniqueResult();
			}
			
			if(user!= null && user.getTillDate()!=null && user.getTillDate().before(new Date()) && !CommonUtil.compareDates(user.getTillDate(), new Date())){
				user=null;
			}
		} catch (Exception e) {
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				// session.close();
			}
		}
		return user;
	}

	/**
	 * @see com.kp.cms.transactions.usermanagement.ILoginTransaction#getAccessableModules
	 *      (com.kp.cms.forms.usermanagement.LoginForm)
	 */
	public List getAccessableModules(LoginForm loginForm)
			throws ApplicationException {

		Session session = null;
		List accessableListModules = null;
		try {
			/*
			 * SessionFactory sessionFactory =
			 * HibernateUtil.getSessionFactory(); session =
			 * sessionFactory.openSession();
			 */
			session = HibernateUtil.getSession();
			if(loginForm.getIsMobileAccess() != null && loginForm.getIsMobileAccess())
			{
				String accessableModuleData = " from Users users inner join users.roles roles inner join roles.accessPrivilegeses accessPrevileges "
					+ " where users.userName = :userName  "
					+ "  and users.pwd = :password "
					+ " and users.isActive = true "
					+ " and users.active=true "
					+ " and accessPrevileges.allowAccess = true "
					+ "  and accessPrevileges.menus.isActive = true "
					+ "  and accessPrevileges.menus.isMenuLink = true "
					+ "  and accessPrevileges.modules.isActive = true "
					+ "  and accessPrevileges.isActive = true   "
                    + "  and accessPrevileges.menus.modules.id="+14
                    +"   and accessPrevileges.menus.id in (88,92,418,847,848,865)"
					+ " order by  accessPrevileges.menus.modules.position , accessPrevileges.menus.position";

			Query accessableModuleQuery = session
					.createQuery(accessableModuleData);
			accessableModuleQuery
					.setString("userName", loginForm.getUserName());
			accessableModuleQuery.setString("password", loginForm
					.getEncryptedPassword());
			accessableListModules = accessableModuleQuery.list();				
				
			}
			else
			{
			String accessableModuleData = " from Users users inner join users.roles roles inner join roles.accessPrivilegeses accessPrevileges "
					+ " where users.userName = :userName  "
					+ "  and users.pwd = :password "
					+ " and users.isActive = true "
					+ " and users.active=true "
					+ " and accessPrevileges.allowAccess = true "
					+ "  and accessPrevileges.menus.isActive = true "
					+ "  and accessPrevileges.menus.isMenuLink = true "
					+ "  and accessPrevileges.modules.isActive = true "
					+ "    and accessPrevileges.isActive = true   "
					+ " order by  accessPrevileges.menus.modules.position , accessPrevileges.menus.position";

			Query accessableModuleQuery = session
					.createQuery(accessableModuleData);
			accessableModuleQuery
					.setString("userName", loginForm.getUserName());
			accessableModuleQuery.setString("password", loginForm
					.getEncryptedPassword());
			accessableListModules = accessableModuleQuery.list();

		}
		}catch (Exception e) {
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				// session.close();
			}
		}

		return accessableListModules;

	}

	/**
	 * @see com.kp.cms.transactions.usermanagement.ILoginTransaction#
	 *      getNonMenuLinksForUser(java.lang.String)
	 */
	public List getNonMenuLinksForUser(String userName)
			throws ApplicationException {

		Session session = null;
		List accessableListModules = null;
		try {
			/*
			 * SessionFactory sessionFactory =
			 * HibernateUtil.getSessionFactory(); session =
			 * sessionFactory.openSession();
			 */
			session = HibernateUtil.getSession();
			String accessableModuleData = " select accessPrevileges.menus.displayName,accessPrevileges.menus.isMenuLink "
					+ " from Users users inner join users.roles roles inner join roles.accessPrivilegeses accessPrevileges "
					+ " where users.id = :userName  "
					+ " and users.isActive = true "
					+ " and users.active = true "
					+ " and accessPrevileges.allowAccess = true "
					+ "  and accessPrevileges.menus.isActive = true "
					+ "  and accessPrevileges.menus.isMenuLink = false "
					+ "  and accessPrevileges.modules.isActive = true "
					+ "  and accessPrevileges.isActive = true   ";

			Query accessableModuleQuery = session
					.createQuery(accessableModuleData);
			accessableModuleQuery.setString("userName", userName);
			accessableListModules = accessableModuleQuery.list();

		} catch (Exception e) {
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				// session.close();
			}
		}
		return accessableListModules;
	}

	/**
	 * @see com.kp.cms.transactions.usermanagement.ILoginTransaction#verifyUser(com.kp.cms.forms.usermanagement.LoginForm)
	 */
	@Override
	public StudentLogin verifyStudentUser(LoginForm loginForm)
			throws ApplicationException {
		Session session = null;
		StudentLogin studentLogin = null;
		try {
			/*
			 * SessionFactory sessionFactory =
			 * HibernateUtil.getSessionFactory(); session =
			 * sessionFactory.openSession();
			 */
			session = HibernateUtil.getSession();
			String userQueryData = " from StudentLogin studentLogin where studentLogin.userName = :userName and studentLogin.password = :password"
					+ " and studentLogin.isActive = true and studentLogin.student.admAppln.isCancelled=0 and (studentLogin.student.isHide = false or studentLogin.student.isHide is null)";
			Query userQuery = session.createQuery(userQueryData);
			userQuery.setString("userName", loginForm.getUserName());
			userQuery.setString("password", loginForm.getEncryptedPassword());
			studentLogin = (StudentLogin) userQuery.uniqueResult();

		} catch (Exception e) {
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				// session.close();
			}
		}
		return studentLogin;
	}

	/**
	 * method to update last logged in
	 */

	public boolean updateLastLoggedIn(int userId) throws Exception {
		Session session = null;
		Transaction tx = null;
		boolean result = false;
		try {
			/*
			 * SessionFactory sessionFactory = InitSessionFactory.getInstance();
			 * session = sessionFactory.openSession();
			 */
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			tx.begin();
			Users users = (Users) session.get(Users.class, userId);
			users.setLastLoggedIn(new Date());
			session.update(users);
			tx.commit();
			session.flush();
			// session.close();
			result = true;
		} catch (ConstraintViolationException e) {
			tx.rollback();
			throw new BusinessException(e);
		} catch (Exception e) {
			tx.rollback();
			throw new ApplicationException(e);
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.kp.cms.transactions.usermanagement.ILoginTransaction#getEmployeeDetails
	 * (java.lang.String)
	 */
	@Override
	public Employee getEmployeeDetails(String employeeId) throws Exception {
		Session session = null;
		Employee employee = null;
		try {
			session = HibernateUtil.getSession();
			String str = "from Employee p where p.id =" + employeeId;
			Query query = session.createQuery(str);
			employee = (Employee) query.uniqueResult();
			session.flush();
		} catch (Exception e) {
			if (session != null) {
				session.flush();
				// session.close();
			}
			throw new ApplicationException(e);
		}
		return employee;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.kp.cms.transactions.usermanagement.ILoginTransaction#getsaveMobileNo
	 * (com.kp.cms.bo.admin.Employee)
	 */
	@Override
	public boolean getsaveMobileNo(Employee emp) throws Exception {
		Session session = null;
		Transaction tx = null;
		boolean isAdded = false;
		try {
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			tx.begin();
			session.update(emp);
			tx.commit();
			session.flush();
			session.close();
			isAdded = true;
		} catch (ConstraintViolationException e) {
			tx.rollback();
			throw new BusinessException(e);
		} catch (Exception e) {
			tx.rollback();
			throw new ApplicationException(e);
		}
		return isAdded;
	}

	
	public Integer getNotificationCount(String userId)throws Exception
	{  
		Session session = null;
	     Integer maxNo = 0;
	     String emp;
		
		try {
			session = HibernateUtil.getSession();
			String str = "select count(e.leaveApprover) from EmpOnlineLeave e " +
					"where e.isApproved=0 and (e.isAuthorized=0 or e.isAuthorized is null) " +
					"and e.status='Applied' and e.isActive=1 " +
					"and e.leaveApprover.id in (select user.employee.id from Users user where user.id = "+userId+")";
			Query query = session.createQuery(str);
			emp = query.uniqueResult().toString();
			maxNo=Integer.parseInt(emp);
		}  catch (Exception e) {
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		return maxNo;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.usermanagement.ILoginTransaction#getMarksEnteryLinks()
	 */
	@Override
	public String getMarksEnteryLinks(String userId,HttpSession s) throws Exception {  
		Session session = null;
	     List<Object> courseIds=new ArrayList<Object>();
	     String displayExamName = "";
		try {
			session = HibernateUtil.getSession();
			java.sql.Date currentDate = CommonUtil.ConvertStringToSQLDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(new Date()),LoginTransactionImpl.SQL_DATEFORMAT,LoginTransactionImpl.FROM_DATEFORMAT));
			String sqlQuery = "select open.display_name " +
			" from EXAM_definition ed " +
			" join EXAM_exam_course_scheme_details ecsd  on ecsd.exam_id = ed.id " +
			" join classes cl on cl.course_id=ecsd.course_id and cl.term_number=ecsd.scheme_no " +
			" join course cou on cl.course_id = cou.id"+
			" join program ON cou.program_id = program.id"+
			" join program_type ON program.program_type_id = program_type.id"+
			" join class_schemewise cls on cls.class_id = cl.id " +
			" join open_internal_marks_entry open on open.program_type_id = program_type.id and open.exam_id = ed.id and open.is_active=1 "+
			" and '"+currentDate+ "' between date(open.start_date) and date(open.end_date)" +
			" join open_internal_exam_classes opencls on open.id = opencls.open_exam_id and opencls.classes_id = cl.id and opencls.is_active=1"+
			" join curriculum_scheme_duration csd on cls.curriculum_scheme_duration_id = csd.id " +
			" join teacher_class_subject tcs on tcs.class_schemewise_id = cls.id " +
			" join subject ON  tcs.subject_id = subject.id and subject.is_active=1 " +
			" and if(open.is_theory_practical='P', subject.is_theory_practical in ('P', 'B'), (if(open.is_theory_practical='T', subject.is_theory_practical in ('T', 'B'), true)))  " +
			" join users u on tcs.teacher_id = u.id " +
			" where u.id= " +userId+
			" and '"+currentDate+"' between  date(csd.start_date) and date(csd.end_date) " +
			" and cl.is_active=1 and tcs.is_active=1 and ed.del_is_active=1 " +
			" and subject.name != 'HOLISTIC EDUCATION'" +
			" and ecsd.is_active=1 and u.active=1 " +
			" group by open.id "+
			" order by ed.name,cl.name,subject.name";
			Query query2=session.createSQLQuery(sqlQuery);
			courseIds = query2.list();
			Iterator<Object> iterator = courseIds.iterator();
			while (iterator.hasNext()) {
				Object object = (Object) iterator.next(); 
				if(displayExamName!=null && !displayExamName.isEmpty()){
					if(!displayExamName.equalsIgnoreCase((String) object)){
						displayExamName =displayExamName+ "/" +(String) object;
					}
				}else{
					displayExamName =(String) object;
				}
			}
		}  catch (Exception e) {
			return displayExamName;
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		return displayExamName;
	}

	public PeersEvaluationOpenSession getOpenSession(String departmentId)
	throws Exception {
Session session=null;
PeersEvaluationOpenSession openSession;
try{
	session = HibernateUtil.getSession();
	String str = "from PeersEvaluationOpenSession connection where connection.isActive = 1 " +
					"and connection.departmentId.id="+departmentId+" and " +
					"'"+CommonUtil.ConvertStringToSQLDate(CommonUtil.getTodayDate())+"' between connection.startDate and connection.endDate";
	Query query = session.createQuery(str);
	openSession = (PeersEvaluationOpenSession) query.uniqueResult();
}catch (Exception e) {
	throw new ApplicationException(e);
} finally {
	if (session != null) {
		session.flush();
	}
}
return openSession;
}
	
	
	
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.usermanagement.ILoginTransaction#getMarksEnteryLinks()
	 */
	@Override
	public List<Object> getResearchLinks(String userId,HttpSession s) throws Exception {  
		Session session = null;
	     List<Object> courseIds=new ArrayList<Object>();
		
		try {
			session = HibernateUtil.getSession();
			String str ="from EmpResearchPublicDetails r where r.approverId=(select u.employee.id from Users u where u.id= "+ userId +") and r.isApproved=0 and r.isActive=1";
			Query query = session.createQuery(str);
			courseIds = query.list();
		}  catch (Exception e) {
			return courseIds;
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		return courseIds;
	}

	@Override
	public String getDepartmentByUserId(String id) throws Exception {

		Session session = null;
        Integer departmentId;
        String strDepartmentId=null;
		try {
			/*
			 * SessionFactory sessionFactory =
			 * HibernateUtil.getSessionFactory(); session =
			 * sessionFactory.openSession();
			 */
			session = HibernateUtil.getSession();
			String strQuery = "select user.department.id from Users user where user.id="+id+" and user.isActive='1'";

			Query query = session
					.createQuery(strQuery);
		
			departmentId = (Integer) query.uniqueResult();
			if(departmentId==null )
			{
				String strQuery1 = "select user.employee.department.id from Users user where user.id="+id+" and user.isActive='1'";

				Query query1 = session
						.createQuery(strQuery1);
				departmentId =   (Integer) query1.uniqueResult();
			}
			strDepartmentId=String.valueOf(departmentId);
		} catch (Exception e) {
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				// session.close();
			}
		}

		return strDepartmentId;

	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.usermanagement.ILoginTransaction#getStudentIdByUserName(com.kp.cms.forms.usermanagement.LoginForm)
	 */
	@Override
	public int getStudentIdByUserName(LoginForm loginForm) throws Exception {

		Session session = null;
        Integer studentId;
		try {
			/*
			 * SessionFactory sessionFactory =
			 * HibernateUtil.getSessionFactory(); session =
			 * sessionFactory.openSession();
			 */
			session = HibernateUtil.getSession();
			String strQuery = "select student.student.id from StudentLogin student where student.userName='"+loginForm.getUserName()+"' and student.isActive=1";

			Query query = session
					.createQuery(strQuery);
		
			studentId = (Integer) query.uniqueResult();
			
		} catch (Exception e) {
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				// session.close();
			}
		}

		return studentId;

	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.usermanagement.ILoginTransaction#checkStudentIsFinalYearOrNot(int)
	 */
	@Override
	public boolean checkStudentIsFinalYearOrNot(int studentId) throws Exception {
		Session session=null;
		String str="";
		boolean finalYear=false;
		try {
			session=HibernateUtil.getSession();
			str=" select * from student"+
                  " inner join class_schemewise ON student.class_schemewise_id = class_schemewise.id "+
                  " inner join curriculum_scheme_duration ON class_schemewise.curriculum_scheme_duration_id = curriculum_scheme_duration.id "+
                  " inner join curriculum_scheme ON curriculum_scheme_duration.curriculum_scheme_id = curriculum_scheme.id "+
                  " where student.id ="+studentId+" and curriculum_scheme.no_scheme = curriculum_scheme_duration.semester_year_no and "+
                  " student.id not in ( "+
                  " select student_id from EXAM_student_detention_rejoin_details "+ 
                  " where (detain=1 or discontinued=1) and (rejoin is null or rejoin=0)) ";

			
			Object student= session.createSQLQuery(str).uniqueResult();
			if(student!=null)
			{
				finalYear=true;
			}
			}catch (Exception e) {
				if (session != null){
					session.flush();
					//session.close();
				}	
				throw  new ApplicationException(e);
			}
		
			return finalYear;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.usermanagement.ILoginTransaction#getAcademicYearByStudentRegNo(javax.servlet.http.HttpSession)
	 */
	@Override
	public int getAcademicYearByStudentRegNo(HttpSession httpSession) throws Exception {
		Session session = null;
        int academicYear;
		try {
			/*
			 * SessionFactory sessionFactory =
			 * HibernateUtil.getSessionFactory(); session =
			 * sessionFactory.openSession();
			 */
			session = HibernateUtil.getSession();
			String strQuery = "select student.classSchemewise.curriculumSchemeDuration.academicYear from Student student where student.id="+httpSession.getAttribute("studentIdforConvocation")+" and student.isActive=1";

			Query query = session
					.createQuery(strQuery);
		
			academicYear =   (Integer) query.uniqueResult();
			
		} catch (Exception e) {
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				// session.close();
			}
		}

		return academicYear;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.usermanagement.ILoginTransaction#saveConvocationRegistration(com.kp.cms.bo.admin.ConvocationRegistration)
	 */
	@Override
	public boolean saveConvocationRegistration(
			ConvocationRegistration registration) throws Exception {
		Session session = null;
		Transaction tx = null;
		boolean isAdded = false;
		try {
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			tx.begin();
			session.saveOrUpdate(registration);
			tx.commit();
			session.flush();
			session.close();
			isAdded = true;
		
		}catch (Exception e) {
			tx.rollback();
			throw new ApplicationException(e);
		}
		return isAdded;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.usermanagement.ILoginTransaction#loadConvocationRegistration(javax.servlet.http.HttpSession)
	 */
	@Override
	public ConvocationRegistration loadConvocationRegistration(
			HttpSession httpSession) throws Exception {
		Session session = null;
		ConvocationRegistration convocationRegistration;
		try {
			/*
			 * SessionFactory sessionFactory =
			 * HibernateUtil.getSessionFactory(); session =
			 * sessionFactory.openSession();
			 */
			session = HibernateUtil.getSession();
			String strQuery = "from ConvocationRegistration con where con.student.id="+httpSession.getAttribute("studentIdforConvocation")+" and con.isActive=1";

			Query query = session.createQuery(strQuery);
		
			convocationRegistration =  (ConvocationRegistration) query.uniqueResult();
			
		} catch (Exception e) {
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				// session.close();
			}
		}

		return convocationRegistration;
	}

	@Override
	public void setIsLoggedIn(Users users) throws Exception {
		Session session = null;
		Transaction tx = null;
		try {
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			tx.begin();
			session.update(users);
			tx.commit();
		}catch (Exception e) {
			tx.rollback();
			throw new ApplicationException(e);
		}
	}
	public void getUserAndSetLoggedIn(int userId) throws Exception
	{
		Session session = null;
		Transaction tx = null;
		Users users;
		try {
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			tx.begin();
			String strQuery = "from Users users where users.isActive=1 and users.id="+userId;

			Query query = session.createQuery(strQuery);
		
			users =   (Users) query.uniqueResult();
			if(users!=null)
			{
			users.setIsLoggedIn(false);
			session.merge(users);
			tx.commit();
			session.flush();
			session.close();
		}
		}catch (Exception e) {
			tx.rollback();
			throw new ApplicationException(e);
		}
	}
	public SapRegistration LoadSapRegistration(LoginForm loginform) throws Exception {
		Session session = null;
		SapRegistration sapRegistration;
		try {
			session = HibernateUtil.getSession();
			String strQuery = "from SapRegistration con where con.stdId="+loginform.getStudentId()+" and con.isActive=1";

			Query query = session.createQuery(strQuery);
		
			sapRegistration =  (SapRegistration) query.uniqueResult();
			
		} catch (Exception e) {
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				// session.close();
			}
		}

		return sapRegistration;
	}
	
	public boolean saveSapRegistration(SapRegistration registration) throws Exception {
		Session session = null;
		Transaction tx = null;
		boolean isAdded = false;
		try {
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			tx.begin();
			session.saveOrUpdate(registration);
			tx.commit();
			session.flush();
			session.close();
			isAdded = true;
		
		}catch (Exception e) {
			tx.rollback();
			throw new ApplicationException(e);
		}
		return isAdded;
	}

	
	@Override
	public List<Object[]> invigilationDutyAllotmentDetails(int userId)
			throws Exception {  
		Session session = null;
	     List<Object[]> dutyAllotmentDetails=new ArrayList<Object[]>();
		
		try {
			session = HibernateUtil.getSession();
			java.sql.Date currentDate = CommonUtil.ConvertStringToSQLDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(new Date()),LoginTransactionImpl.SQL_DATEFORMAT,LoginTransactionImpl.FROM_DATEFORMAT));
			String sqlQuery = "select concat('Room: ', group_concat(room_master.room_no order by room_master.room_no), ' (', floor_name, ' ', block_name, ')') as rooms,"+
								" if(inviligator_or_reliver='I', 'Invigilator', 'Reliever') as type,"+
								" exam_date, EXAM_sessions.session "+
								" from EXAM_invigilator_duties"+
								" inner join EXAM_sessions ON EXAM_invigilator_duties.exam_session_id = EXAM_sessions.id"+
								" inner join EXAM_definition ON EXAM_invigilator_duties.exam_id = EXAM_definition.id"+
								" inner join emp_work_location ON EXAM_invigilator_duties.work_location_id = emp_work_location.id"+
								" inner join room_master ON EXAM_invigilator_duties.room_id = room_master.id"+
								" inner join block on block.id=room_master.block_id"+
								" inner join users ON EXAM_invigilator_duties.teacher_id = users.id"+
								" left join employee ON users.employee_id = employee.id"+
								" left join department on department.id=employee.department_id"+
								" where EXAM_invigilator_duties.is_active=1 and is_approved=1"+
								" and exam_date between DATE_SUB( CURDATE(), INTERVAL 1 MONTH ) and DATE_ADD( CURDATE(), INTERVAL 1 MONTH )"+
								" and teacher_id="+userId+
								" group by exam_date, EXAM_sessions.id"+
								" order by exam_date, EXAM_sessions.id,type";
			Query query2=session.createSQLQuery(sqlQuery);
			dutyAllotmentDetails = query2.list();
		}  catch (Exception e) {
			return dutyAllotmentDetails;
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		return dutyAllotmentDetails;
	}	
	
	
	public Integer getDutyAllotmentDetailsSize(int userId)throws Exception {  
	
			Session session = null;
			 List<ExamInviligatorDuties> dutyAllotmentDetails=new ArrayList<ExamInviligatorDuties>();

		try {
			session = HibernateUtil.getSession();
			Date date=new Date();
		    Calendar cl=Calendar.getInstance();
		    cl.setTime(date);
		    int year=cl.get(Calendar.YEAR);
		    int month=cl.get(Calendar.MONTH);
		    int day=cl.get(Calendar.DAY_OF_MONTH);
		    int beforeOneMonth=month;
		   int afterOneMonth=month+2;
		   int currentMonth=month+1;
		   String afterMonth="";
		   
		   if(currentMonth !=0  && currentMonth==12){
			   int nextYear=year+1;
			   int nextYearMonth=01;
			  afterMonth=String.valueOf(nextYear)+"-"+String.valueOf(nextYearMonth)+"-"+String.valueOf(day);
		   }else{
			   afterMonth=String.valueOf(year)+"-"+String.valueOf(afterOneMonth)+"-"+String.valueOf(day);
		   }
		   String beforeMonth="";
		   if(currentMonth !=0  && currentMonth==1){
			   int priviousYear=year-1;
			   int priviousYearMonth=12;
			  beforeMonth=String.valueOf(priviousYear)+"-"+String.valueOf(priviousYearMonth)+"-"+String.valueOf(day);
		   }else{
			  beforeMonth=String.valueOf(year)+"-"+String.valueOf(beforeOneMonth)+"-"+String.valueOf(day);
		   }
		    //String beforeMonth=String.valueOf(year)+"-"+String.valueOf(beforeOneMonth)+"-"+String.valueOf(day);
			//String afterMonth=String.valueOf(year)+"-"+String.valueOf(afterOneMonth)+"-"+String.valueOf(day);
			
			String sqlQuery = " select eID from ExamInviligatorDuties eID"+
								" where eID.isActive=1 and eID.isApproved=1 "+
								" and  eID.teacherId.id="+userId+
								" and eID.examDate between '"+beforeMonth+"' and '"+afterMonth+"'";
			Query query2=session.createQuery(sqlQuery);
			dutyAllotmentDetails = query2.list();
		}  catch (Exception e) {
			return dutyAllotmentDetails.size();
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		return dutyAllotmentDetails.size();
	}

	@Override
	public boolean checkForSyllabusEntryOpen(SyllabusEntryForm syllabusEntryForm,
			java.sql.Date date) throws Exception {
		boolean flag=false;
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from OpenSyllabusEntry o where o.isActive=1 " +
					" and '"+date+"' between o.startDate and o.endDate ORDER BY o.batch DESC");
			List<OpenSyllabusEntry> list=query.list();
			if(list!=null && !list.isEmpty()){
				List<String> batchList=new ArrayList<String>();
				for (OpenSyllabusEntry openSyllabusEntry : list) {
					batchList.add(String.valueOf(openSyllabusEntry.getBatch()));
				}
				syllabusEntryForm.setBatchYearList(batchList);
				flag=true;
			}
		} catch (Exception e) {
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				// session.close();
			}
		}
		return flag;
	}	
	
	
	@Override
	public List<Object[]> getAttendaceGrpah(String grpahQuery) throws Exception {
		Session session = null;
		List<Object[]> list = new ArrayList<Object[]>();
		try
		{
			session  = HibernateUtil.getSession();
			Query query = session.createSQLQuery(grpahQuery);
			list = query.list();
		}
		catch (Exception e) {
			// TODO: handle exception
			throw new ApplicationException(e);
		}
		
		return list;
	}

	@Override
	public Student getStudentObj(String studentid) throws Exception {
		Student student =new Student();
		Session session=null;
		try{
			session=HibernateUtil.getSession();
			String string ="from Student student where student.isActive = 1 and student.id=:stuId and student.admAppln.isCancelled=0";
			Query query = session.createQuery(string)
								 .setInteger("stuId", Integer.parseInt(studentid));
			student =(Student) query.uniqueResult();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return student;
	}

	@Override
	public boolean savePersonalData(PersonalData data) throws Exception {
		boolean result = false;
		Session session = null;
		Transaction txn=null;
		try{
			session=HibernateUtil.getSession();
			txn=session.beginTransaction();
			session.update(data);
			txn.commit();
			session.close();
			result=true;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public boolean saveStudentPhoto(ApplnDoc docBO) throws Exception {
		boolean result = false;
		Session session = null;
		Transaction txn=null;
		try{
			session=HibernateUtil.getSession();
			txn=session.beginTransaction();
			session.saveOrUpdate(docBO);
			txn.commit();
			result=true;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public PublishStudentEditClasswise getClasswise(int classId)
			throws Exception {
		Session session =null;
		PublishStudentEditClasswise classwise = new PublishStudentEditClasswise();
		try{
			session=HibernateUtil.getSession();
			String string= "from PublishStudentEditClasswise p where p.classes.id= " + classId + " and  p.isActive=1 " +
							" and ('"+CommonUtil.ConvertStringToSQLDate(CommonUtil.getTodayDate())+"' between p.startDate and p.endDate)";
			Query query= session.createQuery(string);
			classwise = (PublishStudentEditClasswise)query.uniqueResult();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return classwise;
	}

	@Override
	public PublishStudentEdit getStudentWise(String studentid) throws Exception {
		Session session= null;
		PublishStudentEdit edit = new PublishStudentEdit();
		try{
			session =HibernateUtil.getSession();
			String string ="from PublishStudentEdit p where p.student.id= " + studentid + " and p.isActive=1 " +
						   " and ('"+CommonUtil.ConvertStringToSQLDate(CommonUtil.getTodayDate())+"' between p.startDate and p.endDate)";
			Query query = session.createQuery(string);
			edit= (PublishStudentEdit)query.uniqueResult();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return edit;
	}
	@Override
	public boolean SaveAdmAppl(AdmAppln admappln) throws Exception {
		boolean result = false;
		Session session = null;
		Transaction txn=null;
		try{
			session=HibernateUtil.getSession();
			txn=session.beginTransaction();
			session.update(admappln);
			txn.commit();
			result=true;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
}
