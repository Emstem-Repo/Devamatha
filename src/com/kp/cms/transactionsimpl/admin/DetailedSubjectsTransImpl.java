package com.kp.cms.transactionsimpl.admin;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.DetailedSubjects;
import com.kp.cms.transactions.admin.IDetailedSubjectsTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class DetailedSubjectsTransImpl implements IDetailedSubjectsTransaction{
	
	private static volatile DetailedSubjectsTransImpl detailedSubjectsTransImpl= null;
	private static final Log log = LogFactory.getLog(DetailedSubjectsTransImpl.class);
	
	/**
	 * 
	 * @return
	 */
	public static DetailedSubjectsTransImpl getInstance() {
	      if(detailedSubjectsTransImpl == null) {
	    	  detailedSubjectsTransImpl = new DetailedSubjectsTransImpl();
	    	  return detailedSubjectsTransImpl;
	      }
	      return detailedSubjectsTransImpl;
	}
	
	/**
	 *  Method returns list of detailed subjects.
	 */
	public List<DetailedSubjects> getAllDetailedSubjects() throws Exception {
		log.debug("Txn Impl : Entering getAllDetailedSubjects ");
		Session session = null;
		try {
			 //SessionFactory sessionFactory = InitSessionFactory.getInstance();
			 //session =sessionFactory.openSession();
			 session = HibernateUtil.getSession();
			 Query query = session.createQuery("from DetailedSubjects where " +
					 						   " course.isActive = 1"+
					 						   " and isActive = :isActive");
			 query.setBoolean("isActive",true);
			 List<DetailedSubjects> list = query.list();
			 //session.close();
			 //sessionFactory.close();
			 log.debug("Txn Impl : Leaving getAllDetailedSubjects with success");
			 return list;
		 } catch (Exception e) {
			 log.debug("Txn Impl : Leaving getAllDetailedSubjects with Exception",e);
			 throw e;
		 }
	}
	
	/**
	 * This method returns detailed subjects based on course.
	 */
	public List<DetailedSubjects> getDetailedsubjectsByCourse(int courseId) throws Exception {
		log.debug("Txn Impl : Entering getAllDetailedSubjects ");
		Session session = null;
		try {
			 //SessionFactory sessionFactory = InitSessionFactory.getInstance();
			 //session =sessionFactory.openSession();
			 session = HibernateUtil.getSession();
			 Query query = session.createQuery("from DetailedSubjects where " +
					 						   " course.isActive = 1"+
					 						   " and isActive = :isActive" +
					 						   " and course.id = :courseId");
			 query.setBoolean("isActive",true);
			 query.setInteger("courseId",courseId);
			 List<DetailedSubjects> list = query.list();
			 //session.close();
			 //sessionFactory.close();
			 log.debug("Txn Impl : Leaving getAllDetailedSubjects with success");
			 return list;
		 } catch (Exception e) {
			 log.debug("Txn Impl : Leaving getAllDetailedSubjects with Exception",e);
			 throw e;
		 }
	}
	
	/**
	 * This method adds the single Detailed subjects to database.
	 */
	public boolean addDetailedSubjects(DetailedSubjects detailedSubjects) throws Exception {
		log.debug("Txn Impl : Entering addDetailedSubjects ");
		Session session = null; 
		Transaction tx = null;
		try {
			 //SessionFactory sessionFactory = InitSessionFactory.getInstance();
			 //session =sessionFactory.openSession();
			 session = HibernateUtil.getSession();
			 tx = session.beginTransaction();
			 tx.begin();
			 session.save(detailedSubjects);
			 tx.commit();
			 session.close();
			 log.debug("Txn Impl : Leaving addDetailedSubjects with success");
	    	 return true;
		 }  catch (Exception e) {
			 if(tx!=null)
			     tx.rollback();
			 //session.close();
			 log.debug("Txn Impl : Leaving addDetailedSubjects with Exception",e);
			 throw e;				 
		 } 
	}
	
	/**
	 * This method deletes the single detailed subject from table.
	 */
	public boolean deleteDetailedSubjects(DetailedSubjects detailedSubjects) throws Exception{
		log.debug("Txn Impl : Entering deleteDetailedSubjects "); 
		Session session = null;
		Transaction tx = null;
		DetailedSubjects persistantDetailedSubject;
		try {
			 //SessionFactory sessionFactory = InitSessionFactory.getInstance();
			 //session =sessionFactory.openSession();
			 session = HibernateUtil.getSession();
		     tx = session.beginTransaction();
			 tx.begin();
			 persistantDetailedSubject = (DetailedSubjects)session.get(DetailedSubjects.class, detailedSubjects.getId());
			 persistantDetailedSubject.setIsActive(false);
			 persistantDetailedSubject.setModifiedBy(detailedSubjects.getModifiedBy());
			 persistantDetailedSubject.setLastModifiedDate(detailedSubjects.getLastModifiedDate());
			 tx.commit();
			 //session.close();
			 log.debug("Txn Impl : Leaving deleteDetailedSubjects with success");
	    	 return true;
		} catch (Exception e){
			 log.debug("Txn Impl : Leaving deleteDetailedSubjects with Exception",e);
			 if(tx!=null)
			     tx.rollback();
			 //session.close();
			 throw e;
		}
	 }
	
	/**
	 *  This method updates the detailed subjects.
	 */
	public boolean updateDetailedSubjects(DetailedSubjects detailedSubjects) throws Exception {
		log.debug("Txn Impl : Entering UpdateDetailedSubjects ");
		Session session = null; 
		Transaction tx = null;
		try {
			 //SessionFactory sessionFactory = InitSessionFactory.getInstance();
			 //session =sessionFactory.openSession();
			 session = HibernateUtil.getSession();
			 tx = session.beginTransaction();
			 tx.begin();
			 session.update(detailedSubjects);
			 tx.commit();
			 session.close();
			 log.debug("Txn Impl : Leaving UpdateDetailedSubjects with success");
	    	 return true;
		 }  catch (Exception e) {
			 if(tx!=null)
			      tx.rollback();
			 //session.close();
			 log.debug("Txn Impl : Leaving UpdateDetailedSubjects with Exception",e);
			 throw e;				 
		 } 
	}
	
	/**
	 * This method used to activate the particular detailed subject.
	 */
	public boolean actiavateDetailedSubjects(DetailedSubjects detailedSubjects) throws Exception{
		log.debug("Txn Impl : Entering ActiavateDetailedSubjects "); 
		Session session = null;
		Transaction tx = null;
		DetailedSubjects persistantDetailedSubject;
		try {
			 //SessionFactory sessionFactory = InitSessionFactory.getInstance();
			 //session =sessionFactory.openSession();
			 session = HibernateUtil.getSession();
		     tx = session.beginTransaction();
			 tx.begin();
			 persistantDetailedSubject = (DetailedSubjects)session.get(DetailedSubjects.class, detailedSubjects.getId());
			 persistantDetailedSubject.setIsActive(true);
			 tx.commit();
			 //session.close();
			 log.debug("Txn Impl : Leaving ActiavateDetailedSubjects with success");
	    	 return true;
		} catch (Exception e){
			 log.debug("Txn Impl : Leaving ActiavateDetailedSubjects with Exception",e);
			 if(tx!=null)
			     tx.rollback();
			 //session.close();
			 throw e;
		}
	 }
	
	/**
	 * This method gets the lst of detailed subjects based on courseId and name
	 */
	public List<DetailedSubjects> getDetailedsubjectsByCourseAndName(int courseId,String name) throws Exception {
		log.debug("Txn Impl : Entering getAllDetailedSubjects ");
		Session session = null;
		try {
			 //SessionFactory sessionFactory = InitSessionFactory.getInstance();
			 //session =sessionFactory.openSession();
			 session = HibernateUtil.getSession();
			 Query query = session.createQuery("from DetailedSubjects where " +
					 						   " course.isActive = 1"+
					 						   " and course.id = :courseId" +
					 						   " and subjectName = :subjectName ");
			 query.setInteger("courseId",courseId);
			 query.setString("subjectName",name);
			 List<DetailedSubjects> list = query.list();
			 //session.close();
			 //sessionFactory.close();
			 log.debug("Txn Impl : Leaving getAllDetailedSubjects with success");
			 return list;
		 } catch (Exception e) {
			 log.debug("Txn Impl : Leaving getAllDetailedSubjects with Exception",e);
			 throw e;
		 }
	}
	
	/**
	 * This method returns the detailed subject by id.
	 */
	public DetailedSubjects getDetailedSubjectsId(int id) throws Exception{
		log.debug("Txn Impl : Entering addDetailedSubjects ");
		Session session = null; 
		Transaction tx = null;
		DetailedSubjects detailedSubjects;
		try {
			 //SessionFactory sessionFactory = InitSessionFactory.getInstance();
			 //session =sessionFactory.openSession();
			 session = HibernateUtil.getSession();
			 tx = session.beginTransaction();
			 tx.begin();
			 detailedSubjects = (DetailedSubjects)session.get(DetailedSubjects.class,id);
			 tx.commit();
			 //session.close();
			 log.debug("Txn Impl : Leaving addDetailedSubjects with success");
	    	 return detailedSubjects;
		 }  catch (Exception e) {
			 if(tx!=null)
			      tx.rollback();
			 //session.close();
			 log.debug("Txn Impl : Leaving addDetailedSubjects with Exception",e);
			 throw e;				 
		 } 
	}

}
