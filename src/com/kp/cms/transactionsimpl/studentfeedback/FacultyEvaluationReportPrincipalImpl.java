package com.kp.cms.transactionsimpl.studentfeedback;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;

import com.kp.cms.bo.admin.Department;
import com.kp.cms.bo.admin.EvaluationStudentFeedbackFaculty;
import com.kp.cms.bo.studentfeedback.EvaStudentFeedbackAverage;
import com.kp.cms.bo.studentfeedback.EvaStudentFeedbackQuestion;
import com.kp.cms.forms.studentfeedback.FacultyEvaluationReportForm;
import com.kp.cms.forms.studentfeedback.FacultyEvaluationReportPrincipalForm;
import com.kp.cms.transactions.studentfeedback.IFacultyEvaluationReportPrincipal;
import com.kp.cms.utilities.HibernateUtil;

public class FacultyEvaluationReportPrincipalImpl implements IFacultyEvaluationReportPrincipal{

	private static final Log log = LogFactory.getLog(FacultyEvaluationReportPrincipalImpl.class);
	private static volatile FacultyEvaluationReportPrincipalImpl facultyEvaluationReportPrincipalImpl = null;

	public static FacultyEvaluationReportPrincipalImpl getInstance() {
		if (facultyEvaluationReportPrincipalImpl == null) {
			facultyEvaluationReportPrincipalImpl = new FacultyEvaluationReportPrincipalImpl();
			return facultyEvaluationReportPrincipalImpl;
		}
		return facultyEvaluationReportPrincipalImpl;
	}
	
	public Map<Integer, String> getClasses(FacultyEvaluationReportPrincipalForm facultyEvaluationReportPrincipalForm, int year) throws Exception
	{
        log.debug("call of getClasses method in FacultyEvaluationReportPrincipalImpl.class");
        Session session = null;
		Map<Integer, String> map=new HashMap<Integer, String>();
        try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			String sql =("select distinct classes.id,classes.name" +
					" from eva_student_feedback_faculty " +
					" inner join eva_student_feedback ON eva_student_feedback_faculty.eva_student_feedback_id = eva_student_feedback.id" +
					" inner join classes ON eva_student_feedback.class_id = classes.id" +
					" inner join eva_student_feedback_session ON eva_student_feedback.session_id = eva_student_feedback_session.id" +
					" where eva_student_feedback_session.academic_year=:academicYear" +
					" and eva_student_feedback_faculty.teacher_id=:userId");
			Query query = session.createSQLQuery(sql);
			query.setInteger("academicYear", year);
			query.setString("userId", facultyEvaluationReportPrincipalForm.getTeacher());			
			List<Object[]> objects = query.list();
			if(objects!=null && !objects.isEmpty()){
				Iterator<Object[]> iterator = objects.iterator();
				while (iterator.hasNext()) {
					Object[] objects2 = (Object[]) iterator.next(); 
					if(objects2[0]!=null && !objects2[0].toString().isEmpty() && objects2[1]!=null && !objects2[1].toString().isEmpty()){
						map.put(Integer.parseInt(objects2[0].toString()), objects2[1].toString());
					}
				}
			}
			return map;
		} catch (Exception e) {
			log.debug("Exception" + e.getMessage());
		}
		return map;		
	}
	public Map<Integer, String> getSubjects(FacultyEvaluationReportPrincipalForm facultyEvaluationReportPrincipalForm, int year) throws Exception
	{
        log.debug("call of getSubjects method in FacultyEvaluationReportPrincipalImpl.class");
        Session session = null;
		Map<Integer, String> map=new HashMap<Integer, String>();
        try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			String sql =("select distinct eva_student_feedback_faculty.subject_id,subject.name" +
					" from eva_student_feedback_faculty " +
					" inner join subject ON eva_student_feedback_faculty.subject_id = subject.id" +
					" inner join eva_student_feedback ON eva_student_feedback_faculty.eva_student_feedback_id = eva_student_feedback.id" +
					" inner join eva_student_feedback_session ON eva_student_feedback.session_id = eva_student_feedback_session.id" +
					" inner join classes ON eva_student_feedback.class_id = classes.id" +
					" where classes.id=:classId and eva_student_feedback_faculty.teacher_id=:userId" +
					" and eva_student_feedback_session.academic_year=:academicYear");
			Query query = session.createSQLQuery(sql);
			query.setInteger("academicYear", year);
			query.setString("classId", facultyEvaluationReportPrincipalForm.getClassId());			
			query.setString("userId", facultyEvaluationReportPrincipalForm.getTeacher());			
			List<Object[]> objects = query.list();
			if(objects!=null && !objects.isEmpty()){
				Iterator<Object[]> iterator = objects.iterator();
				while (iterator.hasNext()) {
					Object[] objects2 = (Object[]) iterator.next(); 
					if(objects2[0]!=null && !objects2[0].toString().isEmpty() && objects2[1]!=null && !objects2[1].toString().isEmpty()){
						map.put(Integer.parseInt(objects2[0].toString()), objects2[1].toString());
					}
				}
			}
			return map;
		} catch (Exception e) {
			log.debug("Exception" + e.getMessage());
		}
		return map;		
	}
	public List<EvaStudentFeedbackQuestion> getEvaluationQuestions(){
		log.debug("call of getEvaluationQuestions method in FacultyEvaluationReportPrincipalImpl.class");
        Session session = null;
        List<EvaStudentFeedbackQuestion> questions = null;
        try{
        	// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session.createQuery(" from EvaStudentFeedbackQuestion where isActive=1");
			questions=query.list();
			return questions;
        }catch (Exception e) {
			log.debug("Exception" + e.getMessage());
		}
        return questions;
	}
	public List<Object[]> getFacultyEvaluationDetails(FacultyEvaluationReportPrincipalForm facultyEvaluationReportPrincipalForm) throws Exception
	{
		log.debug("call of getSubjects method in FacultyEvaluationReportPrincipalImpl.class");
		Session session = null;
		List<Object[]> objects = null;
		try{
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			String sql ="select distinct curriculum_scheme_duration.semester_year_no,program.name as program," +
					" employee.first_name as teacher_name,course.name as course," +
					" classes.name as class,subject.name as subject" +
					" from eva_student_feedback_faculty " +
					" inner join subject ON eva_student_feedback_faculty.subject_id = subject.id" +
					" inner join eva_student_feedback ON eva_student_feedback_faculty.eva_student_feedback_id = eva_student_feedback.id" +
					" inner join eva_student_feedback_session ON eva_student_feedback.session_id = eva_student_feedback_session.id" +
					" inner join classes ON eva_student_feedback.class_id = classes.id" +
					" inner join course ON classes.course_id = course.id" +
					" inner join program ON course.program_id = program.id" +
					" inner join teacher_class_subject on teacher_class_subject.subject_id = subject.id" +
					" inner join class_schemewise ON teacher_class_subject.class_schemewise_id = class_schemewise.id" +
					" inner join curriculum_scheme_duration ON class_schemewise.curriculum_scheme_duration_id = curriculum_scheme_duration.id" +
					" inner join users ON eva_student_feedback_faculty.teacher_id = users.id" +
					" inner join employee ON users.employee_id = employee.id" +
					" where classes.id=:classId and eva_student_feedback_faculty.teacher_id=:userId" +
					" and subject.id=:subjectId";
			Query query = session.createSQLQuery(sql);
			query.setString("classId", facultyEvaluationReportPrincipalForm.getClassId());	
			query.setString("subjectId", facultyEvaluationReportPrincipalForm.getSubjectId());	
			query.setString("userId", facultyEvaluationReportPrincipalForm.getTeacher());			
			objects = query.list();
			return objects;
		}
		catch(Exception e)
		{
			log.debug("Exception" + e.getMessage());
		}
		return objects;
	}	
	public List<Object[]> getAnswers(FacultyEvaluationReportPrincipalForm facultyEvaluationReportPrincipalForm,int queId,int ansId,String teacherId,String subjectId)
	{
		log.debug("call of getSubjects method in FacultyEvaluationReportPrincipalImpl.class");
		Session session = null;
		List<Object[]> objects = null;
		try{
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			String userId = facultyEvaluationReportPrincipalForm.getUserId();
			if(teacherId.equalsIgnoreCase("0")==true)
			teacherId = facultyEvaluationReportPrincipalForm.getTeacher();
			if(teacherId.equalsIgnoreCase("0")==false)
			{
				userId = teacherId;
			}
			String sql = "SELECT eva_student_feedback_question.id as question_id," +
					" eva_student_feedback_question.question," +
					" eva_student_feedback_answer.id as answer_id," +
					" eva_student_feedback_answer.answer," +
					" count(student_id) as student_count," +
					" eva_student_feedback_answer.answer * count(student_id) as total_marks" +
					" FROM  eva_student_feedback_faculty " +
					" INNER JOIN eva_student_feedback ON eva_student_feedback_faculty.eva_student_feedback_id = eva_student_feedback.id" +
					" INNER JOIN eva_student_feedback_answer  ON eva_student_feedback_answer.feedback_faculty_id = eva_student_feedback_faculty.id" +
					" INNER JOIN eva_student_feedback_question  ON eva_student_feedback_answer.question_id = eva_student_feedback_question.id" +
					" where eva_student_feedback_faculty.teacher_id=" +userId+"" +
					" and eva_student_feedback_faculty.subject_id=" + subjectId+"" +
					" and eva_student_feedback.class_id=" +facultyEvaluationReportPrincipalForm.getClassId()+"" +
					" and eva_student_feedback_question.id=" +queId+"" +
					" and eva_student_feedback_answer.answer=" +ansId+"" +
 					" group by eva_student_feedback_answer.answer,eva_student_feedback.class_id,eva_student_feedback_question.question,eva_student_feedback_faculty.subject_id,eva_student_feedback_faculty.teacher_id";
		    Query query = session.createSQLQuery(sql);
					
			objects = query.list();
			return objects;
		}
		catch(Exception e)
		{
			log.debug("Exception" + e.getMessage());
		}
		return objects;
	}
	public List<Object[]> getTeachersByClass(String classId)
	{

		log.debug("call of getTeachers method in FacultyEvaluationReportPrincipalImpl.class");
		Session session = null;
		List<Object[]> teachers = null;
		try{
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			String sql = "select distinct eva_student_feedback_faculty.subject_id, eva_student_feedback_faculty.teacher_id from eva_student_feedback_faculty" +
					" inner join eva_student_feedback ON eva_student_feedback_faculty.eva_student_feedback_id = eva_student_feedback.id" +
					" where eva_student_feedback_faculty.eva_student_feedback_id = eva_student_feedback.id" +
					" and eva_student_feedback.class_id="+classId;
			Query query = session.createSQLQuery(sql);					
		    teachers = query.list();
			return teachers;
		}
		catch(Exception e)
		{
			log.debug("Exception" + e.getMessage());
		}
		return teachers;
	}
	public List<Object[]> getTeachersByDepartment(String subjectId)
	{

		log.debug("call of getTeachers method in FacultyEvaluationReportPrincipalImpl.class");
		Session session = null;
		List<Object[]> teachers = null;
		try{
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			String deptQueryString = "select s.department.id from Subject s where s.id ="+subjectId;
			Query deptIdQuery = session.createQuery(deptQueryString);
			Integer deptId = (Integer) deptIdQuery.uniqueResult();
			String sql = "select distinct eva_student_feedback_faculty.subject_id, eva_student_feedback_faculty.teacher_id " +
					" from eva_student_feedback_faculty " +
					" inner join eva_student_feedback ON eva_student_feedback_faculty.eva_student_feedback_id = eva_student_feedback.id " +
					" inner join subject ON eva_student_feedback_faculty.subject_id = subject.id" +
					" inner join users ON eva_student_feedback_faculty.teacher_id = users.id" +
					" inner join employee ON users.employee_id = employee.id" +
					" where eva_student_feedback_faculty.eva_student_feedback_id = eva_student_feedback.id " +
					" and employee.department_id="+deptId;
			Query query = session.createSQLQuery(sql);					
		    teachers = query.list();
			return teachers;
		}
		catch(Exception e)
		{
			log.debug("Exception" + e.getMessage());
		}
		return teachers;
	}
	@Override
	public boolean isDataExist(
			FacultyEvaluationReportPrincipalForm facultyEvaluationReportPrincipalForm) {
		log.debug("call of isDataExist method in FacultyEvaluationReportImpl.class");
		Session session = null;
		EvaStudentFeedbackAverage data = null;
		boolean exist = false;
		try{
			session = HibernateUtil.getSession();
			Query query = session.createQuery(" from EvaStudentFeedbackAverage where classId="+facultyEvaluationReportPrincipalForm.getClassId());
			data=(EvaStudentFeedbackAverage) query.uniqueResult();
			if(data!=null)
				exist = true;			
		}catch(Exception e)
		{
			log.debug("Exception" + e.getMessage());
		}
		return exist;
	}
	public List<Object[]> getTeachersAndSubjects(FacultyEvaluationReportPrincipalForm facultyEvaluationReportPrincipalForm)
	{
		log.debug("call of getTeachersAndSubjects method in FacultyEvaluationReportPrincipalImpl.class");
		Session session = null;
		List<Object[]> details = null;
		try{
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			/*String sql = "select eva_student_feedback_overall_average.teacher_id,eva_student_feedback_overall_average.subject_id,employee.first_name,s.name as su," +
					" eva_student_feedback_overall_average.overall_average,eva_student_feedback_score.total_count," +
					" eva_student_feedback_average.class_id,c.name" +
					" from eva_student_feedback_overall_average" +
					" inner join eva_student_feedback_average ON eva_student_feedback_overall_average.eva_student_feedback_average_id = eva_student_feedback_average.id" +
					" inner join eva_student_feedback_score on eva_student_feedback_score.eva_student_feedback_overall_average_id = eva_student_feedback_overall_average.id" +
					" inner join users ON eva_student_feedback_overall_average.teacher_id = users.id" +
					" inner join employee ON users.employee_id = employee.id" +
					" inner join department ON employee.department_id = department.id" +
					" inner join classes  c ON eva_student_feedback_average.class_id = c.id" +
					" inner join subject  s ON eva_student_feedback_overall_average.subject_id = s.id" +
					" where eva_student_feedback_average.year=:year" +
					" and employee.department_id=:departmentId and c.term_number=:schemeNo" +
					" group by eva_student_feedback_average.class_id,eva_student_feedback_overall_average.subject_id";			*/
			String sql = "select eva_student_feedback_overall_average.teacher_id," +
					" employee.first_name," +
					" cast(avg(eva_student_feedback_overall_average.overall_average) as decimal(10,2))," +
					" cast(avg(eva_student_feedback_score.total_count) as decimal(10,2))," +
					" program_type.name as p" +
					" from eva_student_feedback_overall_average" +
					" inner join eva_student_feedback_average ON eva_student_feedback_overall_average.eva_student_feedback_average_id = eva_student_feedback_average.id" +
					" inner join eva_student_feedback_score on eva_student_feedback_score.eva_student_feedback_overall_average_id = eva_student_feedback_overall_average.id" +
					" inner join users ON eva_student_feedback_overall_average.teacher_id = users.id" +
					" inner join employee ON users.employee_id = employee.id" +
					" inner join department ON employee.department_id = department.id" +
					" inner join classes c ON eva_student_feedback_average.class_id = c.id" +
					" inner join course ON c.course_id = course.id" +
					" inner join program ON course.program_id = program.id" +
					" inner join program_type ON program.program_type_id = program_type.id" +
					" inner join subject s ON eva_student_feedback_overall_average.subject_id = s.id" +
					" where eva_student_feedback_average.year=:year" +
					" and employee.department_id=:departmentId and c.term_number=:schemeNo" +
					" group by employee.id";
			Query query = session.createSQLQuery(sql);	
			query.setString("year", facultyEvaluationReportPrincipalForm.getAcademicYear());
			query.setString("departmentId", facultyEvaluationReportPrincipalForm.getDepartmentId());
			query.setString("schemeNo", facultyEvaluationReportPrincipalForm.getSchemeNo());
			details = query.list();
			return details;
		}
		catch(Exception e)
		{
			log.debug("Exception" + e.getMessage());
		}
		return details;
	}
	public List<Object[]> getFacultyEvaluationDetailsDepartmentWise(String teacher, String subject, FacultyEvaluationReportPrincipalForm facultyEvaluationReportPrincipalForm,String classId)
	{
		log.debug("call of getFacultyEvaluationDetailsDepartmentWise method in FacultyEvaluationReportPrincipalImpl.class");
		Session session = null;
		List<Object[]> details = null;
		try{
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			/*String sql = " select employee.first_name,department.name,subject.id," +
					" eva_student_feedback_score.question_id,eva_student_feedback_score.score, " +
					" eva_student_feedback_overall_average.overall_average,eva_student_feedback_score.total_count," +
					" eva_student_feedback_average.class_id" +
					" from eva_student_feedback_overall_average" +
					" inner join eva_student_feedback_average ON eva_student_feedback_overall_average.eva_student_feedback_average_id = eva_student_feedback_average.id" +
					" inner join eva_student_feedback_score on eva_student_feedback_score.eva_student_feedback_overall_average_id = eva_student_feedback_overall_average.id" +
					" inner join users ON eva_student_feedback_overall_average.teacher_id = users.id" +
					" inner join employee ON users.employee_id = employee.id" +
					" inner join department ON employee.department_id = department.id" +
					" inner join classes ON eva_student_feedback_average.class_id = classes.id" +
					" inner join subject ON eva_student_feedback_overall_average.subject_id = subject.id" +
					" where eva_student_feedback_average.year=:year" +
					" and employee.department_id=:departmentId and classes.term_number=:schemeNo" +
					" and eva_student_feedback_overall_average.subject_id=:subject" +
					" and eva_student_feedback_overall_average.teacher_id=:teacher" +
					" and eva_student_feedback_average.class_id=:classId";
			*/
			String sql = "select eva_student_feedback_overall_average.teacher_id," +
					" eva_student_feedback_score.question_id," +
					" employee.first_name," +
					" cast(avg(eva_student_feedback_score.score ) as decimal(10,2))," +
					" avg(eva_student_feedback_overall_average.overall_average)," +
					" avg(eva_student_feedback_score.total_count)," +
					" program_type.name as p" +
					" from eva_student_feedback_overall_average" +
					" inner join eva_student_feedback_average ON eva_student_feedback_overall_average.eva_student_feedback_average_id = eva_student_feedback_average.id" +
					" inner join eva_student_feedback_score on eva_student_feedback_score.eva_student_feedback_overall_average_id = eva_student_feedback_overall_average.id" +
					" inner join users ON eva_student_feedback_overall_average.teacher_id = users.id" +
					" inner join employee ON users.employee_id = employee.id" +
					" inner join department ON employee.department_id = department.id" +
					" inner join classes c ON eva_student_feedback_average.class_id = c.id" +
					" inner join course ON c.course_id = course.id" +
					" inner join program ON course.program_id = program.id" +
					" inner join program_type ON program.program_type_id = program_type.id" +
					" inner join subject s ON eva_student_feedback_overall_average.subject_id = s.id" +
					" where eva_student_feedback_average.year=:year" +
					" and employee.department_id=:departmentId and c.term_number=:schemeNo" +
					" and eva_student_feedback_overall_average.teacher_id=:teacher" +
					" group by employee.id,eva_student_feedback_score.question_id";
			Query query = session.createSQLQuery(sql);	
			query.setString("year", facultyEvaluationReportPrincipalForm.getAcademicYear());
			query.setString("departmentId", facultyEvaluationReportPrincipalForm.getDepartmentId());
			query.setString("schemeNo", facultyEvaluationReportPrincipalForm.getSchemeNo());
			//query.setString("subject", subject);
			query.setString("teacher", teacher);
			//query.setString("classId", classId);
			details = query.list();
			return details;
		}
		catch(Exception e)
		{
			log.debug("Exception" + e.getMessage());
		}
		return details;
	}

	@Override
	public Department getDepartment(
			FacultyEvaluationReportPrincipalForm facultyEvaluationReportPrincipalForm) {
		log.debug("call of getDepartment method in FacultyEvaluationReportImpl.class");
		Session session = null;
		Department data = null;
		try{
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from Department d where d.id="+facultyEvaluationReportPrincipalForm.getDepartmentId());
			data=(Department) query.uniqueResult();						
		}catch(Exception e)
		{
			log.debug("Exception" + e.getMessage());
		}
		return data;
	}
	
	/*public List<EvaluationStudentFeedbackFaculty> getRemarksGivenByStudent(String teacherId, String subId) {
		log.debug("call of getRemarksGivenByStudent method in FacultyEvaluationReportPrincipalImpl.class");
		Session session = null;
		List<EvaluationStudentFeedbackFaculty> data = null;
		try{
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from EvaluationStudentFeedbackFaculty e where "+
					" e.faculty.id=" +teacherId+
					" and e.remarks is not null");	
			data=query.list();					
		}catch(Exception e)
		{
			log.debug("Exception" + e.getMessage());
		}
		return data;
	}*/
	
	public List<EvaluationStudentFeedbackFaculty> getRemarksGivenByStudent(FacultyEvaluationReportPrincipalForm facultyEvaluationReportPrincipalForm, String teacher) {
		log.debug("call of getRemarksGivenByStudent method in FacultyEvaluationReportPrincipalImpl.class");
		Session session = null;
		List<Object[]> obj = null;
		List<EvaluationStudentFeedbackFaculty> data = null;
		try{
			session = HibernateUtil.getSession();
			Query query = session.createQuery("select e from EvaluationStudentFeedbackFaculty e" +
					" inner join e.evaStuFeedback.classes.classSchemewises c" +
					" where e.remarks is not null" +
					" and c.curriculumSchemeDuration.academicYear=" +facultyEvaluationReportPrincipalForm.getAcademicYear() +
					" and c.curriculumSchemeDuration.semesterYearNo=" +facultyEvaluationReportPrincipalForm.getSchemeNo() +
					" and e.subject.department.id=" + facultyEvaluationReportPrincipalForm.getDepartmentId() +
					" and e.faculty.id="+teacher);	
			data=query.list();
		}catch(Exception e)
		{
			log.debug("Exception" + e.getMessage());
		}
		return data;
	}
	
}
