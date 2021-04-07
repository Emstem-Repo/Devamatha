package com.kp.cms.helpers.attendance;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Attendance;
import com.kp.cms.bo.admin.AttendanceInstructor;
import com.kp.cms.bo.admin.AttendancePeriod;
import com.kp.cms.forms.attendance.AttendanceTeacherReportForm;
import com.kp.cms.to.attendance.AttendanceTeacherReportTO;
import com.kp.cms.to.attendance.PeriodTO;
import com.kp.cms.to.attendance.StudentAbsentDetailsTO;
import com.kp.cms.transactions.attandance.ITeacherClassAttendanceTxn;
import com.kp.cms.transactionsimpl.attendance.TeacherClassAttendanceTxnImpl;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.CurrentAcademicYear;
import com.kp.cms.utilities.TeacherReportComparator;

public class TeacherClassAttendanceHelper {
	private static final Log log = LogFactory.getLog(TeacherClassAttendanceHelper.class);
	private static final String FROM_DATEFORMAT = "dd/MM/yyyy";
	private static final String SQL_DATEFORMAT = "yyyy-MM-dd";
	private static Map<Integer, String> pMap = null;
	static {
		pMap = new HashMap<Integer, String>();
		pMap.put(13, "01");
		pMap.put(14, "02");
		pMap.put(15, "03");
		pMap.put(16, "04");
		pMap.put(17, "05");
		pMap.put(18, "06");
		pMap.put(19, "07");
		pMap.put(20, "08");
		pMap.put(21, "09");
		pMap.put(22, "10");
		pMap.put(23, "11");
		pMap.put(24, "12");
	}
	/**
	 * @param attendanceSummaryReportForm
	 * @return
	 * This method will build dynamic query
	 */
	private static String commonSearchMonthly(AttendanceTeacherReportForm attendanceTeacherReportForm) {
		log.info("entered commonSearchMonthly..");
		String searchCriteria = "";
		
		if (attendanceTeacherReportForm.getTeacherType().length > 0) {
			String [] tempArray = attendanceTeacherReportForm.getTeacherType();
			StringBuilder attType = new StringBuilder();
			for(int i=0;i<tempArray.length;i++){
				 attType.append(tempArray[i]);
				 if(i<(tempArray.length-1)){
					 attType.append(",");
				 }
			}
		
		String teacher = " attendanceInstructors.users.id in ("
				+ attType +")";		
		searchCriteria = searchCriteria + teacher;
	}
	
		
		if (attendanceTeacherReportForm.getStartDate().trim().length() > 0) {
			String startDate = " and attendance.isCanceled = 0 and attendance.isMonthlyAttendance = 1 and attendance.attendanceDate >= '"
					+ CommonUtil.ConvertStringToSQLDate(attendanceTeacherReportForm.getStartDate())+"'" ;
			searchCriteria = searchCriteria + startDate;
		}

		if (attendanceTeacherReportForm.getEndDate().trim().length() > 0) {
			String endDate = " and attendance.attendanceDate <= '"
					+ CommonUtil.ConvertStringToSQLDate(attendanceTeacherReportForm.getEndDate())+"'";
			searchCriteria = searchCriteria + endDate;
		}
		log.info("exit commonSearchMonthly..");
		return searchCriteria;
	}
	
	/**
	 * @param attendanceSummaryReportForm
	 * @return
	 * This method will build dynamic query
	 */
	private static String commonSearch(AttendanceTeacherReportForm attendanceTeacherReportForm) {
		log.info("entered commonSearch..");
		String searchCriteria = "";
		
		if (attendanceTeacherReportForm.getTeacherType().length > 0) {
			String [] tempArray = attendanceTeacherReportForm.getTeacherType();
			StringBuilder attType=new StringBuilder();
			for(int i=0;i<tempArray.length;i++){
				 attType.append(tempArray[i]);
				 if(i<(tempArray.length-1)){
					 attType.append(",");
				 }
			}
		
		String teacher = " attendanceInstructors.users.id in ("
				+ attType +")";		
		searchCriteria = searchCriteria + teacher;
	}
	
		
		if (attendanceTeacherReportForm.getStartDate().trim().length() > 0) {
			String startDate = " and attendance.isMonthlyAttendance = 0 and attendance.attendanceDate >= '"
					+ CommonUtil.ConvertStringToSQLDate(attendanceTeacherReportForm.getStartDate())+"'" ;
			searchCriteria = searchCriteria + startDate;
		}

		if (attendanceTeacherReportForm.getEndDate().trim().length() > 0) {
			String endDate = " and attendance.attendanceDate <= '"
					+ CommonUtil.ConvertStringToSQLDate(attendanceTeacherReportForm.getEndDate())+"'";
			searchCriteria = searchCriteria + endDate;
		}
		log.info("exit commonSearch..");
		return searchCriteria;
	}

	/**
	 * @param studentSearchForm
	 * @return
	 * This method will give final query
	 */
	public static String getSelectionSearchCriteria(
			AttendanceTeacherReportForm studentSearchForm) {
		log.info("entered getSelectionSearchCriteria..");
		String statusCriteria = commonSearch(studentSearchForm);
		
		String searchCriteria= "";
		
//		String ednJoin = "";
		
		searchCriteria = "select classes.name, attendanceInstructors.users.userName, subject.name, subject.code, sum(attendance.hoursHeld)," +
				" classes.id, attendanceInstructors.users.id, subject.id, attendanceInstructors" 
			+" from Classes classes "
			+ " inner join classes.classSchemewises classSchemewises"
			+ " inner join classSchemewises.curriculumSchemeDuration curriculumSchemeDuration"
			+ " inner join classSchemewises.attendanceClasses attendanceClasses"
			+ " inner join attendanceClasses.attendance attendance "
			+ " inner join attendance.attendanceInstructors attendanceInstructors "
			+ " left  join attendanceInstructors.attendance.subject subject "
			+ " where " + statusCriteria + " and attendance.isCanceled = 0 " +
			" group by attendanceInstructors.attendance.batch, attendanceInstructors.users.id , subject.id , classes.id order by classes.name";					
		log.info("exit getSelectionSearchCriteria..");
		return searchCriteria;

	}
	/**
	 * @param studentSearchForm
	 * @return
	 * This method will give final query
	 */
	public static String getSelectionMonthlyCriteria(
			AttendanceTeacherReportForm studentSearchForm) {
		log.info("entered getSelectionSearchCriteria..");
		String statusCriteria = commonSearchMonthly(studentSearchForm);
		
		String searchCriteria= "";
		
//		String ednJoin = "";
		searchCriteria = "select  classes.id, attendanceInstructors.users.id,attendanceInstructors.attendance.subject.id, classes.name, attendanceInstructors.users.userName, attendance.subject.name, attendance.subject.code, attendance.hoursHeldMonthly" 
			+ " from Classes classes "
			+ " inner join classes.classSchemewises classSchemewises"
			+ " inner join classSchemewises.curriculumSchemeDuration curriculumSchemeDuration"
			+ " inner join classSchemewises.attendanceClasses attendanceClasses"
			+ " inner join attendanceClasses.attendance attendance "
			+ " inner join attendance.attendanceInstructors attendanceInstructors " 
			+ " where " + statusCriteria + " and attendance.isCanceled = 0 " +
			" group by attendanceInstructors.attendance.batch, attendanceInstructors.users.id , attendanceInstructors.attendance.subject.id , classes.id, attendance.attendanceDate";					
		log.info("exit getSelectionSearchCriteria..");
		return searchCriteria;

	}
	
	/**
	 * @param studentSearchBo
	 * @param attendanceSummaryReportForm
	 * @return
	 * This method will converty BO' to TO's
	 */
	public static List<AttendanceTeacherReportTO> convertBoToTo(List teacherSearchBo,AttendanceTeacherReportForm attendanceTeacherReportForm) {
		log.info("entered convertBoToTo..");
		List<AttendanceTeacherReportTO> teachersList = new ArrayList<AttendanceTeacherReportTO>();
		if (teacherSearchBo != null && !teacherSearchBo.isEmpty()) {
			Iterator itr = teacherSearchBo.iterator();
			while (itr.hasNext()) {
				Object[] object = (Object[]) itr.next();
				int length=object.length-1;
				String batchName=null;
				String className = (String)object[0];
				String teacherName = (String)object[1];
				String subjectName = (String)object[2];
				String subjectCode = (String)object[3];
				
				AttendanceInstructor instructor = (AttendanceInstructor)object[8];
				if(instructor.getAttendance()!=null)
				{
					if(instructor.getAttendance().getBatch()!=null)
					{
						if(instructor.getAttendance().getBatch().getBatchName()!=null)
						{
							batchName= instructor.getAttendance().getBatch().getBatchName();	
						}
					}
				}
				Long hoursHeld = (Long)object[4];
				String department="";
				if(length==9){
				 department=(String)object[9];
				}
				AttendanceTeacherReportTO attendanceTeacherReportTO =  new AttendanceTeacherReportTO();
				
				attendanceTeacherReportTO.setClassName(className);
				
				attendanceTeacherReportTO.setTeacherName(teacherName);
				if(subjectName!=null){
				attendanceTeacherReportTO.setSubjectName(subjectName);
				}else
					attendanceTeacherReportTO.setSubjectName("--");
				if(subjectCode!=null)
				attendanceTeacherReportTO.setSubjectCode(subjectCode);
				else
					attendanceTeacherReportTO.setSubjectCode("--");
				//Batch Name added
				if(batchName!=null)
					attendanceTeacherReportTO.setBatch(batchName);
				else
				    attendanceTeacherReportTO.setBatch("--");
				//Batch Name add code ends
				attendanceTeacherReportTO.setHourseTaken(String.valueOf(hoursHeld));
				attendanceTeacherReportTO.setDepartment(department);
				if(object[5]!= null && object[5].toString()!= null){
					attendanceTeacherReportTO.setClassId((Integer)object[5]);
				}
				if(object[6]!= null && object[6].toString()!= null){
					attendanceTeacherReportTO.setUserId((Integer)object[6]);
				}
				if(object[7]!= null && object[7].toString()!= null){
					attendanceTeacherReportTO.setSubjectId(object[7].toString());
				}
				
				teachersList.add(attendanceTeacherReportTO);
			}
		}	
		return teachersList;
	}
	
	/**
	 * @param studentSearchBo
	 * @param attendanceSummaryReportForm
	 * @return
	 * This method will converty BO' to TO's
	 */
	public static List<AttendanceTeacherReportTO> convertMonthlyBoToTo(List teacherSearchBo,AttendanceTeacherReportForm attendanceTeacherReportForm) {
		log.info("entered convertMonthlyBoToTo..");
		List<AttendanceTeacherReportTO> teachersList = new ArrayList<AttendanceTeacherReportTO>();
		Map<String,AttendanceTeacherReportTO> map = new HashMap<String, AttendanceTeacherReportTO>();
//		Map<Integer,Map<Integer,AttendanceTeacherReportTO>> mapClass = new HashMap<Integer, Map<Integer,AttendanceTeacherReportTO>>();
	
		if (teacherSearchBo != null) {
			Iterator itr = teacherSearchBo.iterator();
			while (itr.hasNext()) {
				Object[] object = (Object[]) itr.next();
				String classId = (String)String.valueOf(object[0]);
				String teacherId = (String)String.valueOf(object[1]);
				String subjectId = (String)String.valueOf(object[2]);
				String className = (String)object[3];
				String teacherName = (String)object[4];
				String subjectName = (String)object[5];
				String subjectCode = (String)object[6];
				Integer hoursHeld = (Integer)object[7];
				String mapKey = classId+teacherId+subjectId;
				if (map.containsKey(mapKey)) {
					AttendanceTeacherReportTO attendanceTeacherReportTO = map
							.get(mapKey);

					int hours = Integer.valueOf(attendanceTeacherReportTO
							.getHourseTaken())
							+ hoursHeld.intValue();
					attendanceTeacherReportTO.setHourseTaken(String
							.valueOf(hours));

				} else {
					AttendanceTeacherReportTO attendanceTeacherReportTO = new AttendanceTeacherReportTO();
					attendanceTeacherReportTO.setClassName(className);
					attendanceTeacherReportTO.setTeacherName(teacherName);
					attendanceTeacherReportTO.setSubjectName(subjectName);
					attendanceTeacherReportTO.setSubjectCode(subjectCode);
					attendanceTeacherReportTO.setHourseTaken(String
							.valueOf(hoursHeld.intValue()));
					// teachersList.add(attendanceTeacherReportTO);
					map.put(mapKey, attendanceTeacherReportTO);
					
				}
		}
		}	
		
		teachersList.addAll(map.values());
		return teachersList;
	}
	
	/**
	 * 
	 * @param periodDetailsBO
	 * @return
	 */
	public static List<PeriodTO> createPeriodTOs(List periodDetailsBO,List<String> classids) {
		log.info("entered convertBoToTo..");
		List<PeriodTO> periodTOList = new ArrayList<PeriodTO>();
		String attendance="";
		
		if (periodDetailsBO != null) {
			Iterator itr = periodDetailsBO.iterator();
			while (itr.hasNext()) {
				Object[] object = (Object[]) itr.next();
				PeriodTO periodTO = new PeriodTO();
				int length=object.length;
				List<String> periodName=null;
				if(object[2]!=null && object[3]!=null && classids.contains(object[2].toString())){
					if(!attendance.equals(object[3].toString())){
						periodName=new ArrayList<String>();
				periodName.add((String)object[0]);		
				periodTO.setPeriodNames(periodName);
				periodTO.setDate(CommonUtil.ConvertStringToDateFormat(object[4].toString(),
						TeacherClassAttendanceHelper.SQL_DATEFORMAT,
						TeacherClassAttendanceHelper.FROM_DATEFORMAT));
				periodTO.setAttendanceId(object[3].toString());
				periodTO.setId( Integer.parseInt(object[1].toString()));
				periodTO.setAttendanceType(object[6].toString());
				if(length>7)
				periodTO.setSubjectName(object[7].toString());
				if(attendance.isEmpty())
					attendance=object[3].toString();
				
					periodTO.setHoursTaken(object[5].toString());
				attendance=object[3].toString();
				periodTOList.add(periodTO);
				}
			else{
						Iterator<PeriodTO> it=periodTOList.iterator();
						PeriodTO period=new PeriodTO();
						while(it.hasNext()){
							
							period=it.next();
							if(period.getAttendanceId().equalsIgnoreCase(attendance)){
								break;
								}
						}
						periodName=period.getPeriodNames();
						if(!periodName.contains((String)object[0]))
						periodName.add((String)object[0]);
						period.setPeriodNames(periodName);
					}
				}
			}
		}	
		Collections.sort(periodTOList, new TeacherReportComparator());
		return periodTOList;
	}

	
	
	/**
	 * @param attendanceTeacherReportForm
	 * @param startDate
	 * @return
	 */
	private static String commonSearchForPreviousTeacherSummary(AttendanceTeacherReportForm attendanceTeacherReportForm,Date startDate, Date endDate) {
		log.info("entered commonSearch..");
		String searchCriteria = "";
		String teacher = " attendanceInstructors.users.id in ("
				+ attendanceTeacherReportForm.getUserId() +")";		
		searchCriteria = searchCriteria + teacher;
		String startDate2=CommonUtil.formatDates(startDate);
		String endDate2=CommonUtil.formatDates(endDate);
		if (startDate.toString().trim().length() > 0) {
			String startDate1 = " and attendance.isMonthlyAttendance = 0 and attendance.attendanceDate >= '"
					+ CommonUtil.ConvertStringToSQLDate(startDate2)+"'" ;
			searchCriteria = searchCriteria + startDate1;
		}
		if (endDate.toString().length() > 0) {
			String endDate1 = " and attendance.attendanceDate < '"
					+ CommonUtil.ConvertStringToSQLDate(endDate2)+"'";
			searchCriteria = searchCriteria + endDate1;
		}
		log.info("exit commonSearch..");
		attendanceTeacherReportForm.setPreviousStartDate(startDate2);
		attendanceTeacherReportForm.setPreviousEndDate(endDate2);
		return searchCriteria;
	}
//	sub method of getSelectionSearchCriteriaForTeacherSummary for preparing query 
	/**
	 * @param attendanceTeacherReportForm
	 * @param startDate
	 * @return
	 */
	private static String commonSearchForTeacherSummary(AttendanceTeacherReportForm attendanceTeacherReportForm,Date startDate) {
		log.info("entered commonSearch..");
		String searchCriteria = "";
		
		/*if (attendanceTeacherReportForm.getTeacherType().length > 0) {
			String [] tempArray = attendanceTeacherReportForm.getTeacherType();
			String attType ="";
			for(int i=0;i<tempArray.length;i++){
				 attType = attType+tempArray[i];
				 if(i<(tempArray.length-1)){
					 attType = attType+",";
				 }
			}*/
		
		String teacher = " attendanceInstructors.users.id in ("
				+ attendanceTeacherReportForm.getUserId() +")";		
		searchCriteria = searchCriteria + teacher;
		Date endDate=new Date();
		String startDate2=CommonUtil.formatDates(startDate);
		String endDate2=CommonUtil.formatDates(endDate);
		if (startDate.toString().trim().length() > 0) {
			String startDate1 = " and attendance.isMonthlyAttendance = 0 and attendance.attendanceDate >= '"
					+ CommonUtil.ConvertStringToSQLDate(startDate2)+"'" ;
			searchCriteria = searchCriteria + startDate1;
		}

		if (endDate.toString().length() > 0) {
			String endDate1 = " and attendance.attendanceDate <= '"
					+ CommonUtil.ConvertStringToSQLDate(endDate2)+"'";
			searchCriteria = searchCriteria + endDate1;
		}
		log.info("exit commonSearch..");
		attendanceTeacherReportForm.setStartDate(startDate2);
		attendanceTeacherReportForm.setEndDate(endDate2);
		return searchCriteria;
	}
// method prepares the query with given details
	
	
	/**
	 * @param studentSearchForm
	 * @param startDate
	 * @param classesList 
	 * @return
	 */
	public static String getSelectionSearchCriteriaForPreviousTeacherSummary(
			AttendanceTeacherReportForm studentSearchForm,Date startDate, Date endDate, List<Object[]> classesList)throws Exception {
		log.info("entered getSelectionSearchCriteria..");
		String classId="";
		String startDate2="";
		String endDate2="";
		Iterator<Object[]> itr=classesList.iterator();
		while (itr.hasNext()) {
			Object[] intValue = (Object[]) itr.next();
			if(classId!=null && !classId.isEmpty()){
				classId=classId+","+intValue[0].toString();
			}else{
				classId=intValue[0].toString();
			}
			startDate2=intValue[1].toString();
			endDate2=intValue[2].toString();
		}
		
		
		String query="select au.users.userName,ac.classSchemewise.classes.name,b.batchName,sum(au.attendance.hoursHeld)," +
				" s.code,s.name,ac.classSchemewise.classes.id, au.users.id,s.id,b.id " +
				" from AttendanceInstructor au" +
				" left join au.attendance.subject s" +
				" left join au.attendance.batch b" +
				" join au.attendance.attendanceClasses ac" +
				" where au.users.id in ("+studentSearchForm.getUserId()+")" +
				" and au.attendance.isCanceled=0" +
				" and au.attendance.isMonthlyAttendance=0" +
	 //Added By Manu			
	//			" and au.attendance.attendanceDate >='" + CommonUtil.ConvertStringToSQLDate(startDate2)+"'" +
	//			" and au.attendance.attendanceDate <'" + CommonUtil.ConvertStringToSQLDate(endDate2)+"' " +
				" and ac.classSchemewise.classes.id in ("+classId+")"+
	//End
				"group by b.id,s.name,ac.classSchemewise.classes.name,au.users.id";
		
		studentSearchForm.setPreviousStartDate(startDate2);
		studentSearchForm.setPreviousEndDate(endDate2);
		
		
		log.info("exit getSelectionSearchCriteria..");
		return query;

	}
	/**
	 * @param studentSearchForm
	 * @param startDate
	 * @return
	 */
	public static String getSelectionSearchCriteriaForTeacherSummary( AttendanceTeacherReportForm studentSearchForm,Date startDate)throws Exception {
//		String statusCriteria = commonSearchForTeacherSummary(studentSearchForm,startDate);
//		String searchCriteria = "select classes.name, attendanceInstructors.users.userName, subject.name, subject.code, sum(attendance.hoursHeld)," +
//				" classes.id, attendanceInstructors.users.id, subject.id, attendanceInstructors" 
//			+" from Classes classes "
//			+ " inner join classes.classSchemewises classSchemewises"
//			+ " inner join classSchemewises.curriculumSchemeDuration curriculumSchemeDuration"
//			+ " inner join classSchemewises.attendanceClasses attendanceClasses"
//			+ " inner join attendanceClasses.attendance attendance "
//			+ " inner join attendance.attendanceInstructors attendanceInstructors "
//			+ " left  join attendanceInstructors.attendance.subject subject "
//			+ " where " + statusCriteria + " and attendance.isCanceled = 0 " +
//			" group by attendanceInstructors.attendance.batch, attendanceInstructors.users.id , subject.id , classes.id order by classes.name";
		log.info("entered getSelectionSearchCriteria..");
		String startDate2=CommonUtil.formatDates(startDate);
		String endDate2=CommonUtil.formatDates(new Date());
		String query="select au.users.userName,ac.classSchemewise.classes.name,b.batchName,sum(au.attendance.hoursHeld)," +
				" s.code,s.name,ac.classSchemewise.classes.id, au.users.id,s.id,b.id " +
				" from AttendanceInstructor au" +
				" left join au.attendance.subject s" +
				" left join au.attendance.batch b" +
				" join au.attendance.attendanceClasses ac" +
				" where au.users.id in ("+studentSearchForm.getUserId()+")" +
				" and au.attendance.isCanceled=0" +
				" and au.attendance.isMonthlyAttendance=0" +
				" and au.attendance.attendanceDate >='" + CommonUtil.ConvertStringToSQLDate(startDate2)+"'" +
				" and au.attendance.attendanceDate <='" + CommonUtil.ConvertStringToSQLDate(endDate2)+"' " +
				"group by b.id,s.name,ac.classSchemewise.classes.name,au.users.id";
		
		studentSearchForm.setStartDate(startDate2);
		studentSearchForm.setEndDate(endDate2);
		log.info("exit getSelectionSearchCriteria..");
		return query;
	}
	public static String getSelectionSearchCriteriaForTeacherSummaryReport( AttendanceTeacherReportForm studentSearchForm,List<Integer> classesList)throws Exception {
//		String statusCriteria = commonSearchForTeacherSummary(studentSearchForm,startDate);
//		String searchCriteria = "select classes.name, attendanceInstructors.users.userName, subject.name, subject.code, sum(attendance.hoursHeld)," +
//				" classes.id, attendanceInstructors.users.id, subject.id, attendanceInstructors" 
//			+" from Classes classes "
//			+ " inner join classes.classSchemewises classSchemewises"
//			+ " inner join classSchemewises.curriculumSchemeDuration curriculumSchemeDuration"
//			+ " inner join classSchemewises.attendanceClasses attendanceClasses"
//			+ " inner join attendanceClasses.attendance attendance "
//			+ " inner join attendance.attendanceInstructors attendanceInstructors "
//			+ " left  join attendanceInstructors.attendance.subject subject "
//			+ " where " + statusCriteria + " and attendance.isCanceled = 0 " +
//			" group by attendanceInstructors.attendance.batch, attendanceInstructors.users.id , subject.id , classes.id order by classes.name";
		log.info("entered getSelectionSearchCriteria..");
		//String startDate2=CommonUtil.formatDates(startDate);
		//String endDate2=CommonUtil.formatDates(new Date());
		String query="select au.users.userName,ac.classSchemewise.classes.name,b.batchName,sum(au.attendance.hoursHeld)," +
				" s.code,s.name,ac.classSchemewise.classes.id, au.users.id,s.id,b.id " +
				" from AttendanceInstructor au" +
				" left join au.attendance.subject s" +
				" left join au.attendance.batch b" +
				" join au.attendance.attendanceClasses ac" +
				" where au.users.id in ("+studentSearchForm.getUserId()+")" +
				" and au.attendance.isCanceled=0" +
				" and au.attendance.isMonthlyAttendance=0" +
				"and au.attendance.isMonthlyAttendance=0 and ac.classSchemewise.classes.id in ("+classesList+")" +
				//" and au.attendance.attendanceDate >='" + CommonUtil.ConvertStringToSQLDate(startDate2)+"'" +
				//" and au.attendance.attendanceDate <='" + CommonUtil.ConvertStringToSQLDate(endDate2)+"' " +
				"group by b.id,s.name,ac.classSchemewise.classes.name,au.users.id";
		
		//studentSearchForm.setStartDate(startDate2);
		//studentSearchForm.setEndDate(endDate2);
		log.info("exit getSelectionSearchCriteria..");
		return query;
	}
	// method gives the start date of the teacher's class from curriculumScheme
	/**
	 * @param classesList
	 * @param academicYear
	 * @return
	 */
	public static Date getStartDate(List<Integer> classesList,int academicYear){
		Iterator<Integer> it=classesList.iterator();
		StringBuilder queryString =new StringBuilder();
		ITeacherClassAttendanceTxn attendanceSummary = TeacherClassAttendanceTxnImpl.getInstance();
		Date startDate=null;
		while(it.hasNext()){
			queryString.append(",").append(it.next());
		}
		//String query=queryString.replaceFirst(",", " ");
		startDate=attendanceSummary.getStartDate(classesList,academicYear);
		return startDate;
	}
	
	/**
	 * @param classesList
	 * @param academicYear
	 * @return
	 */
	public static Date getEndDate(List<Integer> classesList,int academicYear){
		Iterator<Integer> it=classesList.iterator();
		StringBuilder queryString =new StringBuilder();
		ITeacherClassAttendanceTxn attendanceSummary = TeacherClassAttendanceTxnImpl.getInstance();
		Date endDate=null;
		while(it.hasNext()){
			queryString.append(",").append(it.next());
		}
		//String query=queryString.replaceFirst(",", " ");
		endDate=attendanceSummary.getEndDate(classesList,academicYear);
		return endDate;
	}
//	converting List to StudentAbsentDetailsTO
	/**
	 * @param students
	 * @param attendanceTeacherReportForm
	 * @return
	 * @throws Exception
	 */
	public static List<StudentAbsentDetailsTO> convertListToTO(List students,AttendanceTeacherReportForm attendanceTeacherReportForm)throws Exception{
		List<StudentAbsentDetailsTO> studentTO=new ArrayList<StudentAbsentDetailsTO>();
		Iterator it=students.iterator();
		Iterator<AttendancePeriod> periodsItr;
		while(it.hasNext()){
			StringBuffer periods = new StringBuffer();
			String newPeriods = "";
			StudentAbsentDetailsTO student=new StudentAbsentDetailsTO();
			Object[] object=(Object[])it.next();
			Attendance attendance=(Attendance)object[2];
		int schemeId=Integer.parseInt(object[3].toString());
		
			periodsItr=attendance.getAttendancePeriods().iterator();
			int periodId=0;
			while(periodsItr.hasNext()){
				AttendancePeriod attendancePeriod=periodsItr.next();
				
			if(attendancePeriod.getPeriod().getStartTime()!=null && !attendancePeriod.getPeriod().getStartTime().isEmpty() && attendancePeriod.getPeriod().getEndTime()!=null && !attendancePeriod.getPeriod().getEndTime().isEmpty() && attendancePeriod.getPeriod().getId()!=periodId &&attendancePeriod.getPeriod().getClassSchemewise().getId()==schemeId){
				int st=Integer.parseInt(attendancePeriod.getPeriod().getStartTime().substring(0,2));
				int et=Integer.parseInt(attendancePeriod.getPeriod().getEndTime().substring(0,2));
				periodId=attendancePeriod.getPeriod().getId();
				if(st<=12){
					periods.append(attendancePeriod.getPeriod().getPeriodName()+"("+attendancePeriod.getPeriod().getStartTime().substring(0,5)+"-"+attendancePeriod.getPeriod().getEndTime().substring(0,5)+")").append("&nbsp;");
				}else{
					periods.append(attendancePeriod.getPeriod().getPeriodName()+"("+pMap.get(st)+attendancePeriod.getPeriod().getStartTime().substring(2,5)+"-"+pMap.get(et)+attendancePeriod.getPeriod().getEndTime().substring(2,5)+")").append("&nbsp;");
				}
			}
		    }
			if(periods.length()!=0){
				newPeriods = periods.substring(0, periods.length()-1);
			}
			DateFormat format=new java.text.SimpleDateFormat("dd/MM/yyyy");
			String date=format.format(attendance.getAttendanceDate());
			student.setDate(date);
			attendanceTeacherReportForm.setDate(date);
			student.setPeriods(newPeriods);
			attendanceTeacherReportForm.setPeriods(newPeriods);
			if(attendance.getSubject()!=null){
			student.setSubjectName(attendance.getSubject().getName());
			//attendanceTeacherReportForm.setSubjectName(attendance.getSubject().getName());
			}
			else{
				student.setSubjectName("--");
				attendanceTeacherReportForm.setSubjectName("--");
			}
					
			student.setRegisterNo(object[1].toString());
			student.setStudentName(object[0].toString());
			studentTO.add(student);
			
		}if(students.isEmpty()){
			DateFormat format=new java.text.SimpleDateFormat("dd/MM/yyyy");
		
			Date dates=format.parse(attendanceTeacherReportForm.getAttendanceDate());
			String date=format.format(dates);
			attendanceTeacherReportForm.setDate(date);
			attendanceTeacherReportForm.setPeriods(attendanceTeacherReportForm.getPeriodName());
			if(attendanceTeacherReportForm.getSubjectName().isEmpty())
				attendanceTeacherReportForm.setSubjectName("--");
		}
		return studentTO;
		}

	/**
	 * @param studentSearchForm
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public static String getSearchQueryForTeachersDetails( AttendanceTeacherReportForm teacherSearchForm,int userId) throws Exception{
		log.info("entered getSelectionSearchCriteria..");
		ITeacherClassAttendanceTxn attendanceSummary = TeacherClassAttendanceTxnImpl.getInstance();
		CurrentAcademicYear year=CurrentAcademicYear.getInstance();
		
		int academicYear=year.getAcademicyear();
	    List classes=attendanceSummary.getClassesByTeacherId(userId, academicYear);
		Date startDate=attendanceSummary.getStartDate(classes, academicYear);
		String statusCriteria = commonSearchForTeacherDetails(teacherSearchForm,startDate,userId);
		
		String searchCriteria= "";
		String empId=attendanceSummary.getEmpId(userId);
		if(empId!=null && !empId.isEmpty()){
//		String ednJoin = "";
		searchCriteria = "select classes.name, attendanceInstructors.users.userName, subject.name, subject.code, sum(attendance.hoursHeld)," +
				" classes.id, attendanceInstructors.users.id, subject.id, attendanceInstructors, attendanceInstructors.users.employee.department.name " 
			+" from Classes classes "
			+ " inner join classes.classSchemewises classSchemewises"
			+ " inner join classSchemewises.curriculumSchemeDuration curriculumSchemeDuration"
			+ " inner join classSchemewises.attendanceClasses attendanceClasses"
			+ " inner join attendanceClasses.attendance attendance "
			+ " inner join attendance.attendanceInstructors attendanceInstructors " 
			+ " left  join attendanceInstructors.attendance.subject subject "
			+ " where " + statusCriteria + " and attendance.isCanceled = 0 " + "and classSchemewises.id in ("+teacherSearchForm.getClassSchemes()+")"+
			" group by attendanceInstructors.attendance.batch, attendanceInstructors.users.id , subject.id , classes.id,attendanceInstructors.users.employee.department.name order by classes.name";
		}else{
			searchCriteria = "select classes.name, attendanceInstructors.users.userName, subject.name, subject.code, sum(attendance.hoursHeld)," +
			" classes.id, attendanceInstructors.users.id, subject.id, attendanceInstructors, attendanceInstructors.users.department.name " 
		+" from Classes classes "
		+ " inner join classes.classSchemewises classSchemewises"
		+ " inner join classSchemewises.curriculumSchemeDuration curriculumSchemeDuration"
		+ " inner join classSchemewises.attendanceClasses attendanceClasses"
		+ " inner join attendanceClasses.attendance attendance "
		+ " inner join attendance.attendanceInstructors attendanceInstructors "
		+ " left  join attendanceInstructors.attendance.subject subject "
		+ " where " + statusCriteria + " and attendance.isCanceled = 0 " + 
		" group by attendanceInstructors.attendance.batch, attendanceInstructors.users.id , subject.id , classes.id,attendanceInstructors.users.employee.department.name order by classes.name";
		}
		log.info("exit getSelectionSearchCriteria..");
		return searchCriteria;

	}
	
	/**
	 * @param attendanceTeacherReportForm
	 * @param startDate
	 * @param userId
	 * @return
	 */
	private static String commonSearchForTeacherDetails(AttendanceTeacherReportForm attendanceTeacherReportForm,Date startDate,int userId) {
		log.info("entered commonSearch..");
		String searchCriteria = "";
		
		
		
		String teacher = " attendanceInstructors.users.id in ("
				+ userId +")";		
		searchCriteria = searchCriteria + teacher;
	//}
		//Calendar xmas = new GregorianCalendar(2011, Calendar.JUNE, 01);
//Date startDate=xmas.getTime();
	Date endDate=new Date();
		String startDate2=CommonUtil.formatDates(startDate);
		String endDate2=CommonUtil.formatDates(endDate);
		if (startDate.toString().trim().length() > 0) {
			String startDate1 = " and attendance.isMonthlyAttendance = 0 and attendance.attendanceDate > '"
					+ CommonUtil.ConvertStringToSQLDate(startDate2)+"'" ;
			searchCriteria = searchCriteria + startDate1;
		}

		if (endDate.toString().length() > 0) {
			String endDate1 = " and attendance.attendanceDate <= '"
					+ CommonUtil.ConvertStringToSQLDate(endDate2)+"'";
			searchCriteria = searchCriteria + endDate1;
		}
		log.info("exit commonSearch..");
		attendanceTeacherReportForm.setStartDate(startDate2);
		attendanceTeacherReportForm.setEndDate(endDate2);
		return searchCriteria;
	}
	
	/**
	 * @param attendanceTeacherReportForm
	 * @param userIdList
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static List<AttendanceTeacherReportTO> getInchargeTeacherClasses(AttendanceTeacherReportForm attendanceTeacherReportForm,List userIdList,List teacherIdsList)throws Exception{
		Iterator it=userIdList.iterator();
		List<AttendanceTeacherReportTO> incharge=new ArrayList<AttendanceTeacherReportTO>();
		ITeacherClassAttendanceTxn attendanceSummary = TeacherClassAttendanceTxnImpl.getInstance();
		while(it.hasNext()){
			int userId=Integer.parseInt((it.next().toString()));
			String search=getSearchQueryForTeachersDetails(attendanceTeacherReportForm,userId);
			List teacherClassesDetialsList=attendanceSummary.getTeacherAttendance(search);
			List<AttendanceTeacherReportTO> teacherSearchTo = AttendanceTeacherReportHelper.convertBoToTo(teacherClassesDetialsList,attendanceTeacherReportForm);
			incharge.addAll(teacherSearchTo);
		}
		if(!teacherIdsList.isEmpty() && teacherIdsList!=null){
		Iterator itr=teacherIdsList.iterator();
		while(itr.hasNext()){
			int userId=Integer.parseInt((itr.next().toString()));
			String search=getSearchQueryTeachersDetails(attendanceTeacherReportForm,userId);
			List teacherClassesDetialsList=attendanceSummary.getTeacherAttendance(search);
			List<AttendanceTeacherReportTO> teacherSearchTo = AttendanceTeacherReportHelper.convertBoToTo(teacherClassesDetialsList,attendanceTeacherReportForm);
			incharge.addAll(teacherSearchTo);
		}
		}
		return incharge;
	}
	
	/**
	 * @param teacherSearchForm
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public static String getSearchQueryTeachersDetails( AttendanceTeacherReportForm teacherSearchForm,int userId) throws Exception{
		log.info("entered getSelectionSearchCriteria..");
		ITeacherClassAttendanceTxn attendanceSummary = TeacherClassAttendanceTxnImpl.getInstance();
		CurrentAcademicYear year=CurrentAcademicYear.getInstance();
		
		int academicYear=year.getAcademicyear();
		 List classes=attendanceSummary.getClassesByTeacherId(userId, academicYear);
		 Date startDate=null;
		if(!classes.isEmpty()){
			 startDate=attendanceSummary.getStartDate(classes, academicYear);
		}else{
			Calendar cal = new GregorianCalendar();
			cal.set(2011, 5, 1);
			startDate=cal.getTime();
		}
		String statusCriteria = commonSearchForTeacherDetails(teacherSearchForm,startDate,userId);
		
		String searchCriteria= "";
		String empId=attendanceSummary.getEmpId(userId);
		if(empId!=null && !empId.isEmpty()){
//		String ednJoin = "";
		searchCriteria = "select classes.name, attendanceInstructors.users.userName, subject.name, subject.code, sum(attendance.hoursHeld)," +
				" classes.id, attendanceInstructors.users.id, subject.id, attendanceInstructors, attendanceInstructors.users.employee.department.name " 
			+" from Classes classes "
			+ " inner join classes.classSchemewises classSchemewises"
			+ " inner join classSchemewises.curriculumSchemeDuration curriculumSchemeDuration"
			+ " inner join classSchemewises.attendanceClasses attendanceClasses"
			+ " inner join attendanceClasses.attendance attendance "
			+ " inner join attendance.attendanceInstructors attendanceInstructors "
			+ " left  join attendanceInstructors.attendance.subject subject "
			+ " where " + statusCriteria + " and attendance.isCanceled = 0 " + 
			" group by attendanceInstructors.attendance.batch, attendanceInstructors.users.id , subject.id , classes.id,attendanceInstructors.users.employee.department.name order by classes.name";
		}else{
			searchCriteria = "select classes.name, attendanceInstructors.users.userName, subject.name, subject.code, sum(attendance.hoursHeld)," +
			" classes.id, attendanceInstructors.users.id, subject.id, attendanceInstructors, attendanceInstructors.users.department.name " 
		+" from Classes classes "
		+ " inner join classes.classSchemewises classSchemewises"
		+ " inner join classSchemewises.curriculumSchemeDuration curriculumSchemeDuration"
		+ " inner join classSchemewises.attendanceClasses attendanceClasses"
		+ " inner join attendanceClasses.attendance attendance "
		+ " inner join attendance.attendanceInstructors attendanceInstructors "
		+ " left  join attendanceInstructors.attendance.subject subject "
		+ " where " + statusCriteria + " and attendance.isCanceled = 0 " + 
		" group by attendanceInstructors.attendance.batch, attendanceInstructors.users.id , subject.id , classes.id,attendanceInstructors.users.employee.department.name order by classes.name";
		}
		log.info("exit getSelectionSearchCriteria..");
		return searchCriteria;
	}

	/**
	 * @param attendanceResults
	 * @param attendanceTeacherReportForm
	 * @return
	 */
	public static List<AttendanceTeacherReportTO> convertListBosToListTos(List<Object[]> attendanceResults, AttendanceTeacherReportForm attendanceTeacherReportForm) throws Exception {
		List<AttendanceTeacherReportTO> toList=new ArrayList<AttendanceTeacherReportTO>();
		Map<Integer,AttendanceTeacherReportTO> batchMap=new HashMap<Integer, AttendanceTeacherReportTO>();
		if(attendanceResults!=null && !attendanceResults.isEmpty()){
			Iterator<Object[]> itr=attendanceResults.iterator();
			while (itr.hasNext()) {
				Object[] obj = (Object[]) itr.next();
				if(obj[9]!=null){
					if(batchMap.containsKey(Integer.parseInt(obj[9].toString()))){
						AttendanceTeacherReportTO to=batchMap.remove(Integer.parseInt(obj[9].toString()));
//						if(obj[6]!=null){
//							if(to.getClassesId()!=null)
//								to.setClassesId(to.getClassesId()+","+obj[6].toString());
//							else
//								to.setClassesId(obj[6].toString());
//						}
						if(obj[1]!=null){
							if(to.getClassName()!=null)
								to.setClassName(to.getClassName()+","+obj[1].toString());
							else
								to.setClassName(obj[1].toString());
						}
						batchMap.put(Integer.parseInt(obj[9].toString()),to);
					}else{
						AttendanceTeacherReportTO to=new AttendanceTeacherReportTO();
						if(obj[0]!=null)
						to.setTeacherName(obj[0].toString());
						if(obj[1]!=null)
						to.setClassName(obj[1].toString());
						if(obj[2]!=null)
						to.setBatch(obj[2].toString());
						if(obj[3]!=null)
						to.setHourseTaken(obj[3].toString());
						if(obj[4]!=null)
						to.setSubjectCode(obj[4].toString());
						if(obj[5]!=null)
						to.setSubjectName(obj[5].toString());
						if(obj[6]!=null)
						to.setClassesId(obj[6].toString());
						if(obj[7]!=null)
						to.setUserId(Integer.parseInt(obj[7].toString()));
						if(obj[8]!=null)
						to.setSubjectId(obj[8].toString());
						batchMap.put(Integer.parseInt(obj[9].toString()),to);
					}
				}else{
					AttendanceTeacherReportTO to=new AttendanceTeacherReportTO();
					if(obj[0]!=null)
					to.setTeacherName(obj[0].toString());
					if(obj[1]!=null)
					to.setClassName(obj[1].toString());
					if(obj[2]!=null)
					to.setBatch(obj[2].toString());
					else
						to.setBatch("--");
					if(obj[3]!=null)
					to.setHourseTaken(obj[3].toString());
					if(obj[4]!=null)
					to.setSubjectCode(obj[4].toString());
					if(obj[5]!=null)
					to.setSubjectName(obj[5].toString());
					if(obj[6]!=null)
					to.setClassesId(obj[6].toString());
					if(obj[7]!=null)
					to.setUserId(Integer.parseInt(obj[7].toString()));
					if(obj[8]!=null)
					to.setSubjectId(obj[8].toString());
					toList.add(to);
				}
			}
			if(!batchMap.isEmpty()){
				toList.addAll(batchMap.values());
			}
		}
		return toList;
	}
}
