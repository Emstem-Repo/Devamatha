package com.kp.cms.helpers.admin;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.kp.cms.bo.admin.Classes;
import com.kp.cms.bo.admin.Course;
import com.kp.cms.bo.admin.HonorsEntryBo;
import com.kp.cms.bo.admin.HonoursCourse;
import com.kp.cms.bo.admin.HonoursCourseApplication;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.Subject;
import com.kp.cms.bo.exam.ConsolidateMarksCard;
import com.kp.cms.bo.exam.ExamDefinition;
import com.kp.cms.bo.exam.InternalMarkSupplementaryDetails;
import com.kp.cms.forms.admin.HonoursCourseEntryForm;
import com.kp.cms.to.admin.HonoursCourseEntryTo;
import com.kp.cms.to.exam.ConsolidateMaxTo;
import com.kp.cms.transactions.exam.IDownloadHallTicketTransaction;
import com.kp.cms.transactions.exam.INewExamMarksEntryTransaction;
import com.kp.cms.transactionsimpl.exam.DownloadHallTicketTransactionImpl;
import com.kp.cms.transactionsimpl.exam.NewExamMarksEntryTransactionImpl;
import com.kp.cms.utilities.CommonUtil;

public class HonoursCourseEntryHelper {
	 public static volatile HonoursCourseEntryHelper courseEntryHelper = null;
	 public static HonoursCourseEntryHelper getInstance(){
		 if(courseEntryHelper == null){
			 courseEntryHelper = new HonoursCourseEntryHelper();
			 return courseEntryHelper;
		 }
		 return courseEntryHelper;
	 }
	public List<HonoursCourseEntryTo> convertBOListToTOList( List<HonoursCourse> honoursCourses) throws Exception{
		List<HonoursCourseEntryTo> list = new ArrayList<HonoursCourseEntryTo>();
		if(honoursCourses!=null && !honoursCourses.isEmpty()){
			Iterator<HonoursCourse> iterator = honoursCourses.iterator();
			while (iterator.hasNext()) {
				HonoursCourse honoursCourse = (HonoursCourse) iterator.next();
				HonoursCourseEntryTo to = new HonoursCourseEntryTo();
				to.setId(honoursCourse.getId());
				to.setHonoursCourse(honoursCourse.getHonoursCourse().getName());
				to.setEligibleCourse(honoursCourse.getEligibleCourse().getName());
				list.add(to);
			}
		}
		return list;
	}
	/**
	 * @param honoursCourseEntryForm
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	public HonoursCourse convertFormToTO( HonoursCourseEntryForm honoursCourseEntryForm, String mode) throws Exception{
		HonoursCourse honoursCourse = null;
		if(mode.equalsIgnoreCase("Add")){
			honoursCourse = new HonoursCourse();
			Course eligbleCourse = new Course();
			Course honourCourse = new Course();
			honourCourse.setId(Integer.parseInt(honoursCourseEntryForm.getHonoursCourseId()));
			honoursCourse.setHonoursCourse(honourCourse);
			eligbleCourse.setId(Integer.parseInt(honoursCourseEntryForm.getEligibleCourseId()));
			honoursCourse.setEligibleCourse(eligbleCourse);
			honoursCourse.setCreatedBy(honoursCourseEntryForm.getUserId());
			honoursCourse.setCreatedDate(new Date());
			honoursCourse.setIsActive(true);
		}else if(mode.equalsIgnoreCase("Edit")){
			honoursCourse = new HonoursCourse();
			honoursCourse.setId(honoursCourseEntryForm.getId());
			Course course = new Course();
			course.setId(Integer.parseInt(honoursCourseEntryForm.getHonoursCourseId()));
			honoursCourse.setHonoursCourse(course);
			course.setId(Integer.parseInt(honoursCourseEntryForm.getEligibleCourseId()));
			honoursCourse.setEligibleCourse(course);
			honoursCourse.setModifiedBy(honoursCourseEntryForm.getUserId());
			honoursCourse.setLastModifiedDate(new Date());
			honoursCourse.setIsActive(true);
		}
		return honoursCourse;
	}
	/**
	 * @param studentId
	 * @return
	 */
	public String getMarksQuery(int studentId) {
		String q="  SELECT EXAM_student_overall_internal_mark_details.exam_id as eid,  " +
		"         EXAM_student_overall_internal_mark_details.student_id as studID,  " +
		"         EXAM_student_overall_internal_mark_details.class_id as classID,  " +
		"         EXAM_student_overall_internal_mark_details.subject_id as subID,  " +
		"         subject.code as subCode,  " +
		"         subject.name as subName,  " +
		"         EXAM_student_overall_internal_mark_details.theory_total_sub_internal_mark,  " +
		"         EXAM_student_overall_internal_mark_details.theory_total_attendance_mark,  " +
		"         EXAM_student_overall_internal_mark_details.  " +
		"         practical_total_sub_internal_mark,  " +
		"         EXAM_student_overall_internal_mark_details.  " +
		"         practical_total_attendance_mark,  " +
		"         CAST(adm_appln.selected_course_id AS UNSIGNED INTEGER) AS course_id,  " +
		"         EXAM_subject_rule_settings.theory_ese_minimum_mark,  " +
		"         EXAM_subject_rule_settings.theory_ese_maximum_mark,  " +
		"         EXAM_subject_rule_settings.practical_ese_minimum_mark,  " +
		"         EXAM_subject_rule_settings.practical_ese_maximum_mark,  " +
		"         (if(EXAM_student_final_mark_details.student_theory_marks is not null,EXAM_student_final_mark_details.student_theory_marks,0)) as student_theory_marks,  " +
		"         (if(EXAM_student_final_mark_details.student_practical_marks is not null,EXAM_student_final_mark_details.student_practical_marks,0)) as student_practical_marks,  " +
		"         personal_data.first_name,  " +
		"         personal_data.middle_name,  " +
		"         personal_data.last_name,  " +
		"         ifnull(EXAM_subject_sections.name,' ') as secName,  " +
		"         EXAM_subject_sections.is_initialise,  " +
		"         EXAM_subject_sections.id as secID,  " +
		"         EXAM_sub_definition_coursewise.subject_order,  " +
		"         EXAM_subject_rule_settings.final_practical_internal_maximum_mark,  " +
		"         EXAM_subject_rule_settings.final_theory_internal_maximum_mark,  " +
		"  (if(EXAM_subject_rule_settings.final_theory_internal_maximum_mark > 0, EXAM_subject_rule_settings.final_theory_internal_maximum_mark,0)+if(EXAM_subject_rule_settings.theory_ese_maximum_mark > 0,EXAM_subject_rule_settings.theory_ese_maximum_mark,0)) as theoryTotal  " +
		"    ,(if(EXAM_subject_rule_settings.final_practical_internal_maximum_mark > 0,EXAM_subject_rule_settings.final_practical_internal_maximum_mark,0)+if(EXAM_subject_rule_settings.practical_ese_maximum_mark>0,EXAM_subject_rule_settings.practical_ese_maximum_mark,0)) as practicalTotal,  " +
		"    (if(EXAM_student_overall_internal_mark_details.theory_total_sub_internal_mark>0,EXAM_student_overall_internal_mark_details.theory_total_sub_internal_mark,0)+if(EXAM_student_overall_internal_mark_details.theory_total_attendance_mark > 0,EXAM_student_overall_internal_mark_details.theory_total_attendance_mark,0)+(if(student_theory_marks is null,0,student_theory_marks))) as theoryObtain,  " +
		"    (if(EXAM_student_overall_internal_mark_details.practical_total_sub_internal_mark > 0,EXAM_student_overall_internal_mark_details.practical_total_sub_internal_mark,0)+if(EXAM_student_overall_internal_mark_details.practical_total_attendance_mark > 0,EXAM_student_overall_internal_mark_details.practical_total_attendance_mark,0)+if(student_practical_marks is null,0,student_practical_marks )) as practicalObtain,  " +
		"         max(EXAM_sub_definition_coursewise.practical_credit) as practical_credit,  " +
		"         max(EXAM_sub_definition_coursewise.theory_credit) as theory_credit,  " +
		"   ifnull( (select oldreg.register_no " +
		"   from student_old_register_numbers oldreg " +
		"   where oldreg.student_id=student.id " +
		"   and oldreg.scheme_no=classes.term_number and oldreg.is_active=1)," +
		"   student.register_no) as register_no," +
		"         if(subject.is_theory_practical='T','Theory',if(subject.is_theory_practical='P','Practical','Theory')) as subType,  " +
		"          if((SELECT max(EXAM_sub_coursewise_attendance_marks.attendance_marks)  " +
		"    FROM EXAM_sub_coursewise_attendance_marks  " +
		"  where course_id=classes.course_id  " +
		"  and subject_id=subject.id) is not null,   " +
		"  (SELECT max(EXAM_sub_coursewise_attendance_marks.attendance_marks)  " +
		"    FROM EXAM_sub_coursewise_attendance_marks  " +
		"  where course_id=classes.course_id  " +
		"  and subject_id=subject.id),  " +
		"  (SELECT max(EXAM_attendance_marks.marks)  " +
		"    FROM EXAM_attendance_marks EXAM_attendance_marks  " +
		"    where course_id = classes.course_id))  AS maxAttMarks,  " +
		"    classes.term_number,  " +
		"         EXAM_publish_exam_results.publish_date,  " +
		"         adm_appln.course_id as admCourseID,  " +
		"           (  (if(EXAM_student_overall_internal_mark_details.theory_total_sub_internal_mark > 0,EXAM_student_overall_internal_mark_details.theory_total_sub_internal_mark,0)+if(EXAM_student_overall_internal_mark_details.theory_total_attendance_mark > 0,EXAM_student_overall_internal_mark_details.theory_total_attendance_mark,0)+if(student_theory_marks > 0,student_theory_marks,0))/     (if(EXAM_subject_rule_settings.final_theory_internal_maximum_mark > 0,EXAM_subject_rule_settings.final_theory_internal_maximum_mark,0)+if(EXAM_subject_rule_settings.theory_ese_maximum_mark >0,EXAM_subject_rule_settings.theory_ese_maximum_mark,0))*100) as theoryPer,  " +
		"  (  (if(EXAM_student_overall_internal_mark_details.practical_total_sub_internal_mark >0,EXAM_student_overall_internal_mark_details.practical_total_sub_internal_mark,0)+if(EXAM_student_overall_internal_mark_details.practical_total_attendance_mark >0,EXAM_student_overall_internal_mark_details.practical_total_attendance_mark,0)+if(student_practical_marks >0,student_practical_marks,0))/(if(EXAM_subject_rule_settings.final_practical_internal_maximum_mark >0,EXAM_subject_rule_settings.final_practical_internal_maximum_mark,0)+if(EXAM_subject_rule_settings.practical_ese_maximum_mark >0,EXAM_subject_rule_settings.practical_ese_maximum_mark,0))*100) as practicalper,  " +
		"   ifnull((SELECT EXAM_sub_coursewise_grade_defn.grade" +
		"  FROM EXAM_sub_coursewise_grade_defn EXAM_sub_coursewise_grade_defn where theoryPer between start_prcntg_grade and end_prcntg_grade and EXAM_sub_coursewise_grade_defn.course_id = classes.course_id and subject_id=subject.id), " +
		"   (ifnull( (SELECT EXAM_grade_class_definition_frombatch.grade " +
		"     FROM EXAM_grade_class_definition_frombatch EXAM_grade_class_definition_frombatch where theoryPer between EXAM_grade_class_definition_frombatch.start_percentage and EXAM_grade_class_definition_frombatch.end_percentage and EXAM_grade_class_definition_frombatch.course_id = classes.course_id and from_batch <= adm_appln.applied_year limit 1), " +
		" (SELECT  EXAM_grade_class_definition.grade   FROM EXAM_grade_class_definition EXAM_grade_class_definition where theoryPer between start_percentage and EXAM_grade_class_definition.end_percentage and EXAM_grade_class_definition.course_id = classes.course_id limit 1)))) as thegra, " +
		" ifnull( (SELECT  EXAM_sub_coursewise_grade_defn.grade " +
		" FROM EXAM_sub_coursewise_grade_defn EXAM_sub_coursewise_grade_defn where practicalper between start_prcntg_grade and end_prcntg_grade and EXAM_sub_coursewise_grade_defn.course_id = classes.course_id and subject_id=subject.id), " +
		" (ifnull( (SELECT EXAM_grade_class_definition_frombatch.grade " +
		"  FROM EXAM_grade_class_definition_frombatch EXAM_grade_class_definition_frombatch where practicalper between EXAM_grade_class_definition_frombatch.start_percentage and EXAM_grade_class_definition_frombatch.end_percentage and EXAM_grade_class_definition_frombatch.course_id = classes.course_id and from_batch <= adm_appln.applied_year limit 1), " +
		" (SELECT EXAM_grade_class_definition.grade " +
		" FROM EXAM_grade_class_definition EXAM_grade_class_definition where practicalper between start_percentage and EXAM_grade_class_definition.end_percentage and EXAM_grade_class_definition.course_id = classes.course_id limit 1)))) as pragra, " +
		" ifnull(  (SELECT   EXAM_sub_coursewise_grade_defn.grade_point " +
		" FROM EXAM_sub_coursewise_grade_defn EXAM_sub_coursewise_grade_defn where theoryPer between start_prcntg_grade and end_prcntg_grade and EXAM_sub_coursewise_grade_defn.course_id = classes.course_id and subject_id=subject.id), " +
		" (ifnull( (SELECT   EXAM_grade_class_definition_frombatch.grade_point" +
		" FROM EXAM_grade_class_definition_frombatch EXAM_grade_class_definition_frombatch where theoryPer between EXAM_grade_class_definition_frombatch.start_percentage and EXAM_grade_class_definition_frombatch.end_percentage and EXAM_grade_class_definition_frombatch.course_id = classes.course_id and from_batch <= adm_appln.applied_year limit 1), " +
		"  (SELECT   EXAM_grade_class_definition.grade_point " +
		"  FROM EXAM_grade_class_definition EXAM_grade_class_definition where theoryPer between start_percentage and EXAM_grade_class_definition.end_percentage and EXAM_grade_class_definition.course_id = classes.course_id limit 1)))) as thegrap, " +
		"  ifnull( (SELECT  EXAM_sub_coursewise_grade_defn.grade_point " +
		"  FROM EXAM_sub_coursewise_grade_defn EXAM_sub_coursewise_grade_defn where practicalper between start_prcntg_grade and end_prcntg_grade and EXAM_sub_coursewise_grade_defn.course_id = classes.course_id and subject_id=subject.id), " +
		" (ifnull(  (SELECT EXAM_grade_class_definition_frombatch.grade_point " +
		"  FROM EXAM_grade_class_definition_frombatch EXAM_grade_class_definition_frombatch where practicalper between EXAM_grade_class_definition_frombatch.start_percentage and EXAM_grade_class_definition_frombatch.end_percentage and EXAM_grade_class_definition_frombatch.course_id = classes.course_id and from_batch <= adm_appln.applied_year limit 1), " +
		" (SELECT  EXAM_grade_class_definition.grade_point " +
		"  FROM EXAM_grade_class_definition EXAM_grade_class_definition where practicalper between start_percentage and EXAM_grade_class_definition.end_percentage and EXAM_grade_class_definition.course_id = classes.course_id limit 1)))) as pragrap,  " +
		"    EXAM_sub_definition_coursewise.dont_show_max_marks,  " +
		"    EXAM_sub_definition_coursewise.dont_show_min_marks,  " +
		"    EXAM_sub_definition_coursewise.show_only_grade,  " +
		"    course_scheme.name as courseSchemeNo,  " +
		"    EXAM_sub_definition_coursewise.dont_show_sub_type, EXAM_sub_definition_coursewise.dont_add_tot_mark_cls_decln  ," +
		"	 EXAM_sub_definition_coursewise.dont_show_att_marks,subject.is_certificate_course, " +
		"    EXAM_sub_definition_coursewise.dont_consider_failure_total_result,curriculum_scheme_duration.academic_year  " +
		" from EXAM_student_overall_internal_mark_details "+
		" LEFT JOIN student on EXAM_student_overall_internal_mark_details.student_id = student.id "+
		" LEFT JOIN adm_appln on student.adm_appln_id = adm_appln.id "+
		" LEFT JOIN personal_data ON adm_appln.personal_data_id = personal_data.id "+
		" LEFT JOIN subject on EXAM_student_overall_internal_mark_details.subject_id = subject.id "+
		" LEFT JOIN subject_type ON subject.subject_type_id = subject_type.id "+
		" LEFT JOIN EXAM_definition ON EXAM_student_overall_internal_mark_details.exam_id = EXAM_definition.id "+
		" LEFT JOIN classes ON EXAM_student_overall_internal_mark_details.class_id = classes.id "+
		" LEFT JOIN class_schemewise ON class_schemewise.class_id = classes.id "+
		" LEFT JOIN curriculum_scheme_duration on class_schemewise.curriculum_scheme_duration_id = curriculum_scheme_duration.id "+
		" LEFT JOIN curriculum_scheme ON curriculum_scheme_duration.curriculum_scheme_id = curriculum_scheme.id "+
		" LEFT JOIN EXAM_student_final_mark_details ON EXAM_student_final_mark_details.class_id = classes.id "+
		" and EXAM_student_final_mark_details.exam_id = EXAM_definition.id "+
		" and EXAM_student_final_mark_details.student_id = student.id "+
		" and EXAM_student_final_mark_details.subject_id = subject.id "+
		" LEFT JOIN EXAM_subject_rule_settings ON EXAM_subject_rule_settings.subject_id = subject.id "+
		" and EXAM_subject_rule_settings.academic_year = curriculum_scheme_duration.academic_year "+
		" and EXAM_subject_rule_settings.course_id = classes.course_id "+
		" and EXAM_subject_rule_settings.scheme_no = classes.term_number "+
		" LEFT JOIN EXAM_publish_exam_results ON EXAM_publish_exam_results.class_id = classes.id "+
		" and EXAM_publish_exam_results.exam_id = EXAM_definition.id "+
		" LEFT JOIN EXAM_sub_definition_coursewise ON EXAM_sub_definition_coursewise.subject_id = subject.id "+
		" and EXAM_sub_definition_coursewise.course_id = classes.course_id "+
		" and EXAM_sub_definition_coursewise.scheme_no = classes.term_number "+
		" and EXAM_sub_definition_coursewise.academic_year = curriculum_scheme_duration.academic_year "+
		" LEFT JOIN EXAM_subject_sections on EXAM_sub_definition_coursewise.subject_section_id = EXAM_subject_sections.id "+
		" LEFT JOIN course_scheme ON curriculum_scheme.course_scheme_id = course_scheme.id "+
		"                    where student.id=  "+studentId +
		"                    group by student.id,EXAM_student_overall_internal_mark_details.exam_id,subject.id  " +
		"                      " +
		"                    UNION ALL  " +
		"                      " +
		"                    SELECT EXAM_student_overall_internal_mark_details.exam_id as eid,  " +
		"         EXAM_student_overall_internal_mark_details.student_id as studID,  " +
		"         EXAM_student_overall_internal_mark_details.class_id as classID,  " +
		"         EXAM_student_overall_internal_mark_details.subject_id as subID,  " +
		"         subject.code as subCode,  " +
		"         subject.name as subName,  " +
		"         EXAM_student_overall_internal_mark_details.theory_total_sub_internal_mark,  " +
		"         EXAM_student_overall_internal_mark_details.theory_total_attendance_mark,  " +
		"         EXAM_student_overall_internal_mark_details.  " +
		"         practical_total_sub_internal_mark,  " +
		"         EXAM_student_overall_internal_mark_details.  " +
		"         practical_total_attendance_mark,  " +
		"         CAST(adm_appln.selected_course_id AS UNSIGNED INTEGER) AS course_id,  " +
		"         EXAM_subject_rule_settings.theory_ese_minimum_mark,  " +
		"         EXAM_subject_rule_settings.theory_ese_maximum_mark,  " +
		"         EXAM_subject_rule_settings.practical_ese_minimum_mark,  " +
		"         EXAM_subject_rule_settings.practical_ese_maximum_mark,  " +
		"         (if(EXAM_student_final_mark_details.student_theory_marks is not null,EXAM_student_final_mark_details.student_theory_marks,0)) as student_theory_marks,  " +
		"         (if(EXAM_student_final_mark_details.student_practical_marks is not null,EXAM_student_final_mark_details.student_practical_marks,0)) as student_practical_marks,  " +
		"         personal_data.first_name,  " +
		"         personal_data.middle_name,  " +
		"         personal_data.last_name,  " +
		"         ifnull(EXAM_subject_sections.name,' ') as secName,  " +
		"         EXAM_subject_sections.is_initialise,  " +
		"         EXAM_subject_sections.id as secID,  " +
		"         EXAM_sub_definition_coursewise.subject_order,  " +
		"         EXAM_subject_rule_settings.final_practical_internal_maximum_mark,  " +
		"         EXAM_subject_rule_settings.final_theory_internal_maximum_mark,  " +
		"  (if(EXAM_subject_rule_settings.final_theory_internal_maximum_mark > 0, EXAM_subject_rule_settings.final_theory_internal_maximum_mark,0)+if(EXAM_subject_rule_settings.theory_ese_maximum_mark > 0,EXAM_subject_rule_settings.theory_ese_maximum_mark,0)) as theoryTotal  " +
		"    ,(if(EXAM_subject_rule_settings.final_practical_internal_maximum_mark > 0,EXAM_subject_rule_settings.final_practical_internal_maximum_mark,0)+if(EXAM_subject_rule_settings.practical_ese_maximum_mark>0,EXAM_subject_rule_settings.practical_ese_maximum_mark,0)) as practicalTotal, " +
		"    (if(EXAM_student_overall_internal_mark_details.theory_total_sub_internal_mark>0,EXAM_student_overall_internal_mark_details.theory_total_sub_internal_mark,0)+if(EXAM_student_overall_internal_mark_details.theory_total_attendance_mark > 0,EXAM_student_overall_internal_mark_details.theory_total_attendance_mark,0)+(if(student_theory_marks is null,0,student_theory_marks))) as theoryObtain,  " +
		"    (if(EXAM_student_overall_internal_mark_details.practical_total_sub_internal_mark > 0,EXAM_student_overall_internal_mark_details.practical_total_sub_internal_mark,0)+if(EXAM_student_overall_internal_mark_details.practical_total_attendance_mark > 0,EXAM_student_overall_internal_mark_details.practical_total_attendance_mark,0)+if(student_practical_marks is null,0,student_practical_marks )) as practicalObtain,  " +
		"         max(EXAM_sub_definition_coursewise.practical_credit) as practical_credit,  " +
		"         max(EXAM_sub_definition_coursewise.theory_credit) as theory_credit,  " +
		"        ifnull( (select oldreg.register_no " +
		"   from student_old_register_numbers oldreg " +
		"   where oldreg.student_id=student.id " +
		"   and oldreg.scheme_no=classes.term_number and oldreg.is_active=1)," +
		"   student.register_no ) as register_no," +
		"        if(subject.is_theory_practical='T','Theory',if(subject.is_theory_practical='P','Practical','Practical')) as subType,  " +
		"          if((SELECT max(EXAM_sub_coursewise_attendance_marks.attendance_marks)  " +
		"    FROM EXAM_sub_coursewise_attendance_marks  " +
		"  where course_id=classes.course_id  " +
		"  and subject_id=subject.id) is not null,   " +
		"  (SELECT max(EXAM_sub_coursewise_attendance_marks.attendance_marks)  " +
		"    FROM EXAM_sub_coursewise_attendance_marks  " +
		"  where course_id=classes.course_id  " +
		"  and subject_id=subject.id),  " +
		"  (SELECT max(EXAM_attendance_marks.marks)  " +
		"    FROM EXAM_attendance_marks EXAM_attendance_marks  " +
		"    where course_id = classes.course_id))  AS maxAttMarks,  " +
		"    classes.term_number,  " +
		"         EXAM_publish_exam_results.publish_date,  " +
		"         adm_appln.course_id as admCourseID,  " +
		"           (  (if(EXAM_student_overall_internal_mark_details.theory_total_sub_internal_mark > 0,EXAM_student_overall_internal_mark_details.theory_total_sub_internal_mark,0)+if(EXAM_student_overall_internal_mark_details.theory_total_attendance_mark > 0,EXAM_student_overall_internal_mark_details.theory_total_attendance_mark,0)+if(student_theory_marks > 0,student_theory_marks,0))/     (if(EXAM_subject_rule_settings.final_theory_internal_maximum_mark > 0,EXAM_subject_rule_settings.final_theory_internal_maximum_mark,0)+if(EXAM_subject_rule_settings.theory_ese_maximum_mark >0,EXAM_subject_rule_settings.theory_ese_maximum_mark,0))*100) as theoryPer,  " +
		"  (  (if(EXAM_student_overall_internal_mark_details.practical_total_sub_internal_mark >0,EXAM_student_overall_internal_mark_details.practical_total_sub_internal_mark,0)+if(EXAM_student_overall_internal_mark_details.practical_total_attendance_mark >0,EXAM_student_overall_internal_mark_details.practical_total_attendance_mark,0)+if(student_practical_marks >0,student_practical_marks,0))/(if(EXAM_subject_rule_settings.final_practical_internal_maximum_mark >0,EXAM_subject_rule_settings.final_practical_internal_maximum_mark,0)+if(EXAM_subject_rule_settings.practical_ese_maximum_mark >0,EXAM_subject_rule_settings.practical_ese_maximum_mark,0))*100) as practicalper,  " +
		"   ifnull((SELECT EXAM_sub_coursewise_grade_defn.grade" +
		"  FROM EXAM_sub_coursewise_grade_defn EXAM_sub_coursewise_grade_defn where theoryPer between start_prcntg_grade and end_prcntg_grade and EXAM_sub_coursewise_grade_defn.course_id = classes.course_id and subject_id=subject.id), " +
		"   (ifnull( (SELECT EXAM_grade_class_definition_frombatch.grade " +
		"     FROM EXAM_grade_class_definition_frombatch EXAM_grade_class_definition_frombatch where theoryPer between EXAM_grade_class_definition_frombatch.start_percentage and EXAM_grade_class_definition_frombatch.end_percentage and EXAM_grade_class_definition_frombatch.course_id = classes.course_id and from_batch <= adm_appln.applied_year limit 1), " +
		" (SELECT  EXAM_grade_class_definition.grade   FROM EXAM_grade_class_definition EXAM_grade_class_definition where theoryPer between start_percentage and EXAM_grade_class_definition.end_percentage and EXAM_grade_class_definition.course_id = classes.course_id limit 1)))) as thegra, " +
		" ifnull( (SELECT  EXAM_sub_coursewise_grade_defn.grade " +
		" FROM EXAM_sub_coursewise_grade_defn EXAM_sub_coursewise_grade_defn where practicalper between start_prcntg_grade and end_prcntg_grade and EXAM_sub_coursewise_grade_defn.course_id = classes.course_id and subject_id=subject.id), " +
		" (ifnull( (SELECT EXAM_grade_class_definition_frombatch.grade " +
		"  FROM EXAM_grade_class_definition_frombatch EXAM_grade_class_definition_frombatch where practicalper between EXAM_grade_class_definition_frombatch.start_percentage and EXAM_grade_class_definition_frombatch.end_percentage and EXAM_grade_class_definition_frombatch.course_id = classes.course_id and from_batch <= adm_appln.applied_year limit 1), " +
		" (SELECT EXAM_grade_class_definition.grade " +
		" FROM EXAM_grade_class_definition EXAM_grade_class_definition where practicalper between start_percentage and EXAM_grade_class_definition.end_percentage and EXAM_grade_class_definition.course_id = classes.course_id limit 1)))) as pragra, " +
		" ifnull(  (SELECT   EXAM_sub_coursewise_grade_defn.grade_point " +
		" FROM EXAM_sub_coursewise_grade_defn EXAM_sub_coursewise_grade_defn where theoryPer between start_prcntg_grade and end_prcntg_grade and EXAM_sub_coursewise_grade_defn.course_id = classes.course_id and subject_id=subject.id), " +
		" (ifnull( (SELECT   EXAM_grade_class_definition_frombatch.grade_point" +
		" FROM EXAM_grade_class_definition_frombatch EXAM_grade_class_definition_frombatch where theoryPer between EXAM_grade_class_definition_frombatch.start_percentage and EXAM_grade_class_definition_frombatch.end_percentage and EXAM_grade_class_definition_frombatch.course_id = classes.course_id and from_batch <= adm_appln.applied_year limit 1), " +
		"  (SELECT   EXAM_grade_class_definition.grade_point " +
		"  FROM EXAM_grade_class_definition EXAM_grade_class_definition where theoryPer between start_percentage and EXAM_grade_class_definition.end_percentage and EXAM_grade_class_definition.course_id = classes.course_id limit 1)))) as thegrap, " +
		"  ifnull( (SELECT  EXAM_sub_coursewise_grade_defn.grade_point " +
		"  FROM EXAM_sub_coursewise_grade_defn EXAM_sub_coursewise_grade_defn where practicalper between start_prcntg_grade and end_prcntg_grade and EXAM_sub_coursewise_grade_defn.course_id = classes.course_id and subject_id=subject.id), " +
		" (ifnull(  (SELECT EXAM_grade_class_definition_frombatch.grade_point " +
		"  FROM EXAM_grade_class_definition_frombatch EXAM_grade_class_definition_frombatch where practicalper between EXAM_grade_class_definition_frombatch.start_percentage and EXAM_grade_class_definition_frombatch.end_percentage and EXAM_grade_class_definition_frombatch.course_id = classes.course_id and from_batch <= adm_appln.applied_year limit 1), " +
		" (SELECT  EXAM_grade_class_definition.grade_point " +
		"  FROM EXAM_grade_class_definition EXAM_grade_class_definition where practicalper between start_percentage and EXAM_grade_class_definition.end_percentage and EXAM_grade_class_definition.course_id = classes.course_id limit 1)))) as pragrap,  " +
		"    EXAM_sub_definition_coursewise.dont_show_max_marks,  " +
		"    EXAM_sub_definition_coursewise.dont_show_min_marks,  " +
		"    EXAM_sub_definition_coursewise.show_only_grade,  " +
		"    course_scheme.name as courseSchemeNo,  " +
		"    EXAM_sub_definition_coursewise.dont_show_sub_type, EXAM_sub_definition_coursewise.dont_add_tot_mark_cls_decln," +
		"	 EXAM_sub_definition_coursewise.dont_show_att_marks,subject.is_certificate_course, " +
		"    EXAM_sub_definition_coursewise.dont_consider_failure_total_result,curriculum_scheme_duration.academic_year  " +
		" from EXAM_student_overall_internal_mark_details "+
		" LEFT JOIN student on EXAM_student_overall_internal_mark_details.student_id = student.id "+
		" LEFT JOIN adm_appln on student.adm_appln_id = adm_appln.id "+
		" LEFT JOIN personal_data ON adm_appln.personal_data_id = personal_data.id "+
		" LEFT JOIN subject on EXAM_student_overall_internal_mark_details.subject_id = subject.id "+
		" LEFT JOIN subject_type ON subject.subject_type_id = subject_type.id "+
		" LEFT JOIN EXAM_definition ON EXAM_student_overall_internal_mark_details.exam_id = EXAM_definition.id "+
		" LEFT JOIN classes ON EXAM_student_overall_internal_mark_details.class_id = classes.id "+
		" LEFT JOIN class_schemewise ON class_schemewise.class_id = classes.id "+
		" LEFT JOIN curriculum_scheme_duration on class_schemewise.curriculum_scheme_duration_id = curriculum_scheme_duration.id "+
		" LEFT JOIN curriculum_scheme ON curriculum_scheme_duration.curriculum_scheme_id = curriculum_scheme.id "+
		" LEFT JOIN EXAM_student_final_mark_details ON EXAM_student_final_mark_details.class_id = classes.id "+
		" and EXAM_student_final_mark_details.exam_id = EXAM_definition.id "+
		" and EXAM_student_final_mark_details.student_id = student.id "+
		" and EXAM_student_final_mark_details.subject_id = subject.id "+
		" LEFT JOIN EXAM_subject_rule_settings ON EXAM_subject_rule_settings.subject_id = subject.id "+
		" and EXAM_subject_rule_settings.academic_year = curriculum_scheme_duration.academic_year "+
		" and EXAM_subject_rule_settings.course_id = classes.course_id "+
		" and EXAM_subject_rule_settings.scheme_no = classes.term_number "+
		" LEFT JOIN EXAM_publish_exam_results ON EXAM_publish_exam_results.class_id = classes.id "+
		" and EXAM_publish_exam_results.exam_id = EXAM_definition.id "+
		" LEFT JOIN EXAM_sub_definition_coursewise ON EXAM_sub_definition_coursewise.subject_id = subject.id "+
		" and EXAM_sub_definition_coursewise.course_id = classes.course_id "+
		" and EXAM_sub_definition_coursewise.scheme_no = classes.term_number "+
		" and EXAM_sub_definition_coursewise.academic_year = curriculum_scheme_duration.academic_year "+
		" LEFT JOIN EXAM_subject_sections on EXAM_sub_definition_coursewise.subject_section_id = EXAM_subject_sections.id "+
		" LEFT JOIN course_scheme ON curriculum_scheme.course_scheme_id = course_scheme.id "+
		"                    where subject.is_theory_practical='B'   " +		             
		"                     and student.id = " +studentId+
		"    group by student.id,EXAM_student_overall_internal_mark_details.exam_id,subject.id  order by subject_order" ;

			
			
		return q;
	}
	/**
	 * @param marksList
	 * @param objForm
	 * @param honourSubjectIds
	 * @param attendanceMap 
	 * @param certificateMap 
	 * @return
	 */
	public Map<Integer, HonoursCourseEntryTo> getAcademicDetails(List<Object[]> marksList,HonoursCourseEntryForm objForm, List<Integer> honourSubjectIds, Map<Integer, String> attendanceMap, Map<Integer, Boolean> certificateMap) {
		Map<Integer, HonoursCourseEntryTo> map = new HashMap<Integer, HonoursCourseEntryTo>();
		
		int arrears=0;
		if(marksList != null && !marksList.isEmpty()){
			Iterator<Object[]> iterator = marksList.iterator();
			while (iterator.hasNext()) {
				Object[] obj = (Object[]) iterator.next();
				int totalMarksAwarded=0;
				int totalMaxMarks=0;
				boolean isAdd=true;
				String resultClass="Pass";
				boolean dontConsiderFail=false;
				boolean certificateCourse=false;
				boolean isOptional=false;
				HonoursCourseEntryTo to = null;
				if(obj[35] != null && obj[35].toString() != null){
					if(map.containsKey(Integer.parseInt(obj[35].toString()))){
						to = map.remove(Integer.parseInt(obj[35].toString()));
					}else{
						to = new HonoursCourseEntryTo();
					}
					if(obj[35].toString().equalsIgnoreCase("1")){
						to.setSemester("Semester I");
					}else if(obj[35].toString().equalsIgnoreCase("2")){
						to.setSemester("Semester II");
					}else{
						to.setSemester("Semester III");
					}
					if(obj[53] != null){
						to.setYear(obj[53].toString().substring(0, 4));
					}
					if(obj[51]!=null && obj[51].toString().equalsIgnoreCase("1")){
						certificateCourse=true;
						if(certificateMap.containsKey(Integer.parseInt(obj[3].toString()))){
							isOptional=certificateMap.get(Integer.parseInt(obj[3].toString()));
						}else{
							isOptional=true;
						}
					}
					if(obj[52]!=null && obj[52].toString().equalsIgnoreCase("1")){
						dontConsiderFail=true;
					}
					String attpercentage = attendanceMap.get(Integer.parseInt(obj[35].toString()));
					to.setAttPercentage(attpercentage);
					if(obj[49]!=null){
						if(Integer.parseInt(obj[49].toString())==1){
							isAdd=false;
						}
					}
					if(obj[33].toString().equalsIgnoreCase("Practical")){
						if(obj[29]!=null && CommonUtil.isValidDecimal(obj[29].toString())){
							if(isAdd){
								if(to.getTotalMarksAwarded() != null && !to.getTotalMarksAwarded().trim().isEmpty()){
									totalMarksAwarded = Integer.parseInt(to.getTotalMarksAwarded());
								}
								totalMarksAwarded=totalMarksAwarded+(int)Math.round(Double.parseDouble(obj[29].toString()));
								to.setTotalMarksAwarded(String.valueOf(totalMarksAwarded));
							}
						}
						if(obj[27]!=null){
							if(isAdd){
								if(to.getTotalMaxmarks() != null && !to.getTotalMaxmarks().trim().isEmpty()){
									totalMaxMarks = Integer.parseInt(to.getTotalMaxmarks());
								}
								totalMaxMarks=totalMaxMarks+(int)Math.round(Double.parseDouble(obj[27].toString()));
								to.setTotalMaxmarks(String.valueOf(totalMaxMarks));
							}
						}
						if(to.getTotalMaxmarks() != null && !to.getTotalMaxmarks().trim().isEmpty() &&
								to.getTotalMarksAwarded() != null && !to.getTotalMarksAwarded().trim().isEmpty()){
							double percentage = 0.0;
							percentage = Integer.parseInt(to.getTotalMarksAwarded())*100 ;
							percentage = percentage / Integer.parseInt(to.getTotalMaxmarks());
							to.setPercentage(String.valueOf(percentage));
						}
						if(honourSubjectIds != null && honourSubjectIds.contains(Integer.parseInt(obj[3].toString()))){
							int marksAwarded=0;
							int maxMarks=0;
							double percentage = 0.0;
							if(obj[29]!=null && CommonUtil.isValidDecimal(obj[29].toString())){
								if(to.getHonourSubjectMarksAwarded() != null && !to.getHonourSubjectMarksAwarded().trim().isEmpty()){
									marksAwarded = Integer.parseInt(to.getHonourSubjectMarksAwarded());
								}
								marksAwarded = marksAwarded + (int)Math.round(Double.parseDouble(obj[29].toString()));
								to.setHonourSubjectMarksAwarded(String.valueOf(marksAwarded));
							}
							if(obj[27]!=null){
								if(to.getHonourSubjectMaxmarks() != null && !to.getHonourSubjectMaxmarks().trim().isEmpty()){
									maxMarks = Integer.parseInt(to.getHonourSubjectMaxmarks());
								}
								maxMarks = maxMarks +(int)Math.round(Double.parseDouble(obj[27].toString()));
								to.setHonourSubjectMaxmarks(String.valueOf(maxMarks));
							}
							percentage = marksAwarded * 100;
							percentage = percentage / maxMarks;
							to.setHonourSubjectPercentage(String.valueOf(percentage));
							if(percentage != 0.0){
								to.setHonourSubPercentage(String.valueOf(Math.round(percentage)));
							}
						}
						//pass or fail process 

						double min=0;
						double stu=0;
						if(obj[13]!=null && CommonUtil.isValidDecimal(obj[13].toString()))
							min=Double.parseDouble(obj[13].toString());
						if(certificateCourse && isOptional)
							min=60;
						if(obj[16]!=null && CommonUtil.isValidDecimal(obj[16].toString()))
							stu=Double.parseDouble(obj[16].toString());
						
						if(obj[16]!=null && !CommonUtil.isValidDecimal(obj[16].toString())){
							if(!obj[20].toString().equalsIgnoreCase("Add On Course")){
								if(!dontConsiderFail)
								resultClass="Fail";
							}
						}else if(obj[41]!=null && obj[41].toString().equalsIgnoreCase("F") || min>stu){
							if(!obj[20].toString().equalsIgnoreCase("Add On Course")){
								if(!dontConsiderFail)
									resultClass="Fail";
							}else{
								
								if(Integer.parseInt(obj[10].toString())==18 && obj[41]!=null && !obj[41].toString().equalsIgnoreCase("E")){
									if(!dontConsiderFail)
										resultClass="Fail";
								}
							}	
						}else{
							if(Integer.parseInt(obj[10].toString())==18 && obj[41]!=null && obj[41].toString().equalsIgnoreCase("E")){
								if(!dontConsiderFail){
									resultClass="Fail";
								}
							} 
						}
						
					}else{
						if(obj[28]!=null && CommonUtil.isValidDecimal(obj[28].toString())){
							if(isAdd){
								if(to.getTotalMarksAwarded() != null && !to.getTotalMarksAwarded().trim().isEmpty()){
									totalMarksAwarded = Integer.parseInt(to.getTotalMarksAwarded());
								}
								totalMarksAwarded=totalMarksAwarded+(int)Math.round(Double.parseDouble(obj[28].toString()));
								to.setTotalMarksAwarded(String.valueOf(totalMarksAwarded));
							}
						}
						if(obj[26]!=null){
							if(isAdd){
								if(to.getTotalMaxmarks() != null && !to.getTotalMaxmarks().trim().isEmpty()){
									totalMaxMarks = Integer.parseInt(to.getTotalMaxmarks());
								}
								totalMaxMarks=totalMaxMarks+(int)Math.round(Double.parseDouble(obj[26].toString()));
								to.setTotalMaxmarks(String.valueOf(totalMaxMarks));
							}
						}
						if(to.getTotalMaxmarks() != null && !to.getTotalMaxmarks().trim().isEmpty() &&
								to.getTotalMarksAwarded() != null && !to.getTotalMarksAwarded().trim().isEmpty()){
							double percentage = 0.0;
							percentage = Integer.parseInt(to.getTotalMarksAwarded())* 100;
							percentage = percentage /Integer.parseInt(to.getTotalMaxmarks()) ;
							to.setPercentage(String.valueOf(percentage));
						}
						if(honourSubjectIds != null && honourSubjectIds.contains(Integer.parseInt(obj[3].toString()))){
							int marksAwarded=0;
							int maxMarks=0;
							double percentage = 0.0;
							if(obj[28]!=null && CommonUtil.isValidDecimal(obj[28].toString())){
								if(to.getHonourSubjectMarksAwarded() != null && !to.getHonourSubjectMarksAwarded().trim().isEmpty()){
									marksAwarded = Integer.parseInt(to.getHonourSubjectMarksAwarded());
								}
								marksAwarded = marksAwarded + (int)Math.round(Double.parseDouble(obj[28].toString()));
								to.setHonourSubjectMarksAwarded(String.valueOf(marksAwarded));
							}
							if(obj[26]!=null){
								if(to.getHonourSubjectMaxmarks() != null && !to.getHonourSubjectMaxmarks().trim().isEmpty()){
									maxMarks = Integer.parseInt(to.getHonourSubjectMaxmarks());
								}
								maxMarks = maxMarks +(int)Math.round(Double.parseDouble(obj[26].toString()));
								to.setHonourSubjectMaxmarks(String.valueOf(maxMarks));
							}
							percentage = marksAwarded *100/maxMarks;
							to.setHonourSubjectPercentage(String.valueOf(percentage));
							if(percentage != 0.0){
								to.setHonourSubPercentage(String.valueOf(Math.round(percentage)));
							}
						}
						//pass or fail process

						double min=0;
						double stu=0;
						if(obj[11]!=null && CommonUtil.isValidDecimal(obj[11].toString()))
							min=Double.parseDouble(obj[11].toString());
						if(certificateCourse && isOptional)
							min=60;
						
						if(obj[15]!=null && CommonUtil.isValidDecimal(obj[15].toString()))
							stu=Double.parseDouble(obj[15].toString());
						
						if(obj[15]!=null && !CommonUtil.isValidDecimal(obj[15].toString())){
							if(!obj[20].toString().equalsIgnoreCase("Add On Course")){
								if(!dontConsiderFail)
									resultClass="Fail";
							}
						}else if(obj[40]!=null && obj[40].toString().equalsIgnoreCase("F") || min>stu){
							if(!obj[20].toString().equalsIgnoreCase("Add On Course")){
								if(!dontConsiderFail)
									resultClass="Fail";
							}else{
								if(Integer.parseInt(obj[10].toString())==18 && obj[40]!=null && obj[40].toString().equalsIgnoreCase("E")){
									if(!dontConsiderFail)
										resultClass="Fail";
								}
							}	
						}else{
							if(Integer.parseInt(obj[10].toString())==18 && obj[40]!=null && obj[40].toString().equalsIgnoreCase("E")){
								if(!dontConsiderFail){
									resultClass="Fail";
								}
							} 
						}
						
					}
					if(!resultClass.equalsIgnoreCase("Pass")){
						arrears = arrears + 1;
					}
					
					map.put(Integer.parseInt(obj[35].toString()), to);
				}
			}
		}
		objForm.setArrears(String.valueOf(arrears));
		return map;
	}
	/**
	 * @param attendanceData
	 * @return
	 */
	public Map<Integer, String> getAttendanceMap(List<Object[]> attendanceData) throws Exception{
		Map<Integer, String> map = new HashMap<Integer, String>();
		if(attendanceData != null && !attendanceData.isEmpty()){
			Iterator<Object[]> iterator = attendanceData.iterator();
			while (iterator.hasNext()) {
				Object[] objects = (Object[]) iterator.next();
				if(objects[0] != null && objects[0].toString() != null && objects[4] != null && objects[4].toString() != null){
					map.put(Integer.parseInt(objects[0].toString()), objects[4].toString());
				}
			}
		}
		return map;
	}
	/**
	 * @param studentId
	 * @return
	 */
	public String getMarksCardQuery(int studentId){
		String query=" select EXAM_definition.id, EXAM_definition.month as exam_month, EXAM_definition.year as exam_year, "+
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
		" ,EXAM_sub_definition_coursewise.dont_consider_failure_total_result,EXAM_supplementary_improvement_application.is_appeared_theory, EXAM_supplementary_improvement_application.is_appeared_practical,EXAM_sub_definition_coursewise.dont_add_tot_mark_cls_decln,subject.is_certificate_course,if(theory_ese_maximum_mark is null and theory_ese_entered_max_mark is null and practical_ese_maximum_mark is null and practical_ese_entered_max_mark is null, 1, 0) as only_internal,EXAM_supplementary_improvement_application.is_improvement " +
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
		" where student.id ="+studentId+
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
		" ,EXAM_sub_definition_coursewise.dont_consider_failure_total_result,EXAM_supplementary_improvement_application.is_appeared_theory, EXAM_supplementary_improvement_application.is_appeared_practical,EXAM_sub_definition_coursewise.dont_add_tot_mark_cls_decln,subject.is_certificate_course,if(theory_ese_maximum_mark is null and theory_ese_entered_max_mark is null and practical_ese_maximum_mark is null and practical_ese_entered_max_mark is null, 1, 0) as only_internal,EXAM_supplementary_improvement_application.is_improvement " +
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
		" where student.id = "+studentId+
		" and subject.is_theory_practical='B'  "+
		" order by term_number, student_id, subject_id,exam_year desc,exam_month desc ";
		return query;
	}
	/**
	 * @param marksList
	 * @param honoursCourseEntryForm
	 * @param certificateMap
	 * @param appearedList
	 * @param attendanceMap
	 * @param honourSubjectIds
	 * @return
	 * @throws Exception 
	 * @throws Exception
	 *//*
	public Map<Integer, HonoursCourseEntryTo> getStudentAcademicDetails(List<Object[]> marksList, HonoursCourseEntryForm honoursCourseEntryForm,
			Map<Integer, Boolean> certificateMap,List<String> appearedList, Map<Integer, String> attendanceMap,
			List<Integer> honourSubjectIds) throws Exception{
		
		Map<Integer, HonoursCourseEntryTo> map = new HashMap<Integer, HonoursCourseEntryTo>();
		int arrears=0;
		if(marksList != null && !marksList.isEmpty()){
			Map<String, HonoursCourseEntryTo> mainMap = new HashMap<String, HonoursCourseEntryTo>();
			Iterator<Object[]> iterator = marksList.iterator();
			while (iterator.hasNext()) {
				Object[] obj = (Object[]) iterator.next();
				int totalMarksAwarded=0;
				int totalMaxMarks=0;
				boolean isAdd=true;
				String resultClass="Pass";
				HonoursCourseEntryTo to = null;
				if(obj[32] != null && obj[32].toString() != null){
					if(map.containsKey(Integer.parseInt(obj[32].toString()))){
						to = map.remove(Integer.parseInt(obj[32].toString()));
					}else{
						to = new HonoursCourseEntryTo();
					}
					if(obj[32].toString().equalsIgnoreCase("1")){
						to.setSemester("Semester I");
					}else if(obj[32].toString().equalsIgnoreCase("2")){
						to.setSemester("Semester II");
					}else{
						to.setSemester("Semester III");
					}
//					if(obj[53] != null){
//						to.setYear(obj[53].toString().substring(0, 4));
//					}
					String attpercentage = attendanceMap.get(Integer.parseInt(obj[32].toString()));
					to.setAttPercentage(attpercentage);
					if(obj[52]!=null){
						if(Integer.parseInt(obj[52].toString())==1){
							isAdd=false;
						}
					}
					if(obj[31].toString().equalsIgnoreCase("Practical")){
						
						
						
						
						if(obj[29]!=null && CommonUtil.isValidDecimal(obj[29].toString())){
							if(isAdd){
								if(to.getTotalMarksAwarded() != null && !to.getTotalMarksAwarded().trim().isEmpty()){
									totalMarksAwarded = Integer.parseInt(to.getTotalMarksAwarded());
								}
								totalMarksAwarded=totalMarksAwarded+(int)Math.round(Double.parseDouble(obj[29].toString()));
								to.setTotalMarksAwarded(String.valueOf(totalMarksAwarded));
							}
						}
						if(obj[27]!=null){
							if(isAdd){
								if(to.getTotalMaxmarks() != null && !to.getTotalMaxmarks().trim().isEmpty()){
									totalMaxMarks = Integer.parseInt(to.getTotalMaxmarks());
								}
								totalMaxMarks=totalMaxMarks+(int)Math.round(Double.parseDouble(obj[27].toString()));
								to.setTotalMaxmarks(String.valueOf(totalMaxMarks));
							}
						}
						if(to.getTotalMaxmarks() != null && !to.getTotalMaxmarks().trim().isEmpty() &&
								to.getTotalMarksAwarded() != null && !to.getTotalMarksAwarded().trim().isEmpty()){
							double percentage = 0.0;
							percentage = Integer.parseInt(to.getTotalMarksAwarded())*100 ;
							percentage = percentage / Integer.parseInt(to.getTotalMaxmarks());
							to.setPercentage(Math.round(percentage));
						}
						if(honourSubjectIds != null && honourSubjectIds.contains(Integer.parseInt(obj[7].toString()))){
							int marksAwarded=0;
							int maxMarks=0;
							double percentage = 0.0;
							if(obj[29]!=null && CommonUtil.isValidDecimal(obj[29].toString())){
								if(to.getHonourSubjectMarksAwarded() != null && !to.getHonourSubjectMarksAwarded().trim().isEmpty()){
									marksAwarded = Integer.parseInt(to.getHonourSubjectMarksAwarded());
								}
								marksAwarded = marksAwarded + (int)Math.round(Double.parseDouble(obj[29].toString()));
								to.setHonourSubjectMarksAwarded(String.valueOf(marksAwarded));
							}
							if(obj[27]!=null){
								if(to.getHonourSubjectMaxmarks() != null && !to.getHonourSubjectMaxmarks().trim().isEmpty()){
									maxMarks = Integer.parseInt(to.getHonourSubjectMaxmarks());
								}
								maxMarks = maxMarks +(int)Math.round(Double.parseDouble(obj[27].toString()));
								to.setHonourSubjectMaxmarks(String.valueOf(maxMarks));
							}
							percentage = marksAwarded * 100;
							percentage = percentage / maxMarks;
							to.setHonourSubjectPercentage(Math.round(percentage));
							if(percentage != 0.0){
								to.setHonourSubPercentage(String.valueOf(Math.round(percentage)));
							}
						}
						
					}else{
						if(obj[28]!=null && CommonUtil.isValidDecimal(obj[28].toString())){
							if(isAdd){
								if(to.getTotalMarksAwarded() != null && !to.getTotalMarksAwarded().trim().isEmpty()){
									totalMarksAwarded = Integer.parseInt(to.getTotalMarksAwarded());
								}
								totalMarksAwarded=totalMarksAwarded+(int)Math.round(Double.parseDouble(obj[28].toString()));
								to.setTotalMarksAwarded(String.valueOf(totalMarksAwarded));
							}
						}
						if(obj[26]!=null){
							if(isAdd){
								if(to.getTotalMaxmarks() != null && !to.getTotalMaxmarks().trim().isEmpty()){
									totalMaxMarks = Integer.parseInt(to.getTotalMaxmarks());
								}
								totalMaxMarks=totalMaxMarks+(int)Math.round(Double.parseDouble(obj[26].toString()));
								to.setTotalMaxmarks(String.valueOf(totalMaxMarks));
							}
						}
						if(to.getTotalMaxmarks() != null && !to.getTotalMaxmarks().trim().isEmpty() &&
								to.getTotalMarksAwarded() != null && !to.getTotalMarksAwarded().trim().isEmpty()){
							double percentage = 0.0;
							percentage = Integer.parseInt(to.getTotalMarksAwarded())* 100;
							percentage = percentage /Integer.parseInt(to.getTotalMaxmarks()) ;
							to.setPercentage(Math.round(percentage));
						}
						if(honourSubjectIds != null && honourSubjectIds.contains(Integer.parseInt(obj[7].toString()))){
							int marksAwarded=0;
							int maxMarks=0;
							double percentage = 0.0;
							if(obj[28]!=null && CommonUtil.isValidDecimal(obj[28].toString())){
								if(to.getHonourSubjectMarksAwarded() != null && !to.getHonourSubjectMarksAwarded().trim().isEmpty()){
									marksAwarded = Integer.parseInt(to.getHonourSubjectMarksAwarded());
								}
								marksAwarded = marksAwarded + (int)Math.round(Double.parseDouble(obj[28].toString()));
								to.setHonourSubjectMarksAwarded(String.valueOf(marksAwarded));
							}
							if(obj[26]!=null){
								if(to.getHonourSubjectMaxmarks() != null && !to.getHonourSubjectMaxmarks().trim().isEmpty()){
									maxMarks = Integer.parseInt(to.getHonourSubjectMaxmarks());
								}
								maxMarks = maxMarks +(int)Math.round(Double.parseDouble(obj[26].toString()));
								to.setHonourSubjectMaxmarks(String.valueOf(maxMarks));
							}
							percentage = marksAwarded *100/maxMarks;
							to.setHonourSubjectPercentage(Math.round(percentage));
							if(percentage != 0.0){
								to.setHonourSubPercentage(String.valueOf(Math.round(percentage)));
							}
						}
						
					}
					if(!resultClass.equalsIgnoreCase("Pass")){
						arrears = arrears + 1;
					}
					
					map.put(Integer.parseInt(obj[32].toString()), to);
				}
			}
		}
		
		return null;
	}*/
	/**
	 * @param list
	 * @param honoursCourseEntryForm
	 * @param certificateMap
	 * @param appearedList
	 * @return
	 * @throws Exception
	 */
	public Map<Integer,Map<String, ConsolidateMarksCard>> getStudentAcademicDetails(List<Object[]> list, HonoursCourseEntryForm honoursCourseEntryForm,
			Map<Integer, Map<Integer, Map<Integer, Boolean>>> certificateMap,List<String> appearedList) throws Exception {
		Map<Integer,Map<String, ConsolidateMarksCard>> mainMap=new HashMap<Integer, Map<String,ConsolidateMarksCard>>();
		boolean isMaxRecord=false;
		if(honoursCourseEntryForm.getCourseId()!=null && !honoursCourseEntryForm.getCourseId().isEmpty() && Integer.parseInt(honoursCourseEntryForm.getCourseId())!=18){
			isMaxRecord=true;
		}
		if(list!=null && !list.isEmpty()){
			Map<String, ConsolidateMarksCard> semMap;
			ConsolidateMarksCard consolidateMarksCard;
			ExamDefinition exam;
			Student student;
			Classes classes;
			Subject subject;
			Course course;
			Iterator<Object[]> itr=list.iterator();
			while (itr.hasNext()) {
				boolean isCertificateCourse=false;
				boolean isOptional=false;
				
				Object[] obj=itr.next();
				
				if(obj[3]!=null && obj[32]!=null && obj[5]!=null /*&& Integer.parseInt(obj[3].toString())==4226 && Integer.parseInt(obj[5].toString())==2962*/){
//					System.out.println(obj[3].toString()+"_"+obj[5].toString());
//					System.out.println(obj[3].toString());
					if(mainMap.containsKey(Integer.parseInt(obj[3].toString()))){
						semMap=mainMap.get(Integer.parseInt(obj[3].toString()));
						if(semMap.containsKey(Integer.parseInt(obj[32].toString())+"_"+Integer.parseInt(obj[5].toString())+"_"+obj[31].toString())){
							if(!isMaxRecord)
								continue;
						}
						semMap=mainMap.remove(Integer.parseInt(obj[3].toString()));
					}else
						semMap=new HashMap<String, ConsolidateMarksCard>();
					
					consolidateMarksCard =new ConsolidateMarksCard();
					if(obj[0]!=null){
						exam=new ExamDefinition();
						exam.setId(Integer.parseInt(obj[0].toString()));
						consolidateMarksCard.setExam(exam);
					}
					if(obj[3]!=null && !obj[3].toString().isEmpty() && StringUtils.isNumeric(obj[3].toString())){
						student=new Student();
						student.setId(Integer.parseInt(obj[3].toString()));
						consolidateMarksCard.setStudent(student);
					}
					if(obj[4]!=null && !obj[4].toString().isEmpty() && StringUtils.isNumeric(obj[4].toString())){
						classes=new Classes();
						classes.setId(Integer.parseInt(obj[4].toString()));
						consolidateMarksCard.setClasses(classes);
					}
					if(obj[5]!=null && !obj[5].toString().isEmpty() && StringUtils.isNumeric(obj[5].toString())){
						subject=new Subject();
						subject.setId(Integer.parseInt(obj[5].toString()));
						consolidateMarksCard.setSubject(subject);
					}
					if(obj[12]!=null && !obj[12].toString().isEmpty() && StringUtils.isNumeric(obj[12].toString())){
						course=new Course();
						course.setId(Integer.parseInt(obj[12].toString()));
						consolidateMarksCard.setCourse(course);
					}
					if(obj[6]!=null)
					consolidateMarksCard.setSubjectCode(obj[6].toString());
					if(obj[7]!=null)
					consolidateMarksCard.setSubjectName(obj[7].toString());
					if(obj[8]!=null)
					consolidateMarksCard.setTheoryTotalSubInternalMark(obj[8].toString());
					if(obj[9]!=null)
					consolidateMarksCard.setTheoryTotalAttendanceMark(obj[9].toString());
					if(obj[10]!=null)
					consolidateMarksCard.setPracticalTotalSubInternalMark(obj[10].toString());
					if(obj[11]!=null)
					consolidateMarksCard.setPracticalTotalAttendanceMark(obj[11].toString());
					if(obj[13]!=null && !obj[13].toString().isEmpty() && CommonUtil.isValidDecimal(obj[13].toString()))
					consolidateMarksCard.setTheoryeseMinimumMark(new BigDecimal(obj[13].toString()));
					if(obj[14]!=null && !obj[14].toString().isEmpty() && CommonUtil.isValidDecimal(obj[14].toString()))
					consolidateMarksCard.setTheoryeseMaximumMark(new BigDecimal(obj[14].toString()));
					if(obj[15]!=null && !obj[15].toString().isEmpty() && CommonUtil.isValidDecimal(obj[15].toString()))
					consolidateMarksCard.setPracticaleseMinimumMark(new BigDecimal(obj[15].toString()));
					if(obj[16]!=null && !obj[16].toString().isEmpty() && CommonUtil.isValidDecimal(obj[16].toString()))
					consolidateMarksCard.setPracticaleseMaximumMark(new BigDecimal(obj[16].toString()));
					if(obj[17]!=null)
					consolidateMarksCard.setStudentTheoryMark(obj[17].toString());
					if(obj[18]!=null)
					consolidateMarksCard.setStudentPracticalMark(obj[18].toString());
					if(obj[19]!=null)
					consolidateMarksCard.setFirstName(obj[19].toString());
					if(obj[20]!=null)
					consolidateMarksCard.setMiddleName(obj[20].toString());
					if(obj[21]!=null)
					consolidateMarksCard.setLastName(obj[21].toString());
					if(obj[22]!=null)
					consolidateMarksCard.setSection(obj[22].toString());
					
					if(obj[23]!=null && (obj[23].toString().equalsIgnoreCase("1") || obj[23].toString().equalsIgnoreCase("true")))
						consolidateMarksCard.setIsInitialise(true);
					else
						consolidateMarksCard.setIsInitialise(false);
					
					if(obj[25]!=null && !obj[25].toString().isEmpty() && StringUtils.isNumeric(obj[25].toString()))
					consolidateMarksCard.setSubjectOrder(Integer.parseInt(obj[25].toString()));
					if(obj[26]!=null && !obj[26].toString().isEmpty() && CommonUtil.isValidDecimal(obj[26].toString()))
					consolidateMarksCard.setFinalPracticalInternalMaximumMark(new BigDecimal(obj[26].toString()));
					if(obj[27]!=null && !obj[27].toString().isEmpty() && CommonUtil.isValidDecimal(obj[27].toString()))
					consolidateMarksCard.setFinalTheoryInternalMaximumMark(new BigDecimal(obj[27].toString()));
					if(obj[28]!=null && !obj[28].toString().isEmpty() && StringUtils.isNumeric(obj[28].toString()))
					consolidateMarksCard.setPracticalCredit(Integer.parseInt(obj[28].toString()));
					if(obj[29]!=null && !obj[29].toString().isEmpty() && StringUtils.isNumeric(obj[29].toString()))
					consolidateMarksCard.setTheoryCredit(Integer.parseInt(obj[29].toString()));
					if(obj[32]!=null && !obj[32].toString().isEmpty() && StringUtils.isNumeric(obj[32].toString()))
					consolidateMarksCard.setTermNumber(Integer.parseInt(obj[32].toString()));
					if(obj[30]!=null)
					consolidateMarksCard.setRegNo(obj[30].toString());
					if(obj[31]!=null)
					consolidateMarksCard.setSubType(obj[31].toString());
					if(obj[33]!=null && !obj[33].toString().isEmpty() && CommonUtil.isValidDecimal(obj[33].toString()))
					consolidateMarksCard.setTheoryMax(new BigDecimal(obj[33].toString()));
					if(obj[34]!=null && !obj[34].toString().isEmpty() && CommonUtil.isValidDecimal(obj[34].toString()))
					consolidateMarksCard.setPracticalMax(new BigDecimal(obj[34].toString()));
					
					if(obj[35]!=null && (obj[35].toString().equalsIgnoreCase("1") || obj[35].toString().equalsIgnoreCase("true")))
						consolidateMarksCard.setDontShowMaxMarks(true);
					else
						consolidateMarksCard.setDontShowMaxMarks(false);
					
					if(obj[36]!=null && (obj[36].toString().equalsIgnoreCase("1") || obj[36].toString().equalsIgnoreCase("true")))
						consolidateMarksCard.setDontShowMinMarks(true);
					else
						consolidateMarksCard.setDontShowMinMarks(false);
					
					if(obj[37]!=null && (obj[37].toString().equalsIgnoreCase("1") || obj[37].toString().equalsIgnoreCase("true")))
						consolidateMarksCard.setShowOnlyGrade(true);
					else
						consolidateMarksCard.setShowOnlyGrade(false);
					
					if(obj[39]!=null && (obj[39].toString().equalsIgnoreCase("1") || obj[39].toString().equalsIgnoreCase("true")))
						consolidateMarksCard.setDontShowSubType(true);
					else
						consolidateMarksCard.setDontShowSubType(false);
					
					if(obj[38]!=null)
					consolidateMarksCard.setName(obj[38].toString());
					if(obj[40]!=null && !obj[40].toString().isEmpty() && StringUtils.isNumeric(obj[40].toString()))
					consolidateMarksCard.setAppliedYear(Integer.parseInt(obj[40].toString()));
					if(obj[24]!=null && !obj[24].toString().isEmpty() && StringUtils.isNumeric(obj[24].toString()))
					consolidateMarksCard.setSectionId(Integer.parseInt(obj[24].toString()));
					if(obj[41]!=null && !obj[41].toString().isEmpty() && StringUtils.isNumeric(obj[41].toString()))
					consolidateMarksCard.setAcademicYear(Integer.parseInt(obj[41].toString()));
					if(obj[42]!=null && !obj[42].toString().isEmpty() && StringUtils.isNumeric(obj[42].toString()))
					consolidateMarksCard.setSelectedCourseId(Integer.parseInt(obj[42].toString()));
					
					if(obj[43]!=null && !obj[43].toString().isEmpty() && CommonUtil.isValidDecimal(obj[43].toString()))
						consolidateMarksCard.setFinalTheoryInternalMinimumMark(new BigDecimal(obj[43].toString()));
					if(obj[44]!=null && !obj[44].toString().isEmpty() && CommonUtil.isValidDecimal(obj[44].toString()))
						consolidateMarksCard.setFinalPracticalInternalMinimumMark(new BigDecimal(obj[44].toString()));
					
					if(obj[45]!=null && !obj[45].toString().isEmpty() && CommonUtil.isValidDecimal(obj[45].toString()))
						consolidateMarksCard.setTheoryMin(new BigDecimal(obj[45].toString()));
					if(obj[46]!=null && !obj[46].toString().isEmpty() && CommonUtil.isValidDecimal(obj[46].toString()))
						consolidateMarksCard.setPracticalMin(new BigDecimal(obj[46].toString()));	
						
					if(obj[47]!=null && (obj[47].toString().equalsIgnoreCase("1") || obj[47].toString().equalsIgnoreCase("true")))
						consolidateMarksCard.setDontConsiderFailureTotalResult(true);
					else
						consolidateMarksCard.setDontConsiderFailureTotalResult(false);
					
//					if(obj[48]!=null && (obj[48].toString().equalsIgnoreCase("1") || obj[48].toString().equalsIgnoreCase("true")))
//						consolidateMarksCard.setIsTheoryAppeared(true);
//					else
//						consolidateMarksCard.setIsTheoryAppeared(false);
//
//					if(obj[49]!=null && (obj[49].toString().equalsIgnoreCase("1") || obj[49].toString().equalsIgnoreCase("true")))
//						consolidateMarksCard.setIsPracticalAppeared(true);
//					else
//						consolidateMarksCard.setIsPracticalAppeared(false);
					
					if(appearedList.contains(consolidateMarksCard.getStudent().getId()+"_"+consolidateMarksCard.getTermNumber()+"_"+consolidateMarksCard.getSubject().getId()+"_T")){
						consolidateMarksCard.setIsTheoryAppeared(true);
					}else{
						consolidateMarksCard.setIsTheoryAppeared(false);
					}
					if(appearedList.contains(consolidateMarksCard.getStudent().getId()+"_"+consolidateMarksCard.getTermNumber()+"_"+consolidateMarksCard.getSubject().getId()+"_P")){
						consolidateMarksCard.setIsPracticalAppeared(true);
					}else{
						consolidateMarksCard.setIsPracticalAppeared(false);
					}
					if(obj[50]!=null && (obj[50].toString().equalsIgnoreCase("1") || obj[50].toString().equalsIgnoreCase("true")))
						consolidateMarksCard.setDontAddInTotal(true);
					else
						consolidateMarksCard.setDontAddInTotal(false);
					
					if(certificateMap.containsKey(consolidateMarksCard.getStudent().getId())){
						Map<Integer,Map<Integer,Boolean>> termMap=certificateMap.get(consolidateMarksCard.getStudent().getId());
						if(termMap.containsKey(consolidateMarksCard.getTermNumber())){
							Map<Integer,Boolean> subMap=termMap.get(consolidateMarksCard.getTermNumber());
							if(subMap.containsKey(consolidateMarksCard.getSubject().getId())){
								isCertificateCourse=true;
								isOptional=subMap.get(consolidateMarksCard.getSubject().getId());
							}
						}
					}else{
						if(obj[51]!=null && (obj[51].toString().equalsIgnoreCase("1") || obj[51].toString().equalsIgnoreCase("true"))){
							isCertificateCourse=true;
							isOptional=true;
						}
					}
					
					consolidateMarksCard.setIsCertificateCourse(isCertificateCourse);
					consolidateMarksCard.setCreatedDate(new Date());
					// if it is only internal subject we should explicitily hit the data base
					if(obj[52]!=null && (obj[52].toString().equalsIgnoreCase("1") || obj[52].toString().equalsIgnoreCase("true"))){
						checkInternalMarksForSubject(consolidateMarksCard,isMaxRecord);
					}
					boolean isImprovement=false;
					if((consolidateMarksCard.getIsTheoryAppeared()!=null && consolidateMarksCard.getIsTheoryAppeared()) ||  (consolidateMarksCard.getIsPracticalAppeared()!=null && consolidateMarksCard.getIsPracticalAppeared()))
						if(obj[53]!=null && (obj[53].toString().equalsIgnoreCase("1") || obj[53].toString().equalsIgnoreCase("true"))){
							isImprovement=true;
						}
					if(isMaxRecord){
						if(semMap.containsKey(consolidateMarksCard.getTermNumber()+"_"+consolidateMarksCard.getSubject().getId()+"_"+consolidateMarksCard.getSubType())){
							ConsolidateMarksCard bo=semMap.get(consolidateMarksCard.getTermNumber()+"_"+consolidateMarksCard.getSubject().getId()+"_"+consolidateMarksCard.getSubType());
							ConsolidateMaxTo to=CheckMaxMarks(consolidateMarksCard,bo,isImprovement);
							if(consolidateMarksCard.getSubType().equalsIgnoreCase("practical")){
								consolidateMarksCard.setPracticalTotalSubInternalMark(to.getPracticalCia());
								consolidateMarksCard.setPracticalTotalAttendanceMark(to.getPracticalAtt());
								consolidateMarksCard.setStudentPracticalMark(to.getPracticalEse());
								consolidateMarksCard.setIsPracticalAppeared(to.getIsAppearedPractical());
							}else{
								consolidateMarksCard.setTheoryTotalSubInternalMark(to.getTheoryCia());
								consolidateMarksCard.setTheoryTotalAttendanceMark(to.getTheoryAtt());
								consolidateMarksCard.setStudentTheoryMark(to.getTheoryEse());
								consolidateMarksCard.setIsTheoryAppeared(to.getIsAppearedTheory());
							}
							calculateMarksPercentage(consolidateMarksCard,isCertificateCourse,isOptional);
						}else{
							calculateMarksPercentage(consolidateMarksCard,isCertificateCourse,isOptional);
						}
						
						if(semMap.containsKey(consolidateMarksCard.getTermNumber()+"_"+consolidateMarksCard.getSubject().getId()+"_"+consolidateMarksCard.getSubType())){
							semMap.remove(consolidateMarksCard.getTermNumber()+"_"+consolidateMarksCard.getSubject().getId()+"_"+consolidateMarksCard.getSubType());
						}
						semMap.put(consolidateMarksCard.getTermNumber()+"_"+consolidateMarksCard.getSubject().getId()+"_"+consolidateMarksCard.getSubType(),consolidateMarksCard);
						mainMap.put(consolidateMarksCard.getStudent().getId(),semMap);
					}else{
						if(!semMap.containsKey(consolidateMarksCard.getTermNumber()+"_"+consolidateMarksCard.getSubject().getId()+"_"+consolidateMarksCard.getSubType())){
							calculateMarksPercentage(consolidateMarksCard,isCertificateCourse,isOptional);
							semMap.put(consolidateMarksCard.getTermNumber()+"_"+consolidateMarksCard.getSubject().getId()+"_"+consolidateMarksCard.getSubType(),consolidateMarksCard);
							mainMap.put(consolidateMarksCard.getStudent().getId(),semMap);
						}else{
							mainMap.put(consolidateMarksCard.getStudent().getId(),semMap);
						}
					}
				}
			}
		}
		
		
		return mainMap;
	}
	
	/**
	 * @param consolidateMarksCard
	 */
	private void calculateMarksPercentage(ConsolidateMarksCard consolidateMarksCard,boolean isCertificateCourse,boolean isOptional) throws Exception {
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
		if(consolidateMarksCard.getSubType().equalsIgnoreCase("Practical")){
			if(consolidateMarksCard.getPracticalTotalAttendanceMark()!=null && !consolidateMarksCard.getPracticalTotalAttendanceMark().isEmpty()){
				if(CommonUtil.isValidDecimal(consolidateMarksCard.getPracticalTotalAttendanceMark()))
					practicalAtt=Double.parseDouble(consolidateMarksCard.getPracticalTotalAttendanceMark());
				else{
					if(!isCertificateCourse && !consolidateMarksCard.getDontConsiderFailureTotalResult())
						fail=true;
				}
			}
			if(consolidateMarksCard.getPracticalTotalSubInternalMark()!=null && !consolidateMarksCard.getPracticalTotalSubInternalMark().isEmpty()){
				if(CommonUtil.isValidDecimal(consolidateMarksCard.getPracticalTotalSubInternalMark())){
					practicalCia=Double.parseDouble(consolidateMarksCard.getPracticalTotalSubInternalMark());
					if(consolidateMarksCard.getFinalPracticalInternalMinimumMark()!=null){
						double minMark=consolidateMarksCard.getFinalPracticalInternalMinimumMark().doubleValue();
						if((practicalAtt+practicalCia)<minMark)
							if(!isCertificateCourse && !consolidateMarksCard.getDontConsiderFailureTotalResult())
								fail=true;
					}
				}else{
					if(!isCertificateCourse && !consolidateMarksCard.getDontConsiderFailureTotalResult())
						fail=true;
				}
			}
			
			if(consolidateMarksCard.getStudentPracticalMark()!=null && !consolidateMarksCard.getStudentPracticalMark().isEmpty()){
				if(CommonUtil.isValidDecimal(consolidateMarksCard.getStudentPracticalMark())){
					practicalEse=Double.parseDouble(consolidateMarksCard.getStudentPracticalMark());
					if(consolidateMarksCard.getPracticaleseMinimumMark()!=null){
						double minMark=consolidateMarksCard.getPracticaleseMinimumMark().doubleValue();
						if(practicalEse<minMark)
							if(!isCertificateCourse && !consolidateMarksCard.getDontConsiderFailureTotalResult())
								fail=true;
					}
				}else{
					if(!isCertificateCourse && !consolidateMarksCard.getDontConsiderFailureTotalResult())
						fail=true;
				}
			}
			practicalTotal=practicalAtt+practicalCia+practicalEse;
			double minMark=0;
			if(consolidateMarksCard.getPracticalMin()!=null){
				minMark=consolidateMarksCard.getPracticalMin().doubleValue();
			}
			if(consolidateMarksCard.getPracticalMax()!=null)
				if(isCertificateCourse)
					if(isOptional){
						if(consolidateMarksCard.getTheoryMax().doubleValue()==100)
							minMark=60;
						else if(consolidateMarksCard.getTheoryMax().doubleValue()==50)
							minMark=30;
					}
			if(practicalTotal<minMark)
				fail=true;
			if(consolidateMarksCard.getPracticalMax()!=null){
				double maxMark=consolidateMarksCard.getPracticalMax().doubleValue();
				if(maxMark>0){
				double percentage=(practicalTotal/maxMark)*100;
				consolidateMarksCard.setPracticalPercentage(CommonUtil.Round(percentage,2));
				
				String query="select ifnull((SELECT EXAM_sub_coursewise_grade_defn.grade " +
							" FROM EXAM_sub_coursewise_grade_defn EXAM_sub_coursewise_grade_defn where "+
							percentage+" between start_prcntg_grade and end_prcntg_grade and EXAM_sub_coursewise_grade_defn.course_id = "+consolidateMarksCard.getCourse().getId()+" and subject_id="+consolidateMarksCard.getSubject().getId()+")," +
							" (ifnull( (SELECT EXAM_grade_class_definition_frombatch.grade " +
							" FROM EXAM_grade_class_definition_frombatch " +
							" EXAM_grade_class_definition_frombatch where "+percentage+" between EXAM_grade_class_definition_frombatch.start_percentage and EXAM_grade_class_definition_frombatch.end_percentage and " +
							" EXAM_grade_class_definition_frombatch.course_id = "+consolidateMarksCard.getCourse().getId()+" and from_batch <= "+consolidateMarksCard.getAppliedYear()+" limit 1)," +
							" (SELECT  EXAM_grade_class_definition.grade " +
							" FROM EXAM_grade_class_definition EXAM_grade_class_definition where "+percentage+" between start_percentage and EXAM_grade_class_definition.end_percentage and EXAM_grade_class_definition.course_id = "+consolidateMarksCard.getCourse().getId()+" limit 1)))) as pragra," +
							"  ifnull( (SELECT EXAM_sub_coursewise_grade_defn.grade_point " +
							" FROM EXAM_sub_coursewise_grade_defn EXAM_sub_coursewise_grade_defn where " +
							+percentage+" between start_prcntg_grade and end_prcntg_grade and " +
							" EXAM_sub_coursewise_grade_defn.course_id = "+consolidateMarksCard.getCourse().getId()+" and subject_id="+consolidateMarksCard.getSubject().getId()+"), " +
							"  (ifnull( (SELECT     EXAM_grade_class_definition_frombatch.grade_point " +
							"  FROM EXAM_grade_class_definition_frombatch EXAM_grade_class_definition_frombatch where " +
							+percentage+" between EXAM_grade_class_definition_frombatch.start_percentage and " +
							" EXAM_grade_class_definition_frombatch.end_percentage and " +
							" EXAM_grade_class_definition_frombatch.course_id = "+consolidateMarksCard.getCourse().getId()+" and from_batch <= "+consolidateMarksCard.getAppliedYear()+" limit 1), " +
							"(SELECT EXAM_grade_class_definition.grade_point " +
							"  FROM EXAM_grade_class_definition EXAM_grade_class_definition " +
							" where "+percentage+" between start_percentage and EXAM_grade_class_definition.end_percentage and " +
							" EXAM_grade_class_definition.course_id = "+consolidateMarksCard.getCourse().getId()+" limit 1)))) as pragrap";
				
				
				
				/*String query="select if((SELECT EXAM_sub_coursewise_grade_defn.grade " +
				" FROM EXAM_sub_coursewise_grade_defn EXAM_sub_coursewise_grade_defn " +
				" where "+percentage+" between start_prcntg_grade and end_prcntg_grade and EXAM_sub_coursewise_grade_defn.course_id ="+consolidateMarksCard.getCourse().getId()+"  and subject_id="+consolidateMarksCard.getSubject().getId()+") is not null, " +
				" (SELECT EXAM_sub_coursewise_grade_defn.grade FROM EXAM_sub_coursewise_grade_defn EXAM_sub_coursewise_grade_defn where "+percentage+
				" between start_prcntg_grade and end_prcntg_grade and EXAM_sub_coursewise_grade_defn.course_id ="+consolidateMarksCard.getCourse().getId()+"  and subject_id="+consolidateMarksCard.getSubject().getId()+") ," +
				" (SELECT EXAM_grade_class_definition.grade FROM EXAM_grade_class_definition EXAM_grade_class_definition " +
				" where "+percentage+" between start_percentage and EXAM_grade_class_definition.end_percentage and EXAM_grade_class_definition.course_id ="+consolidateMarksCard.getCourse().getId()+"  limit 1))," +
						"if((SELECT EXAM_sub_coursewise_grade_defn.grade_point " +
				" FROM EXAM_sub_coursewise_grade_defn EXAM_sub_coursewise_grade_defn " +
				" where "+percentage+" between start_prcntg_grade and end_prcntg_grade and EXAM_sub_coursewise_grade_defn.course_id ="+consolidateMarksCard.getCourse().getId()+"  and subject_id="+consolidateMarksCard.getSubject().getId()+") is not null, " +
				" (SELECT EXAM_sub_coursewise_grade_defn.grade_point FROM EXAM_sub_coursewise_grade_defn EXAM_sub_coursewise_grade_defn where "+percentage+
				" between start_prcntg_grade and end_prcntg_grade and EXAM_sub_coursewise_grade_defn.course_id ="+consolidateMarksCard.getCourse().getId()+"  and subject_id="+consolidateMarksCard.getSubject().getId()+") ," +
				" (SELECT EXAM_grade_class_definition.grade_point FROM EXAM_grade_class_definition EXAM_grade_class_definition " +
				" where "+percentage+" between start_percentage and EXAM_grade_class_definition.end_percentage and EXAM_grade_class_definition.course_id ="+consolidateMarksCard.getCourse().getId()+"  limit 1))";
				*/
				
				List<Object[]> rList=transaction.getStudentHallTicket(query);
				if(rList!=null && !rList.isEmpty()){
					Iterator<Object[]> rItr=rList.iterator();
					while (rItr.hasNext()) {
						Object[] robj = rItr.next();
						if(robj[0]!=null){
							consolidateMarksCard.setGrade(robj[0].toString());
						}
						if(robj[1]!=null){
							consolidateMarksCard.setGradePoint(new BigDecimal(robj[1].toString()));
						}
						
					}
				}
				}
			}
			consolidateMarksCard.setPracticalObtain(practicalTotal);
			if(fail){
				consolidateMarksCard.setPassOrFail("Fail");
				if(consolidateMarksCard.getSection()!=null && !consolidateMarksCard.getSection().equalsIgnoreCase("Add On Course"))
					consolidateMarksCard.setGrade("F");
			}else{
				consolidateMarksCard.setPassOrFail("Pass");
			}
		}else{
			if(consolidateMarksCard.getTheoryTotalAttendanceMark()!=null && !consolidateMarksCard.getTheoryTotalAttendanceMark().isEmpty()){
				if(CommonUtil.isValidDecimal(consolidateMarksCard.getTheoryTotalAttendanceMark())){
					theoryAtt=Double.parseDouble(consolidateMarksCard.getTheoryTotalAttendanceMark());
				}else{
					if(!isCertificateCourse && !consolidateMarksCard.getDontConsiderFailureTotalResult())
						fail=true;
				}
			}
			if(consolidateMarksCard.getTheoryTotalSubInternalMark()!=null && !consolidateMarksCard.getTheoryTotalSubInternalMark().isEmpty()){
				if(CommonUtil.isValidDecimal(consolidateMarksCard.getTheoryTotalSubInternalMark())){
					theoryCia=Double.parseDouble(consolidateMarksCard.getTheoryTotalSubInternalMark());
					if(consolidateMarksCard.getFinalTheoryInternalMinimumMark()!=null){
						double minMark=consolidateMarksCard.getFinalTheoryInternalMinimumMark().doubleValue();
						if((theoryAtt+theoryCia)<minMark)
							if(!isCertificateCourse && !consolidateMarksCard.getDontConsiderFailureTotalResult())
								fail=true;
					}
				}else{
					if(!isCertificateCourse && !consolidateMarksCard.getDontConsiderFailureTotalResult())
						fail=true;
				}
			}
			
			if(consolidateMarksCard.getStudentTheoryMark()!=null && !consolidateMarksCard.getStudentTheoryMark().isEmpty()){
				if(CommonUtil.isValidDecimal(consolidateMarksCard.getStudentTheoryMark())){
					theoryEse=Double.parseDouble(consolidateMarksCard.getStudentTheoryMark());
					if(consolidateMarksCard.getTheoryeseMinimumMark()!=null){
						double minMark=consolidateMarksCard.getTheoryeseMinimumMark().doubleValue();
						if(theoryEse<minMark)
							if(!isCertificateCourse && !consolidateMarksCard.getDontConsiderFailureTotalResult())
								fail=true;
					}
				}else{
					if(!isCertificateCourse && !consolidateMarksCard.getDontConsiderFailureTotalResult())
						fail=true;
				}
			}
			theoryTotal=theoryAtt+theoryCia+theoryEse;
			consolidateMarksCard.setTheoryObtain(theoryTotal);
			double minMark=0;
			if(consolidateMarksCard.getTheoryMin()!=null){
				minMark=consolidateMarksCard.getTheoryMin().doubleValue();
			}
			if(consolidateMarksCard.getTheoryMax()!=null)
				if(isCertificateCourse)
					if(isOptional){
						if(consolidateMarksCard.getTheoryMax().doubleValue()==100)
							minMark=60;
						else if(consolidateMarksCard.getTheoryMax().doubleValue()==50)
							minMark=30;
					}
			if(theoryTotal<minMark)
				fail=true;
			
			if(consolidateMarksCard.getTheoryMax()!=null){
				double maxMark=consolidateMarksCard.getTheoryMax().doubleValue();
				if(maxMark>0){
				double percentage=(theoryTotal/maxMark)*100;
				consolidateMarksCard.setTheoryPercentage(CommonUtil.Round(percentage,2));
				
				String query="select ifnull((SELECT EXAM_sub_coursewise_grade_defn.grade " +
				" FROM EXAM_sub_coursewise_grade_defn EXAM_sub_coursewise_grade_defn where "+
				percentage+" between start_prcntg_grade and end_prcntg_grade and EXAM_sub_coursewise_grade_defn.course_id = "+consolidateMarksCard.getCourse().getId()+" and subject_id="+consolidateMarksCard.getSubject().getId()+")," +
				" (ifnull( (SELECT EXAM_grade_class_definition_frombatch.grade " +
				" FROM EXAM_grade_class_definition_frombatch " +
				" EXAM_grade_class_definition_frombatch where "+percentage+" between EXAM_grade_class_definition_frombatch.start_percentage and EXAM_grade_class_definition_frombatch.end_percentage and " +
				" EXAM_grade_class_definition_frombatch.course_id = "+consolidateMarksCard.getCourse().getId()+" and from_batch <= "+consolidateMarksCard.getAppliedYear()+" limit 1)," +
				" (SELECT  EXAM_grade_class_definition.grade " +
				" FROM EXAM_grade_class_definition EXAM_grade_class_definition where "+percentage+" between start_percentage and EXAM_grade_class_definition.end_percentage and EXAM_grade_class_definition.course_id = "+consolidateMarksCard.getCourse().getId()+" limit 1)))) as thegra," +
				"  ifnull( (SELECT EXAM_sub_coursewise_grade_defn.grade_point " +
				" FROM EXAM_sub_coursewise_grade_defn EXAM_sub_coursewise_grade_defn where " +
				+percentage+" between start_prcntg_grade and end_prcntg_grade and " +
				" EXAM_sub_coursewise_grade_defn.course_id = "+consolidateMarksCard.getCourse().getId()+" and subject_id="+consolidateMarksCard.getSubject().getId()+"), " +
				"  (ifnull( (SELECT     EXAM_grade_class_definition_frombatch.grade_point " +
				"  FROM EXAM_grade_class_definition_frombatch EXAM_grade_class_definition_frombatch where " +
				+percentage+" between EXAM_grade_class_definition_frombatch.start_percentage and " +
				" EXAM_grade_class_definition_frombatch.end_percentage and " +
				" EXAM_grade_class_definition_frombatch.course_id = "+consolidateMarksCard.getCourse().getId()+" and from_batch <= "+consolidateMarksCard.getAppliedYear()+" limit 1), " +
				"(SELECT EXAM_grade_class_definition.grade_point " +
				"  FROM EXAM_grade_class_definition EXAM_grade_class_definition " +
				" where "+percentage+" between start_percentage and EXAM_grade_class_definition.end_percentage and " +
				" EXAM_grade_class_definition.course_id = "+consolidateMarksCard.getCourse().getId()+" limit 1)))) as thegrap";
				
			/*	String query="select if((SELECT EXAM_sub_coursewise_grade_defn.grade " +
						" FROM EXAM_sub_coursewise_grade_defn EXAM_sub_coursewise_grade_defn " +
						" where "+percentage+" between start_prcntg_grade and end_prcntg_grade and EXAM_sub_coursewise_grade_defn.course_id ="+consolidateMarksCard.getCourse().getId()+"  and subject_id="+consolidateMarksCard.getSubject().getId()+") is not null, " +
						" (SELECT EXAM_sub_coursewise_grade_defn.grade FROM EXAM_sub_coursewise_grade_defn EXAM_sub_coursewise_grade_defn where "+percentage+
						" between start_prcntg_grade and end_prcntg_grade and EXAM_sub_coursewise_grade_defn.course_id ="+consolidateMarksCard.getCourse().getId()+"  and subject_id="+consolidateMarksCard.getSubject().getId()+") ," +
						" (SELECT EXAM_grade_class_definition.grade FROM EXAM_grade_class_definition EXAM_grade_class_definition " +
						" where "+percentage+" between start_percentage and EXAM_grade_class_definition.end_percentage and EXAM_grade_class_definition.course_id ="+consolidateMarksCard.getCourse().getId()+"  limit 1))," +
								"if((SELECT EXAM_sub_coursewise_grade_defn.grade_point " +
						" FROM EXAM_sub_coursewise_grade_defn EXAM_sub_coursewise_grade_defn " +
						" where "+percentage+" between start_prcntg_grade and end_prcntg_grade and EXAM_sub_coursewise_grade_defn.course_id ="+consolidateMarksCard.getCourse().getId()+"  and subject_id="+consolidateMarksCard.getSubject().getId()+") is not null, " +
						" (SELECT EXAM_sub_coursewise_grade_defn.grade_point FROM EXAM_sub_coursewise_grade_defn EXAM_sub_coursewise_grade_defn where "+percentage+
						" between start_prcntg_grade and end_prcntg_grade and EXAM_sub_coursewise_grade_defn.course_id ="+consolidateMarksCard.getCourse().getId()+"  and subject_id="+consolidateMarksCard.getSubject().getId()+") ," +
						" (SELECT EXAM_grade_class_definition.grade_point FROM EXAM_grade_class_definition EXAM_grade_class_definition " +
						" where "+percentage+" between start_percentage and EXAM_grade_class_definition.end_percentage and EXAM_grade_class_definition.course_id ="+consolidateMarksCard.getCourse().getId()+"  limit 1))";
				*/
				
				List<Object[]> rList=transaction.getStudentHallTicket(query);
				if(rList!=null && !rList.isEmpty()){
					Iterator<Object[]> rItr=rList.iterator();
					while (rItr.hasNext()) {
						Object[] robj = rItr.next();
						if(robj[0]!=null){
							consolidateMarksCard.setGrade(robj[0].toString());
						}
						if(robj[1]!=null){
							consolidateMarksCard.setGradePoint(new BigDecimal(robj[1].toString()));
						}
						
					}
				}
				}
			}
			if(fail){
				consolidateMarksCard.setPassOrFail("Fail");
				if(consolidateMarksCard.getSection()!=null && !consolidateMarksCard.getSection().equalsIgnoreCase("Add On Course"))
						consolidateMarksCard.setGrade("F");
			}else{
				consolidateMarksCard.setPassOrFail("Pass");
			}
		}
		
		
		
		
	}
	
	/**
	 * @param consolidateMarksCard
	 * @param bo
	 * @return
	 */
	private ConsolidateMaxTo CheckMaxMarks( ConsolidateMarksCard bo1, ConsolidateMarksCard bo2,boolean isImprovement) throws Exception {
		ConsolidateMaxTo to=new ConsolidateMaxTo();
		if(bo1!=null && bo2!=null){
			//Is Appeared Theory
			if((bo1.getIsTheoryAppeared()!=null && bo1.getIsTheoryAppeared()) || (bo2.getIsTheoryAppeared()!=null && bo2.getIsTheoryAppeared())){
				to.setIsAppearedTheory(true);
			}else{
				to.setIsAppearedTheory(false);
			}
			//Is Appeared Practical
			if((bo1.getIsPracticalAppeared()!=null && bo1.getIsPracticalAppeared()) || (bo2.getIsPracticalAppeared()!=null && bo2.getIsPracticalAppeared())){
				to.setIsAppearedPractical(true);
			}else{
				to.setIsAppearedPractical(false);
			}
			
			if(isImprovement){// If it is improvement then we have to get max between the two records
			// Theory Int Marks
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
			
			// Theory Reg Mark
			if(bo1.getStudentTheoryMark()!=null && !bo1.getStudentTheoryMark().isEmpty()){
				if(StringUtils.isAlpha(bo1.getStudentTheoryMark().trim())){
					if(bo2.getStudentTheoryMark()!=null && !bo2.getStudentTheoryMark().isEmpty() && !StringUtils.isAlpha(bo2.getStudentTheoryMark().trim())){
						to.setTheoryEse(bo2.getStudentTheoryMark());
					}else{
						to.setTheoryEse(bo1.getStudentTheoryMark());
					}
				}else{
					if(bo2.getStudentTheoryMark()!=null && !bo2.getStudentTheoryMark().isEmpty() && !StringUtils.isAlpha(bo2.getStudentTheoryMark().trim())){
						if(Double.parseDouble(bo1.getStudentTheoryMark()) < Double.parseDouble(bo2.getStudentTheoryMark()))
							to.setTheoryEse(bo2.getStudentTheoryMark());
						else
							to.setTheoryEse(bo1.getStudentTheoryMark());
					}else{
						to.setTheoryEse(bo1.getStudentTheoryMark());
					}
				}
			}else{
				if(bo2.getStudentTheoryMark()!=null && !bo2.getStudentTheoryMark().isEmpty()){
					to.setTheoryEse(bo2.getStudentTheoryMark());
				}
			}
			
			
			// practical Reg Mark
			if(bo1.getStudentPracticalMark()!=null && !bo1.getStudentPracticalMark().isEmpty()){
				if(StringUtils.isAlpha(bo1.getStudentPracticalMark().trim())){
					if(bo2.getStudentPracticalMark()!=null && !bo2.getStudentPracticalMark().isEmpty() && !StringUtils.isAlpha(bo2.getStudentPracticalMark().trim())){
						to.setPracticalEse(bo2.getStudentPracticalMark());
					}else{
						to.setPracticalEse(bo1.getStudentPracticalMark());
					}
				}else{
					if(bo2.getStudentPracticalMark()!=null && !bo2.getStudentPracticalMark().isEmpty() && !StringUtils.isAlpha(bo2.getStudentPracticalMark().trim())){
						if(Double.parseDouble(bo1.getStudentPracticalMark()) < Double.parseDouble(bo2.getStudentPracticalMark()))
							to.setPracticalEse(bo2.getStudentPracticalMark());
						else
							to.setPracticalEse(bo1.getStudentPracticalMark());
					}else{
						to.setPracticalEse(bo1.getStudentPracticalMark());
					}
				}
			}else{
				if(bo2.getStudentPracticalMark()!=null && !bo2.getStudentPracticalMark().isEmpty()){
					to.setPracticalEse(bo2.getStudentPracticalMark());
				}
			}
			
			// Theory Att Mark
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
			}else{
				
				// If it is supplementary then we have to get latest availablity data
				// Theory Int Marks
				if(bo2.getTheoryTotalSubInternalMark()==null || bo2.getTheoryTotalSubInternalMark().isEmpty()){
					to.setTheoryCia(bo1.getTheoryTotalSubInternalMark());
				}else
					to.setTheoryCia(bo2.getTheoryTotalSubInternalMark());
				
				
				// Practical Int Mark
				if(bo2.getPracticalTotalSubInternalMark()==null || bo2.getPracticalTotalSubInternalMark().isEmpty()){
					to.setPracticalCia(bo1.getPracticalTotalSubInternalMark());
				}else{
					to.setPracticalCia(bo2.getPracticalTotalSubInternalMark());
				}
				
				// Theory Reg Mark
				if(bo2.getStudentTheoryMark()==null || bo2.getStudentTheoryMark().isEmpty()){
					to.setTheoryEse(bo1.getStudentTheoryMark());
				}else{
					to.setTheoryEse(bo2.getStudentTheoryMark());
				}
				
				
				// practical Reg Mark
				if(bo2.getStudentPracticalMark()==null || bo2.getStudentPracticalMark().isEmpty()){
					to.setPracticalEse(bo1.getStudentPracticalMark());
				}else{
					to.setPracticalEse(bo2.getStudentPracticalMark());
				}
				
				// Theory Att Mark
				if(bo2.getTheoryTotalAttendanceMark()==null || bo2.getTheoryTotalAttendanceMark().isEmpty()){
					to.setTheoryAtt(bo1.getTheoryTotalAttendanceMark());
				}else{
					to.setTheoryAtt(bo2.getTheoryTotalAttendanceMark());
				}
				// Practical Att Mark
				if(bo2.getPracticalTotalAttendanceMark()==null || bo2.getPracticalTotalAttendanceMark().isEmpty()){
					to.setPracticalAtt(bo1.getPracticalTotalAttendanceMark());
				}else{
					to.setPracticalAtt(bo2.getPracticalTotalAttendanceMark());
				}
			}
		}
		return to;
	}
	/**
	 * @param consolidateMarksCard
	 * @param isMaxRecord
	 */
	private void checkInternalMarksForSubject( ConsolidateMarksCard consolidateMarksCard, boolean isMaxRecord) throws Exception {
		String query="from InternalMarkSupplementaryDetails i where i.student.id="+consolidateMarksCard.getStudent().getId()+" and i.subject.id="+consolidateMarksCard.getSubject().getId()+" order by i.exam.year desc,i.exam.month desc ";
		INewExamMarksEntryTransaction transaction=NewExamMarksEntryTransactionImpl.getInstance();
		List<InternalMarkSupplementaryDetails> list=transaction.getDataForQuery(query);
		ConsolidateMarksCard card=null;
		ConsolidateMarksCard latest=null;
		if(list!=null && !list.isEmpty()){
			Iterator<InternalMarkSupplementaryDetails> itr=list.iterator();
			while (itr.hasNext()) {
				InternalMarkSupplementaryDetails bo = itr .next();
				card=new ConsolidateMarksCard();
				if(consolidateMarksCard.getSubType().equalsIgnoreCase("practical")){
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
						if(consolidateMarksCard.getSubType().equalsIgnoreCase("practical")){
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
				if(consolidateMarksCard.getSubType().equalsIgnoreCase("practical")){
					if(latest.getPracticalTotalSubInternalMark()!=null)
					consolidateMarksCard.setPracticalTotalSubInternalMark(latest.getPracticalTotalSubInternalMark());
					if(latest.getPracticalTotalAttendanceMark()!=null)
					consolidateMarksCard.setPracticalTotalAttendanceMark(latest.getPracticalTotalAttendanceMark());
				}else{
					if(latest.getTheoryTotalSubInternalMark()!=null)
					consolidateMarksCard.setTheoryTotalSubInternalMark(latest.getTheoryTotalSubInternalMark());
					if(latest.getTheoryTotalAttendanceMark()!=null)
					consolidateMarksCard.setTheoryTotalAttendanceMark(latest.getTheoryTotalAttendanceMark());
				}
			}
		}
		
	}
	
	/**
	 * @param latest
	 * @param card
	 * @return
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
	 * @param honoursCourseEntryForm
	 * @return
	 * @throws Exception
	 */
	public HonoursCourseApplication getCourseApplication(HonoursCourseEntryForm form) throws Exception{
		HonoursCourseApplication bo = new HonoursCourseApplication();
		bo.setAcademicYear(Integer.parseInt(form.getAcademicYear()));
		Course course = new Course();
		course.setId(Integer.parseInt(form.getAppliedCourseId()));
		bo.setCourse(course);
		bo.setCreatedBy(form.getUserId());
		bo.setCreatedDate(new Date());
		bo.setIsActive(true);
		bo.setLastModifiedDate(new Date());
		bo.setModifiedBy(form.getUserId());
		Student student = new Student();
		student.setId(Integer.parseInt(form.getStudentId()));
		bo.setStudent(student );
		bo.setTermNumber(Integer.parseInt(form.getSemister()));
		return bo;
	}
	/**
	 * @param boList
	 * @return
	 * @throws Exception
	 */
	public List<HonoursCourseEntryTo> convertBOTOTO(List<HonoursCourseApplication> boList) throws Exception{
		List<HonoursCourseEntryTo> tos = new ArrayList<HonoursCourseEntryTo>();
		if(boList != null){
			Iterator<HonoursCourseApplication> iterator = boList.iterator();
			while (iterator.hasNext()) {
				HonoursCourseApplication bo = (HonoursCourseApplication) iterator.next();
				if(bo.getStudent() != null){
					HonoursCourseEntryTo to = new HonoursCourseEntryTo();
					to.setStudentId(bo.getStudent().getId());
					to.setRegNO(bo.getStudent().getRegisterNo());
					to.setStudentName(bo.getStudent().getAdmAppln().getPersonalData().getFirstName());
					to.setCurrentClass(bo.getStudent().getClassSchemewise().getClasses().getName());
					to.setCourseApplied(bo.getCourse().getName());
					to.setYear(String.valueOf(bo.getStudent().getAdmAppln().getAppliedYear()));
					tos.add(to);
				}
			}
		}
		return tos;
	}
	/**
	 * @param form
	 * @return
	 * @throws Exception
	 */
	public List<HonorsEntryBo> convertFormToBO(HonoursCourseEntryForm form) throws Exception{
		List<HonorsEntryBo> boList = new ArrayList<HonorsEntryBo>();
		if(form.getAppliedDetails() != null){
			Map<Integer, String> map = new HashMap<Integer, String>(); 
			Iterator<HonoursCourseEntryTo> iterator = form.getAppliedDetails().iterator();
			while (iterator.hasNext()) {
				HonoursCourseEntryTo to = (HonoursCourseEntryTo) iterator.next();
				if(to.getChecked() != null && to.getChecked().equalsIgnoreCase("on")){
					if(!map.containsKey(to.getStudentId())){
						HonorsEntryBo bo = new HonorsEntryBo();
						bo.setAcademicYear(Integer.parseInt(to.getYear()));
						Course course = new Course();
						course.setId(Integer.parseInt(form.getHonoursCourseId()));
						bo.setCourse(course);
						bo.setCreatedBy(form.getUserId());
						bo.setCreatedDate(new Date());
						bo.setIsActive(true);
						bo.setModifiedBy(form.getUserId());
						bo.setModifiedDate(new Date());
						bo.setSemister(Integer.parseInt(form.getSemister()));
						Student student = new Student();
						student.setId(to.getStudentId());
						bo.setStudent(student);
						boList.add(bo);
					}
					map.put(to.getStudentId(), to.getRegNO());
				}
			}
		}
		return boList;
	}
}
