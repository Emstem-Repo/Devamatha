package com.kp.cms.transactionsimpl.employee;

import java.sql.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.EmpApplyLeave;
import com.kp.cms.bo.admin.EmpLeave;
import com.kp.cms.bo.admin.EmpLeaveType;
import com.kp.cms.bo.admin.Employee;
import com.kp.cms.bo.admin.EmployeeTypeBO;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.employee.ApplyLeaveForm;
import com.kp.cms.transactions.employee.IApplyLeaveTransaction;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.PropertyUtil;

public class ApplyLeaveTransactionImpl extends PropertyUtil implements
		IApplyLeaveTransaction {
	private static final Log log = LogFactory
			.getLog(ApplyLeaveTransactionImpl.class);

	// getting the leave type list which are active
	public List<EmpLeaveType> getLeaveTypeList() throws Exception {
		Session session = null;
		try {
			// SessionFactory sessionFactory =
			// HibernateUtil.getSessionFactory();
			// session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			List<EmpLeaveType> leaveList = session.createQuery(
					"from EmpLeaveType l where l.isActive=1").list();
			session.flush();
			// session.close();
			return leaveList;
		} catch (Exception e) {
			if (session != null) {
				session.flush();
				session.close();
			}
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.kp.cms.transactions.employee.IApplyLeaveTransaction#applyLeave(com
	 * .kp.cms.bo.admin.EmpApplyLeave)
	 */
	public boolean applyLeave(EmpApplyLeave empAppLeave) throws Exception {
		return save(empAppLeave);
	}

	public int getemployeeId(String userId) throws Exception {
		log.debug("inside getemployeeId");
		Session session = null;
		int employeeId = 0;
		try {
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("select u.employee.id from Users u where u.id="+ userId+" and u.isActive=1");
			Object obj = query.uniqueResult();

			if (obj != null)
				employeeId = (Integer) obj;
			session.flush();
			log.debug("leaving getemployeeId");

		} catch (Exception e) {
			log.error("Error in getemployeeId checking...", e);
			session.flush();
			throw new ApplicationException(e);
		}
		return employeeId;

	}

	@Override
	public boolean checkDuplicateLeaves(String query) throws Exception {
		Session session = null;
		boolean isDuplcate=false;
		try {
			session = HibernateUtil.getSession();
			List<EmpLeaveType> leaveList = session.createQuery(query).list();
			session.flush();
			if(leaveList!=null && !leaveList.isEmpty()){
				isDuplcate=true;
			}
		} catch (Exception e) {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		return isDuplcate;
	}
	
	public List<EmpApplyLeave> getApplyLeaveDetails(String employeeId)throws Exception
	{
		Session session = null;
		try
		{
			session = HibernateUtil.getSessionFactory().openSession();
			List<EmpApplyLeave> applyLeaveBoList = session.createQuery("from EmpApplyLeave e where e.isActive = 1 and e.empLeaveType.isActive = 1 and e.employee.id='"+employeeId+"'").list();
			session.flush();
			return applyLeaveBoList;
		}
		catch(Exception e)
		{
			log.error("Exception occured in getApplyLeaveDetails in IMPL :"	+ e);
			throw new ApplicationException(e);
			
		}
		finally
		{
			if (session != null) {
				session.flush();
			}
		}
	}
	
	public List<EmpLeave> getAllotedLeaveTypeList(String employeeId) throws Exception {
		Session session = null;
		
		try {
			// SessionFactory sessionFactory =
			// HibernateUtil.getSessionFactory();
			// session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			List<EmpLeave> leaveList = session.createQuery("from EmpLeave l where l.isActive=1 and l.employee.id='"+employeeId+"' and (l.leavesAllocated is not null or l.leavesAllocated!=0)").list();
			session.flush();
			// session.close();
			return leaveList;
		} catch (Exception e) {
			if (session != null) {
				session.flush();
				session.close();
			}
			return null;
		}
	}
	
	public boolean isHoliday(Date time, String employeeId)throws Exception
	{
		Session session=null;
		try
		{
			session=HibernateUtil.getSession();
			String query="from EmpAcademicHolidaysDates e where e.date='"+time+"' and " +
						"e.empAcademicHolidays.employeeTypeBO.id=(select j.objEmployeeTypeBO.id  " +
						"from EmpJob j where j.employee.id="+employeeId+") and e.empAcademicHolidays.isActive=1";
			Query holidayList=session.createQuery(query);
			if(holidayList.list().size()>0)
				return true;
		}
		catch (Exception e) 
		{
			// TODO: handle exception
			if(session!=null)
				session.clear();
			throw new ApplicationException(e.getMessage());
			
		}
		return false;
	}
	
	public Integer getRemainingDays(ApplyLeaveForm applyLeaveForm)throws Exception
	{
		Session session=null;
		try
		{
			session=HibernateUtil.getSession();
			String query="select e.leavesRemaining from EmpLeave e where e.employee.id="+applyLeaveForm.getEmployeeId()+" and e.empLeaveType.id="+applyLeaveForm.getLeaveId();
			Integer remainingDays=(Integer)session.createQuery(query).uniqueResult();
			return remainingDays;
		}
		catch (Exception e) {
			if(session!=null)
				session.clear();
			throw new ApplicationException(e.getMessage());
		}
	}
	
	public boolean isReportingMangerAssigned(String employeeId)throws Exception
	{
		Session session=null;
		try
		{
			session=HibernateUtil.getSession();
			String query="select e.employeeByReportToId from Employee e where e.id="+employeeId;
			Employee reportingTo=(Employee)session.createQuery(query).uniqueResult();
			if(reportingTo!=null)
				return true;
			else
				return false;
		}
		catch (Exception e) {
			if(session!=null)
				session.clear();
			throw new ApplicationException(e.getMessage());
		}
	}
	
	public boolean isEmployeeTypeSet(String employeeId)throws Exception
	{
		Session session=null;
		try
		{
			session=HibernateUtil.getSession();
			String query="select j.objEmployeeTypeBO from EmpJob j where j.employee.id="+employeeId;
			EmployeeTypeBO type=(EmployeeTypeBO)session.createQuery(query).uniqueResult();
			if(type!=null)
				return true;
			else
				return false;
		}
		catch (Exception e) {
			if(session!=null)
				session.clear();
			throw new ApplicationException(e.getMessage());
		}
	}
	
	public boolean cancelLeave(Integer leaveId)throws Exception
	{
		Session session=null;
		Transaction transaction=null;
		try
		{
			session=HibernateUtil.getSession();
			transaction=session.beginTransaction();
			EmpApplyLeave leave=(EmpApplyLeave)session.get(EmpApplyLeave.class, leaveId);
			leave.setStatus("Canceled");
			session.update(leave);
			transaction.commit();
			return true;
		}
		catch (Exception e) {
			if(session!=null)
				session.clear();
			transaction.rollback();
			throw new ApplicationException(e.getMessage());
		}
	}
	
	public EmpApplyLeave getLeaveDetails(Integer leaveId)throws Exception
	{
		Session session=null;
		try
		{
			session=HibernateUtil.getSession();
			EmpApplyLeave leave=(EmpApplyLeave)session.get(EmpApplyLeave.class,leaveId);
			return leave;
		}
		catch (Exception e) {
			if(session!=null)
				session.clear();
			throw new ApplicationException(e.getMessage());
		}
	}
	
	public boolean startCancellation(ApplyLeaveForm applyLeaveForm)throws Exception
	{
		Session session=null;
		Transaction transaction=null;
		try
		{
			session=HibernateUtil.getSession();
			transaction=session.beginTransaction();
			EmpApplyLeave leave=(EmpApplyLeave)session.get(EmpApplyLeave.class, applyLeaveForm.getId());
			leave.setStatus("Cancellation Pending");
			leave.setCancelReason(applyLeaveForm.getCancelReason());
			session.update(leave);
			transaction.commit();
			return true;
		}
		catch (Exception e) {
			if(session!=null)
				session.clear();
			transaction.rollback();
			throw new ApplicationException(e.getMessage());
		}
	}
	
	public Integer getPendingDays(ApplyLeaveForm applyLeaveForm)throws Exception
	{
		Session session=null;
		List<Integer>noOfDaysList=null;
		Integer pendingDays=0;
		try
		{
			session=HibernateUtil.getSession();
			String query="select e.noOfDays from EmpApplyLeave e where e.employee.id="+applyLeaveForm.getEmployeeId()+" and e.empLeaveType.id="+applyLeaveForm.getLeaveId()+" and (e.status='Applied' or e.status='On Hold')";
			noOfDaysList=session.createQuery(query).list();
			if(noOfDaysList!=null && noOfDaysList.size()>0)
			{
				for(Integer noOfDays:noOfDaysList)
					pendingDays+=noOfDays;
			}
			return pendingDays;
		}
		catch (Exception e) {
			if(session!=null)
				session.clear();
			throw new ApplicationException(e.getMessage());
		}
	}
}
