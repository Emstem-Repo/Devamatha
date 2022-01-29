package com.kp.cms.transactionsimpl.reportusermanagement;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.ReportAccessPrivileges;
import com.kp.cms.bo.admin.ReportsMenus;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.transactions.reportusermanagement.IReportsMenusTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class ReportsMenusTransactionImpl implements IReportsMenusTransaction {

	private static final Log log = LogFactory.getLog(ReportsMenusTransactionImpl.class); 
	
	/**
	 * This method is used to get a unique instance when you called handler. 
	 */
	
	private static volatile ReportsMenusTransactionImpl menuTransactionImpl = null;
	
	public static ReportsMenusTransactionImpl getInstance() {
	    if(menuTransactionImpl == null ){
	    	menuTransactionImpl = new ReportsMenusTransactionImpl();
	       return menuTransactionImpl;
	    }
    return menuTransactionImpl;
	}
	
	/**
	 * This method is used to get the menu details from database.
	 */
	
	@Override
	public List<ReportsMenus> getMenuDetails() throws Exception {
		log.info("call of getMenuDetails in MenuTransactionImpl class.");
		Session session = null;
		List<ReportsMenus> list = null;
		try {
			 //SessionFactory sessionFactory = InitSessionFactory.getInstance();
			 //session =sessionFactory.openSession();
			 session = HibernateUtil.getSession();
			 Query query = session.createQuery("from ReportsMenus m where m.isActive= 1 and m.modules.isActive=1 order by modules.displayName, m.position, m.displayName");
			 list = query.list();
		 } catch (Exception e) {
			 log.error("Unable to getMenuDetails",e);
			 throw e;
		 }
		 finally{
			 if(session!=null){
				session.flush();
			 	//session.close();
			 	}
		 	}
	log.info("end of getMenuDetails in MenuTransactionImpl class.");
	return list;
	}
	
	/**
	 * This method is used to check menu exist or not in database.
	 */

	@Override
	public ReportsMenus menuNameExist(int position, int moduleId) throws Exception {
		log.info("call of menuNameExist in MenuTransactionImpl class.");
		
		Session session = null;
		ReportsMenus menus;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session=sessionFactory.openSession();
			 session = HibernateUtil.getSession();
			Query query=session.createQuery("from ReportsMenus m where m.modules.id= :modId and m.position= :pos");
			query.setInteger("pos", position);
			query.setInteger("modId",moduleId);
			menus = (ReportsMenus)query.uniqueResult();
		
		} catch (Exception e) {
			log.error("Unable to get menuNameExist",e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		log.info("end of menuNameExist in MenuTransactionImpl class.");
		return menus;
	}

	/**
	 * This method is used to add menu details to database.
	 */
	
	@Override
	public boolean addMenus(ReportsMenus menus) throws DuplicateException, Exception {
		log.info("call of addMenus in MenuTransactionImpl class.");
		Session session = null;
		Transaction transaction = null;
		boolean isAdded = false;
		try{
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session =sessionFactory.openSession();
			 session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			session.save(menus);
			transaction.commit();
			isAdded = true;
		}catch (ConstraintViolationException e) {
			isAdded = false;
			log.error("Error while saving menus data..",e);
		}catch (Exception e) {
			isAdded = false;
			log.error("Error while saving menus data data ...",e);
		}finally{
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		log.info("end of addMenus in MenuTransactionImpl class.");
		return isAdded;
	}

	/**
	 * This method is used to edit menu data based on id.
	 */
	
	@Override
	public List<ReportsMenus> editMenuDetails(int menuId) throws Exception {
		log.info("call of editMenuDetails in MenuTransactionImpl class.");
		Session session = null;
		List<ReportsMenus> menusList;
		try {
			 //SessionFactory sessionFactory = InitSessionFactory.getInstance();
			 //session =sessionFactory.openSession();
			 session = HibernateUtil.getSession();
			 Query query = session.createQuery("from ReportsMenus m where m.id= :id");
			 query.setInteger("id",menuId);
			 menusList = query.list();
		 } catch (Exception e) {
			 log.error("Unable to get MenuDetails for edit",e);
			 throw e;
		 }
		 finally{
			 if(session!=null){
				session.flush();
			 	//session.close();
			 	}
		 	}
		log.info("end of editMenuDetails in MenuTransactionImpl class.");
		return menusList;
	}
	
	/**
	 * This method is used to update menu details in database.
	 */

	@Override
	public boolean updateMenus(ReportsMenus menus,int oldModuleId) throws Exception {
		log.info("call of updateMenus in MenuTransactionImpl class.");
		Session session = null;
		Transaction transaction = null;
		boolean isUpdated = false;
		try{
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session =sessionFactory.openSession();
			 session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			session.update(menus);
			
			if(oldModuleId != menus.getModules().getId()) {
				Query query = session
				.createQuery("update ReportAccessPrivileges a set a.isActive = false, a.allowAccess = false where a.menus.id =:id");
				query.setInteger("id", menus.getId());
				query.executeUpdate();
			}
			
			transaction.commit();
			isUpdated = true;
		}catch(Exception e){
			isUpdated = false;
			log.error("Unable to updateMenus",e);
		}finally{
			if(session!=null){
				session.flush();
				session.close();
			}
		}
		log.info("end of updateMenus in MenuTransactionImpl class.");
		return isUpdated;
	}
	
	/**
	 * This method is used to delete menu details from database by making isActive as 0.
	 */

	@Override
	public boolean deleteMenus(int menuId, String userId) throws Exception {
		log.info("call of deleteMenus in MenuTransactionImpl class.");
			Session session = null;
			Transaction transaction = null;
			boolean isDeleted = false;
			try{
				//SessionFactory sessionFactory = InitSessionFactory.getInstance();
				//session =sessionFactory.openSession();
			    session = HibernateUtil.getSession();
				transaction = session.beginTransaction();
				transaction.begin();
				ReportsMenus menus = (ReportsMenus)session.get(ReportsMenus.class,menuId);
				menus.setModifiedBy(userId);
				menus.setLastModifiedDate(new Date());
				menus.setIsActive(Boolean.FALSE);
				session.update(menus);
				transaction.commit();
				isDeleted = true;
			}catch(Exception e){
				isDeleted = false;
				log.error("Unable to deleteMenus",e);
			}finally{
				if(session!=null){
					session.flush();
					session.close();
				}
			}
		log.info("end of deleteMenus in MenuTransactionImpl class.");
		return isDeleted;
		}
	
	/**
	 * This method is used to restore menu details from database.
	 */

	@Override
	public void reActivateMenus(int sequence,int moduleId, String userId) throws Exception {
		log.info("call of reActivateMenus in MenuTransactionImpl class.");
		Session session = null;
		Transaction transaction = null;
		try {
				//SessionFactory sessionFactory = InitSessionFactory.getInstance();
				//session = sessionFactory.openSession();
				session = HibernateUtil.getSession();
				Query query = session.createQuery("from ReportsMenus m where m.position = :pos and m.modules.id= :moduleId");
				query.setInteger("pos",sequence);
				query.setInteger("moduleId", moduleId);
				ReportsMenus menus = (ReportsMenus) query.uniqueResult();
				transaction = session.beginTransaction();
				menus.setIsActive(true);
				menus.setModifiedBy(userId);
				menus.setLastModifiedDate(new Date());
				session.update(menus);
				transaction.commit();
			} catch (Exception e) {
				log.error("Unable to restore menus",e);
				if (transaction != null) {
						transaction.rollback();
					}
					throw new ApplicationException(e);
			} finally {
					if (session != null) {
						session.flush();
						session.close();
					}
			}
	log.info("end of reActivateMenus in MenuTransactionImpl class.");
	}

	@Override
	public List<Object[]> getReportRolesByMenuId(int menuId) throws Exception {
		log.info("Inside of getReportRolesByMenuId of ReportsMenusTransactionImpl");
		Session session = null;
		List<Object[]> roleList;
		try {
			session = HibernateUtil.getSession();
			roleList = session.createQuery(" select acp.id as acpId,ro.id as roId from ReportAccessPrivileges acp join acp.menus men join acp.roles ro join acp.modules mo "+
                                           " where acp.isActive=1 and men.isActive=1 and ro.isActive=1 and mo.isActive=1 and men.id="+menuId).list();
		} catch (Exception e) {
			log
					.error("Error occured in getReportRolesByMenuId of ReportsMenusTransactionImpl ");
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		log.info("End of getReportRolesByMenuId of ReportsMenusTransactionImpl");
		return roleList;
	}

	@Override
	public List<Object[]> getReportsAssignPrivilegeRole() throws Exception {
		log.info("Inside of getRolesFromAcccessPrivilege of ReportAssignPrivilege TransactionImpl");
		Session session = null;
		List<Object[]> roleList;
		try {
			//session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			roleList = session
					.createQuery(
							"select distinct(roles.id), roles.name from ReportAccessPrivileges a where a.isActive = 1 order by roles.name")
					.list();
		} catch (Exception e) {
			log
					.error("Error occured in getRolesFromAcccessPrivilege of ReportAssignPrivilege TransactionImpl");
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		log.info("End of getRolesFromAcccessPrivilege of ReportAssignPrivilege TransactionImpl");
		return roleList;
	}

	@Override
	public boolean addMenuAssignAggrement(List<ReportAccessPrivileges> privileges) throws Exception {
		log.info("call of addMenus in MenuTransactionImpl class.");
		Session session = null;
		Transaction transaction = null;
		boolean isAdded = false;
		try{
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			Iterator<ReportAccessPrivileges> itr=privileges.iterator();
			while (itr.hasNext()) {
				ReportAccessPrivileges accessPrivileges = (ReportAccessPrivileges) itr.next();
				session.saveOrUpdate(accessPrivileges);	
			}
			transaction.commit();
			isAdded = true;
		}catch (ConstraintViolationException e) {
			isAdded = false;
			log.error("Error while saving menus data..",e);
		}catch (Exception e) {
			isAdded = false;
			log.error("Error while saving menus data data ...",e);
		}finally{
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		log.info("end of addMenus in MenuTransactionImpl class.");
		return isAdded;
	}
}