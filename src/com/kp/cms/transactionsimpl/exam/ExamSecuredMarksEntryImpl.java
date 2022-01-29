package com.kp.cms.transactionsimpl.exam;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.exam.ExamMarksEntryBO;
import com.kp.cms.bo.exam.ExamMarksEntryDetailsBO;
import com.kp.cms.bo.exam.SubjectUtilBO;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.utilities.HibernateUtil;

@SuppressWarnings("unchecked")
public class ExamSecuredMarksEntryImpl extends ExamGenImpl {

	public ArrayList<SubjectUtilBO> get_subjectList() {

		Session session = null;
		ArrayList<SubjectUtilBO> list;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();

			Query query = session
					.createQuery("select sub.id, sub.name , sub.code from SubjectUtilBO sub	where sub.isActive = 1 order by sub.name");
			list = new ArrayList<SubjectUtilBO>(query.list());

			session.flush();
			// session.close();
		} catch (Exception e) {

			if (session != null) {
				session.flush();
				session.close();
			}
			list = new ArrayList<SubjectUtilBO>();
		}
		return list;
	}

	public ArrayList<SubjectUtilBO> get_subjectListbyNameCode(String sCodeName,
			int examId) {

		Session session = null;
		ArrayList<SubjectUtilBO> list;
		try {

			String courseIds = getCourseidByExamId(examId);

			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();

			String HQL = null;

			// HQL =
			// "select sub.id, sub.name , sub.code from SubjectUtilBO sub	where sub.isActive = 1";
			if (examId == 0) {
				/*
				 * HQL =
				 * " select s.subject.id, s.subject.name, s.subject.code from SubjectGroupSubjects s "
				 * + " where s.subject.isActive = 1" +
				 * " group by s.subject.id,  s.subject.name, s.subject.code " ;
				 */

				HQL = "select s.subject.id, s.subject.name, s.subject.code from SubjectGroupSubjects s  "
						+ " where s.subject.isActive = 1 "
						+ " and s.subjectGroup.isActive = 1 and s.isActive = 1 "
						+ " group by s.subject.id,  s.subject.name, s.subject.code ";
			} else {
				HQL = "select s.subject.id, s.subject.name, s.subject.code from SubjectGroupSubjects s  "
						+ " where s.subjectGroup.course.id in ("
						+ courseIds
						+ ") and s.subject.isActive = 1 "
						+ " and s.subjectGroup.isActive = 1 and s.isActive = 1 "
						+ " group by s.subject.id,  s.subject.name, s.subject.code ";

				/*
				 * HQL =
				 * " select s.subject.id, s.subject.name, s.subject.code from SubjectGroupSubjects s "
				 * + " where s.subjectGroup.course.id in ( '" + courseIds + "')"
				 * + " group by s.subject.id,  s.subject.name, s.subject.code "
				 * ;
				 */
			}
			// HQL =
			// "select sub.id, sub.name , sub.code from SubjectUtilBO sub	where sub.isActive = 1";

			if (sCodeName.equalsIgnoreCase("sCode")) {
				// HQL = HQL + " order by sub.code";
				HQL = HQL + " order by s.subject.code";

			} else {
				// HQL = HQL + " order by sub.name";
				HQL = HQL + " order by s.subject.name";
			}
			Query query = session.createQuery(HQL);

			// query.setParameter("sCodeName", sCodeName);
			list = new ArrayList<SubjectUtilBO>(query.list());

			session.flush();
			// session.close();
		} catch (Exception e) {

			if (session != null) {
				session.flush();
				session.close();
			}
			list = new ArrayList<SubjectUtilBO>();
		}
		return list;
	}

	// To get secured mark type from EXAM - SETTINGS
	public boolean getsecured_marks_type() {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		ArrayList list = null;
		String HQL = null;
		boolean regOrRoll = true;

		HQL = "select es.securedMarkEntryBy from ExamSettingsBO es";

		Query query = session.createQuery(HQL);

		list = new ArrayList(query.list());
		if (list != null && list.size() > 0) {
			return (list.get(0).toString().equalsIgnoreCase("Register No")) ? true
					: false;
		}
		return regOrRoll;
	}

	public Integer getStudentCourseId(Integer studentId) {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		Integer courseId = null;
		String HQL_QUERY = "";

		HQL_QUERY = "select s.classSchemewiseUtilBO.curriculumSchemeDurationUtilBO.curriculumSchemeUtilBO.courseId"
				+ " from StudentUtilBO s" + " where s.id = :studentId";

		Query query = session.createQuery(HQL_QUERY);
		query.setParameter("studentId", studentId);
		List list = query.list();

		if (list != null && list.size() > 0) {
			courseId = (Integer) list.get(0);

		}

		return courseId;
	}

	// To get the max marks from SUBJECT - RULE - SETTINGS
	public BigDecimal getMaxMarksForSubject(String examType, Integer subjectId,
			Integer multipleAnswerScriptId, String subjectType,
			Integer studentId, Integer examId, String isPreviousExam, String supSchemeNo) {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Integer courseId = null, schemeNo = null; 
		if (studentId != null) {
			courseId = getStudentCourseId(studentId);
			schemeNo = getSchemeNo(studentId);
		}
		boolean checkInPrevious = false; 
		Integer academicYear = null;
		if(examType.equalsIgnoreCase("Supplementary") && isPreviousExam.equalsIgnoreCase("true")){
			academicYear = getAcademicYearFromPreviousClassDetails(studentId, supSchemeNo);
			if(academicYear != null){
				checkInPrevious = true;	
			}
		}
		if (checkSubjRuleSettings(subjectId, courseId, examId, academicYear)) {
			
			BigDecimal marks = null;
			String HQL_QUERY = "";
			String academicQuery = "";
			if(academicYear!= null){
				academicQuery = " and srs.academicYear =  '" + academicYear + "'";	
			}
			else{
				academicQuery = " and srs.academicYear IN (select e.academicYear from ExamDefinitionBO e where e.id = :examId) ";		
			}
			
			Integer intType = null;
			if ((examType.equalsIgnoreCase("Regular") || examType
					.equalsIgnoreCase("Supplementary"))) {
				if(supSchemeNo!= null && !supSchemeNo.trim().isEmpty() && examType.equalsIgnoreCase("Supplementary") ){
					schemeNo = 	Integer.parseInt(supSchemeNo);
				}
				if (subjectType.equalsIgnoreCase("Theory")) {

					if (multipleAnswerScriptId == null) {
						HQL_QUERY = "select srs.theoryEseEnteredMaxMark"
								+ " from ExamSubjectRuleSettingsBO srs"
								+ " where srs.courseId = :courseId and srs.schemeNo = :schemeNo"
								+ " and srs.subjectId = :subjectId" + academicQuery +
								" and srs.isActive = 1";
					} else {
						HQL_QUERY = "select ans.value"
								+ " from ExamSubjectRuleSettingsMultipleAnsScriptBO ans"
								+ " join ans.examSubjectRuleSettingsBO s"
								+ " where s.courseId = :courseId and s.schemeNo = :schemeNo and s.subjectId = :subjectId"
								+ academicQuery
								+ " and ans.multipleAnswerScriptId = :multipleAnswerScriptId and ans.isTheoryPractical = 't'";
					}
				}

				else if (subjectType.equalsIgnoreCase("Practical")) {
					if (multipleAnswerScriptId == null) {
						/*HQL_QUERY = "select ans.value"
								+ " from ExamSubjectRuleSettingsMultipleAnsScriptBO ans"
								+ " join ans.examSubjectRuleSettingsBO s"
								+ " where s.courseId = :courseId and s.schemeNo = :schemeNo and s.subjectId = :subjectId"
								+ " and s.academicYear IN (select e.academicYear from ExamDefinitionBO e where e.id = :examId) and s.isActive = 1"
								+ " and ans.multipleAnswerScriptId = :multipleAnswerScriptId and ans.isTheoryPractical = 'p'";*/
						HQL_QUERY = "select srs.practicalEseEnteredMaxMark"
							+ " from ExamSubjectRuleSettingsBO srs"
							+ " where srs.courseId = :courseId and srs.schemeNo = :schemeNo"
							+ " and srs.subjectId = :subjectId"
							+ academicQuery + " and srs.isActive = 1";
					} else {
						HQL_QUERY = "select ans.value"
								+ " from ExamSubjectRuleSettingsMultipleAnsScriptBO ans"
								+ " join ans.examSubjectRuleSettingsBO s"
								+ " where s.courseId = :courseId and s.schemeNo = :schemeNo and s.subjectId = :subjectId"
								+ academicQuery + " and s.isActive = 1"
								+ " and ans.multipleAnswerScriptId = :multipleAnswerScriptId and ans.isTheoryPractical = 'p'";

					}

				}
			} else if (examType.equalsIgnoreCase("Internal")) {
				intType = getIntType(examId);
				if (intType != null && intType != 0) {
					if (subjectType.equalsIgnoreCase("Theory")) {

						HQL_QUERY = "select e.enteredMaxMark"
								+ " from ExamSubjectRuleSettingsSubInternalBO e"
								+ " join e.examSubjectRuleSettingsBO srs"
								+ " where srs.courseId = :courseId and srs.schemeNo = :schemeNo and srs.academicYear IN (select d.academicYear from ExamDefinitionBO d where d.id = :examId) and srs.isActive = 1"
								+ " and e.internalExamTypeId IN (:intType) and e.isTheoryPractical = 't' and srs.subjectId= :subjectId";

					} else if (subjectType.equalsIgnoreCase("Practical")) {

						HQL_QUERY = "select e.enteredMaxMark"
								+ " from ExamSubjectRuleSettingsSubInternalBO e"
								+ " join e.examSubjectRuleSettingsBO srs"
								+ " where srs.courseId = :courseId and srs.schemeNo = :schemeNo and srs.academicYear IN (select d.academicYear from ExamDefinitionBO d where d.id = :examId) and srs.isActive = 1"
								+ " and e.internalExamTypeId IN (:intType) and e.isTheoryPractical = 'p' and srs.subjectId= :subjectId";

					}
				}

			}

			Query query = session.createQuery(HQL_QUERY);
			query.setParameter("courseId", courseId);
			if(!checkInPrevious){
				query.setParameter("examId", examId);
			}
			
			if (isPreviousExam.equalsIgnoreCase("true") && !examType.equalsIgnoreCase("Supplementary")) {
				query.setParameter("schemeNo", schemeNo - 1);
			} else {
				query.setParameter("schemeNo", schemeNo);
			}

			query.setParameter("subjectId", subjectId);
			if (multipleAnswerScriptId != null && multipleAnswerScriptId != 0
					&& !examType.equalsIgnoreCase("Internal")) {
				query.setParameter("multipleAnswerScriptId",
						multipleAnswerScriptId);
			} else if (intType != null && examType.equalsIgnoreCase("Internal")) {
				query.setParameter("intType", intType);
			}
			Iterator it = query.list().iterator();
			while (it.hasNext()) {

				marks = (BigDecimal) it.next();

			}

			return marks;
		}
		return new BigDecimal(0);
	}

	private Integer getSchemeNo(Integer studentId) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		Integer schemeNo = null;
		String HQL_QUERY = "";

		HQL_QUERY = "select s.classSchemewiseUtilBO.curriculumSchemeDurationUtilBO.semesterYearNo"
				+ " from StudentUtilBO s" + " where s.id = :studentId";

		Query query = session.createQuery(HQL_QUERY);
		query.setParameter("studentId", studentId);
		List list = query.list();

		if (list != null && list.size() > 0) {
			schemeNo = (Integer) list.get(0);

		}

		return schemeNo;
	}

	private boolean checkSubjRuleSettings(Integer subjectId, Integer courseId,
			Integer examId, Integer academicYear) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
 
		List<Integer> list = null;
		String HQL_QUERY = "";
		Query query = null;
		if(academicYear!= null){
			HQL_QUERY = "select e.id" + " from ExamSubjectRuleSettingsBO e"
			+ " where e.subjectId = :subjectId "
			+ " and e.courseId = :courseId"
			+ " and e.academicYear = '"+ academicYear + "' and e.isActive = 1";	
			query = session.createQuery(HQL_QUERY);
			query.setParameter("subjectId", subjectId);
			query.setParameter("courseId", courseId);
		}
		else{
			HQL_QUERY = "select e.id" + " from ExamSubjectRuleSettingsBO e"
					+ " where e.subjectId = :subjectId "
					+ " and e.courseId = :courseId"
					+ " and e.academicYear IN (select e.academicYear from "
					+ "ExamDefinitionBO e where e.id = :examId) and e.isActive = 1";
			query = session.createQuery(HQL_QUERY);
			query.setParameter("subjectId", subjectId);
			query.setParameter("courseId", courseId);
			query.setParameter("examId", examId);
		}

		
		list = query.list();
		if (list.size() > 0 && list.get(0) != null) {
			return true;
		} else {
			return false;
		}
	}

	// DUPLICATE CHECK
	public boolean checkForDuplicateEntry(Integer examId, Integer subjectId,
			String subjectType, Integer answerScriptId,
			Integer evaluatorTypeId, String registerNo, boolean rollOrReg) {

		Session session = null;
		boolean notDuplicate = true;
		int id = 0, flag = 0;
		String HQL1 = "";
		String HQL_QUERY = "";
		String rollNo = registerNo;
		String HQL2 = "";
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		session = sessionFactory.openSession();
		if (evaluatorTypeId == null || evaluatorTypeId == 0) {
			HQL1 = " and em.evaluatorTypeId is null";
		} else {
			HQL1 = " and em.evaluatorTypeId = :evaluatorTypeId";
		}
		if (answerScriptId == null || answerScriptId == 0) {
			HQL2 = " and em.answerScriptTypeId is null";
		} else {
			HQL2 = " and em.answerScriptTypeId = :answerScriptId";
		}
		if (rollOrReg) {
			/*HQL_QUERY = "select em.id from ExamMarksEntryBO em left join"
					+ " em.studentUtilBO su where su.registerNo=:registerNo "
					+ " and em.examId=:examId and em.isSecured = 1"

					+ HQL1;*/
			
			HQL_QUERY = "select em.id from ExamMarksEntryBO em"
				+	" inner join em.examMarksEntryDetailsBOset det "
				+ " left join em.studentUtilBO su where su.registerNo=:registerNo "
				+ " and em.examId=:examId and em.isSecured = 1 and det.subjectId = " + subjectId 
				+ HQL1 + HQL2;
		} else {
			/*HQL_QUERY = "select em.id from ExamMarksEntryBO em left join"
					+ " em.studentUtilBO su where su.rollNo=:rollNo "
					+ " and em.examId=:examId and em.isSecured = 1"

					+ HQL1;*/
			
			HQL_QUERY = "select em.id from ExamMarksEntryBO em "
				+	" inner join em.examMarksEntryDetailsBOset det "
				+	" left join em.studentUtilBO su where su.rollNo=:rollNo "
				+ " and em.examId=:examId and em.isSecured = 1 and det.subjectId = " + subjectId 
				+ HQL1 + HQL2;
		}
		Query query = session.createQuery(HQL_QUERY);

		query.setParameter("examId", examId);
		if (evaluatorTypeId != null && evaluatorTypeId != 0) {
			query.setParameter("evaluatorTypeId", evaluatorTypeId);
		}
		if (!rollOrReg) {
			query.setParameter("rollNo", rollNo);
		} else {
			query.setParameter("registerNo", registerNo);
		}
		if (answerScriptId != null && answerScriptId != 0) {
			query.setParameter("answerScriptId", answerScriptId);
		}
		
		
		Iterator it = query.list().iterator();
		while (it.hasNext()) {

			id = Integer.parseInt(it.next().toString());

		}

		Criteria crit = session.createCriteria(ExamMarksEntryDetailsBO.class);
		crit.add(Restrictions.eq("marksEntryId", id));
		crit.add(Restrictions.eq("subjectId", subjectId));

		Iterator<ExamMarksEntryDetailsBO> itr = crit.list().iterator();
		while (itr.hasNext()) {
			flag = 1;
			ExamMarksEntryDetailsBO eBO = (ExamMarksEntryDetailsBO) itr.next();
			if (subjectType.equalsIgnoreCase("Theory")) {
				if (eBO.getTheoryMarks() == null) {
					notDuplicate = false;
				}
			} else {
				if (eBO.getPracticalMarks() == null) {
					notDuplicate = false;
				}
			}

		}
		if (flag == 0)
			notDuplicate = false;
		session.flush();
		// session.close();
		return notDuplicate;
	}

	// DUPLICATE CHECK
	public boolean checkForAbscentEntry(Integer examId, Integer subjectId,
			String subjectType, Integer answerScriptId,
			Integer evaluatorTypeId, String registerNo, String abscentCode,
			String notProcessCode, boolean rollOrReg) {
		Session session = null;
		boolean notAbscent = false;
		int id = 0;
		String HQL1 = "";
		String HQL_QUERY = "";
		String rollNo = registerNo;
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		session = sessionFactory.openSession();
		if (evaluatorTypeId == null || evaluatorTypeId == 0) {
			HQL1 = " and em.evaluatorTypeId is null";
		} else {
			HQL1 = " and em.evaluatorTypeId = :evaluatorTypeId";
		}
		if (rollOrReg) {
			/*
			 * HQL_QUERY = "select em.id from ExamMarksEntryBO em left join" +
			 * " em.studentUtilBO su where su.registerNo=:registerNo " +
			 * " and em.examId=:examId and em.isSecured=1"
			 */
			HQL_QUERY = "select em.id from ExamMarksEntryBO em left join"
					+ " em.studentUtilBO su "
					+ " inner join em.examMarksEntryDetailsBOset det "
					+ " where su.registerNo=:registerNo "
					+ " and em.examId=:examId and em.isSecured=1 and det.subjectId ="
					+ subjectId + HQL1;
		} else {

			/*
			 * HQL_QUERY = "select em.id from ExamMarksEntryBO em left join" +
			 * " em.studentUtilBO su where su.rollNo=:rollNo " +
			 * " and em.examId=:examId and em.isSecured=1"
			 * 
			 * + HQL1;
			 */

			HQL_QUERY = "select em.id from ExamMarksEntryBO em left join"
					+ " em.studentUtilBO su "
					+ " inner join em.examMarksEntryDetailsBOset det "
					+ " where su.rollNo=:rollNo "
					+ " and em.examId=:examId and em.isSecured=1"
					+ " and det.subjectId =" + subjectId

					+ HQL1;
		}
		Query query = session.createQuery(HQL_QUERY);

		query.setParameter("examId", examId);
		if (evaluatorTypeId != null && evaluatorTypeId != 0) {
			query.setParameter("evaluatorTypeId", evaluatorTypeId);
		}
		if (!rollOrReg) {
			query.setParameter("rollNo", rollNo);
		} else {
			query.setParameter("registerNo", registerNo);
		}
		Iterator it = query.list().iterator();
		while (it.hasNext()) {

			id = Integer.parseInt(it.next().toString());

		}

		Criteria crit = session.createCriteria(ExamMarksEntryDetailsBO.class);
		crit.add(Restrictions.eq("marksEntryId", id));
		crit.add(Restrictions.eq("subjectId", subjectId));

		Iterator<ExamMarksEntryDetailsBO> itr = crit.list().iterator();

		while (itr.hasNext()) {
			ExamMarksEntryDetailsBO eBO = (ExamMarksEntryDetailsBO) itr.next();
			if (subjectType.equalsIgnoreCase("Theory")) {
				if (eBO.getTheoryMarks() != null
						&& (eBO.getTheoryMarks().equalsIgnoreCase(abscentCode) || eBO
								.getTheoryMarks().equalsIgnoreCase(
										notProcessCode))) {
					notAbscent = true;
				}
			} else {
				if (eBO.getPracticalMarks() != null
						&& (eBO.getPracticalMarks().equals(abscentCode) || eBO
								.getPracticalMarks().equals(notProcessCode))) {
					notAbscent = true;
				}
			}

		}
		session.flush();
		// session.close();

		return notAbscent;
	}

	// To validate students' subjects
	// To validate students' subjects
	public boolean get_subjectsList(int subjectId, String rollRegNo,
			boolean rollOrReg, String isPreviousExam, String subjectType, String examType, int examId, String schemeNo) throws Exception {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		List list = null;
		List<Integer> stList = getPreviousSchemeNo(rollOrReg, rollRegNo);
		Integer previousSem = 0;
		Integer studentID = 0;
		if (stList != null) {
			studentID = stList.get(0);
			previousSem = stList.get(1);
		}
		if(schemeNo == null || schemeNo.trim().isEmpty()){
			schemeNo = previousSem.toString();
		}

		String HQL_QUERY = "";
		if (isPreviousExam.equalsIgnoreCase("true")) {
			if(examType.equalsIgnoreCase("Supplementary")){
				HQL_QUERY = " SELECT st.id"
					+ " FROM  EXAM_student_previous_class_details st"
					+ " LEFT JOIN"
					+ " EXAM_student_sub_grp_history asg"
					+ " ON asg.student_id = st.student_id"
					+ " LEFT JOIN subject_group_subjects sgs"
					+ " ON asg.subject_group_id = sgs.subject_group_id and sgs.is_active = 1"
					+ "	LEFT JOIN subject s"
					+ " ON sgs.subject_id = s.id " 
					+ " LEFT JOIN EXAM_supplementary_improvement_application imp on " 
					+ " imp.student_id = st.student_id "
					+ " and imp.subject_id = s.id"
					+ " WHERE sgs.subject_id = :subjectId and asg.scheme_no =  "
					+ schemeNo + " and st.scheme_no = " + schemeNo
					+ " and st.student_id = " + studentID  
					+ " and imp.exam_id = " + examId
					+ " and imp.scheme_no =  " + schemeNo;
				
				if(subjectType.equalsIgnoreCase("Theory")){
					HQL_QUERY =  HQL_QUERY + " and  imp.is_appeared_theory = 1";
				}
				else if(subjectType.equalsIgnoreCase("Practical")){
					HQL_QUERY =  HQL_QUERY + " and  imp.is_appeared_practical = 1";
				}
			}else{
				HQL_QUERY = " SELECT st.id"
						+ " FROM  EXAM_student_previous_class_details st"
						+ " LEFT JOIN"
						+ " EXAM_student_sub_grp_history asg"
						+ " ON asg.student_id = st.student_id"
						+ " LEFT JOIN subject_group_subjects sgs"
						+ " ON asg.subject_group_id = sgs.subject_group_id and sgs.is_active = 1"
						+ "	LEFT JOIN subject s"
						+ " ON sgs.subject_id = s.id"
						+ " WHERE sgs.subject_id = :subjectId and asg.scheme_no =  "
						+ previousSem + " and st.scheme_no = " + previousSem
						+ " and st.student_id = " + studentID;
			}
		} else {
			if(examType.equalsIgnoreCase("Supplementary")){
				HQL_QUERY = " SELECT st.id"
					+ " FROM  student st"
					+ " LEFT JOIN"
					+ " applicant_subject_group asg"
					+ " ON asg.adm_appln_id = st.adm_appln_id"
					+ " LEFT JOIN subject_group_subjects sgs"
					+ " ON asg.subject_group_id = sgs.subject_group_id and sgs.is_active = 1"
					+ "	LEFT JOIN subject s" + " ON sgs.subject_id = s.id"
					+ " LEFT JOIN class_schemewise"
					+ " ON st.class_schemewise_id = class_schemewise.id " 
					+ " LEFT JOIN EXAM_supplementary_improvement_application imp " 
					+ " on imp.student_id = st.id and imp.subject_id = s.id"
					+ " WHERE sgs.subject_id = :subjectId " 
					+ " and imp.exam_id = " + examId
					+ " and imp.scheme_no = " + (previousSem + 1);
				
				if(subjectType.equalsIgnoreCase("Theory")){
					HQL_QUERY =  HQL_QUERY + " and  imp.is_appeared_theory = 1";
				}
				else if(subjectType.equalsIgnoreCase("Practical")){
					HQL_QUERY =  HQL_QUERY + " and  imp.is_appeared_practical = 1";
				}
			}
			else{
				HQL_QUERY = " SELECT st.id"
						+ " FROM  student st"
						+ " LEFT JOIN"
						+ " applicant_subject_group asg"
						+ " ON asg.adm_appln_id = st.adm_appln_id"
						+ " LEFT JOIN subject_group_subjects sgs"
						+ " ON asg.subject_group_id = sgs.subject_group_id and sgs.is_active = 1"
						+ "	LEFT JOIN subject s" + " ON sgs.subject_id = s.id"
						+ " LEFT JOIN class_schemewise"
						+ " ON st.class_schemewise_id = class_schemewise.id"
						+ " WHERE sgs.subject_id = :subjectId ";
			}
			if (rollOrReg) {
				HQL_QUERY = HQL_QUERY + " and st.register_no = :rollRegNo";

			} else {

				HQL_QUERY = HQL_QUERY + " and st.roll_no = :rollRegNo";
			}
		}

		Query query = session.createSQLQuery(HQL_QUERY);

		query.setParameter("subjectId", subjectId);
		if (!isPreviousExam.equalsIgnoreCase("true")) {
			query.setParameter("rollRegNo", rollRegNo);
		}

		list = query.list();
		if (list.size() > 0) {
			return true;
		} else {
			return false;
		}

	}

	// To get studentId by roll/register no depending on exam - settings
	public Integer select_Student_id_rollReg(String rollRegNo, boolean rollOrReg) {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		Integer id = null;
		String HQL_QUERY = "select s.id from StudentUtilBO s";

		if (rollOrReg) {
			HQL_QUERY = HQL_QUERY + " where s.registerNo = :rollRegNo";

		} else {

			HQL_QUERY = HQL_QUERY + " where s.rollNo = :rollRegNo";
		}

		Query query = session.createQuery(HQL_QUERY);

		query.setParameter("rollRegNo", rollRegNo);
		if (query.list().size() > 0) {
			id = (Integer) query.list().get(0);

		} else {
			id = Integer.valueOf(0);
		}
		return id;
	}

	// Get percentage difference
	public ArrayList<Object[]> getPerecentage_Difference(Integer intExamId,
			Integer intSubjectId, String subjectType, Integer studentId,
			String marksEntered, Integer intAnswerScriptId,
			Integer intEvaluatorId) {

		String HQL1 = "";
		String HQL2 = "";
		if (intEvaluatorId == null || intEvaluatorId == 0) {
			HQL1 = " and eme.evaluator_type_id is null";
		} else {
			HQL1 = " and eme.evaluator_type_id != :intEvaluatorId";
		}
		if (intAnswerScriptId == null || intAnswerScriptId == 0) {
			HQL2 = " and eme.answer_script_type is null";
		} else {
			HQL2 = " and eme.answer_script_type = :intAnswerScriptId";
		}

		ArrayList<Object[]> list;
		String isTheoryPractical = "";

		if (subjectType.equalsIgnoreCase("Theory")) {
			isTheoryPractical = "T";
		} else {
			isTheoryPractical = "P";
		}
		String HQL_QUERY = "";

		if (subjectType.equalsIgnoreCase("Theory")) {

			HQL_QUERY = " SELECT eme.id AS maxy,"
					+ " IF(:marksEntered > emed.previous_evaluator_theory_marks,  (((:marksEntered - emed.previous_evaluator_theory_marks) / emed.previous_evaluator_theory_marks) * 100),  (((emed.previous_evaluator_theory_marks - :marksEntered ) / :marksEntered ) * 100))"
					+ " AS theoryprnctg,"
					+ " emed.previous_evaluator_theory_marks"
					+ " FROM EXAM_marks_entry eme"
					+ " JOIN EXAM_marks_entry_details emed"
					+ " ON emed.marks_entry_id = eme.id" + " JOIN subject"
					+ " ON emed.subject_id = subject.id"
					//+ " WHERE subject.is_theory_practical = :isTheoryPractical"
					+ " WHERE emed.theory_marks IS NOT NULL "
					+ " AND eme.exam_id = :intExamId"
					+ " AND eme.student_id = :studentId"

					+ HQL1 + HQL2

					+ " AND emed.subject_id = :intSubjectId"
					+ " GROUP BY emed.previous_evaluator_theory_marks"
					+ " ORDER BY maxy DESC" + "  LIMIT 1";
		} else {

			HQL_QUERY = " SELECT max(eme.sequence_evaluator) AS maxy,"
					+ " IF(:marksEntered > emed.previous_evaluator_practical_marks,  (((:marksEntered - emed.previous_evaluator_practical_marks) / emed.previous_evaluator_practical_marks) * 100),  (((emed.previous_evaluator_practical_marks - :marksEntered ) / :marksEntered ) * 100))"
					+ " AS practicalprnctg,"
					+ " emed.previous_evaluator_practical_marks"
					+ " FROM EXAM_marks_entry eme"
					+ " JOIN EXAM_marks_entry_details emed"
					+ " ON emed.marks_entry_id = eme.id" + " JOIN subject"
					+ " ON emed.subject_id = subject.id"
					//+ " WHERE subject.is_theory_practical = :isTheoryPractical"
					+ " WHERE emed.practical_marks IS NOT NULL "
					+ " AND eme.exam_id = :intExamId"
					+ " AND eme.student_id = :studentId" + HQL1 + HQL2
					+ " AND emed.subject_id = :intSubjectId"
					+ " GROUP BY emed.previous_evaluator_practical_marks"
					+ " ORDER BY maxy DESC" + "  LIMIT 1";
		}

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		Query query = session.createSQLQuery(HQL_QUERY);
		query.setParameter("intExamId", intExamId);
		query.setParameter("marksEntered", marksEntered);
		query.setParameter("intSubjectId", intSubjectId);
		query.setParameter("studentId", studentId);
		/*if (subjectType.equalsIgnoreCase("Theory")) {
			query.setParameter("isTheoryPractical", isTheoryPractical);
		}*/
		if (intEvaluatorId != null && intEvaluatorId != 0) {
			query.setParameter("intEvaluatorId", intEvaluatorId);
		}
		if (intAnswerScriptId != null && intAnswerScriptId != 0) {
			query.setParameter("intAnswerScriptId", intAnswerScriptId);
		}
		list = new ArrayList<Object[]>(query.list());
		if (list.size() == 0) {
			list = new ArrayList<Object[]>();

		}

		return list;
	}

	// To FETCH Evaluator List
	public ArrayList<Object> getEvaluatorType(int subjectId, int subjectTypeId,
			Integer examId) {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		String isTheoryPractical = "";

		if (subjectTypeId == 1) {
			isTheoryPractical = "T";
		} else if (subjectTypeId == 0) {
			isTheoryPractical = "P";
		}
		Integer examTypeId=(Integer)session.createQuery("select ed.examTypeID from ExamDefinitionBO ed where ed.id ="+examId).uniqueResult();
		String HQL_QUERY ="";
		if(examTypeId!=null && (examTypeId==6 || examTypeId==3)){
			HQL_QUERY = "select distinct em.evaluatorId from"
				+ " ExamSubjecRuleSettingsMultipleEvaluatorBO em where"
				+ " em.examSubjectRuleSettingsBO.subjectId = :subjectId"
				+ " and em.isTheoryPractical = :isTheoryPractical"
				+ " and em.examSubjectRuleSettingsBO.academicYear >= (select ed.examForJoiningBatch from ExamDefinitionBO ed where ed.id = :examId)"
				+ " and em.examSubjectRuleSettingsBO.isActive = 1";
		}else{
		 HQL_QUERY = "select distinct em.evaluatorId from"
				+ " ExamSubjecRuleSettingsMultipleEvaluatorBO em where"
				+ " em.examSubjectRuleSettingsBO.subjectId = :subjectId"
				+ " and em.isTheoryPractical = :isTheoryPractical"
				+ " and em.examSubjectRuleSettingsBO.academicYear = (select ed.academicYear from ExamDefinitionBO ed where ed.id = :examId)"
				+ " and em.examSubjectRuleSettingsBO.isActive = 1";
		}
		if (subjectTypeId == 1) {

			HQL_QUERY = HQL_QUERY
					+ " and  em.examSubjectRuleSettingsBO.theoryEseIsMultipleEvaluator = 1 ";
		} else if (subjectTypeId == 0) {

			HQL_QUERY = HQL_QUERY
					+ "  and em.examSubjectRuleSettingsBO.practicalEseIsMultipleEvaluator = 1";
		}


		Query query = session.createQuery(HQL_QUERY);
		query.setParameter("subjectId", subjectId);
		query.setParameter("isTheoryPractical", isTheoryPractical);
		query.setParameter("examId", examId);

		if (query.list() != null) {

			return new ArrayList(query.list());

		} else {

			return new ArrayList();

		}
	}

	public String getCodeForAbsentEntry() {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		String name = null;

		String HQL_QUERY = "select es.absentCodeMarkEntry"
				+ " from ExamSettingsBO es";

		Query query = session.createQuery(HQL_QUERY);

		List list = query.list();

		if (list != null && list.size() > 0) {

			name = (String) list.get(0);
		}
		return name;
	}

	public String getCodeForNotprocessEntry() {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		String name = null;

		String HQL_QUERY = "select es.notProcedCodeMarkEntry"
				+ " from ExamSettingsBO es where es.isActive=1";

		Query query = session.createQuery(HQL_QUERY);

		List list = query.list();

		if (list != null && list.size() > 0) {

			name = (String) list.get(0);
		}
		return name;
	}

	// To get max allowed percentage from - Exam Settings
	public BigDecimal get_max_allowed_percentage() {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		BigDecimal marks = null;

		String HQL_QUERY = "select e.maxAllwdDiffPrcntgMultiEvaluator"
				+ " from ExamSettingsBO e where e.isActive=1";

		Query query = session.createQuery(HQL_QUERY);

		List list = query.list();

		if (list != null && list.size() > 0) {

			marks = (BigDecimal) list.get(0);
		}
		return marks;
	}

	public Integer insert_returnId(ExamMarksEntryBO masterBO) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		int id = 0;
		try {

			session.save(masterBO);
			id = masterBO.getId();
			tx.commit();
			session.flush();
			session.close();
			return id;
		} catch (Exception e) {

			if (tx != null) {
				tx.rollback();
			}

		}
		return id;
	}

	// TO UPDATE
	public void update_details(Integer detailId, String marks,
			boolean theory_Practical, int retest) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();

		String HQL_QUERY = "";

		if (theory_Practical) {

			HQL_QUERY = "update ExamMarksEntryDetailsBO e set e.theoryMarks = :marks, "
					+ "e.previousEvaluatorTheoryMarks = :marks, e.isRetest = :retest"
					+ " where e.id = :detailId";

		} else {

			HQL_QUERY = "update ExamMarksEntryDetailsBO e set e.practicalMarks = :marks, e.previousEvaluatorPracticalMarks = :marks, e.isRetest = :retest"
					+ " where e.id = :detailId";

		}
		Query query = session.createQuery(HQL_QUERY);
		query.setParameter("detailId", detailId);
		query.setParameter("marks", marks);
		query.setParameter("retest", retest);
		query.executeUpdate();
		tx.commit();
		session.close();
	}

	public ArrayList<Object[]> get_secured_mark_details(int examId,
			int subjectId, Integer evaluatorId, Integer ansScriptId,
			Integer studentId, boolean isRetest) {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		String SQL3 = null;
		String SQL4 = null;
		if (evaluatorId == null) {
			SQL3 = " and eme.evaluator_type_id is null ";
		} else {
			SQL3 = " and eme.evaluator_type_id = :evaluatorId";
		}

		if (ansScriptId == null) {
			SQL4 = " and eme.answer_script_type is null ";
		} else {
			SQL4 = " and eme.answer_script_type = :ansScriptId";
		}

		/*String SQL_QUERY = " SELECT student.register_no,"
				+ "        student.roll_no,"
				+ "        emed.theory_marks,"
				+ "        emed.practical_marks,"
				+ "        emed.is_mistake,"
				+ "        emed.is_retest,"
				+ "        personal_data.first_name,"
				+ "        eme.id AS emeid,"
				+ "        emed.id as emedid"
				+ "   FROM EXAM_marks_entry eme"
				+ "       LEFT JOIN EXAM_marks_entry_details emed"
				//+ "           ON emed.marks_entry_id = eme.id AND emed.subject_id = :subjectId"
				+ "           ON emed.marks_entry_id = eme.id "
				+ "        JOIN student"
				+ "           ON eme.student_id = student.id"
				+ "        JOIN adm_appln"
				+ "           ON student.adm_appln_id = adm_appln.id"
				+ "        JOIN personal_data"
				+ "           ON adm_appln.personal_data_id = personal_data.id"
				+ "  WHERE eme.student_id = :studentId AND eme.is_secured = 1 AND eme.exam_id = :examId " //AND emed.subject_id = :subjectId
				+ SQL3 + SQL4;
		*/
		
		String SQL_QUERY = "";
		if(isRetest){
			SQL_QUERY = " SELECT student.register_no,"
				+ "        student.roll_no,"
				+ "        emed.theory_marks,"
				+ "        emed.practical_marks,"
				+ "        emed.is_mistake,"
				+ "        emed.is_retest,"
				+ "        personal_data.first_name,"
				+ "        eme.id AS emeid,"
				+ "        emed.id as emedid"
				+ "   FROM EXAM_marks_entry eme"
				+ "       LEFT JOIN EXAM_marks_entry_details emed"
				//+ "           ON emed.marks_entry_id = eme.id AND emed.subject_id = :subjectId"
				+ "           ON emed.marks_entry_id = eme.id "
				+ "        JOIN student"
				+ "           ON eme.student_id = student.id"
				+ "        JOIN adm_appln"
				+ "           ON student.adm_appln_id = adm_appln.id"
				+ "        JOIN personal_data"
				+ "           ON adm_appln.personal_data_id = personal_data.id"
				+ "  WHERE eme.student_id = :studentId AND eme.is_secured = 1 AND eme.exam_id = :examId " //AND emed.subject_id = :subjectId
				+ SQL3 + SQL4;
			
			SQL_QUERY = SQL_QUERY + " AND emed.subject_id = :subjectId";
		}
		else{
			SQL_QUERY = " SELECT student.register_no,"
				+ " student.roll_no,"
				+ "        0 as theory_marks,"
				+ "        0 as practical_marks,"
				+ "        0 as is_mistake,"
				+ "        0 as is_retest,"
				+ "        personal_data.first_name,"
				+ "        eme.id AS emeid,"
				+ "        0 as emedid"
				+ "   FROM EXAM_marks_entry eme"
				+ "        JOIN student"
				+ "           ON eme.student_id = student.id"
				+ "        JOIN adm_appln"
				+ "           ON student.adm_appln_id = adm_appln.id"
				+ "        JOIN personal_data"
				+ "           ON adm_appln.personal_data_id = personal_data.id"
				+ "  WHERE eme.student_id = :studentId AND eme.is_secured = 1 AND eme.exam_id = :examId " 
				+ SQL3 + SQL4;
		}
			
		Query query = session.createSQLQuery(SQL_QUERY);

		query.setParameter("examId", examId);
		if(isRetest){
			query.setParameter("subjectId", subjectId);
		}
		if (evaluatorId != null) {
			query.setParameter("evaluatorId", evaluatorId);
		}

		if (ansScriptId != null) {
			query.setParameter("ansScriptId", ansScriptId);
		}

		query.setParameter("studentId", studentId);

		ArrayList<Object[]> list = (ArrayList<Object[]>) query.list();

		if (list != null && list.size() > 0) {
			return (ArrayList<Object[]>) list;
		} else {
			return new ArrayList<Object[]>();
		}
	}

	// ON - VIEW - To Fetch Data
	public ArrayList<Object[]> get_view_details(int examId, int subjectId,
			Integer evaluatorId, Integer ansScriptId, Integer studentId) {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		ArrayList list = null;
		String HQL1 = "";
		String HQL2 = "";

		if (evaluatorId == null) {

			HQL1 = " and emd.examMarksEntryBO.evaluatorTypeId is null";

		} else {

			HQL1 = " and emd.examMarksEntryBO.evaluatorTypeId = :evaluatorId";

		}

		if (ansScriptId == null) {

			HQL2 = " and emd.examMarksEntryBO.answerScriptTypeId is null";

		} else {

			HQL2 = " and emd.examMarksEntryBO.answerScriptTypeId = :ansScriptId";

		}

		String HQL_QUERY = "select emd.theoryMarks, emd.practicalMarks, emd.previousEvaluatorTheoryMarks, emd.previousEvaluatorPracticalMarks,"
				+ " emd.isMistake, emd.isRetest,emd.examMarksEntryBO.id,emd.id"
				+ " from ExamMarksEntryDetailsBO emd"
				+ " where emd.examMarksEntryBO.examId = :examId and emd.subjectId = :subjectId and emd.examMarksEntryBO.studentId = :studentId"
				+ HQL1 + HQL2;

		Query query = session.createQuery(HQL_QUERY);
		query.setParameter("examId", examId);
		query.setParameter("subjectId", subjectId);
		query.setParameter("studentId", studentId);
		if (evaluatorId != null) {
			query.setParameter("evaluatorId", evaluatorId);
		}

		if (ansScriptId != null) {
			query.setParameter("ansScriptId", ansScriptId);
		}

		list = (ArrayList) query.list();

		if (list != null && list.size() > 0) {
			return (ArrayList<Object[]>) list;
		} else {
			return new ArrayList<Object[]>();
		}
	}

	// TO UPDATE - MISTAKE
	public int update_mistake(Integer detailId, int isMistake) {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();

		String HQL_QUERY = "";

		HQL_QUERY = "update ExamMarksEntryDetailsBO e set e.isMistake= :isMistake"
				+ " where e.id = :detailId";

		Query query = session.createQuery(HQL_QUERY);
		query.setParameter("detailId", detailId);
		query.setParameter("isMistake", isMistake);
		int flag = query.executeUpdate();
		tx.commit();
		session.close();
		return flag;
	}

	public ArrayList<Object> get_answerScript_type(int subjectId,
			int subjectTypeId, Integer examId) {
		
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		String isTheoryPractical = "";

		if (subjectTypeId == 1) {
			isTheoryPractical = "T";
		} else if (subjectTypeId == 0) {
			isTheoryPractical = "P";
		}
		Integer examTypeId=(Integer)session.createQuery("select ed.examTypeID from ExamDefinitionBO ed where ed.id ="+examId).uniqueResult();
		String HQL_QUERY ="";
		if(examTypeId!=null && (examTypeId==6)){
			 HQL_QUERY ="select distinct a.multipleAnswerScriptId"
					+ " from ExamSubjectRuleSettingsMultipleAnsScriptBO a"
					+ " where a.examSubjectRuleSettingsBO.subjectId = :subjectId"
					+ " and a.isTheoryPractical = :isTheoryPractical "
					+ " and a.examSubjectRuleSettingsBO.academicYear >= (select ed.examForJoiningBatch from ExamDefinitionBO ed where ed.id = :examId)"
					+ " and a.examSubjectRuleSettingsBO.isActive = 1";
		}else{
		 HQL_QUERY = "select distinct a.multipleAnswerScriptId"
				+ " from ExamSubjectRuleSettingsMultipleAnsScriptBO a"
				+ " where a.examSubjectRuleSettingsBO.subjectId = :subjectId"
				+ " and a.isTheoryPractical = :isTheoryPractical "
				+ " and a.examSubjectRuleSettingsBO.academicYear = (select ed.academicYear from ExamDefinitionBO ed where ed.id = :examId)"
				+ " and a.examSubjectRuleSettingsBO.isActive = 1";
		}
		if (subjectTypeId == 1) {

			HQL_QUERY = HQL_QUERY
					+ " and a.examSubjectRuleSettingsBO.theoryEseIsMultipleAnswerScript = 1 ";
		} else {

			HQL_QUERY = HQL_QUERY
					+ "  and a.examSubjectRuleSettingsBO.practicalEseIsMultipleAnswerScript = 1";
		}

		

		Query query = session.createQuery(HQL_QUERY);
		query.setParameter("subjectId", subjectId);
		query.setParameter("isTheoryPractical", isTheoryPractical);

		query.setParameter("examId", examId);
		if (query.list() != null) {

			return new ArrayList(query.list());

		} else {

			return new ArrayList();

		}
	}

	private Integer getIntType(Integer examId) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		Integer intTypeid = null;
		String HQL_QUERY = "";

		HQL_QUERY = "SELECT ed.internal_exam_type_id FROM EXAM_definition ed WHERE  ed.id = :examId";

		Query query = session.createSQLQuery(HQL_QUERY);
		query.setParameter("examId", examId);
		List list = query.list();

		if (list != null && list.size() > 0) {
			intTypeid = (Integer) list.get(0);

		}

		return intTypeid;
	}

	/**
	 * 
	 * @param rollOrReg
	 * @return
	 * @throws Exception
	 */
	private List<Integer> getPreviousSchemeNo(boolean rollOrReg,
			String rollRegNo) throws Exception {
		Session session = null;
		Integer previousSem = null;
		List<Integer> stList = new ArrayList<Integer>();
		try {
			session = HibernateUtil.getSession();
			String HQL_QUERY = "FROM  Student st where st.isActive = 1";
			if (rollOrReg) {
				HQL_QUERY = HQL_QUERY + " and st.registerNo = '" + rollRegNo
						+ "'";

			} else {
				HQL_QUERY = HQL_QUERY + " and st.rollNo = '" + rollRegNo + "'";
			}
			Query query = session.createQuery(HQL_QUERY);
			List list = query.list();
			if (list != null) {
				Iterator<Student> stItr = list.iterator();
				while (stItr.hasNext()) {
					Student student = (Student) stItr.next();
					if (student.getClassSchemewise() != null
							&& student.getClassSchemewise()
									.getCurriculumSchemeDuration() != null) {
						previousSem = student.getClassSchemewise()
								.getCurriculumSchemeDuration()
								.getSemesterYearNo() - 1;
						stList.add(student.getId());
						stList.add(previousSem);
					}
				}
			}
			return stList;
		} catch (Exception e) {
			throw new BusinessException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
	}

	/**
	 * 
	 * @param examId
	 * @return
	 */
	public String getCourseidByExamId(int examId) {
		Session session = null;
		ArrayList<Integer> list;
		StringBuffer courseIds = new StringBuffer();
		String courses = "";
		try {
			session = HibernateUtil.getSession();
			String HQL = "select distinct courseId from ExamExamCourseSchemeDetailsBO e where e.examId = "
					+ examId;

			Query query = session.createQuery(HQL);
			list = new ArrayList<Integer>(query.list());

			Iterator<Integer> itr = list.iterator();
			while (itr.hasNext()) {
				Integer id = (Integer) itr.next();
				courseIds.append(id.toString() + ",");
			}
			courses = courseIds.toString();
			if (courses.endsWith(",") == true) {
				courses = StringUtils.chop(courses);
			}
			session.flush();
			session.close();

		} catch (Exception e) {

			if (session != null) {
				session.flush();
				session.close();
			}
			list = new ArrayList<Integer>();
		}
		return courses;
	}
	
	/**
	 * 
	 * @param examId
	 * @param subjectId
	 * @param subjectTypeId
	 * @return
	 */
	public Integer getRegisterNoCount(int examId, int subjectId, int subjectTypeId, int evaluatorId, int scriptId, boolean isTheory) {
		Session session = null;
		ArrayList<Integer> list;
		try {
			session = HibernateUtil.getSession();
			StringBuffer HQL = new StringBuffer("select distinct det.marksEntryId from ExamMarksEntryBO e " +  
						 " inner join e.examMarksEntryDetailsBOset det " +
						 " where e.isSecured = 1 and e.examId = " + examId + 
						 " and det.subjectId = " + subjectId );
			
			if(isTheory){
				HQL.append(" and det.theoryMarks is not null");
			}
			else{
				HQL.append(" and det.practicalMarks is not null ");
			}
			if(evaluatorId > 0){
				HQL.append(" and e.evaluatorTypeId = " + evaluatorId);
			}
			else{
				HQL.append( " and e.evaluatorTypeId is null");
			}
			
			if(scriptId > 0){
				HQL.append( " and e.answerScriptTypeId = " + scriptId);
			}
			else{
				HQL.append( " and e.answerScriptTypeId is null ");
			}

			Query query = session.createQuery(HQL.toString());
			list = new ArrayList<Integer>(query.list());
			session.flush();
			session.close();

		} catch (Exception e) {

			if (session != null) {
				session.flush();
				session.close();
			}
			list = new ArrayList<Integer>();
		}
		if(list!= null && list.size() > 0){
			return list.size();
		}
		else{
			return 0;
		}
	}
	public Integer getAcademicYearFromPreviousClassDetails(Integer studentId, String schemeNo) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		String HQL_QUERY = "";

		HQL_QUERY = "select e.academicYear "
				+ " from ExamStudentPreviousClassDetailsBO e" + " where e.studentId = :studentId and schemeNo = :schemeNo";

		Query query = session.createQuery(HQL_QUERY);
		query.setParameter("studentId", studentId);
		query.setString("schemeNo", schemeNo);
		List list = query.list();

		if (list != null && list.size() > 0) {
			return (Integer) list.get(0);

		}
		return null;
	}

}
