package com.kp.cms.transactionsimpl.attendance;


import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.kp.cms.bo.admin.AcademicYear;
import com.kp.cms.bo.admin.AttendanceStudent;
import com.kp.cms.bo.admin.ClassSchemewise;
import com.kp.cms.bo.admin.MobileMessaging;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.attendance.AttendanceEntryForm;
import com.kp.cms.forms.attendance.NewAttendanceSmsForm;
import com.kp.cms.handlers.attendance.NewAttendanceSmsHandler;
import com.kp.cms.transactions.attandance.INewAttendanceSmsTransaction;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;

public class NewAttendanceSmsTransImpl implements INewAttendanceSmsTransaction    
{
	private static volatile NewAttendanceSmsTransImpl newAttendanceSmsTransImpl = null;
	private static final Log log = LogFactory.getLog(NewAttendanceSmsTransImpl.class);
	
	public static NewAttendanceSmsTransImpl getInstance() 
	{
		if (newAttendanceSmsTransImpl == null) 
		{
			newAttendanceSmsTransImpl = new NewAttendanceSmsTransImpl();
			return newAttendanceSmsTransImpl;
		}
		return newAttendanceSmsTransImpl;
	}

	public List<Object[]> getAbsentees(int year,String[] classes,java.sql.Date date) 
	{

		Session session = null;
		String intType ="";
		for(int i=0;i<classes.length;i++)
		{
			 intType = intType+classes[i].toString();
			 if(i<(classes.length-1))
			 {
				 intType = intType+",";
			 }
		}
		List<Object[]> list;
		try {
			session = InitSessionFactory.getInstance().openSession();
			String sql="";
			sql="SELECT subject.id,attendance_period.period_id,attendance_class.class_schemewise_id," +
					" attendance_student.student_id,curriculum_scheme.year,attendance_student.id as attStudentId"+
					" FROM    ((((((( student student INNER JOIN class_schemewise class_schemewise"+
		            " ON (student.class_schemewise_id =class_schemewise.id))"+
		            " INNER JOIN attendance_class attendance_class ON (attendance_class.class_schemewise_id =class_schemewise.id))"+
		            " INNER JOIN attendance attendance ON (attendance_class.attendance_id = attendance.id))"+
		            " INNER JOIN attendance_period attendance_period ON (attendance_period.attendance_id = attendance.id))"+
		            " INNER JOIN attendance_student attendance_student ON (attendance_student.attendance_id = attendance.id) AND (attendance_student.student_id = student.id))"+
		            " INNER JOIN curriculum_scheme_duration curriculum_scheme_duration ON (class_schemewise.curriculum_scheme_duration_id =curriculum_scheme_duration.id))"+
		            " INNER JOIN curriculum_scheme curriculum_scheme ON (curriculum_scheme_duration.curriculum_scheme_id =curriculum_scheme.id))"+
		            " INNER JOIN subject subject ON (attendance.subject_id = subject.id) " +
		            " where " +
		            " attendance_student.is_present=0 " +
		            " and student.class_schemewise_id in("+intType+") " +
		            " and curriculum_scheme_duration.academic_year="+year+
		            " and (attendance_student.is_cocurricular_leave=0 or attendance_student.is_cocurricular_leave is null)"+
		            " and (attendance_student.is_on_leave=0 or attendance_student.is_on_leave is null)"+	
		            " and (attendance_student.is_sms_sent=0 or attendance_student.is_sms_sent is null)"+
		            " and attendance.is_canceled=0"+
		            " and attendance.attendance_date='"+date+		            
		            "' order by attendance_student.student_id ";
			Query query = session
					.createSQLQuery(sql);
			list = query.list();
			return list;
		} catch (Exception e) {
			log.debug("Exception" + e.getMessage());
			session.flush();
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		return new ArrayList<Object[]>();
	
	}
	public List<Integer> getAllPeriodIdsByInput(AttendanceEntryForm attendanceEntryForm) throws Exception {
		log.debug("Txn Impl : Entering getClassNamesByIds ");
		Session session = null;
		List<Integer> list=new ArrayList<Integer>();
		try {
			 session = HibernateUtil.getSession();
			 String[] periods=attendanceEntryForm.getPeriods();
			 String intType ="";
				for(int i=0;i<periods.length;i++){
					 intType = intType+periods[i];
					 if(i<(periods.length-1)){
						 intType = intType+",";
					 }
				}
				 String[] classes=attendanceEntryForm.getClasses();
				 String intType1 ="";
					for(int i=0;i<classes.length;i++){
						 intType1 = intType1+classes[i];
						 if(i<(classes.length-1)){
							 intType1 = intType1+",";
						 }
					}
			 list = session.createQuery("select p.id" +
			 		" from Period p where p.periodName in (select p1.periodName from Period p1 where p1.id in ("+intType+"))" +
			 		" and p.isActive=1 and p.classSchemewise.id in ("+intType1+")").list();
			return list;
		 } catch (Exception e) {
			 log.debug("Txn Impl : Leaving getClassNamesByIds with Exception");
			 throw e;
		 }
	}

	
	public List<Integer> getAllPeriodIdsByInput(NewAttendanceSmsForm newAttendanceSmsForm) 
	{
		// TODO Auto-generated method stub
		return null;
	}

	public String getPeriodNamesById(String que) throws Exception 
	{

		log.debug("Txn Impl : Entering getSubjectNameById ");
		Session session = null;
		try {
			 session = HibernateUtil.getSession();
			 Query query = session.createQuery(que);
			 List<String> list =query.list();
			 StringBuffer periodNames=new StringBuffer();
			 if(list!=null){
				 Iterator<String> itr=list.iterator();
				 int i=1;
				 int size=list.size();
				 while (itr.hasNext()) {
					 String objects = (String) itr.next();
					if(objects!=null){
						periodNames=periodNames.append(objects.charAt(0)+"-"+objects.charAt(objects.length()-1));
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

	
	public boolean updateAttendanceStudent(List<AttendanceStudent> atteStudList) 
	{

		log.debug("inside updateState");
		Session session = null;
		Transaction tx = null;
		AttendanceStudent attStud=null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			tx.begin();
			Iterator<AttendanceStudent> itr=atteStudList.iterator();
			while(itr.hasNext())
			{
				attStud=itr.next();
				attStud.setIsSmsSent(Boolean.TRUE);
				session.update(attStud);
				attStud=null;
			}
			
			tx.commit();
			if (session != null) {
				session.flush();
				//session.close();
			}
			log.debug("leaving updateState");
			return true;
		} catch (Exception e) {
			log.error("Error during getting state..." , e);
			if (tx != null) {
				tx.rollback();
			}
			if (session != null) {
				session.flush();
				//session.close();
			}
			return false;
		}
	
	}

	
	public List<AttendanceStudent> getAttendanceStudent(String attStuId) throws Exception 
	{

        Session session;
        Transaction transaction;
       List<AttendanceStudent> attendanceStudentList=new ArrayList<AttendanceStudent>();
        log.info("Start of getAcademicYear of AcademicYearTransactionImpl");
        session = null;
        transaction = null;
        try
        {
            session = HibernateUtil.getSession();
            transaction = session.beginTransaction();
            session = HibernateUtil.getSession();
            String hqlQuery="from AttendanceStudent where id in("+attStuId+")";
            Query query = session.createQuery(hqlQuery);
            attendanceStudentList = query.list();
            transaction.commit();
        }
        catch(Exception e)
        {
            if(transaction != null)
            {
                transaction.rollback();
            }
            log.error((new StringBuilder("Exception occured in getAcademicYear in AcademicYearTransactionImpl : ")).append(e).toString());
            throw new ApplicationException(e);
        }
        finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
        log.info("End of of getAcademicYear of AcademicYearTransactionImpl");
        return attendanceStudentList;
    
	}


	public String getPeriodNamesByIdNew(String query1) throws Exception {
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
				 List<String> peridList=new ArrayList<String>();
				 while (itr.hasNext()) {
					 String objects = (String) itr.next();
					if(objects!=null && !peridList.contains(objects)){
						periodNames=periodNames.append(objects);
						if(i<size){
							periodNames.append(",");
						}
					}
					i=i+1;
					peridList.add(objects);
				}
			 }
			 log.debug("Txn Impl : Leaving getSubjectNameById with success");
			 return periodNames.toString();
		 } catch (Exception e) {
			 log.debug("Txn Impl : Leaving getSubjectNameById with Exception");
			 throw e;
		 }
	}
	public String getSubjectNamesByIdNew(String query1) throws Exception {
		log.debug("Txn Impl : Entering getSubjectNameById ");
		Session session = null;
		try {
			 session = HibernateUtil.getSession();
			 Query query = session.createQuery(query1);
			 List<String> list =query.list();
			 StringBuffer subjectNames=new StringBuffer();
			 if(list!=null){
				 Iterator<String> itr=list.iterator();
				 int i=1;
				 int size=list.size();
				 List<String> subList=new ArrayList<String>();
				 while (itr.hasNext()) {
					 String objects = (String) itr.next();
					if(objects!=null && !subList.contains(objects)){
						subjectNames=subjectNames.append(objects);
						if(i<size){
							subjectNames.append(",");
						}
					}
					i=i+1;
					subList.add(objects);
				}
			 }
			 log.debug("Txn Impl : Leaving getSubjectNameById with success");
			 return subjectNames.toString();
		 } catch (Exception e) {
			 log.debug("Txn Impl : Leaving getSubjectNameById with Exception");
			 throw e;
		 }
	}
	
	public List<Integer> getStudentIds(java.sql.Date attDate) throws Exception 
	{
		Session session;
        Transaction transaction;
        List<Integer> studentIdList=new ArrayList<Integer>();
        session = null;
        transaction = null;
        try
        {
            session = HibernateUtil.getSession();
            transaction = session.beginTransaction();
            session = HibernateUtil.getSession();
            String hqlQuery="select studentId from MobileMessaging where attendanceDate='"+attDate+"'";
            Query query = session.createQuery(hqlQuery);
            studentIdList = query.list();
            transaction.commit();
        }
        catch(Exception e)
        {
            if(transaction != null)
            {
                transaction.rollback();
            }
           throw new ApplicationException(e);
        }
        finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
        log.info("End of of getAcademicYear of AcademicYearTransactionImpl");
        return studentIdList;
	}

	@Override
	public List<Object[]> getAbsenteesForSms(int year, String[] classes,java.sql.Date date, String dayOfthePeriods) throws Exception {


		Session session = null;
		String intType ="";
		for(int i=0;i<classes.length;i++)
		{
			 intType = intType+classes[i].toString();
			 if(i<(classes.length-1))
			 {
				 intType = intType+",";
			 }
		}
		List<Object[]> list;
		try {
			session = InitSessionFactory.getInstance().openSession();
			String sql="";
			
			sql="select student.id as stud_id, "+
						"	tt_period_week.week_day,	"+
						"	tt_subject_batch.subject_id,	"+
						"	subject.name as subject,	"+
						"	period.id as period_id,	"+
						"	period.period_name,	"+
						"	tt_class.class_schemewise_id,	"+
						"	classes.id as class_id,	"+
						"	classes.name as class,	"+
						"	classes.term_number,	"+
						"	personal_data.parent_mob_2	"+
						"	FROM tt_subject_batch 	"+
						"	INNER JOIN  tt_period_week ON (tt_subject_batch.tt_period_id = tt_period_week.id)	"+
						"	INNER JOIN tt_class ON (tt_period_week.tt_class_id = tt_class.id)	"+
						"	inner join class_schemewise on (tt_class.class_schemewise_id=class_schemewise.id)	"+
						"	INNER JOIN classes ON (class_schemewise.class_id = classes.id) "+
						"	INNER JOIN course ON (classes.course_id = course.id)	"+
						"	INNER JOIN program ON (course.program_id = program.id)	"+
						"	INNER JOIN program_type ON (program.program_type_id = program_type.id)	"+
						"	inner join subject on(subject.id=tt_subject_batch.subject_id)	"+
						"	inner join curriculum_scheme_duration ON class_schemewise.curriculum_scheme_duration_id = curriculum_scheme_duration.id	"+
						"	inner join period on period.id=tt_period_week.period_id	"+
						"	inner join student on student.class_schemewise_id= class_schemewise.id	"+
						"	inner join adm_appln on student.adm_appln_id = adm_appln.id	"+
						"	inner join personal_data on adm_appln.personal_data_id = personal_data.id	"+
						"	where period.id not in (  SELECT    period.id	"+
						"	FROM    period period	"+
						"	INNER JOIN class_schemewise ON period.class_schemewise_id = class_schemewise.id	"+
						"	INNER JOIN attendance_period ON attendance_period.period_id = period.id	"+
						"	INNER JOIN attendance ON attendance_period.attendance_id = attendance.id	"+
						"	INNER JOIN attendance_class ON attendance_class.attendance_id = attendance.id	"+
						"	INNER JOIN classes class ON class_schemewise.class_id = class.id	"+
						"	INNER JOIN attendance_instructor ON attendance_instructor.attendance_id = attendance.id	"+
						"	INNER JOIN users ON (attendance_instructor.users_id = users.id)	"+
						"	where tt_subject_batch.is_active=1	"+
						"	and  users.is_active=1	"+
						"	and  period.is_active=1	"+
						"	and  class.is_active=1	"+
						"	and  tt_class.is_active=1	"+
						"	and class.id=classes.id	"+
						"	and  date(attendance.attendance_date)='"+date+"'	"+
						"	) 	"+
							
						"	and curriculum_scheme_duration.academic_year="+year +
						"	and classes.id in ("+intType+")	"+
						"	and tt_period_week.week_day='"+dayOfthePeriods+"' "+ 
						"	and student.id not in (select student_id from EXAM_student_detention_rejoin_details where (discontinued=1 || detain=0 || rejoin is null))		"+	

						"	union all "+

						"	SELECT    student.id as stud_id, "+
						"	Dayname(attendance.attendance_date) as week_day, "+
						"	subject.id as subject_id, "+
						"	subject.name as subject, "+
						"	period.id as period_id, "+
						"	period.period_name,  "+
						"	class_schemewise.id as class_schemewise_id,  "+
						"	classes.id as class_id,	"+
						"	classes.name as class,	"+
						"	classes.term_number,	"+
						"	personal_data.parent_mob_2	"+
						"	FROM    period period	"+
						"	INNER JOIN class_schemewise ON period.class_schemewise_id = class_schemewise.id	"+
						"	INNER JOIN attendance_period ON attendance_period.period_id = period.id	"+
						"	INNER JOIN attendance ON attendance_period.attendance_id = attendance.id "+
						"	INNER JOIN attendance_class ON attendance_class.attendance_id = attendance.id "+
						"	INNER JOIN classes ON class_schemewise.class_id = classes.id "+
						"	INNER JOIN attendance_instructor ON attendance_instructor.attendance_id = attendance.id "+
						"	inner join attendance_student on attendance_student.attendance_id = attendance.id "+
						"	inner join student on student.class_schemewise_id = class_schemewise.id and attendance_student.student_id = student.id "+
						"	inner join subject ON attendance.subject_id = subject.id "+
						"	inner join adm_appln on student.adm_appln_id = adm_appln.id "+
						"	inner join personal_data on adm_appln.personal_data_id = personal_data.id "+
						"	where (attendance_student.is_present=0 && attendance_student.is_cocurricular_leave=0) "+
						"	and  period.is_active=1 "+
						"	and  classes.is_active=1 "+
						"	and student.id not in (select student_id from EXAM_student_detention_rejoin_details where (discontinued=1 || detain=0 || rejoin is null)) "+	
						"	and  attendance.attendance_date='"+date+"' "+  
						"	and classes.id in ("+intType+")	"+
						"	group by student.id,period.id order by stud_id ";
			Query query = session
					.createSQLQuery(sql);
			list = query.list();
			return list;
		} catch (Exception e) {
			log.debug("Exception" + e.getMessage());
			session.flush();
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		return new ArrayList<Object[]>();
	
	
	}

}
