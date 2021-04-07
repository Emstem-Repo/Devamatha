package com.kp.cms.helpers.admission;

import com.kp.cms.forms.admission.DisableStudentLoginForm;

public class DisableStudentLoginHelper {
	public static volatile DisableStudentLoginHelper disableStudentLoginHelper = null;
	public static DisableStudentLoginHelper getInstance(){
		if(disableStudentLoginHelper == null){
			disableStudentLoginHelper = new DisableStudentLoginHelper();
			return disableStudentLoginHelper;
		}
		return disableStudentLoginHelper;
	}
	/**
	 * @param disableStudentLoginForm
	 * @return
	 * @throws Exception
	 */
	public StringBuffer getQuery(DisableStudentLoginForm disableStudentLoginForm)throws Exception {
		StringBuffer query = new StringBuffer("select c.student_id,sum(if(c.pass_fail='Fail'and c.dont_consider_failure_total_result=0,1,0))as passfail" +
												" from consolidated_marks_card c" +
												" left join student ON c.student_id = student.id" +
												" left join adm_appln ON student.adm_appln_id = adm_appln.id" +
												" left join personal_data ON adm_appln.personal_data_id = personal_data.id" +
												" left join class_schemewise ON student.class_schemewise_id = class_schemewise.id" +
												" left join classes ON class_schemewise.class_id = classes.id" +
												" left join curriculum_scheme_duration ON class_schemewise.curriculum_scheme_duration_id = curriculum_scheme_duration.id" +
												" left join curriculum_scheme ON curriculum_scheme_duration.curriculum_scheme_id = curriculum_scheme.id" +
												" left join course on course.id=classes.course_id" +
												" left join program on course.program_id=program.id" +
												" where c.student_id not in (select student_id from EXAM_student_detention_rejoin_details" +
												" where ((detain=1) or (discontinued=1)) and ((rejoin=0) or (rejoin is null)))" +
												" and c.student_id not in (select student_id from EXAM_update_exclude_withheld where (withheld=1 or exclude_from_results=1))" +
												" and c.student_id not in (select student_id from EXAM_block_unblock_hall_tkt_marks_card where hall_tkt_or_marks_card='M')" +
												" and curriculum_scheme.no_scheme=classes.term_number" +
												" and section is not null" +
												" and (student.is_hide is null or student.is_hide=0)" );
		if(disableStudentLoginForm.getProgramTypeId()!=null && !disableStudentLoginForm.getProgramTypeId().isEmpty()){//Program Type
							query = query.append(" and program.program_type_id="+disableStudentLoginForm.getProgramTypeId());
		}
		if(disableStudentLoginForm.getExamType().equalsIgnoreCase("Regular")){ // for Regular Students
							query = query.append(" and curriculum_scheme_duration.academic_year="+disableStudentLoginForm.getAcademicYear());
		}else if(disableStudentLoginForm.getExamType().equalsIgnoreCase("Supplementary")){ // for Supplementary Students
							query = query.append(" and curriculum_scheme_duration.academic_year<"+disableStudentLoginForm.getAcademicYear() +
									" and c.student_id in (select student_id from EXAM_supplementary_improvement_application" +
									" left join EXAM_definition on EXAM_supplementary_improvement_application.exam_id = EXAM_definition.id" +
									" where (is_appeared_theory=1 or is_appeared_practical=1) and EXAM_definition.academic_year="+disableStudentLoginForm.getAcademicYear()+")");
		}
							query = query.append(" group by c.student_id");
		return query;
	}
}
