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

import com.kp.cms.bo.admin.EmpLeave;
import com.kp.cms.bo.employee.EmpType;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.employee.LeaveInitializationForm;
import com.kp.cms.transactions.employee.ILeaveInitializationTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class LeaveInitializationTransactionImpl implements
		ILeaveInitializationTransaction {
	/**
	 * Singleton object of LeaveInitializationTransactionImpl
	 */
	private static volatile LeaveInitializationTransactionImpl leaveInitializationTransactionImpl = null;
	private static final Log log = LogFactory.getLog(LeaveInitializationTransactionImpl.class);
	private LeaveInitializationTransactionImpl() {
		
	}
	/**
	 * return singleton object of LeaveInitializationTransactionImpl.
	 * @return
	 */
	public static LeaveInitializationTransactionImpl getInstance() {
		if (leaveInitializationTransactionImpl == null) {
			leaveInitializationTransactionImpl = new LeaveInitializationTransactionImpl();
		}
		return leaveInitializationTransactionImpl;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.employee.ILeaveInitializationTransaction#getExistedData(com.kp.cms.forms.employee.LeaveInitializationForm)
	 */
	@Override
	public Map<String, EmpLeave> getExistedData(LeaveInitializationForm leaveInitializationForm,int accumulateLeaveTypeId) throws Exception {
		Session session = null;
		List<EmpLeave> list = null;
		Map<String, EmpLeave> map=new HashMap<String, EmpLeave>();
		try {
			session = HibernateUtil.getSession();
			Query selectedCandidatesQuery=session.createQuery("from EmpLeave e where e.isActive=1 and e.year=" +(Integer.parseInt(leaveInitializationForm.getYear())-1)+
					" and e.empLeaveType.id !=" +accumulateLeaveTypeId+
					" group by e.employee.id,e.empLeaveType.id");
			list = selectedCandidatesQuery.list();
			if (list!=null && !list.isEmpty()) {
				Iterator<EmpLeave> itr=list.iterator();
				while (itr.hasNext()) {
					EmpLeave bo = (EmpLeave) itr.next();
					if(bo.getEmployee() != null && bo.getEmpLeaveType() != null){
						map.put(bo.getEmployee().getId()+"_"+bo.getEmpLeaveType().getId(),bo);
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
	 * @see com.kp.cms.transactions.employee.ILeaveInitializationTransaction#getOldAcumulateLeaves(java.lang.String, com.kp.cms.forms.employee.LeaveInitializationForm)
	 */
	@Override
	public Map<Integer, EmpLeave> getOldAcumulateLeaves(int accLeaveId, LeaveInitializationForm leaveInitializationForm) throws Exception {
		Session session = null;
		List<EmpLeave> list = null;
		Map<Integer, EmpLeave> map=new HashMap<Integer, EmpLeave>();
		try {
			session = HibernateUtil.getSession();
			Query selectedCandidatesQuery=session.createQuery("from EmpLeave e where e.isActive=1 and e.year=" +(Integer.parseInt(leaveInitializationForm.getYear())-1)+
					" and e.empLeaveType.id =" +accLeaveId+
					" group by e.employee.id,e.empLeaveType.id");
			list = selectedCandidatesQuery.list();
			if (list!=null && list.isEmpty()) {
				Iterator<EmpLeave> itr=list.iterator();
				while (itr.hasNext()) {
					EmpLeave bo = (EmpLeave) itr.next();
					map.put(bo.getEmployee().getId(),bo);
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
	 * @see com.kp.cms.transactions.employee.ILeaveInitializationTransaction#saveLeaveInitialization(java.util.List)
	 */
	@Override
	public boolean saveLeaveInitialization(List<EmpLeave> boList)
			throws Exception {
		log.debug("inside saveLeaveInitialization");
		Session session = null;
		Transaction transaction = null;
		EmpLeave empLeave;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			transaction.begin();
			Iterator<EmpLeave> tcIterator = boList.iterator();
			int count = 0;
			while(tcIterator.hasNext()){
				empLeave = tcIterator.next();
				session.save(empLeave);
				if(++count % 20 == 0){
					session.flush();
					session.clear();
				}
			}
			
			transaction.commit();
			session.flush();
			//session.close();
			log.debug("leaving saveLeaveInitialization");
			return true;
		} catch (ConstraintViolationException e) {
			transaction.rollback();
			log.error("Error in saveLeaveInitialization impl...", e);
			throw new BusinessException(e);
		} catch (Exception e) {
			transaction.rollback();
			log.error("Error in addTermsConditionCheckList impl...", e);
			throw new ApplicationException(e);
		}
	}
	@Override
	public Map<Integer, String> getMonthByEmployeeType() throws Exception {
		Session session = null;
		List<EmpType> list = null;
		Map<Integer, String> map=new HashMap<Integer, String>();
		try {
			session = HibernateUtil.getSession();
			Query selectedCandidatesQuery=session.createQuery("from EmpType e where e.isActive=1");
			list = selectedCandidatesQuery.list();
			if (list!=null && !list.isEmpty()) {
				Iterator<EmpType> itr=list.iterator();
				while (itr.hasNext()) {
					EmpType bo = (EmpType) itr.next();
					map.put(bo.getId(),bo.getLeaveInitializeMonth());
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
