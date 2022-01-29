package com.kp.cms.helpers.attendance;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Attendance;
import com.kp.cms.bo.admin.AttendanceInstructor;
import com.kp.cms.bo.admin.AttendancePeriod;
import com.kp.cms.bo.admin.Period;
import com.kp.cms.forms.attendance.ClassWiseAttendanceStatusForm;
import com.kp.cms.handlers.attendance.ClassWiseAttendanceStatusHandler;
import com.kp.cms.to.attendance.AttendanceTO;
import com.kp.cms.transactions.attandance.IClassWiseAttendanceStatusTransaction;
import com.kp.cms.transactionsimpl.attendance.ClassWiseAttendanceStatusTransactionImpl;
import com.kp.cms.utilities.CommonUtil;

public class ClassWiseAttendanceStatusHelper {
	public static volatile ClassWiseAttendanceStatusHelper classWiseAttendanceStatusHelper = null;
	public static final Log log = LogFactory
			.getLog(ClassWiseAttendanceStatusHelper.class);
	
	private ClassWiseAttendanceStatusHelper() {

	}

	public static ClassWiseAttendanceStatusHelper getInstance() {
		if (classWiseAttendanceStatusHelper == null) {
			classWiseAttendanceStatusHelper = new ClassWiseAttendanceStatusHelper();
		}
		return classWiseAttendanceStatusHelper;
	}

	public String getSearchDatesQuery(ClassWiseAttendanceStatusForm classWiseAttendanceStatusForm) {
		log
				.info("Start getSearchDatesQuery of ClassWiseAttendanceStatusHelper");
		String query = "select ac.attendance from AttendanceClass ac join ac.attendance.attendancePeriods p"
				+ " where ac.attendance.attendanceDate between '"
				+ CommonUtil
						.ConvertStringToSQLDate(classWiseAttendanceStatusForm
								.getFromDate())
				+ "' and '"
				+ CommonUtil
						.ConvertStringToSQLDate(classWiseAttendanceStatusForm
								.getToDate())
				+ "'"
				+ " and ac.classSchemewise.classes.id="
				+ Integer.valueOf(classWiseAttendanceStatusForm.getClasses())
				+ " and ac.classSchemewise.classes.termNumber="
				+ Integer.valueOf(classWiseAttendanceStatusForm.getSemister())
				+"  and p.period.id in(:periodsList)"
				+ " and ac.attendance.isCanceled=0 group by ac.attendance.id order by ac.attendance.attendanceDate";
		log.info("End getSearchDatesQuery of ClassWiseAttendanceStatusHelper");
		return query;
	}

	public Map<String, Map<String, List<AttendanceTO>>> convertBotoTo(List<Attendance> attendaceClassWiseList, List<String> periodList,
			ClassWiseAttendanceStatusForm classWiseAttendanceStatusForm,
			HttpServletRequest request) throws NumberFormatException, Exception {
		IClassWiseAttendanceStatusTransaction iClassWiseAttendanceStatusTransaction = ClassWiseAttendanceStatusTransactionImpl.getInstance();
	 	Map<String,String> subjectMap=new TreeMap<String, String>();
		Map<String, Map<String,List<AttendanceTO>>> attendanceMap = new TreeMap<String, Map<String,List<AttendanceTO>>>();
		Iterator<Attendance> attendanceListIterator=attendaceClassWiseList.iterator();
		String pervPeriodName=null;
		while(attendanceListIterator.hasNext()){
			String weekDay=null;
			Attendance attendanceBO=attendanceListIterator.next();
		    String attendanceDate=CommonUtil.ConvertStringToDateFormat(attendanceBO.getAttendanceDate().toString(), "yyyy-mm-dd", "dd/mm/yyyy");
		    weekDay=iClassWiseAttendanceStatusTransaction.checkingInAttendanceDayEntry(attendanceDate);
		    if(weekDay==null || weekDay.isEmpty()){
		     weekDay=CommonUtil.getDayForADate(attendanceDate);
		    }
		    String totalPeriodDetailsDayWise=ClassWiseAttendanceStatusHelper.getInstance().getTotalPeriodDetailsDayWise(Integer.valueOf(classWiseAttendanceStatusForm.getClasses()),Integer.valueOf(classWiseAttendanceStatusForm.getSemister()),Integer.valueOf(classWiseAttendanceStatusForm.getYear()),weekDay);
			List<Object[]> totalPeriodDetailsDayWiseList=iClassWiseAttendanceStatusTransaction.totalPeriodDetailsDayWiseList(totalPeriodDetailsDayWise);

			if(!attendanceMap.containsKey(attendanceDate)){
			Map<String,List<AttendanceTO>> periodMap=new TreeMap<String,List<AttendanceTO>>();
			Iterator<String> periodListIterator=periodList.iterator();
			while(periodListIterator.hasNext()){
				periodMap.put(periodListIterator.next(),new ArrayList<AttendanceTO>());
			}
			if(totalPeriodDetailsDayWiseList!=null){
				Iterator<Object[]> itr=totalPeriodDetailsDayWiseList.iterator();
				while(itr.hasNext()){
					Object[] obj=(Object[])itr.next();
					if(weekDay.equalsIgnoreCase(obj[2].toString())){
					if(obj[1]!=null){
						pervPeriodName=obj[1].toString();
						if(periodList.contains(pervPeriodName)){
							List<AttendanceTO> periodMapList=periodMap.get(pervPeriodName);
							periodMapList.add(getAttendanceTOforClassWiseAttendance(obj,attendanceBO,classWiseAttendanceStatusForm,subjectMap));
							periodMap.put(pervPeriodName,periodMapList);
						}	
					}
					
				}
			}
				
			}
			
			attendanceMap.put(attendanceDate, periodMap);
		}
		}
	
		
		return attendanceMap;
	}

	public AttendanceTO getAttendanceTOforClassWiseAttendance(Object[] obj,Attendance attendance,ClassWiseAttendanceStatusForm classWiseAttendanceStatusForm,Map<String, String> subjectMap) {

		AttendanceTO attendanceTO=new AttendanceTO();
	     if(obj!=null){
	    	 if(obj[4]!=null){
	 			attendanceTO.setSubjectName(obj[4].toString());
	    	 }
	    	 if(obj[5]!=null){
		 			attendanceTO.setSubject(obj[5].toString());
		     }
	    	 if(!subjectMap.containsKey(obj[3].toString())){
					subjectMap.put(obj[3].toString(),obj[4].toString());
				}
				classWiseAttendanceStatusForm.setSubMap(subjectMap);
				attendanceTO.setDates(CommonUtil.ConvertStringToDateFormat(attendance.getAttendanceDate().toString(), "yyyy-mm-dd", "dd/mm/yyyy")); 
	     
				if(obj[6]!=null){
					attendanceTO.setTeachers(obj[7].toString());
				}
	     
	     }
	     classWiseAttendanceStatusForm.setSubMap(subjectMap);
	    
		if (attendance != null) {
			Set<AttendancePeriod> attendancePeriods = attendance
					.getAttendancePeriods();
			Iterator<AttendancePeriod> attendancePeriodsiIterator = attendancePeriods
					.iterator();
			while (attendancePeriodsiIterator.hasNext()) {
				AttendancePeriod attendancePeriod = attendancePeriodsiIterator
						.next();
				if(classWiseAttendanceStatusForm.getClasses().equalsIgnoreCase( String.valueOf(attendancePeriod.getPeriod().getClassSchemewise().getId()))){
					if(obj[1].toString().equalsIgnoreCase(attendancePeriod.getPeriod().getPeriodName())){
						attendanceTO.setIsAttendanceEntered(true);
					}else{
						attendanceTO.setIsAttendanceEntered(false);
					}
				}

			}
		}
				
			   
		   
		return attendanceTO;

	}

	public String getTotalPeriodDetailsDayWise(int classId,int semisterId, int acadamicYear,String weekDay) throws Exception {
		StringBuilder query = new StringBuilder("select period.id,");
		query
				.append("  period.period_name,")
				.append("  tt_period_week.week_day,")
				.append("  subject.id as subj_id,")
				.append("  subject.name as subject,")
				.append("  subject.code,")
				.append("  ifnull(employee.id,guest_faculty.id) as emp_id,")
				.append(
						"  ifnull(employee.first_name,guest_faculty.first_name) as employ")
				.append("  from tt_class")
				.append(
						"  inner join class_schemewise ON tt_class.class_schemewise_id = class_schemewise.id")
				.append(
						"  inner join classes ON class_schemewise.class_id = classes.id")
				.append(
						"  inner join curriculum_scheme_duration ON class_schemewise.curriculum_scheme_duration_id = curriculum_scheme_duration.id")
				.append(
						"  inner join tt_period_week on tt_period_week.tt_class_id = tt_class.id")
				.append(
						"  inner join period ON tt_period_week.period_id = period.id and period.class_schemewise_id = class_schemewise.id")
				.append(
						"  inner join tt_subject_batch on tt_subject_batch.tt_period_id = tt_period_week.id and tt_subject_batch.is_active=1")
				.append(
						"  inner join subject ON tt_subject_batch.subject_id = subject.id and subject.is_active=1")
				.append(
						"  left join tt_users on tt_users.tt_subject_id = tt_subject_batch.id and tt_users.is_active=1")
				.append("  left join users ON tt_users.user_id = users.id")
				.append("  left join employee ON users.employee_id = employee.id")
				.append(
						"  left join guest_faculty on users.guest_id = guest_faculty.id")
				.append("  where tt_class.is_approved=1 ").append(
						"  and curriculum_scheme_duration.academic_year="
								+ acadamicYear).append(
						"  and classes.term_number=" + semisterId).append(
						"  and classes.id=" + classId).append(
						"  and tt_period_week.week_day='"+weekDay+"'").append(
						"  order by period.period_name");

		return query.toString();
	}

}
