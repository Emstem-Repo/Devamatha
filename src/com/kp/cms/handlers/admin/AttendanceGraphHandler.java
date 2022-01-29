package com.kp.cms.handlers.admin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.gson.Gson;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.usermanagement.LoginForm;
import com.kp.cms.helpers.admission.AdmissionFormHelper;
import com.kp.cms.to.admin.AttendanceGrpahData;
import com.kp.cms.to.admin.StudentTO;
import com.kp.cms.transactions.usermanagement.ILoginTransaction;
import com.kp.cms.transactionsimpl.usermanagement.LoginTransactionImpl;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.StudentRollNoComparator;

public class AttendanceGraphHandler implements Comparator<AttendanceGrpahData> {
	private static Log log = LogFactory.getLog(AttendanceGraphHandler.class);
	public static volatile AttendanceGraphHandler attendanceGraphHandler = null;
	private static final String FROM_DATEFORMAT="dd-MM-yyyy";
	private static final String SQL_DATEFORMAT="dd-MMM-yyyy";
	public static AttendanceGraphHandler getInstance() {
		if (attendanceGraphHandler == null) {
			attendanceGraphHandler = new AttendanceGraphHandler();
			return attendanceGraphHandler;
		}
		return attendanceGraphHandler;
	}

	public String getAttendanceGraph(String sectionForAtt, LoginForm loginForm) throws Exception {
		log.debug("call of getAttendanceGraph method in AttendanceGraphHandler.class");
		List<AttendanceGrpahData> graphList = new ArrayList<AttendanceGrpahData>();
		ILoginTransaction loginTransaction = new LoginTransactionImpl();
		String grpahQuery = getAttendanceGraphQuery(loginForm);
		List<Object[]> list = new ArrayList<Object[]>();
		list = loginTransaction.getAttendaceGrpah(grpahQuery);
		graphList = convertObjectData(list);
		Gson gson = new Gson();
		String json = gson.toJson(graphList);  
        
		log.debug("end of getAttendanceGraph method in AttendanceGraphHandler.class");
		return json;
	}

	private List<AttendanceGrpahData> convertObjectData(List<Object[]> list) throws Exception{
		log.debug("call of convertObjectData method in AttendanceGraphHandler.class");
		List<AttendanceGrpahData> graphList =  new ArrayList<AttendanceGrpahData>();
		Iterator<Object[]> iterator = list.iterator();
		
		String Jan="";
		String Feb="";
		String Mar="";
		String Apr="";
		String May="";
		String Jun="";
		String Jul="";
		String Aug="";
		String Sep="";
		String Oct="";
		String Nov="";
		String Dec="";
		
		
		while(iterator.hasNext())
		{
			Object[] objects = iterator.next();
			
				if("1".equalsIgnoreCase(objects[3].toString())){
				Jan=objects[3].toString();
				AttendanceGrpahData grpahData = new AttendanceGrpahData();
				grpahData.setMonthName(objects[0].toString());
				grpahData.setTotalPresent(objects[1].toString());
				grpahData.setTotalConduct(objects[2].toString());
				
				Integer p=Integer.parseInt(objects[1].toString());
				Integer c=Integer.parseInt(objects[2].toString());
				Integer a=c-p;
				grpahData.setTotalAbsent(a.toString());
				
				grpahData.setPercentage(objects[4].toString());
				grpahData.setMonthNo(1);
				graphList.add(grpahData);
				
			}else if("2".equalsIgnoreCase(objects[3].toString())){
				Feb=objects[3].toString();
				AttendanceGrpahData grpahData = new AttendanceGrpahData();
				grpahData.setMonthName(objects[0].toString());
				grpahData.setTotalPresent(objects[1].toString());
				grpahData.setTotalConduct(objects[2].toString());
				Integer p=Integer.parseInt(objects[1].toString());
				Integer c=Integer.parseInt(objects[2].toString());
				Integer a=c-p;
				grpahData.setTotalAbsent(a.toString());
				
				grpahData.setPercentage(objects[4].toString());
				grpahData.setMonthNo(2);
				graphList.add(grpahData);
				
			}else if("3".equalsIgnoreCase(objects[3].toString())){
				Mar=objects[3].toString();
				AttendanceGrpahData grpahData = new AttendanceGrpahData();
				grpahData.setMonthName(objects[0].toString());
				grpahData.setTotalPresent(objects[1].toString());
				grpahData.setTotalConduct(objects[2].toString());
				Integer p=Integer.parseInt(objects[1].toString());
				Integer c=Integer.parseInt(objects[2].toString());
				Integer a=c-p;
				grpahData.setTotalAbsent(a.toString());
				
				grpahData.setPercentage(objects[4].toString());
				grpahData.setMonthNo(3);
				graphList.add(grpahData);
			}else if("4".equalsIgnoreCase(objects[3].toString())){
				Apr=objects[3].toString();
				AttendanceGrpahData grpahData = new AttendanceGrpahData();
				grpahData.setMonthName(objects[0].toString());
				grpahData.setTotalPresent(objects[1].toString());
				grpahData.setTotalConduct(objects[2].toString());
				Integer p=Integer.parseInt(objects[1].toString());
				Integer c=Integer.parseInt(objects[2].toString());
				Integer a=c-p;
				grpahData.setTotalAbsent(a.toString());
				
				grpahData.setPercentage(objects[4].toString());
				grpahData.setMonthNo(4);
				graphList.add(grpahData);
			}else if("5".equalsIgnoreCase(objects[3].toString())){
				May=objects[3].toString();
				AttendanceGrpahData grpahData = new AttendanceGrpahData();
				grpahData.setMonthName(objects[0].toString());
				grpahData.setTotalPresent(objects[1].toString());
				grpahData.setTotalConduct(objects[2].toString());
				Integer p=Integer.parseInt(objects[1].toString());
				Integer c=Integer.parseInt(objects[2].toString());
				Integer a=c-p;
				grpahData.setTotalAbsent(a.toString());
				
				grpahData.setPercentage(objects[4].toString());
				grpahData.setMonthNo(5);
				graphList.add(grpahData);
			}else if("6".equalsIgnoreCase(objects[3].toString())){
				Jun=objects[3].toString();
				AttendanceGrpahData grpahData = new AttendanceGrpahData();
				grpahData.setMonthName(objects[0].toString());
				grpahData.setTotalPresent(objects[1].toString());
				grpahData.setTotalConduct(objects[2].toString());
				Integer p=Integer.parseInt(objects[1].toString());
				Integer c=Integer.parseInt(objects[2].toString());
				Integer a=c-p;
				grpahData.setTotalAbsent(a.toString());
				
				grpahData.setPercentage(objects[4].toString());
				grpahData.setMonthNo(6);
				graphList.add(grpahData);
			}else if("7".equalsIgnoreCase(objects[3].toString())){
				Jul=objects[3].toString();
				AttendanceGrpahData grpahData = new AttendanceGrpahData();
				grpahData.setMonthName(objects[0].toString());
				grpahData.setTotalPresent(objects[1].toString());
				grpahData.setTotalConduct(objects[2].toString());
				Integer p=Integer.parseInt(objects[1].toString());
				Integer c=Integer.parseInt(objects[2].toString());
				Integer a=c-p;
				grpahData.setTotalAbsent(a.toString());
				
				grpahData.setPercentage(objects[4].toString());
				grpahData.setMonthNo(7);
				graphList.add(grpahData);
			}else if("8".equalsIgnoreCase(objects[3].toString())){
				Aug=objects[3].toString();
				AttendanceGrpahData grpahData = new AttendanceGrpahData();
				grpahData.setMonthName(objects[0].toString());
				grpahData.setTotalPresent(objects[1].toString());
				grpahData.setTotalConduct(objects[2].toString());
				Integer p=Integer.parseInt(objects[1].toString());
				Integer c=Integer.parseInt(objects[2].toString());
				Integer a=c-p;
				grpahData.setTotalAbsent(a.toString());
				
				grpahData.setPercentage(objects[4].toString());
				grpahData.setMonthNo(8);
				graphList.add(grpahData);
			}else if("9".equalsIgnoreCase(objects[3].toString())){
				Sep=objects[3].toString();
				AttendanceGrpahData grpahData = new AttendanceGrpahData();
				grpahData.setMonthName(objects[0].toString());
				grpahData.setTotalPresent(objects[1].toString());
				grpahData.setTotalConduct(objects[2].toString());
				Integer p=Integer.parseInt(objects[1].toString());
				Integer c=Integer.parseInt(objects[2].toString());
				Integer a=c-p;
				grpahData.setTotalAbsent(a.toString());
				
				grpahData.setPercentage(objects[4].toString());
				grpahData.setMonthNo(9);
				graphList.add(grpahData);
			}else if("10".equalsIgnoreCase(objects[3].toString())){
				Oct=objects[3].toString();
				AttendanceGrpahData grpahData = new AttendanceGrpahData();
				grpahData.setMonthName(objects[0].toString());
				grpahData.setTotalPresent(objects[1].toString());
				grpahData.setTotalConduct(objects[2].toString());
				Integer p=Integer.parseInt(objects[1].toString());
				Integer c=Integer.parseInt(objects[2].toString());
				Integer a=c-p;
				grpahData.setTotalAbsent(a.toString());
				
				grpahData.setPercentage(objects[4].toString());
				grpahData.setMonthNo(10);
				graphList.add(grpahData);
			}else if("11".equalsIgnoreCase(objects[3].toString())){
				Nov=objects[3].toString();
				AttendanceGrpahData grpahData = new AttendanceGrpahData();
				grpahData.setMonthName(objects[0].toString());
				grpahData.setTotalPresent(objects[1].toString());
				grpahData.setTotalConduct(objects[2].toString());
				Integer p=Integer.parseInt(objects[1].toString());
				Integer c=Integer.parseInt(objects[2].toString());
				Integer a=c-p;
				grpahData.setTotalAbsent(a.toString());
				
				grpahData.setPercentage(objects[4].toString());
				grpahData.setMonthNo(11);
				graphList.add(grpahData);
			}else if("12".equalsIgnoreCase(objects[3].toString())){
				Dec=objects[3].toString();
				AttendanceGrpahData grpahData = new AttendanceGrpahData();
				grpahData.setMonthName(objects[0].toString());
				grpahData.setTotalPresent(objects[1].toString());
				grpahData.setTotalConduct(objects[2].toString());
				Integer p=Integer.parseInt(objects[1].toString());
				Integer c=Integer.parseInt(objects[2].toString());
				Integer a=c-p;
				grpahData.setTotalAbsent(a.toString());
				
				grpahData.setPercentage(objects[4].toString());
				grpahData.setMonthNo(12);
				graphList.add(grpahData);
			}
			
			
			
		}
		
		
		if(Jan.equalsIgnoreCase("")){
			
			AttendanceGrpahData grpahData = new AttendanceGrpahData();
			grpahData.setMonthName("January");
			grpahData.setTotalPresent("0");
			grpahData.setTotalConduct("0");
			grpahData.setTotalAbsent("0");
			grpahData.setPercentage("0");
			grpahData.setMonthNo(1);
			graphList.add(grpahData);
			
		}
		
if(Feb.equalsIgnoreCase("")){
			
			AttendanceGrpahData grpahData = new AttendanceGrpahData();
			grpahData.setMonthName("February");
			grpahData.setTotalPresent("0");
			grpahData.setTotalConduct("0");
			grpahData.setTotalAbsent("0");
			grpahData.setPercentage("0");
			grpahData.setMonthNo(2);
			graphList.add(grpahData);
			
		}
if(Mar.equalsIgnoreCase("")){
	
	AttendanceGrpahData grpahData = new AttendanceGrpahData();
	grpahData.setMonthName("March");
	grpahData.setTotalPresent("0");
	grpahData.setTotalConduct("0");
	grpahData.setTotalAbsent("0");
	grpahData.setPercentage("0");
	grpahData.setMonthNo(3);
	graphList.add(grpahData);
	
}
if(Apr.equalsIgnoreCase("")){
	
	AttendanceGrpahData grpahData = new AttendanceGrpahData();
	grpahData.setMonthName("April");
	grpahData.setTotalPresent("0");
	grpahData.setTotalConduct("0");
	grpahData.setTotalAbsent("0");
	grpahData.setPercentage("0");
	grpahData.setMonthNo(4);
	graphList.add(grpahData);
	
}
if(May.equalsIgnoreCase("")){
	
	AttendanceGrpahData grpahData = new AttendanceGrpahData();
	grpahData.setMonthName("May");
	grpahData.setTotalPresent("0");
	grpahData.setTotalConduct("0");
	grpahData.setTotalAbsent("0");
	grpahData.setPercentage("0");
	grpahData.setMonthNo(5);
	graphList.add(grpahData);
	
}

if(Jun.equalsIgnoreCase("")){
	
	AttendanceGrpahData grpahData = new AttendanceGrpahData();
	grpahData.setMonthName("June");
	grpahData.setTotalPresent("0");
	grpahData.setTotalConduct("0");
	grpahData.setTotalAbsent("0");
	grpahData.setPercentage("0");
	grpahData.setMonthNo(6);
	graphList.add(grpahData);
	
}

if(Jul.equalsIgnoreCase("")){
	
	AttendanceGrpahData grpahData = new AttendanceGrpahData();
	grpahData.setMonthName("July");
	grpahData.setTotalPresent("0");
	grpahData.setTotalConduct("0");
	grpahData.setTotalAbsent("0");
	grpahData.setPercentage("0");
	grpahData.setMonthNo(7);
	graphList.add(grpahData);
	
}

if(Aug.equalsIgnoreCase("")){
	
	AttendanceGrpahData grpahData = new AttendanceGrpahData();
	grpahData.setMonthName("August");
	grpahData.setTotalPresent("0");
	grpahData.setTotalConduct("0");
	grpahData.setTotalAbsent("0");
	grpahData.setPercentage("0");
	grpahData.setMonthNo(8);
	graphList.add(grpahData);
	
}


if(Sep.equalsIgnoreCase("")){
	
	AttendanceGrpahData grpahData = new AttendanceGrpahData();
	grpahData.setMonthName("September");
	grpahData.setTotalPresent("0");
	grpahData.setTotalConduct("0");
	grpahData.setTotalAbsent("0");
	grpahData.setPercentage("0");
	grpahData.setMonthNo(9);
	graphList.add(grpahData);
	
}


if(Oct.equalsIgnoreCase("")){
	
	AttendanceGrpahData grpahData = new AttendanceGrpahData();
	grpahData.setMonthName("October");
	grpahData.setTotalPresent("0");
	grpahData.setTotalConduct("0");
	grpahData.setTotalAbsent("0");
	grpahData.setPercentage("0");
	grpahData.setMonthNo(10);
	graphList.add(grpahData);
	
}


if(Nov.equalsIgnoreCase("")){
	
	AttendanceGrpahData grpahData = new AttendanceGrpahData();
	grpahData.setMonthName("November");
	grpahData.setTotalPresent("0");
	grpahData.setTotalConduct("0");
	grpahData.setTotalAbsent("0");
	grpahData.setPercentage("0");
	grpahData.setMonthNo(11);
	graphList.add(grpahData);
	
}
if(Dec.equalsIgnoreCase("")){
	
	AttendanceGrpahData grpahData = new AttendanceGrpahData();
	grpahData.setMonthName("December");
	grpahData.setTotalPresent("0");
	grpahData.setTotalConduct("0");
	grpahData.setTotalAbsent("0");
	grpahData.setPercentage("0");
	grpahData.setMonthNo(12);
	graphList.add(grpahData);
	
}
	
		
AttendanceGraphHandler month = new AttendanceGraphHandler();
Collections.sort(graphList, month);

		log.debug("end of convertObjectData method in AttendanceGraphHandler.class");
		return graphList;
	}

	private String getAttendanceGraphQuery(LoginForm loginForm) {
		log.debug("call of getAttendanceGraphQuery method in AttendanceGraphHandler.class");
		/*String query = "SELECT concat(classes.name,substring(classes.section_name,8,7)) as class, "+                
			" ifnull((select count(attendance_student.is_present)  "+
			" FROM class_schemewise class_schemewise "+
			" INNER JOIN classes clas ON class_schemewise.class_id = clas.id "+
			" INNER JOIN student student ON student.class_schemewise_id = class_schemewise.id "+
			" INNER JOIN attendance_class attendance_class ON attendance_class.class_schemewise_id = class_schemewise.id "+
			" INNER JOIN attendance attendance ON attendance_class.attendance_id = attendance.id "+
			" INNER JOIN attendance_student attendance_student ON attendance_student.attendance_id = attendance.id "+
			" AND attendance_student.student_id = student.id "+
			" INNER JOIN adm_appln adm_appln on  student.adm_appln_id = adm_appln.id "+
			" INNER JOIN personal_data  personal_data on adm_appln.personal_data_id = personal_data.id "+
			" INNER JOIN  curriculum_scheme_duration curriculum_scheme_duration "+
			" on class_schemewise.curriculum_scheme_duration_id = curriculum_scheme_duration.id "+
			" where attendance.is_canceled=0  "+
			" and attendance.attendance_date=curdate() "+
			" and attendance.am_pm='"+sectionForAtt+"'"+
			"	and clas.id=classes.id "+
			"	group by am_pm,classes.name,classes.section_name,attendance.attendance_date "+
			"	order by term_number),0) as totalstudent, "+

			"	ifnull((select sum(attendance_student.is_present) "+
			"	FROM class_schemewise class_schemewise "+
			"	INNER JOIN classes clas ON class_schemewise.class_id = clas.id "+
			"	INNER JOIN student student ON student.class_schemewise_id = class_schemewise.id "+
			"	INNER JOIN attendance_class attendance_class ON attendance_class.class_schemewise_id = class_schemewise.id "+
			"	INNER JOIN attendance attendance ON attendance_class.attendance_id = attendance.id "+
			"	INNER JOIN attendance_student attendance_student ON attendance_student.attendance_id = attendance.id "+
			"	AND attendance_student.student_id = student.id "+
			"	INNER JOIN adm_appln adm_appln on  student.adm_appln_id = adm_appln.id "+
			"	INNER JOIN personal_data  personal_data on adm_appln.personal_data_id = personal_data.id "+
			"	INNER JOIN  curriculum_scheme_duration curriculum_scheme_duration "+
			"	on class_schemewise.curriculum_scheme_duration_id = curriculum_scheme_duration.id"+
			"	where attendance.is_canceled=0 "+
			"	and attendance.attendance_date=curdate() "+
			"	and attendance.am_pm='"+sectionForAtt+"'"+
			"		and clas.id=classes.id "+
			"		group by am_pm,classes.name,classes.section_name,attendance.attendance_date "+
			"		order by term_number),0)  as presentstudent, "+

			"		ifnull((select (count(attendance_student.is_present)-sum(attendance_student.is_present)) "+
			"				FROM class_schemewise class_schemewise "+
			"				INNER JOIN classes clas ON class_schemewise.class_id = clas.id "+
			"				INNER JOIN student student ON student.class_schemewise_id = class_schemewise.id "+
			"				INNER JOIN attendance_class attendance_class ON attendance_class.class_schemewise_id = class_schemewise.id "+
			"				INNER JOIN attendance attendance ON attendance_class.attendance_id = attendance.id"+
			"				INNER JOIN attendance_student attendance_student ON attendance_student.attendance_id = attendance.id"+
			"				AND attendance_student.student_id = student.id "+
			"				INNER JOIN adm_appln adm_appln on  student.adm_appln_id = adm_appln.id "+
			"				INNER JOIN personal_data  personal_data on adm_appln.personal_data_id = personal_data.id"+
			"				INNER JOIN  curriculum_scheme_duration curriculum_scheme_duration "+
			"				on class_schemewise.curriculum_scheme_duration_id = curriculum_scheme_duration.id "+
			"				where attendance.is_canceled=0 "+
			"				and attendance.attendance_date=curdate() "+
			"				and attendance.am_pm='"+sectionForAtt+"'"+
			"					and clas.id=classes.id "+
			"					group by am_pm,classes.name,classes.section_name,attendance.attendance_date "+
			"					order by term_number),0) as absentstudent " +
			"			FROM class_schemewise class_schemewise "+
			"					INNER JOIN classes classes ON class_schemewise.class_id = classes.id "+
			"					INNER JOIN curriculum_scheme_duration curriculum_scheme_duration "+
			"					ON class_schemewise.curriculum_scheme_duration_id = curriculum_scheme_duration.id "+
			"					where classes.is_active=1 "+
			"					and curriculum_scheme_duration.academic_year=2014 "+
			"					order by term_number,concat(classes.name,substring(classes.section_name,8,7)) ";							
		*/
		
		
		
		String query = "SELECT monthname(attendance.attendance_date),"+
				" sum(if(attendance_student.is_present=1 || attendance_student.is_cocurricular_leave=1,attendance.hours_held,0)) as ispresent,"+
               " sum(attendance.hours_held) as totoutoff,month(attendance.attendance_date),"+
               "round((((sum(if(attendance_student.is_present=1 || attendance_student.is_cocurricular_leave=1,attendance.hours_held,0)))/ sum(attendance.hours_held))*100))   as per1"+
                         
              " FROM    student student  "+                     
              " inner JOIN    attendance_student attendance_student     ON (attendance_student.student_id =      student.id)" +
              " inner JOIN       attendance attendance ON (attendance_student.attendance_id =  attendance.id)" +
              " inner JOIN attendance_class attendance_class  ON (attendance_class.attendance_id = attendance.id)" +
              " inner join class_schemewise class_schemewise     on (attendance_class.class_schemewise_id =  class_schemewise.id) and student.class_schemewise_id=class_schemewise.id" +
              " INNER JOIN     classes classes   ON (class_schemewise.class_id = classes.id)" +
              " INNER JOIN curriculum_scheme_duration curriculum_scheme_duration  ON (class_schemewise.curriculum_scheme_duration_id =  curriculum_scheme_duration.id)" +
              " INNER JOIN   adm_appln adm_appln ON (student.adm_appln_id = adm_appln.id)" +
              " INNER JOIN course course ON (classes.course_id = course.id)  AND (adm_appln.selected_course_id = course.id)" +
              " INNER JOIN  personal_data personal_data ON (adm_appln.personal_data_id = personal_data.id)" +
              " INNER JOIN subject subject ON (attendance.subject_id = subject.id)" +
              " left JOIN   EXAM_sub_definition_coursewise EXAM_sub_definition_coursewise    ON (EXAM_sub_definition_coursewise.course_id = course.id)" +
              "  AND (EXAM_sub_definition_coursewise.subject_id =  subject.id) and EXAM_sub_definition_coursewise.scheme_no=classes.term_number" +
              " where student.is_admitted=1  and adm_appln.is_cancelled=0 and attendance.is_canceled=0 and classes.id="+ Integer.parseInt(loginForm.getClassId())+" and student.id="+ loginForm.getStudentId() +
              " and course.id="+ Integer.parseInt(loginForm.getCourseId())+" and classes.term_number="+ loginForm.getTermNo()+
              " group by student.id,classes.id,month(attendance.attendance_date) order by month(attendance.attendance_date)";

		log.debug("end of getAttendanceGraphQuery method in AttendanceGraphHandler.class");
		return query;
	}


	 @Override
	 	public int compare(AttendanceGrpahData s1, AttendanceGrpahData s2) {
		 
	     	 return s1.getMonthNo().compareTo(s2.getMonthNo());
	     
	    }

	
}
