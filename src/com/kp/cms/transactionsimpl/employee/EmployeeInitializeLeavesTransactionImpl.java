package com.kp.cms.transactionsimpl.employee;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.actions.employee.EmployeeInitializeLeavesAction;
import com.kp.cms.bo.admin.EmpInitializeLeaves;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.employee.EmployeeInitializeLeavesForm;
import com.kp.cms.transactions.employee.IEmployeeInitializeLeavesTransaction;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;

public class EmployeeInitializeLeavesTransactionImpl implements
		IEmployeeInitializeLeavesTransaction {
	private static final Log log = LogFactory.getLog(EmployeeInitializeLeavesAction.class);

	@Override
	public List<EmpInitializeLeaves> getInitializeData() throws Exception {
		Session session = null;
		List<EmpInitializeLeaves> selectedCandidatesList = null;
		try {
			session =InitSessionFactory.getInstance().openSession();
			String SearchCriteria="from EmpInitializeLeaves e where e.isActive=1";
			Query selectedCandidatesQuery=session.createQuery(SearchCriteria);
			selectedCandidatesList = selectedCandidatesQuery.list();
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
	 * @see com.kp.cms.transactions.employee.IEmployeeInitializeLeavesTransaction#addEmployeeInitialize(com.kp.cms.bo.admin.EmpInitializeLeaves)
	 */
	@Override
	public boolean addEmployeeInitialize(EmpInitializeLeaves empInitializeLeaves)
			throws Exception {
		Session session = null;
		Transaction transaction = null;
		try {
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			session.save(empInitializeLeaves);
			transaction.commit();
			session.flush();
			return true;
		} catch (Exception e) {
			if ( transaction != null){
				transaction.rollback();
			}
			if (session != null){
				session.flush();
			}
			return false;
		}
	}

	@Override
	public EmpInitializeLeaves getEmpInitializeLeavesByEmpTypeId(
			String empTypeId,String leaveTypeId,String allotedDate) throws Exception {
		Session session = null;
		EmpInitializeLeaves selectedCandidatesList = null;
		String[] date= allotedDate.split("/");
		try {
			session = HibernateUtil.getSession();
			String SearchCriteria="from EmpInitializeLeaves e where e.empTypeId.id="+empTypeId+" and e.leaveType.id="+leaveTypeId+" and YEAR(e.allotedDate)='"+date[2]+"' and month(e.allotedDate)='"+date[1]+"'";
			Query selectedCandidatesQuery=session.createQuery(SearchCriteria);
			selectedCandidatesList = (EmpInitializeLeaves)selectedCandidatesQuery.uniqueResult();
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
	 * @see com.kp.cms.transactions.employee.IEmployeeInitializeLeavesTransaction#deleteEmployeeInitialize(int)
	 */
	public boolean deleteEmployeeInitialize(int empId) throws Exception {
		
		Session session = null;
		Transaction transaction = null;
		EmpInitializeLeaves empInitializeLeaves = null;
		try
		{
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			Query query = session.createQuery("from EmpInitializeLeaves e where e.id='"+empId+"' and e.isActive = 1");
			empInitializeLeaves = (EmpInitializeLeaves) query.uniqueResult();
			empInitializeLeaves.setIsActive(false);
			transaction.commit();
			session.flush();
			return true;
		}
		catch (Exception e) {
			if(transaction != null)
			{
				transaction.rollback();
			}
			log.error("Error while deleteing Employee Leave Initialize .." +e);
			throw  new ApplicationException(e);
		}
		finally
		{
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
	}
	
	/**
	 * @param employeeInitializeLeavesForm
	 * @return
	 * @throws Exception
	 */
	public EmpInitializeLeaves getEmpInitializeLeavesToEdit(EmployeeInitializeLeavesForm employeeInitializeLeavesForm)throws Exception{
		
		Session session = null;
		EmpInitializeLeaves empInitializeLeaves = null;
		try
		{
			session =HibernateUtil.getSession();
			Query query = session.createQuery("from EmpInitializeLeaves e where e.id='"+employeeInitializeLeavesForm.getId()+"' and e.isActive = 1 ");
			empInitializeLeaves = (EmpInitializeLeaves) query.uniqueResult();
			return empInitializeLeaves;
		}
		catch (Exception e) {
			log.error("Error occured to getEmpInitializeLeavesToEdit "+e);
			throw new ApplicationException(e);
		}finally
		{
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
	}
	
	/**
	 * @param empInitializeLeaves
	 * @return
	 * @throws Exception
	 */
	public boolean updateEmployeeInitialize(EmpInitializeLeaves empInitializeLeaves)throws Exception {
		Session session = null;
		Transaction transaction = null;
		try {
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			session.update(empInitializeLeaves);
			transaction.commit();
			session.flush();
			return true;
		} catch (Exception e) {
			log.error("Error occured updateEmployeeInitialize in IMPL "+e);
			if ( transaction != null){
				transaction.rollback();
			}
			if (session != null){
				session.flush();
			}
			return false;
		}
	}
	
	/**
	 * @param empId
	 * @return
	 * @throws Exception
	 */
	public boolean reActivateEmployeeInitialize(int empId) throws Exception {
		
		Session session = null;
		Transaction transaction = null;
		EmpInitializeLeaves empInitializeLeaves = null;
		try
		{
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			Query query = session.createQuery("from EmpInitializeLeaves e where e.id='"+empId+"'");
			empInitializeLeaves = (EmpInitializeLeaves) query.uniqueResult();
			empInitializeLeaves.setIsActive(true);
			transaction.commit();
			session.flush();
			return true;
		}
		catch (Exception e) {
			if(transaction != null)
			{
				transaction.rollback();
			}
			log.error("Error while deleteing Employee Leave Initialize .." +e);
			throw  new ApplicationException(e);
		}
		finally
		{
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
	}
}
