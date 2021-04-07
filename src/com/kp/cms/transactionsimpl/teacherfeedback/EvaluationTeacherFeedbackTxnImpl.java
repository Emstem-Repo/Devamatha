package com.kp.cms.transactionsimpl.teacherfeedback;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.WordUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.EvaluationStudentFeedbackAnswer;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.TeacherClassSubject;
import com.kp.cms.bo.admin.Users;
import com.kp.cms.bo.exam.ExamStudentOverallInternalMarkDetailsBO;
import com.kp.cms.bo.teacherfeedback.EvaTeacherFeedbackOpenConnection;
import com.kp.cms.bo.teacherfeedback.EvaluationTeacherFeedback;
import com.kp.cms.bo.teacherfeedback.EvaluationTeacherFeedbackAnswer;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.teacherfeedback.EvaluationTeacherFeedbackForm;
import com.kp.cms.to.teacherfeedback.EvaTeacherFeedBackQuestionTo;
import com.kp.cms.to.teacherfeedback.EvaluationTeacherFeedbackTo;
import com.kp.cms.transactions.teacherfeedback.IEvaluationTeacherFeedback;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.HibernateUtil;

public class EvaluationTeacherFeedbackTxnImpl implements IEvaluationTeacherFeedback{
	private static final Log log = LogFactory.getLog(EvaluationTeacherFeedbackTxnImpl.class);

	public static volatile EvaluationTeacherFeedbackTxnImpl evaluationTeacherFeedbackImpl = null;
	public static EvaluationTeacherFeedbackTxnImpl getInstance(){
		if(evaluationTeacherFeedbackImpl == null){
			evaluationTeacherFeedbackImpl = new EvaluationTeacherFeedbackTxnImpl();
			return evaluationTeacherFeedbackImpl;
		}
		return evaluationTeacherFeedbackImpl;
	}


	@Override
	public List<EvaTeacherFeedbackOpenConnection> getConnectionClassesList(EvaluationTeacherFeedbackForm evaTeacherFeedbackForm) throws Exception {
		Session session = null;
		List<EvaTeacherFeedbackOpenConnection> connection;
		try{
			String date = CommonUtil.getTodayDate();
			Date curDate = CommonUtil.ConvertStringToSQLDate(date);

			session = HibernateUtil.getSession();
			String str = "from EvaTeacherFeedbackOpenConnection conn " +
			" where conn.isActive=1 and conn.feedbackSession.academicYear="+Integer.parseInt(evaTeacherFeedbackForm.getYear())+
			" and ('" +  curDate + "' >= conn.startDate and '" +
			curDate + "' <= conn.endDate) and conn.classesId in ( select tcs.classId.classes.id from TeacherClassSubject tcs " +
			" where tcs.teacherId = ? and tcs.year=? and tcs.classId.classes.isActive=1 and tcs.isActive=1 and tcs.subject.isActive=1) ";
			Query query = session.createQuery(str);
			query.setInteger(0, Integer.parseInt(evaTeacherFeedbackForm.getTeacher()));
			query.setString(1, evaTeacherFeedbackForm.getYear());

			connection = query.list();
		}catch (Exception e) {
			throw new ApplicationException(e);
		}
		finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		return connection;
	}


	@Override
	public List<Student> getStudentsForFeedBack(EvaluationTeacherFeedbackForm evaTeacherFeedbackForm)
	throws Exception {
		Session session = null;
		List<Student> students;
		List<Student> stu = null;	
		List<Integer> studentIds;
		try{
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from Student s where s.classSchemewise.classes.id="+evaTeacherFeedbackForm.getClassSchemewiseId().split("_")[0]+
					" and s.admAppln.id in (select a.admAppln.id from ApplicantSubjectGroup a where a.subjectGroup.id in (select sgs.subjectGroup.id from SubjectGroupSubjects sgs " +
					" where sgs.subject.id="+evaTeacherFeedbackForm.getSubjectId()+" and sgs.subject.isActive=1))");
			students = (List<Student>) query.list();
			if(students.size()==0)
			{
				Query query1 = session.createQuery("select espc.studentId from ExamStudentPreviousClassDetailsBO espc " +
						" where espc.studentId in" +
						" (select essh.studentId from ExamStudentSubGrpHistoryBO essh" +
						" where essh.schemeNo=(select c.termNumber from Classes c" +
						" where c.id="+evaTeacherFeedbackForm.getClassSchemewiseId().split("_")[0]+") and essh.subjectGroupId in" +
						" (select sgs.subjectGroup.id from SubjectGroupSubjects sgs where sgs.subject.id="+evaTeacherFeedbackForm.getSubjectId()+" and sgs.subject.isActive=1))" +
						" and espc.classId="+evaTeacherFeedbackForm.getClassSchemewiseId().split("_")[0]);
				studentIds = (List<Integer>) query1.list();
				if(studentIds.size()!=0)
				{
					Query query3 = session.createQuery("from Student s where s.id in (:studentIds)");
					query3.setParameterList("studentIds",studentIds);
					stu = (List<Student>) query3.list();

				}
				return stu;
			}else
				return students;
		}catch (Exception e) {
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}

	}	

	@Override
	public boolean checkStudentIsDuplicate(EvaluationTeacherFeedbackForm evaTeacherFeedbackForm) throws Exception {
		Session session =null;
		boolean isDuplicate = false;
		try{
			int teacherId = Integer.parseInt(evaTeacherFeedbackForm.getTeacher());
			int sessionId = Integer.parseInt(evaTeacherFeedbackForm.getClassSchemewiseId().split("_")[1]);
			int studentId = evaTeacherFeedbackForm.getStudentId();
			session = HibernateUtil.getSession();
			String str = " from EvaluationTeacherFeedback teacherEvaluation join teacherEvaluation.feedbackStudent stu" +
			" where teacherEvaluation.isActive = 1" +
			" and teacherEvaluation.teacher.id = "+teacherId+" and teacherEvaluation.facultyEvaluationSession.id = " + sessionId +
			" and stu.student.id = "+studentId+" and stu.isActive = 1";
			Query query = session.createQuery(str);
			List<EvaluationTeacherFeedback> bo = (List<EvaluationTeacherFeedback>) query.list();
			if(bo!=null && !bo.toString().isEmpty() && bo.size()!=0){
				isDuplicate = true;
			}
		}catch (Exception e) {
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		return isDuplicate;
	}

	@Override
	public EvaluationTeacherFeedback getExistsBO(EvaluationTeacherFeedbackForm evaTeacherFeedbackForm) throws Exception {
		Session session =null;
		EvaluationTeacherFeedback evaluationFeedback;
		try{
			/* before saving the bo once again we are checking the duplicate entry */
			int teacherId = Integer.parseInt(evaTeacherFeedbackForm.getTeacher());
			int sessionId = Integer.parseInt(evaTeacherFeedbackForm.getClassSchemewiseId().split("_")[0]);
			session = HibernateUtil.getSession();
			String str = "from EvaluationTeacherFeedback teacherEvaluation where teacherEvaluation.isActive = 1 "
				+ "and teacherEvaluation.teacher.id="
				+ teacherId
				+ " and teacherEvaluation.facultyEvaluationSession.id=" + sessionId;
			Query query = session.createQuery(str);
			evaluationFeedback = (EvaluationTeacherFeedback) query.uniqueResult();
		}catch (Exception e) {
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		return evaluationFeedback;
	}

	@Override
	public boolean saveTeacherEvaluationFeedback(EvaluationTeacherFeedback evaluationTeacherFeedback, EvaluationTeacherFeedbackForm evaTeacherFeedbackForm) throws Exception {
		Session session = null;
		boolean isAdded = false;
		Transaction tx =null;
		try{
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			tx.begin();
			session.saveOrUpdate(evaluationTeacherFeedback);
			tx.commit();
			isAdded = true;
		}catch (Exception e) {
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		return isAdded;
	}

	@Override
	public Map<Integer, String> getClassesListForReport(EvaluationTeacherFeedbackForm evaTeacherFeedbackForm) throws Exception {
		Session session = null;
		Map<Integer, String> classesForReport=null;
		try{

			session = HibernateUtil.getSession();
			if(evaTeacherFeedbackForm.getYear()!=null && !evaTeacherFeedbackForm.getYear().isEmpty())
			{
				String str = "select distinct(feedback.classes.id),feedback.classes.name  from EvaluationTeacherFeedback feedback " +
				" where feedback.isActive=1 and feedback.facultyEvaluationSession.academicYear="+Integer.parseInt(evaTeacherFeedbackForm.getYear())+
				" and feedback.teacher.id =:teacherId";
				Query query = session.createQuery(str);
				if(evaTeacherFeedbackForm.getRoleId()==19)
					query.setInteger("teacherId", Integer.parseInt(evaTeacherFeedbackForm.getUserId()));
				else
					query.setInteger("teacherId", evaTeacherFeedbackForm.getTeacherId());
				List<Object[]> classes = (List<Object[]>) query.list();
				if(classes!=null && classes.size()!=0){
					classesForReport = new HashMap<Integer, String>();
					Iterator<Object[]> itr = classes.iterator();
					while (itr.hasNext()) {
						Object[] bo =  itr.next();
						classesForReport.put(Integer.parseInt(bo[0].toString()), bo[1].toString());					
					}
				}
			}
		}catch (Exception e) {
			throw new ApplicationException(e);
		}
		finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		return classesForReport;
	}
	
	
	@Override
	public List<EvaluationTeacherFeedbackAnswer> getTecherFeedbackDetailsForReport(EvaluationTeacherFeedbackForm evaTeacherFeedbackForm)
	throws Exception {
		Session session = null;
		List<EvaluationTeacherFeedbackAnswer> students;
		try{
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from EvaluationTeacherFeedbackAnswer s where s.feedbackStudent.subject.id="+evaTeacherFeedbackForm.getSubjectId()+
					" and s.feedbackStudent.evaTeacherFeedback.classes.id="+evaTeacherFeedbackForm.getClassSchemewiseId()+" and s.feedbackStudent.evaTeacherFeedback.teacher.id=:teacherId");
			if(evaTeacherFeedbackForm.getRoleId()==19)
				query.setInteger("teacherId", Integer.parseInt(evaTeacherFeedbackForm.getUserId()));
			else
				query.setInteger("teacherId", evaTeacherFeedbackForm.getTeacherId());
			students = (List<EvaluationTeacherFeedbackAnswer>) query.list();
			if(students!=null && students.size()!=0){
				Iterator<EvaluationTeacherFeedbackAnswer> itr = students.iterator();
				while(itr.hasNext()){
					EvaluationTeacherFeedbackAnswer bo = itr.next();
					evaTeacherFeedbackForm.setCourseName(WordUtils.capitalizeFully(bo.getFeedbackStudent().getEvaTeacherFeedback().getClasses().getCourse().getName()));
					evaTeacherFeedbackForm.setSemister(String.valueOf(bo.getFeedbackStudent().getEvaTeacherFeedback().getClasses().getTermNumber()));
					evaTeacherFeedbackForm.setSubjectName(WordUtils.capitalizeFully(bo.getFeedbackStudent().getSubject().getName()));
				}
			}
			return students;
		}catch (Exception e) {
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}

	}


	@Override
	public Map<Integer, Double> getStudentInternalMarks(EvaluationTeacherFeedbackForm evaTeacherFeedbackForm)
			throws Exception {
		Session session = null;
		Map<Integer, Double> marksMap=new HashMap<Integer, Double>();
		try{
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from ExamStudentOverallInternalMarkDetailsBO e where e.classId="+Integer.parseInt(evaTeacherFeedbackForm.getClassSchemewiseId())+
					" and e.subjectId="+Integer.parseInt(evaTeacherFeedbackForm.getSubjectId()));
			List<ExamStudentOverallInternalMarkDetailsBO> students =  query.list();
			if(students!=null && students.size()!=0){
				Iterator<ExamStudentOverallInternalMarkDetailsBO> itr = students.iterator();
				while(itr.hasNext()){
					ExamStudentOverallInternalMarkDetailsBO bo = itr.next();
					if(bo.getTheoryTotalMarks()!=null && !bo.getTheoryTotalMarks().isEmpty())
						marksMap.put(bo.getStudentId(), Double.parseDouble(bo.getTheoryTotalMarks()));
					else
						marksMap.put(bo.getStudentId(), Double.parseDouble(bo.getPracticalTotalMarks()));
				}
			}
			return marksMap;
		}catch (Exception e) {
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
	}	
	
	@Override
	public Map<Integer, List<EvaTeacherFeedBackQuestionTo>> getPointsScoredByTeacher(EvaluationTeacherFeedbackForm evaTeacherFeedbackForm)
			throws Exception {
		Session session = null;
		Map<Integer, List<EvaTeacherFeedBackQuestionTo>> teacherScoreMap=new HashMap<Integer, List<EvaTeacherFeedBackQuestionTo>>();
		try{
			session = HibernateUtil.getSession();
			Query query = session.createQuery("select esfa from EvaluationStudentFeedbackAnswer esfa" +
					" where esfa.feedbackFaculty.faculty.id=:teacherId" +
					" and esfa.feedbackFaculty.subject.id=" +Integer.parseInt(evaTeacherFeedbackForm.getSubjectId())+
					" and esfa.feedbackFaculty.evaStuFeedback.classes.id=" +Integer.parseInt(evaTeacherFeedbackForm.getClassSchemewiseId()));
			if(evaTeacherFeedbackForm.getRoleId()==19)
				query.setInteger("teacherId", Integer.parseInt(evaTeacherFeedbackForm.getUserId()));
			else
				query.setInteger("teacherId", evaTeacherFeedbackForm.getTeacherId());
			List<EvaluationStudentFeedbackAnswer> students =  query.list();		
			if(students!=null && students.size()!=0){
				Iterator<EvaluationStudentFeedbackAnswer> itr = students.iterator();
				while(itr.hasNext()){
					EvaluationStudentFeedbackAnswer bo = itr.next();
					if(teacherScoreMap.containsKey(bo.getFeedbackFaculty().getEvaStuFeedback().getStudent().getId())){
						List<EvaTeacherFeedBackQuestionTo> toList = teacherScoreMap.get(bo.getFeedbackFaculty().getEvaStuFeedback().getStudent().getId());
						EvaTeacherFeedBackQuestionTo to = new EvaTeacherFeedBackQuestionTo();
						to.setQuestion(String.valueOf(bo.getQuestionId().getId()));
						to.setAnswer(bo.getAnswer());
						toList.add(to);
					}else{
						List<EvaTeacherFeedBackQuestionTo> toList = new ArrayList<EvaTeacherFeedBackQuestionTo>();
						EvaTeacherFeedBackQuestionTo to = new EvaTeacherFeedBackQuestionTo();
						to.setQuestion(String.valueOf(bo.getQuestionId().getId()));
						to.setAnswer(bo.getAnswer());
						toList.add(to);
						teacherScoreMap.put(bo.getFeedbackFaculty().getEvaStuFeedback().getStudent().getId(), toList);
					}
				}
				
			}
			return teacherScoreMap;
		}catch (Exception e) {
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
	}


	@Override
	public Map<Integer, String> getSubjectsByClassForTeacher(
			EvaluationTeacherFeedbackForm evaTeacherFeedbackForm)
			throws Exception {
		Session session = null;
		Map<Integer, String> marksMap=new HashMap<Integer, String>();
		try{
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from TeacherClassSubject e where e.classId.classes.id="+Integer.parseInt(evaTeacherFeedbackForm.getClassSchemewiseId().split("_")[0])+
					" and e.teacherId.id=:teacherId");
			if(evaTeacherFeedbackForm.getRoleId()==19)
				query.setInteger("teacherId", Integer.parseInt(evaTeacherFeedbackForm.getUserId()));
			else
				query.setInteger("teacherId", evaTeacherFeedbackForm.getTeacherId());
			List<TeacherClassSubject> subjects =  query.list();
			if(subjects!=null && subjects.size()!=0){
				Iterator<TeacherClassSubject> itr = subjects.iterator();
				while(itr.hasNext()){
					TeacherClassSubject bo = itr.next();
					if(bo.getSubject()!=null)
						marksMap.put(bo.getSubject().getId(), bo.getSubject().getName());					
				}
			}
			return marksMap;
		}catch (Exception e) {
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
	}	
	
	@Override
	public List<Integer> getStudentsFeedbackAleardyGiven(EvaluationTeacherFeedbackForm evaTeacherFeedbackForm) throws Exception {
		Session session =null;
		List<Integer> students = null;
		try{
			int teacherId = Integer.parseInt(evaTeacherFeedbackForm.getTeacher());
			int sessionId = Integer.parseInt(evaTeacherFeedbackForm.getClassSchemewiseId().split("_")[1]);
			session = HibernateUtil.getSession();
			String str = "select stu.student.id from EvaluationTeacherFeedback teacherEvaluation join teacherEvaluation.feedbackStudent stu" +
			" where teacherEvaluation.isActive = 1" +
			" and teacherEvaluation.teacher.id = "+teacherId+" and teacherEvaluation.facultyEvaluationSession.id = " + sessionId +
			" and stu.isActive = 1";
			Query query = session.createQuery(str);
			students = query.list();
			
		}catch (Exception e) {
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				//session.flush();
				//session.close();
			}
		}
		return students;
	}


	@Override
	public Map<Integer, String> getTeacherMapByYearForReport(
			EvaluationTeacherFeedbackForm evaTeacherFeedbackForm)
			throws Exception {
		Session session = null;
		Map<Integer, String> teacherMap=null;
		try{

			session = HibernateUtil.getSession();
			if(evaTeacherFeedbackForm.getYear()!=null && !evaTeacherFeedbackForm.getYear().isEmpty())
			{
				Query query = session.createQuery("select distinct(e.teacherId) from TeacherClassSubject e where e.year="+evaTeacherFeedbackForm.getYear());

				List<Users> teachers = (List<Users>) query.list();
				if(teachers!=null && teachers.size()!=0){
					teacherMap = new HashMap<Integer, String>();
					Iterator<Users> itr = teachers.iterator();
					while (itr.hasNext()) {
						Users bo =  itr.next();
						teacherMap.put(bo.getId(), bo.getEmployee().getFirstName());					
					}
				}
			}
		}catch (Exception e) {
			throw new ApplicationException(e);
		}
		finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		return teacherMap;
	}
}
