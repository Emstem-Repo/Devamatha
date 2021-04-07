package com.kp.cms.transactionsimpl.employee;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.upload.FormFile;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.EmpAttendance;
import com.kp.cms.bo.admin.EmpReadAttendanceFileBO;
import com.kp.cms.bo.admin.PersonalData;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.to.admission.PersonalDataTO;
import com.kp.cms.transactions.employee.IReadAttendanceFileTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class ReadAttendanceFileTransactionImpl implements
		IReadAttendanceFileTransaction {

	private static final Log log = LogFactory
			.getLog(ReadAttendanceFileTransactionImpl.class);
	public static volatile ReadAttendanceFileTransactionImpl objImpl = null;

	public static ReadAttendanceFileTransactionImpl getInstance() {
		if (objImpl == null) {
			objImpl = new ReadAttendanceFileTransactionImpl();
			return objImpl;
		}
		return objImpl;
	}

	public boolean addAttendance(List<EmpAttendance> listOfBo)
			throws Exception {
		boolean flag = false;
		log.debug("inside addAttendance");
		Session session = null;
		Transaction transaction = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			transaction.begin();
			int count=0;
			for (Object obj : listOfBo) {
				session.save(obj);
				if(count++%20==0)
				{
					session.flush();
					session.clear();
				}
			}
			transaction.commit();
			session.flush();
			session.close();

			
			log.debug("leaving addAttendance");
			flag = true;
		} catch (Exception e) {
			if (transaction != null)
				transaction.rollback();
			log.error("Error in addAttendance impl...", e);
			throw new ApplicationException(e);
		}
		return flag;
	}
	
	public Integer getEmployeeId(String employeeCode)throws Exception
	{
		Integer employeeId=null;
		Session session = null;
		Transaction transaction = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			Query query=session.createQuery("select e.id from Employee e where e.code='"+employeeCode+"'");
			employeeId=(Integer)query.uniqueResult();
			session.flush();
			session.close();
			log.debug("leaving addAttendance");
		} catch (Exception e) {
			if (transaction != null)
				transaction.rollback();
			log.error("Error in addAttendance impl...", e);
			throw new ApplicationException(e);
		}
		return employeeId;
	}
	
	public EmpAttendance getEmployeeAttendanceForDate(int employeeId,String attendanceDate)throws Exception 
	{
		EmpAttendance empAttendance=null;
		Session session = null;
		Transaction transaction = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			String strQuery="select e from EmpAttendance e where e.date='"+attendanceDate+"' and e.employee.id="+employeeId;
			Query query=session.createQuery(strQuery);
			empAttendance=(EmpAttendance)query.uniqueResult();
			session.flush();
			session.close();
			log.debug("leaving addAttendance");
		} catch (Exception e) {
			if (transaction != null)
				transaction.rollback();
			log.error("Error in addAttendance impl...", e);
			throw new ApplicationException(e);
		}
		return empAttendance;
	}

}
