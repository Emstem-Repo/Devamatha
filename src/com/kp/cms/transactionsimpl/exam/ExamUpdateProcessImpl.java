package com.kp.cms.transactionsimpl.exam;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.Classes;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.Subject;
import com.kp.cms.bo.exam.ApplicantSubjectGroupUtilBO;
import com.kp.cms.bo.exam.ExamMarksEntryDetailUtilBo;
import com.kp.cms.bo.exam.ExamStudentAssignmenteMarkDetailsBO;
import com.kp.cms.bo.exam.ExamStudentAttendanceMarkDetailsBO;
import com.kp.cms.bo.exam.ExamStudentFinalMarkDetailsBO;
import com.kp.cms.bo.exam.ExamStudentInternalFinalMarkDetailsBO;
import com.kp.cms.bo.exam.ExamStudentOverallInternalMarkDetailsBO;
import com.kp.cms.bo.exam.ExamStudentPassFail;
import com.kp.cms.bo.exam.ExamStudentPreviousClassDetailsBO;
import com.kp.cms.bo.exam.ExamStudentSubGrpHistoryBO;
import com.kp.cms.bo.exam.ExamSubjectRuleSettingsBO;
import com.kp.cms.bo.exam.SubjectRuleSettings;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.exceptions.DataNotFoundException;
import com.kp.cms.to.exam.BatchClassTO;
import com.kp.cms.to.exam.DisplayValueTO;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.HibernateUtil;

@SuppressWarnings("unchecked")
public class ExamUpdateProcessImpl  extends ExamGenImpl {
	private static final Log log = LogFactory.getLog(ExamGenImpl.class);

	// To get students for a class
	public ArrayList<Integer> getStudentsForClass(Integer classId) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		ArrayList<Integer> l = null;
		String HQL_QUERY = "";
		HQL_QUERY = "select s.id"
				+ " from StudentUtilBO s"
				+ " where s.classSchemewiseUtilBO.classId = :classId"
				+ " and s.admApplnUtilBO.id in (select a.id from AdmApplnUtilBO a"
				+ " where a.isCancelled = 0)";

		Query query = session.createQuery(HQL_QUERY);
		query.setParameter("classId", classId);
		l = new ArrayList<Integer>(query.list());
		if (l != null && l.size() > 0) {
			return l;
		} else {
			return new ArrayList<Integer>();
		}
	}

	public ArrayList<Integer> getStudentsForClassWithPrevExam(Integer classId,
			String isPreviousExam) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		ArrayList<Integer> l = null;
		String HQL_QUERY = "";
		Query query = null;
		if (isPreviousExam.equalsIgnoreCase("true")) {

			HQL_QUERY = " select s.student_id"
					+ " from EXAM_student_previous_class_details s "
					+ " left join student stu on " + " s.student_id = stu.id "
					+ " left join adm_appln on "
					+ " adm_appln.id = stu.adm_appln_id where "
					+ " s.class_id = " + classId
					+ " and adm_appln.is_cancelled = 0";
			query = session.createSQLQuery(HQL_QUERY);
		} else {
			HQL_QUERY = "select s.id" + " from StudentUtilBO s"
					+ " where s.classSchemewiseUtilBO.classId = :classId"
					+ " and s.admApplnUtilBO.isCancelled = 0)";
			query = session.createQuery(HQL_QUERY);
			query.setParameter("classId", classId);
		}

		l = new ArrayList<Integer>(query.list());
		session.flush();
		session.close();
		if (l != null && l.size() > 0) {
			return l;
		} else {
			return new ArrayList<Integer>();
		}
	}

	// To FETCH the joining batch & classes based on the process selected
	public List<Object[]> get_classDetails(Integer examId, Integer academicYear) {
		String HQL_QUERY = "";
		try {
			if (academicYear == null || academicYear == 0) {
				HQL_QUERY = "select cs.classUtilBO.id, cs.classUtilBO.name, csd.academicYear||'-'||csd.academicYear+1, csd.semesterYearNo"
						+ " from ClassSchemewiseUtilBO cs"
						+ " join cs.curriculumSchemeDurationUtilBO csd"
						+ " where (csd.curriculumSchemeUtilBO.courseId, csd.curriculumSchemeUtilBO.courseSchemeId,"
						+ " csd.semesterYearNo) in "
						+ " (select eec.courseId,"
						+ " eec.courseSchemeId, eec.schemeNo "
						+ " from ExamExamCourseSchemeDetailsBO eec "
						+ " where eec.examId = :examId) and cs.classUtilBO.isActive = 1 order by csd.academicYear";
			} else {
				HQL_QUERY = "select cs.classUtilBO.id, cs.classUtilBO.name, csd.academicYear||'-'||csd.academicYear+1, csd.semesterYearNo"
						+ " from ClassSchemewiseUtilBO cs"
						+ " join cs.curriculumSchemeDurationUtilBO csd"
						+ " where  csd.academicYear = :academicYear and cs.classUtilBO.isActive = 1"
						+ " order by csd.academicYear";
			}
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			Session session = sessionFactory.openSession();

			Query query = session.createQuery(HQL_QUERY);
			if (examId != null) {
				query.setParameter("examId", examId);
			}
			if (academicYear != null) {
				query.setParameter("academicYear", academicYear);
			}
			return query.list();
		} catch (Exception e) {

		}
		return null;
	}

	// ==========================================================================
	// =========================================================================
	// =============== PROCESS - OVERALL REGULAR ===============

	// To FETCH subjects for a given class which are not regular
	public ArrayList<Integer> getSubjectsForClass(Integer classId) {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		ArrayList<Integer> l = null;
		String HQL = "";

		HQL = "SELECT DISTINCT s.id" + " FROM class_schemewise cs"
				+ " JOIN curriculum_scheme_subject css"
				+ " ON cs.curriculum_scheme_duration_id ="
				+ " css.curriculum_scheme_duration_id"
				+ " JOIN curriculum_scheme_duration csd"
				+ " ON cs.curriculum_scheme_duration_id = csd.id"
				+ " JOIN curriculum_scheme c"
				+ " ON csd.curriculum_scheme_id = c.id"
				+ " JOIN subject_group_subjects sgs"
				+ " ON css.subject_group_id = sgs.subject_group_id"
				+ " JOIN subject s" + "  ON sgs.subject_id = s.id"
				+ " WHERE cs.class_id = :classId ";

		Query query = session.createSQLQuery(HQL);
		query.setParameter("classId", classId);
		l = new ArrayList<Integer>(query.list());
		if (l != null && l.size() > 0) {
			return l;
		} else {
			return new ArrayList<Integer>();
		}
	}

	public ArrayList<Integer> getSubjectsForStudentForSupp(Integer studentId) {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		ArrayList<Integer> l = new ArrayList<Integer>();
		String HQL = "";

		HQL = "  SELECT s.id  FROM   student st"
				+ "  LEFT JOIN"
				+ "  applicant_subject_group asg"
				+ "  ON asg.adm_appln_id = st.adm_appln_id"
				+ "  LEFT JOIN"
				+ "  subject_group_subjects sgs"
				+ "  ON asg.subject_group_id = sgs.subject_group_id and sgs.is_active = 1"
				+ "  LEFT JOIN" + "  subject s" + "  ON sgs.subject_id = s.id"
				+ "  LEFT JOIN class_schemewise"
				+ "  ON st.class_schemewise_id = class_schemewise.id"
				+ "  LEFT JOIN classes"
				+ "  ON class_schemewise.class_id = classes.id"
				+ "  LEFT JOIN adm_appln"
				+ "  ON st.adm_appln_id = adm_appln.id"
				+ "  LEFT JOIN personal_data"
				+ "  ON adm_appln.personal_data_id = personal_data.id"
				+ "  WHERE st.id = :studentId" + " UNION"
				+ " SELECT sgs.subject_id"
				+ "   FROM EXAM_student_sub_grp_history sgh" + " LEFT JOIN"
				+ " subject_group_subjects sgs"
				+ " ON sgh.subject_group_id = sgs.subject_group_id"
				+ "  AND sgs.is_active = 1" + " LEFT JOIN"
				+ " EXAM_student_previous_class_details pr"
				+ " ON pr.student_id = sgh.student_id"
				+ " WHERE sgh.student_id = :studentId";

		Query query = session.createSQLQuery(HQL);
		query.setParameter("studentId", studentId);
		Iterator<Object> itr = query.list().iterator();
		while (itr.hasNext()) {
			Object obj = (Object) itr.next();
			if (obj != null) {
				l.add((Integer) obj);
			}

		}

		return l;

	}

	// To fetch student's subjects
	public ArrayList<Integer> getSubjectsForStudent(Integer studentId) {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		ArrayList<Integer> l = new ArrayList<Integer>();
		String HQL = "";

		HQL = "  SELECT s.id  FROM   student st"
				+ "  LEFT JOIN"
				+ "  applicant_subject_group asg"
				+ "  ON asg.adm_appln_id = st.adm_appln_id"
				+ "  LEFT JOIN"
				+ "  subject_group_subjects sgs"
				+ "  ON asg.subject_group_id = sgs.subject_group_id and sgs.is_active = 1"
				+ "  LEFT JOIN" + "  subject s" + "  ON sgs.subject_id = s.id"
				+ "  LEFT JOIN class_schemewise"
				+ "  ON st.class_schemewise_id = class_schemewise.id"
				+ "  LEFT JOIN classes"
				+ "  ON class_schemewise.class_id = classes.id"
				+ "  LEFT JOIN adm_appln"
				+ "  ON st.adm_appln_id = adm_appln.id"
				+ "  WHERE st.id = :studentId";

		Query query = session.createSQLQuery(HQL);
		query.setParameter("studentId", studentId);
		Iterator<Object> itr = query.list().iterator();
		while (itr.hasNext()) {
			Object obj = (Object) itr.next();
			if (obj != null) {
				l.add((Integer) obj);
			}

		}
		session.flush();
		session.close();
		return l;

	}

	public ArrayList<Integer> getSubjectsForStudentWithPrevExam(
			Integer studentId, String isPrevExam,int schemeNo) {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		ArrayList<Integer> l = new ArrayList<Integer>();
		String HQL = "";
		if (isPrevExam.equalsIgnoreCase("true")) {
			Integer previousSchemeNo=null;
			if(schemeNo>0){
				previousSchemeNo=schemeNo;
			}else{
				previousSchemeNo=getPreviousSchemeNo(studentId);
			}
			HQL = "  SELECT s.id  FROM   EXAM_student_previous_class_details st"
					+ "  LEFT JOIN"
					+ "  EXAM_student_sub_grp_history asg"
					+ "  ON asg.student_id = st.student_id"
					+ "  LEFT JOIN"
					+ "  subject_group_subjects sgs"
					+ "  ON asg.subject_group_id = sgs.subject_group_id and sgs.is_active = 1"
					+ "  LEFT JOIN"
					+ "  subject s"
					+ "  ON sgs.subject_id = s.id"
					+ "  WHERE st.student_id = :studentId"
					+ " and asg.scheme_no = "
					+ previousSchemeNo
					+ " and st.scheme_no = " + previousSchemeNo;

		} else {
			HQL = "  SELECT s.id  FROM   student st"
					+ "  LEFT JOIN"
					+ "  applicant_subject_group asg"
					+ "  ON asg.adm_appln_id = st.adm_appln_id"
					+ "  LEFT JOIN"
					+ "  subject_group_subjects sgs"
					+ "  ON asg.subject_group_id = sgs.subject_group_id and sgs.is_active = 1"
					+ "  LEFT JOIN" + "  subject s"
					+ "  ON sgs.subject_id = s.id"
					+ "  WHERE st.id = :studentId";
		}
		Query query = session.createSQLQuery(HQL);
		query.setParameter("studentId", studentId);
		Iterator<Object> itr = query.list().iterator();
		while (itr.hasNext()) {
			Object obj = (Object) itr.next();
			if (obj != null) {
				l.add((Integer) obj);
			}

		}
		session.flush();
		session.close();
		return l;

	}

	// To FETCH subjects for a given class
	public ArrayList<Integer> get_all_subjects_for_class(Integer classId) {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		ArrayList<Integer> l = null;
		String HQL = "";

		HQL = "SELECT DISTINCT s.id" + " FROM class_schemewise cs"
				+ " JOIN curriculum_scheme_subject css"
				+ " ON cs.curriculum_scheme_duration_id ="
				+ " css.curriculum_scheme_duration_id"
				+ " JOIN curriculum_scheme_duration csd"
				+ " ON cs.curriculum_scheme_duration_id = csd.id"
				+ " JOIN curriculum_scheme c"
				+ " ON csd.curriculum_scheme_id = c.id"
				+ " JOIN subject_group_subjects sgs"
				+ " ON css.subject_group_id = sgs.subject_group_id"
				+ " JOIN subject s" + "  ON sgs.subject_id = s.id"
				+ " WHERE cs.class_id = :classId "
				+ " AND s.id IN (SELECT srs.subject_id as subjId"
				+ " FROM EXAM_subject_rule_settings srs where srs.is_active = 1)";

		Query query = session.createSQLQuery(HQL);
		query.setParameter("classId", classId);
		l = new ArrayList<Integer>(query.list());
		if (l != null && l.size() > 0) {
			return l;
		} else {
			return new ArrayList<Integer>();
		}
	}

	public List<Object[]> get_type_of_evaluation_theory(Integer subjectId,
			Integer courseId, Integer schemeNo, String subjectType, String academicYear) {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		List<Object[]> l = null;

		String HQL_QUERY = "select distinct s.subjectId, s.theoryEseEnteredMaxMark, s.theoryEseMaximumMark, s.practicalEseEnteredMaxMark,"
				+ " s.practicalEseMaximumMark, s.subjectUtilBO.isTheoryPractical,"
				+ " s.theoryEseMinimumMark , s.practicalEseMinimumMark , sm.typeOfEvaluation"
				+ " from ExamSubjecRuleSettingsMultipleEvaluatorBO sm"
				+ " right join sm.examSubjectRuleSettingsBO s"
				+ " where s.subjectId = :subjectId"
				+ " and s.schemeNo = :schemeNo and s.courseId = :courseId"
				+ " and s.isActive = 1 and s.academicYear = :year and "
				+ " ((s.subjectUtilBO.isTheoryPractical='t' or s.subjectUtilBO.isTheoryPractical='T') or (s.subjectUtilBO.isTheoryPractical='b' or s.subjectUtilBO.isTheoryPractical='B'))";

		Query query = session.createQuery(HQL_QUERY);
		query.setParameter("subjectId", subjectId);
		query.setParameter("courseId", courseId);
		query.setParameter("schemeNo", schemeNo);
		query.setString("year", academicYear);
		l = query.list();
		if (l != null && l.size() > 0) {
			return l;
		} else {
			return new ArrayList<Object[]>();
		}
	}

	// To FETCH subject type & evaluation type from SUBJECT - RULE - SETTINGS
	public List<Object[]> get_type_of_evaluation_practical(Integer subjectId,
			Integer courseId, Integer schemeNo, String subjectType, String academicYear) {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		List<Object[]> l = null;

		String HQL_QUERY = "select distinct s.subjectId, s.theoryEseEnteredMaxMark, s.theoryEseMaximumMark, s.practicalEseEnteredMaxMark,"
				+ " s.practicalEseMaximumMark, s.subjectUtilBO.isTheoryPractical,"
				+ " s.theoryEseMinimumMark , s.practicalEseMinimumMark, sm.typeOfEvaluation"
				+ " from ExamSubjecRuleSettingsMultipleEvaluatorBO sm"
				+ " right join sm.examSubjectRuleSettingsBO s"
				+ " where s.subjectId = :subjectId"
				+ " and s.schemeNo = :schemeNo and s.courseId = :courseId"
				+ " and s.isActive = 1 and s.academicYear = :year and " +
				" ((s.subjectUtilBO.isTheoryPractical='p' or s.subjectUtilBO.isTheoryPractical='P') or (s.subjectUtilBO.isTheoryPractical='b' or s.subjectUtilBO.isTheoryPractical='B'))";

		Query query = session.createQuery(HQL_QUERY);
		query.setParameter("subjectId", subjectId);
		query.setParameter("courseId", courseId);
		query.setParameter("schemeNo", schemeNo);
		query.setString("year", academicYear);
		l = query.list();
		if (l != null && l.size() > 0) {
			return l;
		} else {
			return new ArrayList<Object[]>();
		}
	}

	// To get students' final marks
	public List<Object[]> get_student_mark_details(Integer examId,
			ArrayList<Integer> subjectList, Integer studentId, Integer courseId) {

		if (subjectList.size() > 0) {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			Session session = sessionFactory.openSession();

			List<Object[]> l = null;
			String HQL_QUERY = "";

			HQL_QUERY = " SELECT sub.id AS subjectId,"
					+ " sub.name,"
					+ " b.maxthoery,"
					+ " b.maxpractical,"
					+ " b.minthoery,"
					+ " b.minpractical,"
					+ " b.avgthoery,"
					+ " b.avgpractical,"
					+ " sub.is_theory_practical"
					+ " FROM subject sub"
					+ " LEFT JOIN"
					+ " (SELECT a.exam_id,"
					+ " a.student_id,"
					+ " a.subject_id,"
					+ " (max(a.finalthoerymark)) AS maxthoery,"
					+ " (max(a.finalpracticalmark)) AS maxpractical,"
					+ " (sum(a.finalthoerymark) "
					+ " / (SELECT distinct e.no_of_evaluations "
					+ " FROM EXAM_subject_rule_settings_mul_evaluator as e JOIN "
					+ " EXAM_subject_rule_settings as srs ON e.subject_rule_settings_id = srs.id "
					+ " WHERE srs.subject_id = a.subject_id AND e.is_theory_practical = 't' and srs.course_id = :courseId"
					+ " AND srs.academic_year IN (SELECT academic_year "
					+ " FROM EXAM_definition "
					+ " WHERE id = :examId))) "
					+ " AS avgthoery, "
					+ " (sum(a.finalpracticalmark) "
					+ " / (SELECT distinct e.no_of_evaluations "
					+ " FROM EXAM_subject_rule_settings_mul_evaluator as e JOIN "
					+ " EXAM_subject_rule_settings as srs ON e.subject_rule_settings_id = srs.id "
					+ " WHERE srs.subject_id = a.subject_id AND e.is_theory_practical = 'p'"
					+ " AND srs.academic_year IN (SELECT academic_year "
					+ " FROM EXAM_definition "
					+ " WHERE id = :examId))) "
					+ " AS avgpractical, "
					+ " (min(a.finalthoerymark)) AS minthoery,"
					+ " (min(a.finalpracticalmark)) AS minpractical"
					+ " FROM (SELECT me.exam_id,"
					+ " me.student_id,"
					+ " med.subject_id,"
					+ " me.evaluator_type_id,"
					+ " sum(med.theory_marks) AS finalthoerymark,"
					+ " sum(med.practical_marks) AS finalpracticalmark"
					+ " FROM EXAM_marks_entry me"
					+ " LEFT JOIN EXAM_marks_entry_details med"
					+ " ON med.marks_entry_id = me.id"
					+ " WHERE me.exam_id = :examId AND me.student_id = :studentId"
					+ " GROUP BY me.exam_id," + " me.student_id,"
					+ " med.subject_id," + " me.evaluator_type_id) a"
					+ " GROUP BY a.student_id, a.subject_id) AS b"
					+ " ON b.subject_id = sub.id AND b.exam_id = :examId"
					+ " WHERE sub.id IN (:subjectId )";

			Query query = session.createSQLQuery(HQL_QUERY);
			query.setParameter("examId", examId);
			query.setParameter("studentId", studentId);
			query.setParameter("courseId", courseId);
			query.setParameterList("subjectId", subjectList);
			l = query.list();
			if (l != null && l.size() > 0) {
				return l;
			} else {
				return new ArrayList<Object[]>();
			}
		}
		return new ArrayList<Object[]>();
	}

	// To get course & scheme for a subject & class
	public List<Object[]> get_course_scheme_of_subject(Integer subjectId,
			Integer classId) {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		List<Object[]> l = null;
		String SQL_QUERY = " select cs.course_id, csd.semester_year_no from curriculum_scheme_subject css"
				+ " join curriculum_scheme_duration csd on css.curriculum_scheme_duration_id = csd.id"
				+ " join curriculum_scheme cs on csd.curriculum_scheme_id = cs.id"
				+ " join class_schemewise csw on csw.curriculum_scheme_duration_id = csd.id"
				+ " and csw.class_id = :classId"
				+ " join subject_group_subjects sgs on css.subject_group_id = sgs.subject_group_id and sgs.is_active = 1"
				+ " join subject ON sgs.subject_id = subject.id"
				+ " and subject.id = :subjectId ";

		Query query = session.createSQLQuery(SQL_QUERY);
		query.setParameter("subjectId", subjectId);
		query.setParameter("classId", classId);
		l = (List<Object[]>) query.list();

		if (l != null && l.size() > 0) {
			return l;
		} else {
			return new ArrayList<Object[]>();
		}

	}

	// ==========================================================================
	// =========================================================================
	// ================= PROCESS - UPDATE DETENTION =================

	// To get course & schemeNo for a class
	public List<Object[]> get_course_schemeNo_forClass(Integer classId) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		List<Object[]> l = null;
		String HQL_QUERY = "select cls.curriculumSchemeDurationUtilBO.curriculumSchemeUtilBO.courseId,"
				+ " cls.curriculumSchemeDurationUtilBO.semesterYearNo"
				+ " from ClassSchemewiseUtilBO cls"
				+ " where cls.classId = :classId";

		Query query = session.createQuery(HQL_QUERY);
		query.setParameter("classId", classId);
		l = (List<Object[]>) query.list();

		if (l != null && l.size() > 0) {
			return l;
		} else {
			return new ArrayList<Object[]>();
		}
	}

	// ==========================================================================
	// =========================================================================
	// =================== PROCESS - PROMOTION ================

	// To get the value of maxBackLogPercentage & maxBackLogNumber from
	// promotion criteria for a course & scheme
	public String get_maxPecent_OrMaxLog(Integer courseId, Integer schemeNo,
			boolean maxLogNumber_LogPercentage) {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		List list = null;
		String HQL_QUERY = "";
		String maxBackLogOrPercentage = null;
		if (maxLogNumber_LogPercentage) {
			HQL_QUERY = "select epr.maxBacklogNumber"
					+ " from ExamPromotionCriteriaBO epr"
					+ " where epr.courseId = :courseId and epr.fromSchemeId = :schemeNo";
			maxBackLogOrPercentage = "maxBacLogNumber_0";

		} else {

			HQL_QUERY = "select epr.maxBacklogCountPrcntg"
					+ " from ExamPromotionCriteriaBO epr"
					+ " where epr.courseId = :courseId and epr.fromSchemeId = :schemeNo";
			maxBackLogOrPercentage = "maxBacLogPercentage_0";
		}

		Query query = session.createQuery(HQL_QUERY);

		query.setParameter("courseId", courseId);
		query.setParameter("schemeNo", schemeNo);
		list = query.list();
		if (list != null && list.size() > 0) {
			if (list.get(0) != null)
				maxBackLogOrPercentage += list.get(0);
		}
		return maxBackLogOrPercentage;
	}

	// To determine for a course & scheme what has been defined in promotion
	// criteria
	public boolean get_numberLog_percentage(Integer courseId, Integer schemeNo) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		boolean testBackLogOrPercentage = false;
		List list = null;
		String HQL_QUERY = "";
		HQL_QUERY = "select epr.maxBacklogNumber"
				+ " from ExamPromotionCriteriaBO epr"
				+ " where epr.courseId = :courseId and epr.fromSchemeId = :schemeNo";

		Query query = session.createQuery(HQL_QUERY);

		query.setParameter("courseId", courseId);
		query.setParameter("schemeNo", schemeNo);
		list = query.list();
		if (list != null && list.size() > 0) {
			if (list.get(0) != null) {
				if (Integer.parseInt(list.get(0).toString()) < 0) {
					testBackLogOrPercentage = false;
				} else {
					testBackLogOrPercentage = true;
				}
			}
		}
		return testBackLogOrPercentage;
	}

	// Get count of students' failed subjects
	public Integer get_count_failed_subjects(Integer classId,
			Integer studentId, ArrayList<Integer> subjectId) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		Integer count = 0;
		String HQL_QUERY = "";
		if (subjectId != null && subjectId.size() > 0) {
			HQL_QUERY = "select count(*)"
					+ " from ExamStudentFinalMarkDetailsBO esf"
					+ " where esf.studentId = :studentId and esf.classId = :classId"
					+ " and esf.subjectId in (:subjectId)"
					+ " and esf.passOrFail = 'fail'";

			Query query = session.createQuery(HQL_QUERY);
			query.setParameter("classId", classId);
			query.setParameter("studentId", studentId);
			query.setParameterList("subjectId", subjectId);
			List list = query.list();

			if (list != null && list.size() > 0) {
				count = new BigInteger(list.get(0).toString()).intValue();
			}
		}
		return count;
	}

	// To get curriculum scheme of student
	public Integer getCurrSchemeOfStudent(Integer studentId) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		Integer classSchemewiseId = null;
		String HQL_QUERY = "";

		HQL_QUERY = "select s.classSchemewiseUtilBO.curriculumSchemeDurationUtilBO.curriculumSchemeUtilBO.id"
				+ " from StudentUtilBO s" + " where s.id = :studentId";

		Query query = session.createQuery(HQL_QUERY);
		query.setParameter("studentId", studentId);
		List list = query.list();

		if (list != null && list.size() > 0) {
			classSchemewiseId = (Integer) list.get(0);

		}

		return classSchemewiseId;

	}

	// To get curriculum scheme of student
	public Integer getSchemeNoOfStudent(Integer studentId) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		Integer classSchemewiseId = null;
		String HQL_QUERY = "";

		HQL_QUERY = "select "
				+ " s.classSchemewiseUtilBO.curriculumSchemeDurationUtilBO.semesterYearNo"
				+ " from StudentUtilBO s" + " where s.id = :studentId";

		Query query = session.createQuery(HQL_QUERY);
		query.setParameter("studentId", studentId);
		List list = query.list();

		if (list != null && list.size() > 0) {
			classSchemewiseId = (Integer) list.get(0);

		}

		return classSchemewiseId;

	}

	// To get student's academic year for next scheme no
	public Integer getacademicYearForNextScheme(Integer currSchId,
			Integer schemeNo) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		Integer classSchemewiseId = null;
		String HQL_QUERY = "";

		HQL_QUERY = "select csd.academicYear"
				+ " from CurriculumSchemeDurationUtilBO csd"
				+ " where csd.curriculumSchemeUtilBO.id = :currSchId and csd.semesterYearNo = :schemeNo + 1";

		Query query = session.createQuery(HQL_QUERY);
		query.setParameter("currSchId", currSchId);
		query.setParameter("schemeNo", schemeNo);
		List list = query.list();

		if (list != null && list.size() > 0) {
			classSchemewiseId = (Integer) list.get(0);

		}

		return classSchemewiseId;

	}

	// To get student's next classSchemewise considering section for promotion
	public Integer getStudentNextClassSchemwiseForSection(Integer courseId,
			Integer schemeNo, String sectionName, Integer academicYear) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		Integer classSchemewiseId = null;
		String HQL_QUERY = "";

		HQL_QUERY = "select cs.id"
				+ " from ClassSchemewiseUtilBO cs"
				+ " join cs.classUtilBO c"
				+ " where cs.curriculumSchemeDurationUtilBO.curriculumSchemeUtilBO.courseId = :courseId"
				+ " and cs.curriculumSchemeDurationUtilBO.semesterYearNo = :schemeNo + 1 ";

		if (sectionName != null) {
			HQL_QUERY = HQL_QUERY + " and c.sectionName = :sectionName ";
		}

		if (academicYear != null) {
			HQL_QUERY = HQL_QUERY
					+ " and cs.curriculumSchemeDurationUtilBO.academicYear = :academicYear ";
		}

		Query query = session.createQuery(HQL_QUERY);
		query.setParameter("courseId", courseId);
		query.setParameter("schemeNo", schemeNo);
		if (sectionName != null) {
			query.setParameter("sectionName", sectionName);
		}
		if (academicYear != null) {
			query.setParameter("academicYear", academicYear);
		}

		List list = query.list();

		if (list != null && list.size() > 0) {
			classSchemewiseId = (Integer) list.get(0);

		}

		return classSchemewiseId;

	}

	// To get student's next class
	public Integer getStudentNextCla(Integer courseId, Integer schemeNo,
			String sectionName, Integer academicYear) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		Integer classId = null;
		String HQL_QUERY = "";

		HQL_QUERY = "select cs.classId"
				+ " from ClassSchemewiseUtilBO cs"
				+ " where cs.curriculumSchemeDurationUtilBO.curriculumSchemeUtilBO.courseId = :courseId"
				+ " and cs.curriculumSchemeDurationUtilBO.semesterYearNo = :schemeNo + 1";

		if (sectionName != null) {
			HQL_QUERY = HQL_QUERY
					+ " and cs.classUtilBO.sectionName = :sectionName ";
		}
		if (academicYear != null) {
			HQL_QUERY = HQL_QUERY
					+ " and cs.curriculumSchemeDurationUtilBO.academicYear = :academicYear ";
		}
		Query query = session.createQuery(HQL_QUERY);
		query.setParameter("courseId", courseId);
		query.setParameter("schemeNo", schemeNo);
		if (sectionName != null) {
			query.setParameter("sectionName", sectionName);
		}
		if (academicYear != null) {
			query.setParameter("academicYear", academicYear);
		}
		List list = query.list();

		if (list != null && list.size() > 0) {
			classId = (Integer) list.get(0);

		}

		return classId;

	}

	// To get non - detained students for a class
	public ArrayList<Integer> getNonDetainedStudentsForClass(Integer classId) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		ArrayList<Integer> l = null;
		
		String sql_query="select st.id as studId from student st left join EXAM_student_detention_rejoin_details" +
				" e on e.student_id = st.id" +
				" and  e.class_schemewise_id=st.class_schemewise_id" +
				" left join class_schemewise cs on st.class_schemewise_id = cs.id" +
				" left join classes cl on cs.class_id = cl.id" +
				" where cl.id=:classId" +
				" and e.student_id is null";
		
//		String HQL_QUERY = "select distinct  s.id   from ClassSchemewiseUtilBO c"
//				+ " join c.studentUtilBOSet s   where c.classId = :classId"
//				+ " and s.id not in (select esd.studentId from ExamStudentDetentionDetailsBO esd)"
//				+ " and s.id not in (select esdd.studentId from ExamStudentDiscontinuationDetailsBO esdd)";

		Query query = session.createSQLQuery(sql_query);
		query.setParameter("classId", classId);
		l = new ArrayList<Integer>(query.list());
		if (l != null && l.size() > 0) {
			return l;
		} else {
			return new ArrayList<Integer>();
		}
	}
	
	// To fetch the student's second language
	public String getSecondLanguageHistory(Integer stuId) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		String secondLanguage = null;
		String HQL_QUERY = "";

		HQL_QUERY = "select s.admApplnUtilBO.personalDataUtilBO.secondLanguage"
				+ " from StudentUtilBO s" + " where s.id = :stuId";

		Query query = session.createQuery(HQL_QUERY);
		query.setParameter("stuId", stuId);
		List list = query.list();

		if (list != null && list.size() > 0) {
			if (list.get(0) != null)
				secondLanguage = (list.get(0).toString());

		}

		return secondLanguage;
	}

	// To fetch the student's second language Id
	public Integer getSecondLanguageId(String secLang) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		Integer secondLanguageId = null;
		String HQL_QUERY = "";

		HQL_QUERY = "select e.id" + " from ExamSecondLanguageMasterBO e"
				+ " where e.name = :secLang";

		Query query = session.createQuery(HQL_QUERY);
		query.setParameter("secLang", secLang);
		List list = query.list();

		if (list != null && list.size() > 0) {
			if (list.get(0) != null)
				secondLanguageId = Integer.parseInt((list.get(0).toString()));

		}

		return secondLanguageId;

	}

	// To fetch the previous class' subject groups of a student
	public List<Object[]> getPrevSubjGrpForStudent(Integer studentAdmnApplnId) {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		List l = null;

		String HQL_QUERY = "SELECT DISTINCT student.id, a.subject_group_id, curriculum_scheme_duration.semester_year_no"
				+ " FROM applicant_subject_group a"
				+ " join student on a.adm_appln_id = student.adm_appln_id"
				+ " JOIN class_schemewise"
				+ " ON student.class_schemewise_id = class_schemewise.id"
				+ " JOIN curriculum_scheme_duration"
				+ " ON class_schemewise.curriculum_scheme_duration_id ="
				+ " curriculum_scheme_duration.id"
				+ " WHERE a.adm_appln_id = "
				+ studentAdmnApplnId;

		Query query = session.createSQLQuery(HQL_QUERY);
		session.flush();
		l = query.list();

		if (l != null && l.size() > 0) {
			return l;
		} else {
			return new ArrayList<Object[]>();
		}
	}

	// To get subject groups for next class
	public ArrayList<Integer> getNextSubjGrpForClass(Integer classId) {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		ArrayList<Integer> l = null;

		String HQL_QUERY = "select css.subjectGroupUtilBO.id"
				+ " from ClassSchemewiseUtilBO c"
				+ " join c.curriculumSchemeDurationUtilBO cd"
				+ " join cd.curriculumSchemeSubjectUtilBOSet css"
				+ " where c.classId = :classId";

		Query query = session.createQuery(HQL_QUERY);
		query.setParameter("classId", classId);
		l = new ArrayList<Integer>(query.list());
		if (l != null && l.size() > 0) {
			return l;
		} else {
			return new ArrayList<Integer>();
		}

	}

	// To get the next class' subject groups of a student
	public List<Object[]> getNextSubjGrpForStudent(Integer studentId,
			Integer classId, Integer nextClassSchemewise) {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		List<Object[]> l = null;
		List<Object[]> list = null;

		// Get previous class details for student
		String HQL_QUERY = "select s.id,s.classSchemewiseUtilBO.classId,"
				+ " s.classSchemewiseUtilBO.curriculumSchemeDurationUtilBO.academicYear,"
				+ " s.classSchemewiseUtilBO.curriculumSchemeDurationUtilBO.semesterYearNo"
				+ " from StudentUtilBO s" + " where s.id = :studentId";

		Query query = session.createQuery(HQL_QUERY);
		query.setParameter("studentId", studentId);

		list = query.list();

		// Insert into Previous class history table
		Integer studId = null, clasId = null, academicYear = null, semYearNo = null;
		if (list != null && list.size() > 0) {
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				Object row[] = (Object[]) iterator.next();
				if (row[0] != null) {
					studId = (Integer) row[0];
				}
				if (row[1] != null) {
					clasId = (Integer) row[1];
				}
				if (row[2] != null) {
					academicYear = ((Integer) row[2]);
				}
				if (row[3] != null) {
					semYearNo = ((Integer) row[3]);
				}
				ExamStudentPreviousClassDetailsBO prevClassBO = new ExamStudentPreviousClassDetailsBO(
						studId, clasId, academicYear, semYearNo);
				insert(prevClassBO);

			}
		}

		// Update student's next classSchemewise
		HQL_QUERY = "update StudentUtilBO s set s.classSchemewiseId = :nextClassSchemewise"
				+ " where s.id = :studentId";
		query = session.createQuery(HQL_QUERY);
		query.setParameter("studentId", studentId);
		query.setParameter("nextClassSchemewise", nextClassSchemewise);
		query.executeUpdate();

		HQL_QUERY = "select distinct s.admApplnId, css.subjectGroupUtilBO.id,"
				+ " css.subjectGroupUtilBO.isCommonSubGrp,"
				+ " css.subjectGroupUtilBO.secondLanguageId"
				+ " from StudentUtilBO s" + " join s.classSchemewiseUtilBO c"
				+ " join c.curriculumSchemeDurationUtilBO cd"
				+ " join cd.curriculumSchemeSubjectUtilBOSet css"
				+ " where s.id = :studentId and c.classId = :classId";

		query = session.createQuery(HQL_QUERY);
		query.setParameter("studentId", studentId);
		query.setParameter("classId", classId);
		l = (List<Object[]>) query.list();

		if (l != null && l.size() > 0) {
			return l;
		} else {
			return new ArrayList<Object[]>();
		}
	}

	// To get the second language against the subject group for new class
	public ArrayList<Integer> getSecondLangAgainstSubGrp(
			ArrayList<Integer> subjectGrpId) {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		String HQL_QUERY = "";

		HQL_QUERY = "select s.secondLanguageId" + " from SubjectGroupUtilBO s"
				+ " where s.id in (:subjectGrpId)";

		Query query = session.createQuery(HQL_QUERY);
		query.setParameterList("subjectGrpId", subjectGrpId);
		List list = new ArrayList<Integer>(query.list());

		if (list != null && list.size() > 0) {

			return new ArrayList<Integer>(list);
		} else {
			return new ArrayList<Integer>();
		}
	}

	// To get student's admission application id
	public Integer getStudentAdmnApplnId(Integer studentId) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		Integer classSchemewiseId = null;
		String HQL_QUERY = "";

		HQL_QUERY = "select s.admApplnId" + " from StudentUtilBO s"
				+ " where s.id = :studentId";

		Query query = session.createQuery(HQL_QUERY);
		query.setParameter("studentId", studentId);
		List list = query.list();

		if (list != null && list.size() > 0) {
			classSchemewiseId = (Integer) list.get(0);

		}

		return classSchemewiseId;

	}

	// To get common subject groups
	public ArrayList<Integer> getCommonSubjectGroup(ArrayList<Integer> ids) {

		if (ids != null && ids.size() > 0) {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			Session session = sessionFactory.openSession();

			String HQL_QUERY = "";

			HQL_QUERY = "select s.id" + " from SubjectGroupUtilBO s"
					+ " where s.id in (:ids) and s.isCommonSubGrp = 1";

			Query query = session.createQuery(HQL_QUERY);
			query.setParameterList("ids", ids);
			ArrayList<Integer> listSubjIds = new ArrayList<Integer>(query.list());

			return listSubjIds;
		}
		return new ArrayList<Integer>();
	}

	// Update student's subject group for his new class & save his previous
	// subject group in the history table
	public void update_subjectGroup(Integer clsId,
			ArrayList<ExamStudentSubGrpHistoryBO> subGrpHistoryBO,
			Integer nextClass, Integer nextClassSchemewise, Integer stuId,
			Integer studentAdmnApplnId,
			ArrayList<ApplicantSubjectGroupUtilBO> nextSubGrp) {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		// Fetch the previous class's subject group & insert into history
		// table

		insert_List(subGrpHistoryBO);

		// Delete the existing subject groups for a student from
		// ApplicantSubGrp table

		String HQL_QUERY = "delete from ApplicantSubjectGroup a"
				+ " where a.admAppln.id = :studentAdmnApplnId";

		Query query = session.createQuery(HQL_QUERY);
		query.setParameter("studentAdmnApplnId", studentAdmnApplnId);

		query.executeUpdate();

		// Fetch student's new class's subject group & Insert into
		// applicantSubjectGroup
		// table
		insert_List(nextSubGrp);

		session.close();

	}

	// Check exam fees status of a student
	public boolean checkExamFeeStatus(Integer stuId) {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		List<Integer> list = null;
		String HQL_QUERY = "select ese.isExamFeesPaid"
				+ " from ExamStudentEligibilityCheckBO ese"
				+ " where ese.studentId = :stuId ";

		Query query = session.createQuery(HQL_QUERY);
		query.setParameter("stuId", stuId);
		list = query.list();
		if (list.size() > 0 && list.get(0) > 0) {
			return true;
		} else {
			return false;
		}
	}

	// ==========================================================================
	// =========================================================================
	// =================== PROCESS - OVERALL INTERNAL MARKS
	// =========================

	// To get the internal examId for an exam
	public List<Object[]> get_internalExamId_forExam(Integer examId) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		List<Object[]> l = null;
		String HQL_QUERY = "SELECT ed.id, ed.internal_exam_type_id FROM EXAM_definition ed WHERE  ed.id in ("
				+ " select eint.internal_exam_name_id from EXAM_definition e"
				+ " join EXAM_exam_internal_exam_details eint on eint.exam_id = e.id"
				+ " where eint.exam_id = :examId)";

		Query query = session.createSQLQuery(HQL_QUERY);
		query.setParameter("examId", examId);
		l = (List<Object[]>) query.list();

		if (l != null && l.size() > 0) {
			return l;
		} else {
			return new ArrayList<Object[]>();
		}
	}

	// To get student's assignment marks
	public List<Object[]> get_assignment_marks_student(Integer studentId,
			Integer subjectId, Integer examId) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		List<Object[]> l = null;
		String HQL_QUERY = "select eas.theoryMarks, eas.practicalMarks"
				+ " from ExamAssignOverallMarksBO eas"
				+ " where eas.studentId = :studentId and eas.subjectId = :subjectId and eas.examId = :examId"
				+ " and eas.overallAssignmentName like '%Assignment%'";

		Query query = session.createSQLQuery(HQL_QUERY);
		query.setParameter("studentId", studentId);
		query.setParameter("subjectId", subjectId);
		query.setParameter("examId", examId);
		l = (List<Object[]>) query.list();

		if (l != null && l.size() > 0) {
			return l;
		} else {
			return new ArrayList<Object[]>();
		}
	}

	// To get total hours held for subject
	public List<Object[]> getSubjectTotalHoursHeld(Integer attendanceTypeId,
			Integer subjectId, Integer stuId, Integer examId, Integer courseId, 
			String isPreviousExam, boolean isTheoryCalc,int SchemeNo ) {

					
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		List<Object[]> l = null;
		String HQL_QUERY = "";
		boolean flag = false;
		/*if (isPreviousExam.equalsIgnoreCase("true")) {
			Integer previousSchemeNo = getPreviousSchemeNo(stuId);
			HQL_QUERY = " SELECT att.subject_id, sum(hours_held) AS Held  "
					+ " FROM attendance att INNER JOIN attendance_student astu ON astu.attendance_id = att.id "
					+ " INNER JOIN EXAM_student_previous_class_details stu ON stu.student_id = astu.student_id INNER JOIN attendance_class ac "
					+ " ON ac.attendance_id = att.id  "
					+ " left join classes cls on cls.id = stu.class_id "
					+ " left join class_schemewise cs on cs.class_id  = cls.id "
					+ " WHERE stu.student_id = :stuId AND att.subject_id = :subjectId AND att.is_canceled = 0 "
					+ " AND ac.class_schemewise_id = cs.id  and stu.scheme_no = "
					+ previousSchemeNo + " GROUP BY att.subject_id";
		} else {
			HQL_QUERY = " SELECT att.subject_id, sum(hours_held) AS Held"
					+ " FROM attendance att" + " INNER JOIN"
					+ " attendance_student astu"
					+ " ON astu.attendance_id = att.id" + " INNER JOIN"
					+ " student stu" + " ON stu.id = astu.student_id"
					+ " INNER JOIN" + " attendance_class ac"
					+ " ON ac.attendance_id = att.id"
					+ " WHERE stu.id = :stuId"
					+ " AND att.subject_id = :subjectId"
					+ " AND att.is_canceled = 0"
					+ " AND ac.class_schemewise_id = stu.class_schemewise_id"
					+ " GROUP BY att.subject_id";
		}*/
		
		
		String isThPrac = selectSubjectsTypeBySubjectId(subjectId);
//		Integer previousSchemeNo = getPreviousSchemeNo(stuId);
		if (isPreviousExam.equalsIgnoreCase("true")) {
			if ((isThPrac.equalsIgnoreCase("T") || (isThPrac.equalsIgnoreCase("B") && isTheoryCalc) )
					&& attendanceTheoryCheckSubjRuleSettings(subjectId, examId,
							courseId, SchemeNo)) {
				HQL_QUERY = "SELECT att.subject_id, sum(att.hours_held) AS Held "
						+ " FROM  attendance att INNER JOIN attendance_student astu ON astu.attendance_id = att.id "
						+ " INNER JOIN attendance_type at on att.attendance_type_id = at.id"
						+ " INNER JOIN EXAM_student_previous_class_details stu ON stu.student_id = astu.student_id INNER JOIN "
						+ " attendance_class ac ON ac.attendance_id = att.id "
						+ " left join classes cls on cls.id = stu.class_id "
						+ " left join class_schemewise cs on cs.class_id  = cls.id "
						+ " WHERE stu.student_id = :stuId AND att.subject_id = :subId AND att.is_canceled = 0 "
						+ " and at.name = 'Theory'"
						+ " AND ac.class_schemewise_id = cs.id and stu.scheme_no = "
						+ SchemeNo + " GROUP BY att.subject_id ";
				flag = true;
			} else if ((isThPrac.equalsIgnoreCase("P")|| (isThPrac.equalsIgnoreCase("B") && !isTheoryCalc))
					&& attendancePracticalCheckSubjRuleSettings(subjectId, examId,
							courseId, SchemeNo)) {
				HQL_QUERY = "SELECT att.subject_id, sum(att.hours_held) AS Held  "
						+ " FROM  attendance att INNER JOIN attendance_student astu ON astu.attendance_id = att.id "
						+ " INNER JOIN attendance_type at on att.attendance_type_id = at.id"
						+ " INNER JOIN EXAM_student_previous_class_details stu ON stu.student_id = astu.student_id INNER JOIN "
						+ " attendance_class ac ON ac.attendance_id = att.id "
						+ " left join classes cls on cls.id = stu.class_id "
						+ " left join class_schemewise cs on cs.class_id  = cls.id "
						+ " WHERE stu.student_id = :stuId AND att.subject_id = :subId AND att.is_canceled = 0 "
						+ " and at.name = 'Practical'"
						+ " AND ac.class_schemewise_id = cs.id and stu.scheme_no = "
						+ SchemeNo + " GROUP BY att.subject_id ";
				flag = true;

			}
		} else {
//			int curScheme = previousSchemeNo + 1;
			int curScheme = SchemeNo;
			if ((isThPrac.equalsIgnoreCase("T") || (isThPrac.equalsIgnoreCase("B") && isTheoryCalc))
					&& attendanceTheoryCheckSubjRuleSettings(subjectId, examId,
							courseId, curScheme)) {
				HQL_QUERY = " SELECT att.subject_id, sum(att.hours_held) AS Held "
						+ "  FROM  attendance att"
						+ " INNER JOIN"
						+ " attendance_student astu"
						+ " ON astu.attendance_id = att.id"
						+ " INNER JOIN attendance_type at on att.attendance_type_id = at.id"
						+ " INNER JOIN student stu"
						+ " ON stu.id = astu.student_id"
						+ " INNER JOIN"
						+ " attendance_class ac"
						+ " ON ac.attendance_id = att.id"
						+ " WHERE stu.id = :stuId"
						+ " AND att.subject_id = :subId"
						+ " AND att.is_canceled = 0 and at.name = 'Theory'"
						+ " AND ac.class_schemewise_id = stu.class_schemewise_id GROUP BY att.subject_id ";
				flag = true;

			} else if ((isThPrac.equalsIgnoreCase("P") || (isThPrac.equalsIgnoreCase("B") && !isTheoryCalc))
					&& attendancePracticalCheckSubjRuleSettings(subjectId, examId, courseId, curScheme)) {
				HQL_QUERY = " SELECT att.subject_id, sum(att.hours_held) AS Held "
						+ "  FROM  attendance att"
						+ " INNER JOIN"
						+ " attendance_student astu"
						+ " ON astu.attendance_id = att.id"
						+ " INNER JOIN attendance_type at on att.attendance_type_id = at.id"
						+ " INNER JOIN student stu"
						+ " ON stu.id = astu.student_id"
						+ " INNER JOIN"
						+ " attendance_class ac"
						+ " ON ac.attendance_id = att.id"
						+ " WHERE stu.id = :stuId"
						+ " AND att.subject_id = :subId"
						+ " AND att.is_canceled = 0 and at.name = 'Practical'"
						+ " AND ac.class_schemewise_id = stu.class_schemewise_id  GROUP BY att.subject_id ";
				flag = true;

			}
		}

		Query query = session.createSQLQuery(HQL_QUERY);
		// query.setParameter("attendanceTypeId", attendanceTypeId);
		if(flag){
			query.setParameter("subId", subjectId);
			query.setParameter("stuId", stuId);
			l = (List<Object[]>) query.list();
		}
		if (l != null && l.size() > 0) {
			return l;
		} else {
			return new ArrayList<Object[]>();
		}
	}

	// To get total hours attended by student based on rule settings
	public List<Object[]> getStudentAttendance(Integer attendanceTypeId,
			Integer stuId, Integer subId, Integer examId, Integer courseId,
			String isPreviousExam, boolean isTheoryCalc,int schemeNo) {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		boolean flag = false;

		List<Object[]> l = null;
		String HQL_QUERY = "";
		String isThPrac = selectSubjectsTypeBySubjectId(subId);
//		Integer previousSchemeNo = getPreviousSchemeNo(stuId);
//		int schemeNo = 0;
//		if (isPreviousExam.equalsIgnoreCase("true")) {
//			schemeNo = previousSchemeNo;
//		}
//		else{
//			schemeNo = previousSchemeNo + 1;
//		}
		if (isPreviousExam.equalsIgnoreCase("true")) {
			if ((isThPrac.equalsIgnoreCase("T") || (isThPrac.equalsIgnoreCase("B") && isTheoryCalc) )
					&& attendanceTheoryCheckSubjRuleSettings(subId, examId,
							courseId, schemeNo)) {
				HQL_QUERY = "SELECT att.subject_id, sum(att.hours_held), 0, 0 "
						+ " FROM  attendance att INNER JOIN attendance_student astu ON astu.attendance_id = att.id "
						+ " INNER JOIN attendance_type at on att.attendance_type_id = at.id"
						+ " INNER JOIN EXAM_student_previous_class_details stu ON stu.student_id = astu.student_id INNER JOIN "
						+ " attendance_class ac ON ac.attendance_id = att.id "
						+ " left join classes cls on cls.id = stu.class_id "
						+ " left join class_schemewise cs on cs.class_id  = cls.id "
						+ " WHERE stu.student_id = :stuId AND att.subject_id = :subId AND att.is_canceled = 0 "
						+ " and at.name = 'Theory'"
						+ " AND ac.class_schemewise_id = cs.id and stu.scheme_no = "
						+ schemeNo;
				flag = true;
			} else if ((isThPrac.equalsIgnoreCase("P")|| (isThPrac.equalsIgnoreCase("B") && !isTheoryCalc))
					&& attendancePracticalCheckSubjRuleSettings(subId, examId,
							courseId, schemeNo)) {
				HQL_QUERY = "SELECT att.subject_id, sum(att.hours_held), 0, 0 "
						+ " FROM  attendance att INNER JOIN attendance_student astu ON astu.attendance_id = att.id "
						+ " INNER JOIN attendance_type at on att.attendance_type_id = at.id"
						+ " INNER JOIN EXAM_student_previous_class_details stu ON stu.student_id = astu.student_id INNER JOIN "
						+ " attendance_class ac ON ac.attendance_id = att.id "
						+ " left join classes cls on cls.id = stu.class_id "
						+ " left join class_schemewise cs on cs.class_id  = cls.id "
						+ " WHERE stu.student_id = :stuId AND att.subject_id = :subId AND att.is_canceled = 0 "
						+ " and at.name = 'Practical'"
						+ " AND ac.class_schemewise_id = cs.id and stu.scheme_no = "
						+ schemeNo;
				flag = true;
			}
		} else {
			if ((isThPrac.equalsIgnoreCase("T") || (isThPrac.equalsIgnoreCase("B") && isTheoryCalc))
					&& attendanceTheoryCheckSubjRuleSettings(subId, examId,
							courseId, schemeNo)) {
				HQL_QUERY = " SELECT att.subject_id, sum(att.hours_held), 0, 0"
						+ "  FROM  attendance att"
						+ " INNER JOIN"
						+ " attendance_student astu"
						+ " ON astu.attendance_id = att.id"
						+ " INNER JOIN attendance_type at on att.attendance_type_id = at.id"
						+ " INNER JOIN student stu"
						+ " ON stu.id = astu.student_id"
						+ " INNER JOIN"
						+ " attendance_class ac"
						+ " ON ac.attendance_id = att.id"
						+ " WHERE stu.id = :stuId"
						+ " AND att.subject_id = :subId"
						+ " AND att.is_canceled = 0 and at.name = 'Theory'"
						+ " AND ac.class_schemewise_id = stu.class_schemewise_id";
				flag = true;
			} else if ((isThPrac.equalsIgnoreCase("P") || (isThPrac.equalsIgnoreCase("B") && !isTheoryCalc))
					&& attendancePracticalCheckSubjRuleSettings(subId, examId, courseId, schemeNo)) {
				HQL_QUERY = " SELECT att.subject_id, sum(att.hours_held), 0, 0"
						+ "  FROM  attendance att"
						+ " INNER JOIN"
						+ " attendance_student astu"
						+ " ON astu.attendance_id = att.id"
						+ " INNER JOIN attendance_type at on att.attendance_type_id = at.id"
						+ " INNER JOIN student stu"
						+ " ON stu.id = astu.student_id"
						+ " INNER JOIN"
						+ " attendance_class ac"
						+ " ON ac.attendance_id = att.id"
						+ " WHERE stu.id = :stuId"
						+ " AND att.subject_id = :subId"
						+ " AND att.is_canceled = 0 and at.name = 'Practical'"
						+ " AND ac.class_schemewise_id = stu.class_schemewise_id";
				flag = true;
			}
		}

		if (((leaveCheckSubjRuleSettings(subId, examId, courseId, schemeNo)) || coCurricularCheckSubjRuleSettings(
				subId, examId, courseId, schemeNo))) {
			HQL_QUERY = HQL_QUERY
					+ " AND ((astu.is_present = 1 or astu.is_on_leave = 1) or (astu.is_present = 1 or astu.is_cocurricular_leave = 1) ) GROUP BY att.subject_id";
		} else {
			HQL_QUERY = HQL_QUERY
					+ " AND astu.is_present = 1 and astu.is_on_leave = 0 and (astu.is_cocurricular_leave = 0 or astu.is_cocurricular_leave is NULL) GROUP BY att.subject_id";
		}
		Query query = session.createSQLQuery(HQL_QUERY);
		if (flag) {
			query.setParameter("stuId", stuId);
			query.setParameter("subId", subId);
			l = (List<Object[]>) query.list();
		}
		if (l != null && l.size() > 0) {
			return l;
		} else {
			return new ArrayList<Object[]>();
		}
	}

	// THEORY MARKS
	public Double get_student_theory_marks(Integer studentId,
			Integer subjectId, ArrayList<Integer> examId, Integer limit,
			StringBuffer subInternalTheoryPercentage, int schemeNo) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		List<Object[]> l = null;
		String HQL_QUERY = "";
		if (checkTotalRun(subjectId, examId, getStudentCourseId(studentId), "t", schemeNo)) {
			if (limit != null && limit != 0) {
				HQL_QUERY = "SELECT round((sum(convert(a.marks, DECIMAL)) * a.maxmark) / a.valuedmark), "
						+ " (round((sum(a.marks) * a.maxmark) / a.valuedmark) / a.maxmark) * 100 "
						+ " AS prctng FROM (SELECT e.exam_id, "
						+ " e.student_theory_internal_marks AS marks, "
						+ " srs.theory_int_max_marks_total maxmark, "
						+ " srs.theory_int_entry_max_marks_total valuedmark "
						+ " FROM EXAM_student_internal_final_mark_details e "
						+ " JOIN EXAM_subject_rule_settings srs "
						+ " ON srs.subject_id = e.subject_id "
						+ " JOIN EXAM_definition ed ON e.exam_id = ed.id "
						+ " WHERE e.exam_id IN ( :examId) "
						+ " AND e.subject_id = :subjectId "
						+ " AND e.student_id = :studentId "
						+ " AND srs.course_id = (SELECT cs.course_id "
						+ "  FROM          curriculum_scheme cs "
						+ "             JOIN "
						+ "                curriculum_scheme_duration AS csd "
						+ "             ON csd.curriculum_scheme_id = cs.id "
						+ "          JOIN "
						+ "             class_schemewise AS csw "
						+ "          ON csw.curriculum_scheme_duration_id = csd.id "
						+ "       JOIN "
						+ "          student AS stu "
						+ "       ON stu.class_schemewise_id = csw.id "
						+ " WHERE stu.id = :studentId) "
						+ " AND srs.academic_year = ed.academic_year " +
						 " and srs.scheme_no = " + schemeNo + " and srs.is_active = 1"
						+ " GROUP BY e.exam_id "
						+ " ORDER BY e.student_theory_internal_marks DESC LIMIT :limit "
						+ "	) AS a ";
			} else {
				HQL_QUERY = "SELECT round((sum(convert(a.marks, DECIMAL)) * a.maxmark) / a.valuedmark), "
						+ " (round((sum(a.marks) * a.maxmark) / a.valuedmark) / a.maxmark) * 100 "
						+ " AS prctng FROM (SELECT e.exam_id, e.student_theory_internal_marks AS marks, "
						+ " srs.theory_int_max_marks_total maxmark, "
						+ " srs.theory_int_entry_max_marks_total valuedmark "
						+ " FROM EXAM_student_internal_final_mark_details e JOIN "
						+ " EXAM_subject_rule_settings srs ON srs.subject_id = e.subject_id "
						+ " JOIN EXAM_definition ed ON e.exam_id = ed.id WHERE e.exam_id IN ( :examId) "
						+ " AND e.subject_id = :subjectId AND e.student_id = :studentId "
						+ " AND srs.course_id = (SELECT cs.course_id "
						+ "  FROM          curriculum_scheme cs "
						+ "             JOIN "
						+ "                curriculum_scheme_duration AS csd "
						+ "             ON csd.curriculum_scheme_id = cs.id "
						+ "          JOIN "
						+ "             class_schemewise AS csw "
						+ "          ON csw.curriculum_scheme_duration_id = csd.id "
						+ "       JOIN "
						+ "          student AS stu "
						+ "       ON stu.class_schemewise_id = csw.id "
						+ " WHERE stu.id = :studentId) "
						+ " AND srs.academic_year = ed.academic_year " 
						+ " and srs.scheme_no = " + schemeNo + " and srs.is_active = 1"
						+ " GROUP BY e.exam_id "
						+ " ORDER BY e.student_theory_internal_marks DESC) AS a ";
			}

		} else {
			if (limit != null && limit != 0) {
				HQL_QUERY = "SELECT sum(convert(a.marks, DECIMAL)), (sum(a.marks) / sum(a.maxmark)) * 100 "
						+ " FROM (SELECT e.exam_id, "
						+ " si.internal_exam_type_id, "
						+ " round((e.student_theory_internal_marks / si.entered_max_mark) * si.maximum_mark) "
						+ " AS marks, si.maximum_mark AS maxmark "
						+ "          FROM  EXAM_student_internal_final_mark_details e "
						+ "                     JOIN "
						+ "                        EXAM_subject_rule_settings srs "
						+ "                     ON srs.subject_id = e.subject_id "
						+ "                  JOIN "
						+ "                     EXAM_subject_rule_settings_sub_internal si "
						+ "                  ON srs.id = si.subject_rule_settings_id "
						+ "               JOIN "
						+ "                  EXAM_definition ed "
						+ "               ON e.exam_id = ed.id "
						+ "         WHERE e.exam_id IN ( :examId) "
						+ "           AND e.subject_id = :subjectId "
						+ "           AND e.student_id = :studentId "
						+ " AND srs.course_id = (SELECT cs.course_id "
						+ "  FROM          curriculum_scheme cs "
						+ "             JOIN "
						+ "                curriculum_scheme_duration AS csd "
						+ "             ON csd.curriculum_scheme_id = cs.id "
						+ "          JOIN "
						+ "             class_schemewise AS csw "
						+ "          ON csw.curriculum_scheme_duration_id = csd.id "
						+ "       JOIN "
						+ "          student AS stu "
						+ "       ON stu.class_schemewise_id = csw.id "
						+ " WHERE stu.id = :studentId) "
						+ "           AND srs.academic_year = ed.academic_year "
						+ " and srs.scheme_no = " + schemeNo + " and srs.is_active = 1"
						+ "           AND ed.internal_exam_type_id = si.internal_exam_type_id "
						+ "           AND si.is_theory_practical = 't' "
						+ "        GROUP BY e.exam_id, si.internal_exam_type_id "
						+ "        ORDER BY e.student_theory_internal_marks DESC LIMIT :limit "
						+ "	) AS a ";
			} else {
				HQL_QUERY = "SELECT sum(convert(a.marks, DECIMAL)), (sum(a.marks) / sum(a.maxmark)) * 100 "
						+ "  FROM (SELECT e.exam_id, "
						+ "               si.internal_exam_type_id, "
						+ "               round((e.student_theory_internal_marks / si.entered_max_mark) "
						+ "                   * si.maximum_mark) "
						+ "                  AS marks, "
						+ "               si.maximum_mark AS maxmark "
						+ "          FROM          EXAM_student_internal_final_mark_details e "
						+ "                     JOIN "
						+ "                        EXAM_subject_rule_settings srs "
						+ "                     ON srs.subject_id = e.subject_id "
						+ "                  JOIN "
						+ "                     EXAM_subject_rule_settings_sub_internal si "
						+ "                  ON srs.id = si.subject_rule_settings_id "
						+ "               JOIN "
						+ "                  EXAM_definition ed "
						+ "               ON e.exam_id = ed.id "
						+ "         WHERE e.exam_id IN ( :examId) "
						+ "           AND e.subject_id = :subjectId "
						+ "           AND e.student_id = :studentId "
						+ " AND srs.course_id = (SELECT cs.course_id "
						+ "  FROM          curriculum_scheme cs "
						+ "             JOIN "
						+ "                curriculum_scheme_duration AS csd "
						+ "             ON csd.curriculum_scheme_id = cs.id "
						+ "          JOIN "
						+ "             class_schemewise AS csw "
						+ "          ON csw.curriculum_scheme_duration_id = csd.id "
						+ "       JOIN "
						+ "          student AS stu "
						+ "       ON stu.class_schemewise_id = csw.id "
						+ " WHERE stu.id = :studentId) "
						+ "           AND srs.academic_year = ed.academic_year "
						+ " and srs.scheme_no = " + schemeNo + " and srs.is_active = 1 "						
						+ "           AND ed.internal_exam_type_id = si.internal_exam_type_id "
						+ "           AND si.is_theory_practical = 't' "
						+ "        GROUP BY e.exam_id, si.internal_exam_type_id "
						+ "        ORDER BY e.student_theory_internal_marks DESC) AS a ";
			}
		}
		Query query = session.createSQLQuery(HQL_QUERY);
		query.setParameter("studentId", studentId);
		query.setParameter("subjectId", subjectId);
		query.setParameterList("examId", examId);
		if (limit != null && limit != 0) {
			query.setParameter("limit", limit);
		}
		l = query.list();
		Double marks = 0.0;
		if (l != null && l.size() > 0) {
			if (l.get(0) != null) {
				Object[] obj = l.get(0);
				if (obj[0] != null) {
					marks = Double.parseDouble(obj[0].toString());
				}
			}
			if (l.get(0) != null) {
				Object[] obj = l.get(0);
				if (obj[1] != null) {
					subInternalTheoryPercentage.append(obj[1]);
				} else {
					subInternalTheoryPercentage.append(0);
				}
			}
		}
		return marks;

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

	// PRACTICAL MARKS
	public Double get_student_practical_marks(Integer studentId,
			Integer subjectId, ArrayList<Integer> examId, Integer limit,
			StringBuffer subInternalPracticalPercentage, int schemeNo) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		List<Object[]> l = null;
		String HQL_QUERY = "";
		if (checkTotalRun(subjectId, examId, getStudentCourseId(studentId), "p", schemeNo)) {

			if (limit != null && limit != 0) {
				HQL_QUERY = "SELECT round((sum(a.marks) * a.maxmark) / a.valuedmark), "
						+ "       (round((sum(a.marks) * a.maxmark) / a.valuedmark) / a.maxmark) "
						+ "     * 100 "
						+ "          AS prctng "
						+ "  FROM (SELECT e.exam_id, "
						+ "               max(e.student_practical_internal_marks) AS marks, "
						+ "               srs.practical_int_max_marks_total maxmark, "
						+ "               srs.practical_int_entry_max_marks_total valuedmark "
						+ "          FROM       EXAM_student_internal_final_mark_details e "
						+ "                  JOIN "
						+ "                     EXAM_subject_rule_settings srs "
						+ "                  ON srs.subject_id = e.subject_id "
						+ "               JOIN "
						+ "                  EXAM_definition ed "
						+ "               ON e.exam_id = ed.id "
						+ "         WHERE e.exam_id IN ( :examId) "
						+ "           AND e.subject_id = :subjectId "
						+ "           AND e.student_id = :studentId "
						+ "           AND srs.academic_year = ed.academic_year and srs.scheme_no = " + schemeNo + " and srs.is_active = 1 "
						+" and srs.course_id= (select selected_course_id from adm_appln where id in(select adm_appln_id from student where id=:studentId))"
						+ "        GROUP BY e.exam_id "
						+ "        ORDER BY e.student_theory_internal_marks DESC LIMIT :limit "
						+ "	) AS a ";
			} else {
				HQL_QUERY = "SELECT round((sum(a.marks) * a.maxmark) / a.valuedmark), "
						+ "       (round((sum(a.marks) * a.maxmark) / a.valuedmark) / a.maxmark) "
						+ "     * 100 "
						+ "          AS prctng "
						+ "  FROM (SELECT e.exam_id, "
						+ "               max(e.student_practical_internal_marks) AS marks, "
						+ "               srs.practical_int_max_marks_total maxmark, "
						+ "               srs.practical_int_entry_max_marks_total valuedmark "
						+ "          FROM       EXAM_student_internal_final_mark_details e "
						+ "                  JOIN "
						+ "                     EXAM_subject_rule_settings srs "
						+ "                  ON srs.subject_id = e.subject_id "
						+ "               JOIN "
						+ "                  EXAM_definition ed "
						+ "               ON e.exam_id = ed.id "
						+ "         WHERE e.exam_id IN ( :examId) "
						+ "           AND e.subject_id = :subjectId "
						+ "           AND e.student_id = :studentId "
						+ "           AND srs.academic_year = ed.academic_year and srs.scheme_no = " + schemeNo + " and srs.is_active = 1"
						+" and srs.course_id= (select selected_course_id from adm_appln where id in(select adm_appln_id from student where id=:studentId))"
						+ "        GROUP BY e.exam_id "
						+ "        ORDER BY e.student_theory_internal_marks DESC) AS a ";
			}

		} else {
			if (limit != null && limit != 0) {
				HQL_QUERY = "SELECT sum(a.marks), (sum(a.marks) / sum(a.maxmark)) * 100 "
						+ "  FROM (SELECT e.exam_id, "
						+ "               si.internal_exam_type_id, "
						+ "               round((e.student_practical_internal_marks / si.entered_max_mark) "
						+ "                   * si.maximum_mark) "
						+ "                  AS marks, "
						+ "               si.maximum_mark AS maxmark "
						+ "          FROM          EXAM_student_internal_final_mark_details e "
						+ "                     JOIN "
						+ "                        EXAM_subject_rule_settings srs "
						+ "                     ON srs.subject_id = e.subject_id "
						+ "                  JOIN "
						+ "                     EXAM_subject_rule_settings_sub_internal si "
						+ "                  ON srs.id = si.subject_rule_settings_id "
						+ "               JOIN "
						+ "                  EXAM_definition ed "
						+ "               ON e.exam_id = ed.id "
						+ "         WHERE e.exam_id IN ( :examId) "
						+ "           AND e.subject_id = :subjectId "
						+ "           AND e.student_id = :studentId "
						+ "           AND srs.academic_year = ed.academic_year and srs.scheme_no = " + schemeNo + " and srs.is_active = 1"
						+ " and srs.course_id= (select selected_course_id from adm_appln where id in(select adm_appln_id from student where id=:studentId))"
						+ "           AND ed.internal_exam_type_id = si.internal_exam_type_id "
						+ "           AND si.is_theory_practical = 'p' "
						+ "        GROUP BY e.exam_id, si.internal_exam_type_id "
						+ "        ORDER BY e.student_theory_internal_marks DESC LIMIT :limit "
						+ "	) AS a ";
			} else {
				HQL_QUERY = "SELECT sum(a.marks), (sum(a.marks) / sum(a.maxmark)) * 100 "
						+ "  FROM (SELECT e.exam_id, "
						+ "               si.internal_exam_type_id, "
						+ "               round((e.student_practical_internal_marks / si.entered_max_mark) "
						+ "                   * si.maximum_mark) "
						+ "                  AS marks, "
						+ "               si.maximum_mark AS maxmark "
						+ "          FROM          EXAM_student_internal_final_mark_details e "
						+ "                     JOIN "
						+ "                        EXAM_subject_rule_settings srs "
						+ "                     ON srs.subject_id = e.subject_id "
						+ "                  JOIN "
						+ "                     EXAM_subject_rule_settings_sub_internal si "
						+ "                  ON srs.id = si.subject_rule_settings_id "
						+ "               JOIN "
						+ "                  EXAM_definition ed "
						+ "               ON e.exam_id = ed.id "
						+ "         WHERE e.exam_id IN ( :examId) "
						+ "           AND e.subject_id = :subjectId "
						+ "           AND e.student_id = :studentId "
						+ "           AND srs.academic_year = ed.academic_year and srs.scheme_no = " + schemeNo + " and srs.is_active = 1 "
						+ " and srs.course_id= (select selected_course_id from adm_appln where id in(select adm_appln_id from student where id=:studentId))"
						+ "           AND ed.internal_exam_type_id = si.internal_exam_type_id "
						+ "           AND si.is_theory_practical = 'p' "
						+ "        GROUP BY e.exam_id, si.internal_exam_type_id "
						+ "        ORDER BY e.student_theory_internal_marks DESC) AS a ";
			}
		}
		Query query = session.createSQLQuery(HQL_QUERY);

		query.setParameter("studentId", studentId);
		query.setParameter("subjectId", subjectId);
		query.setParameterList("examId", examId);
		if (limit != null && limit != 0) {
			query.setParameter("limit", limit);
		}
		l = query.list();
		Double marks = 0.0;
		if (l != null && l.size() > 0) {
			if (l.get(0) != null) {
				Object[] obj = l.get(0);
				if (obj[0] != null) {
					marks = Double.parseDouble(obj[0].toString());
				}
			}
			if (l.get(0) != null) {
				Object[] obj = l.get(0);
				if (obj[1] != null) {
					subInternalPracticalPercentage.append(obj[1]);
				}
			}
		}
		return marks;

	}

	private boolean checkTotalRun(Integer subjectId, ArrayList<Integer> examId,
			Integer courseId, String type, int schemeNo) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		String HQL_QUERY = "";
		HQL_QUERY = "SELECT IFNULL(srs.theory_int_max_marks_total,0) tmaxmark,"
				+ " IFNULL(srs.practical_int_max_marks_total,0) pmaxmark "
				+ " FROM EXAM_subject_rule_settings AS srs "
				+ " WHERE srs.subject_id = :subjectId and srs.is_active = 1 and srs.scheme_no = " +
				schemeNo 
				+ " AND srs.academic_year = (SELECT distinct ed."
				+ " academic_year FROM EXAM_definition ed WHERE ed.id IN (:examId))"
				+ " and course_id = :courseId";

		Query query = session.createSQLQuery(HQL_QUERY);
		query.setParameter("subjectId", subjectId);
		query.setParameterList("examId", examId);
		query.setParameter("courseId", courseId);
		List list = query.list();
		Object[] row = null;
		if (type.equalsIgnoreCase("t")) {
			if (list != null && list.size() > 0) {
				if (list.get(0) != null) {
					row = (Object[]) list.get(0);
					if (row[0] != null && getIntValue(row[0]) > 0) {
						return true;
					}
				}
			}
			return false;
		} else {
			if (list != null && list.size() > 0) {
				if (list.get(0) != null) {
					row = (Object[]) list.get(0);
					if (row[1] != null && getIntValue(row[1]) > 0) {
						return true;
					}
				}
			}
			return false;
		}

	}

	private int getIntValue(Object object) {
		if (object != null) {
			BigDecimal val = new BigDecimal(object.toString());
			return val.intValue();
		}
		return 0;
	}

	// To get the value of 'best of' theory internal
	public Integer getBestOfTheoryInternal(Integer subId, Integer examId,
			Integer courseId, int schemeNo) {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		Integer bestOfTheory = null;
		String HQL_QUERY = "";

		HQL_QUERY = "select distinct srs.selectBestOfTheoryInternal"
				+ " from ExamSubjectRuleSettingsBO srs"
				+ " where srs.subjectId = :subId and srs.courseId =:courseId " +
						" and srs.schemeNo = " + schemeNo + " and srs.isActive = 1 "
				+ " and srs.academicYear IN (select ed.academicYear from ExamDefinitionBO ed where ed.id = :examId)";

		Query query = session.createQuery(HQL_QUERY);
		query.setParameter("subId", subId);
		query.setParameter("examId", examId);
		query.setParameter("courseId", courseId);

		List list = query.list();

		if (list != null && list.size() > 0) {
			if (list.get(0) != null)
				bestOfTheory = (Integer) list.get(0);

		}

		return bestOfTheory;

	}

	// To get the value of 'best of' practical internal
	public Integer getBestOfPracticalInternal(Integer subId, Integer examId,
			Integer courseId, int schemeNo) {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		Integer bestOfPractical = null;
		String HQL_QUERY = "";

		HQL_QUERY = "select distinct srs.selectBestOfPracticalInternal"
				+ " from ExamSubjectRuleSettingsBO srs "
				+ " where srs.subjectId = :subId and srs.courseId =:courseId and srs.isActive = 1 and srs.schemeNo = " +
				+ schemeNo 
				+ " and srs.academicYear IN (select ed.academicYear from ExamDefinitionBO ed where ed.id = :examId)";

		Query query = session.createQuery(HQL_QUERY);
		query.setParameter("subId", subId);
		query.setParameter("examId", examId);
		query.setParameter("courseId", courseId);
		List list = query.list();

		if (list != null && list.size() > 0) {
			if (list.get(0) != null)
				bestOfPractical = (Integer) list.get(0);

		}

		return bestOfPractical;
	}

	// Check Sub Internal option in subject rule settings for theory
	public boolean subInternalTheoryCheckSubjRuleSettings(Integer subjectId,
			Integer examId, Integer courseId, int schemeNo) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		List<Integer> list = null;
		String HQL_QUERY = "select distinct srs.finalTheoryInternalIsSubInternal"
				+ " from ExamSubjectRuleSettingsBO srs"
				+ " where srs.subjectId = :subjectId  and srs.courseId =:courseId  " +
				" and srs.isActive = 1 and srs.schemeNo = " + schemeNo
				+ " and srs.academicYear IN (select ed.academicYear from ExamDefinitionBO ed where ed.id = :examId)";

		Query query = session.createQuery(HQL_QUERY);
		query.setParameter("subjectId", subjectId);
		query.setParameter("examId", examId);
		query.setParameter("courseId", courseId);

		list = query.list();
		if (list.size() > 0 && list.get(0) != null && list.get(0) > 0) {
			return true;
		} else {
			return false;
		}
	}

	// Check Sub Internal option in subject rule settings for practical
	public boolean subInternalPracticalCheckSubjRuleSettings(Integer subjectId,
			Integer examId, Integer courseId, int schemeNo) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		List<Integer> list = null;
		String HQL_QUERY = "select distinct srs.finalPracticalInternalIsSubInternal"
				+ " from ExamSubjectRuleSettingsBO srs"
				+ " where srs.subjectId = :subjectId and srs.courseId =:courseId " +
						" and srs.isActive = 1 and srs.schemeNo = " + schemeNo
				+ " and srs.academicYear IN (select ed.academicYear from ExamDefinitionBO ed where ed.id = :examId)";

		Query query = session.createQuery(HQL_QUERY);
		query.setParameter("subjectId", subjectId);
		query.setParameter("examId", examId);
		query.setParameter("courseId", courseId);
		list = query.list();
		if (list.size() > 0 && list.get(0) != null && list.get(0) > 0) {
			return true;
		} else {
			return false;
		}
	}

	// Check Attendance option in subject rule settings for theory
	public boolean attendanceTheoryCheckSubjRuleSettings(Integer subjectId,
			Integer examId, Integer courseId, int schemeNo) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		List<Integer> list = null;
		String HQL_QUERY = "select distinct srs.finalTheoryInternalIsAttendance"
				+ " from ExamSubjectRuleSettingsBO srs"
				+ " where srs.subjectId = :subjectId  and srs.courseId =:courseId and srs.isActive = 1 and srs.schemeNo = " + schemeNo 
				+ " and srs.academicYear IN (select ed.academicYear from ExamDefinitionBO ed where ed.id = :examId)";

		Query query = session.createQuery(HQL_QUERY);
		query.setParameter("subjectId", subjectId);
		query.setParameter("examId", examId);
		query.setParameter("courseId", courseId);

		list = query.list();
		if (list.size() > 0 && list.get(0) != null && list.get(0) > 0) {
			return true;
		} else {
			return false;
		}
	}

	// Check Attendance option in subject rule settings for practical
	public boolean attendancePracticalCheckSubjRuleSettings(Integer subjectId,
			Integer examId, Integer courseId, int schemeNo) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		List<Integer> list = null;
		String HQL_QUERY = "select distinct srs.finalPracticalInternalIsAttendance"
				+ " from ExamSubjectRuleSettingsBO srs "
				+ " where srs.subjectId = :subjectId  and srs.courseId =:courseId and srs.isActive = 1 " +
				" and srs.schemeNo = " + schemeNo
				+ " and srs.academicYear IN (select ed.academicYear from ExamDefinitionBO ed where ed.id = :examId)";

		Query query = session.createQuery(HQL_QUERY);
		query.setParameter("subjectId", subjectId);
		query.setParameter("examId", examId);
		query.setParameter("courseId", courseId);

		list = query.list();
		if (list.size() > 0 && list.get(0) != null && list.get(0) > 0) {
			return true;
		} else {
			return false;
		}
	}

	// Check Leave option in subject rule settings attendance
	public boolean leaveCheckSubjRuleSettings(Integer subjectId,
			Integer examId, Integer courseId, int schemeNo) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		List<Integer> list = null;
		String HQL_QUERY = "select esra.isLeave"
				+ " from ExamSubjectRuleSettingsAttendanceBO esra"
				+ " join esra.examSubjectRuleSettingsBO srs"
				+ " where srs.subjectId = :subjectId and srs.courseId =:courseId and srs.isActive = 1 and srs.schemeNo =" + schemeNo
				+ " and srs.academicYear IN (select ed.academicYear from ExamDefinitionBO ed where ed.id = :examId)";

		Query query = session.createQuery(HQL_QUERY);
		query.setParameter("subjectId", subjectId);
		query.setParameter("examId", examId);
		query.setParameter("courseId", courseId);
		list = query.list();
		if (list.size() > 0 && list.get(0) != null && list.get(0) > 0) {
			return true;
		} else {
			return false;
		}
	}

	// Check CoCurricular option in subject rule settings attendance
	public boolean coCurricularCheckSubjRuleSettings(Integer subjectId,
			Integer examId, Integer courseId, int schemeNo) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		List<Integer> list = null;
		String HQL_QUERY = "select esra.isCoCurricular"
				+ " from ExamSubjectRuleSettingsAttendanceBO esra"
				+ " join esra.examSubjectRuleSettingsBO srs"
				+ " where srs.subjectId = :subjectId  and srs.courseId =:courseId and srs.isActive = 1 and " +
				" srs.schemeNo = " + schemeNo
				+ " and srs.academicYear IN (select ed.academicYear from ExamDefinitionBO ed where ed.id = :examId)";

		Query query = session.createQuery(HQL_QUERY);
		query.setParameter("subjectId", subjectId);
		query.setParameter("examId", examId);
		query.setParameter("courseId", courseId);
		list = query.list();
		if (list.size() > 0 && list.get(0) != null && list.get(0) > 0) {
			return true;
		} else {
			return false;
		}
	}

	// Check Assignment option in subject rule settings for theory
	public boolean assignmentTheoryCheckSubjRuleSettings(Integer subjectId,
			Integer examId, Integer courseId, int schemeNo) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		List<Integer> list = null;
		String HQL_QUERY = "select srs.finalTheoryInternalIsAssignment"
				+ " from ExamSubjectRuleSettingsBO srs"
				+ " where srs.subjectId = :subjectId  and srs.courseId =:courseId and srs.isActive = 1"
				+ " and srs.schemeNo = " + schemeNo
				+ "  and srs.academicYear IN (select ed.academicYear from ExamDefinitionBO ed where ed.id = :examId)";

		Query query = session.createQuery(HQL_QUERY);
		query.setParameter("subjectId", subjectId);
		query.setParameter("examId", examId);
		query.setParameter("courseId", courseId);

		list = query.list();
		if (list.size() > 0 && list.get(0) != null && list.get(0) > 0) {
			return true;
		} else {
			return false;
		}
	}

	// Check Assignment option in subject rule settings for practical
	public boolean assignmentPracticalCheckSubjRuleSettings(Integer subjectId,
			Integer examId, Integer courseId, int schemeNo) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		List<Integer> list = null;
		String HQL_QUERY = "select srs.finalPracticalInternalIsAssignment"
				+ " from ExamSubjectRuleSettingsBO srs"
				+ " where srs.subjectId = :subjectId and srs.courseId =:courseId and srs.isActive = 1 and " +
						" srs.schemeNo =  " + schemeNo
				+ " and srs.academicYear IN (select ed.academicYear from ExamDefinitionBO ed where ed.id = :examId)";

		Query query = session.createQuery(HQL_QUERY);
		query.setParameter("subjectId", subjectId);
		query.setParameter("examId", examId);
		query.setParameter("courseId", courseId);

		list = query.list();
		if (list.size() > 0 && list.get(0) != null && list.get(0) > 0) {
			return true;
		} else {
			return false;
		}
	}

	// To fetch the minimum, maximum, entered total theory internal marks for a
	// subject
	public Integer getTotalMinMaxEnteredIntTheoryMarks(Integer subjectId,
			Integer examId, Integer courseId, int schemeNo) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		List l = null;
		String HQL_QUERY = "select srs.theoryIntMinMarksTotal"
				+ " from ExamSubjectRuleSettingsBO srs"
				+ " where srs.subjectId = :subjectId  and srs.courseId =:courseId " +
						" and srs.schemeNo = " + schemeNo + " and srs.isActive = 1"
				+ " and srs.academicYear IN (select ed.academicYear from ExamDefinitionBO ed where ed.id = :examId)";

		Query query = session.createQuery(HQL_QUERY);
		query.setParameter("subjectId", subjectId);
		query.setParameter("examId", examId);
		query.setParameter("courseId", courseId);

		l = query.list();
		Integer theoryMarks = 0;
		if (l != null) {
			if (l.size() > 0 && l.get(0) != null) {
				BigDecimal dValue = (BigDecimal) l.get(0);
				if (dValue != null)
					theoryMarks = dValue.intValue();
			}
		}
		return theoryMarks;
	}

	// To fetch the minimum, maximum, entered total practical internal marks for
	// a subject
	public Integer getTotalMinMaxEnteredIntPracticalMarks(Integer subjectId,
			Integer examId, Integer courseId, int schemeNo) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		List l = null;
		String HQL_QUERY = "select srs.practicalIntMinMarksTotal"
				+ " from ExamSubjectRuleSettingsBO srs"
				+ " where srs.subjectId = :subjectId  and srs.courseId =:courseId " +
						" and srs.schemeNo = " + schemeNo + " and srs.isActive = 1 "
				+ " and srs.academicYear IN (select ed.academicYear from ExamDefinitionBO ed where ed.id = :examId)";
		Query query = session.createQuery(HQL_QUERY);
		query.setParameter("subjectId", subjectId);
		query.setParameter("examId", examId);
		query.setParameter("courseId", courseId);

		l = query.list();
		Integer pracMarks = 0;
		if (l != null) {
			if (l.size() > 0 && l.get(0) != null) {
				BigDecimal dValue = (BigDecimal) l.get(0);
				if (dValue != null)
					pracMarks = dValue.intValue();
			}
		}
		return pracMarks;
	}

	// ==========================================================================
	// =========================================================================
	// ================= PROCESS - SUPPLEMENTARY DATA CREATION ===============

	public Integer getSchemeNoForSubject(Integer stuId) {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		String HQL_QUERY = "";
		Integer schemeNo = null;
		HQL_QUERY = "SELECT curriculum_scheme_duration.semester_year_no"
				+ " FROM student s" + " JOIN class_schemewise"
				+ "  ON s.class_schemewise_id = class_schemewise.id"
				+ " JOIN curriculum_scheme_duration"
				+ "  ON class_schemewise.curriculum_scheme_duration_id ="
				+ "  curriculum_scheme_duration.id" + " WHERE s.id = :stuId"
				+ " UNION" + " SELECT p.scheme_no"
				+ " FROM EXAM_student_previous_class_details p"
				+ " WHERE p.student_id = :stuId";

		Query query = session.createSQLQuery(HQL_QUERY);
		query.setParameter("stuId", stuId);
		List list = query.list();
		if (list != null && list.size() > 0) {
			schemeNo = (Integer) list.get(0);

		}

		return schemeNo;
	}

	// To fetch the students for supplementary data creation process
	public ArrayList<Integer> getStudentList(Integer classId,
			Integer academicYear, String isPreviousExam) {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		String HQL_QUERY = "";
		if (isPreviousExam.equalsIgnoreCase("true")) {
			
			
			HQL_QUERY = " select s.student_id"
					+ " from EXAM_student_previous_class_details s "
					+ " left join student stu on " + " s.student_id = stu.id "
					+ " left join adm_appln on "
					+ " adm_appln.id = stu.adm_appln_id " 
					+ " left join class_schemewise ON stu.class_schemewise_id = class_schemewise.id "
					+ " left join curriculum_scheme_duration ON class_schemewise.curriculum_scheme_duration_id = curriculum_scheme_duration.id"
					+ " where "
					+ " s.class_id = :classId" 
					+ " and adm_appln.is_cancelled = 0 " +
					" and curriculum_scheme_duration.academic_year >= :academicYear";
			
		} else {
			HQL_QUERY = "SELECT s.id  FROM student s" +
						" JOIN class_schemewise ON s.class_schemewise_id = class_schemewise.id " +
						" left join curriculum_scheme_duration cd on class_schemewise.curriculum_scheme_duration_id = cd.id " +
						" left join adm_appln ON s.adm_appln_id = adm_appln.id " +
						" where cd.academic_year >= :academicYear and " +
						" adm_appln.is_cancelled = 0 and class_schemewise.class_id = :classId";
			
		}
		
		Query query = session.createSQLQuery(HQL_QUERY);
		query.setParameter("academicYear", academicYear);
		query.setParameter("classId", classId);

		List list = (ArrayList<Integer>) query.list();

		return (ArrayList<Integer>) list;
	}

	// To get subjects for a class
	public ArrayList<Integer> get_subjects_forClass(Integer classId) {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		String HQL_QUERY = "";

		HQL_QUERY = "SELECT DISTINCT s.id" + " FROM class_schemewise cs"
				+ " JOIN curriculum_scheme_subject css"
				+ " ON cs.curriculum_scheme_duration_id ="
				+ " css.curriculum_scheme_duration_id"
				+ " JOIN curriculum_scheme_duration csd"
				+ " ON cs.curriculum_scheme_duration_id = csd.id"
				+ " JOIN curriculum_scheme c"
				+ " ON csd.curriculum_scheme_id = c.id"
				+ " JOIN subject_group_subjects sgs"
				+ " ON css.subject_group_id = sgs.subject_group_id"
				+ " JOIN subject s" + " ON sgs.subject_id = s.id"
				+ " WHERE cs.class_id = :classId";

		Query query = session.createSQLQuery(HQL_QUERY);
		query.setParameter("classId", classId);
		List list = (ArrayList<Integer>) query.list();
		if (list != null && list.size() > 0) {
			return (ArrayList<Integer>) list;
		}
		return new ArrayList<Integer>();
	}

	// To get max allowed failed subjects for special supplementary exam
	public Integer getMaxAllowedFailedSubject(Integer examId) {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		Integer maxAllowedFailedSubjects = null;
		String HQL_QUERY = "";

		HQL_QUERY = "select ed.maxNoFailedSub" + " from ExamDefinitionBO ed"
				+ " where ed.id = :examId";

		Query query = session.createQuery(HQL_QUERY);
		query.setParameter("examId", examId);
		List list = query.list();

		if (list != null && list.size() > 0) {
			maxAllowedFailedSubjects = (Integer) list.get(0);

		}

		return maxAllowedFailedSubjects;
	}

	// To get course of a student
	public Integer getCourseOfStudent(Integer stuId,boolean byClassId) {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		Integer courseId = null;
		String HQL_QUERY = "";
		if(byClassId){
			HQL_QUERY ="select c.course.id from Classes c where c.id=:stuId";
		}else{
			HQL_QUERY = "select s.classSchemewiseUtilBO.curriculumSchemeDurationUtilBO.curriculumSchemeUtilBO.courseId"
				+ " from StudentUtilBO s" + " where s.id = :stuId";
		}
		Query query = session.createQuery(HQL_QUERY);
		query.setParameter("stuId", stuId);
		List list = query.list();

		if (list != null && list.size() > 0) {
			courseId = (Integer) list.get(0);

		}

		return courseId;
	}

	// To get the chance of the student for a particular subject
	public List<Object[]> getChanceOfStudent(Integer stuId,
			ArrayList<Integer> subjectList) {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		List<Object[]> l = null;
		if(!subjectList.isEmpty()){
		String HQL_QUERY = "select s.subjectId, s.chance"
				+ " from ExamSupplementaryImprovementApplicationBO s"
				+ " where s.studentId = :stuId and s.subjectId in (:subjectList)";

		Query query = session.createQuery(HQL_QUERY);
		query.setParameter("stuId", stuId);
		query.setParameterList("subjectList", subjectList);

		l = (List<Object[]>) query.list();
		}
		if (l != null && l.size() > 0) {
			return l;
		} else {
			return new ArrayList<Object[]>();
		}
	}

	// Student's internal marks
	public List<Object[]> getInternalMarksOfStudent(Integer internalExamId,
			Integer stuId, ArrayList<Integer> subjectList) {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		List<Object[]> l = null;
		if (subjectList != null && subjectList.size() > 0) {
			String HQL_QUERY = "select e.subjectId, e.theoryMarks, e.practicalMarks"
					+ " from ExamMarksEntryDetailsBO e"
					+ " join e.examMarksEntryBO m"
					+ " where m.studentId = :stuId and e.subjectId in (:subjectList) "
					+ " and m.examId = :internalExamId ";

			Query query = session.createQuery(HQL_QUERY);
			query.setParameter("stuId", stuId);
			query.setParameter("internalExamId", internalExamId);
			query.setParameterList("subjectList", subjectList);

			l = (List<Object[]>) query.list();
		}
		if (l != null && l.size() > 0) {
			return l;
		} else {
			return new ArrayList<Object[]>();
		}
	}

	// Fetch total practical assignment marks against exam & subject for a
	// student
	public String practicalTotalAssignmentMarks(Integer stuId, Integer subId) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		String practicalMarks = null;

		String HQL_QUERY = "select sum(a.practicalMarks)"
				+ " from ExamAssignOverallMarksBO a"
				+ " where a.subjectId = :subId and a.studentId = :stuId"
				+ " and a.overallAssignmentName like '%Assig%' group by a.studentId";

		Query query = session.createQuery(HQL_QUERY);
		query.setParameter("subId", subId);
		query.setParameter("stuId", stuId);
		List list = query.list();

		if (list != null && list.size() > 0) {
			if (list.get(0) != null)
				practicalMarks = list.get(0).toString();
		}
		return practicalMarks;
	}

	// Fetch total theory assignment marks against exam & subject for a student
	public String theoryTotalAssignmentMarks(Integer stuId, Integer subId) {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		String theoryMarks = null;

		String HQL_QUERY = "select sum(a.theoryMarks)"
				+ " from ExamAssignOverallMarksBO a"
				+ " where a.subjectId = :subId and a.studentId = :stuId"
				+ " and a.overallAssignmentName like '%Assig%' group by a.studentId";

		Query query = session.createQuery(HQL_QUERY);
		query.setParameter("subId", subId);
		query.setParameter("stuId", stuId);

		List list = query.list();

		if (list != null && list.size() > 0) {
			if (list.get(0) != null)
				theoryMarks = list.get(0).toString();
		}
		return theoryMarks;
	}

	// To get the classes & joining batch for a particular exam
	public List<Object[]> getClassJoiningBatch(Integer examId) {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		List<Object[]> l = null;
		String HQL_QUERY = " select css.classUtilBO.id, css.classUtilBO.name, css.curriculumSchemeDurationUtilBO.academicYear ||'-'|| css.curriculumSchemeDurationUtilBO.academicYear+1"
				+ " from ClassSchemewiseUtilBO css"
				+ " where (css.curriculumSchemeDurationUtilBO.curriculumSchemeUtilBO.courseId,"
				+ " css.curriculumSchemeDurationUtilBO.curriculumSchemeUtilBO.courseSchemeId,"
				+ " css.curriculumSchemeDurationUtilBO.semesterYearNo) in"
				+ " (select eec.courseId, eec.courseSchemeId, eec.schemeNo from ExamExamCourseSchemeDetailsBO eec"
				+ " where eec.examId = :examId) and css.curriculumSchemeDurationUtilBO.academicYear >="
				+ " (select ed.examForJoiningBatch from ExamDefinitionBO ed where ed.id = :examId)";

		Query query = session.createQuery(HQL_QUERY);
		query.setParameter("examId", examId);

		l = (List<Object[]>) query.list();

		if (l != null && l.size() > 0) {
			return l;
		} else {
			return new ArrayList<Object[]>();
		}
	}

	// To check whether the student has passed or failed in internal
	public boolean getPassOrFailInternal(Integer studentId, Integer subjectIds) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		List<String> list = null;
		String HQL_QUERY = "select e.passOrFail"
				+ " from ExamStudentOverallInternalMarkDetailsBO e"
				+ " where e.studentId = :studentId and e.subjectId = :subjectIds";

		Query query = session.createQuery(HQL_QUERY);
		query.setParameter("studentId", studentId);
		query.setParameter("subjectIds", subjectIds);
		list = query.list();
		if (list.size() > 0) {
			if (list.get(0) != null && list.get(0).equalsIgnoreCase("pass")) {
				return true;
			} else {
				return false;
			}
		}
		return true;
	}

	// To check whether the student has passed or failed in regular
	public boolean getPassOrFailRegular(Integer studentId, Integer subjectIds) {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		List<String> list = null;
		String HQL_QUERY = "select e.passOrFail"
				+ " from ExamStudentFinalMarkDetailsBO e"
				+ " where e.studentId = :studentId and e.subjectId = :subjectIds";

		Query query = session.createQuery(HQL_QUERY);
		query.setParameter("studentId", studentId);
		query.setParameter("subjectIds", subjectIds);
		list = query.list();
		if (list.size() > 0) {
			if (list.get(0) != null && list.get(0).equalsIgnoreCase("pass")) {
				return true;
			} else {
				return false;
			}
		}
		return true;
	}

	// To get the pass/fail status of theory/practical of a subject
	public List<Object[]> getFailStatusTheoryPrac(
			ArrayList<Integer> subjectIds, Integer studentId) {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		List<Object[]> l = null;
		String HQL_QUERY = "select e.subjectId, e.studentTheoryMarks, e.subjectTheoryMark,"
				+ " e.studentPracticalMarks, e.subjectPracticalMark"
				+ " from ExamStudentFinalMarkDetailsBO e"
				+ " where e.subjectId in (:subjectIds) and e.studentId = :studentId";

		Query query = session.createQuery(HQL_QUERY);
		query.setParameterList("subjectIds", subjectIds);
		query.setParameter("studentId", studentId);

		l = (List<Object[]>) query.list();

		if (l != null && l.size() > 0) {
			return l;
		} else {
			return new ArrayList<Object[]>();
		}
	}

	// To check whether the exam type is special supplementary
	public boolean getExamType(Integer examId) {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		List<String> list = null;
		String HQL_QUERY = "select ed.examTypeUtilBO.name"
				+ " from ExamDefinitionBO ed" + " where ed.id = :examId";

		Query query = session.createQuery(HQL_QUERY);
		query.setParameter("examId", examId);
		list = query.list();
		if (list.size() > 0
				&& list.get(0).equalsIgnoreCase("Special Supplementary")) {
			return true;
		} else {
			return false;
		}
	}

	// To get the getAggregate Pass Percentage from Course Settings
	public BigDecimal getAggregatePassPercentage(Integer courseId) {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		BigDecimal aggregatePassPercentage = null;

		String HQL_QUERY = "select e.aggregatePass"
				+ " from ExamSettingCourseBO e"
				+ " where e.courseId = :courseId";

		Query query = session.createQuery(HQL_QUERY);
		query.setParameter("courseId", courseId);

		List list = query.list();

		if (list != null && list.size() > 0) {

			aggregatePassPercentage = (BigDecimal) list.get(0);
		}
		return aggregatePassPercentage;
	}

	public String get_class_section_name(Integer clsId) {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		String section = null;
		String HQL_QUERY = "";

		HQL_QUERY = "select cs.sectionName " + " from ClassUtilBO cs "
				+ " where cs.id = :clsId ";

		Query query = session.createQuery(HQL_QUERY);
		query.setParameter("clsId", clsId);

		List list = query.list();

		if (list != null && list.size() > 0) {
			section = (String) list.get(0);

		}

		return section;

	}

	// Check whether the subject is present in subject definition courseWise
	public boolean checkSubjectCourseWise(Integer subId) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		List<Integer> list = null;
		String HQL_QUERY = "select e.id"
				+ " from ExamSubCoursewiseAttendanceMarksBO e"
				+ " where e.subjectId = :subId";

		Query query = session.createQuery(HQL_QUERY);
		query.setParameter("subId", subId);
		list = query.list();
		if (list.size() > 0 && list.get(0) != null && list.get(0) > 0) {
			return true;
		} else {
			return false;
		}
	}

	// To get Attendance Marks for a student
	public int getSubjectDefinitionMarks(float totalCalculation, Integer subId,
			Integer courseId) {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		int marks = 0;
		String HQL_QUERY = "";

		if (checkSubjectCourseWise(subId)) {
			HQL_QUERY = "select e.attendanceMarks"
					+ " from ExamSubCoursewiseAttendanceMarksBO e"
					+ " where e.subjectId = :subId"
					+ " and e.fromPrcntgAttndnc <= :totalCalculation"
					+ " and e.toPrcntgAttndnc >= :totalCalculation";
		} else {
			HQL_QUERY = "select a.marks"
					+ " from ExamAttendanceMarksBO a"
					+ " where a.courseId = :courseId and a.fromPercentage <= :totalCalculation"
					+ " and a.toPercentage >= :totalCalculation";
		}
		Query query = session.createQuery(HQL_QUERY);
		query
				.setParameter("totalCalculation", new BigDecimal(
						totalCalculation));
		if (subId != null && subId > 0 && (checkSubjectCourseWise(subId))) {
			query.setParameter("subId", subId);
		}
		if (courseId != null && courseId > 0
				&& !(checkSubjectCourseWise(subId))) {
			query.setParameter("courseId", courseId);
		}
		List list = query.list();

		if (list != null && list.size() > 0) {
			marks = new Double(list.get(0).toString()).intValue();

		}

		return marks;

	}

	// To fetch the sub internal marks for a particular range
	public Integer getSubInternalMarks(StringBuffer buffer, Integer courseId) {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		BigDecimal totalCalculation = null;
		if (buffer != null && buffer.length() > 0) {
			totalCalculation = new BigDecimal(buffer.toString());
		} else {
			totalCalculation = new BigDecimal(0.0);
		}
		Session session = sessionFactory.openSession();

		Integer marks = null;
		String HQL_QUERY = "";

		HQL_QUERY = "select i.marks"
				+ " from ExamInternalCalculationMarksBO i"
				+ " where i.courseId = :courseId and i.startPercentage <= :totalCalculation"
				+ " and i.endPercentage >= :totalCalculation and i.isActive = 1";

		Query query = session.createQuery(HQL_QUERY);
		query.setParameter("totalCalculation", totalCalculation);
		query.setParameter("courseId", courseId);

		List list = query.list();

		if (list != null && list.size() > 0) {
			marks = new Double(list.get(0).toString()).intValue();

		}

		return marks;

	}

	// Get theory attendance type
	public Integer getTheoryAttendenceTypeId(Integer subId, String subjectType,
			Integer examId, Integer courseId, int schemeNo) {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
 
		Integer attendanceTypeId = null;
		String HQL_QUERY = "";

		HQL_QUERY = "select distinct a.attendanceTypeId"
				+ " from ExamSubjectRuleSettingsAttendanceBO a"
				+ " join a.examSubjectRuleSettingsBO s"
				+ " where s.subjectId = :subId and a.isTheoryPractical = :subjectType and s.courseId =:courseId  "
				+ " and s.schemeNo = " + schemeNo + " and s.isActive = 1 "
				+ " and s.academicYear IN (select ed.academicYear from ExamDefinitionBO ed "
				+ " where ed.id = :examId)  ";

		Query query = session.createQuery(HQL_QUERY);
		query.setParameter("subId", subId);
		query.setParameter("subjectType", subjectType);
		query.setParameter("examId", examId);
		query.setParameter("courseId", courseId);

		List list = query.list();

		if (list != null && list.size() > 0) {
			attendanceTypeId = (Integer) list.get(0);

		}

		return attendanceTypeId;
	}

	// Get student's theory marks for each subject
	public Integer getStudentsTheoryMarkForSubj(Integer subjectId,
			Integer studentId, boolean isHeighest) {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		Integer marks = null;
		String HQL_QUERY = "";
		Query query = null;
		if(isHeighest){
			/*HQL_QUERY = "select max(e.studentTheoryMarks)"
					+ " from ExamStudentFinalMarkDetailsBO e"
					+ " where e.studentId = :studentId and e.subjectId = :subjectId";*/
			HQL_QUERY = "select  max( (case when (e.student_theory_marks     " +
					" = ( select absent_code_mark_entry from EXAM_exam_settings)) OR " +
					" (e.student_theory_marks  = (select not_proced_code_mark_entry from  EXAM_exam_settings)) " +
					" then 0  else convert(e.student_theory_marks,decimal)  end))"
					+ " from EXAM_student_final_mark_details e"
					+ " where e.student_id = :studentId and e.subject_id = :subjectId";
			 query = session.createSQLQuery(HQL_QUERY);
		}
		else{
			HQL_QUERY = " select e.studentTheoryMarks from ExamStudentFinalMarkDetailsBO e   " +
			" where e.studentId = :studentId and e.subjectId =:subjectId " +
			" group by e.studentId  order by e.examDefinitionBO.academicYear, e.examDefinitionBO.month desc limit 1 ";
			 query = session.createQuery(HQL_QUERY);
		}

		
		query.setParameter("subjectId", subjectId);
		query.setParameter("studentId", studentId);
		List list = query.list();

		if (list != null && list.size() > 0 && list.get(0) != null) {
			String str = list.get(0).toString();
			if (str != null && str.trim().length() > 0 && !StringUtils.isAlpha(str))
				marks = new Double(str).intValue();

		}

		return marks;
	}

	// Get student's practical marks for each subject
	public Integer getStudentsPracticalMarkForSubj(Integer subjectId,
			Integer studentId, boolean isHeighestMark) {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		Integer marks = null;
		String HQL_QUERY = "";
		Query query = null;
		if(isHeighestMark){
			HQL_QUERY = "select  max( (case when (e.student_practical_marks     " +
				" = ( select absent_code_mark_entry from EXAM_exam_settings)) OR " +
				" (e.student_practical_marks  = (select not_proced_code_mark_entry from  EXAM_exam_settings)) " +
				" then 0  else convert(e.student_practical_marks,decimal)  end))"
				+ " from EXAM_student_final_mark_details e"
				+ " where e.student_id = :studentId and e.subject_id = :subjectId";
			
			/*HQL_QUERY = "select max(e.studentPracticalMarks)"
					+ " from ExamStudentFinalMarkDetailsBO e"
					+ " where e.studentId = :studentId and e.subjectId = :subjectId";*/
			query = session.createSQLQuery(HQL_QUERY);
		}else{
			HQL_QUERY = "select e.studentPracticalMarks"
				+ " from ExamStudentFinalMarkDetailsBO e"
				+ " where e.studentId = :studentId and e.subjectId =:subjectId "
				+ " group by e.studentId  order by e.examDefinitionBO.academicYear, e.examDefinitionBO.month desc limit 1 ";
			query = session.createQuery(HQL_QUERY);
		}


		query.setParameter("subjectId", subjectId);
		query.setParameter("studentId", studentId);
		List list = query.list();

		if (list != null && list.size() > 0 && list.get(0) != null) {
			String str = list.get(0).toString();
			if (str != null && str.trim().length() > 0 && !StringUtils.isAlpha(str))
				marks = new Double(str).intValue();

		}

		return marks;
	}

	// Get student's internal marks for each subject
	public List<Object[]> getStudentsInternalMarkForSubj(Integer subjectId, Integer studentId, boolean isHeighestMark) {
		List<Object[]> list = getStudentsInternalMarkForSubjFromSupInt(subjectId, studentId,isHeighestMark);
		if(list!= null && list.size() > 0){
			return list;
		}
		
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		List<Object[]> l = null;
		String HQL_QUERY = "";
		if(isHeighestMark){
			 HQL_QUERY = "select e.theoryTotalSubInternalMarks, e.theoryTotalAssignmentMarks, e.theoryTotalAttendenceMarks,"
					+ " e.practicalTotalSubInternalMarks, e.practicalTotalAssignmentMarks, e.practicalTotalAttendenceMarks"
					+ " from ExamStudentOverallInternalMarkDetailsBO e"
					+ " where e.studentId = :studentId and e.subjectId = :subjectId";
		}
		else{
			 HQL_QUERY = "select e.theoryTotalSubInternalMarks, e.theoryTotalAssignmentMarks, e.theoryTotalAttendenceMarks,"
				+ " e.practicalTotalSubInternalMarks, e.practicalTotalAssignmentMarks, e.practicalTotalAttendenceMarks"
				+ " from ExamStudentOverallInternalMarkDetailsBO e"
				+ " where e.studentId = :studentId and e.subjectId = :subjectId " +
				" group by e.studentId  order by e.examDefinitionBO.academicYear, e.examDefinitionBO.month  desc limit 1";
		}

		Query query = session.createQuery(HQL_QUERY);
		query.setParameter("subjectId", subjectId);
		query.setParameter("studentId", studentId);
		l = (List<Object[]>) query.list();

		if (l != null && l.size() > 0) {
			return l;
		} else {
			return new ArrayList<Object[]>();
		}
	}
	
	// Get student's internal marks for each subject
	public List<Object[]> getStudentsInternalMarkForSubjFromSupInt(Integer subjectId, Integer studentId, boolean isHeighestMark) {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		List<Object[]> l = null;
		String HQL_QUERY = "";
		if(isHeighestMark){
			 HQL_QUERY = "select e.theoryTotalSubInternalMarks, e.theoryTotalAssignmentMarks, e.theoryTotalAttendenceMarks,"
					+ " e.practicalTotalSubInternalMarks, e.practicalTotalAssignmentMarks, e.practicalTotalAttendenceMarks"
					+ " from ExamInternalMarkSupplementaryDetailsBO e"
					+ " where e.student.id = :studentId and e.subject.id = :subjectId";
		}
		else{
			 HQL_QUERY = "select e.theoryTotalSubInternalMarks, e.theoryTotalAssignmentMarks, e.theoryTotalAttendenceMarks,"
				+ " e.practicalTotalSubInternalMarks, e.practicalTotalAssignmentMarks, e.practicalTotalAttendenceMarks"
				+ " from ExamInternalMarkSupplementaryDetailsBO e"
				+ " where e.student.id = :studentId and e.subject.id = :subjectId " +
				" group by e.subject.id  order by e.examDefinitionBO.academicYear, e.examDefinitionBO.month  desc limit 1";
		}

		Query query = session.createQuery(HQL_QUERY);
		query.setParameter("subjectId", subjectId);
		query.setParameter("studentId", studentId);
		l = (List<Object[]>) query.list();

		if (l != null && l.size() > 0) {
			return l;
		} else {
			return new ArrayList<Object[]>();
		}
	}

	// Get sum of all subjects from subject rule settings to calculate aggregate
	public Double getAggregateOfSubjects(ArrayList<Integer> subjectList,
			Integer academicYear, Integer courseId, int schemeNo) {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		List l = null;
		
		String HQL_QUERY = " SELECT subject_id, theory_ese_theory_final_maximum_mark, practical_ese_theory_final_maximum_mark from "+
					        " EXAM_subject_rule_settings where subject_id in " +
					        "(:subjectList) and course_id = :courseId and academic_year = :academicYear" +
					        " and scheme_no = " + schemeNo + " and is_active = 1" +
					        " group by subject_id ";
				 
		Query query = session.createSQLQuery(HQL_QUERY);
		query.setParameter("academicYear", academicYear);
		query.setParameterList("subjectList", subjectList);
		query.setParameter("courseId", courseId);

		l = query.list();
		Double sum = 0.0;
		if (l != null && l.size() > 0) {
			Iterator<Object[]> itr = l.iterator();
			while (itr.hasNext()) {
				Object[] row = (Object[]) itr.next();
				if (row[1] != null) {
					sum = sum + new Double(row[1].toString());
				}
				if (row[2] != null) {
					sum = sum + new Double( row[2].toString());
				}
			}
			

		}
		return sum;
	}

	// To fetch the details
	public List<Object[]> getDetailsSubjectRuleSettings(
			ArrayList<Integer> subjectList, Integer academicYear) {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		List<Object[]> l = null;
		String HQL_QUERY = "SELECT s.subject_id,"
				+ " s.subject_final_is_theory_exam,"
				+ " s.subject_final_is_practical_exam,"
				+ " s.subject_final_is_internal_exam"
				+ " FROM EXAM_subject_rule_settings s"
				+ "  WHERE s.subject_id IN (:subjectList) AND s.academic_year = :academicYear";

		Query query = session.createSQLQuery(HQL_QUERY);
		query.setParameter("academicYear", academicYear);
		query.setParameterList("subjectList", subjectList);

		l = (List<Object[]>) query.list();

		if (l != null && l.size() > 0) {
			return l;
		} else {
			return new ArrayList<Object[]>();
		}
	}

	// Theory Final Check
	public boolean getFinalTheoryCheck(Integer subjectId, Integer academicYear,
			Integer courseId) {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		List<Integer> list = null;
		String HQL_QUERY = "select e.subjectFinalIsTheoryExam"
				+ " from ExamSubjectRuleSettingsBO e"
				+ " where e.subjectId = :subjectId and e.academicYear = :academicYear and e.courseId = :courseId";

		Query query = session.createQuery(HQL_QUERY);
		query.setParameter("subjectId", subjectId);
		query.setParameter("academicYear", academicYear);
		query.setParameter("courseId", courseId);
		list = query.list();
		if (list.size() > 0 && list.get(0) != null && list.get(0) > 0) {
			return true;
		} else {
			return false;
		}
	}

	// Practical Final Check
	public boolean getFinalPracticalCheck(Integer subjectId,
			Integer academicYear, Integer courseId) {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		List<Integer> list = null;
		String HQL_QUERY = "select e.subjectFinalIsPracticalExam"
				+ " from ExamSubjectRuleSettingsBO e"
				+ " where e.subjectId = :subjectId and e.academicYear = :academicYear and e.courseId = :courseId";

		Query query = session.createQuery(HQL_QUERY);
		query.setParameter("subjectId", subjectId);
		query.setParameter("academicYear", academicYear);
		query.setParameter("courseId", courseId);
		list = query.list();
		if (list.size() > 0 && list.get(0) != null && list.get(0) > 0) {
			return true;
		} else {
			return false;
		}
	}

	// Final Internal Check
	public boolean getFinalInternalCheck(Integer subjectId,
			Integer academicYear, Integer courseId) {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		List<Integer> list = null;
		String HQL_QUERY = "select e.subjectFinalIsInternalExam"
				+ " from ExamSubjectRuleSettingsBO e"
				+ " where e.subjectId = :subjectId and e.academicYear = :academicYear and e.courseId = :courseId";

		Query query = session.createQuery(HQL_QUERY);
		query.setParameter("subjectId", subjectId);
		query.setParameter("academicYear", academicYear);
		query.setParameter("courseId", courseId);
		list = query.list();
		if (list.size() > 0 && list.get(0) != null && list.get(0) > 0) {
			return true;
		} else {
			return false;
		}
	}

	public List<Object[]> getStudentsRegularTheoryMarksForSubj(Integer subId,
			Integer stuId) {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		List<Object[]> l = null;
		String HQL_QUERY = "select e.subjectId, e.studentTheoryMarks, e.subjectTheoryMark,"
				+ " e.studentPracticalMarks, e.subjectPracticalMark"
				+ " from ExamStudentFinalMarkDetailsBO e"
				+ " where e.subjectId = :subId and e.studentId = :stuId";

		Query query = session.createQuery(HQL_QUERY);
		query.setParameter("subId", subId);
		query.setParameter("stuId", stuId);

		l = (List<Object[]>) query.list();

		if (l != null && l.size() > 0) {
			return l;
		} else {
			return new ArrayList<Object[]>();
		}
	}

	public boolean getInternalFailStatus(Integer subId, Integer stuId) {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		List<String> list = null;

		String HQL_QUERY = "select e.passOrFail"
				+ " from ExamStudentOverallInternalMarkDetailsBO e"
				+ " where e.studentId = :stuId and e.subjectId = :subId";

		Query query = session.createQuery(HQL_QUERY);
		query.setParameter("subId", subId);
		query.setParameter("stuId", stuId);
		list = query.list();
		if (list.size() > 0 && list.get(0).equalsIgnoreCase("fail")) {
			return true;
		} else {
			return false;
		}
	}

	// To find the type of evaluation for each subject
	public List<Object[]> getSubjectEvalType(Integer subId,
			String joiningBatch, String subjectType) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		List<Object[]> l = null;

		String HQL_QUERY = "";

		if (subjectType.equalsIgnoreCase("t")) {

			HQL_QUERY = "select srs.theoryEseIsRegular, srs.theoryEseIsMultipleAnswerScript, srs.theoryEseIsMultipleEvaluator"
					+ " from ExamSubjectRuleSettingsBO srs"
					+ " where srs.subjectId = :subId and srs.academicYear = :joiningBatch";

		}

		if (subjectType.equalsIgnoreCase("p")) {
			HQL_QUERY = "select srs.practicalEseIsRegular, srs.practicalEseIsMultipleAnswerScript, srs.practicalEseIsMultipleEvaluator"
					+ " from ExamSubjectRuleSettingsBO srs"
					+ " where srs.subjectId = :subId and srs.academicYear = :joiningBatch";
		}

		if (subjectType.equalsIgnoreCase("b")) {
			HQL_QUERY = "select srs.theoryEseIsRegular, srs.theoryEseIsMultipleAnswerScript, srs.theoryEseIsMultipleEvaluator, srs.practicalEseIsRegular, srs.practicalEseIsMultipleAnswerScript, srs.practicalEseIsMultipleEvaluator"
					+ " from ExamSubjectRuleSettingsBO srs"
					+ " where srs.subjectId = :subId and srs.academicYear = :joiningBatch";
		}

		Query query = session.createQuery(HQL_QUERY);
		String[] split = joiningBatch.split("-");
		query.setParameter("joiningBatch", Integer.parseInt(split[0]));
		query.setParameter("subId", subId);

		l = (List<Object[]>) query.list();

		if (l != null && l.size() > 0) {
			return l;
		} else {
			return new ArrayList<Object[]>();
		}
	}

	// Regular Check for a subject
	public DisplayValueTO getRegularCheckForSubject(Integer subId,
			String joiningBatch, String subjectType, Integer courseId,
			Integer schemeNo) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		Iterator<Object[]> list = null;
		DisplayValueTO dto = null;
		String HQL_QUERY = "";
		HQL_QUERY = "select srs.theoryEseIsRegular, srs.practicalEseIsRegular from ExamSubjectRuleSettingsBO srs"
				+ " where srs.subjectId = :subId and srs.academicYear = :joiningBatch and srs.courseId = :courseId and srs.schemeNo = :schemeNo " +
				 " and srs.isActive = 1";

		Query query = session.createQuery(HQL_QUERY);
		String[] split = joiningBatch.split("-");

		if (joiningBatch != null && joiningBatch.length() > 0) {
			query.setParameter("joiningBatch", Integer.parseInt(split[0]));
		}
		query.setParameter("subId", subId);
		query.setParameter("courseId", courseId);
		query.setParameter("schemeNo", schemeNo);
		list = query.list().iterator();
		dto = new DisplayValueTO();
		while (list.hasNext()) {
			Object[] objects = (Object[]) list.next();

			if (objects[0] != null) {
				dto.setDisplay(objects[0].toString());
			}
			if (objects[1] != null) {
				dto.setValue(objects[1].toString());
			}
		}
		return dto;
	}

	// Multiple Evaluator Check for a subject
	public DisplayValueTO getmultipleEvalCheckForSubject(Integer subId,
			String joiningBatch, String subjectType, Integer courseId,
			Integer schemeNo) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		Iterator<Object[]> list = null;
		DisplayValueTO dto = null;
		String HQL_QUERY = "";

		HQL_QUERY = "select srs.theoryEseIsMultipleEvaluator, srs.practicalEseIsMultipleEvaluator from ExamSubjectRuleSettingsBO srs"
				+ " where srs.subjectId = :subId and srs.academicYear = :joiningBatch and srs.courseId = :courseId and srs.schemeNo = :schemeNo " +
				 " and srs.isActive = 1";
		Query query = session.createQuery(HQL_QUERY);
		String[] split = joiningBatch.split("-");
		query.setParameter("joiningBatch", Integer.parseInt(split[0]));
		query.setParameter("subId", subId);
		query.setParameter("courseId", courseId);
		query.setParameter("schemeNo", schemeNo);
		list = query.list().iterator();
		dto = new DisplayValueTO();
		while (list.hasNext()) {
			Object[] objects = (Object[]) list.next();

			if (objects[0] != null) {
				dto.setDisplay(objects[0].toString());
			}
			if (objects[1] != null) {
				dto.setValue(objects[1].toString());
			}
		}
		return dto;
	}

	// Multiple Answer Script Check for a subject
	public DisplayValueTO getMultipleAnsScriptCheckForSubject(Integer subId,
			String joiningBatch, String subjectType, Integer courseId,
			Integer schemeNo) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		Iterator<Object[]> list = null;
		DisplayValueTO dto = null;
		String HQL_QUERY = "";

		HQL_QUERY = "select srs.theoryEseIsMultipleAnswerScript, srs.practicalEseIsMultipleAnswerScript from ExamSubjectRuleSettingsBO srs"
				+ " where srs.subjectId = :subId and srs.academicYear = :joiningBatch and srs.courseId = :courseId and srs.schemeNo = :schemeNo " +
						" and srs.isActive = 1";
		Query query = session.createQuery(HQL_QUERY);
		String[] split = joiningBatch.split("-");
		query.setParameter("joiningBatch", Integer.parseInt(split[0]));
		query.setParameter("subId", subId);
		query.setParameter("courseId", courseId);
		query.setParameter("schemeNo", schemeNo);
		list = query.list().iterator();
		dto = new DisplayValueTO();
		while (list.hasNext()) {
			Object[] objects = (Object[]) list.next();

			if (objects[0] != null) {
				dto.setDisplay(objects[0].toString());
			}
			if (objects[1] != null) {
				dto.setValue(objects[1].toString());
			}
		}
		return dto;
	}

	// Get student's theory marks for regular
	public List<Object[]> getStudentMarkTheoryReg(Integer examId,
			ArrayList<Integer> subjectList, Integer studentId) {

		if (subjectList.size() > 0) {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			Session session = sessionFactory.openSession();

			List<Object[]> l = null;
			String HQL_QUERY = "";

			HQL_QUERY = " SELECT sub.id AS subjectId, "
					+ " sub.name, "
					+ " b.maxthoery, "
					+ " 0, 0, 0, 0, 0, "
					+ " sub.is_theory_practical "
					+ " FROM subject sub "
					+ " LEFT JOIN "
					+ " (SELECT a.exam_id, "
					+ " a.student_id, "
					+ " a.subject_id, "
					+ " (max(a.finalthoerymark)) AS maxthoery "
					+ " FROM (SELECT me.exam_id, "
					+ " me.student_id, "
					+ " med.subject_id, "
					+ " me.evaluator_type_id, "
					//+ " sum(med.theory_marks) AS finalthoerymark "
					+ " med.theory_marks AS finalthoerymark "
					+ " FROM EXAM_marks_entry me LEFT JOIN "
					+ " EXAM_marks_entry_details med "
					+ " ON med.marks_entry_id = me.id "
					+ " WHERE me.exam_id = :examId AND me.student_id = :studentId "
					+ " and med.theory_marks is not null and trim(med.theory_marks)!= '' "
					+ " GROUP BY me.exam_id, " + " me.student_id, "
					+ " med.subject_id, " + " me.evaluator_type_id) a "
					+ " GROUP BY a.student_id, a.subject_id) AS b "
					+ " ON b.subject_id = sub.id AND b.exam_id = :examId "
					+ " WHERE sub.id IN (:subjectId) ";

			Query query = session.createSQLQuery(HQL_QUERY);
			query.setParameter("examId", examId);
			query.setParameter("studentId", studentId);
			query.setParameterList("subjectId", subjectList);
			l = query.list();
			if (l != null && l.size() > 0) {
				return l;
			} else {
				return new ArrayList<Object[]>();
			}
		}
		return new ArrayList<Object[]>();
	}

	// Get student's practical marks for regular
	public List<Object[]> getStudentMarkPracReg(Integer examId,
			ArrayList<Integer> subjectList, Integer studentId) {

		if (subjectList.size() > 0) {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			Session session = sessionFactory.openSession();

			List<Object[]> l = null;
			String HQL_QUERY = "";

			HQL_QUERY = " SELECT sub.id AS subjectId, "
					+ " sub.name, "
					+ " 0, "
					+ " b.maxpractical, 0, 0, 0, 0, "
					+ " sub.is_theory_practical "
					+ " FROM subject sub LEFT JOIN "
					+ " (SELECT a.exam_id, "
					+ " a.student_id, "
					+ " a.subject_id, "
					+ " (max(a.finalpracticalmark)) AS maxpractical "
					+ " FROM (SELECT me.exam_id, "
					+ " me.student_id, "
					+ " med.subject_id, "
					+ " me.evaluator_type_id, "
					//+ " sum(med.practical_marks) AS finalpracticalmark "
					+ " med.practical_marks  AS finalpracticalmark "
					+ " FROM EXAM_marks_entry me LEFT JOIN "
					+ " EXAM_marks_entry_details med "
					+ " ON med.marks_entry_id = me.id "
					+ " WHERE me.exam_id = :examId AND me.student_id = :studentId "
					+ " and med.practical_marks is not null and trim(med.practical_marks)!= '' "
					+ " GROUP BY me.exam_id, " + " me.student_id, "
					+ " med.subject_id, " + " me.evaluator_type_id) a "
					+ " GROUP BY a.student_id, a.subject_id) AS b "
					+ " ON b.subject_id = sub.id AND b.exam_id = :examId "
					+ " WHERE sub.id IN (:subjectId) ";

			Query query = session.createSQLQuery(HQL_QUERY);
			query.setParameter("examId", examId);
			query.setParameter("studentId", studentId);
			query.setParameterList("subjectId", subjectList);
			l = query.list();
			if (l != null && l.size() > 0) {
				return l;
			} else {
				return new ArrayList<Object[]>();
			}
		}
		return new ArrayList<Object[]>();
	}

	// Get student's theory marks for multiple evaluator
	public List<Object[]> getStudentMarkTheoryMulEval(Integer examId,
			ArrayList<Integer> subjectList, Integer stuId, Integer courseId, int schemeNo) {
		if (subjectList.size() > 0) {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			Session session = sessionFactory.openSession();

			List<Object[]> l = null;
			String HQL_QUERY = "";

			HQL_QUERY = "SELECT sub.id AS subjectId, "
					+ " sub.name, "
					+ " b.maxthoery, "
					+ " 0, "
					+ " b.minthoery, "
					+ " 0, "
					+ " b.avgthoery, "
					+ " 0, "
					+ " sub.is_theory_practical "
					+ " FROM subject sub LEFT JOIN "
					+ " (SELECT a.exam_id, "
					+ " a.student_id, "
					+ " a.subject_id, "
					+ " (max(a.finalthoerymark)) AS maxthoery, "
					+ " (case when (a.finalthoerymark = (select absent_code_mark_entry from EXAM_exam_settings)) OR "
					+ " (a.finalthoerymark = (select not_proced_code_mark_entry from EXAM_exam_settings)) "
					+" then a.finalthoerymark  else " 
					+ " (sum(a.finalthoerymark) "
					+ " / (SELECT distinct e.no_of_evaluations "
					+ " FROM EXAM_subject_rule_settings_mul_evaluator as e JOIN "
					+ " EXAM_subject_rule_settings as srs ON e.subject_rule_settings_id = srs.id "
					+ " WHERE srs.subject_id in (:subjectId) and srs.scheme_no =  " + schemeNo + " and srs.is_active = 1 "
					+ " AND e.is_theory_practical = 't' and srs.course_id = :courseId"
					+ " AND srs.academic_year IN (SELECT academic_year "
					+ " FROM EXAM_definition " + " WHERE id = :examId))) end) "
					+ " AS avgthoery, "
					+ " (min(a.finalthoerymark)) AS minthoery "
					+ " FROM (SELECT me.exam_id, " + " me.student_id, "
					+ " med.subject_id, " + " me.evaluator_type_id, "
					//+ " sum(med.theory_marks) AS finalthoerymark, "
					//+ " sum(med.practical_marks) AS finalpracticalmark "
					+ " med.theory_marks AS finalthoerymark, "
					+ " med.practical_marks AS finalpracticalmark "
					+ " FROM EXAM_marks_entry me "
					+ " LEFT JOIN EXAM_marks_entry_details med "
					+ " ON med.marks_entry_id = me.id "
					+ " WHERE me.exam_id = :examId AND me.student_id = :stuId "
					+ " GROUP BY me.exam_id, " + " me.student_id, "
					+ " med.subject_id, " + " me.evaluator_type_id) a "
					+ " GROUP BY a.student_id, a.subject_id) AS b "
					+ " ON b.subject_id = sub.id AND b.exam_id = :examId "
					+ " WHERE sub.id IN (:subjectId) ";

			Query query = session.createSQLQuery(HQL_QUERY);
			query.setParameter("examId", examId);
			query.setParameter("stuId", stuId);
			query.setParameter("courseId", courseId);
			query.setParameterList("subjectId", subjectList);
			l = query.list();
			if (l != null && l.size() > 0) {
				return l;
			} else {
				return new ArrayList<Object[]>();
			}
		}
		return new ArrayList<Object[]>();
	}

	// Get student's practical marks for multiple evaluator
	public List<Object[]> getStudentMarkPracMulEval(Integer examId,
			ArrayList<Integer> subjectList, Integer stuId, Integer courseId, int schemeNo) {
		if (subjectList.size() > 0) {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			Session session = sessionFactory.openSession();

			List<Object[]> l = null;
			String HQL_QUERY = "";

			HQL_QUERY = "SELECT sub.id AS subjectId, "
					+ " sub.name, "
					+ " 0, "
					+ " b.maxpractical, "
					+ " 0, "
					+ " b.minpractical, "
					+ " 0, "
					+ " b.avgpractical, "
					+ " sub.is_theory_practical "
					+ " FROM subject sub "
					+ " LEFT JOIN "
					+ " (SELECT a.exam_id, "
					+ " a.student_id, "
					+ " a.subject_id, "
					+ " (max(a.finalpracticalmark)) AS maxpractical, "
					+ " (case when (a.finalpracticalmark = (select absent_code_mark_entry from EXAM_exam_settings)) OR "
					+ " (a.finalpracticalmark = (select not_proced_code_mark_entry from EXAM_exam_settings)) "
					+ " then a.finalpracticalmark  else "
					+ " (sum(a.finalpracticalmark) "
					+ " / (SELECT distinct e.no_of_evaluations "
					+ " FROM EXAM_subject_rule_settings_mul_evaluator as e JOIN "
					+ " EXAM_subject_rule_settings as srs ON e.subject_rule_settings_id = srs.id "
					+ " WHERE srs.subject_id in (:subjectId) and srs.scheme_no = " + schemeNo + " and srs.is_active = 1 "
					+ "  AND e.is_theory_practical = 'p' and srs.course_id = :courseId"
					+ " AND srs.academic_year IN (SELECT academic_year "
					+ " FROM EXAM_definition "
					+ " WHERE id = :examId))) end) "
					+ " AS avgpractical, "
					+ " (min(a.finalpracticalmark)) AS minpractical "
					+ " FROM (SELECT me.exam_id, "
					+ " me.student_id, "
					+ " med.subject_id, "
					+ " me.evaluator_type_id, "
					//+ " sum(IF(med.theory_marks='NP' || med.theory_marks = 'AB',0,med.theory_marks)) AS finalthoerymark, "
					//+ " sum(IF(med.practical_marks='NP' || med.practical_marks = 'AB',0,med.practical_marks)) AS finalpracticalmark "
					+ " med.theory_marks AS finalthoerymark,"
					+ " med.practical_marks AS finalpracticalmark "
					+ " FROM EXAM_marks_entry me "
					+ " LEFT JOIN EXAM_marks_entry_details med "
					+ " ON med.marks_entry_id = me.id "
					+ " WHERE me.exam_id = :examId AND me.student_id = :stuId "
					+ " GROUP BY me.exam_id, " + " me.student_id, "
					+ " med.subject_id, " + " me.evaluator_type_id) a "
					+ " GROUP BY a.student_id, a.subject_id) AS b "
					+ " ON b.subject_id = sub.id AND b.exam_id = :examId "
					+ " WHERE sub.id IN (:subjectId) ";

			// sum(IF ( a="NP" || a = "AB",0,1))
			// i have used variable a. it is the marks field
			// i am just showing u a sample
			// for u it will be sum(IF ( marks="NP" || a =
			// "AB",0,marks ))

			Query query = session.createSQLQuery(HQL_QUERY);
			query.setParameter("examId", examId);
			query.setParameter("stuId", stuId);
			query.setParameterList("subjectId", subjectList);
			query.setParameter("courseId", courseId);
			l = query.list();
			if (l != null && l.size() > 0) {
				return l;
			} else {
				return new ArrayList<Object[]>();
			}
		}
		return new ArrayList<Object[]>();
	}

	/*
	 * Insert list of objects into respective tables
	 */
	public int insert_ListFinalMarks(
			ArrayList<ExamStudentFinalMarkDetailsBO> listBO) {
		Transaction tx = null;
		Session session = null;
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		int flag = 0;
		try {
			for (ExamStudentFinalMarkDetailsBO obj : listBO) {
				// get id for examid stuid classid subid
				deleteIfExists(obj.getExamId(), obj.getStudentId(), obj
						.getClassId(), obj.getSubjectId());
				flag = 1;
				session = sessionFactory.openSession();
				tx = session.beginTransaction();

				session.save(obj);
				tx.commit();
				session.flush();
				session.close();
			}

		} catch (Exception e) {
			flag = 0;
			e.printStackTrace();
			log.error(e.getMessage());
			if (tx != null) {
				tx.rollback();
			}
			if (session != null) {
				// session.flush();
				session.close();
			}
		}
		return flag;
	}

	public int insert_List_finalInternalMarksOverall(
			ArrayList<ExamStudentOverallInternalMarkDetailsBO> listBO) {
		Transaction tx = null;
		Session session = null;
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		int flag = 0;
		try {
			for (ExamStudentOverallInternalMarkDetailsBO obj : listBO) {
				// get id for examid stuid classid subid
				deleteIfExistsInternalOverall(obj.getExamId(), obj
						.getStudentId(), obj.getClassId(), obj.getSubjectId());
				flag = 1;
				session = sessionFactory.openSession();
				tx = session.beginTransaction();

				session.save(obj);
				tx.commit();
				session.flush();
				session.close();
			}

		} catch (Exception e) {
			flag = 0;
			e.printStackTrace();
			log.error(e.getMessage());
			if (tx != null) {
				tx.rollback();
			}
			if (session != null) {
				// session.flush();
				session.close();
			}
		}
		return flag;
	}

	public int insertList_assignmentMarks(
			ArrayList<ExamStudentAssignmenteMarkDetailsBO> listBO) {
		Transaction tx = null;
		Session session = null;
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		int flag = 0;
		try {
			for (ExamStudentAssignmenteMarkDetailsBO obj : listBO) {

				deleteIfExistsAssignmentMarks(obj.getClassId(), obj
						.getStudentId());
				flag = 1;
				session = sessionFactory.openSession();
				tx = session.beginTransaction();

				session.save(obj);
				tx.commit();
				session.flush();
				session.close();
			}

		} catch (Exception e) {
			flag = 0;
			e.printStackTrace();
			log.error(e.getMessage());
			if (tx != null) {
				tx.rollback();
			}
			if (session != null) {
				// session.flush();
				session.close();
			}
		}
		return flag;
	}

	public int insertList_attendanceMarks(
			ArrayList<ExamStudentAttendanceMarkDetailsBO> listBO) {
		Transaction tx = null;
		Session session = null;
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		int flag = 0;
		try {
			for (ExamStudentAttendanceMarkDetailsBO obj : listBO) {

				deleteIfExistsAttendanceMarks(obj.getClassId(), obj
						.getStudentId(), obj.getAttendanceTypeId());
				flag = 1;
				session = sessionFactory.openSession();
				tx = session.beginTransaction();

				session.save(obj);
				tx.commit();
				session.flush();
				session.close();
			}

		} catch (Exception e) {
			flag = 0;
			e.printStackTrace();
			log.error(e.getMessage());
			if (tx != null) {
				tx.rollback();
			}
			if (session != null) {
				// session.flush();
				session.close();
			}
		}
		return flag;
	}

	private void deleteIfExistsAssignmentMarks(Integer classId,
			Integer studentId) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();

		String HQL_QUERY = "DELETE FROM ExamStudentAssignmenteMarkDetailsBO sf "
				+ " where sf.classId=:classId and sf.studentId=:studentId ";

		Query query = session.createQuery(HQL_QUERY);
		query.setParameter("classId", classId);
		query.setParameter("studentId", studentId);
		query.executeUpdate();
		tx.commit();
	}

	private void deleteIfExistsAttendanceMarks(Integer classId,
			Integer studentId, Integer attendanceTypeId) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();

		String HQL_QUERY = "DELETE FROM ExamStudentAttendanceMarkDetailsBO sf "
				+ " where sf.classId=:classId and sf.studentId=:studentId and sf.attendanceTypeId = :attendanceTypeId";

		Query query = session.createQuery(HQL_QUERY);
		query.setParameter("classId", classId);
		query.setParameter("studentId", studentId);
		query.setParameter("attendanceTypeId", attendanceTypeId);
		query.executeUpdate();
		tx.commit();
	}

	private void deleteIfExistsInternal(int examId, int studentId, int classId,
			int subjectId) {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();

		/*String HQL_QUERY = "DELETE FROM ExamStudentInternalFinalMarkDetailsBO sf "
				+ "where sf.examId=:examId and sf.classId=:classId and sf.studentId=:studentId "
				+ "and sf.subjectId=:subjectId";*/

		String HQL_QUERY =" DELETE FROM EXAM_student_internal_final_mark_details "  +
		 " WHERE exam_id =:examId AND class_id = :classId AND student_id = :studentId AND subject_id = :subjectId ";
		
		//Query query = session.createQuery(HQL_QUERY);
		Query query = session.createSQLQuery(HQL_QUERY);
		query.setParameter("examId", examId);
		query.setParameter("classId", classId);
		query.setParameter("studentId", studentId);
		query.setParameter("subjectId", subjectId);
		query.executeUpdate();
		tx.commit();
		session.flush();
		session.close();

	}

	private void deleteIfExists(int examId, int studentId, int classId,
			int subjectId) {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();

		String HQL_QUERY = "DELETE FROM ExamStudentFinalMarkDetailsBO sf "
				+ "where sf.examId=:examId and sf.classId=:classId and sf.studentId=:studentId "
				+ "and sf.subjectId=:subjectId";

		Query query = session.createQuery(HQL_QUERY);
		query.setParameter("examId", examId);
		query.setParameter("classId", classId);
		query.setParameter("studentId", studentId);
		query.setParameter("subjectId", subjectId);
		query.executeUpdate();
		tx.commit();

	}

	public int insert_List_finalInternalMarks(
			ArrayList<ExamStudentInternalFinalMarkDetailsBO> listBO) {
		Transaction tx = null;
		Session session = null;
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		int flag = 0;
		session = sessionFactory.openSession();
		tx = session.beginTransaction();
		try {
			
			for (ExamStudentInternalFinalMarkDetailsBO obj : listBO) {

				String HQL_QUERY =" DELETE FROM EXAM_student_internal_final_mark_details "  +
				 " WHERE exam_id =:examId AND class_id = :classId AND student_id = :studentId AND subject_id = :subjectId ";
				Query query = session.createSQLQuery(HQL_QUERY);
				query.setParameter("examId", obj.getExamId());
				query.setParameter("classId",  obj.getClassId());
				query.setParameter("studentId", obj.getStudentId());
				query.setParameter("subjectId", obj.getSubjectId());
				query.executeUpdate();
			}
			tx.commit();
			tx = session.beginTransaction();
			for (ExamStudentInternalFinalMarkDetailsBO obj : listBO) {
				flag = 1;
				session.save(obj);
			}
			tx.commit();
			session.flush();
			session.close();

		} catch (Exception e) {
			flag = 0;
			e.printStackTrace();
			log.error(e.getMessage());
			if (tx != null) {
				tx.rollback();
			}
			if (session != null) {
				// session.flush();
				session.close();
			}
		}
		return flag;
	}

	private void deleteIfExistsInternalOverall(int examId, int studentId,
			int classId, int subjectId) {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();

		String HQL_QUERY = "DELETE FROM ExamStudentOverallInternalMarkDetailsBO sf "
				+ "where sf.examId=:examId and sf.classId=:classId and sf.studentId=:studentId "
				+ "and sf.subjectId=:subjectId";

		Query query = session.createQuery(HQL_QUERY);
		query.setParameter("examId", examId);
		query.setParameter("classId", classId);
		query.setParameter("studentId", studentId);
		query.setParameter("subjectId", subjectId);
		query.executeUpdate();
		tx.commit();

	}

	public List<Object[]> getInternalSettings(int subjectId, int examId,
			int courseId, int schemeNo) throws DataNotFoundException {

		List returnList = null;

		Query query = null;
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		String SQL = "select srs.finalTheoryInternalIsAssignment, "
				+ "  srs.finalTheoryInternalIsAttendance, srs.finalTheoryInternalIsSubInternal, "
				+ " srs.finalPracticalInternalIsAssignment, srs.finalPracticalInternalIsAttendance,"
				+ " srs.finalPracticalInternalIsSubInternal "
				+ " from ExamSubjectRuleSettingsBO  srs "
				+ " where srs.courseId =:courseId "
				+ " and srs.subjectId =:subjectId and "
				+ "  srs.schemeNo = " + schemeNo + " and "
				+ " srs.isActive = 1 and "
				+ " srs.academicYear = (select ed.academicYear"
				+ " from ExamDefinitionBO ed where ed.id =:examId )";

		query = session.createQuery(SQL);
		query.setParameter("courseId", courseId);
		query.setParameter("subjectId", subjectId);
		query.setParameter("examId", examId);

		returnList = query.list();

		session.flush();
		session.close();

		return returnList;

	}

	// To fetch student's latest supplementary exam
	public Integer getStudLatestExam(Integer stuId) {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		Integer supplexamId = null;
		String HQL_QUERY = "";

		HQL_QUERY = "SELECT a.exam_id"
				+ " FROM (SELECT max(s.id) AS id, s.exam_id"
				+ " FROM EXAM_supplementary_improvement_application s"
				+ " WHERE s.student_id = :stuId AND s.is_supplementary = 1"
				+ " GROUP BY s.student_id) a";

		Query query = session.createSQLQuery(HQL_QUERY);
		query.setParameter("stuId", stuId);
		List list = query.list();
		if (list != null && list.size() > 0) {
			supplexamId = (Integer) list.get(0);
		}
		return supplexamId;
	}

	// Fetch all the subjects for the supplementary exam
	public ArrayList<Integer> getSupplSubjList(Integer stuId, Integer classId,
			Integer academicYear) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		String HQL_QUERY = "";

		HQL_QUERY = " SELECT s.id FROM student st"
				+ " LEFT JOIN  applicant_subject_group asg"
				+ " ON asg.adm_appln_id = st.adm_appln_id"
				+ " LEFT JOIN subject_group_subjects sgs"
				+ " ON asg.subject_group_id = sgs.subject_group_id"
				+ " AND sgs.is_active = 1"
				+ " LEFT JOIN subject s"
				+ " ON sgs.subject_id = s.id"
				+ " LEFT JOIN class_schemewise"
				+ " ON st.class_schemewise_id = class_schemewise.id"
				+ " LEFT JOIN classes"
				+ " ON class_schemewise.class_id = classes.id"
				+ " LEFT JOIN  curriculum_scheme_duration csd"
				+ " ON class_schemewise.curriculum_scheme_duration_id = csd.id"
				+ " LEFT JOIN adm_appln"
				+ " ON st.adm_appln_id = adm_appln.id"
				+ " LEFT JOIN  personal_data"
				+ " ON adm_appln.personal_data_id = personal_data.id"
				+ " WHERE st.id = :stuId AND classes.id = :classId AND csd.academic_year = :academicYear"
				+ " UNION"
				+ " SELECT s.id"
				+ " FROM EXAM_student_sub_grp_history sh"
				+ " LEFT JOIN EXAM_student_previous_class_details p"
				+ " ON p.student_id = sh.student_id"
				+ " LEFT JOIN subject_group_subjects sgs"
				+ " ON sh.subject_group_id = sgs.subject_group_id AND sgs.is_active = 1"
				+ " LEFT JOIN subject s"
				+ " ON sgs.subject_id = s.id"
				+ " WHERE sh.student_id = :stuId AND p.class_id = :classId AND p.academic_year = :academicYear";

		Query query = session.createSQLQuery(HQL_QUERY);
		query.setParameter("stuId", stuId);
		query.setParameter("classId", classId);
		query.setParameter("academicYear", academicYear);
		List list = (ArrayList<Integer>) query.list();
		if (list != null && list.size() > 0) {
			return (ArrayList<Integer>) list;
		}
		return new ArrayList<Integer>();
	}

	// Get the student record count from the regular overall
	// table
	public Integer getStudentRecCount(Integer stuId, Integer subId) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		Integer supplexamId = 0;
		String HQL_QUERY = "";

		HQL_QUERY = "select fm.id as c"
				+ " from ExamStudentFinalMarkDetailsBO fm"
				+ " where fm.studentId = :stuId and fm.subjectId = :subId";

		Query query = session.createQuery(HQL_QUERY);
		query.setParameter("stuId", stuId);
		query.setParameter("subId", subId);
		List list = query.list();
		if (list != null && list.size() > 0) {
			supplexamId = (Integer) list.size();
		}
		return supplexamId;
	}

	public List<Object[]> get_student_supp_mark_details(Integer stuId,
			Integer subId, Integer courseId, Integer academicYear, int recCount, int schemeNo) {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		List<Object[]> l = null;
		String HQL_QUERY = "";

		HQL_QUERY = " SELECT fm.student_id, fm.subject_id, IFNULL(max(convert(fm.student_theory_marks, decimal)), 0), " +
				" IFNULL(max(convert(fm.student_practical_marks, decimal)), 0), " +
				" (IFNULL(max(convert(if(sup.theory_total_sub_internal_mark is not null, sup.theory_total_sub_internal_mark, oi.theory_total_sub_internal_mark), decimal)), 0) +  " +
				" IFNULL(max(convert(if(sup.theory_total_attendance_mark is not null, sup.theory_total_attendance_mark, oi.theory_total_attendance_mark), decimal)), 0)) as theoryMark, " +
				" (IFNULL(max(convert(if(sup.practical_total_sub_internal_mark is not null, sup.practical_total_sub_internal_mark, oi.practical_total_sub_internal_mark), decimal)), 0) + " +
				" IFNULL(max(convert(if(sup.practical_total_attendance_mark is not null, sup.practical_total_attendance_mark, oi.practical_total_attendance_mark), decimal)), 0)) as practicalMark, s." +
				" is_theory_practical, srs.theory_ese_minimum_mark, srs." +
				" theory_ese_theory_final_minimum_mark, srs." +
				" final_theory_internal_minimum_mark, srs.practical_ese_minimum_mark, srs." +
				" practical_ese_theory_final_minimum_mark, srs." +
				" final_practical_internal_minimum_mark,  fm.pass_or_fail as regPass, oi." +
				" pass_or_fail as intPass" +
				  " FROM EXAM_student_final_mark_details fm"
				+ " join subject s on fm.subject_id = s.id"
				+ " JOIN EXAM_subject_rule_settings srs"
				+ " ON s.id = srs.subject_id and srs.course_id = :courseId and srs.academic_year = :academicYear " +
						" and srs.scheme_no = " + schemeNo + " and srs.is_active = 1 "
				+ " left JOIN"
				+ " EXAM_student_overall_internal_mark_details oi"
				+ " ON fm.student_id = oi.student_id and fm.subject_id = oi.subject_id"
				+ " left join EXAM_internal_mark_supplementary_details sup on sup.student_id = oi.student_id and " +
				" sup.subject_id = oi.subject_id " 
				+ " join EXAM_definition ed on fm.exam_id = ed.id"
				+ " WHERE fm.student_id = :stuId AND fm.subject_id = :subId"
				+ " group by fm.student_id";
		/*if(recCount > 1){
			HQL_QUERY =  HQL_QUERY + " order by ed.academic_year, ed.month desc limit 1";
		}*/

		Query query = session.createSQLQuery(HQL_QUERY);
		query.setParameter("stuId", stuId);
		query.setParameter("subId", subId);
		query.setParameter("courseId", courseId);
		query.setParameter("academicYear", academicYear);
		l = query.list();
		if (l != null && l.size() > 0) {
			return l;
		} else {
			return new ArrayList<Object[]>();
		}
	}

	public List<Object[]> get_student_supp_mark_details_multiRec(Integer stuId,
			Integer subId, Integer courseId, Integer academicYear) {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		List<Object[]> l = null;
		String HQL_QUERY = "";

		HQL_QUERY = " SELECT fm.student_id,"
				+ " fm.subject_id,"
				+ " fm.student_theory_marks,"
				+ " fm.student_practical_marks,"
				+ " max(oi.theory_total_mark),"
				+ " max(oi.practical_total_mark),"
				+ " s.is_theory_practical, srs.theory_ese_minimum_mark,"
				+ " srs.theory_ese_theory_final_minimum_mark,"
				+ " srs.theory_int_min_marks_total,"
				+ " srs.practical_ese_minimum_mark,"
				+ " srs.practical_ese_theory_final_minimum_mark,"
				+ " srs.practical_int_min_marks_total,  fm.pass_or_fail,"
				+ " oi.pass_or_fail"
				+ " FROM EXAM_student_final_mark_details fm"
				+ " join subject s on fm.subject_id = s.id"
				+ " JOIN EXAM_subject_rule_settings srs"
				+ " ON s.id = srs.subject_id and srs.course_id = :courseId and srs.academic_year = :academicYear"
				+ " left JOIN"
				+ " EXAM_student_overall_internal_mark_details oi"
				+ " ON fm.student_id = oi.student_id and fm.subject_id = oi.subject_id"
				+ " join EXAM_definition ed on fm.exam_id = ed.id"
				+ " WHERE fm.student_id = :stuId AND fm.subject_id = :subId"
				+ " group by fm.student_id  order by ed.academic_year, ed.month desc limit 1";

		Query query = session.createSQLQuery(HQL_QUERY);
		query.setParameter("stuId", stuId);
		query.setParameter("subId", subId);
		query.setParameter("courseId", courseId);
		query.setParameter("academicYear", academicYear);
		l = query.list();
		if (l != null && l.size() > 0) {
			return l;
		} else {
			return new ArrayList<Object[]>();
		}
	}

	public List<Object[]> getSubjectRuleDetails(Integer courseId,
			Integer subId, Integer academicYear) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		List<Object[]> l = null;
		String HQL_QUERY = "";

		HQL_QUERY = " select srs.theoryEseMinimumMark, srs.theoryEseTheoryFinalMinimumMark, srs.theoryIntMinMarksTotal,"
				+ " srs.practicalEseMinimumMark, srs.practicalEseTheoryFinalMinimumMark,"
				+ " srs.practicalIntMinMarksTotal"
				+ " from ExamSubjectRuleSettingsBO srs"
				+ " where srs.courseId = :courseId and srs.subjectId = :subId and srs.academicYear = :academicYear";

		Query query = session.createQuery(HQL_QUERY);
		query.setParameter("courseId", courseId);
		query.setParameter("subId", subId);
		query.setParameter("academicYear", academicYear);
		l = query.list();
		if (l != null && l.size() > 0) {
			return l;
		} else {
			return new ArrayList<Object[]>();
		}
	}

	private Integer getPreviousSchemeNo(int studentId) {
		Session session = null;
		Integer previousSem = null;
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		session = sessionFactory.openSession();
		String HQL_QUERY = "FROM  Student st where st.id = " + studentId;
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
							.getCurriculumSchemeDuration().getSemesterYearNo() - 1;
				}
			}
		}
		if (session != null) {
			session.flush();
			session.close();
		}
		return previousSem;
	}
	/**
	 * 
	 * @param courseId
	 * @return
	 */
	public String getHighestorLatest(Integer courseId) {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		String highestOrLatest = null;

		String HQL_QUERY = "select e.improvement from ExamSettingCourseBO e"
							+ " where e.courseId = :courseId";

		Query query = session.createQuery(HQL_QUERY);
		query.setParameter("courseId", courseId);

		List list = query.list();

		if (list != null && list.size() > 0) {

			highestOrLatest = (String) list.get(0);
		}
		if (session != null) {
			session.flush();
			session.close();
		}
		return highestOrLatest;
	}

	public List<Object[]> get_student_supp_mark_details_highest_marks(Integer stuId,
			Integer subId, Integer courseId, Integer academicYear) {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		List<Object[]> l = null;
		String HQL_QUERY = "";

		HQL_QUERY = " SELECT fm.student_id,"
				+ " fm.subject_id,"
				+ " max(fm.student_theory_marks),"
				+ " max(fm.student_practical_marks),"
				+ " max(oi.theory_total_mark),"
				+ " max(oi.practical_total_mark),"
				+ " s.is_theory_practical, srs.theory_ese_minimum_mark,"
				+ " srs.theory_ese_theory_final_minimum_mark,"
				+ " srs.theory_int_min_marks_total,"
				+ " srs.practical_ese_minimum_mark,"
				+ " srs.practical_ese_theory_final_minimum_mark,"
				+ " srs.practical_int_min_marks_total,  fm.pass_or_fail,"
				+ " oi.pass_or_fail"
				+ " FROM EXAM_student_final_mark_details fm"
				+ " join subject s on fm.subject_id = s.id"
				+ " JOIN EXAM_subject_rule_settings srs"
				+ " ON s.id = srs.subject_id and srs.course_id = :courseId and srs.academic_year = :academicYear"
				+ " left JOIN"
				+ " EXAM_student_overall_internal_mark_details oi"
				+ " ON fm.student_id = oi.student_id and fm.subject_id = oi.subject_id"
				+ " join EXAM_definition ed on fm.exam_id = ed.id"
				+ " WHERE fm.student_id = :stuId AND fm.subject_id = :subId " 
				+ " group by fm.student_id ";


		Query query = session.createSQLQuery(HQL_QUERY);
		query.setParameter("stuId", stuId);
		query.setParameter("subId", subId);
		query.setParameter("courseId", courseId);
		query.setParameter("academicYear", academicYear);
		l = query.list();
		if (l != null && l.size() > 0) {
			return l;
		} else {
			return new ArrayList<Object[]>();
		}
	}
	
	
	/**
	 * 
	 * @param courseId
	 * @return
	 */
	public List<Integer> getExcludedFromResultSubjects(Integer courseId, int schemeNo, int academicYear) throws Exception {
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			Session session = sessionFactory.openSession();
	
			String HQL_QUERY = "select e.subjectId from ExamSubDefinitionCourseWiseBO e"
								+ " where e.courseId = :courseId and e.schemeNo = " + schemeNo + 
								" and (dontAddTotMarkClsDecln = 1 or dontConsiderFailureTotalResult=1) and e.academicYear = :academicYear" ;
	
			Query query = session.createQuery(HQL_QUERY);
			query.setParameter("courseId", courseId);
			query.setParameter("academicYear", academicYear);
			
			List<Integer> list = query.list();
			
			List<Integer> excludedSubject = new ArrayList<Integer>();
			if (list != null && list.size() > 0) {
	
				Iterator<Integer> itr = list.iterator();
				while (itr.hasNext()) {
					Integer subjectId = (Integer) itr.next();
					excludedSubject.add(subjectId);
				}
			}
			if (session != null) {
				session.flush();
				session.close();
			}
			return excludedSubject;			
		} catch (Exception e) {
			log.error("Error while deleting" + e);
			throw new Exception();
		}

	}
	
	public boolean deleleAlreadyExistedRecords(int classId, int examId)throws Exception {
		log.debug("inside deleleAlreadyExistedRecords");
		Session session = null;
		Transaction tx = null;
		try {
			session = HibernateUtil.getSession();
		    tx= session.beginTransaction();
			Query query = session.createQuery(" delete from ExamSupplementaryImprovementApplicationBO where examId = " + examId + " and classes.id = " + classId);
			
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
	
	/**
	 * 
	 * @param courseId
	 * @param clsId
	 * @return
	 * @throws Exception
	 */
	public Integer getSchemeNo(int clsId) throws Exception {
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			Session session = sessionFactory.openSession();

			String HQL_QUERY = "select  c.termNumber from Classes c"
					+ " where c.id = " + clsId
					+ " and c.isActive = 1" ;
					

			Query query = session.createQuery(HQL_QUERY);

			List<Integer> list = query.list();

			int schemeNo = 0;
			if (list != null && list.size() > 0) {

				Iterator<Integer> itr = list.iterator();
				while (itr.hasNext()) {
					schemeNo = (Integer) itr.next();
				}
			}
			if (session != null) {
				session.flush();
				session.close();
			}
			return schemeNo;
		} catch (Exception e) {
			log.error("Error in getSchemeNo" + e);
			throw new Exception();
		}

	}
	/**
	 * 
	 * @param studentId
	 * @param isPrevExam
	 * @param schemeNo
	 * @return
	 */
	public ArrayList<Integer> getSubjectsForStudentWithPreviousScheme(
			Integer studentId, String isPrevExam, int schemeNo) {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		ArrayList<Integer> l = new ArrayList<Integer>();
		String HQL = "";
		if (isPrevExam.equalsIgnoreCase("true")) {
			HQL = "  SELECT s.id  FROM   EXAM_student_previous_class_details st"
					+ "  LEFT JOIN"
					+ "  EXAM_student_sub_grp_history asg"
					+ "  ON asg.student_id = st.student_id"
					+ "  LEFT JOIN"
					+ "  subject_group_subjects sgs"
					+ "  ON asg.subject_group_id = sgs.subject_group_id and sgs.is_active = 1"
					+ "  LEFT JOIN"
					+ "  subject s"
					+ "  ON sgs.subject_id = s.id"
					+ "  WHERE st.student_id = :studentId"
					+ " and asg.scheme_no = "
					+ schemeNo
					+ " and st.scheme_no = " + schemeNo;

		} else {
			HQL = "  SELECT s.id  FROM   student st"
					+ "  LEFT JOIN"
					+ "  applicant_subject_group asg"
					+ "  ON asg.adm_appln_id = st.adm_appln_id"
					+ "  LEFT JOIN"
					+ "  subject_group_subjects sgs"
					+ "  ON asg.subject_group_id = sgs.subject_group_id and sgs.is_active = 1"
					+ "  LEFT JOIN" + "  subject s"
					+ "  ON sgs.subject_id = s.id"
					+ "  WHERE st.id = :studentId";
		}
		Query query = session.createSQLQuery(HQL);
		query.setParameter("studentId", studentId);
		Iterator<Object> itr = query.list().iterator();
		while (itr.hasNext()) {
			Object obj = (Object) itr.next();
			if (obj != null) {
				l.add((Integer) obj);
			}

		}
		session.flush();
		session.close();
		return l;

	}

	
	public List<Object[]> getMarkDetails(Integer stuId,
			Integer subId, Integer courseId, Integer academicYear, int schemeNo) {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		List<Object[]> l = null;
		String HQL_QUERY = "";

		/*HQL_QUERY = " SELECT fm.student_id,"
				+ " fm.subject_id,"
				+ " max( (case when (fm.student_theory_marks = (select absent_code_mark_entry from EXAM_exam_settings)) OR  "
				+ " (fm.student_theory_marks  = (select not_proced_code_mark_entry from EXAM_exam_settings)) "
				+ " then 0 else fm.student_theory_marks  "
				+ "	 end)) ,"
				+ " max( (case when (fm.student_practical_marks = (select absent_code_mark_entry from EXAM_exam_settings)) OR  "
				+ " (fm.student_practical_marks  = (select not_proced_code_mark_entry from EXAM_exam_settings)) "
				+ " then 0 else fm.student_practical_marks  "
				+ "	 end)) ,"
				+ " (IFNULL(max(oi.theory_total_sub_internal_mark), 0) + IFNULL(max(theory_total_attendance_mark), 0)) as theoryMark,"
				+ " (IFNULL(max(oi.practical_total_sub_internal_mark), 0) + IFNULL(max(practical_total_attendance_mark), 0)) as practicalMark,"
				+ " s.is_theory_practical, srs.theory_ese_minimum_mark,"
				+ " srs.theory_ese_theory_final_minimum_mark,"
				+ " srs.final_theory_internal_minimum_mark,"
				+ " srs.practical_ese_minimum_mark,"
				+ " srs.practical_ese_theory_final_minimum_mark,"
				+ " srs.final_practical_internal_minimum_mark,  fm.pass_or_fail as regPass,"
				+ " oi.pass_or_fail as intPass "
				+ " FROM EXAM_student_final_mark_details fm"
				+ " join subject s on fm.subject_id = s.id"
				+ " JOIN EXAM_subject_rule_settings srs"
				+ " ON s.id = srs.subject_id and srs.course_id = :courseId and srs.academic_year = :academicYear " +
				" and srs.scheme_no = " + schemeNo + " and srs.is_active = 1 "
				+ " left JOIN"
				+ " EXAM_student_overall_internal_mark_details oi"
				+ " ON fm.student_id = oi.student_id and fm.subject_id = oi.subject_id"
				+ " join EXAM_definition ed on fm.exam_id = ed.id"
				+ " WHERE fm.student_id = :stuId AND fm.subject_id = :subId"
				+ " group by fm.student_id";*/

		/*HQL_QUERY = " SELECT student.id as sid, s.id, " +
		"  max( (case when (fm.student_theory_marks  " +
		 " = (  " +
		 " select absent_code_mark_entry  " +
		 " from EXAM_exam_settings)) OR  " +
		 " (fm.student_theory_marks  = (select not_proced_code_mark_entry from  " +
		 " EXAM_exam_settings))  " +
		 " then 0  " +
		 "  else convert(fm.student_theory_marks,decimal) " +
		 "  end)) , max( (  " +
		 "  case when (fm.student_practical_marks =  " +
		 "  (select absent_code_mark_entry  " +
		 " from EXAM_exam_settings)) OR   (fm.student_practical_marks  = (  " +
		 " select not_proced_code_mark_entry " +
		 "  from EXAM_exam_settings)) " +
		 " then 0 " +
		 " else convert(fm.student_practical_marks, decimal) " +
		 " end)) , (IFNULL(max(convert(oi.theory_total_sub_internal_mark, decimal)), 0) + IFNULL(max( " +
		 " convert(theory_total_attendance_mark,decimal)), 0)) as theoryMark, (IFNULL(max(convert(oi. " +
		 " practical_total_sub_internal_mark,decimal)), 0) + IFNULL(max( " +
		 " convert(practical_total_attendance_mark, decimal)), 0)) as practicalMark, s. " +
		 " is_theory_practical, srs.theory_ese_minimum_mark, srs. " +
		 " theory_ese_theory_final_minimum_mark, srs. " +
		 " final_theory_internal_minimum_mark, srs.practical_ese_minimum_mark, srs." +
		 "  practical_ese_theory_final_minimum_mark, srs." +
		 " final_practical_internal_minimum_mark,  fm.pass_or_fail as regPass, oi." +
		 " pass_or_fail as intPass" +
		 " FROM    (   (   (   (   EXAM_student_overall_internal_mark_details oi" +
		 "  INNER JOIN" +
		 "  EXAM_definition EXAM_definition   ON (oi.exam_id = " +
		 " EXAM_definition.id))  INNER JOIN   classes classes   ON (oi.class_id = " +
		 "  classes.id))  INNER JOIN  student student ON (oi.student_id =  student.id)) " +
		 "  INNER JOIN subject s  ON (oi.subject_id =  s.id)) " +
		 " INNER JOIN EXAM_subject_rule_settings srs ON (srs.subject_id = s.id and srs.course_id = :courseId  " +
		 " and srs.academic_year = :academicYear and srs.scheme_no = :schemeNo and srs.is_active = 1) " +
		 " AND (srs.scheme_no = classes.term_number) LEFT JOIN EXAM_student_final_mark_details fm " +
		 " ON (oi.class_id = fm.class_id) AND (oi.student_id = fm.student_id) AND (oi.subject_id = " +
		 " fm.subject_id) WHERE student.id = :stuId AND s.id = :subId group by student.id" ;*/
		if(courseId==18){
			HQL_QUERY=" select "+
			" student.id, subject.id, "+
			" (case when (EXAM_student_final_mark_details.student_theory_marks "+
			" = ( "+
			" select absent_code_mark_entry "+
			" from EXAM_exam_settings)) OR "+
			" (EXAM_student_final_mark_details.student_theory_marks  = (select not_proced_code_mark_entry from "+
			" EXAM_exam_settings)) "+
			" then 0 "+
			" else convert(EXAM_student_final_mark_details.student_theory_marks,decimal) "+
			" end) as theory_mark, "+
			" ( "+
			" case when (EXAM_student_final_mark_details.student_practical_marks = "+
			" (select absent_code_mark_entry "+
			" from EXAM_exam_settings)) OR   (EXAM_student_final_mark_details.student_practical_marks  = ( "+
			" select not_proced_code_mark_entry "+
			" from EXAM_exam_settings)) "+
			" then 0 "+
			" else convert(EXAM_student_final_mark_details.student_practical_marks, decimal) "+
			" end) as practical_mark, "+
			" CASE WHEN "+
			" ((IFNULL(convert(EXAM_internal_mark_supplementary_details.theory_total_sub_internal_mark, decimal), 0) + "+
			" IFNULL(convert(EXAM_internal_mark_supplementary_details.theory_total_attendance_mark,decimal), 0)) > 0) "+
			" THEN "+
			" (IFNULL(convert(EXAM_internal_mark_supplementary_details.theory_total_sub_internal_mark, decimal), 0) + "+
			" IFNULL(convert(EXAM_internal_mark_supplementary_details.theory_total_attendance_mark,decimal), 0)) "+
			" ELSE "+
			" (IFNULL(convert(EXAM_student_overall_internal_mark_details.theory_total_sub_internal_mark, decimal), 0) + "+
			" IFNULL(convert(EXAM_student_overall_internal_mark_details.theory_total_attendance_mark,decimal), 0)) "+
			" END "+
			" as internal_theory_mark, "+
			" CASE WHEN "+
			" ((IFNULL(convert(EXAM_internal_mark_supplementary_details.practical_total_sub_internal_mark, decimal), 0) + "+
			" IFNULL(convert(EXAM_internal_mark_supplementary_details.practical_total_attendance_mark,decimal), 0)) > 0) "+
			" THEN "+
			" (IFNULL(convert(EXAM_internal_mark_supplementary_details.practical_total_sub_internal_mark, decimal), 0) + "+
			" IFNULL(convert(EXAM_internal_mark_supplementary_details.practical_total_attendance_mark,decimal), 0)) "+
			" ELSE "+
			" (IFNULL(convert(EXAM_student_overall_internal_mark_details.practical_total_sub_internal_mark, decimal), 0) + "+
			" IFNULL(convert(EXAM_student_overall_internal_mark_details.practical_total_attendance_mark,decimal), 0)) "+
			" END "+
			" as internal_practical_mark, "+
			" subject.is_theory_practical, "+
			" EXAM_subject_rule_settings.theory_ese_minimum_mark, "+
			" EXAM_subject_rule_settings.theory_ese_theory_final_minimum_mark, "+
			" EXAM_subject_rule_settings.final_theory_internal_minimum_mark, "+
			" EXAM_subject_rule_settings.practical_ese_minimum_mark, "+
			" EXAM_subject_rule_settings.practical_ese_theory_final_minimum_mark, "+
			" EXAM_subject_rule_settings.final_practical_internal_minimum_mark "+
			" from EXAM_student_overall_internal_mark_details "+
			" LEFT JOIN subject ON EXAM_student_overall_internal_mark_details.subject_id = subject.id "+
			" LEFT JOIN classes ON EXAM_student_overall_internal_mark_details.class_id = classes.id "+
			" LEFT JOIN student on EXAM_student_overall_internal_mark_details.student_id = student.id "+
			" LEFt JOIN EXAM_subject_rule_settings on EXAM_subject_rule_settings.subject_id = subject.id "+
			" and EXAM_subject_rule_settings.academic_year = :academicYear "+
			" and EXAM_subject_rule_settings.course_id = :courseId "+
			" and EXAM_subject_rule_settings.scheme_no = :schemeNo "+
			" LEFT JOIN EXAM_student_final_mark_details  on "+
			" EXAM_student_final_mark_details.class_id = classes.id "+
			" and EXAM_student_final_mark_details.student_id = student.id "+
			" and EXAM_student_final_mark_details.subject_id = subject.id "+
			" and ((EXAM_student_final_mark_details.student_theory_marks is not null) or (EXAM_student_final_mark_details.student_practical_marks is not null)) "+
			" LEFT JOIN EXAM_definition ON EXAM_student_final_mark_details.exam_id = EXAM_definition.id "+
			" LEFT JOIN EXAM_internal_mark_supplementary_details on "+
			" EXAM_internal_mark_supplementary_details.exam_id = EXAM_student_final_mark_details.exam_id "+
			" and EXAM_internal_mark_supplementary_details.student_id = student.id "+
			" and EXAM_internal_mark_supplementary_details.class_id = classes.id "+
			" and EXAM_internal_mark_supplementary_details.subject_id = subject.id "+
			" where student.id = :stuId and subject.id = :subId "+
			" and EXAM_subject_rule_settings.is_active = 1 "+
			" and EXAM_definition.del_is_active = 1 "+
			
			" order by EXAM_definition.year DESC, EXAM_definition.month DESC LIMIT 1 ";
		}else{
			HQL_QUERY=" select "+
			" student.id as stuId, subject.id as subId, "+
			" max((case when (EXAM_student_final_mark_details.student_theory_marks "+
			" = ( "+
			" select absent_code_mark_entry "+
			" from EXAM_exam_settings)) OR "+
			" (EXAM_student_final_mark_details.student_theory_marks  = (select not_proced_code_mark_entry from "+
			" EXAM_exam_settings)) "+
			" then 0 "+
			" else convert(EXAM_student_final_mark_details.student_theory_marks,decimal) "+
			" end)) as theoryMark, "+
			" ( "+
			" max(case when (EXAM_student_final_mark_details.student_practical_marks = "+
			" (select absent_code_mark_entry "+
			" from EXAM_exam_settings)) OR   (EXAM_student_final_mark_details.student_practical_marks  = ( "+
			" select not_proced_code_mark_entry "+
			" from EXAM_exam_settings)) "+
			" then 0 "+
			" else convert(EXAM_student_final_mark_details.student_practical_marks, decimal) "+
			" end)) as practicalMark, "+
			" max(CASE WHEN "+
			" ((IFNULL(convert(EXAM_internal_mark_supplementary_details.theory_total_sub_internal_mark, decimal), 0) + "+
			" IFNULL(convert(EXAM_internal_mark_supplementary_details.theory_total_attendance_mark,decimal), 0)) > 0) "+
			" THEN "+
			" (IFNULL(convert(EXAM_internal_mark_supplementary_details.theory_total_sub_internal_mark, decimal), 0) + "+
			" IFNULL(convert(EXAM_internal_mark_supplementary_details.theory_total_attendance_mark,decimal), 0)) "+
			" ELSE "+
			" (IFNULL(convert(EXAM_student_overall_internal_mark_details.theory_total_sub_internal_mark, decimal), 0) + "+
			" IFNULL(convert(EXAM_student_overall_internal_mark_details.theory_total_attendance_mark,decimal), 0)) "+
			" END) "+
			" as internal_theory_mark, "+
			" max(CASE WHEN "+
			" ((IFNULL(convert(EXAM_internal_mark_supplementary_details.practical_total_sub_internal_mark, decimal), 0) + "+
			" IFNULL(convert(EXAM_internal_mark_supplementary_details.practical_total_attendance_mark,decimal), 0)) > 0) "+
			" THEN "+
			" (IFNULL(convert(EXAM_internal_mark_supplementary_details.practical_total_sub_internal_mark, decimal), 0) + "+
			" IFNULL(convert(EXAM_internal_mark_supplementary_details.practical_total_attendance_mark,decimal), 0)) "+
			" ELSE "+
			" (IFNULL(convert(EXAM_student_overall_internal_mark_details.practical_total_sub_internal_mark, decimal), 0) + "+
			" IFNULL(convert(EXAM_student_overall_internal_mark_details.practical_total_attendance_mark,decimal), 0)) "+
			" END) "+
			" as internal_practical_mark, "+
			" subject.is_theory_practical, "+
			" EXAM_subject_rule_settings.theory_ese_minimum_mark, "+
			" EXAM_subject_rule_settings.theory_ese_theory_final_minimum_mark, "+
			" EXAM_subject_rule_settings.final_theory_internal_minimum_mark, "+
			" EXAM_subject_rule_settings.practical_ese_minimum_mark, "+
			" EXAM_subject_rule_settings.practical_ese_theory_final_minimum_mark, "+
			" EXAM_subject_rule_settings.final_practical_internal_minimum_mark "+
			" from EXAM_student_overall_internal_mark_details "+
			" LEFT JOIN subject ON EXAM_student_overall_internal_mark_details.subject_id = subject.id "+
			" LEFT JOIN classes ON EXAM_student_overall_internal_mark_details.class_id = classes.id "+
			" LEFT JOIN student on EXAM_student_overall_internal_mark_details.student_id = student.id "+
			" LEFt JOIN EXAM_subject_rule_settings on EXAM_subject_rule_settings.subject_id = subject.id "+
			" and EXAM_subject_rule_settings.academic_year = :academicYear "+
			" and EXAM_subject_rule_settings.course_id = :courseId "+
			" and EXAM_subject_rule_settings.scheme_no = :schemeNo "+
			" LEFT JOIN EXAM_student_final_mark_details  on "+
			" EXAM_student_final_mark_details.class_id = classes.id "+
			" and EXAM_student_final_mark_details.student_id = student.id "+
			" and EXAM_student_final_mark_details.subject_id = subject.id "+
			" LEFT JOIN EXAM_definition ON EXAM_student_final_mark_details.exam_id = EXAM_definition.id "+
			" LEFT JOIN EXAM_internal_mark_supplementary_details on "+
			" EXAM_internal_mark_supplementary_details.exam_id = EXAM_student_final_mark_details.exam_id "+
			" and EXAM_internal_mark_supplementary_details.student_id = student.id "+
			" and EXAM_internal_mark_supplementary_details.class_id = classes.id "+
			" and EXAM_internal_mark_supplementary_details.subject_id = subject.id "+
			" where student.id = :stuId and subject.id = :subId "+
			" and EXAM_subject_rule_settings.is_active = 1 "+
			" and EXAM_definition.del_is_active = 1 "+
			" order by EXAM_definition.year DESC, EXAM_definition.month DESC LIMIT 1 ";
		}
		Query query = session.createSQLQuery(HQL_QUERY);
		query.setParameter("stuId", stuId);
		query.setParameter("subId", subId);
		query.setParameter("courseId", courseId);
		query.setParameter("academicYear", academicYear);
		query.setParameter("schemeNo", schemeNo);
		
		l = query.list();
		if (l != null && l.size() > 0) {
			return l;
		} else {
			return new ArrayList<Object[]>();
		}
	}
	public boolean updatePassFail(List<ExamStudentPassFail> passFailList) throws Exception {
		log.info("Inside updateSgpa");
		ExamStudentPassFail examStudentPassFail;
		Transaction tx=null;
		Session session = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			Iterator<ExamStudentPassFail> itr = passFailList.iterator();
			int count = 0;
			tx = session.beginTransaction();
			tx.begin();
			while (itr.hasNext()) {
				examStudentPassFail = itr.next();
				//--------------
				String sqlQuey = " delete from EXAM_student_pass_fail " +
				" where class_id = " + examStudentPassFail.getClasses().getId() +
				" and scheme_no =  " + examStudentPassFail.getSchemeNo() + " and student_id = " +  examStudentPassFail.getStudent().getId();
				Query query = session.createSQLQuery(sqlQuey);
				query.executeUpdate();
				//------------------
				session.save(examStudentPassFail);
				if (++count % 20 == 0) {
					session.flush();
					session.clear();
				}
			}
			tx.commit();
			log.info("End of updatePassFail");
			return true;
		} catch (Exception e) {
			if(tx!=null){
				tx.rollback();
			}
			session.flush();
			session.clear();
			log.error("Error occured in updatePassFail");
			throw new ApplicationException(e);
		}
	}
	
	/**
	 * 
	 * @param classId
	 * @param schemeNo
	 * @return
	 * @throws Exception
	 */
	public boolean deleleAlreadyExistedRecordsForPassFail(Integer classId, int schemeNo)throws Exception {
		log.debug("inside deleleAlreadyExistedRecords");
		Session session = null;
		Transaction tx = null;
		try {
			session = HibernateUtil.getSession();
		    tx= session.beginTransaction();
		    String sqlQuey = " delete from EXAM_student_pass_fail " +
		    				" where class_id = :classId and scheme_no =  " + schemeNo ;
			Query query = session.createSQLQuery(sqlQuey);
			query.setParameter("classId", classId);
   		    query.executeUpdate();
			tx.commit();
			session.flush();
			session.close();
			log.debug("leaving deleleAlreadyExistedRecordsForPassFail");
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
	public ArrayList<Integer> getSubjectsForStudentWithPrevSchemeNo(
			Integer studentId, String isPrevExam, int schemeNo) {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		ArrayList<Integer> l = new ArrayList<Integer>();
		String HQL = "";
		if (isPrevExam.equalsIgnoreCase("true")) {
			HQL = "  SELECT s.id  FROM   EXAM_student_previous_class_details st"
					+ "  LEFT JOIN"
					+ "  EXAM_student_sub_grp_history asg"
					+ "  ON asg.student_id = st.student_id"
					+ "  LEFT JOIN"
					+ "  subject_group_subjects sgs"
					+ "  ON asg.subject_group_id = sgs.subject_group_id and sgs.is_active = 1"
					+ "  LEFT JOIN"
					+ "  subject s"
					+ "  ON sgs.subject_id = s.id"
					+ "  WHERE st.student_id = :studentId"
					+ " and asg.scheme_no = "
					+ schemeNo
					+ " and st.scheme_no = " + schemeNo;

		} else {
			HQL = "  SELECT s.id  FROM   student st"
					+ "  LEFT JOIN"
					+ "  applicant_subject_group asg"
					+ "  ON asg.adm_appln_id = st.adm_appln_id"
					+ "  LEFT JOIN"
					+ "  subject_group_subjects sgs"
					+ "  ON asg.subject_group_id = sgs.subject_group_id and sgs.is_active = 1"
					+ "  LEFT JOIN" + "  subject s"
					+ "  ON sgs.subject_id = s.id"
					+ "  WHERE st.id = :studentId";
		}
		Query query = session.createSQLQuery(HQL);
		query.setParameter("studentId", studentId);
		Iterator<Object> itr = query.list().iterator();
		while (itr.hasNext()) {
			Object obj = (Object) itr.next();
			if (obj != null) {
				l.add((Integer) obj);
			}

		}
		session.flush();
		session.close();
		return l;

	}

	public Classes getClasess(int classId) throws Exception
	{
		Session session=null;
		Classes classes=null;
		try
		{
			session=HibernateUtil.getSession();
			classes=(Classes)session.get(Classes.class, classId);
		}
		catch (Exception e) 
		{
			throw new ApplicationException(e);
		}
		return classes;
	}

	public List<Subject> getSubjectsForStudent(Integer stuId,String subjectPart) throws Exception
	{
		List<Subject>subjectList=null;
		Session session=null;
		try
		{
			session=HibernateUtil.getSession();
			String query="select distinct su.subject from Student s " +
					"join s.admAppln.applicantSubjectGroups sg " +
					"join sg.subjectGroup.subjectGroupSubjectses su " +
					"join su.subject.examSubDefinitionCourseWiseBOSet sd " +
					"where sd.subjectSectionId=(select  ss.id from ExamSubjectSectionMasterBO ss where ss.name='"+subjectPart+"') " +
					" and s.id="+stuId;
			subjectList=session.createQuery(query).list();
		}
		catch (Exception e) {
			// TODO: handle exception
			throw new ApplicationException(e);
		}
		return subjectList;
	}

	public void getMarksObtainedByStudent(Integer stuId, Integer subjectId,String subjectType,String theoryMarks,String practicalMarks, List<Integer> examIdList, String examType) throws Exception
	{
		Session session=null;
		try
		{
			session=HibernateUtil.getSession();
			String strQuery="select max(e)from ExamMarksEntryDetailUtilBo e " +
						"where e.subjectUtilBO.id="+subjectId+" and e.examMarksEntryBO.studentUtilBO.id="+stuId+
						" and e.examMarksEntryBO.examDefinitionBO.id in(:examList) group by e.examMarksEntryBO.studentUtilBO.id ";
			Query query=session.createQuery(strQuery);
			query.setParameterList("examList",examIdList);
			ExamMarksEntryDetailUtilBo bo=(ExamMarksEntryDetailUtilBo)query.uniqueResult();
			if(bo!=null)
			{	
				if(subjectType.equalsIgnoreCase("T"))
					theoryMarks=bo.getTheoryMarks();
				else
				if(subjectType.equalsIgnoreCase("P"))
					practicalMarks=bo.getPracticalMarks();
				else
				if(subjectType.equalsIgnoreCase("B"))
				{
					theoryMarks=bo.getTheoryMarks();
					practicalMarks=bo.getPracticalMarks();
				}
				examType=bo.getExamMarksEntryBO().getExamDefinitionBO().getExamTypeUtilBO().getName();
			}	
		}
		catch (Exception e) {
			throw new ApplicationException(e);
		}
		
		
	}

	public List<Integer> getExamsFortheCourse(int courseId) throws Exception
	{
		List<Integer> examId;
		Session session=null;
		try
		{
			String query="select e.examDefinitionBO.id from ExamDefinitionProgramBO e "+ 
							"join e.examProgramUtilBO.courseUtilBOSet co "+
							"where co.courseID="+courseId+" and e.examDefinitionBO.examTypeUtilBO.name not like '%Internal%'";
			examId=session.createQuery(query).list();
		}
		catch (Exception e) {
			// TODO: handle exception
			throw new ApplicationException(e);
		}
		return examId;
	}

	public SubjectRuleSettings getMarksToPass(int subjectId, int courseId) 
	{
		Session session=null;
		SubjectRuleSettings subjectRuleSettings=null;
		try
		{
			session=HibernateUtil.getSession();
			String query="from ExamSubjectRuleSettingsBO e where e.courseId="+courseId+" and e.subjectId="+subjectId;
			subjectRuleSettings=(SubjectRuleSettings)session.createQuery(query);
			
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return subjectRuleSettings;
		
	}

	public List<Object[]> getPassOrFail(Integer stuId) throws Exception
	{
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		List<Object[]> l = null;
		String HQL_QUERY = "";


		HQL_QUERY = " SELECT sum(if(EXAM_marks_entry_details.theory_marks is not null,EXAM_marks_entry_details.theory_marks,0)+"+
			"if(EXAM_marks_entry_details.practical_marks is not null,EXAM_marks_entry_details.practical_marks,0)) as totObtain," +
			"sum(if(EXAM_subject_rule_settings.theory_ese_minimum_mark is not null,EXAM_subject_rule_settings.theory_ese_minimum_mark,0)+" +
			"if(EXAM_subject_rule_settings.practical_ese_minimum_mark is not null,EXAM_subject_rule_settings.practical_ese_minimum_mark,0)) as minTot," +
			"sum(if(EXAM_subject_rule_settings.theory_ese_maximum_mark is not null,EXAM_subject_rule_settings.theory_ese_maximum_mark,0)+" +
			"if(EXAM_subject_rule_settings.practical_ese_maximum_mark is not null,EXAM_subject_rule_settings.practical_ese_maximum_mark,0)) as maxTot," +
			"((sum(if(EXAM_marks_entry_details.theory_marks is not null,EXAM_marks_entry_details.theory_marks,0)+" +
			"if(EXAM_marks_entry_details.practical_marks is not null,EXAM_marks_entry_details.practical_marks,0))/" +
			"sum(if(EXAM_subject_rule_settings.theory_ese_maximum_mark is not null,EXAM_subject_rule_settings.theory_ese_maximum_mark,0)+" +
			"if(EXAM_subject_rule_settings.practical_ese_maximum_mark is not null,EXAM_subject_rule_settings.practical_ese_maximum_mark,0)))*100) as per," +
			"if(sum((if(EXAM_marks_entry_details.theory_marks is not null,EXAM_marks_entry_details.theory_marks,0)+" +
			"if(EXAM_marks_entry_details.practical_marks is not null,EXAM_marks_entry_details.practical_marks,0))<" +
			"(if(EXAM_subject_rule_settings.theory_ese_minimum_mark is not null,EXAM_subject_rule_settings.theory_ese_minimum_mark,0)+" +
			"if(EXAM_subject_rule_settings.practical_ese_minimum_mark is not null,EXAM_subject_rule_settings.practical_ese_minimum_mark,0))) ||" +
			"(sum(if(EXAM_marks_entry_details.theory_marks is not null,EXAM_marks_entry_details.theory_marks,0)<" +
			"if(EXAM_subject_rule_settings.theory_ese_minimum_mark is not null,EXAM_subject_rule_settings.theory_ese_minimum_mark,0))) ||" +
			"(sum(if(EXAM_marks_entry_details.practical_marks is not null,EXAM_marks_entry_details.practical_marks,0)<" +
			" if(EXAM_subject_rule_settings.practical_ese_minimum_mark is not null,EXAM_subject_rule_settings.practical_ese_minimum_mark,0))) ||" +
			"(((sum(if(EXAM_marks_entry_details.theory_marks is not null,EXAM_marks_entry_details.theory_marks,0)+" +
			"if(EXAM_marks_entry_details.practical_marks is not null,EXAM_marks_entry_details.practical_marks,0))/" +
			"sum(if(EXAM_subject_rule_settings.theory_ese_maximum_mark is not null,EXAM_subject_rule_settings.theory_ese_maximum_mark,0)+" +
			"if(EXAM_subject_rule_settings.practical_ese_maximum_mark is not null,EXAM_subject_rule_settings.practical_ese_maximum_mark,0)))*100)<35),1,0) as PorF" +
			" FROM student student " +
			" INNER JOIN" +
			" adm_appln adm_appln" +
			" ON (student.adm_appln_id = adm_appln.id)" +
			" and (adm_appln.is_cancelled = 0)" +
			" INNER JOIN" +
			" personal_data personal_data" +
			" ON (adm_appln.personal_data_id = personal_data.id)" +
			" INNER JOIN" +
			" course course" +
			" ON (adm_appln.selected_course_id = course.id)" +
			" and (course.is_active = 1)" +
			" INNER JOIN" +
			" class_schemewise class_schemewise" +
			" ON (student.class_schemewise_id = class_schemewise.id)" +
			" INNER JOIN" +
			" classes classes" +
			" ON (classes.id=class_schemewise.class_id)" +
			" and (classes.is_active = 1)" +
			" INNER JOIN" +
			" applicant_subject_group applicant_subject_group" +
			" ON (applicant_subject_group.adm_appln_id = adm_appln.id)" +
			" INNER JOIN" +
			" subject_group subject_group" +
			" ON (applicant_subject_group.subject_group_id = subject_group.id)" +
			" INNER JOIN" +
			" subject_group_subjects  subject_group_subjects" +
			" ON (subject_group_subjects.subject_group_id = subject_group.id)" +
			" INNER JOIN" +
			" subject subject" +
			" ON(subject_group_subjects.subject_id = subject.id)" +
			" AND (subject.is_active = 1)" +
			" INNER JOIN" +
			" EXAM_subject_rule_settings EXAM_subject_rule_settings" +
			" ON (EXAM_subject_rule_settings.subject_id = subject.id)" +
			" AND (EXAM_subject_rule_settings.course_id = course.id)" +
			" INNER JOIN" +
			" EXAM_marks_entry EXAM_marks_entry" +
			" ON (EXAM_marks_entry.student_id=student.id)" +
			" INNER JOIN" +
			" EXAM_marks_entry_details EXAM_marks_entry_details" +
			" ON (EXAM_marks_entry_details.marks_entry_id=EXAM_marks_entry.id)" +
			" and (EXAM_marks_entry_details.subject_id=subject.id)" +
			" INNER JOIN" +
			" EXAM_definition EXAM_definition" +
			" ON (EXAM_marks_entry.exam_id = EXAM_definition.id)" +
			" and (EXAM_definition.del_is_active = 1)" +
			" INNER JOIN" +
			" exam_type exam_type" +
			" ON (exam_type.id = EXAM_definition.exam_type_id)" +
			" LEFT JOIN" +
			" EXAM_update_exclude_withheld EXAM_update_exclude_withheld" +
			" ON (EXAM_update_exclude_withheld.student_id = student.id)" +
			" and (EXAM_update_exclude_withheld.exam_id = EXAM_definition.id)" +
			" and (EXAM_update_exclude_withheld.course_id = course.id)" +
			" and (EXAM_update_exclude_withheld.scheme_no = classes.term_number)" +
			" LEFT JOIN" +
			" EXAM_exam_settings_course EXAM_exam_settings_course" +
			" ON (EXAM_exam_settings_course.course_id = course.id)" +
			" LEFT JOIN" +
			" EXAM_sub_definition_coursewise EXAM_sub_definition_coursewise" +
			" ON (EXAM_sub_definition_coursewise.subject_id = subject.id)" +
			" AND (EXAM_sub_definition_coursewise.course_id = EXAM_subject_rule_settings.course_id)" +
			" AND (EXAM_sub_definition_coursewise.scheme_no = classes.term_number)" +
			" INNER JOIN" +
			" EXAM_subject_sections EXAM_subject_sections" +
			" ON (EXAM_sub_definition_coursewise.subject_section_id=EXAM_subject_sections.id)" +
			" INNER JOIN" +
			" curriculum_scheme_duration curriculum_scheme_duration" +
			" ON (class_schemewise.curriculum_scheme_duration_id = curriculum_scheme_duration.id)" +
			" AND (EXAM_subject_rule_settings.academic_year=curriculum_scheme_duration.academic_year)" +
			" where student.id=" +stuId+" and exam_type.name like '%Regular%' "+
			" group by student.id,EXAM_definition.id,EXAM_subject_sections.id" ;
		
		Query query = session.createSQLQuery(HQL_QUERY);
		l = query.list();
		if (l != null && l.size() > 0) {
			return l;
		} else {
			return new ArrayList<Object[]>();
		}
	}
	
	
	public boolean deleleAlreadyExistedRecordsForaStudentPassFail(Integer classId, int schemeNo, int studentId)throws Exception {
		log.debug("inside deleleAlreadyExistedRecords");
		Session session = null;
		Transaction tx = null;
		try {
			session = HibernateUtil.getSession();
		    tx= session.beginTransaction();
		    String sqlQuey = " delete from EXAM_student_pass_fail " +
		    				" where class_id = :classId and scheme_no =  " + schemeNo + " and student_id = " +  studentId;
			Query query = session.createSQLQuery(sqlQuey);
			query.setParameter("classId", classId);
   		    query.executeUpdate();
			tx.commit();
			session.flush();
			session.close();
			log.debug("leaving deleleAlreadyExistedRecordsForPassFail");
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
	
	/**
	 * 
	 * @param courseId
	 * @return
	 */
	public List<Integer> getExcludedFromTotResultSubjects(Integer courseId, int schemeNo, int academicYear) throws Exception {
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			Session session = sessionFactory.openSession();
	
			String HQL_QUERY = "select e.subjectId from ExamSubDefinitionCourseWiseBO e"
								+ " where e.courseId = :courseId and e.schemeNo = " + schemeNo + 
								" and dontConsiderFailureTotalResult = 1 and e.academicYear = :academicYear" ;
	
			Query query = session.createQuery(HQL_QUERY);
			query.setParameter("courseId", courseId);
			query.setParameter("academicYear", academicYear);
			
			List<Integer> list = query.list();
			
			List<Integer> excludedSubject = new ArrayList<Integer>();
			if (list != null && list.size() > 0) {
	
				Iterator<Integer> itr = list.iterator();
				while (itr.hasNext()) {
					Integer subjectId = (Integer) itr.next();
					excludedSubject.add(subjectId);
				}
			}
			if (session != null) {
				session.flush();
				session.close();
			}
			return excludedSubject;			
		} catch (Exception e) {
			log.error("Error while deleting" + e);
			throw new Exception();
		}

	}

	public List<String> checkPromotionProcessNewClass(
			ArrayList<BatchClassTO> listBatchClass) throws Exception {
		Session session = null;
		List<String> classes = new ArrayList<String>();
		try {
			session = HibernateUtil.getSession();
			if(listBatchClass!=null && !listBatchClass.isEmpty()){
				Iterator<BatchClassTO> iterator=listBatchClass.iterator();
				BatchClassTO listTo = (BatchClassTO) iterator.next();
				Integer clsId = null;
				if (CommonUtil.checkForEmpty(listTo.getClassId())) {
					clsId = Integer.parseInt(listTo.getClassId());
				}
				if(clsId!=null){
				List<Object[]> list=session.createQuery("select csd.semesterYearNo,csd.curriculumScheme.id,csd.academicYear,cs.classes.sectionName,cs.classes.course.id,cs.classes.name" +
						" from CurriculumSchemeDuration csd" +
						" join csd.classSchemewises cs where cs.classes.id="+clsId).list();
				if(list!=null && !list.isEmpty()){
					int schemeNo=0;
					int csId=0;
					int year=0;
					String sectionName="";
					int courseId=0;
					String className="";
					
					Iterator<Object[]> itr=list.iterator();
					while (itr.hasNext()) {
						Object[] obj = (Object[]) itr.next();
						if(obj[0]!=null){
							schemeNo=Integer.parseInt(obj[0].toString());
							schemeNo+=1;
						}
						if(obj[1]!=null)
							csId=Integer.parseInt(obj[1].toString());
						
						if(obj[2]!=null)
							year=Integer.parseInt(obj[2].toString());
						
						if(obj[3]!=null)
							sectionName=obj[3].toString();
						
						if(obj[4]!=null)
							courseId=Integer.parseInt(obj[4].toString());
						
						if(obj[5]!=null)
							className=obj[5].toString();
						
						
						int newyear=(Integer)session.createQuery("select csd.academicYear" +
								" from CurriculumSchemeDuration csd where csd.semesterYearNo="+schemeNo+
								" and csd.curriculumScheme.id="+csId).uniqueResult();
						List<Object[]> classList=null;
						if(newyear>0){
							String newQuery="select cs.id," +
											" cs.classes.name" +
											" from CurriculumSchemeDuration csd" +
											" join csd.classSchemewises cs" +
											" where csd.academicYear=" +newyear+
											" and csd.semesterYearNo="+schemeNo+" and cs.classes.course.id="+courseId;
							if(!sectionName.isEmpty()){
								newQuery=newQuery+" and cs.classes.sectionName='"+sectionName+"'";
							}
							
							classList=session.createQuery(newQuery).list();
						
						}
						if(classList==null || classList.isEmpty()){
							classes.add(className+sectionName);
						}
							
					}
				}
			}
			}
			return classes;
		} catch (Exception e) {
			log.error("Error while retrieving selected candidates.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		
	}
}



