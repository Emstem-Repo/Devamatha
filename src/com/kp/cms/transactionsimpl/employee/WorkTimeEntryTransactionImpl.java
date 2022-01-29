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

import com.kp.cms.bo.admin.EmpWorkTime;
import com.kp.cms.bo.admin.EmployeeTypeBO;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.employee.WorkTimeEntryForm;
import com.kp.cms.transactions.employee.IWorkTimeEntryTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class WorkTimeEntryTransactionImpl implements IWorkTimeEntryTransaction{
	private static final Log log = LogFactory.getLog(WorkTimeEntryTransactionImpl.class);
	public static volatile WorkTimeEntryTransactionImpl workTimeEntryTransactionImpl = null;
	
	public static WorkTimeEntryTransactionImpl getInstance() {
		if (workTimeEntryTransactionImpl == null) {
			workTimeEntryTransactionImpl = new WorkTimeEntryTransactionImpl();
			return workTimeEntryTransactionImpl;
		}
		return workTimeEntryTransactionImpl;
	}
		
	/**
	 * duplication checking for Work Time entry
	 */
	public void isWorkTimeEntryDuplcated(WorkTimeEntryForm workEntryForm) throws Exception {
		log.debug("inside isAttributeDuplcated");
		Session session = null;
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			StringBuffer sqlString = new StringBuffer("select i.employeeTypeBO from EmpWorkTime i where i.isActive=1 and i.employeeTypeBO.id = :name group by i.employeeTypeBO.id");
			Query query = session.createQuery(sqlString.toString());
			query.setInteger("name", Integer.parseInt(workEntryForm.getName()));
			
			EmployeeTypeBO workTime = (EmployeeTypeBO) query.uniqueResult();
			if (workTime != null ) {
				throw new DuplicateException();
			}else{
				StringBuffer sqlString1 = new StringBuffer("select i.employeeTypeBO from EmpWorkTime i where i.isActive=0 and i.employeeTypeBO.id = :name group by i.employeeTypeBO.id");
				Query query1 = session.createQuery(sqlString1.toString());
				query1.setInteger("name", Integer.parseInt(workEntryForm.getName()));
				workTime = (EmployeeTypeBO) query1.uniqueResult();
				if (workTime != null)
				{
					workEntryForm.setDuplId(workTime.getId());
					throw new ReActivateException();
				}	
			}
			session.flush();
			//session.close();
			//sessionFactory.close();
			log.debug("leaving isWorkTimeEntryDuplcated");
	}	

	/**
	 * This method add EmpWorkTime master.
	 * 
	 * @return true / false based on result.
	 * @throws BusinessException 
	 */
	public boolean addWorkTimeEntry(List<EmpWorkTime> workTime, String mode) throws Exception {
		log.debug("inside addWorkTimeEntry");
		Session session = null;
		Transaction transaction = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			Iterator<EmpWorkTime> itr=workTime.iterator();
			if("edit".equalsIgnoreCase(mode)){
				while (itr.hasNext()) {
					EmpWorkTime empWorkTime = (EmpWorkTime) itr.next();
					session.merge(empWorkTime);
				}
			}
			else
			{
				while (itr.hasNext()) {
					EmpWorkTime empWorkTime = (EmpWorkTime) itr.next();
					session.save(empWorkTime);
				}
			}
			transaction.commit();
			if (session != null) {
				session.flush();
				session.close();
			}
			log.debug("leaving addWorkTimeEntry");
			return true;
		} catch (ConstraintViolationException e) {
			transaction.rollback();
			log.error("Error during in addWorkTimeEntry..." , e);
			throw new BusinessException(e);
		} catch (Exception e) {
			transaction.rollback();
			log.error("Error in addWorkTimeEntry..." , e);
			throw new ApplicationException(e);
		}

	}

	/**
	 * This will retrieve all the Work Types.
	 * 
	 * @return all EmpWorkTime
	 * @throws Exception
	 */

	public List<EmpWorkTime> getWorkTime() throws Exception {
		log.debug("inside getWorkTime");
		Session session = null;
		
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from EmpWorkTime i where i.isActive = 1");
			List<EmpWorkTime> list = query.list();
			session.flush();
			//session.close();
			//sessionFactory.close();
			log.debug("leaving getEducationDetails");
			return list;
		 } catch (Exception e) {
			 log.error("Error in getEducationDetails...",e);
			 session.flush();
			 session.close();
			 throw  new ApplicationException(e);
		 }
	}
	
	public boolean deleteWorkTimeEntry(int employeeTypeId) throws Exception{
		log.debug("inside deleteWorkTimeEntry");
		Session session = null;
		Transaction transaction = null;
		boolean isDeleted = false;
		try{
			session = HibernateUtil.getSession();
			transaction=session.beginTransaction(); 
			Query query = session.createQuery("from EmpWorkTime w where w.employeeTypeBO.id =:employeeTypeId and w.isActive = 1");
			query.setInteger("employeeTypeId", employeeTypeId);
			List<EmpWorkTime> empWorkTimeList = query.list();
			Iterator<EmpWorkTime> itr = empWorkTimeList.iterator();
			while(itr.hasNext()){
				EmpWorkTime empWorkTime = itr.next();
				empWorkTime.setIsActive(false);
				session.update(empWorkTime);
			}
			transaction.commit();
			isDeleted = true;
			return isDeleted;
		}
		catch(Exception e)
		{
			log.error("error in deleteWorkTimeEntry in IMPL" +e);
			if(transaction != null)
			{
				transaction.rollback();
			}
			throw new ApplicationException(e);
		}
		finally
		{
			if(session != null)
			{
				session.flush();
			}
		}
		
	}
	
	public List<EmpWorkTime> getWorkTimeEntryToEdit(WorkTimeEntryForm workTimeEntryForm) throws Exception{
		log.debug("inside getWorkTimeEntryToEdit");
		Session session = null;
		try
		{
			session= HibernateUtil.getSession();
			Query query = session.createQuery("from EmpWorkTime w where w.employeeTypeBO.id ='"+workTimeEntryForm.getId()+"' and w.isActive = 1");
			List<EmpWorkTime> empWorkTimeListBoList= query.list();
			log.debug("Existing getWorkTimeEntryToEdit");
			return empWorkTimeListBoList;
		}
		catch(Exception e)
		{
			log.error(" errors occured in getWorkTimeEntryToEdit in IMPL"+e);
			throw new ApplicationException(e);
		}
		finally
		{
			if(session != null)
			{
				session.flush();
			}
		}
	}
	
	public boolean updateWorkTimeEntry(List<EmpWorkTime> worktimeListBo) throws Exception{
		log.debug("inside updateWorkTimeEntry");
		Session session = null;
		Transaction transaction = null;
		try
		{
			session =HibernateUtil.getSession();
			transaction = session.beginTransaction();
			Iterator<EmpWorkTime> iterator = worktimeListBo.iterator();
			while(iterator.hasNext())
			{
				EmpWorkTime empWorkTime = (EmpWorkTime)iterator.next();
				session.update(empWorkTime);
			}
			transaction.commit();
			log.debug("Existing updateWorkTimeEntry");
			return true;
		}
		catch (Exception e) {
			log.error(" errors occured in getWorkTimeEntryToEdit in IMPL"+e);
			if(transaction != null)
			{
				transaction.rollback();
			}
			throw new ApplicationException(e);
		}
		finally
		{
			if(session != null)
			{
				session.flush();
			}
		}
	}
	
	public List<EmployeeTypeBO> getEmployeeTypeList() throws Exception{
		
		Session session = null;
		try
		{
			session=HibernateUtil.getSession();
			List<EmployeeTypeBO> empTypeBoList = session.createQuery("select w.employeeTypeBO from EmpWorkTime w where w.isActive = 1 and w.employeeTypeBO.isActive=1 group by w.employeeTypeBO.id").list();
			return empTypeBoList;
		}
		catch (Exception e) {
			throw new ApplicationException(e);
		}
		finally
		{
			if(session != null)
			{
				session.flush();
			}	
		}
	}
	
	public boolean reActivateWorkTimeEntry(int employeeTypeId) throws Exception{
		log.debug("inside reActivateWorkTimeEntry");
		Session session = null;
		Transaction transaction = null;
		boolean isDeleted = false;
		try{
			session = HibernateUtil.getSession();
			transaction=session.beginTransaction(); 
			Query query = session.createQuery("from EmpWorkTime w where w.employeeTypeBO.id =:employeeTypeId ");
			query.setInteger("employeeTypeId", employeeTypeId);
			List<EmpWorkTime> empWorkTimeList = query.list();
			Iterator<EmpWorkTime> itr = empWorkTimeList.iterator();
			while(itr.hasNext()){
				EmpWorkTime empWorkTime = itr.next();
				empWorkTime.setIsActive(true);
				session.update(empWorkTime);
			}
			transaction.commit();
			isDeleted = true;
			return isDeleted;
		}
		catch(Exception e)
		{
			log.error("error in reActivateWorkTimeEntry in IMPL" +e);
			if(transaction != null)
			{
				transaction.rollback();
			}
			throw new ApplicationException(e);
		}
		finally
		{
			if(session != null)
			{
				session.flush();
			}
		}
		
	}
	
}
