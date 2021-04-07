package com.kp.cms.transactionsimpl.inventory;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.InvVendor;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.inventory.VendorForm;
import com.kp.cms.transactions.inventory.IVendorTransactions;
import com.kp.cms.utilities.HibernateUtil;

public class VendorTransactionImpl implements IVendorTransactions {
	private static Log log = LogFactory.getLog(VendorTransactionImpl.class);
	public static volatile VendorTransactionImpl vendorTransactionImpl;
	public static VendorTransactionImpl getInstance() {
		if (vendorTransactionImpl == null) {
			vendorTransactionImpl = new VendorTransactionImpl();
		}
		return vendorTransactionImpl;
	}
	/***
	 * this method will add/update record in vendor table 
	 */
	public boolean addVendor(InvVendor invVendor, String mode) throws  Exception {
		log.debug("inside addVendor in impl");
		Session session = null;
		Transaction transaction = null;
		boolean result = false;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			session = HibernateUtil.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			if(mode.equalsIgnoreCase("Add")){
				session.save(invVendor);
				}
			else{
				session.saveOrUpdate(invVendor);
			}
			transaction.commit();
			if (session != null) {
				session.flush();
				session.close();
			}
			//sessionFactory.close();
			result = true;
		} catch (ConstraintViolationException e) {
			transaction.rollback();
			log.error("Error during saving addVendor data..." , e);
			throw new BusinessException(e);
		} catch (Exception e) {
			transaction.rollback();
			log.error("Error during saving addVendor data..." , e);
			throw new ApplicationException(e);
		}
		log.debug("leaving addVendor in impl");
		return result;
	}
	
	/**
	 * get vendor details
	 */
	public List<InvVendor> getVendorDetails() throws Exception{
		log.debug("inside getVendorDetails");
		Session session = null;
		try {
			/*SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from InvVendor h where h.isActive = 1");
			List<InvVendor> list = query.list();
			session.flush();
			//session.close();
			//sessionFactory.close();
			log.debug("leaving getVendorDetails");
			return list;
		 } catch (Exception e) {
			 log.error("Error in getVendorDetails...",e);
			 session.flush();
			 session.close();
			 throw  new ApplicationException(e);
		 }
	}

	/**
	 * get vendor based on id
	 */
	public InvVendor getVendor(int id) throws Exception{
		log.debug("inside getVendor");
		Session session = null;
		InvVendor  invVendor;
		try {
			/*SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from InvVendor h where h.isActive = 1 and id = '" + id + "'");
			invVendor = (InvVendor) query.uniqueResult();
			session.flush();
			//session.close();
			//sessionFactory.close();
			log.debug("leaving getVendor");
			return invVendor;
		 } catch (Exception e) {
			 log.error("Error in getVendor...",e);
			 session.flush();
			 session.close();
			 throw  new ApplicationException(e);
		 }
	}
	/**
	 * duplication checking for Vendor Master
	 */
	public InvVendor isVendorNameDuplcated(String name, int id) throws Exception {
		log.debug("inside isVendorNameDuplcated");
		Session session = null;
		InvVendor invVendor;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			StringBuffer sqlString = new StringBuffer("from InvVendor v where v.name = :tempname ");
			if(id!= 0){
				sqlString.append(" and id != :id");
			}
			Query query = session.createQuery(sqlString.toString());
			query.setString("tempname",  name);
			if(id!= 0){
				query.setInteger("id", id);
			}
			
			invVendor = (InvVendor) query.uniqueResult();
			session.flush();
			//session.close();
			//sessionFactory.close();
		} catch (Exception e) {
			log.error("Error during duplcation checking..." , e);
			session.flush();
			session.close();
			throw new ApplicationException(e);
		}
		log.debug("leaving isVendorNameDuplcated");
		return invVendor;
	}
	
	/**
	 * delete & reactivate
	 */
	public boolean deleteVendor(int id, Boolean activate, VendorForm vendorForm) throws Exception {
		Session session = null;
		Transaction tx = null;
		boolean result = false;
		try {
			/*SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			tx.begin();
			InvVendor vendor = (InvVendor) session.get(InvVendor.class, id);
			if (activate) {
				vendor.setIsActive(true);
			}else
			{
				vendor.setIsActive(false);
			}
			vendor.setModifiedBy(vendorForm.getUserId());
			vendor.setLastModifiedDate(new Date());
			session.update(vendor);
			tx.commit();
			session.flush();
			session.close();
			result = true;
		} catch (ConstraintViolationException e) {
			tx.rollback();
			log.error("Error in deleteVendor..." , e);
			throw new BusinessException(e);
		} catch (Exception e) {
			tx.rollback();
			log.error("Error in deleteVendor.." , e);
			throw new ApplicationException(e);
		}
		return result;
	}
	
}
