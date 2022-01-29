package com.kp.cms.transactionsimpl.attendance;

import java.sql.Date;
import java.util.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.FlushMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.Attendance;
import com.kp.cms.bo.admin.AttendanceInstructor;
import com.kp.cms.bo.admin.AttendanceSlipNumber;
import com.kp.cms.bo.admin.AttendanceTimeRestrictionClasswise;
import com.kp.cms.bo.admin.Batch;
import com.kp.cms.bo.admin.BatchStudent;
import com.kp.cms.bo.admin.ClassSchemewise;
import com.kp.cms.bo.admin.MobileSMSCriteriaBO;
import com.kp.cms.bo.admin.Period;
import com.kp.cms.bo.admin.StuCocurrLeave;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.StudentLeave;
import com.kp.cms.bo.admin.Subject;
import com.kp.cms.bo.admin.TTUsers;
import com.kp.cms.bo.admin.TeacherClassSubject;
import com.kp.cms.bo.admin.Users;
import com.kp.cms.bo.exam.HolidayBO;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.forms.attendance.AttendanceEntryForm;
import com.kp.cms.forms.attendance.MobileSmsCriteriaForm;
import com.kp.cms.forms.attendance.NewAttendanceSmsForm;
import com.kp.cms.handlers.exam.ExamMarksEntryHandler;
import com.kp.cms.to.attendance.TeacherClassEntryTo;
import com.kp.cms.transactions.attandance.IAttendanceEntryTransaction;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.HibernateUtil;

/**
 * This Impl class will interact with Attendance and associated classes of it. 
 *
 */
public class AttendanceEntryTransactionImpl implements IAttendanceEntryTransaction{
	private static volatile AttendanceEntryTransactionImpl attendanceEntryTransactionImpl= null;
	private static final Log log = LogFactory.getLog(AttendanceEntryTransactionImpl.class);
	
	private static final String FROM_DATEFORMAT = "dd/MM/yyyy";
	private static final String SQL_DATEFORMAT = "dd-MMM-yyyy";
	/**
	 * This method returns the single instance of ClassEntryTxn impl.
	 * @return
	 */
	public static AttendanceEntryTransactionImpl getInstance() {
	      if(attendanceEntryTransactionImpl == null) {
	    	  attendanceEntryTransactionImpl = new AttendanceEntryTransactionImpl();
	    	  return attendanceEntryTransactionImpl;
	      }
	      return attendanceEntryTransactionImpl;
	}
	
	public List<BatchStudent> getStudentByBatch(int batchId) throws Exception {
	log.debug("Txn Impl : Entering getStudentByBatch ");
	Session session = null;
	try {
		 //SessionFactory sessionFactory = InitSessionFactory.getInstance();
		 session = HibernateUtil.getSession();
		 // Query query = session.createQuery("from BatchStudent b where b.batch.id = (:batchId) and b.student.isAdmitted = 1 and b.isActive = 1 and (b.student.isHide = 0 or b.student.isHide is null)");
		 Query query = session.createQuery("from BatchStudent b where b.batch.id = (:batchId) and b.student.isAdmitted = 1 and b.isActive = 1 and b.student.tcNo is null and (b.student.isHide = 0 or b.student.isHide is null)");
		 query.setInteger("batchId", batchId);
		
		 List<BatchStudent> list = query.list();
		 //session.close();
		 //sessionFactory.close();
		 log.debug("Txn Impl : Leaving getStudentByBatch with success");
		 return list;
	 } catch (Exception e) {
		 log.debug("Txn Impl : Leaving getStudentByBatch with Exception");
		 throw e;
	 }
	}
	
	public List<Student> getStudentByClass(Set<Integer> classIds) throws Exception {
		log.debug("Txn Impl : Entering getStudentByClass ");
		Session session = null;
		try {
			 //SessionFactory sessionFactory = InitSessionFactory.getInstance();
			 session = HibernateUtil.getSession();
			Query query = session.createQuery("from Student s where s.classSchemewise.id in (:classesIds) and isAdmitted = 1 and s.isActive = 1  and s.admAppln.isCancelled=0 and s.tcNo is null and (s.isHide =0 or s.isHide is null)");	
			query.setParameterList("classesIds", classIds);
			
			 List<Student> list = query.list();
			 //session.close();
			 //sessionFactory.close();
			 log.debug("Txn Impl : Leaving getStudentByClass with success");
			 return list;
		 } catch (Exception e) {
			 log.debug("Txn Impl : Leaving getStudentByClass with Exception");
			 throw e;
		 }
		}
	
	
	/**
	 * This method add the Fee and FeeAccountAssignment.
	 * code changed by mehaboob List of Attendance
	 */
	public boolean addAttendance(List<Attendance> attendanceList,AttendanceEntryForm attendanceEntryForm) throws ConstraintViolationException,Exception{
		log.debug("Txn Impl : Entering addAttendance ");
		Session session = null; 
		Transaction tx = null;
		boolean isAdded=false;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			 session = HibernateUtil.getSession();
			 tx = session.beginTransaction();
			 
			 tx.begin();
			 if(!attendanceList.isEmpty()){
				 for (Attendance attendance : attendanceList) {
					 int maxSlipNumber=0;
					 Query	query = session.createQuery("select max(a.slipNo) from Attendance a where a.isCanceled=0 and a.attendanceDate='"+CommonUtil.ConvertStringToSQLDate(attendanceEntryForm.getAttendancedate())+"'");
					 Object obj=query.uniqueResult();
					 if(obj!=null){
						 maxSlipNumber=Integer.parseInt(obj.toString());
						 maxSlipNumber=maxSlipNumber+1;
					 }else{
						 maxSlipNumber=1;
					 }
					 attendance.setSlipNo(Integer.toString(maxSlipNumber));
					 attendanceEntryForm.setSlipNo(Integer.toString(maxSlipNumber));
					 session.save(attendance);
				 }
				 tx.commit();
				 isAdded=true;
			 }
			
			 session.close();
			 log.debug("Txn Impl : Leaving addAttendance with success");
	    	 return isAdded;
		 } catch (ConstraintViolationException e) {
			 tx.rollback();
			 //session.close();
			 log.debug("Txn Impl : Leaving addAttendance with Exception"+e.getMessage());
			 throw e;				 
		 } catch (Exception e) {
			 tx.rollback();
			 //session.close();
			 log.debug("Txn Impl : Leaving addAttendance with Exception"+e.getMessage());
			 throw e;				 
		 } 
			 
	}
	
	/**
	 * This method add the Fee and FeeAccountAssignment.
	 */
	public boolean updateAttendance(Attendance attendance) throws ConstraintViolationException,Exception{
		log.debug("Txn Impl : Entering updateAttendance ");
		Session session = null; 
		Transaction tx = null;
		try {
			 //SessionFactory sessionFactory = InitSessionFactory.getInstance();
			 session = HibernateUtil.getSession();
			 tx = session.beginTransaction();
			 
			 tx.begin();
			 session.update(attendance);
			 tx.commit();
			 session.close();
			 log.debug("Txn Impl : Leaving updateAttendance with success");
	    	 return true;
		 } catch (ConstraintViolationException e) {
			 tx.rollback();
			 session.close();
			 log.debug("Txn Impl : Leaving updateAttendance with Exception");
			 throw e;				 
		 } catch (Exception e) {
			 tx.rollback();
			 session.close();
			 log.debug("Txn Impl : Leaving updateAttendance with Exception");
			 throw e;				 
		 } 
			 
	}
	
	public Attendance getAttendanceStudents(AttendanceEntryForm attendanceEntryForm,int id)throws Exception {
		log.debug("Txn Impl : Entering getAttendanceStudents ");
		Session session = null;
		try {
			 //SessionFactory sessionFactory = InitSessionFactory.getInstance();
			 session = HibernateUtil.getSession();
			 
			 Attendance attendance = (Attendance)session.get(Attendance.class,id);
			 //session.close();
			 //sessionFactory.close();
			 log.debug("Txn Impl : Leaving getAttendanceStudents with success");
			 return attendance;
		 } catch (Exception e) {
			 log.debug("Txn Impl : Leaving getAttendanceStudents with Exception");
			 throw e;
		 }
		}
	
	public int getAttendanceBySingleEntries(AttendanceEntryForm attendanceEntryForm)throws Exception {
		log.debug("Txn Impl : Entering getAttendanceBySingleEntries ");
		Session session = null;
		try {
			 //SessionFactory sessionFactory = InitSessionFactory.getInstance();
			 session = HibernateUtil.getSession();
			 int classId=0;
			 int teacherId=0;
			 int periodId=0;
			 			 
			if(attendanceEntryForm.getClasses() != null && attendanceEntryForm.getClasses().length != 0) {
				classId = Integer.parseInt(attendanceEntryForm.getClasses()[0]);
			}
			if(attendanceEntryForm.getPeriods()!= null && attendanceEntryForm.getPeriods().length !=0) {
				periodId = Integer.parseInt(attendanceEntryForm.getPeriods()[0]);
			}
			if(attendanceEntryForm.getTeachers() != null && attendanceEntryForm.getTeachers().length != 0) {
				teacherId = Integer.parseInt(attendanceEntryForm.getTeachers()[0]);
			}
		 
		    Date date = CommonUtil.ConvertStringToSQLDate(attendanceEntryForm.getAttendancedate());
			int attendanceTypeId = Integer.parseInt(attendanceEntryForm.getAttendanceTypeId());
			String query="select a.id ";
			query+=" from Attendance a ";
			if(attendanceEntryForm.getClasses() != null && attendanceEntryForm.getClasses().length != 0) {
				query+=" join a.attendanceClasses ac with ac.classSchemewise.id ="+classId;
			}
			if(attendanceEntryForm.getTeachers()!= null && attendanceEntryForm.getTeachers().length !=0) {
				query+=" join a.attendanceInstructors ai with ai.users.id ="+teacherId;
			}
			if(attendanceEntryForm.getPeriods() != null && attendanceEntryForm.getTeachers().length != 0) {
				query+=" join a.attendancePeriods ap with ap.period.id ="+periodId;
			}
			query+=" and a.attendanceDate = :date";
			if(attendanceEntryForm.getSubjectId()!=null && attendanceEntryForm.getSubjectId().length() != 0) {
				int subId = Integer.parseInt(attendanceEntryForm.getSubjectId());
				query+=" and a.subject.id = "+subId;
			}	
			if(attendanceEntryForm.getBatchId()!=null && attendanceEntryForm.getBatchId().length() != 0) {
				int batchId = Integer.parseInt(attendanceEntryForm.getBatchId());
				query+=" and a.batch.id = "+batchId;
			}
			if(attendanceEntryForm.getActivityId()!=null && attendanceEntryForm.getActivityId().length() != 0) {
				int actId = Integer.parseInt(attendanceEntryForm.getActivityId());
				query+=" and a.activity.id = "+actId;
			}
			query+=" and a.isCanceled = 0"; 
			query+=" and a.isMonthlyAttendance = 0";
			query+=" and ai.users.isActive=1";
			Query sessionQuery = session.createQuery(query);
			sessionQuery.setDate("date", date);
			List<Integer> list = sessionQuery.list();
			int attendanceId = 0;
			if(!list.isEmpty()) {
				attendanceId = list.get(0);
			}

			//session.close();
			//sessionFactory.close();
			log.debug("Txn Impl : Leaving getAttendanceBySingleEntries with success");
			return attendanceId;
		 } catch (Exception e) {
			 log.debug("Txn Impl : Leaving getAttendanceBySingleEntries with Exception");
			 throw e;
		 }
		}
	
	/**
	 * This method will returns the list of Id's of duplicate ids.
	 */
	public List<Object[]> duplicateAttendanceCheck(Set<Integer> classSet,Set<Integer> periodSet,Date date,int batchId,int subjectId) throws Exception {
		log.debug("Txn Impl : Entering duplicateAttendanceCheck ");
		Session session = null;
		List<Object[]> list = new ArrayList<Object[]>();
		try {
			 //SessionFactory sessionFactory = InitSessionFactory.getInstance();
			 session = HibernateUtil.getSession();

			 
			 String sQuery = " select a" +
		 		" from Attendance a" +
		 		" join a.attendanceClasses ac with ac.classSchemewise.id in (:classSet)";
			 if(!periodSet.isEmpty()){
				 sQuery+= " join a.attendancePeriods ap with ap.period.id in (:periodsSet) ";
			 }
			 sQuery += " where a.attendanceDate = :date and a.isCanceled = 0  and a.isMonthlyAttendance = 0";
			 if(subjectId != 0)
				 sQuery+=" and a.subject.id ="+subjectId;

			 
			 
			 String stringQuery = " select a.subject.id,a.batch.id" +
			 		" from Attendance a" +
			 		" join a.attendanceClasses ac with ac.classSchemewise.id in (:classSet)";
			 if(!periodSet.isEmpty()) {
				 stringQuery+= " join a.attendancePeriods ap with ap.period.id in (:periodsSet) ";
			 }
			 stringQuery += " where a.attendanceDate = :date and a.isCanceled = 0  and a.isMonthlyAttendance = 0 and a.subject.id is not null";
			 //raghu 
			 //if(subjectId != 0)
			//	 stringQuery+=" and a.subject.id ="+subjectId;
			 
			 
			 String stringQuery1 = " select a.id,a.batch.id" +
		 		" from Attendance a" +
		 		" join a.attendanceClasses ac with ac.classSchemewise.id in (:classSet)";
			 if(!periodSet.isEmpty()) {
				 stringQuery1+= " join a.attendancePeriods ap with ap.period.id in (:periodsSet) ";
			 }
			 stringQuery1+= " where a.attendanceDate = :date and a.isCanceled = 0 and a.isMonthlyAttendance = 0";
			 stringQuery1+=" and a.batch.id = "+batchId;
			 if(subjectId != 0) {
				 stringQuery1+=" and a.subject.id ="+subjectId;
			 }
			 
			 Query query = null;
			 if(batchId == 0) {
				 query = session.createQuery(stringQuery);
				 query.setParameterList("classSet", classSet);
				 if(!periodSet.isEmpty()) {
					 query.setParameterList("periodsSet",periodSet);
				 }
				 query.setDate("date", date);
				
				 list = query.list();
				 //session.close();
				 //sessionFactory.close();
				 log.debug("Txn Impl : Leaving duplicateAttendanceCheck with success");
				 return list;
				 
			 } else if(batchId != 0) {
				 boolean reCheck = true;
				 query = session.createQuery(sQuery);
				 query.setParameterList("classSet", classSet);
				 if(!periodSet.isEmpty()) {
					 query.setParameterList("periodsSet",periodSet);
				 }
				 query.setDate("date", date);
				
				 List<Attendance> list1 = query.list();
				 if(!list1.isEmpty()) {
					 Object[] obj=new Object[2];
					 
					 Attendance attendance = list1.get(0);
					 if(attendance.getId()!=0)
					 obj[0]=attendance.getId();
					 if(attendance.getBatch()!=null && attendance.getBatch().getId()!=0)
					 obj[1]=attendance.getBatch().getId();
					 list.add(obj);
					 if(attendance.getBatch() == null) {
						 reCheck =false; 
					 }
				 }
				 if(reCheck) {
				 query = session.createQuery(stringQuery1);
				 query.setParameterList("classSet", classSet);
				 if(!periodSet.isEmpty()) {
					 query.setParameterList("periodsSet",periodSet);
				 }
				 query.setDate("date", date);
				
				 list = query.list();
				 //session.close();
				 //sessionFactory.close();
				 log.debug("Txn Impl : Leaving duplicateAttendanceCheck with success");
				 return list;
				 }
			 }
			 
		 } catch (Exception e) {
			 log.debug("Txn Impl : Leaving duplicateAttendanceCheck with Exception");
			 throw e;
		 }
		return list; 
		}
	/*
	 * This method will returns the list of Attendance based on class,date & attendancetype.
	 * (non-Javadoc)
	 * @see com.kp.cms.transactions.attandance.IAttendanceEntryTransaction#getAttendances(java.util.Set, java.sql.Date, int)
	 */
	public List<Attendance> getAttendances(Set<Integer> classSet,Date date,int AttendaceTypeId) throws Exception {
		log.debug("Txn Impl : Entering getAttendances ");
		Session session = null;
		try {
			 //SessionFactory sessionFactory = InitSessionFactory.getInstance();
			 session = HibernateUtil.getSession();

			 Query query = session.createQuery(" select a" +
			 		" from Attendance a" +
			 		" join a.attendanceClasses ac with ac.classSchemewise.id in (:classSet) " +
					" where a.attendanceDate = :date" +
					" and a.attendanceType.id = :AttendaceTypeId" +
					" and a.isCanceled = 0" +
					" and a.isMonthlyAttendance = 0");
						 
			 query.setParameterList("classSet", classSet);
			 query.setDate("date", date);
			 query.setInteger("AttendaceTypeId", AttendaceTypeId);
			
			 List<Attendance> list = query.list();
			 //session.close();
			 //sessionFactory.close();
			 log.debug("Txn Impl : Leaving getAttendances with success");
			 return list;
		 } catch (Exception e) {
			 log.debug("Txn Impl : Leaving getAttendances with Exception");
			 throw e;
		 }
	}
	
	/*
	 * This method will returns the list of Attendance based on class,date & attendancetype.
	 * (non-Javadoc)
	 * @see com.kp.cms.transactions.attandance.IAttendanceEntryTransaction#getAttendances(java.util.Set, java.sql.Date, int)
	 */
	public List<Attendance> getClassAttendances(Set<Integer> classSet,Date date) throws Exception {
		log.debug("Txn Impl : Entering getAttendances ");
		Session session = null;
		try {
			 //SessionFactory sessionFactory = InitSessionFactory.getInstance();
			 session = HibernateUtil.getSession();

			 Query query = session.createQuery(" select a" +
			 		" from Attendance a" +
			 		" join a.attendancePeriods ap join a.attendanceClasses ac with ac.classSchemewise.id in (:classSet) " +
					" where a.attendanceDate = :date" +
					" and a.isCanceled = 0" +
					" and a.isMonthlyAttendance = 0 order by ap.period.periodName" );
						 
			 query.setParameterList("classSet", classSet);
			 query.setDate("date", date);
			
			 List<Attendance> list = query.list();
			 //session.close();
			 //sessionFactory.close();
			 log.debug("Txn Impl : Leaving getAttendances with success");
			 return list;
		 } catch (Exception e) {
			 log.debug("Txn Impl : Leaving getAttendances with Exception");
			 throw e;
		 }
	}
	
	
	/*
	 * This method returns all the attendance.
	 * (non-Javadoc)
	 * @see com.kp.cms.transactions.attandance.IAttendanceEntryTransaction#getAllAttendances()
	 */
	public List<Attendance> getAllAttendances() throws Exception {
		log.debug("Txn Impl : Entering getAllAttendances ");
		Session session = null;
		try {
			 //SessionFactory sessionFactory = InitSessionFactory.getInstance();
			 session = HibernateUtil.getSession();

			 Query query = session.createQuery(" from Attendance a" +
			 								   " where a.isCanceled = 0");
						 
			 List<Attendance> list = query.list();
			 //session.close();
			 //sessionFactory.close();
			 log.debug("Txn Impl : Leaving getAllAttendances with success");
			 return list;
		 } catch (Exception e) {
			 log.debug("Txn Impl : Leaving getAllAttendances with Exception");
			 throw e;
		 }
		}
	
	/*
	 * This method returns all the attendance.
	 * (non-Javadoc)
	 * @see com.kp.cms.transactions.attandance.IAttendanceEntryTransaction#getAllAttendances()
	 */
	public List<Attendance> getAllAttendancesForDate(Date attendanceDate) throws Exception {
		log.debug("Txn Impl : Entering getAllAttendances ");
		Session session = null;
		try {
			 //SessionFactory sessionFactory = InitSessionFactory.getInstance();
			 session = HibernateUtil.getSession();

			 Query query = session.createQuery(" from Attendance a" +
					 						   " where a.attendanceDate = :date" +
			 								   " and a.isCanceled = 0" +
			 								   " and a.isMonthlyAttendance = 0 ");
			 query.setDate("date", attendanceDate);
			 List<Attendance> list = query.list();
			 //session.close();
			 //sessionFactory.close();
			 log.debug("Txn Impl : Leaving getAllAttendances with success");
			 return list;
		 } catch (Exception e) {
			 log.debug("Txn Impl : Leaving getAllAttendances with Exception");
			 throw e;
		 }
		}
	
	/**
	 *  This method makes iscanceled column as true for particular ids.
	 */
	public  void cancelAttendance(Set<Integer> ids) throws Exception {
		log.debug("TXN Impl : Entering cancelAttendance");
		Session session = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Iterator<Integer> itr = ids.iterator();
			int count = 0 ;
			Transaction tx = session.beginTransaction();
			tx.begin();
			while(itr.hasNext()) {
				int id = itr.next();
				Query query = session.createQuery("update Attendance set isCanceled = 1 " +
												 " where id = :id");
				query.setInteger("id", id);
				query.executeUpdate();
				if(++count % 20 == 0){
					session.flush();
					session.clear();
				}
			}	
			tx.commit();
			session.close();
			//sessionFactory.close();
			log.debug("TXN Impl : Leaving cancelAttendance with success");	
		return;
		} catch (Exception e) {
			log.debug("TXN Impl : Leaving cancelAttendance with Exception");
			throw e;
		}
	}
	
	/*
	 * This method lists the Attendance for a particular day,classes & period.
	 * (non-Javadoc)
	 * @see com.kp.cms.transactions.attandance.IAttendanceEntryTransaction#getActivityAttendanceOnDateClassPeriod(int, int, java.sql.Date)
	 */
	public List<Attendance> getActivityAttendanceOnDateClassPeriod(int classId,int periodId,Date date) throws Exception {
		log.debug("Txn Impl : Entering getActivityAttendanceOnDateClassPeriod ");
		Session session = null;
		try {
			 //SessionFactory sessionFactory = InitSessionFactory.getInstance();
			 session = HibernateUtil.getSession();

			 Query query = session.createQuery(" select a" +
			 		" from Attendance a" +
			 		" join a.attendanceClasses ac with ac.classSchemewise.id = :classId " +
			 		" join a.attendancePeriods ap with ap.period.id = :periodId" +
					" where a.attendanceDate = :date" +
					" and a.isActivityAttendance = 1"+
					" and a.isCanceled = 0");
						 
			 query.setInteger("classId", classId);
			 query.setInteger("periodId", periodId);
			 query.setDate("date", date);
			
			 List<Attendance> list = query.list();
			 //session.close();
			 //sessionFactory.close();
			 log.debug("Txn Impl : Leaving duplicateAttendanceCheck with success");
			 return list;
		 } catch (Exception e) {
			 log.debug("Txn Impl : Leaving duplicateAttendanceCheck with Exception");
			 throw e;
		 }
		
		
	}
	
	public List<StuCocurrLeave> getActivityAttendanceOnDateClassPeriod(Set<Integer> classSet,Set<Integer> periodSet,Date date) throws Exception {
		log.debug("Txn Impl : Entering getActivityAttendanceOnDateClassPeriod ");
		List<StuCocurrLeave> studentLeaveTypes;
		Session session = null;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			Query leaveTypeQuery = session
					.createQuery("from StuCocurrLeave s "
							+ " where s.isCocurrLeaveCancelled=0 and  s.startDate <= :fromDate and s.endDate >= :endDate " +
							" and s.classSchemewise.id in (:classId) ");
			leaveTypeQuery.setDate("fromDate", date);
			leaveTypeQuery.setDate("endDate", date);
			leaveTypeQuery.setParameterList("classId", classSet);
//			leaveTypeQuery.setParameterList("periodId", periodSet);

			studentLeaveTypes = leaveTypeQuery.list();
		 } catch (Exception e) {
			 log.debug("Txn Impl : Leaving getActivityAttendanceOnDateClassPeriod with Exception");
			 throw e;
		 }
		return studentLeaveTypes;
	}
	/**
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public List<Period> getPeriodsForClass(int classSchemeWise) throws Exception {
		Session session = null;
		List<Period> periodList;

		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from Period a where isActive=1 and" +
											  " a.classSchemewise.id = :classSchemeWise order by a.periodName");
			query.setInteger("classSchemeWise", classSchemeWise);
			List<Period> list = query.list();
			session.flush();
			//session.close();
			//sessionFactory.close();
			periodList = list;

		} catch (Exception e) {
			log.debug("Error during getting applicaition numbers..." + e);
			session.flush();
			//session.close();
			throw new ApplicationException(e);
		}
		return periodList;
	}

	@Override
	public boolean checkIsStaff(String userId) throws Exception {
		log.debug("Txn Impl : Entering checkIsStaff ");
		boolean isStaff=false;
		Session session = null;
		try {
			 session = HibernateUtil.getSession();
			 Query query = session.createQuery("from Users u where u.isTeachingStaff=1 and u.id=:userId and u.isActive=1");
			 query.setInteger("userId", Integer.parseInt(userId));
			 Users u=(Users)query.uniqueResult();
			 if(u!=null){
				 isStaff=true;
			 }
			 log.debug("Txn Impl : Leaving checkIsStaff with success");
			return isStaff;
		 } catch (Exception e) {
			 log.debug("Txn Impl : Leaving checkIsStaff with Exception");
			 throw e;
		 }
	}

	@Override
	public Map<Integer, String> getClassesByTeacher(String[] teachers,
			String year) throws Exception {
		StringBuilder intType =new StringBuilder();
		if (teachers.length > 0) {
		String [] tempArray = teachers;
		for(int i=0;i<tempArray.length;i++){
			intType.append(tempArray[i]);
			 if(i<(tempArray.length-1)){
				 intType.append(",");
			 }
		}
		}
		try {
			Session session = null;
			session = HibernateUtil.getSession();
			Query query = session.createQuery(
							" select tcs " + 
							" from TeacherClassSubject tcs" +
							" where tcs.teacherId in ("+intType +") and tcs.year="+year);
			Iterator<TeacherClassSubject> tuples = query.list().iterator();
			Map<Integer, String> classMap = new LinkedHashMap<Integer, String>();
			while (tuples.hasNext()){
				TeacherClassSubject tcs = (TeacherClassSubject)tuples.next();
				classMap.put(tcs.getClassId().getId(), tcs.getClassId().getClasses().getName());
			}
			session.flush();
			return classMap;
		} catch (Exception e) {
			log.debug("Exception" + e.getMessage());
		}		
		return new HashMap<Integer, String>();
	}
	
	/* 
	 * checking the slipno is existed or not
	 * (non-Javadoc)
	 * @see com.kp.cms.transactions.attandance.IAttendanceEntryTransaction#checkDuplicateSlipNo(java.lang.String)
	 */
	public AttendanceSlipNumber checkDuplicateSlipNo(int year) throws Exception
	{
		log.debug("Txn Impl : Entering checkDuplicateSlipNo ");
		Session session = null;
		try {
			 session = HibernateUtil.getSession();
			 Query query = session.createQuery("from AttendanceSlipNumber a where a.isActive=1 and a.pcFinancialYear.financialYear =:year and a.pcFinancialYear.isCurrent=1");
			 query.setInteger("year", year);
			 AttendanceSlipNumber attendanceSlipNumberBo=(AttendanceSlipNumber)query.uniqueResult();
			
			log.debug("Txn Impl : Leaving checkDuplicateSlipNo with success");
			 return attendanceSlipNumberBo;
		 	} catch (Exception e) {
			 log.debug("Txn Impl : Leaving checkDuplicateSlipNo with Exception");
			 throw e;
		 	}
	}
	
	@Override
	public String getSubjectNameById(int parseInt) throws Exception {
		log.debug("Txn Impl : Entering getSubjectNameById ");
		Session session = null;
		try {
			 session = HibernateUtil.getSession();
			 Query query = session.createQuery("select b.code from Subject b where b.id = :batchId");
			 query.setInteger("batchId", parseInt);
			 String list =(String) query.uniqueResult();
			 log.debug("Txn Impl : Leaving getSubjectNameById with success");
			 return list;
		 } catch (Exception e) {
			 log.debug("Txn Impl : Leaving getSubjectNameById with Exception");
			 throw e;
		 }
	}
	
	public String getPeriodNamesById(String query1) throws Exception {
		log.debug("Txn Impl : Entering getSubjectNameById ");
		Session session = null;
		try {
			 session = HibernateUtil.getSession();
			 Query query = session.createQuery(query1);
			 List<String> list =query.list();
			 StringBuffer periodNames=new StringBuffer();
			 if(list!=null){
				 Iterator<String> itr=list.iterator();
				 int i=1;
				 int size=list.size();
				 while (itr.hasNext()) {
					 String objects = (String) itr.next();
					if(objects!=null){
						periodNames=periodNames.append(objects);
						if(i<size){
							periodNames.append(",");
						}
					}
					i=i+1;
				}
			 }
			 log.debug("Txn Impl : Leaving getSubjectNameById with success");
			 return periodNames.toString();
		 } catch (Exception e) {
			 log.debug("Txn Impl : Leaving getSubjectNameById with Exception");
			 throw e;
		 }
	}

	@Override
	public java.util.Date checkAttendanceDateMinRange(String q) throws Exception{
		log.debug("Txn Impl : Entering getStudentByBatch ");
		Session session = null;
		try {
			System.out.println("Query : " + q);
			 session = HibernateUtil.getSession();
			 Query query = session.createQuery(q);
			 java.util.Date startDate =(Date)query.uniqueResult();;
			 log.debug("Txn Impl : Leaving getStudentByBatch with success");
			 return startDate;
		 } catch (Exception e) {
			 e.printStackTrace();
			 log.debug("Txn Impl : Leaving getStudentByBatch with Exception");
			 throw e;
		 }
		}

	@Override
	public List<Integer> getCommonSubGrpForClass(Set<Integer> classSet,int year)
			throws Exception {
		log.debug("Txn Impl : Entering getStudentByBatch ");
		Session session = null;
		try {
			String subQuery="select distinct ss.subject.id FROM CurriculumSchemeSubject cs" +
					" join cs.curriculumSchemeDuration.classSchemewises c" +
					" join  cs.subjectGroup.subjectGroupSubjectses ss" +
				  " where cs.subjectGroup.isCommonSubGrp = 1 and " +
					//raghu
					"  cs.subjectGroup.isActive = 1 and ss.isActive=1 and cs.curriculumSchemeDuration.academicYear = :year" +
					" and c.id in (:classId)";
			 session = HibernateUtil.getSession();
			 Query query = session.createQuery(subQuery);
			 query.setInteger("year", year);
			 query.setParameterList("classId", classSet);
			 List<Integer> commonSubGrp=query.list();
			 log.debug("Txn Impl : Leaving getStudentByBatch with success");
			 return commonSubGrp;
		 } catch (Exception e) {
			 log.debug("Txn Impl : Leaving getStudentByBatch with Exception");
			 throw e;
		 }
		}

	/* To Get the last Attendance Date from db
	 * (non-Javadoc)
	 * @see com.kp.cms.transactions.attandance.IAttendanceEntryTransaction#getLastEnteredAttendanceDate()
	 */
	@Override
	public Date getLastEnteredAttendanceDate() throws Exception {
		log.debug("Txn Impl : Entering getStudentByBatch ");
		Session session = null;
		Date date=null;
		try {
			String subQuery="select max(a1.attendanceDate)" +
					" from Attendance a1 where a1.createdDate=(select max(a.createdDate) from Attendance a) group by a1.attendanceDate";
			 session = HibernateUtil.getSession();
			 Query query = session.createQuery(subQuery);
			 List<Date> dateList=query.list();
			 if(dateList!=null && !dateList.isEmpty()){
				 date=dateList.get(0);
			 }
			 log.debug("Txn Impl : Leaving getStudentByBatch with success");
			 return date;
		 } catch (Exception e) {
			 log.debug("Txn Impl : Leaving getStudentByBatch with Exception");
			 throw e;
		 }
		}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.attandance.IAttendanceEntryTransaction#getperiodsForInputClasses(java.util.Set)
	 */
	@Override
	public Map<Integer, Integer> getperiodsForInputClasses(
			Set<Integer> classesIdsSet) throws Exception {
		log.debug("Txn Impl : Entering getSubjectNameById ");
		Session session = null;
		try {
			 session = HibernateUtil.getSession();
			 Query query = session.createQuery("select period.id,period.classSchemewise.id from Period period where period.classSchemewise.id in (:classSchemeId)  and period.isActive  = true order by period.periodName")
				.setParameterList("classSchemeId", classesIdsSet);

			Map<Integer, Integer> periodMap = new LinkedHashMap<Integer, Integer>();
	
			Iterator<Object[]> periodIterator = query.iterate();
			while (periodIterator.hasNext()) {
				Object[] period = (Object[]) periodIterator.next();
				periodMap.put(Integer.parseInt(period[0].toString()),Integer.parseInt(period[1].toString()));
			}
			return periodMap;
		 } catch (Exception e) {
			 log.debug("Txn Impl : Leaving getSubjectNameById with Exception");
			 throw e;
		 }
	}
	
	public List<Integer> duplicateAttendanceCheckWhileUpdate(Set<Integer> classSet,Set<Integer> periodSet,Date date,int batchId,int subjectId,int attendanceId) throws Exception {
		log.debug("Txn Impl : Entering duplicateAttendanceCheck ");
		Session session = null;
		List<Integer> list = new ArrayList<Integer>();
		try {
			 //SessionFactory sessionFactory = InitSessionFactory.getInstance();
			 session = HibernateUtil.getSession();

			 
			 String sQuery = " select a" +
		 		" from Attendance a" +
		 		" join a.attendanceClasses ac with ac.classSchemewise.id in (:classSet)";
			 if(!periodSet.isEmpty()){
				 sQuery+= " join a.attendancePeriods ap with ap.period.id in (:periodsSet) ";
			 }
			 sQuery += " where a.id<>"+attendanceId+" and a.attendanceDate = :date and a.isCanceled = 0  and a.isMonthlyAttendance = 0";
			 if(subjectId != 0)
				 sQuery+=" and a.subject.id ="+subjectId;

			 
			 
			 String stringQuery = " select a.subject.id" +
			 		" from Attendance a" +
			 		" join a.attendanceClasses ac with ac.classSchemewise.id in (:classSet)";
			 if(!periodSet.isEmpty()) {
				 stringQuery+= " join a.attendancePeriods ap with ap.period.id in (:periodsSet) ";
			 }
			 stringQuery += " where  a.id<>"+attendanceId+" and a.attendanceDate = :date and a.isCanceled = 0  and a.isMonthlyAttendance = 0";
//			 if(subjectId != 0)
//				 stringQuery+=" and a.subject.id ="+subjectId;
			 
			 
			 String stringQuery1 = " select a.id" +
		 		" from Attendance a" +
		 		" join a.attendanceClasses ac with ac.classSchemewise.id in (:classSet)";
			 if(!periodSet.isEmpty()) {
				 stringQuery1+= " join a.attendancePeriods ap with ap.period.id in (:periodsSet) ";
			 }
			 stringQuery1+= " where  a.id<>"+attendanceId+" and a.attendanceDate = :date and a.isCanceled = 0 and a.isMonthlyAttendance = 0";
			 stringQuery1+=" and a.batch.id = "+batchId;
			 if(subjectId != 0) {
				 stringQuery1+=" and a.subject.id ="+subjectId;
			 }
			 
			 Query query = null;
			 if(batchId == 0) {
				 query = session.createQuery(stringQuery);
				 query.setParameterList("classSet", classSet);
				 if(!periodSet.isEmpty()) {
					 query.setParameterList("periodsSet",periodSet);
				 }
				 query.setDate("date", date);
				
				 list = query.list();
				 //session.close();
				 //sessionFactory.close();
				 log.debug("Txn Impl : Leaving duplicateAttendanceCheck with success");
				 return list;
				 
			 } else if(batchId != 0) {
				 boolean reCheck = true;
				 query = session.createQuery(sQuery);
				 query.setParameterList("classSet", classSet);
				 if(!periodSet.isEmpty()) {
					 query.setParameterList("periodsSet",periodSet);
				 }
				 query.setDate("date", date);
				
				 List<Attendance> list1 = query.list();
				 if(!list1.isEmpty()) {
					 Attendance attendance = list1.get(0);
					 list.add(attendance.getId());
					 if(attendance.getBatch() == null) {
						 reCheck =false; 
					 }
				 }
				 if(reCheck) {
				 query = session.createQuery(stringQuery1);
				 query.setParameterList("classSet", classSet);
				 if(!periodSet.isEmpty()) {
					 query.setParameterList("periodsSet",periodSet);
				 }
				 query.setDate("date", date);
				
				 list = query.list();
				 //session.close();
				 //sessionFactory.close();
				 log.debug("Txn Impl : Leaving duplicateAttendanceCheck with success");
				 return list;
				 }
			 }
			 
		 } catch (Exception e) {
			 log.debug("Txn Impl : Leaving duplicateAttendanceCheck with Exception");
			 throw e;
		 }
		return list; 
		}

	@Override
	public Map<Integer, String> getClassNamesByIds(Set<Integer> classesIdsSet)
			throws Exception {
		log.debug("Txn Impl : Entering getClassNamesByIds ");
		Session session = null;
		try {
			 session = HibernateUtil.getSession();
			 Query query = session.createQuery("select c.id,c.classes.name" +
			 		" from ClassSchemewise c where c.id in (:classSchemeId)").setParameterList("classSchemeId", classesIdsSet);

			Map<Integer, String> classNames = new LinkedHashMap<Integer, String>();
	
			Iterator<Object[]> periodIterator = query.iterate();
			while (periodIterator.hasNext()) {
				Object[] period = (Object[]) periodIterator.next();
				classNames.put(Integer.parseInt(period[0].toString()),period[1].toString());
			}
			return classNames;
		 } catch (Exception e) {
			 log.debug("Txn Impl : Leaving getClassNamesByIds with Exception");
			 throw e;
		 }
	}

	@Override
	public Map<Integer, List<String>> getPeriodsForClassAndPeriods(Set<Integer> classesIdsSet, String[] periods) throws Exception {
		log.debug("Txn Impl : Entering getClassNamesByIds ");
		Session session = null;
		try {
			 session = HibernateUtil.getSession();
			 StringBuilder intType =new StringBuilder();
				for(int i=0;i<periods.length;i++){
					 intType.append(periods[i]);
					 if(i<(periods.length-1)){
						 intType.append(",");
					 }
				}
			 Query query = session.createQuery("select p.classSchemewise.id,p.periodName" +
			 		" from Period p where p.periodName in (select p1.periodName from Period p1 where p1.id in ("+intType+"))" +
			 		" and p.isActive=1 and p.classSchemewise.id in (:classSchemeId)").setParameterList("classSchemeId", classesIdsSet);

			Map<Integer, List<String>> periodNames = new LinkedHashMap<Integer, List<String>>();
	
			Iterator<Object[]> periodIterator = query.iterate();
			while (periodIterator.hasNext()) {
				Object[] period = (Object[]) periodIterator.next();
				int id=Integer.parseInt(period[0].toString());
				if(periodNames.containsKey(id)){
					List<String> p=periodNames.remove(id);
					p.add(period[1].toString());
					periodNames.put(id,p);
				}else{
					List<String> p=new ArrayList<String>();
					p.add(period[1].toString());
					periodNames.put(id,p);
				}
			}
			return periodNames;
		 } catch (Exception e) {
			 log.debug("Txn Impl : Leaving getClassNamesByIds with Exception");
			 throw e;
		 }
	}

	@Override
	public List<Integer> getAllPeriodIdsByInput(AttendanceEntryForm attendanceEntryForm) throws Exception {
		log.debug("Txn Impl : Entering getClassNamesByIds ");
		Session session = null;
		try {
			 session = HibernateUtil.getSession();
			 String[] periods=attendanceEntryForm.getPeriods();
			 StringBuilder intType =new StringBuilder();
				for(int i=0;i<periods.length;i++){
					 intType.append(periods[i]);
					 if(intType != null){
						 if(i<(periods.length-1)){
							 intType.append(",");
						 }
					 }
				}
				 String[] classes=attendanceEntryForm.getClasses();
				 String intType1 ="";
					for(int i=0;i<classes.length;i++){
						if(classes[i] != null){
							intType1 = intType1+classes[i];
							 if(i<(classes.length-1)){
								 intType1 = intType1+",";
							 }
						 }
					}
					int len=intType1.length()-1;
			        if(intType1.endsWith(",")){
			            StringBuffer buff=new StringBuffer(intType1);
			            buff.setCharAt(len, ' ');
			            intType1=buff.toString();
			        }
			        List<Integer> list = session.createQuery("select p.id" +
			 		" from Period p where p.periodName in (select p1.periodName from Period p1 where p1.id in ("+intType+"))" +
			 		" and p.isActive=1 and p.classSchemewise.id in ("+intType1+")").list();
			return list;
		 } catch (Exception e) {
			 log.debug("Txn Impl : Leaving getClassNamesByIds with Exception");
			 throw e;
		 }
	}
	public Map<Integer,String> getUsers() throws Exception {
		log.debug("Txn Impl : Entering getAttendances ");
		Session session = null;
		Map<Integer,String> userMap= new HashMap<Integer, String>();
		try {
			 //SessionFactory sessionFactory = InitSessionFactory.getInstance();
			 session = HibernateUtil.getSession();

			 Query query = session.createQuery(" from Users a where a.isActive=1" ); 		
						 
			
			 List<Users> list = query.list();
			 if(list!=null && !list.isEmpty()){
				 Iterator<Users> itr = list.iterator();
				 while (itr.hasNext()) {
					Users users = (Users) itr.next();
					userMap.put(users.getId(), users.getUserName());
				}
			 }
			 //session.close();
			 //sessionFactory.close();
			 log.debug("Txn Impl : Leaving getAttendances with success");
			 return userMap;
		 } catch (Exception e) {
			 log.debug("Txn Impl : Leaving getAttendances with Exception");
			 throw e;
		 }
	}

	@Override
	public List<Period> getperiodsByIds(String[] classIds)
			throws Exception {
		Session session = null;
		List<Period> periodList;

		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			StringBuilder intType =new StringBuilder();
			for(int i=0;i<classIds.length;i++){
				 intType.append(classIds[i]);
				 if(i<(classIds.length-1)){
					 intType.append(",");
				 }
			}
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from Period a where isActive=1 and" +
											  " a.classSchemewise.id in ("+intType+") order by a.periodName");
//			query.setParameterList("classSchemeWise", classIds);
			List<Period> list = query.list();
			session.flush();
			//session.close();
			//sessionFactory.close();
			periodList = list;

		} catch (Exception e) {
			log.debug("Error during getting applicaition numbers..." + e);
			session.flush();
			//session.close();
			throw new ApplicationException(e);
		}
		return periodList;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.attandance.IAttendanceTypeTransaction#getUserDetails(com.kp.cms.forms.attendance.AttendanceEntryForm)
	 */
	public Users getUserDetails(AttendanceEntryForm attendanceEntryForm) throws Exception{
		Session session = null;
		Users user = new Users();
		try{
			session = HibernateUtil.getSession();
			user = (Users)session.createQuery(" from Users u where u.id="+attendanceEntryForm.getUserId()).uniqueResult();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return user;
		
	}
	/**
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public List<Period> getPeriodsList(List<Integer> classes) throws Exception {
		Session session = null;
		List<Period> periodList;

		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from Period a where a.isActive=1 and" +
											  " a.classSchemewise.id in (:classes) group by a.periodName order by a.periodName");
			query.setParameterList("classes", classes);
			periodList = query.list();
			session.flush();
		} catch (Exception e) {
			log.debug("Error during getting applicaition numbers..." + e);
			session.flush();
			//session.close();
			throw new ApplicationException(e);
		}
		return periodList;
	}
	/**
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public List<Subject> getSubjectList(List<Integer> classes, AttendanceEntryForm attendanceEntryForm) throws Exception {
		Session session = null;
		List<Subject> subList = null;

		try {
			session = HibernateUtil.getSession();
			Query query = session.createQuery("select tcs.subject "
					+ " from TeacherClassSubject tcs "
					+ " where tcs.teacherId = "+attendanceEntryForm.getUserId()+" and tcs.classId.id in (:classes) group by tcs.subject.id  order by tcs.subject.name");
			query.setParameterList("classes", classes);
			subList = query.list();
			session.flush();
		} catch (Exception e) {
			log.debug("Error during getting applicaition numbers..." + e);
			session.flush();
			//session.close();
			throw new ApplicationException(e);
		}
		return subList;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.attandance.IAttendanceEntryTransaction#getBatchesByActivityAndClassScheme(int, java.util.List)
	 */
	public Map<Integer, String> getBatchesByActivityAndClassScheme(
			int activityId, List<Integer> classSchemeId) {
		Session session = null;
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery(
							"from Batch batch where batch.classSchemewise.id in (:classSchemeId) and batch.activity.id = :activityId  and batch.isActive  = 1 order by batch.batchName")
					.setParameterList("classSchemeId", classSchemeId)
					.setParameter("activityId", activityId);

			Map<Integer, String> batchMap = new HashMap<Integer, String>();

			Iterator<Batch> periodIterator = query.iterate();
			while (periodIterator.hasNext()) {
				Batch batch = periodIterator.next();
				batchMap.put(batch.getId(), batch.getBatchName());
			}
			batchMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(batchMap);

			return batchMap;
		} catch (Exception e) {
			log.debug("Exception" + e.getMessage());
			return new HashMap<Integer, String>();
		} finally {
			if (session != null) {
				session.flush();
				// session.close();
			}
		}
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.attandance.IAttendanceEntryTransaction#getBatchesBySubjectAndClassScheme1(int, java.util.List)
	 */
	public Map<Integer, String> getBatchesBySubjectAndClassScheme1(String subjectId, List<Integer> classSchemeId,HttpSession httpSession) {
		Session session = null;
		Map<Integer, String> batchMap=null;
		Map<Integer, List<Integer>> subjectBatchMap=new HashMap<Integer, List<Integer>>();
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Map<String, List<Integer>> subjectCodeGroupMap= (Map<String, List<Integer>>) httpSession.getAttribute("SubjectCodeGroupMap");
			if(subjectCodeGroupMap!=null && !subjectCodeGroupMap.isEmpty()){
				if(subjectCodeGroupMap.containsKey(subjectId)){
					List<Integer> subjectIdList=subjectCodeGroupMap.get(subjectId);
					 batchMap = new HashMap<Integer, String>();
					for (Integer subjectId1 : subjectIdList) {
						//code added by mehaboob start
						Query query1=session.createQuery("select subject.code from Subject subject where subject.id="+subjectId1);
						String subjectCode=(String) query1.uniqueResult();
						//end
						Query query = session
								.createQuery(
										"select bs.batch" +
										" from BatchStudent bs" +
										" where bs.batch.isActive=1" +
										" and bs.classSchemewise.classes.isActive=1" +
										" and bs.classSchemewise.id in (:classSchemeId) and bs.batch.subject.id=:subjectId" +
										" group by bs.batch.id order by bs.batch.batchName")
								.setParameterList("classSchemeId", classSchemeId)
								.setParameter("subjectId", subjectId1);
						Iterator<Batch> periodIterator = query.iterate();
						while (periodIterator.hasNext()) {
							Batch batch = periodIterator.next();
							//code changed by mehaboob start
							if(subjectCode!=null && !subjectCode.isEmpty()){
								batchMap.put(batch.getId(), batch.getBatchName()+"("+subjectCode+")");
							}else{
								batchMap.put(batch.getId(), batch.getBatchName());
							}
							if(subjectBatchMap.containsKey(subjectId1)){
								List<Integer> batchIdList=subjectBatchMap.get(subjectId1);
								if(!batchIdList.contains(batch.getId())){
								batchIdList.add(batch.getId());
								}
								subjectBatchMap.put(subjectId1, batchIdList);
							}else{
								List<Integer> batchIdList=new ArrayList<Integer>();
								batchIdList.add(batch.getId());
								subjectBatchMap.put(subjectId1, batchIdList);
							}
							//end
						}
					}
			}else{
				 batchMap = new HashMap<Integer, String>();
				//code added by mehaboob start
				Query query1=session.createQuery("select subject.code from Subject subject where subject.id="+Integer.parseInt(subjectId));
				String subjectCode=(String) query1.uniqueResult();
				//end
				Query query = session
						.createQuery(
								"select bs.batch" +
								" from BatchStudent bs" +
								" where bs.batch.isActive=1" +
								" and bs.classSchemewise.classes.isActive=1" +
								" and bs.classSchemewise.id in (:classSchemeId) and bs.batch.subject.id=:subjectId" +
								" group by bs.batch.id order by bs.batch.batchName")
						.setParameterList("classSchemeId", classSchemeId)
						.setParameter("subjectId", Integer.parseInt(subjectId));
				Iterator<Batch> periodIterator = query.iterate();
				while (periodIterator.hasNext()) {
					Batch batch = periodIterator.next();
					//code changed by mehaboob start
					if(subjectCode!=null && !subjectCode.isEmpty()){
						batchMap.put(batch.getId(), batch.getBatchName()+"("+subjectCode+")");
					}else{
						batchMap.put(batch.getId(), batch.getBatchName());
					}
					if(subjectBatchMap.containsKey(Integer.parseInt(subjectId))){
						List<Integer> batchIdList=subjectBatchMap.get(Integer.parseInt(subjectId));
						if(!batchIdList.contains(batch.getId())){
						batchIdList.add(batch.getId());
						}
						subjectBatchMap.put(Integer.parseInt(subjectId), batchIdList);
					}else{
						List<Integer> batchIdList=new ArrayList<Integer>();
						batchIdList.add(batch.getId());
						subjectBatchMap.put(Integer.parseInt(subjectId), batchIdList);
					}
					//end
				}
			}
			}else{
				//code added by mehaboob start
				Query query1=session.createQuery("select subject.code from Subject subject where subject.id="+Integer.parseInt(subjectId));
				String subjectCode=(String) query1.uniqueResult();
				//end
				Query query = session
						.createQuery(
								"select bs.batch" +
								" from BatchStudent bs" +
								" where bs.batch.isActive=1" +
								" and bs.classSchemewise.classes.isActive=1" +
								" and bs.classSchemewise.id in (:classSchemeId) and bs.batch.subject.id=:subjectId" +
								" group by bs.batch.id order by bs.batch.batchName")
						.setParameterList("classSchemeId", classSchemeId)
						.setParameter("subjectId", Integer.parseInt(subjectId));

				 batchMap = new HashMap<Integer, String>();

				Iterator<Batch> periodIterator = query.iterate();
				while (periodIterator.hasNext()) {
					Batch batch = periodIterator.next();
					//code changed by mehaboob start
					if(subjectCode!=null && !subjectCode.isEmpty()){
						batchMap.put(batch.getId(), batch.getBatchName()+"("+subjectCode+")");
					}else{
						batchMap.put(batch.getId(), batch.getBatchName());
					}
					if(subjectBatchMap.containsKey(Integer.parseInt(subjectId))){
						List<Integer> batchIdList=subjectBatchMap.get(Integer.parseInt(subjectId));
						if(!batchIdList.contains(batch.getId())){
						batchIdList.add(batch.getId());
						}
						subjectBatchMap.put(Integer.parseInt(subjectId), batchIdList);
					}else{
						List<Integer> batchIdList=new ArrayList<Integer>();
						batchIdList.add(batch.getId());
						subjectBatchMap.put(Integer.parseInt(subjectId), batchIdList);
					}
					//end
				}
			}
			
			batchMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(batchMap);
			httpSession.setAttribute("SubjectBatchMap", subjectBatchMap);
			return batchMap;
		} catch (Exception e) {
			log.debug("Exception" + e.getMessage());
			return new HashMap<Integer, String>();
		} finally {
			if (session != null) {
				session.flush();
				// session.close();
			}
		}
	}
	/**
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public List<TeacherClassSubject> getMultiTeachers(List<Integer> classes, AttendanceEntryForm attendanceEntryForm) throws Exception {
		Session session = null;
		List<TeacherClassSubject> teacherList = null;

		try {
			session = HibernateUtil.getSession();
			Query query = session.createQuery("select tcs "
					+ " from TeacherClassSubject tcs "
					+ " where tcs.classId.curriculumSchemeDuration.academicYear="+attendanceEntryForm.getYear()+" and tcs.isActive=1 and tcs.classId.id in (:classes) and tcs.subject.id="+attendanceEntryForm.getSubjectId());
			query.setParameterList("classes", classes);
			teacherList = query.list();
			
		} catch (Exception e) {
			log.debug("Error during getting applicaition numbers..." + e);
			session.flush();
			//session.close();
			throw new ApplicationException(e);
		}
		return teacherList;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.attandance.IAttendanceEntryTransaction#getPeriodsByTeacher(java.lang.String[], java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public List<Period> getPeriodsByTeacher(String[] teachers, String year,
			String day, String date, String subjectId) throws Exception {
		List<Period> periods=new ArrayList<Period>();
		try {
			Session session = HibernateUtil.getSession();
			Query query = session.createQuery( "select t.ttSubjectBatch.ttPeriodWeek.period from TTUsers t where t.users.id in ("+teachers[0]+") " +
					" and t.ttSubjectBatch.ttPeriodWeek.ttClasses.classSchemewise.curriculumSchemeDuration.academicYear = "+year+" and t.ttSubjectBatch.isActive=1 and t.isActive=1 and t.ttSubjectBatch.ttPeriodWeek.weekDay='"+day+"'" +
							" and '"+CommonUtil.ConvertStringToSQLDate(date)+"' between t.ttSubjectBatch.ttPeriodWeek.ttClasses.classSchemewise.curriculumSchemeDuration.startDate and t.ttSubjectBatch.ttPeriodWeek.ttClasses.classSchemewise.curriculumSchemeDuration.endDate" +
							" and  t.ttSubjectBatch.subject.id="+subjectId+
							" group by t.ttSubjectBatch.ttPeriodWeek.period.periodName order by t.ttSubjectBatch.ttPeriodWeek.ttClasses.classSchemewise.id");
			periods = query.list();
			return periods;
		} catch (Exception e) {
			log.debug("Exception" + e.getMessage());
		}
		return periods;
	}

	@Override
	public List<TeacherClassEntryTo> getAditionalTeachers(String[] periods,	String day , String[] teachers, String subjectId) throws Exception {
		List<TeacherClassEntryTo> teasherList=new ArrayList<TeacherClassEntryTo>();
		 int periodId=0;
		try {
			Session session = HibernateUtil.getSession();
			periodId = Integer.parseInt(periods[0]);
			List<TTUsers> userList=session.createQuery("from TTUsers t where   t.ttSubjectBatch.isActive=1 and t.isActive=1 and t.ttSubjectBatch.ttPeriodWeek.weekDay=:day and t.ttSubjectBatch.ttPeriodWeek.period.id=:periodId and t.ttSubjectBatch.subject.id="+subjectId+" order by t.users.userName ").setString("day",day).setInteger("periodId", periodId).list();
			if(userList!=null && !userList.isEmpty()){
				Iterator<TTUsers> userItr=userList.iterator();
				while (userItr.hasNext()) {
					TTUsers bo=userItr.next();
					if(!teachers[0].contains(String.valueOf(bo.getUsers().getId()))){
						TeacherClassEntryTo to = new TeacherClassEntryTo();
						to.setTeacherName(bo.getUsers().getUserName());
						to.setId(bo.getUsers().getId());
						to.setTeacherId(bo.getUsers().getId());
						teasherList.add(to);
					}
				}
			}
		
			session.flush();
			return teasherList;
		} catch (Exception e) {
			log.debug("Exception" + e.getMessage());
		}

		return teasherList;
	}

	@Override
	public int getCurrentPeriodId(List<Integer> periodList) throws Exception {
		int periodId=0;
		try {
			Session session = HibernateUtil.getSession();
			periodId=(Integer)session.createSQLQuery(" select p.id from period p where p.is_active=1 and '"+CommonUtil.getCurrentTime()+"' between subtime(p.start_time,'00:10:00') and addtime(p.start_time,concat('00:',(select time_limit_for_automatic_att_entry from organisation),':00')) and p.id in (:periodId)").setParameterList("periodId", periodList).uniqueResult();
			return periodId;
		} catch (Exception e) {
			log.debug("Exception" + e.getMessage());
		}
		return periodId;
	}
	

	@Override
	public List<StuCocurrLeave> stuCocurrLeaveResult(String reg,AttendanceEntryForm attendanceEntryForm, Set<Integer> classSet) throws Exception {
        Session session = null;
        try{
        	session = HibernateUtil.getSession();
   		    Query query = session.createQuery(" select stuCocurrLeave "
   		    		                     +" from StuCocurrLeave stuCocurrLeave inner join stuCocurrLeave.stuCocurrLeaveDetailses stuCocurrLeaveDetailses "
						                + " where ( ( :startDate between stuCocurrLeave.startDate  and stuCocurrLeave.endDate )"
						                + " or (:endDate  between  stuCocurrLeave.startDate  and stuCocurrLeave.endDate  )  " 
						                + " or (:startDate >=  stuCocurrLeave.startDate and :endDate <=  stuCocurrLeave.endDate))" 
						                + " and stuCocurrLeave.isCocurrLeaveCancelled = 0 "
						                + " and stuCocurrLeave.classSchemewise.id in (:classId)"
						                + " and stuCocurrLeaveDetailses.student.registerNo='"+reg+"'");
   		query.setDate("startDate",CommonUtil.ConvertStringToSQLDate(attendanceEntryForm.getAttendancedate()));
   		query.setDate("endDate", CommonUtil.ConvertStringToSQLDate(attendanceEntryForm.getAttendancedate()));
   		query.setParameterList("classId", classSet);
   		List<StuCocurrLeave> results = query.list();
   		session.flush();
   	   	return results;
       }catch (Exception e) {
			log.error("Error during getting EvaStudentFeedbackGroup Entry..." ,e);
			session.flush();
	        throw new ApplicationException(e);
		}
   }

	@Override
	public List<StudentLeave> getLeavesDetails(String reg, java.util.Date date,	Set<Integer> classSet) throws Exception {
        Session session = null;
        try{
        	session = HibernateUtil.getSession();
   		    Query query = session.createQuery(" select studentLeave "
   		    		                     +" from StudentLeave studentLeave inner join studentLeave.studentLeaveDetails studentLeaveDetails "
						                + " where ( ( :startDate between studentLeave.startDate  and studentLeave.endDate )"
						                + " or (:endDate  between  studentLeave.startDate  and studentLeave.endDate  )  " 
						                + " or (:startDate >=  studentLeave.startDate and :endDate <=  studentLeave.endDate))" 
						                + " and studentLeave.leaveCanceled = 0 "
						                + " and studentLeave.classSchemewise.id in (:classId)"
						                + " and studentLeaveDetails.student.registerNo='"+reg+"'");
   		  query.setDate("startDate", date);
   		 query.setDate("endDate", date);
   		query.setParameterList("classId", classSet);
   		List<StudentLeave> results = query.list();
   		session.flush();
   	   	return results;
       }catch (Exception e) {
			log.error("Error during getting EvaStudentFeedbackGroup Entry..." ,e);
			session.flush();
	        throw new ApplicationException(e);
		}
   }
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.attandance.IAttendanceEntryTransaction#getClassesByTeacher(int, java.lang.String)
	 */
	public Map<Integer, String> getClassesByTeacher(int teacherId, String year) {
		try {
			Session session = null;
			session = HibernateUtil.getSession();
			java.sql.Date currentDate = CommonUtil.ConvertStringToSQLDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(new java.util.Date()),AttendanceEntryTransactionImpl.SQL_DATEFORMAT,AttendanceEntryTransactionImpl.FROM_DATEFORMAT));
			Query query = session
					.createQuery(" select tcs "
							+ " from TeacherClassSubject tcs"
							+ " where tcs.teacherId = ? and tcs.year=? " +
							" and tcs.classId.classes.isActive=1 " +
							" and tcs.subject.isActive=1 " +
							" and '"+currentDate+"' between tcs.classId.curriculumSchemeDuration.startDate and tcs.classId.curriculumSchemeDuration.endDate ");
			query.setInteger(0, teacherId);
			query.setString(1, year);
			Iterator<TeacherClassSubject> tuples = query.list().iterator();
			Map<Integer, String> classMap = new LinkedHashMap<Integer, String>();
			while (tuples.hasNext()) {
				TeacherClassSubject tcs = (TeacherClassSubject) tuples.next();
				classMap.put(tcs.getClassId().getId(), tcs.getClassId()
						.getClasses().getName());
			}
			session.flush();
			classMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(classMap);

			return classMap;
		} catch (Exception e) {
			log.debug("Exception" + e.getMessage());
		}
		return new HashMap<Integer, String>();
	}

	@Override
	public List<Attendance> checkAttendanceDuplication(java.util.Date date,int teacherId,String startTime,String endTime) throws Exception {
		List<Attendance> attendances=null;
		try {
			Session session = HibernateUtil.getSession();
			Query query = session.createQuery("select a from Attendance a join a.attendanceInstructors ins 	join a.attendancePeriods per where a.attendanceDate='"+date+
					"' and a.isCanceled=0 and ins.users.id="+teacherId+" and per.period.startTime='"+startTime+
					"' and per.period.endTime='"+endTime+"'");
			attendances =(List<Attendance>) query.list();
			return attendances;
		} catch (Exception e) {
			log.debug("Exception" + e.getMessage());
		}
		return attendances;
	}

	@Override
	public Period getPeriods(int periodId) throws Exception {
		Period period=null;
		try {
			Session session = HibernateUtil.getSession();
			Query query1=session.createQuery(" from Period p where p.isActive=1 and p.id="+periodId);
			period=(Period)query1.uniqueResult();
			return period;
		} catch (Exception e) {
			log.debug("Exception" + e.getMessage());
		}
		return period;
	}

	@Override
	public String getTeacherName(int teacherId) throws Exception {
		String teacherName=null;
		try {
			Session session = HibernateUtil.getSession();
			//Query query1=session.createQuery("select p.userName from Users p where p.isActive=1 and p.id="+teacherId);
			Query query1=session.createQuery("select p.employee.firstName from Users p where p.isActive=1 and p.id="+teacherId);
			
			teacherName=(String)query1.uniqueResult();
			return teacherName;
		} catch (Exception e) {
			log.debug("Exception" + e.getMessage());
		}
		return teacherName;
	}
	
	/* (non-Javadoc)  code added by chandra
	 * @see com.kp.cms.transactions.attandance.IAttendanceEntryTransaction#duplicateAttendanceCheckForBatches(java.util.Set, java.util.Set, java.sql.Date)
	 */
	public List<Integer> duplicateAttendanceCheckForBatches(Set<Integer> classSet,Set<Integer> periodSet,Date date,int batchId) throws Exception {
		log.debug("Txn Impl : Entering duplicateAttendanceCheck ");
		Session session = null;
		List<Integer> list = new ArrayList<Integer>();
		try {
			 //SessionFactory sessionFactory = InitSessionFactory.getInstance();
			 session = HibernateUtil.getSession();
			 String sQuery = " select a.batch.id" +
		 		" from Attendance a" +
		 		" join a.attendanceClasses ac with ac.classSchemewise.id in (:classSet)";
			 if(!periodSet.isEmpty()){
				 sQuery+= " join a.attendancePeriods ap with ap.period.id in (:periodsSet) ";
			 }
			 sQuery += " where a.attendanceDate = :date and a.isCanceled = 0  and a.isMonthlyAttendance = 0";
		if(batchId!=0){	 
			 Query query = session.createQuery(sQuery);
			 query.setParameterList("classSet", classSet);
			 if(!periodSet.isEmpty()) {
				 query.setParameterList("periodsSet",periodSet);
			 }
			 query.setDate("date", date);
			
			 list = query.list();
			 //session.close();
			 //sessionFactory.close();
			 log.debug("Txn Impl : Leaving duplicateAttendanceCheck with success");
			 return list;
		}
		}catch (Exception e) {
			 log.debug("Txn Impl : Leaving duplicateAttendanceCheck with Exception");
			 throw e;
		 }
		return list;
	}

	
	
	
	public List<Object[]> duplicateAttendanceCheckForCommonSubject(Set<Integer> classSet,Set<Integer> periodSet,Date date) throws Exception {
		log.debug("Txn Impl : Entering duplicateAttendanceCheck ");
		Session session = null;
		List<Object[]> list = null;
		try {
			 //SessionFactory sessionFactory = InitSessionFactory.getInstance();
			 session = HibernateUtil.getSession();
			 
			 String stringQuery = " select a.subject.name,a.batch.batchName,clss.name" +
			 		" from Attendance a" +
			 		" join a.attendanceClasses ac with ac.classSchemewise.id in (:classSet)"+
			 		" join ac.classSchemewise.classes clss ";
			 if(!periodSet.isEmpty()) {
				 stringQuery+= " join a.attendancePeriods ap with ap.period.id in (:periodsSet) ";
			 }
			 stringQuery += " where a.attendanceDate = :date and a.isCanceled = 0  and a.isMonthlyAttendance = 0 and a.subject.id is not null";
			 
			 Query query = null;
			 
				 query = session.createQuery(stringQuery);
				 query.setParameterList("classSet", classSet);
				 if(!periodSet.isEmpty()) {
					 query.setParameterList("periodsSet",periodSet);
				 }
				 query.setDate("date", date);
				
				 list = query.list();
				 //session.close();
				 //sessionFactory.close();
			 
		 } catch (Exception e) {
			 log.debug("Txn Impl : Leaving duplicateAttendanceCheck with Exception");
			 throw e;
		 }
		return list; 
		}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.attandance.IAttendanceEntryTransaction#getSubjectCodeBySubId(int)
	 * method added by mehaboob to get subject Code
	 */
	@Override
	public String getSubjectCodeBySubId(int subjectId) throws Exception {
		log.debug("Txn Impl : Entering getSubjectCodeBySubId ");
		Session session = null;
		String subjectCode=null;
		try {
			 //SessionFactory sessionFactory = InitSessionFactory.getInstance();
			 session = HibernateUtil.getSession();
			 Query query=session.createQuery("select subject.code from Subject subject where subject.id="+subjectId);
				subjectCode=(String) query.uniqueResult();
			 
		 } catch (Exception e) {
			 log.debug("Txn Impl : Leaving getSubjectCodeBySubId with Exception");
			 throw e;
		 }
		return subjectCode; 
	}
	
	
	
	
	//raghu
	
	@Override
		public List<Integer> getStudentIdsByClassSubject(Set<Integer> classIds,int subjectId) throws Exception {
			log.debug("Txn Impl : Entering getStudentByClass ");
			Session session = null;
			try {
				 //SessionFactory sessionFactory = InitSessionFactory.getInstance();
				 session = HibernateUtil.getSession();
				Query query = session.createQuery("select distinct(s.id) from Student s inner join s.admAppln.applicantSubjectGroups asb inner join  asb.subjectGroup.subjectGroupSubjectses sbs where s.classSchemewise.id in (:classesIds) and s.isAdmitted = 1 and s.isActive = 1 and sbs.isActive=1 and s.admAppln.isCancelled=0 and (s.isHide =0 or s.isHide is null) and sbs.subject.id="+subjectId +
						" and s.tcNo is null and s.id not in (select es.student.id from ExamStudentDetentionRejoinDetails es where (es.detain=1 or es.discontinued=1) and (es.rejoin = 0 or es.rejoin is null)) ");	
				query.setParameterList("classesIds", classIds);
				
				 List<Integer> list = query.list();
				 //session.close();
				 //sessionFactory.close();
				 log.debug("Txn Impl : Leaving getStudentByClass with success");
				 return list;
			 } catch (Exception e) {
				 log.debug("Txn Impl : Leaving getStudentByClass with Exception");
				 throw e;
			 }
			}
		
		
		
		
		/*
		 * This method will returns the list of Attendance based on class,date & attendancetype.
		 * (non-Javadoc)
		 * @see com.kp.cms.transactions.attandance.IAttendanceEntryTransaction#getAttendances(java.util.Set, java.sql.Date, int)
		 */
	@Override
		public List<Integer> getClassAttendancesStudentsIds(Set<Integer> classSet,Set<Integer> periodSet,java.util.Date date,String batchId) throws Exception {
			log.debug("Txn Impl : Entering getAttendances ");
			Session session = null;
			try {
				 //SessionFactory sessionFactory = InitSessionFactory.getInstance();
				 session = HibernateUtil.getSession();
				 String s=" select ast.student.id" +
			 		" from Attendance a" +
			 		" join a.attendancePeriods ap with ap.period.id in (:periodsSet) join a.attendanceClasses ac  with ac.classSchemewise.id in (:classSet) " +
					" join a.attendanceStudents ast where a.attendanceDate = :date" +
					" and ast.student.tcNo is null and a.isCanceled = 0" +
					" and a.isMonthlyAttendance = 0 ";
					if(batchId!=null && !batchId.equalsIgnoreCase("")){
						s=s+" and a.batch.id="+Integer.parseInt(batchId);
					}
					s=s+" order by ap.period.periodName" ;
				 
				 Query query = session.createQuery(s);
							 
				 query.setParameterList("classSet", classSet);
				 query.setDate("date", date);
				 query.setParameterList("periodsSet",periodSet);
				 
				 List<Integer> list = query.list();
				 //session.close();
				 //sessionFactory.close();
				 log.debug("Txn Impl : Leaving getAttendances with success");
				 return list;
			 } catch (Exception e) {
				 log.debug("Txn Impl : Leaving getAttendances with Exception");
				 throw e;
			 }
		}

	
	
	
	@Override
	public List getClassAttendancesStudents(Set<Integer> classSet,java.util.Date date) throws Exception {
		log.debug("Txn Impl : Entering getAttendances ");
		Session session = null;
		try {
			 //SessionFactory sessionFactory = InitSessionFactory.getInstance();
			 session = HibernateUtil.getSession();
			 List attList = null;
			/* Query studentQuery = session.createSQLQuery("SELECT period.period_name, student.roll_no,student.id,attendance_student.is_cocurricular_leave,attendance_student.is_on_leave,attendance_student.is_present"+
						" FROM    (   (   (   (   attendance_period attendance_period  INNER JOIN  period period   ON (attendance_period.period_id = period.id))"+
						" INNER JOIN  attendance attendance  ON (attendance_period.attendance_id = attendance.id))"+
						" INNER JOIN  attendance_student attendance_student  ON (attendance_student.attendance_id = attendance.id))"+
						" INNER JOIN  student student  ON (attendance_student.student_id = student.id))"+
						" INNER JOIN  attendance_class attendance_class  ON (attendance_class.attendance_id = attendance.id)"+
						" where attendance_class.class_schemewise_id in (:classSet) and attendance.attendance_date=:date"+
						" group by  attendance_student.student_id,attendance_period.period_id order by period.period_name,student.roll_no");
			 */
			 
			 Query studentQuery = session.createSQLQuery("SELECT distinct student.id as sid ,period.period_name as pname, student.roll_no as rno,attendance_student.is_cocurricular_leave as clea,"+
					 "  attendance_student.is_on_leave as lea,attendance_student.is_present as pre,ifnull(employee.first_name,guest_faculty.first_name) as ename,classes.name as clsname,subject.name as subname,personal_data.first_name as pername, attendance_student.id as attstudentid"+ 

					 " from attendance_student attendance_student  inner JOIN   attendance attendance ON (attendance_student.attendance_id = attendance.id)"+
					 " inner JOIN attendance_class attendance_class ON (attendance_class.attendance_id = attendance.id)"+
					 " inner join class_schemewise class_schemewise on (attendance_class.class_schemewise_id = class_schemewise.id)"+ 
					 " inner JOIN student student  ON (attendance_student.student_id =  student.id)"+
					 " INNER JOIN classes classes  ON (class_schemewise.class_id = classes.id)"+
					 " INNER JOIN adm_appln adm_appln  ON (student.adm_appln_id = adm_appln.id)"+
					 " INNER JOIN course course  ON (classes.course_id = course.id) " +
					// " AND (adm_appln.selected_course_id = course.id)"+
					 " INNER JOIN personal_data personal_data ON (adm_appln.personal_data_id = personal_data.id)"+
					 " INNER JOIN subject subject ON (attendance.subject_id = subject.id)"+
					 " inner join  period period ON (period.class_schemewise_id =  class_schemewise.id)"+
					 " INNER JOIN  attendance_period attendance_period ON (attendance_period.period_id = period.id) and (attendance_period.attendance_id = attendance.id )"+
					 " inner join attendance_instructor attendance_instructor on attendance_instructor.attendance_id= attendance.id"+  
					 " inner join users users on attendance_instructor.users_id = users.id"+ 
					 " left join employee employee on users.employee_id = employee.id"+  
					 " left join guest_faculty guest_faculty on users.guest_id = guest_faculty.id"+
					 " where attendance_class.class_schemewise_id in (:classSet)" +
					 " and attendance.attendance_date=:date and attendance.is_canceled=0 and student.tc_no is null"+
					 " and (adm_appln.admission_date<=:date or adm_appln.admission_date is null) "+
					 " group by student.id,subject.id,period.id,classes.id,attendance.attendance_date order by period.period_name,student.roll_no");
			 
				 
			 studentQuery.setParameterList("classSet", classSet);
			 studentQuery.setDate("date", date);
			 
			 
			 attList = studentQuery.list();
			 //session.close();
			 //sessionFactory.close();
			 log.debug("Txn Impl : Leaving getAttendances with success");
			 return attList;
		 } catch (Exception e) {
			 log.debug("Txn Impl : Leaving getAttendances with Exception");
			 throw e;
		 }
	}

	@Override
	public List<String> getPeriodNamesOnClass(Set<Integer> classSet)throws Exception {
		
		Session session = null;
		
		try {
			 //SessionFactory sessionFactory = InitSessionFactory.getInstance();
			 session = HibernateUtil.getSession();

			 Query query = session.createQuery("select distinct(p.periodName) from Period p where p.isActive=1 and p.classSchemewise.id in (:classSet) order by p.periodName" );
						 
			 query.setParameterList("classSet", classSet);
			 
			 
			 List<String> list = query.list();
		
			 return list;
		 } catch (Exception e) {
			 log.debug("Txn Impl : Leaving getAttendances with Exception");
			 throw e;
		 }
	}

	@Override
	public List<String> getPeriodNmaesOnPeriods(Set<Integer> periodSet)throws Exception {
		
		Session session = null;
		
		try {
			 //SessionFactory sessionFactory = InitSessionFactory.getInstance();
			 session = HibernateUtil.getSession();

			 Query query = session.createQuery("select distinct(p.periodName) from Period p where p.isActive=1 and p.id in (:periodSet) order by p.periodName" );
						 
			 query.setParameterList("periodSet", periodSet);
			 
			 
			 List<String> list = query.list();
		
			 return list;
		 } catch (Exception e) {
			 log.debug("Txn Impl : Leaving getAttendances with Exception");
			 throw e;
		 }
	}

	@Override
	public List<Student> getStudentsOnClasses(Set<Integer> classSet, int year,int subjectId,Date startDate)throws Exception {
		
		Session session = null;
		
		try {
			 //SessionFactory sessionFactory = InitSessionFactory.getInstance();
			 session = HibernateUtil.getSession();
 
			 Query query = session.createQuery("select distinct(s) from Student s inner join s.admAppln.applicantSubjectGroups asb inner join  asb.subjectGroup.subjectGroupSubjectses sbs where s.classSchemewise.id in (:classesIds) and s.isAdmitted = 1 and s.isActive = 1  and s.admAppln.isCancelled=0 and sbs.isActive=1 and (s.isHide =0 or s.isHide is null) and sbs.subject.id="+subjectId +
				" and s.tcNo is null and s.id not in (select es.student.id from ExamStudentDetentionRejoinDetails es where (es.detain=1 or es.discontinued=1) and (es.rejoin = 0 or es.rejoin is null)) and (s.admAppln.admissionDate<='"+startDate+"' or s.admAppln.admissionDate is null)");	
			 query.setParameterList("classesIds", classSet);
		
			 
			 List<Student> list = query.list();
		
			 return list;
		 } catch (Exception e) {
			 log.debug("Txn Impl : Leaving getAttendances with Exception");
			 throw e;
		 }
	}

	@Override
	public List<Student> getStudentsOnAttendance(int attid)
			throws Exception {
			Session session = null;
		
		try {
			 //SessionFactory sessionFactory = InitSessionFactory.getInstance();
			 session = HibernateUtil.getSession();
 
			 Query query = session.createQuery("select atst.student from Attendance at inner join at.attendanceStudents atst where  atst.student.tcNo is null and at.id= "+attid);	
			 List<Student> list = query.list();
		
			 return list;
		 } catch (Exception e) {
			 log.debug("Txn Impl : Leaving getAttendances with Exception");
			 throw e;
		 }
	}

	
	
	
	@Override
	public List getStudentsOnBatches(Set<Integer> batchSet,Set<Integer> classSet)throws Exception {
		
		Session session = null;
		
		try {
			 //SessionFactory sessionFactory = InitSessionFactory.getInstance();
			 session = HibernateUtil.getSession();
 
			Query query = session.createQuery("from BatchStudent b where b.batch.id in (:batchIds) and b.student.isAdmitted = 1 " +
					" and b.student.tcNo is null and b.classSchemewise.id in (:classIds) and b.isActive = 1 and (b.student.isHide = 0 or b.student.isHide is null)");
			 query.setParameterList("batchIds", batchSet);
			 query.setParameterList("classIds", classSet);
			 
			 List list = query.list();
		
			 return list;
		 } catch (Exception e) {
			 log.debug("Txn Impl : Leaving getAttendances with Exception");
			 throw e;
		 }
	}

	
	
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.attandance.IAttendanceEntryTransaction#getperiodsByPeriodIds(java.util.List)
	added by mehaboob 
	 */
	/*@Override
	public List<Period> getperiodsByPeriodIds(List<Integer> periodList) throws Exception {
		Session session = null;
		List<Period> periodList1=null;

		try {
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from Period a where isActive=1 and" +
											  " a.id in (:periodList) order by a.periodName");
//			query.setParameterList("classSchemeWise", classIds);
			query.setParameterList("periodList", periodList);
			List<Period> list = query.list();
			session.flush();
			//session.close();
			//sessionFactory.close();
			periodList1 = list;

		} catch (Exception e) {
			log.debug("Error during getting applicaition numbers..." + e);
			session.flush();
			//session.close();
			throw new ApplicationException(e);
		}
		return periodList1;
	}*/
	
	
	public List<Student> getStudentByClasssetForWeeklyAttendance(Set<Integer> classIds) throws Exception {
		log.debug("Txn Impl : Entering getStudentByClass ");
		Session session = null;
		try {
			 //SessionFactory sessionFactory = InitSessionFactory.getInstance();
			 session = HibernateUtil.getSession();
			Query query = session.createQuery("from Student s where s.classSchemewise.id in (:classesIds) and isAdmitted = 1 and s.isActive = 1  and s.tcNo is null and s.admAppln.isCancelled=0 and (s.isHide =0 or s.isHide is null)");	
			query.setParameterList("classesIds", classIds);
			
			 List<Student> list = query.list();
			 //session.close();
			 //sessionFactory.close();
			 log.debug("Txn Impl : Leaving getStudentByClass with success");
			 return list;
		 } catch (Exception e) {
			 log.debug("Txn Impl : Leaving getStudentByClass with Exception");
			 throw e;
		 }
		}

	@Override
	public List  getStudentByClassBYWeeklyAttendance(
			Set<Integer> classSet) throws Exception {
		// TODO Auto-generated method stub
		Session session=null;
		List<Student> list=null;
		 Calendar cal = Calendar.getInstance();
		 SimpleDateFormat df = new SimpleDateFormat( "yyyy-MM-dd" ); 
		  cal.add(Calendar.DAY_OF_MONTH, -7);
	     String monday= df.format( cal.getTime() ); 
	     Calendar cale = Calendar.getInstance();
	     cal.add(Calendar.DAY_OF_MONTH, -3);
	     String friday= df.format(cale.getTime() );
	     
		try{
			session=HibernateUtil.getSession();
			Query query = session.createQuery("select student.classSchemewise.classes.id,"+
                                               " student.id ,"+
                                               " student.registerNo,"+
                                               " student.admAppln.personalData.firstName,"+
                                               " (((sum(case when (attendanceStudents.isPresent = 1 or "+
                                               " attendanceStudents.isCoCurricularLeave = 1) then "+
                                               " attendanceStudents.attendance.hoursHeld else 0 end))/(sum(attendanceStudents.attendance.hoursHeld)))*100),"+
                                               " attendanceStudents.attendance.hoursHeld,"+
                                               " attendanceStudents.attendance.attendanceDate,  "+ 
                                               " student.admAppln.personalData.gender "+ 
                                               " from Student student "+
                                               " inner join student.attendanceStudents attendanceStudents "+
                                               " join attendanceStudents.attendance.attendanceClasses ac "+
                                               " where attendanceStudents.attendance.isMonthlyAttendance = 0 "+ 
                                               " and  attendanceStudents.attendance.isActivityAttendance = 0 "+
                                               " and  attendanceStudents.attendance.isCanceled = 0 "+
                                               " and student.classSchemewise.id in (:classesIds)"+
                                               " and student.id not in (select s.student.id from ExamStudentDetentionRejoinDetails s "+
                                               " where (s.detain=1 or s.discontinued=1) and (s.rejoin = 0 or s.rejoin is null)) "+
                                               " and (attendanceStudents.attendance.attendanceDate between '"+ monday + "' and '"+ friday+"')"+
                                               " and student.tcNo is null and student.id not in (select s.studentId from StudentBulkAttendanceSMS s where s.isMessageSent=1 and s.attendanceDate=current_date)"+
                                               " group by student.id order by student.id");	
			            query.setParameterList("classesIds", classSet);
			            list = query.list();
		}
			  catch (Exception e) {
				 log.debug("Txn Impl : Leaving getStudentByClass with Exception");
				 throw e;
			 }
		return list;
	}
	


	@Override
	public List getStudentByClassBYWeeklyAttendanceData(String classid,
			String studentid) throws Exception {
		// TODO Auto-generated method stub
		Session session=null;
		List student =null;
		Calendar cal = Calendar.getInstance();
		 SimpleDateFormat df = new SimpleDateFormat( "yyyy-MM-dd" ); 
		  cal.add(Calendar.DAY_OF_MONTH, -7);
	     String monday= df.format( cal.getTime() ); 
	     Calendar cale = Calendar.getInstance();
	     cal.add(Calendar.DAY_OF_MONTH, -3);
	     String friday= df.format(cale.getTime() );
	     float percentage=0;
		try{
			session=HibernateUtil.getSession();
			Query query = session.createQuery("select student.classSchemewise.classes.id,"+
                    " student.id ,"+
                    " student.registerNo,"+
                    " student.admAppln.personalData.firstName,"+
                    " (sum(case when (attendanceStudents.isPresent = 1 or "+
                    " attendanceStudents.isCoCurricularLeave = 1) then "+
                    " attendanceStudents.attendance.hoursHeld else 0 end)),"+
                    " (sum(case when (attendanceStudents.isPresent = 0 or "+
                    " attendanceStudents.isCoCurricularLeave = 0) then "+
                    " attendanceStudents.attendance.hoursHeld else 0 end)),"+
                    " sum(attendanceStudents.attendance.hoursHeld),"+
                   // " attendanceStudents.attendance.hoursHeld,"+
                    " attendanceStudents.attendance.attendanceDate,  "+ 
                    " student.admAppln.personalData.gender, "+ 
                    " student.admAppln.personalData.parentMob2"+
                    " from Student student "+
                    " inner join student.attendanceStudents attendanceStudents "+
                    " join attendanceStudents.attendance.attendanceClasses ac "+
                    " where attendanceStudents.attendance.isMonthlyAttendance = 0 "+ 
                    " and  attendanceStudents.attendance.isActivityAttendance = 0 "+
                    " and  attendanceStudents.attendance.isCanceled = 0 "+
                    " and student.classSchemewise.classes.id ="+classid+
                    " and student.id not in (select s.student.id from ExamStudentDetentionRejoinDetails s "+
                    " where (s.detain=1 or s.discontinued=1) and (s.rejoin = 0 or s.rejoin is null)) "+
                    " and (attendanceStudents.attendance.attendanceDate between '"+ monday + "' and '"+ friday+"')"+
                    " and student.id not in (select s.studentId from StudentBulkAttendanceSMS s where s.isMessageSent=1 and s.attendanceDate=current_date)"+
                    " and student.tcNo is null and student.id = "+studentid+" group by student.id,attendanceStudents.attendance.attendanceDate order by student.id");
                                              	
			            //query.setParameterList("classesIds", classSet);
			student = query.list();
		}
			  catch (Exception e) {
				 log.debug("Txn Impl : Leaving getStudentByClass with Exception");
				 throw e;
			 }
		return student;
	}

	@Override
	public boolean saveAttendanceTimeRestrictionBo(AttendanceTimeRestrictionClasswise bo) {
		log.debug("call of saveAttendanceTimeRestrictionBo method in MobileSMSCriteriaTransactionImpl.class");
		boolean isAdded=false;
		Session session=null;
		Transaction transaction=null;
		try
		{
			session=HibernateUtil.getSession();
			transaction=session.beginTransaction();
			transaction.begin();
			session.save(bo);
			transaction.commit();
			isAdded=true;
		}
		catch (Exception e) {
			e.printStackTrace();
			log.error("Error in saveAttendanceTimeRestrictionBo method in AttendanceEntryTransactionImpl.class");
			log.error("Error is "+e.toString());
			transaction.rollback();
		}
		finally
		{
			if(session!=null)
			{
				session.close();
			}
		}
		log.debug("end of addSMSCriteria method in AttendanceEntryTransactionImpl.class");
		return isAdded;
	}

	@Override
	public AttendanceTimeRestrictionClasswise alreadyExist(AttendanceEntryForm attendanceEntryForm) throws Exception {

		log.debug("Txn Impl : Entering alreadyExist ");
		Session session = null;
		try {
			 session = HibernateUtil.getSession();
			Query query = session.createQuery("from AttendanceTimeRestrictionClasswise a where a.classSchemewise.id="+attendanceEntryForm.getClassId());	
			AttendanceTimeRestrictionClasswise list = (AttendanceTimeRestrictionClasswise) query.uniqueResult();
			 log.debug("Txn Impl : Leaving alreadyExist with success");
			 return list;
		 } catch (Exception e) {
			 log.debug("Txn Impl : Leaving alreadyExist with Exception");
			 throw e;
		 }
		 finally
			{
				if(session!=null)
				{
					session.flush();
				}
			}
			}

	@Override
	public List<AttendanceTimeRestrictionClasswise> getAttendanceTimeRestrictedList() throws Exception {

		log.debug("Txn Impl : Entering alreadyExist ");
		Session session = null;
		try {
			 session = HibernateUtil.getSession();
			Query query = session.createQuery("from AttendanceTimeRestrictionClasswise");	
			List<AttendanceTimeRestrictionClasswise> list = (List<AttendanceTimeRestrictionClasswise>) query.list();
			 log.debug("Txn Impl : Leaving alreadyExist with success");
			 return list;
		 } catch (Exception e) {
			 log.debug("Txn Impl : Leaving alreadyExist with Exception");
			 throw e;
		 }
		 finally
			{
				if(session!=null)
				{
					session.flush();
				}
			}
			}

	
	
	@Override
	public boolean updateAttendanceTimeRestrictionBo(
			AttendanceTimeRestrictionClasswise attBo) {
		log.debug("call of deleteAttendanceTimeRestriction method in AttendanceEntryTransactionImpl.class");
		boolean isUpdated=false;
		Session session=null;
		Transaction transaction=null;
		try
		{
			session=HibernateUtil.getSession();
			session.close();
			session=HibernateUtil.getSession();
			transaction=session.beginTransaction();
			transaction.begin();
			session.update(attBo);
			transaction.commit();
			isUpdated=true;
		}
		catch (Exception e) {
			e.printStackTrace();
			log.error("Error in deleteAttendanceTimeRestriction method in AttendanceEntryTransactionImpl.class");
			transaction.rollback();
		}
		finally
		{
			if(session!=null)
			{
				session.close();
			}
		}
		log.debug("end of updateSMSCriteria method in MobileSMSCriteriaTransactionImpl.class");
		return isUpdated;
	}

	@Override
	public boolean deleteAttendanceTimeRestriction(AttendanceEntryForm attendanceEntryForm) {
		log.debug("call of deleteSMSCriteria method in MobileSMSCriteriaTransactionImpl.class");
		boolean isDeleted=false;
		Session session=null;
		Transaction transaction=null;
		try
		{
			session=HibernateUtil.getSession();
			transaction=session.beginTransaction();
			transaction.begin();
			AttendanceTimeRestrictionClasswise bo=(AttendanceTimeRestrictionClasswise) 
			session.get(AttendanceTimeRestrictionClasswise.class, attendanceEntryForm.getAttTimeRestrictionId());
			bo.setIsActive(Boolean.FALSE);
			bo.setModifiedBy(attendanceEntryForm.getUserId());
			bo.setLastModifiedDate(new java.util.Date());
			session.update(bo);
			transaction.commit();
			isDeleted=true;
		}
		catch (Exception e) {
			e.printStackTrace();
			log.debug("Error in deleteSMSCriteria method in AttendanceEntryTransactionImpl.class");
			transaction.rollback();
		}
		finally
		{
			if(session!=null)
			{
				session.close();
			}
		}
		log.debug("end of deleteSMSCriteria method in AttendanceEntryTransactionImpl.class");
		return isDeleted;
	}

	@Override
	public List<AttendanceTimeRestrictionClasswise> getAttendanceTimeRestriction(
			String[] classes) throws Exception {


		log.debug("Txn Impl : Entering getAttendanceTimeRestriction ");
		Integer[] classIds=new Integer[classes.length];
	    int i=0;
	    for(String str:classes){
	    	classIds[i]=Integer.parseInt(str);//Exception in this line
	        i++;
	    }
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from AttendanceTimeRestrictionClasswise a where a.classSchemewise.id in (:classes) and a.isActive=1");	
			query.setParameterList("classes", classIds);
			List<AttendanceTimeRestrictionClasswise> list = (List<AttendanceTimeRestrictionClasswise>) query.list();
			 log.debug("Txn Impl : Leaving alreadyExist with success");
			 return list;
		 } catch (Exception e) {
			 log.debug("Txn Impl : Leaving getAttendanceTimeRestriction with Exception");
			 throw e;
		 }
		 finally
			{
				if(session!=null)
				{
					session.flush();
				}
			}
				}
	
	public List<Student> getStudentByClass(Set<Integer> classIds,java.util.Date attendanceDate) throws Exception {
		log.debug("Txn Impl : Entering getStudentByClass ");
		Session session = null;
		try {
			 //SessionFactory sessionFactory = InitSessionFactory.getInstance();
			 session = HibernateUtil.getSession();
			Query query = session.createQuery("from Student s where s.classSchemewise.id in (:classesIds) and isAdmitted = 1 and s.isActive = 1  and s.admAppln.isCancelled=0 and s.tcNo is null and (s.isHide =0 or s.isHide is null) and (s.admAppln.admissionDate<='"+attendanceDate+"' or s.admAppln.admissionDate is null)");	
			query.setParameterList("classesIds", classIds);
			
			 List<Student> list = query.list();
			 //session.close();
			 //sessionFactory.close();
			 log.debug("Txn Impl : Leaving getStudentByClass with success");
			 return list;
		 } catch (Exception e) {
			 log.debug("Txn Impl : Leaving getStudentByClass with Exception");
			 throw e;
		 }
		}

	public String getAttendanceEntryDay(BaseActionForm baseActionForm) throws Exception{
		log.debug("Txn Impl : Entering addAttendance ");
		Session session = null; 
		Transaction tx = null;
		String sqlDate=null;
		 String day=null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			 session = HibernateUtil.getSession();
			 tx = session.beginTransaction();
			 tx.begin();
			 sqlDate=CommonUtil.formatDate(baseActionForm.getDate());
			 String attQuery = "select  day from AttendanceEntryDay where is_active = 1 and date='"+sqlDate+"' ";
			 Query q =session.createQuery(attQuery);
             day=(String) q.uniqueResult();
	}
		catch (Exception e) {
			log.debug("Error during getting applicaition numbers..." + e);
			session.flush();
			//session.close();
			throw new ApplicationException(e);
		}
		return day;
	}
	
	@Override
	public Map<Integer, String> getClassesByTeacherForADay(int userId, String date) throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			java.util.Date attendaneDate = CommonUtil.ConvertStringToDate(date);
			StringBuilder sql = new StringBuilder("select");
			sql.append(" classes.id,");
			sql.append(" classes.name");
			sql.append(" from attendance_instructor");
			sql.append(" inner join attendance on attendance.id = attendance_instructor.attendance_id");
			sql.append(" inner join attendance_class on attendance_class.attendance_id = attendance.id");
			sql.append(" inner join class_schemewise ON class_schemewise.id = attendance_class.class_schemewise_id");
			sql.append(" inner join classes ON classes.id = class_schemewise.class_id");
			sql.append(" where attendance_instructor.users_id = :userId");
			sql.append(" and attendance.attendance_date = :attendaneDate");
			sql.append(" group by classes.id");
			Query query = session.createSQLQuery(sql.toString())
								 .setInteger("userId", userId)
								 .setDate("attendaneDate", attendaneDate);
			Iterator<Object[]> tuples = query.list().iterator();
			Map<Integer, String> classMap = new LinkedHashMap<Integer, String>();
			while (tuples.hasNext()) {
				Object[] obj =  tuples.next();
				classMap.put(Integer.parseInt(obj[0].toString()), obj[1].toString());
			}
			session.flush();
			classMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(classMap);

			return classMap;
		} catch (Exception e) {
			e.printStackTrace();
			if(session != null) {
				session.flush();
				session.close();
			}
			throw new ApplicationException();
		}
	}

	@Override
	public List<HolidayBO> checkHolidayDate() throws Exception {
		Session session=null;
		try{
			session=HibernateUtil.getSession();
			Query query=session.createQuery("from HolidayBO h where h.isActive=1");
			query.setFlushMode(FlushMode.COMMIT);
			List<HolidayBO> list=query.list();
			return list;
		}catch (Exception e) {
			log.error("Error during getting Holiday..." ,e);
			throw new ApplicationException(e);
		}
		
	}

	@Override
	public String getclassName(int classId)
			throws Exception {
		Session session = null;
		String className = null;
		try{
			session = HibernateUtil.getSession();
			String sql = "select c.name from Classes c where c.id=:cid";
			Query query = session.createQuery(sql)
			              .setInteger("cid", classId);
			className = (String)query.uniqueResult();
			return className;
		}catch(Exception ex){
			ex.printStackTrace();
			throw new ApplicationException();
		}
	}

	@Override
	public List<Period> getPeriodsForAttendanceEntryDay(String attendanceDayCheck, int classId) throws Exception {
		Session session = null;
		List<Period> periodList;

		try {
			session = HibernateUtil.getSession();
			Query query = session.createQuery("select ttp.period from TTPeriodWeek ttp where ttp.ttClasses.classSchemewise= :classId and ttp.weekDay= :weekDay  and ttp.period.isActive=1");
			query.setInteger("classId", classId);
			query.setString("weekDay", attendanceDayCheck);
			List<Period> list = query.list();
			session.flush();
			periodList = list;

		} catch (Exception e) {
			log.debug("Error during getting getPeriodsForAttendanceEntryDay numbers..." + e);
			session.flush();
			throw new ApplicationException(e);
		}
		return periodList;
	}

	@Override
	public boolean isDateBetweenCurriculumSchemeDuration(String[] classes, Date date,int year)throws Exception {
		
		Session session = null;
		boolean isValideDate=false;
		
		String intType ="";
		for(int i=0;i<classes.length;i++)
		{
			 intType = intType+classes[i].toString();
			 if(i<(classes.length-1))
			 {
				 intType = intType+",";
			 }
		}
		try{
			
			session = HibernateUtil.getSession();
			
			Query query = session.createQuery("from ClassSchemewise c where c.id in(:classIds) and  :attendanceDate between c.curriculumSchemeDuration.startDate and c.curriculumSchemeDuration.endDate and c.curriculumSchemeDuration.academicYear=:year");
			query.setString("classIds", intType);
			query.setDate("attendanceDate", date);
			query.setInteger("year", year);
			List<ClassSchemewise> list = query.list();
			
			if(list!=null && !list.isEmpty()) isValideDate=true;
			
			
		}catch (Exception e) {
			log.debug("Error during getting getPeriodsForAttendanceEntryDay numbers..." + e);
			session.flush();
			throw new ApplicationException(e);
		}
		return isValideDate;
	}

	@Override
	public List<TTUsers> getCoTeachersForAteendance(String sQuery) {
		Session session = null;
		List<TTUsers> ttUsersBo=new ArrayList<TTUsers>();
		try {
			session=HibernateUtil.getSession();
			Query query=session.createQuery(sQuery);
			ttUsersBo=query.list();
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
		return ttUsersBo;
	}
	
	public String getEmployeeDuty(AttendanceEntryForm attendanceEntryForm) throws Exception{
		log.debug("Txn Impl : Entering addAttendance ");
		Session session = null; 
		Transaction tx = null;
		String sqlDate=null;
		 String duty=null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			 session = HibernateUtil.getSession();
			 tx = session.beginTransaction();
			 tx.begin();
			 sqlDate=CommonUtil.formatDate(attendanceEntryForm.getAttendancedate());
			 String attQuery = "select  duty from EmployeeDutyPerformed where is_active = 1 and users.id ="+attendanceEntryForm.getUserId() +" and date='"+sqlDate+"' ";
			 Query q =session.createQuery(attQuery);
			 if (q.uniqueResult()!=null) {
	             duty=(String) q.uniqueResult();
			}else{
				duty="NO";
			}

	}
		catch (Exception e) {
			log.debug("Error during getting applicaition numbers..." + e);
			session.flush();
			//session.close();
			throw new ApplicationException(e);
		}
		return duty;
	}
	public List<AttendanceInstructor> getAttendanceEntryDetails(AttendanceEntryForm attendanceEntryForm) throws Exception {
		Session session = null;
		List<AttendanceInstructor> periodList;

		try {
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from AttendanceInstructor at where at.attendance.attendanceDate='"+CommonUtil.ConvertStringToSQLDate(attendanceEntryForm.getDutyPerformedDate())+"' and at.users.id="+attendanceEntryForm.getUserId());
			List<AttendanceInstructor> list = query.list();
			session.flush();
			periodList = list;

		} catch (Exception e) {
			log.debug("Error during getting getPeriodsForAttendanceEntryDay numbers..." + e);
			session.flush();
			throw new ApplicationException(e);
		}
		return periodList;
	}
}
