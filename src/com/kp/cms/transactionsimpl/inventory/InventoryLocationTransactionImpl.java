package com.kp.cms.transactionsimpl.inventory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.Employee;
import com.kp.cms.bo.admin.InvCampus;
import com.kp.cms.bo.admin.InvLocation;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.inventory.InventoryLocationForm;
import com.kp.cms.transactions.inventory.IInventoryLocationTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class InventoryLocationTransactionImpl implements IInventoryLocationTransaction {
	private static final Log log = LogFactory.getLog(InventoryLocationTransactionImpl.class);
	public static volatile InventoryLocationTransactionImpl inventoryLocationTransactionImpl = null;

	public static InventoryLocationTransactionImpl getInstance() {
		if (inventoryLocationTransactionImpl == null) {
			inventoryLocationTransactionImpl = new InventoryLocationTransactionImpl();
		}
		return inventoryLocationTransactionImpl;
	}
	

	/**
	 * This will retrieve all the employees from database for UI display.
	 * 
	 * @return all Employee
	 * @throws Exception
	 */

	public List<Employee> getEmployee() throws Exception {
		log.debug("inside getEmployee");
		Session session = null;
		
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from Employee i where i.isActive = 1 and i.active=1");
			List<Employee> list = query.list();
			session.flush();
			//session.close();
			//sessionFactory.close();
			log.debug("exit getEmployee");
			return list;
		 } catch (Exception e) {
			 log.error("Error in getEmployee...",e);
			 session.flush();
			 session.close();
			 throw  new ApplicationException(e);
		 }
	}
	
	/**
	 * duplication checking for Inv Location master
	 */
	public InvLocation isLocationDuplcated(InventoryLocationForm locationForm) throws Exception {
		log.debug("inside isEducationDuplcated");
		Session session = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			StringBuffer sqlString = new StringBuffer("from InvLocation invLocation where invLocation.name = :name and invLocation.invCampusId=:campus");
			if(locationForm.getId()!= 0){
				sqlString.append(" and invLocation.id != :id");
			}
			Query query = session.createQuery(sqlString.toString());
			query.setString("name", locationForm.getLocation());
			query.setString("campus", locationForm.getInvCampusId());
			if(locationForm.getId()!= 0){
				query.setInteger("id", locationForm.getId());
			}
			
			InvLocation location = (InvLocation) query.uniqueResult();
			session.flush();
			//session.close();
			//sessionFactory.close();
			log.debug("leaving isLocationDuplcated");
			return location;
		} catch (Exception e) {
			log.error("Error during duplcation checking..." , e);
			session.flush();
			session.close();
			throw new ApplicationException(e);
		}
	}

	/**
	 * This method add Inventory Locaton.
	 * 
	 * @return true / false based on result.
	 * @throws BusinessException 
	 */
	public boolean addInventoryLocation(InvLocation invLocation, String mode) throws Exception {
		log.debug("inside addInventoryLocation");
		Session session = null;
		Transaction transaction = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			if("edit".equalsIgnoreCase(mode)){
				session.update(invLocation);
			}
			else
			{
				session.save(invLocation);
			}
			transaction.commit();
			if (session != null) {
				session.flush();
				session.close();
			}
			log.debug("leaving addInventoryLocation");
			return true;
		} catch (ConstraintViolationException e) {
			transaction.rollback();
			log.error("Error during in addInventoryLocation..." , e);
			throw new BusinessException(e);
		} catch (Exception e) {
			transaction.rollback();
			log.error("Error in addInventoryLocation..." , e);
			throw new ApplicationException(e);
		}

	}
	
	/**
	 * This will retrieve all the inventory locations.
	 */
	public List<InvLocation> getInventoryLocations() throws Exception {
		log.debug("impl: inside getInventoryLocations");
		Session session = null;
		List result;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from InvLocation a where a.isActive=1");
			List<InvLocation> list = query.list();

			session.flush();
//			session.close();
			//sessionFactory.close();
			log.debug("impl: leaving getInventoryLocations");
			result = list;
		} catch (Exception e) {
			log.error("Error in getInventoryLocations..." + e);
			if (session != null) {
				session.flush();
				//session.close();
			}
			throw new ApplicationException(e);
		}
		return result;
	}	

	/**
	 * delete & reactivate
	 */
	public boolean deleteInvLocation(int id, Boolean activate, InventoryLocationForm locationForm) throws Exception {
		Session session = null;
		Transaction tx = null;
		boolean result = false;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			tx.begin();
			InvLocation location = (InvLocation) session.get(InvLocation.class, id);
			if (activate) {
				location.setIsActive(true);
			}else
			{
				location.setIsActive(false);
			}
			location.setModifiedBy(locationForm.getUserId());
			location.setLastModifiedDate(new Date());
			session.update(location);
			tx.commit();
			session.flush();
			session.close();
			result = true;
		} catch (ConstraintViolationException e) {
			tx.rollback();
			log.error("Error in deleteEducation..." , e);
			throw new BusinessException(e);
		} catch (Exception e) {
			tx.rollback();
			log.error("Error in deleteEducation.." , e);
			throw new ApplicationException(e);
		}
		return result;
	}


	@Override
	public List<InvCampus> getCampus() throws Exception {
		Session session=null;
		try {
			session=HibernateUtil.getSession();
			String query="from InvCampus invCampus where invCampus.isActive=1";
			List<InvCampus> invCampusList=session.createQuery(query).list();
			return invCampusList;
			
		} catch (Exception e) {
			if(session!=null){
				session.flush();
			}
			throw new ApplicationException(e);
		}
		
	}
	
	
}
