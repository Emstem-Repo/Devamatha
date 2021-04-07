package com.kp.cms.transactionsimpl.exam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.exam.ExamMarksVerificationEntryBO;
import com.kp.cms.bo.exam.MarksEntryDetails;
import com.kp.cms.bo.exam.StudentSupplementaryImprovementApplication;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.exam.PendingExamResultsForm;
import com.kp.cms.transactions.admission.IPendingExamResultsTransactions;
import com.kp.cms.utilities.HibernateUtil;


public class PendingExamResultsImpl implements IPendingExamResultsTransactions {
	private static Log log = LogFactory.getLog(PendingExamResultsImpl.class);
	public static volatile PendingExamResultsImpl examCceFactorImpl = null;
	
	public static PendingExamResultsImpl getInstance() {
		if (examCceFactorImpl == null) {
			examCceFactorImpl = new PendingExamResultsImpl();
			return examCceFactorImpl;
		}
		return examCceFactorImpl;
	}
	
	@Override
	public List<Object[]> getPendingExamResultsList(PendingExamResultsForm objForm) throws Exception {
        Session session = null;
        List<Object[]> pendingResults = new ArrayList<Object[]>();
        String[] examName=objForm.getSelectExam();
             
        try {
        	session = HibernateUtil.getSession();
        	for(int i=0;i<examName.length;i++){
        		List<Object[]> list =null;
        if(examName[i]!=null && !examName[i].isEmpty()){
			String programHibernateQuery = " SELECT ed.id,cl.id as cid,ed.name,cl.name as cname,cs.year" +
					" FROM EXAM_definition ed" +
					" INNER JOIN EXAM_exam_course_scheme_details ecsc on ecsc.exam_id = ed.id " +
					" and ecsc.is_active=1 " +
					" and ed.del_is_active=1 " +
					" INNER JOIN course co ON ecsc.course_id = co.id " +
					" and co.is_active=1 " +
					" INNER JOIN classes cl on cl.course_id = co.id " +
					" and cl.is_active=1 " +
					" and cl.term_number =ecsc.scheme_no " +
					" and cl.id not in (select class_id from EXAM_publish_hall_ticket_marks_card epht where publish_for='Marks Card' and exam_id=ed.id and is_active=1) " +
					" inner join exam_type et ON ed.exam_type_id = et.id " +
					" and et.id not in (4,5) " +
					" inner join curriculum_scheme cs on cs.course_id = co.id " +
					" inner join curriculum_scheme_duration cus ON cus.curriculum_scheme_id = cs.id  " +
					" and cus.semester_year_no=cl.term_number " +
					" inner join class_schemewise on class_schemewise.class_id = cl.id " +
					" and class_schemewise.curriculum_scheme_duration_id = cus.id " +
					" where (if(et.name like '%Regular%',(cus.academic_year=ed.academic_year),(cus.academic_year<=ed.academic_year and cs.year>=ed.exam_for_joining_batch ))) " +
			        " and ed.id="+examName[i];
			      if(!objForm.getExamTypeName().contains("Regular")){
						programHibernateQuery=programHibernateQuery+" and cl.id in (select class_id from EXAM_supplementary_improvement_application where exam_id=ed.id and ((is_appeared_theory=1) or (is_appeared_practical=1))) " +
						                                            " order by ed.id,cs.year,cl.id";
				   }else{
						programHibernateQuery=programHibernateQuery+" and cl.id in (select distinct class_schemewise.class_id from student"+
							" inner join class_schemewise ON student.class_schemewise_id = class_schemewise.id "+
							" where class_id=cl.id "+
							" union all "+
							" select distinct class_id from EXAM_student_previous_class_details "+
							" where class_id=cl.id ) order by ed.id,cs.year,cl.id ";
                       }
			list = session.createSQLQuery(programHibernateQuery).list();
			pendingResults.addAll(list);
        		 }
       	}
        	return pendingResults;
        }catch (Exception e) {
			if (session != null){
				session.flush();
			}	
			throw  new ApplicationException(e);
        }
  }

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.IPendingExamResultsTransactions#getTotalStudents(java.lang.String)
	 */
	@Override
	public List<StudentSupplementaryImprovementApplication> getTotalStudents(String query)	throws Exception {
		Session session=null;
		List<StudentSupplementaryImprovementApplication> list=new ArrayList<StudentSupplementaryImprovementApplication>();
		try {
			session=HibernateUtil.getSession();
			Query viewsetail=session.createQuery(query);
			list=viewsetail.list();
			return list;
			
		} catch (Exception e) {
			log.error("Error while retrieving selected candidates.." +e);
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		return list;
	}


//	@Override
//	public boolean addPendingExamResults(List<PendingExamResults> pendingExamResults, ActionErrors errors,PendingExamResultsForm objForm) {
//        Session session;
//        Transaction transaction;
//        session = null;
//        transaction = null;
//          Iterator<PendingExamResults> itr=pendingExamResults.iterator();
//       try{
//    	  while (itr.hasNext()) {
//    		     PendingExamResults pendingExam = (PendingExamResults) itr.next();
//			     session=HibernateUtil.getSession();
//			transaction=session.beginTransaction();
//			transaction.begin();
//			session.save(pendingExam);
//    	  }
//		transaction.commit();
//		}catch(Exception e){
//				if (transaction != null)
//				transaction.rollback();
//				session.close();
//			log.debug("Error during saving data...", e);
//		}finally{
//			session.flush();
//			session.close();
//		}
//        return true;
//    }
	
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.IPendingExamResultsTransactions#getTotalVerfiedStudentForSupplementary(java.lang.String, java.lang.String, boolean, java.lang.Integer)
	 */
	public List<Object[]> getTotalVerfiedStudentForSupplementary(String examId,String classId,
			boolean absent, Integer internalExamId) throws Exception {
		Session session = null;
		List<Object[]> list;
		try {

			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			String SQL = " SELECT subject.name AS subName, subject.id AS subId, count(student.id) AS total_students, count(EXAM_marks_entry_details.id) AS total_entered," +
					" count(EXAM_marks_verification_details.id) AS total_verified, EXAM_subject_rule_settings_mul_evaluator.evaluator_id AS eTypeId," +
					" EXAM_definition.name AS examName, course.name AS courseName, subject.code AS subCode, course.id AS courseId," +
					" ifnull(EXAM_subject_rule_settings.theory_ese_is_multiple_evaluator, 0) AS theory_multiple," +
					" ifnull(EXAM_subject_rule_settings.practical_ese_is_multiple_evaluator,0) AS practical_multiple," +
					" subject.is_theory_practical AS isTheory,subject.is_certificate_course AS certificatecourse, " +
					" if(subject.is_theory_practical='T',if(((theory_ese_entered_max_mark is null or theory_ese_entered_max_mark='') and final_theory_internal_maximum_mark > 0),1,0), " +
					"	if(subject.is_theory_practical='P',if(((practical_ese_entered_max_mark is null or practical_ese_entered_max_mark='') and final_practical_internal_maximum_mark > 0),1,0),0)) as internal_subject" +
					"  FROM  EXAM_supplementary_improvement_application" +
					" INNER JOIN EXAM_definition ON EXAM_supplementary_improvement_application.exam_id = EXAM_definition.id" +
					" INNER JOIN student ON EXAM_supplementary_improvement_application.student_id = student.id" +
					" INNER JOIN classes ON EXAM_supplementary_improvement_application.class_id = classes.id" +
					" INNER JOIN course ON classes.course_id = course.id" +
					" INNER JOIN class_schemewise ON class_schemewise.class_id = classes.id" +
					" INNER JOIN curriculum_scheme_duration ON curriculum_scheme_duration.id = class_schemewise.curriculum_scheme_duration_id" +
					" INNER JOIN  subject ON EXAM_supplementary_improvement_application.subject_id = subject.id" +
					" LEFT JOIN EXAM_subject_rule_settings  ON curriculum_scheme_duration.academic_year = EXAM_subject_rule_settings.academic_year" +
					" AND EXAM_subject_rule_settings.subject_id = subject.id" +
					" AND EXAM_subject_rule_settings.scheme_no = classes.term_number" +
					" AND EXAM_subject_rule_settings.course_id = course.id" +
					" LEFT JOIN EXAM_subject_rule_settings_mul_evaluator ON EXAM_subject_rule_settings_mul_evaluator.subject_rule_settings_id = EXAM_subject_rule_settings.id" +
					" LEFT JOIN EXAM_marks_entry ON EXAM_marks_entry.exam_id = EXAM_definition.id" +
					" AND EXAM_marks_entry.student_id = student.id AND EXAM_marks_entry.class_id = classes.id" +
					" AND (if (EXAM_subject_rule_settings_mul_evaluator.evaluator_id IS NULL, EXAM_marks_entry.evaluator_type_id IS NULL, EXAM_marks_entry.evaluator_type_id =" +
					" EXAM_subject_rule_settings_mul_evaluator.evaluator_id))" +
					" LEFT JOIN EXAM_marks_entry_details ON EXAM_marks_entry_details.marks_entry_id = EXAM_marks_entry.id " +
					" AND EXAM_marks_entry_details.subject_id = subject.id" +
					" AND if(EXAM_supplementary_improvement_application.is_appeared_theory = 1, " +
					" (EXAM_marks_entry_details.theory_marks IS NOT NULL  AND trim(EXAM_marks_entry_details.theory_marks) <> ''), " +
					" if(EXAM_supplementary_improvement_application.is_appeared_practical = 1 , " +
					" (EXAM_marks_entry_details.practical_marks IS NOT NULL  AND trim(EXAM_marks_entry_details.practical_marks) <> ''),TRUE)) " +  
					" LEFT JOIN EXAM_marks_verification_details ON EXAM_marks_verification_details.exam_id = EXAM_definition.id" +
					" AND EXAM_marks_verification_details.student_id = student.id AND EXAM_marks_verification_details.subject_id = subject.id" +
					" AND (if (EXAM_marks_entry.evaluator_type_id IS NULL, EXAM_marks_verification_details.evaluator_type_id IS NULL," +
					" EXAM_marks_verification_details.evaluator_type_id = EXAM_marks_entry.evaluator_type_id))" +
					" LEFT JOIN EXAM_student_detention_rejoin_details ON EXAM_student_detention_rejoin_details.student_id = student.id" +
					" AND EXAM_student_detention_rejoin_details.class_schemewise_id = class_schemewise.id " ;
					//" WHERE  classes.term_number = "+termNumber;
					if(internalExamId != null && internalExamId !=0){
						SQL = SQL + " WHERE  if(subject.is_theory_practical='T',if(((theory_ese_entered_max_mark is null or theory_ese_entered_max_mark='') and final_theory_internal_maximum_mark > 0),EXAM_definition.id = " +internalExamId+",EXAM_definition.id = " +examId+"), " +
								" if(subject.is_theory_practical='P',if(((practical_ese_entered_max_mark is null or practical_ese_entered_max_mark='') and final_practical_internal_maximum_mark > 0),EXAM_definition.id = " +internalExamId+",EXAM_definition.id = " +examId+"),EXAM_definition.id = " +examId+"))";
					}else{
						SQL = SQL + " WHERE  EXAM_definition.id=" +examId;
					}
					/*if(courseId != null && !courseId.trim().isEmpty()){
						SQL = SQL +" and course.id= "+courseId;
					}*/
					if(absent){
						SQL = SQL + " and (EXAM_marks_entry_details.theory_marks='AA' OR EXAM_marks_entry_details.theory_marks='NP')";
					}
					
					SQL = SQL + " AND (EXAM_supplementary_improvement_application.is_appeared_theory = 1 OR EXAM_supplementary_improvement_application.is_appeared_practical = 1)" +
					" AND EXAM_supplementary_improvement_application.class_id="+classId+
					" GROUP BY course.id, subject.id, EXAM_subject_rule_settings_mul_evaluator.evaluator_id  ";
			
			
			Query query = session.createSQLQuery(SQL);

			list = query.list();

			session.flush();
			// session.close();
		} catch (Exception e) {

			if (session != null) {
				session.flush();
				session.close();
			}
			list = new ArrayList<Object[]>();
		}
		return list;
	}
	
	
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.IPendingExamResultsTransactions#getTotalVerfiedStudent(java.lang.String, java.lang.String, java.lang.Boolean, boolean, java.lang.Integer)
	 */
	public List<Object[]> getTotalVerfiedStudent(String examId ,String classId,Boolean finalYears,boolean absent, Integer internalExamId) throws Exception{

		Session session = null;
		List<Object[]> list;
		try {

			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			String SQL = "select subName, subId,sum(total_students) as total_students, " +
			" sum(total_entered) as total_entered, sum(total_verified) as total_verified,eTypeId,examName,courseName,subCode,courseId, theory_multiple, practical_multiple, isTheory, certificatecourse,internal_subject " +
			"	from ( " +
			" select subject.name as subName,subject.id as subId, count(student.id) as total_students," +
			" count(EXAM_marks_entry_details.id) as total_entered, count(EXAM_marks_verification_details.id) as total_verified, EXAM_subject_rule_settings_mul_evaluator.evaluator_id as eTypeId, EXAM_definition.name as examName, course.name as courseName, subject.code as subCode,course.id as courseId," +
			" ifnull(EXAM_subject_rule_settings.theory_ese_is_multiple_evaluator, 0) as theory_multiple, ifnull(EXAM_subject_rule_settings.practical_ese_is_multiple_evaluator, 0) as practical_multiple, subject.is_theory_practical as isTheory,subject.is_certificate_course As certificatecourse, "+
			" if(subject.is_theory_practical='T',if(((theory_ese_entered_max_mark is null or theory_ese_entered_max_mark='') and final_theory_internal_maximum_mark > 0),1,0), " +
			"	if(subject.is_theory_practical='P',if(((practical_ese_entered_max_mark is null or practical_ese_entered_max_mark='') and final_practical_internal_maximum_mark > 0),1,0),0)) as internal_subject" +
			" from student" +
			" inner join adm_appln ON student.adm_appln_id = adm_appln.id" +
			" inner join personal_data ON adm_appln.personal_data_id = personal_data.id" +
			" inner join applicant_subject_group on applicant_subject_group.adm_appln_id = adm_appln.id" +
			" inner join subject_group ON applicant_subject_group.subject_group_id = subject_group.id" +
			" inner join subject_group_subjects on subject_group_subjects.subject_group_id = subject_group.id" +
			" inner join subject ON subject_group_subjects.subject_id = subject.id " +
			" inner join class_schemewise ON student.class_schemewise_id = class_schemewise.id " +
			" inner join curriculum_scheme_duration ON class_schemewise.curriculum_scheme_duration_id = curriculum_scheme_duration.id " +
			" inner join classes ON class_schemewise.class_id = classes.id " +
			" inner join course ON adm_appln.selected_course_id = course.id " +
			" inner join EXAM_exam_course_scheme_details on EXAM_exam_course_scheme_details.course_id = course.id " +
			" and classes.term_number = EXAM_exam_course_scheme_details.scheme_no" +
			" inner join EXAM_definition ON EXAM_exam_course_scheme_details.exam_id = EXAM_definition.id" +
			" left join EXAM_subject_rule_settings on EXAM_subject_rule_settings.academic_year=curriculum_scheme_duration.academic_year" +
			" and EXAM_subject_rule_settings.subject_id=subject.id " +
			" and EXAM_subject_rule_settings.scheme_no=classes.term_number " +
			" and EXAM_subject_rule_settings.course_id=course.id" +
			" left join EXAM_subject_rule_settings_mul_evaluator on EXAM_subject_rule_settings_mul_evaluator.subject_rule_settings_id=EXAM_subject_rule_settings.id"+
			" left join EXAM_marks_entry on EXAM_marks_entry.exam_id = EXAM_definition.id" +
			" and EXAM_marks_entry.student_id = student.id and EXAM_marks_entry.class_id = classes.id" +
			" and (if(EXAM_subject_rule_settings_mul_evaluator.evaluator_id is null,  EXAM_marks_entry.evaluator_type_id IS NULL, EXAM_marks_entry.evaluator_type_id = EXAM_subject_rule_settings_mul_evaluator.evaluator_id))"+
			" left join EXAM_marks_entry_details on EXAM_marks_entry_details.marks_entry_id = EXAM_marks_entry.id " +
			" and EXAM_marks_entry_details.subject_id = subject.id " +
			" AND if(subject.is_theory_practical = 'B', " +
			" (EXAM_marks_entry_details.theory_marks IS NOT NULL " +
			" AND EXAM_marks_entry_details.practical_marks IS NOT NULL " +
			" AND trim(EXAM_marks_entry_details.theory_marks) <> '' " +
			" AND trim(EXAM_marks_entry_details.practical_marks) <>''), " +
			" if(subject.is_theory_practical = 'T' , " +
			" (EXAM_marks_entry_details.theory_marks IS NOT NULL  AND trim(EXAM_marks_entry_details.theory_marks) <> ''), " +
			" if(subject.is_theory_practical = 'P' , " +
			" (EXAM_marks_entry_details.practical_marks IS NOT NULL  AND trim(EXAM_marks_entry_details.practical_marks) <> ''),TRUE)))"+ 
//			" left join EXAM_student_detention_rejoin_details on EXAM_student_detention_rejoin_details.student_id = student.id and EXAM_student_detention_rejoin_details.class_schemewise_id = class_schemewise.id" +
			" left join EXAM_marks_verification_details on EXAM_marks_verification_details.exam_id = EXAM_definition.id" +
			" and EXAM_marks_verification_details.student_id = student.id and EXAM_marks_verification_details.subject_id = subject.id" +
			" and (if(EXAM_marks_entry.evaluator_type_id is null, EXAM_marks_verification_details.evaluator_type_id IS NULL, EXAM_marks_verification_details.evaluator_type_id = EXAM_marks_entry.evaluator_type_id))"+
			" where curriculum_scheme_duration.academic_year = EXAM_definition.academic_year ";
			if( internalExamId != null && internalExamId !=0 ){
				SQL = SQL + " AND if(subject.is_theory_practical='T',if(((theory_ese_entered_max_mark is null or theory_ese_entered_max_mark='') and final_theory_internal_maximum_mark > 0),EXAM_definition.id = " +internalExamId+",EXAM_definition.id = " +examId+"), " +
						" if(subject.is_theory_practical='P',if(((practical_ese_entered_max_mark is null or practical_ese_entered_max_mark='') and final_practical_internal_maximum_mark > 0),EXAM_definition.id = " +internalExamId+",EXAM_definition.id = " +examId+"),EXAM_definition.id = " +examId+"))";
			}else{
				SQL = SQL + " AND EXAM_definition.id=" +examId;
			}
			/*if(finalYears)
				SQL=SQL+" and classes.term_number=(select no_scheme from curriculum_scheme where course_id=course.id and year=adm_appln.applied_year)";
			else SQL=SQL+" and classes.term_number=" +termNumber;
			if(courseId != null && !courseId.trim().isEmpty()){
				
				SQL = SQL +" and course.id= "+courseId;
			}*/
			if(absent){
				SQL = SQL + " and (EXAM_marks_entry_details.theory_marks='AA' OR EXAM_marks_entry_details.theory_marks='NP')";
			}
			SQL = SQL +	/*" and (EXAM_student_detention_rejoin_details.rejoin is null or EXAM_student_detention_rejoin_details.rejoin=0)" +*/
//			" and (EXAM_student_detention_rejoin_details.detain IS NULL OR EXAM_student_detention_rejoin_details.detain = 0)" +
//			" and (EXAM_student_detention_rejoin_details.discontinued IS NULL OR EXAM_student_detention_rejoin_details.discontinued = 0)" +
			" and adm_appln.is_cancelled=0 and student.is_admitted=1 and (student.is_hide=0 or student.is_hide is null) and subject.is_active=1 and subject_group_subjects.is_active=1 and subject_group.is_active=1" +
			" and student.id not in (select student_id from EXAM_student_detention_rejoin_details where (detain=1 or discontinued=1) and (rejoin is null or rejoin=0))" +
			" group by course.id, subject.id, EXAM_subject_rule_settings_mul_evaluator.evaluator_id"+
			" union all" +
			" select subject.name as subName,subject.id as subId, count(student.id) as total_students," +
			" count(EXAM_marks_entry_details.id) as total_entered, count(EXAM_marks_verification_details.id) as total_verified, EXAM_subject_rule_settings_mul_evaluator.evaluator_id as eTypeId, EXAM_definition.name as examName, course.name as courseName, subject.code as subCode,course.id as courseId," +
			" ifnull(EXAM_subject_rule_settings.theory_ese_is_multiple_evaluator, 0) as theory_multiple, ifnull(EXAM_subject_rule_settings.practical_ese_is_multiple_evaluator, 0) as practical_multiple, subject.is_theory_practical as isTheory,subject.is_certificate_course As certificatecourse,"+
			" if(subject.is_theory_practical='T',if(((theory_ese_entered_max_mark is null or theory_ese_entered_max_mark='') and final_theory_internal_maximum_mark > 0),1,0), " +
			"	if(subject.is_theory_practical='P',if(((practical_ese_entered_max_mark is null or practical_ese_entered_max_mark='') and final_practical_internal_maximum_mark > 0),1,0),0)) as internal_subject" +
			" from student" +
			" inner join adm_appln ON student.adm_appln_id = adm_appln.id" +
			" inner join personal_data ON adm_appln.personal_data_id = personal_data.id" +
			" inner join EXAM_student_sub_grp_history on EXAM_student_sub_grp_history.student_id = student.id" +
			" inner join EXAM_student_previous_class_details on EXAM_student_previous_class_details.student_id = student.id" +
			" and EXAM_student_previous_class_details.scheme_no = EXAM_student_sub_grp_history.scheme_no" +
			" inner join subject_group ON EXAM_student_sub_grp_history.subject_group_id = subject_group.id" +
			" inner join subject_group_subjects on subject_group_subjects.subject_group_id = subject_group.id" +
			" inner join subject ON subject_group_subjects.subject_id = subject.id" +
			" inner join classes ON EXAM_student_previous_class_details.class_id = classes.id" +
			" inner join class_schemewise on class_schemewise.class_id = classes.id" +
			" inner join curriculum_scheme_duration ON class_schemewise.curriculum_scheme_duration_id = curriculum_scheme_duration.id" +
			" inner join course ON classes.course_id = course.id" +
			" inner join EXAM_exam_course_scheme_details on EXAM_exam_course_scheme_details.course_id = course.id" +
			" and EXAM_student_sub_grp_history.scheme_no = EXAM_exam_course_scheme_details.scheme_no" +
			" inner join EXAM_definition ON EXAM_exam_course_scheme_details.exam_id = EXAM_definition.id" +
			" left join EXAM_subject_rule_settings on EXAM_subject_rule_settings.academic_year=curriculum_scheme_duration.academic_year" +
			" and EXAM_subject_rule_settings.subject_id=subject.id " +
			" and EXAM_subject_rule_settings.scheme_no=classes.term_number " +
			" and EXAM_subject_rule_settings.course_id=course.id" +
			" left join EXAM_subject_rule_settings_mul_evaluator on EXAM_subject_rule_settings_mul_evaluator.subject_rule_settings_id=EXAM_subject_rule_settings.id"+
			" left join EXAM_marks_entry on EXAM_marks_entry.exam_id = EXAM_definition.id" +
			" and EXAM_marks_entry.student_id = student.id and EXAM_marks_entry.class_id = classes.id" +
			" and (if(EXAM_subject_rule_settings_mul_evaluator.evaluator_id is null,  EXAM_marks_entry.evaluator_type_id IS NULL, EXAM_marks_entry.evaluator_type_id = EXAM_subject_rule_settings_mul_evaluator.evaluator_id))"+
			" left join EXAM_marks_entry_details on EXAM_marks_entry_details.marks_entry_id = EXAM_marks_entry.id" +
			" and EXAM_marks_entry_details.subject_id = subject.id" +
			" AND if(subject.is_theory_practical = 'B', " +
			" (EXAM_marks_entry_details.theory_marks IS NOT NULL " +
			" AND EXAM_marks_entry_details.practical_marks IS NOT NULL " +
			" AND trim(EXAM_marks_entry_details.theory_marks) <> '' " +
			" AND trim(EXAM_marks_entry_details.practical_marks) <>''), " +
			" if(subject.is_theory_practical = 'T' , " +
			" (EXAM_marks_entry_details.theory_marks IS NOT NULL  AND trim(EXAM_marks_entry_details.theory_marks) <> ''), " +
			" if(subject.is_theory_practical = 'P' , " +
			" (EXAM_marks_entry_details.practical_marks IS NOT NULL  AND trim(EXAM_marks_entry_details.practical_marks) <> ''),TRUE)))"+ 
//			" left join EXAM_student_detention_rejoin_details on EXAM_student_detention_rejoin_details.student_id = student.id and EXAM_student_detention_rejoin_details.class_schemewise_id = class_schemewise.id" +
			" left join EXAM_marks_verification_details on EXAM_marks_verification_details.exam_id = EXAM_definition.id" +
			" and EXAM_marks_verification_details.student_id = student.id and EXAM_marks_verification_details.subject_id = subject.id" +
			" and (if(EXAM_marks_entry.evaluator_type_id is null, EXAM_marks_verification_details.evaluator_type_id IS NULL, EXAM_marks_verification_details.evaluator_type_id = EXAM_marks_entry.evaluator_type_id))"+
			" where curriculum_scheme_duration.academic_year = EXAM_definition.academic_year ";
			if(internalExamId != null && internalExamId !=0){
				SQL = SQL + " AND if(subject.is_theory_practical='T',if(((theory_ese_entered_max_mark is null or theory_ese_entered_max_mark='') and final_theory_internal_maximum_mark > 0),EXAM_definition.id = " +internalExamId+",EXAM_definition.id = " +examId+"), " +
						" if(subject.is_theory_practical='P',if(((practical_ese_entered_max_mark is null or practical_ese_entered_max_mark='') and final_practical_internal_maximum_mark > 0),EXAM_definition.id = " +internalExamId+",EXAM_definition.id = " +examId+"),EXAM_definition.id = " +examId+"))";
			}else{
				SQL = SQL + " AND EXAM_definition.id=" +examId;
			}
			if(finalYears)
				SQL=SQL+" and EXAM_student_previous_class_details.scheme_no=(select no_scheme from curriculum_scheme where course_id=course.id and year=adm_appln.applied_year)";
			/*else SQL=SQL+" and EXAM_student_previous_class_details.scheme_no=" +termNumber;
			if(courseId != null && !courseId.trim().isEmpty()){
				
				SQL = SQL +" and course.id= "+courseId;
			}*/
			if(absent){
				SQL = SQL + " and (EXAM_marks_entry_details.theory_marks='AA' OR EXAM_marks_entry_details.theory_marks='NP')";
			}
			SQL = SQL +	/*" and (EXAM_student_detention_rejoin_details.rejoin is null or EXAM_student_detention_rejoin_details.rejoin=0)" +*/
//			" and (EXAM_student_detention_rejoin_details.detain IS NULL OR EXAM_student_detention_rejoin_details.detain = 0)" +
//			" and (EXAM_student_detention_rejoin_details.discontinued IS NULL OR EXAM_student_detention_rejoin_details.discontinued = 0)" +
			" and adm_appln.is_cancelled=0 and student.is_admitted=1 and (student.is_hide=0 or student.is_hide is null) and subject.is_active=1 and subject_group_subjects.is_active=1 and subject_group.is_active=1" +
			" and student.id not in (select student_id from EXAM_student_detention_rejoin_details where (detain=1 or discontinued=1) and (rejoin is null or rejoin=0))" +
			" group by course.id, subject.id, EXAM_subject_rule_settings_mul_evaluator.evaluator_id ) as exam_valuation " +
			" group by courseId,subId,eTypeId";
			
			
			Query query = session.createSQLQuery(SQL);

			list = query.list();

			session.flush();
			// session.close();
		} catch (Exception e) {

			if (session != null) {
				session.flush();
				session.close();
			}
			list = new ArrayList<Object[]>();
		}
		return list;
	}
	
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.IPendingExamResultsTransactions#checkMismatchFoundForStudents(java.util.List, java.util.List, com.kp.cms.forms.exam.PendingExamResultsForm)
	 */
	public List<Object[]> checkMismatchFoundForStudents( List<Integer> courseListIds, List<Integer> subjectListIds,
			PendingExamResultsForm objForm) throws Exception {
		Session session = null;
		List<Object[]> list = null;
		try{
			List<String> examAbscentCode = CMSConstants.EXAM_ABSCENT_CODE;
			session = HibernateUtil.getSession();
			String str ="select subject.id as subjectId,ed.theory_marks,ed.practical_marks,"+ 
			 " entry.evaluator_type_id,entry.answer_script_type,ed.is_theory_secured," +
			 " verification.vmarks,subject.name,student.register_no,subject.code,student.id as studentId,course.id as courseId,classes.name as className"+
			 " ,personal_data.first_name,classes.id as classId"+
			 " from EXAM_marks_entry_details ed "+
			 " inner JOIN EXAM_marks_entry entry ON ed.marks_entry_id = entry.id "+
			 " inner JOIN classes ON entry.class_id = classes.id "+
			 " inner JOIN course ON classes.course_id = course.id "+
			 " inner JOIN student ON entry.student_id = student.id "+
			 " inner JOIN subject ON ed.subject_id = subject.id "+
			 " inner Join adm_appln ON student.adm_appln_id = adm_appln.id "+
			 " inner Join personal_data ON adm_appln.personal_data_id = personal_data.id "+
			 " inner JOIN EXAM_definition ON entry.exam_id = EXAM_definition.id "+
			 " LEFT JOIN EXAM_marks_verification_details verification ON "+
			 " (verification.exam_id = EXAM_definition.id AND verification.student_id = student.id AND verification.subject_id = subject.id "+
			 " and if(entry.evaluator_type_id is null, true, entry.evaluator_type_id=verification.evaluator_type_id) "+
			 " and if(entry.answer_script_type is null, true, entry.answer_script_type=verification.answer_script_type)) "+
			 " WHERE  EXAM_definition.id="+objForm.getExamId() +
			// " AND subject.id in (:subjectIds) "+
			// " AND EXAM_definition.academic_year>=2012 "+			
			 " AND course.id in (:courseIds)" +
			 " AND ed.is_theory_secured=1 AND (verification.id is null OR verification.vmarks != ed.theory_marks) AND ed.theory_marks not in (:examAbscentCode)"+
			 " AND adm_appln.is_cancelled=0 and student.is_admitted=1 and (student.is_hide=0 or student.is_hide is null)  and subject.is_active=1 "+
			 " AND student.id not in (select student_id from EXAM_student_detention_rejoin_details where (detain=1 or discontinued=1) and (rejoin is null or rejoin=0))"+
			 " GROUP BY entry.evaluator_type_id,entry.answer_script_type,subject.id,student.id "+
			 " ORDER BY subject.id";
			Query query = session.createSQLQuery(str);
			query.setParameterList("examAbscentCode", examAbscentCode);
			query.setParameterList("courseIds", courseListIds);
			//query.setParameterList("subjectIds", subjectListIds);
			list = query.list();
		}catch (Exception e) {
			log.error("Error at this method checkMismatchFoundForStudents" +e);
			e.printStackTrace();
		}
		return list;
	}
	
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.IPendingExamResultsTransactions#getDetailsForView(com.kp.cms.forms.exam.PendingExamResultsForm)
	 */
	public List<MarksEntryDetails> getDetailsForView(PendingExamResultsForm objForm) throws Exception {

		Session session = null;
		List<MarksEntryDetails> list = null;
		try{
			session = HibernateUtil.getSession();
			String subjectName = (String)session.createQuery("select concat(s.code,' - ',s.name) from Subject s where s.id="+objForm.getSubjectId()).uniqueResult();
			objForm.setSubjectName(subjectName);
			String hql = "select m from MarksEntryDetails m "+
							" where m.marksEntry.exam.id = " +objForm.getExamId() +
							" and m.subject.id="+objForm.getSubjectId();
			if(objForm.getCourseId() != null && !objForm.getCourseId().trim().isEmpty()){
				hql = hql + " and m.marksEntry.classes.course.id="+objForm.getCourseId(); 
			}
			if(objForm.getEvaluatorTypeId() != null && !objForm.getEvaluatorTypeId().trim().isEmpty()){
				int evaluatorType = Integer.parseInt(objForm.getEvaluatorTypeId());
				if(evaluatorType != 0){
					hql = hql + " and m.marksEntry.evaluatorType= "+evaluatorType;
				}
			}
			Query query = session.createQuery(hql);
			list = query.list();
			session.flush();
		}catch (Exception e) {
			if (session != null) {
				session.flush();
				session.close();
			}
			list = new ArrayList<MarksEntryDetails>();
		}
		return list;
	
	}
	
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.IPendingExamResultsTransactions#getTotalStudentsForVerifyDetails(java.lang.String)
	 */
	public List<Student> getTotalStudentsForVerifyDetails(String query) throws Exception{
		Session session = null;
		List<Student> list = new ArrayList<Student>();
		try {
			session = HibernateUtil.getSession();
			Query selectedCandidatesQuery=session.createQuery(query);
			list= selectedCandidatesQuery.list();
//			list = session.createQuery("select s from Student s where s.id in(:studentIds) group by s.id").setParameterList("studentIds", studentIds).list();
			return list;
		} catch (Exception e) {
			log.error("Error while retrieving selected candidates.." +e);
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		return list;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.IPendingExamResultsTransactions#getTotalStudents1(java.lang.String)
	 */
	public List<Object[]> getTotalStudents1(String query) throws Exception{
		Session session = null;
		List<Object[]> list = new ArrayList<Object[]>();
		try {
			session = HibernateUtil.getSession();
			Query selectedCandidatesQuery=session.createQuery(query);
			list = selectedCandidatesQuery.list();
			return list;
		} catch (Exception e) {
			log.error("Error while retrieving selected candidates.." +e);
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		return list;
	}
	
	
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.IPendingExamResultsTransactions#getVerificationDetailsForView(com.kp.cms.forms.exam.PendingExamResultsForm)
	 */
	public Map<Integer,String> getVerificationDetailsForView(PendingExamResultsForm objForm) throws Exception {

		Session session = null;
		Map<Integer,String> stuMap = new HashMap<Integer, String>();
		try{
			session = HibernateUtil.getSession();
			int evaluatorType = 0;
			String subjectName = (String)session.createQuery("select concat(s.code,' - ',s.name) from Subject s where s.id="+objForm.getSubjectId()).uniqueResult();
			objForm.setSubjectName(subjectName);
			String hql = "select m from ExamMarksVerificationEntryBO m "+
							" where m.examId = " +objForm.getExamId() +
							" and m.subjectId="+objForm.getSubjectId();
			if(objForm.getEvaluatorTypeId() != null && !objForm.getEvaluatorTypeId().trim().isEmpty()){
				evaluatorType = Integer.parseInt(objForm.getEvaluatorTypeId());
				if(evaluatorType != 0){
					hql = hql + " and m.evaluatorTypeId= "+evaluatorType;
				}
			}
			hql = hql + " group by m.studentId";
			Query query = session.createQuery(hql);
			List<ExamMarksVerificationEntryBO> list = query.list();
			if(list != null){
				Iterator<ExamMarksVerificationEntryBO> iterator = list.iterator();
				while (iterator.hasNext()) {
					ExamMarksVerificationEntryBO examMarksVerificationEntryBO = (ExamMarksVerificationEntryBO) iterator.next();
					if(examMarksVerificationEntryBO.getStudentId() != null )
					stuMap.put(examMarksVerificationEntryBO.getStudentId(), examMarksVerificationEntryBO.getVmarks());
				}
			}
			String hqlQuery = "select m from MarksEntryDetails m " +
					" where  m.theoryMarks='AA' and m.marksEntry.exam.id= " +objForm.getExamId() +
					" and m.subject.id= " +objForm.getSubjectId();
			if(evaluatorType != 0){
				hqlQuery = hqlQuery + " and m.marksEntry.evaluatorType= "+evaluatorType;
			}
			List<MarksEntryDetails> marksEntryDetails = session.createQuery(hqlQuery).list();
			if(marksEntryDetails != null){
				Iterator<MarksEntryDetails> iterator = marksEntryDetails.iterator();
				while (iterator.hasNext()) {
					MarksEntryDetails marksEntryDetails2 = (MarksEntryDetails) iterator.next();
					if(marksEntryDetails2.getMarksEntry() != null && marksEntryDetails2.getMarksEntry().getStudent() != null && marksEntryDetails2.getMarksEntry().getStudent().getId() != 0){
						stuMap.put(marksEntryDetails2.getMarksEntry().getStudent().getId(), marksEntryDetails2.getTheoryMarks());
					}
				}
			}
			session.flush();
		}catch (Exception e) {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		return stuMap;
	
	}
}
