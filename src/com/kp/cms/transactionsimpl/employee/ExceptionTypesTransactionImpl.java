package com.kp.cms.transactionsimpl.employee;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.DocTypeExams;
import com.kp.cms.bo.admin.EmpExceptionTypeBO;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.employee.ExceptionTypesForm;
import com.kp.cms.transactions.employee.IExceptionTypesTransaction;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;

public class ExceptionTypesTransactionImpl implements
		IExceptionTypesTransaction {

	private static final Log log = LogFactory
			.getLog(ExceptionTypesTransactionImpl.class);
	public static volatile ExceptionTypesTransactionImpl objImpl = null;

	public static ExceptionTypesTransactionImpl getInstance() {
		if (objImpl == null) {
			objImpl = new ExceptionTypesTransactionImpl();
			return objImpl;
		}
		return objImpl;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.employee.IExceptionTypesTransaction#addAttendance(com.kp.cms.bo.admin.EmpExceptionTypeBO, java.lang.String)
	 */
	public boolean addAttendance(EmpExceptionTypeBO bo, String mode)
			throws Exception {
		Session session = null;
		boolean flag = false;
		Transaction transaction = null;
		try {
			session = InitSessionFactory.getInstance().openSession();
			transaction = session.beginTransaction();
			if (mode.equalsIgnoreCase("add")) {
				session.save(bo);
			} else {
				session.update(bo);
			}
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

	public List<EmpExceptionTypeBO> getExceptionTypes() throws Exception {
		List<EmpExceptionTypeBO> listBo = null;
		Session session = null;

		try {
			session = InitSessionFactory.getInstance().openSession();
			Query query = session
					.createQuery(" from EmpExceptionTypeBO et where et.isActive=1");
			listBo = query.list();
			session.flush();
		} catch (Exception e) {
			session.flush();
			session.close();
			throw new ApplicationException(e);
		}finally{
			session.flush();
			session.close();
		}
		return listBo;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.employee.IExceptionTypesTransaction#isDuplicated(java.lang.String, java.lang.Integer)
	 */
	public void isDuplicated(ExceptionTypesForm exForm,String exceptionType,String exceptionShortName, Integer id)
			throws Exception {
		Session session = null;
		EmpExceptionTypeBO exceptionTypeBO=null;
			session = HibernateUtil.getSession();
			exceptionTypeBO =(EmpExceptionTypeBO)session.createQuery("from EmpExceptionTypeBO e where e.exceptionType ='"+exceptionType+"' or e.exceptionShortName='"+exceptionShortName+"'").uniqueResult();
			session.flush();
			if(exceptionTypeBO!=null){
				if (!(exceptionTypeBO.getId() == id) && !exceptionTypeBO.getIsActive() ) {
					exForm.setOldId(exceptionTypeBO.getId());
					throw new ReActivateException();
				}else if(!(exceptionTypeBO.getId() == id) && exceptionTypeBO.getIsActive()){
					throw new DuplicateException();
				}
			}
	}

	public boolean deleteExceptionTypes(int id, String userId) throws Exception {
		Session session = null;
		Transaction tx = null;
		boolean result = false;
		try {
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			tx.begin();
			EmpExceptionTypeBO objBO = (EmpExceptionTypeBO) session.get(
					EmpExceptionTypeBO.class, id);

			objBO.setIsActive(false);
			objBO.setModifiedBy(userId);
			objBO.setLastModifiedDate(new Date());
			session.update(objBO);
			tx.commit();
			session.flush();
			session.close();
			result = true;
		} catch (ConstraintViolationException e) {
			tx.rollback();
			log.error("Error in deleteAttribute...", e);
			throw new BusinessException(e);
		} catch (Exception e) {
			tx.rollback();
			log.error("Error in deleteAttribute..", e);
			throw new ApplicationException(e);
		}
		return result;
	}

	public EmpExceptionTypeBO getException(int id) throws Exception {
		Session session = null;
		EmpExceptionTypeBO objBO = null;
		try {
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery(" from EmpExceptionTypeBO et where et.isActive=1 and et.id="
							+ id);
			objBO = (EmpExceptionTypeBO) query.uniqueResult();
			session.flush();
		} catch (Exception e) {
			session.flush();
			session.close();
			throw new ApplicationException(e);
		}
		return objBO;

	}

	@Override
	public boolean reactivate(int oldId, String userId) throws Exception {

		log.info("Entering into reactivate");
		//SessionFactory sessionFactory = InitSessionFactory.getInstance();		
		Session session = null;
		EmpExceptionTypeBO type = null;
		Transaction transaction = null;
		try { 
		 //session =sessionFactory.openSession();
		 session = HibernateUtil.getSession();
		 Query query = session.createQuery("from EmpExceptionTypeBO e where e.id=:id");				                          
		 query.setInteger("id", oldId);
		 type = (EmpExceptionTypeBO) query.uniqueResult();	
		 transaction = session.beginTransaction();
		 type.setIsActive(true);
		 type.setModifiedBy(userId);
		 type.setLastModifiedDate(new Date());
		 session.update(type);
		 transaction.commit();
		 return true;
		}
		catch (Exception e) {	
			if(transaction!=null){
				transaction.rollback();
			}
			log.error("Exception occured while reactivate  :"+e);
			throw  new ApplicationException(e);
		} finally {
		if (session != null) {
			//sessionFactory.close();
			session.flush();
			session.close();
		}
		log.info("Leaving into reactivate");
		}
	
	}

}
