package com.kp.cms.transactionsimpl.admin;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.StaleStateException;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.Organisation;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.transactions.admin.IOrganizationTransaction;
import com.kp.cms.utilities.HibernateUtil;


public class OrganizationTransactionImpl implements IOrganizationTransaction{
	
	private static final Log log = LogFactory.getLog(OrganizationTransactionImpl.class);

	/**
	 * Used for inserting an organization details
	 */
	public boolean saveOrganizationDetails(Organisation organisation)throws Exception {
		Session session = null;
		Transaction transaction = null;
		try {
			//session = InitSessionFactory.getInstance().openSession();\
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			session.save(organisation);
			transaction.commit();
			return true;
		} catch (Exception e) {	
			if(transaction != null){
				transaction.rollback();
			}
			log.warn("Exception occured in saving Organization Details in IMPL :",e);
			throw  new ApplicationException(e);
			
		} finally {
		if (session != null) {
			session.flush();
			session.close();
		}
		}
	}
	
	/**
	 * Used while Getting all organization details from DB
	 */
	
	public List<Organisation> getOrganizationDetails()throws Exception
	{
		Session session = null;
		List<Organisation> organisationList;
		try {
			//session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			organisationList = session.createQuery("from Organisation org").list();
		} catch (Exception e) {			
			log.error("Exception occured in getting all Organization Details in IMPL :",e);
			throw  new ApplicationException(e);
		} finally {
		if (session != null) {
			session.flush();
			//session.close();
		}
		}
		return organisationList;		
	}
	
	/**
	 * Used for Deleting an organization
	 */
	
	public boolean deleteOrganizationDetails(Organisation organisation)throws Exception{
		Session session = null;
		Transaction transaction = null;
		try {
			//session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			session.delete(organisation);
			transaction.commit();
			return true;
		}
		catch (StaleStateException e) {
			if(transaction != null){
				transaction.rollback();
			}
			log.error("Unable to delete OrganizationDetails---");
			throw  new ApplicationException(e);
		}
		catch (Exception e) {			
			log.error("Exception occured in deleting a OrganizationDetails in IMPL :",e);	
			throw  new BusinessException(e);
		} finally {
		if (session != null) {
			session.flush();
			session.close();
			}
		}
	}
	/**
	 * Used for downloading logo & topbar 
	 */
	
	public Organisation getRequiredFile(int id)throws Exception
	{
		Session session = null;
		try {
			//session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			Organisation organisation = (Organisation) session.createQuery("from Organisation org where org.isActive = 1 and org.id=?").setInteger(0,id).uniqueResult();
			if(organisation!=null){
				return organisation;
			}
		} catch (Exception e) {
			log.error("Exception occured in getRequiredFile IMPL :",e);
		throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		return null;
	}

	/**
	 * Used in File downloading 
	 */
	
	public Organisation getRequiredFile()throws Exception
	{
		Session session = null;
		try {
			//session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			Organisation organisation = (Organisation) session.createQuery("from Organisation org").uniqueResult();
			if(organisation!=null){
				return organisation;
			}			
		} catch (Exception e) {
			log.error("Exception occured in getRequiredFile IMPL :",e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		return null;
		}
	
	/**
	 * 	Used to update Organization Details
	 * Returns the result (true if success, false if failures)
	 */
	public boolean updateOrganizationDetails(Organisation newOrganizationBO)
			throws Exception {
		Session session = null;
		Transaction transaction = null;
		try {
			//session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			session.update(newOrganizationBO);
			transaction.commit();
			return true;
		} catch (Exception e) {	
			if(transaction != null){
				transaction.rollback();
			}
			log.error("Exception occured in updating Organization Details in IMPL :",e);
			throw  new ApplicationException(e);			
		} finally {
		if (session != null) {
			session.flush();
			session.close();
		}
		}
	}
	}
