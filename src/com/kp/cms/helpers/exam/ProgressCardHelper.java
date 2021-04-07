package com.kp.cms.helpers.exam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.exam.ExamAttendanceMarksBO;
import com.kp.cms.bo.exam.ExamCourseUtilBO;
import com.kp.cms.forms.exam.ExamAttendanceMarksForm;
import com.kp.cms.to.admin.ProgressCardTo;
import com.kp.cms.to.admin.ProgressSubjectTO;
import com.kp.cms.to.exam.ExamAttendanceMarksTO;
import com.kp.cms.to.exam.ExamCourseUtilTO;
import com.kp.cms.to.exam.MarksCardTO;
import com.kp.cms.to.exam.ProgressCardStudentDetailTO;
import com.kp.cms.transactions.exam.IDownloadHallTicketTransaction;
import com.kp.cms.transactionsimpl.admin.ProgressCardImpl;
import com.kp.cms.transactionsimpl.exam.DownloadHallTicketTransactionImpl;
import com.kp.cms.utilities.CommonUtil;

public class ProgressCardHelper {
	private static ProgressCardHelper progressCardHelper=null;

	public static ProgressCardHelper getInstance() {
		if (progressCardHelper==null) {
			progressCardHelper=new ProgressCardHelper();
		}
		return progressCardHelper;
	}

	public List<ProgressCardStudentDetailTO> studentDetailstoTO(List<Student> studentList) {
		List<ProgressCardStudentDetailTO> studentTOList = new ArrayList<ProgressCardStudentDetailTO>();
		Iterator<Student> iterator = studentList.iterator();
		Student student;
		ProgressCardStudentDetailTO feeStudentDetailTO;
		while (iterator.hasNext()) {
			feeStudentDetailTO = new ProgressCardStudentDetailTO();
			student = (Student) iterator.next();
			feeStudentDetailTO.setApplnNo(Integer.toString(student.getAdmAppln().getApplnNo()));
			feeStudentDetailTO.setName(student.getAdmAppln().getPersonalData().getFirstName().trim());
			feeStudentDetailTO.setCourse(student.getAdmAppln().getCourse().getName().trim());
			feeStudentDetailTO.setRegNo(student.getRegisterNo());
			feeStudentDetailTO.setRollNo(student.getRollNo());
			feeStudentDetailTO.setStudentId(Integer.toString(student.getId()));
			feeStudentDetailTO.setStudentName(student.getAdmAppln().getPersonalData().getFirstName());
			//feeStudentDetailTO.setProgramType(student.getAdmAppln().getCourse().getProgram().getProgramType().getName());
			studentTOList.add(feeStudentDetailTO);
		}
		return studentTOList;
	}
	
	public String getQueryForUGStudentMarksCard(int classId,int studentId, int academicYear) throws Exception {
		String q=" select adm_appln.applied_year, "+
				" EXAM_definition.academic_year, "+
				" EXAM_definition.id as exam_id, "+
				" EXAM_definition.name as exam, "+
				" EXAM_definition.internal_exam_type_id, "+
				" EXAM_internal_exam_type.name as internal_exam_type, "+
				" student.id as student_id, "+
				" student.roll_no, "+
				" personal_data.first_name, "+
				" classes.id as class_id, "+
				" classes.name as class, "+
				" classes.term_number, "+
				" course.id as course_id, "+
				" course.name as course, "+
				" subject.id as subject_id, "+
				" subject.code, "+
				" subject.name as subject, "+
				" subject.is_theory_practical, "+
				" EXAM_sub_definition_coursewise.subject_order, "+
				" EXAM_subject_sections.id as subject_section_id, "+
				" EXAM_subject_sections.name as subject_section, "+
				" EXAM_marks_entry_details.theory_marks, "+
				" EXAM_marks_entry_details.practical_marks , "+
				" EXAM_subject_rule_settings_sub_internal.maximum_mark as internal_max, "+
				" EXAM_subject_rule_settings_sub_internal.entered_max_mark as intternal_entered_max, "+
				" EXAM_subject_rule_settings.theory_ese_minimum_mark, "+
				" EXAM_subject_rule_settings.theory_ese_maximum_mark, "+
				" EXAM_subject_rule_settings.practical_ese_minimum_mark, "+
				" EXAM_subject_rule_settings.practical_ese_maximum_mark, "+
				" EXAM_subject_rule_settings.subject_final_minimum, "+
				" EXAM_subject_rule_settings.subject_final_maximum, "+
				" (  select sum(if(att_stu.is_present=1 || att_stu.is_cocurricular_leave=1,att.hours_held,0)) as ispresent "+
				" FROM   student stu "+
				" inner JOIN attendance_student att_stu ON (att_stu.student_id = stu.id) "+
				" inner JOIN attendance att ON (att_stu.attendance_id =  att.id) "+
				" inner JOIN attendance_class att_class ON (att_class.attendance_id = att.id) "+
				" inner join class_schemewise class_schem on (att_class.class_schemewise_id = class_schem.id) "+
				" and stu.class_schemewise_id = class_schem.id "+
				" INNER JOIN classes class ON (class_schem.class_id = class.id) "+
				" inner join subject subj ON att.subject_id = subj.id and subj.is_active=1 "+
				" where stu.is_admitted=1 "+
				" and att.is_canceled=0   "+
				" and class.id=classes.id "+
				" and stu.id=student.id "+
				" and subj.id=subject.id "+
				" group by stu.id,subj.id,class.id "+
				" union all "+
				" select sum(if(att_stu.is_present=1 || att_stu.is_cocurricular_leave=1,att.hours_held,0)) as ispresent "+
				" FROM EXAM_student_previous_class_details "+
				" inner join student stu ON EXAM_student_previous_class_details.student_id = stu.id "+
				" inner JOIN attendance_student att_stu ON (att_stu.student_id = stu.id) "+
				" inner JOIN attendance att ON (att_stu.attendance_id =  att.id) "+
				" inner JOIN attendance_class att_class ON (att_class.attendance_id = att.id) "+
				" inner join class_schemewise class_schem on (att_class.class_schemewise_id = class_schem.id) "+
				" INNER JOIN classes class ON (class_schem.class_id = class.id) "+
				" and EXAM_student_previous_class_details.class_id=class.id "+
				" inner join subject subj ON att.subject_id = subj.id and subj.is_active=1 "+
				" where stu.is_admitted=1 "+
				" and att.is_canceled=0   "+
				" and class.id=classes.id "+
				" and stu.id=student.id "+
				" and subj.id=subject.id "+
				" group by stu.id,subj.id,class.id) as present_hrs, "+
				" ( select sum(att.hours_held) as tot "+
				" FROM   student stu "+
				" inner JOIN attendance_student att_stu ON (att_stu.student_id = stu.id) "+
				" inner JOIN attendance att ON (att_stu.attendance_id =  att.id) "+
				" inner JOIN attendance_class att_class ON (att_class.attendance_id = att.id) "+
				" inner join class_schemewise class_schem on (att_class.class_schemewise_id = class_schem.id) "+
				" and stu.class_schemewise_id = class_schem.id "+
				" INNER JOIN classes class ON (class_schem.class_id = class.id) "+
				" inner join subject subj ON att.subject_id = subj.id and subj.is_active=1 "+
				" where stu.is_admitted=1 "+
				" and att.is_canceled=0   "+
				" and class.id=classes.id "+
				" and stu.id=student.id "+
				" and subj.id=subject.id "+
				" group by stu.id,subj.id,class.id "+
				" union all "+
				" select "+
				" sum(att.hours_held) as tot "+
				" FROM EXAM_student_previous_class_details "+
				" inner join student stu ON EXAM_student_previous_class_details.student_id = stu.id "+
				" inner JOIN attendance_student att_stu ON (att_stu.student_id = stu.id) "+
				" inner JOIN attendance att ON (att_stu.attendance_id =  att.id) "+
				" inner JOIN attendance_class att_class ON (att_class.attendance_id = att.id) "+
				" inner join class_schemewise class_schem on (att_class.class_schemewise_id = class_schem.id) "+
				" INNER JOIN classes class ON (class_schem.class_id = class.id) "+
				" and EXAM_student_previous_class_details.class_id=class.id "+
				" inner join subject subj ON att.subject_id = subj.id and subj.is_active=1 "+
				" where stu.is_admitted=1 "+
				" and att.is_canceled=0   "+
				" and class.id=classes.id "+
				" and stu.id=student.id "+
				" and subj.id=subject.id "+
				" group by stu.id,subj.id,class.id) as total_hrs "+
				" from EXAM_marks_entry "+
				" inner join EXAM_definition ON EXAM_definition.id = EXAM_marks_entry.exam_id "+
				" inner join EXAM_marks_entry_details ON EXAM_marks_entry.id = EXAM_marks_entry_details.marks_entry_id "+
				" inner join classes ON classes.id = EXAM_marks_entry.class_id "+
				" left join EXAM_internal_exam_type ON EXAM_internal_exam_type.id = EXAM_definition.internal_exam_type_id "+
				" and EXAM_internal_exam_type.id in (3,6) "+
				" inner join course ON course.id = classes.course_id "+
				" inner join student ON student.id = EXAM_marks_entry.student_id "+
				" inner join subject ON subject.id = EXAM_marks_entry_details.subject_id "+
				" inner join adm_appln ON student.adm_appln_id = adm_appln.id "+
				" inner join personal_data ON adm_appln.personal_data_id = personal_data.id "+
				" left join EXAM_sub_definition_coursewise on EXAM_definition.academic_year = EXAM_sub_definition_coursewise.academic_year "+
				" and EXAM_sub_definition_coursewise.course_id = course.id "+
				" and EXAM_sub_definition_coursewise.subject_id = subject.id "+
				" and EXAM_sub_definition_coursewise.scheme_no = classes.term_number "+
				" inner join EXAM_subject_rule_settings on EXAM_definition.academic_year = EXAM_subject_rule_settings.academic_year "+
				" and EXAM_subject_rule_settings.course_id = course.id "+
				" and EXAM_subject_rule_settings.subject_id = subject.id "+
				" and EXAM_subject_rule_settings.scheme_no = classes.term_number "+
				" left join EXAM_subject_sections ON EXAM_sub_definition_coursewise.subject_section_id = EXAM_subject_sections.id "+
				" left join EXAM_subject_rule_settings_sub_internal on EXAM_subject_rule_settings_sub_internal.subject_rule_settings_id = EXAM_subject_rule_settings.id "+
				" and EXAM_subject_rule_settings_sub_internal.internal_exam_type_id = EXAM_internal_exam_type.id "+
				" inner join class_schemewise on class_schemewise.class_id = classes.id "+
				" inner join curriculum_scheme_duration ON class_schemewise.curriculum_scheme_duration_id = curriculum_scheme_duration.id "+
				" inner join program on course.program_id = program.id "+
				//" where  curriculum_scheme_duration.academic_year="+academicYear +
				//" and student.id="+studentId +
				" where student.id="+studentId +
				" group by classes.id,EXAM_definition.id,subject.id,student.id"; 

		return q;
	}
	
	public List<ProgressCardTo> getMarksCardForUg(List<Object[]> ugMarksCardData,int sid) throws Exception {
		if(ugMarksCardData!=null && !ugMarksCardData.isEmpty()){
			Iterator<Object[]> itr=ugMarksCardData.iterator();
			List<Integer> subjectIdList = new ArrayList<Integer>();
			List<Integer> termId = new ArrayList<Integer>();
			List<ProgressSubjectTO> pogressList=new ArrayList();
			Map<Integer, ProgressSubjectTO> subMap=new HashMap();
			IDownloadHallTicketTransaction transaction= new DownloadHallTicketTransactionImpl();
			int academicYear=0;
			int appliedYear=0;
			int star=0;
			int firstInternalTotal=0;
			int SecondInternalTotal=0;
			int firstInternalMaxTotal=0;
			int SecondInternalMaxTotal=0;
			int eseAwardedTotal=0;
			int eseMaxTotal=0;
			int attTotalprssesnt=0;
			int attTotalHeld=0;
			String studentName=null;
			String programme=null;
			String classNo=null;
			String regNo=null;
			String ugOrPg=null;
			//Student student=ProgressCardImpl.getInstance().getStudentsDetails(sid);
			while (itr.hasNext()) {
				Object[] obj = (Object[]) itr.next();
				String internalOneMax="";
				String internalTwoMax="";
				Double internalOneAwarded = new Double("0.00");
				Double internalTwoAwarded = new Double("0.00");
				int eseMarks = 0;
				String eseMarksMax = "";
				boolean isTheoryStar=false;
				boolean isPracticalStar=false;
				
				if(obj[1]!=null)
					academicYear = Integer.parseInt(obj[1].toString());
				if(obj[0]!=null)
					appliedYear = Integer.parseInt(obj[0].toString());
				if(obj[14]!=null)
					subjectIdList.add(Integer.parseInt(obj[14].toString()));
				String gradeForSubject="";
				if (!termId.isEmpty()) {
					if (!termId.contains(Integer.parseInt(obj[11].toString()))) {
						termId.add(Integer.parseInt(obj[11].toString()));
					}
				}else{
					termId.add(Integer.parseInt(obj[11].toString()));
				}
				String starQuery="SELECT EXAM_marks_entry_details.is_retest,  " +
				" EXAM_marks_entry.exam_id, " +
				" EXAM_marks_entry.student_id," +
				" EXAM_marks_entry_details.subject_id," +
				" if(EXAM_marks_entry_details.theory_marks is not null,EXAM_marks_entry_details.is_retest,0) as thRetest," +
				" if(EXAM_marks_entry_details.practical_marks is not null,EXAM_marks_entry_details.is_retest,0) as praRetest" +
				" FROM    (   EXAM_marks_entry EXAM_marks_entry" +
				" INNER JOIN  EXAM_exam_internal_exam_details EXAM_exam_internal_exam_details " +
				" ON (EXAM_marks_entry.exam_id = EXAM_exam_internal_exam_details.internal_exam_name_id))  INNER JOIN" +
				" EXAM_marks_entry_details EXAM_marks_entry_details ON (EXAM_marks_entry_details.marks_entry_id = EXAM_marks_entry.id)" +
				" where EXAM_exam_internal_exam_details.exam_id = " +obj[2].toString()+
				" and EXAM_marks_entry.student_id = " +obj[6].toString()+
				" and EXAM_marks_entry_details.subject_id = " +obj[14].toString()+
				" and is_retest = 1";
				List<Object[]> slist=transaction.getStudentHallTicket(starQuery);
				if(slist!=null && !slist.isEmpty()){
					Iterator<Object[]> sItr=slist.iterator();
					while (sItr.hasNext()) {
						Object[] s = (Object[]) sItr.next();
						if(s[4]!=null){
							if(!s[4].toString().isEmpty() && Integer.parseInt(s[4].toString())==1){
								star=star+1;
								isTheoryStar=true;
							}
						}
						if(s[5]!=null){
							if(!s[5].toString().isEmpty() && Integer.parseInt(s[5].toString())==1){
								star=star+1;
								isPracticalStar=true;
							}
						}
					}
				}

				
				
				if(obj[8]!=null){
					studentName=(obj[8].toString());
				}
				if(obj[7]!=null){
					regNo=(obj[7].toString());
				}
				if(obj[13]!=null){
					programme=(obj[13].toString());
				}
				ProgressSubjectTO to=null;
				if (!subMap.isEmpty()) {
					if(subMap.containsKey(Integer.parseInt(obj[14].toString()))){
						to=subMap.get(Integer.parseInt(obj[14].toString()));
					}else{
						to=new ProgressSubjectTO();
					}
				}else{
					to=new ProgressSubjectTO();	
				}
				if(!obj[17].toString().equalsIgnoreCase("P")){
					if((obj[23]!=null && !StringUtils.isEmpty(obj[23].toString())) || (obj[26]!=null && !StringUtils.isEmpty(obj[26].toString()))){
						if(obj[4]!=null && Integer.parseInt(obj[4].toString())==3){
							internalOneMax=String.valueOf((int)(Double.parseDouble(obj[23].toString())));
							to.setFirstInternalMarksMax(internalOneMax);
						}else if(obj[4]!=null && Integer.parseInt(obj[4].toString())==6){
							internalTwoMax=obj[23].toString();
							to.setSecondInternalMarksMax(internalTwoMax);
						}else{
								eseMarksMax=obj[26].toString();
								to.setEseMaxMarks(eseMarksMax);
						}
					}
				}else{
					if(obj[23]!=null && !StringUtils.isEmpty(obj[23].toString())){
						if(obj[4]!=null && Integer.parseInt(obj[4].toString())==3){
							internalOneMax=String.valueOf((int)(Double.parseDouble(obj[23].toString())));
							to.setFirstInternalMarksMax(internalOneMax);
						}else if(obj[4]!=null && Integer.parseInt(obj[4].toString())==6){
							internalTwoMax=obj[23].toString();
							to.setSecondInternalMarksMax(internalTwoMax);
						}
						else{
							eseMarksMax=obj[26].toString();
							to.setEseMaxMarks(eseMarksMax);
					}
					}
				}
				if(obj[16]!=null){
					to.setName(obj[16].toString()+"("+obj[20].toString()+")");
				}
				if(obj[15]!=null){
					to.setCode(obj[15].toString());
				}
				if(obj[18]!=null){
					to.setOrder(Integer.parseInt(obj[18].toString()));
				}
				if(!obj[17].toString().equalsIgnoreCase("P")){
					if(obj[21]!=null && CommonUtil.isValidDecimal(obj[21].toString())){
						if(obj[4]!=null && Integer.parseInt(obj[4].toString())==3){
							internalOneAwarded=Double.parseDouble(obj[21].toString());
							to.setFirstInternalMarksAwarded(obj[21].toString());
						}else if(obj[4]!=null && Integer.parseInt(obj[4].toString())==6){
							internalTwoAwarded=Double.parseDouble(obj[21].toString());
							to.setSecondInternalMarksAwarded(obj[21].toString());
						}else {
							eseMarks=Integer.parseInt(obj[21].toString());
							to.setEseMarksAwarded(obj[21].toString());
						}
					}
				}else{
					if(obj[22]!=null && CommonUtil.isValidDecimal(obj[22].toString())){
						if(obj[4]!=null && Integer.parseInt(obj[4].toString())==3){
							internalOneAwarded=Double.parseDouble(obj[22].toString());
							to.setFirstInternalMarksAwarded(obj[22].toString());
						}else if(obj[4]!=null && Integer.parseInt(obj[4].toString())==6){
							internalTwoAwarded=Double.parseDouble(obj[22].toString());
							to.setSecondInternalMarksAwarded(obj[22].toString());
						}
						else {
							eseMarks=Integer.parseInt(obj[22].toString());
							to.setEseMarksAwarded(obj[22].toString());
						}
					}
				}
				
				if (obj[31]!=null) {
					to.setAttPressent(obj[31].toString());
				}
				if (obj[32]!=null) {
					to.setAttMax(obj[32].toString());
				}
				if (obj[32]!=null && obj[32]!=null) {
					to.setAttPercentage(new Double((Integer.parseInt(obj[31].toString())*100)/Integer.parseInt(obj[32].toString())));
				}
				if (obj[11]!=null) {
					to.setTerm(Integer.parseInt(obj[11].toString()));
				}
				subMap.put(Integer.parseInt(obj[14].toString()), to);
			}
			//Set<ProgressSubjectTO>progSubSet=(Set<ProgressSubjectTO>) subMap.values();
			List<ProgressCardTo> progressCardList=new ArrayList();
			for (int term : termId) {
				ProgressCardTo progresCardTo=new ProgressCardTo();
				List<ProgressSubjectTO> sublist=new ArrayList();
				for (ProgressSubjectTO to : subMap.values()) {
					if (to.getTerm()==term) {
						sublist.add(to);
							if (to.getFirstInternalMarksAwarded()!=null && to.getFirstInternalMarksAwarded()!="") {
								firstInternalTotal=firstInternalTotal+Integer.parseInt(to.getFirstInternalMarksAwarded());
							}
							if (to.getSecondInternalMarksAwarded()!=null && to.getSecondInternalMarksAwarded()!="") {
								SecondInternalTotal=SecondInternalTotal+Integer.parseInt(to.getSecondInternalMarksAwarded());
							}
							if (to.getFirstInternalMarksMax()!=null && to.getFirstInternalMarksMax()!="") {
								firstInternalMaxTotal=firstInternalMaxTotal+Integer.parseInt(to.getFirstInternalMarksMax());
							}
							if (to.getSecondInternalMarksMax()!=null && to.getSecondInternalMarksMax()!="") {
								SecondInternalMaxTotal=(int) (SecondInternalMaxTotal+new Double(to.getSecondInternalMarksMax()));
							}
							if (to.getEseMarksAwarded()!=null && to.getEseMarksAwarded()!="") {
								eseAwardedTotal=eseAwardedTotal+Integer.parseInt(to.getEseMarksAwarded());
							}
							if (to.getEseMaxMarks()!=null && to.getEseMaxMarks()!="") {
								eseMaxTotal=(int) (eseMaxTotal+new Double(to.getEseMaxMarks()));
							}
							if (to.getAttMax()!=null && to.getAttMax()!="") {
								attTotalHeld=attTotalHeld+Integer.parseInt(to.getAttMax());
							}
							if (to.getAttPressent()!=null && to.getAttPressent()!="") {
								attTotalprssesnt=attTotalprssesnt+Integer.parseInt(to.getAttPressent());
							}
						
					}
					if (term==1) {
						progresCardTo.setTermName("FIRST SEMESTER");
					}
					else if (term==2) {
						progresCardTo.setTermName("SECOND SEMESTER");
					}
					else if (term==3) {
						progresCardTo.setTermName("THIRD SEMESTER");
					}
					else if (term==4) {
						progresCardTo.setTermName("FOURTH SEMESTER");
					}
					else if (term==5) {
						progresCardTo.setTermName("FIFTH SEMESTER");
					}
					else if (term==6) {
						progresCardTo.setTermName("SIXTH SEMESTER");
					}
					progresCardTo.setTerm(term);
					
					int acc=(Integer.parseInt(String.valueOf(academicYear))%100)+1;
					progresCardTo.setAcademicYear(String.valueOf(academicYear)+"-"+acc);
					progresCardTo.setAppliedYear(String.valueOf(appliedYear));
				}
				progresCardTo.setFirstInternalTotal(String.valueOf(firstInternalTotal));
				progresCardTo.setSecondInternalTotal(String.valueOf(SecondInternalTotal));
				progresCardTo.setFirstInternalMaxTotal(String.valueOf(firstInternalMaxTotal));
				progresCardTo.setSecondInternalMaxTotal(String.valueOf(SecondInternalMaxTotal));
				progresCardTo.setEseAwardedTotal(String.valueOf(eseAwardedTotal));
				progresCardTo.setEseMaxTotal(String.valueOf(eseMaxTotal));
				progresCardTo.setAttTotalHeld(String.valueOf(attTotalHeld));
				progresCardTo.setAttTotalprssesnt(String.valueOf(attTotalprssesnt));
				Collections.sort(sublist);
				progresCardTo.setSubjectList(sublist);
				progressCardList.add(progresCardTo);
				progresCardTo.setStudentName(studentName);
				progresCardTo.setClassNo(regNo);
				progresCardTo.setProgrameme(programme);
				progresCardTo.setTotalAttPercentage(new Double((attTotalprssesnt*100)/attTotalHeld));
				 
			}
			return progressCardList;
			}
		
		
		return null;
		
	}
}
