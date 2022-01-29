package com.kp.cms.transactionsimpl.admin;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.DataException;

import com.kp.cms.bo.admin.GuidelinesDoc;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.transactions.admin.IGuidelinesEntryTransaction;
import com.kp.cms.utilities.HibernateUtil;


public class GuidelinesEntryTransactionImpl implements
		IGuidelinesEntryTransaction {
	private static final Log log = LogFactory.getLog(GuidelinesEntryTransactionImpl.class);

	@Override
	public boolean isGuidelinesExist(int courseID, int year) throws Exception {
		Session session = null;
		boolean isExist=false;
		try {
			//session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			Query query=session.createQuery("from GuidelinesDoc g where g.course.id= :courseID and g.year= :year and g.isActive = 1");
			query.setInteger("courseID", courseID);
			query.setInteger("year", year);
			List<GuidelinesDoc> glist=query.list();
			if(glist!=null && !glist.isEmpty()){
				isExist= true;
			}
			session.flush();
			//session.close();
			return isExist;
		} catch (Exception e) {
			log.error("Error occured in isGuidelinesExist of GuidelinesEntryTransactionImpl");
			if( session != null){
				session.flush();
				//session.close();
			}
			throw new ApplicationException(e);
		} finally {
			log.info("Ending of isGuidelinesExist of GuidelinesEntryTransactionImpl");
		}

	}
	
	public GuidelinesDoc getGuidelinesEntry(int courseId, int year) throws Exception {
		Session session = null;
		try {
			//session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			Query query=session.createQuery("from GuidelinesDoc g where g.course.id= :courseID and g.year= :year and g.isActive = 1");
			query.setInteger("courseID", courseId);
			query.setInteger("year", year);
			List<GuidelinesDoc> glist=query.list();
			GuidelinesDoc doc=null;
			if(glist!=null && !glist.isEmpty()){
				doc=glist.get(0);
			}
			session.flush();
			//session.close();
			return doc;
		} catch (Exception e) {
			log.error("Error occured in getGuidelinesEntry of GuidelinesEntryTransactionImpl");
			if( session != null){
				session.flush();
				//session.close();
			}
			throw new ApplicationException(e);
		} finally {
			log.info("End of getGuidelinesEntry of GuidelinesEntryTransactionImpl");
		}

	}
	
	/**
	 * Gets all the records from Guidelines table displays in UI
	 */


	public List<GuidelinesDoc> getGuidelinesEntryAll ()throws Exception
	{
		Session session = null;
		List<GuidelinesDoc> guidelinesDetails;
		try {
			//session =InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			guidelinesDetails = session.createQuery("from GuidelinesDoc doc where isActive = 1 ").list();
			
		} catch (Exception e) {
		log.error("Exception ocured at getting all records of guidelinesentry in IMPL :",e);
			throw  new ApplicationException(e);
		} finally {
		if (session != null) {
			session.flush();
			//session.close();
		}
	}
		return guidelinesDetails;

	}

	/**
	 * Adds a guidelines entry
	 */
	
	public boolean addGuidelinesEntry(GuidelinesDoc guidelinesDoc)throws Exception {
		Session session = null;
		Transaction transaction = null;
		try {
			//session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			session.save(guidelinesDoc);
			transaction.commit();
			return true;
		}catch(DataException e){
			if(transaction != null){
				transaction.rollback();
				log.error("Exception occured in adding guidelinesentry in IMPL :"+e);
				throw  new ApplicationException(e);
		}
		}
		catch (Exception e1) {	
			if(transaction != null){
				transaction.rollback();
			}
			log.error("Exception occured in adding guidelinesentry in IMPL :",e1);
			throw  new BusinessException(e1);
		} finally {
		if (session != null) {
			session.flush();
			session.close();
		}
		}
		return false;
	}
	
	/**
	 * Deletes a guidelines entry
	 */
	
	public boolean deleteGuidelinesEntry(int id, String userId)throws Exception{
		Session session = null;
		Transaction transaction = null;
		try {
			//session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			GuidelinesDoc guidelinesDoc = (GuidelinesDoc) session.get(GuidelinesDoc.class, id);
			guidelinesDoc.setIsActive(false);
			guidelinesDoc.setModifiedBy(userId);
			guidelinesDoc.setLastModifiedDate(new Date());
			session.update(guidelinesDoc);
			transaction.commit();
			return true;
		} catch (Exception e) {		
			if(transaction != null){
				transaction.rollback();
			}
			log.error("Exception occured in  deleting guidelinesentry in IMPL :",e);
			throw  new ApplicationException(e);
		} finally {
		if (session != null) {
			session.flush();
			session.close();
		}
		}
	}
	
	/**
	 * Check for the duplicate record if exists for the course and year
	 */
	
	public GuidelinesDoc isGuidelinesExistCourseYear(int courseId, int year) throws Exception
	{
		Session session = null;
		try {
		//session=InitSessionFactory.getInstance().openSession();
		session = HibernateUtil.getSession();
		GuidelinesDoc guidelinesDoc=(GuidelinesDoc) session.createQuery("from GuidelinesDoc doc where doc.course.id = '" + courseId +" ' and doc.year = ' " + year + " ' ").uniqueResult();
		return guidelinesDoc;
		}
		catch(Exception e){
			log.error("Unable to check courseId and year for duplicate in GuidelinesImpl .",e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
			log.info("End of isGuidelinesExistCourseYear of GuidelinesEntryTransactionImpl");
		}	
	}
	
	/**
	 * Updates a record passed updated values from UI.
	 */
	
	public boolean updateGuidelinesEntry(GuidelinesDoc guidelinesDoc) throws Exception
	{
		Session session = null;
		Transaction transaction = null;
		try {
			//session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			session.merge(guidelinesDoc);
			transaction.commit();
			return true;
		} catch (Exception e) {	
			if(transaction != null){
				transaction.rollback();
			}
			log.error("Exception occured in updated guidelinesentry in IMPL :",e);
			throw  new ApplicationException(e);
		
		} finally {
		if (session != null) {
			session.flush();
			session.close();
		}
		}
	}
	
	/**
	 * Download a file based on id
	 */
	

	public GuidelinesDoc getGuidelinesEntryonId(int id) throws Exception {
		Session session = null;
		try {
			//session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			List<GuidelinesDoc> glist = session.createQuery("from GuidelinesDoc g where g.id=?").setInteger(0,id).list();
			GuidelinesDoc doc=null;
			if(glist!=null && !glist.isEmpty()){
				doc=glist.get(0);
			}
			return doc;
		} catch (Exception e) {
			log.error("Error occured in getGuidelinesEntryonId of GuidelinesEntryTransactionImpl",e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}

	}
	
	/**
	 * Get the record based on the ID
	 */
	
	public GuidelinesDoc getFileonId(int id) throws Exception {
		Session session = null;
		try {
			//session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			GuidelinesDoc guidelinesDoc = (GuidelinesDoc) session.createQuery("from GuidelinesDoc g where g.id=?").setInteger(0,id).uniqueResult();
			if(guidelinesDoc!=null){
				return guidelinesDoc;
			}
		} catch (Exception e) {
		log.error("Error getting file on Id in Impl" ,e);
		throw new ApplicationException(e);
		} finally {
			if( session != null){
				session.flush();
				//session.close();
			}
		}
		return null;
	}
	/**
	 * Used in reactivating GuidelinesEntry based on courseId and year
	 */
	public boolean reActivateGuidelinesEntry(int courseId, int year, String userId)throws Exception
	{
		Session session = null;
		Transaction transaction = null;
			try {
				//SessionFactory sessionFactory = InitSessionFactory.getInstance();
				//session = sessionFactory.openSession();
				session = HibernateUtil.getSession();
				Query query = session.createQuery("from GuidelinesDoc doc where doc.course.id = :courseId and doc.year = :year");
				query.setInteger("courseId",courseId);
				query.setInteger("year",year);
				GuidelinesDoc doc = (GuidelinesDoc) query.uniqueResult();
				transaction = session.beginTransaction();
				doc.setIsActive(true);
				doc.setLastModifiedDate(new Date());
				doc.setModifiedBy(userId);
				session.update(doc);
				transaction.commit();
				return true;
				} catch (Exception e) {
				if(transaction != null){
					transaction.rollback();
				}
				log.error("Exception occured in reactivating of GuidelinesEntry in IMPL :",e);
				throw new ApplicationException(e);
				} finally {
					if (session != null) {
						session.flush();
						session.close();
					}
					log.info("End of reActivateGuidelinesEntry of GuidelinesEntryTransactionImpl");
				}
		}
}