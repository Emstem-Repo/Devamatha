package com.kp.cms.transactionsimpl.usermanagement;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.Department;
import com.kp.cms.bo.admin.Employee;
import com.kp.cms.bo.admin.Users;
import com.kp.cms.bo.employee.GuestFaculty;
import com.kp.cms.bo.exam.IExamGenBO;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.transactions.usermanagement.IUserInfoTransaction;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.HibernateUtil;

public class UserInfoTransactionImpl implements IUserInfoTransaction {

	private static final Log log = LogFactory
			.getLog(UserInfoTransactionImpl.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.kp.cms.transactions.usermanagement.IUserInfoTransaction#getDepartment
	 * ()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map<Integer, String> getDepartment() throws Exception {
		Map<Integer, String> departmentMap = new HashMap<Integer, String>();
		Session session = null;
		try {
			// SessionFactory sessionFactory =
			// HibernateUtil.getSessionFactory();
			// session = sessionFactory.openSession();
			session = HibernateUtil.getSession();

			String referredByHQ = "select id, name from Department where isActive = 1 order by name";
			Query referredByQuery = session.createQuery(referredByHQ);

			Iterator iterator = referredByQuery.iterate();
			while (iterator.hasNext()) {
				Object[] row = (Object[]) iterator.next();

				departmentMap.put((Integer) row[0], (String) row[1].toString());
			}
		} catch (Exception e) {
			log.error("Error while getting department...", e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				// session.close();
			}
		}
		departmentMap = (Map<Integer, String>)CommonUtil.sortMapByValue(departmentMap);
		return departmentMap;
	}
	
	public Map<Integer, String> getDepartmentMap() throws Exception {
		Session session=null;
		Map<Integer, String> map=new HashMap<Integer, String>();
		try{
			session=HibernateUtil.getSession();
			Query query=session.createQuery("from Department d where d.isActive=true");
			List<Department> list=query.list();
			if(list!=null){
				Iterator<Department> iterator=list.iterator();
				while(iterator.hasNext()){
					Department department=iterator.next();
					if(department.getId()!=0 && department.getName()!=null && !department.getName().isEmpty())
					map.put(department.getId(),department.getName());
				}
			}
		}catch (Exception exception) {
			
			throw new ApplicationException();
		}finally{
			if(session!=null){
				session.flush();
			}
		}
		map = (Map<Integer, String>) CommonUtil.sortMapByValue(map);
		return map;
	}
	
	
	public Map<Integer, String> getGuestMap() throws Exception {
		Session session=null;
		Map<Integer, String> map=new HashMap<Integer, String>();
		try{
			session=HibernateUtil.getSession();
			Query query=session.createQuery("from GuestFaculty d where d.isActive=true and d.active=1");
			List<GuestFaculty> list=query.list();
			if(list!=null){
				Iterator<GuestFaculty> iterator=list.iterator();
				while(iterator.hasNext()){
					GuestFaculty gf=iterator.next();
					if(gf.getId()!=0 && gf.getFirstName()!=null && !gf.getFirstName().isEmpty())
					map.put(gf.getId(),gf.getFirstName());
				}
			}
		}catch (Exception exception) {
			
			throw new ApplicationException();
		}finally{
			if(session!=null){
				session.flush();
			}
		}
		map = (Map<Integer, String>) CommonUtil.sortMapByValue(map);
		return map;
	}
	

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.kp.cms.transactions.usermanagement.IUserInfoTransaction#getDesignation
	 * ()
	 */
	@Override
	public Map<Integer, String> getDesignation() throws Exception {
		Map<Integer, String> designationMap = new HashMap<Integer, String>();
		Session session = null;
		try {
			// SessionFactory sessionFactory =
			// HibernateUtil.getSessionFactory();
			// session = sessionFactory.openSession();
			session = HibernateUtil.getSession();

			String referredByHQ = "select id, name from Designation where isActive = 1";
			Query referredByQuery = session.createQuery(referredByHQ);

			Iterator iterator = referredByQuery.iterate();
			while (iterator.hasNext()) {
				Object[] row = (Object[]) iterator.next();

				designationMap
						.put((Integer) row[0], (String) row[1].toString());
			}
		} catch (Exception e) {
			log.error("Error while getting roles...", e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				// session.close();
			}
		}
		return designationMap;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.kp.cms.transactions.usermanagement.IUserInfoTransaction#getRoles()
	 */
	@Override
	public Map<Integer, String> getRoles() throws Exception {
		Map<Integer, String> rolesMap = new HashMap<Integer, String>();
		Session session = null;
		try {
			// SessionFactory sessionFactory =
			// HibernateUtil.getSessionFactory();
			// session = sessionFactory.openSession();
			session = HibernateUtil.getSession();

			String referredByHQ = "select id, name from Roles where isActive = 1";
			Query referredByQuery = session.createQuery(referredByHQ);

			Iterator iterator = referredByQuery.iterate();
			while (iterator.hasNext()) {
				Object[] row = (Object[]) iterator.next();

				rolesMap.put((Integer) row[0], (String) row[1].toString());
			}
		} catch (Exception e) {
			log.error("Error while getting roles...", e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				// session.close();
			}
		}
		rolesMap = (Map<Integer, String>) CommonUtil.sortMapByValue(rolesMap);
		return rolesMap;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.kp.cms.transactions.usermanagement.IUserInfoTransaction#addUserInfo
	 * (com.kp.cms.bo.admin.Users)
	 */
	@Override
	public boolean addUserInfo(Users users, String mode)
			throws DuplicateException, Exception {
		Session session = null;
		Transaction transaction = null;
		try {
			// SessionFactory sessionFactory =
			// HibernateUtil.getSessionFactory();
			// session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();

			if (users != null) {
				if (mode.equalsIgnoreCase("add")) {
					Employee employee=users.getEmployee();
					session.saveOrUpdate(employee);
					users.setEmployee(employee);
					session.save(users);
					
				} else {
					session.merge(users);
				}
			}
			transaction.commit();
		} catch (ConstraintViolationException e) {e.printStackTrace();
			if (transaction != null) {
				transaction.rollback();
			}
			log.error("Error while saving interview result data..", e);
			throw new DuplicateException(e);
		} catch (Exception e) {e.printStackTrace();
			if (transaction != null) {
				transaction.rollback();
			}
			log.error("Error while saving interview result data ...", e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				// session.close();
			}
		}
		return true;
	}

	public List<Users> getTeachingStaffs() throws Exception {
		log.debug("Txn Impl : Entering getTeachingStaffs ");
		Session session = null;
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			// session =sessionFactory.openSession();
			session = HibernateUtil.getSession();

			Query query = session
					.createQuery("from Users where isTeachingStaff = 1 and isActive=1 order by userName");
			List<Users> list = query.list();
			// session.close();
			// sessionFactory.close();
			log.debug("Txn Impl : Leaving getTeachingStaffs with success");
			return list;
		} catch (Exception e) {
			log.debug("Txn Impl : Leaving getTeachingStaffs with Exception");
			throw e;
		}
	}

	/**
	 * 
	 */
	public List<Users> getUsers(String dob, String firstName,
			String middleName, String lastName, String dep) throws Exception {
		log.debug("Txn Impl : getUsers");
		Session session = null;
		Boolean sqlStringAdded = false;
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			// session =sessionFactory.openSession();
			session = HibernateUtil.getSession();
			StringBuffer sqlString = new StringBuffer("from Users ");
			if (firstName != null && !firstName.trim().isEmpty()) {
				sqlString = sqlString
						.append(" where employee.firstName LIKE '%" + firstName
								+ "%'");
				sqlStringAdded = true;
			}
			if (middleName != null && !middleName.trim().isEmpty()) {
				if (sqlStringAdded) {
					sqlString = sqlString.append(" OR");
				} else {
					sqlString = sqlString.append(" where");
				}

				sqlString = sqlString.append(" employee.middleName LIKE '%"
						+ middleName + "%'");
				sqlStringAdded = true;
			}

			if (lastName != null && !lastName.trim().isEmpty()) {
				if (sqlStringAdded) {
					sqlString = sqlString.append(" OR");
				} else {
					sqlString = sqlString.append(" where");
				}

				sqlString = sqlString.append(" employee.lastName LIKE '%"
						+ lastName + "%'");
				sqlStringAdded = true;
			}

			if (dob != null && !dob.trim().isEmpty()) {
				if (sqlStringAdded) {
					sqlString = sqlString.append(" and");
				} else {
					sqlString = sqlString.append(" where");
				}
				sqlString = sqlString.append(" employee.dob = '"
						+ CommonUtil.ConvertStringToSQLDate(dob) + "'");
				sqlStringAdded = true;
			}
			if (dep != null && !dep.trim().isEmpty()) {
				if (sqlStringAdded) {
					sqlString = sqlString.append(" and");
				} else {
					sqlString = sqlString.append(" where");
				}
				sqlString = sqlString.append(" employee.department.id = '"
						+ dep + "'");
				sqlStringAdded = true;
			}
			if(sqlStringAdded)
			{
				sqlString=sqlString.append(" and isActive=1 and active=1");
			}
			else
			{
				sqlString=sqlString.append(" where isActive=1 and active=1");
			}
			Query query = session.createQuery(sqlString.toString());
			// query.setDate("dateOfB", CommonUtil.ConvertStringToSQLDate(dob));
			List<Users> list = query.list();
			// session.close();
			// sessionFactory.close();
			log.debug("Txn Impl : Leaving getUsers with success");
			return list;
		} catch (Exception e) {
			log.debug("Txn Impl : Leaving getUsers with Exception");
			throw e;
		}
	}

	/**
	 * 
	 */
	public List<Users> getUsersById(int id) throws Exception {
		log.debug("Txn Impl : getUsersById");
		Session session = null;
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			// session =sessionFactory.openSession();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from Users where isActive = 1 and id = :userId");
			query.setInteger("userId", id);
			List<Users> list = query.list();
			// session.close();
			// sessionFactory.close();
			log.debug("Txn Impl : Leaving getUsersById with success");
			return list;
		} catch (Exception e) {
			log.debug("Txn Impl : Leaving getUsersById with Exception");
			throw e;
		}
	}

	/**
	 * This will delete a users from database.
	 * 
	 * @return true/false
	 * @throws Exception
	 */

	public Boolean deleteUsers(Integer userId) throws Exception {
		Session session = null;
		Transaction tx = null;
		boolean result = false;
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			// session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			Users users=(Users)session.get(Users.class, userId);
			users.setIsActive(false);
			session.update(users);
			tx.commit();
			session.flush();
			// session.close();
			result = true;
		} catch (ConstraintViolationException e) {
			tx.rollback();
			log.error("Error during delete Users data...", e);
			throw new BusinessException(e);
		} catch (Exception e) {
			tx.rollback();
			log.error("Error during deleting Users data...", e);
			throw new ApplicationException(e);
		}
		return result;
	}

	public void deleteEmployee(int id) {
		Session session = null;
		Transaction transaction = null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			transaction.begin();

			Employee objBO = (Employee) session.get(Employee.class, id);
			objBO.setIsActive(false);

			session.update(objBO);
			transaction.commit();
			session.flush();

		} catch (Exception e) {
			log.error(e.getMessage());
			if (transaction != null) {
				transaction.rollback();
			}
			if (session != null) {
				session.flush();
				session.close();
			}

		}
	}
	
	public void deleteGuest(int id) {
		Session session = null;
		Transaction transaction = null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			transaction.begin();

			GuestFaculty objBO = (GuestFaculty) session.get(GuestFaculty.class, id);
			objBO.setIsActive(false);

			session.update(objBO);
			transaction.commit();
			session.flush();

		} catch (Exception e) {
			log.error(e.getMessage());
			if (transaction != null) {
				transaction.rollback();
			}
			if (session != null) {
				session.flush();
				session.close();
			}

		}
	}

	public int getemployeeId(int id) throws Exception {
		Session session = null;
		int result = 0;
		try {
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("select u.employee.id from Users u where u.id="+ id+" and u.isActive = 1" );
			Object obj = query.uniqueResult();
			if (obj != null) {
				Integer idValue = (Integer) obj;
				if (idValue > 0) {
					result = idValue;
				}
			}
			session.flush();
			log.debug("leaving getemployeeId");

		} catch (Exception e) {
			log.error("Error in getemployeeId...", e);
			session.flush();
			session.close();
			throw new ApplicationException(e);
		}

		return result;
	}
	public int getGuestId(int id) throws Exception {
		Session session = null;
		int result = 0;
		try {
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("select u.guest.id from Users u where u.id="+ id+" and u.isActive = 1" );
			Object obj = query.uniqueResult();
			if (obj != null) {
				Integer idValue = (Integer) obj;
				if (idValue > 0) {
					result = idValue;
				}
			}
			session.flush();
			log.debug("leaving getGuestId");

		} catch (Exception e) {
			log.error("Error in getGuestId...", e);
			session.flush();
			session.close();
			throw new ApplicationException(e);
		}

		return result;
	}

	/**
	 * checking user name duplication
	 */
	public boolean isUserNameDuplcated(String userName, int uId)
			throws Exception {
		log.debug("inside isUserNameDuplcated");
		Session session = null;
		boolean result = false;
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			// session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			StringBuffer sqlString = new StringBuffer(
					"from Users u where isActive=1 and userName = :uName");
			if (uId != 0) {
				sqlString = sqlString.append(" and id != :userId");
			}
			Query query = session.createQuery(sqlString.toString());

			query.setString("uName", userName);
			if (uId != 0) {
				query.setInteger("userId", uId);
			}
			List<Users> list = query.list();
			session.flush();
			// session.close();
			// sessionFactory.close();
			log.debug("leaving isUserNameDuplcated");
			if (!list.isEmpty()) {
				result = true;
			}
		} catch (Exception e) {
			log.error("Error in duplication checking...", e);
			session.flush();
			// session.close();
			throw new ApplicationException(e);
		}
		return result;
	}
}
