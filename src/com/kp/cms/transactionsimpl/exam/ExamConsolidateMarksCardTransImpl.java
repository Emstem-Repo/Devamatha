package com.kp.cms.transactionsimpl.exam;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.exam.UGConsolidateMarksCardBO;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.transactions.exam.IExamConsolidateMarksCardTransaction;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;

public class ExamConsolidateMarksCardTransImpl implements
		IExamConsolidateMarksCardTransaction {
	private static final Log log = LogFactory
			.getLog(ExamConsolidateMarksCardTransImpl.class);

	public List<Object[]> getConsolidateMarksCardDetails(int courseId, int year)
			throws Exception {

		log.info("Entering into getConsolidateMarksCardDetails of ExamConsolidateMarksCardTransImpl");
		Session session = null;
		Transaction transaction = null;
		List<Object[]> ugConsolidateMarksCardBOsArray;
		try {
			// session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			
			String sqlQuery = "   (SELECT EXAM_student_overall_internal_mark_details.exam_id, "+
			"   EXAM_student_overall_internal_mark_details.student_id,    "+
			"   EXAM_student_overall_internal_mark_details.class_id,    "+
			"   EXAM_student_overall_internal_mark_details.subject_id,    "+
			"   subject.code,subject.name as subject,    "+
			"   if(max(cast(EXAM_internal_mark_supplementary_details.theory_total_sub_internal_mark as unsigned)) is not null,max(cast(EXAM_internal_mark_supplementary_details.theory_total_sub_internal_mark as unsigned)),max(cast(EXAM_student_overall_internal_mark_details.theory_total_sub_internal_mark as unsigned))) as theory_total_sub_internal_mark,    "+
			"   if(max(cast(EXAM_internal_mark_supplementary_details.theory_total_attendance_mark as unsigned)) is not null,max(cast(EXAM_internal_mark_supplementary_details.theory_total_attendance_mark as unsigned)),max(cast(EXAM_student_overall_internal_mark_details.theory_total_attendance_mark as unsigned))) as theory_total_attendance_mark, "+
			"   if(max(cast(EXAM_internal_mark_supplementary_details.practical_total_sub_internal_mark as unsigned)) is not null,max(cast(EXAM_internal_mark_supplementary_details.practical_total_sub_internal_mark as unsigned)),max(cast(EXAM_student_overall_internal_mark_details.practical_total_sub_internal_mark as unsigned))) as practical_total_sub_internal_mark,    "+
			"   if(max(cast(EXAM_internal_mark_supplementary_details.practical_total_attendance_mark as unsigned)) is not null,max(cast(EXAM_internal_mark_supplementary_details.practical_total_attendance_mark as unsigned)),max(cast(EXAM_student_overall_internal_mark_details.practical_total_attendance_mark as unsigned))) as practical_total_attendance_mark,    "+
			"   CAST(classes.course_id AS UNSIGNED INTEGER) AS course_id,    "+
			"   EXAM_subject_rule_settings.theory_ese_minimum_mark,    "+
			"   EXAM_subject_rule_settings.theory_ese_maximum_mark,    "+
			"   EXAM_subject_rule_settings.practical_ese_minimum_mark,    "+
			"   EXAM_subject_rule_settings.practical_ese_maximum_mark,    "+
			"   (if(max(cast(EXAM_student_final_mark_details.student_theory_marks as unsigned)) is not null,max(cast(EXAM_student_final_mark_details.student_theory_marks as unsigned)),0)) as student_theory_marks,    "+
			"   (if(max(cast(EXAM_student_final_mark_details.student_practical_marks as unsigned)) is not null,max(cast(EXAM_student_final_mark_details.student_practical_marks as unsigned)),0)) as student_practical_marks,    "+
			"   personal_data.first_name,    "+
			"   personal_data.middle_name,    "+
			"   personal_data.last_name, "+
			"   EXAM_subject_sections.name as section, "+
			"   EXAM_subject_sections.is_initialise,    "+
			"   EXAM_subject_sections.id as section_id,    "+
			"   EXAM_sub_definition_coursewise.subject_order,    "+
			"   EXAM_subject_rule_settings.final_practical_internal_maximum_mark,    "+
			"   EXAM_subject_rule_settings.final_theory_internal_maximum_mark, "+
			"   max(EXAM_sub_definition_coursewise.practical_credit) as practical_credit,    "+
			"   max(EXAM_sub_definition_coursewise.theory_credit) as theory_credit,    "+
			"   student.register_no,    "+
			"   if(subject.is_theory_practical='T','Theory',if(subject.is_theory_practical='P','Practical','Theory')) as subType,classes.term_number,    "+
			" if(adm_appln.selected_course_id!=18,(if(max(if(EXAM_internal_mark_supplementary_details.theory_total_sub_internal_mark is not null,EXAM_internal_mark_supplementary_details.theory_total_sub_internal_mark,EXAM_student_overall_internal_mark_details.theory_total_sub_internal_mark))>0,max(if(EXAM_internal_mark_supplementary_details.theory_total_sub_internal_mark is not null,EXAM_internal_mark_supplementary_details.theory_total_sub_internal_mark,EXAM_student_overall_internal_mark_details.theory_total_sub_internal_mark)),0)+if(max(if(EXAM_internal_mark_supplementary_details.theory_total_attendance_mark is not null,EXAM_internal_mark_supplementary_details.theory_total_attendance_mark,EXAM_student_overall_internal_mark_details.theory_total_attendance_mark)) > 0,max(if(EXAM_internal_mark_supplementary_details.theory_total_attendance_mark is not null,EXAM_internal_mark_supplementary_details.theory_total_attendance_mark,EXAM_student_overall_internal_mark_details.theory_total_attendance_mark)),0)+(if(max(cast(student_theory_marks as unsigned)) is null,0,max(cast(student_theory_marks as unsigned))))), "+
			" (ifnull((SELECT mark.theory_total_sub_internal_mark "+
			" FROM student s "+
			" INNER JOIN "+
			" EXAM_internal_mark_supplementary_details mark "+
			" ON (mark.student_id = s.id) "+
			" INNER JOIN "+
			" EXAM_definition ed "+
			" ON (mark.exam_id = ed.id) "+
			" INNER JOIN "+
			" EXAM_exam_course_scheme_details ecsd "+
			" ON (ecsd.exam_id = ed.id)" +
			" INNER JOIN" +
			" classes c" +
			" ON (c.id=mark.class_id) "+
			" where ecsd.course_id=18 and c.term_number=classes.term_number and s.id=student.id and mark.subject_id=subject.id and mark.theory_total_sub_internal_mark is not null "+
			" order by year desc,month desc limit 1), "+
			" ifnull((SELECT mark.theory_total_sub_internal_mark "+
			" FROM student s "+
			" INNER JOIN "+
			" EXAM_student_overall_internal_mark_details mark "+
			" ON (mark.student_id = s.id) "+
			" INNER JOIN "+
			" EXAM_definition ed "+
			" ON (mark.exam_id = ed.id) "+
			" INNER JOIN "+
			" EXAM_exam_course_scheme_details ecsd "+
			" ON (ecsd.exam_id = ed.id)" +
			" INNER JOIN" +
			" classes c" +
			" ON (c.id=mark.class_id) "+
			" where ecsd.course_id=18 and c.term_number=classes.term_number and s.id=student.id and mark.subject_id=subject.id and mark.theory_total_sub_internal_mark is not null "+
			" order by year desc,month desc limit 1), 0))+ "+
			" ifnull((SELECT mark.student_theory_marks "+
			" FROM student s "+
			" INNER JOIN "+
			" EXAM_student_final_mark_details mark "+
			" ON (mark.student_id = s.id) "+
			" INNER JOIN "+
			" EXAM_definition ed "+
			" ON (mark.exam_id = ed.id) "+
			" INNER JOIN "+
			" EXAM_exam_course_scheme_details ecsd "+
			" ON (ecsd.exam_id = ed.id)" +
			" INNER JOIN" +
			" classes c" +
			" ON (c.id=mark.class_id) "+
			" where ecsd.course_id=18 and c.term_number=classes.term_number and s.id=student.id and mark.subject_id=subject.id and mark.student_theory_marks is not null "+
			" order by year desc,month desc limit 1),0))) as theoryObtain, "+
			" if(adm_appln.selected_course_id!=18,(if(max(if(EXAM_internal_mark_supplementary_details.practical_total_sub_internal_mark is not null,EXAM_internal_mark_supplementary_details.practical_total_sub_internal_mark,EXAM_student_overall_internal_mark_details.practical_total_sub_internal_mark)) > 0,max(if(EXAM_internal_mark_supplementary_details.practical_total_sub_internal_mark is not null,EXAM_internal_mark_supplementary_details.practical_total_sub_internal_mark,EXAM_student_overall_internal_mark_details.practical_total_sub_internal_mark)),0)+if(max(if(EXAM_internal_mark_supplementary_details.practical_total_attendance_mark is not null,EXAM_internal_mark_supplementary_details.practical_total_attendance_mark,EXAM_student_overall_internal_mark_details.practical_total_attendance_mark)) > 0,max(if(EXAM_internal_mark_supplementary_details.practical_total_attendance_mark is not null,EXAM_internal_mark_supplementary_details.practical_total_attendance_mark,EXAM_student_overall_internal_mark_details.practical_total_attendance_mark)),0)+if(max(cast(student_practical_marks as unsigned)) is null,0,max(cast(student_practical_marks as unsigned)))), "+
			" (ifnull((SELECT mark.practical_total_sub_internal_mark "+
			" FROM student s "+
			" INNER JOIN "+
			" EXAM_internal_mark_supplementary_details mark "+
			" ON (mark.student_id = s.id) "+
			" INNER JOIN "+
			" EXAM_definition ed "+
			" ON (mark.exam_id = ed.id) "+
			" INNER JOIN "+
			" EXAM_exam_course_scheme_details ecsd "+
			" ON (ecsd.exam_id = ed.id)" +
			" INNER JOIN" +
			" classes c" +
			" ON (c.id=mark.class_id) "+
			" where ecsd.course_id=18 and c.term_number=classes.term_number and s.id=student.id and mark.subject_id=subject.id and mark.practical_total_sub_internal_mark is not null "+
			" order by year desc,month desc limit 1), "+
			" ifnull((SELECT mark.practical_total_sub_internal_mark "+
			" FROM student s "+
			" INNER JOIN "+
			" EXAM_student_overall_internal_mark_details mark "+
			" ON (mark.student_id = s.id) "+
			" INNER JOIN "+
			" EXAM_definition ed "+
			" ON (mark.exam_id = ed.id) "+
			" INNER JOIN "+
			" EXAM_exam_course_scheme_details ecsd "+
			" ON (ecsd.exam_id = ed.id)" +
			" INNER JOIN" +
			" classes c" +
			" ON (c.id=mark.class_id) "+
			" where ecsd.course_id=18 and c.term_number=classes.term_number and s.id=student.id and mark.subject_id=subject.id and mark.practical_total_sub_internal_mark is not null "+
			" order by year desc,month desc limit 1), 0))+ "+
			" ifnull((SELECT mark.student_practical_marks "+
			" FROM student s "+
			" INNER JOIN "+
			" EXAM_student_final_mark_details mark "+
			" ON (mark.student_id = s.id) "+
			" INNER JOIN "+
			" EXAM_definition ed "+
			" ON (mark.exam_id = ed.id) "+
			" INNER JOIN "+
			" EXAM_exam_course_scheme_details ecsd "+
			" ON (ecsd.exam_id = ed.id)" +
			" INNER JOIN" +
			" classes c" +
			" ON (c.id=mark.class_id) "+
			" where ecsd.course_id=18 and c.term_number=classes.term_number and s.id=student.id and mark.subject_id=subject.id and mark.student_practical_marks is not null "+
			" order by year desc,month desc limit 1),0))) as practicalObtain, "+
			"   (if(EXAM_subject_rule_settings.final_theory_internal_maximum_mark > 0,EXAM_subject_rule_settings.final_theory_internal_maximum_mark,0)+if(EXAM_subject_rule_settings.theory_ese_maximum_mark >0,EXAM_subject_rule_settings.theory_ese_maximum_mark,0)) as theoryMax,    "+
			"   (if(EXAM_subject_rule_settings.final_practical_internal_maximum_mark >0,EXAM_subject_rule_settings.final_practical_internal_maximum_mark,0)+if(EXAM_subject_rule_settings.practical_ese_maximum_mark >0,EXAM_subject_rule_settings.practical_ese_maximum_mark,0)) as practicalMax,    "+
			"   ((if(max(if(EXAM_internal_mark_supplementary_details.theory_total_sub_internal_mark is not null,EXAM_internal_mark_supplementary_details.theory_total_sub_internal_mark,EXAM_student_overall_internal_mark_details.theory_total_sub_internal_mark))>0,max(if(EXAM_internal_mark_supplementary_details.theory_total_sub_internal_mark is not null,EXAM_internal_mark_supplementary_details.theory_total_sub_internal_mark,EXAM_student_overall_internal_mark_details.theory_total_sub_internal_mark)),0)+if(max(if(EXAM_internal_mark_supplementary_details.theory_total_attendance_mark is not null,EXAM_internal_mark_supplementary_details.theory_total_attendance_mark,EXAM_student_overall_internal_mark_details.theory_total_attendance_mark)) > 0,max(if(EXAM_internal_mark_supplementary_details.theory_total_attendance_mark is not null,EXAM_internal_mark_supplementary_details.theory_total_attendance_mark,EXAM_student_overall_internal_mark_details.theory_total_attendance_mark)),0)+(if(max(student_theory_marks) is null,0,max(student_theory_marks))))/ (if(EXAM_subject_rule_settings.final_theory_internal_maximum_mark > 0,EXAM_subject_rule_settings.final_theory_internal_maximum_mark,0)+if(EXAM_subject_rule_settings.theory_ese_maximum_mark >0,EXAM_subject_rule_settings.theory_ese_maximum_mark,0))*100) as theoryPer,    "+
			"   ((if(max(if(EXAM_internal_mark_supplementary_details.practical_total_sub_internal_mark is not null,EXAM_internal_mark_supplementary_details.practical_total_sub_internal_mark,EXAM_student_overall_internal_mark_details.practical_total_sub_internal_mark)) > 0,max(if(EXAM_internal_mark_supplementary_details.practical_total_sub_internal_mark is not null,EXAM_internal_mark_supplementary_details.practical_total_sub_internal_mark,EXAM_student_overall_internal_mark_details.practical_total_sub_internal_mark)),0)+if(max(if(EXAM_internal_mark_supplementary_details.practical_total_attendance_mark is not null,EXAM_internal_mark_supplementary_details.practical_total_attendance_mark,EXAM_student_overall_internal_mark_details.practical_total_attendance_mark)) > 0,max(if(EXAM_internal_mark_supplementary_details.practical_total_attendance_mark is not null,EXAM_internal_mark_supplementary_details.practical_total_attendance_mark,EXAM_student_overall_internal_mark_details.practical_total_attendance_mark)),0)+if(max(student_practical_marks) is null,0,max(student_practical_marks)))/(if(EXAM_subject_rule_settings.final_practical_internal_maximum_mark >0,EXAM_subject_rule_settings.final_practical_internal_maximum_mark,0)+if(EXAM_subject_rule_settings.practical_ese_maximum_mark >0,EXAM_subject_rule_settings.practical_ese_maximum_mark,0))*100) as practicalper,    "+
			"   EXAM_sub_definition_coursewise.dont_show_max_marks,    "+
			"   EXAM_sub_definition_coursewise.dont_show_min_marks, "+
			"   EXAM_sub_definition_coursewise.show_only_grade, "+
			"   course_scheme.name,    "+
			"   EXAM_sub_definition_coursewise.dont_show_sub_type,     "+
			"   adm_appln.applied_year,     "+
			"   EXAM_definition.academic_year," +
			"	adm_appln.selected_course_id  "+
			"   FROM "+
			"   student student   "+
			"   INNER JOIN   "+
			"   adm_appln adm_appln   "+
			"   ON (student.adm_appln_id = adm_appln.id)    "+
			"   and (adm_appln.is_cancelled=0)   "+
			"   INNER JOIN personal_data personal_data   "+
			"   ON (adm_appln.personal_data_id = personal_data.id) "+
			"   INNER JOIN EXAM_student_overall_internal_mark_details EXAM_student_overall_internal_mark_details "+
			"   ON (EXAM_student_overall_internal_mark_details.student_id = student.id) "+
			"   INNER JOIN classes classes "+
			"   ON (EXAM_student_overall_internal_mark_details.class_id=classes.id)  "+
			"   and (classes.is_active=1) "+
			"    INNER JOIN course course "+
			"   ON (classes.course_id = course.id) "+
			"   and (course.is_active=1) "+
			"   INNER JOIN "+
			"   subject subject "+
			"   ON(EXAM_student_overall_internal_mark_details.subject_id = subject.id) "+
			"   LEFT JOIN "+
			"   EXAM_student_final_mark_details EXAM_student_final_mark_details "+
			"   ON (EXAM_student_final_mark_details.subject_id = subject.id) "+
			"   AND (EXAM_student_final_mark_details.student_id =EXAM_student_overall_internal_mark_details.student_id) "+
			"   AND (EXAM_student_final_mark_details.class_id =EXAM_student_overall_internal_mark_details.class_id) "+
			"   INNER JOIN "+
			"   EXAM_subject_rule_settings EXAM_subject_rule_settings "+
			"   ON (EXAM_subject_rule_settings.subject_id = subject.id) "+
			"   and (EXAM_subject_rule_settings.course_id = course.id) "+
			"   and (EXAM_subject_rule_settings.scheme_no = classes.term_number) "+
			"   LEFT OUTER JOIN "+
			"   EXAM_sub_definition_coursewise EXAM_sub_definition_coursewise "+
			"   ON (EXAM_sub_definition_coursewise.subject_id = "+
			"   subject.id) "+
			"   AND (EXAM_sub_definition_coursewise.course_id     "+
			"   = EXAM_subject_rule_settings.course_id)     "+
			"   AND (EXAM_sub_definition_coursewise.academic_year=     "+
			"   EXAM_subject_rule_settings.academic_year) "+
			"   LEFT OUTER JOIN     "+
			"   EXAM_subject_sections EXAM_subject_sections     "+
			"   ON (EXAM_sub_definition_coursewise.subject_section_id =    "+
			"   EXAM_subject_sections.id)   "+
			"   INNER JOIN     "+
			"   curriculum_scheme curriculum_scheme     "+
			"   ON (EXAM_subject_rule_settings.course_id =    "+
			"   curriculum_scheme.course_id)     "+
			"   INNER JOIN     "+
			"   curriculum_scheme_duration curriculum_scheme_duration     "+
			"   ON (curriculum_scheme_duration.curriculum_scheme_id=curriculum_scheme.id)    "+
			"   INNER JOIN     "+
			"   course_scheme course_scheme "+
			"   ON (curriculum_scheme.course_scheme_id = course_scheme.id)   "+
			"   LEFT JOIN "+
			"   EXAM_student_detention_rejoin_details   "+
			"   ON(EXAM_student_detention_rejoin_details.student_id=student.id)   "+
			"   AND(EXAM_student_detention_rejoin_details.class_schemewise_id=student.class_schemewise_id) "+
			"   AND (EXAM_student_detention_rejoin_details.scheme_no=classes.term_number)   "+
			"   LEFT JOIN EXAM_update_exclude_withheld EXAM_update_exclude_withheld   "+
			"   ON(EXAM_update_exclude_withheld.student_id=student.id)    "+
			"   and (EXAM_update_exclude_withheld.course_id=course.id)    "+
			"   and (EXAM_update_exclude_withheld.scheme_no=classes.term_number)   "+
			"   INNER JOIN    "+
			"   EXAM_definition EXAM_definition   "+
			"   ON (EXAM_update_exclude_withheld.exam_id=EXAM_definition.id)    "+
			"   OR (EXAM_student_overall_internal_mark_details.exam_id =EXAM_definition.id) "+
			"   OR (EXAM_student_final_mark_details.exam_id = EXAM_definition.id)   "+
			"   and (EXAM_subject_rule_settings.academic_year = EXAM_definition.academic_year)   "+
			"   AND(curriculum_scheme_duration.academic_year=EXAM_definition.academic_year)     "+
			"   and (EXAM_definition.del_is_active=1) "+
			"   LEFT JOIN "+
			"   EXAM_internal_mark_supplementary_details EXAM_internal_mark_supplementary_details "+
			"   ON (EXAM_internal_mark_supplementary_details.student_id=EXAM_student_overall_internal_mark_details.student_id) "+
			"   AND (EXAM_internal_mark_supplementary_details.subject_id=EXAM_student_overall_internal_mark_details.subject_id) "+
			"   AND (EXAM_internal_mark_supplementary_details.class_id=EXAM_student_overall_internal_mark_details.class_id) "+
			"   where adm_appln.selected_course_id = "+courseId+
			"   and adm_appln.applied_year= "+year+
			"   group by student.id,subject.id,classes.term_number) "+
			"   UNION ALL "+
			"   (SELECT EXAM_student_overall_internal_mark_details.exam_id, "+
			"   EXAM_student_overall_internal_mark_details.student_id, "+
			"   EXAM_student_overall_internal_mark_details.class_id, "+
			"   EXAM_student_overall_internal_mark_details.subject_id, "+
			"   subject.code,subject.name as subject, "+
			"   if(max(cast(EXAM_internal_mark_supplementary_details.theory_total_sub_internal_mark as unsigned)) is not null,max(cast(EXAM_internal_mark_supplementary_details.theory_total_sub_internal_mark as unsigned)),max(cast(EXAM_student_overall_internal_mark_details.theory_total_sub_internal_mark as unsigned))) as theory_total_sub_internal_mark,    "+
			"   if(max(cast(EXAM_internal_mark_supplementary_details.theory_total_attendance_mark as unsigned)) is not null,max(cast(EXAM_internal_mark_supplementary_details.theory_total_attendance_mark as unsigned)),max(cast(EXAM_student_overall_internal_mark_details.theory_total_attendance_mark as unsigned))) as theory_total_attendance_mark, "+
			"   if(max(cast(EXAM_internal_mark_supplementary_details.practical_total_sub_internal_mark as unsigned)) is not null,max(cast(EXAM_internal_mark_supplementary_details.practical_total_sub_internal_mark as unsigned)),max(cast(EXAM_student_overall_internal_mark_details.practical_total_sub_internal_mark as unsigned))) as practical_total_sub_internal_mark,    "+
			"   if(max(cast(EXAM_internal_mark_supplementary_details.practical_total_attendance_mark as unsigned)) is not null,max(cast(EXAM_internal_mark_supplementary_details.practical_total_attendance_mark as unsigned)),max(cast(EXAM_student_overall_internal_mark_details.practical_total_attendance_mark as unsigned))) as practical_total_attendance_mark,    "+
			"   CAST(classes.course_id AS UNSIGNED INTEGER) AS course_id,    "+
			"   EXAM_subject_rule_settings.theory_ese_minimum_mark, "+
			"   EXAM_subject_rule_settings.theory_ese_maximum_mark, "+
			"   EXAM_subject_rule_settings.practical_ese_minimum_mark, "+
			"   EXAM_subject_rule_settings.practical_ese_maximum_mark, "+
			"   (if(max(cast(EXAM_student_final_mark_details.student_theory_marks as unsigned)) is not null,max(cast(EXAM_student_final_mark_details.student_theory_marks as unsigned)),0)) as student_theory_marks, "+
			"   (if(max(cast(EXAM_student_final_mark_details.student_practical_marks as unsigned)) is not null,max(cast(EXAM_student_final_mark_details.student_practical_marks as unsigned)),0)) as student_practical_marks, "+
			"   personal_data.first_name, "+
			"   personal_data.middle_name, "+
			"   personal_data.last_name, "+
			"   EXAM_subject_sections.name as section, "+
			"   EXAM_subject_sections.is_initialise, "+
			"   EXAM_subject_sections.id as section_id, "+
			"   EXAM_sub_definition_coursewise.subject_order, "+
			"   EXAM_subject_rule_settings.final_practical_internal_maximum_mark, "+
			"   EXAM_subject_rule_settings.final_theory_internal_maximum_mark, "+
			"   max(EXAM_sub_definition_coursewise.practical_credit) as practical_credit, "+
			"   max(EXAM_sub_definition_coursewise.theory_credit) as theory_credit, "+
			"   student.register_no, "+
			"   if(subject.is_theory_practical='T','Theory',if(subject.is_theory_practical='P','Practical','Practical')) as subType,classes.term_number, "+
			" if(adm_appln.selected_course_id!=18,(if(max(if(EXAM_internal_mark_supplementary_details.theory_total_sub_internal_mark is not null,EXAM_internal_mark_supplementary_details.theory_total_sub_internal_mark,EXAM_student_overall_internal_mark_details.theory_total_sub_internal_mark))>0,max(if(EXAM_internal_mark_supplementary_details.theory_total_sub_internal_mark is not null,EXAM_internal_mark_supplementary_details.theory_total_sub_internal_mark,EXAM_student_overall_internal_mark_details.theory_total_sub_internal_mark)),0)+if(max(if(EXAM_internal_mark_supplementary_details.theory_total_attendance_mark is not null,EXAM_internal_mark_supplementary_details.theory_total_attendance_mark,EXAM_student_overall_internal_mark_details.theory_total_attendance_mark)) > 0,max(if(EXAM_internal_mark_supplementary_details.theory_total_attendance_mark is not null,EXAM_internal_mark_supplementary_details.theory_total_attendance_mark,EXAM_student_overall_internal_mark_details.theory_total_attendance_mark)),0)+(if(max(cast(student_theory_marks as unsigned)) is null,0,max(cast(student_theory_marks as unsigned))))), "+
			" (ifnull((SELECT mark.theory_total_sub_internal_mark "+
			" FROM student s "+
			" INNER JOIN "+
			" EXAM_internal_mark_supplementary_details mark "+
			" ON (mark.student_id = s.id) "+
			" INNER JOIN "+
			" EXAM_definition ed "+
			" ON (mark.exam_id = ed.id) "+
			" INNER JOIN "+
			" EXAM_exam_course_scheme_details ecsd "+
			" ON (ecsd.exam_id = ed.id)" +
			" INNER JOIN" +
			" classes c" +
			" ON (c.id=mark.class_id) "+
			" where ecsd.course_id=18 and c.term_number=classes.term_number and s.id=student.id and mark.subject_id=subject.id and mark.theory_total_sub_internal_mark is not null "+
			" order by year desc,month desc limit 1), "+
			" ifnull((SELECT mark.theory_total_sub_internal_mark "+
			" FROM student s "+
			" INNER JOIN "+
			" EXAM_student_overall_internal_mark_details mark "+
			" ON (mark.student_id = s.id) "+
			" INNER JOIN "+
			" EXAM_definition ed "+
			" ON (mark.exam_id = ed.id) "+
			" INNER JOIN "+
			" EXAM_exam_course_scheme_details ecsd "+
			" ON (ecsd.exam_id = ed.id)" +
			" INNER JOIN" +
			" classes c" +
			" ON (c.id=mark.class_id) "+
			" where ecsd.course_id=18 and c.term_number=classes.term_number and s.id=student.id and mark.subject_id=subject.id and mark.theory_total_sub_internal_mark is not null "+
			" order by year desc,month desc limit 1), 0))+ "+
			" ifnull((SELECT mark.student_theory_marks "+
			" FROM student s "+
			" INNER JOIN "+
			" EXAM_student_final_mark_details mark "+
			" ON (mark.student_id = s.id) "+
			" INNER JOIN "+
			" EXAM_definition ed "+
			" ON (mark.exam_id = ed.id) "+
			" INNER JOIN "+
			" EXAM_exam_course_scheme_details ecsd "+
			" ON (ecsd.exam_id = ed.id)" +
			" INNER JOIN" +
			" classes c" +
			" ON (c.id=mark.class_id) "+
			" where ecsd.course_id=18 and c.term_number=classes.term_number and s.id=student.id and mark.subject_id=subject.id and mark.student_theory_marks is not null "+
			" order by year desc,month desc limit 1),0))) as theoryObtain, "+
			" if(adm_appln.selected_course_id!=18,(if(max(if(EXAM_internal_mark_supplementary_details.practical_total_sub_internal_mark is not null,EXAM_internal_mark_supplementary_details.practical_total_sub_internal_mark,EXAM_student_overall_internal_mark_details.practical_total_sub_internal_mark)) > 0,max(if(EXAM_internal_mark_supplementary_details.practical_total_sub_internal_mark is not null,EXAM_internal_mark_supplementary_details.practical_total_sub_internal_mark,EXAM_student_overall_internal_mark_details.practical_total_sub_internal_mark)),0)+if(max(if(EXAM_internal_mark_supplementary_details.practical_total_attendance_mark is not null,EXAM_internal_mark_supplementary_details.practical_total_attendance_mark,EXAM_student_overall_internal_mark_details.practical_total_attendance_mark)) > 0,max(if(EXAM_internal_mark_supplementary_details.practical_total_attendance_mark is not null,EXAM_internal_mark_supplementary_details.practical_total_attendance_mark,EXAM_student_overall_internal_mark_details.practical_total_attendance_mark)),0)+if(max(cast(student_practical_marks as unsigned)) is null,0,max(cast(student_practical_marks as unsigned)))), "+
			" (ifnull((SELECT mark.practical_total_sub_internal_mark "+
			" FROM student s "+
			" INNER JOIN "+
			" EXAM_internal_mark_supplementary_details mark "+
			" ON (mark.student_id = s.id) "+
			" INNER JOIN "+
			" EXAM_definition ed "+
			" ON (mark.exam_id = ed.id) "+
			" INNER JOIN "+
			" EXAM_exam_course_scheme_details ecsd "+
			" ON (ecsd.exam_id = ed.id)" +
			" INNER JOIN" +
			" classes c" +
			" ON (c.id=mark.class_id) "+
			" where ecsd.course_id=18 and c.term_number=classes.term_number and s.id=student.id and mark.subject_id=subject.id and mark.practical_total_sub_internal_mark is not null "+
			" order by year desc,month desc limit 1), "+
			" ifnull((SELECT mark.practical_total_sub_internal_mark "+
			" FROM student s "+
			" INNER JOIN "+
			" EXAM_student_overall_internal_mark_details mark "+
			" ON (mark.student_id = s.id) "+
			" INNER JOIN "+
			" EXAM_definition ed "+
			" ON (mark.exam_id = ed.id) "+
			" INNER JOIN "+
			" EXAM_exam_course_scheme_details ecsd "+
			" ON (ecsd.exam_id = ed.id)" +
			" INNER JOIN" +
			" classes c" +
			" ON (c.id=mark.class_id) "+
			" where ecsd.course_id=18 and c.term_number=classes.term_number and s.id=student.id and mark.subject_id=subject.id and mark.practical_total_sub_internal_mark is not null "+
			" order by year desc,month desc limit 1), 0))+ "+
			" ifnull((SELECT mark.student_practical_marks "+
			" FROM student s "+
			" INNER JOIN "+
			" EXAM_student_final_mark_details mark "+
			" ON (mark.student_id = s.id) "+
			" INNER JOIN "+
			" EXAM_definition ed "+
			" ON (mark.exam_id = ed.id) "+
			" INNER JOIN "+
			" EXAM_exam_course_scheme_details ecsd "+
			" ON (ecsd.exam_id = ed.id)" +
			" INNER JOIN" +
			" classes c" +
			" ON (c.id=mark.class_id) "+
			" where ecsd.course_id=18 and c.term_number=classes.term_number and s.id=student.id and mark.subject_id=subject.id and mark.student_practical_marks is not null "+
			" order by year desc,month desc limit 1),0))) as practicalObtain, "+
			"   (if(EXAM_subject_rule_settings.final_theory_internal_maximum_mark > 0,EXAM_subject_rule_settings.final_theory_internal_maximum_mark,0)+if(EXAM_subject_rule_settings.theory_ese_maximum_mark >0,EXAM_subject_rule_settings.theory_ese_maximum_mark,0)) as theoryMax,    "+
			"   (if(EXAM_subject_rule_settings.final_practical_internal_maximum_mark >0,EXAM_subject_rule_settings.final_practical_internal_maximum_mark,0)+if(EXAM_subject_rule_settings.practical_ese_maximum_mark >0,EXAM_subject_rule_settings.practical_ese_maximum_mark,0)) as practicalMax,    "+
			"   ((if(max(if(EXAM_internal_mark_supplementary_details.theory_total_sub_internal_mark is not null,EXAM_internal_mark_supplementary_details.theory_total_sub_internal_mark,EXAM_student_overall_internal_mark_details.theory_total_sub_internal_mark))>0,max(if(EXAM_internal_mark_supplementary_details.theory_total_sub_internal_mark is not null,EXAM_internal_mark_supplementary_details.theory_total_sub_internal_mark,EXAM_student_overall_internal_mark_details.theory_total_sub_internal_mark)),0)+if(max(if(EXAM_internal_mark_supplementary_details.theory_total_attendance_mark is not null,EXAM_internal_mark_supplementary_details.theory_total_attendance_mark,EXAM_student_overall_internal_mark_details.theory_total_attendance_mark)) > 0,max(if(EXAM_internal_mark_supplementary_details.theory_total_attendance_mark is not null,EXAM_internal_mark_supplementary_details.theory_total_attendance_mark,EXAM_student_overall_internal_mark_details.theory_total_attendance_mark)),0)+(if(max(student_theory_marks) is null,0,max(student_theory_marks))))/ (if(EXAM_subject_rule_settings.final_theory_internal_maximum_mark > 0,EXAM_subject_rule_settings.final_theory_internal_maximum_mark,0)+if(EXAM_subject_rule_settings.theory_ese_maximum_mark >0,EXAM_subject_rule_settings.theory_ese_maximum_mark,0))*100) as theoryPer,    "+
			"   ((if(max(if(EXAM_internal_mark_supplementary_details.practical_total_sub_internal_mark is not null,EXAM_internal_mark_supplementary_details.practical_total_sub_internal_mark,EXAM_student_overall_internal_mark_details.practical_total_sub_internal_mark)) > 0,max(if(EXAM_internal_mark_supplementary_details.practical_total_sub_internal_mark is not null,EXAM_internal_mark_supplementary_details.practical_total_sub_internal_mark,EXAM_student_overall_internal_mark_details.practical_total_sub_internal_mark)),0)+if(max(if(EXAM_internal_mark_supplementary_details.practical_total_attendance_mark is not null,EXAM_internal_mark_supplementary_details.practical_total_attendance_mark,EXAM_student_overall_internal_mark_details.practical_total_attendance_mark)) > 0,max(if(EXAM_internal_mark_supplementary_details.practical_total_attendance_mark is not null,EXAM_internal_mark_supplementary_details.practical_total_attendance_mark,EXAM_student_overall_internal_mark_details.practical_total_attendance_mark)),0)+if(max(student_practical_marks) is null,0,max(student_practical_marks)))/(if(EXAM_subject_rule_settings.final_practical_internal_maximum_mark >0,EXAM_subject_rule_settings.final_practical_internal_maximum_mark,0)+if(EXAM_subject_rule_settings.practical_ese_maximum_mark >0,EXAM_subject_rule_settings.practical_ese_maximum_mark,0))*100) as practicalper, "+
			"   EXAM_sub_definition_coursewise.dont_show_max_marks,    "+
			"   EXAM_sub_definition_coursewise.dont_show_min_marks,    "+
			"   EXAM_sub_definition_coursewise.show_only_grade,    "+
			"   course_scheme.name,    "+
			"   EXAM_sub_definition_coursewise.dont_show_sub_type,     "+
			"   adm_appln.applied_year, "+
			"   EXAM_definition.academic_year," +
			"	adm_appln.selected_course_id "+
			"   FROM    "+
			"   student student   "+
			"   INNER JOIN "+
			"   adm_appln adm_appln "+
			"   ON (student.adm_appln_id = adm_appln.id)    "+
			"   and (adm_appln.is_cancelled=0) "+
			"   INNER JOIN personal_data personal_data "+
			"   ON (adm_appln.personal_data_id = personal_data.id)  "+
			"   INNER JOIN EXAM_student_overall_internal_mark_details EXAM_student_overall_internal_mark_details "+
			"   ON (EXAM_student_overall_internal_mark_details.student_id = student.id) "+
			"   INNER JOIN classes classes "+
			"   ON (EXAM_student_overall_internal_mark_details.class_id=classes.id) and (classes.is_active=1) "+
			"   INNER JOIN course course "+
			"   ON (classes.course_id = course.id) "+
			"   and (course.is_active=1) "+
			"   INNER JOIN "+
			"   subject subject "+
			"   ON(EXAM_student_overall_internal_mark_details.subject_id = subject.id) "+
			"   LEFT JOIN "+
			"   EXAM_student_final_mark_details EXAM_student_final_mark_details   "+
			"   ON (EXAM_student_final_mark_details.subject_id = subject.id) "+
			"   AND (EXAM_student_final_mark_details.student_id =EXAM_student_overall_internal_mark_details.student_id)   "+
			"   AND (EXAM_student_final_mark_details.class_id =EXAM_student_overall_internal_mark_details.class_id) "+
			"   INNER JOIN   "+
			"   EXAM_subject_rule_settings EXAM_subject_rule_settings   "+
			"   ON (EXAM_subject_rule_settings.subject_id = subject.id)   "+
			"   and (EXAM_subject_rule_settings.course_id = course.id)   "+
			"   and (EXAM_subject_rule_settings.scheme_no = classes.term_number)   "+
			"   LEFT OUTER JOIN     "+
			"   EXAM_sub_definition_coursewise EXAM_sub_definition_coursewise     "+
			"   ON (EXAM_sub_definition_coursewise.subject_id =    "+
			"   subject.id)     "+
			"   AND (EXAM_sub_definition_coursewise.course_id     "+
			"   = EXAM_subject_rule_settings.course_id)     "+
			"   AND (EXAM_sub_definition_coursewise.academic_year= "+
			"   EXAM_subject_rule_settings.academic_year) "+
			"   LEFT OUTER JOIN "+
			"   EXAM_subject_sections EXAM_subject_sections     "+
			"   ON (EXAM_sub_definition_coursewise.subject_section_id =    "+
			"   EXAM_subject_sections.id) "+
			"   INNER JOIN "+
			"   curriculum_scheme curriculum_scheme     "+
			"   ON (EXAM_subject_rule_settings.course_id =    "+
			"   curriculum_scheme.course_id)     "+
			"   INNER JOIN     "+
			"   curriculum_scheme_duration curriculum_scheme_duration     "+
			"   ON (curriculum_scheme_duration.curriculum_scheme_id=curriculum_scheme.id)    "+
			"   INNER JOIN     "+
			"   course_scheme course_scheme     "+
			"   ON (curriculum_scheme.course_scheme_id = course_scheme.id)   "+
			"   LEFT JOIN   "+
			"   EXAM_student_detention_rejoin_details   "+
			"   ON(EXAM_student_detention_rejoin_details.student_id=student.id)   "+
			"   AND(EXAM_student_detention_rejoin_details.class_schemewise_id=student.class_schemewise_id) "+
			"   AND (EXAM_student_detention_rejoin_details.scheme_no=classes.term_number) "+
			"   LEFT JOIN EXAM_update_exclude_withheld EXAM_update_exclude_withheld "+
			"   ON(EXAM_update_exclude_withheld.student_id=student.id)    "+
			"   and (EXAM_update_exclude_withheld.course_id=course.id)    "+
			"   and (EXAM_update_exclude_withheld.scheme_no=classes.term_number) "+
			"   INNER JOIN   "+
			"   EXAM_definition EXAM_definition   "+
			"   ON (EXAM_update_exclude_withheld.exam_id=EXAM_definition.id)    "+
			"   OR (EXAM_student_overall_internal_mark_details.exam_id =EXAM_definition.id)    "+
			"   OR (EXAM_student_final_mark_details.exam_id = EXAM_definition.id)   "+
			"   and (EXAM_subject_rule_settings.academic_year = EXAM_definition.academic_year)   "+
			"   AND(curriculum_scheme_duration.academic_year=EXAM_definition.academic_year)     "+
			"   and (EXAM_definition.del_is_active=1)   "+
			"   LEFT JOIN     "+
			"   EXAM_internal_mark_supplementary_details EXAM_internal_mark_supplementary_details     "+
			"   ON (EXAM_internal_mark_supplementary_details.student_id=EXAM_student_overall_internal_mark_details.student_id)     "+
			"   AND (EXAM_internal_mark_supplementary_details.subject_id=EXAM_student_overall_internal_mark_details.subject_id)     "+
			"   AND (EXAM_internal_mark_supplementary_details.class_id=EXAM_student_overall_internal_mark_details.class_id)     "+
			"   where subject.is_theory_practical='B' "+
			"   and adm_appln.selected_course_id = "+courseId+
			"   and adm_appln.applied_year= "+year+
			"   group by student.id,subject.id,classes.term_number) ";
			ugConsolidateMarksCardBOsArray = session.createSQLQuery(sqlQuery).list();
			if (ugConsolidateMarksCardBOsArray != null) {
				transaction = session.beginTransaction();
				session.createQuery(
						"delete from UGConsolidateMarksCardBO where selectedCourseId='"
								+ courseId + "' and appliedYear='" + year
								+ "'").executeUpdate();
				transaction.commit();
			}
		} catch (Exception e) {
			log
					.error("Error in getConsolidateMarksCardDetails of ExamConsolidateMarksCardTransImpl");
			if (transaction != null) {
				transaction.rollback();
			}
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				// session.close();
			}
		}
		log
				.info("Leaving into getConsolidateMarksCardDetails of ExamConsolidateMarksCardTransImpl");
		return ugConsolidateMarksCardBOsArray;
	}

	public boolean saveConsolidateMarksCardDetails(
			List<Object[]> ugConsolidateMarksCardBO) throws Exception {

		log
				.info("Entering into saveConsolidateMarksCardDetails of ExamConsolidateMarksCardTransImpl");
		Session session = null;
		Transaction transaction = null;
		UGConsolidateMarksCardBO ugConsolidateMarksCardBOs;
		try {
			 session = InitSessionFactory.getInstance().openSession();
//			session = HibernateUtil.getSession();
			int count=0;
			transaction = session.beginTransaction();
			Iterator<Object[]> iterator = ugConsolidateMarksCardBO.iterator();
			while (iterator.hasNext()) {
				Object[] objects = (Object[]) iterator.next();
				ugConsolidateMarksCardBOs = new UGConsolidateMarksCardBO();
				if (objects[0] != null)
					ugConsolidateMarksCardBOs.setExamId(Integer
							.parseInt(objects[0].toString()));
				if (objects[1] != null)
					ugConsolidateMarksCardBOs.setStudentId(Integer
							.parseInt(objects[1].toString()));
				if (objects[2] != null)
					ugConsolidateMarksCardBOs.setClassId(Integer
							.parseInt(objects[2].toString()));
				if (objects[3] != null)
					ugConsolidateMarksCardBOs.setSubjectId(Integer
							.parseInt(objects[3].toString()));
				if (objects[4] != null)
					ugConsolidateMarksCardBOs.setSubjectCode(objects[4]
							.toString());
				if (objects[5] != null)
					ugConsolidateMarksCardBOs.setSubjectName(objects[5]
							.toString());
				if (objects[6] != null)
					ugConsolidateMarksCardBOs
							.setTheoryTotalSubInternalMark(objects[6]
									.toString());
				if (objects[7] != null)
					ugConsolidateMarksCardBOs
							.setTheoryTotalAttendanceMark(objects[7].toString());
				if (objects[8] != null)
					ugConsolidateMarksCardBOs
							.setPracticalTotalSubInternalMark(objects[8]
									.toString());
				if (objects[9] != null)
					ugConsolidateMarksCardBOs
							.setPracticalTotalAttendanceMark(objects[9]
									.toString());
				if (objects[10] != null)
					ugConsolidateMarksCardBOs.setCourseId(new BigDecimal(
							objects[10].toString()));
				if (objects[11] != null)
					ugConsolidateMarksCardBOs
							.setTheoryeseMinimumMark(new BigDecimal(objects[11]
									.toString()));
				if (objects[12] != null)
					ugConsolidateMarksCardBOs
							.setTheoryeseMaximumMark(new BigDecimal(objects[12]
									.toString()));
				if (objects[13] != null)
					ugConsolidateMarksCardBOs
							.setPracticaleseMinimumMark(new BigDecimal(
									objects[13].toString()));
				if (objects[14] != null)
					ugConsolidateMarksCardBOs
							.setPracticaleseMaximumMark(new BigDecimal(
									objects[14].toString()));
				if (objects[15] != null)
					ugConsolidateMarksCardBOs.setStudentTheoryMark(objects[15]
							.toString());
				if (objects[16] != null)
					ugConsolidateMarksCardBOs
							.setStudentPracticalMark(objects[16].toString());
				if (objects[17] != null)
					ugConsolidateMarksCardBOs.setFirstName(objects[17]
							.toString());
				if (objects[18] != null)
					ugConsolidateMarksCardBOs.setMiddleName(objects[18]
							.toString());
				if (objects[19] != null)
					ugConsolidateMarksCardBOs.setLastName(objects[19]
							.toString());
				if (objects[20] != null)
					ugConsolidateMarksCardBOs
							.setSection(objects[20].toString());
				if (objects[21] != null)
					ugConsolidateMarksCardBOs.setIsInitialise(Boolean.valueOf(
							objects[21].toString()));
				if (objects[22] != null)
					ugConsolidateMarksCardBOs.setSectionId(Integer
							.parseInt(objects[22].toString()));
				if (objects[23] != null)
					ugConsolidateMarksCardBOs.setSubjectOrder(Integer
							.parseInt(objects[23].toString()));
				if (objects[24] != null)
					ugConsolidateMarksCardBOs
							.setFinalPracticalInternalMaximumMark(new BigDecimal(
									objects[24].toString()));
				if (objects[25] != null)
					ugConsolidateMarksCardBOs
							.setFinalTheoryInternalMaximumMark(new BigDecimal(
									objects[25].toString()));
				if (objects[26] != null)
					ugConsolidateMarksCardBOs.setPracticalCredit(Integer
							.parseInt(objects[26].toString()));
				if (objects[27] != null)
					ugConsolidateMarksCardBOs.setTheoryCredit(Integer
							.parseInt(objects[27].toString()));
				if (objects[28] != null)
					ugConsolidateMarksCardBOs.setRegNo(objects[28].toString());
				if (objects[29] != null)
					ugConsolidateMarksCardBOs
							.setSubType(objects[29].toString());
				if (objects[30] != null)
					ugConsolidateMarksCardBOs.setTermNumber(Integer
							.parseInt(objects[30].toString()));
				if (objects[31] != null)
					ugConsolidateMarksCardBOs.setTheoryObtain(new Double(
							objects[31].toString()));
				if (objects[32] != null)
					ugConsolidateMarksCardBOs.setPracticalObtain(new Double(
							objects[32].toString()));
				if (objects[33] != null)
					ugConsolidateMarksCardBOs.setTheoryMax(new BigDecimal(
							objects[33].toString()));
				if (objects[34] != null)
					ugConsolidateMarksCardBOs.setPracticalMax(new BigDecimal(
							objects[34].toString()));
				if (objects[35] != null)
					ugConsolidateMarksCardBOs.setTheoryPercentage(new Double(
							objects[35].toString()));
				if (objects[36] != null)
					ugConsolidateMarksCardBOs
							.setPracticalPercentage(new Double(objects[36]
									.toString()));
				if (objects[37] != null) {
					if (objects[37].toString().equalsIgnoreCase("1"))
						ugConsolidateMarksCardBOs.setDontShowMaxMarks(true);
					else
						ugConsolidateMarksCardBOs.setDontShowMaxMarks(false);
				}
				if (objects[38] != null) {
					if (objects[38].toString().equalsIgnoreCase("1"))
						ugConsolidateMarksCardBOs.setDontShowMinMarks(true);
					else
						ugConsolidateMarksCardBOs.setDontShowMinMarks(false);
				}
				if (objects[39] != null) {
					if (objects[39].toString().equalsIgnoreCase("1"))
						ugConsolidateMarksCardBOs.setShowOnlyGrade(true);
					else
						ugConsolidateMarksCardBOs.setShowOnlyGrade(false);
				}
				if (objects[40] != null)
					ugConsolidateMarksCardBOs.setName(objects[40].toString());
				if (objects[41] != null) {
					if (objects[41].toString().equalsIgnoreCase("1"))
						ugConsolidateMarksCardBOs.setDontShowSubType(true);
					else
						ugConsolidateMarksCardBOs.setDontShowSubType(false);
				}
				if (objects[42] != null)
					ugConsolidateMarksCardBOs.setAppliedYear(Integer
							.parseInt(objects[42].toString()));
				if (objects[43] != null)
					ugConsolidateMarksCardBOs.setAcademicYear(Integer
							.parseInt(objects[43].toString()));
				if (objects[44] != null)
					ugConsolidateMarksCardBOs.setSelectedCourseId(Integer
							.parseInt(objects[44].toString()));

//				System.out.println("from UGConsolidateMarksCardBO u where u.appliedYear="+ugConsolidateMarksCardBOs.getAppliedYear()
//						+" and u.courseId="+ugConsolidateMarksCardBOs.getCourseId()+" and u.termNumber="+ugConsolidateMarksCardBOs.getTermNumber()
//						+" and u.subjectId="+ugConsolidateMarksCardBOs.getSubjectId()+" and u.studentId="+ugConsolidateMarksCardBOs.getStudentId()+" and u.subType='"+ugConsolidateMarksCardBOs.getSubType()+"'");
//				UGConsolidateMarksCardBO oldBo=(UGConsolidateMarksCardBO) session.createQuery("from UGConsolidateMarksCardBO u where u.appliedYear="+ugConsolidateMarksCardBOs.getAppliedYear()
//						+" and u.courseId="+ugConsolidateMarksCardBOs.getCourseId()+" and u.termNumber="+ugConsolidateMarksCardBOs.getTermNumber()
//						+" and u.subjectId="+ugConsolidateMarksCardBOs.getSubjectId()+" and u.studentId="+ugConsolidateMarksCardBOs.getStudentId()+" and u.subType='"+ugConsolidateMarksCardBOs.getSubType()+"'").uniqueResult();
//				if(oldBo!=null)
//					ugConsolidateMarksCardBOs.setId(oldBo.getId());
				
				session.saveOrUpdate(ugConsolidateMarksCardBOs);
				if(++count % 20 == 0){
					session.flush();
					session.clear();
				}
			}
			transaction.commit();
			return true;
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			log
					.error("Exception occured while saveConsolidateMarksCard Details in IMPL :"
							+ e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
			log
					.info("Leaving into saveConsolidateMarksCardDetails of ExamConsolidateMarksCardTransImpl");
		}
	}
}
