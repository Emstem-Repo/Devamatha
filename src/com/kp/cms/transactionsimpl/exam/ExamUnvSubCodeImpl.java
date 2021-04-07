package com.kp.cms.transactionsimpl.exam;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.kp.cms.bo.exam.CurriculumSchemeUtilBO;
import com.kp.cms.utilities.HibernateUtil;

public class ExamUnvSubCodeImpl extends ExamGenImpl {
	@SuppressWarnings("unchecked")
	public List<Object[]> select_UnvSubCode(int courseId, int schemeNo,
			int academicYear, int schmeId) {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();


		String HQL_QUERY = " SELECT s.id, s.name, s.code, s.universitySubjectCode  FROM SubjectUtilBO s"

				+ " where s.id in (select sgs.subject.id FROM SubjectGroupSubjects sgs"

				+ " where sgs.subjectGroup.id in (select  css.subjectGroup.id from CurriculumSchemeSubject css"

				+ " where css.curriculumSchemeDuration.id in (select csd.id from CurriculumSchemeDuration csd"

				+ " where csd.curriculumScheme.id in (select cs.id from CurriculumScheme cs"

				+ " where cs.course.id= :courseId"

				+ " and cs.courseScheme.id= :schemeId"

				+ " and csd.academicYear= :academicYear"

				+ " and csd.semesterYearNo= :schemeNo  ) ) ) )";

		Query query = session.createQuery(HQL_QUERY);
		query.setParameter("academicYear", academicYear);
		query.setParameter("courseId", courseId);
		query.setParameter("schemeId", schmeId);
		query.setParameter("schemeNo", schemeNo);
		List<Object[]> list = query.list();
		return list;
	}

	public void update(int subjectId, String univCode) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		String hqlUpdate = "update Subject s set s.universitySubjectCode = :univCode where s.id = :subjectId";

		session.createQuery(hqlUpdate).setInteger("subjectId", subjectId)
				.setString("univCode", univCode).executeUpdate();
		tx.commit();
		session.flush();
		session.close();
	}

	@SuppressWarnings("unchecked")
	public List<CurriculumSchemeUtilBO> select_Scheme_IdBy_Course(int courseId,
			int academicYear) {
		Session session = null;
		List<CurriculumSchemeUtilBO> list = new ArrayList<CurriculumSchemeUtilBO>();
		;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			Criteria crit = session
					.createCriteria(CurriculumSchemeUtilBO.class);

			crit.add(Restrictions.eq("courseId", courseId));
			crit.add(Restrictions.eq("year", academicYear));

			list = crit.list();
			session.flush();
		} catch (Exception e) {
			if (session != null) {
				session.flush();
				session.close();
			}

		}
		return list;

	}
}
