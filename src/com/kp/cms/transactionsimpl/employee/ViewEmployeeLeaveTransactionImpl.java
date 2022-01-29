package com.kp.cms.transactionsimpl.employee;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;

import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.to.employee.EmployeeLeaveTO;
import com.kp.cms.transactions.employee.IViewEmployeeLeaveTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class ViewEmployeeLeaveTransactionImpl implements
		IViewEmployeeLeaveTransaction {
	private static final Log log = LogFactory
			.getLog(ViewEmployeeLeaveTransactionImpl.class);
	public static volatile ViewEmployeeLeaveTransactionImpl viewEmployeeLeaveTransactionImpl = null;

	public static ViewEmployeeLeaveTransactionImpl getInstance() {
		if (viewEmployeeLeaveTransactionImpl == null) {
			viewEmployeeLeaveTransactionImpl = new ViewEmployeeLeaveTransactionImpl();
			return viewEmployeeLeaveTransactionImpl;
		}
		return viewEmployeeLeaveTransactionImpl;
	}

	public List<Object[]> getEmployees() throws Exception {
		Session session = null;
		List<Object[]> employeeList = null;

		try {
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("select e.id, e.firstName, e.middleName, e.lastName from Employee e where e.isActive=1");
			employeeList = query.list();
		} catch (Exception e) {
			log.error("Error while getting Employees details..." + e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();

			}
		}
		return employeeList;

	}

	public List<Object[]> getEmployeeDetails(String academicYear,
			String employeeId) throws Exception {
		Session session = null;
		List<Object[]> employeeList = null;

		try {
			session = HibernateUtil.getSession();
			String strQuery = " select el.employee.firstName,  el.employee.lastName,el.employee.middleName, "
					+ "el.employee.department.name, el.empLeaveType.name, el.fromDate, el.toDate, el.reason,el.status "
					+ "from EmpApplyLeave el where el.employee.isActive=1";
			if (employeeId != null && !employeeId.trim().isEmpty()) {
				strQuery = strQuery + " and el.employee.id= "+ Integer.parseInt(employeeId);
			}
			 if (academicYear != null && Integer.parseInt(academicYear) != 0)
			 {
			 strQuery = strQuery + " and el.fromDate like '%"+academicYear+"%' ";
			 }
			Query query = session.createQuery(strQuery);

			employeeList = query.list();
		} catch (Exception e) {
			log.error("Error while getting Employees details..." + e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();

			}
		}
		return employeeList;

	}

}
