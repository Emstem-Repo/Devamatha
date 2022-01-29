package com.kp.cms.transactionsimpl.employee;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.EmpEducationMaster;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.employee.EducationMasterForm;
import com.kp.cms.transactions.employee.IEducationMasterTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class EducationMasterTransactionImpl implements IEducationMasterTransaction{
	private static final Log log = LogFactory.getLog(EducationMasterTransactionImpl.class);
	public static volatile EducationMasterTransactionImpl educationMasterTransactionImpl = null;

	public static EducationMasterTransactionImpl getInstance() {
		if (educationMasterTransactionImpl == null) {
			educationMasterTransactionImpl = new EducationMasterTransactionImpl();
			return educationMasterTransactionImpl;
		}
		return educationMasterTransactionImpl;
	}
	
	/**
	 * duplication checking for Education master
	 */
	public EmpEducationMaster isEducationDuplcated(EducationMasterForm educationMasterForm) throws Exception {
		log.debug("inside isEducationDuplcated");
		Session session = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			StringBuffer sqlString = new StringBuffer("from EmpEducationMaster i where i.name = :name and empQualificationLevel.id = :qId");
			if(educationMasterForm.getId()!= 0){
				sqlString.append(" and id != :id");
			}
			Query query = session.createQuery(sqlString.toString());
			query.setString("name", educationMasterForm.getEducation());
			query.setInteger("qId", Integer.parseInt(educationMasterForm.getQualificationId()));
			if(educationMasterForm.getId()!= 0){
				query.setInteger("id", educationMasterForm.getId());
			}
			
			EmpEducationMaster empEducationMaster = (EmpEducationMaster) query.uniqueResult();
			session.flush();
			//session.close();
			//sessionFactory.close();
			log.debug("leaving isEducationDuplcated");
			return empEducationMaster;
		} catch (Exception e) {
			log.error("Error during duplcation checking..." , e);
			session.flush();
			session.close();
			throw new ApplicationException(e);
		}
	}

	/**
	 * This method add Education master.
	 * 
	 * @return true / false based on result.
	 * @throws BusinessException 
	 */
	public boolean addEducationMaster(EmpEducationMaster educationMaster, String mode) throws Exception {
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
				session.merge(educationMaster);
			}
			else
			{
				session.save(educationMaster);
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
	 * This will retrieve all the education from database for UI display.
	 * 
	 * @return all InvCounter
	 * @throws Exception
	 */

	public List<EmpEducationMaster> getEducationDetails() throws Exception {
		log.debug("inside getEducationDetails");
		Session session = null;
		
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from EmpEducationMaster i where i.isActive = 1");
			List<EmpEducationMaster> list = query.list();
			session.flush();
			//session.close();
			//sessionFactory.close();
			log.debug("leaving getEducationDetails");
			return list;
		 } catch (Exception e) {
			 log.error("Error in getEducationDetails...",e);
			 session.flush();
			 session.close();
			 throw  new ApplicationException(e);
		 }
	}
	
	/**
	 * delete & reactivate
	 */
	public boolean deleteEducation(int id, Boolean activate, EducationMasterForm educationMasterForm) throws Exception {
		Session session = null;
		Transaction tx = null;
		boolean result = false;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			tx.begin();
			EmpEducationMaster educationMaster = (EmpEducationMaster) session.get(EmpEducationMaster.class, id);
			if (activate) {
				educationMaster.setIsActive(true);
			}else
			{
				educationMaster.setIsActive(false);
			}
			educationMaster.setModifiedBy(educationMasterForm.getUserId());
			educationMaster.setLastModifiedDate(new Date());
			session.update(educationMaster);
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

	
}
