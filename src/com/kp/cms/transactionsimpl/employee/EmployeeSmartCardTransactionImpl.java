package com.kp.cms.transactionsimpl.employee;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.Employee;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.transactions.employee.IEmployeeSmartCardUploadTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class EmployeeSmartCardTransactionImpl implements IEmployeeSmartCardUploadTransaction {
	private static final Log log = LogFactory
	.getLog(EmployeeSmartCardTransactionImpl.class);
	private static volatile EmployeeSmartCardTransactionImpl cardTransactionImpl = null;
	public static EmployeeSmartCardTransactionImpl getInstance(){
		if(cardTransactionImpl == null){
			cardTransactionImpl = new EmployeeSmartCardTransactionImpl();
			return cardTransactionImpl;
		}
		return cardTransactionImpl;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.employee.IEmployeeSmartCardUploadTransaction#getFingerPrintIds()
	 */
	@Override
	public Map<String, Employee> getFingerPrintIds() throws Exception {
		Session session= null;
		Map<String,Employee> map = new HashMap<String, Employee>();
		List<Employee> emp;
		try{
			session=HibernateUtil.getSession();
			String str="from Employee emp";
			Query query = session.createQuery(str);
			emp=query.list();
		}catch (Exception e) {
			// TODO: handle exception
			throw new ApplicationException();
		}finally{
			if(session!=null)
				session.flush();
			//session.close();
		}
		if(emp!=null && !emp.isEmpty()){
			Iterator<Employee> iterator = emp.iterator();
			while (iterator.hasNext()) {
				Employee employee = (Employee) iterator.next();
				if(employee.getFingerPrintId()!=null && !employee.getFingerPrintId().isEmpty()){
					map.put(employee.getFingerPrintId(), employee);
				}
			}
		}
		return map;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.employee.IEmployeeSmartCardUploadTransaction#addEmpSmartCardNo(java.util.List, java.lang.String)
	 */
	@Override
	public boolean addEmpSmartCardNo(List<Employee> list, String userId) throws Exception {
		Session session=null;
		Transaction tx=null;
		boolean isAdded=false;
		Employee empObj = null;
		try{
			session=HibernateUtil.getSession();
			tx=session.beginTransaction();
			tx.begin();
			if(list!=null && !list.isEmpty()){
				Iterator<Employee> iterator= list.iterator();
				int count = 0; 
				while (iterator.hasNext()) {
					Employee employee = (Employee) iterator.next();
					empObj=(Employee)session.get(Employee.class, employee.getId());
					empObj.setBankAccNo(employee.getBankAccNo());
					empObj.setSmartCardNo(employee.getSmartCardNo());
					empObj.setLastModifiedDate(new Date());
					empObj.setModifiedBy(userId);
					empObj.setIsSCDataDelivered(true);
					 session.update(empObj);
					 if (++count % 20 == 0) {
							session.flush();
							session.clear();
						}
				}
			}
			tx.commit();
			isAdded = true;
		}catch (Exception e) {
			if(tx != null)
				tx.rollback();
			isAdded = false;
			log.error("Error in addEmpSmartCardNo..."+e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		log.info("end of addEmpSmartCardNo method in  EmployeeSmartCardTransactionImpl class.");
		return isAdded;
	}

}
