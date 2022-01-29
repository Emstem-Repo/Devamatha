package com.kp.cms.transactionsimpl.studentfeedback;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.studentfeedback.EvaStudentFeedbackAverage;
import com.kp.cms.bo.studentfeedback.EvaStudentFeedbackOverallAverage;
import com.kp.cms.bo.studentfeedback.EvaStudentFeedbackQuestion;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.studentfeedback.EvaluateFacultyFeedbackForm;
import com.kp.cms.forms.studentfeedback.FacultyEvaluationReportForm;
import com.kp.cms.transactions.studentfeedback.IEvaluateFacultyFeedback;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;


public class EvaluateFacultyFeedbackImpl implements IEvaluateFacultyFeedback{
	private static final Log log = LogFactory.getLog(EvaluateFacultyFeedbackImpl.class);
	private static volatile EvaluateFacultyFeedbackImpl evaluateFacultyFeedbackImpl = null;

	public static EvaluateFacultyFeedbackImpl getInstance() {
		if (evaluateFacultyFeedbackImpl == null) {
			evaluateFacultyFeedbackImpl = new EvaluateFacultyFeedbackImpl();
			return evaluateFacultyFeedbackImpl;
		}
		return evaluateFacultyFeedbackImpl;
	}
	public Map<Integer, String> getClasses(EvaluateFacultyFeedbackForm evaluateFacultyFeedbackForm, int year) throws Exception
	{
        log.debug("call of getClasses method in EvaluateFacultyFeedbackImpl");
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
					" where eva_student_feedback_session.academic_year=:academicYear");
			Query query = session.createSQLQuery(sql);
			query.setInteger("academicYear", year);
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
	public List<Object[]> getTeachersByClass(String classId)
	{

		log.debug("call of getTeachers method in EvaluateFacultyFeedbackImpl.class");
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
	public List<EvaStudentFeedbackQuestion> getEvaluationQuestions(){
		log.debug("call of getEvaluationQuestions method in EvaluateFacultyFeedbackImpl.class");
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
	public List<Object[]> getAnswers(EvaluateFacultyFeedbackForm evaluateFacultyFeedbackForm,int queId,int ansId,String subjectId)
	{
		log.debug("call of getSubjects method in EvaluateFacultyFeedbackImpl.class");
		Session session = null;
		List<Object[]> objects = null;
		try{
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();		
			session = HibernateUtil.getSession();
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
					" where eva_student_feedback_faculty.teacher_id=" +evaluateFacultyFeedbackForm.getTeacherId()+"" +
					" and eva_student_feedback_faculty.subject_id=" +subjectId+"" +
					" and eva_student_feedback.class_id=" +evaluateFacultyFeedbackForm.getClassId()+"" +
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
	
	public boolean setAverage(EvaStudentFeedbackAverage avg) {
		Session session = null;
		Transaction transaction = null;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			session.save(avg);
			transaction.commit();
			session.flush();
			//session.close();
			return true;
		} catch (Exception e) {
			if ( transaction != null){
				transaction.rollback();
			}
			if (session != null){
				session.flush();
				//session.close();
			}
			return false;
		}		
	}
	
	public boolean setOverallAverage(
			Set<EvaStudentFeedbackOverallAverage> overallAvg) {
		Session session = null;
		Transaction transaction = null;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			session.save(overallAvg);
			transaction.commit();
			session.flush();
			//session.close();
			return true;
		} catch (Exception e) {
			if ( transaction != null){
				transaction.rollback();
			}
			if (session != null){
				session.flush();
				//session.close();
			}
			return false;
		}		
	}
	public boolean setScore(EvaStudentFeedbackOverallAverage overallAvgBo) {

		Session session = null;
		Transaction transaction = null;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			session.save(overallAvgBo);
			transaction.commit();
			session.flush();
			//session.close();
			return true;
		} catch (Exception e) {
			if ( transaction != null){
				transaction.rollback();
			}
			if (session != null){
				session.flush();
				//session.close();
			}
			return false;
		}		
	
	}
	public boolean deleteIfExist(
			EvaluateFacultyFeedbackForm evaluateFacultyFeedbackForm) throws Exception {
		log.debug("inside deleleAlreadyExistedRecords");
		Session session = null;
		Transaction tx = null;
		try {
			session = InitSessionFactory.getInstance().openSession();
		    tx= session.beginTransaction();
			Query query = session.createQuery(" delete from EvaStudentFeedbackAverage s where s.classId = " + evaluateFacultyFeedbackForm.getClassId());
   		    query.executeUpdate();
			tx.commit();
			session.flush();
			session.close();
			log.debug("leaving deleleAlreadyExistedRecords");
			return true;
		} catch (ConstraintViolationException e) {
			tx.rollback();
			log.error("Error while deleting"	, e);
			throw new BusinessException(e);
		} catch (Exception e) {
			tx.rollback();
			log.error("Error while deleting" + e);
			throw new ApplicationException(e);
		}
		
	}
	@Override
	public int getSemester(String classId) {
		log.debug("call of getSemester method in EvaluateFacultyFeedbackImpl.class");
        Session session = null;
        int semester = 0;
        try{
        	// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session.createQuery("select c.termNumber from Classes c where c.id="+classId);
			semester=(Integer) query.uniqueResult();
			return semester;
        }catch (Exception e) {
			log.debug("Exception" + e.getMessage());
		}
        return semester;
	}
	
}