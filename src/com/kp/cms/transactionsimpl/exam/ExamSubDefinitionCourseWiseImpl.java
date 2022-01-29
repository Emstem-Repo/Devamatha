package com.kp.cms.transactionsimpl.exam;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.kp.cms.bo.exam.ExamSubCoursewiseAttendanceMarksBO;
import com.kp.cms.bo.exam.ExamSubCoursewiseGradeDefnBO;
import com.kp.cms.bo.exam.ExamSubDefinitionCourseWiseBO;
import com.kp.cms.bo.exam.SubjectGroupUtilBO;
import com.kp.cms.bo.exam.SubjectUtilBO;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.forms.exam.ExamSubjectDefCourseForm;
import com.kp.cms.utilities.HibernateUtil;

public class ExamSubDefinitionCourseWiseImpl extends ExamGenImpl {

	private static final Log log = LogFactory
			.getLog(ExamSubDefinitionCourseWiseImpl.class);

	@SuppressWarnings("unchecked")
	public ArrayList<ExamSubDefinitionCourseWiseBO> select_course_scheme_year(
			int courseId, int shemeId, int schemeNo, int academicYear) {

		Session session = null;
		ArrayList<ExamSubDefinitionCourseWiseBO> list;

		String HQL = "select sub.id, sub.name, sub.code, " +
				" ( select subjectOrder from ExamSubDefinitionCourseWiseBO def where def.subjectId = sub.id and def.courseId = :courseId and" +
				" def.schemeNo = :schemeNo and def.academicYear=:academicYear) as subjectOrder,"
				+" ( select theoryCredit from ExamSubDefinitionCourseWiseBO def where def.subjectId = sub.id and def.courseId = :courseId and" +
				" def.schemeNo = :schemeNo and def.academicYear=:academicYear) as theoryCredit,"
				+" ( select examSubjectSectionMasterBO.name from ExamSubDefinitionCourseWiseBO def where def.subjectId = sub.id and def.courseId = :courseId and" +
				" def.schemeNo = :schemeNo and def.academicYear=:academicYear) as subjectSection,"
				+" ( select practicalCredit from ExamSubDefinitionCourseWiseBO def where def.subjectId = sub.id and def.courseId = :courseId and" +
				" def.schemeNo = :schemeNo and def.academicYear=:academicYear) as practicalCredit"
				+ " from SubjectUtilBO sub"
				+ " where sub.id in"
				+ " (select distinct sgs.subjectUtilBO.id"
				+ " from SubjectGroupSubjectsUtilBO sgs"
				+ " where sgs.isActive=1 and sgs.subjectGroupUtilBO.id in ("
				+ " select css.subjectGroupId from CurriculumSchemeSubjectUtilBO css"
				+ " where css.curriculumSchemeDurationId in ("
				+ " select csd.id from CurriculumSchemeDurationUtilBO csd"
				+ " where csd.curriculumSchemeId in ( "
				+ " select cs.id from CurriculumSchemeUtilBO cs"
				+ " where cs.courseId = :courseId and cs.courseSchemeId = :shemeId)"
				+ " and csd.semesterYearNo = :schemeNo and csd.academicYear = :academicYear and sub.isActive=1)))";

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		session = sessionFactory.openSession();
		Query q = session.createQuery(HQL);
		q.setParameter("courseId", courseId);
		q.setParameter("shemeId", shemeId);
		q.setParameter("schemeNo", schemeNo);
		q.setParameter("academicYear", academicYear);
		list = new ArrayList<ExamSubDefinitionCourseWiseBO>(q.list());
		session.flush();
		// session.close();
		return list;
	}

	public SubjectUtilBO select_subcode_subname(int id) throws Exception {
		Session session = null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			SubjectUtilBO objBO = (SubjectUtilBO) session.get(
					SubjectUtilBO.class, id);
			session.flush();
			session.close();
			return objBO != null ? objBO : null;
		} catch (Exception e) {
			log.error(e.getMessage());
			session.flush();
			session.close();
			throw new ApplicationException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public List<ExamSubCoursewiseGradeDefnBO> select_All_grade_details(
			int subjectId, int courseId,int academicYear) {
		Session session = null;
		List<ExamSubCoursewiseGradeDefnBO> list;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			String HQL = "from ExamSubCoursewiseGradeDefnBO bo where bo.academicYear= :academicYear and bo.subjectId= :subjectId and bo.courseId = :courseId";

			Query query = session.createQuery(HQL);
			query.setParameter("subjectId", subjectId);
			query.setParameter("courseId", courseId);
			query.setParameter("academicYear", academicYear);
			list = new ArrayList<ExamSubCoursewiseGradeDefnBO>(query.list());
			session.flush();
			// session.close();
		} catch (Exception e) {
			log.error(e.getMessage());

			if (session != null) {
				session.flush();
				session.close();
			}
			list = new ArrayList<ExamSubCoursewiseGradeDefnBO>();

		}
		return list;

	}

	@SuppressWarnings("unchecked")
	public boolean isDuplicated(int id, int subjectId,
			BigDecimal startPercentage, BigDecimal endPercentage, String grade,
			String mode, int courseId,int academicYear) throws Exception {
		boolean duplicate = false;
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		Criteria crit = session
				.createCriteria(ExamSubCoursewiseGradeDefnBO.class);
		crit.add(Restrictions.eq("subjectId", subjectId));
		crit.add(Restrictions.eq("courseId", courseId));
		crit.add(Restrictions.eq("academicYear", academicYear));

		List<ExamSubCoursewiseGradeDefnBO> list = new ArrayList<ExamSubCoursewiseGradeDefnBO>(
				crit.list());

		if (list.size() > 0) {
			Iterator it = list.iterator();
			ExamSubCoursewiseGradeDefnBO objbo = null;
			while (it.hasNext()) {
				objbo = (ExamSubCoursewiseGradeDefnBO) it.next();
				BigDecimal lowestPerc = objbo.getStartPrcntgGrade();
				BigDecimal highestPerc = objbo.getEndPrcntgGrade();

				if (mode.equalsIgnoreCase("add")) {

					if (grade != null
							&& grade.equalsIgnoreCase(objbo.getGrade())) {

						if (subjectId == objbo.getSubjectId()
								&& courseId == objbo.getCourseId()) {
							throw new DuplicateException();
						}

						if ((lowestPerc.doubleValue() <= startPercentage
								.doubleValue() && startPercentage.doubleValue() <= highestPerc
								.doubleValue())
								|| (lowestPerc.doubleValue() <= endPercentage
										.doubleValue() && endPercentage
										.doubleValue() <= highestPerc
										.doubleValue())) {

							if (id != objbo.getId()) {
								if (subjectId == objbo.getSubjectId()) {
									throw new DuplicateException();
								}
							}
						}
					}

					else if (((lowestPerc.doubleValue() <= startPercentage
							.doubleValue() && startPercentage.doubleValue() <= highestPerc
							.doubleValue()) || (lowestPerc.doubleValue() <= endPercentage
							.doubleValue() && endPercentage.doubleValue() <= highestPerc
							.doubleValue()))
							|| (startPercentage.doubleValue() <= lowestPerc
									.doubleValue() && lowestPerc.doubleValue() <= endPercentage
									.doubleValue())) {
						if (id != objbo.getId()) {
							if (subjectId == objbo.getSubjectId()) {
								throw new DuplicateException();
							}
						}

					}
				}

				else {

					if (grade != null
							&& grade.equalsIgnoreCase(objbo.getGrade())) {

						if ((lowestPerc.doubleValue() <= startPercentage
								.doubleValue() && startPercentage.doubleValue() <= highestPerc
								.doubleValue())
								|| (lowestPerc.doubleValue() <= endPercentage
										.doubleValue() && endPercentage
										.doubleValue() <= highestPerc
										.doubleValue())) {

							if (id != objbo.getId()) {
								throw new DuplicateException();
							}
						}

					} else if (((lowestPerc.doubleValue() <= startPercentage
							.doubleValue() && startPercentage.doubleValue() <= highestPerc
							.doubleValue()) || (lowestPerc.doubleValue() <= endPercentage
							.doubleValue() && endPercentage.doubleValue() <= highestPerc
							.doubleValue()))
							|| (startPercentage.doubleValue() <= lowestPerc
									.doubleValue() && lowestPerc.doubleValue() <= endPercentage
									.doubleValue())) {

						if (id != objbo.getId()) {
							throw new DuplicateException();
						}

					}

				}

			}

		}
		session.flush();
		session.close();
		return duplicate;
	}

	@SuppressWarnings("unchecked")
	public ArrayList<ExamSubCoursewiseGradeDefnBO> select_GradeDefinition(
			ArrayList<Integer> listSubId) {
		Session session = null;
		ArrayList<ExamSubCoursewiseGradeDefnBO> list;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();

			Criteria crit = session
					.createCriteria(ExamSubCoursewiseGradeDefnBO.class);
			crit.add(Restrictions.in("subjectId", listSubId));

			list = new ArrayList<ExamSubCoursewiseGradeDefnBO>(crit.list());
			session.flush();
			session.close();
		} catch (Exception e) {
			log.error(e.getMessage());

			if (session != null) {
				session.flush();
				session.close();
			}
			list = new ArrayList<ExamSubCoursewiseGradeDefnBO>();

		}
		return list;

	}

	public ExamSubCoursewiseGradeDefnBO loadExamSubCoursewiseGradeDefnBO(int id)
			throws Exception {
		Session session = null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			ExamSubCoursewiseGradeDefnBO objEGCDO = (ExamSubCoursewiseGradeDefnBO) session
					.get(ExamSubCoursewiseGradeDefnBO.class, id);
			session.flush();
			// session.close();
			return objEGCDO != null ? objEGCDO : null;
		} catch (Exception e) {
			log.error(e.getMessage());
			session.flush();
			session.close();
			throw new ApplicationException(e);
		}
	}

	public boolean update(int id, int subjectId, BigDecimal startPercentage,
			BigDecimal endPercentage, String grade, String interpretation,
			String resultClass, BigDecimal gradePoint, String userId)
			throws Exception {
		Session session = null;
		Transaction transaction = null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			transaction.begin();
			ExamSubCoursewiseGradeDefnBO objbo = (ExamSubCoursewiseGradeDefnBO) session
					.get(ExamSubCoursewiseGradeDefnBO.class, id);
			objbo.setStartPrcntgGrade(startPercentage);
			objbo.setEndPrcntgGrade(endPercentage);
			objbo.setGrade(grade);
			objbo.setGradeInterpretation(interpretation);
			objbo.setResultClass(resultClass);
			objbo.setGradePoint(gradePoint);

			session.update(objbo);
			transaction.commit();
			session.flush();
			session.close();
			return true;
		} catch (Exception e) {
			log.error(e.getMessage());
			if (session != null) {
				session.flush();
				session.close();
			}
			return false;

		}
	}

	public void delete_SubDefCoursewiseEntryDetails(int id) {

		String HQL_QUERY = "delete from ExamSubCoursewiseGradeDefnBO e"
				+ " where e.id = :id";

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();

		Query query = session.createQuery(HQL_QUERY);
		query.setParameter("id", id);
		query.executeUpdate();

		tx.commit();
		session.flush();
		session.close();
	}

	@SuppressWarnings("unchecked")
	public List<ExamSubCoursewiseAttendanceMarksBO> select_All_attendance_details(
			int subjectId, int courseId) {
		Session session = null;
		List<ExamSubCoursewiseAttendanceMarksBO> list;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			String HQL = "from ExamSubCoursewiseAttendanceMarksBO bo where bo.subjectId= :subjectId and bo.courseId = :courseId ";

			Query query = session.createQuery(HQL);
			query.setParameter("subjectId", subjectId);
			query.setParameter("courseId", courseId);

			list = new ArrayList<ExamSubCoursewiseAttendanceMarksBO>(query
					.list());
			session.flush();
			// session.close();
		} catch (Exception e) {
			log.error(e.getMessage());

			if (session != null) {
				session.flush();
				session.close();
			}
			list = new ArrayList<ExamSubCoursewiseAttendanceMarksBO>();

		}
		return list;

	}

	// define attendance-----------------

	public ExamSubCoursewiseAttendanceMarksBO loadExamSubCoursewiseAttendanceMarksBO(
			int id) throws Exception {
		Session session = null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			ExamSubCoursewiseAttendanceMarksBO objEGCDO = (ExamSubCoursewiseAttendanceMarksBO) session
					.get(ExamSubCoursewiseAttendanceMarksBO.class, id);
			session.flush();
			//session.close();
			return objEGCDO != null ? objEGCDO : null;
		} catch (Exception e) {
			log.error(e.getMessage());
			session.flush();
			session.close();
			throw new ApplicationException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public boolean isDuplicatedAttendance(int id, int subjectId,
			BigDecimal startPercInput, BigDecimal endPercInput,
			BigDecimal attendanceMarks, String mode, int courseId)
			throws Exception {
		boolean duplicate = false;
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		BigDecimal toDB = new BigDecimal(0);
		String hql = "from ExamSubCoursewiseAttendanceMarksBO b where b.subjectId= :subjectId and b.courseId = :courseId";
		Query query = session.createQuery(hql);
		query.setParameter("subjectId", subjectId);
		query.setParameter("courseId", courseId);
		List<ExamSubCoursewiseAttendanceMarksBO> list = new ArrayList<ExamSubCoursewiseAttendanceMarksBO>(
				query.list());
		if (list.size() > 0) {
			Iterator it = list.iterator();
			ExamSubCoursewiseAttendanceMarksBO objbo = null;
			while (it.hasNext()) {
				objbo = (ExamSubCoursewiseAttendanceMarksBO) it.next();
				BigDecimal fromDB = objbo.getFromPrcntgAttndnc();// start
				toDB = objbo.getToPrcntgAttndnc();// end
				if ((fromDB.doubleValue() == toDB.doubleValue())
						&& (startPercInput.doubleValue() == startPercInput
								.doubleValue())
						&& (endPercInput.doubleValue() == endPercInput
								.doubleValue())) {

					if (id != objbo.getId()) {
						if (subjectId == objbo.getSubjectId()) {
							throw new DuplicateException();
						}
					}
				}

				if (mode.equals("add")) {
					if (((fromDB.doubleValue() <= startPercInput.doubleValue() && startPercInput
							.doubleValue() <= toDB.doubleValue())
							|| (fromDB.doubleValue() <= endPercInput
									.doubleValue() && endPercInput
									.doubleValue() <= toDB.doubleValue()) || (startPercInput
							.doubleValue() <= fromDB.doubleValue() && fromDB
							.doubleValue() <= endPercInput.doubleValue()))) {

						if (id != objbo.getId()) {
							if (subjectId == objbo.getSubjectId()) {
								throw new DuplicateException();
							}
						}
					}

					else if (((fromDB.doubleValue() <= startPercInput
							.doubleValue() && startPercInput.doubleValue() <= toDB
							.doubleValue())
							|| (fromDB.doubleValue() <= endPercInput
									.doubleValue() && endPercInput
									.doubleValue() <= toDB.doubleValue()) || (startPercInput
							.doubleValue() <= fromDB.doubleValue() && fromDB
							.doubleValue() <= endPercInput.doubleValue()))) {

						if (id != objbo.getId()) {
							if (subjectId == objbo.getSubjectId()) {
								throw new DuplicateException();
							}
						}
					}
				}

				else {
					if (((fromDB.doubleValue() <= startPercInput.doubleValue() && startPercInput
							.doubleValue() <= toDB.doubleValue())
							|| (fromDB.doubleValue() <= endPercInput
									.doubleValue() && endPercInput
									.doubleValue() <= toDB.doubleValue()) || (startPercInput
							.doubleValue() <= fromDB.doubleValue() && fromDB
							.doubleValue() <= endPercInput.doubleValue()))) {

						if (id != objbo.getId()) {

							if (subjectId == objbo.getSubjectId()) {
								throw new DuplicateException();
							}
						}
					}
				}
			}

		}
		session.flush();
		session.close();
		return duplicate;
	}

	public boolean updateAttendance(int id, int subjectId,
			BigDecimal startPercBD, BigDecimal endPercBD, BigDecimal marksBD)
			throws Exception {
		Session session = null;
		Transaction transaction = null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			transaction.begin();
			ExamSubCoursewiseAttendanceMarksBO objbo = (ExamSubCoursewiseAttendanceMarksBO) session
					.get(ExamSubCoursewiseAttendanceMarksBO.class, id);
			objbo.setFromPrcntgAttndnc(startPercBD);
			objbo.setToPrcntgAttndnc(endPercBD);
			objbo.setSubjectId(subjectId);
			objbo.setAttendanceMarks(marksBD);

			session.update(objbo);
			transaction.commit();
			session.flush();
			session.close();
			return true;
		} catch (Exception e) {
			log.error(e.getMessage());
			if (session != null) {
				session.flush();
				session.close();
			}
			return false;

		}
	}

	public void delete_ExamSubCoursewiseAttendanceMarksBO(int id) {

		String HQL_QUERY = "delete from ExamSubCoursewiseAttendanceMarksBO e"
				+ " where e.id = :id";

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();

		Query query = session.createQuery(HQL_QUERY);
		query.setParameter("id", id);
		query.executeUpdate();

		tx.commit();
		session.flush();
		session.close();
	}

	@SuppressWarnings("unchecked")
	public boolean checkOptionalAndSecondLang(int id) {
		boolean check = true;

		String HQL_QUERY = " select s.id from SubjectUtilBO s "
				+ "where (s.isOptionalSubject=1 or s.isSecondLanguage=1)"
				+ " and s.id= :id";

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

		Session session = sessionFactory.openSession();
		@SuppressWarnings("unused")
		Transaction tx = session.beginTransaction();

		Query query = session.createQuery(HQL_QUERY);
		query.setParameter("id", id);
		List<SubjectUtilBO> list = new ArrayList<SubjectUtilBO>(query.list());
		if (list.size() == 0) {
			check = false;
		}

		return check;
	}

	@SuppressWarnings("unchecked")
	public boolean checkCommonSubject(int id, int courseId, int academicYear) {
		boolean check = true;

		String HQL_QUERY = " select distinct subject.id from subject_group_subjects " +
				" inner join subject on subject_group_subjects.subject_id = subject.id" +
				" inner join subject_group ON subject_group_subjects.subject_group_id = subject_group.id " +
				" JOIN course ON subject_group.course_id = course.id " +
				" JOIN classes ON classes.course_id = course.id " +
				" JOIN class_schemewise ON class_schemewise.class_id = classes.id " +
				" JOIN curriculum_scheme_duration ON class_schemewise.curriculum_scheme_duration_id = curriculum_scheme_duration.id " +
				" join curriculum_scheme_subject on curriculum_scheme_subject.curriculum_scheme_duration_id = curriculum_scheme_duration.id " +
				" and curriculum_scheme_subject.subject_group_id = subject_group.id " +
				" where course.id=" +courseId+
				" AND subject.id=" +id+
				" AND subject_group.is_common_sub_grp=1 " +
				" AND subject.is_active=1 AND subject_group.is_active=1 AND subject_group_subjects.is_active=1" +
				" AND curriculum_scheme_duration.academic_year="+academicYear; 

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

		Session session = sessionFactory.openSession();

		Query query = session.createSQLQuery(HQL_QUERY);
		List<SubjectGroupUtilBO> list = new ArrayList<SubjectGroupUtilBO>(query
				.list());
		if (list.size() == 0) {
			check = false;
		}

		return check;
	}

	@SuppressWarnings("unchecked")
	public boolean isDuplicatedSubject(int subjectId, int subjectOrder,
			int courseId, int schemeNo, int academicYear) throws Exception {
		boolean check = true;

		String HQL_QUERY = "select s.id from ExamSubDefinitionCourseWiseBO s"
				+ " where s.subjectId = :subjectId and s.courseId = :courseId"
				+ " and s.subjectOrder= :subjectOrder and schemeNo= :schemeNo and s.academicYear=:academicYear";

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

		Session session = sessionFactory.openSession();

		Query query = session.createQuery(HQL_QUERY);
		query.setParameter("subjectId", subjectId);
		query.setParameter("subjectOrder", subjectOrder);
		query.setParameter("courseId", courseId);
		query.setParameter("schemeNo", schemeNo);
		query.setParameter("academicYear", academicYear);
		
		List<ExamSubDefinitionCourseWiseBO> list = new ArrayList<ExamSubDefinitionCourseWiseBO>(
				query.list());

		if (list.size() == 0) {
			check = false;
		} else {
			throw new DuplicateException();
		}

		return check;
	}

	@SuppressWarnings("unchecked")
	public List<ExamSubDefinitionCourseWiseBO> getSubjectDetailsFromSubjectId(
			int subjectId, int courseId,int schemeno,int academicYear) {
		Session session = null;
		List<ExamSubDefinitionCourseWiseBO> list = null;

		String HQL_QUERY = " select sub.id, sub.name, sub.code, e.id, e.subjectOrder, e.universitySubjectCode, e.subjectSectionId, subsec.name,"
				+ "  e.theoryHours, e.theoryCredit, e.practicalHours, e.practicalCredit,"
				+ " e.dontAddTotMarkClsDecln, e.dontShowSubType, e.dontShowMaxMarks, e.dontShowAttMarks,"
				+ " e.dontShowMinMarks, e.dontConsiderFailureTotalResult, e.showInternalFinalMarkAdded, e.showOnlyGrade"
				+ " from ExamSubDefinitionCourseWiseBO e"
				+ "  right join e.subjectUtilBO sub"
				+ " join e.examSubjectSectionMasterBO subsec"
				+ " where sub.id = :subjectId and e.courseId=:courseId and e.schemeNo=:schemeNo and e.academicYear=:academicYear";

		try {

			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

			session = sessionFactory.openSession();
			Transaction tx = session.beginTransaction();

			Query query = session.createQuery(HQL_QUERY);
			query.setParameter("subjectId", subjectId);
			query.setParameter("courseId", courseId);
			query.setParameter("schemeNo", schemeno);
			query.setParameter("academicYear", academicYear);
			list = new ArrayList<ExamSubDefinitionCourseWiseBO>(query.list());

			tx.commit();
			session.flush();
			// session.close();

			return list;
		} catch (Exception e) {
			log.error(e.getMessage());
			if (session != null) {
				session.flush();
				session.close();
			}
			list = new ArrayList<ExamSubDefinitionCourseWiseBO>();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public int isPresentDetails(int subjectId, int courseId,int schemeNo, int academicYear) {
		int isPresent = 0;
		Session session = null;
		List<ExamSubDefinitionCourseWiseBO> list = null;
		try {

			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			String HQL = " select bo.id from ExamSubDefinitionCourseWiseBO bo "
					+ "where bo.subjectId= :subjectId and bo.courseId=:courseId and bo.schemeNo=:schemeNo and bo.academicYear=:academicYear";

			Query query = session.createQuery(HQL);
			query.setParameter("subjectId", subjectId);
			query.setParameter("courseId", courseId);
			query.setParameter("schemeNo", schemeNo);
			query.setParameter("academicYear", academicYear);
			list = new ArrayList<ExamSubDefinitionCourseWiseBO>(query.list());
			session.flush();
			// session.close();

		} catch (Exception e) {

			log.error(e.getMessage());
			if (session != null) {
				session.flush();
				session.close();
			}

			list = new ArrayList<ExamSubDefinitionCourseWiseBO>();

		}

		Iterator it = list.iterator();
		while (it.hasNext()) {
			Integer i = (Integer) it.next();
			isPresent = i.intValue();
		}
		return isPresent;
	}

	@SuppressWarnings("unchecked")
	public boolean isDuplicatedGradeForSubject(int id, int subjectId,
			BigDecimal startPercentage, BigDecimal endPercentage, String grade,
			String mode) throws Exception {
		boolean duplicate = false;
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		@SuppressWarnings("unused")
		BigDecimal lowestPerc = new BigDecimal(0);
		@SuppressWarnings("unused")
		BigDecimal highestPerc = new BigDecimal(0);

		Criteria crit = session
				.createCriteria(ExamSubCoursewiseGradeDefnBO.class);
		crit.add(Restrictions.eq("subjectId", subjectId));

		List<ExamSubCoursewiseGradeDefnBO> list = new ArrayList<ExamSubCoursewiseGradeDefnBO>(
				crit.list());

		if (list.size() > 0) {
			Iterator it = list.iterator();
			ExamSubCoursewiseGradeDefnBO objbo = null;
			while (it.hasNext()) {
				objbo = (ExamSubCoursewiseGradeDefnBO) it.next();
				lowestPerc = objbo.getStartPrcntgGrade();
				highestPerc = objbo.getEndPrcntgGrade();

				if (grade != null && grade.equalsIgnoreCase(objbo.getGrade())) {
					if (id != objbo.getId()) {

						throw new DuplicateException();

					}
				}

			}

		}

		session.flush();
		session.close();
		return duplicate;
	}

	@SuppressWarnings("unchecked")
	public String checkIfTheoryOrPractical(int subjectId) {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Criteria crit = session.createCriteria(SubjectUtilBO.class);
		crit.add(Restrictions.eq("id", subjectId));

		List<SubjectUtilBO> list = new ArrayList<SubjectUtilBO>(crit.list());

		session.flush();
		session.close();

		return list.get(0).getIsTheoryPractical();

	}

	@SuppressWarnings("unchecked")
	public void duplicateOrderCheck(int subjectOrder, int subjectId,
			int courseId, int schemeNo, int academicYear) throws DuplicateException {
		Session session = null;
		List<ExamSubDefinitionCourseWiseBO> list = null;

		try {

			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			String HQL = " select bo.subjectId from ExamSubDefinitionCourseWiseBO bo "
					+ "where bo.courseId=:courseId and bo.subjectOrder=:subjectOrder and bo.subjectId != :subjectId " +
					  " and schemeNo = :schemeNo and bo.academicYear = :academicYear";

			Query query = session.createQuery(HQL);
			query.setParameter("subjectId", subjectId);
			query.setParameter("courseId", courseId);
			query.setParameter("subjectOrder", subjectOrder);
			query.setParameter("schemeNo", schemeNo);
			query.setParameter("academicYear", academicYear);
			list = new ArrayList<ExamSubDefinitionCourseWiseBO>(query.list());
			session.flush();
			// session.close();

		} catch (Exception e) {

			log.error(e.getMessage());
			if (session != null) {
				session.flush();
				session.close();
			}

			list = new ArrayList<ExamSubDefinitionCourseWiseBO>();

		}

		Iterator it = list.iterator();
		while (it.hasNext()) {
			Integer i = (Integer) it.next();
			List l=session.createQuery("select subSet.subject.id from CurriculumSchemeDuration cd join cd.curriculumSchemeSubjects cs join cs.subjectGroup subGrp join subGrp.subjectGroupSubjectses subSet" +
					" where cd.academicYear="+academicYear+" and cd.semesterYearNo="+schemeNo+
					" and cd.curriculumScheme.course.id= " +courseId+
					"and subSet.isActive=1 and subGrp.isActive=1 and subSet.subject.id="+i).list();
			if(l!=null && !l.isEmpty()){
				if (checkCommonSubject(i, courseId,academicYear)) {
					throw new DuplicateException();
				}
			}
		}

	}

	public List<String> checkExisted(String query, String[] courseIds)
	throws Exception {
		Session session = null;
		List<String> courseName=new ArrayList<String>();
		List<Integer> existList = null;
		try {
			session = HibernateUtil.getSession();
			Query selectedCandidatesQuery=session.createQuery(query);
			existList = selectedCandidatesQuery.list();
			for(int i=0;i<courseIds.length;i++){
				if(!existList.contains(Integer.parseInt(courseIds[i]))){
					String name=(String)session.createQuery("select c.name from Course c where c.id="+courseIds[i]).uniqueResult();
					courseName.add(name);
				}
			}
			return courseName;
		} catch (Exception e) {
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
	}
	
	public List<String> checkExistedCopy(String query, String[] courseIds)
	throws Exception {
		Session session = null;
		List<String> courseName=new ArrayList<String>();
		List<Integer> existList = null;
		try {
			session = HibernateUtil.getSession();
			Query selectedCandidatesQuery=session.createQuery(query);
			existList = selectedCandidatesQuery.list();
			for(int i=0;i<courseIds.length;i++){
				if(existList.contains(Integer.parseInt(courseIds[i]))){
					String name=(String)session.createQuery("select c.name from Course c where c.id="+courseIds[i]).uniqueResult();
					courseName.add(name);
				}
			}
			return courseName;
		} catch (Exception e) {
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
	}
	
	public List<String> getSubjectsForCopy(ExamSubjectDefCourseForm objform) throws Exception {
		Session session = null;
		List<Object[]> subList = null;
		List<String> str=new ArrayList<String>();
		try {
			session = HibernateUtil.getSession();
			String schemeNo="";
			if(objform.getScheme()!=null && !objform.getScheme().isEmpty()){
				String[] termNo=objform.getScheme().split("_");
				schemeNo = termNo[1];
			}else{
				if(!objform.getSchemeType().isEmpty()){
					if(objform.getSchemeType().equalsIgnoreCase("1")){
						schemeNo="1,3,5,7,9";
					}else if(objform.getSchemeType().equalsIgnoreCase("2")){
						schemeNo="2,4,6,8,10";
					}else{
						schemeNo="1,2,3,4,5,6,7,8,9,10";
					}
				}
			}
			String [] tempArray = objform.getCourseIds();
			StringBuilder intType = new StringBuilder();
			for(int i=0;i<tempArray.length;i++){
				 intType.append(tempArray[i]);
				 if(i<(tempArray.length-1)){
					 intType.append(",");
				 }
			}
			String q="select cd.curriculumScheme.course.id,ss.subject.id,cd.semesterYearNo from CurriculumSchemeDuration cd" +
					" join cd.curriculumSchemeSubjects cs" +
					" join cs.subjectGroup.subjectGroupSubjectses ss" +
					" where cd.semesterYearNo in ("+schemeNo+") and cd.academicYear="+objform.getToYear()+
					" and cd.curriculumScheme.course.id in("+intType+")";
			Query selectedCandidatesQuery=session.createQuery(q);
			subList = selectedCandidatesQuery.list();
			if(subList!=null && !subList.isEmpty()){
				Iterator<Object[]> itr=subList.iterator();
				while (itr.hasNext()) {
					Object[] obj = (Object[]) itr.next();
					if(obj[0]!=null && obj[1]!=null && obj[2]!=null){
						str.add(obj[0].toString()+"_"+obj[1].toString()+"_"+obj[2].toString());
					}
				}
			}
			return str;
		} catch (Exception e) {
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
	}
	
	public List<ExamSubCoursewiseGradeDefnBO> select_All_grade_detailsForMarksCard(
			int subjectId, int courseId,int academicYear) {
		Session session = null;
		List<ExamSubCoursewiseGradeDefnBO> list;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			String HQL = "from ExamSubCoursewiseGradeDefnBO bo where bo.academicYear= :academicYear and bo.subjectId= :subjectId and bo.courseId = :courseId order by bo.endPrcntgGrade";

			Query query = session.createQuery(HQL);
			query.setParameter("subjectId", subjectId);
			query.setParameter("courseId", courseId);
			query.setParameter("academicYear", academicYear);
			list = new ArrayList<ExamSubCoursewiseGradeDefnBO>(query.list());
			session.flush();
			// session.close();
		} catch (Exception e) {
			log.error(e.getMessage());

			if (session != null) {
				session.flush();
				session.close();
			}
			list = new ArrayList<ExamSubCoursewiseGradeDefnBO>();

		}
		return list;

	}
}
