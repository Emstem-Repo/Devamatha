package com.kp.cms.transactionsimpl.exam;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.TCNumber;
import com.kp.cms.bo.exam.GrievanceNumber;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.admin.TCMasterForm;
import com.kp.cms.forms.exam.GrievanceMasterForm;
import com.kp.cms.transactions.exam.IGrievanceMasterTransaction;
import com.kp.cms.utilities.HibernateUtil;


public class GrievanceMasterTransactionImpl implements IGrievanceMasterTransaction{
	
	private static volatile GrievanceMasterTransactionImpl grievanceMasterTransactionImpl = null;
	private static final Log log = LogFactory.getLog(GrievanceMasterTransactionImpl.class);
	private GrievanceMasterTransactionImpl() {
		
	}
	/**
	 * return singleton object of TCMasterTransactionImpl.
	 * @return
	 */
	public static GrievanceMasterTransactionImpl getInstance() {
		if (grievanceMasterTransactionImpl == null) {
			grievanceMasterTransactionImpl = new GrievanceMasterTransactionImpl();
		}
		return grievanceMasterTransactionImpl;
	}
	
	public List<GrievanceNumber> getAllGrievanceNumber() throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			List<GrievanceNumber> tcList = session.createQuery("from GrievanceNumber gv where gv.isActive=1").list();
			session.flush();
			return tcList;
		} catch (Exception e) {
			if (session != null){
				session.flush();
			}
			return null;
		}
	}
	
	public GrievanceNumber isGrievanceNumberDuplcated(GrievanceMasterForm grievanceMasterForm) throws Exception {
		Session session = null;
		GrievanceNumber grievanceNumber;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			//StringBuffer sqlString = new StringBuffer("from TCNumber i where ((i.type = :type and i.year=:year) or (i.year=:year and i.prefix=:prefix)) and i.tcFor=:tcFor");
			StringBuffer sqlString = new StringBuffer("from GrievanceNumber g where g.year=:year");
			Query query = session.createQuery(sqlString.toString());
		
			query.setInteger("year", Integer.parseInt(grievanceMasterForm.getYear()));
			
			grievanceNumber = (GrievanceNumber) query.uniqueResult();
			session.flush();
			//session.close();
			//sessionFactory.close();
		} 
		catch (RuntimeException e) {
			log.error("Error during duplcation checking..." , e);
			throw new ApplicationException(e);
		}catch (Exception e) {
			log.error("Error during duplcation checking..." , e);
			throw new ApplicationException(e);
		}
		return grievanceNumber;
	}
	
	public boolean addGrievanceMaster(GrievanceNumber bo,String mode) throws Exception {
		Session session = null;
		Transaction transaction = null;
		try {
			/*session = InitSessionFactory.getInstance().openSession();
			transaction = session.beginTransaction();
			transaction.begin();*/
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			if(mode.equals("add")){
				session.save(bo);
			}else{
				session.merge(bo);
			}
			transaction.commit();
			session.flush();
			return true;
		} 
		catch (RuntimeException e) {
			if ( transaction != null){
				transaction.rollback();
			}
			return false;
		}catch (Exception e) {
			if ( transaction != null){
				transaction.rollback();
			}
			return false;
		}
	}
	
	public boolean deleteGrievanceMaster(int id, Boolean activate, GrievanceMasterForm grievanceMasterForm) throws Exception {
		Session session = null;
		Transaction tx = null;
		boolean result = false;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			tx.begin();
			GrievanceNumber grievanceNumber = (GrievanceNumber) session.get(GrievanceNumber.class, id);
			if (activate) {
				grievanceNumber.setIsActive(true);
			}else
			{
				grievanceNumber.setIsActive(false);
			}
			grievanceNumber.setModifiedBy(grievanceMasterForm.getUserId());
			grievanceNumber.setLastModifiedDate(new Date());
			session.update(grievanceNumber);
			tx.commit();
			session.flush();
			session.close();
			result = true;
		} catch (ConstraintViolationException e) {
			if(tx!=null)
			     tx.rollback();
			log.error("Error in deleteCounter..." , e);
			throw new BusinessException(e);
		} catch (Exception e) {
			if(tx!=null)
			     tx.rollback();
			log.error("Error in deleteCounter.." , e);
			throw new ApplicationException(e);
		}
		return result;
	}
	

}
