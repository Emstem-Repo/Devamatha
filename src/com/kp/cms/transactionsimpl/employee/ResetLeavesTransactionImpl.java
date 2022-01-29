package com.kp.cms.transactionsimpl.employee;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.EmpInitializeLeaves;
import com.kp.cms.bo.admin.EmpLeave;
import com.kp.cms.bo.admin.Employee;
import com.kp.cms.bo.admin.TermsConditionChecklist;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.employee.ResetLeavesForm;
import com.kp.cms.transactions.employee.IResetLeavesTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class ResetLeavesTransactionImpl implements IResetLeavesTransaction {
	
	/**
	 * 
	 */
	private static final Log log = LogFactory.getLog(ResetLeavesTransactionImpl.class);
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.employee.IResetLeavesTransaction#getEmployeeByEmpTypeId(java.lang.String)
	 */
	@Override
	public List<Employee> getEmployeeByEmpTypeId(String empQuery)
			throws Exception {
		log.info("ResetLeavesTransactionImpl-getEmployeeByEmpTypeId -Entered");
		Session session=null;
		List<Employee> empList=null;
		try {
			session=HibernateUtil.getSession();
			Query query=session.createQuery(empQuery);
			empList=query.list();
			session.clear();
		} catch (Exception e) {
			log.error("Error occured in getEmployeeByEmpTypeId"+e);
		}
		log.info("ResetLeavesTransactionImpl-getEmployeeByEmpTypeId -Exit");
		return empList;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.employee.IResetLeavesTransaction#getEmpInitializeLeavesByEmpTypeId(java.lang.String)
	 */
	@Override
	public List<EmpInitializeLeaves> getEmpInitializeLeavesByEmpTypeId(
			String empInitQuery) throws Exception {
		log.info("ResetLeavesTransactionImpl-empInitQuery -Entered");
		Session session=null;
		List<EmpInitializeLeaves> empInitList=null;
		try {
			session=HibernateUtil.getSession();
			Query query=session.createQuery(empInitQuery);
			empInitList=query.list();
			session.clear();
		} catch (Exception e) {
			log.error("Error occured in empInitQuery"+e);
		}
		log.info("ResetLeavesTransactionImpl-empInitQuery -Exit");
		return empInitList;
	}
	@Override
	public Map<Integer, Integer> getExistedLeavesForLeaveTypeAndEmpType(
			String empTypeId, int id,ResetLeavesForm resetLeavesForm) throws Exception {
		log.info("ResetLeavesTransactionImpl-getEmployeeByEmpTypeId -Entered");
		Session session=null;
		List<EmpLeave> empList=null;
		Map<Integer,Integer> finalMap=new HashMap<Integer, Integer>();
		Map<Integer,EmpLeave> empLeaveMap=new HashMap<Integer, EmpLeave>();
		try {
			session=HibernateUtil.getSession();
			String empQuery="select e from EmpLeave e join e.employee.empJobs ej" +
					" where e.empLeaveType.id="+id+
					" and e.employee.isActive=1" +
					" and e.isActive=1" +
					" and ej.objEmployeeTypeBO.id="+empTypeId+
					" group by e.employee.id";
			Query query=session.createQuery(empQuery);
			empList=query.list();
			if(empList!=null){
				Iterator<EmpLeave> itr=empList.iterator();
				while (itr.hasNext()) {
					EmpLeave empLeave = (EmpLeave) itr.next();
					if (empLeave.getEmployee()!=null) {
						finalMap.put(empLeave.getEmployee().getId(),empLeave.getId());
						empLeaveMap.put(empLeave.getId(), empLeave);
					}
				}
			}
			session.clear();
			resetLeavesForm.setEmpLeaveMap(empLeaveMap);
		} catch (Exception e) {
			log.error("Error occured in getEmployeeByEmpTypeId"+e);
		}
		log.info("ResetLeavesTransactionImpl-getEmployeeByEmpTypeId -Exit");
		return finalMap;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.employee.IResetLeavesTransaction#saveEmpLeaves(java.util.List)
	 */
	@Override
	public boolean saveEmpLeaves(List<EmpLeave> empLeaveList) throws Exception {
		log.debug("inside addTermsConditionCheckList");
		Session session = null;
		Transaction transaction = null;
		EmpLeave empLeave;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			transaction.begin();
			Iterator<EmpLeave> tcIterator = empLeaveList.iterator();
			int count = 0;
			while(tcIterator.hasNext()){
				empLeave = tcIterator.next();
				session.saveOrUpdate(empLeave);
				if(++count % 20 == 0){
					session.flush();
					session.clear();
				}
			}
			transaction.commit();
			session.flush();
			//session.close();
			log.debug("leaving addTermsConditionCheckList");
			return true;
		} catch (ConstraintViolationException e) {
			transaction.rollback();
			log.error("Error in addTermsConditionCheckList impl...", e);
			throw new BusinessException(e);
		} catch (Exception e) {
			transaction.rollback();
			log.error("Error in addTermsConditionCheckList impl...", e);
			throw new ApplicationException(e);
		}
	}
}