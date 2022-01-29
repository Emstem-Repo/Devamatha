package com.kp.cms.transactionsimpl.admin;


import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;


import com.kp.cms.bo.admin.ParishBO;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;

import com.kp.cms.forms.admin.ParishForm;
import com.kp.cms.to.admin.ParishTo;
import com.kp.cms.utilities.HibernateUtil;

import common.Logger;

public class ParishTransactionImpl {
	
	private static final Logger log = Logger.getLogger(ParishTransactionImpl.class);
	private static volatile ParishTransactionImpl transaction = null;
	public static ParishTransactionImpl getInstance()
    {
        if(transaction == null)
        {
        	transaction = new ParishTransactionImpl();
        }
        return transaction;
    }
	
	public boolean addParishtran(ParishBO parish,String mode) throws Exception {
		Session session = null;
		Transaction tx = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			if(session!=null){
			     tx = session.beginTransaction();
			     tx.begin();
			     
			     if (mode.equalsIgnoreCase("Add")) { //add mode
				     session.save(parish);
			     } else if (mode.equalsIgnoreCase("Edit")) {  //edit mode
				 session.update(parish);
			       }
				     tx.commit();
				 
			       }
			return true;
		
		} catch (Exception e) {
			if(tx!=null)
			     tx.rollback();
			log.error("Error during saving parish data...", e);
			throw new ApplicationException(e);
		}finally{
			
		     session.flush();
		     session.close();
		}
		
	}
	
	public List<ParishBO> getParishTran() throws ApplicationException
	{
		Session session = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			if(session!=null){
			    Query query = session.createQuery("from ParishBO p where p.isActive = 1 ");
			    List<ParishBO> list = query.list();
				session.flush();
				//session.close();
			    return list;
			}else
				return null;
		} catch (Exception e) {
			log.error("Error in getDiocese.. impl", e);
			//session.flush();
			//session.close();
			throw new ApplicationException(e);
		}
	}

	public boolean deleteParish(int parishId, Boolean activate, ParishForm pForm) throws Exception{
		Session session = null;
		Transaction tx = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			tx.begin();
			ParishBO parishBo = (ParishBO) session.get(ParishBO.class, parishId);
			if (activate) {
				parishBo.setIsActive(true);
			} else {
				parishBo.setIsActive(false);
			}
			parishBo.setModifiedBy(pForm.getUserId());
			parishBo.setLastModifiedDate(new Date());
			session.update(parishBo);
			tx.commit();
			session.flush();
			session.close();
			return true;
		} catch (ConstraintViolationException e) {
			if(tx!=null)
			    tx.rollback();
			log.error("Error during deleting diocese...", e);
			throw new BusinessException(e);
		} catch (Exception e) {
			if(tx!=null)
		    	tx.rollback();
			log.error("Error during deleting diocese...", e);
			throw new ApplicationException(e);
		}
	 
	 }
	
	public ParishBO isParishDuplicated(ParishBO duplParish) throws Exception {
		Session session = null;
		ParishBO parish;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			StringBuffer sqlString = new StringBuffer("from ParishBO a where a.name = :parishName and a.dioceseId.id = :dioceseId");
			if(duplParish.getId() !=0){
				sqlString = sqlString.append(" and id != :parishId");
			}
			Query query = session.createQuery(sqlString.toString());
			query.setString("parishName", duplParish.getName());
			query.setInteger("dioceseId", duplParish.getDioceseId().getId());
			if(duplParish.getId() !=0){
				query.setInteger("parishId", duplParish.getId());
			}
			
			parish = (ParishBO) query.uniqueResult();
			session.flush();
			//session.close();
			//sessionFactory.close();
			return parish;
		} catch (Exception e) {
			log.error("Error during duplcation checking..." + e);
			//session.flush();
			//session.close();
			throw new ApplicationException(e);
		}
	}
	
	public List<ParishBO> getParish() throws Exception {
		Session session = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			if(session!=null){
			    Query query = session.createQuery("from ParishBO p where p.isActive = 1 ");
			    List<ParishBO> list = query.list();
				session.flush();
				//session.close();
			    return list;
			}else
				return null;
		} catch (Exception e) {
			log.error("Error in getdiocese.. impl", e);
			//session.flush();
			//session.close();
			throw new ApplicationException(e);
		}
	}
	
	public List<ParishTo> getParishes() throws Exception {
		Session session = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			if(session!=null){
			    Query query = session.createQuery("from ParishBO p where p.isActive = 1 ");
			    List<ParishTo> list = query.list();
				session.flush();
				//session.close();
			    return list;
			}else
				return null;
		} catch (Exception e) {
			log.error("Error in getdiocese.. impl", e);
			//session.flush();
			//session.close();
			throw new ApplicationException(e);
		}
	}
}
