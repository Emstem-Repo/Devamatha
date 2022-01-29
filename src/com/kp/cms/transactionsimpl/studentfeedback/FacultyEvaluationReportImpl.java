package com.kp.cms.transactionsimpl.studentfeedback;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;

import com.kp.cms.bo.admin.EvaluationStudentFeedbackFaculty;
import com.kp.cms.bo.studentfeedback.EvaStudentFeedbackAverage;
import com.kp.cms.bo.studentfeedback.EvaStudentFeedbackQuestion;
import com.kp.cms.forms.studentfeedback.FacultyEvaluationReportForm;
import com.kp.cms.transactions.studentfeedback.IFacultyEvaluationReport;
import com.kp.cms.utilities.HibernateUtil;

public class FacultyEvaluationReportImpl implements IFacultyEvaluationReport{
	private static final Log log = LogFactory.getLog(FacultyEvaluationReportImpl.class);
	private static volatile FacultyEvaluationReportImpl facultyEvaluationReportImpl = null;

	public static FacultyEvaluationReportImpl getInstance() {
		if (facultyEvaluationReportImpl == null) {
			facultyEvaluationReportImpl = new FacultyEvaluationReportImpl();
			return facultyEvaluationReportImpl;
		}
		return facultyEvaluationReportImpl;
	}
	
	public Map<Integer, String> getClasses(FacultyEvaluationReportForm facultyEvaluationReportForm, int year) throws Exception
	{
        log.debug("call of getClasses method in FacultyEvaluationReportImpl.class");
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
			query.setString("userId", facultyEvaluationReportForm.getUserId());			
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
	public Map<Integer, String> getSubjects(FacultyEvaluationReportForm facultyEvaluationReportForm, int year) throws Exception
	{
        log.debug("call of getSubjects method in FacultyEvaluationReportImpl.class");
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
			query.setString("classId", facultyEvaluationReportForm.getClassId());			
			query.setString("userId", facultyEvaluationReportForm.getUserId());			
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
		log.debug("call of getEvaluationQuestions method in FacultyEvaluationReportImpl.class");
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
	public List<Object[]> getFacultyEvaluationDetails(FacultyEvaluationReportForm facultyEvaluationReportForm) throws Exception
	{
		log.debug("call of getSubjects method in FacultyEvaluationReportImpl.class");
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
			query.setString("classId", facultyEvaluationReportForm.getClassId());	
			query.setString("subjectId", facultyEvaluationReportForm.getSubjectId());	
			query.setString("userId", facultyEvaluationReportForm.getUserId());			
			objects = query.list();
			return objects;
		}
		catch(Exception e)
		{
			log.debug("Exception" + e.getMessage());
		}
		return objects;
	}	
	public List<Object[]> getAnswers(FacultyEvaluationReportForm facultyEvaluationReportForm,int queId,int ansId,String teacherId,String subjectId)
	{
		log.debug("call of getSubjects method in FacultyEvaluationReportImpl.class");
		Session session = null;
		List<Object[]> objects = null;
		try{
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			String userId = facultyEvaluationReportForm.getUserId();
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
					" and eva_student_feedback_faculty.subject_id=" +subjectId+"" +
					" and eva_student_feedback.class_id=" +facultyEvaluationReportForm.getClassId()+"" +
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

		log.debug("call of getTeachers method in FacultyEvaluationReportImpl.class");
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
	public List<Object[]> getTeachersByDepartment(FacultyEvaluationReportForm facultyEvaluationReportForm)
	{

		log.debug("call of getTeachers method in FacultyEvaluationReportImpl.class");
		Session session = null;
		List<Object[]> teachers = null;
		try{
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			String deptQueryString = "select s.employee.department.id from Users s where s.id ="+facultyEvaluationReportForm.getUserId();
			Query deptIdQuery = session.createQuery(deptQueryString);
			Integer deptId = (Integer) deptIdQuery.uniqueResult();
			String sql = "select distinct eva_student_feedback_faculty.subject_id, eva_student_feedback_faculty.teacher_id " +
					" from eva_student_feedback_faculty " +
					" inner join eva_student_feedback ON eva_student_feedback_faculty.eva_student_feedback_id = eva_student_feedback.id " +
					" inner join subject ON eva_student_feedback_faculty.subject_id = subject.id" +
					" inner join users ON eva_student_feedback_faculty.teacher_id = users.id" +
					" inner join employee ON users.employee_id = employee.id" +
					" where eva_student_feedback_faculty.eva_student_feedback_id = eva_student_feedback.id " +
					" and employee.department_id="+deptId+
					" and eva_student_feedback.class_id="+facultyEvaluationReportForm.getClassId();
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
			FacultyEvaluationReportForm facultyEvaluationReportForm) {
		log.debug("call of isDataExist method in FacultyEvaluationReportImpl.class");
		Session session = null;
		EvaStudentFeedbackAverage data = null;
		boolean exist = false;
		try{
			session = HibernateUtil.getSession();
			Query query = session.createQuery(" from EvaStudentFeedbackAverage where classId="+facultyEvaluationReportForm.getClassId());
			data=(EvaStudentFeedbackAverage) query.uniqueResult();
			if(data!=null)
				exist = true;			
		}catch(Exception e)
		{
			log.debug("Exception" + e.getMessage());
		}
		return exist;
	}
	@Override
	public List<EvaluationStudentFeedbackFaculty> getRemarksGivenByStudent(
			FacultyEvaluationReportForm facultyEvaluationReportForm) {
		log.debug("call of getRemarksGivenByStudent method in FacultyEvaluationReportImpl.class");
		Session session = null;
		List<EvaluationStudentFeedbackFaculty> data = null;
		try{
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from EvaluationStudentFeedbackFaculty e where e.subject.id=" +facultyEvaluationReportForm.getSubjectId()+
					" and e.faculty.id=" +facultyEvaluationReportForm.getUserId()+
					" and e.remarks is not null and e.evaStuFeedback.id in (select esf.id from EvaluationStudentFeedback esf" +
					" where esf.classes.id="+facultyEvaluationReportForm.getClassId()+")");	
			data=query.list();					
		}catch(Exception e)
		{
			log.debug("Exception" + e.getMessage());
		}
		return data;
	}
}
