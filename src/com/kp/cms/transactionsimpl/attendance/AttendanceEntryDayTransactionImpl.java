package com.kp.cms.transactionsimpl.attendance;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;


import com.kp.cms.bo.admin.AttendanceEntryDay;
import com.kp.cms.bo.admin.Caste;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.attendance.AttendanceEntryDayForm;
import com.kp.cms.transactions.attandance.IAttendanceEntryDayTransaction;
import com.kp.cms.transactionsimpl.admin.CasteTransactionImpl;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;



public class AttendanceEntryDayTransactionImpl implements IAttendanceEntryDayTransaction{
	
	private static final Log log = LogFactory.getLog(AttendanceEntryDayTransactionImpl.class);
	
	public List<AttendanceEntryDay> getAttendanceEntyDays(){
		Session session = null;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			List<AttendanceEntryDay> attList = session.createQuery("from AttendanceEntryDay a where a.isActive=1").list();
			session.flush();
			//session.close();
			return attList;
		} catch (Exception e) {
			if (session != null){
				session.flush();
				//session.close();
			}
			return null;
		}
	}

	public AttendanceEntryDay isDuplicateAttendanceEntryDayDetail(
			AttendanceEntryDayForm attendanceEntryDayForm, String mode) throws ApplicationException {
		Session session=null;
		String query = null;
		AttendanceEntryDay attendanceEntryDay;
		try{
			session=HibernateUtil.getSession();
			if(mode.equalsIgnoreCase("edit")){
				
			   query="from AttendanceEntryDay a where day = :day  and date = :date   and a.id = :id " ;
			}
			
			else{
				query="from AttendanceEntryDay a where day = :day  and date = :date ";
			}
				
			Query que=session.createQuery(query);
			if(mode.equalsIgnoreCase("add")){
				que.setString("day", attendanceEntryDayForm.getDay());					
				que.setDate("date", CommonUtil.ConvertStringToSQLDate(attendanceEntryDayForm.getDate()));				
			}
			
			if(mode.equalsIgnoreCase("edit")){
			  que.setString("id", attendanceEntryDayForm.getId());
			que.setString("day", attendanceEntryDayForm.getDay());					
			que.setDate("date", CommonUtil.ConvertStringToSQLDate(attendanceEntryDayForm.getDate()));
			}
			attendanceEntryDay = (AttendanceEntryDay) que.uniqueResult();
			session.flush();
			session.close();
			//if(attendanceEntryDay!=null)
				return attendanceEntryDay;
			//else
				//return false;
			   
		}catch (Exception e) {
			log.error("Error during duplcation checking..." ,e);
			//session.flush();
			//session.close();
			throw new ApplicationException(e);
		}
		
	
	
	}
	
	
	public boolean addAttendanceEntryDay(AttendanceEntryDay attendanceEntryDay ,String mode)
			throws Exception {
		Session session = null;
		Transaction transaction= null;
		boolean isAdded=false;
		try{
			session=InitSessionFactory.getInstance().openSession();
			transaction=session.beginTransaction();
			transaction.begin();
			if(mode.equalsIgnoreCase("Add")){
				session.save(attendanceEntryDay);
			}else if(mode.equalsIgnoreCase("edit")){
				session.update(attendanceEntryDay);
			}
			transaction.commit();
			session.flush();
			session.close();
			isAdded = true;
		}catch (ConstraintViolationException e) {
			if(transaction!=null)
			     transaction.rollback();
			log.error("Error during saving admitted Through data..." ,e);
			throw new BusinessException(e);
		} catch (Exception e) {
			if(transaction!=null)
			     transaction.rollback();
			log.error("Error during saving admitted Through data..." ,e);
			throw new ApplicationException(e);
		}
		return isAdded;
		
		
	}
	

	public boolean deleteAttendanceEntryDay(int id, boolean activate,
			AttendanceEntryDayForm attendanceEntryDayForm) throws Exception {
		Session session=null;
		Transaction transaction=null;
		boolean isDeleted= false;
		try{
			session=HibernateUtil.getSession();
			transaction=session.beginTransaction();
			transaction.begin();
			AttendanceEntryDay attendanceEntryDay= (AttendanceEntryDay)session.get(AttendanceEntryDay.class, id);
			if(activate){
				attendanceEntryDay.setIsActive(true);
			}else{
				attendanceEntryDay.setIsActive(false);
			}
			attendanceEntryDay.setLastModifiedDate(new Date());
			attendanceEntryDay.setModifiedBy(attendanceEntryDayForm.getUserId());
			session.update(attendanceEntryDay);
			transaction.commit();
			session.flush();
			//session.close();
			 isDeleted = true;
		}catch (ConstraintViolationException e) {
			if(transaction!=null)
			     transaction.rollback();
			log.error("Error during deleting Department data..." ,e);
			throw new BusinessException(e);
		} catch (Exception e) {
			if(transaction!=null)
			     transaction.rollback();
			log.error("Error during deleting Department data..." ,e);
			throw new ApplicationException(e);
		}
		return isDeleted;
	}
	
	public boolean updateAttendanceEntryDay(AttendanceEntryDay attendanceEntryDay) throws Exception{
		Session session = null;
		Transaction transaction = null;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			session.merge(attendanceEntryDay);
			transaction.commit();
			session.flush();
			session.close();
			return true;
		} catch (Exception e) {
			if ( transaction != null){
				transaction.rollback();
			}
			if (session != null){
				session.flush();
				session.close();
			}
			return false;
		}
	}
	
	public boolean reActivateAttendanceEntryDay(AttendanceEntryDay attendanceEntryDay,String userId) throws Exception{
		Session session = null;
		Transaction transaction = null;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			attendanceEntryDay.setModifiedBy(userId);
			attendanceEntryDay.setLastModifiedDate(new Date());
			attendanceEntryDay.setIsActive(true);
			session.update(attendanceEntryDay);
			transaction.commit();
			session.flush();
			session.close();
			return true;
		} catch (Exception e) {
			if ( transaction != null){
				transaction.rollback();
			}
			if (session != null){
				session.flush();
				session.close();
			}
			return false;
		}
	}
	public AttendanceEntryDay getAttendanceEntryDayDetails(String id) throws Exception {
		Session session=null;
		AttendanceEntryDay attendanceEntryDay=null;
		try{
			session=HibernateUtil.getSession();
			String str="from AttendanceEntryDay a where a.id="+id;
			Query query=session.createQuery(str);
			attendanceEntryDay=(AttendanceEntryDay)query.uniqueResult();
			
		}catch(Exception e){
			log.error("Error during getting TeacherDepartment by id..." , e);
			//session.flush();
			//session.close();
			
		}
		return attendanceEntryDay;
		
	
	}

	@Override
	public AttendanceEntryDay getLastAttendanceEntryDayDetails() {
		Session session=null;
		AttendanceEntryDay attendanceEntryDay=null;
		try{
			session=HibernateUtil.getSession();
			String str="from AttendanceEntryDay a where a.isActive=1 order by a.date desc";
			Query query=session.createQuery(str);
			query.setMaxResults(1);
			attendanceEntryDay=(AttendanceEntryDay)query.uniqueResult();
			
		}catch(Exception e){
			log.error("Error during getting TeacherDepartment by id..." , e);
			
		}
		return attendanceEntryDay;
		
	
	}

	@Override
	public void addAttendanceEntryDayByRemainder(AttendanceEntryDay attendanceEntryDay) {

		Session session = null;
		Transaction transaction= null;
		try{
			session=InitSessionFactory.getInstance().openSession();
			transaction=session.beginTransaction();
			transaction.begin();
				session.save(attendanceEntryDay);
			transaction.commit();
			session.flush();
			session.close();
		}catch (ConstraintViolationException e) {
			if(transaction!=null)
			     transaction.rollback();
			log.error("Error during saving admitted Through data..." ,e);
		} catch (Exception e) {
			if(transaction!=null)
			     transaction.rollback();
			log.error("Error during saving admitted Through data..." ,e);
		}
		
	}

}
