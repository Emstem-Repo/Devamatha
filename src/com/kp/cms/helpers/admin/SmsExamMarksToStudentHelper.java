package com.kp.cms.helpers.admin;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.PersonalData;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.admin.SmsExamMarksToStudentForm;
import com.kp.cms.to.admin.StudentTO;

public class SmsExamMarksToStudentHelper {
	private static volatile SmsExamMarksToStudentHelper smsExamMarksToStudentHelper = null;
	private static final Log log = LogFactory.getLog(SmsExamMarksToStudentHelper.class);
	public SmsExamMarksToStudentHelper()
	{
		
	}
	public static SmsExamMarksToStudentHelper getInstance()
	{
		if(smsExamMarksToStudentHelper==null)
		{
			smsExamMarksToStudentHelper = new SmsExamMarksToStudentHelper();
		}
		return smsExamMarksToStudentHelper;
	}
	public List<StudentTO> convertBOtoTOList(List<Student> studentsBO) throws Exception{
        log.debug("call of convertBOtoTOList method in SmsExamMarksToStudentHelper.class");
        List<StudentTO> studentList =  new ArrayList<StudentTO>();
        try
        {
        	if(studentsBO!=null && !studentsBO.isEmpty())
        	{
        		Iterator<Student> iterator = studentsBO.iterator();
        		while(iterator.hasNext())
        		{
        			Student student = iterator.next();
        			StudentTO studentTO=new StudentTO();
        			studentTO.setId(student.getId());
        			studentTO.setClassId(student.getClassSchemewise().getId());
        			studentTO.setClassName(student.getClassSchemewise().getClasses().getName());
    				PersonalData data=student.getAdmAppln().getPersonalData();
    				if(data.getParentMob1()!=null){
    					studentTO.setMobileNo1(data.getParentMob1());
    				}
    				if(data.getParentMob2()!=null){
    					studentTO.setMobileNo2(data.getParentMob2());
    				}
    				String name="";
    				if(data.getFirstName()!=null){
    					name=name+data.getFirstName();
    				}
    				if(data.getMiddleName()!=null){
    					name=name+data.getMiddleName();
    				}
    				if(data.getLastName()!=null){
    					name=name+data.getLastName();
    				}
    				studentTO.setStudentName(name);
    				studentTO.setRegisterNo(student.getRegisterNo());
    				studentTO.setRollNo(student.getRollNo());
    				studentList.add(studentTO);
        		}
        	}
        	
        }
        catch (Exception e) {
			log.error("Error in convertBOtoTOList");
			log.error("Error is.."+e.getMessage());
			throw new ApplicationException(e);
		}
        log.debug("end of convertBOtoTOList method in SmsExamMarksToStudentHelper.class");
		return studentList;
	}
	public String getExamMarksQuery(SmsExamMarksToStudentForm smsExamMarksToStudentForm,StudentTO studentTO, int examTyeId) throws Exception{
		log.debug("call of getExamMarksQuery method in SmsExamMarksToStudentHelper.class");
		String query = " select subject.id as subjectId, "+
			        " subject.code as code,"+
			        " subject.name as subject, "+
					" EXAM_sub_definition_coursewise.subject_order, "+
					" exam_type.name as examtype, "+
					" EXAM_definition.id as examId, "+
			        " convert(EXAM_definition.name,char(50)) as examName, "+
					" EXAM_subject_rule_settings_sub_internal.maximum_mark  as maxmark, "+
					" EXAM_subject_rule_settings_sub_internal.minimum_mark  as minimum, "+
					" if(subject.is_theory_practical='B',if(EXAM_marks_entry_details.theory_marks is not null && EXAM_marks_entry_details.practical_marks is not null,(EXAM_marks_entry_details.theory_marks+EXAM_marks_entry_details.practical_marks),ifnull(EXAM_marks_entry_details.theory_marks,EXAM_marks_entry_details.practical_marks)),ifnull(EXAM_marks_entry_details.theory_marks,EXAM_marks_entry_details.practical_marks)) as obtainedmarks, "+
					" if(subject.is_theory_practical='B', "+
					"(if(EXAM_marks_entry_details.theory_marks is null || EXAM_marks_entry_details.theory_marks='AB',0,EXAM_marks_entry_details.theory_marks)+if(EXAM_marks_entry_details.practical_marks is null || EXAM_marks_entry_details.practical_marks='AB',0,EXAM_marks_entry_details.practical_marks)),if(subject.is_theory_practical='T',if(EXAM_marks_entry_details.theory_marks is null || EXAM_marks_entry_details.theory_marks='AB',0,EXAM_marks_entry_details.theory_marks),if(EXAM_marks_entry_details.practical_marks is null || EXAM_marks_entry_details.practical_marks='AB',0,EXAM_marks_entry_details.practical_marks)) ) as total_marks "+
					" from EXAM_marks_entry "+
					" inner join EXAM_marks_entry_details on EXAM_marks_entry_details.marks_entry_id = EXAM_marks_entry.id "+
					" inner join EXAM_definition ON EXAM_marks_entry.exam_id = EXAM_definition.id "+
					" inner join exam_type ON EXAM_definition.exam_type_id = exam_type.id "+
					" inner join EXAM_internal_exam_type on EXAM_definition.internal_exam_type_id = EXAM_internal_exam_type.id "+
					" inner join classes ON EXAM_marks_entry.class_id = classes.id "+
					" inner join class_schemewise on class_schemewise.class_id = classes.id "+
					" inner join curriculum_scheme_duration ON class_schemewise.curriculum_scheme_duration_id = curriculum_scheme_duration.id "+
					" inner join course ON classes.course_id = course.id "+
					" inner join subject ON EXAM_marks_entry_details.subject_id = subject.id "+
					" inner join student ON EXAM_marks_entry.student_id = student.id "+
					" inner join adm_appln on student.adm_appln_id=adm_appln.id "+
					" inner join personal_data on adm_appln.personal_data_id=personal_data.id "+
					" left join EXAM_subject_rule_settings on  EXAM_subject_rule_settings.course_id = course.id "+
					" and EXAM_subject_rule_settings.subject_id = subject.id "+
					" and EXAM_subject_rule_settings.scheme_no=classes.term_number "+
					" and EXAM_subject_rule_settings.academic_year=curriculum_scheme_duration.academic_year "+
					" left join EXAM_subject_rule_settings_sub_internal on EXAM_subject_rule_settings_sub_internal.subject_rule_settings_id = EXAM_subject_rule_settings.id "+
					" and EXAM_subject_rule_settings_sub_internal.internal_exam_type_id = EXAM_internal_exam_type.id "+
					" left join EXAM_sub_definition_coursewise on EXAM_sub_definition_coursewise.course_id = course.id "+
					" and EXAM_sub_definition_coursewise.subject_id = subject.id "+
					" and EXAM_sub_definition_coursewise.scheme_no=classes.term_number "+
					" and EXAM_subject_rule_settings.academic_year=EXAM_sub_definition_coursewise.academic_year "+
					" where curriculum_scheme_duration.academic_year="+smsExamMarksToStudentForm.getAcademicYear()+""+
					" and  exam_type.id="+examTyeId+""+
					" and EXAM_definition.id="+ smsExamMarksToStudentForm.getExamName()+ ""+ 
					" and classes.id = "+studentTO.getClassId()+""+
					" and student.id="+studentTO.getId()+ ""+
					" GROUP BY student.id,classes.id,EXAM_definition.id,subject.id";
					
		log.debug("end of getExamMarksQuery method in SmsExamMarksToStudentHelper.class");
		
		return query;
	}
	

}
