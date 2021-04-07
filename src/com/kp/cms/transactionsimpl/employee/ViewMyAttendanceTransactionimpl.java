package com.kp.cms.transactionsimpl.employee;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;

import com.kp.cms.bo.admin.EmpApplyLeave;
import com.kp.cms.bo.admin.EmpAttendance;
import com.kp.cms.bo.admin.Employee;
import com.kp.cms.bo.employee.EmpEventVacation;
import com.kp.cms.bo.employee.Holidays;
import com.kp.cms.forms.employee.ViewMyAttendanceForm;
import com.kp.cms.transactions.employee.IViewMyAttendancetransaction;
import com.kp.cms.utilities.HibernateUtil;

public class ViewMyAttendanceTransactionimpl implements
		IViewMyAttendancetransaction {
	/**
	 * Singleton object of ViewMyAttendanceTransactionimpl
	 */
	private static volatile ViewMyAttendanceTransactionimpl viewMyAttendanceTransactionimpl = null;
	private static final Log log = LogFactory.getLog(ViewMyAttendanceTransactionimpl.class);
	private ViewMyAttendanceTransactionimpl() {
		
	}
	/**
	 * return singleton object of ViewMyAttendanceTransactionimpl.
	 * @return
	 */
	public static ViewMyAttendanceTransactionimpl getInstance() {
		if (viewMyAttendanceTransactionimpl == null) {
			viewMyAttendanceTransactionimpl = new ViewMyAttendanceTransactionimpl();
		}
		return viewMyAttendanceTransactionimpl;
	}
	/* (non-Javadoc)
	 * returns EmpAttendanceBO for input criteria
	 * @see com.kp.cms.transactions.employee.IViewMyAttendancetransaction#getEmpAttendanceBO(java.lang.String)
	 */
	@Override
	public List<EmpAttendance> getEmpAttendanceBO(String query) throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			Query que = session.createQuery(query);
			List<EmpAttendance> empAttBo = que.list();
			session.flush();
			log.debug("leaving getEmpAttendanceBO");
			return empAttBo;
		 } catch (Exception e) {
			 log.error("Error during getEmpAttendanceBO...",e);
			 session.flush();
			 //session.close();
			 return null;
		 }
	
	}
	/* (non-Javadoc)
	 * querying the database to get the empLeave BO for leave details
	 * @see com.kp.cms.transactions.employee.IViewMyAttendancetransaction#getEmpLeaveList(java.lang.String)
	 */
	@Override
	public List<EmpApplyLeave> getEmpLeaveList(String query) throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			Query que = session.createQuery(query);
			List<EmpApplyLeave> empLeaveBo = que.list();
			session.flush();
			log.debug("leaving getEmpAttendanceBO");
			return empLeaveBo;
		 } catch (Exception e) {
			 log.error("Error during getEmpAttendanceBO...",e);
			 session.flush();
			 //session.close();
			 return null;
		 }
	
	}
	/* (non-Javadoc)
	 * returns employee name
	 * @see com.kp.cms.transactions.employee.IViewMyAttendancetransaction#getEmployeeName(com.kp.cms.forms.employee.ViewMyAttendanceForm)
	 */
	@Override
	public String getEmployeeName(ViewMyAttendanceForm viewAttForm) throws Exception {
		Session session = null;
		String query=null;
		String empName=null;
			try {
				session = HibernateUtil.getSession();
				if(viewAttForm.getFingerPrintId()!=null && !viewAttForm.getFingerPrintId().isEmpty())
				 query="select e.firstName from Employee e where e.isActive=1 and e.active=1 and e.fingerPrintId="+viewAttForm.getFingerPrintId();
				else if (viewAttForm.getEmpCode()!=null && !viewAttForm.getEmpCode().isEmpty())
					 query="select e.firstName from Employee e where e.isActive=1 and e.active=1 and e.fingerPrintId="+viewAttForm.getEmpCode();
				Query que = session.createQuery(query);
				empName = (String)que.uniqueResult();
				session.flush();
				log.debug("leaving getEmpAttendanceBO");
				return empName;
			 } catch (Exception e) {
				 log.error("Error during getEmpAttendanceBO...",e);
				 session.flush();
				 //session.close();
				 return null;
			 }
		}
	/* (non-Javadoc)
	 * getting the holiday list
	 * @see com.kp.cms.transactions.employee.IViewMyAttendancetransaction#getHolidays()
	 */
	@Override
	public List<Holidays> getHolidays() throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			String query="from Holidays h where h.isActive=1";
			Query que = session.createQuery(query);
			List<Holidays> empHolidaysBo = que.list();
			session.flush();
			log.debug("leaving getHolidays");
			return empHolidaysBo;
		 } catch (Exception e) {
			 log.error("Error during getHolidays...",e);
			 session.flush();
			 //session.close();
			 return null;
		 }
	
	}
	/* (non-Javadoc)
	 * returns the list of events for employees department and search criteria
	 * @see com.kp.cms.transactions.employee.IViewMyAttendancetransaction#getEmpEventVacations(java.lang.String)
	 */
	@Override
	public List<EmpEventVacation> getEmpEventVacations(
			String queryForEventVacation) throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			Query que = session.createQuery(queryForEventVacation);
			List<EmpEventVacation> empEventList = que.list();
			session.flush();
			log.debug("leaving getEmpEventVacations");
			return empEventList;
		 } catch (Exception e) {
			 log.error("Error during getEmpEventVacations...",e);
			 session.flush();
			 //session.close();
			 return null;
		 }
	
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.employee.IViewMyAttendancetransaction#getEmployeeDetails(int)
	 */
	@Override
	public Employee getEmployeeDetails(int userId, ViewMyAttendanceForm viewAttForm,String employee) throws Exception {
		Session session = null;
		Employee emp=null;
			try {
				session = HibernateUtil.getSession();
				if(employee.equalsIgnoreCase("User")){
					Query que = session.createQuery("select u.employee from Users u where u.isActive=1 and u.employee.isActive=1 and u.id="+userId);
					emp = (Employee)que.uniqueResult();
				}else{
					String query = "";
					if(viewAttForm.getFingerPrintId()!=null && !viewAttForm.getFingerPrintId().isEmpty())
						 query="select e from Employee e where e.isActive=1 and e.active=1 and e.fingerPrintId="+viewAttForm.getFingerPrintId();
					else if (viewAttForm.getEmpCode()!=null && !viewAttForm.getEmpCode().isEmpty())
						query="select e from Employee e where e.isActive=1 and e.active=1 and e.fingerPrintId="+viewAttForm.getEmpCode();
					Query que = session.createQuery(query);
					emp = (Employee)que.uniqueResult();
				}
				session.flush();
				log.debug("leaving getEmployeeDetails");
				return emp;
			 } catch (Exception e) {
				 log.error("Error during getEmployeeDetails...",e);
				 session.flush();
				 //session.close();
				 return emp;
			 }
		}
}
