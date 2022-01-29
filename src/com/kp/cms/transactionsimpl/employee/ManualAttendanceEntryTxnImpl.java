package com.kp.cms.transactionsimpl.employee;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.EmpAttendance;
import com.kp.cms.bo.admin.EmpAttendanceBc;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.employee.ManualAttendanceEntryForm;
import com.kp.cms.transactions.employee.IManualAttendanceEntryTransaction;
import com.kp.cms.transactionsimpl.inventory.InventoryRequestTxnImpl;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.HibernateUtil;

public class ManualAttendanceEntryTxnImpl implements
		IManualAttendanceEntryTransaction {

	private static final Log log = LogFactory
			.getLog(InventoryRequestTxnImpl.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.kp.cms.transactions.employee.IManualAttendanceEntryTransaction#
	 * getEmployeesToMarkAttendanceList(int, int)
	 */
	@Override
	public List<EmpAttendance> getEmployeesAttendanceList(String strQuery) throws Exception {
		Session session = null;
		List<EmpAttendance> employeesAttendanceEntryList = null;

		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			// session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			Query query = session.createQuery(strQuery);
			employeesAttendanceEntryList = query.list();
		} catch (Exception e) {
			log.error("Error while getting applicant details..." + e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				// session.close();
			}
		}
		return employeesAttendanceEntryList;
	}

	public List<Object[]> getEmployeesToMarkAttendanceList(int departmentId,
			int designationId, String date) throws Exception {
		Session session = null;
		List<Object[]> employeesAttendanceEntryList = null;

		try {
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("select e.firstName, e.middleName,e.lastName, e.id, ea.amPresent, ea.pmPresent, ea.id "
							+ "from Employee e left join e.empAttendances ea where "
							+ "e.department.id= "+departmentId+" and e.designation.id = "+designationId+" "
							+ "and ea.date= '"+date+"' ");
			employeesAttendanceEntryList = query.list();
		} catch (Exception e) {
			log.error("Error while getting applicant details..." + e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		return employeesAttendanceEntryList;
	}

	@Override
	public boolean markEmployeesAttendance(EmpAttendance manualAttendance,EmpAttendanceBc attendanceBc) throws Exception {
		Session session = null;
		Transaction transaction = null;
		try {
			// SessionFactory sessionFactory =
			// HibernateUtil.getSessionFactory();
			// session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			session.saveOrUpdate(manualAttendance);
			session.saveOrUpdate(attendanceBc);
			transaction.commit();
		} catch (ConstraintViolationException e) {
			if (transaction != null) {
				transaction.rollback();
			}
			log.error("Error while saving interview result data.." + e);
			throw new BusinessException(e);
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			log.error("Error while saving interview result data ..." + e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		return true;
	}

	@Override
	public EmpAttendance isAttendanceTaken(String attendanceDate,Integer userId) throws Exception {
		Session session = null;
		EmpAttendance employeesAttendanceEntryList = null;

		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			// session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from EmpAttendance empAttendance"
							+ " where empAttendance.date = :date" 
							+ " and empAttendance.employee.id= :employee");
			query.setDate("date", CommonUtil.ConvertStringToSQLDate(attendanceDate));
			query.setInteger("employee",userId);
			employeesAttendanceEntryList = (EmpAttendance) query.uniqueResult();

			if (employeesAttendanceEntryList != null) {
				return employeesAttendanceEntryList;
			} else {
				return null;
			}
		} catch (Exception e) {
			log.error("Error while getting applicant details..." + e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				// session.close();
			}
		}
	}
	
	public EmpAttendance getEmployeeAttendance(Integer id)throws Exception
	{
		Session session = null;
		EmpAttendance empAttendance=null;
		try 
		{
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			// session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			empAttendance=(EmpAttendance)session.get(EmpAttendance.class,id);
			
		}
		catch (Exception e) 
		{
			log.error("Error while getting applicant details..." + e);
			throw new ApplicationException(e);
		}
		finally 
		{
			if (session != null)
			{
				session.flush();
			// session.close();
			}
		}
		return empAttendance;
	}
	public boolean deleteAttendance(Integer id,String userId,EmpAttendanceBc attendanceBc)throws Exception
	{
		Session session = null;
		Transaction transaction = null;
		try {
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			EmpAttendance empAttendance=(EmpAttendance)session.get(EmpAttendance.class,id);
			empAttendance.setIsActive(false);
			empAttendance.setModifiedBy(userId);
			empAttendance.setLastModifiedDate(new Date());
			session.update(empAttendance);
			session.save(attendanceBc);
			transaction.commit();
			return true;
		} catch (Exception e) {
			log.error("Error while getting applicant details..." + e);
			if ( transaction != null){
				transaction.rollback();
			}
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				// session.close();
			}
		}
	}
	
	public boolean reActivateAttendance(Integer id, String userId,EmpAttendanceBc attendanceBc) throws Exception{
		
		Session session = null;
		Transaction transaction = null;
		try {
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			EmpAttendance empAttendance=(EmpAttendance)session.get(EmpAttendance.class,id);
			empAttendance.setIsActive(true);
			empAttendance.setModifiedBy(userId);
			empAttendance.setLastModifiedDate(new Date());
			session.update(empAttendance);
			session.save(attendanceBc);
			transaction.commit();
			return true;
		} catch (Exception e) {
			log.error("Error while getting applicant details..." + e);
			if ( transaction != null){
				transaction.rollback();
			}
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				// session.close();
			}
		}
	}

}