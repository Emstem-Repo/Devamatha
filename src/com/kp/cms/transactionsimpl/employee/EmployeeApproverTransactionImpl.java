package com.kp.cms.transactionsimpl.employee;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.Employee;
import com.kp.cms.bo.admin.EmployeeApprover;
import com.kp.cms.bo.employee.HodApprover;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.transactions.exam.IEmployeeApproverTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class EmployeeApproverTransactionImpl implements
		IEmployeeApproverTransaction {
	/**
	 * Singleton object of EmployeeApplyLeaveTransactionImpl
	 */
	private static volatile EmployeeApproverTransactionImpl impl = null;
	private static final Log log = LogFactory.getLog(EmployeeApproverTransactionImpl.class);
	private EmployeeApproverTransactionImpl() {
		
	}
	/**
	 * return singleton object of EmployeeApplyLeaveTransactionImpl.
	 * @return
	 */
	public static EmployeeApproverTransactionImpl getInstance() {
		if (impl == null) {
			impl = new EmployeeApproverTransactionImpl();
		}
		return impl;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.exam.IEmployeeApproverTransaction#getEmployeeDetailsDeptWise(java.lang.String)
	 */
	@Override
	public List<Employee> getEmployeeDetailsDeptWise(String departmentId)
			throws Exception {
		Session session = null;
		List<Employee> selectedCandidatesList = null;
		try {
			session = HibernateUtil.getSession();
			String query="from Employee e where e.isActive=1";
			if(departmentId!=null && !departmentId.isEmpty()){
				query=query+" and e.department.id=" +departmentId;
						
			}
			Query selectedCandidatesQuery=session.createQuery(query);
			selectedCandidatesList =selectedCandidatesQuery.list();
			return selectedCandidatesList;
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
	 * @see com.kp.cms.transactions.exam.IEmployeeApproverTransaction#getAllApproverEmployees()
	 */
	@Override
	public Map<Integer, Employee> getAllApproverEmployees() throws Exception {
		Session session = null;
		Map<Integer, Employee> map = new HashMap<Integer, Employee>();
		List<EmployeeApprover> selectedCandidatesList = null;
		try {
			session = HibernateUtil.getSession();
			String query="from EmployeeApprover e where e.isActive=1";
			Query selectedCandidatesQuery=session.createQuery(query);
			selectedCandidatesList =selectedCandidatesQuery.list();
			if(selectedCandidatesList != null){
				Iterator<EmployeeApprover> iterator = selectedCandidatesList.iterator();
				while (iterator.hasNext()) {
					EmployeeApprover employeeApprover = (EmployeeApprover) iterator.next();
					if(employeeApprover.getEmployee() != null &&  employeeApprover.getApprover() != null){
						map.put(employeeApprover.getEmployee().getId(), employeeApprover.getApprover());
					}
					
				}
			}
			return map;
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
	 * @see com.kp.cms.transactions.exam.IEmployeeApproverTransaction#saveDetails(java.util.List)
	 */
	@Override
	public boolean saveDetails(List<EmployeeApprover> boList) throws Exception {
		Session session = null;
		Transaction transaction = null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			transaction.begin();
			if(boList != null){
				Iterator<EmployeeApprover> iterator = boList.iterator();
				while (iterator.hasNext()) {
					EmployeeApprover employeeApprover = (EmployeeApprover) iterator.next();
					String query = "from EmployeeApprover e where e.employee.id="+employeeApprover.getEmployee().getId();
					EmployeeApprover approver = (EmployeeApprover)session.createQuery(query).uniqueResult();
					if(approver != null){
						approver.setApprover(employeeApprover.getApprover());
						approver.setLastModifiedDate(employeeApprover.getLastModifiedDate());
						approver.setModifiedBy(employeeApprover.getModifiedBy());
						session.update(approver);
					}else{
						session.save(employeeApprover);
					}
				}
			}
			transaction.commit();
			session.flush();
			session.close();
			return true;
		} catch (Exception e) {
			if ( transaction != null){
				transaction.rollback();
			}
			if (session != null){
				session.flush();
				session.close();
			}
			return false;
		}
		
	}
	
	public boolean saveHodDetails(List<HodApprover> boList) throws Exception {
		Session session = null;
		Transaction transaction = null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			transaction.begin();
			if(boList != null){
				Iterator<HodApprover> iterator = boList.iterator();
				while (iterator.hasNext()) {
					HodApprover hodApprover = (HodApprover) iterator.next();
					String query = "from HodApprover e where e.employee.id="+hodApprover.getEmployee().getId();
					HodApprover approver = (HodApprover)session.createQuery(query).uniqueResult();
					if(approver != null){
						approver.setApprover(hodApprover.getApprover());
						approver.setLastModifiedDate(hodApprover.getLastModifiedDate());
						approver.setModifiedBy(hodApprover.getModifiedBy());
						session.update(approver);
					}else{
						session.save(hodApprover);
					}
				}
			}
			transaction.commit();
			session.flush();
			session.close();
			return true;
		} catch (Exception e) {
			if ( transaction != null){
				transaction.rollback();
			}
			if (session != null){
				session.flush();
				session.close();
			}
			return false;
		}
		
	}
	
	public Map<Integer, Employee> getAllHodApproverEmployees() throws Exception {
		Session session = null;
		Map<Integer, Employee> map = new HashMap<Integer, Employee>();
		List<HodApprover> selectedCandidatesList = null;
		try {
			session = HibernateUtil.getSession();
			String query="from HodApprover e where e.isActive=1";
			Query selectedCandidatesQuery=session.createQuery(query);
			selectedCandidatesList =selectedCandidatesQuery.list();
			if(selectedCandidatesList != null){
				Iterator<HodApprover> iterator = selectedCandidatesList.iterator();
				while (iterator.hasNext()) {
					HodApprover employeeApprover = (HodApprover) iterator.next();
					if(employeeApprover.getEmployee() != null &&  employeeApprover.getApprover() != null){
						map.put(employeeApprover.getEmployee().getId(), employeeApprover.getApprover());
					}
					
				}
			}
			return map;
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
}
