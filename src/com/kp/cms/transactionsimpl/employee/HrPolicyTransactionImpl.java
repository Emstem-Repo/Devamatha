package com.kp.cms.transactionsimpl.employee;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.EmpHrPolicy;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.transactions.employee.IHRPolicyTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class HrPolicyTransactionImpl implements IHRPolicyTransaction{

	
	private static final Log log = LogFactory.getLog(HrPolicyTransactionImpl.class);
	
	/**
	 *	This method is used to get all HR policies from database. 
	 */
	
	@SuppressWarnings("unchecked")
	public List<EmpHrPolicy> getHrPolicyDetails() throws Exception {
		log.info("entering of getHrPolicyDetails method in HrPolicyTransactionImpl class..");
		Session session = null;
		List<EmpHrPolicy> policiesList;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			policiesList = session.createQuery(
					"from EmpHrPolicy ehp where ehp.isActive = 1").list();

		} catch (Exception e) {
			log.error("Unable to get getCurrencyMasters" ,e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		log.info("exit of getHrPolicyDetails method in HrPolicyTransactionImpl class..");
		return policiesList;
		
	}

	/**
	 * This method is used to save the HR policy to database.
	 */
	
	@Override
	public boolean saveHrPolicy(EmpHrPolicy empHrPolicy) throws Exception {
		log.info("call of saveHrPolicy in HrPolicyTransactionImpl class.");
		Session session = null;
		Transaction transaction = null;
		boolean isAdded = false;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			session.save(empHrPolicy);
			transaction.commit();
			isAdded = true;
		} catch (Exception e) {
			isAdded = false;
			log.error("Unable to saveHrPolicy" , e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		log.info("end of saveHrPolicy in HrPolicyTransactionImpl class.");
		return isAdded;
	}
	
	
	/**
	 * This method is used to delete the HR policy from database.
	 */

	@Override
	public boolean deleteHrPolicy(int id) throws Exception {
		log.info("call of saveHrPolicy in HrPolicyTransactionImpl class.");
		Session session = null;
		Transaction transaction = null;
		boolean isDeleted = false;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			EmpHrPolicy empHrPolicy = (EmpHrPolicy) session.get(EmpHrPolicy.class, id);
			session.delete(empHrPolicy);
			transaction.commit();
			isDeleted = true;
		} catch (Exception e) {
			isDeleted = false;
			log.error("Unable to delete FeeHeadings" , e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		log.info("end of saveHrPolicy in HrPolicyTransactionImpl class.");
		return isDeleted;
	}

	/**
	 * This method is used to get the HR policy from database based on policy id.
	 */
	
	@Override
	public EmpHrPolicy getHrPolicy(int policyId) throws Exception {
		log.info("call of saveHrPolicy in HrPolicyTransactionImpl class.");
		Session session = null;
		EmpHrPolicy empHrPolicy = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from EmpHrPolicy emp where emp.id = " + policyId);
			empHrPolicy = (EmpHrPolicy)query.uniqueResult();
		} catch (Exception e) {
			log.error("Unable to delete FeeHeadings" , e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		log.info("end of saveHrPolicy in HrPolicyTransactionImpl class.");
		return empHrPolicy;
	}

	@Override
	public boolean isDuplicateHrPolicyName(String policyName) throws Exception {
		log.info("entering of getHrPolicyDetails method in HrPolicyTransactionImpl class..");
		Session session = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			List<EmpHrPolicy> policiesList = session.createQuery(
					"from EmpHrPolicy ehp where ehp.isActive = 1 and ehp.name='"+policyName+"'").list();
			if(policiesList!=null && !policiesList.isEmpty()){
				return true;
			}

		} catch (Exception e) {
			log.error("Unable to get getCurrencyMasters" ,e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		log.info("exit of getHrPolicyDetails method in HrPolicyTransactionImpl class..");
		return false;
	}
}