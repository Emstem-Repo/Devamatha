package com.kp.cms.transactionsimpl.employee;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.EmpAllowance;
import com.kp.cms.bo.admin.Employee;
import com.kp.cms.bo.admin.PayScaleBO;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.to.admin.StudentTO;
import com.kp.cms.transactions.employee.IUploadPayScaleGradeTransaction;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;

public class UploadPayScaleGradeTransactionimpl implements IUploadPayScaleGradeTransaction {
	/**
	 * Singleton object of UploadPayScaleGradeTransactionimpl
	 */
	private static volatile UploadPayScaleGradeTransactionimpl uploadPayScaleGradeTransactionimpl = null;
	private static final Log log = LogFactory.getLog(UploadPayScaleGradeTransactionimpl.class);
	private UploadPayScaleGradeTransactionimpl() {
		
	}
	/**
	 * return singleton object of UploadPayScaleGradeTransactionimpl.
	 * @return
	 */
	public static UploadPayScaleGradeTransactionimpl getInstance() {
		if (uploadPayScaleGradeTransactionimpl == null) {
			uploadPayScaleGradeTransactionimpl = new UploadPayScaleGradeTransactionimpl();
		}
		return uploadPayScaleGradeTransactionimpl;
	}
	/* (non-Javadoc)
	 * getting the employee BO and setting the empId and fingerPrintId to map
	 * @see com.kp.cms.transactions.employee.IUploadPayScaleGradeTransaction#getEmpMap()
	 */
	@Override
	public Map<String, Integer> getEmpMap() throws Exception {

		Session session = null;
		Map<String, Integer> empMap = new HashMap<String, Integer>();
		try {
			session = HibernateUtil.getSession();
			String query= "from Employee e where e.active=1 and e.isActive=1 ";
			List<Employee> emp = session.createQuery(query).list();
			if(emp!=null && !emp.isEmpty()){
				Iterator<Employee> itr=emp.iterator();
				while (itr.hasNext()) {
					Employee employee = (Employee) itr.next();
					if(employee.getFingerPrintId()!=null){
					empMap.put(employee.getFingerPrintId().toUpperCase(), employee.getId());
					}
				}
			}
			return empMap;
		} catch (Exception e) {
			log.error("Error while retrieving selected candidates.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
	}
	/* (non-Javadoc)
	 * querying the database to get payScaleBo and a map is returned with key as payScale and value as id
	 * @see com.kp.cms.transactions.employee.IUploadPayScaleGradeTransaction#getPayScale()
	 */
	@Override
	public Map<String, Integer> getPayScale() throws Exception {

		Session session = null;
		Map<String, Integer> payScale = new HashMap<String, Integer>();
		try {
			session = HibernateUtil.getSession();
			String query= "from PayScaleBO p where p.isActive=1 ";
			List<PayScaleBO> payScaleList = session.createQuery(query).list();
			if(payScaleList!=null &&!payScaleList.isEmpty()){
				Iterator<PayScaleBO> itr=payScaleList.iterator();
				while (itr.hasNext()) {
					PayScaleBO payScaleBO = (PayScaleBO) itr.next();
					payScale.put(payScaleBO.getPayScale(), payScaleBO.getId());
				}
			}
			return payScale;
		} catch (Exception e) {
			log.error("Error while retrieving selected candidates.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
	}
	/* (non-Javadoc)
	 * a map of allowance name and id is returned back
	 * @see com.kp.cms.transactions.employee.IUploadPayScaleGradeTransaction#getAllowanceDetails()
	 */
	@Override
	public Map<String, Integer> getAllowanceDetails() throws Exception {

		Session session = null;
		Map<String, Integer> allowanceMap = new HashMap<String, Integer>();
		try {
			session = HibernateUtil.getSession();
			String query= "from EmpAllowance e where e.isActive=1 ";
			List<EmpAllowance> empAllowance = session.createQuery(query).list();
			if(empAllowance!=null && !empAllowance.isEmpty()){
				Iterator<EmpAllowance> itr=empAllowance.iterator();
				while (itr.hasNext()) {
					EmpAllowance employeeAllowance = (EmpAllowance) itr.next();
					if(employeeAllowance.getName()!=null)
						allowanceMap.put(employeeAllowance.getName(),employeeAllowance.getId());
				}
			}
			return allowanceMap;
		} catch (Exception e) {
			log.error("Error while retrieving selected candidates.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
	}
	/* (non-Javadoc)
	 * querying the database to get payScaleBo and a map is returned with key as payScale and value as scale
	 * @see com.kp.cms.transactions.employee.IUploadPayScaleGradeTransaction#getPayScaleMap()
	 */
	@Override
	public Map<String, String> getPayScaleMap() throws Exception {

		Session session = null;
		Map<String, String> payScale = new HashMap<String, String>();
		try {
			session = HibernateUtil.getSession();
			String query= "from PayScaleBO p where p.isActive=1 ";
			List<PayScaleBO> payScaleList = session.createQuery(query).list();
			if(payScaleList!=null &&!payScaleList.isEmpty()){
				Iterator<PayScaleBO> itr=payScaleList.iterator();
				while (itr.hasNext()) {
					PayScaleBO payScaleBO = (PayScaleBO) itr.next();
					payScale.put(payScaleBO.getPayScale(), payScaleBO.getScale());
				}
			}
			return payScale;
		} catch (Exception e) {
			log.error("Error while retrieving selected candidates.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
	}
	/* (non-Javadoc)
	 * adding the uploaded data to database
	 * @see com.kp.cms.transactions.employee.IUploadPayScaleGradeTransaction#addUploadedData(java.util.List, java.lang.String)
	 */
	@Override
	public boolean addUploadedData(List<Employee> empBoList, String user)
			throws Exception {
		Session session = null;
		boolean flagSet=false;
		Transaction txn=null;
		try {
			session = HibernateUtil.getSession();
			txn=session.beginTransaction();
			if(empBoList!=null && !empBoList.isEmpty()){
				Iterator<Employee> itr=empBoList.iterator();
				while (itr.hasNext()) {
					Employee employee = (Employee) itr.next();
					Employee bo=(Employee)session.get(Employee.class, employee.getId());
					bo.setGrossPay(employee.getGrossPay());
					bo.setPayScaleId(employee.getPayScaleId());
					bo.setScale(employee.getScale());
					bo.setEmpPayAllowance(employee.getEmpPayAllowance());
					session.update(bo);
					flagSet=true;
				}
			}
			txn.commit();
			return flagSet;
		} catch (Exception e) {
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
