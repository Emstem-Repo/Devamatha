package com.kp.cms.transactionsimpl.employee;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.EmpBiometricLogSetupBO;
import com.kp.cms.bo.admin.EmpWorkTime;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.transactions.employee.IBiometricLogSetupTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class BiometricLogSetupTransactionImpl implements
		IBiometricLogSetupTransaction {
	private static final Log log = LogFactory
			.getLog(BiometricLogSetupTransactionImpl.class);
	public static volatile BiometricLogSetupTransactionImpl objImpl = null;

	public static BiometricLogSetupTransactionImpl getInstance() {
		if (objImpl == null) {
			objImpl = new BiometricLogSetupTransactionImpl();
			return objImpl;
		}
		return objImpl;
	}

	public List<EmpBiometricLogSetupBO> getBiometricLogDetails() throws Exception {
		log.debug("inside getWorkTime");
		List<EmpBiometricLogSetupBO> list=null;
		Session session = null;
		
		try {
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from EmpBiometricLogSetupBO ebs where ebs.isActive=1");
			list=query.list();
			session.flush();
			log.debug("leaving getEducationDetails");
			return list;
		} catch (Exception e) {
			log.error("Error in getEducationDetails...", e);
			session.flush();
			session.close();
			throw new ApplicationException(e);
		}
	
	}

	public boolean addBiometricLog(EmpBiometricLogSetupBO objBO, Integer id)
			throws Exception {
		boolean flag = false;
		log.debug("inside addBiometricLog");
		Session session = null;
		Transaction tx = null;
		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			tx = session.beginTransaction();
			if (id == 0 || id == null) {
				session.save(objBO);
				flag = true;
			} else {
				session.update(objBO);
				flag = true;
			}
			tx.commit();
		} catch (ConstraintViolationException e) {
			log.error("Error during in addBiometricLog...", e);
			if (tx != null)
				tx.rollback();
			throw new BusinessException(e);
		} catch (Exception e) {
			log.error("Error in addBiometricLog...", e);
			if (tx != null)
				tx.rollback();
			throw new ApplicationException(e);
		}
		return flag;
	}

	@Override
	public EmpBiometricLogSetupBO getBiometricLogDetailsById(int id)
			throws Exception {
		log.debug("inside getWorkTime");
		EmpBiometricLogSetupBO obj=null;
		Session session = null;
		
		try {
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from EmpBiometricLogSetupBO ebs where ebs.isActive=1 and ebs.id="+id);
			obj=(EmpBiometricLogSetupBO)query.uniqueResult();
			session.flush();
			log.debug("leaving getEducationDetails");
			return obj;
		} catch (Exception e) {
			log.error("Error in getEducationDetails...", e);
			session.flush();
			session.close();
			throw new ApplicationException(e);
		}
	}
}
