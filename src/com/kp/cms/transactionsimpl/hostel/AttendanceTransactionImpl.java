package com.kp.cms.transactionsimpl.hostel;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.HlAttendance;
import com.kp.cms.bo.admin.HlGroupStudent;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.transactions.hostel.IAttendanceTransactions;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.HibernateUtil;

public class AttendanceTransactionImpl implements IAttendanceTransactions{
	public static Log log = LogFactory.getLog(AttendanceTransactionImpl.class);
	public static volatile AttendanceTransactionImpl attendanceTransactionImpl;
	
	public static AttendanceTransactionImpl getInstance(){
		if(attendanceTransactionImpl == null){
			attendanceTransactionImpl = new AttendanceTransactionImpl();
			return attendanceTransactionImpl;
		}
		return attendanceTransactionImpl;
	}

	/**
	 * getting student details based on group id
	 */
	public List<HlGroupStudent> getStudentDeatilsByGroupId(int groupId) throws Exception{
		log.debug("inside getStudentDeatilsByGroupId");
		Session session = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from HlGroupStudent h where h.isActive = 1 and h.hlGroup.id =" + "'" + groupId + "'" + " and h.hlApplicationForm.hlStatus.id = 2");
			List<HlGroupStudent> list = query.list();
			session.flush();
			//session.close();
			//sessionFactory.close();
			log.debug("leaving getStudentDeatilsByGroupId");
			return list;
		 } catch (Exception e) {
			 log.error("Error in getStudentDeatilsByGroupId...",e);
			 session.flush();
			 session.close();
			 throw  new ApplicationException(e);
		 }
	}
	
	 /**
	  * saving to table
	  */
	public boolean addAttendance(List<HlAttendance> attBOList) throws Exception {
		log.debug("inside addAttendance");
		Session session = null;
		Transaction transaction = null;
		HlAttendance hlAttendance;
		try {
			//session = HibernateUtil.getSessionFactory().openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			Iterator<HlAttendance> iterator = attBOList.iterator();
			int count = 0;
			while(iterator.hasNext()){
				hlAttendance = iterator.next();
				session.save(hlAttendance);
				if(++count % 20 == 0){
					session.flush();
					session.clear();
				}
			}
			
			transaction.commit();
			session.flush();
			session.close();;
			log.debug("leaving addAttendance");
			return true;
		} catch (ConstraintViolationException e) {
			transaction.rollback();
			log.error("Error in addAttendance impl...", e);
			throw new BusinessException(e);
		} catch (Exception e) {
			transaction.rollback();
			log.error("Error in addAttendance impl...", e);
			throw new ApplicationException(e);
		}
	}
	/**
	 * duplication checking
	 * @param attDate
	 * @return
	 * @throws Exception
	 */
	public Boolean isAttendanceEntryDuplicated(String attDate, String groupId, String hostelId) throws Exception {
		log.debug("inside isAttendanceEntryDuplicated");
		Session session = null;
		Boolean result = false;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			StringBuffer sqlString = new StringBuffer("from HlAttendance h where h.isActive = 1 and h.attendanceDate =:date" +
					" and h.hlGroupStudent.hlGroup.id = :groupId and h.hlHostel.id = :hostelId");
			Query query = session.createQuery(sqlString.toString());
			query.setDate("date", CommonUtil.ConvertStringToDate(attDate));
			query.setInteger("groupId", Integer.parseInt(groupId));
			query.setInteger("hostelId", Integer.parseInt(hostelId));
			List<HlAttendance> hlAttList = query.list(); 
			
			if(hlAttList!= null && hlAttList.size() > 0){
				result = true;
			}
			session.flush();
			//session.close();;
			//sessionFactory.close();
		} catch (Exception e) {
			log.error("Error during duplcation checking..." , e);
			session.flush();
			//session.close();;
			throw new ApplicationException(e);
		}
		log.debug("leaving isAttendanceEntryDuplicated");
		return result;
	}

		
	
}
