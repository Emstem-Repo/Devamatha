package com.kp.cms.transactionsimpl.usermanagement;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;

import com.kp.cms.bo.admin.Users;
import com.kp.cms.transactions.usermanagement.IUserReportTransaction;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.HibernateUtil;

public class UserReportTransactionImpl implements IUserReportTransaction{
	private static final Log log = LogFactory.getLog(UserInfoTransactionImpl.class);
	
	/**
	 * 
	 */
	public List<Users> getUsers(String dob, String firstName, String middleName, String lastName, String dep) throws Exception {
		log.debug("Txn Impl : getUsers");
		Session session = null;
		Boolean sqlStringAdded = false;
		try {
			 //SessionFactory sessionFactory = InitSessionFactory.getInstance();
			 //session =sessionFactory.openSession();
			 session = HibernateUtil.getSession();
			 StringBuffer sqlString = new StringBuffer("select u, " +
			 "(select users.userName from Users users where users.id= u.createdBy  and users.isActive = 1)" +
				", (select users.userName from Users users where users.id= u.modifiedBy  and users.isActive = 1)" +
				" from Users u");
			 
			 if(firstName!= null && !firstName.trim().isEmpty()){
				 sqlString = sqlString.append(" where employee.firstName LIKE '%"+firstName+"%'");
				 sqlStringAdded = true;
			 }
			 if(middleName!= null && !middleName.trim().isEmpty()){
				 if(sqlStringAdded) {
					 sqlString = sqlString.append(" OR");
				 }
				 else{
					 sqlString = sqlString.append( " where");
				 }
					 
				 sqlString = sqlString.append(" employee.middleName LIKE '%"+middleName+"%'");
				 sqlStringAdded = true;
			 }

			 if(lastName!= null && !lastName.trim().isEmpty()){
				 if(sqlStringAdded) {
					 sqlString = sqlString.append(" OR");
				 }
				 else{
					 sqlString = sqlString.append(" where");
				 }
					 
				 sqlString = sqlString.append(" employee.lastName LIKE '%"+lastName+"%'");
				 sqlStringAdded = true;
			 }
			 
			 if(dob != null && !dob.trim().isEmpty()){
				 if(sqlStringAdded) {
					 sqlString = sqlString.append(" and");
				 }
				 else{
					 sqlString = sqlString.append(" where");
				 }
				 sqlString = sqlString.append(" employee.dob = '" + CommonUtil.ConvertStringToSQLDate(dob) + "'");
				 sqlStringAdded = true;
			 }
			 if(dep != null && !dep.trim().isEmpty()){
				 if(sqlStringAdded) {
					 sqlString = sqlString .append(" and");
				 }
				 else{
					 sqlString = sqlString.append(" where");
				 }
				 sqlString = sqlString.append(" employee.department.id = '" + dep + "'");
			 }
			 if(sqlStringAdded)
				{
					sqlString=sqlString.append(" and isActive=1");
				}
				else
				{
					sqlString=sqlString.append(" where isActive=1");
				}
			 Query query = session.createQuery(sqlString.toString());
//			 query.setDate("dateOfB", CommonUtil.ConvertStringToSQLDate(dob));
			 List<Users> list = query.list();
			 //session.close();
			 //sessionFactory.close();
			 log.debug("Txn Impl : Leaving getUsers with success");
			 return list;
		 } catch (Exception e) {
			 log.debug("Txn Impl : Leaving getUsers with Exception");
			 throw e;
		 }
	}

}
