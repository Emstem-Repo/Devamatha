package com.kp.cms.helpers.admission;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Course;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.Subject;
import com.kp.cms.bo.exam.ConsolidateMarksCard;
import com.kp.cms.bo.exam.InternalMarkSupplementaryDetails;
import com.kp.cms.forms.admission.StudentTranscriptPrintForm;
import com.kp.cms.to.admission.StudentTranscriptPrintTO;
import com.kp.cms.to.exam.ConsolidateMaxTo;
import com.kp.cms.transactions.exam.IDownloadHallTicketTransaction;
import com.kp.cms.transactions.exam.INewExamMarksEntryTransaction;
import com.kp.cms.transactionsimpl.exam.DownloadHallTicketTransactionImpl;
import com.kp.cms.transactionsimpl.exam.NewExamMarksEntryTransactionImpl;
import com.kp.cms.transactionsimpl.exam.UpdateExamStudentSGPAImpl;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.SubjectGroupDetailsComparator;

/**
 * @author dIlIp
 *
 */
public class StudentTranscriptPrintHelper {
	
	private static final Log log = LogFactory.getLog(StudentTranscriptPrintHelper.class);
	private static volatile StudentTranscriptPrintHelper studentTranscriptPrintHelper = null;
	
	private StudentTranscriptPrintHelper() {
		
	}
	/**
	 * return singleton object of StudentTranscriptPrintHelper.
	 * @return
	 */
	public static StudentTranscriptPrintHelper getInstance() {
		if (studentTranscriptPrintHelper == null) {
			studentTranscriptPrintHelper = new StudentTranscriptPrintHelper();
		}
		return studentTranscriptPrintHelper;
	}
	
	private static Map<String, String> monthMap = null;
	private static SubjectGroupDetailsComparator comparator=new SubjectGroupDetailsComparator();
	static {
		comparator.setCompare(1);
		monthMap = new HashMap<String, String>();
		monthMap.put("00", "JANUARY");
		monthMap.put("01", "FEBRUARY");
		monthMap.put("02", "MARCH");
		monthMap.put("03", "APRIL");
		monthMap.put("04", "MAY");
		monthMap.put("05", "JUNE");
		monthMap.put("06", "JULY");
		monthMap.put("07", "AUGUST");
		monthMap.put("08", "SEPTEMBER");
		monthMap.put("09", "OCTOBER");
		monthMap.put("10", "NOVEMBER");
		monthMap.put("11", "DECEMBER");
		
	}

	
	
	
	/**
	 * @return
	 * @throws Exception
	 */
	public String getConsolidateQuery() throws Exception {
		log.info("inside getConsolidateQuery method ");
		String query=" select EXAM_definition.id, SUBSTR(EXAM_definition.month, 1, 2) as exam_month, EXAM_definition.year as exam_year, "+
		" student.id as student_id, classes.id as classId, subject.id as subject_id, "+
		" subject.code as subCode, subject.name as subName, "+
		" ifnull(EXAM_internal_mark_supplementary_details.theory_total_sub_internal_mark, EXAM_student_overall_internal_mark_details.theory_total_sub_internal_mark) "+
		" as theory_total_sub_internal_mark, "+
		" ifnull(EXAM_internal_mark_supplementary_details.theory_total_attendance_mark, EXAM_student_overall_internal_mark_details.theory_total_attendance_mark) "+
		" as theory_total_attendance_mark, "+
		" ifnull(EXAM_internal_mark_supplementary_details.practical_total_sub_internal_mark, EXAM_student_overall_internal_mark_details.practical_total_sub_internal_mark) "+
		" as practical_total_sub_internal_mark, "+
		" ifnull(EXAM_internal_mark_supplementary_details.practical_total_attendance_mark, EXAM_student_overall_internal_mark_details.practical_total_attendance_mark) "+
		" as practical_total_attendance_mark, "+
		" classes.course_id as course_id, "+
		" EXAM_subject_rule_settings.theory_ese_minimum_mark,     "+
		" EXAM_subject_rule_settings.theory_ese_maximum_mark,     "+
		" EXAM_subject_rule_settings.practical_ese_minimum_mark,     "+
		" EXAM_subject_rule_settings.practical_ese_maximum_mark, "+
		" EXAM_student_final_mark_details.student_theory_marks, "+
		" EXAM_student_final_mark_details.student_practical_marks, "+
		" personal_data.first_name,     "+
		" personal_data.middle_name,     "+
		" personal_data.last_name,  "+
		" EXAM_subject_sections.name as section,  "+
		" EXAM_subject_sections.is_initialise,     "+
		" EXAM_subject_sections.id as section_id,     "+
		" EXAM_sub_definition_coursewise.subject_order,     "+
		" EXAM_subject_rule_settings.final_practical_internal_maximum_mark,     "+
		" EXAM_subject_rule_settings.final_theory_internal_maximum_mark,  "+
		" EXAM_sub_definition_coursewise.practical_credit, "+
		" EXAM_sub_definition_coursewise.theory_credit, "+
		" student.register_no, "+
		" if(subject.is_theory_practical='T','Theory',if(subject.is_theory_practical='P','Practical','Theory')) as subType, "+
		" classes.term_number as term_number, "+
		" (if(EXAM_subject_rule_settings.final_theory_internal_maximum_mark > 0,EXAM_subject_rule_settings.final_theory_internal_maximum_mark,0)+if(EXAM_subject_rule_settings.theory_ese_maximum_mark >0,EXAM_subject_rule_settings.theory_ese_maximum_mark,0)) as theoryMax,     "+
		" (if(EXAM_subject_rule_settings.final_practical_internal_maximum_mark >0,EXAM_subject_rule_settings.final_practical_internal_maximum_mark,0)+if(EXAM_subject_rule_settings.practical_ese_maximum_mark >0,EXAM_subject_rule_settings.practical_ese_maximum_mark,0)) as practicalMax, "+
		" EXAM_sub_definition_coursewise.dont_show_max_marks,     "+
		" EXAM_sub_definition_coursewise.dont_show_min_marks,  "+
		" EXAM_sub_definition_coursewise.show_only_grade,  "+
		" course_scheme.name as schemeName,     "+
		" EXAM_sub_definition_coursewise.dont_show_sub_type,      "+
		" adm_appln.applied_year,  "+
		" EXAM_definition.academic_year, "+
		" adm_appln.selected_course_id,EXAM_subject_rule_settings.final_theory_internal_minimum_mark, " +
		" EXAM_subject_rule_settings.final_practical_internal_minimum_mark ,EXAM_subject_rule_settings.theory_ese_theory_final_minimum_mark,EXAM_subject_rule_settings.practical_ese_theory_final_minimum_mark"+
		" ,EXAM_sub_definition_coursewise.dont_consider_failure_total_result,EXAM_supplementary_improvement_application.is_appeared_theory, EXAM_supplementary_improvement_application.is_appeared_practical,EXAM_sub_definition_coursewise.dont_add_tot_mark_cls_decln,subject.is_certificate_course," +
		" if(theory_ese_maximum_mark is null and theory_ese_entered_max_mark is null and practical_ese_maximum_mark is null and practical_ese_entered_max_mark is null, 1, 0) as only_internal,EXAM_supplementary_improvement_application.is_improvement, " +
		" if((SELECT MAX(EXAM_sub_coursewise_attendance_marks.attendance_marks) FROM EXAM_sub_coursewise_attendance_marks WHERE course_id = classes.course_id AND subject_id = subject.id) IS NOT NULL, (SELECT MAX(EXAM_sub_coursewise_attendance_marks.attendance_marks) " +
		" FROM EXAM_sub_coursewise_attendance_marks WHERE course_id = classes.course_id AND subject_id = subject.id), (SELECT MAX(EXAM_attendance_marks.marks) FROM EXAM_attendance_marks EXAM_attendance_marks WHERE course_id = classes.course_id)) AS maxAttMarks " +
		" from student "+
		" inner join adm_appln ON student.adm_appln_id = adm_appln.id "+
		" inner join personal_data ON adm_appln.personal_data_id = personal_data.id "+
		" inner join EXAM_student_overall_internal_mark_details on EXAM_student_overall_internal_mark_details.student_id = student.id "+
		" inner join classes ON EXAM_student_overall_internal_mark_details.class_id = classes.id "+
		" inner join class_schemewise on class_schemewise.class_id = classes.id "+
		" inner join curriculum_scheme_duration ON class_schemewise.curriculum_scheme_duration_id = curriculum_scheme_duration.id "+
		" inner join curriculum_scheme ON curriculum_scheme_duration.curriculum_scheme_id = curriculum_scheme.id "+
		" inner join course_scheme ON curriculum_scheme.course_scheme_id = course_scheme.id "+
		" inner join course ON classes.course_id = course.id "+
		" inner join subject ON EXAM_student_overall_internal_mark_details.subject_id = subject.id "+
		" left join EXAM_student_final_mark_details on EXAM_student_final_mark_details.class_id = classes.id "+
		" and EXAM_student_final_mark_details.student_id = student.id "+
		" and EXAM_student_final_mark_details.subject_id = subject.id and (EXAM_student_final_mark_details.student_theory_marks is not null or "+
		" EXAM_student_final_mark_details.student_practical_marks is not null ) "+
		" inner join EXAM_subject_rule_settings on EXAM_subject_rule_settings.course_id = course.id "+
		" and EXAM_subject_rule_settings.subject_id = subject.id "+
		" and EXAM_subject_rule_settings.scheme_no=classes.term_number "+
		" and EXAM_subject_rule_settings.academic_year = curriculum_scheme_duration.academic_year "+
		" left join EXAM_sub_definition_coursewise on EXAM_sub_definition_coursewise.course_id = course.id "+
		" and EXAM_sub_definition_coursewise.subject_id = subject.id "+
		" and EXAM_sub_definition_coursewise.scheme_no = classes.term_number "+
		" and EXAM_sub_definition_coursewise.academic_year=curriculum_scheme_duration.academic_year "+
		" left join EXAM_subject_sections on EXAM_sub_definition_coursewise.subject_section_id = EXAM_subject_sections.id "+
		" inner join EXAM_definition ON ifnull(EXAM_student_final_mark_details.exam_id,EXAM_student_overall_internal_mark_details.exam_id) = EXAM_definition.id "+
		" left join EXAM_supplementary_improvement_application on EXAM_supplementary_improvement_application.scheme_no = classes.term_number "+
		" and EXAM_supplementary_improvement_application.student_id = student.id "+
		" and EXAM_supplementary_improvement_application.subject_id = subject.id "+
//		" and EXAM_supplementary_improvement_application.exam_id = EXAM_definition.id "+
		" left join EXAM_internal_mark_supplementary_details on EXAM_internal_mark_supplementary_details.class_id = classes.id "+
//		" and EXAM_internal_mark_supplementary_details.exam_id = EXAM_definition.id "+
		" and EXAM_internal_mark_supplementary_details.student_id = student.id "+
		" and EXAM_internal_mark_supplementary_details.subject_id = subject.id "+
		" AND if((classes.course_id=18),"+ /*if BBA take the internal mark for the current regerring supli as they have internal always for each supli, other courses take the latest exam marks*/
	       " (EXAM_internal_mark_supplementary_details.exam_id=EXAM_definition.id)," +
	       " (EXAM_internal_mark_supplementary_details.exam_id=(select e.exam_id from  EXAM_internal_mark_supplementary_details e" +
	       " inner join EXAM_definition as ex on e.exam_id=ex.id" +
	       " where e.class_id=classes.id and e.student_id=student.id and e.subject_id=subject.id" +
	       " and ex.exam_type_id in (3, 6) order by ex.year desc, ex.month desc limit 1)))"+
		" where student.id = :id"+
		"  union all "+
		"  select EXAM_definition.id, EXAM_definition.month as exam_month, EXAM_definition.year as exam_year, "+
		"  student.id as student_id, classes.id as classId, subject.id as subject_id, "+
		" subject.code as subCode, subject.name as subName, "+
		" ifnull(EXAM_internal_mark_supplementary_details.theory_total_sub_internal_mark, EXAM_student_overall_internal_mark_details.theory_total_sub_internal_mark) "+
		" as theory_total_sub_internal_mark, "+
		" ifnull(EXAM_internal_mark_supplementary_details.theory_total_attendance_mark, EXAM_student_overall_internal_mark_details.theory_total_attendance_mark) "+
		" as theory_total_attendance_mark, "+
		" ifnull(EXAM_internal_mark_supplementary_details.practical_total_sub_internal_mark, EXAM_student_overall_internal_mark_details.practical_total_sub_internal_mark) "+
		" as practical_total_sub_internal_mark, "+
		" ifnull(EXAM_internal_mark_supplementary_details.practical_total_attendance_mark, EXAM_student_overall_internal_mark_details.practical_total_attendance_mark) "+
		" as practical_total_attendance_mark, "+
		" classes.course_id as course_id, "+
		" EXAM_subject_rule_settings.theory_ese_minimum_mark,     "+
		" EXAM_subject_rule_settings.theory_ese_maximum_mark,     "+
		" EXAM_subject_rule_settings.practical_ese_minimum_mark,     "+
		" EXAM_subject_rule_settings.practical_ese_maximum_mark, "+
		" EXAM_student_final_mark_details.student_theory_marks, "+
		" EXAM_student_final_mark_details.student_practical_marks, "+
		" personal_data.first_name,     "+
		" personal_data.middle_name,     "+
		" personal_data.last_name,  "+
		" EXAM_subject_sections.name as section,  "+
		" EXAM_subject_sections.is_initialise,     "+
		" EXAM_subject_sections.id as section_id,     "+
		" EXAM_sub_definition_coursewise.subject_order,     "+
		" EXAM_subject_rule_settings.final_practical_internal_maximum_mark,     "+
		" EXAM_subject_rule_settings.final_theory_internal_maximum_mark,  "+
		" EXAM_sub_definition_coursewise.practical_credit, "+
		" EXAM_sub_definition_coursewise.theory_credit, "+
		" student.register_no, "+
		" if(subject.is_theory_practical='T','Theory',if(subject.is_theory_practical='P','Practical','Practical')) as subType, "+
		" classes.term_number as term_number, "+
		" (if(EXAM_subject_rule_settings.final_theory_internal_maximum_mark > 0,EXAM_subject_rule_settings.final_theory_internal_maximum_mark,0)+if(EXAM_subject_rule_settings.theory_ese_maximum_mark >0,EXAM_subject_rule_settings.theory_ese_maximum_mark,0)) as theoryMax,     "+
		" (if(EXAM_subject_rule_settings.final_practical_internal_maximum_mark >0,EXAM_subject_rule_settings.final_practical_internal_maximum_mark,0)+if(EXAM_subject_rule_settings.practical_ese_maximum_mark >0,EXAM_subject_rule_settings.practical_ese_maximum_mark,0)) as practicalMax, "+
		" EXAM_sub_definition_coursewise.dont_show_max_marks,     "+
		" EXAM_sub_definition_coursewise.dont_show_min_marks,  "+
		" EXAM_sub_definition_coursewise.show_only_grade,  "+
		" course_scheme.name as schemeName,     "+
		" EXAM_sub_definition_coursewise.dont_show_sub_type,      "+
		" adm_appln.applied_year,  "+
		" EXAM_definition.academic_year, "+
		" adm_appln.selected_course_id,EXAM_subject_rule_settings.final_theory_internal_minimum_mark, " +
		" EXAM_subject_rule_settings.final_practical_internal_minimum_mark,EXAM_subject_rule_settings.theory_ese_theory_final_minimum_mark,EXAM_subject_rule_settings.practical_ese_theory_final_minimum_mark"+
		" ,EXAM_sub_definition_coursewise.dont_consider_failure_total_result,EXAM_supplementary_improvement_application.is_appeared_theory, EXAM_supplementary_improvement_application.is_appeared_practical,EXAM_sub_definition_coursewise.dont_add_tot_mark_cls_decln,subject.is_certificate_course,if(theory_ese_maximum_mark is null and theory_ese_entered_max_mark is null and practical_ese_maximum_mark is null and practical_ese_entered_max_mark is null, 1, 0) as only_internal,EXAM_supplementary_improvement_application.is_improvement, " +
		" if((SELECT MAX(EXAM_sub_coursewise_attendance_marks.attendance_marks) FROM EXAM_sub_coursewise_attendance_marks WHERE course_id = classes.course_id AND subject_id = subject.id) IS NOT NULL, (SELECT MAX(EXAM_sub_coursewise_attendance_marks.attendance_marks) " +
		" FROM EXAM_sub_coursewise_attendance_marks WHERE course_id = classes.course_id AND subject_id = subject.id), (SELECT MAX(EXAM_attendance_marks.marks) FROM EXAM_attendance_marks EXAM_attendance_marks WHERE course_id = classes.course_id)) AS maxAttMarks " +
		" from student "+
		" inner join adm_appln ON student.adm_appln_id = adm_appln.id "+
		" inner join personal_data ON adm_appln.personal_data_id = personal_data.id "+
		" inner join EXAM_student_overall_internal_mark_details on EXAM_student_overall_internal_mark_details.student_id = student.id "+
		" inner join classes ON EXAM_student_overall_internal_mark_details.class_id = classes.id "+
		" inner join class_schemewise on class_schemewise.class_id = classes.id "+
		" inner join curriculum_scheme_duration ON class_schemewise.curriculum_scheme_duration_id = curriculum_scheme_duration.id "+
		" inner join curriculum_scheme ON curriculum_scheme_duration.curriculum_scheme_id = curriculum_scheme.id "+
		" inner join course_scheme ON curriculum_scheme.course_scheme_id = course_scheme.id "+
		" inner join course ON classes.course_id = course.id "+
		" inner join subject ON EXAM_student_overall_internal_mark_details.subject_id = subject.id "+
		" left join EXAM_student_final_mark_details on EXAM_student_final_mark_details.class_id = classes.id "+
		" and EXAM_student_final_mark_details.student_id = student.id "+
		" and EXAM_student_final_mark_details.subject_id = subject.id and (EXAM_student_final_mark_details.student_theory_marks is not null or "+
		" EXAM_student_final_mark_details.student_practical_marks is not null )"+
		" inner join EXAM_subject_rule_settings on EXAM_subject_rule_settings.course_id = course.id "+
		" and EXAM_subject_rule_settings.subject_id = subject.id "+
		" and EXAM_subject_rule_settings.scheme_no=classes.term_number "+
		" and EXAM_subject_rule_settings.academic_year = curriculum_scheme_duration.academic_year "+
		" left join EXAM_sub_definition_coursewise on EXAM_sub_definition_coursewise.course_id = course.id "+
		" and EXAM_sub_definition_coursewise.subject_id = subject.id "+
		" and EXAM_sub_definition_coursewise.scheme_no = classes.term_number "+
		" and EXAM_sub_definition_coursewise.academic_year=curriculum_scheme_duration.academic_year "+
		" left join EXAM_subject_sections on EXAM_sub_definition_coursewise.subject_section_id = EXAM_subject_sections.id "+
		" inner join EXAM_definition ON ifnull(EXAM_student_final_mark_details.exam_id,EXAM_student_overall_internal_mark_details.exam_id) = EXAM_definition.id "+
		" left join EXAM_supplementary_improvement_application on EXAM_supplementary_improvement_application.scheme_no = classes.term_number "+
		" and EXAM_supplementary_improvement_application.student_id = student.id "+
		" and EXAM_supplementary_improvement_application.subject_id = subject.id "+
//		" and EXAM_supplementary_improvement_application.exam_id = EXAM_definition.id "+
		" left join EXAM_internal_mark_supplementary_details on EXAM_internal_mark_supplementary_details.class_id = classes.id "+
//		" and EXAM_internal_mark_supplementary_details.exam_id = EXAM_definition.id "+
		" and EXAM_internal_mark_supplementary_details.student_id = student.id "+
		" and EXAM_internal_mark_supplementary_details.subject_id = subject.id "+
		" AND if((classes.course_id=18),"+ /*if BBA take the internal mark for the current regerring supli as they have internal always for each supli, other courses take the latest exam marks*/
	       " (EXAM_internal_mark_supplementary_details.exam_id=EXAM_definition.id)," +
	       " (EXAM_internal_mark_supplementary_details.exam_id=(select e.exam_id from  EXAM_internal_mark_supplementary_details e" +
	       " inner join EXAM_definition as ex on e.exam_id=ex.id" +
	       " where e.class_id=classes.id and e.student_id=student.id and e.subject_id=subject.id" +
	       " and ex.exam_type_id in (3, 6) order by ex.year desc, ex.month desc limit 1)))"+
		" where student.id = :id "+
		" and subject.is_theory_practical='B'  "+
		" order by term_number, student_id, subject_id";
		return query;
	}
	
	/**
	 * @param list
	 * @param certificateMap
	 * @param appearedList
	 * @param courseId
	 * @return
	 * @throws Exception
	 */
	public Map<Integer, Map<String, StudentTranscriptPrintTO>> getBoListForInput(List<Object[]> list,
			Map<Integer,Map<Integer,Map<Integer,Boolean>>> certificateMap,List<String> appearedList,String courseId) throws Exception {
		
		Map<Integer,Map<String, StudentTranscriptPrintTO>> mainMap=new HashMap<Integer, Map<String,StudentTranscriptPrintTO>>();

		try{
		boolean isMaxRecord=false;
		if(courseId!=null && !courseId.isEmpty() && Integer.parseInt(courseId)!=18){
			isMaxRecord=true;
		}
		
		if(list!=null && !list.isEmpty()){
			Map<String, StudentTranscriptPrintTO> semMap;
			StudentTranscriptPrintTO printTO = null;
			Student student;
			Subject subject;
			Course course;
			Iterator<Object[]> itr=list.iterator();
			while (itr.hasNext()) {
				boolean isCertificateCourse=false;
				boolean isOptional=false;
				
				Object[] obj=itr.next();
				
				if(obj[3]!=null && obj[32]!=null && obj[5]!=null){
					
					if(mainMap.containsKey(Integer.parseInt(obj[3].toString()))){
						semMap=mainMap.get(Integer.parseInt(obj[3].toString()));
						if(semMap.containsKey(Integer.parseInt(obj[32].toString())+"_"+Integer.parseInt(obj[5].toString())+"_"+obj[31].toString())){
							if(!isMaxRecord)
								continue;
						}
						
						semMap=mainMap.remove(Integer.parseInt(obj[3].toString()));
					}else{
						semMap=new HashMap<String, StudentTranscriptPrintTO>();
					}
					
					printTO = new StudentTranscriptPrintTO();
					
					printTO.setShowSuppl(false);
					if(obj[3]!=null && !obj[3].toString().isEmpty() && StringUtils.isNumeric(obj[3].toString())){
						student=new Student();
						student.setId(Integer.parseInt(obj[3].toString()));
						printTO.setStudent(student);
					}
					if(obj[5]!=null && !obj[5].toString().isEmpty() && StringUtils.isNumeric(obj[5].toString())){
						subject=new Subject();
						subject.setId(Integer.parseInt(obj[5].toString()));
						printTO.setSubject(subject);
					}
					if(obj[12]!=null && !obj[12].toString().isEmpty() && StringUtils.isNumeric(obj[12].toString())){
						course=new Course();
						course.setId(Integer.parseInt(obj[12].toString()));
						printTO.setCourse(course);
					}
					String month="";
					String year="";
					if(obj[1]!=null && !obj[1].toString().isEmpty()){
						month=monthMap.get(obj[1].toString());
					}
					if(obj[2]!=null && !obj[2].toString().isEmpty()){
						year=obj[2].toString();
					}
					printTO.setMonthYear(month+", "+year);
					
					

					if(obj[19]!=null)
						printTO.setStudentName(obj[19].toString());
					if(obj[20]!=null)
						printTO.setStudentName(printTO.getStudentName()+" "+obj[20].toString());
					if(obj[21]!=null)
						printTO.setStudentName(printTO.getStudentName()+" "+obj[21].toString());
					if(obj[22]!=null)
						printTO.setSection(obj[22].toString());
					
					if(obj[7]!=null)
						printTO.setSubjectName(obj[7].toString());
					if(obj[8]!=null)
						printTO.setTheCiaMarksAwarded(obj[8].toString());
					if(obj[9]!=null)
						printTO.setTheAttMarksAwarded(obj[9].toString());
					if(obj[10]!=null)
						printTO.setPraCiaMarksAwarded(obj[10].toString());
					if(obj[11]!=null)
						printTO.setPraAttMarksAwarded(obj[11].toString());
					
					if(obj[13]!=null && !obj[13].toString().isEmpty() && CommonUtil.isValidDecimal(obj[13].toString()))
						printTO.setTheEndSemMinMarks(BigDecimal.valueOf(new BigDecimal(obj[13].toString()).doubleValue()));
					if(obj[14]!=null && !obj[14].toString().isEmpty() && CommonUtil.isValidDecimal(obj[14].toString()))
						printTO.setTheEndSemMaxMarks(BigDecimal.valueOf(new BigDecimal(obj[14].toString()).doubleValue()));
					if(obj[15]!=null && !obj[15].toString().isEmpty() && CommonUtil.isValidDecimal(obj[15].toString()))
						printTO.setPraEndSemMinMarks(BigDecimal.valueOf(new BigDecimal(obj[15].toString()).doubleValue()));
					if(obj[16]!=null && !obj[16].toString().isEmpty() && CommonUtil.isValidDecimal(obj[16].toString()))
						printTO.setPraEndSemMaxMarks(BigDecimal.valueOf(new BigDecimal(obj[16].toString()).doubleValue()));
					if(obj[17]!=null && !obj[17].toString().isEmpty())
						printTO.setTheEndSemMarksAwarded(obj[17].toString());
					if(obj[18]!=null && !obj[18].toString().isEmpty())
						printTO.setPraEndSemMarksAwarded(obj[18].toString());
					
					/*if(obj[23]!=null && (obj[23].toString().equalsIgnoreCase("1") || obj[23].toString().equalsIgnoreCase("true")))
						printTO.setIsInitialise(true);
					else
						printTO.setIsInitialise(false);
					if(obj[24]!=null && !obj[24].toString().isEmpty() && StringUtils.isNumeric(obj[24].toString()))
					printTO.setSectionId(Integer.parseInt(obj[24].toString()));*/
					if(obj[25]!=null && !obj[25].toString().isEmpty() && StringUtils.isNumeric(obj[25].toString()))
						printTO.setSubjectOrder(Integer.parseInt(obj[25].toString()));
					if(obj[26]!=null && !obj[26].toString().isEmpty() && CommonUtil.isValidDecimal(obj[26].toString()))
						printTO.setPraCiaMaxMarks(BigDecimal.valueOf(new BigDecimal(obj[26].toString()).doubleValue()));
					if(obj[27]!=null && !obj[27].toString().isEmpty() && CommonUtil.isValidDecimal(obj[27].toString()))
						printTO.setTheCiaMaxMarks(BigDecimal.valueOf(new BigDecimal(obj[27].toString()).doubleValue()));
					if(obj[28]!=null && !obj[28].toString().isEmpty() && StringUtils.isNumeric(obj[28].toString()))
						printTO.setPraCredit(Integer.parseInt(obj[28].toString()));
					if(obj[29]!=null && !obj[29].toString().isEmpty() && StringUtils.isNumeric(obj[29].toString()))
						printTO.setTheCredit(Integer.parseInt(obj[29].toString()));
					if(obj[30]!=null && !obj[30].toString().isEmpty())
						printTO.setRegNo(obj[30].toString());
					if(obj[31]!=null)
						printTO.setSubType(obj[31].toString());
					if(obj[32]!=null && !obj[32].toString().isEmpty() && StringUtils.isNumeric(obj[32].toString()))
						printTO.setTermNumber(Integer.parseInt(obj[32].toString()));
					if(obj[33]!=null && !obj[33].toString().isEmpty() && CommonUtil.isValidDecimal(obj[33].toString()))						
							printTO.setTheTotMaxMarks(BigDecimal.valueOf(new BigDecimal(obj[33].toString()).doubleValue()));
					if(obj[34]!=null && !obj[34].toString().isEmpty() && CommonUtil.isValidDecimal(obj[34].toString()))
						printTO.setPraTotMaxMarks(BigDecimal.valueOf(new BigDecimal(obj[34].toString()).doubleValue()));
					/*if(obj[35]!=null && (obj[35].toString().equalsIgnoreCase("1") || obj[35].toString().equalsIgnoreCase("true")))
						printTO.setDontShowMaxMarks(true);
					else
						printTO.setDontShowMaxMarks(false);
					if(obj[36]!=null && (obj[36].toString().equalsIgnoreCase("1") || obj[36].toString().equalsIgnoreCase("true")))
						printTO.setDontShowMinMarks(true);
					else
						printTO.setDontShowMinMarks(false);*/
					if(obj[37]!=null && (obj[37].toString().equalsIgnoreCase("1") || obj[37].toString().equalsIgnoreCase("true")))
						printTO.setShowOnlyGrade(true);
					else
						printTO.setShowOnlyGrade(false);
					if(obj[38]!=null)
						printTO.setSchemeName(obj[38].toString());
					if(obj[39]!=null && (obj[39].toString().equalsIgnoreCase("1") || obj[39].toString().equalsIgnoreCase("true")))
						printTO.setDontShowSubType(true);
					else
						printTO.setDontShowSubType(false);
					if(obj[40]!=null && !obj[40].toString().isEmpty() && StringUtils.isNumeric(obj[40].toString()))
						printTO.setAppliedYear(Integer.parseInt(obj[40].toString()));
					if(obj[41]!=null && !obj[41].toString().isEmpty() && StringUtils.isNumeric(obj[41].toString()))
						printTO.setAcademicYear(Integer.parseInt(obj[41].toString()));
					/*if(obj[42]!=null && !obj[42].toString().isEmpty() && StringUtils.isNumeric(obj[42].toString()))
						printTO.setSelectedCourseId(Integer.parseInt(obj[42].toString()));*/
					if(obj[43]!=null && !obj[43].toString().isEmpty() && CommonUtil.isValidDecimal(obj[43].toString()))
						printTO.setFinalTheoryInternalMinimumMark(BigDecimal.valueOf(new BigDecimal(obj[43].toString()).doubleValue()));
					if(obj[44]!=null && !obj[44].toString().isEmpty() && CommonUtil.isValidDecimal(obj[44].toString()))
						printTO.setFinalPracticalInternalMinimumMark(BigDecimal.valueOf(new BigDecimal(obj[44].toString()).doubleValue()));
					if(obj[45]!=null && !obj[45].toString().isEmpty() && CommonUtil.isValidDecimal(obj[45].toString()))
						printTO.setTheoryMin(BigDecimal.valueOf(new BigDecimal(obj[45].toString()).doubleValue()));
					if(obj[46]!=null && !obj[46].toString().isEmpty() && CommonUtil.isValidDecimal(obj[46].toString()))
						printTO.setPracticalMin(BigDecimal.valueOf(new BigDecimal(obj[46].toString()).doubleValue()));
					if(obj[47]!=null && (obj[47].toString().equalsIgnoreCase("1") || obj[47].toString().equalsIgnoreCase("true")))
						printTO.setDontConsiderFailureTotalResult(true);
					else
						printTO.setDontConsiderFailureTotalResult(false);
					
					if(obj[50]!=null && (obj[50].toString().equalsIgnoreCase("1") || obj[50].toString().equalsIgnoreCase("true")))
						printTO.setDontAddInTotal(true);
					else
						printTO.setDontAddInTotal(false);
					
					if(appearedList.contains(printTO.getStudent().getId()+"_"+printTO.getTermNumber()+"_"+printTO.getSubject().getId()+"_T")){
						printTO.setIsSuppTheoryAppeared(true);
						printTO.setShowSuppl(true);
					}else{
						printTO.setIsSuppTheoryAppeared(false);
					}
					if(appearedList.contains(printTO.getStudent().getId()+"_"+printTO.getTermNumber()+"_"+printTO.getSubject().getId()+"_P")){
						printTO.setIsSuppPracticalAppeared(true);
						printTO.setShowSuppl(true);
					}else{
						printTO.setIsSuppPracticalAppeared(false);
					}
					
					if(certificateMap.containsKey(printTO.getStudent().getId())){
						Map<Integer,Map<Integer,Boolean>> termMap=certificateMap.get(printTO.getStudent().getId());
						if(termMap.containsKey(printTO.getTermNumber())){
							Map<Integer,Boolean> subMap=termMap.get(printTO.getTermNumber());
							if(subMap.containsKey(printTO.getSubject().getId())){
								isCertificateCourse=true;
								isOptional=subMap.get(printTO.getSubject().getId());
							}else if(obj[51]!=null && (obj[51].toString().equalsIgnoreCase("1") || obj[51].toString().equalsIgnoreCase("true"))){
								isCertificateCourse=true;
								isOptional=true;
							}
						}else if(obj[51]!=null && (obj[51].toString().equalsIgnoreCase("1") || obj[51].toString().equalsIgnoreCase("true"))){
							isCertificateCourse=true;
							isOptional=true;
						}
					}else{
						if(obj[51]!=null && (obj[51].toString().equalsIgnoreCase("1") || obj[51].toString().equalsIgnoreCase("true"))){
							isCertificateCourse=true;
							isOptional=true;
						}
					}
					
					printTO.setIsCertificateCourse(isCertificateCourse);
					// if it is only internal subject we should explicitily hit the data base
					if(obj[52]!=null && (obj[52].toString().equalsIgnoreCase("1") || obj[52].toString().equalsIgnoreCase("true"))){
						checkInternalMarksForSubject(printTO,isMaxRecord);
					}
					boolean isImprovement=false;
					if((printTO.getIsSuppTheoryAppeared()!=null && printTO.getIsSuppTheoryAppeared()) ||  (printTO.getIsSuppPracticalAppeared()!=null && printTO.getIsSuppPracticalAppeared()))
						if(obj[53]!=null && (obj[53].toString().equalsIgnoreCase("1") || obj[53].toString().equalsIgnoreCase("true"))){
							isImprovement=true;
						}
					if(obj[54]!=null && !obj[54].toString().isEmpty() && CommonUtil.isValidDecimal(obj[54].toString()))
						printTO.setAttMaxMarks(BigDecimal.valueOf(new BigDecimal(obj[54].toString()).doubleValue()));
					if(isMaxRecord){
						if(semMap.containsKey(printTO.getTermNumber()+"_"+printTO.getSubject().getId()+"_"+printTO.getSubType())){
							StudentTranscriptPrintTO bo=semMap.get(printTO.getTermNumber()+"_"+printTO.getSubject().getId()+"_"+printTO.getSubType());
							ConsolidateMaxTo to=CheckMaxMarks(printTO,bo,isImprovement);
							if(printTO.getSubType().equalsIgnoreCase("practical")){
								printTO.setPraCiaMarksAwarded(to.getPracticalCia());
								printTO.setPraAttMarksAwarded(to.getPracticalAtt());
								printTO.setPraEndSemMarksAwarded(to.getPracticalEse());
								printTO.setIsSuppPracticalAppeared(to.getIsAppearedPractical());
							}else{
								printTO.setTheCiaMarksAwarded(to.getTheoryCia());
								printTO.setTheAttMarksAwarded(to.getTheoryAtt());
								printTO.setTheEndSemMarksAwarded(to.getTheoryEse());
								printTO.setIsSuppTheoryAppeared(to.getIsAppearedTheory());
							}
							calculateMarksPercentage(printTO,isCertificateCourse,isOptional);
						}else{
							calculateMarksPercentage(printTO,isCertificateCourse,isOptional);
						}
						
						if(semMap.containsKey(printTO.getTermNumber()+"_"+printTO.getSubject().getId()+"_"+printTO.getSubType())){
							semMap.remove(printTO.getTermNumber()+"_"+printTO.getSubject().getId()+"_"+printTO.getSubType());
						}
						if(!printTO.getSection().equalsIgnoreCase("Add On Course") && !printTO.getGrade().equalsIgnoreCase("F"))
							semMap.put(printTO.getTermNumber()+"_"+printTO.getSubject().getId()+"_"+printTO.getSubType(),printTO);
						else if(printTO.getSection().equalsIgnoreCase("Add On Course") && !printTO.getGrade().equalsIgnoreCase("F"))
							semMap.put(printTO.getTermNumber()+"_"+printTO.getSubject().getId()+"_"+printTO.getSubType(),printTO);
						mainMap.put(printTO.getStudent().getId(),semMap);
						
					}else{
						if(!semMap.containsKey(printTO.getTermNumber()+"_"+printTO.getSubject().getId()+"_"+printTO.getSubType())){
							calculateMarksPercentage(printTO,isCertificateCourse,isOptional);
							if(!printTO.getSection().equalsIgnoreCase("Add On Course") && !printTO.getGrade().equalsIgnoreCase("F"))
								semMap.put(printTO.getTermNumber()+"_"+printTO.getSubject().getId()+"_"+printTO.getSubType(),printTO);
							else if(printTO.getSection().equalsIgnoreCase("Add On Course") && !printTO.getGrade().equalsIgnoreCase("F"))
								semMap.put(printTO.getTermNumber()+"_"+printTO.getSubject().getId()+"_"+printTO.getSubType(),printTO);
							mainMap.put(printTO.getStudent().getId(),semMap);
						}else{
							mainMap.put(printTO.getStudent().getId(),semMap);
						}
					}
				}
			}
		}
		}catch (Exception e) {
			e.getMessage();
		}
		
		return mainMap;
	}
	
	
	
	
	/**
	 * @param printTO
	 * @param isCertificateCourse
	 * @param isOptional
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private void calculateMarksPercentage(StudentTranscriptPrintTO printTO,	boolean isCertificateCourse, boolean isOptional) throws Exception {
		
		IDownloadHallTicketTransaction transaction= DownloadHallTicketTransactionImpl.getInstance();
		double theoryCia=0;
		double theoryAtt=0;
		double theoryEse=0;
		double theoryTotal=0;
		double practicalCia=0;
		double practicalAtt=0;
		double practicalEse=0;
		double practicalTotal=0;
		boolean fail=false;
		if(printTO.getSubType().equalsIgnoreCase("Practical")){
			if(printTO.getPraAttMarksAwarded()!=null && !printTO.getPraAttMarksAwarded().isEmpty()){
				if(CommonUtil.isValidDecimal(printTO.getPraAttMarksAwarded()))
					practicalAtt=Double.parseDouble(printTO.getPraAttMarksAwarded());
				else{
					if(!isCertificateCourse && !printTO.getDontConsiderFailureTotalResult())
						fail=true;
				}
			}
			if(printTO.getPraCiaMarksAwarded()!=null && !printTO.getPraCiaMarksAwarded().isEmpty()){
				if(CommonUtil.isValidDecimal(printTO.getPraCiaMarksAwarded())){
					practicalCia=Double.parseDouble(printTO.getPraCiaMarksAwarded());
					if(printTO.getFinalPracticalInternalMinimumMark()!=null){
						double minMark=printTO.getFinalPracticalInternalMinimumMark().doubleValue();
						if((practicalAtt+practicalCia)<minMark)
							if(!isCertificateCourse && !printTO.getDontConsiderFailureTotalResult())
								fail=true;
					}
				}else{
					if(!isCertificateCourse && !printTO.getDontConsiderFailureTotalResult())
						fail=true;
				}
			}
			
			if(printTO.getPraEndSemMarksAwarded()!=null && !printTO.getPraEndSemMarksAwarded().isEmpty()){
				if(CommonUtil.isValidDecimal(printTO.getPraEndSemMarksAwarded())){
					practicalEse=Double.parseDouble(printTO.getPraEndSemMarksAwarded());
					if(printTO.getPraEndSemMinMarks()!=null){
						double minMark=printTO.getPraEndSemMinMarks().doubleValue();
						if(practicalEse<minMark)
							if(!isCertificateCourse && !printTO.getDontConsiderFailureTotalResult())
								fail=true;
					}
				}else{
					if(!isCertificateCourse && !printTO.getDontConsiderFailureTotalResult())
						fail=true;
				}
			}
			practicalTotal=practicalAtt+practicalCia+practicalEse;
			printTO.setPraTotMarksAwarded(practicalTotal);
						
			double minMark=0;
			if(printTO.getPracticalMin()!=null){
				minMark=printTO.getPracticalMin().doubleValue();
			}
			if(printTO.getPraTotMaxMarks()!=null)
				if(isCertificateCourse)
					if(isOptional){
						if(printTO.getPraTotMaxMarks().doubleValue()==100)
							minMark=60;
						else if(printTO.getPraTotMaxMarks().doubleValue()==50)
							minMark=30;
					}
			if(practicalTotal<minMark)
				fail=true;
			if(printTO.getPraTotMaxMarks()!=null){
				double maxMark=printTO.getPraTotMaxMarks().doubleValue();
				if(maxMark>0){
				double percentage=(practicalTotal/maxMark)*100;
				printTO.setPracticalPercentage(CommonUtil.Round(percentage,2));
				
				String query="select ifnull((SELECT EXAM_sub_coursewise_grade_defn.grade " +
							" FROM EXAM_sub_coursewise_grade_defn EXAM_sub_coursewise_grade_defn where "+
							percentage+" between start_prcntg_grade and end_prcntg_grade and EXAM_sub_coursewise_grade_defn.course_id = "+printTO.getCourse().getId()+" and subject_id="+printTO.getSubject().getId()+")," +
							" (ifnull( (SELECT EXAM_grade_class_definition_frombatch.grade " +
							" FROM EXAM_grade_class_definition_frombatch " +
							" EXAM_grade_class_definition_frombatch where "+percentage+" between EXAM_grade_class_definition_frombatch.start_percentage and EXAM_grade_class_definition_frombatch.end_percentage and " +
							" EXAM_grade_class_definition_frombatch.course_id = "+printTO.getCourse().getId()+" and from_batch <= "+printTO.getAppliedYear()+" limit 1)," +
							" (SELECT  EXAM_grade_class_definition.grade " +
							" FROM EXAM_grade_class_definition EXAM_grade_class_definition where "+percentage+" between start_percentage and EXAM_grade_class_definition.end_percentage and EXAM_grade_class_definition.course_id = "+printTO.getCourse().getId()+" limit 1)))) as pragra," +
							"  ifnull( (SELECT EXAM_sub_coursewise_grade_defn.grade_point " +
							" FROM EXAM_sub_coursewise_grade_defn EXAM_sub_coursewise_grade_defn where " +
							+percentage+" between start_prcntg_grade and end_prcntg_grade and " +
							" EXAM_sub_coursewise_grade_defn.course_id = "+printTO.getCourse().getId()+" and subject_id="+printTO.getSubject().getId()+"), " +
							"  (ifnull( (SELECT     EXAM_grade_class_definition_frombatch.grade_point " +
							"  FROM EXAM_grade_class_definition_frombatch EXAM_grade_class_definition_frombatch where " +
							+percentage+" between EXAM_grade_class_definition_frombatch.start_percentage and " +
							" EXAM_grade_class_definition_frombatch.end_percentage and " +
							" EXAM_grade_class_definition_frombatch.course_id = "+printTO.getCourse().getId()+" and from_batch <= "+printTO.getAppliedYear()+" limit 1), " +
							"(SELECT EXAM_grade_class_definition.grade_point " +
							"  FROM EXAM_grade_class_definition EXAM_grade_class_definition " +
							" where "+percentage+" between start_percentage and EXAM_grade_class_definition.end_percentage and " +
							" EXAM_grade_class_definition.course_id = "+printTO.getCourse().getId()+" limit 1)))) as pragrap";
				
				List<Object[]> rList=transaction.getStudentHallTicket(query);
				if(rList!=null && !rList.isEmpty()){
					Iterator<Object[]> rItr=rList.iterator();
					while (rItr.hasNext()) {
						Object[] robj = rItr.next();
						if(robj[0]!=null){
							printTO.setGrade(robj[0].toString());
						}
						if(robj[1]!=null){
							printTO.setGradePoint(new BigDecimal(robj[1].toString()));
						}
						
					}
				}
				}
			}
			
			if(fail){
				printTO.setPassOrFail("Fail");
				if(printTO.getSection()!=null && !printTO.getSection().equalsIgnoreCase("Add On Course"))
					printTO.setGrade("F");
			}else{
				printTO.setPassOrFail("Pass");
			}
		}else{
			if(printTO.getTheAttMarksAwarded()!=null && !printTO.getTheAttMarksAwarded().isEmpty()){
				if(CommonUtil.isValidDecimal(printTO.getTheAttMarksAwarded())){
					theoryAtt=Double.parseDouble(printTO.getTheAttMarksAwarded());
				}else{
					if(!isCertificateCourse && !printTO.getDontConsiderFailureTotalResult())
						fail=true;
				}
			}
			if(printTO.getTheCiaMarksAwarded()!=null && !printTO.getTheCiaMarksAwarded().isEmpty()){
				if(CommonUtil.isValidDecimal(printTO.getTheCiaMarksAwarded())){
					theoryCia=Double.parseDouble(printTO.getTheCiaMarksAwarded());
					if(printTO.getFinalTheoryInternalMinimumMark()!=null){
						double minMark=printTO.getFinalTheoryInternalMinimumMark().doubleValue();
						if((theoryAtt+theoryCia)<minMark)
							if(!isCertificateCourse && !printTO.getDontConsiderFailureTotalResult())
								fail=true;
					}
				}else{
					if(!isCertificateCourse && !printTO.getDontConsiderFailureTotalResult())
						fail=true;
				}
			}
			
			if(printTO.getTheEndSemMarksAwarded()!=null && !printTO.getTheEndSemMarksAwarded().isEmpty()){
				if(CommonUtil.isValidDecimal(printTO.getTheEndSemMarksAwarded())){
					theoryEse=Double.parseDouble(printTO.getTheEndSemMarksAwarded());
					if(printTO.getTheEndSemMinMarks()!=null){
						double minMark=printTO.getTheEndSemMinMarks().doubleValue();
						if(theoryEse<minMark)
							if(!isCertificateCourse && !printTO.getDontConsiderFailureTotalResult())
								fail=true;
					}
				}else{
					if(!isCertificateCourse && !printTO.getDontConsiderFailureTotalResult())
						fail=true;
				}
			}

			theoryTotal=theoryAtt+theoryCia+theoryEse;
			printTO.setTheTotMarksAwarded(theoryTotal);
			
			double minMark=0;
			if(printTO.getTheoryMin()!=null){
				minMark=printTO.getTheoryMin().doubleValue();
			}
			if(printTO.getTheTotMaxMarks()!=null)
				if(isCertificateCourse)
					if(isOptional){
						if(printTO.getTheTotMaxMarks().doubleValue()==100)
							minMark=60;
						else if(printTO.getTheTotMaxMarks().doubleValue()==50)
							minMark=30;
					}
			if(theoryTotal<minMark)
				fail=true;
			
			if(printTO.getTheTotMaxMarks()!=null){
				double maxMark=printTO.getTheTotMaxMarks().doubleValue();
				if(maxMark>0){
				double percentage=(theoryTotal/maxMark)*100;
				printTO.setTheoryPercentage(CommonUtil.Round(percentage,2));
				
				String query="select ifnull((SELECT EXAM_sub_coursewise_grade_defn.grade " +
				" FROM EXAM_sub_coursewise_grade_defn EXAM_sub_coursewise_grade_defn where "+
				percentage+" between start_prcntg_grade and end_prcntg_grade and EXAM_sub_coursewise_grade_defn.course_id = "+printTO.getCourse().getId()+" and subject_id="+printTO.getSubject().getId()+")," +
				" (ifnull( (SELECT EXAM_grade_class_definition_frombatch.grade " +
				" FROM EXAM_grade_class_definition_frombatch " +
				" EXAM_grade_class_definition_frombatch where "+percentage+" between EXAM_grade_class_definition_frombatch.start_percentage and EXAM_grade_class_definition_frombatch.end_percentage and " +
				" EXAM_grade_class_definition_frombatch.course_id = "+printTO.getCourse().getId()+" and from_batch <= "+printTO.getAppliedYear()+" limit 1)," +
				" (SELECT  EXAM_grade_class_definition.grade " +
				" FROM EXAM_grade_class_definition EXAM_grade_class_definition where "+percentage+" between start_percentage and EXAM_grade_class_definition.end_percentage and EXAM_grade_class_definition.course_id = "+printTO.getCourse().getId()+" limit 1)))) as thegra," +
				"  ifnull( (SELECT EXAM_sub_coursewise_grade_defn.grade_point " +
				" FROM EXAM_sub_coursewise_grade_defn EXAM_sub_coursewise_grade_defn where " +
				+percentage+" between start_prcntg_grade and end_prcntg_grade and " +
				" EXAM_sub_coursewise_grade_defn.course_id = "+printTO.getCourse().getId()+" and subject_id="+printTO.getSubject().getId()+"), " +
				"  (ifnull( (SELECT     EXAM_grade_class_definition_frombatch.grade_point " +
				"  FROM EXAM_grade_class_definition_frombatch EXAM_grade_class_definition_frombatch where " +
				+percentage+" between EXAM_grade_class_definition_frombatch.start_percentage and " +
				" EXAM_grade_class_definition_frombatch.end_percentage and " +
				" EXAM_grade_class_definition_frombatch.course_id = "+printTO.getCourse().getId()+" and from_batch <= "+printTO.getAppliedYear()+" limit 1), " +
				"(SELECT EXAM_grade_class_definition.grade_point " +
				"  FROM EXAM_grade_class_definition EXAM_grade_class_definition " +
				" where "+percentage+" between start_percentage and EXAM_grade_class_definition.end_percentage and " +
				" EXAM_grade_class_definition.course_id = "+printTO.getCourse().getId()+" limit 1)))) as thegrap";
			
				List<Object[]> rList=transaction.getStudentHallTicket(query);
				if(rList!=null && !rList.isEmpty()){
					Iterator<Object[]> rItr=rList.iterator();
					while (rItr.hasNext()) {
						Object[] robj = rItr.next();
						if(robj[0]!=null){
							printTO.setGrade(robj[0].toString());
						}
						if(robj[1]!=null){
							printTO.setGradePoint(new BigDecimal(robj[1].toString()));
						}
						
					}
				}
				}
			}
			if(fail){
				printTO.setPassOrFail("Fail");
				if(printTO.getSection()!=null && !printTO.getSection().equalsIgnoreCase("Add On Course"))
						printTO.setGrade("F");
			}else{
				printTO.setPassOrFail("Pass");
			}
		}
		
	}
	
	
	/**
	 * @param printTO
	 * @param isMaxRecord
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private void checkInternalMarksForSubject(StudentTranscriptPrintTO printTO, boolean isMaxRecord) throws Exception {
		String query="from InternalMarkSupplementaryDetails i where i.student.id="+printTO.getStudent().getId()+" and i.subject.id="+printTO.getSubject().getId()+" order by i.exam.year desc,i.exam.month desc ";
		INewExamMarksEntryTransaction transaction=NewExamMarksEntryTransactionImpl.getInstance();
		List<InternalMarkSupplementaryDetails> list=transaction.getDataForQuery(query);
		ConsolidateMarksCard card=null;
		ConsolidateMarksCard latest=null;
		if(list!=null && !list.isEmpty()){
			Iterator<InternalMarkSupplementaryDetails> itr=list.iterator();
			while (itr.hasNext()) {
				InternalMarkSupplementaryDetails bo = itr .next();
				card=new ConsolidateMarksCard();
				if(printTO.getSubType().equalsIgnoreCase("practical")){
					if(bo.getPracticalTotalSubInternalMarks()!=null)
						card.setPracticalTotalSubInternalMark(bo.getPracticalTotalSubInternalMarks());
					if(bo.getPracticalTotalAttendenceMarks()!=null)
						card.setPracticalTotalAttendanceMark(bo.getPracticalTotalAttendenceMarks());
				}else{
					if(bo.getTheoryTotalSubInternalMarks()!=null)
						card.setTheoryTotalSubInternalMark(bo.getTheoryTotalSubInternalMarks());
					if(bo.getTheoryTotalAttendenceMarks()!=null)
						card.setTheoryTotalAttendanceMark(bo.getTheoryTotalAttendenceMarks());
				}
				
				if(isMaxRecord){
					if(latest==null){
						latest=card;
					}else{
						ConsolidateMaxTo to=checkMaxForInternal(latest,card);
						if(printTO.getSubType().equalsIgnoreCase("practical")){
							if(to.getPracticalCia()!=null)
								latest.setPracticalTotalSubInternalMark(to.getPracticalCia());
							if(to.getPracticalAtt()!=null)
								latest.setPracticalTotalAttendanceMark(to.getPracticalAtt());
						}else{
							if(to.getTheoryCia()!=null)
								latest.setTheoryTotalSubInternalMark(to.getTheoryCia());
							if(to.getTheoryAtt()!=null)
								latest.setTheoryTotalAttendanceMark(to.getTheoryAtt());
						}
					}
						
				}else{
					if(latest==null)
						latest=card;
				}
				
			}
			if(latest!=null){
				if(printTO.getSubType().equalsIgnoreCase("practical")){
					if(latest.getPracticalTotalSubInternalMark()!=null)
						printTO.setPraCiaMarksAwarded(latest.getPracticalTotalSubInternalMark());
					if(latest.getPracticalTotalAttendanceMark()!=null)
						printTO.setPraAttMarksAwarded(latest.getPracticalTotalAttendanceMark());
				}else{
					if(latest.getTheoryTotalSubInternalMark()!=null)
						printTO.setTheCiaMarksAwarded(latest.getTheoryTotalSubInternalMark());
					if(latest.getTheoryTotalAttendanceMark()!=null)
						printTO.setTheAttMarksAwarded(latest.getTheoryTotalAttendanceMark());
				}
			}
		}
		
	}
	
	
	/**
	 * @param bo1
	 * @param bo2
	 * @return
	 * @throws Exception
	 */
	private ConsolidateMaxTo checkMaxForInternal( ConsolidateMarksCard bo1, ConsolidateMarksCard bo2) throws Exception {
		ConsolidateMaxTo to=new ConsolidateMaxTo();
		if(bo1!=null && bo2!=null){
			if(bo1.getTheoryTotalSubInternalMark()!=null && !bo1.getTheoryTotalSubInternalMark().isEmpty()){
				if(StringUtils.isAlpha(bo1.getTheoryTotalSubInternalMark().trim())){
					if(bo2.getTheoryTotalSubInternalMark()!=null && !bo2.getTheoryTotalSubInternalMark().isEmpty() && !StringUtils.isAlpha(bo2.getTheoryTotalSubInternalMark().trim())){
						to.setTheoryCia(bo2.getTheoryTotalSubInternalMark());
					}else{
						to.setTheoryCia(bo1.getTheoryTotalSubInternalMark());
					}
				}else{
					if(bo2.getTheoryTotalSubInternalMark()!=null && !bo2.getTheoryTotalSubInternalMark().isEmpty() && !StringUtils.isAlpha(bo2.getTheoryTotalSubInternalMark().trim())){
						if(Double.parseDouble(bo1.getTheoryTotalSubInternalMark()) < Double.parseDouble(bo2.getTheoryTotalSubInternalMark()))
							to.setTheoryCia(bo2.getTheoryTotalSubInternalMark());
						else
							to.setTheoryCia(bo1.getTheoryTotalSubInternalMark());
					}else{
						to.setTheoryCia(bo1.getTheoryTotalSubInternalMark());
					}
				}
			}else{
				if(bo2.getTheoryTotalSubInternalMark()!=null && !bo2.getTheoryTotalSubInternalMark().isEmpty()){
					to.setTheoryCia(bo2.getTheoryTotalSubInternalMark());
				}
			}
			
			// Practical Int Mark
			if(bo1.getPracticalTotalSubInternalMark()!=null && !bo1.getPracticalTotalSubInternalMark().isEmpty()){
				if(StringUtils.isAlpha(bo1.getPracticalTotalSubInternalMark().trim())){
					if(bo2.getPracticalTotalSubInternalMark()!=null && !bo2.getPracticalTotalSubInternalMark().isEmpty() && !StringUtils.isAlpha(bo2.getPracticalTotalSubInternalMark().trim())){
						to.setPracticalCia(bo2.getPracticalTotalSubInternalMark());
					}else{
						to.setPracticalCia(bo1.getPracticalTotalSubInternalMark());
					}
				}else{
					if(bo2.getPracticalTotalSubInternalMark()!=null && !bo2.getPracticalTotalSubInternalMark().isEmpty() && !StringUtils.isAlpha(bo2.getPracticalTotalSubInternalMark().trim())){
						if(Double.parseDouble(bo1.getPracticalTotalSubInternalMark()) < Double.parseDouble(bo2.getPracticalTotalSubInternalMark()))
							to.setPracticalCia(bo2.getPracticalTotalSubInternalMark());
						else
							to.setPracticalCia(bo1.getPracticalTotalSubInternalMark());
					}else{
						to.setPracticalCia(bo1.getPracticalTotalSubInternalMark());
					}
				}
			}else{
				if(bo2.getPracticalTotalSubInternalMark()!=null && !bo2.getPracticalTotalSubInternalMark().isEmpty()){
					to.setPracticalCia(bo2.getPracticalTotalSubInternalMark());
				}
			}
			
			if(bo1.getTheoryTotalAttendanceMark()!=null && !bo1.getTheoryTotalAttendanceMark().isEmpty()){
				if(StringUtils.isAlpha(bo1.getTheoryTotalAttendanceMark().trim())){
					if(bo2.getTheoryTotalAttendanceMark()!=null && !bo2.getTheoryTotalAttendanceMark().isEmpty() && !StringUtils.isAlpha(bo2.getTheoryTotalAttendanceMark().trim())){
						to.setTheoryAtt(bo2.getTheoryTotalAttendanceMark());
					}else{
						to.setTheoryAtt(bo1.getTheoryTotalAttendanceMark());
					}
				}else{
					if(bo2.getTheoryTotalAttendanceMark()!=null && !bo2.getTheoryTotalAttendanceMark().isEmpty() && !StringUtils.isAlpha(bo2.getTheoryTotalAttendanceMark().trim())){
						if(Double.parseDouble(bo1.getTheoryTotalAttendanceMark()) < Double.parseDouble(bo2.getTheoryTotalAttendanceMark()))
							to.setTheoryAtt(bo2.getTheoryTotalAttendanceMark());
						else
							to.setTheoryAtt(bo1.getTheoryTotalAttendanceMark());
					}else{
						to.setTheoryAtt(bo1.getTheoryTotalAttendanceMark());
					}
				}
			}else{
				if(bo2.getTheoryTotalAttendanceMark()!=null && !bo2.getTheoryTotalAttendanceMark().isEmpty()){
					to.setTheoryAtt(bo2.getTheoryTotalAttendanceMark());
				}
			}
			// Practical Att Mark
			if(bo1.getPracticalTotalAttendanceMark()!=null && !bo1.getPracticalTotalAttendanceMark().isEmpty()){
				if(StringUtils.isAlpha(bo1.getPracticalTotalAttendanceMark().trim())){
					if(bo2.getPracticalTotalAttendanceMark()!=null && !bo2.getPracticalTotalAttendanceMark().isEmpty() && !StringUtils.isAlpha(bo2.getPracticalTotalAttendanceMark().trim())){
						to.setPracticalAtt(bo2.getPracticalTotalAttendanceMark());
					}else{
						to.setPracticalAtt(bo1.getPracticalTotalAttendanceMark());
					}
				}else{
					if(bo2.getPracticalTotalAttendanceMark()!=null && !bo2.getPracticalTotalAttendanceMark().isEmpty() && !StringUtils.isAlpha(bo2.getPracticalTotalAttendanceMark().trim())){
						if(Double.parseDouble(bo1.getPracticalTotalAttendanceMark()) < Double.parseDouble(bo2.getPracticalTotalAttendanceMark()))
							to.setPracticalAtt(bo2.getPracticalTotalAttendanceMark());
						else
							to.setPracticalAtt(bo1.getPracticalTotalAttendanceMark());
					}else{
						to.setPracticalAtt(bo1.getPracticalTotalAttendanceMark());
					}
				}
			}else{
				if(bo2.getPracticalTotalAttendanceMark()!=null && !bo2.getPracticalTotalAttendanceMark().isEmpty()){
					to.setPracticalAtt(bo2.getPracticalTotalAttendanceMark());
				}
			}
		}
		return to;
	}
	
	
	/**
	 * @param printTO
	 * @param bo
	 * @param isImprovement
	 * @return
	 * @throws Exception
	 */
	private ConsolidateMaxTo CheckMaxMarks(StudentTranscriptPrintTO printTO, StudentTranscriptPrintTO bo,boolean isImprovement) throws Exception {
		ConsolidateMaxTo to=new ConsolidateMaxTo();
		if(printTO!=null && bo!=null){
			//Is Appeared Theory
			if((printTO.getIsSuppTheoryAppeared()!=null && printTO.getIsSuppTheoryAppeared()) || (bo.getIsSuppTheoryAppeared()!=null && bo.getIsSuppTheoryAppeared())){
				to.setIsAppearedTheory(true);
			}else{
				to.setIsAppearedTheory(false);
			}
			//Is Appeared Practical
			if((printTO.getIsSuppPracticalAppeared()!=null && printTO.getIsSuppPracticalAppeared()) || (bo.getIsSuppPracticalAppeared()!=null && bo.getIsSuppPracticalAppeared())){
				to.setIsAppearedPractical(true);
			}else{
				to.setIsAppearedPractical(false);
			}
			
			if(isImprovement){// If it is improvement then we have to get max between the two records
			// Theory Int Marks
			if(printTO.getTheCiaMarksAwarded()!=null && !printTO.getTheCiaMarksAwarded().isEmpty()){
				if(StringUtils.isAlpha(printTO.getTheCiaMarksAwarded().trim())){
					if(bo.getTheCiaMarksAwarded()!=null && !bo.getTheCiaMarksAwarded().isEmpty() && !StringUtils.isAlpha(bo.getTheCiaMarksAwarded().trim())){
						to.setTheoryCia(bo.getTheCiaMarksAwarded());
					}else{
						to.setTheoryCia(printTO.getTheCiaMarksAwarded());
					}
				}else{
					if(bo.getTheCiaMarksAwarded()!=null && !bo.getTheCiaMarksAwarded().isEmpty() && !StringUtils.isAlpha(bo.getTheCiaMarksAwarded().trim())){
						if(Double.parseDouble(printTO.getTheCiaMarksAwarded()) < Double.parseDouble(bo.getTheCiaMarksAwarded()))
							to.setTheoryCia(bo.getTheCiaMarksAwarded());
						else
							to.setTheoryCia(printTO.getTheCiaMarksAwarded());
					}else{
						to.setTheoryCia(printTO.getTheCiaMarksAwarded());
					}
				}
			}else{
				if(bo.getTheCiaMarksAwarded()!=null && !bo.getTheCiaMarksAwarded().isEmpty()){
					to.setTheoryCia(bo.getTheCiaMarksAwarded());
				}
			}
			
			// Practical Int Mark
			if(printTO.getPraCiaMarksAwarded()!=null && !printTO.getPraCiaMarksAwarded().isEmpty()){
				if(StringUtils.isAlpha(printTO.getPraCiaMarksAwarded().trim())){
					if(bo.getPraCiaMarksAwarded()!=null && !bo.getPraCiaMarksAwarded().isEmpty() && !StringUtils.isAlpha(bo.getPraCiaMarksAwarded().trim())){
						to.setPracticalCia(bo.getPraCiaMarksAwarded());
					}else{
						to.setPracticalCia(printTO.getPraCiaMarksAwarded());
					}
				}else{
					if(bo.getPraCiaMarksAwarded()!=null && !bo.getPraCiaMarksAwarded().isEmpty() && !StringUtils.isAlpha(bo.getPraCiaMarksAwarded().trim())){
						if(Double.parseDouble(printTO.getPraCiaMarksAwarded()) < Double.parseDouble(bo.getPraCiaMarksAwarded()))
							to.setPracticalCia(bo.getPraCiaMarksAwarded());
						else
							to.setPracticalCia(printTO.getPraCiaMarksAwarded());
					}else{
						to.setPracticalCia(printTO.getPraCiaMarksAwarded());
					}
				}
			}else{
				if(bo.getPraCiaMarksAwarded()!=null && !bo.getPraCiaMarksAwarded().isEmpty()){
					to.setPracticalCia(bo.getPraCiaMarksAwarded());
				}
			}
			
			// Theory Reg Mark
			if(printTO.getTheEndSemMarksAwarded()!=null && !printTO.getTheEndSemMarksAwarded().isEmpty()){
				if(StringUtils.isAlpha(printTO.getTheEndSemMarksAwarded().trim())){
					if(bo.getTheEndSemMarksAwarded()!=null && !bo.getTheEndSemMarksAwarded().isEmpty() && !StringUtils.isAlpha(bo.getTheEndSemMarksAwarded().trim())){
						to.setTheoryEse(bo.getTheEndSemMarksAwarded());
					}else{
						to.setTheoryEse(printTO.getTheEndSemMarksAwarded());
					}
				}else{
					if(bo.getTheEndSemMarksAwarded()!=null && !bo.getTheEndSemMarksAwarded().isEmpty() && !StringUtils.isAlpha(bo.getTheEndSemMarksAwarded().trim())){
						if(Double.parseDouble(printTO.getTheEndSemMarksAwarded()) < Double.parseDouble(bo.getTheEndSemMarksAwarded()))
							to.setTheoryEse(bo.getTheEndSemMarksAwarded());
						else
							to.setTheoryEse(printTO.getTheEndSemMarksAwarded());
					}else{
						to.setTheoryEse(printTO.getTheEndSemMarksAwarded());
					}
				}
			}else{
				if(bo.getTheEndSemMarksAwarded()!=null && !bo.getTheEndSemMarksAwarded().isEmpty()){
					to.setTheoryEse(bo.getTheEndSemMarksAwarded());
				}
			}
			
			
			// practical Reg Mark
			if(printTO.getPraEndSemMarksAwarded()!=null && !printTO.getPraEndSemMarksAwarded().isEmpty()){
				if(StringUtils.isAlpha(printTO.getPraEndSemMarksAwarded().trim())){
					if(bo.getPraEndSemMarksAwarded()!=null && !bo.getPraEndSemMarksAwarded().isEmpty() && !StringUtils.isAlpha(bo.getPraEndSemMarksAwarded().trim())){
						to.setPracticalEse(bo.getPraEndSemMarksAwarded());
					}else{
						to.setPracticalEse(printTO.getPraEndSemMarksAwarded());
					}
				}else{
					if(bo.getPraEndSemMarksAwarded()!=null && !bo.getPraEndSemMarksAwarded().isEmpty() && !StringUtils.isAlpha(bo.getPraEndSemMarksAwarded().trim())){
						if(Double.parseDouble(printTO.getPraEndSemMarksAwarded()) < Double.parseDouble(bo.getPraEndSemMarksAwarded()))
							to.setPracticalEse(bo.getPraEndSemMarksAwarded());
						else
							to.setPracticalEse(printTO.getPraEndSemMarksAwarded());
					}else{
						to.setPracticalEse(printTO.getPraEndSemMarksAwarded());
					}
				}
			}else{
				if(bo.getPraEndSemMarksAwarded()!=null && !bo.getPraEndSemMarksAwarded().isEmpty()){
					to.setPracticalEse(bo.getPraEndSemMarksAwarded());
				}
			}
			
			// Theory Att Mark
			if(printTO.getTheAttMarksAwarded()!=null && !printTO.getTheAttMarksAwarded().isEmpty()){
				if(StringUtils.isAlpha(printTO.getTheAttMarksAwarded().trim())){
					if(bo.getTheAttMarksAwarded()!=null && !bo.getTheAttMarksAwarded().isEmpty() && !StringUtils.isAlpha(bo.getTheAttMarksAwarded().trim())){
						to.setTheoryAtt(bo.getTheAttMarksAwarded());
					}else{
						to.setTheoryAtt(printTO.getTheAttMarksAwarded());
					}
				}else{
					if(bo.getTheAttMarksAwarded()!=null && !bo.getTheAttMarksAwarded().isEmpty() && !StringUtils.isAlpha(bo.getTheAttMarksAwarded().trim())){
						if(Double.parseDouble(printTO.getTheAttMarksAwarded()) < Double.parseDouble(bo.getTheAttMarksAwarded()))
							to.setTheoryAtt(bo.getTheAttMarksAwarded());
						else
							to.setTheoryAtt(printTO.getTheAttMarksAwarded());
					}else{
						to.setTheoryAtt(printTO.getTheAttMarksAwarded());
					}
				}
			}else{
				if(bo.getTheAttMarksAwarded()!=null && !bo.getTheAttMarksAwarded().isEmpty()){
					to.setTheoryAtt(bo.getTheAttMarksAwarded());
				}
			}
			// Practical Att Mark
			if(printTO.getPraAttMarksAwarded()!=null && !printTO.getPraAttMarksAwarded().isEmpty()){
				if(StringUtils.isAlpha(printTO.getPraAttMarksAwarded().trim())){
					if(bo.getPraAttMarksAwarded()!=null && !bo.getPraAttMarksAwarded().isEmpty() && !StringUtils.isAlpha(bo.getPraAttMarksAwarded().trim())){
						to.setPracticalAtt(bo.getPraAttMarksAwarded());
					}else{
						to.setPracticalAtt(printTO.getPraAttMarksAwarded());
					}
				}else{
					if(bo.getPraAttMarksAwarded()!=null && !bo.getPraAttMarksAwarded().isEmpty() && !StringUtils.isAlpha(bo.getPraAttMarksAwarded().trim())){
						if(Double.parseDouble(printTO.getPraAttMarksAwarded()) < Double.parseDouble(bo.getPraAttMarksAwarded()))
							to.setPracticalAtt(bo.getPraAttMarksAwarded());
						else
							to.setPracticalAtt(printTO.getPraAttMarksAwarded());
					}else{
						to.setPracticalAtt(printTO.getPraAttMarksAwarded());
					}
				}
			}else{
				if(bo.getPraAttMarksAwarded()!=null && !bo.getPraAttMarksAwarded().isEmpty()){
					to.setPracticalAtt(bo.getPraAttMarksAwarded());
				}
			}
			}else{
				
				// If it is supplementary then we have to get latest availablity data
				// Theory Int Marks
				if(bo.getTheCiaMarksAwarded()==null || bo.getTheCiaMarksAwarded().isEmpty()){
					to.setTheoryCia(printTO.getTheCiaMarksAwarded());
				}else
					to.setTheoryCia(bo.getTheCiaMarksAwarded());
				
				
				// Practical Int Mark
				if(bo.getPraCiaMarksAwarded()==null || bo.getPraCiaMarksAwarded().isEmpty()){
					to.setPracticalCia(printTO.getPraCiaMarksAwarded());
				}else{
					to.setPracticalCia(bo.getPraCiaMarksAwarded());
				}
				
				// Theory Reg Mark
				if(bo.getTheEndSemMarksAwarded()==null || bo.getTheEndSemMarksAwarded().isEmpty()){
					to.setTheoryEse(printTO.getTheEndSemMarksAwarded());
				}else{
					to.setTheoryEse(bo.getTheEndSemMarksAwarded());
				}
				
				
				// practical Reg Mark
				if(bo.getPraEndSemMarksAwarded()==null || bo.getPraEndSemMarksAwarded().isEmpty()){
					to.setPracticalEse(printTO.getPraEndSemMarksAwarded());
				}else{
					to.setPracticalEse(bo.getPraEndSemMarksAwarded());
				}
				
				// Theory Att Mark
				if(bo.getTheAttMarksAwarded()==null || bo.getTheAttMarksAwarded().isEmpty()){
					to.setTheoryAtt(printTO.getTheAttMarksAwarded());
				}else{
					to.setTheoryAtt(bo.getTheAttMarksAwarded());
				}
				// Practical Att Mark
				if(bo.getPraAttMarksAwarded()==null || bo.getPraAttMarksAwarded().isEmpty()){
					to.setPracticalAtt(printTO.getPraAttMarksAwarded());
				}else{
					to.setPracticalAtt(bo.getPraAttMarksAwarded());
				}
			}
		}
		return to;
	}
	
	
	/**
	 * @param boMap
	 * @param transcriptPrintForm
	 * @param isPGMarksCard
	 * @return
	 */
	public Map<Integer, List<StudentTranscriptPrintTO>> getTranscript(Map<Integer, Map<String, StudentTranscriptPrintTO>> boMap, StudentTranscriptPrintForm transcriptPrintForm, boolean isPGMarksCard) 
	{
		Map<Integer, List<StudentTranscriptPrintTO>> termStudentMap = new HashMap<Integer, List<StudentTranscriptPrintTO>>();
		
		try {
			if(boMap!=null && !boMap.isEmpty()){
				
				for (Entry<Integer, Map<String, StudentTranscriptPrintTO>> boMap1 : boMap.entrySet()) {
					for (Entry<String, StudentTranscriptPrintTO> subMap : boMap1.getValue().entrySet()) {
						
						String[] studentTermNumber=subMap.getKey().split("_");
						if(termStudentMap.containsKey(Integer.parseInt(studentTermNumber[0]))){
							List<StudentTranscriptPrintTO> transcriptPrintTOList=termStudentMap.get(Integer.parseInt(studentTermNumber[0]));
							transcriptPrintTOList.add(subMap.getValue());
							/*Collections.sort(transcriptPrintTOList, new Comparator<StudentTranscriptPrintTO>() {
								 public int compare(StudentTranscriptPrintTO o1, StudentTranscriptPrintTO o2) {
							            return o2.compareTo(o1);
							        }
						    });*/
							Collections.sort(transcriptPrintTOList);
						    termStudentMap.put(Integer.parseInt(studentTermNumber[0]), transcriptPrintTOList);
						}else{
							List<StudentTranscriptPrintTO> studentTranscriptPrintToList=new ArrayList<StudentTranscriptPrintTO>();
							studentTranscriptPrintToList.add(subMap.getValue());
							termStudentMap.put(Integer.parseInt(studentTermNumber[0]), studentTranscriptPrintToList);
						}
						
					}
				}
				
				if(termStudentMap!=null){
					for (Entry<Integer, List<StudentTranscriptPrintTO>> termStudentMapSub : termStudentMap.entrySet()) {
						List<StudentTranscriptPrintTO> studentTranscriptPrintToList1=new ArrayList<StudentTranscriptPrintTO>();
						int totMaxMarks = 0;
						int totMaxMarksAwarded = 0;
						double totCreditsAwarded = 0;
						double percentage = 0;
						double r1 = 0;
						double gradePointAvg = 0;
						Map<Integer, String> subMap = new HashMap<Integer, String>();
						DecimalFormat twoDForm = new DecimalFormat("#.##");
						transcriptPrintForm.setShowSuppl("NO");
						transcriptPrintForm.setResult(null);
						studentTranscriptPrintToList1 = termStudentMapSub.getValue();
						Iterator<StudentTranscriptPrintTO> printItr = studentTranscriptPrintToList1.iterator();
						while(printItr.hasNext()){
							StudentTranscriptPrintTO printTO = printItr.next();
							
							if(!subMap.containsValue(printTO.getSubjectName())){
								subMap.put(printTO.getSubjectOrder(), printTO.getSubjectName());
							}
							else{
								printTO.setSubjectName(null);
								printTO.setSubjectOrder(0);
							}
							
							if(printTO.getTheTotMaxMarks()!=null && !printTO.getTheTotMaxMarks().toString().equalsIgnoreCase("0.0")){
								if(!printTO.getDontAddInTotal())
									totMaxMarks = totMaxMarks + (printTO.getTheTotMaxMarks().intValue());
							}
							else if(printTO.getPraTotMaxMarks()!=null && !printTO.getPraTotMaxMarks().toString().equalsIgnoreCase("0.0")){
								if(!printTO.getDontAddInTotal())
									totMaxMarks = totMaxMarks + (printTO.getPraTotMaxMarks().intValue());
							}
							printTO.setTotalMaxMarks(totMaxMarks);
							
							if(printTO.getTheTotMarksAwarded() > 0){
								if(!printTO.getDontAddInTotal())
									totMaxMarksAwarded = (int) (totMaxMarksAwarded + printTO.getTheTotMarksAwarded());
							}
							else if(printTO.getPraTotMarksAwarded() > 0){
								if(!printTO.getDontAddInTotal())
									totMaxMarksAwarded = (int) (totMaxMarksAwarded + printTO.getPraTotMarksAwarded());
							}
							printTO.setTotalMaxMarksAwarded(totMaxMarksAwarded);
							printTO.setTotalMarksInWords(CommonUtil.numberToWord(totMaxMarksAwarded));
							
							if(printTO.getTotalMaxMarks() > 0 && printTO.getTotalMaxMarksAwarded() > 0){
								percentage = ((double)(printTO.getTotalMaxMarksAwarded())/(double)(printTO.getTotalMaxMarks()))*100;
								//percentage = (printTO.getTotalMaxMarksAwarded()*100)/printTO.getTotalMaxMarks();
								printTO.setPercentage(Math.round((float)percentage));
							}

							if(printTO.getShowSuppl() 
									|| (transcriptPrintForm.getShowSuppl()!=null && transcriptPrintForm.getShowSuppl().equalsIgnoreCase("YES"))){
								transcriptPrintForm.setShowSuppl("YES");
								printTO.setShowSupplDisplay(true);
							}
							
							if(((printTO.getGrade()!=null && printTO.getGrade().equalsIgnoreCase("F"))
									|| (transcriptPrintForm.getResult()!=null && transcriptPrintForm.getResult().equalsIgnoreCase("Fail")))
									&& !printTO.getDontConsiderFailureTotalResult()){
								transcriptPrintForm.setResult("Fail");
								printTO.setResult("Fail");
							}
							
							if(printTO.getResult()==null){
								String robj = UpdateExamStudentSGPAImpl.getInstance().getResultClass(printTO.getCourse().getId(), percentage, 0, transcriptPrintForm.getStuId());
								if(robj!=null){
									printTO.setResult(robj);
								}
							}
							
							if(printTO.getTheCredit() > 0){
								totCreditsAwarded = totCreditsAwarded + printTO.getTheCredit();
								if(printTO.getGradePoint()!=null)
									r1 = r1 + ((printTO.getGradePoint().doubleValue()) * printTO.getTheCredit());
							}
							else if(printTO.getPraCredit() > 0){
								totCreditsAwarded = totCreditsAwarded + printTO.getPraCredit();
								if(printTO.getGradePoint()!=null)
									r1 = r1 + ((printTO.getGradePoint().doubleValue()) * printTO.getPraCredit());
							}
							printTO.setTotalCreditsAwarded(twoDForm.format(totCreditsAwarded));
							
							if(r1 > 0){
								gradePointAvg = r1 / totCreditsAwarded;
							}
							printTO.setTotalGradePoint(twoDForm.format(gradePointAvg));
							
							/*if(printTO.getGradePoint()!=null){
								r1 = r1 + printTO.getGradePoint().doubleValue();
								gradePointAvg = (r1/count);
								DecimalFormat twoDForm = new DecimalFormat("#.##");
								printTO.setTotalGradePoint(String.valueOf(twoDForm.format(gradePointAvg)));
								count++;
							}*/
							
							if(printTO.getTheEndSemMaxMarks()!=null)
								printTO.setTheEndSemMaxMarks(printTO.getTheEndSemMaxMarks().setScale(0, BigDecimal.ROUND_DOWN));
							if(printTO.getPraEndSemMaxMarks()!=null)
								printTO.setPraEndSemMaxMarks(printTO.getPraEndSemMaxMarks().setScale(0, BigDecimal.ROUND_DOWN));
							if(printTO.getTheEndSemMinMarks()!=null)
								printTO.setTheEndSemMinMarks(printTO.getTheEndSemMinMarks().setScale(0, BigDecimal.ROUND_DOWN));
							if(printTO.getPraEndSemMinMarks()!=null)
								printTO.setPraEndSemMinMarks(printTO.getPraEndSemMinMarks().setScale(0, BigDecimal.ROUND_DOWN));
							if(printTO.getTheTotMaxMarks()!=null)
								printTO.setTheTotMaxMarks(printTO.getTheTotMaxMarks().setScale(0, BigDecimal.ROUND_DOWN));
							if(printTO.getPraTotMaxMarks()!=null)
								printTO.setPraTotMaxMarks(printTO.getPraTotMaxMarks().setScale(0, BigDecimal.ROUND_DOWN));
							if(printTO.getTheAttMarksAwarded()!=null){
								if(printTO.getTheAttMarksAwarded().equalsIgnoreCase("0") || printTO.getTheAttMarksAwarded().equalsIgnoreCase("0.0"))
									printTO.setTheAttMarksAwarded(null);
								else
									printTO.setTheAttMarksAwarded(printTO.getTheAttMarksAwarded().split("\\.")[0]);
							}
							if(printTO.getPraAttMarksAwarded()!=null){
								if(printTO.getPraAttMarksAwarded().equalsIgnoreCase("0") || printTO.getPraAttMarksAwarded().equalsIgnoreCase("0.0"))
									printTO.setPraAttMarksAwarded(null);
								else
									printTO.setPraAttMarksAwarded(printTO.getPraAttMarksAwarded().split("\\.")[0]);
							}
							if(printTO.getTheEndSemMarksAwarded()!=null){
								if(printTO.getTheEndSemMarksAwarded().equalsIgnoreCase("0"))
									printTO.setTheEndSemMarksAwarded(null);
								else
									printTO.setTheEndSemMarksAwarded(printTO.getTheEndSemMarksAwarded().split("\\.")[0]);
							}
							if(printTO.getPraEndSemMarksAwarded()!=null){
								if(printTO.getPraEndSemMarksAwarded().equalsIgnoreCase("0"))
									printTO.setPraEndSemMarksAwarded(null);
								else
									printTO.setPraEndSemMarksAwarded(printTO.getPraEndSemMarksAwarded().split("\\.")[0]);
							}
							if(printTO.getTheTotMarksAwarded()>0)
								printTO.setTheTotMarksAwardedDisplay((int)printTO.getTheTotMarksAwarded());
							if(printTO.getPraTotMarksAwarded()>0)
								printTO.setPraTotMarksAwardedDisplay((int)(printTO.getPraTotMarksAwarded()));
							
							if(isPGMarksCard){
								if(printTO.getTheCiaMaxMarks()!=null && printTO.getAttMaxMarks()!=null)
									printTO.setTheCiaMaxMarks(printTO.getTheCiaMaxMarks().setScale(0, BigDecimal.ROUND_DOWN));
								if(printTO.getPraCiaMaxMarks()!=null && printTO.getAttMaxMarks()!=null)
									printTO.setPraCiaMaxMarks(printTO.getPraCiaMaxMarks().setScale(0, BigDecimal.ROUND_DOWN));
								
								if(printTO.getTheCiaMarksAwarded()!=null && printTO.getTheAttMarksAwarded()!=null){
									printTO.setTheCiaMarksAwarded(printTO.getTheCiaMarksAwarded().split("\\.")[0]);
									printTO.setTheAttMarksAwarded(printTO.getTheAttMarksAwarded().split("\\.")[0]);
									printTO.setTheCiaMarksAwarded(String.valueOf(Integer.parseInt(printTO.getTheCiaMarksAwarded())+Integer.parseInt(printTO.getTheAttMarksAwarded())));
								}else if(printTO.getTheCiaMarksAwarded()!=null){
									printTO.setTheCiaMarksAwarded(printTO.getTheCiaMarksAwarded().split("\\.")[0]);
								}
								if(printTO.getPraCiaMarksAwarded()!=null && printTO.getPraAttMarksAwarded()!=null){
									printTO.setPraCiaMarksAwarded(printTO.getPraCiaMarksAwarded().split("\\.")[0]);
									printTO.setPraAttMarksAwarded(printTO.getPraAttMarksAwarded().split("\\.")[0]);
									printTO.setPraCiaMarksAwarded(String.valueOf(Integer.parseInt(printTO.getPraCiaMarksAwarded())+Integer.parseInt(printTO.getPraAttMarksAwarded())));
								}else if(printTO.getPraCiaMarksAwarded()!=null){
									printTO.setPraCiaMarksAwarded(printTO.getPraCiaMarksAwarded().split("\\.")[0]);
								}
							}else{
								if(printTO.getTheCiaMaxMarks()!=null)
									printTO.setTheCiaMaxMarks((printTO.getTheCiaMaxMarks().subtract(printTO.getAttMaxMarks())).setScale(0, BigDecimal.ROUND_DOWN));
								/*if(printTO.getPraCiaMaxMarks()!=null)
									printTO.setPraCiaMaxMarks((printTO.getPraCiaMaxMarks().subtract(printTO.getAttMaxMarks())).setScale(0, BigDecimal.ROUND_DOWN));*/
								if(printTO.getPraCiaMaxMarks()!=null){
									printTO.setPraCiaMaxMarks(printTO.getPraCiaMaxMarks().setScale(0, BigDecimal.ROUND_DOWN));
									if(printTO.getSubType().equalsIgnoreCase("practical"))
										printTO.setAttMaxMarks(null);
									
								}
								if(printTO.getAttMaxMarks()!=null)
									printTO.setAttMaxMarks(printTO.getAttMaxMarks().setScale(0, BigDecimal.ROUND_DOWN));
								if(printTO.getTheCiaMarksAwarded()!=null){
									if(printTO.getTheCiaMarksAwarded().equalsIgnoreCase("0.0"))
										printTO.setTheCiaMarksAwarded(null);
									else
										printTO.setTheCiaMarksAwarded(printTO.getTheCiaMarksAwarded().split("\\.")[0]);
								}
								if(printTO.getPraCiaMarksAwarded()!=null){
									if(printTO.getPraCiaMarksAwarded().equalsIgnoreCase("0.0"))
										printTO.setPraCiaMarksAwarded(null);
									else
										printTO.setPraCiaMarksAwarded(printTO.getPraCiaMarksAwarded().split("\\.")[0]);
								}
							}
							
							
						}
						
					}
				}
				
			}
			
			
		} catch (Exception e) {
			e.getMessage();
		}
		return termStudentMap;
	}
	
	
	
}
