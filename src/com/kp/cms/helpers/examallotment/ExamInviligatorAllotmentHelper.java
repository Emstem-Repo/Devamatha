package com.kp.cms.helpers.examallotment;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;

import com.kp.cms.bo.admin.EmployeeWorkLocationBO;
import com.kp.cms.bo.admin.Users;
import com.kp.cms.bo.exam.ExamDefinition;
import com.kp.cms.bo.examallotment.ExamInvigilationDutySettings;
import com.kp.cms.bo.examallotment.ExamInvigilatorAvailable;
import com.kp.cms.bo.examallotment.ExamInviligatorDuties;
import com.kp.cms.bo.examallotment.ExamInviligatorExemptionDatewise;
import com.kp.cms.bo.examallotment.ExaminationSessions;
import com.kp.cms.bo.studentfeedback.RoomMaster;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.DataNotFoundException;
import com.kp.cms.forms.examallotment.ExamInviligatorAllotmentForm;
import com.kp.cms.forms.examallotment.InvigilatorsForExamForm;
import com.kp.cms.to.examallotment.InvigilatorsDatewiseExemptionTO;
import com.kp.cms.to.examallotment.InvigilatorsForExamTo;
import com.kp.cms.utilities.CommonUtil;

public class ExamInviligatorAllotmentHelper {
	
	private static volatile ExamInviligatorAllotmentHelper examInviligatorAllotmentHelper = null;
	private static final Log log = LogFactory.getLog(ExamInviligatorAllotmentHelper.class);
	private ExamInviligatorAllotmentHelper() {
		
	}
	/**
	 * return singleton object of AdminHallTicketHandler.
	 * @return
	 */
	public static ExamInviligatorAllotmentHelper getInstance() {
		if (examInviligatorAllotmentHelper == null) {
			examInviligatorAllotmentHelper = new ExamInviligatorAllotmentHelper();
		}
		return examInviligatorAllotmentHelper;
	}
	
	 
	 public String TeacherQuery(ExamInviligatorAllotmentForm invForm)
	    {
		 StringBuffer query = new StringBuffer ("select department.name, " +
				"users.id, employee.first_name, " +
				"(select count(invigilator.teacher_id) from EXAM_invigilator_duties as invigilator 	where invigilator.teacher_id = users.id " +
				"and invigilator.exam_id = EXAM_definition.id and invigilator.is_active=1 and invigilator.inviligator_or_reliver='I') as invigilator_duties_count, " +
				"(select session1.session from EXAM_invigilator_duties as duty_session "+
				" inner join EXAM_sessions as session1 ON duty_session.exam_session_id = session1.id "+
				" where duty_session.teacher_id = users.id and duty_session.exam_id = EXAM_definition.id and duty_session.is_active=1 "+
				"order by duty_session.exam_date desc limit 1 ) as prev_session "+

				/*"(select session from EXAM_invigilator_duties as duty_session where duty_session.teacher_id = users.id "+ 
                "and duty_session.exam_id = EXAM_definition.id and duty_session.is_active=1 "+ 
                "order by duty_session.exam_date desc limit 1 ) as prev_session "+*/
				"from EXAM_invigilator_available " +
				"inner join users on EXAM_invigilator_available.teacher_id = users.id " +
				"inner join employee ON users.employee_id = employee.id and employee.is_active=1 and employee.active=1 " +
				"inner join department ON employee.department_id = department.id " +
				"inner join EXAM_definition on EXAM_invigilator_available.exam_id = EXAM_definition.id and EXAM_definition.is_active=1 " +
				"left join EXAM_invigilator_duties on EXAM_invigilator_duties.teacher_id=users.id and EXAM_invigilator_duties.is_active=1  "+
				"and EXAM_invigilator_duties.exam_date='"+ CommonUtil.ConvertStringToSQLDate(invForm.getAllotmentDate())+"'" +
				" where employee.id not in(select employee_id from emp_apply_leave where '"+ CommonUtil.ConvertStringToSQLDate(invForm.getAllotmentDate())+"' between emp_apply_leave.from_date and emp_apply_leave.to_date  " +
																	" and ((is_half_day=0 or is_half_day is null) or ");
				if(invForm.getOrderNo() == 2){
					query = query.append(" (is_half_day=1 and is_am='AM') or (is_half_day=1 and is_am='PM'))) ");
				}else if(invForm.getOrderNo() == 1){
					query = query.append(" (is_half_day=1 and is_am='AM'))) ");
				}else if(invForm.getOrderNo() == 3){
					query = query.append(" (is_half_day=1 and is_am='PM'))) ");
				}
																	
				query = query.append(" and  users.id not in(select teacher_id from EXAM_invigilation_exemption_datewise " +
						" INNER JOIN EXAM_sessions ON EXAM_invigilation_exemption_datewise.exam_session_id=EXAM_sessions.id AND EXAM_sessions.id="+invForm.getExamSessionId()+
						" where EXAM_invigilation_exemption_datewise.date='"+ CommonUtil.ConvertStringToSQLDate(invForm.getAllotmentDate())+"'" +
			    " and EXAM_invigilation_exemption_datewise.is_active=1) " +
				" and users.id not in (select teacher_id from EXAM_invigilator_duty_exemption where EXAM_invigilator_duty_exemption.is_active=1) ");
				/*if(invForm.getPreviousDate()!=null && !invForm.getPreviousDate().isEmpty()){
					query = query.append(" and users.id not in (select teacher_id from EXAM_invigilator_duties where EXAM_invigilator_duties.exam_date='"+ CommonUtil.ConvertStringToSQLDate(invForm.getPreviousDate())+"' and EXAM_invigilator_duties.session='"+ invForm.getAllotmentSession()+"')");
				}*/
				query = query.append(" and EXAM_invigilator_available.is_active=1 " +
					"and EXAM_invigilator_available.exam_id=" +invForm.getExamId()
					+ " and employee.emp_teaching_staff=1 " +
					"and users.is_active=1 " +
					"and EXAM_invigilator_duties.id is null "+
					"and users.active=1 " +
					"and EXAM_invigilator_available.work_location_id=" +invForm.getWorkLocationId()
					+ " and users.staff_type='employee' order by invigilator_duties_count");
					if(invForm.getOrderNo() == 2){
						query = query.append(" , prev_session Desc, department.name");
					}else if(invForm.getOrderNo() == 1){
						query = query.append(" , prev_session, department.name");
					}else if(invForm.getOrderNo() == 3){
						query = query.append(" , prev_session, department.name");
					}
				
				
				
				
				
				
					/*if(invForm.getAllotmentSession()!=null && !invForm.getAllotmentSession().isEmpty() && invForm.getAllotmentSession().equalsIgnoreCase("AM")){
						query = query.append(" , prev_session Desc, department.name");
					}
					else if(invForm.getAllotmentSession()!=null && !invForm.getAllotmentSession().isEmpty() && invForm.getAllotmentSession().equalsIgnoreCase("PM")){
						query = query.append(" , prev_session, department.name");
					}*/
			 return query.toString();
	    }
	 
	 public String ReliverQuery(ExamInviligatorAllotmentForm invForm)
	    {
		 String Order="";
		 StringBuffer searchCriteria = new StringBuffer("select department.name, " +
				"users.id, employee.first_name, " +
				"(select count(distinct reliever.exam_date, session1.session, reliever.teacher_id) " +
				"  from EXAM_invigilator_duties as reliever "+
                " inner join EXAM_sessions as session1 on session1.id=reliever.exam_session_id "+
             	//"from EXAM_invigilator_duties as reliever " +
				"where reliever.teacher_id = users.id "+
				"and reliever.exam_id = EXAM_definition.id and reliever.is_active=1 and reliever.inviligator_or_reliver='R') as reliever_duties_count "+
				"from EXAM_invigilator_available " +
				"inner join users on EXAM_invigilator_available.teacher_id = users.id " +
				"inner join employee ON users.employee_id = employee.id and employee.is_active=1 and employee.active=1 " +
				"inner join department ON employee.department_id = department.id " +
				"inner join EXAM_definition on EXAM_invigilator_available.exam_id = EXAM_definition.id and EXAM_definition.is_active=1 " +
				"left join EXAM_invigilator_duties on EXAM_invigilator_duties.teacher_id=users.id and EXAM_invigilator_duties.is_active=1  "+
				"and EXAM_invigilator_duties.exam_date='"+ CommonUtil.ConvertStringToSQLDate(invForm.getAllotmentDate())+"'" +
				" where employee.id not in(select employee_id from emp_apply_leave where '"+ CommonUtil.ConvertStringToSQLDate(invForm.getAllotmentDate())+"' between emp_apply_leave.from_date and emp_apply_leave.to_date  " +
																	" and ((is_half_day=0 or is_half_day is null) or ");
		 	if(invForm.getOrderNo() == 2){
		 		searchCriteria = searchCriteria.append(" (is_half_day=1 and is_am='AM') or (is_half_day=1 and is_am='PM'))) ");
			}else if(invForm.getOrderNo() == 1){
				searchCriteria = searchCriteria.append(" (is_half_day=1 and is_am='AM'))) ");
			}else if(invForm.getOrderNo() == 3){
				searchCriteria = searchCriteria.append(" (is_half_day=1 and is_am='PM'))) ");
			}
		 	searchCriteria = searchCriteria.append(" and  users.id not in(select teacher_id from EXAM_invigilation_exemption_datewise " +
		 			" INNER JOIN EXAM_sessions ON EXAM_invigilation_exemption_datewise.exam_session_id=EXAM_sessions.id AND EXAM_sessions.id="+invForm.getExamSessionId()+
		 			" where EXAM_invigilation_exemption_datewise.date='"+ CommonUtil.ConvertStringToSQLDate(invForm.getAllotmentDate())+"'" +
			    " and EXAM_invigilation_exemption_datewise.is_active=1) " +
				" and users.id not in (select teacher_id from EXAM_invigilator_duty_exemption where EXAM_invigilator_duty_exemption.is_active=1) " +
				"and EXAM_invigilator_available.is_active=1 " +
				"and EXAM_invigilator_available.exam_id=" +invForm.getExamId()
				+ " and employee.emp_teaching_staff=1 " +
				"and users.is_active=1 " +
				"and EXAM_invigilator_duties.id is null "+
				"and users.active=1 " +
				" and EXAM_invigilator_available.work_location_id=" +invForm.getWorkLocationId()
				+ " and users.staff_type='employee'  " +
				"order by reliever_duties_count, department.name ");
		 if(Order.isEmpty()){
			 Order= "desc";
			 searchCriteria.append(Order);
		 }else
		 {
			 Order= "";
			 searchCriteria.append(Order);
		 }
	        return searchCriteria.toString();
	    }
	 
	 
	 public StringBuffer SessionQuery(ExamInviligatorAllotmentForm invForm){
		 StringBuffer query = new StringBuffer(	"select distinct date_Format(EXAM_time_table.date_starttime,'%d/%m/%Y') as exam_date, " +
		 		" EXAM_sessions.id as sessionId, EXAM_sessions.order_number as orderNo, EXAM_sessions.session_description" +
		 		" from EXAM_exam_course_scheme_details " +
		 		"INNER JOIN EXAM_time_table ON EXAM_time_table.exam_course_scheme_id = EXAM_exam_course_scheme_details.id " +
		 		" INNER JOIN EXAM_sessions ON EXAM_time_table.exam_session_id=EXAM_sessions.id" +
		 		" inner join course ON EXAM_exam_course_scheme_details.course_id = course.id " +
		 		"where EXAM_time_table.is_active=1 " +
		 		"and EXAM_exam_course_scheme_details.is_active=1  and course.work_location_id="+invForm.getWorkLocationId());
			 	if (invForm.getExamId() != null && !StringUtils.isEmpty(invForm.getExamId().trim())) {
					query = query.append(" and EXAM_exam_course_scheme_details.exam_id=" +invForm.getExamId());
				}
			 	if ((invForm.getFromDate() != null && !invForm.getFromDate().isEmpty()) && (invForm.getToDate() != null && !invForm.getToDate().isEmpty())) {
					query = query.append(" and date(EXAM_time_table.date_starttime) between '"+ CommonUtil.ConvertStringToSQLDate(invForm.getFromDate())+"'"
		 		+ " and '"+ CommonUtil.ConvertStringToSQLDate(invForm.getToDate())+"'");
				}
			 	query = query.append(" order by date_Format(EXAM_time_table.date_starttime,'%d/%m/%Y'),EXAM_sessions.order_number");
		 		/*"and EXAM_exam_course_scheme_details.exam_id=" +invForm.getExamId()
		 		+ " and date(EXAM_time_table.date_starttime) between '"+ CommonUtil.ConvertStringToSQLDate(invForm.getFromDate())+"'"
		 		+ " and '"+ CommonUtil.ConvertStringToSQLDate(invForm.getToDate())+"'" 
		 		+ " order by EXAM_time_table.date_starttime";*/
		 return query;
	}
	
	 
	 public String RoomStudentQueryAm(ExamInviligatorAllotmentForm invForm)
	    {
		 
		 String q="select room_master.room_no, room_master.id, count(distinct EXAM_room_allotment_details.student_id) as nos ,block.block_name, room_master.floor_no "+
		 		  "from EXAM_room_allotment_details "+
		 		  "inner join EXAM_room_allotment on EXAM_room_allotment_details.exam_room_allotment_id= EXAM_room_allotment.id "+
		 		  "inner join classes ON EXAM_room_allotment_details.class_id = classes.id "+
		 		  "inner join course ON classes.course_id = course.id "+
		 		  "inner join room_master ON EXAM_room_allotment.room_id = room_master.id "+
		 		  "inner join block on block.id=room_master.block_id "+
		 		 "inner join EXAM_rooms_available ON room_master.id=EXAM_rooms_available.room_id "+
		 		  "inner join EXAM_exam_course_scheme_details on EXAM_exam_course_scheme_details.course_id = course.id "+
		 		  "and EXAM_exam_course_scheme_details.scheme_no=classes.term_number "+
		 		  "and EXAM_exam_course_scheme_details.exam_id=" +invForm.getExamId()
		         + " inner JOIN EXAM_time_table on EXAM_time_table.exam_course_scheme_id=EXAM_exam_course_scheme_details.id "+
		 		  "and EXAM_time_table.is_active=1 "+
		 		  "AND time(EXAM_time_table.date_starttime) < '12:00:00' "+
		 		  " and date(EXAM_time_table.date_starttime) = '"+ CommonUtil.ConvertStringToSQLDate(invForm.getAllotmentDate())+"'"
		 		  + "INNER JOIN student ON student.id=EXAM_room_allotment_details.student_id "
				+" INNER JOIN subject ON (EXAM_time_table.subject_id = subject.id) "
		         +" where EXAM_room_allotment.exam_id=" +invForm.getExamId()
		         +" and course.work_location_id= " +invForm.getWorkLocationId()
		         +" and block.location_id=" +invForm.getWorkLocationId()
		         +" and EXAM_room_allotment.is_active=1 AND EXAM_rooms_available.is_active=1 "
		         +" and EXAM_room_allotment_details.is_active=1"
				+" and subject.id in "
				+" (select subject1.id from applicant_subject_group "
				+" inner join adm_appln ON applicant_subject_group.adm_appln_id = adm_appln.id "
				+" inner join student as student1 on student1.adm_appln_id = adm_appln.id"
				+" inner join subject_group ON applicant_subject_group.subject_group_id = subject_group.id"
				+" inner join subject_group_subjects on subject_group_subjects.subject_group_id = subject_group.id and subject_group_subjects.is_active=1"
				+" inner join subject as subject1 ON subject_group_subjects.subject_id = subject1.id"
				+" where student1.id = student.id"
				+" union"
				+" select old_subject.id from student as student2 "
				+" inner join EXAM_student_sub_grp_history on EXAM_student_sub_grp_history.student_id = student2.id"
				+" inner join subject_group as old_subject_group on EXAM_student_sub_grp_history.subject_group_id = old_subject_group.id"
				+" inner join subject_group_subjects as old_subject_group_subjects on old_subject_group_subjects.subject_group_id = old_subject_group.id and old_subject_group_subjects.is_active=1"
				+" inner join subject as old_subject on old_subject_group_subjects.subject_id = old_subject.id"
				+" where EXAM_student_sub_grp_history.scheme_no = classes.term_number and"
				+"  student2.id = student.id)"
		        +" group by room_master.id"
		        +" order by block.block_order, room_master.floor_no, room_master.room_no";
		 
	        return q;
	    }
	 public String roomStudentQueryPm(ExamInviligatorAllotmentForm invForm)
	    {
		 
		 String q="select room_master.room_no, room_master.id, count(distinct EXAM_room_allotment_details.student_id) as nos ,block.block_name, room_master.floor_no "+
		 		  "from EXAM_room_allotment_details "+
		 		  "inner join EXAM_room_allotment on EXAM_room_allotment_details.exam_room_allotment_id= EXAM_room_allotment.id "+
		 		  "inner join classes ON EXAM_room_allotment_details.class_id = classes.id "+
		 		  "inner join course ON classes.course_id = course.id "+
		 		  "inner join room_master ON EXAM_room_allotment.room_id = room_master.id "+
		 		  "inner join block on block.id=room_master.block_id "+
		 		 "inner join EXAM_rooms_available ON room_master.id=EXAM_rooms_available.room_id "+
		 		  "inner join EXAM_exam_course_scheme_details on EXAM_exam_course_scheme_details.course_id = course.id "+
		 		  "and EXAM_exam_course_scheme_details.scheme_no=classes.term_number "+
		 		  "and EXAM_exam_course_scheme_details.exam_id=" +invForm.getExamId()
		         + " inner JOIN EXAM_time_table on EXAM_time_table.exam_course_scheme_id=EXAM_exam_course_scheme_details.id "+
		 		  "and EXAM_time_table.is_active=1 "+
		 		  "AND time(EXAM_time_table.date_starttime) >= '12:00:00' "+
		 		  " and date(EXAM_time_table.date_starttime) = '"+ CommonUtil.ConvertStringToSQLDate(invForm.getAllotmentDate())+"'"
		 		 + "INNER JOIN student ON student.id=EXAM_room_allotment_details.student_id "
				 +" INNER JOIN subject ON (EXAM_time_table.subject_id = subject.id) "
		         +" where EXAM_room_allotment.exam_id=" +invForm.getExamId()
		         +" and course.work_location_id= " +invForm.getWorkLocationId()
		         +" and block.location_id=" +invForm.getWorkLocationId()
		         +" and EXAM_room_allotment.is_active=1 AND EXAM_rooms_available.is_active=1 "
		         +" and EXAM_room_allotment_details.is_active=1"
		         +" and subject.id in "
					+" (select subject1.id from applicant_subject_group "
					+" inner join adm_appln ON applicant_subject_group.adm_appln_id = adm_appln.id "
					+" inner join student as student1 on student1.adm_appln_id = adm_appln.id"
					+" inner join subject_group ON applicant_subject_group.subject_group_id = subject_group.id"
					+" inner join subject_group_subjects on subject_group_subjects.subject_group_id = subject_group.id"
					+" inner join subject as subject1 ON subject_group_subjects.subject_id = subject1.id"
					+" where student1.id = student.id"
					+" union"
					+" select old_subject.id from student as student2 "
					+" inner join EXAM_student_sub_grp_history on EXAM_student_sub_grp_history.student_id = student2.id"
					+" inner join subject_group as old_subject_group on EXAM_student_sub_grp_history.subject_group_id = old_subject_group.id"
					+" inner join subject_group_subjects as old_subject_group_subjects on old_subject_group_subjects.subject_group_id = old_subject_group.id"
					+" inner join subject as old_subject on old_subject_group_subjects.subject_id = old_subject.id"
					+" where EXAM_student_sub_grp_history.scheme_no = classes.term_number and"
					+"  student2.id = student.id)"
					  +" group by room_master.id"
		        +" order by block.block_order, room_master.floor_no, room_master.room_no";
	      
	        return q;
	    }
	 
public List<ExamInviligatorDuties> setInvigilatorDuty(ExamInviligatorAllotmentForm InvForm, List<Object[]> teacherList, List<Object[]> RoomList, ExamInvigilationDutySettings settings, String examType, ActionErrors errors) throws Exception
 {
     List<ExamInviligatorDuties> invDutiesList = new ArrayList<ExamInviligatorDuties>();
     int  MaxNostdPerTchr=0;
     int  NoOfRoomsPerReliver=0;
     if(settings != null)
     {
    	 MaxNostdPerTchr=settings.getMaxNoOfStudentsPerTeacher();
    	 NoOfRoomsPerReliver=settings.getNoOfRoomsPerReliever();
     }
     getInvigilatorAllotted(MaxNostdPerTchr,InvForm, teacherList,RoomList,examType, errors, invDutiesList);
    // getReliverAllotted(MaxNostdPerTchr,NoOfRoomsPerReliver,InvForm, ReliverList,RoomList,examType, errors, invDutiesList);
 //    InvForm.setPreviousDate(InvForm.getEndDate());
    return invDutiesList;
   }
public List<ExamInviligatorDuties> setReliverDuty(ExamInviligatorAllotmentForm InvForm,List<Object[]> ReliverList, List<Object[]> RoomList, ExamInvigilationDutySettings settings, String examType, ActionErrors errors) throws Exception
{
    List<ExamInviligatorDuties> invDutiesList = new ArrayList<ExamInviligatorDuties>();
    int  MaxNostdPerTchr=0;
    int  NoOfRoomsPerReliver=0;
    if(settings != null)
    {
   	 MaxNostdPerTchr=settings.getMaxNoOfStudentsPerTeacher();
   	 NoOfRoomsPerReliver=settings.getNoOfRoomsPerReliever();
    }
   // getInvigilatorAllotted(MaxNostdPerTchr,InvForm, teacherList,RoomList,examType, errors, invDutiesList);
    if(NoOfRoomsPerReliver>0){
    	getReliverAllotted(MaxNostdPerTchr,NoOfRoomsPerReliver,InvForm, ReliverList,RoomList,examType, errors, invDutiesList);
    }
   return invDutiesList;
  }
public StringBuilder createQueryForSearch(
		ExamInviligatorAllotmentForm InvForm) throws Exception{
	StringBuilder query=new StringBuilder();
	query=query.append("from ExamInvigilatorAvailable u where u.isActive=1 and u.examId.id="+Integer.parseInt(InvForm.getExamId())
			+" and  u.workLocationId.id="+Integer.parseInt(InvForm.getWorkLocationId()));
	query=query.append("and u.teacherId not in (select e.teacherId from ExamInvigilatorDutyExemption e where e.isActive=1)");
	query=query.append(" order by u.teacherId.employee.department.name ,u.teacherId.employee.firstName");
return query;
}

public StringBuilder createQueryForSearchExempted(
		ExamInviligatorAllotmentForm InvForm) throws Exception{
	StringBuilder query=new StringBuilder();
	query=query.append("from ExamInviligatorExemptionDatewise u where u.isActive=1 and u.examId.id="+Integer.parseInt(InvForm.getExamId())
			+" and  u.teacherId.employee.workLocationId.id="+Integer.parseInt(InvForm.getWorkLocationId()));
	query=query.append(" order by u.teacherId.employee.department.name ,u.teacherId.employee.firstName,u.date,u.session");
return query;
}


public List<InvigilatorsForExamTo> convertBosToToslidt(
		List<ExamInvigilatorAvailable> list) {
	List<InvigilatorsForExamTo> invigilatorsForExamTos=null;
	if(list!=null && !list.isEmpty()){
		invigilatorsForExamTos=new ArrayList<InvigilatorsForExamTo>();
		Iterator<ExamInvigilatorAvailable> iterator=list.iterator();
		InvigilatorsForExamTo invigilatorsForExamTo=null;
		while (iterator.hasNext()) {
			ExamInvigilatorAvailable examInvigilatorAvailable = (ExamInvigilatorAvailable) iterator.next();
			invigilatorsForExamTo=new InvigilatorsForExamTo();
			invigilatorsForExamTo.setId(examInvigilatorAvailable.getId());
			StringBuilder name=new StringBuilder();
			if(examInvigilatorAvailable.getTeacherId().getEmployee()!=null && examInvigilatorAvailable.getTeacherId().getEmployee().getFirstName()!=null && !examInvigilatorAvailable.getTeacherId().getEmployee().getFirstName().isEmpty()){
				name=name.append(examInvigilatorAvailable.getTeacherId().getEmployee().getFirstName());
			}
			if(examInvigilatorAvailable.getTeacherId().getEmployee()!=null && examInvigilatorAvailable.getTeacherId().getEmployee().getMiddleName()!=null && !examInvigilatorAvailable.getTeacherId().getEmployee().getMiddleName().isEmpty()){
				name=name.append(examInvigilatorAvailable.getTeacherId().getEmployee().getMiddleName());
			}
			invigilatorsForExamTo.setName(name.toString());
			if(examInvigilatorAvailable.getTeacherId().getEmployee()!=null && examInvigilatorAvailable.getTeacherId().getEmployee().getDepartment()!=null && examInvigilatorAvailable.getTeacherId().getEmployee().getDepartment().getName()!=null){
				invigilatorsForExamTo.setDepartment(examInvigilatorAvailable.getTeacherId().getEmployee().getDepartment().getName());
			}
			if(examInvigilatorAvailable.getExamId().getName()!=null){
				invigilatorsForExamTo.setExamName(examInvigilatorAvailable.getExamId().getName());
			}
			if(examInvigilatorAvailable.getWorkLocationId().getName()!=null){
				invigilatorsForExamTo.setWorkLocation(examInvigilatorAvailable.getWorkLocationId().getName());
			}
			invigilatorsForExamTos.add(invigilatorsForExamTo);
		}
	}
	return invigilatorsForExamTos;
}
	
	public List<InvigilatorsDatewiseExemptionTO> convertBosToTosList(List<ExamInviligatorExemptionDatewise> list) {
		List<InvigilatorsDatewiseExemptionTO> invigilatorsExemptedTos=null;
		if(list!=null && !list.isEmpty()){
			invigilatorsExemptedTos=new ArrayList<InvigilatorsDatewiseExemptionTO>();
			Iterator<ExamInviligatorExemptionDatewise> iterator=list.iterator();
			InvigilatorsDatewiseExemptionTO invigilatorsForExamTo=null;
			while (iterator.hasNext()) {
				ExamInviligatorExemptionDatewise examInvigilatorAvailable = (ExamInviligatorExemptionDatewise) iterator.next();
				invigilatorsForExamTo=new InvigilatorsDatewiseExemptionTO();
				invigilatorsForExamTo.setId(examInvigilatorAvailable.getId());
				StringBuilder name=new StringBuilder();
				if(examInvigilatorAvailable.getTeacherId().getEmployee()!=null && examInvigilatorAvailable.getTeacherId().getEmployee().getFirstName()!=null && !examInvigilatorAvailable.getTeacherId().getEmployee().getFirstName().isEmpty()){
					name=name.append(examInvigilatorAvailable.getTeacherId().getEmployee().getFirstName());
				}
				if(examInvigilatorAvailable.getTeacherId().getEmployee()!=null && examInvigilatorAvailable.getTeacherId().getEmployee().getMiddleName()!=null && !examInvigilatorAvailable.getTeacherId().getEmployee().getMiddleName().isEmpty()){
					name=name.append(examInvigilatorAvailable.getTeacherId().getEmployee().getMiddleName());
				}
				invigilatorsForExamTo.setName(name.toString());
				if(examInvigilatorAvailable.getDate()!=null){
					invigilatorsForExamTo.setDate(CommonUtil.formatSqlDate(examInvigilatorAvailable.getDate().toString()));
				}
				if(examInvigilatorAvailable.getExamId().getName()!=null){
					invigilatorsForExamTo.setExamName(examInvigilatorAvailable.getExamId().getName());
				}
				if(examInvigilatorAvailable.getExaminationSessions()!=null){
					invigilatorsForExamTo.setSession(examInvigilatorAvailable.getExaminationSessions().getSessionDescription());
				}
				if(examInvigilatorAvailable.getTeacherId().getEmployee().getDepartment().getName()!=null && !examInvigilatorAvailable.getTeacherId().getEmployee().getDepartment().getName().isEmpty()){
					invigilatorsForExamTo.setDepartment(examInvigilatorAvailable.getTeacherId().getEmployee().getDepartment().getName());
				}
				invigilatorsExemptedTos.add(invigilatorsForExamTo);
			}
		}
		return invigilatorsExemptedTos;
	}



@SuppressWarnings("deprecation")
private void getInvigilatorAllotted(int MaxNostdPerTchr, ExamInviligatorAllotmentForm InvForm, List<Object[]> teacherList, List<Object[]> RoomList,String examType,ActionErrors errors, List<ExamInviligatorDuties> invDutiesList) throws DataNotFoundException
{
	int remainingteacher=teacherList.size();
	int remainingRoomNos=RoomList.size();
	String date=InvForm.getAllotmentDate();
	String session=InvForm.getSessionDescription(); 
	if(RoomList != null && !RoomList.isEmpty())
    {
    	Iterator<Object[]> stIterator = RoomList.iterator();
		while (stIterator.hasNext()) {
			Object[] obj = (Object[]) stIterator.next();
			ExamInviligatorDuties	duties=null;
			if(obj[1]!=null){
				if(obj[2]!=null){
					int studentNos=Integer.parseInt(obj[2].toString());	
					 if(studentNos>0){
							if(MaxNostdPerTchr<studentNos)
							{
								BigDecimal decimal=new BigDecimal(studentNos);
								BigDecimal decimal2=new BigDecimal(MaxNostdPerTchr);
								BigDecimal decimal3=decimal.divide(decimal2, RoundingMode.CEILING);
									if(teacherList != null && !teacherList.isEmpty())
								     {
								     	Iterator<Object[]> tchItr = teacherList.iterator();
								     	int count =1;
										while(tchItr.hasNext()) {
											if(count>decimal3.intValue()){
												break;
											}
											Object[] objTeacher = (Object[]) tchItr.next();
											duties=new ExamInviligatorDuties();
											RoomMaster rm=new RoomMaster();
											rm.setId(Integer.parseInt(obj[1].toString()));
											duties.setRoomId(rm);
											Users us=new Users();
											us.setId(Integer.parseInt(objTeacher[1].toString()));
											duties.setTeacherId(us);
											ExamDefinition exam=new ExamDefinition();
											exam.setId(Integer.parseInt(InvForm.getExamId()));
											duties.setExamId(exam);
										    duties.setInvOrReliver("I");
										    duties.setIsActive(true);
										    duties.setLastModifiedDate(new Date());
										    duties.setCreatedBy(InvForm.getUserId());
										    duties.setCreatedDate(new Date());
										    duties.setExamDate(CommonUtil.ConvertStringToDate(InvForm.getAllotmentDate()));
										    duties.setModifiedBy(InvForm.getUserId());
//										    duties.setSession(InvForm.getAllotmentSession());
										    EmployeeWorkLocationBO work =new EmployeeWorkLocationBO();
										    work.setId(Integer.parseInt(InvForm.getWorkLocationId()));
										    duties.setWorkLocationId(work);
										    duties.setIsApproved(false);
										    if(InvForm.getExamSessionId() != 0){
										    	ExaminationSessions examinationSessions = new ExaminationSessions();
										    	examinationSessions.setId(InvForm.getExamSessionId());
												duties.setExaminationSessions(examinationSessions );
										    }
										    invDutiesList.add(duties);
										    tchItr.remove();
											count++;

											//teacherCount++;
											}
								     }
								else{
									 if(remainingteacher<remainingRoomNos){
											int bal=remainingRoomNos-remainingteacher;
											BigDecimal rooms=new BigDecimal(remainingRoomNos);
											BigDecimal noOfRoomsPerRelivers=new BigDecimal(6);
											BigDecimal reliversReq=rooms.divide(noOfRoomsPerRelivers, RoundingMode.CEILING);
											BigDecimal teachersrequired=new BigDecimal(bal);
											//BigDecimal reliversRequired=new BigDecimal(reliversReq);
											BigDecimal totalteachersrequired= teachersrequired.add(reliversReq);
											errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.teacher.allotment.teacher.not.available.count ",totalteachersrequired,session,date));
											throw new DataNotFoundException();

									}
								}
						}
						else if(MaxNostdPerTchr>=studentNos)
						{
							if(teacherList != null && !teacherList.isEmpty())
						     {
								
						     	Iterator<Object[]> tchItr = teacherList.iterator();
								while (tchItr.hasNext()) {
									duties=new ExamInviligatorDuties();
									Object[] objTeacher = (Object[]) tchItr.next();
									RoomMaster rm=new RoomMaster();
									rm.setId(Integer.parseInt(obj[1].toString()));
									duties.setRoomId(rm);
									Users us=new Users();
									us.setId(Integer.parseInt(objTeacher[1].toString()));
									duties.setTeacherId(us);
									ExamDefinition exam=new ExamDefinition();
									exam.setId(Integer.parseInt(InvForm.getExamId()));
									duties.setExamId(exam);
								    duties.setInvOrReliver("I");
								    duties.setIsActive(true);
								    duties.setLastModifiedDate(new Date());
								    duties.setCreatedBy(InvForm.getUserId());
								    duties.setCreatedDate(new Date());
								    duties.setExamDate(CommonUtil.ConvertStringToDate(InvForm.getAllotmentDate()));
								    duties.setModifiedBy(InvForm.getUserId());
//								    duties.setSession(InvForm.getAllotmentSession());
								    EmployeeWorkLocationBO work =new EmployeeWorkLocationBO();
								    work.setId(Integer.parseInt(InvForm.getWorkLocationId()));
								    duties.setWorkLocationId(work);
								    duties.setIsApproved(false);
								    if(InvForm.getExamSessionId() != 0){
								    	ExaminationSessions examinationSessions = new ExaminationSessions();
								    	examinationSessions.setId(InvForm.getExamSessionId());
										duties.setExaminationSessions(examinationSessions );
								    }
								    invDutiesList.add(duties);
								    tchItr.remove();
									//teacherCount++;
								  //  remainingteacher=teacherList.size();
								//	remainingRoomNos=RoomList.size();

									break;
								}
						     }else{
						    	 if(remainingteacher<remainingRoomNos){
										int bal=remainingRoomNos-remainingteacher;
										BigDecimal rooms=new BigDecimal(remainingRoomNos);
										BigDecimal noOfRoomsPerRelivers=new BigDecimal(6);
										BigDecimal reliversReq=rooms.divide(noOfRoomsPerRelivers, RoundingMode.CEILING);
										BigDecimal teachersrequired=new BigDecimal(bal);
										//BigDecimal reliversRequired=new BigDecimal(reliversReq);
										BigDecimal totalteachersrequired= teachersrequired.add(reliversReq);
										errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.teacher.allotment.teacher.not.available.count",totalteachersrequired,session,date));
										throw new DataNotFoundException();

									//}
									}						    }
							
						}
					 }
						
				}
			}
		}
    }
}

@SuppressWarnings("deprecation")
private void getReliverAllotted(int MaxNostdPerTchr, int NoOfRoomsPerReliver, ExamInviligatorAllotmentForm InvForm,
		List<Object[]> teacherList, List<Object[]> RoomList,String examType,ActionErrors errors, List<ExamInviligatorDuties> invDutiesList) throws DataNotFoundException
{
	String date=InvForm.getAllotmentDate();
	String session=InvForm.getSessionDescription();
	int remainingteacher=teacherList.size();
	List<Object[]> RoomListForReliver=RoomList;
	int remainingRoomNos=RoomListForReliver.size();
	int checkRoom=NoOfRoomsPerReliver*2;
	boolean flag=true;
	boolean test=true;
	BigDecimal decimal3=null;
	BigDecimal leftToAllot=null;
		if(teacherList != null && !teacherList.isEmpty())
		{
			  Iterator<Object[]> tchItr = teacherList.iterator();
				while (tchItr.hasNext()) {
				Object[] objTeacher = (Object[]) tchItr.next();
				int count=0;
				int count1=1;
				if(RoomListForReliver!=null && !RoomListForReliver.isEmpty()){
				Iterator<Object[]> stReliverItr = RoomListForReliver.iterator();
				
				while (stReliverItr.hasNext()) 
				{
					/*if(InvForm.getAllotmentDate().equalsIgnoreCase("18/01/14y")){
						System.out.println("Hello");
					}*/
					// remainingRoomNos=RoomListForReliver.size()+1;
					Object[] obj = (Object[]) stReliverItr.next();
					     
					        String blockName=null;
							String OldBlock=null;
					if(obj[2]!=null){
							int studentNos=Integer.parseInt(obj[2].toString());	
							if(studentNos>0){
							if(studentNos>MaxNostdPerTchr){
									stReliverItr.remove();
									
							}else if(remainingRoomNos>0 && remainingRoomNos<checkRoom)
							{
									if(flag && (remainingRoomNos>NoOfRoomsPerReliver)){
											BigDecimal decimal=new BigDecimal(remainingRoomNos);
											BigDecimal decimal2=new BigDecimal(2);
											decimal3=decimal.divide(decimal2, RoundingMode.CEILING);
											leftToAllot=decimal.subtract(decimal3);
											flag=false;
									}else if(remainingRoomNos<=NoOfRoomsPerReliver)
									{
										BigDecimal decimal=new BigDecimal(remainingRoomNos);
										decimal3=decimal;
									}
										    ExamInviligatorDuties duties3=new ExamInviligatorDuties();
											if(decimal3!=null && count1<=decimal3.intValue() && decimal3.intValue()>0)
													{
														if(obj[1]!=null){
															Users us=new Users();
															us.setId(Integer.parseInt(objTeacher[1].toString()));
															duties3.setTeacherId(us);
															RoomMaster rm=new RoomMaster();
															rm.setId(Integer.parseInt(obj[1].toString()));
															duties3.setRoomId(rm);
															ExamDefinition exam=new ExamDefinition();
															exam.setId(Integer.parseInt(InvForm.getExamId()));
															duties3.setExamId(exam);
															duties3.setInvOrReliver("R");
															duties3.setIsActive(true);
															duties3.setLastModifiedDate(new Date());
															duties3.setCreatedBy(InvForm.getUserId());
															duties3.setCreatedDate(new Date());
															duties3.setExamDate(CommonUtil.ConvertStringToDate(InvForm.getAllotmentDate()));
															duties3.setModifiedBy(InvForm.getUserId());
															duties3.setSession(InvForm.getAllotmentSession());
														    EmployeeWorkLocationBO work =new EmployeeWorkLocationBO();
														    work.setId(Integer.parseInt(InvForm.getWorkLocationId()));
														    duties3.setWorkLocationId(work);
														    duties3.setIsApproved(false);
														    if(InvForm.getExamSessionId() != 0){
														    	ExaminationSessions examinationSessions = new ExaminationSessions();
														    	examinationSessions.setId(InvForm.getExamSessionId());
														    	duties3.setExaminationSessions(examinationSessions );
														    }
														    invDutiesList.add(duties3);	
														    stReliverItr.remove();
															count1++;
														}
												}else
												{
														test=false;
														tchItr.remove();
														remainingteacher=teacherList.size();
														remainingRoomNos=RoomListForReliver.size();
														if(leftToAllot!=null && leftToAllot.intValue()>0)
														decimal3=leftToAllot;
														break;
												}
							}
						else if(remainingRoomNos>=checkRoom)
							{	
							 	if(blockName==null || blockName!=obj[3].toString()){
									blockName=obj[3].toString();
								}
									if(OldBlock==null && blockName!=null){
											OldBlock=blockName;
									}
									else if((OldBlock!=null && blockName !=null) && OldBlock!=blockName){
										count++;
									}
									
											 ExamInviligatorDuties duties1=new ExamInviligatorDuties();
											 
												if(count<NoOfRoomsPerReliver) {
													if(obj[1]!=null){
														Users us=new Users();
														us.setId(Integer.parseInt(objTeacher[1].toString()));
														duties1.setTeacherId(us);
														RoomMaster rm=new RoomMaster();
														rm.setId(Integer.parseInt(obj[1].toString()));
														duties1.setRoomId(rm);
														ExamDefinition exam=new ExamDefinition();
														exam.setId(Integer.parseInt(InvForm.getExamId()));
														duties1.setExamId(exam);
														duties1.setInvOrReliver("R");
														duties1.setIsActive(true);
														duties1.setLastModifiedDate(new Date());
														duties1.setCreatedBy(InvForm.getUserId());
														duties1.setCreatedDate(new Date());
														duties1.setExamDate(CommonUtil.ConvertStringToDate(InvForm.getAllotmentDate()));
														duties1.setModifiedBy(InvForm.getUserId());
														duties1.setSession(InvForm.getAllotmentSession());
													    EmployeeWorkLocationBO work =new EmployeeWorkLocationBO();
													    work.setId(Integer.parseInt(InvForm.getWorkLocationId()));
													    duties1.setWorkLocationId(work);
													    duties1.setIsApproved(false);
													    if(InvForm.getExamSessionId() != 0){
													    	ExaminationSessions examinationSessions = new ExaminationSessions();
													    	examinationSessions.setId(InvForm.getExamSessionId());
													    	duties1.setExaminationSessions(examinationSessions );
													    }
													    invDutiesList.add(duties1);	
													    stReliverItr.remove();
														count++;
														test=true;
														}
												}
												else
												{
													test=false;
													tchItr.remove();
													remainingteacher=teacherList.size();
													remainingRoomNos=RoomListForReliver.size();
												    break;
												    
										}
								}
							}
							}
						}
					}else
					{
						remainingRoomNos=RoomListForReliver.size();
						break;
					}
				}	
		  }if(remainingteacher<=0 && remainingRoomNos>0){
			  if(remainingRoomNos>NoOfRoomsPerReliver){
			  	BigDecimal decimal=new BigDecimal(remainingRoomNos);
				BigDecimal decimal2=new BigDecimal(NoOfRoomsPerReliver);
				decimal3=decimal.divide(decimal2, RoundingMode.CEILING);
			  }else
			  {
				  BigDecimal decimal=new BigDecimal(1);
				  decimal3=decimal;
			  }
			  	
				  errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.teacher.allotment.teacher.not.available.count ",decimal3,session,date));
				  throw new DataNotFoundException();
		  }
}

		public void getRelivingDutiesEqualised(Object[] obj, BigDecimal decimal3, int count1,Object[] objTeacher ,ExamInviligatorAllotmentForm InvForm, List<ExamInviligatorDuties> invDutiesList)
		{
		if(obj[2]!=null){
				int studentNos=Integer.parseInt(obj[2].toString());	
				if(studentNos>0){
		

				 ExamInviligatorDuties duties1=new ExamInviligatorDuties();
					if(decimal3!=null && count1<=decimal3.intValue()) {
						if(obj[1]!=null){
							Users us=new Users();
							us.setId(Integer.parseInt(objTeacher[1].toString()));
							duties1.setTeacherId(us);
							RoomMaster rm=new RoomMaster();
							rm.setId(Integer.parseInt(obj[1].toString()));
							duties1.setRoomId(rm);
							ExamDefinition exam=new ExamDefinition();
							exam.setId(Integer.parseInt(InvForm.getExamId()));
							duties1.setExamId(exam);
							duties1.setInvOrReliver("R");
							duties1.setIsActive(true);
							duties1.setLastModifiedDate(new Date());
							duties1.setCreatedBy(InvForm.getUserId());
							duties1.setCreatedDate(new Date());
							duties1.setExamDate(CommonUtil.ConvertStringToDate(InvForm.getAllotmentDate()));
							duties1.setModifiedBy(InvForm.getUserId());
							duties1.setSession(InvForm.getAllotmentSession());
						    EmployeeWorkLocationBO work =new EmployeeWorkLocationBO();
						    work.setId(Integer.parseInt(InvForm.getWorkLocationId()));
						    duties1.setWorkLocationId(work);
						    duties1.setIsApproved(false);
						    invDutiesList.add(duties1);	
							count1++;
							}
					}
					
				}
			}
		}

		/**
 * @param invForm
 * @return
 * @throws Exception
 */
public String roomStudentQuery(ExamInviligatorAllotmentForm invForm) throws Exception{
	 String q="select room_master.room_no, room_master.id, count(distinct EXAM_room_allotment_details.student_id) as nos ,block.block_name, room_master.floor_no "+
	 		  "from EXAM_room_allotment_details "+
	 		  "inner join EXAM_room_allotment on EXAM_room_allotment_details.exam_room_allotment_id= EXAM_room_allotment.id "+
	 		  "inner join classes ON EXAM_room_allotment_details.class_id = classes.id "+
	 		  "inner join course ON classes.course_id = course.id "+
	 		  "inner join room_master ON EXAM_room_allotment.room_id = room_master.id "+
	 		  "inner join block on block.id=room_master.block_id "+
	 		 //"inner join EXAM_rooms_available ON room_master.id=EXAM_rooms_available.room_id "+
	 		  "inner join EXAM_exam_course_scheme_details on EXAM_exam_course_scheme_details.course_id = course.id "+
	 		  "and EXAM_exam_course_scheme_details.scheme_no=classes.term_number "+
	 		  "and EXAM_exam_course_scheme_details.exam_id=" +invForm.getExamId()
	         + " inner JOIN EXAM_time_table on EXAM_time_table.exam_course_scheme_id=EXAM_exam_course_scheme_details.id "+
	 		  "and EXAM_time_table.is_active=1 "+
	 		  " and date(EXAM_time_table.date_starttime) = '"+ CommonUtil.ConvertStringToSQLDate(invForm.getAllotmentDate())+"'"
	 		 +" INNER JOIN EXAM_sessions ON EXAM_time_table.exam_session_id=EXAM_sessions.id AND EXAM_sessions.id="+invForm.getExamSessionId()
	 		  + " INNER JOIN student ON student.id=EXAM_room_allotment_details.student_id "
			+" INNER JOIN subject ON (EXAM_time_table.subject_id = subject.id) "
	         +" where EXAM_room_allotment.exam_id=" +invForm.getExamId()
	         +" and course.work_location_id= " +invForm.getWorkLocationId()
	         +" and block.location_id=" +invForm.getWorkLocationId()
	         +" and EXAM_room_allotment.is_active=1 "
	         +" and EXAM_room_allotment_details.is_active=1 AND EXAM_room_allotment.date is null"
			+" and subject.id in "
			+" (select subject1.id from applicant_subject_group "
			+" inner join adm_appln ON applicant_subject_group.adm_appln_id = adm_appln.id "
			+" inner join student as student1 on student1.adm_appln_id = adm_appln.id"
			+" inner join subject_group ON applicant_subject_group.subject_group_id = subject_group.id"
			+" inner join subject_group_subjects on subject_group_subjects.subject_group_id = subject_group.id and subject_group_subjects.is_active=1"
			+" inner join subject as subject1 ON subject_group_subjects.subject_id = subject1.id"
			+" where student1.id = student.id)"
			+" and classes.id not in (select distinct EXAM_room_allotment_details.class_id " +
			" from EXAM_room_allotment as datewise_allotment "+
			" inner join EXAM_room_allotment_details on " +
			" EXAM_room_allotment_details.exam_room_allotment_id = datewise_allotment.id "+
			" where datewise_allotment.exam_id=EXAM_room_allotment.exam_id "+
			" and datewise_allotment.is_active=1 "+
			" and datewise_allotment.date='"+ CommonUtil.ConvertStringToSQLDate(invForm.getAllotmentDate())+"' and datewise_allotment.exam_session_id="+invForm.getExamSessionId()+")"
			/*+" union"
			+" select old_subject.id from student as student2 "
			+" inner join EXAM_student_sub_grp_history on EXAM_student_sub_grp_history.student_id = student2.id"
			+" inner join subject_group as old_subject_group on EXAM_student_sub_grp_history.subject_group_id = old_subject_group.id"
			+" inner join subject_group_subjects as old_subject_group_subjects on old_subject_group_subjects.subject_group_id = old_subject_group.id"
			+" inner join subject as old_subject on old_subject_group_subjects.subject_id = old_subject.id"
			+" where EXAM_student_sub_grp_history.scheme_no = classes.term_number and"
			+"  student2.id = student.id)"*/
	        +" group by room_master.id"
	        +" order by block.block_order, room_master.floor_no, room_master.room_no";
	 
        return q;
}

}
