package com.kp.cms.transactionsimpl.admin;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;

import com.kp.cms.bo.admin.BulkSMStoEmployeeMobileMessaging;
import com.kp.cms.bo.admin.Roles;
import com.kp.cms.bo.admin.Users;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.transactions.admin.IBulkSMStoEmployeesTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class BulkSMStoEmployeesTransactionImpl implements IBulkSMStoEmployeesTransaction {
	private static volatile BulkSMStoEmployeesTransactionImpl bulkSMStoEmployeesTransactionImpl = null;
	private static final Log log = LogFactory.getLog(BulkSMStoEmployeesTransactionImpl.class);
	
	public static BulkSMStoEmployeesTransactionImpl getInstance()
	{
		if(bulkSMStoEmployeesTransactionImpl==null)
		{
			bulkSMStoEmployeesTransactionImpl = new BulkSMStoEmployeesTransactionImpl();
		}
		return bulkSMStoEmployeesTransactionImpl;
		
	}

	@Override
	public Map<Integer, String> getRollMap() throws Exception {
		log.debug("call of getRollMap method in BulkSMStoEmployeesTransactionImpl.class");
		Map<Integer, String> rollMap = new HashMap<Integer, String>();
		Session session = null;
		try
		{
			session = HibernateUtil.getSession();
			String hqlQuery = "select r.id,r.name from Roles r where r.isActive=1";
			Query query = session.createQuery(hqlQuery);
			List<Object[]>  list = query.list();
			if(list!=null)
			{
				Iterator<Object[]> iterator = list.iterator();
				while(iterator.hasNext())
				{
					Object[] objects = iterator.next();
					rollMap.put(Integer.parseInt(objects[0].toString()), objects[1].toString());
				}
			}
		}
		catch (Exception e) {
			log.error("Error in getRollMap"+e.getMessage());
			throw new ApplicationException(e);
		}
		finally
		{
			if(session!=null)
			{
				session.close();
			}
		}
		log.debug("end of getRollMap method in BulkSMStoEmployeesTransactionImpl.class");
		return rollMap;
	}

	@Override
	public List<Users> getEmployeeList(String selectedRolls) throws Exception {
		log.debug("call of getEmployeeList method in BulkSMStoEmployeesTransactionImpl.class");
		List<Users> userBoList =  new ArrayList<Users>();
		Session session = null;
		try
		{
			session =  HibernateUtil.getSession();
			String hqlQuery = "select u from Users u where u.isActive=1 and u.roles.id in ("+selectedRolls+")";
			Query query = session.createQuery(hqlQuery);
			userBoList = query.list();
		}
		catch (Exception e) {
			log.error("Error in getEmployeeList method in BulkSMStoEmployeesTransactionImpl.class");
			log.error("Error is"+e.getMessage());
			throw new ApplicationException(e);
		}
		log.debug("end of getEmployeeList method in BulkSMStoEmployeesTransactionImpl.class");
		return userBoList;
	}

	@Override
	public List<Integer> getUserIds(Date smsDate) throws Exception {
		log.debug("call of getUserIds method in BulkSMStoEmployeesTransactionImpl.class");
		List<Integer> useridList = new ArrayList<Integer>();
		Session session = null;
		try
		{
			session =  HibernateUtil.getSession();
			String hqlQuery = "select a.userId from  BulkSMStoEmployeeMobileMessaging a where a.smsDate='"+smsDate+"'";
			Query query = session.createQuery(hqlQuery);
			useridList = query.list();
			
		}
		catch (Exception e) {
			log.error("Error in BulkSMStoEmployeesTransactionImpl.class");
			
		}
		finally
		{
			if(session!=null)
			{
				session.flush();
			}
		}
		
		log.debug("end of getStudentIds method in BulkSMStoStudentTransactionImpl.class");
		return useridList;
	}
}
