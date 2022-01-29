package com.kp.cms.transactionsimpl.attendance;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;

import com.kp.cms.bo.admin.Users;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.attendance.AttendanceTeacherReportForm;
import com.kp.cms.transactions.attandance.ITeacherClassAttendanceTxn;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.HibernateUtil;

public class TeacherClassAttendanceTxnImpl implements ITeacherClassAttendanceTxn{
	private static final Log log = LogFactory.getLog(TeacherClassAttendanceTxnImpl.class);
	/**
	 * 
	 */
	public static volatile TeacherClassAttendanceTxnImpl self=null;
	/**
	 * @return
	 * This method will return instance of this class
	 */
	public static TeacherClassAttendanceTxnImpl getInstance(){
		if(self==null)
			self= new TeacherClassAttendanceTxnImpl();
		return self;
	}
	/**
	 * 
	 */
	private TeacherClassAttendanceTxnImpl(){
		
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
			session = HibernateUtil.getSession();
			 studentSearchResult = session.createQuery(searchCriteria).list();
		} catch (Exception e) {
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		log.info("exit getTeacherAttendance..");
		return studentSearchResult;
	}
	public List getTeacherAttendanceNew(AttendanceTeacherReportForm attendanceTeacherReportForm,List<Integer> classesList) throws ApplicationException {
		log.info("entered getTeacherAttendance..");
		Session session = null;
		List studentSearchResult = null;
		try {
			session = HibernateUtil.getSession();
			String searchCriteria="select au.users.userName,ac.classSchemewise.classes.name,b.batchName,sum(au.attendance.hoursHeld)," +
			" s.code,s.name,ac.classSchemewise.classes.id, au.users.id,s.id,b.id " +
			" from AttendanceInstructor au" +
			" left join au.attendance.subject s" +
			" left join au.attendance.batch b" +
			" join au.attendance.attendanceClasses ac" +
			" where au.users.id in ("+attendanceTeacherReportForm.getUserId()+")" +
			" and au.attendance.isCanceled=0" +
			" and au.attendance.isMonthlyAttendance=0" +
			" and au.attendance.isMonthlyAttendance=0 and ac.classSchemewise.classes.id in (:classes)" +
			" group by b.id,s.id,ac.classSchemewise.classes.name,au.users.id";
			Query querys=session.createQuery(searchCriteria);
			querys.setParameterList("classes", classesList);
			studentSearchResult=querys.list();
			// studentSearchResult = session.createQuery(searchCriteria).list();
			//There will be issue for attendance entered through subject code group as it save as 2 different entries as the subject code is different
			//Check it later if it is an issue
		} catch (Exception e) {
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
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
	public List getPeriodDetails(int userId, String classId,int subjectId, String startDate, String endDate, String batchName) throws ApplicationException {
		log.info("entered getPeriodDetails..");
		Session session = null;
		List studentSearchResult = null;
		String searchCriteria = "";
		if(startDate!=null)
			searchCriteria = " and attendance.attendanceDate >= '"
					+ CommonUtil.ConvertStringToSQLDate(startDate)+"'" ;

		if(endDate!=null)
			searchCriteria = searchCriteria +  " and attendance.attendanceDate <= '" + CommonUtil.ConvertStringToSQLDate(endDate)+"'";
		if(batchName!=null && !batchName.isEmpty() && !batchName.equalsIgnoreCase("--"))
		searchCriteria = searchCriteria +  " and attendanceInstructors.attendance.batch.batchName='"+batchName+"'";
		try {
			session = HibernateUtil.getSession();
		/*	searchCriteria="select periods.period.periodName,periods.period.id,periods.period.classSchemewise.classes.id," +
					"attendance.id,attendance.attendanceDate,attendance.hoursHeld,attendance.attendanceType.name,attendance.subject.name from Classes classes" +
					" inner join classes.classSchemewises classSchemewises inner join classSchemewises.curriculumSchemeDuration curriculumSchemeDuration" +
					" inner join classSchemewises.attendanceClasses attendanceClasses inner join attendanceClasses.attendance attendance" +
					" inner join attendance.attendanceInstructors attendanceInstructors" +
					" inner join attendance.attendancePeriods periods " +
					" where  attendanceInstructors.users.id in ("+ userId +")" +
					" and attendance.isMonthlyAttendance = 0 and attendance.isCanceled = 0  " 
					+searchCriteria+" and classes.id in ("+classId+")  and attendance.subject.id="+subjectId ;*/
			searchCriteria="select ap.period.periodName, ap.period.id, c.id, au.attendance.id, au.attendance.attendanceDate, " +
					" au.attendance.hoursHeld, au.attendance.attendanceType.name, au.attendance.subject.name" +
					" from AttendanceInstructor au" +
					" join au.attendance a" +
					" join a.attendanceClasses ac join ac.classSchemewise cc" +
					" join cc.classes c" +
					" join a.attendancePeriods ap" +
					" where au.users.id in ("+ userId +")" +
					" and a.isCanceled=0" ;
			if(startDate!=null && !startDate.isEmpty() && endDate!=null && !endDate.isEmpty()){
				searchCriteria=searchCriteria+" and a.attendanceDate>='" + CommonUtil.ConvertStringToSQLDate(startDate)+"' and a.attendanceDate<='" + CommonUtil.ConvertStringToSQLDate(endDate)+"' ";
			}
			searchCriteria=searchCriteria+" and a.subject.id ="+subjectId+" and c.id in ("+classId+")";
			if(batchName!=null && !batchName.isEmpty() && !batchName.equalsIgnoreCase("--"))
				searchCriteria=searchCriteria+" and a.batch.batchName='"+batchName+"'";
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
		/*String query="select distinct(tcs.classId.classes.id) from TeacherClassSubject tcs " +
				"where tcs.teacherId in ("+teacherId+") " +
				"and tcs.year in ("+year+") " +
				"and tcs.classId.curriculumSchemeDuration.startDate<='"+ CommonUtil.ConvertStringToSQLDate(currentDate1)+"' " +
				"and tcs.classId.curriculumSchemeDuration.endDate>='"+ CommonUtil.ConvertStringToSQLDate(currentDate1)+"' " +
				"order by tcs.classId.classes.id";*/
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
		Date currentDate=new Date();
		String currentDate1=CommonUtil.formatDates(currentDate);
		try{
			session=HibernateUtil.getSession();
			
			String quer="select min(cur.startDate) from CurriculumSchemeDuration cur"+
                        " where cur.curriculumScheme.id in (select DISTINCT c.id from CurriculumScheme c"+ 
                        " where c.course.id in(select DISTINCT cs.curriculumScheme.course.id from CurriculumSchemeDuration cs"+ 
                        " join cs.curriculumScheme.course.classes classes"+
                        " where classes.id in (:classes)))"+
                        " and cur.academicYear = "+academicYear+" and cur.startDate <= '"+ CommonUtil.ConvertStringToSQLDate(currentDate1)+"'";
			Query querys=session.createQuery(quer);
			querys.setParameterList("classes", classes);
			startDate=(Date)querys.uniqueResult();
		}catch(Exception exception){
			log.error("error in get start Date ");
		}
		return startDate;
	}
	
	public Date getEndDate(List classes,int academicYear){
		Session session=null;
		Date endDate=null;
		Date currentDate=new Date();
		String currentDate1=CommonUtil.formatDates(currentDate);
		try{
			session=HibernateUtil.getSession();
			
			String quer="select min(cur.startDate) from CurriculumSchemeDuration cur"+
                        " where cur.curriculumScheme.id in (select DISTINCT c.id from CurriculumScheme c"+ 
                        " where c.course.id in(select DISTINCT cs.curriculumScheme.course.id from CurriculumSchemeDuration cs"+ 
                        " join cs.curriculumScheme.course.classes classes"+
                        " where classes.id in (:classes)))"+
                        " and cur.academicYear = "+academicYear+" and cur.endDate >= '"+ CommonUtil.ConvertStringToSQLDate(currentDate1)+"'";
			Query querys=session.createQuery(quer);
			querys.setParameterList("classes", classes);
			endDate=(Date)querys.uniqueResult();
		}catch(Exception exception){
			log.error("error in get start Date ");
		}
		return endDate;
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
	public List getUserIds(
			AttendanceTeacherReportForm attendanceTeacherReportForm) {
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
	
	public List getActivityPeriodDetails(int userId, String classId, String startDate, String endDate) throws ApplicationException {
		log.info("entered getPeriodDetails..");
		Session session = null;
		List studentSearchResult = null;
		String searchCriteria = "";
		if(startDate!=null)//Added by dilip
			searchCriteria = " and attendance.attendanceDate >= '" + CommonUtil.ConvertStringToSQLDate(startDate)+"'" ;
		
		if(endDate!=null)
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
					+searchCriteria+" and classes.id in ("+classId+") and attendance.subject.id=null ";
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
	@Override
	public List<Object[]> getPreviousClassesByTeacherId(int teacherId,int year) throws Exception {
		List<Object[]> classesList=null;
		Session session=null; 
		Date currentDate=new Date();
		String currentDate1=CommonUtil.formatDates(currentDate);
		try{
		session=HibernateUtil.getSession();
		String query="select distinct classes.id,date_format(curriculum_scheme_duration.start_date,'%d/%m/%Y') as sdt, date_format(curriculum_scheme_duration.end_date,'%d/%m/%Y') as edt from teacher_class_subject " +
				" inner join class_schemewise ON teacher_class_subject.class_schemewise_id = class_schemewise.id" +
				" inner join curriculum_scheme_duration ON class_schemewise.curriculum_scheme_duration_id = curriculum_scheme_duration.id" +
				" inner join classes ON class_schemewise.class_id = classes.id" +
				" where teacher_id="+teacherId+
				" and curriculum_scheme_duration.academic_year="+year+
				" and curriculum_scheme_duration.end_date < '"+ CommonUtil.ConvertStringToSQLDate(currentDate1)+"' " +
				" and classes.is_active=1" +
				" and teacher_class_subject.is_active=1";
		Query querys=session.createSQLQuery(query);
		classesList=querys.list();
		}catch(Exception exception){
			log.error("error in getClasses ");
		}
		return classesList;
	}
	
}
