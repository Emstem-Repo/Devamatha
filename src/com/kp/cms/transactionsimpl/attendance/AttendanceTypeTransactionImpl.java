package com.kp.cms.transactionsimpl.attendance;


import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.StaleStateException;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.AttendanceType;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.attendance.AttendanceTypeForm;
import com.kp.cms.transactions.attandance.IAttendanceTypeTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class AttendanceTypeTransactionImpl implements IAttendanceTypeTransaction{
	
	private static final Log log = LogFactory.getLog(AttendanceTypeTransactionImpl.class);
	
	public static volatile AttendanceTypeTransactionImpl attendanceTypeTransactionImpl = null;
	
	//Returns single instance when when called(Singleton implementation)
	public static AttendanceTypeTransactionImpl getInstance() {
		if (attendanceTypeTransactionImpl == null) {
			attendanceTypeTransactionImpl = new AttendanceTypeTransactionImpl();
			return attendanceTypeTransactionImpl;
		}
		return attendanceTypeTransactionImpl;
	}

	/**
	 * Used while inserting the attendanceType
	 */
	public boolean saveAttendanceType(AttendanceType attendanceType) throws Exception{
		log.info("Inside of saveAttendanceType of AttendanceTypeTransactionImpl");
		Transaction tx=null;
		Session session = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			
			tx = session.beginTransaction();
			session.save(attendanceType);
			tx.commit();
			session.close();
			//sessionFactory.close();
			log.info("End of saveAttendanceType of AttendanceTypeTransactionImpl");
			return true;
		} catch (Exception e) {
			if(tx!=null){
				tx.rollback();
			}
			log.error("Error occured saveAttendanceType of AttendanceTypeTransactionImpl");
			throw new ApplicationException(e);
		}
	}
	
	/**
	 * Used in viewing all the attendance Type
	 */
	public List<AttendanceType>getAttendanceTypesAll()throws Exception{
		log.info("Inside of getAttendanceTypesAll of AttendanceTypeTransactionImpl");
		Session session = null;
		List<AttendanceType> attendanceTypeList;
		try {
			//session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			attendanceTypeList = session.createQuery("from AttendanceType a where a.isActive = 1 order by a.id").list();
		} catch (Exception e) {
			log.error("Error occured in getAttendanceTypesAll of AttendanceTypeTransactionImpl");
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		log.info("End of getAttendanceTypesAll of AttendanceTypeTransactionImpl");
		return attendanceTypeList;
	}

	public List<AttendanceType> getAttendanceType() throws Exception {
		log.info("inside getAttendanceType");
		Session session = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from AttendanceType a where isActive=1");
			List<AttendanceType> list = query.list();
			session.flush();
			//session.close();
			//sessionFactory.close();
			log.info("leaving getAttendanceType");
			return list;
		 } catch (Exception e) {
			 log.error("Error during getting AttendanceType..."+e);
			 session.flush();
			 //session.close();
			 throw  new ApplicationException(e);
		 }
	}
	
	/**
	 * Used while deleting a particular attendanceType
	 */
	public boolean deleteAttendanceType(int id,Boolean activate,AttendanceTypeForm attendanceTypeForm)throws Exception{
		log.info("Inside of deleteAttendanceType of AttendanceTypeTransactionImpl");
//		AttendanceType attendanceType;
		Transaction tx=null;
		Session session = null;
		try {
//			attendanceType = new AttendanceType();
//			attendanceType.setId(id);
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			AttendanceType attendanceType=(AttendanceType)session.get(AttendanceType.class, id);
			attendanceType.setIsActive(activate);
			attendanceType.setLastModifiedDate(new Date());
			attendanceType.setModifiedBy(attendanceTypeForm.getUserId());
			session.update(attendanceType);
//			session.delete(attendanceType);
			tx.commit();
			session.flush();
			//session.close();
			//sessionFactory.close();
			log.info("End of deleteAttendanceType of AttendanceTypeTransactionImpl");
			return true;
		} 
		catch (StaleStateException e) {
				if(tx != null){
					tx.rollback();
					session.flush();
					//session.close();
				}
			log.error("Unable to delete in deleteAttendanceType---");
			throw  new ApplicationException(e);
			}
			catch(Exception e){
				if(tx != null){
					tx.rollback();
					session.flush();
					//session.close();
				}
			log.error("Unable to delete in deleteAttendanceType---");			
			throw  new BusinessException(e);
			}
		}


	/**
	 * 	Used in duplicate checking if the default value is true
	 */
	
	public AttendanceType getAttendanceTypeOnDefault(boolean defaultValue)throws Exception {
		log.info("Inside of getAttendanceTypeOnDefault of AttendanceTypeTransactionImpl");
		Session session = null;
		AttendanceType attendanceType;
		try {
			//session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			attendanceType = (AttendanceType) session.createQuery("from AttendanceType a where a.isActive = 1 and a.isDefault =" + defaultValue).uniqueResult();
			if (attendanceType != null) {
				return attendanceType;
			}
		} catch (Exception e) {
			log.error("Error in getAttendanceTypeOnDefault of AttendanceTypeTransactionImpl");
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		log.info("End of getAttendanceTypeOnDefault of AttendanceTypeTransactionImpl");
		return null;
	}

	/**
	 * Used while editing
	 * Gets the record based on the Id from the DB
	 */
	public AttendanceType getDetailsById(int id) throws Exception{
		log.info("Inside of getDetailsById of AttendanceTypeTransactionImpl");
		Session session = null;
		AttendanceType attendanceType;
		try {
			//session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			attendanceType = (AttendanceType) session.createQuery("from AttendanceType a where a.isActive = 1 and a.id =" + id).uniqueResult();
			if (attendanceType != null) {
				return attendanceType;
			}
		} catch (Exception e) {
			log.error("Error in getDetailsById of AttendanceTypeTransactionImpl");
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		log.info("End of getDetailsById of AttendanceTypeTransactionImpl");
		return null;
	}

	/**
	 * Used while updating an attendanceType
	 */
	
	public boolean updateAttendanceType(AttendanceType attendanceType)
			throws Exception {
		log.info("Inside of updateAttendanceType of AttendanceTypeTransactionImpl");		
		Transaction tx=null;
		Session session = null;
		SessionFactory sessionFactory = null;
		try {
			//sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			session.update(attendanceType);
			tx.commit();
			log.info("End of updateAttendanceType of AttendanceTypeTransactionImpl");
			return true;
		} catch (Exception e) {
			if(tx!=null){
				tx.rollback();
			}
			log.error("Error occured updateAttendanceType of AttendanceTypeTransactionImpl");
			throw new ApplicationException(e);
		}
		finally {
			if (session != null) {
				//sessionFactory.close();
				session.flush();
				session.close();
			}
		}
	}
	/**
	 * Used to check for duplicate on attendanceTypeName while inserting and updating
	 */
	public AttendanceType getAttendanceTypeDetailsonAttendanceType(String name)throws Exception{
		log.info("Inside of getAttendanceTypeDetailsonAttendanceType of AttendanceTypeTransactionImpl");
		Session session = null;
		AttendanceType attendanceType;
		try {
			//session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			attendanceType = (AttendanceType) session.createQuery("from AttendanceType a where a.isActive = 1 and a.name =?").setString(0, name).uniqueResult();
			if (attendanceType != null) {
				return attendanceType;
			}
		} catch (Exception e) {
			log.error("Error in getAttendanceTypeDetailsonAttendanceType of AttendanceTypeTransactionImpl");
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		log.info("End of getAttendanceTypeDetailsonAttendanceType of AttendanceTypeTransactionImpl");
		return null;
	}
}
