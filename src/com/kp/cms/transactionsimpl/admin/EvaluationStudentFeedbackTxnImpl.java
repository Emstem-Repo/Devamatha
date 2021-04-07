package com.kp.cms.transactionsimpl.admin;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.EvaluationStudentFeedback;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.StudentLogin;
import com.kp.cms.bo.admin.TeacherClassSubject;
import com.kp.cms.bo.studentfeedback.EvaHiddenSubjectTeacher;
import com.kp.cms.bo.studentfeedback.EvaStudentFeedbackGroup;
import com.kp.cms.bo.studentfeedback.EvaStudentFeedbackQuestion;
import com.kp.cms.bo.studentfeedback.ExitEvaluationQuestion;
import com.kp.cms.bo.studentfeedback.ExitEvaluationStudentFeedbackAnswer;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.admin.EvaluationStudentFeedbackForm;
import com.kp.cms.to.studentfeedback.EvaStudentFeedBackGroupTo;
import com.kp.cms.to.studentfeedback.EvaStudentFeedBackQuestionTo;
import com.kp.cms.transactions.admin.IEvaluationStudentFeedbackTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class EvaluationStudentFeedbackTxnImpl implements IEvaluationStudentFeedbackTransaction{
	public static volatile EvaluationStudentFeedbackTxnImpl evaluationStudentFeedbackTxnImpl = null;
	public static EvaluationStudentFeedbackTxnImpl getInstance(){
		if(evaluationStudentFeedbackTxnImpl == null){
			evaluationStudentFeedbackTxnImpl = new EvaluationStudentFeedbackTxnImpl();
			return evaluationStudentFeedbackTxnImpl;
		}
		return evaluationStudentFeedbackTxnImpl;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admin.IEvaluationStudentFeedbackTransaction#getStudentLoginDetails(java.lang.String)
	 */
	@Override
	public StudentLogin getStudentLoginDetails(String userId) throws Exception {
		Session session = null;
		StudentLogin studentLogin = null;
		try{
			session = HibernateUtil.getSession();
			String str = "from StudentLogin stuLogin where stuLogin.id ="+Integer.parseInt(userId)+ "and stuLogin.isActive = 1";
			Query query = session.createQuery(str);
			studentLogin = (StudentLogin) query.uniqueResult();
		}catch (Exception e) {
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		return studentLogin;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admin.IEvaluationStudentFeedbackTransaction#getSubjectIds(com.kp.cms.bo.admin.StudentLogin)
	 */
	@Override
	public List<Integer> getSubjectIds(int admApplnId) throws Exception {
		Session session = null;
		List<Integer> subjectIds = new ArrayList<Integer>();
		try{
//			int admApplnId = stuLogin.getStudent().getAdmAppln().getId();
			session = HibernateUtil.getSession();
			String str1 = "select applnSubGrp.subjectGroup.id from ApplicantSubjectGroup applnSubGrp where applnSubGrp.admAppln=" + admApplnId;
			Query query = session.createQuery(str1);
			List<Integer> subjectGroupIds = query.list();
			if (subjectGroupIds != null && !subjectGroupIds.isEmpty()) {
					/*Iterator<Integer> iterator =  subjectGroupIds.iterator();
					while (iterator.hasNext()) {
						Integer subGroupId = (Integer) iterator.next();
						String str2 = "select sub.subject.id from SubjectGroupSubjects sub where sub.subjectGroup.id = "+subGroupId+" and sub.isActive = 1";
						query = session.createQuery(str2);
						List<Integer> subjectIdsList = query.list();
						if(subjectIdsList!=null && !subjectIdsList.isEmpty()){
							Iterator<Integer> iterator2 = subjectIdsList.iterator();
							while (iterator2.hasNext()) {
								Integer subjId = (Integer) iterator2.next();
								if(subjId!=null && !subjId.toString().isEmpty()){
									subjectIds.add(subjId);
								}
							}
						}
					}*/
				String getSubjectsQuery = "select sub.subject.id from SubjectGroupSubjects sub where "
											+ "sub.subjectGroup.id in(:subjectGrpList) and sub.isActive = 1";
				Query query1 = session.createQuery(getSubjectsQuery);
				query1.setParameterList("subjectGrpList", subjectGroupIds);
				subjectIds = query1.list();
				}
		}catch (Exception e) {
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		return subjectIds;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admin.IEvaluationStudentFeedbackTransaction#getTeacherClassSubjects(java.util.List, com.kp.cms.bo.admin.StudentLogin)
	 */
	@Override
	public List<TeacherClassSubject> getTeacherClassSubjects(List<Integer> subjectIds,
			String classSchemeWiseId) throws Exception {
		Session session = null;
		List<TeacherClassSubject> classSubjects = new ArrayList<TeacherClassSubject>();
		try{
			session = HibernateUtil.getSession();
			int clsSchemeWiseId = 0;
			if(classSchemeWiseId!=null && !classSchemeWiseId.isEmpty()){
				clsSchemeWiseId = Integer.parseInt(classSchemeWiseId);
			}
			
			/*if(subjectIds != null && !subjectIds.isEmpty()){
				Iterator<Integer> iterator1 = subjectIds.iterator();
				while (iterator1.hasNext()) {
					Integer subjectId = (Integer) iterator1.next();
					String str3 = "from TeacherClassSubject teacherClassSubject " +
									"where teacherClassSubject.isActive = 1 and teacherClassSubject.classId.id="+classSchemeWiseId+
									" and teacherClassSubject.subject.id="+subjectId;
									 //commented code
//									+" and ((teacherClassSubject.subject.isTheoryPractical = 'T') or (teacherClassSubject.subject.isTheoryPractical = 'B'))"
					Query query = session.createQuery(str3);
					List<TeacherClassSubject> teacherClassSubjects = query.list();
					if(teacherClassSubjects!=null && !teacherClassSubjects.isEmpty()){
						EvaHiddenSubjectTeacher hideSubjectTeacher =null;
						Iterator<TeacherClassSubject> iterator = teacherClassSubjects.iterator();
						while (iterator.hasNext()) {
							TeacherClassSubject teacherClassSubject = (TeacherClassSubject) iterator .next();
							boolean add = false;
							String str = "from EvaHiddenSubjectTeacher hideTeacherSubject where hideTeacherSubject.teacherId.id="+ 
										 teacherClassSubject.getTeacherId().getId()+" and hideTeacherSubject.classId.id="+classSchemeWiseId
										 +" and hideTeacherSubject.subjectId.id="+teacherClassSubject.getSubject().getId()+" and hideTeacherSubject.isActive = 1";
							Query query2 = session.createQuery(str);
							 hideSubjectTeacher = (EvaHiddenSubjectTeacher) query2.uniqueResult();
							 if(hideSubjectTeacher == null){
								 classSubjects.add(teacherClassSubject);
							 }
							 // commented code 
							if(CMSConstants.LINK_FOR_CJC){
								 if(hideSubjectTeacher == null){
									 classSubjects.add(teacherClassSubject);
								 }
							 }else{
								 if(hideSubjectTeacher ==null){
									 add = true;
								 }
								 List<Integer> teacherIds = new ArrayList<Integer>();
								 String sqlQuery ="select distinct users.id from attendance_student " +
								 " inner join attendance on attendance_student.attendance_id = attendance.id " +
								 " inner join subject on attendance.subject_id = subject.id "+
								 " inner join attendance_class on attendance_class.attendance_id = attendance.id" +
								 " inner join attendance_instructor on attendance_instructor.attendance_id = attendance.id" +
								 " inner join users ON attendance_instructor.users_id = users.id" +
								 
								 " where attendance_student.student_id=" +stuLogin.getStudent().getId()+
								 " and attendance_class.class_schemewise_id="+stuLogin.getStudent().getClassSchemewise().getId() +
								 " and  users.id="+teacherClassSubject.getTeacherId().getId() +
								 " and subject.id="+teacherClassSubject.getSubject().getId()+
								 " and attendance.is_canceled =0";
								 teacherIds = session.createSQLQuery(sqlQuery).list();
								 if(teacherIds!=null && !teacherIds.isEmpty()&& add){
									 classSubjects.add(teacherClassSubject);
								 }
							 }
							 
							 //
						}
					}
				}
			}*/
			if (subjectIds != null && !subjectIds.isEmpty()) {
				String HQL_QUERY = "from TeacherClassSubject teacherClassSubject where teacherClassSubject.isActive = 1 "
									+ "and teacherClassSubject.classId.id=" + clsSchemeWiseId 
									+ "and teacherClassSubject.subject.id in (:subjectList)";
				Query query = session.createQuery(HQL_QUERY);
				query.setParameterList("subjectList", subjectIds);
				List<TeacherClassSubject> teacherClassSubjects = query.list();
				if (teacherClassSubjects != null && !teacherClassSubjects.isEmpty()) {
					List<EvaHiddenSubjectTeacher> hideSubjectTeacher = null;
					Iterator<TeacherClassSubject> iterator = teacherClassSubjects .iterator();
					while (iterator.hasNext()) {
						TeacherClassSubject teacherClassSubject = (TeacherClassSubject) iterator .next();
						// boolean add = false;
						String str = "from EvaHiddenSubjectTeacher hideTeacherSubject where hideTeacherSubject.teacherId.id=" + teacherClassSubject.getTeacherId().getId()  
									+ " and hideTeacherSubject.classId.id=" + clsSchemeWiseId
									+ " and hideTeacherSubject.subjectId.id=" + teacherClassSubject.getSubject().getId()
									+ " and hideTeacherSubject.isActive = 1";
						Query query2 = session.createQuery(str);
						hideSubjectTeacher =  query2 .list();
						if (hideSubjectTeacher == null || hideSubjectTeacher.isEmpty()) {
							classSubjects.add(teacherClassSubject);
						}
					}
				}
			}
		}catch (Exception e) {
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		return classSubjects;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admin.IEvaluationStudentFeedbackTransaction#getfacultyEvalQuestionList()
	 */
	@Override
	public List<EvaStudentFeedbackQuestion> getfacultyEvalQuestionList() throws Exception {
		Session session = null;
		List<EvaStudentFeedbackQuestion> facultyEvalQuestion = null;
		try{
			session = HibernateUtil.getSession();
			String str = "from EvaStudentFeedbackQuestion question where question.isActive = 1 and question.groupId.id=1 order by question.order";
			Query query = session.createQuery(str);
			facultyEvalQuestion = query.list();
		}catch (Exception e) {
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		return facultyEvalQuestion;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admin.IEvaluationStudentFeedbackTransaction#saveFacultyEvaluationFeedback(com.kp.cms.bo.admin.EvaluationStudentFeedback)
	 */
	@Override
	public boolean saveFacultyEvaluationFeedback( EvaluationStudentFeedback facultyEvaluationFeedback) throws Exception {
		Session session = null;
		Transaction tx = null;
		boolean isAdded = false;
		try{
			int studentId = facultyEvaluationFeedback.getStudent().getId();
			int classId = facultyEvaluationFeedback.getClasses().getId();
			int sessionId = facultyEvaluationFeedback.getFacultyEvaluationSession().getId();
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			tx.begin();
			/* checking duplicate*/
			String str= "from EvaluationStudentFeedback feedback where feedback.isActive = 1 " +
						" and feedback.student.id="+studentId+
						" and feedback.classes.id="+classId+
						" and feedback.facultyEvaluationSession.id="+sessionId+
						" and feedback.isExitEval=1"+
						" and feedback.cancel=0"; 
			Query query = session.createQuery(str);
			EvaluationStudentFeedback bo = (EvaluationStudentFeedback) query.uniqueResult();
			/* if the bo is not null then it will not save and returning isAdded false ,otherwise it will save*/
			if(bo!= null && !bo.toString().isEmpty()){
				isAdded = false;
			}else{
				session.save(facultyEvaluationFeedback);
				isAdded = true;
			}
			tx.commit();
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
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admin.IEvaluationStudentFeedbackTransaction#getFacultyEvaluationFeedback(com.kp.cms.bo.admin.StudentLogin)
	 */
	@Override
	public boolean checkStuIsAlreadyExist( EvaluationStudentFeedbackForm evaStudentFeedbackForm) throws Exception {
		Session session = null;
		boolean isExist = false;
		try{
			session = HibernateUtil.getSession();
			int studentId = evaStudentFeedbackForm.getStudentId();
			int classId = Integer.parseInt(evaStudentFeedbackForm.getClassId());
			int sessionId = evaStudentFeedbackForm.getSessionId();
			String str= "from EvaluationStudentFeedback feedback where feedback.isActive = 1 " +
						" and feedback.student.id="+studentId+
						" and feedback.classes.id="+classId+
						" and feedback.facultyEvaluationSession.id="+sessionId+
						" and feedback.isExitEval=1"+
						" and feedback.cancel=0";
			Query query = session.createQuery(str);
			EvaluationStudentFeedback facultyEvaluationFeedback = (EvaluationStudentFeedback) query.uniqueResult();
			if(facultyEvaluationFeedback!= null && !facultyEvaluationFeedback.toString().isEmpty()){
				isExist = true;
			}
		}catch (Exception e) {
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		return isExist;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admin.IEvaluationStudentFeedbackTransaction#getGroupDetailsList(com.kp.cms.forms.admin.EvaluationStudentFeedbackForm)
	 */
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admin.IEvaluationStudentFeedbackTransaction#getGroupDetailsList(com.kp.cms.forms.admin.EvaluationStudentFeedbackForm)
	 */
	@Override
	public List<EvaStudentFeedBackGroupTo> getGroupDetailsList( EvaluationStudentFeedbackForm evaStudentFeedbackForm)
			throws Exception {
		Session session =null;
		List<EvaStudentFeedBackGroupTo> groupToList = new ArrayList<EvaStudentFeedBackGroupTo>();
		//int totalGroups = 0;
		int totalQuestions = 0;
		try{
			session = HibernateUtil.getSession();
			String str = "from EvaStudentFeedbackGroup group where group.isActive = 1 order by group.disOrder";
			Query query = session.createQuery(str);
			List<EvaStudentFeedbackGroup> groupBoList = query.list();
			if(groupBoList!=null && !groupBoList.isEmpty()){
				Iterator<EvaStudentFeedbackGroup> iterator = groupBoList.iterator();
				while (iterator.hasNext()) {
					EvaStudentFeedbackGroup group = (EvaStudentFeedbackGroup) iterator .next();
					EvaStudentFeedBackGroupTo groupTo = new EvaStudentFeedBackGroupTo();
					List<EvaStudentFeedBackQuestionTo> questionToList = new ArrayList<EvaStudentFeedBackQuestionTo>();
					if(group.getId()!=0){
						groupTo.setId(group.getId());
					}
					if(group.getName()!=null && !group.getName().isEmpty()){
						groupTo.setName(group.getName());
					}
					
					String str1 = "from EvaStudentFeedbackQuestion question where question.isActive = 1 and question.groupId.id="+group.getId()+ " order by question.order";
					Query query2 = session.createQuery(str1);
					List<EvaStudentFeedbackQuestion> questionBoList = query2.list();
					if(questionBoList!=null && !questionBoList.isEmpty()){
						
						Iterator<EvaStudentFeedbackQuestion> iterator2 = questionBoList.iterator();
						while (iterator2.hasNext()) {
							EvaStudentFeedbackQuestion questionBo = (EvaStudentFeedbackQuestion) iterator2 .next();
							EvaStudentFeedBackQuestionTo questionTo = new EvaStudentFeedBackQuestionTo();
							
							if(questionBo.getId()!=0){
								questionTo.setId(questionBo.getId());
							}
							if(questionBo.getQuestion()!=null && !questionBo.getQuestion().isEmpty()){
								questionTo.setQuestion(questionBo.getQuestion());
							}
							if(questionBo.getGroupId()!=null && questionBo.getGroupId().getId()!=0){
								questionTo.setGroupId(String.valueOf(questionBo.getGroupId().getId()));
							}
							questionToList.add(questionTo);
							totalQuestions++;
						}
					}
					if(questionToList!=null && !questionToList.isEmpty()){
						groupTo.setQuestionList(questionToList);
						//groupTo.setTempTotalQuestions(totalQuestions);
						groupToList.add(groupTo);
						//totalGroups++;
					}
				}
			}
			evaStudentFeedbackForm.setTotalQuestions(totalQuestions);
		}catch (Exception e) {
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush(); }
		}
		return groupToList;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admin.IEvaluationStudentFeedbackTransaction#getBatchId(int, int, int)
	 */
	@Override
	public String getBatchId(int studentId, int classId, int subjectId)throws Exception {
		Session session = null;
		String batchId = "";
		try{
			session = HibernateUtil.getSession();
			String str = "select batch.id from" +
							" batch inner join batch_student on batch_student.batch_id = batch.id" +
							" inner join class_schemewise ON batch_student.class_schemewise_id = class_schemewise.id" +
							" inner join classes ON class_schemewise.class_id = classes.id" +
							" inner join subject ON batch.subject_id = subject.id" +
							" where batch.subject_id ="+subjectId+
							" and batch_student.student_id="+studentId+
							" and classes.id="+classId+
							" and batch.is_active=1 " +
							" and batch_student.is_active=1" +
							" and subject.is_theory_practical in ('T', 'P')";
			Query query = session.createSQLQuery(str);
			Integer batchId1 = (Integer) query.uniqueResult();
			batchId = String.valueOf(batchId1);
		}catch (Exception e) {
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush(); }
		}
		return batchId;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admin.IEvaluationStudentFeedbackTransaction#getAttendancePercentage(java.lang.String)
	 */
	@Override
	public String getAttendancePercentage(String attendanceQuery) throws Exception {
		Session session = null;
		String attendancePercentage = "";
		try{
			session = HibernateUtil.getSession();
			Query query = session.createSQLQuery(attendanceQuery);
			BigDecimal attendancePercentageTotal = (BigDecimal) query.uniqueResult();
			attendancePercentage = attendancePercentageTotal.toString();
		}catch (Exception e) {
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush(); }
		}
		return attendancePercentage;
	}
	@Override
	public int getprogramtypeId(EvaluationStudentFeedbackForm evaStudentFeedbackForm)
			throws Exception {
		Session session = null;
		int programtypeId=0;
		try{
			session=HibernateUtil.getSession();
			String string="select p.id from AdmAppln a join a.course.program.programType p where a.id="+evaStudentFeedbackForm.getAdmApplnId();
			Query query=session.createQuery(string);
			programtypeId=(Integer) query.uniqueResult();
		}catch (Exception e) {
			throw new ApplicationException(e);
		}finally{
			if (session != null) {
				session.flush(); }
		}
		return programtypeId;
	}
	
	@Override
	public List<ExitEvaluationQuestion> getExitEvalQuestionList() throws Exception {
		Session session = null;
		List<ExitEvaluationQuestion> exitEvalQuestion = null;
		try{
			
			session = HibernateUtil.getSession();
			String str = "from ExitEvaluationQuestion question where question.isActive = 1 order by question.order";
			Query query = session.createQuery(str);
			exitEvalQuestion = query.list();
		}catch (Exception e) {
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		return exitEvalQuestion;
	}
	
	public boolean checkExitEvalIsAlreadyExist( EvaluationStudentFeedbackForm evaStudentFeedbackForm) throws Exception {
		Session session = null;
		boolean isExist = false;
		try{
			session = HibernateUtil.getSession();
			int studentId = evaStudentFeedbackForm.getStudentId();
			int classId = Integer.parseInt(evaStudentFeedbackForm.getClassId());
			int sessionId = evaStudentFeedbackForm.getSessionId();
			String str= "from EvaluationStudentFeedback feedback where feedback.isActive = 1 " +
						" and feedback.student.id="+studentId+
						" and feedback.classes.id="+classId+
						" and feedback.facultyEvaluationSession.id="+sessionId+
						" and feedback.isExitEval=1"+
						" and feedback.cancel=0";
			Query query = session.createQuery(str);
			EvaluationStudentFeedback facultyEvaluationFeedback = (EvaluationStudentFeedback) query.uniqueResult();
			if(facultyEvaluationFeedback!= null && !facultyEvaluationFeedback.toString().isEmpty()){
				isExist = true;
			}
		}catch (Exception e) {
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		return isExist;
	}
	
	public EvaluationStudentFeedback getEvalFeedBack( EvaluationStudentFeedbackForm evaStudentFeedbackForm) throws Exception {
		Session session = null;
		EvaluationStudentFeedback facultyEvaluationFeedback=null;
		try{
			session = HibernateUtil.getSession();
			int studentId = evaStudentFeedbackForm.getStudentId();
			int classId = Integer.parseInt(evaStudentFeedbackForm.getClassId());
			int sessionId = evaStudentFeedbackForm.getSessionId();
			String str= "from EvaluationStudentFeedback feedback where feedback.isActive = 1 " +
						" and feedback.student.id="+studentId+
						" and feedback.classes.id="+classId+
						" and feedback.facultyEvaluationSession.id="+sessionId+
						" and feedback.isExitEval=0"+
						" and feedback.cancel=0";
			Query query = session.createQuery(str);
			facultyEvaluationFeedback = (EvaluationStudentFeedback) query.uniqueResult();
		}catch (Exception e) {
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		return facultyEvaluationFeedback;
	}
	
	public boolean saveExitEvaluationFeedback( EvaluationStudentFeedback facultyEvaluationFeedback) throws Exception {
		Session session = null;
		Transaction tx = null;
		boolean isAdded = false;
		try{
			int studentId = facultyEvaluationFeedback.getStudent().getId();
			int classId = facultyEvaluationFeedback.getClasses().getId();
			int sessionId = facultyEvaluationFeedback.getFacultyEvaluationSession().getId();
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			tx.begin();
			/* checking duplicate*/
			String str= "from EvaluationStudentFeedback feedback where feedback.isActive = 1 " +
						" and feedback.student.id="+studentId+
						" and feedback.classes.id="+classId+
						" and feedback.facultyEvaluationSession.id="+sessionId+
						" and feedback.isExitEval=1"+
						" and feedback.cancel=0"; 
			Query query = session.createQuery(str);
			EvaluationStudentFeedback bo = (EvaluationStudentFeedback) query.uniqueResult();
			/* if the bo is not null then it will not save and returning isAdded false ,otherwise it will save*/
			if(bo!= null && !bo.toString().isEmpty()){
				isAdded = false;
			}else{
				session.saveOrUpdate(facultyEvaluationFeedback);
				isAdded = true;
			}
			tx.commit();
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
	public List<EvaStudentFeedbackQuestion> getSubjectWiseEvalQuestionList()
			throws Exception {

		Session session = null;
		List<EvaStudentFeedbackQuestion> facultyEvalQuestion = null;
		try{
			session = HibernateUtil.getSession();
			String str = "from EvaStudentFeedbackQuestion question where question.isActive = 1 and question.groupId.id=2 order by question.order";
			Query query = session.createQuery(str);
			facultyEvalQuestion = query.list();
		}catch (Exception e) {
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		return facultyEvalQuestion;
	
	}
	
}
