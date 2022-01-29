package com.kp.cms.transactionsimpl.employee;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.AcademicYear;
import com.kp.cms.bo.admin.EmpAcademicHolidays;
import com.kp.cms.bo.admin.EmpAcademicHolidaysDates;
import com.kp.cms.bo.admin.EmployeeTypeBO;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.employee.HolidaysForm;
import com.kp.cms.transactions.employee.IHolidaysTransaction;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;
import com.kp.cms.utilities.PropertyUtil;

public class HolidaysTransactionImpl extends PropertyUtil implements IHolidaysTransaction{
	
	private static final Log log = LogFactory.getLog(HolidaysTransactionImpl.class);
	
	public List<EmployeeTypeBO> getEmployeeTypeList() throws Exception {
		Session session = null;
		
		try {
			// SessionFactory sessionFactory =
			// HibernateUtil.getSessionFactory();
			// session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			List<EmployeeTypeBO> empTypeList = session.createQuery("from EmployeeTypeBO empType where empType.isActive=1").list();
			session.flush();
			// session.close();
			return empTypeList;
		} catch (Exception e) {
			if (session != null) {
				session.flush();
				session.close();
			}
			return null;
		}
	}
	
	public boolean addHolidays(EmpAcademicHolidays empHolidays,List<EmpAcademicHolidaysDates> datesTobeDeleted) throws Exception{
		Session session = null;
		Transaction transaction = null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			if(datesTobeDeleted!=null)
			{
				int count=0;
				for(EmpAcademicHolidaysDates dates:datesTobeDeleted)
				{
					session.delete(dates);
					if(++count%20==0)
					{	
						session.clear();
						session.flush();
					}	
				}
			}
			session.saveOrUpdate(empHolidays);
			transaction.commit();
			session.flush();
			//session.close();
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
	
	public List<EmpAcademicHolidays> getEmployeeHolidaysList() throws Exception {
		Session session = null;
		
		try {
			// SessionFactory sessionFactory =
			// HibernateUtil.getSessionFactory();
			// session = sessionFactory.openSession();
			session = InitSessionFactory.getInstance().openSession();
			List<EmpAcademicHolidays> empHolidaysList = session.createQuery("from EmpAcademicHolidays where isActive=1").list();
			session.flush();
			// session.close();
			return empHolidaysList;
		} catch (Exception e) {
			if (session != null) {
				session.flush();
				session.close();
			}
			return null;
		}
	}
	
	public EmpAcademicHolidays getHolidaysDetailsToEdit(int id) throws Exception{
		Session session = null;
		
		try {
			session = HibernateUtil.getSession();
			EmpAcademicHolidays empHolidays = (EmpAcademicHolidays) session.createQuery("from EmpAcademicHolidays where isActive=1 and id = '"+id+"'").uniqueResult();
			session.flush();
			// session.close();
			return empHolidays;
		} catch (Exception e) {
			if (session != null) {
				session.flush();
				session.close();
			}
			return null;
		}
	}
	
	public EmpAcademicHolidays duplicateCheck(String query) throws Exception{
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			EmpAcademicHolidays empHolidays =(EmpAcademicHolidays) session.createQuery(query).uniqueResult();
			session.flush();
			// session.close();
			return empHolidays;
		} 
		catch (Exception e) {
			if (session != null) {
				session.flush();
				session.close();
			}
			throw e;
		}
	}
	public AcademicYear getYearId(HolidaysForm holidaysForm) throws Exception{
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			AcademicYear year = (AcademicYear) session.createQuery("from AcademicYear a where a.year = '"+holidaysForm.getAcademicYear()+"'").uniqueResult();
			session.flush();
			// session.close();
			return year;
		} catch (Exception e) {
			if (session != null) {
				session.flush();
				session.close();
			}
			return null;
		}
	}
	
	public boolean deleteHolidays(int id, boolean activate, String userId) throws Exception {
		Session session = null;
		Transaction transaction = null;
		boolean result = false;
		try {
			//session = HibernateUtil.getSessionFactory().openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			EmpAcademicHolidays empAcademicHolidays = (EmpAcademicHolidays) session.get(EmpAcademicHolidays.class, id);
			if (activate) {
				empAcademicHolidays.setIsActive(true);
				empAcademicHolidays.setLastModifiedDate(new Date());
				empAcademicHolidays.setModifiedBy(userId);
			} else {
				empAcademicHolidays.setIsActive(false);
				empAcademicHolidays.setLastModifiedDate(new Date());
				empAcademicHolidays.setModifiedBy(userId);
			}
			session.update(empAcademicHolidays);
			transaction.commit();
			result = true;
		} catch (ConstraintViolationException e) {
			log.error("Error while deleting Interview Definition entry..." + e);
			if (transaction != null){
				transaction.rollback();
			}
			throw new BusinessException(e);
		} catch (Exception e) {
			log.error("Error while deleting Interview Definition entry..." + e);
			if (transaction != null){
				transaction.rollback();
			}
			throw new ApplicationException(e);
		} finally {
			if ( session!= null){
				session.flush();
				session.close();
			}
		}
		return result;
	}
}
