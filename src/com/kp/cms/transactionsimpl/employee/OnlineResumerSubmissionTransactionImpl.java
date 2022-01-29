package com.kp.cms.transactionsimpl.employee;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.Country;
import com.kp.cms.bo.admin.EmpAcheivement;
import com.kp.cms.bo.admin.EmpEducationDetails;
import com.kp.cms.bo.admin.EmpJobType;
import com.kp.cms.bo.admin.EmpOnlineResume;
import com.kp.cms.bo.admin.EmpPreviousOrg;
import com.kp.cms.bo.admin.Nationality;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.transactions.employee.IOnlineResumerSubmissionTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class OnlineResumerSubmissionTransactionImpl implements
		IOnlineResumerSubmissionTransaction {
	private static final Log log = LogFactory
			.getLog(OnlineResumerSubmissionTransactionImpl.class);

	public static volatile OnlineResumerSubmissionTransactionImpl obImpl = null;

	public static OnlineResumerSubmissionTransactionImpl getInstance() {
		if (obImpl == null) {
			obImpl = new OnlineResumerSubmissionTransactionImpl();
		}
		return obImpl;
	}

	@Override
	public boolean saveOnlineResume(EmpOnlineResume objBO) throws Exception {
		boolean result = false;
		Session session = null;
		Transaction txn = null;
		try {
			session = HibernateUtil.getSession();
			txn = session.beginTransaction();
			session.saveOrUpdate(objBO);
			txn.commit();
			session.flush();
			session.close();
			result = true;
		} catch (ConstraintViolationException e) {
			if (session.isOpen()) {
				txn.rollback();
			}
			log.error("Error during saveOnlineResume..." + e);
			throw new BusinessException(e);
		} catch (Exception e) {
			if (session.isOpen()) {
				txn.rollback();
			}
			log.error("Error during saveOnlineResume..." + e);
			throw new ApplicationException(e);
		}
		return result;
	}


}
