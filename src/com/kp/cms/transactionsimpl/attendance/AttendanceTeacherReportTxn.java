package com.kp.cms.transactionsimpl.attendance;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;

import com.kp.cms.bo.admin.AttendanceInstructor;
import com.kp.cms.bo.admin.Users;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.attendance.AttendanceTeacherReportForm;
import com.kp.cms.to.admin.StudentTO;
import com.kp.cms.to.attendance.PeriodTO;
import com.kp.cms.transactions.attandance.IAttendanceTeacherReportTxn;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.HibernateUtil;

public class AttendanceTeacherReportTxn implements IAttendanceTeacherReportTxn{
	private static final Log log = LogFactory.getLog(AttendanceTeacherReportTxn.class);
	/**
	 * 
	 */
	public static volatile AttendanceTeacherReportTxn self=null;
	/**
	 * @return
	 * This method will return instance of this class
	 */
	public static AttendanceTeacherReportTxn getInstance(){
		if(self==null)
			self= new AttendanceTeacherReportTxn();
		return self;
	}
	/**
	 * 
	 */
	private AttendanceTeacherReportTxn(){
		
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.attandance.IAttendanceSummaryReportTxnImpl#getStudentAttendance(java.lang.String)
	 * This method will get teacher details
	 */
	public List getTeacherAttendance(String searchCriteria) throws ApplicationException {
		log.info("entered getTeacherAttendance..");
		Session session = null;
		List studentSearchResult = null;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			 studentSearchResult = session.createQuery(searchCriteria).list();
		} catch (Exception e) {
			log.error("error while getting the student attendance results  "+e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		log.info("exit getTeacherAttendance..");
		return studentSearchResult;
	}

	/**
	 * 
	 * @param userId
	 * @param classId
	 * @param subjectId
	 * @return
	 * @throws ApplicationException
	 */
	public List getPeriodDetails(int userId, int classId,int subjectId, String startDate, String endDate, String batchName) throws ApplicationException {
		log.info("entered getPeriodDetails..");
		Session session = null;
		List studentSearchResult = null;
		String searchCriteria = "";
		if(startDate!=null && !startDate.isEmpty() && endDate!=null && !endDate.isEmpty()){
		searchCriteria = " and attendance.attendanceDate >= '"
				+ CommonUtil.ConvertStringToSQLDate(startDate)+"'" ;

		searchCriteria = searchCriteria +  " and attendance.attendanceDate <= '" + CommonUtil.ConvertStringToSQLDate(endDate)+"'";
		}
		if(batchName!=null && !batchName.isEmpty() && !batchName.equalsIgnoreCase("--"))
		searchCriteria = searchCriteria +  " and attendanceInstructors.attendance.batch.batchName='"+batchName+"'";
		try {
			session = HibernateUtil.getSession();
			searchCriteria="select periods.period.periodName,periods.period.id,periods.period.classSchemewise.classes.id," +
					"attendance.id,attendance.attendanceDate,attendance.hoursHeld,attendance.attendanceType.name,attendance.subject.name from Classes classes" +
					" inner join classes.classSchemewises classSchemewises inner join classSchemewises.curriculumSchemeDuration curriculumSchemeDuration" +
					" inner join classSchemewises.attendanceClasses attendanceClasses inner join attendanceClasses.attendance attendance" +
					" inner join attendance.attendanceInstructors attendanceInstructors" +
					" inner join attendance.attendancePeriods periods " +
					" where  attendanceInstructors.users.id in ("+ userId +")" +
					" and attendance.isMonthlyAttendance = 0 and attendance.isCanceled = 0  " 
					+searchCriteria+" and classes.id="+classId+"  and attendance.subject.id="+subjectId ;
					
			
			 studentSearchResult = session.createQuery(searchCriteria ).list();
			
		} catch (Exception e) {
			log.error("error in getPeriodDetails "+e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		log.info("exit getPeriodDetails..");
		return studentSearchResult;
	}
	// getting userName from users table by using userId
	public String getUserName(int userId){
		log.info("getting username");
		Session session=null;
		String search="";
		String userName="";
		Users users=null;
		try{
			session=HibernateUtil.getSession();
			search="from Users u where u.id = "+userId;
			Query query=session.createQuery(search);
			users=(Users)query.uniqueResult();
			userName=users.getUserName();
		}catch(Exception exception){
			log.error("error in getUserName ");
		}
		finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		return userName;
	}
	public List<Integer> getClassesByTeacherId(int teacherId,int year){
		List<Integer> classesList=new ArrayList<Integer>();
		Session session=null; 
		
		try{
			session=HibernateUtil.getSession();
		String query="select tcs.classId.classes.id from TeacherClassSubject tcs where tcs.teacherId in ( "+teacherId+" ) and tcs.year in ("+year+") ";
		Query querys=session.createQuery(query);
		classesList=querys.list();
		}catch(Exception exception){
			log.error("error in getClasses ");
		}
		return classesList;
	}
	public List<Integer> getClassesByTeacherIdReport(int teacherId,int year){
		List<Integer> classesList=new ArrayList<Integer>();
		Session session=null; 
		Date currentDate=new Date();
		String currentDate1=CommonUtil.formatDates(currentDate);
		try{
			session=HibernateUtil.getSession();
		//String query="select tcs.classId.classes.id from TeacherClassSubject tcs where tcs.teacherId in ( "+teacherId+" ) and tcs.year in ("+year+") ";
		String query="select distinct(tcs.classId.classes.id) from TeacherClassSubject tcs " +
		"where tcs.teacherId in ("+teacherId+") " +
		"and tcs.year in ("+year+") " +
		"and tcs.classId.curriculumSchemeDuration.startDate<='"+ CommonUtil.ConvertStringToSQLDate(currentDate1)+"' " +
		"and tcs.classId.curriculumSchemeDuration.endDate>='"+ CommonUtil.ConvertStringToSQLDate(currentDate1)+"' " +
		"order by tcs.classId.classes.id";
		Query querys=session.createQuery(query);
		classesList=querys.list();
		}catch(Exception exception){
			log.error("error in getClasses ");
		}
		return classesList;
	}
	
	public Date getStartDate(List classes,int academicYear){
		Session session=null;
		Date startDate=null;
		try{
			session=HibernateUtil.getSession();
			
			String quer="select min(cur.startDate) from CurriculumSchemeDuration cur"+
                        " where cur.curriculumScheme.id in (select DISTINCT c.id from CurriculumScheme c"+ 
                        " where c.course.id in(select DISTINCT cs.curriculumScheme.course.id from CurriculumSchemeDuration cs"+ 
                        " join cs.curriculumScheme.course.classes classes"+
                        " where classes.id in (:classes)))"+
                        " and cur.academicYear = "+academicYear;
			Query querys=session.createQuery(quer);
			querys.setParameterList("classes", classes);
			startDate=(Date)querys.uniqueResult();
		}catch(Exception exception){
			log.error("error in get start Date ");
		}
		return startDate;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.attandance.IAttendanceTeacherReportTxn#getAbsentStudents(com.kp.cms.forms.attendance.AttendanceTeacherReportForm)
	 */
	public List getAbsentStudents(AttendanceTeacherReportForm attendanceTeacherReportForm)throws Exception{
		Session session=null;
		List studentTO=null;
		String query="";
		try{
			session=HibernateUtil.getSession();
			if(attendanceTeacherReportForm.getSubjectId()!=null && !attendanceTeacherReportForm.getSubjectId().isEmpty()){
			 query="select DISTINCT ass.student.admAppln.personalData.firstName,ass.student.registerNo,ass.attendance,ac.classSchemewise.id"+
			" from AttendanceStudent ass inner join ass.attendance.attendanceClasses ac where ass.attendance.id in ("+attendanceTeacherReportForm.getAttendanceId()+") and ass.attendance.subject.id in ("+attendanceTeacherReportForm.getSubjectId()+")"+  
 " and ass.isPresent in (0) and ac.classSchemewise.classes.id in("+attendanceTeacherReportForm.getClassId()+") order by ass.student.registerNo";
			}else{
				query="select DISTINCT ass.student.admAppln.personalData.firstName,ass.student.registerNo,ass.attendance,ac.classSchemewise.id"+
				" from AttendanceStudent ass inner join ass.attendance.attendanceClasses ac where ass.attendance.id in ("+attendanceTeacherReportForm.getAttendanceId()+") and ass.attendance.subject.id =null"+  
	 " and ass.isPresent in (0) and ac.classSchemewise.classes.id in("+attendanceTeacherReportForm.getClassId()+") order by ass.student.registerNo";
			}
            studentTO=session.createQuery(query).list();
            
		}catch(Exception exception){
			log.error("error in getting students ");
		}
		return studentTO;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.attandance.IAttendanceTeacherReportTxn#getUserIds(com.kp.cms.forms.attendance.AttendanceTeacherReportForm)
	 */
	@Override
	public List getUserIds(AttendanceTeacherReportForm attendanceTeacherReportForm) {
		Session session=null;
		List useId=null;
		try{
		String str="select tc.classId.id from TeacherClass tc where tc.teacherId = "+attendanceTeacherReportForm.getUserId();
		session=HibernateUtil.getSession();
		Query q=session.createQuery(str);
		List schemeid=q.list();
		StringBuilder classSchemes = new StringBuilder();
		String classes="";
		if(!schemeid.isEmpty()){
		Iterator it=schemeid.iterator();
		while(it.hasNext()){
			classSchemes.append(",").append(it.next());
		}
		String schemes=classSchemes.toString().replaceFirst(",", " ");
		String st="select c.classes.id from ClassSchemewise c where c.id in ("+schemes+")";
		Query qquery=session.createQuery(st);
		List classList=qquery.list();
		Iterator its=classList.iterator();
		while(its.hasNext()){
			classes=classes+","+its.next();
		}
		String classess=classes.replaceFirst(",", " ");
		attendanceTeacherReportForm.setClassSchemes(schemes);
		attendanceTeacherReportForm.setClasses(classess);
		String strs="select inst.users.id from AttendanceClass ac join ac.attendance.attendanceInstructors inst"+
			" where ac.classSchemewise.id in (:scheme) group by inst.users.id";
        Query qu=session.createQuery(strs);
        qu.setParameterList("scheme", schemeid);
        useId=qu.list();
		}else{
			useId=schemeid;
		}
		}catch(Exception exception){
			log.error("error in getting userIds ");
		}
		return useId;
	}
	public List getUserIdsFromTeacherDepartment(AttendanceTeacherReportForm attendanceTeacherReportForm){
		Session session=null;
		List users=null;
		try{
			String query="select u.id from Users u where u.employee.department.id in" +
					" (select td.departmentId.id from TeacherDepartment td where td.teacherId.id in ("+attendanceTeacherReportForm.getUserId()+"))"; 
			session=HibernateUtil.getSession();
			Query quer=session.createQuery(query);
			users=quer.list();
		}catch(Exception exception){
			log.error("error in getting userIds from TeacherDepartment");
		}
		return users;
	}
	
	public List getActivityPeriodDetails(int userId, int classId, String startDate, String endDate) throws ApplicationException {
		log.info("entered getPeriodDetails..");
		Session session = null;
		List studentSearchResult = null;
		String searchCriteria = "";
		searchCriteria = " and attendance.attendanceDate >= '"
				+ CommonUtil.ConvertStringToSQLDate(startDate)+"'" ;

		searchCriteria = searchCriteria +  " and attendance.attendanceDate <= '" + CommonUtil.ConvertStringToSQLDate(endDate)+"'";
		try {
			session = HibernateUtil.getSession();
			searchCriteria="select periods.period.periodName,periods.period.id,periods.period.classSchemewise.classes.id," +
					"attendance.id,attendance.attendanceDate,attendance.hoursHeld,attendance.attendanceType.name from Classes classes" +
					" inner join classes.classSchemewises classSchemewises inner join classSchemewises.curriculumSchemeDuration curriculumSchemeDuration" +
					" inner join classSchemewises.attendanceClasses attendanceClasses inner join attendanceClasses.attendance attendance" +
					" inner join attendance.attendanceInstructors attendanceInstructors" +
					" inner join attendance.attendancePeriods periods " +
					" where  attendanceInstructors.users.id in ("+ userId +")" +
					" and attendance.isMonthlyAttendance = 0 and attendance.isCanceled = 0  " 
					+searchCriteria+" and classes.id="+classId+" and attendance.subject.id=null ";
					//+" and attendanceInstructors.attendance.subject.id="+subjectId;
//			searchCriteria = "select attPeriod.period.periodName, attPeriod.period.id, att.id,"+
//							" att.attendanceDate   from AttendancePeriod attPeriod " + 
//							" inner join attPeriod.attendance att " +
//							" inner join att.attendanceInstructors user " +
//							" where att.subject.id = " + subjectId +
//							" and attPeriod.period.classSchemewise.classes.id =  " + classId + 
//							" and user.users.id = "  " and att.isCanceled = 0 "  + searchCriteria;
			
			 studentSearchResult = session.createQuery(searchCriteria ).list();
			
		} catch (Exception e) {
			log.error("error in getPeriodDetails "+e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		log.info("exit getPeriodDetails..");
		return studentSearchResult;
	}
	@Override
	public String getEmpId(int userId) throws Exception {
		Session session=null;
		String empId="";
		try{
		   session=HibernateUtil.getSession();
		   String quer="select users.employee.id from Users users where users.id="+userId;
		   Query query=session.createQuery(quer);
		   empId=query.uniqueResult().toString();
		}catch(Exception e){
			log.error("error in getting empId "+e);
		}
		return empId;
	}
	
}
