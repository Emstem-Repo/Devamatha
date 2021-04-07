package com.kp.cms.transactionsimpl.employee;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.EmpAttribute;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.employee.AttributeMasterForm;
import com.kp.cms.transactions.employee.IAttributeMasterTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class AttributeMasterTxImpl implements IAttributeMasterTransaction{
	private static final Log log = LogFactory.getLog(AttributeMasterTxImpl.class);
	public static volatile AttributeMasterTxImpl attributeMasterTxImpl = null;
	
	public static AttributeMasterTxImpl getInstance() {
		if (attributeMasterTxImpl == null) {
			attributeMasterTxImpl = new AttributeMasterTxImpl();
			return attributeMasterTxImpl;
		}
		return attributeMasterTxImpl;
	}
	
	/**
	 * This will retrieve all the attributes from database for UI display.
	 * 
	 * @return all EmpAttribute
	 * @throws Exception
	 */

	public List<EmpAttribute> getAttributeDetails() throws Exception {
		log.debug("inside getEducationDetails");
		Session session = null;
		
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from EmpAttribute i where i.isActive = 1");
			List<EmpAttribute> list = query.list();
			session.flush();
			//session.close();
			//sessionFactory.close();
			log.debug("leaving getAttributeDetails");
			return list;
		 } catch (Exception e) {
			 log.error("Error in getAttributeDetails...",e);
			 session.flush();
			 session.close();
			 throw  new ApplicationException(e);
		 }
	}
	
	/**
	 * duplication checking for Attribute master
	 */
	public EmpAttribute isAttributeDuplcated(AttributeMasterForm attributeMasterForm) throws Exception {
		log.debug("inside isAttributeDuplcated");
		Session session = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			StringBuffer sqlString = new StringBuffer("from EmpAttribute i where i.name = :name and isEmployee = :employee");
			if(attributeMasterForm.getId()!= 0){
				sqlString.append(" and id != :id");
			}
			Query query = session.createQuery(sqlString.toString());
			query.setString("name", attributeMasterForm.getName());
			query.setBoolean("employee", Boolean.valueOf(attributeMasterForm.getIsEmployee()));
			
			if(attributeMasterForm.getId()!= 0){
				query.setInteger("id", attributeMasterForm.getId());
			}
			
			EmpAttribute attribute = (EmpAttribute) query.uniqueResult();
			session.flush();
			//session.close();
			//sessionFactory.close();
			log.debug("leaving isAttributeDuplcated");
			return attribute;
		} catch (Exception e) {
			log.error("Error during duplcation checking..." , e);
			session.flush();
			session.close();
			throw new ApplicationException(e);
		}
	}
	/**
	 * This method add EmpAttribute master.
	 * 
	 * @return true / false based on result.
	 * @throws BusinessException 
	 */
	public boolean addAttributeMaster(EmpAttribute attribute, String mode) throws Exception {
		log.debug("inside addEducationMaster");
		Session session = null;
		Transaction transaction = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			if("edit".equalsIgnoreCase(mode)){
				session.merge(attribute);
			}
			else
			{
				session.save(attribute);
			}
			transaction.commit();
			if (session != null) {
				session.flush();
				session.close();
			}
			log.debug("leaving addEducationMaster");
			return true;
		} catch (ConstraintViolationException e) {
			transaction.rollback();
			log.error("Error during in addEducationMaster..." , e);
			throw new BusinessException(e);
		} catch (Exception e) {
			transaction.rollback();
			log.error("Error in addEducationMaster..." , e);
			throw new ApplicationException(e);
		}

	}

	/**
	 * delete & reactivate
	 */
	public boolean deleteAttribute(int id, Boolean activate, AttributeMasterForm attributeMasterForm) throws Exception {
		Session session = null;
		Transaction tx = null;
		boolean result = false;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			tx.begin();
			EmpAttribute empAttribute = (EmpAttribute) session.get(EmpAttribute.class, id);
			if (activate) {
				empAttribute.setIsActive(true);
			}else
			{
				empAttribute.setIsActive(false);
			}
			empAttribute.setModifiedBy(attributeMasterForm.getUserId());
			empAttribute.setLastModifiedDate(new Date());
			session.update(empAttribute);
			tx.commit();
			session.flush();
			session.close();
			result = true;
		} catch (ConstraintViolationException e) {
			tx.rollback();
			log.error("Error in deleteAttribute..." , e);
			throw new BusinessException(e);
		} catch (Exception e) {
			tx.rollback();
			log.error("Error in deleteAttribute.." , e);
			throw new ApplicationException(e);
		}
		return result;
	}
	
	
}
