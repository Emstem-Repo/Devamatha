package com.kp.cms.transactionsimpl.employee;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.EmpExceptionDetailsBO;
import com.kp.cms.bo.admin.EmpExceptionDetailsDates;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.transactions.employee.IExceptionDetailsTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class ExceptionDetailsTransactionImpl implements
		IExceptionDetailsTransaction {

	@SuppressWarnings("unused")
	private static final Log log = LogFactory
			.getLog(ExceptionDetailsTransactionImpl.class);
	public static volatile ExceptionDetailsTransactionImpl objImpl = null;

	public static ExceptionDetailsTransactionImpl getInstance() {
		if (objImpl == null) {
			objImpl = new ExceptionDetailsTransactionImpl();
			return objImpl;
		}
		return objImpl;
	}

	
	/* 
	 * used to add the bo into datasebase
	 * (non-Javadoc)
	 * @see com.kp.cms.transactions.employee.IExceptionDetailsTransaction#addExceptionDetails(com.kp.cms.bo.admin.EmpExceptionDetailsBO, java.lang.String)
	 */
	public boolean addExceptionDetails(EmpExceptionDetailsBO bo)
			throws Exception {
		Session session = null;
		boolean flag = false;
		Transaction transaction = null;

		try {
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
				session.save(bo);
			transaction.commit();
			session.flush();
			session.close();
			flag = true;
		} catch (Exception e) {
			if (session.isOpen()) {
				transaction.rollback();
			}
			throw new BusinessException(e);
		}

		return flag;
		
	}
	
	/* 
	 * used to get the ExceptionsDetails from database
	 * (non-Javadoc)
	 * @see com.kp.cms.transactions.employee.IExceptionDetailsTransaction#getExceptionDetails()
	 */
	@SuppressWarnings("unchecked")
	public List<EmpExceptionDetailsBO> getExceptionDetails() throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			List<EmpExceptionDetailsBO> exceptionDetailsBoList = session
					.createQuery(
							"from EmpExceptionDetailsBO e where e.isActive=1 and e.exceptionTypeBO.isActive=1 and e.employee.isActive=1")
					.list();
			session.flush();
			return exceptionDetailsBoList;
		} catch (Exception e) {
			
			if (session != null) {
				session.flush();
			}
		}
		return null;

	}
	
	/* 
	 * used to delete the ExceptionDetails 
	 * (non-Javadoc)
	 * @see com.kp.cms.transactions.employee.IExceptionDetailsTransaction#deleteExceptionDetails(int, java.lang.String)
	 */
	public boolean deleteExceptionDetails(int id, String userId)
			throws Exception {
		log
				.info("inside the deleteExceptionsDetails of ExceptionDetailsTransactionImpl");
		Session session = null;
		Transaction transaction = null;
		try {
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			EmpExceptionDetailsBO exceptionDetailsBo = (EmpExceptionDetailsBO) session
					.get(EmpExceptionDetailsBO.class, id);
			exceptionDetailsBo.setIsActive(false);
			exceptionDetailsBo.setModifiedBy(userId);
			exceptionDetailsBo.setLastModifiedDate(new Date());
			session.update(exceptionDetailsBo);
			transaction.commit();
			return true;
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			log
					.error("error occured deleteExceptionsDetails of ExceptionDetailsTransactionImpl"
							+ e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
	}
	
	public boolean updateExceptionDetails(EmpExceptionDetailsBO exceptionDetailsBo, List<EmpExceptionDetailsDates> datesTobeDeleted) throws Exception
	{
		Session session = null;
		Transaction transaction = null;
		try
		{
			session = HibernateUtil.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			if(datesTobeDeleted!=null)
			{
				int count=0;
				for(EmpExceptionDetailsDates dates:datesTobeDeleted)
				{
					session.delete(dates);
					if(++count%20==0)
					{	
						session.clear();
						session.flush();
					}	
				}
			}
			session.update(exceptionDetailsBo);
			transaction.commit();
			return true;
		}catch(Exception e)
		{
			if(transaction != null)
			{
				transaction.rollback();
			}
			log.error("error occured update exception details in IMPL"  +e);
			throw new ApplicationException(e);
		}
		finally
		{
			if(session != null)
			{ 
				session.flush();
				session.close();
			}
			
		}
	}
	
	/**
	 * 
	 * used to reactivate Exception Details
	 * @param id
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public boolean reActivateExceptionDetails(int id, String userId)
			throws Exception {
		log
				.info("inside reActivateExceptionDetails of ExceptionDetailsTransactionImpl");
		Session session = null;
		Transaction transaction = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			EmpExceptionDetailsBO exceptionDetailsBo = (EmpExceptionDetailsBO) session
					.get(EmpExceptionDetailsBO.class, id);
			exceptionDetailsBo.setIsActive(true);
			exceptionDetailsBo.setModifiedBy(userId);
			exceptionDetailsBo.setLastModifiedDate(new Date());
			session.update(exceptionDetailsBo);
			transaction.commit();
			return true;
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			log
					.error("error occured in reActivateExceptionDetails of ExceptionDetailsTransactionImpl"
							+ e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
	}
	
	public EmpExceptionDetailsBO getExceptionDetailsOnId(int id)
			throws Exception {
		// TODO Auto-generated method stub
		log
				.info("Inside of getExceptionDetailsOnId of ExceptionDetailsTransactionImpl");
		Session session = null;
		EmpExceptionDetailsBO exceptionDetailsBO = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			Query query = session
					.createQuery("from EmpExceptionDetailsBO e where e.isActive=1 and e.id=:id");
			query.setInteger("id", id);
			exceptionDetailsBO = (EmpExceptionDetailsBO) query.uniqueResult();
			
		} catch (Exception e) {
			log
					.error("Exception occured in getExceptionDetailsOnId in IMPL :"
							+ e);
			throw new ApplicationException(e);
		
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		log.info("End of getExceptionDetailsOnId of ExceptionDetailsTransactionImpl");
		return exceptionDetailsBO;
		}
	
	public EmpExceptionDetailsBO duplicateCheckException(String query)throws Exception
	{
		Session session = null;
		try
		{
			session = HibernateUtil.getSession();
			EmpExceptionDetailsBO exceptionDetailsBO = (EmpExceptionDetailsBO) session.createQuery(query).uniqueResult();
			return exceptionDetailsBO;
		}catch(Exception e)
		{
			log.error("Exception occured in getExceptionDetailsOnId in IMPL :"+ e);
			throw new ApplicationException(e);
		}
		finally {
			if(session != null){
				session.flush();
				session.close();
			}
		}
	}
}
