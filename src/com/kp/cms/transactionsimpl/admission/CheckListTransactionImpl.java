package com.kp.cms.transactionsimpl.admission;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.DocChecklist;
import com.kp.cms.bo.admin.DocType;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.transactions.admission.ICheckListTransaction;
import com.kp.cms.utilities.HibernateUtil;

/**
 * 
 * @author microhard
 *  Transaction class for Checklist entry.
 */
public class CheckListTransactionImpl implements ICheckListTransaction {
	
	private static Log log = LogFactory.getLog(CheckListTransactionImpl.class);
	
	/**
	 *  Checks for duplicate based on courseId and year.
	 */
	public List<DocChecklist> getCheckDuplicate(int courseId, int year) throws Exception{
		List<DocChecklist> list=null;
		Session session=null;
		Transaction transaction = null;
		try {
			//session =InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			list=session.createQuery("from DocChecklist d where d.course.id =  ' "+courseId+" '  and d.year = ' "+year+" ' ").list();
			transaction.commit();
//			session.flush();
//			session.close();
		}catch(Exception e){
			log.error("Error while getting interview types by course..."+e);
			throw  new ApplicationException(e);
		}finally{
			if (session != null){
				session.flush();
				//session.close();
			}
		}
		return list;
	}

	@Override
	/**
	 *  adds the checklist to database.
	 */
	public boolean addCheckList(DocChecklist checklist) throws Exception{
		boolean isAdd=false;
		Session session=null;
		Transaction transaction = null;
		try {
			//session =InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			session.save(checklist);
			transaction.commit();
			isAdd=true;
		} catch (ConstraintViolationException e) {
			transaction.rollback();
             log.error("Error during saving complete application data..."+e);
             throw new BusinessException(e);
			}
			finally{
			if (session != null){
				session.flush();
				session.close();
			}
		}
		return isAdd;
	}
	
	/**
	 * update the checklist.
	 * 
	 */
	public boolean updateCheckList(DocChecklist checklist) throws Exception{
		boolean isAdd=false;
		Session session=null;
		Transaction transaction = null;
		try {
			//session =InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			session.saveOrUpdate(checklist);
			transaction.commit();
			isAdd=true;
		} catch (ConstraintViolationException e) {
			transaction.rollback();
             log.error("Error during saving complete application data..."+e);
             throw new BusinessException(e);
			}
			finally{
			if (session != null){
				session.flush();
				session.close();
			}
		}
		return isAdd;
	}

	/**
	 *  Loads all the checklist.
	 */
	@Override
	public List<DocChecklist> getCheckList(int year) throws Exception{
		Session session=null;
		Transaction transaction = null;
		List<DocChecklist> tplist=null; 
		try {
			//session =InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
//			transaction = session.beginTransaction();
			Query q = session.createQuery("from DocChecklist d where d.isActive = :isactive and  d.docType.isActive = 1 and d.year=:academicYear " +
					" order by  d.program.programType.name,d.program.name,d.course.name");
			
			q.setBoolean("isactive",true);
			q.setInteger("academicYear", year);
			tplist=q.list();
			//session.flush();
//			session.close();
//			transaction.commit();
		} catch (Exception e) {
			log.error("Error while getting interview types by course..."+e);
			throw  new ApplicationException(e);
		}finally{
			if (session != null){
//				session.flush();
				//session.close();
			}
		}
		return tplist;
	}

	/**
	 * This loads the documents from database. useful while assigning checklist documents.
	 */
	public List<DocType> getDocuments() throws Exception{
		Session session=null;
		Transaction transaction = null;
		List<DocType> list = null;
		try {
//			session =InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
//			transaction = session.beginTransaction();
//			transaction.begin();
			list = session.createQuery("from DocType where isActive = 1").list();
//			transaction.commit();
//			session.flush();
//			session.close();
		} catch (Exception e) {
			log.error("Error while getting interview types by course..."+e);
			throw  new ApplicationException(e);
		}finally{
			if (session != null){
				//session.flush();
				//session.close();
			}
		}
		return list;
	}

	/**
	 * Will load the docchecklist based on particular id.
	 */
	public List<DocChecklist> getToFromId(int id)throws Exception{
		Session session=null;
		Transaction transaction = null;
		List<DocChecklist> list=null;
		try {
			//session =InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			list=session.createQuery("from DocChecklist d where d.id=?").setInteger(0,id).list();
			transaction.commit();
			}catch (Exception e) {
				log.error("Error while getting interview types by course..."+e);
				throw  new ApplicationException(e);
			}finally{
				if(session!=null){
				session.flush();
				//session.close();
			}
		}
		return list;
	}
	
	/**
	 * will load the checklist for particular course and year.
	 */
	public List<DocChecklist>  getChecklist(String courseId, String year) throws Exception{
		List<DocChecklist> list=null;
		Session session=null;
		Transaction transaction = null;
		try {
			//session =InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			Query query =session.createQuery("from DocChecklist d where  d.docType.isActive = 1 and d.course.id = :courseId  and d.year = :year and d.isActive = :isActive");
			query.setString("courseId", courseId);
			query.setString("year", year);
			query.setBoolean("isActive",true);
			list = query.list();
			transaction.commit();
		}catch(Exception e){
			log.error("Error while getting interview types by course..."+e);
			throw  new ApplicationException(e);
		}finally{
			if (session != null){
				//session.flush();
				//session.close();
			}
		}
		return list;
	}
	
	/**
	 * will edit the particular checklist.
	 */
	public boolean editCheckList(DocChecklist checklist) throws Exception{
		boolean isAdd=false;
		Session session=null;
		Transaction transaction = null;
		try {
			//session =InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			if(checklist!=null)
			session.update(checklist);
			
			transaction.commit();
			isAdd=true;
		} catch (Exception e) {
			log.error("Error while getting interview types by course..."+e);
			throw  new ApplicationException(e);
		}finally{
			if (session != null){
				session.flush();
				session.close();
			}
		}
		return isAdd;
	}
	
	/**
	 *  delete the checklist based for particular course and year.
	 */
	public boolean deleteCheckList(String courseId,String year) throws Exception{
		boolean isdelete = false;
		Session session = null;
		Transaction transaction = null;
		try {
			//session =InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			
			List<DocChecklist> docCheckList =session.createQuery("from DocChecklist d where d.course.id =  ' "+courseId+" '  and d.year = ' "+year+" ' ").list();
            DocChecklist checklist;
			Iterator<DocChecklist> itr = docCheckList.iterator();
            while(itr.hasNext()) {
            	checklist = itr.next();
            	checklist.setIsActive(false);
            }

			isdelete = true;
			transaction.commit();
			session.flush();
			//session.close();
			return isdelete;
		} catch (Exception e) {
			log.error("Error while getting interview types by course..."+e);
			throw  new ApplicationException(e);
		}
   }
	
	/**
	 * This will reactivate the checklist for course and year.
	 */
	public boolean reActivateCheckList(String courseId,String year) throws Exception{
		boolean isdelete = false;
		Session session = null;
		Transaction transaction = null;
		try {
			//session =InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			
			List<DocChecklist> docCheckList =session.createQuery("from DocChecklist d where d.course.id =  ' "+courseId+" '  and d.year = ' "+year+" ' ").list();
            DocChecklist checklist;
			Iterator<DocChecklist> itr = docCheckList.iterator();
            while(itr.hasNext()) {
            	checklist = itr.next();
            	checklist.setIsActive(true);
            }

			isdelete = true;
			transaction.commit();
			session.flush();
			//session.close();
			return isdelete;
		} catch (Exception e) {
			log.error("Error while getting interview types by course..."+e);
			throw  new ApplicationException(e);
		}
   }
}