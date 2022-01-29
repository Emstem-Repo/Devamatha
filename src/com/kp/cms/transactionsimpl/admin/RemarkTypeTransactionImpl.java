package com.kp.cms.transactionsimpl.admin;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.RemarkType;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.admin.RemarkTypeForm;
import com.kp.cms.transactions.admin.IRemarkTypeTransaction;
import com.kp.cms.utilities.HibernateUtil;



public class RemarkTypeTransactionImpl implements IRemarkTypeTransaction{
	public static volatile RemarkTypeTransactionImpl remarkTypeTransactionImpl = null;
	private static final Log log = LogFactory.getLog(RemarkTypeTransactionImpl.class);

	public static RemarkTypeTransactionImpl getInstance() {
		if (remarkTypeTransactionImpl == null) {
			remarkTypeTransactionImpl = new RemarkTypeTransactionImpl();
			return remarkTypeTransactionImpl;
		}
		return remarkTypeTransactionImpl;
	}
	/**
	 * this will add new remark type
	 */
	public boolean addRemarkType(RemarkType remarkType, String mode) throws Exception {
		Session session = null;
		Transaction tx = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			if(session!=null){
			     tx = session.beginTransaction();
			     tx.begin();
			     if(mode.equalsIgnoreCase("Add")){
				     session.save(remarkType);
			     }
			     else
			     {
				     session.update(remarkType);
			     }
			     tx.commit();
			     session.flush();
			     session.close();
			     return true;
			}else
			     return false;
		} catch (ConstraintViolationException e) {
			if(tx!=null)
			     tx.rollback();
			log.error("Error during saving remark type data...", e);
			throw new BusinessException(e);
		} catch (Exception e) {
			if(tx!=null)
			     tx.rollback();
			log.error("Error during saving remark type data...", e);
			throw new ApplicationException(e);
		}

	}
	/**
	 * creating remark type list for ui display
	 * @return
	 * @throws Exception
	 */
	public List<RemarkType> getRemarks() throws Exception {
		Session session = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from RemarkType r where r.isActive = 1");
			List<RemarkType> list = query.list();
			session.flush();
			//session.close();
			//sessionFactory.close();
			return list;
		 } catch (Exception e) {
			 log.error("Error during getting Grades...",e);
			 // session.flush();
			 //session.close();
			 throw  new ApplicationException(e);
		 }
	}
	
	/**
	 * 
	 * @param id
	 * @param activate
	 * @param remarkTypeForm
	 * @return
	 * @throws Exception
	 */
	public boolean deleteRemarks(int id, Boolean activate, RemarkTypeForm remarkTypeForm) throws Exception {
	Session session = null;
	Transaction tx = null;
	boolean result = false;
	try {
		//SessionFactory sessionFactory = InitSessionFactory.getInstance();
		//session = sessionFactory.openSession();
		session = HibernateUtil.getSession();
		tx = session.beginTransaction();
		tx.begin();
		RemarkType remarkType = (RemarkType) session.get(RemarkType.class, id);
		if (activate) {
			remarkType.setIsActive(true);
		}else
		{
			remarkType.setIsActive(false);
		}
		remarkType.setModifiedBy(remarkTypeForm.getModifiedBy());
		remarkType.setLastModifiedDate(new Date());
		session.update(remarkType);
		tx.commit();
		session.flush();
		session.close();
		result = true;
	} catch (ConstraintViolationException e) {
		if(tx!=null)
		     tx.rollback();
		log.error("Error in deleteRemarks..." , e);
		throw new BusinessException(e);
	} catch (Exception e) {
		if(tx!=null)
		    tx.rollback();
		log.error("Error in deleteRemarks.." , e);
		throw new ApplicationException(e);
	}
	return result;
	}
	
	/**
	 * 
	 */
	public RemarkType isRemarkTypeDuplcated(String remType, int remarkId) throws Exception {
		Session session = null;
		RemarkType remarkType;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			String sqlString;
			if(remarkId!= 0){
				sqlString = "from RemarkType r where r.remarkType = :tempRemark and r.id != :remarkId";
				}
			else{
				sqlString = "from RemarkType r where r.remarkType = :tempRemark";
			}
			Query query = session.createQuery(sqlString);
			
			if(remarkId!= 0){
				query.setString("tempRemark", remType);
				query.setInteger("remarkId", remarkId);
			}
			else
			{
				query.setString("tempRemark", remType);
			}
				
			remarkType = (RemarkType) query.uniqueResult();
			session.flush();
			//session.close();
			//sessionFactory.close();
		} catch (Exception e) {
			log.error("Error during duplcation checking..." , e);
			//session.flush();
			//session.close();
			throw new ApplicationException(e);
		}
		log.debug("leaving isRemarkTypeDuplcated");
		return remarkType;
	}
	/* (non-Javadoc)
	 * get remark type by id
	 * @see com.kp.cms.transactions.admin.IRemarkTypeTransaction#getRemarkTypeById(int)
	 */
	@Override
	public RemarkType getRemarkTypeById(int id) throws Exception {
		Session session = null;
		RemarkType rtype=null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from RemarkType r where r.id=:ID and r.isActive = 1");
			query.setInteger("ID", id);
			rtype =(RemarkType) query.uniqueResult();
			session.flush();
			//session.close();
			//sessionFactory.close();
			
		 } catch (Exception e) {
			 log.error("Error during getRemarkTypeById..",e);
			 //session.flush();
			 //session.close();
			 throw  new ApplicationException(e);
		 }
		 return rtype;
	}	
	
}
