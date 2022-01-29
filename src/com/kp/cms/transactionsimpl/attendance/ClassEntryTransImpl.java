package com.kp.cms.transactionsimpl.attendance;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.ClassSchemewise;
import com.kp.cms.bo.admin.Classes;
import com.kp.cms.transactions.attandance.IClassEntryTransaction;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;

public class ClassEntryTransImpl implements IClassEntryTransaction{
	
	private static volatile ClassEntryTransImpl classTransImpl= null;
	private static final Log log = LogFactory.getLog(ClassEntryTransImpl.class);
	/**
	 * This method returns the single instance of ClassEntryTxn impl.
	 * @return
	 */
	public static ClassEntryTransImpl getInstance() {
	      if(classTransImpl == null) {
	    	  classTransImpl = new ClassEntryTransImpl();
	      }
	      return classTransImpl;
	}
	
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.attandance.IClassEntryTransaction#getAllClass(int)
	 */
	public List<ClassSchemewise> getAllClass(int currentYear) throws Exception {
		log.debug("Txn Impl : Entering getAllClass ");
		Session session = null;
		try {
			 //SessionFactory sessionFactory = InitSessionFactory.getInstance();
			 //session =sessionFactory.openSession();
			 session = HibernateUtil.getSession();
			 Query query = session.createQuery("from ClassSchemewise schemewise" +
			 		" where schemewise.classes.course.isActive = 1 " +
			 		" and schemewise.classes.isActive = :isActive " +
			 		" and schemewise.curriculumSchemeDuration.academicYear = :academicYear ");
			 query.setBoolean("isActive",true);
			 query.setInteger("academicYear", currentYear);
			 List<ClassSchemewise> list = query.list();
			 //session.close();
			 //sessionFactory.close();
			 log.debug("Txn Impl : Leaving getAllClass with success");
			 return list;
		 } catch (Exception e) {
			 log.debug("Txn Impl : Leaving getAllClass with Exception");
			 //session.close();
			 throw e;
		 }
	}
	
	
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.attandance.IClassEntryTransaction#addClass(com.kp.cms.bo.admin.Classes)
	 */
	public boolean addClass(Classes classes) throws Exception {
		log.debug("Txn Impl : Entering addClass ");
		Session session = null; 
		Transaction tx = null;
		try {
			 //SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			 tx = session.beginTransaction();
			 tx.begin();
			 session.save(classes);
			 tx.commit();
			 session.close();
			 log.debug("Txn Impl : Leaving addClass with success");
	    	 return true;
		 }  catch (Exception e) {
			 e.printStackTrace();
			 tx.rollback();
			 session.close();
			 log.debug("Txn Impl : Leaving addClass with Exception");
			 throw e;				 
		 } 
	}
	

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.attandance.IClassEntryTransaction#deleteClass(com.kp.cms.bo.admin.Classes)
	 */
	public boolean deleteClass(Classes classes) throws Exception {
		log.debug("Txn Impl : Entering deleteClass "); 
		Session session = null;
		Transaction tx = null;
		Classes persistantclasses;
		try {
			 //SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
		     tx = session.beginTransaction();
			 tx.begin();
			 persistantclasses = (Classes)session.get(Classes.class, classes.getId());
			 persistantclasses.setIsActive(false);
			 persistantclasses.setModifiedBy(classes.getModifiedBy());
			 persistantclasses.setLastModifiedDate(classes.getLastModifiedDate());
			 tx.commit();
			 //session.close();
			 log.debug("Txn Impl : Leaving deleteClass with success");
	    	 return true;
		} catch (Exception e){
			 log.debug("Txn Impl : Leaving deleteClass with Exception");
			 tx.rollback();
			 //session.close();
			 throw e;
		}
	 }
	
	
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.attandance.IClassEntryTransaction#updateClass(com.kp.cms.bo.admin.Classes)
	 */
	public boolean updateClass(Classes classes) throws Exception {
		log.debug("Txn Impl : Entering UpdateDetailedSubjects ");
		Session session = null; 
		Transaction tx = null;
		try {
			 //SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = InitSessionFactory.getInstance().openSession();
			 tx = session.beginTransaction();
			 tx.begin();
			 session.update(classes);
			 tx.commit();
			 session.close();
			 log.debug("Txn Impl : Leaving UpdateDetailedSubjects with success");
	    	 return true;
		 }  catch (Exception e) {
			 e.printStackTrace();
			 tx.rollback();
			 session.close();
			 log.debug("Txn Impl : Leaving UpdateDetailedSubjects with Exception");
			 throw e;				 
		 } 
	}
	
	
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.attandance.IClassEntryTransaction#actiavateClass(com.kp.cms.bo.admin.Classes)
	 */
	public boolean actiavateClass(Classes classes) throws Exception{
		log.debug("Txn Impl : Entering actiavateClass "); 
		Session session = null;
		Transaction tx = null;
		Classes persistantClass;
		try {
			 //SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
		     tx = session.beginTransaction();
			 tx.begin();
			 persistantClass = (Classes)session.get(Classes.class, classes.getId());
			 persistantClass.setIsActive(true);
			 tx.commit();
			 //session.close();
			 log.debug("Txn Impl : Leaving actiavateClass with success");
	    	 return true;
		} catch (Exception e){
			 log.debug("Txn Impl : Leaving actiavateClass with Exception");
			 tx.rollback();
			 //session.close();
			 throw e;
		}
	 }
	
	
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.attandance.IClassEntryTransaction#getClassById(int)
	 */
	public ClassSchemewise getClassById(int id) throws Exception{
		log.debug("Txn Impl : Entering getClassById ");
		Session session = null; 
		ClassSchemewise classSchemewise;
		try {
			 //SessionFactory sessionFactory = InitSessionFactory.getInstance();
			 session = HibernateUtil.getSession();
			 
			 classSchemewise = (ClassSchemewise)session.createQuery("from ClassSchemewise schemewise " +
					   " where schemewise.classes.id ="+id).uniqueResult();
			 //session.close();
			 //sessionFactory.close();
			 
			 log.debug("Txn Impl : Leaving getClassById with success");
	    	 return classSchemewise;
		 }  catch (Exception e) {
			 //session.close();
			 log.debug("Txn Impl : Leaving getClassById with Exception");
			 throw e;				 
		 } 
	}
	
	
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.attandance.IClassEntryTransaction#getClassesByIds()
	 */
	public List<ClassSchemewise> getClassesByIds() throws Exception {
		log.debug("Txn Impl : Entering getAllClass ");
		Session session = null;
		try {
			 //SessionFactory sessionFactory = InitSessionFactory.getInstance();
			 session = HibernateUtil.getSession();
			 Query query = session.createQuery("from ClassSchemewise schemewise"); 
			 
			 List<ClassSchemewise> list = query.list();
			 //session.close();
			 //sessionFactory.close();
			 log.debug("Txn Impl : Leaving getAllClass with success");
			 return list;
		 } catch (Exception e) {
			 log.debug("Txn Impl : Leaving getAllClass with Exception");
			 //session.close();
			 throw e;
		 }
	}
	
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.attandance.IClassEntryTransaction#getClassesByClassName(java.lang.String)
	 */
	public List<ClassSchemewise> getClassesByClassName(String className,String year) throws Exception {
		log.debug("Txn Impl : Entering getAllClass ");
		Session session = null;
		try {
			 //SessionFactory sessionFactory = InitSessionFactory.getInstance();
			 session = HibernateUtil.getSession();
			 Query query = session.createQuery("from ClassSchemewise schemewise where " +
					 						   " schemewise.classes.name = :className and schemewise.classes.isActive=1 and schemewise.curriculumSchemeDuration.academicYear = '"+year+"'");
			 query.setString("className", className);
			 List<ClassSchemewise> list = query.list();
			 //session.close();
			 //sessionFactory.close();
			 log.debug("Txn Impl : Leaving getAllClass with success");
			 return list;
		 } catch (Exception e) {
			 log.debug("Txn Impl : Leaving getAllClass with Exception");
			 //session.close();
			 throw e;
		 }
	}

	@Override
	public ClassSchemewise checkDuplicateThroughQuery(String duplicateQuery)
			throws Exception {
		log.debug("Txn Impl : Entering getAllClass ");
		Session session = null;
		ClassSchemewise c=null;
		try {
			 session = HibernateUtil.getSession();
			 Query query = session.createQuery(duplicateQuery);
			 c =(ClassSchemewise)query.uniqueResult();
			 log.debug("Txn Impl : Leaving getAllClass with success");
			 return c;
		 } catch (Exception e) {
			 log.debug("Txn Impl : Leaving getAllClass with Exception");
			 throw e;
		 }
	}
}