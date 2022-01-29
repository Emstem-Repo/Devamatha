package com.kp.cms.transactionsimpl.usermanagement;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;

import com.kp.cms.bo.admin.Users;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.transactions.usermanagement.ICreateUserTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class CreateUserTransactionImpl implements ICreateUserTransaction {
	/**
	 * Singleton object of CreateUserTransactionImpl
	 */
	private static volatile CreateUserTransactionImpl createUserTransactionImpl = null;
	private static final Log log = LogFactory.getLog(CreateUserTransactionImpl.class);
	private CreateUserTransactionImpl() {

	}
	/**
	 * return singleton object of CreateUserTransactionImpl.
	 * @return
	 */
	public static CreateUserTransactionImpl getInstance() {
		if (createUserTransactionImpl == null) {
			createUserTransactionImpl = new CreateUserTransactionImpl();
		}
		return createUserTransactionImpl;
	}
	@Override
	public boolean isEmployeeDuplicated(String employeeId, int id)
			throws Exception {
		log.debug("inside isUserNameDuplcated");
		Session session = null;
		boolean result = false;
		try {
			session = HibernateUtil.getSession();
			StringBuffer sqlString = new StringBuffer(
					"from Users u where u.employee.id = :eId");
			if (id != 0) {
				sqlString = sqlString.append(" and id != :userId");
			}
			Query query = session.createQuery(sqlString.toString());

			query.setString("eId", employeeId);
			if (id != 0) {
				query.setInteger("userId", id);
			}
			List<Users> list = query.list();
			session.flush();
			log.debug("leaving isUserNameDuplcated");
			if (!list.isEmpty()) {
				result = true;
			}
		} catch (Exception e) {
			log.error("Error in duplication checking...", e);
			session.flush();
			throw new ApplicationException(e);
		}
		return result;
	}


			public boolean isGuestDuplicated(String guestId, int id)
			throws Exception {
			log.debug("inside isUserNameDuplcated");
			Session session = null;
			boolean result = false;
			try {
			session = HibernateUtil.getSession();
			StringBuffer sqlString = new StringBuffer(
					"from Users u where u.guest.id = :eId");
			if (id != 0) {
				sqlString = sqlString.append(" and id != :userId");
			}
			Query query = session.createQuery(sqlString.toString());
			
			query.setString("eId", guestId);
			if (id != 0) {
				query.setInteger("userId", id);
			}
			List<Users> list = query.list();
			session.flush();
			log.debug("leaving isUserNameDuplcated");
			if (!list.isEmpty()) {
				result = true;
			}
			} catch (Exception e) {
			log.error("Error in duplication checking...", e);
			session.flush();
			throw new ApplicationException(e);
			}
			return result;
			}
}
