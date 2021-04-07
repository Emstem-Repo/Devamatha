package com.kp.cms.transactionsimpl.admin;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.College;
import com.kp.cms.bo.admin.University;
import com.kp.cms.to.admin.CollegeTO;
import com.kp.cms.transactions.admin.IUniversityTxn;
import com.kp.cms.utilities.HibernateUtil;

public class UniversityTxnImpl implements IUniversityTxn{

	private static volatile UniversityTxnImpl universityTxnImpl = null;

	/**
	 * 
	 * @return List
	 */
	public List<University> getUniversity() {
		List<University> universityList = null;
		Session dbSession = null;
		try {
			SessionFactory sessfactory = HibernateUtil.getSessionFactory();
			dbSession = sessfactory.openSession();
			//dbSession = HibernateUtil.getSession();
			universityList = new ArrayList<University>();
			Query q = dbSession.createQuery("from University u where isActive=1");
			universityList = q.list();
			dbSession.flush();
			//dbSession.close();
			return universityList;
		} catch (ConstraintViolationException e) {
			if( dbSession!= null){
			dbSession.flush();
			dbSession.close();
			}
			throw e;
		} catch (Exception e) {
			if( dbSession!= null){
				dbSession.flush();
				dbSession.close();
			}
			return universityList;
		}
	}
	public static UniversityTxnImpl getInstance() {
		   if(universityTxnImpl == null ){
			   universityTxnImpl = new UniversityTxnImpl();
			   return universityTxnImpl;
		   }
		   return universityTxnImpl;
	}
	/**
	 * 
	 * @param uniId
	 * @return
	 */
	public List<College> getCollegeByUniversity(int uniId) {
		List<College> collegeList = null;
		Session dbSession = null;
		try {
			//SessionFactory sessfactory = HibernateUtil.getSessionFactory();
			//dbSession = sessfactory.openSession();
			dbSession = HibernateUtil.getSession();
			collegeList = new ArrayList<College>();
			Query q = dbSession.createQuery("from College c where isActive=1 and c.university.id = " + uniId);
			collegeList = q.list();
			dbSession.flush();
			//dbSession.close();
			return collegeList;
		} catch (ConstraintViolationException e) {
			if( dbSession!= null){
			dbSession.flush();
			dbSession.close();
			}
			throw e;
		} catch (Exception e) {
			if( dbSession!= null){
				dbSession.flush();
				dbSession.close();
			}
			return collegeList;
		}
	}
	public List<CollegeTO> getCollegeByUniversityList(int uniId) {
		List<CollegeTO> collegeList = null;
		Session dbSession = null;
		try {
			//SessionFactory sessfactory = HibernateUtil.getSessionFactory();
			//dbSession = sessfactory.openSession();
			dbSession = HibernateUtil.getSession();
			collegeList = new ArrayList<CollegeTO>();
			Query q = dbSession.createQuery("from College c where isActive=1 and c.university.id = " + uniId);
			collegeList = q.list();
			dbSession.flush();
			//dbSession.close();
			return collegeList;
		} catch (ConstraintViolationException e) {
			if( dbSession!= null){
			dbSession.flush();
			dbSession.close();
			}
			throw e;
		} catch (Exception e) {
			if( dbSession!= null){
				dbSession.flush();
				dbSession.close();
			}
			return collegeList;
		}
	}
	
	public boolean addUniversity(University university)throws Exception
	{
		Session dbSession = null;
		Transaction transaction=null;
		try {
			//SessionFactory sessfactory = HibernateUtil.getSessionFactory();
			//dbSession = sessfactory.openSession();
			dbSession = HibernateUtil.getSession();
			transaction=dbSession.beginTransaction();
			dbSession.saveOrUpdate(university);
			transaction.commit();
			dbSession.flush();
			//dbSession.close();
			return true;
		} catch (ConstraintViolationException e) {
			if( dbSession!= null){
			transaction.rollback();
			dbSession.flush();
			}
			throw e;
		} catch (Exception e) {
			if( dbSession!= null){
				transaction.rollback();
				dbSession.flush();
			}
			throw e;
			
		}
	}
	
	public University getUniversityDetails(Integer id)throws Exception
	{
		Session dbSession = null;
		try {
			//SessionFactory sessfactory = HibernateUtil.getSessionFactory();
			//dbSession = sessfactory.openSession();
			dbSession = HibernateUtil.getSession();
			University university=(University) dbSession.get(University.class,id);
			
			dbSession.flush();
			//dbSession.close();
			return university;
		} catch (ConstraintViolationException e) {
			if( dbSession!= null){
			dbSession.flush();
			}
			throw e;
		} catch (Exception e) {
			if( dbSession!= null){
				dbSession.flush();
			}
			throw e;
		}
	}
	
	public boolean deleteOrReactivate(Integer id, String action)throws Exception
	{
		Session dbSession = null;
		Transaction transaction=null;
		try {
			//SessionFactory sessfactory = HibernateUtil.getSessionFactory();
			//dbSession = sessfactory.openSession();
			dbSession = HibernateUtil.getSession();
			transaction=dbSession.beginTransaction();
			University university=(University) dbSession.get(University.class,id);
			if(action.equalsIgnoreCase("delete"))
				university.setIsActive(false);
			else
				university.setIsActive(true);
			dbSession.update(university);
			transaction.commit();
			dbSession.flush();
			//dbSession.close();
			return true;
		} catch (ConstraintViolationException e) {
			if( dbSession!= null){
				transaction.rollback();
				dbSession.flush();
			
			}
			throw e;
		} catch (Exception e) {
			if( dbSession!= null){
				transaction.rollback();
				dbSession.flush();
			}
			throw e;
		}
	}
	public University checkDuplicate(String university, int docType)throws Exception
	{
		Session dbSession = null;
		try {
			//SessionFactory sessfactory = HibernateUtil.getSessionFactory();
			//dbSession = sessfactory.openSession();
			dbSession = HibernateUtil.getSession();
			Query query =dbSession.createQuery("from University u where u.name='"+university+"' and u.docType.id="+docType);
			University universityObj=(University)query.uniqueResult();
			dbSession.flush();
			//dbSession.close();
			return universityObj;
		} catch (ConstraintViolationException e) {
			if( dbSession!= null){
			dbSession.flush();
			}
			throw e;
		} catch (Exception e) {
			if( dbSession!= null){
				dbSession.flush();
			}
			throw e;
		}
	}
	
}
