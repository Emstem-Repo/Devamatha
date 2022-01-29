package com.kp.cms.helpers.timetable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import com.kp.cms.bo.admin.Period;
import com.kp.cms.forms.timetable.ViewTeacherWiseTimeTableForm;
import com.kp.cms.to.timetable.DepartentTimeTableClassTo;
import com.kp.cms.to.timetable.DepartmentTimeTablePeriodTo;
import com.kp.cms.to.timetable.TimeTableClassTo;
import com.kp.cms.to.timetable.TimeTablePeriodTo;
import com.kp.cms.utilities.TimeTableComparator;

public class ViewTeacherWiseTimeTableHelper {
	private static volatile ViewTeacherWiseTimeTableHelper timeTableHelper = null;
	/**
	 * private Constructor is defined, avoid to create object anywhere for these class.
	 */
	private ViewTeacherWiseTimeTableHelper(){
		
	}
	/** SingleTon Object Created for Class.
	 * @return
	 */
	public static ViewTeacherWiseTimeTableHelper  getInstance(){
		if(timeTableHelper == null){
			timeTableHelper = new ViewTeacherWiseTimeTableHelper();
		}
		return timeTableHelper;
	}
	/**
	 * @param objForm
	 * @return
	 * @throws Exception
	 */
	public String getTimeTableQueryByTeacherWise( ViewTeacherWiseTimeTableForm objForm) throws Exception{
		String hqlQuery = "select " +
		" period.id as periodId," +
		" period.period_name as periodName," +
		" tt_period_week.week_day as weekDay," +
		" group_concat(DISTINCT concat(' ',classes.name)) as className ," +
		" group_concat(DISTINCT concat(' ', subject.name)) as subjectName," +
		" period.start_time as startTime," +
		" concat(room_master.room_no, ' (',block.block_name,')') AS roomName," +
		" subject.code as subCode,"+
		" class_schemewise.class_id,"+
		" classes.name"+
		
		" from tt_class" +
		" inner join class_schemewise ON tt_class.class_schemewise_id = class_schemewise.id" +
		" inner join curriculum_scheme_duration ON class_schemewise.curriculum_scheme_duration_id = curriculum_scheme_duration.id" +
		" inner join tt_period_week on tt_period_week.tt_class_id = tt_class.id" +
		" and tt_period_week.is_active=1" +
		" inner join period on period.class_schemewise_id = class_schemewise.id" +
		" and tt_period_week.period_id = period.id and period.is_active=1" +
		" INNER JOIN tt_subject_batch ON tt_subject_batch.tt_period_id = tt_period_week.id" +
		" INNER JOIN tt_users ON tt_users.tt_subject_id = tt_subject_batch.id" +
		" INNER JOIN users ON tt_users.user_id = users.id" +
		" and tt_users.is_active=1" +
		" left join employee on users.employee_id = employee.id" +
		" INNER JOIN subject ON tt_subject_batch.subject_id = subject.id" +
		" INNER JOIN classes ON class_schemewise.class_id = classes.id" +
		" left JOIN room_master ON room_master.id = tt_subject_batch.room_master_id"+
		" left join block on block.id = room_master.block_id"+
		" where " +
		" curdate() between curriculum_scheme_duration.start_date and curriculum_scheme_duration.end_date" +
		" and tt_class.is_active=1 and tt_class.is_approved=1" +
		" and users.id=" +objForm.getUserId()+
		" and tt_subject_batch.is_active=1" +
		" group by users.id,period.period_name,tt_period_week.week_day";
return hqlQuery;
	}
	/**
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public Map<String, Map<Integer, TimeTablePeriodTo>> convertListToMap(List<Object[]> list, ViewTeacherWiseTimeTableForm objForm,String mode)
		throws Exception {
		Map<String,Map<Integer,TimeTablePeriodTo>> weekDayMap = new HashMap<String, Map<Integer,TimeTablePeriodTo>>();
		Map<Integer,TimeTablePeriodTo> periodMap = null;
		Set<Integer> periodList =new HashSet<Integer>();
		if(list!=null && !list.isEmpty()){
			for (Object[] obj : list) {
				if(!weekDayMap.containsKey(obj[2].toString())){
					periodMap = new HashMap<Integer, TimeTablePeriodTo>();
				}else{
					periodMap = weekDayMap.get(obj[2].toString());
				}
				TimeTablePeriodTo to = new TimeTablePeriodTo();
				to.setPeriodId(Integer.parseInt(obj[0].toString()));
				periodList.add(Integer.parseInt(obj[0].toString()));
				to.setPeriodName(obj[1].toString());
				to.setClassNames(obj[3].toString());
				to.setSubjectNames(obj[4].toString());
				to.setSubjectCode(obj[7].toString());
				to.setStartTime(obj[5].toString());
				if(obj[6]!=null){
					to.setRoomNo(obj[6].toString());
				}
				if(mode!=null && mode.equalsIgnoreCase("teacher")){
					if(obj[8]!=null ){
						to.setTeacherName(obj[8].toString());
					}
				}
				periodMap.put(Integer.parseInt(obj[0].toString()), to);
				weekDayMap.put(obj[2].toString(), periodMap);
			}
		}
		if(periodList!=null && !periodList.isEmpty()){
			objForm.setPeriodId(periodList);
		}
		return weekDayMap;
	}
	/**
	 * @param weekDayMap
	 * @param periodList
	 * @param list 
	 * @return
	 * @throws Exception
	 */
	public List<TimeTableClassTo> createTimeTableList( Map<String, Map<Integer, TimeTablePeriodTo>> weekDayMap,
			List<Period> periodList, List<Period> list)throws Exception {
		List<TimeTableClassTo> timeTableList = new ArrayList<TimeTableClassTo>();
		TimeTableClassTo classTo=null;
		for(int i=1;i<=7;i++){
			String day = getDayName(i);
			if(weekDayMap.containsKey(day)){
				classTo=new TimeTableClassTo();
				classTo.setPosition(i);
				classTo.setWeek(day);
				setPeriodListToClassTo(weekDayMap.get(classTo.getWeek()),classTo,periodList,list);
				timeTableList.add(classTo);
			}
		}
		return timeTableList;
	}
	/**
	 * @param i
	 * @return
	 * @throws Exception
	 */
	private String getDayName(int i)throws Exception {
		String day="";
    	if(i==7){
    		day="Sunday";
    	}else if(i==1){
    		day="Monday";
    	}else if(i==2){
    		day="Tuesday";
    	}else if(i==3){
    		day="Wednesday";
    	}else if(i==4){
    		day="Thursday";
    	}else if(i==5){
    		day="Friday";
    	}else if(i==6){
    		day="Saturday";
    	}
    	return day;
	}
	/**
	 * @param map
	 * @param classTo
	 * @param periodList
	 * @param list 
	 * @param periodListGroup 
	 * @throws Exception
	 */
	private void setPeriodListToClassTo(Map<Integer, TimeTablePeriodTo> map,
			TimeTableClassTo classTo, List<Period> periodList, List<Period> list)throws Exception {
		if(periodList!=null && !periodList.isEmpty()){
			Iterator<Period> itr=periodList.iterator();
			TimeTablePeriodTo to=null;
			Map<String,String> tempMap = new HashMap<String, String>();
			List<TimeTablePeriodTo> periodToList=new ArrayList<TimeTablePeriodTo>();
			while (itr.hasNext()) {
				Period period = itr.next();
				if(map!=null && map.containsKey(period.getId())){
					to=map.get(period.getId());
					tempMap.put(to.getPeriodName(), to.getPeriodName());
					periodToList.add(to);
				}
				/*else{
					to = new TimeTablePeriodTo();
					to.setStartTime(period.getStartTime());
					to.setPeriodName(period.getPeriodName());
					periodToList.add(to);
				}*/
			}
			if(list!=null && !list.isEmpty()){
				for (Period bo : list) {
					if(!tempMap.containsKey(bo.getPeriodName())){
						to = new TimeTablePeriodTo();
						to.setStartTime(bo.getStartTime());
						to.setPeriodName(bo.getPeriodName());
						periodToList.add(to);
					}
				}
			}
			//Collections.sort(periodToList);
			Collections.sort(periodToList, new TimeTableComparator());
			
			classTo.setTimeTablePeriodTos(periodToList);
		}
	}
	/**
	 * @param studentId
	 * @param classId
	 * @return
	 * @throws Exception
	 */
	public String getStudentTimeTableQuery(String studentId, String classId)throws Exception {
		String sqlQuery = "select period.id as periodId," +
				" period.period_name as periodName," +
				" tt_period_week.week_day as weekDay," +
				" group_concat(DISTINCT concat(' ',classes.name)) as className ," +
				" group_concat(DISTINCT concat(' ',subject.name)) as subjectName," +
				" period.start_time as startTime," +
				" concat(room_master.room_no, ' (',block.block_name,')') AS roomName," +
				" subject.code as subCode"+
				" from tt_class" +
				" inner join class_schemewise ON tt_class.class_schemewise_id = class_schemewise.id" +
				" inner join classes ON class_schemewise.class_id = classes.id" +
				" inner join curriculum_scheme_duration ON class_schemewise.curriculum_scheme_duration_id = curriculum_scheme_duration.id" +
				" inner join tt_period_week on tt_period_week.tt_class_id = tt_class.id" +
				" inner join period ON tt_period_week.period_id = period.id" +
				" inner join tt_subject_batch on tt_subject_batch.tt_period_id = tt_period_week.id" +
				" left join room_master on room_master.id = tt_subject_batch.room_master_id" +
				" left join block on block.id = room_master.block_id"+
				" inner join student On student.class_schemewise_id = class_schemewise.id" +
//				" inner join subject ON tt_subject_batch.subject_id = subject.id" +
				" inner join adm_appln on adm_appln.id = student.adm_appln_id" +
				" inner join applicant_subject_group on applicant_subject_group.adm_appln_id = adm_appln.id" +
				" inner join subject_group ON applicant_subject_group.subject_group_id = subject_group.id" +
				" inner join subject_group_subjects on subject_group_subjects.subject_group_id = subject_group.id" +
				" inner join subject  ON subject_group_subjects.subject_id = subject.id"+
				" where tt_class.is_active=1" +
				" and tt_class.is_approved =1" +
				" and tt_subject_batch.is_active=1" +
				" and tt_period_week.is_active =1" +
				" and period.is_active = 1" +
				" and classes.is_active =1" +
				" and student.id="+Integer.valueOf(studentId)+
				" and classes.id="+Integer.valueOf(classId)+
				" and tt_subject_batch.subject_id = subject.id"+
				" and curdate() between curriculum_scheme_duration.start_date and curriculum_scheme_duration.end_date"+
				" group by student.id,period.period_name,tt_period_week.week_day";

		return sqlQuery;
	}
	/**
	 * @param weekDayMap
	 * @param periodList
	 * @param batchWeekDayMap 
	 * @return
	 * @throws Exception
	 */
	public List<TimeTableClassTo> convertMapToTOList( Map<String, Map<Integer, TimeTablePeriodTo>> weekDayMap,
			List<Period> periodList, Map<String, Map<Integer, TimeTablePeriodTo>> batchWeekDayMap)throws Exception {
		List<TimeTableClassTo> timeTableList = new ArrayList<TimeTableClassTo>();
		TimeTableClassTo classTo=null;
		for(int i=1;i<=7;i++){
			String day = getDayName(i);    // get the day name by passing the number from (1-7).
			if(weekDayMap.containsKey(day)){
				classTo=new TimeTableClassTo();
				classTo.setPosition(i);
				classTo.setWeek(day);
				Map<Integer, TimeTablePeriodTo> periodMap = null;
//				if any batch is there for a particular day then get the Map of batch .
				if(batchWeekDayMap.containsKey(day)){
					periodMap = batchWeekDayMap.get(day);
				}
				setStudentPeriodListTO(weekDayMap.get(classTo.getWeek()),classTo,periodList,periodMap);
				timeTableList.add(classTo);
			}
		}
		return timeTableList;
	}
	/**
	 * @param map
	 * @param classTo
	 * @param periodList
	 * @param periodMap
	 * @throws Exception
	 */
	private void setStudentPeriodListTO(Map<Integer, TimeTablePeriodTo> periodMap,
			TimeTableClassTo classTo, List<Period> periodList, Map<Integer, TimeTablePeriodTo> batchPeriodMap) throws Exception{
		Iterator<Period> itr=periodList.iterator();
		TimeTablePeriodTo to=null;
		Map<String,String> tempMap = new HashMap<String, String>();
		List<TimeTablePeriodTo> periodToList=new ArrayList<TimeTablePeriodTo>();
		while (itr.hasNext()) {
			Period period = itr.next();
			if(batchPeriodMap!=null && batchPeriodMap.containsKey(period.getId())){
//				if batch is there for particular period ,then get the batch details TO.
				to=batchPeriodMap.get(period.getId());
				tempMap.put(to.getPeriodName(), to.getPeriodName());
				periodToList.add(to);
			}else if(periodMap!=null && periodMap.containsKey(period.getId())){
//				get the period details.
				to=periodMap.get(period.getId());
				tempMap.put(to.getPeriodName(), to.getPeriodName());
				periodToList.add(to);
			}else{
//				if there is no period , just create TO to Display the Empty Period.
				to = new TimeTablePeriodTo();
				to.setStartTime(period.getStartTime());
				to.setPeriodName(period.getPeriodName());
				periodToList.add(to);
			}
		}
		Collections.sort(periodToList);
		classTo.setTimeTablePeriodTos(periodToList);
	}
	/**
	 * @param objForm
	 * @return
	 * @throws Exception
	 */
	public String getStudentBatchSubjectsQuery( String studentId,String classId ) throws Exception{

		String sql_query = "select period.id as periodId," +
				" period.period_name as periodName," +
				" tt_period_week.week_day as weekDay," +
				" classes.name as className,"+
				" group_concat(DISTINCT concat(' ',subject.name)) as subjectName," +
				" period.start_time as startTime," +
				" concat(room_master.room_no, ' (',block.block_name,')') AS roomName,"+
				" subject.code as subCode"+
				" from tt_subject_batch as sub_batch" +
				" join tt_period_week ON sub_batch.tt_period_id = tt_period_week.id" +
				" join tt_class ON tt_period_week.tt_class_id = tt_class.id" +
				" join class_schemewise ON tt_class.class_schemewise_id = class_schemewise.id" +
				" join curriculum_scheme_duration ON class_schemewise.curriculum_scheme_duration_id = curriculum_scheme_duration.id" +
				" join period ON tt_period_week.period_id = period.id" +
				" join batch ON sub_batch.batch_id = batch.id" +
				" join batch_student on batch_student.batch_id = batch.id" +
				" join subject ON sub_batch.subject_id = subject.id" +
				" join classes on class_schemewise.class_id = classes.id" +
				" join student ON batch_student.student_id = student.id" +
				" left join room_master on room_master.id = sub_batch.room_master_id" +
				" left join block on block.id = room_master.block_id"+
				" where student.id =" +Integer.valueOf(studentId)+
				" and classes.id =" +Integer.valueOf(classId)+
				" and batch.is_active =1" +
				" and batch_student.is_active = 1" +
				" and period.is_active =1" +
				" and tt_period_week.is_active =1" +
				" and tt_class.is_active = 1" +
				" and subject.is_active = 1" +
				" AND tt_class.is_approved = 1"+
				" and curdate() between curriculum_scheme_duration.start_date and curriculum_scheme_duration.end_date"+
				" group by student.id,period.period_name,tt_period_week.week_day" ;
		
		return sql_query;
	}
	public String getClassTimeTableQuery(ViewTeacherWiseTimeTableForm objForm) throws Exception{
System.out.println("hi");
		String sql_Query = "SELECT period.id AS periodId," +
				" period.period_name AS periodName," +
				" tt_period_week.week_day AS weekDay," +
				" group_concat(DISTINCT concat (' ', classes.name)) AS className," +
				" group_concat(DISTINCT concat(' ', subject.name)) as subjectName," +
				" period.start_time AS startTime," +
				" concat(room_master.room_no, ' (',block.block_name,')') AS roomName,"+
				" subject.code as subCode," +
				" group_concat(DISTINCT concat (' ', ifnull(employee.first_name,guest_faculty.first_name), ' ')) AS teacherName" +
				" FROM tt_class" +
				" INNER JOIN class_schemewise ON tt_class.class_schemewise_id = class_schemewise.id" +
				" INNER JOIN classes ON class_schemewise.class_id = classes.id" +
				" INNER JOIN curriculum_scheme_duration ON class_schemewise.curriculum_scheme_duration_id = curriculum_scheme_duration.id" +
				" INNER JOIN tt_period_week ON tt_period_week.tt_class_id = tt_class.id" +
				" INNER JOIN period ON tt_period_week.period_id = period.id" +
				" INNER JOIN tt_subject_batch ON tt_subject_batch.tt_period_id = tt_period_week.id" +
				" LEFT JOIN room_master ON room_master.id = tt_subject_batch.room_master_id" +
				" left join block on block.id = room_master.block_id"+
				" INNER JOIN subject ON tt_subject_batch.subject_id = subject.id" +
				" INNER JOIN tt_users ON tt_users.tt_subject_id = tt_subject_batch.id" +
				" INNER JOIN users ON tt_users.user_id = users.id and tt_users.is_active=1" +
				" left join employee on users.employee_id = employee.id" +
				" left join department on department.id = employee.department_id" +
				" left join guest_faculty on users.guest_id=guest_faculty.id" +
				" WHERE tt_class.is_active = 1" +
				" AND tt_class.is_approved = 1" +
				" AND tt_subject_batch.is_active = 1" +
				" AND tt_period_week.is_active = 1" +
				" AND period.is_active = 1" +
				" AND classes.is_active = 1" +
				" AND class_schemewise.id="+Integer.valueOf(objForm.getClassSchemewiseId())+
				" AND curriculum_scheme_duration.academic_year = "+objForm.getYear()+
				" GROUP BY period.period_name, tt_period_week.week_day";
		
		return sql_Query;
	}
	public String getDeptTimeTableQuery(ViewTeacherWiseTimeTableForm objForm) {
		String hqlQuery = "select " +
		" period.id as periodId," +
		" period.period_name as periodName," +
		" tt_period_week.week_day as weekDay," +
		" group_concat(DISTINCT concat(' ',classes.name)) as className ," +
		" subject.code as subCode,"+
		" class_schemewise.class_id as classSchemeId,"+
		" classes.name as className,"+
		" employee.code as empCode,"+
		" subject_type.id as subTypeId,"+
		" subject_type.name as subTypeName, "+
		" classes.term_number as sem"+
		" from tt_class" +
		" inner join class_schemewise ON tt_class.class_schemewise_id = class_schemewise.id" +
		" inner join curriculum_scheme_duration ON class_schemewise.curriculum_scheme_duration_id = curriculum_scheme_duration.id" +
		" inner join tt_period_week on tt_period_week.tt_class_id = tt_class.id" +
		" and tt_period_week.is_active=1" +
		" inner join period on period.class_schemewise_id = class_schemewise.id" +
		" and tt_period_week.period_id = period.id and period.is_active=1" +
		" INNER JOIN tt_subject_batch ON tt_subject_batch.tt_period_id = tt_period_week.id" +
		" INNER JOIN tt_users ON tt_users.tt_subject_id = tt_subject_batch.id" +
		" INNER JOIN users ON tt_users.user_id = users.id" +
		" and tt_users.is_active=1" +
		" left join employee on users.employee_id = employee.id" +
		" INNER JOIN subject ON tt_subject_batch.subject_id = subject.id" +
		" INNER JOIN classes ON class_schemewise.class_id = classes.id" +
		" left JOIN room_master ON room_master.id = tt_subject_batch.room_master_id"+
		" left join block on block.id = room_master.block_id"+
		" left join department on subject.department_id = department.id" +
		" inner join subject_type ON subject_type.id = subject.subject_type_id"+
		" where " +
		" curdate() between curriculum_scheme_duration.start_date and curriculum_scheme_duration.end_date" +
		" and tt_class.is_active=1 and tt_class.is_approved=1" +
		" and tt_subject_batch.is_active=1" +
		" and department.id="+objForm.getDepartmentId()+
		" group by users.id,period.period_name,tt_period_week.week_day";
return hqlQuery;
	}
	public Map<String, Map<Integer, DepartmentTimeTablePeriodTo>> convertListToMapForDept(List<Object[]> list, ViewTeacherWiseTimeTableForm objForm,String mode)
			throws Exception {
			Map<String, Map<Integer, DepartmentTimeTablePeriodTo>> weekDayMap = new HashMap<String, Map<Integer,DepartmentTimeTablePeriodTo>>();
			Map<Integer, DepartmentTimeTablePeriodTo> periodMap = null;
			Set<Integer> periodList =new HashSet<Integer>();
			if(list!=null && !list.isEmpty()){
				for (Object[] obj : list) {
					if(!weekDayMap.containsKey(obj[2].toString())){
						periodMap = new HashMap<Integer, DepartmentTimeTablePeriodTo>();
					}else{
						periodMap = weekDayMap.get(obj[2].toString());
					}
					DepartmentTimeTablePeriodTo to = new DepartmentTimeTablePeriodTo();
					to.setPeriodId(Integer.parseInt(obj[0].toString()));
					periodList.add(Integer.parseInt(obj[0].toString()));
					to.setPeriodName(obj[1].toString());
					//to.setClassNames(obj[3].toString());
					to.setSubjectCode(obj[4].toString());
					if (obj[7]!=null) {
						to.setEmpCode(obj[7].toString());
					}
					to.setSubTypeId(obj[8].toString());
					to.setSubTypeName(obj[9].toString());
					to.setTermNo(obj[10].toString());
					String classname=null;
					if (to.getTermNo()=="1" || to.getTermNo().equalsIgnoreCase("1")) {
						classname="I sem "+to.getSubTypeName();
					}
					else if (to.getTermNo()=="2" || to.getTermNo().equalsIgnoreCase("2")) {
						classname="II sem "+to.getSubTypeName();
					}
					else if (to.getTermNo()=="3" || to.getTermNo().equalsIgnoreCase("3")) {
						classname="III sem "+to.getSubTypeName();
					}
					else if (to.getTermNo()=="4" || to.getTermNo().equalsIgnoreCase("4")) {
						classname="IV sem "+to.getSubTypeName();
					}
					else if (to.getTermNo()=="5" || to.getTermNo().equalsIgnoreCase("5")) {
						classname="V sem "+to.getSubTypeName();
					}
					else if (to.getTermNo()=="6" || to.getTermNo().equalsIgnoreCase("6")) {
						classname="VI sem "+to.getSubTypeName();
					}
					if (!periodMap.isEmpty()) {
						int i=0;
						for (Map.Entry<Integer, DepartmentTimeTablePeriodTo> entry : periodMap.entrySet()) {
							if (entry.getValue().getClassNames()!=null) {
								if (entry.getValue().getClassNames()==classname || entry.getValue().getClassNames().equalsIgnoreCase(classname)) {
								//to.setClassNames(classname);
								i++;
								break;
								}
							}
						}
						if (i==0) {
							to.setClassNames(classname);
						}
					}else
						to.setClassNames(classname);
					//to.setClassNames(classname);
					periodMap.put(Integer.parseInt(obj[0].toString()), to);
					weekDayMap.put(obj[2].toString(), periodMap);
				}
			}
			if(periodList!=null && !periodList.isEmpty()){
				objForm.setPeriodId(periodList);
			}
			return weekDayMap;
		}
	private void setDepartmentPeriodListToClassTo(Map<Integer, DepartmentTimeTablePeriodTo> map,
			DepartentTimeTableClassTo classTo, List<Period> periodList, List<Period> list)throws Exception {
		if(periodList!=null && !periodList.isEmpty()){
			Iterator<Period> itr=periodList.iterator();
			DepartmentTimeTablePeriodTo to=null;
			Map<String,String> tempMap = new HashMap<String, String>();
			List<DepartmentTimeTablePeriodTo> periodToList=new ArrayList<DepartmentTimeTablePeriodTo>();
			while (itr.hasNext()) {
				Period period = itr.next();
				if(map!=null && map.containsKey(period.getId())){
					to=map.get(period.getId());
					tempMap.put(to.getPeriodName(), to.getPeriodName());
					periodToList.add(to);
				}
				/*else{
					to = new TimeTablePeriodTo();
					to.setStartTime(period.getStartTime());
					to.setPeriodName(period.getPeriodName());
					periodToList.add(to);
				}*/
			}
			if(list!=null && !list.isEmpty()){
				for (Period bo : list) {
					if(!tempMap.containsKey(bo.getPeriodName())){
						to = new DepartmentTimeTablePeriodTo();
						to.setPeriodName(bo.getPeriodName());
						periodToList.add(to);
					}
				}
			}
			Collections.sort(periodToList);
			classTo.setDepartmentTimeTablePeriodTos(periodToList);
		}
	}
	public List<DepartentTimeTableClassTo> createTimeTableListForDepartment( Map<String, Map<Integer, DepartmentTimeTablePeriodTo>> weekDayMap,
			List<Period> periodList, List<Period> list)throws Exception {
		List<DepartentTimeTableClassTo> timeTableList = new ArrayList<DepartentTimeTableClassTo>();
		DepartentTimeTableClassTo classTo=null;
		for(int i=1;i<=7;i++){
			String day = getDayName(i);
			if(weekDayMap.containsKey(day)){
				classTo=new DepartentTimeTableClassTo();
				classTo.setPosition(i);
				classTo.setWeek(day);
				setDepartmentPeriodListToClassTo(weekDayMap.get(classTo.getWeek()),classTo,periodList,list);
				timeTableList.add(classTo);
			}
		}
		return timeTableList;
	}
}
