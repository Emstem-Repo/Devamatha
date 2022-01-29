package com.kp.cms.transactionsimpl.employee;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.Employee;
import com.kp.cms.bo.admin.Users;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.helpers.usermanagement.Base64Coder;
import com.kp.cms.transactions.employee.IGeneratePasswordForEmployeeTransaction;
import com.kp.cms.utilities.EncryptUtil;
import com.kp.cms.utilities.InitSessionFactory;
import com.kp.cms.utilities.PasswordGenerator;

public class GeneratePasswordForEmployeeTransactionimpl implements	IGeneratePasswordForEmployeeTransaction {

	/**
	 * Singleton object of GeneratePasswordForEmployeeTransactionimpl
	 */
	private static volatile GeneratePasswordForEmployeeTransactionimpl generatePasswordForEmployeeTransactionimpl = null;
	private static final Log log = LogFactory.getLog(GeneratePasswordForEmployeeTransactionimpl.class);
	private GeneratePasswordForEmployeeTransactionimpl() {
		
	}
	/**
	 * return singleton object of GeneratePasswordForEmployeeTransactionimpl.
	 * @return
	 */
	public static GeneratePasswordForEmployeeTransactionimpl getInstance() {
		if (generatePasswordForEmployeeTransactionimpl == null) {
			generatePasswordForEmployeeTransactionimpl = new GeneratePasswordForEmployeeTransactionimpl();
		}
		return generatePasswordForEmployeeTransactionimpl;
	}
	/* (non-Javadoc)
	 * getting the list of users for whom the password has to be updated
	 * @see com.kp.cms.transactions.employee.IGeneratePasswordForEmployeeTransaction#getUsersToUpdatePassword(java.lang.String)
	 */
	@Override
	public boolean updatePassword(String query,String userId) throws Exception {

		Session session = null;
		Transaction txn=null;
		boolean isUpdated = false;
		try {
			session = InitSessionFactory.getInstance().openSession();
			txn=session.beginTransaction();
			List<Users> usersList = session.createQuery(query).list();
			if(usersList!=null && !usersList.isEmpty()){
				Iterator<Users> itr=usersList.iterator();
				while (itr.hasNext()) {
					Users users = (Users) itr.next();
					String randPass=PasswordGenerator.getPassword();
					EncryptUtil encUtil=EncryptUtil.getInstance();
					String encpass=null;
					if(randPass!=null && !StringUtils.isEmpty(randPass.trim())){
						encpass=encUtil.encryptDES(randPass);
						users.setAndroidPwd(Base64Coder.encodeString(randPass));
					}
					users.setPwd(encpass);
					users.setModifiedBy(userId);
					users.setLastModifiedDate(new Date());
					session.update(users);
					isUpdated=true;
				}
			}
			txn.commit();
			return isUpdated;
		} catch (Exception e) {
			isUpdated=false;
			if(txn!=null)
				txn.rollback();
			log.error("Error while retrieving selected candidates.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
	}


}
