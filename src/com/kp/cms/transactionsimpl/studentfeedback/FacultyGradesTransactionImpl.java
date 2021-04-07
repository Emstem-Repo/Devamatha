package com.kp.cms.transactionsimpl.studentfeedback;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.studentfeedback.FacultyGrades;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.studentfeedback.FacultyGradesForm;
import com.kp.cms.transactions.studentfeedback.IFacultyGradesTransaction;
import com.kp.cms.transactionsimpl.studentfeedback.FacultyGradesTransactionImpl;
import com.kp.cms.utilities.HibernateUtil;

public class FacultyGradesTransactionImpl implements IFacultyGradesTransaction {
	private static final Log log = LogFactory.getLog(FacultyGradesTransactionImpl.class);
	
	public boolean addFacultyGrades(FacultyGrades facultyGrades) throws Exception
	{
		log.info("Start of addFacultyGrades of FacultyGrades TransactionImpl");
		Session session = null;
		Transaction transaction = null;
		try {
			//session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			session.save(facultyGrades);
			transaction.commit();
			return true;
		} catch (Exception e) {
			if(transaction != null){
				transaction.rollback();
			}
			log.error("Exception occured in adding FacultyGrades in IMPL :"+e);
			throw  new ApplicationException(e);
		} finally {
		if (session != null) {
			session.flush();
			session.close();
		}
		log.info("End of addFacultyGrades of FacultyGrades TransactionImpl");
		}
	}
	
	public List<FacultyGrades> getFacultyGradesDetails()throws Exception{
		log.info("Start of getFacultyGradesDetails of FacultyGrades TransactionImpl");
		Session session = null;
		List<FacultyGrades> facultyGradesList;
		try {
		//	session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			facultyGradesList = session.createQuery("from FacultyGrades e where e.isActive = 1 ").list();
		} catch (Exception e) {			
			log.error("Exception occured in getFacultyGradesDetails in FacultyGradesIMPL :"+e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
		//	session.close();
			}
		}
		log.info("End of getFacultyGradesDetails of FacultyGrades TransactionImpl");
		return facultyGradesList;		
	}
	
	public boolean deleteFacultyGrades(int id, String userId) throws Exception{
		log.info("Start of deleteFacultyGrades of FacultyGrades TransactionImpl");
		Session session = null;
		Transaction transaction = null;
		try {
			//session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			FacultyGrades facultyGrades=(FacultyGrades)session.get(FacultyGrades.class,id);
			facultyGrades.setIsActive(false);
			facultyGrades.setLastModifiedDate(new Date());
			facultyGrades.setModifiedBy(userId);
			session.update(facultyGrades);
			transaction.commit();
			return true;
		} catch (Exception e) {
			if(transaction != null){
				transaction.rollback();
			}
			log.error("Exception occured in deleting FacultyGrades in IMPL :"+e);	
			throw  new ApplicationException(e);
		} finally {
		if (session != null) {
			session.flush();
			session.close();
		}
		log.info("End of deleteFacultyGrades of FacultyGrades TransactionImpl");
		}
	}
	
	public boolean updateFacultyGrades(FacultyGrades facultyGrades)throws Exception
	{
		log.info("Start of updateFacultyGrades of FacultyGrades TransactionImpl");
		Session session = null;
		Transaction transaction = null;
		try {
			//session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			session.merge(facultyGrades);
			transaction.commit();
			return true;
		} catch (Exception e) {
			if(transaction != null){
				transaction.rollback();
			}
			log.error("Exception occured while updating FacultyGrades in IMPL :"+e);
			throw  new ApplicationException(e);
		} finally {
		if (session != null) {
			session.flush();
			session.close();		
		}
		log.info("End of updateFacultyGrades of FacultyGrades TransactionImpl");
		}
	}
	
	public boolean reActivateFacultyGrades(String grade, String userId)throws Exception
	{
		log.info("Start of reActivateFacultyGrades of FacultyGrades TransactionImpl");
		Session session = null;
		Transaction transaction = null;
			try {
				//SessionFactory sessionFactory = InitSessionFactory.getInstance();
				//session = sessionFactory.openSession();
				session = HibernateUtil.getSession();
				Query query = session.createQuery("from FacultyGrades fac where fac.grade = :grade");
				query.setString("grade",grade);
				FacultyGrades facultyGrades = (FacultyGrades)query.uniqueResult();
				transaction = session.beginTransaction();
				facultyGrades.setIsActive(true);
				facultyGrades.setModifiedBy(userId);
				facultyGrades.setLastModifiedDate(new Date());
				session.update(facultyGrades);
				transaction.commit();
				return true;
				} catch (Exception e) {
				if(transaction != null){
					transaction.rollback();
				}
				log.error("Exception occured in reactivating of FacultyGrades in IMPL :"+e);
				throw new ApplicationException(e);
				} finally {
					if (session != null) {
						session.flush();
						session.close();
					}
					log.info("End of reActivateFacultyGrades of FacultyGrades TransactionImpl");
				}
		}
	
	public FacultyGrades getDetailsonId(int id)throws Exception
	{
		log.info("Start of getDetailsonId of FacultyGrades TransactionImpl");
		Session session = null;
		FacultyGrades facultyGrades = null;
		try {
			//session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			
			Query query=session.createQuery("from FacultyGrades e where e.id= :row");
			query.setInteger("row", id);
			facultyGrades = (FacultyGrades)query.uniqueResult();
		} catch (Exception e) {			
		log.error("Exception occured while getting the row based on the Id in IMPL :"+e);
			throw  new ApplicationException(e);
		} finally {
		if (session != null) {
			session.flush();
			//session.close();
		}
		}
		log.info("End of getDetailsonId of FacultyGrades TransactionImpl");
		return facultyGrades;	
	}
	
	public FacultyGrades checkForDuplicateonGrade(String grade)throws Exception
	{
		log.info("Start of checkForDuplicateonGrade of FacultyGrades TransactionImpl");
		Session session = null;
		FacultyGrades facultyGrades = null;
		try {
			//session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			Query query=session.createQuery("from FacultyGrades e where e.grade= :grade");
			query.setString("grade", grade);
			facultyGrades = (FacultyGrades)query.uniqueResult();
		} catch (Exception e) {
		log.error("Exception occured in checking duplicate records for facultyGrades in IMPL :"+e);
			throw  new ApplicationException(e);
		} finally {
		if (session != null) {
			session.flush();
			//session.close();
		}
		}
		log.info("End of updateFacultyGrades of FacultyGrades TransactionImpl");
		return facultyGrades;	
	}
	
	public boolean checkForDuplicateonGrade1(String grade, ActionErrors errors, FacultyGradesForm facultyGradesForm)throws Exception
	{
		log.info("Start of checkForDuplicateonGrade of FacultyGrades TransactionImpl");
		Session session = null;
		FacultyGrades facultyGrades = null;
		boolean flag=false;
		try {
			//session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			Query query=session.createQuery("from FacultyGrades e where e.grade= :grade");
			query.setString("grade", grade);
			facultyGrades = (FacultyGrades)query.uniqueResult();
			if(facultyGrades!= null){
			if(facultyGrades.getId()!=0){
				if(facultyGrades.getId()==facultyGradesForm.getId())
				{
					flag=false;
				}
					else if(facultyGrades.getIsActive().booleanValue())
   	              {
   	            	 flag = true;
   	            	 errors.add("error", new ActionError("knowledgepro.studentfeedback.facultygrades.exists"));
   	              }else
                        {
                         flag = true;
                         facultyGradesForm.setId(facultyGrades.getId());
                        throw new ReActivateException(facultyGrades.getId());
                        }
				}else if(facultyGrades.getIsActive()){
						flag=true;
						errors.add("error", new ActionError("knowledgepro.studentfeedback.facultygrades.exists"));
					}else{
						  flag=true;
						  
						  facultyGradesForm.setId(facultyGrades.getId());
						  throw new ReActivateException(facultyGrades.getId());
					  }
				
		
			}
			
		} catch (Exception e) {
			if(e instanceof ReActivateException){
				errors.add("error", new ActionError(CMSConstants.STUDENTFEEDBACK_FACULTYGRADES_REACTIVATE));
			}else{
		log.error("Exception occured in checking duplicate records for facultyGrades in IMPL :"+e);
			throw  new ApplicationException(e);
			}
		} finally {
		if (session != null) {
			session.flush();
			//session.close();
		}
		}
		log.info("End of updateFacultyGrades of FacultyGrades TransactionImpl");
		return flag;	
	}

}
