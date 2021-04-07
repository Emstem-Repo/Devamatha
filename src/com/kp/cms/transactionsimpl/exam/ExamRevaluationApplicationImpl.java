package com.kp.cms.transactionsimpl.exam;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.kp.cms.bo.exam.CurriculumSchemeUtilBO;
import com.kp.cms.bo.exam.ExamDefinitionBO;
import com.kp.cms.bo.exam.ExamRevaluationApplicationBO;
import com.kp.cms.bo.exam.ExamRevaluationApplicationSubjectBO;
import com.kp.cms.utilities.HibernateUtil;

public class ExamRevaluationApplicationImpl extends ExamGenImpl {

	@SuppressWarnings("unchecked")
	public int select_year(int examId) {
		int year = 0;
		Session session = null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			String HQL_QUERY = "from ExamDefinitionBO e where e.id =:id ";
			Query query = session.createQuery(HQL_QUERY);
			query.setParameter("id", examId);
			Iterator i = query.list().iterator();
			while (i.hasNext()) {
				ExamDefinitionBO e = (ExamDefinitionBO) i.next();
				year = e.getYear();
			}
			session.flush();
			session.close();
		} catch (Exception e) {

			if (session != null) {
				session.flush();
				session.close();
			}
		}
		return year;
	}

	@SuppressWarnings("unchecked")
	public ArrayList<CurriculumSchemeUtilBO> select_student(int courseId,
			int year, int schemeId) {
		Session session = null;
		ArrayList<CurriculumSchemeUtilBO> list;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			String HQL_QUERY = "from StudentUtilBO s "
					+ " where s.classSchemewiseUtilBO.curriculumSchemeDurationUtilBO.curriculumSchemeUtilBO.courseId =:courseId "
					+ " and  s.classSchemewiseUtilBO.curriculumSchemeDurationUtilBO.curriculumSchemeUtilBO.year =:year"
					+ " and s.classSchemewiseUtilBO.curriculumSchemeDurationUtilBO.curriculumSchemeUtilBO.courseSchemeId =:schemeId ";
			Query query = session.createQuery(HQL_QUERY);
			query.setParameter("year", year);
			query.setParameter("courseId", courseId);
			query.setParameter("schemeId", schemeId);
			list = new ArrayList(query.list());
			session.flush();
			session.close();
		} catch (Exception e) {

			if (session != null) {
				session.flush();
				session.close();
			}
			list = new ArrayList<CurriculumSchemeUtilBO>();

		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public ArrayList<ExamRevaluationApplicationSubjectBO> select_RevaluationCheckmarks(
			int examRevaluationId) {
		Session session = null;
		ArrayList<ExamRevaluationApplicationSubjectBO> list;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();

			Criteria crit = session
					.createCriteria(ExamRevaluationApplicationSubjectBO.class);
			crit.add(Restrictions.eq("revaluationAppId", examRevaluationId));

			list = new ArrayList(crit.list());
			session.flush();
			//session.close();
		} catch (Exception e) {

			if (session != null) {
				session.flush();
				session.close();
			}
			list = new ArrayList<ExamRevaluationApplicationSubjectBO>();

		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public ArrayList<ExamRevaluationApplicationBO> select(int examId,
			int courseId, int schemeId) {
		Session session = null;
		ArrayList<ExamRevaluationApplicationBO> list;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();

			Criteria crit = session
					.createCriteria(ExamRevaluationApplicationBO.class);
			crit.add(Restrictions.eq("examId", examId));
			crit.add(Restrictions.eq("courseId", courseId));
			if (schemeId != 0) {
				crit.add(Restrictions.eq("schemeNo", schemeId));
			}
			crit.add(Restrictions.eq("isActive", true));

			list = new ArrayList(crit.list());
			session.flush();
			// session.close();
		} catch (Exception e) {
			if (session != null) {
				session.flush();
				session.close();
			}
			list = new ArrayList<ExamRevaluationApplicationBO>();

		}
		return list;

	}

	@SuppressWarnings("unchecked")
	public ArrayList<ExamRevaluationApplicationBO> select_ForID(
			int examRevaluationId) {
		Session session = null;

		ArrayList<ExamRevaluationApplicationBO> list;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();

			Criteria crit = session
					.createCriteria(ExamRevaluationApplicationBO.class);
			crit.add(Restrictions.eq("id", examRevaluationId));
			crit.setMaxResults(1);

			list = new ArrayList(crit.list());
			session.flush();
			// session.close();
		} catch (Exception e) {
			if (session != null) {
				session.flush();
				session.close();
			}
			list = new ArrayList<ExamRevaluationApplicationBO>();

		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public List select_Student(String rollNo, String registerNo) {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		boolean rollNoPresent = false, regNoPresent = false;
		if (!(rollNo == null)) {
			rollNoPresent = true;
		}
		if (!(registerNo == null)) {
			regNoPresent = true;
		}

		StringBuffer SQL_QUERY = new StringBuffer(
				"select s.admApplnUtilBO.personalDataUtilBO.id, s.admApplnUtilBO.personalDataUtilBO.firstName,s.rollNo,s.registerNo "
						+ " from StudentUtilBO s ");
		String whereQuery = " where ";
		String rollNoQuery = "s.rollNo = :rollNo";
		String regNoQuery = "s.registerNo = :registerNo";

		if (rollNoPresent && !regNoPresent) {
			SQL_QUERY = SQL_QUERY.append(whereQuery);
			SQL_QUERY = SQL_QUERY.append(rollNoQuery);
		} else if (!rollNoPresent && regNoPresent) {
			SQL_QUERY = SQL_QUERY.append(whereQuery);
			SQL_QUERY = SQL_QUERY.append(regNoQuery);
		} else if (rollNoPresent && regNoPresent) {
			SQL_QUERY = SQL_QUERY.append(whereQuery);
			SQL_QUERY = SQL_QUERY.append(rollNoQuery);
			SQL_QUERY = SQL_QUERY.append(" and ");
			SQL_QUERY = SQL_QUERY.append(regNoQuery);

		}
		String QUERY = SQL_QUERY.toString();
		Query query = session.createQuery(QUERY);
		if (rollNoPresent && !regNoPresent) {
			query.setParameter("rollNo", rollNo);
		} else if (!rollNoPresent && regNoPresent) {
			query.setParameter("registerNo", registerNo);
		} else if (rollNoPresent && regNoPresent) {
			query.setParameter("rollNo", rollNo);
			query.setParameter("registerNo", registerNo);

		}

		return query.list();

	}

	public int insert_returnId(ExamRevaluationApplicationBO objBO) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		int id = 0;
		try {

			session.save(objBO);

			id = objBO.getId();

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

	public void updateRevalApp(int revaluationAppId, int revaluationTypeId,
			String amount, String userId, Date applicationDate) {
		try {
			String QUERY = "update ExamRevaluationApplicationBO r set"
					+ " r.applicationDate = :applicationDate, r.revaluationTypeId = :revaluationTypeId, "
					+ " r.amount = :amount, r.lastModifiedDate = :date,"
					+ " r.modifiedBy = :userId where r.id = :id";

			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			Session session = sessionFactory.openSession();
			Transaction tx = session.beginTransaction();
			Query query = session.createQuery(QUERY);
			query.setParameter("id", revaluationAppId);
			query.setParameter("applicationDate", applicationDate);
			query.setParameter("revaluationTypeId", revaluationTypeId);
			query.setParameter("amount", new BigDecimal(amount));
			query.setParameter("date", new Date());
			query.setParameter("userId", userId);

			query.executeUpdate();
			tx.commit();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void deleteRevalAppSub(int id) {

		String HQL_QUERY = "delete from ExamRevaluationApplicationSubjectBO r"
				+ " where r.revaluationAppId = :id";

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();

		Query query = session.createQuery(HQL_QUERY);
		query.setParameter("id", id);
		query.executeUpdate();
		tx.commit();
		session.close();

	}

	public ExamRevaluationApplicationBO getDetails(int id) {
		Session session = null;
		ExamRevaluationApplicationBO s = null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();

			Criteria crit = session
					.createCriteria(ExamRevaluationApplicationBO.class);
			crit.add(Restrictions.eq("id", id));
			s = (ExamRevaluationApplicationBO) crit.uniqueResult();
			session.flush();
			// session.close();
		} catch (Exception e) {
			// log.error(e.getMessage());
			if (session != null) {
				session.flush();
				session.close();
			}

		}
		return s;
	}

	@SuppressWarnings("unchecked")
	public List<Object[]> select_SubjectsForAdd(Integer courseId,
			Integer examId, Integer schemeNo) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Query query = null;
		String SQL_QUERY = " SELECT DISTINCT subject.name, subject.id,  subject.code"
				+ "  FROM subject"
				+ " WHERE subject.id IN"
				+ " (SELECT sgs.subject_id"
				+ " FROM subject_group_subjects sgs"
				+ " WHERE sgs.subject_group_id IN"
				+ " (SELECT css.subject_group_id"
				+ " FROM curriculum_scheme_subject css"
				+ " WHERE css.curriculum_scheme_duration_id IN"
				+ " (SELECT csd.id FROM curriculum_scheme_duration csd"
				+ " WHERE csd.curriculum_scheme_id IN"
				+ " (SELECT cs.id FROM curriculum_scheme cs"
				+ " WHERE cs.course_id = :courseId"
				+ " AND cs.course_scheme_id IN"
				+ " (select ec.course_scheme_id from EXAM_exam_course_scheme_details ec "
				+ " where ec.exam_id = :examId and ec.course_id =:courseId and ec.scheme_no=:schemeNo)"
				+ " AND csd.semester_year_no = :schemeNo"
				+ " AND csd.academic_year IN (SELECT e.academic_year"
				+ " FROM EXAM_definition e" + " WHERE e.id = :examId)))))";
		try {
			query = session.createSQLQuery(SQL_QUERY);
			query.setParameter("courseId", courseId);
			query.setParameter("examId", examId);
			query.setParameter("schemeNo", schemeNo);
		} catch (Exception e) {
		}
		List<Object[]> list = query.list();
		if (list != null && list.size() > 0)
			return new ArrayList<Object[]>(list);
		return new ArrayList<Object[]>();

	}

	@SuppressWarnings("unchecked")
	public List<Object[]> select_SubjectsForCourseScheme(Integer courseId,
			Integer examId, Integer schemeNo, Integer revalTypeId) {
		List<Object[]> list = new ArrayList<Object[]>();
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		try {

			String HQL_QUERY = " SELECT DISTINCT subject.id, subject.name, subject.code, rs.subject_id rsid"
					+ " FROM  subject LEFT JOIN EXAM_re_valuation_application_subject rs"
					+ " ON rs.subject_id = subject.id AND rs.revaluation_app_id = :revalTypeId"
					+ " LEFT JOIN EXAM_re_valuation_application r"
					+ " ON rs.revaluation_app_id = r.id"
					+ " WHERE subject.id IN (SELECT sgs.subject_id"
					+ " FROM subject_group_subjects sgs"
					+ " WHERE sgs.subject_group_id IN"
					+ " (SELECT css.subject_group_id"
					+ " FROM curriculum_scheme_subject css"
					+ " WHERE css.curriculum_scheme_duration_id IN"
					+ " (SELECT csd.id FROM curriculum_scheme_duration csd"
					+ " WHERE csd.curriculum_scheme_id IN"
					+ " (SELECT cs.id FROM curriculum_scheme cs"
					+ " WHERE cs.course_id = :courseId"
					+ " AND cs.course_scheme_id IN"
					+ " (select ec.course_scheme_id from EXAM_exam_course_scheme_details ec "
					+ " where ec.exam_id = :examId and ec.course_id =:courseId and ec.scheme_no=:schemeNo)"
					+ " AND csd.semester_year_no = :schemeNo AND csd.academic_year IN"
					+ " (SELECT e.academic_year"
					+ " FROM EXAM_definition e"
					+ " WHERE e.id =  :examId)))))";

			Query query = session.createSQLQuery(HQL_QUERY);
			query.setParameter("courseId", courseId);
			query.setParameter("examId", examId);
			query.setParameter("schemeNo", schemeNo);
			query.setParameter("revalTypeId", revalTypeId);
			list = query.list();

		} catch (Exception e) {
			session.close();
			sessionFactory.close();
		} finally {
			// session.flush();
		}
		return list;

	}

	public ExamRevaluationApplicationBO getRevaluationApplicationid(int examId,
			int studentId, int courseId, int schemeNo) {
		Session session = null;
		ExamRevaluationApplicationBO s = null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();

			Criteria crit = session
					.createCriteria(ExamRevaluationApplicationBO.class);
			crit.add(Restrictions.eq("examId", examId));
			crit.add(Restrictions.eq("studentId", studentId));
			crit.add(Restrictions.eq("courseId", courseId));
			crit.add(Restrictions.eq("schemeNo", schemeNo));
			crit.add(Restrictions.eq("isActive", true));
			s = (ExamRevaluationApplicationBO) crit.uniqueResult();
			session.flush();
			// session.close();
		} catch (Exception e) {
			// log.error(e.getMessage());
			if (session != null) {
				session.flush();
				session.close();
			}

		}
		return s;
	}

}
