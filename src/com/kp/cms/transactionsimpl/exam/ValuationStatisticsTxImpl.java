package com.kp.cms.transactionsimpl.exam;

import java.util.ArrayList;
import java.util.Date;
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
import com.kp.cms.bo.admin.Subject;
import com.kp.cms.bo.exam.ExamMarksVerificationEntryBO;
import com.kp.cms.bo.exam.ExamTimeTableBO;
import com.kp.cms.bo.exam.ExamValidationDetails;
import com.kp.cms.bo.exam.ExamValuationProcess;
import com.kp.cms.bo.exam.MarksEntryDetails;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.exam.ValuationStatisticsForm;
import com.kp.cms.handlers.employee.ModifyEmployeeLeaveHandler;
import com.kp.cms.transactions.exam.IValuationStatisticsTransaction;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.HibernateUtil;

public class ValuationStatisticsTxImpl implements IValuationStatisticsTransaction{

	/**
	 * Singleton object of NewExamMarksEntryTransactionImpl
	 */
	private static volatile ValuationStatisticsTxImpl impl = null;
	private static final Log log = LogFactory.getLog(ValuationStatisticsTxImpl.class);
	private ValuationStatisticsTxImpl() {
		
	}
	/**
	 * return singleton object of NewExamMarksEntryTransactionImpl.
	 * @return
	 */
	public static ValuationStatisticsTxImpl getInstance() {
		if (impl == null) {
			impl = new ValuationStatisticsTxImpl();
		}
		return impl;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.exam.IValuationStatisticsTransaction#getStatistics(com.kp.cms.forms.exam.ValuationStatisticsForm)
	 */
	@Override
	public List<Integer> getTotalSubjects(ValuationStatisticsForm valuationStatisticsForm)
			throws Exception {
		Session session = null;
		List<Integer> subjects = new ArrayList<Integer>();
		try{
			session = HibernateUtil.getSession();
			
			List<Integer> schemeNo = session.createQuery("select e.schemeNo from ExamExamCourseSchemeDetailsBO e where e.examId="+valuationStatisticsForm.getExamId()+" group by e.schemeNo").list();
			if(schemeNo !=null){
				valuationStatisticsForm.setSchemeNoList(schemeNo);
				if(valuationStatisticsForm.getExamType() != null && valuationStatisticsForm.getExamType().equalsIgnoreCase("Supplementary")){
					String query = "select subject.id from EXAM_supplementary_improvement_application app " +
							"inner join subject ON app.subject_id = subject.id inner " +
							"join EXAM_definition ON app.exam_id = EXAM_definition.id " +
							"inner join EXAM_time_table ON EXAM_time_table.subject_id = subject.id " +
							"where EXAM_definition.id= " +valuationStatisticsForm.getExamId()+
							" and EXAM_time_table.is_active=1 and app.scheme_no in(:SchemeNO) " +
							"and (app.is_appeared_practical=1 or app.is_appeared_theory=1) " +
							"and EXAM_time_table.date_starttime <curdate() group by subject.id";
					subjects = session.createSQLQuery(query).setParameterList("SchemeNO", schemeNo).list();
				}else if(valuationStatisticsForm.getExamType() != null && valuationStatisticsForm.getExamType().equalsIgnoreCase("Regular")){
					String HQL = "select distinct courseId from ExamExamCourseSchemeDetailsBO e where e.schemeNo in(:schemeNo) and e.examId = "+ valuationStatisticsForm.getExamId();
					List<Integer> courseList = session.createQuery(HQL).setParameterList("schemeNo", schemeNo).list();
					if(courseList != null){
						String sql ="select subject.id from subject_group " +
								"inner join subject_group_subjects ON subject_group_subjects.subject_group_id = subject_group.id " +
								"inner join subject ON subject_group_subjects.subject_id = subject.id " +
								"inner join EXAM_time_table ON EXAM_time_table.subject_id = subject.id " +
								"inner join EXAM_exam_course_scheme_details ON EXAM_time_table.exam_course_scheme_id = EXAM_exam_course_scheme_details.id " +
								"inner join EXAM_definition ON EXAM_exam_course_scheme_details.exam_id = EXAM_definition.id " +
								"inner join course ON subject_group.course_id = course.id " +
								"inner join curriculum_scheme_subject ON curriculum_scheme_subject.subject_group_id = subject_group.id " +
								"inner join curriculum_scheme_duration ON curriculum_scheme_subject.curriculum_scheme_duration_id = curriculum_scheme_duration.id " +
								"inner join class_schemewise ON class_schemewise.curriculum_scheme_duration_id = curriculum_scheme_duration.id " +
								"inner join classes ON class_schemewise.class_id = classes.id " +
								"where course.id in(:Courses) and classes.term_number in (:SchemeNo) " +
								"and curriculum_scheme_duration.academic_year=EXAM_definition.academic_year and EXAM_definition.id=" +valuationStatisticsForm.getExamId()+
								" and subject.is_active=1 and subject_group.is_active=1 and subject_group_subjects.is_active=1 " +
								"and EXAM_time_table.date_starttime<curdate() and EXAM_time_table.is_active=1 group by subject.id";
						Query subjectQuery = session.createSQLQuery(sql);
						subjectQuery.setParameterList("Courses", courseList).setParameterList("SchemeNo", schemeNo);
						subjects = subjectQuery.list();
					}
				}else{
					String HQL = "select distinct courseId from ExamExamCourseSchemeDetailsBO e where e.schemeNo in(:schemeNo) and e.examId = "+ valuationStatisticsForm.getExamId();
					List<Integer> courseList = session.createQuery(HQL).setParameterList("schemeNo", schemeNo).list();
					if(courseList != null){
						String query = "select groupsub.subject.id from SubjectGroup sub " +
						" join sub.curriculumSchemeSubjects curr  " +
						" join curr.curriculumSchemeDuration cd"+
						" join cd.classSchemewises cls"+
						" join sub.subjectGroupSubjectses groupsub" +
						" where sub.course.id in(:Courses) " +
						" and sub.isActive=1 and groupsub.isActive=1 and groupsub.subject.isActive=1 and cls.classes.termNumber in (:SchemeNo)" +
						" and cd.academicYear=(select e.academicYear from ExamDefinitionBO e where e.id= "+ valuationStatisticsForm.getExamId() +
						") group by groupsub.subject.id";
						Query subjectQuery = session.createQuery(query);
						subjectQuery.setParameterList("Courses", courseList).setParameterList("SchemeNo", schemeNo);
						subjects = subjectQuery.list();
					}
				}
			}
			return subjects;
		}catch (Exception e) {
			if(session!=null){
				session.flush();
				session.close();
			}
		}
		return subjects;
		
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.exam.IValuationStatisticsTransaction#getValuationCompleted(com.kp.cms.forms.exam.ValuationStatisticsForm)
	 */
	@Override
	public int getValuationNotStarted(
			ValuationStatisticsForm valuationStatisticsForm,List<Integer> subjects) throws Exception {
		Session session = null;
		int notStarted=0;
		try{
			session = HibernateUtil.getSession();
			String query = "from ExamValidationDetails e where e.exam.id="+valuationStatisticsForm.getExamId()+" and e.subject.id in (:subject) group by e.subject.id";
			List<ExamValidationDetails> list = session.createQuery(query).setParameterList("subject", subjects).list();
			notStarted = subjects.size()-list.size();
			if(list != null){
				//total issued subjects
				List<Integer> issuedForValuationSubject = new ArrayList<Integer>();
				
				Map<Integer, List<Integer>> deaneryWiseNotStarted = new HashMap<Integer, List<Integer>>();
				List<Integer> subjectIds = null;
				
				Map<Integer, List<Integer>> departmentWiseNotStarted = new HashMap<Integer, List<Integer>>();
				List<Integer> departmentsubjectIds = null;
				Iterator<ExamValidationDetails> iterator = list.iterator();
				while (iterator.hasNext()) {
					ExamValidationDetails examValidationDetails = (ExamValidationDetails) iterator.next();
					if(examValidationDetails.getSubject() != null && examValidationDetails.getSubject().getId() !=0)
						issuedForValuationSubject.add(examValidationDetails.getSubject().getId());
					if(examValidationDetails.getSubject() != null && examValidationDetails.getSubject().getDepartment() !=null && examValidationDetails.getSubject().getDepartment().getEmployeeStreamBO() != null && examValidationDetails.getSubject().getDepartment().getEmployeeStreamBO().getId() !=0){
						//total issued subjects
						// deanery wise map
						if(deaneryWiseNotStarted.containsKey(examValidationDetails.getSubject().getDepartment().getEmployeeStreamBO().getId())){
							subjectIds = deaneryWiseNotStarted.remove(examValidationDetails.getSubject().getDepartment().getEmployeeStreamBO().getId());
						}else{
							subjectIds = new ArrayList<Integer>();
						}
						subjectIds.add(examValidationDetails.getSubject().getId());
						deaneryWiseNotStarted.put(examValidationDetails.getSubject().getDepartment().getEmployeeStreamBO().getId(), subjectIds);
					}
					if(examValidationDetails.getSubject() != null && examValidationDetails.getSubject().getDepartment() !=null){
						// department wise
						
						if(departmentWiseNotStarted.containsKey(examValidationDetails.getSubject().getDepartment().getId())){
							departmentsubjectIds = departmentWiseNotStarted.remove(examValidationDetails.getSubject().getDepartment().getId());
						}else{
							departmentsubjectIds = new ArrayList<Integer>();
						}
						departmentsubjectIds.add(examValidationDetails.getSubject().getId());
						departmentWiseNotStarted.put(examValidationDetails.getSubject().getDepartment().getId(), departmentsubjectIds);
					}
				}
			}
			return notStarted;
		}catch (Exception e) {
			if(session!=null){
				session.flush();
				session.close();
			}
		}finally{
			if(session!=null){
				session.flush();
			}
		}
		return notStarted;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.exam.IValuationStatisticsTransaction#getMarksEntryDetails(com.kp.cms.forms.exam.ValuationStatisticsForm)
	 */
	@Override
	public List<MarksEntryDetails> getMarksEntryDetails(ValuationStatisticsForm valuationStatisticsForm) throws Exception {
		Session session = null;
		List<MarksEntryDetails> marksEntryDetails = new ArrayList<MarksEntryDetails>();
		try{
			session = HibernateUtil.getSession();
			String query = "from MarksEntryDetails m where m.marksEntry.exam.id="+valuationStatisticsForm.getExamId()+" and m.subject.id in (:subject) group by m.marksEntry.student.id,m.subject.id";
			marksEntryDetails = session.createQuery(query).setParameterList("subject", valuationStatisticsForm.getTotalSubjects()).list();
		}catch (Exception e) {
			if(session!=null){
				session.flush();
				session.close();
			}
		}finally{
			if(session!=null){
				session.flush();
			}
		}
		return marksEntryDetails;
	}
	/* 
	 * returning the data for the input query
	 * (non-Javadoc)
	 * @see com.kp.cms.transactions.exam.INewExamMarksEntryTransaction#getDataForQuery(java.lang.String)
	 */
	@Override
	public List getDataForQuery(String query, List<Integer> subjectList) throws Exception {
		Session session = null;
		List list = null;
		try {
			session = HibernateUtil.getSession();
			Query selectedCandidatesQuery=session.createQuery(query).setParameterList("subjects",subjectList);
			list = selectedCandidatesQuery.list();
			return list;
		} catch (Exception e) {
			log.error("Error while retrieving selected candidates.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.exam.IValuationStatisticsTransaction#getDataForCurrentClassStudents(com.kp.cms.forms.exam.ValuationStatisticsForm)
	 */
	@Override
	public List getDataForCurrentClassStudents(
			ValuationStatisticsForm valuationStatisticsForm) throws Exception {
		Session session = null;
		List list = null;
		try {
			session = HibernateUtil.getSession();
			String query="select s.id,subGroup.subject.id from Student s" +
			" join s.admAppln.applicantSubjectGroups appSub" +
			" join appSub.subjectGroup.subjectGroupSubjectses subGroup" +
			" where s.admAppln.isCancelled=0 and subGroup.isActive=1 " +
			" and s.classSchemewise.curriculumSchemeDuration.academicYear=(select e.academicYear" +
			" from ExamDefinitionBO e where e.id="+valuationStatisticsForm.getExamId()+") " +
			" and subGroup.subject.id in(:subjects) group by subGroup.subject.id";
			
			Query selectedCandidatesQuery=session.createQuery(query).setParameterList("subjects", valuationStatisticsForm.getTotalSubjects());
			list = selectedCandidatesQuery.list();
			return list;
		} catch (Exception e) {
			log.error("Error while retrieving selected candidates.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.exam.IValuationStatisticsTransaction#getStatistics(com.kp.cms.forms.exam.ValuationStatisticsForm)
	 */
	@Override
	public List<Subject> getTotalSubjectsForExam(ValuationStatisticsForm valuationStatisticsForm)
			throws Exception {
		Session session = null;
		List<Subject> subjects = new ArrayList<Subject>();
		try{
			session = HibernateUtil.getSession();
			if(valuationStatisticsForm.getTotalSubjects() != null && !valuationStatisticsForm.getTotalSubjects().isEmpty()){
				String query = "select s from Subject s where s.id in(:subjects)";
				subjects = session.createQuery(query).setParameterList("subjects", valuationStatisticsForm.getTotalSubjects()).list();
			}
			return subjects;
		}catch (Exception e) {
			if(session!=null){
				session.flush();
				session.close();
			}
		}finally{
			if(session!=null){
				session.flush();
			}
		}
		return subjects;
		
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.exam.IExamValidationDetailsTransaction#getExamValidationList(java.lang.String)
	 */
	@Override
	public List<ExamValidationDetails> getExamValidationList(String currentExam) throws Exception {
		Session session = null;
		List<ExamValidationDetails> list = null;
		try{
			session = HibernateUtil.getSession();
			Query query = session.createQuery("select e from ExamValidationDetails e join e.answerScripts script  where e.isActive=1 and script.isActive=1 and e.isValuator != 'Reviewer' and e.exam="+currentExam+" group by e.id");
			list = query.list();
			session.flush();
		}catch (Exception e) {
			if (session != null) {
				session.flush();
				session.close();
			}
			list = new ArrayList<ExamValidationDetails>();
		}
		return list;
	}
	/**
	 * @param examId
	 * @param termNumber
	 * @param courseId
	 * @param finalYears
	 * @param absent
	 * @return
	 * @throws Exception
	 */
	public List<Object[]> getTotalVerfiedStudentForSupplementary(String examId,List<Integer> schemeNoList, List<Integer> deptSubjects,boolean absent) throws Exception {

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
					" subject.is_theory_practical AS isTheory,subject.is_certificate_course AS certificatecourse,classes.term_number as termNumber" +
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
					" AND EXAM_student_detention_rejoin_details.class_schemewise_id = class_schemewise.id" +
					" WHERE  EXAM_definition.id = "+examId+"    AND classes.term_number in (:termNO) AND subject.id in(:subjects)";
					if(absent){
						SQL = SQL + " and (EXAM_marks_entry_details.theory_marks='AA' OR EXAM_marks_entry_details.theory_marks='NP')";
					}
					
					SQL = SQL + " AND (EXAM_supplementary_improvement_application.is_appeared_theory = 1 OR EXAM_supplementary_improvement_application.is_appeared_practical = 1)" +
					" GROUP BY subject.id, EXAM_subject_rule_settings_mul_evaluator.evaluator_id  ";
			
			
			Query query = session.createSQLQuery(SQL).setParameterList("termNO", schemeNoList).setParameterList("subjects", deptSubjects);

			list = query.list();

//			session.flush();
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
	 * @see com.kp.cms.transactions.exam.IValuationStatisticsTransaction#getTotalVerfiedStudent(java.lang.String, java.util.List, java.util.List, boolean)
	 */
	public List<Object[]> getTotalVerfiedStudent(String examId,
			List<Integer> schemeNoList, List<Integer> deptSubjects, boolean absent, String examType) throws Exception{

		Session session = null;
		List<Object[]> list;
		try {

			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			String SQL = "select subName, subId,sum(total_students) as total_students, " +
			" sum(total_entered) as total_entered, sum(total_verified) as total_verified,eTypeId,examName,courseName,subCode,courseId, theory_multiple, practical_multiple, isTheory, certificatecourse, termNumber,total_absent " +
			"	from ( " +
			" select subject.name as subName,subject.id as subId, count(student.id) as total_students," +
			" count(EXAM_marks_entry_details.id) as total_entered, count(EXAM_marks_verification_details.id) as total_verified, EXAM_subject_rule_settings_mul_evaluator.evaluator_id as eTypeId, EXAM_definition.name as examName, course.name as courseName, subject.code as subCode,course.id as courseId," +
			" ifnull(EXAM_subject_rule_settings.theory_ese_is_multiple_evaluator, 0) as theory_multiple, ifnull(EXAM_subject_rule_settings.practical_ese_is_multiple_evaluator, 0) as practical_multiple, subject.is_theory_practical as isTheory,subject.is_certificate_course As certificatecourse,classes.term_number as termNumber,count(if(EXAM_marks_entry_details.theory_marks='AA',EXAM_marks_entry_details.id,null)) as total_absent "+
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
			" and EXAM_marks_entry_details.subject_id = subject.id ";
			if(examType.equalsIgnoreCase("Internal")){
				SQL = SQL + " AND EXAM_marks_entry_details.theory_marks IS NOT NULL " +
							" AND trim(EXAM_marks_entry_details.theory_marks) <> '' ";
			}else{
				SQL = SQL + " AND if(subject.is_theory_practical = 'B', " +
				" (EXAM_marks_entry_details.theory_marks IS NOT NULL " +
				" AND EXAM_marks_entry_details.practical_marks IS NOT NULL " +
				" AND trim(EXAM_marks_entry_details.theory_marks) <> '' " +
				" AND trim(EXAM_marks_entry_details.practical_marks) <>''), " +
				" if(subject.is_theory_practical = 'T' , " +
				" (EXAM_marks_entry_details.theory_marks IS NOT NULL  AND trim(EXAM_marks_entry_details.theory_marks) <> ''), " +
				" if(subject.is_theory_practical = 'P' , " +
				" (EXAM_marks_entry_details.practical_marks IS NOT NULL  AND trim(EXAM_marks_entry_details.practical_marks) <> ''),TRUE)))";
			}
			SQL = SQL + 
//			" left join EXAM_student_detention_rejoin_details on EXAM_student_detention_rejoin_details.student_id = student.id and EXAM_student_detention_rejoin_details.class_schemewise_id = class_schemewise.id" +
			" left join EXAM_marks_verification_details on EXAM_marks_verification_details.exam_id = EXAM_definition.id" +
			" and EXAM_marks_verification_details.student_id = student.id and EXAM_marks_verification_details.subject_id = subject.id" +
			" and (if(EXAM_marks_entry.evaluator_type_id is null, EXAM_marks_verification_details.evaluator_type_id IS NULL, EXAM_marks_verification_details.evaluator_type_id = EXAM_marks_entry.evaluator_type_id))"+
			" where curriculum_scheme_duration.academic_year = EXAM_definition.academic_year and EXAM_definition.id=" +examId+
			" and classes.term_number in (:termNO) and subject.id in (:subjects)";
			if(absent){
				SQL = SQL + " and (EXAM_marks_entry_details.theory_marks='AA' OR EXAM_marks_entry_details.theory_marks='NP')";
			}
			SQL = SQL +	/*" and (EXAM_student_detention_rejoin_details.rejoin is null or EXAM_student_detention_rejoin_details.rejoin=0)" +*/
//			" and (EXAM_student_detention_rejoin_details.detain IS NULL OR EXAM_student_detention_rejoin_details.detain = 0)" +
//			" and (EXAM_student_detention_rejoin_details.discontinued IS NULL OR EXAM_student_detention_rejoin_details.discontinued = 0)" +
			" and adm_appln.is_cancelled=0 and student.is_admitted=1 and student.is_hide=0 and subject.is_active=1 and subject_group_subjects.is_active=1 and subject_group.is_active=1" +
			" and student.id not in (select student_id from EXAM_student_detention_rejoin_details where (detain=1 or discontinued=1) and (rejoin is null or rejoin=0))" +
			" group by subject.id, EXAM_subject_rule_settings_mul_evaluator.evaluator_id"+
			" union all" +
			" select subject.name as subName,subject.id as subId, count(student.id) as total_students," +
			" count(EXAM_marks_entry_details.id) as total_entered, count(EXAM_marks_verification_details.id) as total_verified, EXAM_subject_rule_settings_mul_evaluator.evaluator_id as eTypeId, EXAM_definition.name as examName, course.name as courseName, subject.code as subCode,course.id as courseId," +
			" ifnull(EXAM_subject_rule_settings.theory_ese_is_multiple_evaluator, 0) as theory_multiple, ifnull(EXAM_subject_rule_settings.practical_ese_is_multiple_evaluator, 0) as practical_multiple, subject.is_theory_practical as isTheory,subject.is_certificate_course As certificatecourse,classes.term_number as termNumber,count(if(EXAM_marks_entry_details.theory_marks='AA',EXAM_marks_entry_details.id,null)) as total_absent"+
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
			" and EXAM_marks_entry_details.subject_id = subject.id";
			if(examType.equalsIgnoreCase("Internal")){
				SQL = SQL + " AND EXAM_marks_entry_details.theory_marks IS NOT NULL " +
							" AND trim(EXAM_marks_entry_details.theory_marks) <> '' ";
			}else{
				SQL = SQL +" AND if(subject.is_theory_practical = 'B', " +
				" (EXAM_marks_entry_details.theory_marks IS NOT NULL " +
				" AND EXAM_marks_entry_details.practical_marks IS NOT NULL " +
				" AND trim(EXAM_marks_entry_details.theory_marks) <> '' " +
				" AND trim(EXAM_marks_entry_details.practical_marks) <>''), " +
				" if(subject.is_theory_practical = 'T' , " +
				" (EXAM_marks_entry_details.theory_marks IS NOT NULL  AND trim(EXAM_marks_entry_details.theory_marks) <> ''), " +
				" if(subject.is_theory_practical = 'P' , " +
				" (EXAM_marks_entry_details.practical_marks IS NOT NULL  AND trim(EXAM_marks_entry_details.practical_marks) <> ''),TRUE)))";
			}
			
			SQL = SQL +
//			" left join EXAM_student_detention_rejoin_details on EXAM_student_detention_rejoin_details.student_id = student.id and EXAM_student_detention_rejoin_details.class_schemewise_id = class_schemewise.id" +
			" left join EXAM_marks_verification_details on EXAM_marks_verification_details.exam_id = EXAM_definition.id" +
			" and EXAM_marks_verification_details.student_id = student.id and EXAM_marks_verification_details.subject_id = subject.id" +
			" and (if(EXAM_marks_entry.evaluator_type_id is null, EXAM_marks_verification_details.evaluator_type_id IS NULL, EXAM_marks_verification_details.evaluator_type_id = EXAM_marks_entry.evaluator_type_id))"+
			" where curriculum_scheme_duration.academic_year = EXAM_definition.academic_year and EXAM_definition.id=" +examId+
			" and EXAM_student_previous_class_details.scheme_no in (:termNO) and subject.id in (:subjects)";
			if(absent){
				SQL = SQL + " and (EXAM_marks_entry_details.theory_marks='AA' OR EXAM_marks_entry_details.theory_marks='NP')";
			}
			SQL = SQL +	/*" and (EXAM_student_detention_rejoin_details.rejoin is null or EXAM_student_detention_rejoin_details.rejoin=0)" +*/
//			" and (EXAM_student_detention_rejoin_details.detain IS NULL OR EXAM_student_detention_rejoin_details.detain = 0)" +
//			" and (EXAM_student_detention_rejoin_details.discontinued IS NULL OR EXAM_student_detention_rejoin_details.discontinued = 0)" +
			" and adm_appln.is_cancelled=0 and student.is_admitted=1 and student.is_hide=0 and subject.is_active=1 and subject_group_subjects.is_active=1 and subject_group.is_active=1" +
			" and student.id not in (select student_id from EXAM_student_detention_rejoin_details where (detain=1 or discontinued=1) and (rejoin is null or rejoin=0))" +
			" group by subject.id, EXAM_subject_rule_settings_mul_evaluator.evaluator_id ) as exam_valuation " +
			" group by subId,eTypeId";
			
			
			Query query = session.createSQLQuery(SQL).setParameterList("termNO", schemeNoList).setParameterList("subjects", deptSubjects);

			list = query.list();

//			session.flush();
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
	 * @see com.kp.cms.transactions.exam.IValuationStatisticsTransaction#getProcessCompletedDetails()
	 */
	@Override
	public List<ExamValuationProcess> getProcessCompletedDetails()
			throws Exception {
		
		Session session = null;
		List<ExamValuationProcess> list = null;
		try{
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from ExamValuationProcess c where c.isActive=1");
			list = query.list();
			session.flush();
		}catch (Exception e) {
			if (session != null) {
				session.flush();
				session.close();
			}
			list = new ArrayList<ExamValuationProcess>();;
		}
		return list;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.exam.IValuationStatisticsTransaction#getTotalStudentsForSubjects(java.util.List, com.kp.cms.forms.exam.ValuationStatisticsForm)
	 */
	@Override
	public Map<Integer, List<Student>> getTotalStudentsForSubjects(
			List<Integer> deptSubjects,ValuationStatisticsForm valuationStatisticsForm) throws Exception {
		Session session = null;
		Map<Integer, List<Student>> map = new HashMap<Integer, List<Student>>();
		List<Student> students = null;
		try {
			session = HibernateUtil.getSession();
			String query="select s,subGroup.subject.id from Student s" +
			" join s.admAppln.applicantSubjectGroups appSub" +
			" join appSub.subjectGroup.subjectGroupSubjectses subGroup" +
			" where s.admAppln.isCancelled=0 and subGroup.isActive=1 " +
			" and s.classSchemewise.curriculumSchemeDuration.academicYear=(select e.academicYear" +
			" from ExamDefinitionBO e where e.id="+valuationStatisticsForm.getExamId()+") " +
			" and subGroup.subject.id in(:subjects) group by subGroup.subject.id";
			
			Query selectedCandidatesQuery=session.createQuery(query).setParameterList("subjects", deptSubjects);
			List list = selectedCandidatesQuery.list();
			if(list != null){
				Iterator<Object[]> iterator = list.iterator();
				while (iterator.hasNext()) {
					Object[] objects = (Object[]) iterator.next();
					if(objects[0] != null && objects[1] != null && !objects[1].toString().isEmpty()){
						if(map.containsKey(Integer.parseInt(objects[1].toString()))){
							students = map.remove(Integer.parseInt(objects[1].toString()));
						}else{
							students = new ArrayList<Student>();
						}
						students.add( (Student) objects[1]);
						map.put(Integer.parseInt(objects[1].toString()), students);
					}
				}
			}
			return map;
		} catch (Exception e) {
			log.error("Error while retrieving selected candidates.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.exam.IValuationStatisticsTransaction#getMarkEntryStudents(java.util.List, com.kp.cms.forms.exam.ValuationStatisticsForm)
	 */
	@Override
	public Map<Integer, List<Student>> getMarkEntryStudents(
			List<Integer> deptSubjects,
			ValuationStatisticsForm valuationStatisticsForm) throws Exception {
		Session session = null;
		Map<Integer, List<Student>> map = new HashMap<Integer, List<Student>>();
		List<Student> students = null;
		Map<Integer, List<Student>> verificationMap = new HashMap<Integer, List<Student>>();
		List<Student> vstudents = null;
		try{
			session = HibernateUtil.getSession();
			String query = "from MarksEntryDetails m where m.marksEntry.exam.id="+valuationStatisticsForm.getExamId()+" and m.subject.id in (:subject) group by m.marksEntry.student.id,m.subject.id";
			List<MarksEntryDetails> marksEntryDetails = session.createQuery(query).setParameterList("subject", deptSubjects).list();
			if(marksEntryDetails != null){
				Iterator<MarksEntryDetails> iterator = marksEntryDetails.iterator();
				while (iterator.hasNext()) {
					MarksEntryDetails bo = (MarksEntryDetails) iterator.next();
					if(bo.getSubject() != null && bo.getSubject().getId() !=0 &&  bo.getMarksEntry() != null && bo.getMarksEntry().getStudent() != null){
						if(map.containsKey(bo.getSubject().getId())){
							students = map.remove(bo.getSubject().getId());
						}else{
							students = new ArrayList<Student>();
						}
						students.add(bo.getMarksEntry().getStudent());
						map.put(bo.getSubject().getId(), students);
					}
					if(bo.getSubject() != null && bo.getSubject().getId() !=0 &&  bo.getMarksEntry() != null && 
							bo.getMarksEntry().getStudent() != null && (!bo.getTheoryMarks().equalsIgnoreCase("AA") || !bo.getTheoryMarks().equalsIgnoreCase("NP"))){
						if(verificationMap.containsKey(bo.getSubject().getId())){
							vstudents = verificationMap.remove(bo.getSubject().getId());
						}else{
							vstudents = new ArrayList<Student>();
						}
						vstudents.add(bo.getMarksEntry().getStudent());
						verificationMap.put(bo.getSubject().getId(), vstudents);
					}
					
					
					
				}
				valuationStatisticsForm.setVerificationMap(verificationMap);
			}
		}catch (Exception e) {
			if(session!=null){
				session.flush();
				session.close();
			}
		}finally{
			if(session!=null){
				session.flush();
			}
		}
		return map;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.exam.IExamValuationStatusTransaction#getTotalStudents(java.lang.String)
	 */
	public List<Student> getTotalStudents(String query) throws Exception{
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
	 * @see com.kp.cms.transactions.exam.IExamValuationStatusTransaction#getDetailsForView(com.kp.cms.forms.exam.ExamValuationStatusForm)
	 */
	@Override
	public List<MarksEntryDetails> getDetailsForView(ValuationStatisticsForm valuationStatisticsForm) throws Exception {

		Session session = null;
		List<MarksEntryDetails> list = null;
		try{
			session = HibernateUtil.getSession();
			String subjectName = (String)session.createQuery("select concat(s.code,' - ',s.name) from Subject s where s.id="+valuationStatisticsForm.getSubjectId()).uniqueResult();
			valuationStatisticsForm.setSubjectName(subjectName);
			String hql = "select m from MarksEntryDetails m "+
							" where m.marksEntry.exam.id = " +valuationStatisticsForm.getExamId() +
							" and m.subject.id="+valuationStatisticsForm.getSubjectId();
			if(valuationStatisticsForm.getCourseId() != null && !valuationStatisticsForm.getCourseId().trim().isEmpty()){
				hql = hql + " and m.marksEntry.classes.course.id="+valuationStatisticsForm.getCourseId(); 
			}
			if(valuationStatisticsForm.getEvaluatorTypeId() != null && !valuationStatisticsForm.getEvaluatorTypeId().trim().isEmpty()){
				int evaluatorType = Integer.parseInt(valuationStatisticsForm.getEvaluatorTypeId());
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
	 * @see com.kp.cms.transactions.exam.IExamValuationStatusTransaction#getTotalStudents(java.lang.String)
	 */
	public List<Student> getTotalStudents1(String query) throws Exception{
		Session session = null;
		List<Student> list = new ArrayList<Student>();
		try {
			session = HibernateUtil.getSession();
			Query selectedCandidatesQuery=session.createQuery(query);
			List<Integer> studentsIds = selectedCandidatesQuery.list();
			if(studentsIds != null){
				list = session.createQuery("select s from Student s where s.id in(:studentIds) group by s.id").setParameterList("studentIds", studentsIds).list();
			}
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
	 * @see com.kp.cms.transactions.exam.IExamValuationStatusTransaction#getDetailsForView(com.kp.cms.forms.exam.ExamValuationStatusForm)
	 */
	@Override
	public Map<Integer,String> getVerificationDetailsForView(ValuationStatisticsForm valuationStatisticsForm) throws Exception {

		Session session = null;
		Map<Integer,String> stuMap = new HashMap<Integer, String>();
		try{
			session = HibernateUtil.getSession();
			int evaluatorType = 0;
			String subjectName = (String)session.createQuery("select concat(s.code,' - ',s.name) from Subject s where s.id="+valuationStatisticsForm.getSubjectId()).uniqueResult();
			valuationStatisticsForm.setSubjectName(subjectName);
			String hql = "select m from ExamMarksVerificationEntryBO m "+
							" where m.examId = " +valuationStatisticsForm.getExamId() +
							" and m.subjectId="+valuationStatisticsForm.getSubjectId();
			if(valuationStatisticsForm.getEvaluatorTypeId() != null && !valuationStatisticsForm.getEvaluatorTypeId().trim().isEmpty()){
				evaluatorType = Integer.parseInt(valuationStatisticsForm.getEvaluatorTypeId());
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
					" where  m.theoryMarks='AA' and m.marksEntry.exam.id= " +valuationStatisticsForm.getExamId() +
					" and m.subject.id= " +valuationStatisticsForm.getSubjectId();
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
//			session.flush();
		}catch (Exception e) {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		return stuMap;
	
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.exam.IValuationStatisticsTransaction#getStatistics(com.kp.cms.forms.exam.ValuationStatisticsForm)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> getTotalSubjectsForUser(ValuationStatisticsForm valuationStatisticsForm)
			throws Exception {
		Session session = null;
		List<Integer> subjects = new ArrayList<Integer>();
		try{
			session = HibernateUtil.getSession();
			List<Integer> departmentIds = session.createQuery("select t.departmentId.id from TeacherDepartment t where t.teacherId.id="+valuationStatisticsForm.getUserId()+" group by t.departmentId.id").list();
			if(departmentIds != null && !departmentIds.isEmpty()){
				List<Integer> schemeNo = session.createQuery("select e.schemeNo from ExamExamCourseSchemeDetailsBO e where e.examId="+valuationStatisticsForm.getExamId()+" group by e.schemeNo").list();
				if(schemeNo !=null){
					valuationStatisticsForm.setSchemeNoList(schemeNo);
					if(valuationStatisticsForm.getExamType() != null && valuationStatisticsForm.getExamType().equalsIgnoreCase("Supplementary")){
						String query = "select subject.id from EXAM_supplementary_improvement_application app " +
						"inner join subject ON app.subject_id = subject.id " +
						"inner join department ON subject.department_id=department.id " +
						"inner join EXAM_definition ON app.exam_id = EXAM_definition.id " +
						"inner join EXAM_time_table ON EXAM_time_table.subject_id = subject.id " +
						"where EXAM_definition.id= " +valuationStatisticsForm.getExamId()+
						" and EXAM_time_table.is_active=1 and app.scheme_no in(:SchemeNO) and department.id in(:department) " +
						"and (app.is_appeared_practical=1 or app.is_appeared_theory=1) " +
						"and EXAM_time_table.date_starttime <curdate()";
						subjects = session.createSQLQuery(query).setParameterList("SchemeNO", schemeNo).setParameterList("department", departmentIds).list();
					}else if(valuationStatisticsForm.getExamType() != null && valuationStatisticsForm.getExamType().equalsIgnoreCase("Regular")){
						String HQL = "select distinct courseId from ExamExamCourseSchemeDetailsBO e where e.schemeNo in(:schemeNo) and e.examId = "+ valuationStatisticsForm.getExamId();
						List<Integer> courseList = session.createQuery(HQL).setParameterList("schemeNo", schemeNo).list();
						if(courseList != null){
							String sql ="select subject.id from subject_group " +
							"inner join subject_group_subjects ON subject_group_subjects.subject_group_id = subject_group.id " +
							"inner join subject ON subject_group_subjects.subject_id = subject.id " +
							"inner join department ON subject.department_id=department.id " +
							"inner join EXAM_time_table ON EXAM_time_table.subject_id = subject.id " +
							"inner join EXAM_exam_course_scheme_details ON EXAM_time_table.exam_course_scheme_id = EXAM_exam_course_scheme_details.id " +
							"inner join EXAM_definition ON EXAM_exam_course_scheme_details.exam_id = EXAM_definition.id " +
							"inner join course ON subject_group.course_id = course.id " +
							"inner join curriculum_scheme_subject ON curriculum_scheme_subject.subject_group_id = subject_group.id " +
							"inner join curriculum_scheme_duration ON curriculum_scheme_subject.curriculum_scheme_duration_id = curriculum_scheme_duration.id " +
							"inner join class_schemewise ON class_schemewise.curriculum_scheme_duration_id = curriculum_scheme_duration.id " +
							"inner join classes ON class_schemewise.class_id = classes.id " +
							"where course.id in(:Courses) and classes.term_number in (:SchemeNo) and department.id in(:department) " +
							"and curriculum_scheme_duration.academic_year=EXAM_definition.academic_year and EXAM_definition.id=" +valuationStatisticsForm.getExamId()+
							" and subject.is_active=1 and subject_group.is_active=1 and subject_group_subjects.is_active=1 " +
							"and EXAM_time_table.date_starttime<curdate() and EXAM_time_table.is_active=1 group by subject.id";
							Query subjectQuery = session.createSQLQuery(sql);
							subjectQuery.setParameterList("Courses", courseList).setParameterList("SchemeNo", schemeNo).setParameterList("department", departmentIds);
							subjects = subjectQuery.list();
						}
					}else{
						String HQL = "select distinct courseId from ExamExamCourseSchemeDetailsBO e where e.schemeNo in(:schemeNo) and e.examId = "+ valuationStatisticsForm.getExamId();
						List<Integer> courseList = session.createQuery(HQL).setParameterList("schemeNo", schemeNo).list();
						if(courseList != null){
							String query = "select groupsub.subject.id from SubjectGroup sub " +
							" join sub.curriculumSchemeSubjects curr  " +
							" join curr.curriculumSchemeDuration cd"+
							" join cd.classSchemewises cls"+
							" join sub.subjectGroupSubjectses groupsub" +
							" where sub.course.id in(:Courses) and groupsub.subject.department.id in (:department)" +
							" and sub.isActive=1 and groupsub.isActive=1 and groupsub.subject.isActive=1 and cls.classes.termNumber in (:SchemeNo)" +
							" and cd.academicYear=(select e.academicYear from ExamDefinitionBO e where e.id= "+ valuationStatisticsForm.getExamId() +
							") group by groupsub.subject.id";
							Query subjectQuery = session.createQuery(query);
							subjectQuery.setParameterList("Courses", courseList).setParameterList("SchemeNo", schemeNo).setParameterList("department", departmentIds);
							subjects = subjectQuery.list();
						}
					}
				}
			}
			return subjects;
		}catch (Exception e) {
			if(session!=null){
				session.flush();
				session.close();
			}
		}finally{
			if(session!=null){
				session.flush();
			}
		}
		return subjects;
		
	}
	/**
	 * @param examId
	 * @param termNumber
	 * @param courseId
	 * @param finalYears
	 * @param absent
	 * @return
	 * @throws Exception
	 */
	public List<Object[]> getTotalVerfiedStudentForSupplementaryForDashBord(String examId,List<Integer> schemeNoList, List<Integer> deptSubjects,boolean absent) throws Exception {

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
					" subject.is_theory_practical AS isTheory,subject.is_certificate_course AS certificatecourse,classes.term_number as termNumber,count(if(EXAM_marks_entry_details.theory_marks='AA',EXAM_marks_entry_details.id,null)) as total_absent" +
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
					" AND EXAM_student_detention_rejoin_details.class_schemewise_id = class_schemewise.id" +
					" WHERE  EXAM_definition.id = "+examId+"    AND classes.term_number in (:termNO) AND subject.id in(:subjects)";
					if(absent){
						SQL = SQL + " and (EXAM_marks_entry_details.theory_marks='AA' OR EXAM_marks_entry_details.theory_marks='NP')";
					}
					
					SQL = SQL + " AND (EXAM_supplementary_improvement_application.is_appeared_theory = 1 OR EXAM_supplementary_improvement_application.is_appeared_practical = 1)" +
					" GROUP BY subject.id, EXAM_subject_rule_settings_mul_evaluator.evaluator_id  ";
			
			
			Query query = session.createSQLQuery(SQL).setParameterList("termNO", schemeNoList).setParameterList("subjects", deptSubjects);

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
	 * @see com.kp.cms.transactions.exam.IValuationStatisticsTransaction#getTotalVerfiedStudent(java.lang.String, java.util.List, java.util.List, boolean)
	 */
	public List<Object[]> getTotalVerfiedStudentForDashBord(String examId,
			List<Integer> schemeNoList, List<Integer> deptSubjects, boolean absent, String examType) throws Exception{

		Session session = null;
		List<Object[]> list;
		try {

			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			String SQL = "select subName, subId,sum(total_students) as total_students, " +
			" sum(total_entered) as total_entered, 0 as total_verified,eTypeId,examName,courseName,subCode,courseId, theory_multiple, practical_multiple, isTheory, certificatecourse, termNumber,total_absent " +
			"	from ( " +
			" select subject.name as subName,subject.id as subId, count(student.id) as total_students," +
			" count(EXAM_marks_entry_details.id) as total_entered,  EXAM_subject_rule_settings_mul_evaluator.evaluator_id as eTypeId, EXAM_definition.name as examName, course.name as courseName, subject.code as subCode,course.id as courseId," +
			" ifnull(EXAM_subject_rule_settings.theory_ese_is_multiple_evaluator, 0) as theory_multiple, ifnull(EXAM_subject_rule_settings.practical_ese_is_multiple_evaluator, 0) as practical_multiple, subject.is_theory_practical as isTheory,subject.is_certificate_course As certificatecourse,classes.term_number as termNumber,count(if(EXAM_marks_entry_details.theory_marks='AA',EXAM_marks_entry_details.id,null)) as total_absent "+
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
			" and EXAM_marks_entry_details.subject_id = subject.id ";
			if(examType.equalsIgnoreCase("Internal")){
				SQL = SQL + " AND EXAM_marks_entry_details.theory_marks IS NOT NULL " +
							" AND trim(EXAM_marks_entry_details.theory_marks) <> '' ";
			}else{
				SQL = SQL + " AND if(subject.is_theory_practical = 'B', " +
				" (EXAM_marks_entry_details.theory_marks IS NOT NULL " +
				" AND EXAM_marks_entry_details.practical_marks IS NOT NULL " +
				" AND trim(EXAM_marks_entry_details.theory_marks) <> '' " +
				" AND trim(EXAM_marks_entry_details.practical_marks) <>''), " +
				" if(subject.is_theory_practical = 'T' , " +
				" (EXAM_marks_entry_details.theory_marks IS NOT NULL  AND trim(EXAM_marks_entry_details.theory_marks) <> ''), " +
				" if(subject.is_theory_practical = 'P' , " +
				" (EXAM_marks_entry_details.practical_marks IS NOT NULL  AND trim(EXAM_marks_entry_details.practical_marks) <> ''),TRUE)))";
			}
			SQL = SQL +
//			" left join EXAM_student_detention_rejoin_details on EXAM_student_detention_rejoin_details.student_id = student.id and EXAM_student_detention_rejoin_details.class_schemewise_id = class_schemewise.id" +
//			" left join EXAM_marks_verification_details on EXAM_marks_verification_details.exam_id = EXAM_definition.id" +
//			" and EXAM_marks_verification_details.student_id = student.id and EXAM_marks_verification_details.subject_id = subject.id" +
//			" and (if(EXAM_marks_entry.evaluator_type_id is null, EXAM_marks_verification_details.evaluator_type_id IS NULL, EXAM_marks_verification_details.evaluator_type_id = EXAM_marks_entry.evaluator_type_id))"+
			" where curriculum_scheme_duration.academic_year = EXAM_definition.academic_year and EXAM_definition.id=" +examId+
			" and classes.term_number in (:termNO) and subject.id in (:subjects)";
			if(absent){
				SQL = SQL + " and (EXAM_marks_entry_details.theory_marks='AA' OR EXAM_marks_entry_details.theory_marks='NP')";
			}
			SQL = SQL +	/*" and (EXAM_student_detention_rejoin_details.rejoin is null or EXAM_student_detention_rejoin_details.rejoin=0)" +*/
//			" and (EXAM_student_detention_rejoin_details.detain IS NULL OR EXAM_student_detention_rejoin_details.detain = 0)" +
//			" and (EXAM_student_detention_rejoin_details.discontinued IS NULL OR EXAM_student_detention_rejoin_details.discontinued = 0)" +
			" and adm_appln.is_cancelled=0 and student.is_admitted=1 and student.is_hide=0 and subject.is_active=1 and subject_group_subjects.is_active=1 and subject_group.is_active=1" +
			" and student.id not in (select student_id from EXAM_student_detention_rejoin_details where (detain=1 or discontinued=1) and (rejoin is null or rejoin=0))" +
			" group by subject.id, EXAM_subject_rule_settings_mul_evaluator.evaluator_id"+
			" union all" +
			" select subject.name as subName,subject.id as subId, count(student.id) as total_students," +
			" count(EXAM_marks_entry_details.id) as total_entered, EXAM_subject_rule_settings_mul_evaluator.evaluator_id as eTypeId, EXAM_definition.name as examName, course.name as courseName, subject.code as subCode,course.id as courseId," +
			" ifnull(EXAM_subject_rule_settings.theory_ese_is_multiple_evaluator, 0) as theory_multiple, ifnull(EXAM_subject_rule_settings.practical_ese_is_multiple_evaluator, 0) as practical_multiple, subject.is_theory_practical as isTheory,subject.is_certificate_course As certificatecourse,classes.term_number as termNumber,count(if(EXAM_marks_entry_details.theory_marks='AA',EXAM_marks_entry_details.id,null)) as total_absent"+
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
			" and EXAM_marks_entry_details.subject_id = subject.id";
			if(examType.equalsIgnoreCase("Internal")){
				SQL = SQL + " AND EXAM_marks_entry_details.theory_marks IS NOT NULL " +
							" AND trim(EXAM_marks_entry_details.theory_marks) <> '' ";
			}else{
				SQL = SQL +" AND if(subject.is_theory_practical = 'B', " +
				" (EXAM_marks_entry_details.theory_marks IS NOT NULL " +
				" AND EXAM_marks_entry_details.practical_marks IS NOT NULL " +
				" AND trim(EXAM_marks_entry_details.theory_marks) <> '' " +
				" AND trim(EXAM_marks_entry_details.practical_marks) <>''), " +
				" if(subject.is_theory_practical = 'T' , " +
				" (EXAM_marks_entry_details.theory_marks IS NOT NULL  AND trim(EXAM_marks_entry_details.theory_marks) <> ''), " +
				" if(subject.is_theory_practical = 'P' , " +
				" (EXAM_marks_entry_details.practical_marks IS NOT NULL  AND trim(EXAM_marks_entry_details.practical_marks) <> ''),TRUE)))"; 
			}
				
			SQL = SQL +
//			" left join EXAM_student_detention_rejoin_details on EXAM_student_detention_rejoin_details.student_id = student.id and EXAM_student_detention_rejoin_details.class_schemewise_id = class_schemewise.id" +
//			" left join EXAM_marks_verification_details on EXAM_marks_verification_details.exam_id = EXAM_definition.id" +
//			" and EXAM_marks_verification_details.student_id = student.id and EXAM_marks_verification_details.subject_id = subject.id" +
//			" and (if(EXAM_marks_entry.evaluator_type_id is null, EXAM_marks_verification_details.evaluator_type_id IS NULL, EXAM_marks_verification_details.evaluator_type_id = EXAM_marks_entry.evaluator_type_id))"+
			" where curriculum_scheme_duration.academic_year = EXAM_definition.academic_year and EXAM_definition.id=" +examId+
			" and EXAM_student_previous_class_details.scheme_no in (:termNO) and subject.id in (:subjects)";
			if(absent){
				SQL = SQL + " and (EXAM_marks_entry_details.theory_marks='AA' OR EXAM_marks_entry_details.theory_marks='NP')";
			}
			SQL = SQL +	/*" and (EXAM_student_detention_rejoin_details.rejoin is null or EXAM_student_detention_rejoin_details.rejoin=0)" +*/
//			" and (EXAM_student_detention_rejoin_details.detain IS NULL OR EXAM_student_detention_rejoin_details.detain = 0)" +
//			" and (EXAM_student_detention_rejoin_details.discontinued IS NULL OR EXAM_student_detention_rejoin_details.discontinued = 0)" +
			" and adm_appln.is_cancelled=0 and student.is_admitted=1 and student.is_hide=0 and subject.is_active=1 and subject_group_subjects.is_active=1 and subject_group.is_active=1" +
			" and student.id not in (select student_id from EXAM_student_detention_rejoin_details where (detain=1 or discontinued=1) and (rejoin is null or rejoin=0))" +
			" group by subject.id, EXAM_subject_rule_settings_mul_evaluator.evaluator_id ) as exam_valuation " +
			" group by subId,eTypeId";
			
			
			Query query = session.createSQLQuery(SQL).setParameterList("termNO", schemeNoList).setParameterList("subjects", deptSubjects);

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
	 * @see com.kp.cms.transactions.exam.IValuationStatisticsTransaction#getIssuedForValuationSubjects(com.kp.cms.forms.exam.ValuationStatisticsForm)
	 */
	@Override
	public List<Integer> getIssuedForValuationSubjects(
			ValuationStatisticsForm valuationStatisticsForm) throws Exception {
		
		Session session = null;
		List<Integer> list = new ArrayList<Integer>();
		try{
			session = HibernateUtil.getSession();
			Query query = session.createSQLQuery("select subject_id,DATE_FORMAT(min(created_date),'%d/%m/%y') as date from exam_valuation_details where is_active=1 and exam_id="+valuationStatisticsForm.getExamId()+" group by subject_id");
			List<Object[]> validationDetails = query.list();
			if(validationDetails != null){
				Map<Integer, String> firstIssuedDate = new HashMap<Integer, String>();
				for (Object[] examValidationDetails : validationDetails) {
					if(examValidationDetails[0] != null && examValidationDetails[0].toString() != null){
						list.add(Integer.parseInt(examValidationDetails[0].toString()));
						if(examValidationDetails[1] != null && examValidationDetails[1].toString() != null)
							firstIssuedDate.put(Integer.parseInt(examValidationDetails[0].toString()), CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(CommonUtil.ConvertStringToDate(examValidationDetails[1].toString())), "dd-MMM-yyyy","dd/MM/yyyy"));
					}
				}
				valuationStatisticsForm.setFirstIssuedDate(firstIssuedDate);
			}
			
			session.flush();
		}catch (Exception e) {
			if (session != null) {
				session.flush();
				session.close();
			}
			list = new ArrayList<Integer>();;
		}
		return list;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.exam.IValuationStatisticsTransaction#setExamSubjectDatesToForm(java.util.List, com.kp.cms.forms.exam.ValuationStatisticsForm)
	 */
	@Override
	public void setExamSubjectDatesToForm(List<Integer> deptSubjects,
			ValuationStatisticsForm valuationStatisticsForm) throws Exception {
		
		Session session = null;
		try{
			session = HibernateUtil.getSession();
			if(deptSubjects != null){
				Query query = session.createQuery("select e from ExamTimeTableBO e where e.isActive=1 and e.subjectUtilBO.id in(:Subjects) and e.examExamCourseSchemeDetailsBO.examDefinitionBO.id="+valuationStatisticsForm.getExamId());
				List<ExamTimeTableBO> timeTable = query.setParameterList("Subjects", deptSubjects).list();
				if(timeTable != null){
					Map<Integer, String> examStartDate = new HashMap<Integer, String>();
					for (ExamTimeTableBO bo : timeTable) {
						if(bo.getSubjectUtilBO() != null && bo.getDateStarttime() != null){
							examStartDate.put(bo.getSubjectUtilBO().getId(), CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(bo.getDateStarttime()), "dd-MMM-yyyy","dd/MM/yyyy"));
						}
					}
					valuationStatisticsForm.setExamStartDate(examStartDate);
				}
				String sqlQuery = "select DATE_FORMAT(max(EXAM_marks_entry_details.created_date),'%d/%m/%y') as endDate, EXAM_marks_entry_details.subject_id " +
						" from EXAM_marks_entry_details JOIN EXAM_marks_entry ON EXAM_marks_entry.id=EXAM_marks_entry_details.marks_entry_id " +
						" WHERE EXAM_marks_entry.exam_id = " +valuationStatisticsForm.getExamId()+
						" and EXAM_marks_entry_details.subject_id in (:SubjectList)"+
						" group by EXAM_marks_entry_details.subject_id";
				Query sql = session.createSQLQuery(sqlQuery).setParameterList("SubjectList", deptSubjects);
				List<Object[]> marksEntryDates = sql.list();
				if(marksEntryDates != null && !marksEntryDates.isEmpty()){
					Map<Integer, String> valuationEndDate = new HashMap<Integer, String>();
					for (Object[] objects : marksEntryDates) {
						if(objects[0] != null && objects[0].toString() != null && objects[1] != null){
							valuationEndDate.put(Integer.parseInt(objects[1].toString()), CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(CommonUtil.ConvertStringToDate(objects[0].toString())), "dd-MMM-yyyy","dd/MM/yyyy"));
						}
					}
					valuationStatisticsForm.setValuationEndDate(valuationEndDate);
				}
			}
			session.flush();
		}catch (Exception e) {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
	}
}