package com.kp.cms.transactionsimpl.employee;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.EmpApplyLeave;
import com.kp.cms.bo.admin.EmpLeave;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.forms.employee.EmployeeApproveLeaveForm;
import com.kp.cms.to.employee.EmployeeApproveLeaveTO;
import com.kp.cms.transactions.employee.IEmployeeApproveLeaveTxn;
import com.kp.cms.utilities.HibernateUtil;

public class EmployeeApproveLeaveTxnImpl implements IEmployeeApproveLeaveTxn {
	private static final Log log = LogFactory.getLog(EmployeeApproveLeaveTxnImpl.class);
	public static volatile EmployeeApproveLeaveTxnImpl employeeApproveLeaveTxnImpl = null;

	public static EmployeeApproveLeaveTxnImpl getInstance() {
		if (employeeApproveLeaveTxnImpl == null) {
			employeeApproveLeaveTxnImpl = new EmployeeApproveLeaveTxnImpl();
			return employeeApproveLeaveTxnImpl;
		}
		return employeeApproveLeaveTxnImpl;
	}
	/**
	 * getting leave details
	 */
	public List<EmpApplyLeave> getLeaveDetails(EmployeeApproveLeaveForm approveLeaveForm,String employeeId) throws Exception{
		log.debug("start getLeaveDetails");
		Session session = null;
		try {
 			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session =InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			StringBuffer sqlString = new StringBuffer("from EmpApplyLeave e where(status != 'approved' and status!= 'Canceled' and status!='Cancellation Pending' and status!='Cancellation On Hold' and status!='Cancellation Rejected') and e.employee.id ='"+employeeId+"'");
			Query  query = session.createQuery(sqlString.toString());
			List<EmpApplyLeave> leaveList = query.list();
			session.flush();
			
			log.debug("exit getFeeConcessionDetails");
			return leaveList;
		} catch (Exception e) {
			throw  new BusinessException(e);
		}
	}
	
	/**
	 * 
	 * @param leaveList
	 * @return
	 * @throws Exception
	 */
	public boolean updateStatus(List<EmployeeApproveLeaveTO> leaveList) throws Exception {
		Session session = null;
		Transaction txn=null;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			txn= session.beginTransaction();
			if(leaveList == null || leaveList.size() <= 0){
				return false;
			}
			Iterator<EmployeeApproveLeaveTO> leaveListItr = leaveList.iterator();
			
			while (leaveListItr.hasNext()) {
				EmployeeApproveLeaveTO employeeApproveLeaveTO = leaveListItr.next();
				String updateQuery = "update EmpApplyLeave emp set emp.status = :stat, emp.approvalRemarks= :comments"
					+ " where emp.id = :empId";
				Query query = session.createQuery(updateQuery);
				query.setString("stat", employeeApproveLeaveTO.getStatus());
				query.setString("comments", employeeApproveLeaveTO.getRemarks());
				query.setInteger("empId", employeeApproveLeaveTO.getId());
				query.executeUpdate();
			}				

			txn.commit();
			return true;
		} catch (ConstraintViolationException e) {
			if(session.isOpen()){	
				txn.rollback();
			}
			return false;
		}catch (Exception e) {
			if(session.isOpen()){	
				txn.rollback();
			}
			return false;
		}finally{
			if (session != null) {
				session.flush();
				session.close();
			}
		}
	}
	/**
	 * getting available leaves
	 */
	public List<EmpLeave> getAvailableLeaveDetails(String employeeId) throws Exception{
		log.debug("start getLeaveDetails");
		Session session = null;
		List<EmpLeave> availableLeaveList;
		try {
 			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session =InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			StringBuffer sqlString = new StringBuffer("from EmpLeave e where isActive = 1 and e.employee.id = " + employeeId);
			Query  query = session.createQuery(sqlString.toString());
			availableLeaveList = query.list();
			session.flush();
			//session.close();
			//sessionFactory.close();
			log.debug("exit getAvailableLeaveDetails");
			return availableLeaveList;
		} catch (Exception e) {
			throw  new BusinessException(e);
		}
	}
	
	public List<EmpLeave>getApprovedLeaveDetails(List<EmployeeApproveLeaveTO> approveList)throws Exception
	{
		log.debug("start getLeaveDetails");
		Session session = null;
		List<EmpLeave> leaveList=new ArrayList<EmpLeave>();
		try {
			session = HibernateUtil.getSession();
			String hqlQuery="";
			for(EmployeeApproveLeaveTO approveLeave :approveList)
			{	
				if(approveLeave.getStatus().equalsIgnoreCase("Approved"))
				{
					hqlQuery="select e from EmpLeave e " +
							 "where e.employee.id=(select l.employee.id from EmpApplyLeave l  where l.id=:leaveId) " +
							 "and e.empLeaveType.id=(select l1.empLeaveType.id from EmpApplyLeave l1  where l1.id=:leaveId)"; 
					Query  query = session.createQuery(hqlQuery);	
					query.setInteger("leaveId",approveLeave.getId());
					leaveList.add((EmpLeave)query.uniqueResult());
				}
			}
			
			session.flush();
			//session.close();
			//sessionFactory.close();
			log.debug("exit getAvailableLeaveDetails");
			return leaveList;
		} catch (Exception e) {
			throw  new BusinessException(e);
		}
	}
	
	public void updateLeavesSanctioned(List<EmpLeave> leaveL1ist)throws Exception
	{
		Session session = null;
		Transaction transaction = null;
		try {
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			int count=0;
			for(EmpLeave  leave :leaveL1ist)
			{	
				session.update(leave);
				if(++count%20==0)
				{
					session.flush();
					count=0;
				}
			}
			transaction.commit();
			session.flush();
		} 
		catch (ConstraintViolationException e) {
			if (transaction != null) {
				transaction.rollback();
			}
			log.error("Error while saving interview result data..", e);
			throw new DuplicateException(e);
		} catch (Exception e) {
			throw  new BusinessException(e);
		}
	}
	
	public List<EmpApplyLeave> getCancelLeaveDetails(EmployeeApproveLeaveForm approveLeaveForm,String employeeId) throws Exception{
		log.debug("start getLeaveDetails");
		Session session = null;
		try {
 			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session =InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			StringBuffer sqlString = new StringBuffer("from EmpApplyLeave e where (status='Cancellation Pending' or status='Cancellation On Hold' or status='Cancellation Rejected') and e.employee.employeeByReportToId ='"+employeeId+"'");
			Query  query = session.createQuery(sqlString.toString());
			List<EmpApplyLeave> leaveList = query.list();
			session.flush();
			//session.close();
			//sessionFactory.close();
			log.debug("exit getFeeConcessionDetails");
			return leaveList;
		} catch (Exception e) {
			throw  new BusinessException(e);
		}
	}
	
	public boolean updateCancelStatus(List<EmployeeApproveLeaveTO> leaveList) throws Exception {
		Session session = null;
		Transaction txn=null;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			txn= session.beginTransaction();
			if(leaveList == null || leaveList.size() <= 0){
				return false;
			}
			Iterator<EmployeeApproveLeaveTO> leaveListItr = leaveList.iterator();
			
			while (leaveListItr.hasNext()) {
				EmployeeApproveLeaveTO employeeApproveLeaveTO = leaveListItr.next();
				String updateQuery = "update EmpApplyLeave emp set emp.status = :stat, emp.cancelRemarks= :comments"
					+ " where emp.id = :empId";
				Query query = session.createQuery(updateQuery);
				query.setString("stat", employeeApproveLeaveTO.getStatus());
				query.setString("comments", employeeApproveLeaveTO.getCancelRemarks());
				query.setInteger("empId", employeeApproveLeaveTO.getId());
				query.executeUpdate();
			}				

			txn.commit();
			return true;
		} catch (ConstraintViolationException e) {
			if(session.isOpen()){	
				txn.rollback();
			}
			return false;
		}catch (Exception e) {
			if(session.isOpen()){	
				txn.rollback();
			}
			return false;
		}finally{
			if (session != null) {
				session.flush();
				session.close();
			}
		}
	}

	public List<EmpLeave>getCanceledLeaveDetails(List<EmployeeApproveLeaveTO> approveList)throws Exception
	{
		log.debug("start getLeaveDetails");
		Session session = null;
		List<EmpLeave> leaveList=new ArrayList<EmpLeave>();
		try {
			session = HibernateUtil.getSession();
			String hqlQuery="";
			for(EmployeeApproveLeaveTO approveLeave :approveList)
			{	
				if(approveLeave.getStatus().equalsIgnoreCase("Canceled"))
				{
					hqlQuery="select e from EmpLeave e " +
							 "where e.employee.id=(select l.employee.id from EmpApplyLeave l  where l.id=:leaveId) " +
							 "and e.empLeaveType.id=(select l1.empLeaveType.id from EmpApplyLeave l1  where l1.id=:leaveId)"; 
					Query  query = session.createQuery(hqlQuery);	
					query.setInteger("leaveId",approveLeave.getId());
					leaveList.add((EmpLeave)query.uniqueResult());
				}
			}
			
			session.flush();
			//session.close();
			//sessionFactory.close();
			log.debug("exit getAvailableLeaveDetails");
			return leaveList;
		} catch (Exception e) {
			throw  new BusinessException(e);
		}
	}
}
