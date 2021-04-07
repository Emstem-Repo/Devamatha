package com.kp.cms.transactionsimpl.employee;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.EmpWorkDairy;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.handlers.employee.EducationMasterHandler;
import com.kp.cms.transactions.employee.IWorkDiaryTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class WorkDiaryTransactionImpl implements IWorkDiaryTransaction {
	private static final Log log = LogFactory.getLog(EducationMasterHandler.class);
	public static volatile WorkDiaryTransactionImpl workDiaryTransactionImpl = null;

	public static WorkDiaryTransactionImpl getInstance() {
		if (workDiaryTransactionImpl == null) {
			workDiaryTransactionImpl = new WorkDiaryTransactionImpl();
			return workDiaryTransactionImpl;
		}
		return workDiaryTransactionImpl;
	}	
	
	public boolean addWorkDiary(EmpWorkDairy workDairy, String mode) throws Exception {
		log.debug("inside addWorkDiary");
		Session session = null;
		Transaction transaction = null;
		try {
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			if("edit".equalsIgnoreCase(mode)){
				session.merge(workDairy);
			}
			else
			{
				session.save(workDairy);
			}
			transaction.commit();
			if (session != null) {
				session.flush();
				session.close();
			}
			log.debug("leaving addWorkDiary");
			return true;
		} catch (ConstraintViolationException e) {
			transaction.rollback();
			log.error("Error during in addWorkDiary..." , e);
			throw new BusinessException(e);
		} catch (Exception e) {
			transaction.rollback();
			log.error("Error in addWorkDiary..." , e);
			throw new ApplicationException(e);
		}

	}
	

}
