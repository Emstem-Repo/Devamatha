package com.kp.cms.transactionsimpl.employee;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.Employee;
import com.kp.cms.bo.admin.Users;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.transactions.employee.IEmployeeInfoTransaction;
import com.kp.cms.utilities.HibernateUtil;

/**
 * IMPL. CLASS FOR EMPLOYEE INFORMATION TRANSACTION
 * 
 */
public class EmployeeInfoTransactionImpl implements IEmployeeInfoTransaction {
	private static final Log log = LogFactory
			.getLog(EmployeeInfoTransactionImpl.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.kp.cms.transactions.employee.IEmployeeInfoTransaction#getEmployeeDetails
	 * (java.lang.String, java.lang.String, java.lang.String, java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public Employee getEmployeeDetails(String code, String lastName,
			String firstName, String middleName, String nickName)
			throws Exception {
		// SessionFactory sessionFactory = InitSessionFactory.getInstance();
		Session session = null;
		Employee emp = null;
		try {
			StringBuffer query = new StringBuffer(
					"from Employee e where e.isActive=1 ");
			if (firstName != null && !StringUtils.isEmpty(firstName)) {
				query.append(" and e.firstName LIKE '" + firstName + "%'");
			}
			if (lastName != null && !StringUtils.isEmpty(lastName)) {
				query.append(" and e.lastName LIKE '" + lastName + "%'");
			}

			if (code != null && !StringUtils.isEmpty(code)) {
				query.append(" and e.code='" + code + "'");
			}
			if (middleName != null && !StringUtils.isEmpty(middleName)) {
				query.append(" and e.middleName LIKE '" + middleName + "%'");
			}
			if (nickName != null && !StringUtils.isEmpty(nickName)) {
				query.append(" and e.nickName LIKE '" + nickName + "%'");
			}

			// session =sessionFactory.openSession();
			session = HibernateUtil.getSession();
			Query queri = session.createQuery(query.toString());
			List<Employee> stList = queri.list();
			if (stList != null && !stList.isEmpty())
				emp = stList.get(0);
			session.flush();
			// session.close();
			// sessionFactory.close();
		} catch (Exception e) {
			session.flush();
			session.close();
			// sessionFactory.close();
			throw new ApplicationException(e);
		}
		return emp;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.kp.cms.transactions.employee.IEmployeeInfoTransaction#
	 * saveEmployeePersonalInfo(com.kp.cms.bo.admin.Employee)
	 */
	@Override
	public boolean saveEmployeePersonalInfo(Employee employee) throws Exception {
		boolean result = false;
		Session session = null;
		Transaction txn = null;
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			// session =sessionFactory.openSession();
			session = HibernateUtil.getSession();
			txn = session.beginTransaction();
			session.saveOrUpdate(employee);
			txn.commit();
			session.flush();
			session.close();
			// sessionFactory.close();
			result = true;
		} catch (ConstraintViolationException e) {
			if (session.isOpen()) {
				txn.rollback();
			}
			log.error("Error during saveEmployeePersonalInfo..." + e);
			throw new BusinessException(e);
		} catch (Exception e) {
			if (session.isOpen()) {
				txn.rollback();
			}
			log.error("Error during saveEmployeePersonalInfo..." + e);
			throw new ApplicationException(e);
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.kp.cms.transactions.employee.IEmployeeInfoTransaction#getAllEmployees
	 * ()
	 */
	@Override
	public List<Employee> getAllEmployees() throws Exception {
		List<Employee> employees = null;
		// SessionFactory sessionFactory = InitSessionFactory.getInstance();
		Session session = null;
		Employee emp = null;
		try {

			// session =sessionFactory.openSession();
			session = HibernateUtil.getSession();
			Query queri = session
					.createQuery("from Employee e where e.isActive=1");
			List<Employee> stList = queri.list();
			if (stList != null && !stList.isEmpty())
				employees = stList;
			session.flush();
			// session.close();
			// sessionFactory.close();
		} catch (Exception e) {
			session.flush();
			session.close();
			// sessionFactory.close();
			throw new ApplicationException(e);
		}
		return employees;

	}

	public List<Object[]> getEmployeeInfoDetails(String code, String lastName,
			String firstName, String middleName, String nickName)
			throws Exception {

		Session session = null;
		List<Object[]> stList = null;
		try {
			StringBuilder query = new StringBuilder(
					"select e.code, e.firstName, e.middleName, e.lastName, e.id "
							+ "from Employee e join e.userses u where e.isActive=1 and u.isActive=1");
			if (code != null && !StringUtils.isEmpty(code)) {
				query.append(" and e.code='" + code + "'");
			}
			if (firstName != null && !StringUtils.isEmpty(firstName)) {
				query.append(" and e.firstName LIKE '" + firstName + "%'");
			}
			if (middleName != null && !StringUtils.isEmpty(middleName)) {
				query.append(" and e.middleName LIKE '" + middleName + "%'");
			}
			if (lastName != null && !StringUtils.isEmpty(lastName)) {
				query.append(" and e.lastName LIKE '" + lastName + "%'");
			}
			if (nickName != null && !StringUtils.isEmpty(nickName)) {
				query.append(" and e.nickName LIKE '" + nickName + "%'");
			}

			// session =sessionFactory.openSession();
			session = HibernateUtil.getSession();
			Query queri = session.createQuery(query.toString());
			stList = queri.list();
			if (stList != null && !stList.isEmpty())
				session.flush();
		} catch (Exception e) {
			session.flush();
			session.close();
			throw new ApplicationException(e);
		}
		return stList;
	}

	public Integer addUserInfo(Users users) throws Exception {
		Session session = null;
		Transaction transaction = null;
		Integer id = null;
		try {
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();

			if (users != null) {
				session.save(users);

			}
			id = users.getEmployee().getId();
			transaction.commit();
		} catch (ConstraintViolationException e) {
			if (transaction != null) {
				transaction.rollback();
			}
			log.error("Error while saving interview result data..", e);
			throw new DuplicateException(e);
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			log.error("Error while saving interview result data ...", e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();

			}
		}
		return id;
	}

	public boolean isUserNameDuplicated(String userName,int employeeId) throws Exception {
		log.debug("inside isUserNameDuplcated");
		Session session = null;
		boolean result = false;
		try {
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from Users u where userName = :uName and employee <> :employeeId and u.isActive=1");
			query.setString("uName", userName);
			query.setInteger("employeeId",employeeId);
			List<Users> list = query.list();
			session.flush();
			log.debug("leaving isUserNameDuplicated");
			if (!list.isEmpty()) {
				result = true;
			}
		} catch (Exception e) {
			log.error("Error in duplication checking...", e);
			session.flush();
			throw new ApplicationException(e);
		}
		return result;
	}

	public int getUserId(int id) throws Exception {
		log.debug("inside isUserNameDuplcated");
		Session session = null;
		int userId = 0;
		try {
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("select u.id from Users u where u.employee.id="+ id+"  and u.isActive=1");
			Object obj = query.uniqueResult();

			if (obj != null)
				userId = (Integer) obj;
			session.flush();
			log.debug("leaving isUserNameDuplcated");

		} catch (Exception e) {
			log.error("Error in duplication checking...", e);
			session.flush();
			throw new ApplicationException(e);
		}
		return userId;
	}

	public Employee getEmployeeDetailsByEmployeeId(int employeeId)
			throws Exception {
		Session session = null;
		Employee emp = null;
		try {
			session = HibernateUtil.getSession();
			Query querey = session
					.createQuery("from Employee e where e.isActive=1 and e.id="
							+ employeeId);
			emp = (Employee) querey.uniqueResult();
			if (emp != null)
				session.flush();
		} catch (Exception e) {
			session.flush();
			session.close();
			throw new ApplicationException(e);
		}
		return emp;
	}
	
	public Users getUserDetailsByEmployeeId(int employeeId)throws Exception 
	{
		Session session = null;
		Users user = null;
		try
		{
			session = HibernateUtil.getSession();
			Query querey = session.createQuery("from Users e where e.isActive=1 and e.employee="+ employeeId);
			user = (Users) querey.uniqueResult();
			if (user != null)
				session.flush();
		}
		catch (Exception e) {
			session.flush();
			session.close();
			throw new ApplicationException(e);
		}
		return user;
	}
	
	public boolean isFingerDuplicated(String fingerPrintId,int employeeId)throws Exception
	{
		log.debug("inside isUserNameDuplcated");
		Session session = null;
		boolean result = false;
		try {
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from Employee e where fingerPrintId = :printId and id <> :employeeId");
			query.setString("printId", fingerPrintId);
			query.setInteger("employeeId",employeeId);
			List<Users> list = query.list();
			session.flush();
			log.debug("leaving isUserNameDuplicated");
			if (!list.isEmpty()) {
				result = true;
			}
		} catch (Exception e) {
			log.error("Error in duplication checking...", e);
			session.flush();
			throw new ApplicationException(e);
		}
		return result;
		
	}
	
	public boolean isCodeDuplicated(String newCode, int id)throws Exception
	{
		log.debug("inside isUserNameDuplcated");
		Session session = null;
		boolean result = false;
		try {
			session = HibernateUtil.getSession();
			String strQuery="";
			if(id==0)
				strQuery="from Employee e where code = :code";
			else
				strQuery="from Employee e where code = :code and id <> :employeeId";
			Query query = session.createQuery(strQuery);
			query.setString("code",newCode);
			if(id!=0)
				query.setInteger("employeeId",id);
			List<Users> list = query.list();
			session.flush();
			log.debug("leaving isUserNameDuplicated");
			if (!list.isEmpty()) {
				result = true;
			}
		} catch (Exception e) {
			log.error("Error in duplication checking...", e);
			session.flush();
			throw new ApplicationException(e);
		}
		return result;
	}
}
