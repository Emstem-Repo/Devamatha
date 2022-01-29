package com.kp.cms.transactionsimpl.usermanagement;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.AccessPrivileges;
import com.kp.cms.bo.admin.Modules;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.handlers.usermanagement.AssignPrivilegeHandler;
import com.kp.cms.transactions.usermanagement.IAssignPrivilegeTransaction;
import com.kp.cms.utilities.HibernateUtil;


public class AssignPrivilegeTransactionImpl implements
		IAssignPrivilegeTransaction {

	private static final Log log = LogFactory.getLog(AssignPrivilegeHandler.class);

	/**
	 * Gets all the modules which are in active state from Module table
	 */
	public List<Modules> getModuleDetails() throws Exception {
		log.info("Inside of getModuleDetails of AssignPrivilege TransactionImpl");
		Session session = null;
		List<Modules> moduleList = null;
		try {
			//session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			moduleList = session.createQuery("from Modules modules where modules.isActive = 1 order by modules.id").list();			
		} catch (Exception e) {
			log.error("Error in getModuleDetails of AssignPrivilege TransactionImpl");
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		log.info("End of getModuleDetails of AssignPrivilege TransactionImpl");
		return moduleList;
	}

	/**
	 * Used while inserting privilege for a particular role
	 */
	public boolean addPrivilege(List<AccessPrivileges> privileges)
			throws Exception {
		log.info("Inside of addPrivilege of AssignPrivilege TransactionImpl");
		AccessPrivileges accessPrivileges;
		Transaction tx=null;
		Session session = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			Iterator<AccessPrivileges> itr = privileges.iterator();
			int count = 0;
			tx = session.beginTransaction();
			tx.begin();
			while (itr.hasNext()) {
				accessPrivileges = itr.next();
				session.save(accessPrivileges);
				if (++count % 20 == 0) {
					session.flush();
					session.clear();
				}
			}
			tx.commit();
			//session.close();
			//sessionFactory.close();
			log.info("End of addPrivilege of AssignPrivilege TransactionImpl");
			return true;
		} catch (Exception e) {
			if(tx!=null){
				tx.rollback();
			}
			session.flush();
			session.clear();
			//session.close();
			log.error("Error occured addPrivilege of AssignPrivilege TransactionImpl");
			throw new ApplicationException(e);
		}
	}

	/**
	 * Used while getting Role id and role names
	 */
	public List<Object[]> getRolesFromAcccessPrivilege() throws Exception {
		log.info("Inside of getRolesFromAcccessPrivilege of AssignPrivilege TransactionImpl");
		Session session = null;
		List<Object[]> roleList;
		try {
			//session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			roleList = session
					.createQuery(
							"select distinct(roles.id), roles.name from AccessPrivileges a where a.isActive = 1")
					.list();
		} catch (Exception e) {
			log
					.error("Error occured in getRolesFromAcccessPrivilege of AssignPrivilege TransactionImpl");
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		log.info("End of getRolesFromAcccessPrivilege of AssignPrivilege TransactionImpl");
		return roleList;
	}

	/**
	 * Deletes an assigned privilege (Makes that to inactive)
	 */
	public boolean deletePrivilege(int roleId, String userId) throws Exception {
		log.info("Inside of deletePrivilege of AssignPrivilege TransactionImpl");
		Session session = null;
		Transaction transaction = null;
		Date date = new Date();
		try {
			//session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			Query query = session
					.createQuery("update AccessPrivileges a set modifiedBy= :userId, lastModifiedDate = :date, a.isActive = false, a.allowAccess = false where a.roles.id =  :id");
			query.setDate("date", date);
			query.setString("userId", userId);
			query.setInteger("id", roleId);
			query.executeUpdate();
			transaction.commit();
			log.info("End of deletePrivilege of AssignPrivilege TransactionImpl");
			return true;
		} catch (Exception e) {
			if(transaction!=null){
				transaction.rollback();
			}
			log.error("Error occured in deletePrivilege of AssignPrivilege TransactionImpl");
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
	}

	/**
	 * Displays the module and menus available for that which are in active mode
	 * in click of view button
	 */
	public List<Object[]> getModuleMenuOnRole(int roleId) throws Exception {
		log.info("Inside of getModuleMenuOnRole of AssignPrivilege TransactionImpl");
		Session session = null;
		try {
			//session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			List<Object[]> moduleMenuList = session
					.createQuery(
							"select a.modules.displayName, a.menus.displayName from AccessPrivileges a where a.isActive = 1 and a.modules.isActive =1 and a.menus.isActive =1 and a.allowAccess = 1 and a.roles.id = "
									+ roleId + "order by a.modules.id, a.menus.id")
					.list();
			log.info("End of getModuleMenuOnRole of AssignPrivilege TransactionImpl");
			return moduleMenuList;
		} catch (Exception e) {
			log.error("Error occured in getModuleMenuOnRole of AssignPrivilege TransactionImpl");
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
	}

	/**
	 * Check for duplicate entry based on the roleId
	 */

	public List<AccessPrivileges> getPrivilegebyRole(int roleId)
			throws Exception {
		log.info("Inside of getPrivilegebyRole of Privilege TransactionImpl");
		Session session = null;
		List<AccessPrivileges> privilegeList;
		try {
			//session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			privilegeList = session.createQuery(
					"from AccessPrivileges a where a.roles.id =" + roleId)
					.list();			
		} catch (Exception e) {
			log.error("Error in getPrivilegebyRole of Privilege TransactionImpl");
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		log.info("End of getPrivilegebyRole of AssignPrivilege TransactionImpl");
		return privilegeList;
	}

	/**
	 * Used in reactivation
	 */

	public boolean reActivatePrivilege(int roleId, String userId)
			throws Exception {
		log.info("Inside of reActivatePrivilege of AssignPrivilege TransactionImpl");
		Session session = null;
		Transaction transaction = null;
		Date date = new Date();
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			Query query = session
					.createQuery("update AccessPrivileges a set modifiedBy= :userId, lastModifiedDate = :date, a.isActive = true, a.allowAccess = true where a.roles.id =  :id");
			query.setDate("date", date);
			query.setInteger("id", roleId);
			query.setString("userId", userId);
			query.executeUpdate();
			transaction.commit();
			log.info("End of reActivatePrivilege of AssignPrivilege TransactionImpl");
			return true;
		} catch (Exception e) {
			if(transaction!=null){
				transaction.rollback();
			}
			log.error("Exception occured in reactivating of AssignPrivilege in TransactionImpl :"+ e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
	}

	/**
	 * Used in Edit
	 */
	public List<AccessPrivileges> getRoleModuleMenuonroleId(int roleId)
			throws Exception {
		log.info("Inside of getRoleModuleMenuonroleId of AssignPrivilege TransactionImpl");
		Session session = null;
		List<AccessPrivileges> roleModuleMenuList = null;
		try {
			//session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			roleModuleMenuList = session.createQuery("from AccessPrivileges access where access.roles.isActive=1 and access.modules.isActive=1 and " +
			"access.menus.isActive=1 and access.roles.id =" + roleId + "order by access.modules.id").list();
			
			log.info("End of getRoleModuleMenuonroleId of Privilege TransactionImpl");
			return roleModuleMenuList;
		} catch (Exception e) {
			log.error("Error in getRoleModuleMenuonroleId of AssignPrivilege TransactionImpl");
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
	}

	/**
	 * Used in update
	 */

	public boolean updatePrivilege(List<AccessPrivileges> updateList)
			throws Exception {
		log.info("Inside of updatePrivilege of AssignPrivilege TransactionImpl");
		AccessPrivileges accessPrivileges;
		Transaction tx=null;
		Session session = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			Iterator<AccessPrivileges> itr = updateList.iterator();
			int count = 0;
			tx = session.beginTransaction();
			tx.begin();
			while (itr.hasNext()) {
				accessPrivileges = itr.next();
				session.saveOrUpdate(accessPrivileges);
				if (++count % 20 == 0) {
					session.flush();
					session.clear();
				}
			}
			tx.commit();
			//sessionFactory.close();
			return true;
		} catch (Exception e) {
			if(tx !=null){
				tx.rollback();
			}
			log.error("Error occured addPrivilege of AssignPrivilege TransactionImpl");
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
			log.info("End of updatePrivilege of AssignPrivilege TransactionImpl");
		}
	}
}
